package lab7.server.model;

public class NotEnoughRightsExceptions extends Exception {
    public NotEnoughRightsExceptions() {
        super("У пользователя недостаточно прав на выполнение данной операции");
    }
}
