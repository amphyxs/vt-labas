package lab7.client.presenter.commands;

import java.io.IOException;
import java.util.List;
import lab7.serializedobjects.MessagesType;
import lab7.serializedobjects.ServerMessage;
import lab7.serializedobjects.auth.User;
import lab7.serializedobjects.exceptions.ValidationFailedException;
import lab7.serializedobjects.form.Form;
import lab7.serializedobjects.form.UserForm;

import lab7.client.presenter.Presenter;
import lab7.client.presenter.exceptions.InputEndedException;
import lab7.client.presenter.exceptions.UserLoginFailedException;


public abstract class AbstractAuthCommand extends AbstractNetworkCommand {
    
    private UserForm userForm = null;
    private ServerMessage serverMessage = null;
    
    @Override
    protected void processForm(Form<?> form, Presenter presenter) throws IOException {
        try {
            this.userForm = (UserForm) presenter.getView().readForm(new UserForm());
        } catch (InputEndedException e) {
            this.userForm = null;
        }

        if (presenter.connectToHost()) {
            sendObject(presenter.getClient().getOutputStream(), this.userForm);
            Object anotherAnswer = getAnswer(presenter.getClient().getInputStream());
            processAnswer(anotherAnswer, presenter);
        } else {
            throw new IOException("Невозможно отправить введённые данные, так как подключение к серверу потеряно");
        }    
    }

    @Override
    protected void processServerMessages(List<ServerMessage> messages, Presenter presenter) {
        this.serverMessage = messages.get(0);
    }
    
    public User createUser() throws ValidationFailedException, UserLoginFailedException, InputEndedException {
        if (userForm == null)
            throw new InputEndedException();
        
        if (!checkUserAuthentication()) {
            if (this.serverMessage != null)
                throw new UserLoginFailedException(this.serverMessage.getText());    
            else
                throw new UserLoginFailedException("Не удалось получить ответ от сервера");
        }

        return userForm.createObject();
    }

    private boolean checkUserAuthentication() {
        if (this.serverMessage == null)
            return false;
        
        return (this.serverMessage.getType() != MessagesType.ERROR);
    }

}
