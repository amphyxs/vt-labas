package lab6.server.Presenter.Commands;

import java.util.stream.Collectors;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import lab6.serializedobjects.DataClasses.Chapter;
import lab6.serializedobjects.ServerMessage;
import lab6.serializedobjects.MessagesType;

import lab6.server.Presenter.IPresenter;
import lab6.server.Presenter.Exceptions.*;


/**
 * Команда вывода значений всех полей chapter объектов коллекции, отсортировав по убыванию
 */
public class PrintFieldDescendingChapterCommand implements ICommand {
    
    @Override
    public List<ServerMessage> execute(IPresenter presenter) {
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
