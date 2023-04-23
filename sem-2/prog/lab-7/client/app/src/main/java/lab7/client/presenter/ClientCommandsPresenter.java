package lab7.client.presenter;

import java.io.IOException;
import java.util.Stack;
import lab7.serializedobjects.auth.User;
import lab7.serializedobjects.exceptions.ValidationFailedException;
import lab7.client.presenter.commands.*;
import lab7.client.presenter.exceptions.*;
import lab7.client.view.View;


public class ClientCommandsPresenter implements Presenter {
    
    private Stack<View> views;
    private Client client;
    private User user;

    public ClientCommandsPresenter(View view, Client client) {
        view.setPresenter(this);
        this.views = new Stack<View>();
        addView(view);
        this.client = client;
        connectToHost();
        this.user = null;
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
        if (this.views.empty())
            return null;
        else
            return this.views.peek();
    }

    @Override
    public void addView(View view) {
        this.views.push(view);   
    }

    @Override
    public void start() {
        if (this.user == null)
            authUser();

        if (this.views.isEmpty())
            return;

        View currView = this.views.peek();
        
        while (!this.views.isEmpty() && currView == this.views.peek()) {
            Command currentCommand;
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

    private void authUser() {
        boolean isAuthenticated = false;
        while (!isAuthenticated && !this.views.empty()) {
            boolean isRegister;
            try {
                isRegister = getView().askYesOrNo("Осуществить регистрацию нового пользователя");
            } catch (InputEndedException e) {
                stop();
                break;
            }
            
            AbstractAuthCommand authCommand;
            if (isRegister)
                authCommand = new RegisterCommand();
            else
                authCommand = new LoginCommand();
                
            authCommand.execute(this);
            try {
                User user = authCommand.createUser();
                setUser(user);
                isAuthenticated = true;
            } catch (ValidationFailedException | UserLoginFailedException e) {
                getView().showError(e.getLocalizedMessage());
            } catch (InputEndedException e) {
                stop();
            }
        }
    }
}
