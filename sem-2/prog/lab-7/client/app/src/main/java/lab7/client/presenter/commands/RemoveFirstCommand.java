package lab7.client.presenter.commands;

import lab7.client.presenter.Presenter;
import lab7.client.presenter.exceptions.*;


/**
 * Команда удаления первого объекта коллекции
 */
public class RemoveFirstCommand extends AbstractNetworkCommand {

    @Override
    public String getName() {
        return "remove_first";
    }

    @Override
    public String getDescription() {
        return "удалить первый элемент из коллекции";
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
