package src.moves;

import ru.ifmo.se.pokemon.*;

public class Leer extends StatusMove {
    public Leer() {
        /*
            Leer lowers the target's Defense by one stage.
            Stats can be lowered to a minimum of -6 stages each.
        */
        super(Type.NORMAL, 0, 100);
    }

    protected void applyOppEffects(Pokemon pokemon) {
        pokemon.setMod(Stat.DEFENSE, -1);
    }

    protected String describe() {
        return "зловеще смотрит";
    }
}
