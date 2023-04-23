package lab7.client.presenter.commands;

import lab7.client.presenter.Presenter;
import lab7.client.presenter.exceptions.*;


/**
 * Команда удаления первого объекта коллекции
 */
public class RemoveLastCommand extends AbstractNetworkCommand {

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
