package lab7.serializedobjects.form;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lab7.serializedobjects.exceptions.ValidationFailedException;


/**
 * Простое поле для ввода
 */
public class TextField<T> implements Field<T> {
    public Class<T> fieldType;
    public String fieldName;
    public T value;
    public transient Method validator;
    
    public TextField(Class<T> fieldType, String fieldName, Method validator) {
        this.fieldType = fieldType;
        this.fieldName = fieldName;
        this.validator = validator;
    }

    public TextField(Class<T> fieldType, String fieldName) {
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
