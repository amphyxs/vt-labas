package lab7.server.presenter.commands;

import lab7.serializedobjects.ServerMessage;
import lab7.serializedobjects.auth.User;
import lab7.serializedobjects.exceptions.SaveFailedException;
import lab7.server.model.NotEnoughRightsExceptions;
import lab7.server.model.ObjectNotFoundException;
import lab7.server.presenter.Presenter;
import lab7.server.presenter.exceptions.*;
import lab7.serializedobjects.MessagesType;
import java.util.List;
import java.util.ArrayList;


/**
 * Команда удаления первого объекта коллекции
 */
public class RemoveFirstCommand implements Command {

    @Override
    public List<ServerMessage> execute(Presenter presenter, User executor) {
        List<ServerMessage> messages = new ArrayList<ServerMessage>();
        
        if (presenter.getCollection().empty()) {
            presenter.getView().showError("Коллекция пуста");
            messages.add(new ServerMessage(MessagesType.ERROR, "Коллекция пуста"));
        } else {
            try {
                presenter.getModel().removeObject(presenter.getCollection().peek(), executor);
                presenter.getCollection().pop();
            } catch (SaveFailedException | ObjectNotFoundException | NotEnoughRightsExceptions e) {
                messages.add(new ServerMessage(MessagesType.ERROR, e.getLocalizedMessage()));
                presenter.getView().showError(e.getLocalizedMessage());
            }
        }

        return messages;
    }

    @Override
    public String getName() {
        return "remove_first";
    }

    @Override
    public String getDescription() {
        return "удалить первый элемент из коллекции";
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
