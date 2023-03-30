package lab6.client.Presenter.Commands;

import java.util.Collections;

import lab6.client.Presenter.IPresenter;
import lab6.client.Presenter.Exceptions.*;


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
