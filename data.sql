call createPermission('PERM_CHANGE_PASSWORD');
call createRole('ROLE_USER', @role_user);
call createRole('ROLE_ADMIN', @role_admin);
call createRole('ROLE_REGISTERED', @role_registered);
call roleHasPermission(@role_registered, 'PERM_CHANGE_PASSWORD');