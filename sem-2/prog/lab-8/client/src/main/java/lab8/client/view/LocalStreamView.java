package lab8.client.view;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.io.InputStream;

import javafx.stage.Stage;
import lab7.serializedobjects.auth.User;
import lab8.client.presenter.AppLanguage;
import lab8.client.presenter.Presenter;
import lab8.client.presenter.commands.AbstractAuthCommand;
import lab8.client.presenter.commands.Command;
import lab8.client.presenter.commands.LoginCommand;
import lab8.client.presenter.commands.RegisterCommand;
import lab8.client.presenter.exceptions.*;
import lab7.serializedobjects.exceptions.ValidationFailedException;
import lab7.serializedobjects.form.*;


/**
 * Отображение в виде вывода и чтения текста из консоли
 */
public class LocalStreamView implements View {

    private static final int DASHES = 20;

    private Scanner sc;
    private InputStream inputStream;
    private Presenter presenter;
    private boolean isScriptMode;
    private int formLevel = 0;
    private boolean isStopped;

    public LocalStreamView(InputStream inputStream, Presenter presenter, boolean isScriptMode) {
        setInputStream(inputStream);
        setPresenter(presenter);
        this.isScriptMode = isScriptMode;
        this.isStopped = false;
    }

    public LocalStreamView(InputStream inputStream, boolean isScriptMode) {
        this(inputStream, null, isScriptMode);
    }

    public LocalStreamView(Presenter presenter, boolean isScriptMode) {
        this(System.in, presenter, isScriptMode);
    }

    public LocalStreamView(boolean isScriptMode) {
        this(System.in, null, isScriptMode);
    }
    
    public LocalStreamView() {
        this(System.in, null, false);
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
        if (this.sc != null)
            this.sc.close();

        this.sc = new Scanner(inputStream);
    }

