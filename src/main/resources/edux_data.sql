use edux;

insert into `holidays` (`day`, `reason`, `type`, `created_at`, `created_by`)
values ('Jan 1', 'New Year''s day', 'FESTIVAL', CURDATE(), 'DBA');

insert into `holidays` (`day`, `reason`, `type`, `created_at`, `created_by`)
values ('Oct 31', 'Halloween', 'FESTIVAL', CURDATE(), 'DBA');

insert into `holidays` (`day`, `reason`, `type`, `created_at`, `created_by`)
values ('Nov 24', 'Thanksgiving Day', 'FESTIVAL', CURDATE(), 'DBA');

insert into `holidays` (`day`, `reason`, `type`, `created_at`, `created_by`)
values ('Dec 25', 'Christmas', 'FESTIVAL', CURDATE(), 'DBA');

insert into `holidays` (`day`, `reason`, `type`, `created_at`, `created_by`)
values ('Jul 4', 'Independence Day', 'FEDERAL', CURDATE(), 'DBA');

insert into `holidays` (`day`, `reason`, `type`, `created_at`, `created_by`)
values ('Nov 11', 'Veterans Day', 'FEDERAL', CURDATE(), 'DBA');

insert into `role` (`role_name`, `created_at`, `created_by`)
values ('ADMIN', CURDATE(), 'DBA');

insert into `role` (`role_name`, `created_at`, `created_by`)
values ('STUDENT', CURDATE(), 'DBA');

insert into `person` (`name`, `email`, `phone_number`, `password`, `role_id`, `created_at`, `created_by`)
values ('Admin', 'admin@edux.com', '1234567890', 'adminstr0ngp@sw00rd', 1, CURDATE(), 'DBA');