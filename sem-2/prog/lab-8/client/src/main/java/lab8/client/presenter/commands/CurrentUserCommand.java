package lab8.client.presenter.commands;

import lab8.client.presenter.Presenter;
import lab8.client.presenter.exceptions.CommandArgNotFound;


public class CurrentUserCommand implements Command {
    
    @Override
    public void execute(Presenter presenter) {
        String username = presenter.getUser().getName();
        presenter.getView().showResult(username);
    }

    @Override
    public String getName() {
        return "current_user";
    }

    @Override
    public String getDescription() {
        return "вывести имя текущего пользователя";
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
