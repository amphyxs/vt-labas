package org.lab5.Presenter.Form;

import org.lab5.Model.Exceptions.ValidationFailedException;


/**
 * Интерфейс поля для ввода.
 * Поле состоит из его названия и типа данных, которое оно принимает.
 * Также поле должно валидироваться некоторым методом.
 */
public interface IField<T> extends IFormElement {
    /**
     * 
     * @return Тип данного поля
     */
    Class<T> getFieldType();

    /**
     * 
     * @return Текущее значение поля
     */
    T getValue();

    /**
     * 
     * @param value Новое значение поля
     * @throws ValidationFailedException При неуспешной валидации значения
     */
    void setValue(T value) throws ValidationFailedException;
}
