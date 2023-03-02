package org.lab5.Presenter.Commands;

import java.util.Iterator;

import org.lab5.Model.DataClasses.SpaceMarine;
import org.lab5.Presenter.IPresenter;
import org.lab5.Presenter.Exceptions.*;


/**
 * Команда удаления всех персонажей с заданным значением поля health
 */
public class RemoveAllByHealth implements ICommand {
    
    private Long health;
    
    @Override
    public void execute(IPresenter presenter) {
        Iterator<SpaceMarine> it = presenter.getCollection().iterator();
        while (it.hasNext()) {
            SpaceMarine element = it.next();
            if (element.getHealth() == this.health)
                it.remove();
        }
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
