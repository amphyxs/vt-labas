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
                throw new ValidationFailedException(this, e.getCause().getMessage());
            }
        }
        this.value = value;
    }

    @Override
    public void setRawValue(String rawValue) throws ValidationFailedException {
        if (rawValue == null) {
            setValue(null);
            return;
        }

        Object converted;
        Class<T> t = this.fieldType;
        try {
            if (t == String.class)
                converted = rawValue;
            else if (t == Integer.class)
                converted = Integer.parseInt(rawValue);
            else if (t == Boolean.class)
                converted = Boolean.parseBoolean(rawValue);
            else if (t == Float.class)
                converted = Float.parseFloat(rawValue);
            else if (t == Double.class)
                converted = Double.parseDouble(rawValue);
            else if (t == Long.class)
                converted = Long.parseLong(rawValue);
            else if (t == Short.class)
                converted = Short.parseShort(rawValue);
            else if (t == Byte.class)
                converted = Byte.parseByte(rawValue);
            else
                throw new ValidationFailedException(this, String.format("Unknown type to parse: %s", t.getSimpleName()));
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new ValidationFailedException(this, e.getLocalizedMessage());
        }

        setValue(t.cast(converted));
    }
}
