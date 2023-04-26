package lab7.server.presenter.commands;

import java.util.ArrayList;
import java.util.List;
import lab7.serializedobjects.ServerMessage;
import lab7.serializedobjects.auth.User;
import lab7.serializedobjects.MessagesType;
import lab7.serializedobjects.dataclasses.*;
import lab7.serializedobjects.exceptions.*;

import lab7.server.model.SaveInfo;
import lab7.server.presenter.Presenter;
import lab7.server.presenter.exceptions.*;


/**
 * Команда выхода из программы
 */
public class ExitCommand implements Command {
    
    @Override
    public List<ServerMessage> execute(Presenter presenter, User executor) {
        List<ServerMessage> messages = new ArrayList<ServerMessage>();
        
        SpaceMarine[] arr = presenter.getCollection().toArray(new SpaceMarine[presenter.getCollection().size()]);
        try {
            SaveInfo info = presenter.getModel().onExit(arr);
            if (info.getSaveLocation() != null) {  // TODO: use SaveCommand
                String result = String.format("Данные были записаны в %s", info.getSaveLocation());
                presenter.getView().showResult(result);
                messages.add(new ServerMessage(MessagesType.RESULT, result));
            }
        } catch (SaveFailedException e) {
            String errorMessage = e.getMessage();
            messages.add(new ServerMessage(MessagesType.ERROR, errorMessage));
            presenter.getView().showError(errorMessage);
        }

        presenter.fullStop();
            
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
