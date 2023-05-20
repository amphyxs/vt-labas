CREATE OR REPLACE FUNCTION min_dist(a int, b int) RETURNS integer AS $$
    DECLARE
        min_dists int[];  -- Массив найденных мин. расстояний от вершины 1 до всех других
        is_visited boolean[];  -- Массив отметок каждой вершины о её посещении 
        d int;  -- Переменная для подсчёта расстояний
        v int;  -- Переменная для итерируемой вершины
        n int;  -- Количество вершин
        u int;  -- Переменная для вершнины, смежной с итерируемой
        qres record;  -- Строка для поиска смежных вершин
        result int;  -- Переменная для сохранения результата работы функции
    BEGIN
        -- Получение числа вершин
        SELECT count(*) INTO n
        FROM territories;

        -- Проверка на некорректные значение
        IF a < 1 OR a > n OR b < 1 OR b > n THEN
            RAISE EXCEPTION 'Некорректные значения a или b';
            RETURN NULL;
        END IF;

        -- Массив найденных расстояний от a
        min_dists := array_fill(10000, ARRAY[n + 1], ARRAY[0]);
        min_dists[a] := 0;

        -- Массив отметки о посещение
        is_visited := array_fill(0, ARRAY[n + 1], ARRAY[0]);

        LOOP
            -- Найдём непосещённую вершину с наименьшим текущим расстоянием
            d := 10000;
            v := -1;
            FOR i IN 1..n 
            LOOP
                IF is_visited[i] = false AND min_dists[i] < d THEN
                    v = i;
                    d := min_dists[v];
                END IF;
            END LOOP;
                
            EXIT WHEN v = -1;

            is_visited[v] = true;

            -- Итерируем по всем смежным с v вершинам 
            FOR qres IN
                SELECT territory1_id t1, territory2_id t2, distance dist
                FROM distances
                WHERE territory1_id = v OR territory2_id = v
            LOOP
                -- Получаем номер смежной вершины
                IF qres.t1 = v THEN
                    u := qres.t2;
                ELSE
                    u := qres.t1;
                END IF;
                
                -- Проверяем, посещена ли она
                IF is_visited[u] THEN
                    CONTINUE;
                END IF;

                -- Если нашли расстояние лучше, то обновляем
                IF min_dists[u] > (min_dists[v] + qres.dist) THEN
                    min_dists[u] := min_dists[v] + qres.dist;
                END IF;
            END LOOP;
        END LOOP;

        -- Результат с проверкой на существование пути
        IF min_dists[b] = 10000 THEN
            result := NULL;
        ELSE
            result := min_dists[b];
        END IF;

        RETURN result;
    END;
$$ LANGUAGE plpgsql IMMUTABLE;

CREATE OR REPLACE FUNCTION update_min_distances() RETURNS trigger AS $$
    -- Вспомогательная функиция, вызывающая перегенерацию всех значений минимальных расстояний
    -- для каждой записи в таблице min_distances
    BEGIN
        UPDATE min_distances 
        SET territory1_id = territory1_id;
        RETURN NEW;
    END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER distances_update AFTER INSERT OR UPDATE OR DELETE ON distances
FOR EACH ROW EXECUTE PROCEDURE update_min_distances();