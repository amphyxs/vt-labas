package lab6.client.Presenter.Commands;

import lab6.client.Presenter.Exceptions.*;


/**
 * Команда вывода объектов коллекции, отфильтрованных по полю name
 */
public class FilterStartsWithNameCommand extends AbstractNetworkCommand { 

    private String name;

    @Override
    public String getName() {
        return "filter_starts_with_name";
    }

    @Override
    public String getDescription() {
        return "вывести элементы, значение поля name которых начинается с заданной подстроки";
    }

    @Override
    public String[] getArgsNames() {
        return new String[] {"name"};
    }

    @Override
    public void setArg(String argName, String valueString) throws BadCommandArgException {
        switch (argName) {
            case "name":
                this.name = valueString;
                break;
        }
    }

    @Override
    public Object getArg(String argName) throws CommandArgNotFound {
        switch (argName) {
            case "name":
                return this.name;
            default:
                throw new CommandArgNotFound(getName(), argName);
        }
    }

}