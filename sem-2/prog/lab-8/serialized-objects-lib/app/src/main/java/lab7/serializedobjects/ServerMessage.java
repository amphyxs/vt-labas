package lab7.serializedobjects;

import java.io.Serializable;


public class ServerMessage implements Serializable {
    
    private MessagesType type;
    private String text;
    private Serializable[] data;

    public ServerMessage(MessagesType type, String text, Serializable[] data) {
        this.type = type;
        this.text = text;
        this.data = data;
    }
    
    public ServerMessage(MessagesType type, String text) {
        this(type, text, null);
    }

    public ServerMessage(MessagesType type, Serializable[] data) {
        this(type, null, data);
    }

    public MessagesType getType() {
        return this.type;
    }

    public String getText() {
        return this.text;
    }

    public Object[] getData() {
        return this.data;
    }
}
