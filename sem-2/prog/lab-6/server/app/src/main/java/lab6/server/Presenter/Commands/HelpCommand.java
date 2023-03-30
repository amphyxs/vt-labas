package lab6.server.Presenter.Commands;

import java.util.List;
import java.util.ArrayList;

import lab6.serializedobjects.MessagesType;
import lab6.serializedobjects.ServerMessage;

import lab6.server.Presenter.IPresenter;
import lab6.server.Presenter.Exceptions.*;
import lab6.server.View.IView;


/**
 * Команда вывода информации обо всех командах
 */
public class HelpCommand implements ICommand {

    @Override
    public List<ServerMessage> execute(IPresenter presenter) {
        List<ServerMessage> messages = new ArrayList<ServerMessage>();
        
        ICommand[] commandsList = presenter.getCommands();
        IView view = presenter.getView();
        List<String> commandsDescriptions = new ArrayList<String>();
        for (ICommand command : commandsList) {
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
