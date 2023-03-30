package lab6.client.Presenter.Commands;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;
import lab6.serializedobjects.SerializedCommand;
import lab6.serializedobjects.ServerMessage;
import lab6.serializedobjects.Form.*;

import lab6.client.Presenter.IPresenter;
import lab6.client.Presenter.Exceptions.InputEndedException;


public abstract class AbstractNetworkCommand implements ICommand {
    
    @Override
    public void execute(IPresenter presenter) {
        /*
         * 1) Команда без формы
         *      Посылаем ICommand на сервер
         *      Получаем результат
         *      Выводим результат
         * 2) Команда с формой
         *      Посылаем ICommand на сервер
         *      Принимаем форму
         *      Открываем ввод формы
         *      Делаем валидацию полей:
         *          * Либо с помощью привязанного метода (если это возможно)
         *          * Либо каждый раз отправляя значение на сервер и ожидая ответ
         *          * Либо сделать классы валидации (мин. значение, не null и т.п.) и пересылать их внутри поля
         *      Отправляем форму на сервер
         *      Получаем результат
         *      Выводим результат
         */
        if (presenter.connectToHost()) {
            try {
                sendObject(presenter.getClient().getOutputStream(), prepareForSerialization());
                Object answer = getAnswer(presenter.getClient().getInputStream());
                processAnswer(answer, presenter);
            } catch (IOException e) {
                if (presenter.getView() != null)
                    presenter.getView().showError(e.getLocalizedMessage());
            }
        }
    }

    protected void sendObject(OutputStream sendStream, Object obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        
        try (ObjectOutputStream os = new ObjectOutputStream(bos)) {
            os.writeObject(obj);
            os.flush();    

            sendStream.write(bos.toByteArray());
            sendStream.flush();
        }
    }

    protected Object getAnswer(InputStream answerStream) throws IOException {
        Object obj;
        try {
            try(ObjectInputStream os = new ObjectInputStream(answerStream)) {
                obj = os.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new IOException(e.getLocalizedMessage());
        }
        return obj;
    }

    protected SerializedCommand prepareForSerialization() {
        return new SerializedCommand(getName(), getCurrentArgs());
    }

    protected void processAnswer(Object answer, IPresenter presenter) throws IOException {
        if (answer instanceof List) {
            List rawList = (List) answer;
            if (rawList.isEmpty()) {
                return;
            } else {
                if (rawList.get(0) instanceof ServerMessage) {
                    List<ServerMessage> messages = (List<ServerMessage>) rawList;
                    for (ServerMessage msg : messages) {
                        switch (msg.getType()) {
                            case ERROR: 
                                presenter.getView().showError(msg.getText());
                                break;
                            case INFO:
                                presenter.getView().showInfo(msg.getText());
                                break;
                            case RESULT:
                                presenter.getView().showResult(msg.getText());
                                break;
                            default:
                                presenter.getView().showInfo(msg.getText());
                                break;
                        }  
                    } 
                }
            }
        } else if (answer instanceof IForm) {
            IForm form = (IForm) answer;
            try {
                form = presenter.getView().readForm(form);
            } catch (InputEndedException e) {
                form = null;
            }
            if (presenter.connectToHost()) {
                sendObject(presenter.getClient().getOutputStream(), form);
                Object anotherAnswer = getAnswer(presenter.getClient().getInputStream());
                processAnswer(anotherAnswer, presenter);
            } else {
                throw new IOException("Невозможно отправить введённые данные, так как подключение к серверу потеряно");
            }
        } else if (answer == null) {
            throw new IOException("Сервер отправил null");
        } else {
            throw new IOException(String.format("Сервер передал неподдерживаемый тип: %s", answer.getClass().getName()));
        }
    }

}
