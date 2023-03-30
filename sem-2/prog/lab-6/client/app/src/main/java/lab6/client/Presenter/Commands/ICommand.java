package lab6.client.Presenter.Commands;

import java.util.HashMap;
import java.util.Map;

import lab6.client.Presenter.IPresenter;
import lab6.client.Presenter.Exceptions.BadCommandArgException;
import lab6.client.Presenter.Exceptions.CommandArgNotFound;

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

    default Map<String, String> getCurrentArgs() {
        HashMap<String, String> args = new HashMap<String, String>();

        if (getArgsNames() != null) {
            for (String arg : getArgsNames()) {
                String value;
                try {
                    value = getArg(arg).toString();
                } catch (CommandArgNotFound e) {
                    value = null;
                }
    
                args.put(arg, value);
            }
        }

        return args;
    }
}