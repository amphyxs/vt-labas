package org.lab5.Presenter.Commands;

import org.lab5.Model.DataClasses.SpaceMarine;
import org.lab5.Model.Exceptions.SaveFailedException;
import org.lab5.Presenter.IPresenter;
import org.lab5.Presenter.Exceptions.*;


public class SaveCommand implements ICommand {
    
    @Override
    public void execute(IPresenter presenter) {
        SpaceMarine[] arr = presenter.getCollection().toArray(new SpaceMarine[presenter.getCollection().size()]);
        String filepath;
        try {
            filepath = presenter.getModel().saveData(arr);
        } catch (SaveFailedException e) {
            presenter.getView().showError(e.getMessage());
            return;
        }

        presenter.getView().showResult(String.format("Данные были записаны в файл \"%s\"", filepath));
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescription() {
        return "сохранить коллекцию в файл";
    }

    @Override
    public String[] getArgsNames() {
        return null;
    }

    @Override
    public void setArg(String argName, String valueString) throws BadCommandArgException {
        return;
    }

    @Override
    public Object getArg(String argName) throws CommandArgNotFound {
        throw new CommandArgNotFound(getName(), argName);
    }

}
