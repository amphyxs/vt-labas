package Lab4.Model.Objects;

import Lab4.Model.Characters.*;
import Lab4.Model.Interfaces.*;
import Lab4.Model.Enums.*;

public abstract class Corpse implements Hideable, Provokable {
    private DeathType deathType;
    private AliveCreature body;

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

    public void confuse(Reactionable target, IPlace where) {
        provokeReactionTo(ReactionType.CONFUSION, target, where);
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

    @Override
    public String toString() {
        return "труп" + " " + body.toString();
    }
}
