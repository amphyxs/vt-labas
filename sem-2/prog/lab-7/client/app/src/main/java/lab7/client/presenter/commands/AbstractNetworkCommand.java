package lab7.client.presenter.commands;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;

import lab7.client.presenter.Presenter;
import lab7.client.presenter.exceptions.InputEndedException;
import lab7.serializedobjects.SerializedCommand;
import lab7.serializedobjects.ServerMessage;
import lab7.serializedobjects.auth.User;
import lab7.serializedobjects.form.*;


public abstract class AbstractNetworkCommand implements Command {
    
    @Override
    public void execute(Presenter presenter) {
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
                sendObject(presenter.getClient().getOutputStream(), prepareForSerialization(presenter.getUser()));
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

    protected SerializedCommand prepareForSerialization(User executor) {
        return new SerializedCommand(getName(), getCurrentArgs(), executor);
    }

    protected void processAnswer(Object answer, Presenter presenter) throws IOException {
        try {
            if (answer instanceof List) {
                processList((List<?>) answer, presenter);
            } else if (answer instanceof Form) {
                processForm((Form<?>) answer, presenter);
            } else if (answer == null) {
                throw new IOException("Сервер отправил null");
            } else {
                throw new ClassCastException();
            }
        } catch (ClassCastException e) {
            throw new IOException(String.format("Сервер передал неподдерживаемый тип: %s", answer.getClass().getName()));
        }
    }

    protected void processList(List<?> list, Presenter presenter) throws IOException {
        if (list.isEmpty()) {
            return;
        } else {
            try {
                if (list.get(0) instanceof ServerMessage) {
                    processServerMessages((List<ServerMessage>) list, presenter);
                } else {
                    throw new ClassCastException();
                }
            } catch (ClassCastException e) {
                throw new IOException(String.format("Сервер передал List с неподдерживаемым типом: %s", list.get(0).getClass().getName()));
            }
        }
    }

    protected void processServerMessages(List<ServerMessage> messages, Presenter presenter) {
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

    protected void processForm(Form<?> form, Presenter presenter) throws IOException {
        try {
            if (form.getSimpleTypeName() == "SpaceMarine") {
                processSpaceMarineForm((SpaceMarineForm) form, presenter);
            } else {
                throw new ClassCastException();
            }
        } catch (ClassCastException e) {
            throw new IOException(String.format("Сервер передал форму с неподдерживаемым типом: %s", form.getSimpleTypeName()));
        }
    }

    protected void processSpaceMarineForm(SpaceMarineForm form, Presenter presenter) throws IOException {
        try {
            form = new SpaceMarineForm();
            form = (SpaceMarineForm) presenter.getView().readForm(form);
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
    }

}
