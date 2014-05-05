CREATE TABLE account (
	id BIGINT NOT NULL /*! AUTO_INCREMENT */,
	first_name VARCHAR(50) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	password VARCHAR(80),
	enabled BOOLEAN NOT NULL,
	google_credentials VARCHAR(200),
	calendar_ok BOOLEAN NOT NULL,
	google_plus_ok BOOLEAN NOT NULL,
	credit DECIMAL(6,2) NOT NULL DEFAULT 0.0,
	internal VARCHAR(1000),
	date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	date_modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	UNIQUE (email),
	CONSTRAINT account_pk PRIMARY KEY (id)
);
CREATE SEQUENCE account_id_seq;
ALTER TABLE account ALTER COLUMN id SET DEFAULT nextval('account_id_seq');

CREATE TABLE account_group (
	id SMALLINT NOT NULL /*! AUTO_INCREMENT */,
	name VARCHAR(50) NOT NULL unique,
	CONSTRAINT account_group_pk PRIMARY KEY (id)
);
CREATE SEQUENCE account_group_id_seq;
ALTER TABLE account_group ALTER COLUMN id SET DEFAULT nextval('account_group_id_seq');

CREATE TABLE role (
	id SMALLINT NOT NULL /*! AUTO_INCREMENT */,
	name VARCHAR(50) NOT NULL unique,
	CONSTRAINT role_pk PRIMARY KEY (id)
);
CREATE SEQUENCE role_id_seq;
ALTER TABLE role ALTER COLUMN id SET DEFAULT nextval('role_id_seq');

CREATE TABLE permission (
	id SMALLINT NOT NULL /*! AUTO_INCREMENT */,
	name VARCHAR(50) NOT NULL unique,
	CONSTRAINT permission_pk PRIMARY KEY (id)
);
CREATE SEQUENCE permission_id_seq;
ALTER TABLE permission ALTER COLUMN id SET DEFAULT nextval('permission_id_seq');

CREATE TABLE account_role (
	id SMALLINT NOT NULL /*! AUTO_INCREMENT */,
	account_id BIGINT NOT NULL,
	role_id SMALLINT NOT NULL,
	FOREIGN KEY (account_id) REFERENCES account (id) ON DELETE CASCADE,
	FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE,
	UNIQUE (account_id, role_id),
	CONSTRAINT account_role_pk PRIMARY KEY (id)
);
CREATE SEQUENCE account_role_id_seq;
ALTER TABLE account_role ALTER COLUMN id SET DEFAULT nextval('account_role_id_seq');

CREATE TABLE role_permission (
	id SMALLINT NOT NULL /*! AUTO_INCREMENT */,
	role_id SMALLINT NOT NULL,
	permission_id SMALLINT NOT NULL,
	FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE CASCADE,
	FOREIGN KEY (permission_id) REFERENCES permission (id) ON DELETE CASCADE,
	UNIQUE (role_id, permission_id),
	CONSTRAINT role_permission_pk PRIMARY KEY (id)
);
CREATE SEQUENCE role_permission_id_seq;
ALTER TABLE role_permission ALTER COLUMN id SET DEFAULT nextval('role_permission_id_seq');

CREATE TABLE resource (
	id BIGINT NOT NULL /*! AUTO_INCREMENT */,
	visible BOOLEAN NOT NULL,
	price DECIMAL(10,2),
	capacity INTEGER NOT NULL,
	duration INTEGER NOT NULL,
	CONSTRAINT resource_pk PRIMARY KEY (id)
);
CREATE SEQUENCE resource_id_seq;
ALTER TABLE resource ALTER COLUMN id SET DEFAULT nextval('resource_id_seq');

CREATE TABLE resource_property (
	id BIGINT NOT NULL /*! AUTO_INCREMENT */,
	name VARCHAR(50) NOT NULL,
	type SMALLINT NOT NULL,
	DEFAULT_value VARCHAR(1000),
	UNIQUE (name),
	CONSTRAINT resource_property_pk PRIMARY KEY (id)
);
CREATE SEQUENCE resource_property_id_seq;
ALTER TABLE resource_property ALTER COLUMN id SET DEFAULT nextval('resource_property_id_seq');

CREATE TABLE resource_property_value (
	id BIGINT NOT NULL /*! AUTO_INCREMENT */,
	value VARCHAR(5000),
	resource_id BIGINT NOT NULL,
	resource_property_id BIGINT NOT NULL, 
	FOREIGN KEY (resource_id) REFERENCES resource (id) ON DELETE CASCADE,
	FOREIGN KEY (resource_property_id) REFERENCES resource_property (id) ON DELETE CASCADE,
	UNIQUE (resource_id, resource_property_id),
	CONSTRAINT resource_property_value_pk PRIMARY KEY (id)
);
CREATE SEQUENCE resource_property_value_id_seq;
ALTER TABLE resource_property_value ALTER COLUMN id SET DEFAULT nextval('resource_property_value_id_seq');

CREATE TABLE schedule (
	id BIGINT NOT NULL /*! AUTO_INCREMENT */,
	start TIMESTAMP NOT NULL,
	duration INTEGER,
	capacity INTEGER,
	note VARCHAR(1000),
	resource_id BIGINT NOT NULL,
	visible BOOLEAN NOT NULL,
	CONSTRAINT schedule_pk PRIMARY KEY (id),
	FOREIGN KEY (resource_id) REFERENCES resource (id) ON DELETE CASCADE
);
CREATE SEQUENCE schedule_id_seq;
ALTER TABLE schedule ALTER COLUMN id SET DEFAULT nextval('schedule_id_seq');

CREATE TABLE reservation (
	id BIGINT NOT NULL /*! AUTO_INCREMENT */,
	status SMALLINT,
	date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	date_canceled TIMESTAMP,
	schedule_id BIGINT NOT NULL,
	account_id BIGINT NOT NULL,
	calendar_event_id VARCHAR(100),
	google_plus_moment_id VARCHAR(100),
	CONSTRAINT reservation_pk PRIMARY KEY (id),
	FOREIGN KEY (schedule_id) REFERENCES schedule (id) ON DELETE CASCADE,
	FOREIGN KEY (account_id) REFERENCES account (id) ON DELETE CASCADE
);
CREATE SEQUENCE reservation_id_seq;
ALTER TABLE reservation ALTER COLUMN id SET DEFAULT nextval('reservation_id_seq');

CREATE TABLE price_change (
	id BIGINT NOT NULL /*! AUTO_INCREMENT */,
	value DECIMAL(10,2) NOT NULL,
	type SMALLINT NOT NULL,
	name VARCHAR(50) NOT NULL,
	CONSTRAINT price_change_pk PRIMARY KEY (id)
);
CREATE SEQUENCE price_change_seq;
ALTER TABLE price_change ALTER COLUMN id SET DEFAULT nextval('price_change_seq');

CREATE TABLE rule (
	id BIGINT NOT NULL /*! AUTO_INCREMENT */,
	expression VARCHAR(1000) NOT NULL,
	action SMALLINT NOT NULL,
	name VARCHAR(50) NOT NULL,
	enabled BOOLEAN NOT NULL,
	price_change_id BIGINT NOT NULL,
	CONSTRAINT rule_pk PRIMARY KEY (id),
	FOREIGN KEY (price_change_id) REFERENCES price_change (id) ON DELETE CASCADE
);
CREATE SEQUENCE rule_id_seq;
ALTER TABLE rule ALTER COLUMN id SET DEFAULT nextval('rule_id_seq');
