package lab7.server.presenter.commands;

import lab7.serializedobjects.dataclasses.SpaceMarine;
import lab7.serializedobjects.exceptions.SaveFailedException;
import lab7.server.model.NotEnoughRightsExceptions;
import lab7.server.model.ObjectNotFoundException;
import lab7.server.presenter.Presenter;
import lab7.server.presenter.exceptions.*;
import lab7.serializedobjects.ServerMessage;
import lab7.serializedobjects.auth.User;
import lab7.serializedobjects.MessagesType;
import java.util.List;
import java.util.ArrayList;


/**
 * Команда удаления объекта коллекции по id
 */
public class RemoveByIdCommand implements Command {
    
    private int id;

    @Override
    public List<ServerMessage> execute(Presenter presenter, User executor) {
        List<ServerMessage> messages = new ArrayList<ServerMessage>();
        
        boolean found = false;
        
        for (SpaceMarine element : presenter.getCollection()) {
            if (element.getId() == this.id) {
                try {
                    presenter.getModel().removeObject(element, executor);
                    presenter.getCollection().remove(element);
                    found = true;
                    break;
                } catch (SaveFailedException | ObjectNotFoundException e) {
                    messages.add(new ServerMessage(MessagesType.ERROR, e.getLocalizedMessage()));
                    presenter.getView().showError(e.getLocalizedMessage());
                    break;
                } catch (NotEnoughRightsExceptions e) {
                    messages.add(new ServerMessage(MessagesType.ERROR, e.getLocalizedMessage()));
                    presenter.getView().showError(e.getLocalizedMessage());
                    return messages;
                }
            }
        }

        if (!found) {
            String errorMessage = String.format("Не удалось найти объект с id = %d", this.id);
            presenter.getView().showError(errorMessage);
            messages.add(new ServerMessage(MessagesType.ERROR, errorMessage));
        }

        return messages;
    }

    @Override
    public String getName() {
        return "remove_by_id";
    }

    @Override
    public String getDescription() {
        return "удалить элемент из коллекции по его id";
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
