package lab6.client.Presenter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IClient {
 
    OutputStream getOutputStream() throws IOException;

    InputStream getInputStream() throws IOException;

    void initSocket() throws IOException;

    boolean checkSocketInited();

}
