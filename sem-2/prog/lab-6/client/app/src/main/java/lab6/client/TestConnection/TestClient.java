package lab6.client.TestConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class TestClient {

    private InetAddress host;
    private int port;
    private Socket socket;
    
    public TestClient(String serverAddress) throws UnknownHostException, IOException {
        String hostName = serverAddress.split(":")[0];
        String hostPort = serverAddress.split(":")[1];
        this.host = Inet4Address.getByName(hostName);
        this.port = Integer.parseInt(hostPort);
        this.socket = new Socket(this.host, this.port);
    }

    public void sendBytes(byte[] data) throws IOException {
        OutputStream os = this.socket.getOutputStream();
        os.write(data);
    }

    public byte[] readBytes() throws IOException {
        InputStream is = this.socket.getInputStream();
        return is.readNBytes(10);
    }

}
