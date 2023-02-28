package Lab4.Model;

import Lab4.Model.Interfaces.*;
import Lab4.Model.Enums.*;
import Lab4.Model.Objects.*;
import Lab4.Model.Sentences.*;
import Lab4.Model.Exceptions.*;
import Lab4.Model.Characters.*;
import Lab4.Model.*;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StoryData {
    private static List<Presentable> sentences = new ArrayList<Presentable>();

    public static void createSentence(SentenceType type, IPlace place, Object character, String description, Object... targets) {
        StoryData.sentences.add(new StorySentence(type, place, character, description, targets));
    }

    public static List<Presentable> getSentences() {
        setup();
        return sentences;
    }

    private static void setup() {
        Narrator n = new Narrator();
        class Camp implements IPlace {
            @Override
            public String toString() {
                return "лагерь Лейка";
            }
        }
        class ThinkBack implements Provokable {

            private IPlace to;

            public ThinkBack(IPlace to) {
                this.to = to;
            }

            public void watchReactionOnThinkBack(Reactionable thinker) {
                provokeReactionTo(ReactionType.HARDNESS, thinker, to);
            }

            @Override
            public String toString() {
                return "мысленное возвращение";
            }
        }
        Camp camp = new Camp();
        ThinkBack backToCamp = new ThinkBack(camp);
        backToCamp.watchReactionOnThinkBack(n);

        class MadnessHills implements IPlace {
            @Override
            public String toString() {
                return "хребты Безумия";
            }
        }
        MadnessHills hills = new MadnessHills();
        n.tellWhatSaw(hills);

        n.describeTalking(TellingWay.values());

        n.tellSaidEnough();

        n.describeMainThing(new Object() {
            @Override
            public String toString() {
                return "ужас, который охватил нас";
            }
        }, camp);

        class Rocks {
            Object whoCrashed;

            public Rocks(Object whoCrashed) {
                this.whoCrashed = whoCrashed;
            }

            @Override
            public String toString() {
                return "сокрушённые ветром скалы";
            }
        }
        class Shelters {
            @Override
            public String toString() {
                return "развороченные укрытия";
            }
        }
        class Cars {
            @Override
            public String toString() {
                return "приведённые в негодность машины";
            }
        }
        class DogsDisquietude {
            @Override
            public String toString() {
                return "странное беспокойство собак";
            }
        }
        class Sleigh {
            @Override
            public String toString() {
                return "пропавшие сани";
            }
        }
        class Death {
            Object[] whoDied;

            public Death(Object... died) {
                this.whoDied = died;
            }

            @Override
            public String toString() {
                String diedString = Arrays.stream(this.whoDied).map(Object::toString).collect(Collectors.joining(" и "));
                return String.format("смерть наших %s", diedString);
            }
        }
        class Disappearing {
            Object whoDisappered;

            public Disappearing(Object whoDisappeared) {
                this.whoDisappered = whoDisappeared;
            }

            @Override
            public String toString() {
                return String.format("исчезновение %s", whoDisappered);
            }
        }
        class Creature {
            int number;
            String feature;
            String featureReason;

            public Creature(int number, String feature, String featureReason) {
                this.number = number;
                this.feature = feature;
                this.featureReason = featureReason;
            }

            @Override
            public String toString() {
                return String.format("%d ненормально захороненных тварей, обладавших %s, особенно если учесть, что %s", number, feature, featureReason);
            }
        }
        n.describeAlreadyTold(
                new Rocks(new HurricaneWind()),
                new Shelters(),
                new Cars(),
                new DogsDisquietude(),
                new Sleigh(),
                new Death(new Human(), new Dog()),
                new Disappearing(new Human("Гедни")),
                new Creature(6, "необычно плотным строением ткани", "они пролежали 40 млн лет в земле")
        );

        Human[] alive = {new Human(), new Human(), new Human(), new Human()};
        Corpse[] dead = Arrays.stream(alive).map((h) -> h.die(DeathType.STRANGLED)).toArray(Corpse[]::new);
        try {
            n.countCorpses(5, dead, camp);
            StoryData.createSentence(SentenceType.PHRASE, camp, n, "мы нашли ровно столько трупов, сколько должно быть");
        } catch (NotMatchException e) {
            StoryData.createSentence(SentenceType.PHRASE, camp, n, e.getMessage());
        }

        Human manDenfort = new Human("Денфорт");
        Human everybody = new Human();
        everybody.setRepr("все");
        everybody.forget("неверное число трупов", camp);
        n.recall("неверное число трупов", camp);
        manDenfort.recall("неверное число трупов", camp);

        DelicateThings delicateThings = new DelicateThings();
        try {
            delicateThings.liftVeilOfSecrecy();
        } catch (SecrecyRevealedException e) {
            StoryData.createSentence(SentenceType.PHRASE, camp, n, e.getMessage());
        }
        try {
            dead[0].liftVeilOfSecrecy();
        } catch (SecrecyRevealedException e) {
            StoryData.createSentence(SentenceType.PHRASE, camp, n, e.getMessage());
        }

        Corpse randomHumanCorpse = (new Human()).die(null);
        Corpse randomDogCorpse = (new Dog()).die(null);
        n.hideObjects(camp, randomHumanCorpse, randomDogCorpse, new DelicateThings());

        String[] narratorsFriends = new String[] {"Денфорт", "Сэм", "Джон"};
        for (String name : narratorsFriends)
            n.addFriend(name);
        n.distractFriendsAttention("всех этих несоответствий", "стихийной вспышке безумия", camp);

        Mountains mountains = new Mountains("чёртовы");
        mountains.makeMad(new Reactionable() {
            @Override
            public String toString() {
                return "кого угодно";
            }
        }, hills);

        randomHumanCorpse.confuse(n, camp);
        randomDogCorpse.confuse(n, camp);

        Human people = new Human();
        people.setRepr("люди");
        Dog dogs = new Dog();
        dogs.setRepr("собаки");
        MysteriousAncientCreature creature = new MysteriousAncientCreature();
        Corpse peopleCorpses = creature.kill(people, camp);
        Corpse dogsCorpses = creature.kill(dogs, camp);

        n.describeCorpses(camp, peopleCorpses, dogsCorpses);

        n.beginBackstory();
        PaddockFactory.Paddock paddock = PaddockFactory.buildPaddock(camp, dogs);
        creature.watchDogReaction(dogs, paddock);

        n.tellPrecautionWereUseless();

        people.leaveAlone(dogs, paddock);
        HurricaneWind wind = new HurricaneWind();
        wind.startBlowing(paddock);
        wind.frighten(dogs, paddock);
        creature.disturbBySmell(dogs, paddock);
        dogs.escapeFromPrison();
    }
}
