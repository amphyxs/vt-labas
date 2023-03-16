package org.lab5.Presenter.Commands;

import org.lab5.Model.DataClasses.*;
import org.lab5.Model.Exceptions.*;
import org.lab5.Presenter.IPresenter;
import org.lab5.Presenter.Exceptions.CommandArgNotFound;
import org.lab5.Presenter.Exceptions.InputEndedException;
import org.lab5.Presenter.Form.SpaceMarineForm;
import org.lab5.View.IView;


/**
 * Команда добавления объекта в коллекцию
 */
public class AddCommand implements ICommand {
    
    @Override
    public void execute(IPresenter presenter) {
        IView view = presenter.getView();
        SpaceMarine marine = null;

        try {
            int id = presenter.getCollection().generateUniqueId();
            SpaceMarineForm form = new SpaceMarineForm(id);
            marine = presenter.getView().readForm(form).createObject();
        } catch (ValidationFailedException e) {
            view.showError(e.getMessage());
        } catch (InputEndedException e) {
            return;
        }

        presenter.getCollection().push(marine);
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

}
