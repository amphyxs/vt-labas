package lab7.serializedobjects.exceptions;

import lab7.serializedobjects.form.Field;


/**
 * Ошибка валидации поля (не тот тип или несоответсвие ограничениям)
 */
public class ValidationFailedException extends Exception {

    private Field<?> field;

    public Field<?> getField() {
        return field;
    }

    public ValidationFailedException(String message) {
        super(message);
    }

    public ValidationFailedException(Field<?> field, String message) {
        super(message);
        this.field = field;
    }
}