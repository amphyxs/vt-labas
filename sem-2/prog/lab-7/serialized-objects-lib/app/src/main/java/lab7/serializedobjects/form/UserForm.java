package lab7.serializedobjects.form;

import lab7.serializedobjects.auth.User;
import lab7.serializedobjects.exceptions.ValidationFailedException;

public class UserForm extends AbstractForm<User> {
    public Field<String> name;
    public Field<String> password;     
    
    @Override
    public String getSimpleTypeName() {
        return "User";
    }

    public UserForm() {
        super("пользователь");
        try {
            this.name = new TextField<String>(String.class, "имя пользователя", User.class.getMethod("checkName", String.class));
            this.password = new TextField<String>(String.class, "пароль", User.class.getMethod("checkPassword", String.class));
        } catch (NoSuchMethodException e) {
            System.out.println(e.getLocalizedMessage());
            this.name = null;
            this.password = null;
        }

        setElements(new FormElement[] { name, password });
    }

    @Override
    public User createObject() throws ValidationFailedException {
        return new User(this.name.getValue(), this.password.getValue());
    }
}
