package lab7.server.presenter.commands;

import java.util.Collections;
import lab7.serializedobjects.ServerMessage;
import lab7.serializedobjects.auth.User;
import lab7.server.presenter.Presenter;
import lab7.server.presenter.exceptions.*;

import java.util.List;
import java.util.ArrayList;


/**
 * Команда для перетасовки всех объектов коллекции
 */
public class ShuffleCommand implements Command {
    
    @Override
    public List<ServerMessage> execute(Presenter presenter, User executor) {
        List<ServerMessage> messages = new ArrayList<ServerMessage>();
        
        Collections.shuffle(presenter.getCollection());

        return messages;
    }

    @Override
    public String getName() {
        return "shuffle";
    }

    @Override
    public String getDescription() {
        return "перемешать элементы коллекции в случайном порядке";
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
