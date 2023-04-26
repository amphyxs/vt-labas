package lab7.serializedobjects.exceptions;

/**
 * Не удалось загрузить данные
 */
public class LoadFailedException extends Exception {
    public LoadFailedException(String message) {
        super(message);
    }
}
