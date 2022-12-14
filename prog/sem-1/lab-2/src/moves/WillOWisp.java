package moves;

import ru.ifmo.se.pokemon.*;

public class WillOWisp extends SpecialMove {
    public WillOWisp() {
        /*
        Will-O-Wisp causes the target to become burned, if it hits.
        */
        super(Type.FIRE, 0, 75);
    }

    protected void applyOppEffects(Pokemon pokemon) {
        Effect.burn(pokemon);
    }

    protected String describe() {
        return "становится огненным призраком";
    }
}
