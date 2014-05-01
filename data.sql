call createPermission('PERM_CHANGE_PASSWORD');
call createPermission('PERM_RES_EDIT');
call createPermission('PERM_SCH_EDIT');
call createPermission('PERM_RESERVE');
call createPermission('PERM_GOOGLE');
call createPermission('PERM_MANAGE_USERS');
call createPermission('PERM_MANAGE_ROLES');
call createPermission('PERM_PRICE');
call createPermission('PERM_RULE');
call createRole('ROLE_SUPERADMIN', @role_superadmin);
call createRole('ROLE_USER', @role_user);
call createRole('ROLE_ADMIN', @role_admin);
call createRole('ROLE_REGISTERED', @role_registered);
call roleHasPermission(@role_registered, 'PERM_CHANGE_PASSWORD');
call roleHasPermission(@role_admin, 'PERM_RES_EDIT');
call roleHasPermission(@role_admin, 'PERM_SCH_EDIT');
call roleHasPermission(@role_admin, 'PERM_MANAGE_USERS');
call roleHasPermission(@role_admin, 'PERM_PRICE');
call roleHasPermission(@role_admin, 'PERM_RULE');
call roleHasPermission(@role_user, 'PERM_RESERVE');
call roleHasPermission(@role_superadmin, 'PERM_MANAGE_ROLES');
call roleHasPermission(@role_user, 'PERM_GOOGLE');
INSERT INTO `ubs`.`account`
( `id`,
`first_name`,
`last_name`,
`email`,
`password`,
`enabled`,
`calendar_ok`,
`google_plus_ok`,
`credit`,
`date_created`,
`date_modified`)
VALUES
(
1,
'Superadmin',
'account',
'superadmin@account.test',
'db22fff3dcc4e38895efc67d1daae577844d2c91a00c62487e293114c9230bc21a9b922991b063cb',
1,
1,
1,
0.00,
'2014-05-01 12:49:24',
 '2014-05-01 12:49:24'
);
INSERT INTO `ubs`.`account`
( `id`,
`first_name`,
`last_name`,
`email`,
`password`,
`enabled`,
`calendar_ok`,
`google_plus_ok`,
`credit`,
`date_created`,
`date_modified`)
VALUES
(
2, 'Admin', 'account', 'admin@account.test', 'db22fff3dcc4e38895efc67d1daae577844d2c91a00c62487e293114c9230bc21a9b922991b063cb', 1, 1, 1, 0.00, '2014-05-01 12:49:24', '2014-05-01 12:49:24'
);
INSERT INTO `ubs`.`account`
( `id`,
`first_name`,
`last_name`,
`email`,
`password`,
`enabled`,
`calendar_ok`,
`google_plus_ok`,
`credit`,
`date_created`,
`date_modified`)
VALUES
(
3, 'User', 'account', 'user@account.test', 'db22fff3dcc4e38895efc67d1daae577844d2c91a00c62487e293114c9230bc21a9b922991b063cb', 1, 1, 1, 0.00, '2014-05-01 12:49:24', '2014-05-01 12:49:24'
);
call accountHasRole(1, @role_superadmin);
call accountHasRole(1, @role_admin);
call accountHasRole(1, @role_registered);
call accountHasRole(2, @role_admin);
call accountHasRole(2, @role_registered);
call accountHasRole(3, @role_user);
call accountHasRole(3, @role_registered);