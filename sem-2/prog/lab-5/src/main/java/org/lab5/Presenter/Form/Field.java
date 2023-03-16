package org.lab5.Presenter.Form;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.lab5.Model.Exceptions.ValidationFailedException;


/**
 * Простое поле для ввода
 */
public class Field<T> implements IField<T> {
    private Class<T> fieldType;
    private String fieldName;
    private T value = null;
    private Method validator;
    
    public Field(Class<T> fieldType, String fieldName, Method validator) {
        this.fieldType = fieldType;
        this.fieldName = fieldName;
        this.validator = validator;
    }

    public Field(Class<T> fieldType, String fieldName) {
        this(fieldType, fieldName, null);
    }
    
    @Override
    public Class<T> getFieldType() {
        return this.fieldType;
    }

    @Override
    public String getName() {
        return this.fieldName;
    }

    @Override
    public T getValue() {
        return this.value;
    }

    @Override
    public void setValue(T value) throws ValidationFailedException {
        if (validator != null) {
            try {
                this.validator.invoke(null, value);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                throw new ValidationFailedException(e.getCause().getMessage());
            }
        }
        this.value = value;
    }
}
