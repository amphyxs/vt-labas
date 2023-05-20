package lab7.server.presenter.commands;

import lab7.serializedobjects.MessagesType;
import lab7.serializedobjects.ServerMessage;
import lab7.serializedobjects.auth.User;
import lab7.serializedobjects.exceptions.SaveFailedException;
import lab7.serializedobjects.exceptions.ValidationFailedException;
import lab7.serializedobjects.form.Form;
import lab7.serializedobjects.form.UserForm;
import java.util.List;
import java.util.ArrayList;

import lab7.server.presenter.Presenter;
import lab7.server.presenter.exceptions.*;


public class RegisterCommand extends AbstractFormCommand<User> {

    private UserForm form = new UserForm();

    @Override
    public List<ServerMessage> execute(Presenter presenter, User executor) {
        List<ServerMessage> messages = new ArrayList<ServerMessage>();
        
        try {
            User userToRegister = getForm().createObject();
            presenter.getModel().createNewUser(userToRegister);
            messages.add(new ServerMessage(MessagesType.RESULT, String.format("Пользователь \"%s\" зарегистрирован", userToRegister.getName())));
        } catch (ValidationFailedException | SaveFailedException e) {
            presenter.getView().showError(e.getLocalizedMessage());
            messages.add(new ServerMessage(MessagesType.ERROR, e.getLocalizedMessage()));
        }
        
        return messages;
    }
    
    @Override
    public String getName() {
        return "register";
    }

    @Override
    public Object getArg(String argName) throws CommandArgNotFound {
        return null;
    }

    @Override
    public void setArg(String argName, String valueString) throws BadCommandArgException {
        return;
    }

    @Override
    public String[] getArgsNames() {
        return null;
    }

    @Override
    public String getDescription() {
        return "осуществить регистрацию пользователя";
    }

    @Override
    public UserForm getForm() {
        return this.form;
    }

    @Override
    public void setForm(Form<User> form) throws ValidationFailedException {
        this.form = (UserForm) form;
    }
}
