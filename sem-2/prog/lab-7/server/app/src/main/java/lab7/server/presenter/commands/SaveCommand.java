package lab7.server.presenter.commands;

import lab7.serializedobjects.dataclasses.SpaceMarine;
import lab7.serializedobjects.exceptions.SaveFailedException;
import lab7.server.model.SaveInfo;
import lab7.server.presenter.Presenter;
import lab7.server.presenter.exceptions.*;
import lab7.serializedobjects.ServerMessage;
import lab7.serializedobjects.auth.User;
import lab7.serializedobjects.MessagesType;
import java.util.List;
import java.util.ArrayList;


/**
 * Команда сохранения коллекции
 */
public class SaveCommand implements Command {
    
    @Override
    public List<ServerMessage> execute(Presenter presenter, User executor) {
        List<ServerMessage> messages = new ArrayList<ServerMessage>();
        
        SpaceMarine[] arr = presenter.getCollection().toArray(new SpaceMarine[presenter.getCollection().size()]);
        try {
            SaveInfo info = presenter.getModel().saveData(arr, executor);
            if (info.getSaveLocation() != null) {
                String result = String.format("Данные были записаны в %s", info.getSaveLocation());
                presenter.getView().showResult(result);
                messages.add(new ServerMessage(MessagesType.RESULT, result));
            }
        } catch (SaveFailedException e) {
            presenter.getView().showError(e.getMessage());
            messages.add(new ServerMessage(MessagesType.ERROR, e.getMessage()));
        }

        return messages;
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescription() {
        return "сохранить коллекцию в файл";
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
