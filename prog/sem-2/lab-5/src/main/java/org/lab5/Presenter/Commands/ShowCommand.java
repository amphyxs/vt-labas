package org.lab5.Presenter.Commands;

import java.util.stream.Collectors;

import org.lab5.Presenter.IPresenter;
import org.lab5.Presenter.Exceptions.*;

public class ShowCommand implements ICommand {
    
    @Override
    public void execute(IPresenter presenter) {
        String result = presenter.getCollection().stream()
                                                    .map(Object::toString)
                                                    .collect(Collectors.joining("\n===\n"));
        presenter.getView().showResult(result);
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
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
