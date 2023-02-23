CREATE TABLE territories_owners
(
    owner_id         integer NOT NULL
        REFERENCES flocks,
    territory_id     integer NOT NULL
        REFERENCES territories,
    ownership_reason integer
        REFERENCES quarrels,
    PRIMARY KEY (owner_id, territory_id)
);

ALTER TABLE territories_owners
    OWNER TO s367527;

INSERT INTO s367527.territories_owners (owner_id, territory_id, ownership_reason) VALUES (1, 1, 1);
INSERT INTO s367527.territories_owners (owner_id, territory_id, ownership_reason) VALUES (2, 2, 1);
INSERT INTO s367527.territories_owners (owner_id, territory_id, ownership_reason) VALUES (3, 3, null);
