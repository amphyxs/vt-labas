package lab8.client.presenter.commands;

import java.util.Stack;

import lab8.client.presenter.Presenter;
import lab8.client.presenter.exceptions.*;
import lab8.client.view.View;
import lab8.client.view.LocalStreamView;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileInputStream;


/**
 * Команда исполнения скрипта
 */
public class ExecuteScriptCommand implements Command {

    private String filename;
    private static Stack<String> executedScripts = new Stack<String>();

    @Override
    public void execute(Presenter presenter) {
        if (ExecuteScriptCommand.executedScripts.contains(getScriptPath())) {
            presenter.getView().showError("Обнаружена рекурсия. Исполнение данного скрипта будет пропущено");
            return;
        }    
        
        View scriptView;
        try {
            scriptView = new LocalStreamView(new FileInputStream(new File(this.filename)), presenter, true);
        } catch (FileNotFoundException e) {
            presenter.getView().showError(String.format("Не удалось получить доступ к скрипту \"%s\" (не найден или недостаточно прав)", this.filename));
            return;
        }

        ExecuteScriptCommand.executedScripts.add(getScriptPath());
        
        presenter.addView(scriptView);
        presenter.start();

        executedScripts.pop();
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

    @Override
    public Object getArg(String argName) throws CommandArgNotFound {
        switch (argName) {
            case "file_name":
                return this.filename;
            default:
                throw new CommandArgNotFound(getName(), argName);
        }
    }

    private String getScriptPath() {
        return (new File(this.filename)).getAbsolutePath();
    }

}