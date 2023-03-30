package lab6.server.Presenter.Exceptions;

/**
 * Не существует команды с таким именем
 */
public class CommandNotFoundException extends Exception {
    public CommandNotFoundException(String commandName) {
        super(String.format("Команды \"%s\" не существует", commandName));
    } 
}
