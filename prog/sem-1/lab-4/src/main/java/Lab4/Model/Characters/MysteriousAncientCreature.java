package Lab4.Model.Characters;

import Lab4.Model.Interfaces.*;
import Lab4.Model.Enums.*;
import Lab4.Model.Objects.*;
import Lab4.Model.Sentences.*;
import Lab4.Model.StoryData;

public class MysteriousAncientCreature implements Provokable, IKiller {

    public Corpse kill(Killable target, IPlace where) {
        target.defendSelf(this, where);
        crippleBody(target, where);
        mangleBody(target, where);
        StoryData.createSentence(SentenceType.ACTION, where, this, "убивает", target);
        return target.die(target instanceof Dog ? DeathType.STRANGLED : DeathType.TEARED_INTO_PIECES);
    }

    public void watchDogReaction(Dog target, IPlace where) {
        provokeReactionTo(ReactionType.PATHOLOGICAL_HATE, target, where);
    }

    public void disturbBySmell(Reactionable target, IPlace where) {
        provokeReactionTo(ReactionType.DISQUIETUDE, target, where);
    }

    public void crippleBody(Killable target, IPlace where) {
        StoryData.createSentence(SentenceType.ACTION, where, this, "калечит тело", target);
    }

    public void mangleBody(Killable target, IPlace where) {
        StoryData.createSentence(SentenceType.ACTION, where, this, "кромсает тело", target);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "загадочный древний организм";
    }
}
