package lab6.server.Presenter;

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
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lab6.serializedobjects.ServerMessage;
import lab6.serializedobjects.MessagesType;
import lab6.serializedobjects.SerializedCommand;
import lab6.server.Presenter.Exceptions.InputEndedException;
import lab6.server.Presenter.Exceptions.UnsupportedObjectReceivedException;


public class TCPChannelServer implements IServer {

    private final int BUFFER_CAPACITY = 500000;  // TODO: change it
    private ByteBuffer buffer;
    private byte[] dataArray;
    private ServerSocketChannel server;
    private IPresenter presenter;
    private Selector selector;
    
    public TCPChannelServer(int serverPort) throws IOException, AlreadyBoundException {
        this.server = ServerSocketChannel.open();
        SocketAddress addr = new InetSocketAddress(InetAddress.getLocalHost(), serverPort);
        this.server.bind(addr);   
        System.out.printf("Сервер запущен по адресу: %s\n", addr);

        this.dataArray = new byte[BUFFER_CAPACITY];
        this.buffer = ByteBuffer.wrap(dataArray);
        this.buffer.limit(BUFFER_CAPACITY);
    }

    public TCPChannelServer(int serverPort, IPresenter presenter) throws IOException, AlreadyBoundException {
        this(serverPort);
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
    public IPresenter getPresenter() {
        return this.presenter;
    }

    @Override
    public void setPresenter(IPresenter presenter) {
        this.presenter = presenter;
    }

    private void accept(SelectionKey key) throws IOException {
        System.out.printf("🤝 Установлено соединение с клиентом\n");
        this.server = (ServerSocketChannel) key.channel();
        SocketChannel socket = this.server.accept();
        socket.configureBlocking(false);
        socket.register(key.selector(), SelectionKey.OP_READ);
    }

    private void read(SelectionKey key) throws IOException {
        System.out.printf("⬇️  Получение данных\n");
        SocketChannel socket = (SocketChannel) key.channel();
        this.buffer.clear();
        int data = socket.read(this.buffer);
        if (data != -1) {
            System.out.printf("✅ Получены данные: %s\n", Arrays.toString(this.dataArray).substring(0, 50));
            socket.configureBlocking(false);
            socket.register(key.selector(), SelectionKey.OP_WRITE);
        } else {
            System.out.printf("❌ Данные не получены\n");
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
            System.out.printf("🔃 Данные обработаны\n");
        } catch (IOException | ClassNotFoundException | UnsupportedObjectReceivedException | InputEndedException e) {
            System.out.printf("❌ Данные не обработаны: %s\n", e.getLocalizedMessage());
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
        System.out.printf("🔼 Отправка данных: %s\n", Arrays.toString(this.dataArray).substring(0, 50));
        SocketChannel socket = (SocketChannel) key.channel();
        this.buffer.flip();
        int data = socket.write(this.buffer);
        if (data != -1) {
            System.out.printf("✅ Данные отправлены\n");
            socket.configureBlocking(false);
            socket.register(key.selector(), SelectionKey.OP_CONNECT);
        } else {
            System.out.printf("❌ Данные не отправлены\n");
        }
    }

}
