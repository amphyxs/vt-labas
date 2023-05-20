package lab7.serializedobjects.exceptions;

/**
 * Не удалось сохранить данные
 */
public class SaveFailedException extends Exception {
    public SaveFailedException(String message) {
        super(message);
    }
}
