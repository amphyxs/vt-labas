package lab6.server.Presenter.Commands;

import java.util.stream.Collectors;
import lab6.serializedobjects.ServerMessage;
import lab6.serializedobjects.MessagesType;
import java.util.List;
import java.util.ArrayList;

import lab6.server.Presenter.IPresenter;
import lab6.server.Presenter.Exceptions.*;


/**
 * Команда вывода коллекции
 */
public class ShowCommand implements ICommand {
    
    @Override
    public List<ServerMessage> execute(IPresenter presenter) {
        List<ServerMessage> messages = new ArrayList<ServerMessage>();
        
        String result = presenter.getCollection().stream()
                                                    .map(Object::toString)
                                                    .collect(Collectors.joining("\n===\n"));
        presenter.getView().showResult(result);
        messages.add(new ServerMessage(MessagesType.RESULT, result));

        return messages;
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
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
