package Lab4.Model.Exceptions;

public class NotMatchException extends Exception {
    public NotMatchException(int shouldBe, int found) {
        super(String.format("Внимание! Количество найденных трупов (%d) не совпадает c известным (%d).", found, shouldBe));
    }
}
