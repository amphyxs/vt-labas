import ru.ifmo.se.pokemon.*;
import pokemons.*;

public class Main {
    public static void main(String[] args) {
        Battle b = new Battle();

        Pokemon[] allies = {
                new Togepi("Eggman", 5),
                new Togetic("Chad Eggman", 8),
                new Togekiss("Flying Gigachad Eggman", 1)
        };
        for (Pokemon p : allies)
            b.addAlly(p);

        Pokemon[] foes = {
                new Entei("Doge", 1),
                new Cofagrigus("Prizrachniy Gonchik", 3),
                new Yamask("Casper the Ghost", 5)
        };
        for (Pokemon p : foes)
            b.addFoe(p);

        b.go();
    }
}