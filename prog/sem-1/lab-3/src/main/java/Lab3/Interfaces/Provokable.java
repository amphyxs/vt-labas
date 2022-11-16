package Lab3.Interfaces;

import Lab3.Enums.ReactionType;

public interface Provokable {
    void provokeReaction(Reactionable r, ReactionType t);
    // TODO: this shouldn't be public
    void describeReaction(Reactionable r, ReactionType t);
    void describeReaction(Reactionable r, ReactionType t, String provoker);

}
