package lab7.server.presenter.commands;

import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

import lab7.serializedobjects.MessagesType;
import lab7.serializedobjects.ServerMessage;
import lab7.serializedobjects.auth.User;
import lab7.server.presenter.Presenter;
import lab7.server.presenter.exceptions.*;


/**
 * Команда вывода объектов коллекции, отфильтрованных по полю name
 */
public class FilterStartsWithNameCommand implements Command { 

    private String name;

    @Override
    public List<ServerMessage> execute(Presenter presenter, User executor) {
        List<ServerMessage> messages = new ArrayList<ServerMessage>();
        
        String result = presenter.getCollection().stream()
                                    .filter(e -> e.getName().startsWith(this.name))
                                    .map(Object::toString)
                                    .collect(Collectors.joining("\n===\n"));

        presenter.getView().showResult(result);   
        messages.add(new ServerMessage(MessagesType.RESULT, result));
        
        return messages;
    }

    @Override
    public String getName() {
        return "filter_starts_with_name";
    }

    @Override
    public String getDescription() {
        return "вывести элементы, значение поля name которых начинается с заданной подстроки";
    }

    @Override
    public String[] getArgsNames() {
        return new String[] {"name"};
    }

    @Override
    public void setArg(String argName, String valueString) throws BadCommandArgException {
        switch (argName) {
            case "name":
                this.name = valueString;
                break;
        }
    }

    @Override
    public Object getArg(String argName) throws CommandArgNotFound {
        switch (argName) {
            case "name":
                return this.name;
            default:
                throw new CommandArgNotFound(getName(), argName);
        }
    }

}