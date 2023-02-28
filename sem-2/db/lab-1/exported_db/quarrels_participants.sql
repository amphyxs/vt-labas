CREATE TABLE quarrels_participants
(
    flock_id   integer NOT NULL
        REFERENCES flocks,
    quarrel_id integer NOT NULL
        REFERENCES quarrels,
    PRIMARY KEY (flock_id, quarrel_id)
);

ALTER TABLE quarrels_participants
    OWNER TO s367527;

INSERT INTO s367527.quarrels_participants (flock_id, quarrel_id) VALUES (1, 1);
INSERT INTO s367527.quarrels_participants (flock_id, quarrel_id) VALUES (2, 1);
