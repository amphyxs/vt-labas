package lab6.server.Presenter.Commands;

import lab6.serializedobjects.DataClasses.SpaceMarine;
import lab6.serializedobjects.ServerMessage;
import lab6.serializedobjects.MessagesType;
import java.util.List;
import java.util.ArrayList;

import lab6.server.Presenter.IPresenter;
import lab6.server.Presenter.Exceptions.*;


/**
 * Команда удаления объекта коллекции по id
 */
public class RemoveByIdCommand implements ICommand {
    
    private int id;

    @Override
    public List<ServerMessage> execute(IPresenter presenter) {
        List<ServerMessage> messages = new ArrayList<ServerMessage>();
        
        boolean found = false;
        
        for (SpaceMarine element : presenter.getCollection()) {
            if (element.getId() == this.id) {
                presenter.getCollection().remove(element);
                found = true;
                break;
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
