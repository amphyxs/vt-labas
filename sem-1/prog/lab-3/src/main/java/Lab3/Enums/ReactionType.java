package Lab3.Enums;

public enum ReactionType {
    MADNESS {
        @Override
        public String getReactionDescription(boolean isSingle) {
            return isSingle ? "сводит с ума" : "сводят с ума";
        }
    },
    CONFUSION {
        @Override
        public String getReactionDescription(boolean isSingle) {
            return isSingle ? "приводит в недоумение" : "приводят в недоумение";
        }
    },
    PATHOLOGICAL_HATE {
        @Override
        public String getReactionDescription(boolean isSingle) {
            return isSingle ? "вызывает патологическую неприязнь" : "вызывают патологическую неприязнь";
        }
    },
    LONELINESS {
        @Override
        public String getReactionDescription(boolean isSingle) {
            return isSingle ? "оставляет в одиночестве" : "оставляют в одиночестве";
        }
    },
    FEAR {
        @Override
        public String getReactionDescription(boolean isSingle) {
            return isSingle ? "пугает" : "пугают";
        }
    },
    DISQUIETUDE {
        @Override
        public String getReactionDescription(boolean isSingle) {
            return isSingle ? "вызывает беспокойство" : "вызывают беспокойство";
        }
    };

    public abstract String getReactionDescription(boolean isSingle);
}
