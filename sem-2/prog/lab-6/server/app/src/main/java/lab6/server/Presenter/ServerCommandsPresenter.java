package lab6.server.Presenter;

import java.util.Stack;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lab6.serializedobjects.ServerMessage;
import lab6.serializedobjects.MessagesType;
import lab6.serializedobjects.SerializedCommand;
import lab6.serializedobjects.DataClasses.SpaceMarine;
import lab6.serializedobjects.Exceptions.*;
import lab6.serializedobjects.Form.IForm;

import lab6.server.Model.IModel;
import lab6.server.View.IView;
import lab6.server.Presenter.Commands.*;
import lab6.server.Presenter.Exceptions.*;


/**
 * Представление, управляемое командами. Команды - средство обмена Отображения и Представления, аналогичное событиям
 */
public class ServerCommandsPresenter implements IPresenter {
    
    private Stack<IView> views;
    private Stack<AbstractFormCommand> waitingForForms;
    private IModel model;
    private DataStack<SpaceMarine> collection;
    private IServer server;

    /**
     * 
     * @param view Отображение
     * @param model Модель данных
     */
    public ServerCommandsPresenter(IView view, IModel model, IServer server) {
        this.views = new Stack<IView>();
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
            getView().showError(String.format("%s. Будет создана пустая коллекция", e.getMessage()));
            this.collection = new DataStack<SpaceMarine>();
        }
    }
    
    @Override
    public ICommand[] getCommands() {
        return new ICommand[] {  // TODO: use reflection to automatically extract all classes those implement ICommand
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
            new UpdateCommand()
        };
    }

    @Override
    public IView getView() {
        if (this.views.isEmpty())
            return null;
        else
            return this.views.peek();
    }

    @Override
    public void addView(IView view) {
        this.views.push(view);
    }

    @Override
    public IModel getModel() {
        return this.model;
    }

    @Override
    public void setModel(IModel model) {
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

        IView currView = this.views.peek();
        
        ICommand currentCommand;
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

            currentCommand.execute(this);
        }
    }
    
    @Override
    public void stop() {
        if (!this.views.isEmpty())
            this.views.pop();
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
    public ICommand getCommandByName(SerializedCommand clientsCommand)
            throws CommandNotFoundException, BadCommandArgException, BadCommandArgsNumberException, CommandArgNotFound {
        for (ICommand command : getCommands()) {
            if (command.getName().equals(clientsCommand.getName())) {
                for (String arg : clientsCommand.getArgs().keySet()) {
                    command.setArg(arg, clientsCommand.getArgs().get(arg));
                }    
                return command;
            }
        }
        
        throw new CommandNotFoundException(clientsCommand.getName());
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
    public Object processClientsObject(Object obj) throws UnsupportedObjectReceivedException, InputEndedException {
        Object answer;

        if (obj instanceof SerializedCommand) {
            answer = processClientsCommand((SerializedCommand) obj);
        } else if (obj instanceof IForm) {
            answer = processClientsForm((IForm) obj);
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
            ICommand serverCommand = getCommandByName(command);
            if (serverCommand instanceof AbstractFormCommand) {
                AbstractFormCommand formCommand = (AbstractFormCommand) serverCommand;
                answer = formCommand.getForm();
                this.waitingForForms.push(formCommand);
            } else {
                answer = serverCommand.execute(this);
            }
        } catch (CommandNotFoundException | BadCommandArgException | BadCommandArgsNumberException | CommandArgNotFound e) {
            getView().showError(e.getLocalizedMessage());
            List<ServerMessage> results = new ArrayList<ServerMessage>();
            results.add(new ServerMessage(MessagesType.ERROR, e.getLocalizedMessage()));
            answer = results;
        }

        return answer;
    }

    private Object processClientsForm(IForm form) {
        AbstractFormCommand command = this.waitingForForms.pop();
        command.setForm(form);
        Object answer = command.execute(this);
        return answer;
    }

}
