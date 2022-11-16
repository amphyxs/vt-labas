package Lab3;

import Lab3.Classes.Human;
import Lab3.Classes.*;

public class Main {
    public static void main(String[] args) {
        Narrator n = new Narrator();
        n.setObjectsToHide(new HumanCorpse(), new DogCorpse(), DelicateThings.getInstance());
        n.describeObjectsToHide();

        n.addFriends(new Friend("Джон"), new Friend("Майк"), new Friend("Сэм"));
        n.distractFriendsAttention();

        Mountains m = Mountains.getInstance();
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
        };
        MysteriousAncientCreature creature = new MysteriousAncientCreature();
        DogCorpse dogsCorpses = (DogCorpse) creature.kill(dogs);
        HumanCorpse peopleCorpses = (HumanCorpse) creature.kill(people);

        n.analyseCorpses(dogsCorpses, peopleCorpses);

        n.beginBackstory();
        creature.watchDogReaction(dogs);
        UnreliablePaddock paddock = new UnreliablePaddock();
        paddock.build();
        paddock.setDogs(new Dog[] {dogs});
        n.say("Однако все предосторожности оказались тщетными");
        people.leaveAlone(dogs);
        HurricaneWind wind = HurricaneWind.getInstance();
        wind.startBlowing();
        wind.frighten(dogs);
        creature.disturbBySmell(dogs);
        paddock.deleteDog(dogs);
    }
}
