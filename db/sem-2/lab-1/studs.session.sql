SELECT *
FROM animals
INNER JOIN animal_types ON (animals.type_id = animal_types.id AND animal_types.weapon_id = 1);