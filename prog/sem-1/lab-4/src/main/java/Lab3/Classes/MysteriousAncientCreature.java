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
        Provokable.provokeReaction(d, ReactionType.PATHOLOGICAL_HATE);
        Provokable.describeReaction(d, ReactionType.PATHOLOGICAL_HATE, toString(), true);
    }

    public void disturbBySmell(Dog r) {
        Provokable.provokeReaction(r, ReactionType.DISQUIETUDE);
        Provokable.describeReaction(r, ReactionType.DISQUIETUDE, toString(), true);
    }

    protected void crippleBody(Killable k) {
        System.out.printf("%s калечит тело: %s\n", toString(), k.toString());
    }

    protected  void mangleBody(Killable k) {
        System.out.printf("%s кромсает тело: %s\n", toString(), k.toString());
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
