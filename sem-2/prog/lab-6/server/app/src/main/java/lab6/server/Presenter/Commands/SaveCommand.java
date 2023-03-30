package lab6.server.Presenter.Commands;

import lab6.serializedobjects.DataClasses.SpaceMarine;
import lab6.serializedobjects.Exceptions.SaveFailedException;
import lab6.serializedobjects.ServerMessage;
import lab6.serializedobjects.MessagesType;
import java.util.List;
import java.util.ArrayList;

import lab6.server.Presenter.IPresenter;
import lab6.server.Presenter.Exceptions.*;


/**
 * Команда сохранения коллекции
 */
public class SaveCommand implements ICommand {
    
    @Override
    public List<ServerMessage> execute(IPresenter presenter) {
        List<ServerMessage> messages = new ArrayList<ServerMessage>();
        
        SpaceMarine[] arr = presenter.getCollection().toArray(new SpaceMarine[presenter.getCollection().size()]);
        String filepath;
        try {
            filepath = presenter.getModel().saveData(arr);
        } catch (SaveFailedException e) {
            presenter.getView().showError(e.getMessage());
            messages.add(new ServerMessage(MessagesType.ERROR, e.getMessage()));
            return messages;
        }
        
        String result = String.format("Данные были записаны в файл \"%s\"", filepath);
        presenter.getView().showResult(result);
        messages.add(new ServerMessage(MessagesType.RESULT, result));

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
