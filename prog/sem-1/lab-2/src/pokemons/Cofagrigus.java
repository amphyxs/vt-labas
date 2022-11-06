package src.pokemons;

import ru.ifmo.se.pokemon.*;
import src.moves.*;

public class Cofagrigus extends Pokemon {

    public Cofagrigus(String name, int level) {
        super(name, level);
        setStats(58, 50, 145, 95, 105, 30);
        setType(Type.GHOST);
        setMove(new WillOWisp(), new Confide(), new Swagger(), new ScaryFace());
    }
}
