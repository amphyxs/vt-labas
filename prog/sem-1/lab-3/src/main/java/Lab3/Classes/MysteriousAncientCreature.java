package Lab3.Classes;

import Lab3.AbstractClasses.*;
import Lab3.Enums.*;
import Lab3.Interfaces.*;

public class MysteriousAncientCreature implements Provokable {
    public Corpse kill(Killable k) {
        crippleBody(k);
        mangleBody(k);
        return k.die(k instanceof Dog ? DeathType.STRANGLED : DeathType.TEARED_INTO_PIECES);
    }

    public void watchDogReaction(Dog d) {
        provokeReaction(d, ReactionType.PATHOLOGICAL_HATE);
        describeReaction(d, ReactionType.PATHOLOGICAL_HATE);
    }

    public void disturbBySmell(Reactionable r) {
        provokeReaction(r, ReactionType.DISQUIETUDE);
        describeReaction(r, ReactionType.DISQUIETUDE, "Источаемый кошмарными тварями едкий запах");
    }

    protected void crippleBody(Killable k) {
        System.out.printf("%s калечит тело: %s\n", toString(), k.toString());
    }

    protected  void mangleBody(Killable k) {
        System.out.printf("%s кромсает тело: %s\n", toString(), k.toString());
    }

    @Override
    public void provokeReaction(Reactionable r, ReactionType t) {
        r.setReaction(t);
    }

    @Override
    public void describeReaction(Reactionable r, ReactionType t, String provoker) {
        switch (t) {
            case PATHOLOGICAL_HATE:
                System.out.printf("%s вызывает патологическую неприязнь у: %s\n", provoker, r.toString());
                break;
            case DISQUIETUDE:
                System.out.printf("%s вызывает беспокойство у: %s\n", provoker, r.toString());
                break;
        }
    }

    @Override
    public void describeReaction(Reactionable r, ReactionType t) {
        describeReaction(r, t, toString());
    }

    @Override
    public String toString() {
        return "Загадочный древний организм";
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
