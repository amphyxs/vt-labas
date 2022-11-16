package Lab3.Classes;

import Lab3.Enums.*;
import Lab3.Interfaces.*;

public class Mountains implements Provokable {
    private static Mountains instance;

    private Mountains() { }

    public void makeMad(Human h) {
        provokeReaction(h, ReactionType.MADNESS);
        describeReaction(h, ReactionType.MADNESS);
    }

    @Override
    public void provokeReaction(Reactionable r, ReactionType t) {
        r.setReaction(t);
    }

    @Override
    public void describeReaction(Reactionable r, ReactionType t, String provoker) {
        switch (t) {
            case MADNESS:
                System.out.printf("%s сводят с ума: %s\n", provoker, r.toString());
                break;
        }
    }

    @Override
    public void describeReaction(Reactionable r, ReactionType t) {
        describeReaction(r, t, toString());
    }

    public static Mountains getInstance() {
        if (instance == null)
            instance = new Mountains();
        return instance;
    }

    @Override
    public String toString() {
        return "Чертовы горы";
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return this.getClass() == obj.getClass();
    }
}
