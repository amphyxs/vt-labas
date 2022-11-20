package Lab3.Classes;

import java.util.LinkedHashSet;
import Lab3.AbstractClasses.*;
import Lab3.Enums.*;
import Lab3.Interfaces.*;

public class Human extends AliveCreature implements Provokable {
    public Human() {
        super();
    }

    public Human(String name) {
        super(name);
    }

    public void leaveAlone(Dog d) {
        Provokable.provokeReaction(d, ReactionType.LONELINESS);
        Provokable.describeReaction(d, ReactionType.LONELINESS, toString(), true);
    }

    @Override
    public HumanCorpse die(DeathType d) {
        return new HumanCorpse(d, this);
    }

    @Override
    public String toString() {
        return "Любой человек";
    }
}
