package lab8.client.presenter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Client {
 
    OutputStream getOutputStream() throws IOException;

    InputStream getInputStream() throws IOException;

    void initSocket() throws IOException;

    boolean checkSocketInited();

}
