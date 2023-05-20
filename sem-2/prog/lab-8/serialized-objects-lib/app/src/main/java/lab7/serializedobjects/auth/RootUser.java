package lab7.serializedobjects.auth;

import lab7.serializedobjects.exceptions.ValidationFailedException;


public class RootUser extends User {
    
    public RootUser() throws ValidationFailedException {
        super("root", null);
    }
    
}
