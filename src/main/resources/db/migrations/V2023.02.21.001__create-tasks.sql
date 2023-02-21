CREATE TABLE `tasks` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `input` tinytext NOT NULL,
  `pattern` varchar(255) NOT NULL,
  `percentage` int NOT NULL,
  `position` int DEFAULT NULL,
  `status` varchar(255) NOT NULL,
  `typos` int DEFAULT NULL,
  PRIMARY KEY (`id`)
);
