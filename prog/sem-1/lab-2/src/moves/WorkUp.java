package src.moves;

import ru.ifmo.se.pokemon.*;

public class WorkUp extends StatusMove {
    public WorkUp() {
        /*
        Work Up raises the user's Attack and Special Attack by one stage each.
        */
        super(Type.NORMAL, 0, 0);
    }

    protected void applySelfEffects(Pokemon pokemon) {
        pokemon.setMod(Stat.ATTACK, 1);
        pokemon.setMod(Stat.SPECIAL_ATTACK, 1);
    }

    protected boolean checkAccuracy(Pokemon pokemon, Pokemon pokemon1) {
        return true;
    }

    protected String describe() {
        return "получает мотивацию";
    }
}
