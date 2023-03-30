package lab6.serializedobjects.Exceptions;

/**
 * Поле id не соответствует ограничениям или неуникально
 */
public class BadIdException extends Exception {
    public BadIdException(String message) {
        super(message);
    }
}
