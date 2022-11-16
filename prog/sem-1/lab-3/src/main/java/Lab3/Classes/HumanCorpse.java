package Lab3.Classes;

import Lab3.AbstractClasses.*;
import Lab3.Enums.*;

public class HumanCorpse extends Corpse {
    public HumanCorpse() {
        super();
    }

    public HumanCorpse(DeathType deathType, Human body) {
        super(deathType, body);
    }

    @Override
    public String toString() {
        return "Вид человечьих трупов";
    }
}
