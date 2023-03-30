package lab6.server.Presenter.Commands;

import java.util.Collections;
import lab6.serializedobjects.ServerMessage;
import java.util.List;
import java.util.ArrayList;

import lab6.server.Presenter.IPresenter;
import lab6.server.Presenter.Exceptions.*;


/**
 * Команда для перетасовки всех объектов коллекции
 */
public class ShuffleCommand implements ICommand {
    
    @Override
    public List<ServerMessage> execute(IPresenter presenter) {
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
