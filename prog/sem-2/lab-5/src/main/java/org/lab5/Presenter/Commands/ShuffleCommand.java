package org.lab5.Presenter.Commands;

import java.util.Collections;

import org.lab5.Presenter.IPresenter;
import org.lab5.Presenter.Exceptions.*;

public class ShuffleCommand implements ICommand {
    
    @Override
    public void execute(IPresenter presenter) {
        Collections.shuffle(presenter.getCollection());
    }

    @Override
    public String getName() {
        return "shuffle";
    }

    @Override
    public String getDescription() {
        return "перемешать элементы коллекции в случайном порядке";
    }

    @Override
    public String[] getArgsNames() {
        return null;
    }

    @Override
    public void setArg(String argName, String valueString) throws BadCommandArgException {
        return;
    }

    @Override
    public Object getArg(String argName) throws CommandArgNotFound {
        throw new CommandArgNotFound(getName(), argName);
    }

}
