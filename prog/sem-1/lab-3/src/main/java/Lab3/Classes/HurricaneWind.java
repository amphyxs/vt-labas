package Lab3.Classes;

import Lab3.Enums.*;
import Lab3.Interfaces.*;

public class HurricaneWind implements Provokable {
    private static HurricaneWind instance;

    private HurricaneWind() { }

    public void startBlowing() {
        System.out.printf("%s начинает свои первые яростные порывы\n", toString());
    }

    public void frighten(Reactionable d) {
        provokeReaction(d, ReactionType.FEAR);
        describeReaction(d, ReactionType.FEAR);
    }

    @Override
    public void provokeReaction(Reactionable r, ReactionType t) {
        r.setReaction(t);
    }

    @Override
    public void describeReaction(Reactionable r, ReactionType t, String provoker) {
        switch (t) {
            case FEAR:
                System.out.printf("%s испугал: %s\n", provoker, r.toString());
                break;
        }
    }

    @Override
    public void describeReaction(Reactionable r, ReactionType t) {
        describeReaction(r, t, toString());
    }

    public static HurricaneWind getInstance() {
        if (instance == null)
            instance = new HurricaneWind();
        return instance;
    }

    @Override
    public String toString() {
        return "Ураганный ветер";
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return getClass() == obj.getClass();
    }
}
