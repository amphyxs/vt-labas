package lab7.server.presenter.commands;

import java.util.List;
import java.util.ArrayList;

import lab7.serializedobjects.MessagesType;
import lab7.serializedobjects.ServerMessage;
import lab7.serializedobjects.auth.User;
import lab7.server.presenter.Presenter;
import lab7.server.presenter.exceptions.*;
import lab7.server.view.View;


/**
 * Команда вывода информации обо всех командах
 */
public class HelpCommand implements Command {

    @Override
    public List<ServerMessage> execute(Presenter presenter, User executor) {
        List<ServerMessage> messages = new ArrayList<ServerMessage>();
        
        Command[] commandsList = presenter.getCommands();
        View view = presenter.getView();
        List<String> commandsDescriptions = new ArrayList<String>();
        for (Command command : commandsList) {
            String argsString;
            if (command.getArgsNames() == null)
                argsString = "";
            else
                argsString = String.join(" ", command.getArgsNames()) + " ";
                
            commandsDescriptions.add(String.format("%s %s: %s", command.getName(), argsString, command.getDescription()));
        }

        String result = String.join("\n", commandsDescriptions);
        view.showResult(result);
        messages.add(new ServerMessage(MessagesType.RESULT, result));

        return messages;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "вывести справку по доступным командам";
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
