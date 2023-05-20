package lab7.serializedobjects;

import java.io.Serializable;
import java.util.Map;

import lab7.serializedobjects.auth.User;


public class SerializedCommand implements Serializable {

    private String name;
    private Map<String, String> args;
    private User executor;
    
    public SerializedCommand(String name, Map<String, String> args, User executor) {
        this.name = name; 
        this.args = args;
        this.executor = executor;
    }

    public String getName() {
        return this.name;
    }

    public Map<String, String> getArgs() {
        return this.args;
    }
    
    public User getExecutor() {
        return this.executor;
    }

}
