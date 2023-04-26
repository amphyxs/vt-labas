package lab7.client.presenter.exceptions;

/**
 * Не существует аргумента с таким именем у определённой команды
 */
public class CommandArgNotFound extends Exception {
    public CommandArgNotFound(String commandName, String argName) {
        super(String.format("У команды \"%s\" нет аргумента \"%s\"", commandName, argName));
    }
}
