package lab6.serializedobjects.Exceptions;

/**
 * Не удалось загрузить данные
 */
public class LoadFailedException extends Exception {
    public LoadFailedException(String message) {
        super(message);
    }
}
