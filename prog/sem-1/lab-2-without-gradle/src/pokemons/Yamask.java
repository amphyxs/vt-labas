package src.pokemons;

import ru.ifmo.se.pokemon.*;
import src.moves.*;

public class Yamask extends Pokemon {
    public Yamask(String name, int level) {
        super(name, level);
        setStats(38, 30, 85, 55, 65, 30);
        setType(Type.GHOST);
        setMove(new WillOWisp(), new Confide(), new Swagger());
    }
}
