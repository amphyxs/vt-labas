package org.lab5.Presenter.Commands;

import org.lab5.Presenter.IPresenter;
import org.lab5.Presenter.Exceptions.*;

public class ExitCommand implements ICommand {
    
    @Override
    public void execute(IPresenter presenter) {
        while (presenter.getView().getIsScriptMode())
            presenter.stop();
            
        presenter.stop();
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
