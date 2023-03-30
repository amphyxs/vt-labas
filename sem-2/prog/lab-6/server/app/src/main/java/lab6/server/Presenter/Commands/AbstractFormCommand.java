package lab6.server.Presenter.Commands;

import lab6.serializedobjects.Form.IForm;


public abstract class AbstractFormCommand implements ICommand {
    
    protected IForm form;

    public IForm getForm() {
        return this.form;
    }

    public void setForm(IForm form) {
        this.form = form;
    }

}
