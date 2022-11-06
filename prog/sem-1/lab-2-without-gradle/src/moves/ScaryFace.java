package src.moves;

import ru.ifmo.se.pokemon.*;

public class ScaryFace extends StatusMove {
    public ScaryFace() {
        /*
        Scary Face lowers the target's Speed by two stages.
        */
        super(Type.NORMAL, 0, 100);
    }

    protected void applyOppEffects(Pokemon pokemon) {
        pokemon.setMod(Stat.SPEED, -2);
    }

    protected String describe() {
        return "пугает своим страшным лицом";
    }
}
