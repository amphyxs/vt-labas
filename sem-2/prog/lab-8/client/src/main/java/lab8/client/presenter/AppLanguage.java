package lab8.client.presenter;

public enum AppLanguage {
    RUSSIAN {
        @Override
        public String getLabel() {
            return "русский";
        }

        @Override
        public String getCode() {
            return "ru";
        }
    },
    TURKISH {
        @Override
        public String getLabel() {
            return "türk";
        }

        @Override
        public String getCode() {
            return "tr";
        }
    },
    SPANISH_HONDURAS {
        @Override
        public String getLabel() {
            return "español (Honduras)";
        }

        @Override
        public String getCode() {
            return "es";
        }

        public String getRegionCode() {
            return "HN";
        }
    },
    FRENCH {
        @Override
        public String getLabel() {
            return "français";
        }

        @Override
        public String getCode() {
            return "fr";
        }
    };

    public String getCode() {
        return "ru";
    }

    public String getLabel() {
        return "русский";
    }

    public String getRegionCode() {
        return null;
    }

}
