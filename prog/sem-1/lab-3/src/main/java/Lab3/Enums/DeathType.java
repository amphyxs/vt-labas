package Lab3.Enums;

public enum DeathType {
    STRANGLED {
        @Override
        public String toString() {
            return "задушен";
        }
    },
    TEARED_INTO_PIECES {
        @Override
        public String toString() {
            return "разорван на куски";
        }
    }
}
