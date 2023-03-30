package lab6.server.Presenter;

import java.io.IOException;

public interface IServer {

    void start() throws IOException;

    IPresenter getPresenter();

    void setPresenter(IPresenter presenter);

}