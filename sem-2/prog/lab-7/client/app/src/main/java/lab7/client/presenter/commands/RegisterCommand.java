package lab7.client.presenter.commands;

import lab7.client.presenter.exceptions.BadCommandArgException;
import lab7.client.presenter.exceptions.CommandArgNotFound;


public class RegisterCommand extends AbstractAuthCommand {
    @Override
    public String getName() {
        return "register";
    }

    @Override
    public Object getArg(String argName) throws CommandArgNotFound {
        return null;
    }

    @Override
    public void setArg(String argName, String valueString) throws BadCommandArgException {
        return;
    }

    @Override
    public String[] getArgsNames() {
        return null;
    }

    @Override
    public String getDescription() {
        return "осуществить регистрацию пользователя";
    }
}
