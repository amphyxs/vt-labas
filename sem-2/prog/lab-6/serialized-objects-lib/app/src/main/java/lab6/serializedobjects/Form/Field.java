package lab6.serializedobjects.Form;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lab6.serializedobjects.SerializedMethod;
import lab6.serializedobjects.Exceptions.ValidationFailedException;


/**
 * Простое поле для ввода
 */
public class Field<T> implements IField<T> {
    public Class<T> fieldType;
    public String fieldName;
    public T value;
    public SerializedMethod validator;
    
    public Field(Class<T> fieldType, String fieldName, Method validator) {
        this.fieldType = fieldType;
        this.fieldName = fieldName;
        this.validator = new SerializedMethod(validator);
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
                this.validator.getMethod().invoke(null, value);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                throw new ValidationFailedException(e.getCause().getMessage());
            }
        }
        this.value = value;
    }
}
