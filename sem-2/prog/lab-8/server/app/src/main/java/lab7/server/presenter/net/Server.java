package lab7.server.presenter.net;

import java.io.IOException;
import org.slf4j.Logger;

import lab7.server.presenter.Presenter;


public interface Server {

    void start() throws IOException;

    void stop() throws IOException;

    Presenter getPresenter();

    void setPresenter(Presenter presenter);

    Logger getLogger();

}