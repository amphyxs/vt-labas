package lab8.client.presenter.commands;

import lab7.serializedobjects.exceptions.ValidationFailedException;
import lab7.serializedobjects.form.EnumField;
import lab7.serializedobjects.form.Form;
import lab7.serializedobjects.form.AbstractForm;
import lab8.client.presenter.AppLanguage;
import lab8.client.presenter.Presenter;
import lab8.client.presenter.exceptions.BadCommandArgException;
import lab8.client.presenter.exceptions.CommandArgNotFound;
import lab8.client.presenter.exceptions.InputEndedException;

public class ShowSettingsCommand implements Command {
    private final Form<?> settingsForm = new AbstractForm<>(
            "settings",
            new EnumField<AppLanguage>(AppLanguage.class, "language")
    ) {
        @Override
        public String getSimpleTypeName() {
            return null;
        }

        @Override
        public Object createObject() throws ValidationFailedException {
            throw new UnsupportedOperationException();
        }
    };

    @Override
    public void execute(Presenter presenter) {
        try {
            EnumField<AppLanguage> languageField = (EnumField<AppLanguage>) settingsForm.getFields()[0];
            try {
                languageField.setValue(presenter.getView().getAppLanguage());
            } catch (ValidationFailedException e) {
                presenter.getView().showError(e.getLocalizedMessage());
            }
            presenter.getView().readForm(settingsForm);
            AppLanguage appLanguage = (AppLanguage) settingsForm.getFields()[0].getValue();
            presenter.getView().setAppLanguage(appLanguage);
        } catch (InputEndedException ignored) {
            }
    }

    @Override
    public String getName() {
        return "show_settings";
    }

    @Override
    public String getDescription() {
        return "показать настройки приложения";
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
