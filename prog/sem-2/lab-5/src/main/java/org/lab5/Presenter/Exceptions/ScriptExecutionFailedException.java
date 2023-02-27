package org.lab5.Presenter.Exceptions;

/**
 * Не удалось исполнить скрипт с командами
 */
public class ScriptExecutionFailedException extends Exception {
    public ScriptExecutionFailedException(String message) {
        super(message);
    }
}
