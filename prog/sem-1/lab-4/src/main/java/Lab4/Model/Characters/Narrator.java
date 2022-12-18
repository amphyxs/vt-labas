package Lab4.Model.Characters;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

import Lab4.Model.Interfaces.*;
import Lab4.Model.Enums.*;
import Lab4.Model.Objects.*;
import Lab4.Model.Sentences.*;
import Lab4.Model.*;

import javax.naming.Name;

public class Narrator extends Human {
    {
        this.repr = "рассказчик";
    }

    static class Friend extends Human {
        {
            this.repr = "товарищ рассказчика";
        }

        public Friend() {
            super();
        }

        public Friend(String name) {
            super(name);
        }

        public void distractSelfAttention(String newInfo, IPlace where) {
            StoryData.createSentence(SentenceType.ACTION, where, this, "теперь думает, что всё из-за", newInfo);
        }
    }
    private List<Friend> friends = new ArrayList<Friend>();

    public void hideObjects(IPlace where, Hideable... objectsToHide) {
        StoryData.createSentence(SentenceType.ACTION, where, this, "стремится утаить", objectsToHide);
    }

    public void addFriend(String name) {
        this.friends.add(new Friend(name));
    }

    public Friend[] getFriends() {
        return this.friends.toArray(new Friend[0]);
    }

    public void describeCorpses(IPlace where, Corpse... corpses) {
        LinkedHashSet<String> deathTypes = new LinkedHashSet<String>();
        for (Corpse c : corpses)
            deathTypes.add(c.getDeathType().toString());
        StoryData.createSentence(SentenceType.PHRASE, where, this,
                "насколько мы могли судить, все эти трупы были убиты одним из следующих образов:",
                String.join(", ", deathTypes)
        );
    }

    public void distractFriendsAttention(String from, String to, IPlace where) {
        StoryData.createSentence(SentenceType.ACTION, where, this, "старается отвлечь внимание своих товарищей от", from);
        for (Friend f : this.friends)
            f.distractSelfAttention(to, where);
    }

    public void beginBackstory() {
        StoryData.createSentence(SentenceType.ACTION, null, this, "начинает рассказывать предысторию");
    }

    public void tellWhatSaw(IPlace... places) {
        StoryData.createSentence(SentenceType.PHRASE, null, this, "надо наконец откровенно рассказать, что же мы в действительности увидели в", places);
    }

    public void describeTalking(TellingWay... ways) {
        String waysString = Arrays.stream(ways).map(TellingWay::toString).collect(Collectors.joining("; "));
        StoryData.createSentence(SentenceType.PHRASE, null, this, String.format("ловлю себя на постоянном искушении - хочется: %s", waysString));
    }

    public void tellSaidEnough() {
        StoryData.createSentence(SentenceType.PHRASE, null, this, "думаю, я и так уже многое сказал, теперь нужно только заполнить лакуны");
    }

    public void describeMainThing(Object mainThing, IPlace where) {
        StoryData.createSentence(SentenceType.PHRASE, where, this, "главное", mainThing);
    }

    public void describeAlreadyTold(Object... told) {
        StoryData.createSentence(SentenceType.PHRASE, null, this, "я уже рассказывал про", told);
    }

    public void tellPrecautionWereUseless() {
        StoryData.createSentence(SentenceType.PHRASE, null, this, "однако все предосторожности оказались тщетными");
    }
}
