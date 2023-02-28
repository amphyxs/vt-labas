CREATE TABLE quarrels (
    id integer NOT NULL,
    duration integer,
    start_description text,
    end_description text,
    duration_units text,
    PRIMARY KEY (id)
);
CREATE TABLE territories (
    id integer NOT NULL,
    name text,
    has_food boolean,
    PRIMARY KEY (id)
);
CREATE TABLE distances (
    territory1_id integer NOT NULL,
    territory2_id integer NOT NULL,
    distance integer,
    units text,
    PRIMARY KEY (territory1_id, territory2_id),
    FOREIGN KEY (territory1_id) REFERENCES territories,
    FOREIGN KEY (territory2_id) REFERENCES territories
);
CREATE TABLE weapons (
    id integer NOT NULL,
    name text,
    PRIMARY KEY (id)
);
CREATE TABLE animal_types (
    id integer NOT NULL,
    weapon_id integer,
    type_name text,
    PRIMARY KEY (id),
    FOREIGN KEY (weapon_id) REFERENCES weapons
);
CREATE TABLE animals (
    id integer NOT NULL,
    type_id integer,
    flock_id integer,
    name text,
    PRIMARY KEY (id)
);
CREATE TABLE flocks (
    id integer NOT NULL,
    leader_id integer,
    is_honor_confirmed boolean,
    is_main boolean,
    PRIMARY KEY (id),
    CONSTRAINT k_leader_id FOREIGN KEY (leader_id) REFERENCES animals ON UPDATE CASCADE ON DELETE
    SET NULL
);
CREATE TABLE quarrels_participants (
    flock_id integer NOT NULL,
    quarrel_id integer NOT NULL,
    PRIMARY KEY (flock_id, quarrel_id),
    FOREIGN KEY (flock_id) REFERENCES flocks,
    FOREIGN KEY (quarrel_id) REFERENCES quarrels
);
CREATE TABLE actions (
    id integer NOT NULL,
    description text,
    PRIMARY KEY (id)
);
CREATE TABLE actions_executors (
    action_id integer NOT NULL,
    animal_id integer NOT NULL,
    PRIMARY KEY (action_id, animal_id),
    FOREIGN KEY (action_id) REFERENCES actions,
    FOREIGN KEY (animal_id) REFERENCES animals
);
CREATE TABLE territories_owners (
    owner_id integer NOT NULL,
    territory_id integer NOT NULL,
    ownership_reason integer,
    PRIMARY KEY (owner_id, territory_id),
    FOREIGN KEY (owner_id) REFERENCES flocks,
    FOREIGN KEY (territory_id) REFERENCES territories,
    FOREIGN KEY (ownership_reason) REFERENCES quarrels
);