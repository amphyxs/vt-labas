package lab6.client.Presenter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;


public class TCPStreamClient implements IClient {
    
    private String hostName;
    private int port;
    private Socket socket;

    public TCPStreamClient(String hostName, int hostPort) {
        this.hostName = hostName;
        this.port = hostPort;
        this.socket = null;
    }
    
    @Override
    public InputStream getInputStream() throws IOException {
        if(!checkSocketInited())
            throw new IOException("Socket клиента не инициализирован");

        return this.socket.getInputStream();
    }
    
    @Override
    public OutputStream getOutputStream() throws IOException {
        if(!checkSocketInited())
            throw new IOException("Socket клиента не инициализирован");

        return this.socket.getOutputStream();
    }
    
    @Override
    public void initSocket() throws IOException {
        if (this.socket != null)
            this.socket.close();
        
        InetAddress host = Inet4Address.getByName(hostName);
        this.socket = new Socket(host, this.port);
    }

    @Override
    public boolean checkSocketInited() {
        return this.socket != null;
    }

}
