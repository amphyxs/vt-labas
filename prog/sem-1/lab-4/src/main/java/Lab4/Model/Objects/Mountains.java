package Lab4.Model.Objects;

import Lab4.Model.Interfaces.*;
import Lab4.Model.Enums.*;
import Lab4.Model.Sentences.*;

public class Mountains implements Provokable, IPlace {
    private String description;

    public Mountains(String description) {
        this.description = description;
    }

    public void makeMad(Reactionable target, IPlace where) {
        provokeReactionTo(ReactionType.MADNESS, target, where);
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public String toString() {
        return String.format("%s горы", description);
    }
}
