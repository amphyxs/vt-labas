package Lab4.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EventHandler {
    private Object operator;
    private Method handler;

    public EventHandler(Object operator, Method handler) {
        this.operator = operator;
        this.handler = handler;
    }

    public void handleEvent() {
        try {
            handler.invoke(operator);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            System.out.println("Error in event handler: \n" + e.getMessage());
        }
    }
}
