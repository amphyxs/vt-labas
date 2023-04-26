package lab7.client;

import lab7.client.presenter.ClientCommandsPresenter;
import lab7.client.presenter.Client;
import lab7.client.presenter.Presenter;
import lab7.client.presenter.TCPStreamClient;
import lab7.client.view.View;
import lab7.client.view.LocalStreamView;


public class Main {

    private final static String SERVER_HOSTNAME = "127.0.0.1";
    private final static int SERVER_PORT = 9856;

    public static void main(String[] args) {

        View view = new LocalStreamView();
        Client client = new TCPStreamClient(SERVER_HOSTNAME, SERVER_PORT);
        Presenter presenter = new ClientCommandsPresenter(view, client);
            
        presenter.start();

    }    
}
