package lab7.server.presenter.commands;

import lab7.serializedobjects.ServerMessage;
import lab7.serializedobjects.auth.User;
import lab7.serializedobjects.dataclasses.SpaceMarine;
import lab7.server.presenter.Presenter;
import lab7.server.presenter.exceptions.*;
import lab7.serializedobjects.MessagesType;
import java.util.List;
import java.util.ArrayList;


public class FetchCommand implements Command {
    
    @Override
    public List<ServerMessage> execute(Presenter presenter, User executor) {
        List<ServerMessage> messages = new ArrayList<ServerMessage>();
        
        SpaceMarine[] dataArray = new SpaceMarine[presenter.getCollection().size()];
        messages.add(new ServerMessage(MessagesType.RESULT, presenter.getCollection().toArray(dataArray)));

        return messages;
    }

    @Override
    public String getName() {
        return "fetch";
    }

    @Override
    public String getDescription() {
        return "получить коллекцию объектов";
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
