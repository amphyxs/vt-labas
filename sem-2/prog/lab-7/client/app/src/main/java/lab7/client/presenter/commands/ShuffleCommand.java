package lab7.client.presenter.commands;

import java.util.Collections;

import lab7.client.presenter.Presenter;
import lab7.client.presenter.exceptions.*;


/**
 * Команда для перетасовки всех объектов коллекции
 */
public class ShuffleCommand extends AbstractNetworkCommand {
    
    @Override
    public String getName() {
        return "shuffle";
    }

    @Override
    public String getDescription() {
        return "перемешать элементы коллекции в случайном порядке";
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
