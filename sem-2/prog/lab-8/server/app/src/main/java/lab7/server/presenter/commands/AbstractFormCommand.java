package lab7.server.presenter.commands;

import lab7.serializedobjects.form.Form;
import lab7.serializedobjects.auth.User;
import lab7.serializedobjects.exceptions.*;


public abstract class AbstractFormCommand<T> implements Command {
    
    protected Form<T> form;
    protected User executor;

    public Form<T> getForm() {
        return this.form;
    }

    public void setForm(Form<T> form) throws ValidationFailedException {
        this.form = form;
    }

    public User getExecutor() {
        return this.executor;
    }

    public void setExecutor(User executor) {
        this.executor = executor;
    }

}
