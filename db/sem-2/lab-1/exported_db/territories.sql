CREATE TABLE territories
(
    id       integer NOT NULL
        PRIMARY KEY,
    name     text,
    has_food boolean
);

ALTER TABLE territories
    OWNER TO s367527;

INSERT INTO s367527.territories (id, name, has_food) VALUES (1, 'Дикий Берег', false);
INSERT INTO s367527.territories (id, name, has_food) VALUES (2, 'Высокие Холмы', false);
INSERT INTO s367527.territories (id, name, has_food) VALUES (3, 'Пастбище', true);
INSERT INTO s367527.territories (id, name, has_food) VALUES (4, 'Пещеры', false);
INSERT INTO s367527.territories (id, name, has_food) VALUES (5, 'Далёкие Земли', true);
