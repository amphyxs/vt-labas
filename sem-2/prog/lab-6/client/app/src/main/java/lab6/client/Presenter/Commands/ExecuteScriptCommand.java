package lab6.client.Presenter.Commands;

import java.util.Stack;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileInputStream;

import lab6.client.View.IView;
import lab6.client.Presenter.IPresenter;
import lab6.client.Presenter.Exceptions.*;
import lab6.client.View.LocalStreamView;


/**
 * Команда исполнения скрипта
 */
public class ExecuteScriptCommand implements ICommand {

    private String filename;
    private static Stack<String> executedScripts = new Stack<String>();

    @Override
    public void execute(IPresenter presenter) {
        if (ExecuteScriptCommand.executedScripts.contains(getScriptPath())) {
            presenter.getView().showError("Обнаружена рекурсия. Исполнение данного скрипта будет пропущено");
            return;
        }    
        
        IView scriptView;
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