CREATE TABLE distances
(
    territory1_id integer NOT NULL
        REFERENCES territories,
    territory2_id integer NOT NULL
        REFERENCES territories,
    distance      integer,
    units         text,
    PRIMARY KEY (territory1_id, territory2_id)
);

ALTER TABLE distances
    OWNER TO s367527;

INSERT INTO s367527.distances (territory1_id, territory2_id, distance, units) VALUES (3, 4, 2, 'км');
INSERT INTO s367527.distances (territory1_id, territory2_id, distance, units) VALUES (4, 5, 8, 'км');
INSERT INTO s367527.distances (territory1_id, territory2_id, distance, units) VALUES (1, 2, 1, 'км');
