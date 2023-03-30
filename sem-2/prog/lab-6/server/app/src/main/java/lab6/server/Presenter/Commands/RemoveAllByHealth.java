package lab6.server.Presenter.Commands;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import lab6.serializedobjects.DataClasses.SpaceMarine;
import lab6.serializedobjects.ServerMessage;
import lab6.serializedobjects.MessagesType;

import lab6.server.Presenter.IPresenter;
import lab6.server.Presenter.Exceptions.*;


/**
 * Команда удаления всех персонажей с заданным значением поля health
 */
public class RemoveAllByHealth implements ICommand {
    
    private Long health;
    
    @Override
    public List<ServerMessage> execute(IPresenter presenter) {
        List<ServerMessage> messages = new ArrayList<ServerMessage>();
        
        Iterator<SpaceMarine> it = presenter.getCollection().iterator();
        while (it.hasNext()) {
            SpaceMarine element = it.next();
            if (element.getHealth() == this.health)
                it.remove();
        }

        return messages;
    }

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
