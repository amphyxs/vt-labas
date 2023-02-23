CREATE TABLE animals
(
    id       integer NOT NULL
        PRIMARY KEY,
    type_id  integer,
    flock_id integer,
    name     text
);

ALTER TABLE animals
    OWNER TO s367527;

INSERT INTO s367527.animals (id, type_id, flock_id, name) VALUES (1, 1, 1, 'Смотрящий на Луну');
INSERT INTO s367527.animals (id, type_id, flock_id, name) VALUES (2, 1, 1, 'Бегущий по Лезвию');
INSERT INTO s367527.animals (id, type_id, flock_id, name) VALUES (3, 1, 1, 'Знающий обо Всём');
INSERT INTO s367527.animals (id, type_id, flock_id, name) VALUES (4, 1, 1, 'Говорящий по Делу');
INSERT INTO s367527.animals (id, type_id, flock_id, name) VALUES (5, 1, 2, 'Злобный');
INSERT INTO s367527.animals (id, type_id, flock_id, name) VALUES (6, 1, 2, 'Дерзкий');
INSERT INTO s367527.animals (id, type_id, flock_id, name) VALUES (7, 1, 2, 'Опасный');
INSERT INTO s367527.animals (id, type_id, flock_id, name) VALUES (8, 1, 2, 'Больной');
INSERT INTO s367527.animals (id, type_id, flock_id, name) VALUES (9, 2, 3, 'Бык');
INSERT INTO s367527.animals (id, type_id, flock_id, name) VALUES (10, 2, 3, 'Рогач');
INSERT INTO s367527.animals (id, type_id, flock_id, name) VALUES (11, 2, 3, 'Корова');
