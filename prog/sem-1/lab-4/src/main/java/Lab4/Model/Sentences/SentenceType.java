package Lab4.Model.Sentences;

public enum SentenceType {
    ACTION {
        @Override
        public String toString() {
            return "действие";
        }
    },
    PHRASE {
        @Override
        public String toString() {
            return "фраза";
        }
    },
    REACTION {
        @Override
        public String toString() {
            return "реакция";
        }
    }
}
