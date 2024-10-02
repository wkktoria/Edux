create table if not exists contact_message
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

create table if not exists holidays
(
    `day`        varchar(10)  not null,
    `reason`     varchar(100) not null,
    `type`       varchar(20)  not null,
    `created_at` timestamp    not null,
    `created_by` varchar(50)  not null,
    `updated_at` timestamp   default null,
    `updated_by` varchar(50) default null
);