package org.lab5.Presenter;

import org.lab5.Model.IModel;
import org.lab5.Model.DataClasses.SpaceMarine;
import org.lab5.Model.Exceptions.LoadFailedException;
import org.lab5.View.IView;
import org.lab5.Presenter.Commands.*;
import org.lab5.Presenter.Exceptions.BadCommandArgException;
import org.lab5.Presenter.Exceptions.BadCommandArgsNumberException;
import org.lab5.Presenter.Exceptions.CommandNotFoundException;

/**
 * Представление, управляемое командами. Команды - средство обмена Отображения и Представления, аналогичное событиям
 */
public class CommandsPresenter implements IPresenter {
    
    private IView view;
    private IModel model;
    private DataStack<SpaceMarine> collection;
    private boolean exitStatus = false;

    /**
     * 
     * @param view Отображение
     * @param model Модель данных
     */
    public CommandsPresenter(IView view, IModel model) {
        this.view = view;
        this.model = model;
        initCollection();
    }

    private void initCollection() {
        try {
            this.collection = new DataStack<SpaceMarine>(this.model.loadData());
        } catch (LoadFailedException e) {
            this.view.showError(String.format("%s. Будет создана пустая коллекция", e.getMessage()));
            this.collection = new DataStack<SpaceMarine>();
        }
    }
    
    @Override
    public ICommand[] getCommands() {
        return new ICommand[] {
            new HelpCommand(),
            new InfoCommand(),
            new ExitCommand(),
            new ShowCommand(),
            new AddCommand(),
            new UpdateCommand(),
            new RemoveByIdCommand(),
            new ClearCommand(),
            new RemoveFirstCommand(),
            new RemoveLastCommand(),
            new ShuffleCommand(),
            new RemoveAllByHealth(),
            new FilterStartsWithNameCommand(),
            new PrintFieldDescendingChapterCommand(),
            new SaveCommand(),
            new ExecuteScriptCommand()
        };
    }

    @Override
    public IView getView() {
        return this.view;
    }

    @Override
    public IModel getModel() {
        return this.model;
    }

    @Override
    public DataStack<SpaceMarine> getCollection() {
        return this.collection;
    }

    @Override
    public void start() {
        while (this.exitStatus != true) {
            ICommand currentCommand;
            try {
                currentCommand = readCommand();
            } catch (CommandNotFoundException | BadCommandArgException | BadCommandArgsNumberException e) {
                this.view.showError(e.getMessage());
                continue;
            }
            currentCommand.execute(this);
        }
    }

    @Override
    public void stop() {
        this.exitStatus = true;
    }

    @Override
    public ICommand getCommandByName(String[] commandWithArgs) throws CommandNotFoundException, BadCommandArgException, BadCommandArgsNumberException {
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

    private ICommand readCommand() throws CommandNotFoundException, BadCommandArgException, BadCommandArgsNumberException {
        String commandString = this.view.readSimpleField("команду", null, String.class, 0);
        String[] commandWithArgs = commandString.split(" ");
        return getCommandByName(commandWithArgs);
    }
    
}
