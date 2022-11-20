package Lab3.Classes;

import Lab3.AbstractClasses.AliveCreature;
import Lab3.Enums.*;
import Lab3.Interfaces.*;

public class Mountains implements Provokable {
    String description;

    public void makeMad(Human h) {
        Provokable.provokeReaction(h, ReactionType.MADNESS);
        Provokable.describeReaction(h, ReactionType.MADNESS, toString(), false);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("%s горы", description);
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass())
            return false;
        return description.equals(((Mountains) obj).description);
    }
}
