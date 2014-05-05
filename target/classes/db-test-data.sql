INSERT INTO permission (id, name) VALUES (0, 'PERM_CHANGE_PASSWORD');
INSERT INTO permission (id, name) VALUES (1, 'PERM_RES_EDIT');
INSERT INTO permission (id, name) VALUES (2, 'PERM_SCH_EDIT');
INSERT INTO permission (id, name) VALUES (3, 'PERM_RESERVE');
INSERT INTO permission (id, name) VALUES (4, 'PERM_GOOGLE');
INSERT INTO permission (id, name) VALUES (5, 'PERM_MANAGE_USERS');
INSERT INTO permission (id, name) VALUES (6, 'PERM_MANAGE_ROLES');
INSERT INTO permission (id, name) VALUES (7, 'PERM_PRICE');
INSERT INTO permission (id, name) VALUES (8, 'PERM_RULE');
INSERT INTO role (id, name) VALUES (0, 'ROLE_SUPERADMIN');
INSERT INTO role (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO role (id, name) VALUES (2, 'ROLE_ADMIN');
INSERT INTO role (id, name) VALUES (3, 'ROLE_REGISTERED');
INSERT INTO role_permission (role_id, permission_id) VALUES ((SELECT id FROM role WHERE name = 'ROLE_REGISTERED'), (SELECT id FROM permission WHERE name =  'PERM_CHANGE_PASSWORD'));
INSERT INTO role_permission (role_id, permission_id) VALUES ((SELECT id FROM role WHERE name = 'ROLE_ADMIN'), (SELECT id FROM permission WHERE name =  'PERM_RES_EDIT'));
INSERT INTO role_permission (role_id, permission_id) VALUES ((SELECT id FROM role WHERE name = 'ROLE_ADMIN'), (SELECT id FROM permission WHERE name =  'PERM_SCH_EDIT'));
INSERT INTO role_permission (role_id, permission_id) VALUES ((SELECT id FROM role WHERE name = 'ROLE_ADMIN'), (SELECT id FROM permission WHERE name =  'PERM_MANAGE_USERS'));
INSERT INTO role_permission (role_id, permission_id) VALUES ((SELECT id FROM role WHERE name = 'ROLE_ADMIN'), (SELECT id FROM permission WHERE name =  'PERM_PRICE'));
INSERT INTO role_permission (role_id, permission_id) VALUES ((SELECT id FROM role WHERE name = 'ROLE_ADMIN'), (SELECT id FROM permission WHERE name =  'PERM_RULE'));
INSERT INTO role_permission (role_id, permission_id) VALUES ((SELECT id FROM role WHERE name = 'ROLE_USER'), (SELECT id FROM permission WHERE name =  'PERM_RESERVE'));
INSERT INTO role_permission (role_id, permission_id) VALUES ((SELECT id FROM role WHERE name = 'ROLE_SUPERADMIN'), (SELECT id FROM permission WHERE name = 'PERM_MANAGE_ROLES'));
INSERT INTO role_permission (role_id, permission_id) VALUES ((SELECT id FROM role WHERE name = 'ROLE_USER'), (SELECT id FROM permission WHERE name =  'PERM_GOOGLE'));
INSERT INTO account
(first_name,
last_name,
email,
password)
VALUES
(
'Superadmin',
'account',
'superadmin@account.test',
'db22fff3dcc4e38895efc67d1daae577844d2c91a00c62487e293114c9230bc21a9b922991b063cb'
);
INSERT INTO account
(
first_name,
last_name,
email,
password)
VALUES
(
'Admin',
'account',
'admin@account.test',
'db22fff3dcc4e38895efc67d1daae577844d2c91a00c62487e293114c9230bc21a9b922991b063cb'
);
INSERT INTO account
(
first_name,
last_name,
email,
password)
VALUES
(
'User',
'account',
'user@account.test',
'db22fff3dcc4e38895efc67d1daae577844d2c91a00c62487e293114c9230bc21a9b922991b063cb'
);
INSERT INTO account_role (account_id, role_id) VALUES ((SELECT id FROM account WHERE email = 'superadmin@account.test'), (SELECT id FROM role WHERE name =  'ROLE_SUPERADMIN'));
INSERT INTO account_role (account_id, role_id) VALUES ((SELECT id FROM account WHERE email = 'superadmin@account.test'), (SELECT id FROM role WHERE name =  'ROLE_ADMIN'));
INSERT INTO account_role (account_id, role_id) VALUES ((SELECT id FROM account WHERE email = 'superadmin@account.test'), (SELECT id FROM role WHERE name =  'ROLE_REGISTERED'));
INSERT INTO account_role (account_id, role_id) VALUES ((SELECT id FROM account WHERE email = 'admin@account.test'), (SELECT id FROM role WHERE name =  'ROLE_ADMIN'));
INSERT INTO account_role (account_id, role_id) VALUES ((SELECT id FROM account WHERE email = 'admin@account.test'), (SELECT id FROM role WHERE name =  'ROLE_REGISTERED'));
INSERT INTO account_role (account_id, role_id) VALUES ((SELECT id FROM account WHERE email = 'user@account.test'), (SELECT id FROM role WHERE name =  'ROLE_USER'));
INSERT INTO account_role (account_id, role_id) VALUES ((SELECT id FROM account WHERE email = 'user@account.test'), (SELECT id FROM role WHERE name =  'ROLE_REGISTERED'));