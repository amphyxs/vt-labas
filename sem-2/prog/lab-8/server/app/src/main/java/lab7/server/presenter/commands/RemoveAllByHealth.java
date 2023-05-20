package lab7.server.presenter.commands;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.ArrayList;
import lab7.serializedobjects.dataclasses.SpaceMarine;
import lab7.serializedobjects.exceptions.SaveFailedException;
import lab7.server.model.NotEnoughRightsExceptions;
import lab7.server.model.ObjectNotFoundException;
import lab7.server.presenter.Presenter;
import lab7.server.presenter.exceptions.*;
import lab7.serializedobjects.MessagesType;
import lab7.serializedobjects.ServerMessage;
import lab7.serializedobjects.auth.User;


/**
 * Команда удаления всех персонажей с заданным значением поля health
 */
public class RemoveAllByHealth implements Command {
    
    private Long health;
    
    @Override
    public List<ServerMessage> execute(Presenter presenter, User executor) {
        List<ServerMessage> messages = new ArrayList<ServerMessage>();
        
        Iterator<SpaceMarine> it = presenter.getCollection().iterator();
        List<Integer> failedToDeleteIds = new ArrayList<Integer>();
        while (it.hasNext()) {
            SpaceMarine element = it.next();
            if (element.getHealth().equals(this.health)) {
                try {
                    presenter.getModel().removeObject(element, executor);
                    it.remove();
                } catch (SaveFailedException | ObjectNotFoundException e) {
                    presenter.getView().showError(e.getLocalizedMessage());
                    messages.add(new ServerMessage(MessagesType.ERROR, e.getLocalizedMessage()));
                    break;
                } catch (NotEnoughRightsExceptions e) {
                    failedToDeleteIds.add(element.getId());
                    continue;
                }
            } else {
                presenter.getView().showInfo(String.format("Check health:\n\telement: %d, this: %d", element.getHealth(), this.health));
            }
        }

        if (!failedToDeleteIds.isEmpty()) {
            String idsString = failedToDeleteIds.stream()
                                                    .map(Object::toString)
                                                    .collect(Collectors.joining(", "));
            messages.add(new ServerMessage(MessagesType.ERROR, String.format("Не удалось удалить объекты с id: %s", idsString)));
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
