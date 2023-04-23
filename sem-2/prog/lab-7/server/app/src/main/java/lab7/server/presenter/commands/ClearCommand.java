package lab7.server.presenter.commands;

import java.util.ArrayList;
import java.util.List;

import lab7.serializedobjects.MessagesType;
import lab7.serializedobjects.ServerMessage;
import lab7.serializedobjects.auth.User;
import lab7.serializedobjects.dataclasses.*;
import lab7.serializedobjects.exceptions.SaveFailedException;
import lab7.server.model.SaveInfo;
import lab7.server.presenter.Presenter;
import lab7.server.presenter.exceptions.*;


/**
 * Команда очистки коллекции
 */
public class ClearCommand implements Command {
    
    @Override
    public List<ServerMessage> execute(Presenter presenter, User executor) {
        List<ServerMessage> messages = new ArrayList<ServerMessage>();
        
        try {
            SaveInfo info = presenter.getModel().saveData(new SpaceMarine[0], executor);
            for (int removedId : info.getRemovedIds()) {
                presenter.getCollection().removeIf((sp) -> sp.getId() == removedId);   
            }
        } catch (SaveFailedException e) {
            presenter.getView().showError(e.getLocalizedMessage());
            messages.add(new ServerMessage(MessagesType.ERROR, e.getLocalizedMessage()));
        }

        return messages;
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "очистить коллекцию";
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
