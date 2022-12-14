package Lab4.Model.Interfaces;

import Lab4.Model.Enums.*;
import Lab4.Model.Sentences.*;
import Lab4.Model.*;

public interface Provokable {
    default void provokeReactionTo(ReactionType reaction, Reactionable target, IPlace where) {
        StoryData.createSentence(SentenceType.REACTION, where, this, reaction.toString(), target);
    }
}
