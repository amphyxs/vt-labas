package lab7.server.presenter.net;

import java.io.IOException;
import java.nio.channels.AlreadyBoundException;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.ServerSocketChannel;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.net.InetAddress;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;

import lab7.server.presenter.Presenter;


public class MultithreadTCPServer implements Server {

    private final int THREAD_LIMIT = 10;
    private ExecutorService service;
    private Logger logger;
    private ServerSocketChannel server;
    private Presenter presenter;
    
    public MultithreadTCPServer(int serverPort, Logger logger) throws IOException, AlreadyBoundException {
        this.service = Executors.newFixedThreadPool(THREAD_LIMIT);

        this.logger = logger;

        this.server = ServerSocketChannel.open();
        SocketAddress addr = new InetSocketAddress(InetAddress.getLocalHost(), serverPort);
        this.server.bind(addr);   

        this.logger.info("Сервер запущен по адресу: {}", addr);
    }

    public MultithreadTCPServer(int serverPort, Presenter presenter, Logger logger) throws IOException, AlreadyBoundException {
        this(serverPort, logger);
        setPresenter(presenter);
    }

    @Override
    public void start() throws IOException {
        this.service.submit(() -> {
            readLocalInput();
        });
        this.service.submit(() -> {
            try {
                while(true) {
                    SocketChannel acceptedSocket = accept();
                    ClientProcessor processor = new ClientProcessor(acceptedSocket, logger, presenter);
                    processor.start();
                }
            } catch (ClosedByInterruptException e) {
                logger.debug("Поток получения запросов прерван");
            } catch (IOException e) {
                logger.error("Ошибка в потоке обработки запросов: {}", e.getLocalizedMessage(), e);
            }
        });
    }

    @Override
    public void stop() throws IOException {
        if (!this.service.isShutdown())
            this.service.shutdownNow();
    }
    
    @Override
    public Presenter getPresenter() {
        return this.presenter;
    }
    
    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Logger getLogger() {
        return this.logger;
    }

    private SocketChannel accept() throws IOException {
        SocketChannel socket = this.server.accept();
        this.logger.info("🤝 Установлено соединение с клиентом");
        return socket;
    }
    
    private void readLocalInput() {
        boolean isReading = true;
        while (isReading) {
            getPresenter().start();
            if (getPresenter().getView() == null) {
                isReading = false;
                continue;
            }
        }
    }

}
