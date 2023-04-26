package lab7.server.presenter.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.AlreadyBoundException;
import java.nio.channels.ServerSocketChannel;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.net.InetAddress;
import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.ByteBuffer;
import java.util.Set;
import lab7.serializedobjects.ServerMessage;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import lab7.serializedobjects.MessagesType;
import lab7.server.presenter.Presenter;
import lab7.server.presenter.exceptions.*;


public class TCPChannelServer implements Server {

    private final int BUFFER_CAPACITY = 500000;  // TODO: change it
    private ByteBuffer buffer;
    private byte[] dataArray;
    private ServerSocketChannel server;
    private Presenter presenter;
    private Selector selector;
    private Logger logger;
    
    public TCPChannelServer(int serverPort, Logger logger) throws IOException, AlreadyBoundException {
        this.logger = logger;

        this.server = ServerSocketChannel.open();
        SocketAddress addr = new InetSocketAddress(InetAddress.getLocalHost(), serverPort);
        this.server.bind(addr);   
        this.logger.info("Сервер запущен по адресу: {}", addr);

        this.dataArray = new byte[BUFFER_CAPACITY];
        this.buffer = ByteBuffer.wrap(dataArray);
        this.buffer.limit(BUFFER_CAPACITY);
    }

    public TCPChannelServer(int serverPort, Presenter presenter, Logger logger) throws IOException, AlreadyBoundException {
        this(serverPort, logger);
        setPresenter(presenter);
    }

    @Override
    public void start() throws IOException {
        this.selector = Selector.open();
        
        this.server.configureBlocking(false);
        this.server.register(selector, SelectionKey.OP_ACCEPT);
        
        boolean isWorking = true;
        while (isWorking) {
            
            selector.selectNow();
            Set<SelectionKey> keys = selector.selectedKeys();

            if (keys.isEmpty()) {
                getPresenter().start();
                if (getPresenter().getView() == null) {
                    isWorking = false;
                    break;
                }
            }

            for (Iterator<SelectionKey> iter = keys.iterator(); iter.hasNext(); iter.remove()) {
                SelectionKey key = iter.next();

                if (key.isValid()) {
                    if (key.isAcceptable()) {
                        accept(key);
                    } else if (key.isReadable()) {
                        read(key);
                        processData();
                    } else if (key.isWritable()) {
                        write(key);
                    } else {
                        isWorking = false;
                        break;
                    }
                } else { 
                    isWorking = false;
                    break;
                }
            }
        }
    }

    @Override
    public void stop() throws IOException {
        if (this.selector.isOpen())
            this.selector.close();
    }

    @Override
    public Presenter getPresenter() {
        return this.presenter;
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Logger getLogger() {
        return this.logger;
    }

    private void accept(SelectionKey key) throws IOException {
        this.logger.info("🤝 Установлено соединение с клиентом");
        this.server = (ServerSocketChannel) key.channel();
        SocketChannel socket = this.server.accept();
        socket.configureBlocking(false);
        socket.register(key.selector(), SelectionKey.OP_READ);
    }

    private void read(SelectionKey key) throws IOException {
        this.logger.info("⬇️ Получение данных");
        SocketChannel socket = (SocketChannel) key.channel();
        this.buffer.clear();
        int data = socket.read(this.buffer);
        if (data != -1) {
            this.logger.info("✅ Получены данные...");
            this.logger.debug("{}...", Arrays.toString(this.dataArray).substring(0, 50));
            socket.configureBlocking(false);
            socket.register(key.selector(), SelectionKey.OP_WRITE);
        } else {
            this.logger.error("❌ Данные не получены");
            socket.configureBlocking(false);
            socket.register(key.selector(), SelectionKey.OP_CONNECT);
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
    
    private void write(SelectionKey key) throws IOException {
        this.logger.info("🔼 Отправка данных...");
        this.logger.debug("{}...", Arrays.toString(this.dataArray).substring(0, 50));
        SocketChannel socket = (SocketChannel) key.channel();
        this.buffer.flip();
        int data = socket.write(this.buffer);
        if (data != -1) {
            this.logger.info("✅ Данные отправлены");
            socket.configureBlocking(false);
            socket.register(key.selector(), SelectionKey.OP_CONNECT);
        } else {
            this.logger.error("❌ Данные не отправлены");
        }
    }

}
