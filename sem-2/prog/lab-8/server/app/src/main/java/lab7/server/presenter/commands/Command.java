package lab7.server.presenter.commands;

import java.util.List;
import lab7.serializedobjects.ServerMessage;
import lab7.serializedobjects.auth.User;
import lab7.server.presenter.Presenter;
import lab7.server.presenter.exceptions.BadCommandArgException;
import lab7.server.presenter.exceptions.CommandArgNotFound;


/**
 * Интерфейс для всех команд
 */
public interface Command {
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
    List<ServerMessage> execute(Presenter presenter, User executor);
}
