package moves;

import ru.ifmo.se.pokemon.*;

public class SmartStrike extends PhysicalMove {
    public SmartStrike() {
        /*
        The user stabs the target with a sharp horn. This attack never misses.
        */
        super(Type.STEEL, 70, 0);
    }

    protected boolean checkAccuracy(Pokemon pokemon, Pokemon pokemon1) {
        // Because *attack never misses*
        return true;
    }

    protected String describe() {
        return "ударяет острым рогом";
    }
}
