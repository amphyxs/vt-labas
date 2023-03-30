package lab6.serializedobjects;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;


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
