package src.moves;

import ru.ifmo.se.pokemon.*;

public class Confide extends StatusMove {
    public Confide() {
        /*
        Confide lowers the target's Special Attack by one stage.
        */
        super(Type.NORMAL, 0, 0);
    }

    protected void applyOppEffects(Pokemon pokemon) {
        pokemon.setMod(Stat.SPECIAL_ATTACK, -1);
    }

    protected String describe() {
        return "делится своими переживаниями";
    }
}
