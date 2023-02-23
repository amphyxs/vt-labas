CREATE TABLE quarrels
(
    id                integer NOT NULL
        PRIMARY KEY,
    duration          integer,
    start_description text,
    end_description   text,
    duration_units    text
);

ALTER TABLE quarrels
    OWNER TO s367527;

INSERT INTO s367527.quarrels (id, duration, start_description, end_description, duration_units) VALUES (1, 5, 'внезапно', 'так же внезапно, как и началась', 'мин');
