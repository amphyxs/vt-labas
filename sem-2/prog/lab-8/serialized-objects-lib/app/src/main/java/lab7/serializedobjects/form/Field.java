package lab7.serializedobjects.form;

import lab7.serializedobjects.exceptions.ValidationFailedException;


/**
 * Интерфейс поля для ввода.
 * Поле состоит из его названия и типа данных, которое оно принимает.
 * Также поле должно валидироваться некоторым методом.
 */
public interface Field<T> extends FormElement {
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

    void setRawValue(String rawValue) throws ValidationFailedException;
}
