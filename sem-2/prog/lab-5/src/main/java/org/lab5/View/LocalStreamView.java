package org.lab5.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.io.InputStream;

import org.lab5.Presenter.IPresenter;
import org.lab5.Presenter.Commands.ICommand;
import org.lab5.Presenter.Exceptions.BadCommandArgException;
import org.lab5.Presenter.Exceptions.BadCommandArgsNumberException;
import org.lab5.Presenter.Exceptions.CommandNotFoundException;
import org.lab5.Presenter.Exceptions.InputEndedException;
import org.lab5.Presenter.Exceptions.NullCommandException;

/**
 * Отображение в виде вывода и чтения текста из консоли
 */
public class LocalStreamView implements IView {

    private static final int DASHES = 20;

    private Scanner sc;
    private InputStream inputStream;
    private IPresenter presenter;
    private boolean isScriptMode;

    public LocalStreamView(InputStream inputStream, IPresenter presenter, boolean isScriptMode) {
        this.inputStream = inputStream;
        this.sc = new Scanner(inputStream);
        this.presenter = presenter;
        this.isScriptMode = isScriptMode;
    }

    public LocalStreamView(InputStream inputStream, boolean isScriptMode) {
        this(inputStream, null, isScriptMode);
    }

    public LocalStreamView(IPresenter presenter, boolean isScriptMode) {
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
        this.sc.close();
        this.sc = new Scanner(inputStream);
    }

    @Override
    public IPresenter getPresenter() {
        return this.presenter;
    }

    @Override
    public void setPresenter(IPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public boolean getIsScriptMode() {
        return isScriptMode;
    }

    @Override
    public ICommand readCommand() throws CommandNotFoundException, BadCommandArgException,
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
            this.presenter.stop();
            return null;
        }

        String[] commandWithArgs = commandString.split(" ");
        return presenter.getCommandByName(commandWithArgs);
    }

    @Override
    public <T> T readSimpleField(String fieldName, Method checker, Class<T> fieldClass, int firstLastField) throws InputEndedException {
        // FIXME: make form or refactor this firstLastField
        if (firstLastField == -1)
            printDashes();

        T value;
        while (true) {
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
                        throw new IllegalArgumentException(String.format("Передан тип (%s), неподдерживаемый для чтения с помощью LocalStreamView", fieldClass.getName()));
                }
                
                if (checker != null)
                    checker.invoke(null, value);

                break;
            } catch (IllegalAccessException | InvocationTargetException e) {
                showError(e.getCause().getMessage());
            } catch (NumberFormatException e) {
                showError(String.format("Неверный формат ввода (требуемый тип: %s)", fieldClass.getSimpleName()));
            } catch (NoSuchElementException e) {  // EOF case
                this.presenter.stop();
                return null;
            }
        }

        if (firstLastField == 1)
            printDashes();

        return value;
    }

    @Override
    public <T extends Enum<T>> T readEnumConstant(String fieldName, Method checker, Class<T> enumClass, int firstLastField) throws InputEndedException {
        if (firstLastField == -1)
            printDashes();

        if (!enumClass.isEnum())
            throw new IllegalArgumentException("При попытке чтения константы enum был передан не enum");

        T constant;
        String allConstants = Arrays.stream(enumClass.getEnumConstants()).map(Object::toString).collect(Collectors.joining(", "));
        while (true) {
            System.out.printf("Введите %s (%s): ", fieldName, allConstants);

            String constantName;
            try {
                constantName = this.sc.nextLine().trim();
                if (this.isScriptMode)
                    System.out.println(constantName);
                    
            } catch (NoSuchElementException e) {  // EOF case
                this.presenter.stop();
                return null;
            }

            if (constantName.isBlank()) {
                constant = null;
                try {
                    checker.invoke(null, constant);
                    break;
                } catch (IllegalAccessException | InvocationTargetException e) {
                    showError(e.getCause().getMessage());
                }
            } else {
                try { // Check if user gave ordinal instead of constant name
                    int ordinal = Integer.parseInt(constantName);
                    constant = enumClass.getEnumConstants()[ordinal];
                    break;
                } catch (Exception ee) {
                    try {
                        constant = Enum.valueOf(enumClass, constantName);
                        checker.invoke(null, constant);
                        break;
                    } catch (IllegalArgumentException e) {
                        showError("Введён несуществующий тип");
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        showError(e.getCause().getMessage());
                    }
                }
            }

        }

        if (firstLastField == 1)
            printDashes();

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

}
