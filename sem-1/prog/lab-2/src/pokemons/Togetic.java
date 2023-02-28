package pokemons;

import ru.ifmo.se.pokemon.*;
import moves.*;

public class Togetic extends Pokemon {
    public Togetic(String name, int level) {
        super(name, level);
        setStats(55, 40, 85, 80, 105, 40);
        setType(Type.FAIRY, Type.FLYING);
        setMove(new Flamethrower(), new Rest(), new SmartStrike());
    }
}
