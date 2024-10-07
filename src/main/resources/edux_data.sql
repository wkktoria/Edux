USE edux;


INSERT INTO `holidays` (`day`, `reason`, `type`, `created_at`, `created_by`)
VALUES ('Jan 1', 'New Year''s day', 'FESTIVAL', CURDATE(), 'DBA');


INSERT INTO `holidays` (`day`, `reason`, `type`, `created_at`, `created_by`)
VALUES ('Oct 31', 'Halloween', 'FESTIVAL', CURDATE(), 'DBA');


INSERT INTO `holidays` (`day`, `reason`, `type`, `created_at`, `created_by`)
VALUES ('Nov 24', 'Thanksgiving Day', 'FESTIVAL', CURDATE(), 'DBA');


INSERT INTO `holidays` (`day`, `reason`, `type`, `created_at`, `created_by`)
VALUES ('Dec 25', 'Christmas', 'FESTIVAL', CURDATE(), 'DBA');


INSERT INTO `holidays` (`day`, `reason`, `type`, `created_at`, `created_by`)
VALUES ('Jul 4', 'Independence Day', 'FEDERAL', CURDATE(), 'DBA');


INSERT INTO `holidays` (`day`, `reason`, `type`, `created_at`, `created_by`)
VALUES ('Nov 11', 'Veterans Day', 'FEDERAL', CURDATE(), 'DBA');


INSERT INTO `role` (`role_name`, `created_at`, `created_by`)
VALUES ('ADMIN', CURDATE(), 'DBA');


INSERT INTO `role` (`role_name`, `created_at`, `created_by`)
VALUES ('STUDENT', CURDATE(), 'DBA');


INSERT INTO `person` (`name`, `email`, `phone_number`, `password`, `role_id`, `created_at`, `created_by`)
VALUES ('Admin', 'admin@edux.com', '+1234567890', '$2a$12$6AQpapoZjc7LxZ7TZpLh8eKE./RlDD5IegKS0tP18LIO8nGOfjvFG', 1, CURDATE(), 'DBA');