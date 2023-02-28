CREATE TABLE flocks
(
    id                 integer NOT NULL
        PRIMARY KEY,
    leader_id          integer
        CONSTRAINT k_leader_id
            REFERENCES animals
            ON UPDATE CASCADE ON DELETE SET NULL,
    is_honor_confirmed boolean,
    is_main            boolean
);

ALTER TABLE flocks
    OWNER TO s367527;

INSERT INTO s367527.flocks (id, leader_id, is_honor_confirmed, is_main) VALUES (2, 5, true, false);
INSERT INTO s367527.flocks (id, leader_id, is_honor_confirmed, is_main) VALUES (3, null, false, false);
INSERT INTO s367527.flocks (id, leader_id, is_honor_confirmed, is_main) VALUES (1, 1, true, true);
