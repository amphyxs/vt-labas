package lab7.server.model;

import lab7.serializedobjects.auth.User;
import lab7.serializedobjects.exceptions.ValidationFailedException;


public class RootUser extends User {
    
    public RootUser() throws ValidationFailedException {
        super("root", null);
    }
    
}
