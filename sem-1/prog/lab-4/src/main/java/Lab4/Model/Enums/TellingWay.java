package Lab4.Model.Enums;

public enum TellingWay {
    OMIT_DETAILS {
        @Override
        public String toString() {
            return "опускать детали";
        }
    },
    NOT_DRAW_CONCLUSIONS {
        @Override
        public String toString() {
            return "не делать чётких выводов";
        }
    },
    SPEAK_IN_HINTS {
        @Override
        public String toString() {
            return "говорить намёками";
        }
    };
}
