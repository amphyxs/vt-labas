package Lab3.AbstractClasses;

import Lab3.Enums.*;
import Lab3.Interfaces.*;

public abstract class AliveCreature implements Killable, Reactionable, Nameable {
    protected ReactionType currentReaction = null;
    protected String name;

    public AliveCreature() {
        // TODO: auto counting
        this("Безымянный");
    }

    public AliveCreature(String name) {
        this.name = name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void defendSelf(Nameable enemy) {
        System.out.printf("%s %s защищается от %s", toString(), getName(), enemy.getName());
    }

    @Override
    public void setReaction(ReactionType r) {
        this.currentReaction = r;
    }

    @Override
    public ReactionType getReaction() {
        return this.currentReaction;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass())
            return false;
        return this.name.equals(((AliveCreature) obj).name);
    }
}
