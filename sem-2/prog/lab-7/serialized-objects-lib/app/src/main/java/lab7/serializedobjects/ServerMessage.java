package lab7.serializedobjects;

import java.io.Serializable;


public class ServerMessage implements Serializable {
    
    public MessagesType type;
    public String text;

    public ServerMessage(MessagesType type, String text) {
        this.type = type;
        this.text = text;
    }

    public MessagesType getType() {
        return this.type;
    }

    public String getText() {
        return this.text;
    }
}
