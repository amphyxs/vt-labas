package Lab3.Classes;

import java.util.LinkedHashSet;
import Lab3.AbstractClasses.*;
import Lab3.Enums.*;

public class Human extends AliveCreature {
    public Human() {
        super();
    }

    public Human(String name) {
        super(name);
    }

    public void leaveAlone(AliveCreature d) {
        System.out.printf("%s оставили в одиночестве: %s\n", toString(), d.toString());
    }

    @Override
    public HumanCorpse die(DeathType d) {
        // TODO
        return new HumanCorpse(d, this);
    }

    @Override
    public String toString() {
        return "Любой человек";
    }
}
