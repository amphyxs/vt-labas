package lab8.client.view.fxapp;

import javafx.event.ActionEvent;
import lab7.serializedobjects.auth.User;
import lab7.serializedobjects.exceptions.ValidationFailedException;
import lab8.client.presenter.commands.AbstractAuthCommand;
import lab8.client.presenter.commands.LoginCommand;
import lab8.client.presenter.commands.RegisterCommand;
import lab8.client.presenter.commands.ShowSettingsCommand;
import lab8.client.presenter.exceptions.InputEndedException;
import lab8.client.presenter.exceptions.UserLoginFailedException;

import java.io.IOException;

public class WelcomeController {
    private GuiView view;

    public void setView(GuiView view) {
        this.view = view;
    }

    public void showAuthForm(boolean isForLogin) {
        AbstractAuthCommand authCommand = isForLogin ? new LoginCommand() : new RegisterCommand();
        authCommand.execute(this.view.getPresenter());
        try {
            User user = authCommand.createUser();
            this.view.getPresenter().setUser(user);
            this.view.showMainView();
        } catch (ValidationFailedException | UserLoginFailedException | IOException e) {
            this.view.showError(e.getLocalizedMessage());
        } catch (InputEndedException ignored) {

        }
    }

    public void showLoginForm(ActionEvent actionEvent) {
        showAuthForm(true);
    }

    public void showRegisterForm(ActionEvent actionEvent) {
        showAuthForm(false);
    }

    public void showLanguagePicker(ActionEvent actionEvent) {
        ShowSettingsCommand command = new ShowSettingsCommand();
        command.execute(view.getPresenter());
    }
}
