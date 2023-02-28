package org.lab5.Model.Exceptions;

/**
 * Ошибка пользовательского ввода (не тот тип или несоответсвие ограничениям)
 */
public class UserInputException extends Exception {
    public UserInputException(String message) {
        super(message);
    }
}