package lab7.server.presenter.commands;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lab7.serializedobjects.ServerMessage;
import lab7.serializedobjects.auth.User;
import lab7.serializedobjects.MessagesType;
import lab7.server.presenter.Presenter;
import lab7.server.presenter.collection.DataCollection;
import lab7.server.presenter.exceptions.*;
import lab7.server.view.View;


/**
 * Команда вывода информации о коллекции
 */
public class InfoCommand implements Command {

    @Override
    public List<ServerMessage> execute(Presenter presenter, User executor) {
        List<ServerMessage> messages = new ArrayList<ServerMessage>();
        
        DataCollection collection = presenter.getCollection();
        View view = presenter.getView();
        String initDate = collection.getInitDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm"));
        String modificationDate = collection.getModificationDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm"));
        
        String result = String.format(
            "Тип коллекции: %s\n" +
            "Дата инициализации: %s\n" +
            "Дата последнего изменения: %s\n" +
            "Количество элементов: %s",
            collection.getTypeName(), initDate, modificationDate, collection.getSize()
        );
        view.showResult(result);
        messages.add(new ServerMessage(MessagesType.RESULT, result));

        return messages;
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
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
