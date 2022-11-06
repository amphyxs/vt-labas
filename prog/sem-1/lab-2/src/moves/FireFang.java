package src.moves;

import ru.ifmo.se.pokemon.*;

public class FireFang extends PhysicalMove {
    public FireFang() {
        /*
        Fire Fang deals damage, has a 10% chance of burning the target and has a 10% chance of causing the target
        to flinch (if the target has not yet moved).
        */
        super(Type.FIRE, 65, 95);
    }

    protected void applyOppEffects(Pokemon pokemon) {
        pokemon.addEffect(new Effect().chance(0.1).condition(Status.BURN));
        // Dummy 10% chance check
        if (Math.random() <= 0.1)
            Effect.flinch(pokemon);
    }

    protected String describe() {
        return "кусает клыком огня";
    }
}
