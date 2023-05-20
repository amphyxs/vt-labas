package lab7.server.presenter;

import java.util.Stack;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import lab7.serializedobjects.ServerMessage;
import lab7.serializedobjects.auth.RootUser;
import lab7.serializedobjects.MessagesType;
import lab7.serializedobjects.SerializedCommand;
import lab7.serializedobjects.dataclasses.SpaceMarine;
import lab7.serializedobjects.exceptions.*;
import lab7.serializedobjects.form.Form;
import lab7.server.model.Model;
import lab7.server.presenter.collection.DataStack;
import lab7.server.presenter.commands.*;
import lab7.server.presenter.exceptions.*;
import lab7.server.presenter.net.Server;
import lab7.server.view.View;


/**
 * Представление, управляемое командами. Команды - средство обмена Отображения и Представления, аналогичное событиям
 */
public class ServerCommandsPresenter implements Presenter {
    
    private Stack<View> views;
    private Model model;
    private Server server;

    private Stack<AbstractFormCommand> waitingForForms;
    private DataStack<SpaceMarine> collection;


    /**
     * 
     * @param view Отображение
     * @param model Модель данных
     */
    public ServerCommandsPresenter(View view, Model model, Server server) {
        this.views = new Stack<View>();
        this.waitingForForms = new Stack<AbstractFormCommand>();

        addView(view);
        view.setPresenter(this);
        
        this.model = model;
        model.setPresenter(this);
        
        this.server = server;
        server.setPresenter(this);
        
        initCollection();
    }

    private void initCollection() {
        try {
            this.collection = new DataStack<SpaceMarine>(this.model.loadData());
        } catch (LoadFailedException e) {
            this.server.getLogger().error("{}. Будет создана пустая коллекция", e.getLocalizedMessage(), e);
            this.collection = new DataStack<SpaceMarine>();
        }
    }
    
    @Override
    public Command[] getCommands() {
        return new Command[] {
            new ExitCommand(),
            new ClearCommand(),
            new InfoCommand(),
            new AddCommand(),
            new FilterStartsWithNameCommand(),
            new HelpCommand(),
            new PrintFieldDescendingChapterCommand(),
            new RemoveAllByHealth(),
            new RemoveByIdCommand(),
            new RemoveFirstCommand(),
            new RemoveLastCommand(),
            new SaveCommand(),
            new ShowCommand(),
            new ShuffleCommand(),
            new UpdateCommand(),
            new RegisterCommand(),
            new LoginCommand(),
            new FetchCommand()
        };
    }

    @Override
    public View getView() {
        if (this.views.isEmpty())
            return null;
        else
            return this.views.peek();
    }

    @Override
    public void addView(View view) {
        this.views.push(view);
    }

    @Override
    public Model getModel() {
        return this.model;
    }

    @Override
    public void setModel(Model model) {
        this.model = model;
    }

    @Override
    public DataStack<SpaceMarine> getCollection() {
        return this.collection;
    }

    @Override
    public void start() {
        if (this.views.isEmpty())
            return;

        View currView = this.views.peek();
        
        Command currentCommand;
        RootUser user;
        try {
            user = new RootUser();
        } catch (ValidationFailedException e) {
            getView().showError(e.getLocalizedMessage());
            stop();
            return;
        }

        try {
            currentCommand = currView.readCommand();  
        } catch (CommandNotFoundException | BadCommandArgException | BadCommandArgsNumberException e) {
            currView.showError(e.getMessage());
            return;
        } catch (NullCommandException e) {
            return;
        } catch (InputEndedException e) {
            stop();
            return;
        }

        if (currentCommand != null) {
            if (currView.getIsScriptMode()) {
                currView.showInfo(String.format("Исполнение команды \"%s%s\"", currentCommand.getName(), getCommandArgs(currentCommand)));
            }

            currentCommand.execute(this, user);
        }
    }
    
    @Override
    public void stop() {
        if (!this.views.isEmpty()) {
            this.views.pop();
        }
    }
    
    @Override
    public void fullStop() {
        while (!this.views.isEmpty())
            stop();
        
        stopServer();
    }
        
    private void stopServer() {
        try {
            this.server.stop();
        } catch (IOException e) {
            getView().showError("Не удалось остановить сервер");
        }
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
    public Command getCommandByName(SerializedCommand clientsCommand)
            throws CommandNotFoundException, BadCommandArgException, BadCommandArgsNumberException, CommandArgNotFound {
        for (Command command : getCommands()) {
            if (command.getName().equals(clientsCommand.getName())) {
                for (String arg : clientsCommand.getArgs().keySet()) {
                    command.setArg(arg, clientsCommand.getArgs().get(arg));
                }    
                return command;
            }
        }
        
        throw new CommandNotFoundException(clientsCommand.getName());
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
    public Object processClientsObject(Object obj) throws UnsupportedObjectReceivedException, InputEndedException {
        Object answer;

        if (obj instanceof SerializedCommand) {
            answer = processClientsCommand((SerializedCommand) obj);
        } else if (obj instanceof Form) {
            answer = processClientsForm((Form<?>) obj);
        } else if (obj == null) {
            throw new InputEndedException();
        } else {
            throw new UnsupportedObjectReceivedException(obj.getClass().getSimpleName());
        }

        return answer;
    }

    private Object processClientsCommand(SerializedCommand command) {
        Object answer;
        
        try {
            Command serverCommand = getCommandByName(command);
            if (serverCommand instanceof AbstractFormCommand) {
                AbstractFormCommand<?> formCommand = (AbstractFormCommand<?>) serverCommand;
                formCommand.setExecutor(command.getExecutor());
                if (formCommand instanceof UpdateCommand) {
                    UpdateCommand updateCommand = (UpdateCommand) formCommand;
                    answer = updateCommand.getFilledForm(this);
                }
                else {
                    answer = formCommand.getForm();
                }
                this.waitingForForms.push(formCommand);
            } else {
                answer = serverCommand.execute(this, command.getExecutor());
            }
        } catch (CommandNotFoundException | BadCommandArgException | BadCommandArgsNumberException | CommandArgNotFound e) {
            getView().showError(e.getLocalizedMessage());
            List<ServerMessage> results = new ArrayList<ServerMessage>();
            results.add(new ServerMessage(MessagesType.ERROR, e.getLocalizedMessage()));
            answer = results;
        }

        return answer;
    }

    private Object processClientsForm(Form<?> form) {
        Object answer = null;
        ListIterator<AbstractFormCommand> index = this.waitingForForms.listIterator(this.waitingForForms.size());
        while (index.hasPrevious()) {
            AbstractFormCommand command = index.previous();
            try {
                command.setForm(form);
                index.remove();
                answer = command.execute(this, command.getExecutor());
                break;
            } catch (ValidationFailedException e) {
                getView().showError("Не удалось валидировать полученную форму");
                answer = null;
                break;
            } catch (ClassCastException e) {  // TODO: refactor it
                continue;
            }
        }
        return answer;
    }

}
