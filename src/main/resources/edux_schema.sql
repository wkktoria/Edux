CREATE DATABASE edux;

USE edux;

CREATE TABLE IF NOT EXISTS `contact_message` (
    `contact_id` int AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(100) NOT NULL,
    `phone_number` varchar(12) NOT NULL,
    `email` varchar(50) NOT NULL,
    `subject` varchar(100) NOT NULL,
    `message` varchar(1000) NOT NULL,
    `status` varchar(10) NOT NULL,
    `created_at` timestamp NOT NULL,
    `created_by` varchar(50) NOT NULL,
    `updated_at` timestamp NULL DEFAULT NULL,
    `updated_by` varchar(50) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `holidays` (
    `day` varchar(10) NOT NULL,
    `reason` varchar(100) NOT NULL,
    `type` varchar(20) NOT NULL,
    `created_at` timestamp NOT NULL,
    `created_by` varchar(50) NOT NULL,
    `updated_at` timestamp NULL DEFAULT NULL,
    `updated_by` varchar(50) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `role` (
    `role_id` int NOT NULL AUTO_INCREMENT,
    `role_name` varchar(25) NOT NULL,
    `created_at` timestamp NOT NULL,
    `created_by` varchar(50) NOT NULL,
    `updated_at` timestamp NULL DEFAULT NULL,
    `updated_by` varchar(50) DEFAULT NULL,
    PRIMARY KEY (`role_id`)
);

CREATE TABLE IF NOT EXISTS `address` (
    `address_id` int NOT NULL AUTO_INCREMENT,
    `address1` varchar(200) NOT NULL,
    `address2` varchar(200) DEFAULT NULL,
    `city` varchar(50) NOT NULL,
    `state` varchar(50) DEFAULT NULL,
    `zip_code` varchar(12) NOT NULL,
    `country` varchar(50) NOT NULL,
    `created_at` timestamp NOT NULL,
    `created_by` varchar(50) NOT NULL,
    `updated_at` timestamp NULL DEFAULT NULL,
    `updated_by` varchar(50) DEFAULT NULL,
    PRIMARY KEY (`address_id`)
);

CREATE TABLE IF NOT EXISTS `person` (
    `person_id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(100) NOT NULL,
    `phone_number` varchar(12) NOT NULL,
    `email` varchar(50) NOT NULL,
    `password` varchar(200) NOT NULL,
    `role_id` int NOT NULL,
    `address_id` int NULL,
    `created_at` timestamp NOT NULL,
    `created_by` varchar(50) NOT NULL,
    `updated_at` timestamp NULL DEFAULT NULL,
    `updated_by` varchar(50) DEFAULT NULL,
    PRIMARY KEY (`person_id`),
    FOREIGN KEY (`role_id`) REFERENCES ROLE (`role_id`),
    FOREIGN KEY (`address_id`) REFERENCES `address`(`address_id`)
);

CREATE TABLE IF NOT EXISTS `class` (
    `class_id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(100) NOT NULL,
    `created_at` timestamp NOT NULL,
    `created_by` varchar(50) NOT NULL,
    `updated_at` timestamp NULL DEFAULT NULL,
    `updated_by` varchar(50) DEFAULT NULL,
    PRIMARY KEY (`class_id`)
);

ALTER TABLE `person` ADD COLUMN `class_id` int NULL AFTER `address_id`,
    ADD CONSTRAINT `FK_CLASS_CLASS_ID` FOREIGN KEY (`class_id`) REFERENCES `class`(`class_id`);

CREATE TABLE IF NOT EXISTS `course` (
    `course_id` INT NOT NULL AUTO_INCREMENT,
    `name` varchar(100) NOT NULL,
    `fees` varchar(10) NOT NULL,
    `created_at` timestamp NOT NULL,
    `created_by` varchar(50) NOT NULL,
    `updated_at` timestamp NULL DEFAULT NULL,
    `updated_by` varchar(50) DEFAULT NULL,
    PRIMARY KEY (`course_id`)
);

CREATE TABLE IF NOT EXISTS `person_course` (
    `person_id` int NOT NULL,
    `course_id` int NOT NULL,
    FOREIGN KEY (`person_id`) REFERENCES `person`(`person_id`),
    FOREIGN KEY (`course_id`) REFERENCES `course`(`course_id`),
    PRIMARY KEY (`person_id`, `course_id`)
);

CREATE TABLE IF NOT EXISTS `course_offer` (
    `course_id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(50) NOT NULL,
    `description` varchar(200) NOT NULL,
    `lessons` int NOT NULL,
    `stars` double NOT NULL,
    `created_at` timestamp NOT NULL,
    `created_by` varchar(50) NOT NULL,
    `updated_at` timestamp NULL DEFAULT NULL,
    `updated_by` varchar(50) DEFAULT NULL,
    PRIMARY KEY (`course_id`)
);