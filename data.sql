call createPermission('PERM_CHANGE_PASSWORD');
call createPermission('PERM_RES_EDIT');
call createPermission('PERM_SCH_EDIT');
call createPermission('PERM_RESERVATION_CREATE');
call createPermission('PERM_MANAGE_USERS');
call createPermission('PERM_MANAGE_ROLES');
call createRole('ROLE_SUPERADMIN', @role_superadmin);
call createRole('ROLE_USER', @role_user);
call createRole('ROLE_ADMIN', @role_admin);
call createRole('ROLE_REGISTERED', @role_registered);
call roleHasPermission(@role_registered, 'PERM_CHANGE_PASSWORD');
call roleHasPermission(@role_admin, 'PERM_RES_EDIT');
call roleHasPermission(@role_admin, 'PERM_SCH_EDIT');
call roleHasPermission(@role_admin, 'PERM_MANAGE_USERS');
call roleHasPermission(@role_user, 'PERM_RESERVATION_CREATE');
call roleHasPermission(@role_superadmin, 'PERM_MANAGE_ROLES');
INSERT INTO `ubs`.`account`
( `id`,
`first_name`,
`last_name`,
`email`,
`password`,
`marketing_ok`,
`accept_terms`,
`enabled`,
`calendar_ok`,
`twitter_ok`,
`email_ok`,
`credit`,
`group_id`,
`date_created`,
`date_modified`)
VALUES
(
3, 'Superadmin', 'account', 'superadmin@ubs.cz', '3be403f37f83a1ff0411087b67c5e0e6f23f25c7b4cf1f071f3d142d8fd7f93a7c91bf28aaf7ff00', 0, 1, 1, 1, 1, 1, 0.00, NULL, '2014-03-13 22:59:31', '2014-03-13 22:59:31'
);
INSERT INTO `ubs`.`account`
( `id`,
`first_name`,
`last_name`,
`email`,
`password`,
`marketing_ok`,
`accept_terms`,
`enabled`,
`calendar_ok`,
`twitter_ok`,
`email_ok`,
`credit`,
`group_id`,
`date_created`,
`date_modified`)
VALUES
(
2, 'Admin', 'account', 'admin@ubs.cz', '3be403f37f83a1ff0411087b67c5e0e6f23f25c7b4cf1f071f3d142d8fd7f93a7c91bf28aaf7ff00', 0, 1, 1, 1, 1, 1, 0.00, NULL, '2014-03-13 22:59:31', '2014-03-13 22:59:31'
);
INSERT INTO `ubs`.`account`
( `id`,
`first_name`,
`last_name`,
`email`,
`password`,
`marketing_ok`,
`accept_terms`,
`enabled`,
`calendar_ok`,
`twitter_ok`,
`email_ok`,
`credit`,
`group_id`,
`date_created`,
`date_modified`)
VALUES
(
3, 'User', 'account', 'user@ubs.cz', '3be403f37f83a1ff0411087b67c5e0e6f23f25c7b4cf1f071f3d142d8fd7f93a7c91bf28aaf7ff00', 0, 1, 1, 1, 1, 1, 0.00, NULL, '2014-03-13 22:59:31', '2014-03-13 22:59:31'
);
call accountHasRole(1, @role_superadmin);
call accountHasRole(1, @role_admin);
call accountHasRole(1, @role_registered);
call accountHasRole(2, @role_admin);
call accountHasRole(2, @role_registered);
call accountHasRole(3, @role_user);
call accountHasRole(3, @role_registered);