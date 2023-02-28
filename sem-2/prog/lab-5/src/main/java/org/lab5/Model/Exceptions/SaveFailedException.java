package org.lab5.Model.Exceptions;

/**
 * Не удалось сохранить данные
 */
public class SaveFailedException extends Exception {
    public SaveFailedException(String message) {
        super(message);
    }
}
