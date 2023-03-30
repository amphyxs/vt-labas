package lab6.client;

import lab6.client.Presenter.ClientCommandsPresenter;
import lab6.client.Presenter.IClient;
import lab6.client.Presenter.IPresenter;
import lab6.client.Presenter.TCPStreamClient;
import lab6.client.View.LocalStreamView;
import lab6.client.View.IView;


public class Main {

    private final static String SERVER_HOSTNAME = "127.0.0.1";
    private final static int SERVER_PORT = 9856;

    public static void main(String[] args) {

        IView view = new LocalStreamView();
        IClient client = new TCPStreamClient(SERVER_HOSTNAME, SERVER_PORT);

        IPresenter presenter = new ClientCommandsPresenter(view, client);

        presenter.start();
        
    }    
}
