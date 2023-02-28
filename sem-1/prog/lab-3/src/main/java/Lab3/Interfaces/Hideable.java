package Lab3.Interfaces;

public interface Hideable {
    static String[] getAbilities() {
        return new String[] {liftVeilOfSecrecy(), giveExplanation()};
    }

    static String liftVeilOfSecrecy() {
        return "приоткрыть ужасную завесу тайны";
    }

    static String giveExplanation() {
        return "дать объяснение бессвязным и непостижимым событиям";
    }
}
