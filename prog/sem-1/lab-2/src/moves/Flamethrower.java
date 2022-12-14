package moves;

import ru.ifmo.se.pokemon.*;

public class Flamethrower extends SpecialMove {
    public Flamethrower() {
        /*
        Flamethrower deals damage and has a 10% chance of burning the target.
        */
        super(Type.FIRE, 90, 100);
    }

    protected void applyOppEffects(Pokemon pokemon) {
        pokemon.addEffect(new Effect().chance(0.1).condition(Status.BURN));
    }

    protected String describe() {
        return "поджигает";
    }
}
