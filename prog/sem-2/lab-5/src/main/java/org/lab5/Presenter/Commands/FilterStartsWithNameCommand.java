package org.lab5.Presenter.Commands;

import java.util.stream.Collectors;

import org.lab5.Presenter.IPresenter;
import org.lab5.Presenter.Exceptions.*;

public class FilterStartsWithNameCommand implements ICommand { 

    private String name;

    @Override
    public void execute(IPresenter presenter) {
        String result = presenter.getCollection().stream()
                                    .filter(e -> e.getName().startsWith(this.name))
                                    .map(Object::toString)
                                    .collect(Collectors.joining("\n===\n"));
        presenter.getView().showResult(result);   
    }

    @Override
    public String getName() {
        return "filter_starts_with_name";
    }

    @Override
    public String getDescription() {
        return "вывести элементы, значение поля name которых начинается с заданной подстроки";
    }

    @Override
    public String[] getArgsNames() {
        return new String[] {"name"};
    }

    @Override
    public void setArg(String argName, String valueString) throws BadCommandArgException {
        switch (argName) {
            case "name":
                this.name = valueString;
                break;
        }
    }

    @Override
    public Object getArg(String argName) throws CommandArgNotFound {
        switch (argName) {
            case "name":
                return this.name;
            default:
                throw new CommandArgNotFound(getName(), argName);
        }
    }

}