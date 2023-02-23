CREATE TABLE animal_types
(
    id        integer NOT NULL
        PRIMARY KEY,
    weapon_id integer
        REFERENCES weapons,
    type_name text
);

ALTER TABLE animal_types
    OWNER TO s367527;

INSERT INTO s367527.animal_types (id, weapon_id, type_name) VALUES (2, 1, 'крупные рогатые животные');
INSERT INTO s367527.animal_types (id, weapon_id, type_name) VALUES (1, null, 'питекантропы');
