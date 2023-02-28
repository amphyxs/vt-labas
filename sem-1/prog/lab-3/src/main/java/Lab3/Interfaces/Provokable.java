package Lab3.Interfaces;

import Lab3.Enums.ReactionType;

public interface Provokable {
    static void provokeReaction(Reactionable r, ReactionType t) {
        r.setReaction(t);
    }

    static void describeReaction(Reactionable r, ReactionType t, String provoker, boolean isSingle) {
        System.out.printf("%s %s: %s\n", provoker, t.getReactionDescription(isSingle), r.toString());
    }
}
