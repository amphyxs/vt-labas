package lab8.client.presenter.commands;

import java.util.List;

import lab8.client.presenter.Presenter;
import lab8.client.presenter.exceptions.*;
import lab8.client.view.View;

import java.util.ArrayList;


/**
 * Команда вывода информации обо всех командах
 */
public class HelpCommand implements Command {

    @Override
    public void execute(Presenter presenter) {
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
        view.showResult(String.join("\n", commandsDescriptions));
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
