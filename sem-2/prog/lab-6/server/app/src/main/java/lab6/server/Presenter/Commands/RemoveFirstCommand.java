package lab6.server.Presenter.Commands;

import lab6.serializedobjects.ServerMessage;
import lab6.serializedobjects.MessagesType;
import java.util.List;
import java.util.ArrayList;

import lab6.server.Presenter.IPresenter;
import lab6.server.Presenter.Exceptions.*;


/**
 * Команда удаления первого объекта коллекции
 */
public class RemoveFirstCommand implements ICommand {

    @Override
    public List<ServerMessage> execute(IPresenter presenter) {
        List<ServerMessage> messages = new ArrayList<ServerMessage>();
        
        if (presenter.getCollection().empty()) {
            presenter.getView().showError("Коллекция пуста");
            messages.add(new ServerMessage(MessagesType.ERROR, "Коллекция пуста"));
        } else {
            presenter.getCollection().pop();
        }

        return messages;
    }

    @Override
    public String getName() {
        return "remove_first";
    }

    @Override
    public String getDescription() {
        return "удалить первый элемент из коллекции";
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
