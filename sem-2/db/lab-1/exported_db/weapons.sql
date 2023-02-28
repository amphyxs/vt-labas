CREATE TABLE weapons
(
    id   integer NOT NULL
        PRIMARY KEY,
    name text
);

ALTER TABLE weapons
    OWNER TO s367527;

INSERT INTO s367527.weapons (id, name) VALUES (1, 'устрашающие рога-кинжалы');
