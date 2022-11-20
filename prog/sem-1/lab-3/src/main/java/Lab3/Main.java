package Lab3;

import Lab3.Classes.Human;
import Lab3.Classes.*;
import Lab3.Enums.ReactionType;
import Lab3.Interfaces.Provokable;

public class Main {
    public static void main(String[] args) {
        Narrator n = new Narrator();
        n.setObjectsToHide(new HumanCorpse(), new DogCorpse(), new DelicateThings());

        n.describeObjectsToHide();

        n.addFriends(new Friend("Джон"), new Friend("Майк"), new Friend("Сэм"));
        n.distractFriendsAttention();

        Mountains m = new Mountains();
        m.setDescription("Чертовы");
        m.makeMad(new Human());

        HumanCorpse deadHuman = new HumanCorpse();
        DogCorpse deadDog = new DogCorpse();
        deadHuman.confuse(n);
        deadDog.confuse(n);

        Dog dogs = new Dog() {
            @Override
            public String toString() {
                return "Собаки";
            }
        };
        Human people = new Human() {
            @Override
            public String toString() {
                return "Люди";
            }

            @Override
            public void leaveAlone(Dog d) {
                Provokable.provokeReaction(d, ReactionType.LONELINESS);
                Provokable.describeReaction(d, ReactionType.LONELINESS, toString(), false);
            }
        };
        MysteriousAncientCreature creature = new MysteriousAncientCreature();
        DogCorpse dogsCorpses = (DogCorpse) creature.kill(dogs);
        HumanCorpse peopleCorpses = (HumanCorpse) creature.kill(people);

        n.analyseCorpses(dogsCorpses, peopleCorpses);

        n.beginBackstory();
        creature.watchDogReaction(dogs);
        UnreliablePaddock paddock = new UnreliablePaddock();
        paddock.build();
        paddock.addDogs(dogs);
        n.say("Однако все предосторожности оказались тщетными");
        people.leaveAlone(dogs);
        HurricaneWind wind = new HurricaneWind();
        wind.setPower(10);
        wind.startBlowing();
        wind.frighten(dogs);
        creature.disturbBySmell(dogs);
        paddock.deleteDog(dogs);
    }
}

// Преподаватель сказал сделать это:
// TODO: интерфейсов должно быть больше, чем классов (где-то на 20% больше)
// TODO: MVC
// TODO: Google Guice
