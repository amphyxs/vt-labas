package lab6.server.View;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.InputStream;

import lab6.server.Presenter.IPresenter;
import lab6.server.Presenter.Commands.ICommand;
import lab6.server.Presenter.Exceptions.*;
import lab6.serializedobjects.Form.IField;
import lab6.serializedobjects.Form.IForm;
import lab6.serializedobjects.Form.IFormElement;
import lab6.serializedobjects.Exceptions.ValidationFailedException;


/**
 * Отображение в виде вывода и чтения текста из консоли
 */
public class LocalStreamView implements IView {

    private static final int DASHES = 20;

    private Scanner sc;
    private InputStream inputStream;
    private IPresenter presenter;
    private boolean isScriptMode;
    private int formLevel = 0;

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
        // if (!this.isScriptMode)
        //     System.out.print("Введите команду: ");
            
        String commandString;
        try {
            if (System.in.available() > 0) {
                commandString = this.sc.nextLine().trim();
            } else {
                return null;
            }
            
            if (commandString.isBlank()) {
                throw new NullCommandException();
            }
        } catch (NoSuchElementException | IOException e) {  // EOF case
            this.presenter.stop();
            return null;
        }

        String[] commandWithArgs = commandString.split(" ");
        return presenter.getCommandByName(commandWithArgs);
    }

    @Override
    public <T> IForm<T> readForm(IForm<T> form) throws InputEndedException {
        if (formLevel == 0)
            printDashes();
        for (IFormElement el : form.getElements()) {
            if (el instanceof IField) {
                IField f = (IField) el;
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
            } else if (el instanceof IForm) {
                formLevel += 1;
                readForm((IForm) el);
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
            this.presenter.stop();
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
            this.presenter.stop();
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

}
