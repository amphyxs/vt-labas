package src.pokemons;

import ru.ifmo.se.pokemon.*;
import src.moves.*;

public class Entei extends Pokemon {
    public Entei(String name, int level) {
        super(name, level);
        setStats(115, 115, 85, 90, 75, 100);
        setType(Type.FIRE);
        setMove(new Flamethrower(), new Leer(), new Swagger(), new FireFang());
    }
}
