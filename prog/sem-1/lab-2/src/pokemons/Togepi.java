package pokemons;

import ru.ifmo.se.pokemon.*;
import moves.*;

public class Togepi extends Pokemon {
    public Togepi(String name, int level) {
        super(name, level);
        setStats(35, 20, 65, 40, 65, 20);
        setType(Type.FAIRY);
        setMove(new Flamethrower(), new Rest());
    }
}
