CREATE TABLE lab7_users (
    id SERIAL PRIMARY KEY,
    login TEXT NOT NULL,
    password TEXT
);

CREATE TABLE lab7_coordinates (
    id SERIAL PRIMARY KEY,
    x DOUBLE PRECISION,
    y REAL NOT NULL CHECK (y > -273)
);

CREATE TABLE lab7_astartes_categories (
    id INT PRIMARY KEY,
    constant_name TEXT
);

CREATE TABLE lab7_weapons (
    id INT PRIMARY KEY,
    constant_name TEXT
);

CREATE TABLE lab7_melee_weapons (
    id INT PRIMARY KEY,
    constant_name TEXT
);

CREATE TABLE lab7_chapters (
    id INT PRIMARY KEY,
    constant_name TEXT
);

CREATE TABLE lab7_spacemarines (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL CHECK (name <> ''),
    coordinates_id INT NOT NULL REFERENCES lab7_coordinates(id),
    creation_date TIMESTAMP NOT NULL,
    health BIGINT NOT NULL CHECK (health > 0),
    category_id INT NOT NULL REFERENCES lab7_astartes_categories(id),
    weapon_type_id INT REFERENCES lab7_weapons(id),
    melee_weapon_id INT REFERENCES lab7_melee_weapons(id),
    chapter_id INT NOT NULL REFERENCES lab7_chapters(id)
);