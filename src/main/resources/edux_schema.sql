create database edux;

use edux;

create table if not exists `contact_message`
(
    `contact_id`   int auto_increment primary key,
    `name`         varchar(100)  not null,
    `phone_number` varchar(12)   not null,
    `email`        varchar(50)   not null,
    `subject`      varchar(100)  not null,
    `message`      varchar(1000) not null,
    `status`       varchar(10)   not null,
    `created_at`   timestamp     not null,
    `created_by`   varchar(50)   not null,
    `updated_at`   timestamp   default null,
    `updated_by`   varchar(50) default null
);

create table if not exists `holidays`
(
    `day`        varchar(10)  not null,
    `reason`     varchar(100) not null,
    `type`       varchar(20)  not null,
    `created_at` timestamp    not null,
    `created_by` varchar(50)  not null,
    `updated_at` timestamp   default null,
    `updated_by` varchar(50) default null
);

create table if not exists `role`
(
    `role_id`    int         not null auto_increment,
    `role_name`  varchar(25) not null,
    `created_at` timestamp   not null,
    `created_by` varchar(50) not null,
    `updated_at` timestamp   default null,
    `updated_by` varchar(50) default null,
    primary key (`role_id`)
);

create table if not exists `address`
(
    `address_id` int          not null auto_increment,
    `address1`   varchar(200) not null,
    `address2`   varchar(200) default null,
    `city`       varchar(50)  not null,
    `zip_code`   int          not null,
    `created_at` timestamp    not null,
    `created_by` varchar(50)  not null,
    `updated_at` timestamp    default null,
    `updated_by` varchar(50)  default null,
    primary key (`address_id`)
);

create table if not exists `person`
(
    `person_id`    int          not null auto_increment,
    `name`         varchar(100) not null,
    `phone_number` varchar(12)  not null,
    `email`        varchar(50)  not null,
    `password`     varchar(200) not null,
    `role_id`      int          not null,
    `address_id`   int          null,
    `created_at`   timestamp    not null,
    `created_by`   varchar(50)  not null,
    `updated_at`   timestamp   default null,
    `updated_by`   varchar(50) default null,
    primary key (`person_id`),
    foreign key (`role_id`) references role (`role_id`),
    foreign key (`address_id`) references address (`address_id`)
);