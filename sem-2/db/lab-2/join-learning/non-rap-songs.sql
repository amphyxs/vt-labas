-- Найти все треки, которые не относятся к жанру "Реп"
SELECT title, author
FROM songs
LEFT JOIN genres ON (songs.genre_id=genres.id AND genres.name='Rap')
WHERE genres.id IS NULL