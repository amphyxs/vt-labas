package lab6.client.Presenter;

import java.io.IOException;
import java.util.Stack;

import lab6.client.Presenter.Exceptions.*;
import lab6.client.View.IView;
import lab6.client.Presenter.Commands.*;

public class ClientCommandsPresenter implements IPresenter {
    
    private Stack<IView> views;
    private IClient client;

    public ClientCommandsPresenter(IView view, IClient client) {
        view.setPresenter(this);
        this.views = new Stack<IView>();
        addView(view);
        this.client = client;
        connectToHost();
    }

    @Override
    public ICommand[] getCommands() {
        return new ICommand[] {  // TODO: use reflection to automatically extract all classes those implement ICommand
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
            new UpdateCommand()
        };
    }

    @Override
    public ICommand getCommandByName(String[] commandWithArgs)
            throws CommandNotFoundException, BadCommandArgException, BadCommandArgsNumberException {
        String commandName = commandWithArgs[0];
        for (ICommand command : getCommands()) {
            if (command.getName().equals(commandName)) {
                if (command.getArgsNames() != null) {
                    if (command.getArgsNames().length != commandWithArgs.length - 1)
                        throw new BadCommandArgsNumberException(commandName, commandWithArgs.length - 1, command.getArgsNames().length);
                    for (int i = 0; i < command.getArgsNames().length; i++) {
                        String arg = command.getArgsNames()[i];
                        command.setArg(arg, commandWithArgs[i + 1]);
                    }
                }

                return command;
            }
        }
        throw new CommandNotFoundException(commandName);
    }

    @Override
    public IView getView() {
        if (this.views.empty())
            return null;
        else
            return this.views.peek();
    }

    @Override
    public void addView(IView view) {
        this.views.push(view);   
    }

    @Override
    public void start() {
        if (this.views.isEmpty())
            return;

        IView currView = this.views.peek();
        
        while (!this.views.isEmpty() && currView == this.views.peek()) {
            ICommand currentCommand;
            try {
                currentCommand = currView.readCommand();  
            } catch (CommandNotFoundException | BadCommandArgException | BadCommandArgsNumberException e) {
                currView.showError(e.getMessage());
                continue;
            } catch (NullCommandException e) {
                continue;
            } catch (InputEndedException e) {
                stop();
                break;
            }

            if (currentCommand != null) {
                if (currView.getIsScriptMode()) {
                    currView.showInfo(String.format("Исполнение команды \"%s%s\"", currentCommand.getName(), getCommandArgs(currentCommand)));
                }

                currentCommand.execute(this);
            }
        }
    }

    @Override
    public void stop() {
        if (!this.views.isEmpty())
            this.views.pop();
    }

    private String getCommandArgs(ICommand command) {
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
    public IClient getClient() {
        return this.client;
    }

    @Override
    public boolean connectToHost() {
        try {
            this.client.initSocket();
        } catch (IOException e) {
            getView().showError(String.format("Не удалось подключиться к серверу: %s", e.getLocalizedMessage()));
            return false;
        }
        return true;
    }

}
