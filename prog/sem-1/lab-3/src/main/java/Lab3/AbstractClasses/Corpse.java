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
        provokeReaction(h, ReactionType.CONFUSION);
        describeReaction(h, ReactionType.CONFUSION);
    }

    public void setDeathType(DeathType d) {
        this.deathType = d;
    }

    public DeathType getDeathType() {
        return deathType;
    }

    @Override
    public void provokeReaction(Reactionable r, ReactionType t) {
        r.setReaction(t);
    }

    @Override
    public void describeReaction(Reactionable r, ReactionType t, String provoker) {
        switch (t) {
            case CONFUSION:
                System.out.printf("%s приводит в недоумение: %s\n", provoker, r.toString());
                break;
        }
    }

    @Override
    public void describeReaction(Reactionable r, ReactionType t) {
        describeReaction(r, t, toString());
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
