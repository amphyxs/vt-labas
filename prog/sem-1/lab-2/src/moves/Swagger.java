package moves;

import ru.ifmo.se.pokemon.*;

public class Swagger extends StatusMove {
    public Swagger() {
        /*
            Swagger confuses the target and raises its Attack by two stages.
            If one of the two effects cannot be invoked
            (for example the target is already confused or its Attack is already raised to the maximum of +6 stages),
            Swagger still works and will invoke the other effect.
        */
        super(Type.NORMAL, 0, 85);
    }

    protected void applyOppEffects(Pokemon pokemon) {
        Effect.confuse(pokemon);
        pokemon.setMod(Stat.ATTACK, 2);
    }

    protected String describe() {
        return "становится наглым";
    }
}
