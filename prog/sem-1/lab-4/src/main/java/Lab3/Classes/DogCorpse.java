package Lab3.Classes;

import Lab3.AbstractClasses.*;
import Lab3.Enums.*;

public class DogCorpse extends Corpse {
    public DogCorpse() {
        super();
    }

    public DogCorpse(DeathType deathType, Dog body) {
        super(deathType, body);
    }

    @Override
    public String toString() {
        return "Вид собачьих трупов";
    }
}
