SELECT Н_ГРУППЫ_ПЛАНОВ.ГРУППА, COUNT(DISTINCT Н_УЧЕНИКИ.ИД)  -- Ученики из-за LEFT JOIN могут повторяться, поэтому используем DISTINCT
FROM Н_ГРУППЫ_ПЛАНОВ
JOIN Н_ПЛАНЫ ON Н_ГРУППЫ_ПЛАНОВ.ПЛАН_ИД = Н_ПЛАНЫ.ИД
JOIN Н_ОТДЕЛЫ ON ( 
    Н_ОТДЕЛЫ.КОРОТКОЕ_ИМЯ = 'КТиУ' 
    AND 
    Н_ПЛАНЫ.ОТД_ИД = Н_ОТДЕЛЫ.ИД
)
LEFT JOIN Н_УЧЕНИКИ ON (
    Н_УЧЕНИКИ.ПРИЗНАК = 'обучен' -- Проверяем, учится ли в 2011 студент
    AND
    DATE_PART('year', Н_УЧЕНИКИ.НАЧАЛО) <= '2011' 
    AND
    DATE_PART('year', Н_УЧЕНИКИ.КОНЕЦ) >= '2011' 
    AND 
    Н_ГРУППЫ_ПЛАНОВ.ГРУППА = Н_УЧЕНИКИ.ГРУППА
)
GROUP BY Н_ГРУППЫ_ПЛАНОВ.ГРУППА
HAVING COUNT(DISTINCT Н_УЧЕНИКИ.ИД) < 5;