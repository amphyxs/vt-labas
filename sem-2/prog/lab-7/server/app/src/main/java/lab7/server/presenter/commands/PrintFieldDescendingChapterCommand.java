package lab7.server.presenter.commands;

import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import lab7.server.presenter.Presenter;
import lab7.server.presenter.exceptions.*;
import lab7.serializedobjects.ServerMessage;
import lab7.serializedobjects.auth.User;
import lab7.serializedobjects.MessagesType;


/**
 * Команда вывода значений всех полей chapter объектов коллекции, отсортировав по убыванию
 */
public class PrintFieldDescendingChapterCommand implements Command {
    
    @Override
    public List<ServerMessage> execute(Presenter presenter, User executor) {
        List<ServerMessage> messages = new ArrayList<ServerMessage>();
        
        String result = presenter.getCollection().stream()
                                                    .map(e -> e.getChapter())
                                                    .sorted()
                                                    .map(Object::toString)
                                                    .collect(Collectors.joining("\n===\n"));

        presenter.getView().showResult(result);
        messages.add(new ServerMessage(MessagesType.RESULT, result));

        return messages;
    }

    @Override
    public String getName() {
        return "print_field_descending_chapter";
    }

    @Override
    public String getDescription() {
        return "вывести значения поля chapter всех элементов в порядке убывания";
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
