package lab6.client.Presenter.Commands;

import lab6.client.Presenter.Exceptions.*;


/**
 * Команда удаления всех персонажей с заданным значением поля health
 */
public class RemoveAllByHealth extends AbstractNetworkCommand {
    
    private Long health;

    @Override
    public String getName() {
        return "remove_all_by_health";
    }

    @Override
    public String getDescription() {
        return "удалить из коллекции все элементы, значение поля health которого эквивалентно заданному";
    }

    @Override
    public String[] getArgsNames() {
        return new String[] {"health"};
    }

    @Override
    public void setArg(String argName, String valueString) throws BadCommandArgException {
        try {
            switch (argName) {
                case "health":
                    this.health = Long.valueOf(valueString);
                    break;
            }
        } catch (NumberFormatException e) {
            throw new BadCommandArgException(getName(), argName, Long.class.getSimpleName());
        }
    }

    @Override
    public Object getArg(String argName) throws CommandArgNotFound {
        switch (argName) {
            case "health":
                return this.health;
            default:
                throw new CommandArgNotFound(getName(), argName);
        }
    }

}
