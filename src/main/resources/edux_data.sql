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