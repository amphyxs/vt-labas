package lab7.server.presenter.commands;

import lab7.serializedobjects.ServerMessage;
import lab7.serializedobjects.auth.User;
import lab7.serializedobjects.dataclasses.SpaceMarine;
import lab7.serializedobjects.MessagesType;
import lab7.serializedobjects.exceptions.*;
import java.util.List;
import java.util.ArrayList;

import lab7.server.presenter.Presenter;
import lab7.server.presenter.exceptions.*;
import lab7.server.model.NotEnoughRightsExceptions;
import lab7.server.model.ObjectNotFoundException;


/**
 * Команда удаления первого объекта коллекции
 */
public class RemoveLastCommand implements Command {

    @Override
    public List<ServerMessage> execute(Presenter presenter, User executor) {
        List<ServerMessage> messages = new ArrayList<ServerMessage>();
        
        if (presenter.getCollection().empty()) {
            presenter.getView().showError("Коллекция пуста");
            messages.add(new ServerMessage(MessagesType.ERROR, "Коллекция пуста"));
        } else {
            try {
                SpaceMarine last = presenter.getCollection().firstElement();
                presenter.getModel().removeObject(last, executor);
                presenter.getCollection().remove(last);
            } catch (SaveFailedException | ObjectNotFoundException | NotEnoughRightsExceptions e) {
                messages.add(new ServerMessage(MessagesType.ERROR, e.getLocalizedMessage()));
                presenter.getView().showError(e.getLocalizedMessage());
            }
        }

        return messages;
    }

    @Override
    public String getName() {
        return "remove_last";
    }

    @Override
    public String getDescription() {
        return "удалить последний элемент из коллекции";
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
