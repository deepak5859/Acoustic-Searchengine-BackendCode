DROP TABLE IF EXISTS customer;  

CREATE TABLE customer(
	id INT AUTO_INCREMENT PRIMARY KEY, 
	jobtitle VARCHAR(500), 
	email VARCHAR(500), 
	firstname VARCHAR(500), 
	lastname VARCHAR(500), 
	company VARCHAR(500)
);