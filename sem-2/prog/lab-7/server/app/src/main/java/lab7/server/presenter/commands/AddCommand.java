package lab7.server.presenter.commands;

import java.util.ArrayList;
import java.util.List;
import lab7.serializedobjects.dataclasses.*;
import lab7.serializedobjects.exceptions.*;
import lab7.serializedobjects.ServerMessage;
import lab7.serializedobjects.auth.User;
import lab7.serializedobjects.MessagesType;
import lab7.serializedobjects.form.*;

import lab7.server.model.SaveInfo;
import lab7.server.presenter.Presenter;
import lab7.server.presenter.exceptions.CommandArgNotFound;
import lab7.server.presenter.exceptions.InputEndedException;
import lab7.server.view.View;


/**
 * Команда добавления объекта в коллекцию
 */
public class AddCommand extends AbstractFormCommand<SpaceMarine> {
    
    private SpaceMarineForm form = null;

    @Override
    public List<ServerMessage> execute(Presenter presenter, User executor) {
        List<ServerMessage> messages = new ArrayList<ServerMessage>();
        
        View view = presenter.getView();
        SpaceMarine marine = null;

        try {
            int id = presenter.getCollection().generateUniqueId();
            if (this.form != null) {  // TODO: refactor
                if (getForm() == null)
                    throw new InputEndedException();
                    
                getForm().setId(id);
                marine = getForm().createObject();
            } else {
                SpaceMarineForm form = new SpaceMarineForm(id);
                marine = presenter.getView().readForm(form).createObject();
            }
            
            marine.setOwner(executor);
            SaveInfo info = presenter.getModel().appendObject(marine, executor);
            if (info.getSavedIds().length != 1)
                throw new SaveFailedException("Объект не был сохранён");
            
            marine.setId(info.getSavedIds()[0]);
            presenter.getCollection().push(marine);
            
        } catch (ValidationFailedException | SaveFailedException e) {
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
    public void setForm(Form<SpaceMarine> form) throws ValidationFailedException {
        this.form = new SpaceMarineForm((SpaceMarineForm) form);
    };

}
