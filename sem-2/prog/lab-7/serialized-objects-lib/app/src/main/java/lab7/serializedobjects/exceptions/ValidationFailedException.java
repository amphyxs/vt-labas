package lab7.serializedobjects.exceptions;

/**
 * Ошибка валидации поля (не тот тип или несоответсвие ограничениям)
 */
public class ValidationFailedException extends Exception {
    public ValidationFailedException(String message) {
        super(message);
    }
}