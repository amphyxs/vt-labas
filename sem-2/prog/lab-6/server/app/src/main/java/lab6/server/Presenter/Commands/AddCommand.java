package lab6.server.Presenter.Commands;

import java.util.ArrayList;
import java.util.List;
import lab6.serializedobjects.DataClasses.*;
import lab6.serializedobjects.Exceptions.*;
import lab6.serializedobjects.ServerMessage;
import lab6.serializedobjects.MessagesType;
import lab6.serializedobjects.Form.IForm;
import lab6.serializedobjects.Form.SpaceMarineForm;

import lab6.server.Presenter.IPresenter;
import lab6.server.Presenter.Exceptions.CommandArgNotFound;
import lab6.server.Presenter.Exceptions.InputEndedException;
import lab6.server.View.IView;


/**
 * Команда добавления объекта в коллекцию
 */
public class AddCommand extends AbstractFormCommand {
    
    private SpaceMarineForm form = null;

    @Override
    public List<ServerMessage> execute(IPresenter presenter) {
        List<ServerMessage> messages = new ArrayList<ServerMessage>();
        
        IView view = presenter.getView();
        SpaceMarine marine = null;

        try {
            int id = presenter.getCollection().generateUniqueId();
            if (this.form != null) {
                if (getForm() == null)
                    throw new InputEndedException();
                    
                getForm().setId(id);
                marine = getForm().createObject();
                presenter.getCollection().push(marine);
            } else {
                SpaceMarineForm form = new SpaceMarineForm(id);
                marine = presenter.getView().readForm(form).createObject();
                presenter.getCollection().push(marine);
            }
        } catch (ValidationFailedException e) {
            view.showError(e.getLocalizedMessage());
            messages.add(new ServerMessage(MessagesType.ERROR, e.getLocalizedMessage()));
        } catch (InputEndedException e) {
            return messages;
        }


        return messages;
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию";
    }

    @Override
    public String[] getArgsNames() {
        return null;
    }

    @Override
    public void setArg(String argName, String valueString) {
        return;
    }

    @Override
    public Object getArg(String argName) throws CommandArgNotFound {
        throw new CommandArgNotFound(getName(), argName);
    }

    @Override
    public SpaceMarineForm getForm() {
        if (this.form == null)
            return new SpaceMarineForm();
        else
            return this.form;
    }

    @Override
    public void setForm(IForm form) {
        this.form = (SpaceMarineForm) form;
    };

}
