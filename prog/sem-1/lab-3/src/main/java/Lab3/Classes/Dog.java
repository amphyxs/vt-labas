package Lab3.Classes;

import Lab3.AbstractClasses.*;
import Lab3.Enums.*;

public class Dog extends AliveCreature {
    @Override
    public DogCorpse die(DeathType d) {
        // TODO
        return new DogCorpse(d, this);
    }

    @Override
    public String toString() {
        return "Собака";
    }
}
