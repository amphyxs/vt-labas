package org.lab5.Presenter.Commands;

import java.util.List;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.lab5.Presenter.IPresenter;
import org.lab5.Presenter.Exceptions.*;


public class ExecuteScriptCommand implements ICommand {

    private String filename;

    @Override
    public void execute(IPresenter presenter) {
        // TODO: JSON arguments (e.g. add command)
        try {
            for (ICommand command : readCommandsScript(presenter)) {
                StringBuilder commandArgs = new StringBuilder();
                if (command.getArgsNames() != null && command.getArgsNames().length != 0) {
                    for (String argName : command.getArgsNames()) {
                        commandArgs.append(" " + command.getArg(argName).toString());
                    }
                }

                presenter.getView().showInfo(String.format("Исполнение команды \"%s%s\"", command.getName(), commandArgs));
                try {
                    command.execute(presenter);
                } catch (StackOverflowError e) {
                    presenter.getView().showError("ВЫ ХОТЕЛИ УБИТЬ ПРОГРАММУ, НО НЕ ВЫШЛО. Остановка скрипта");
                    return;
                }
            }
        } catch (ScriptExecutionFailedException | CommandArgNotFound e) {
            presenter.getView().showError(e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "execute_script";
    }

    @Override
    public String getDescription() {
        return "считать и исполнить скрипт из указанного файла, в скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме";
    }

    @Override
    public String[] getArgsNames() {
        return new String[] {"file_name"};
    }

    @Override
    public void setArg(String argName, String valueString) throws BadCommandArgException {
        switch (argName) {
            case "file_name":
                this.filename = valueString;
                break;
        }
    }

    private ICommand[] readCommandsScript(IPresenter presenter) throws ScriptExecutionFailedException {
        List<ICommand> result = new ArrayList<ICommand>();

        try (BufferedReader reader = new BufferedReader(new FileReader(this.filename))) {
            List<String[]> scriptCommandsWithArgs = reader.lines().map(c -> c.strip().split(" ")).collect(Collectors.toList());
            for (String[] scriptCommand : scriptCommandsWithArgs) {
                String fullCommand = String.join(" ", scriptCommand);
                try {
                    result.add(presenter.getCommandByName(scriptCommand));
                } catch (CommandNotFoundException e) {
                    throw new ScriptExecutionFailedException(String.format("При считывании скрипта обнаружена неизвестная команда \"%s\"", fullCommand));
                } catch (BadCommandArgException e) {
                    throw new ScriptExecutionFailedException(String.format("В скрипте команде \"%s\" передан неверный аргумент (%s)", fullCommand, e.getMessage()));
                } catch (BadCommandArgsNumberException e) {
                    throw new ScriptExecutionFailedException(String.format("В скрипте команде \"%s\" передано неверное число аргументов (%s)", fullCommand, e.getMessage()));
                }
            }
        } catch (FileNotFoundException e) {
            throw new ScriptExecutionFailedException(String.format("Невозможно получить доступ к файлу \"%s\" (не найден или недостаточно прав)", this.filename));
        } catch (IOException e) {
            throw new ScriptExecutionFailedException(String.format("Не удалось считать скрипт", this.filename));
        }

        return result.toArray(new ICommand[result.size()]);
    }

    @Override
    public Object getArg(String argName) throws CommandArgNotFound {
        switch (argName) {
            case "file_name":
                return this.filename;
            default:
                throw new CommandArgNotFound(getName(), argName);
        }
    }

}