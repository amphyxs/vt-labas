package org.lab5.Presenter.Commands;

import org.lab5.Presenter.IPresenter;
import org.lab5.Presenter.Exceptions.BadCommandArgException;
import org.lab5.Presenter.Exceptions.CommandArgNotFound;

/**
 * Интерфейс для всех команд
 */
public interface ICommand {
    /**
     * 
     * @return Имя команды
     */
    String getName();

    /**
     * 
     * @return Описание команды
     */
    String getDescription();

    /**
     * 
     * @return Массив имён аргументов команды
     */
    String[] getArgsNames();

    /**
     * 
     * @param argName Имя аргумента
     * @return Значение аргумента команды
     * @throws CommandArgNotFound Если нет аргумента с таким именем
     */
    Object getArg(String argName) throws CommandArgNotFound;

    /**
     * 
     * @param argName Имя аргумента
     * @param valueString Значение аргумента команды
     * @throws BadCommandArgException Если невозможно присвоить такое значение аргументу с этим типом
     */
    void setArg(String argName, String valueString) throws BadCommandArgException;

    /**
     * Исполнить команду
     * 
     * @param presenter Представление
     */
    void execute(IPresenter presenter);
}
