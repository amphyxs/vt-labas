package lab6.server.Presenter.Commands;

import java.util.ArrayList;
import java.util.List;

import lab6.serializedobjects.MessagesType;
import lab6.serializedobjects.ServerMessage;
import lab6.serializedobjects.DataClasses.SpaceMarine;
import lab6.serializedobjects.Exceptions.*;

import lab6.server.Presenter.IPresenter;
import lab6.server.Presenter.Exceptions.*;


/**
 * Команда выхода из программы
 */
public class ExitCommand implements ICommand {
    
    @Override
    public List<ServerMessage> execute(IPresenter presenter) {
        List<ServerMessage> messages = new ArrayList<ServerMessage>();
        
        SpaceMarine[] arr = presenter.getCollection().toArray(new SpaceMarine[presenter.getCollection().size()]);
        String filepath;
        try {
            filepath = presenter.getModel().saveData(arr);
            String result = String.format("Данные были записаны в файл \"%s\"", filepath);
            presenter.getView().showResult(result);
            messages.add(new ServerMessage(MessagesType.RESULT, result));
        } catch (SaveFailedException e) {
            String errorMessage = e.getMessage();
            messages.add(new ServerMessage(MessagesType.ERROR, errorMessage));
            presenter.getView().showError(errorMessage);
        }

        while (presenter.getView() != null)
            presenter.stop();
            
        return messages;
    }

    @Override
    public String getDescription() {
        return "завершить программу (без сохранения в файл)";
    }

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String[] getArgsNames() {
        return null;
    }
    
    @Override
    public void setArg(String argName, String valueString) {
        return;
    }

    @Override
    public Object getArg(String argName) throws CommandArgNotFound {
        throw new CommandArgNotFound(getName(), argName);
    }

}
