SELECT format('Перебранка продолжалась %s %s, началась %s, закончилась %s. ' || 
              'В перебранке участвовали стая вожака %s и вожака %s. ' ||
              'Стая вожака %s и вожака %s принялись %s. ' ||
              'Честь стаи вожака %s %s и вожака %s %s. ' ||
              'Стая вожака %s овладела территорией %s, вожака %s - территорией %s. ' ||
              '%s и его сородичи решили %s, вдоль %s. ' ||
              'До %s, где ещё можно было кормиться, от %s было %s %s. ' ||
              'Здесь же паслись животные вида %s, они %s стаю вожака %s. ' ||
              'Эти %s %s %s, так как у %s было оружие: %s, а у %s: %s.',
    -- Предложение 1
    (SELECT duration FROM quarrels WHERE id = 1),
    (SELECT duration_units FROM quarrels WHERE id = 1),
    (SELECT start_description FROM quarrels WHERE id = 1),
    (SELECT end_description FROM quarrels WHERE id = 1),
    -- Предложение 2
    (
        SELECT name
        FROM animals
        WHERE animals.id IN (
            SELECT leader_id
            FROM quarrels_participants
            INNER JOIN flocks ON quarrels_participants.flock_id = flocks.id
            WHERE quarrels_participants.quarrel_id = 1
        )
        LIMIT 1 OFFSET 0
    ),
    (
        SELECT name
        FROM animals
        WHERE animals.id IN (
            SELECT leader_id
            FROM quarrels_participants
            INNER JOIN flocks ON quarrels_participants.flock_id = flocks.id
            WHERE quarrels_participants.quarrel_id = 1
        )
        LIMIT 1 OFFSET 1
    ),
    -- Предложение 3
    (
        SELECT name
        FROM animals, actions
        WHERE animals.id IN (
            SELECT leader_id
            FROM quarrels_participants
            INNER JOIN flocks ON (
                flocks.id = quarrels_participants.flock_id
                AND
                actions.id = 1
            )
        )
        LIMIT 1 OFFSET 0
    ),
    (
        SELECT name
        FROM animals, actions
        WHERE animals.id IN (
            SELECT leader_id
            FROM quarrels_participants
            INNER JOIN flocks ON (
                flocks.id = quarrels_participants.flock_id
                AND
                actions.id = 1
            )
        )
        LIMIT 1 OFFSET 1
    ),
    (
        SELECT description
        FROM animals, actions
        WHERE animals.id IN (
            SELECT leader_id
            FROM quarrels_participants
            INNER JOIN flocks ON (
                flocks.id = quarrels_participants.flock_id
                AND
                actions.id = 1
            )
        )
        LIMIT 1 OFFSET 1
    ),
    -- Предложение 4
    (
        SELECT name
        FROM animals
        INNER JOIN flocks ON animals.flock_id = flocks.id
        WHERE animals.id IN (
            SELECT leader_id
            FROM quarrels_participants
            INNER JOIN flocks ON (
                flocks.id = quarrels_participants.flock_id
                AND
                quarrels_participants.quarrel_id = 1
            )
        )
        ORDER BY flocks.id
        LIMIT 1 OFFSET 0
    ),
    (
        SELECT CASE
            WHEN is_honor_confirmed THEN 'была удовлетворена'
            ELSE 'не была удовлетворена'
        END
        FROM animals
        INNER JOIN flocks ON animals.flock_id = flocks.id
        WHERE animals.id IN (
            SELECT leader_id
            FROM quarrels_participants
            INNER JOIN flocks ON (
                flocks.id = quarrels_participants.flock_id
                AND
                quarrels_participants.quarrel_id = 1
            )
        )
        ORDER BY flocks.id
        LIMIT 1 OFFSET 0
    ),
    (
        SELECT name
        FROM animals
        INNER JOIN flocks ON animals.flock_id = flocks.id
        WHERE animals.id IN (
            SELECT leader_id
            FROM quarrels_participants
            INNER JOIN flocks ON (
                flocks.id = quarrels_participants.flock_id
                AND
                quarrels_participants.quarrel_id = 1
            )
        )
        ORDER BY flocks.id
        LIMIT 1 OFFSET 1
    ),
    (
        SELECT CASE
            WHEN is_honor_confirmed THEN 'была удовлетворена'
            ELSE 'не была удовлетворена'
        END
        FROM animals
        INNER JOIN flocks ON animals.flock_id = flocks.id
        WHERE animals.id IN (
            SELECT leader_id
            FROM quarrels_participants
            INNER JOIN flocks ON (
                flocks.id = quarrels_participants.flock_id
                AND
                quarrels_participants.quarrel_id = 1
            )
        )
        ORDER BY flocks.id
        LIMIT 1 OFFSET 1
    ),
    -- Предложение 5
    (
        SELECT animals.name
        FROM territories_owners
        INNER JOIN animals ON (
            animals.flock_id = territories_owners.owner_id
            AND
            animals.id IN (SELECT leader_id FROM flocks)
        )
        INNER JOIN territories ON (
            territories.id = territories_owners.territory_id
        )
        WHERE ownership_reason = 1
        ORDER BY territories_owners.owner_id
        LIMIT 1 OFFSET 0
    ),
    (
        SELECT territories.name
        FROM territories_owners
        INNER JOIN animals ON (
            animals.flock_id = territories_owners.owner_id
            AND
            animals.id IN (SELECT leader_id FROM flocks)
        )
        INNER JOIN territories ON (
            territories.id = territories_owners.territory_id
        )
        WHERE ownership_reason = 1
        ORDER BY territories_owners.owner_id
        LIMIT 1 OFFSET 0
    ),
    (
        SELECT animals.name
        FROM territories_owners
        INNER JOIN animals ON (
            animals.flock_id = territories_owners.owner_id
            AND
            animals.id IN (SELECT leader_id FROM flocks)
        )
        INNER JOIN territories ON (
            territories.id = territories_owners.territory_id
        )
        WHERE ownership_reason = 1
        ORDER BY territories_owners.owner_id
        LIMIT 1 OFFSET 1
    ),
    (
        SELECT territories.name
        FROM territories_owners
        INNER JOIN animals ON (
            animals.flock_id = territories_owners.owner_id
            AND
            animals.id IN (SELECT leader_id FROM flocks)
        )
        INNER JOIN territories ON (
            territories.id = territories_owners.territory_id
        )
        WHERE ownership_reason = 1
        ORDER BY territories_owners.owner_id
        LIMIT 1 OFFSET 1
    ),
    -- Предложение 6
    (
        SELECT animals.name
        FROM territories_owners
        INNER JOIN animals ON (
            animals.flock_id = territories_owners.owner_id
            AND
            animals.id IN (SELECT leader_id FROM flocks WHERE is_main)
        )
        INNER JOIN territories ON (
            territories.id = territories_owners.territory_id
        )
        WHERE ownership_reason = 1
    ),
    (SELECT description FROM actions WHERE id = 2),
    (
        SELECT territories.name
        FROM territories_owners
        INNER JOIN animals ON (
            animals.flock_id = territories_owners.owner_id
            AND
            animals.id IN (SELECT leader_id FROM flocks WHERE is_main)
        )
        INNER JOIN territories ON (
            territories.id = territories_owners.territory_id
        )
        WHERE ownership_reason = 1
    ),
    -- Предложение 7
    (
        SELECT name
        FROM distances
        INNER JOIN territories ON (
            distances.territory1_id = territories.id
            OR
            distances.territory2_id = territories.id
        )
        WHERE (
            distance = (
                SELECT MIN(distance)
                FROM distances
                INNER JOIN territories ON (
                    distances.territory1_id = territories.id
                    OR
                    distances.territory2_id = territories.id
                )
                WHERE has_food
            )
        )
        ORDER BY has_food DESC
        LIMIT 1 OFFSET 0
    ),
    (
        SELECT name
        FROM distances
        INNER JOIN territories ON (
            distances.territory1_id = territories.id
            OR
            distances.territory2_id = territories.id
        )
        WHERE (
            distance = (
                SELECT MIN(distance)
                FROM distances
                INNER JOIN territories ON (
                    distances.territory1_id = territories.id
                    OR
                    distances.territory2_id = territories.id
                )
                WHERE has_food
            )
        )
        ORDER BY has_food DESC
        LIMIT 1 OFFSET 1
    ),
    (
        SELECT distance
        FROM distances
        INNER JOIN territories ON (
            distances.territory1_id = territories.id
            OR
            distances.territory2_id = territories.id
        )
        WHERE (
            distance = (
                SELECT MIN(distance)
                FROM distances
                INNER JOIN territories ON (
                    distances.territory1_id = territories.id
                    OR
                    distances.territory2_id = territories.id
                )
                WHERE has_food
            )
        )
        ORDER BY has_food DESC
        LIMIT 1 OFFSET 1
    ),
    (
        SELECT units
        FROM distances
        INNER JOIN territories ON (
            distances.territory1_id = territories.id
            OR
            distances.territory2_id = territories.id
        )
        WHERE (
            distance = (
                SELECT MIN(distance)
                FROM distances
                INNER JOIN territories ON (
                    distances.territory1_id = territories.id
                    OR
                    distances.territory2_id = territories.id
                )
                WHERE has_food
            )
        )
        ORDER BY has_food DESC
        LIMIT 1 OFFSET 1
    ),
    -- Предложение 8
    (
        SELECT type_name
        FROM animal_types
        WHERE animal_types.id IN (
            SELECT type_id
            FROM animals
            WHERE flock_id = (
                SELECT flocks.id
                FROM flocks
                WHERE flocks.id IN (
                    SELECT owner_id
                    FROM territories_owners
                    WHERE territory_id = 3
                )
            )
        )
    ),
    (SELECT description FROM actions WHERE id = 3),
    (
        SELECT animals.name
        FROM territories_owners
        INNER JOIN animals ON (
            animals.flock_id = territories_owners.owner_id
            AND
            animals.id IN (SELECT leader_id FROM flocks WHERE is_main)
        )
        INNER JOIN territories ON (
            territories.id = territories_owners.territory_id
        )
        WHERE ownership_reason = 1
    ),
    -- Предложение 9
    (SELECT type_name FROM animal_types WHERE id = 1),
    (SELECT description FROM actions WHERE id = 4),
    (SELECT type_name FROM animal_types WHERE id = 2),
    (SELECT type_name FROM animal_types WHERE id = 2),
    (
        SELECT weapon.name
        FROM (
            SELECT *,
            CASE
                WHEN weapon_id IS NULL THEN 'нет оружия'
                ELSE (SELECT weapons.name FROM weapons WHERE weapons.id = animal_types.weapon_id)
            END
            FROM animal_types
            WHERE type_name = 'крупные рогатые животные'
        ) AS weapon
    ),
    (SELECT type_name FROM animal_types WHERE id = 1),
    (
        SELECT weapon.name
        FROM (
            SELECT *,
            CASE
                WHEN weapon_id IS NULL THEN 'нет оружия'
                ELSE (SELECT weapons.name FROM weapons WHERE weapons.id = animal_types.weapon_id)
            END
            FROM animal_types
            WHERE type_name = 'питекантропы'
        ) AS weapon
    )
);