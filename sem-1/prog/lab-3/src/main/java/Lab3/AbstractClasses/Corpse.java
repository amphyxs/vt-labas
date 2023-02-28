package Lab3.AbstractClasses;

import Lab3.Classes.*;
import Lab3.Enums.*;
import Lab3.Interfaces.*;

import java.util.Objects;

public abstract class Corpse implements Hideable, Provokable {
    protected DeathType deathType;
    protected AliveCreature body;

    public Corpse() {
        this(null, null);
    }

    public Corpse(AliveCreature body) {
        this(null, body);
    }

    public Corpse(DeathType deathType, AliveCreature body) {
        this.deathType = deathType;
        this.body = body;
    }

    public void confuse(Human h) {
        Provokable.provokeReaction(h, ReactionType.CONFUSION);
        Provokable.describeReaction(h, ReactionType.CONFUSION, toString(), true);
    }

    public void setDeathType(DeathType d) {
        this.deathType = d;
    }

    public DeathType getDeathType() {
        return deathType;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass())
            return false;
        return body.equals(((Corpse) obj).body);
    }

    @Override
    public int hashCode() {
        return super.hashCode() + this.deathType.hashCode();
    }
}
