package lab7.server;

import java.io.IOException;
import java.nio.channels.AlreadyBoundException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.SQLException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import lab7.server.model.Model;
import lab7.server.model.BadConfigException;
import lab7.server.model.DatabaseCredentials;
import lab7.server.model.DatabaseModel;
import lab7.server.presenter.Presenter;
import lab7.server.presenter.ServerCommandsPresenter;
import lab7.server.presenter.net.MultithreadTCPServer;
import lab7.server.presenter.net.Server;
import lab7.server.presenter.net.TCPChannelServer;
import lab7.server.view.View;
import lab7.server.view.LocalStreamView;


public class Main {

    private final static int SERVER_PORT = 9856;
    private final static Path DB_CONFIG = FileSystems.getDefault().getPath("db.cfg");

    public static void main(String[] args) {
        
        Logger serverLogger = LoggerFactory.getLogger(TCPChannelServer.class);
        Server server;
        
        try {
            server = new MultithreadTCPServer(SERVER_PORT, serverLogger);
        } catch (IOException e) {
            serverLogger.error("Не удалось открыть сервер по причине: {}", e.getLocalizedMessage(), e);
            return;
        } catch (AlreadyBoundException e) {
            serverLogger.error("Порт {} занят", SERVER_PORT, e);
            return;
        }        
        
        try {
            View view = new LocalStreamView();
            Model model = new DatabaseModel(new DatabaseCredentials(DB_CONFIG));
            Presenter presenter = new ServerCommandsPresenter(view, model, server);
            
            server.start();  // TODO: It would be better to make presenter.start()
        } catch (IOException e) {
            serverLogger.error("Ошибка сервера: ", e.getLocalizedMessage(), e);
            return;
        } catch (SQLException | BadConfigException e) {
            serverLogger.error("Ошибка инициализации БД: ", e.getLocalizedMessage(), e);
            return;
        }

    }
}
