CREATE TABLE actions_executors
(
    action_id integer NOT NULL
        REFERENCES actions,
    animal_id integer NOT NULL
        REFERENCES animals,
    PRIMARY KEY (action_id, animal_id)
);

ALTER TABLE actions_executors
    OWNER TO s367527;

INSERT INTO s367527.actions_executors (action_id, animal_id) VALUES (1, 1);
INSERT INTO s367527.actions_executors (action_id, animal_id) VALUES (1, 2);
INSERT INTO s367527.actions_executors (action_id, animal_id) VALUES (1, 3);
INSERT INTO s367527.actions_executors (action_id, animal_id) VALUES (1, 4);
INSERT INTO s367527.actions_executors (action_id, animal_id) VALUES (1, 5);
INSERT INTO s367527.actions_executors (action_id, animal_id) VALUES (1, 6);
INSERT INTO s367527.actions_executors (action_id, animal_id) VALUES (1, 7);