    @Override
    public Presenter getPresenter() {
        return this.presenter;
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public boolean getIsScriptMode() {
        return isScriptMode;
    }

    @Override
    public void start(Stage stage) {
        this.isStopped = false;

        if (getPresenter().getUser() == null)
            authUser();

        while (!checkIfStopped()) {
            Command command = null;
            try {
                command = readCommand();
            } catch (InputEndedException e) {
                stop();
            } catch (CommandNotFoundException | BadCommandArgException | BadCommandArgsNumberException | NullCommandException e) {
                showError(e.getLocalizedMessage());
            }

            if (command != null)
                getPresenter().processCommand(this, command);
        }
    }

    @Override
    public void stop() {
        this.isStopped = true;
    }

    @Override
    public boolean checkIfStopped() {
        return this.isStopped;
    }

    public Command readCommand() throws CommandNotFoundException, BadCommandArgException,
            BadCommandArgsNumberException, NullCommandException, InputEndedException {
        if (!this.isScriptMode)
            System.out.print("Введите команду: ");
            
        String commandString;
        try {
            commandString = this.sc.nextLine().trim();
            
            if (commandString.isBlank()) {
                throw new NullCommandException();
            }
        } catch (NoSuchElementException e) {  // EOF case
            stop();
            return null;
        }

        String[] commandWithArgs = commandString.split(" ");
        return presenter.getCommandByName(commandWithArgs);
    }

    @Override
    public <T> Form<T> readForm(Form<T> form) throws InputEndedException {
        if (formLevel == 0)
            printDashes();
        for (FormElement el : form.getElements()) {
            if (el instanceof Field) {
                Field f = (Field) el;
                while (true) {
                    try {
                        f.setValue(
                            f.getFieldType().isEnum() ?
                                readEnumConstant(f.getName(), f.getFieldType())
                            :
                                readSimpleField(f.getName(), f.getFieldType())
                        );
                        break;
                    } catch (ValidationFailedException | IllegalArgumentException e) {
                        showError(e.getMessage());
                    }
                }
            } else if (el instanceof Form) {
                formLevel += 1;
                readForm((Form) el);
                formLevel -= 1;
            }
        }
        if (formLevel == 0)
            printDashes();
        return form;
    }

    private <T> T readSimpleField(String fieldName, Class<T> fieldClass) throws InputEndedException, IllegalArgumentException {
        T value;
        System.out.printf("Введите %s: ", fieldName);
            
        try {
            String strValue = null;
            strValue = this.sc.nextLine().trim();
            if (this.isScriptMode)
                System.out.println(strValue);
            
            if (strValue.isBlank()) {
                value = null;
            } else {
                if (Long.class == fieldClass)
                    value = fieldClass.cast(Long.parseLong(strValue));
                else if (Integer.class == fieldClass)
                    value = fieldClass.cast(Integer.parseInt(strValue));
                else if (Boolean.class == fieldClass)
                    value = fieldClass.cast(Boolean.parseBoolean(strValue));
                else if (Byte.class == fieldClass)
                    value = fieldClass.cast(Byte.parseByte(strValue));
                else if (Short.class == fieldClass)
                    value = fieldClass.cast(Short.parseShort(strValue));
                else if (Float.class == fieldClass)
                    value = fieldClass.cast(Float.parseFloat(strValue));
                else if (Double.class == fieldClass)
                    value = fieldClass.cast(Double.parseDouble(strValue));
                else if (String.class == fieldClass)
                    value = fieldClass.cast(strValue);
                else
                    throw new IllegalArgumentException(String.format("Передан тип (%s), неподдерживаемый для чтения с помощью %s", fieldClass.getName(), getClass().getSimpleName()));
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("Неверный формат ввода (требуемый тип: %s)", fieldClass.getSimpleName()));
        } catch (NoSuchElementException e) {  // EOF case
            stop();
            throw new InputEndedException();
        }

        return value;
    }

    private <T extends Enum<T>> T readEnumConstant(String fieldName, Class<T> enumClass) throws InputEndedException, IllegalArgumentException {
        if (!enumClass.isEnum())
            throw new IllegalArgumentException("При попытке чтения константы enum был передан не enum");

        T constant;
        String allConstants = Arrays.stream(enumClass.getEnumConstants())
                                                                    .map(Object::toString)
                                                                    .collect(Collectors.joining(", "));
        
        
        System.out.printf("Введите %s (%s): ", fieldName, allConstants);
        String constantName;

        try {
            constantName = this.sc.nextLine().trim();
            if (this.isScriptMode)
                System.out.println(constantName);

        } catch (NoSuchElementException e) {  // EOF case
            stop();
            throw new InputEndedException();
        }

        if (constantName.isBlank()) {
            constant = null;
        } else {
            try { // Check if user gave ordinal instead of constant name
                int ordinal = Integer.parseInt(constantName);
                constant = enumClass.getEnumConstants()[ordinal];
            } catch (Exception e) {
                constant = Enum.valueOf(enumClass, constantName);
            }
        }

        return constant;
    }

    @Override
    public void showResult(String result) {
        printDashes();
        System.out.println(result);
        printDashes();
    }
    
    @Override
    public void showError(String message) {
        printDashes();
        System.out.printf("⚠️ Ошибка: %s\n", message);
        printDashes();
    }

    @Override
    public void showInfo(String message) {
        System.out.printf("ℹ️ %s\n", message);
    }

    private void printDashes() {
        for (int i = 0; i < DASHES; i++)
            System.out.print("-");
        System.out.println();
    }

    private boolean askYesOrNo(String message) throws InputEndedException {
        String answer = "";
        while (!answer.equals("д") && !answer.equals("н")) {
            System.out.printf("? %s? (д/н): ", message);
            try {
                answer = this.sc.nextLine().trim();
            } catch (NoSuchElementException e) {
                throw new InputEndedException();
            }
        }
        return answer.equals("д");
    }

    private void authUser() {
        boolean isAuthenticated = false;
        while (!isAuthenticated && !checkIfStopped()) {
            boolean isRegister;
            try {
                isRegister = askYesOrNo("Осуществить регистрацию нового пользователя");
            } catch (InputEndedException e) {
                stop();
                break;
            }

            AbstractAuthCommand authCommand;
            if (isRegister)
                authCommand = new RegisterCommand();
            else
                authCommand = new LoginCommand();

            authCommand.execute(getPresenter());
            try {
                User user = authCommand.createUser();
                getPresenter().setUser(user);
                isAuthenticated = true;
            } catch (ValidationFailedException | UserLoginFailedException e) {
                showError(e.getLocalizedMessage());
            } catch (InputEndedException e) {
                stop();
            }
        }
    }

    @Override
    public void setAppLanguage(AppLanguage language) {
        throw new UnsupportedOperationException();
    }

    @Override
    public AppLanguage getAppLanguage() {
        throw new UnsupportedOperationException();
    }
}
