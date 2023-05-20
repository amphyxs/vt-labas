package lab8.client.presenter.exceptions;

/**
 * Неподходящее число аргументов команды
 */
public class BadCommandArgsNumberException extends Exception {
    public BadCommandArgsNumberException(String commandName, int given, int shouldBe) {
        super(String.format("Неверное число аргументов команды \"%s\" (введено %d, должно быть %d)", commandName, given, shouldBe));
    }
}
