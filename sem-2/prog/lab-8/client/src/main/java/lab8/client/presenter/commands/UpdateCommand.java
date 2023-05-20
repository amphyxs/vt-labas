package lab8.client.presenter.commands;

import lab7.serializedobjects.dataclasses.SpaceMarine;
import lab8.client.presenter.exceptions.*;


/**
 * Команда для обновления объекта коллекции с заданным полем id
 */
public class UpdateCommand extends AbstractNetworkCommand {

    private int id;

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return "обновить значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public String[] getArgsNames() {
        return new String[] {"id"};
    }

    @Override
    public void setArg(String argName, String valueString) throws BadCommandArgException {
        try {
            switch (argName) {
                case "id":
                    this.id = Integer.parseInt(valueString);
                    break;
            }
        } catch (NumberFormatException e) {
            throw new BadCommandArgException(getName(), argName, Integer.class.getSimpleName());
        }
    }

    @Override
    public Object getArg(String argName) throws CommandArgNotFound {
        switch (argName) {
            case "id":
                return this.id;
            default:
                throw new CommandArgNotFound(getName(), argName);
        }
    }

}
