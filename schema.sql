drop database ubs;
create database ubs DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;
use ubs;

create table account (
    id bigint unsigned not null auto_increment primary key,
    username varchar(50) unique not null,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    email varchar(50) not null,
    password varchar(80),
    marketing_ok boolean not null,
    accept_terms boolean not null,
    enabled boolean not null,
    date_created timestamp default 0,
    date_modified timestamp default current_timestamp on update current_timestamp,
    unique index account_idx_1 (username),
    unique index account_idx_2 (email)
) engine = InnoDb;


create table role (
    id smallint unsigned not null auto_increment primary key,
    name varchar(50) not null unique
) engine = InnoDb;

create table permission (
    id smallint unsigned not null auto_increment primary key,
    name varchar(50) not null
) engine = InnoDb;

create table account_role (
    id smallint unsigned not null auto_increment primary key,
    account_id bigint unsigned not null,
    role_id smallint unsigned not null,
    foreign key (account_id) references account (id) on delete cascade,
    foreign key (role_id) references role(id) on delete cascade,
    unique index account_role_idx_1 (account_id, role_id)
) engine = InnoDb;

create table role_permission (
    id smallint unsigned not null auto_increment primary key,
    role_id smallint unsigned not null,
    permission_id smallint unsigned not null,
    foreign key (role_id) references role (id) on delete cascade,
    foreign key (permission_id) references permission (id) on delete cascade,
    unique index role_permission_idx1 (role_id, permission_id)
) engine = InnoDb;

delimiter //

create procedure createPermission($name varchar(50))
begin
    insert into permission (name) values ($name);
end //

create procedure createRole($name varchar(50), out $id smallint)
begin
    insert into role (name) values ($name);
    set $id := last_insert_id();
end //

create procedure roleHasPermission($role_id smallint, $perm_name varchar(50))
begin
    declare _perm_id int;
    select id from permission where name = $perm_name into _perm_id;
    insert into role_permission (role_id, permission_id) values ($role_id, _perm_id);
end //

create procedure createAccount($name varchar(50), $first_name varchar(50), $last_name varchar(50), $email varchar(50), out $id int)
begin
    insert into account (username, password, first_name, last_name, email, enabled) values ($name, 'p@ssword', $first_name, $last_name, $email, 1);
    set $id := last_insert_id();
end //

create procedure accountHasRole($account_id int, $role_id smallint)
begin
    insert into account_role (account_id, role_id) values ($account_id, $role_id);
end //