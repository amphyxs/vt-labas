package Lab4.Model.Characters;

import Lab4.Model.Enums.*;
import Lab4.Model.Interfaces.*;
import Lab4.Model.Objects.*;
import Lab4.Model.Sentences.*;
import Lab4.Model.StoryData;

public class Dog extends AliveCreature implements Prisonable {
    {
        this.repr = "собака";
    }

    public class DogCorpse extends Corpse {
        public DogCorpse(DeathType deathType) {
            super(deathType, Dog.this);
        }

        @Override
        public String toString() {
            return "труп собаки";
        }
    }

    IPrison prison;

    @Override
    public DogCorpse die(DeathType d) {
        return new DogCorpse(d);
    }

    @Override
    public void setPrison(IPrison pr) {
        this.prison = pr;
        if (pr != null)
            pr.addPrisoner(this);
    }

    @Override
    public void escapeFromPrison() {
        StoryData.createSentence(SentenceType.ACTION, this.prison, this, "разрушает", this.prison);
        this.prison.removePrisoner(this);
    }
}
