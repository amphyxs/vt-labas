package Lab4.Model.Characters;

import Lab4.Model.Interfaces.*;
import Lab4.Model.Enums.*;
import Lab4.Model.Objects.*;
import Lab4.Model.Sentences.*;
import Lab4.Model.Exceptions.*;
import Lab4.Model.*;

public class Human extends AliveCreature implements Provokable {
    {
        this.repr = "человек";
    }

    public class HumanCorpse extends Corpse {
        public HumanCorpse(DeathType d) {
            super(d, Human.this);
        }

        @Override
        public String toString() {
            return "труп человека";
        }
    }
    
    public Human() {
        super();
    }

    public Human(String name) {
        super(name);
    }

    public void leaveAlone(Reactionable left, IPlace where) {
        StoryData.createSentence(SentenceType.ACTION, where, this, "оставляет одних", left);
        provokeReactionTo(ReactionType.LONELINESS, left, where);
    }

    public void countCorpses(int shouldBe, Corpse[] foundCorpses, IPlace where) throws NotMatchException {
        StoryData.createSentence(SentenceType.ACTION, where, this, "начинает считать трупы");
        if (shouldBe != foundCorpses.length)
            throw new NotMatchException(shouldBe, foundCorpses.length);
    }

    @Override
    public HumanCorpse die(DeathType d) {
        return new HumanCorpse(d);
    }
}
