package lab6.server.Presenter.Commands;

import java.util.List;
import lab6.serializedobjects.ServerMessage;

import lab6.server.Presenter.IPresenter;
import lab6.server.Presenter.Exceptions.BadCommandArgException;
import lab6.server.Presenter.Exceptions.CommandArgNotFound;


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
    List<ServerMessage> execute(IPresenter presenter);
}
