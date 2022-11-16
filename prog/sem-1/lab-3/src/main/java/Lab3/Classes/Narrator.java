package Lab3.Classes;

import java.util.LinkedHashSet;

import Lab3.AbstractClasses.Corpse;
import Lab3.Interfaces.*;

public class Narrator extends Human {
    private Hideable[] objectsToHide;
    private Friend[] friends;

    public void setObjectsToHide(Hideable... h) {
        this.objectsToHide = h;
    }

    public void describeObjectsToHide() {
        LinkedHashSet<String> goingToHide = new LinkedHashSet<String>();
        for (Hideable h : this.objectsToHide)
            goingToHide.add(h.toString());

        say(
                String.format("Основное, что я стремился утаить: %s.\nОни, возможно, могли: %s",
                        String.join(", ", goingToHide),
                        String.join(", ", Hideable.getAbilities())
                )
        );
    }

    public void addFriends(Friend... friends) {
        this.friends = friends;
    }

    public Friend[] getFriends() {
        return this.friends;
    }

    public void analyseCorpses(Corpse... corpses) {
        LinkedHashSet<String> deathTypes = new LinkedHashSet<String>();
        for (Corpse c : corpses)
            deathTypes.add(c.getDeathType().toString());
        say(
                String.format(
                    "Насколько мы могли судить, все эти трупы были убиты одним из следующих образов: %s",
                    String.join(", ", deathTypes)
                )
        );
    }

    public void distractFriendsAttention() {
        System.out.printf("%s старается отвлечь внимание своих товарищей от всех этих несоответствий\n", toString());
        for (Friend f : this.friends)
            f.distractSelfAttention();
    }

    public void say(String s) {
        System.out.printf("%s говорит: %s\n", toString(), s);
    }

    public void beginBackstory() {
        System.out.printf("%s начинает рассказывать предысторию\n", toString());
    }

    @Override
    public String toString() {
        return "Рассказчик";
    }
}
