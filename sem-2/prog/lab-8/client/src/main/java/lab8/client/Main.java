package lab8.client;

import javafx.application.Application;
import javafx.stage.Stage;
import lab8.client.presenter.Client;
import lab8.client.presenter.ClientCommandsPresenter;
import lab8.client.presenter.Presenter;
import lab8.client.presenter.TCPStreamClient;
import lab8.client.view.View;
import lab8.client.view.fxapp.GuiView;


public class Main extends Application {
    public final static String SERVER_HOSTNAME = "127.0.0.1";
    public final static int SERVER_PORT = 9856;
    private View view;
    private Client client;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void init() throws Exception {
        this.view = new GuiView();
        this.client = new TCPStreamClient(Main.SERVER_HOSTNAME, Main.SERVER_PORT);
        super.init();
    }

    @Override
    public void start(Stage primaryStage) {
        Presenter presenter = new ClientCommandsPresenter(this.view, this.client, primaryStage);
        presenter.start();
    }
}
