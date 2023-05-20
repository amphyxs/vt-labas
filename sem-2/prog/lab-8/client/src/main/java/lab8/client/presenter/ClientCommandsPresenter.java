package lab8.client.presenter;

import java.io.IOException;
import java.util.Stack;

import javafx.stage.Stage;
import lab7.serializedobjects.auth.User;
import lab7.serializedobjects.dataclasses.SpaceMarine;
import lab8.client.presenter.commands.*;
import lab8.client.presenter.exceptions.*;
import lab8.client.view.View;


public class ClientCommandsPresenter implements Presenter {
    
    private Stack<View> views;
    private Client client;
    private User user;
    private Stage stage;
    private AppLanguage appLanguage;

    public ClientCommandsPresenter(View view, Client client, Stage stage) {
        view.setPresenter(this);
        this.views = new Stack<View>();
        addView(view);
        this.client = client;
        this.user = null;
        this.stage = stage;
    }

    @Override
    public Command[] getCommands() {
        return new Command[] {  // TODO: use reflection to automatically extract all classes those implement ICommand
            new AddCommand(),
            new ClearCommand(),
            new ExecuteScriptCommand(),
            new ExitCommand(),
            new FilterStartsWithNameCommand(),
            new HelpCommand(),
            new InfoCommand(),
            new PrintFieldDescendingChapterCommand(),
            new RemoveAllByHealth(),
            new RemoveByIdCommand(),
            new RemoveFirstCommand(),
            new RemoveLastCommand(),
            new ShowCommand(),
            new ShuffleCommand(),
            new UpdateCommand(),
            new CurrentUserCommand()
        };
    }

    @Override
    public Command getCommandByName(String[] commandWithArgs)
            throws CommandNotFoundException, BadCommandArgException, BadCommandArgsNumberException {
        String commandName = commandWithArgs[0];
        for (Command command : getCommands()) {
            if (command.getName().equals(commandName)) {
                if (command.getArgsNames() != null) {
                    if (command.getArgsNames().length != commandWithArgs.length - 1)
                        throw new BadCommandArgsNumberException(commandName, commandWithArgs.length - 1, command.getArgsNames().length);
                    for (int i = 0; i < command.getArgsNames().length; i++) {
                        String arg = command.getArgsNames()[i];
                        command.setArg(arg, commandWithArgs[i + 1]);
                    }
                } else {
                    if (commandWithArgs.length > 1)
                        throw new BadCommandArgsNumberException(commandName, commandWithArgs.length - 1, 0);
                }

                return command;
            }
        }
        throw new CommandNotFoundException(commandName);
    }

    @Override
    public View getView() {
        while (!this.views.empty() && this.views.peek().checkIfStopped())
                this.views.pop();

        return this.views.empty() ? null : this.views.peek();
    }

    @Override
    public void addView(View view) {
        this.views.push(view);   
    }

    @Override
    public void start() {
        if (this.views.isEmpty())
            return;

        View currView = getView();
        if (currView != null)
            currView.start(stage);
    }

    @Override
    public void processCommand(View view, Command command) {
        if (command == null)
            return;

        if (view.getIsScriptMode())
            view.showInfo(String.format("Исполнение команды \"%s%s\"", command.getName(), getCommandArgs(command)));

        command.execute(this);
    }

    private String getCommandArgs(Command command) {
        StringBuilder commandArgs = new StringBuilder();
        try {
            if (command.getArgsNames() != null && command.getArgsNames().length != 0) {
                for (String argName : command.getArgsNames()) {
                    commandArgs.append(" " + command.getArg(argName).toString());
                }
            }
        } catch (CommandArgNotFound e) {
            getView().showError(e.getMessage());
        }
        return commandArgs.toString();
    }

    @Override
    public Client getClient() {
        return this.client;
    }

    @Override
    public boolean connectToHost() {
        try {
            this.client.initSocket();
        } catch (IOException e) {
            if (getView() != null)
                getView().showError(String.format("Не удалось подключиться к серверу: %s", e.getLocalizedMessage()));
            return false;
        }
        return true;
    }

    @Override
    public User getUser() {
        return this.user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public SpaceMarine[] fetchData() {
        FetchCommand command = new FetchCommand();
        command.execute(this);
        return command.getFetchedData();
    }

}
