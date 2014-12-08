DROP TABLE messages;
DROP TABLE contacts;

CREATE TABLE `messages` (
  `mid` int(11) NOT NULL AUTO_INCREMENT,
  `message` varchar(255) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  PRIMARY KEY (`mid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE `contacts` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO contacts (firstname, lastname) VALUES ("NULL", "NULL");
INSERT INTO contacts (firstname, lastname) VALUES ("Karen", "Lo");
INSERT INTO contacts (firstname, lastname) VALUES ("Taylor", "Fah");
INSERT INTO contacts (firstname, lastname) VALUES ("Angie", "Nguyen");
INSERT INTO contacts (firstname, lastname) VALUES ("Justin", "Hung");
INSERT INTO contacts (firstname, lastname) VALUES ("Ryan", "Liao");
INSERT INTO contacts (firstname, lastname) VALUES ("Sean", "Rowan");
INSERT INTO contacts (firstname, lastname) VALUES ("Alvin", "Portillo");


-- QUERY FOR GETTING MOST COMMON
-- SELECT message, COUNT(message) AS messageCount
-- FROM (SELECT * FROM messages WHERE pid=30) AS userMessages
-- GROUP BY message
-- ORDER BY COUNT(message) DESC
-- LIMIT 3;