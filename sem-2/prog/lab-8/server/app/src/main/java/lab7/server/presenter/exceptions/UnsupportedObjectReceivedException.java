package lab7.server.presenter.exceptions;

public class UnsupportedObjectReceivedException extends Exception {
    
    public UnsupportedObjectReceivedException(String objType) {
        super(String.format("Получен неподдерживаемый объект: %s", objType));
    }
    
}
