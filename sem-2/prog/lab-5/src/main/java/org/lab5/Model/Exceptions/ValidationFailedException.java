package org.lab5.Model.Exceptions;

/**
 * Ошибка валидации поля (не тот тип или несоответсвие ограничениям)
 */
public class ValidationFailedException extends Exception {
    public ValidationFailedException(String message) {
        super(message);
    }
}