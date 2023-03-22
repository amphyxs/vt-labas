-- Найти все жанры, которые не играют в Британии
SELECT name
FROM songs
RIGHT JOIN genres ON (songs.genre_id=genres.id AND songs.country='UK')
WHERE songs.title IS NULL