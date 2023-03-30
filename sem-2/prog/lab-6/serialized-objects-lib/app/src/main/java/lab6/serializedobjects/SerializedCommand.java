package lab6.serializedobjects;

import java.io.Serializable;
import java.util.Map;


public class SerializedCommand implements Serializable {

    public String name;
    public Map<String, String> args;
    
    public SerializedCommand(String name, Map<String, String> args) {
        this.name = name; 
        this.args = args;
    }

    public String getName() {
        return this.name;
    }

    public Map<String, String> getArgs() {
        return this.args;
    }

}
