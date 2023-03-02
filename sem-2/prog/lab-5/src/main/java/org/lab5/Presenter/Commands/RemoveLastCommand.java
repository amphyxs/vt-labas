package org.lab5.Presenter.Commands;

import org.lab5.Presenter.IPresenter;
import org.lab5.Presenter.Exceptions.*;


/**
 * Команда удаления первого объекта коллекции
 */
public class RemoveLastCommand implements ICommand {

    @Override
    public void execute(IPresenter presenter) {
        if (presenter.getCollection().empty()) {
            presenter.getView().showError("Коллекция пуста");
        } else {
            presenter.getCollection().remove(presenter.getCollection().firstElement());
        }
    }

    @Override
    public String getName() {
        return "remove_last";
    }

    @Override
    public String getDescription() {
        return "удалить последний элемент из коллекции";
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
