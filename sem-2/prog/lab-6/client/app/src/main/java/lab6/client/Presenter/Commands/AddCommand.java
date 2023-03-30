package lab6.client.Presenter.Commands;

import lab6.client.Presenter.Exceptions.CommandArgNotFound;

/**
 * Команда добавления объекта в коллекцию
 */
public class AddCommand extends AbstractNetworkCommand {
    
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
