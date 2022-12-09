http://localhost:8080/h2-console


SELECT * FROM route WHERE id like '%PNR%' AND stops = 17 AND zones > 2

SELECT * FROM route WHERE id like '%BNK%' AND stops = 17 AND zones > 2

SELECT * FROM station WHERE name like '%Upm%'

SELECT * FROM route WHERE id like '%PNR%' and id like '%UPM%'
