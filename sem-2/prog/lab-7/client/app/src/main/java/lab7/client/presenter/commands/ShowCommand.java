package lab7.client.presenter.commands;

import java.util.stream.Collectors;

import lab7.client.presenter.Presenter;
import lab7.client.presenter.exceptions.*;


/**
 * Команда вывода коллекции
 */
public class ShowCommand extends AbstractNetworkCommand {

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
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
