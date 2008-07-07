-- Sequence: data_object_type_seq

-- DROP SEQUENCE data_object_type_seq;

CREATE SEQUENCE data_object_type_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 2147483647
  START 36
  CACHE 1;
ALTER TABLE data_object_type_seq OWNER TO "postgres";

-- Sequence: data_objects_seq

-- DROP SEQUENCE data_objects_seq;

CREATE SEQUENCE data_objects_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 999999999999999999
  START 2
  CACHE 1;
ALTER TABLE data_objects_seq OWNER TO "postgres";

-- Sequence: hello_world_hello_world_id_seq

-- DROP SEQUENCE hello_world_hello_world_id_seq;

CREATE SEQUENCE hello_world_hello_world_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE hello_world_hello_world_id_seq OWNER TO "postgres";

-- Sequence: sequence_identifiers_seq_id_key_seq

-- DROP SEQUENCE sequence_identifiers_seq_id_key_seq;

CREATE SEQUENCE sequence_identifiers_seq_id_key_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE sequence_identifiers_seq_id_key_seq OWNER TO "postgres";

-- Table: "TestTable"

-- DROP TABLE "TestTable";

CREATE TABLE "TestTable"
(
  test_id numeric(18) NOT NULL,
  test_name character varying(64),
  CONSTRAINT pk_test_table PRIMARY KEY (test_id),
  CONSTRAINT fk_test_id_do_id FOREIGN KEY (test_id)
      REFERENCES data_objects (data_object_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (OIDS=FALSE);
ALTER TABLE "TestTable" OWNER TO "postgres";

-- Table: data_object_types

-- DROP TABLE data_object_types;

CREATE TABLE data_object_types
(
  data_object_type_id integer NOT NULL,
  data_object_type character varying(255) NOT NULL,
  notes text,
  small_image_uri character varying(1024),
  small_image bytea,
  medium_image_uri character varying(1024),
  medium_image bytea,
  CONSTRAINT pk_data_object_types PRIMARY KEY (data_object_type_id),
  CONSTRAINT uk_dot_data_object_type UNIQUE (data_object_type)
)
WITH (OIDS=FALSE);
ALTER TABLE data_object_types OWNER TO "postgres";

-- Table: data_objects

-- DROP TABLE data_objects;

CREATE TABLE data_objects
(
  data_object_id numeric(18) NOT NULL,
  data_object_version integer NOT NULL,
  data_object_type_id integer NOT NULL,
  creation_time timestamp with time zone NOT NULL,
  creator_id bigint NOT NULL,
  owner_id bigint NOT NULL,
  is_deleted boolean NOT NULL DEFAULT false,
  is_read_only boolean NOT NULL DEFAULT false,
  is_system boolean NOT NULL DEFAULT false,
  is_folder boolean NOT NULL DEFAULT false,
  is_link boolean NOT NULL DEFAULT false,
  parent_data_object_id numeric(18),
  linked_data_object_id numeric(18),
  order_position character varying(10),
  child_counter integer,
  notes text,
  small_image_uri character varying(1024),
  small_image bytea,
  medium_image_uri character varying(1024),
  medium_image bytea,
  data_object_uri character varying(1024),
  CONSTRAINT pk_data_objects PRIMARY KEY (data_object_id),
  CONSTRAINT fk74e5117f6017f920 FOREIGN KEY (linked_data_object_id)
      REFERENCES data_objects (data_object_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk74e5117fdfc2026f FOREIGN KEY (parent_data_object_id)
      REFERENCES data_objects (data_object_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_data_objects_linked_data_object_id FOREIGN KEY (linked_data_object_id)
      REFERENCES data_objects (data_object_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_data_objects_parent_data_object_id FOREIGN KEY (parent_data_object_id)
      REFERENCES data_objects (data_object_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_do_linked_id FOREIGN KEY (linked_data_object_id)
      REFERENCES data_objects (data_object_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_do_parent_id FOREIGN KEY (parent_data_object_id)
      REFERENCES data_objects (data_object_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (OIDS=FALSE);
ALTER TABLE data_objects OWNER TO "postgres";

-- Table: hello_world

-- DROP TABLE hello_world;

CREATE TABLE hello_world
(
  hello_world_id bigserial NOT NULL,
  hello_world character varying(255),
  CONSTRAINT pk_hello_world PRIMARY KEY (hello_world_id)
)
WITH (OIDS=FALSE);
ALTER TABLE hello_world OWNER TO "postgres";

-- Table: object_identifiers

-- DROP TABLE object_identifiers;

CREATE TABLE object_identifiers
(
  object_id numeric(36) NOT NULL,
  deleted boolean,
  CONSTRAINT pk_object_identifiers PRIMARY KEY (object_id)
)
WITH (OIDS=FALSE);
ALTER TABLE object_identifiers OWNER TO "postgres";

-- Table: products

-- DROP TABLE products;

CREATE TABLE products
(
  product_id numeric(18) NOT NULL,
  parent_id numeric(18),
  category_id numeric(18) NOT NULL,
  product_name character varying(100) NOT NULL,
  product_code character varying(50) NOT NULL,
  measure_unit_id integer NOT NULL,
  is_complex boolean NOT NULL DEFAULT false,
  is_purchased boolean NOT NULL DEFAULT false,
  is_salable boolean NOT NULL DEFAULT true,
  is_obsolete boolean NOT NULL DEFAULT false,
  pattern_format_id integer,
  product_color character varying(45),
  minimum_quantity numeric(19,4) NOT NULL DEFAULT 1,
  maximum_quantity numeric(19,4),
  default_quantity numeric(19,4),
  purchase_price numeric(19,4) NOT NULL,
  sale_price numeric(19,4) NOT NULL,
  list_price numeric(19,4) NOT NULL,
  quantity_per_package integer NOT NULL DEFAULT 1,
  dimension_unit_id integer,
  dimension_width numeric(7,2),
  dimension_length numeric(7,2),
  dimension_height numeric(7,2),
  weight_unit_id integer,
  weight numeric(13,3),
  delivery_time integer,
  description text,
  producer_id numeric(18),
  CONSTRAINT pk_products PRIMARY KEY (product_id),
  CONSTRAINT fk_products_product_id FOREIGN KEY (product_id)
      REFERENCES data_objects (data_object_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (OIDS=FALSE);
ALTER TABLE products OWNER TO "postgres";

-- Table: sequence_identifiers

-- DROP TABLE sequence_identifiers;

CREATE TABLE sequence_identifiers
(
  seq_id_key bigserial NOT NULL,
  seq_id_name character varying(64) NOT NULL,
  seq_id_value numeric(38) NOT NULL DEFAULT 0,
  CONSTRAINT uk_seq_id_name UNIQUE (seq_id_name)
)
WITH (OIDS=FALSE);
ALTER TABLE sequence_identifiers OWNER TO "postgres";

-- Table: users

-- DROP TABLE users;

CREATE TABLE users
(
  user_id bigint NOT NULL,
  "version" integer NOT NULL,
  user_name character varying(32) NOT NULL,
  email_address character varying(64) NOT NULL,
  user_password character varying(64) NOT NULL,
  system_password character varying(64),
  system_password_validity date,
  is_active boolean NOT NULL DEFAULT true,
  is_new boolean NOT NULL DEFAULT true,
  creation_time time with time zone NOT NULL,
  creator_id bigint NOT NULL,
  person_id numeric(18),
  description text,
  small_image_uri character varying(1024),
  small_image bytea,
  medium_image_uri character varying(1024),
  medium_image bytea,
  user_uri character varying(1024),
  next_action_after_login character varying(1024),
  CONSTRAINT pk_users PRIMARY KEY (user_id),
  CONSTRAINT fk6a68e0844bb6904 FOREIGN KEY (creator_id)
      REFERENCES users (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk6a68e08a08870b9 FOREIGN KEY (person_id)
      REFERENCES data_objects (data_object_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_users_creator_id FOREIGN KEY (creator_id)
      REFERENCES users (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_users_person_id FOREIGN KEY (person_id)
      REFERENCES data_objects (data_object_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_users_email_address UNIQUE (email_address),
  CONSTRAINT uk_users_user_name UNIQUE (user_name)
)
WITH (OIDS=FALSE);
ALTER TABLE users OWNER TO "postgres";
