package lab7.server.presenter.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.SocketChannel;
import lab7.serializedobjects.ServerMessage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import lab7.serializedobjects.MessagesType;
import java.nio.ByteBuffer;

import lab7.server.presenter.Presenter;
import lab7.server.presenter.exceptions.*;


public class ClientProcessor {
    private final int BUFFER_CAPACITY = 500000;
    private ByteBuffer buffer;
    private byte[] dataArray;
    private SocketChannel socket;
    private Logger logger;
    private Presenter presenter;

    public ClientProcessor(SocketChannel clientSocket, Logger logger, Presenter presenter) {
        this.dataArray = new byte[BUFFER_CAPACITY];
        this.buffer = ByteBuffer.wrap(dataArray);
        this.buffer.limit(BUFFER_CAPACITY);
        this.socket = clientSocket;
        this.logger = logger;
        this.presenter = presenter;
    }

    public void start() {
        startDataProcessingThread();
    }

    private void read() throws IOException {
        this.logger.info("⬇️ Получение данных");
        this.buffer.clear();
        int data = this.socket.read(this.buffer);
        if (data != -1) {
            this.logger.info("✅ Получены данные...");
            this.logger.debug("{}...", Arrays.toString(this.dataArray).substring(0, 50));
        } else {
            this.logger.error("❌ Данные не получены");
        }
    }

    private void processData() {
        Object answer;
        
        try (ByteArrayInputStream bis = new ByteArrayInputStream(this.dataArray, this.buffer.arrayOffset(), this.buffer.position());
             ObjectInputStream os = new ObjectInputStream(bis)) {
            Object obj = os.readObject();
            answer = this.presenter.processClientsObject(obj);
            bufferObject(answer);
            this.logger.info("🔃 Данные обработаны");
        } catch (IOException | ClassNotFoundException | UnsupportedObjectReceivedException | InputEndedException e) {
            this.logger.info("❌ Данные не обработаны: {}", e.getLocalizedMessage(), e);
            List<ServerMessage> messages = new ArrayList<ServerMessage>();
            messages.add(new ServerMessage(MessagesType.ERROR, "Ошибка обработки данных сервером"));
            answer = messages;
        }

    }

    private void bufferObject(Object obj) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream os = new ObjectOutputStream(bos)) {
            os.writeObject(obj);
            os.flush();
            byte[] bytes = bos.toByteArray();
            if (bytes.length > BUFFER_CAPACITY) {
                throw new IOException(String.format("Недостаточный размер буфера (надо %d)", bytes.length));
            }

            for (int i = 0; i < bytes.length; i++) {
                this.dataArray[i] = bytes[i];
            }
            
            this.buffer.position(bytes.length);
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
    
    private void write() throws IOException {
        this.logger.info("🔼 Отправка данных...");
        this.logger.debug("{}...", Arrays.toString(this.dataArray).substring(0, 50));
        buffer.flip();
        int data = -1;
            data = this.socket.write(buffer);
        if (data != -1) {
            this.logger.info("✅ Данные отправлены");
        } else {
            this.logger.error("❌ Данные не отправлены");
        }
    }

    private void startDataProcessingThread() {
        Thread processDataThread = new Thread(() -> {
            try {
                read();
                processData();
                startDataWritingThread();
            } catch (IOException e) {
                logger.error("Ошибка в потоке обработки данных {}", e.getLocalizedMessage(), e);
            }
        });
        
        processDataThread.start();
    }
    
    private void startDataWritingThread() {
        Thread writeDataThread = new Thread(() -> {
            try {
                write();
            } catch (IOException e) {
                logger.error("Ошибка в потоке отправки данных {}", e.getLocalizedMessage(), e);
            }
        });

        writeDataThread.start();
    }
}
