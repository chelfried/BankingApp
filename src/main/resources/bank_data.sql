CREATE DATABASE IF NOT EXISTS `bank_data`;
ALTER DATABASE bank_data COLLATE = 'latin1_general_cs';
USE `bank_data`;

DROP TABLE IF EXISTS `transactions`;
DROP TABLE IF EXISTS `account`;
DROP TABLE IF EXISTS `client_data`;

CREATE TABLE `client_data` (
  `username` varchar(15) NOT NULL,
  `password` varchar(45) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `cnp` bigint(13) NOT NULL,
  `email` varchar(45) NOT NULL,
  PRIMARY KEY (`username`)
);

CREATE TABLE `account` (
  `account_no` int(8) NOT NULL AUTO_INCREMENT,
  `username` varchar(15) NOT NULL,
  `account_type` int(1) NOT NULL,
  `balance` double DEFAULT 0,
  `currency` int(1) NOT NULL,
  PRIMARY KEY (`account_no`),
  FOREIGN KEY (`username`) REFERENCES client_data(`username`)
);

ALTER TABLE `account` AUTO_INCREMENT = 10000001;

CREATE TABLE `transactions` (
  `reference_id` int(8) NOT NULL AUTO_INCREMENT,
  `transaction_type` int(1) NOT NULL,
  `account_no` int(8) NOT NULL,
  `transfer_amount` double,
  `foreign_account` varchar(34) NOT NULL,
  `name` varchar(50),
  `date_and_time` datetime NOT NULL,
  PRIMARY KEY (`reference_id`),
  FOREIGN KEY (`account_no`) REFERENCES account(`account_no`)
);

ALTER TABLE `transactions` AUTO_INCREMENT = 1000001;