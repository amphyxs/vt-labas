package lab7.serializedobjects.form;

import java.lang.reflect.Method;


public class SecretTextField<T> extends TextField<T> {
    
    public SecretTextField(Class<T> fieldType, String fieldName, Method validator) {
        super(fieldType, fieldName, validator);
    }
    
    public SecretTextField(Class<T> fieldType, String fieldName) {
        super(fieldType, fieldName, null);
    }

}
