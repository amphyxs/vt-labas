package Lab4.Model.Enums;

public enum ReactionType {
    HARDNESS {
        @Override
        public String toString() { // TODO: toString instead
            return "трудности";
        }
    },
    MADNESS {
        @Override
        public String toString() {
            return "безумие";
        }
    },
    CONFUSION {
        @Override
        public String toString() {
            return "недоумение";
        }
    },
    PATHOLOGICAL_HATE {
        @Override
        public String toString() {
            return "патологическую неприязнь";
        }
    },
    LONELINESS {
        @Override
        public String toString() {
            return "одиночество";
        }
    },
    FEAR {
        @Override
        public String toString() {
            return "страх";
        }
    },
    DISQUIETUDE {
        @Override
        public String toString() {
            return "беспокойство";
        }
    };

}
