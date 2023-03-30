package lab6.server;

import java.io.IOException;
import java.nio.channels.AlreadyBoundException;

import lab6.server.Model.IModel;
import lab6.server.Model.JsonModel;
import lab6.server.Presenter.IPresenter;
import lab6.server.Presenter.IServer;
import lab6.server.Presenter.ServerCommandsPresenter;
import lab6.server.Presenter.TCPChannelServer;
import lab6.server.View.IView;
import lab6.server.View.LocalStreamView;


public class Main {

    private final static int SERVER_PORT = 9856;

    public static void main(String[] args) {
        IServer server;
        
        try {
            server = new TCPChannelServer(SERVER_PORT);
        } catch (IOException e) {
            System.out.printf("Не удалось открыть сервер по причине: %s\n", e.getLocalizedMessage());
            return;
        } catch (AlreadyBoundException e) {
            System.out.printf("Порт %d занят\n", SERVER_PORT);
            return;
        }        
        
        IView view = new LocalStreamView();
        IModel model = new JsonModel();
        IPresenter presenter = new ServerCommandsPresenter(view, model, server);
        
        try {
            server.start();
        } catch (IOException e) {
            System.out.printf("Ошибка сервера: %s", e.getLocalizedMessage());
            return;
        }

    }
}
