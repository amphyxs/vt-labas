package lab6.server.Presenter.Exceptions;

public class UnsupportedObjectReceivedException extends Exception {
    
    public UnsupportedObjectReceivedException(String objType) {
        super(String.format("Получен неподдерживаемый объект: %s", objType));
    }
    
}
