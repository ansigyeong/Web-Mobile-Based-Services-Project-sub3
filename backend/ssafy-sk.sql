# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user01` (
  `uid` char(8) NOT NULL,
  `email` varchar(128) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  `create_date` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`uid`),
  UNIQUE KEY `user_idx_unique_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `user` (
  `uid` VARCHAR(50) NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  `email` VARCHAR(50) NOT NULL,
  `pw` VARCHAR(60) NOT NULL,
  `img` VARCHAR(100) DEFAULT NULL,
  `lang` VARCHAR(30) NOT NULL,
  `grade` INT NOT NULL DEFAULT 0,
  `enable` INT(1) NOT NULL DEFAULT 1,
  `follower` INT NOT NULL DEFAULT 0,
  `following` INT NOT NULL DEFAULT 0,
  `total_like` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`uid`))
ENGINE = InnoDB CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `user_auth` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `authority` VARCHAR(50) NOT NULL DEFAULT 'ROLE_USER',
  `auth_id` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`no`),
  INDEX `uid_idx` (`auth_id` ASC) VISIBLE,
  CONSTRAINT `user_auth_id_fk`
    FOREIGN KEY (`auth_id`)
    REFERENCES `user` (`uid`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB CHARSET=utf8;

