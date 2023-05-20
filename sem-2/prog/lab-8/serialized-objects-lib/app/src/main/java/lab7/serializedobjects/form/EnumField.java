package lab7.serializedobjects.form;

import java.lang.reflect.Method;

import lab7.serializedobjects.exceptions.ValidationFailedException;

public class EnumField<T extends Enum<T>> extends TextField<T> {
    
    public EnumField(Class<T> fieldType, String fieldName, Method validator) {
        super(fieldType, fieldName, validator);
    }

    public EnumField(Class<T> fieldType, String fieldName) {
        super(fieldType, fieldName, null);
    }

    @Override
    public void setRawValue(String rawValue) throws ValidationFailedException {
        if(rawValue == null) {
            setValue(null);
            return;
        }
        
        Class<T> t = this.fieldType;
        Object converted;
        try {
            converted = Enum.valueOf(t, rawValue);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new ValidationFailedException(this, e.getLocalizedMessage());
        }
        setValue(t.cast(converted));
    }

}
