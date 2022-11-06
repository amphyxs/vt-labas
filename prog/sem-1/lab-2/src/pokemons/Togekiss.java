package src.pokemons;

import ru.ifmo.se.pokemon.*;
import src.moves.*;

public class Togekiss extends Pokemon {
    public Togekiss(String name, int level) {
        super(name, level);
        setStats(85, 50, 95, 120, 115, 80);
        setType(Type.FAIRY, Type.FLYING);
        setMove(new Flamethrower(), new Rest(), new SmartStrike(), new WorkUp());
    }
}
