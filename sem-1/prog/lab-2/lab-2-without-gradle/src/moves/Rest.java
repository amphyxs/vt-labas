package src.moves;

import ru.ifmo.se.pokemon.*;

public class Rest extends StatusMove {
    public Rest() {
        /*
        User sleeps for 2 turns, but user is fully healed.
        */
        super(Type.PSYCHIC, 0, 0);
    }

    protected void applySelfEffects(Pokemon pokemon) {
        pokemon.restore();
        pokemon.addEffect(new Effect().condition(Status.SLEEP).turns(2));
    }

    protected boolean checkAccuracy(Pokemon pokemon, Pokemon pokemon1) {
        return true;
    }

    protected String describe() {
        return "решил прилечь поспать";
    }
}
