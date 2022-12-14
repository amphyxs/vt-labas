package Lab4.Model.Objects;

import java.util.ArrayList;
import java.util.List;

import Lab4.Model.Interfaces.*;
import Lab4.Model.*;
import Lab4.Model.Sentences.*;

public abstract class PaddockFactory {
    public static class Paddock implements IPrison {
        private List<Prisonable> prisoners = new ArrayList<Prisonable>();

        @Override
        public void addPrisoner(Prisonable prisoner) {
            if (this.prisoners.contains(prisoner))
                return;
            this.prisoners.add(prisoner);
            prisoner.setPrison(this);
        }
        @Override
        public void removePrisoner(Prisonable prisoner) {
            this.prisoners.remove(prisoner);
            prisoner.setPrison(null);
        }

        @Override
        public int hashCode() {
            return super.hashCode() + prisoners.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || obj.getClass() != getClass())
                return false;
            return prisoners.equals(((Paddock) obj).prisoners);
        }

        @Override
        public String toString() {
            return "нанадёжный загончик";
        }
    }

    public static Paddock buildPaddock(IPlace where, Prisonable... prisoners) {
        Paddock p = new Paddock();
        StoryData.createSentence(SentenceType.ACTION, where, p, "пришлось городить");
        for (Prisonable pr : prisoners) {
            p.addPrisoner(pr);
            StoryData.createSentence(SentenceType.ACTION, where, pr, "посажен в", p);
        }
        return p;
    }
}
