package lab8.client.presenter.commands;

import lab8.client.presenter.Presenter;
import lab8.client.presenter.exceptions.*;


/**
 * Команда выхода из программы
 */
public class ExitCommand implements Command {
    
    @Override
    public void execute(Presenter presenter) {
        while (presenter.getView().getIsScriptMode())
            presenter.getView().stop();

        presenter.getView().stop();
    }

    @Override
    public String getDescription() {
        return "завершить программу (без сохранения в файл)";
    }

    @Override
    public String getName() {
        return "exit";
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
