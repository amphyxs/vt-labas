package lab7.server.testconnection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AlreadyBoundException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.Arrays;


public class TestServer {

    private final int BUFFER_CAPACITY = 256;
    private ServerSocketChannel server;
    private ByteBuffer buffer;
    private byte[] dataArray;

    public TestServer(int serverPort) throws IOException, AlreadyBoundException {
        this.server = ServerSocketChannel.open();
        SocketAddress addr = new InetSocketAddress(InetAddress.getLocalHost(), serverPort);
        System.out.printf("Адрес сервера: %s\n", addr);
        this.server.bind(addr);
        
        dataArray = new byte[BUFFER_CAPACITY];
        this.buffer = ByteBuffer.wrap(dataArray);
    }

    public void start() throws IOException {
        Selector selector = Selector.open();
        this.server.configureBlocking(false);
        this.server.register(selector, SelectionKey.OP_ACCEPT);
        boolean isWorking = true;
        while (isWorking) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
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
            System.out.printf("✅ Получены данные: %s\n", Arrays.toString(this.buffer.array()));
            socket.configureBlocking(false);
            socket.register(key.selector(), SelectionKey.OP_WRITE);
        } else {
            System.out.printf("❌ Данные не получены\n");
        }
    }
    
    private void processData() {
        byte result = 0;
        for (byte x : this.buffer.array())
            result += x;
        
        for (int i = 0; i < this.dataArray.length; i++) {
            if (i == 0)
                this.dataArray[i] = result;
            else
                this.dataArray[i] = 0;
        }

        System.out.printf("🔃 Данные обработаны: %s\n", Arrays.toString(this.dataArray));
    }
    
    private void write(SelectionKey key) throws IOException {
        System.out.printf("🔼 Отправка данных\n");
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
