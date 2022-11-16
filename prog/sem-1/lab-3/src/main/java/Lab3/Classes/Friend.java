package Lab3.Classes;

public class Friend extends Human {
    public Friend() {
        super();
    }

    public Friend(String name) {
        super(name);
    }

    public void distractSelfAttention() {
        System.out.printf("%s теперь думает, что всё из-за стихийной вспышки безумия в лагере Лейка\n", toString());
    }

    @Override
    public String toString() {
        return String.format("Товарищ рассказчика %s", this.name);
    }
}
