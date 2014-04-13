drop database ubs;
create database ubs DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;
use ubs;

create table account (
    id bigint unsigned not null auto_increment primary key,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    email varchar(50) not null,
    password varchar(80),
    marketing_ok boolean not null,
    accept_terms boolean not null,
    enabled boolean not null,
	google_credentials varchar(200),
    calendar_ok boolean not null,
    google_plus_ok boolean not null,
    email_ok boolean not null,
    credit decimal(6,2) not null default 0.0,
    group_id smallint unsigned default null,
    date_created timestamp default 0,
    date_modified timestamp default current_timestamp on update current_timestamp,
    unique index account_id (email)
) engine = InnoDb;

create table account_group (
	id smallint unsigned not null auto_increment primary key,
    name varchar(50) not null unique
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
    unique index account_id_role_id (account_id, role_id)
) engine = InnoDb;

create table role_permission (
    id smallint unsigned not null auto_increment primary key,
    role_id smallint unsigned not null,
    permission_id smallint unsigned not null,
    foreign key (role_id) references role (id) on delete cascade,
    foreign key (permission_id) references permission (id) on delete cascade,
    unique index role_id_permission_id (role_id, permission_id)
) engine = InnoDb;

create table resource (
	id bigint unsigned not null auto_increment primary key,
	visible boolean not null,
    price decimal(10,2),
    capacity int(10) not null,
    duration int(10) not null
) engine = InnoDb;

create table resource_property (
	id bigint unsigned not null auto_increment primary key,
	name varchar(50),
    type smallint not null,
    default_value varchar(1000),
	unique index name (name)
) engine = InnoDb;

create table resource_property_value (
	id bigint unsigned not null auto_increment primary key,
	value varchar(1000),
	resource_id bigint unsigned not null,
	resource_property_id bigint unsigned not null, 
    foreign key (resource_id) references resource (id) on delete cascade,
	foreign key (resource_property_id) references resource_property (id) on delete cascade,
	unique index resource_id_resource_property_id (resource_id, resource_property_id) 
) engine = InnoDb;

create table schedule (
	id bigint unsigned not null auto_increment primary key,
    start timestamp not null,
    duration int(10),
    capacity int(10),
    note varchar(1000),
    resource_id bigint unsigned not null,
    visible boolean not null,
	foreign key (resource_id) references resource (id) on delete cascade
) engine = InnoDb;

create table reservation (
	id bigint unsigned not null auto_increment primary key,
	status smallint,
    amount int(10),
	date_created timestamp default 0,
	date_canceled timestamp default 0,
	schedule_id bigint unsigned not null,
    account_id bigint unsigned not null,
	calendar_event_id varchar(100),
	google_plus_moment_id varchar(100),
	foreign key (schedule_id) references schedule (id) on delete cascade,
    foreign key (account_id) references account (id) on delete cascade
) engine = InnoDb;

create table price_change (
	id bigint unsigned not null auto_increment primary key,
	value DECIMAL(10,2),
    type smallint,
    name varchar(50)
) engine = InnoDb;

create table rule (
	id bigint unsigned not null auto_increment primary key,
	expression varchar(1000),
	action smallint,
	name varchar(50),
    enabled boolean,
    price_change_id bigint unsigned not null,
	foreign key (price_change_id) references price_change (id) on delete cascade
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

create procedure createAccount($pass varchar(80), $first_name varchar(50), $last_name varchar(50), $email varchar(50), out $id int)
begin
    insert into account (password, first_name, last_name, email, enabled) values ($pass, $first_name, $last_name, $email, 1, $);
    set $id := last_insert_id();
end //

create procedure accountHasRole($account_id int, $role_id smallint)
begin
    insert into account_role (account_id, role_id) values ($account_id, $role_id);
end //