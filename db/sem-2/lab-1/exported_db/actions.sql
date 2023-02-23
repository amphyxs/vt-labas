CREATE TABLE actions
(
    id          integer NOT NULL
        PRIMARY KEY,
    description text
);

ALTER TABLE actions
    OWNER TO s367527;

INSERT INTO s367527.actions (id, description) VALUES (1, 'пить мутную от глины воду');
INSERT INTO s367527.actions (id, description) VALUES (2, 'покончить с этим делом и отправиться дальше');
INSERT INTO s367527.actions (id, description) VALUES (3, 'не особенно благосклонно встретили');
INSERT INTO s367527.actions (id, description) VALUES (4, 'не могут прогнать');
