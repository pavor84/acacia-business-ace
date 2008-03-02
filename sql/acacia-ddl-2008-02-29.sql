--
-- PostgreSQL database dump
--

-- Started on 2008-02-29 13:56:30

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 369 (class 2612 OID 24991)
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: -
--

--CREATE PROCEDURAL LANGUAGE plpgsql;


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 1567 (class 1259 OID 25369)
-- Dependencies: 6
-- Name: addresses; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE addresses (
    address_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0),
    address_name character varying(64) NOT NULL,
    country_id integer,
    city_id integer,
    postal_code character varying(16),
    postal_address character varying(128),
    description text
);


--
-- TOC entry 252 (class 1247 OID 24994)
-- Dependencies: 6 1541
-- Name: breakpoint; Type: TYPE; Schema: public; Owner: -
--

/*CREATE TYPE breakpoint AS (
	func oid,
	linenumber integer,
	targetname text
);*/


--
-- TOC entry 1569 (class 1259 OID 25397)
-- Dependencies: 6
-- Name: cities; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE cities (
    city_id integer NOT NULL,
    country_id integer NOT NULL,
    city_name character varying(64) NOT NULL,
    postal_code character varying(8),
    city_code character varying(3),
    city_phone_code character varying(6),
    description text
);


--
-- TOC entry 1542 (class 1259 OID 24995)
-- Dependencies: 6
-- Name: classified_objects; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE classified_objects (
    classifier_id numeric(18,0) NOT NULL,
    classified_object_id numeric(18,0) NOT NULL,
    description text
);


--
-- TOC entry 1543 (class 1259 OID 25001)
-- Dependencies: 6
-- Name: classifier_applied_for_dot; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE classifier_applied_for_dot (
    classifier_id numeric(18,0) NOT NULL,
    data_object_type_id integer NOT NULL
);


--
-- TOC entry 1544 (class 1259 OID 25004)
-- Dependencies: 1841 6
-- Name: classifier_groups; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE classifier_groups (
    classifier_group_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0),
    is_system_group boolean DEFAULT false NOT NULL,
    classifier_group_code character varying(32) NOT NULL,
    classifier_group_name character varying(100) NOT NULL,
    description text
);


--
-- TOC entry 1545 (class 1259 OID 25011)
-- Dependencies: 6
-- Name: classifiers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE classifiers (
    classifier_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0),
    classifier_group_id numeric(18,0) NOT NULL,
    classifier_code character varying(32) NOT NULL,
    classifier_name character varying(128) NOT NULL,
    description text
);


--
-- TOC entry 1568 (class 1259 OID 25387)
-- Dependencies: 6
-- Name: countries; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE countries (
    country_id integer NOT NULL,
    country_name character varying(64) NOT NULL,
    country_code_a2 character(2),
    country_code_a3 character(3),
    country_code_n3 character(3),
    country_phone_code character varying(6),
    currency_id integer,
    description text
);


--
-- TOC entry 1570 (class 1259 OID 25432)
-- Dependencies: 6
-- Name: currencies; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE currencies (
    currency_id integer NOT NULL,
    currency_name character varying(64) NOT NULL,
    currency_code_a3 character(3),
    currency_code_n3 character(3),
    description text
);


--
-- TOC entry 1546 (class 1259 OID 25017)
-- Dependencies: 6
-- Name: data_object_types; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE data_object_types (
    data_object_type_id integer NOT NULL,
    data_object_type character varying(255) NOT NULL,
    notes text,
    small_image_uri character varying(1024),
    small_image bytea,
    medium_image_uri character varying(1024),
    medium_image bytea
);


--
-- TOC entry 1547 (class 1259 OID 25023)
-- Dependencies: 1842 1843 1844 1845 1846 1847 6
-- Name: data_objects; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE data_objects (
    data_object_id numeric(18,0) NOT NULL,
    data_object_version integer NOT NULL,
    data_object_type_id integer NOT NULL,
    creation_time timestamp with time zone DEFAULT now() NOT NULL,
    creator_id bigint NOT NULL,
    owner_id bigint NOT NULL,
    is_deleted boolean DEFAULT false NOT NULL,
    is_read_only boolean DEFAULT false NOT NULL,
    is_system boolean DEFAULT false NOT NULL,
    is_folder boolean DEFAULT false NOT NULL,
    is_link boolean DEFAULT false NOT NULL,
    parent_data_object_id numeric(18,0),
    linked_data_object_id numeric(18,0),
    order_position character varying(10),
    child_counter integer,
    notes text,
    small_image_uri character varying(1024),
    small_image bytea,
    medium_image_uri character varying(1024),
    medium_image bytea,
    data_object_uri character varying(1024)
);


--
-- TOC entry 1548 (class 1259 OID 25035)
-- Dependencies: 6
-- Name: enum_classes; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE enum_classes (
    enum_class_id integer NOT NULL,
    enum_class_name character varying(255) NOT NULL
);


--
-- TOC entry 310 (class 1247 OID 25040)
-- Dependencies: 6 1549
-- Name: frame; Type: TYPE; Schema: public; Owner: -
--

/*CREATE TYPE frame AS (
	level integer,
	targetname text,
	func oid,
	linenumber integer,
	args text
);*/


--
-- TOC entry 1566 (class 1259 OID 25337)
-- Dependencies: 6
-- Name: organizations; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE organizations (
    organization_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0),
    organization_type_id integer NOT NULL,
    organization_name character varying(128) NOT NULL,
    nickname character varying(16),
    vat_number character varying(16),
    unique_identifier_code character varying(16),
    registration_date date,
    registration_organization_id numeric(18,0),
    registration_address_id numeric(18,0),
    administration_address_id numeric(18,0),
    share_capital numeric(19,4),
    currency_id integer,
    description text
);


--
-- TOC entry 1550 (class 1259 OID 25041)
-- Dependencies: 6
-- Name: pattern_mask_formats; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE pattern_mask_formats (
    pattern_mask_format_id integer NOT NULL,
    owner_id numeric(18,0),
    pattern_name character varying(64) NOT NULL,
    format_type character(1) NOT NULL,
    format character varying(128) NOT NULL,
    description text
);


--
-- TOC entry 2018 (class 0 OID 0)
-- Dependencies: 1550
-- Name: COLUMN pattern_mask_formats.format_type; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN pattern_mask_formats.format_type IS 'D for DateFormatter;
N for NumberFormatter;
M for MaskFormatter.';


--
-- TOC entry 1571 (class 1259 OID 25447)
-- Dependencies: 6
-- Name: persons; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE persons (
    person_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0),
    first_name character varying(16) NOT NULL,
    second_name character varying(16),
    last_name character varying(16) NOT NULL,
    extra_name character varying(16),
    personal_unique_id character varying(16),
    birth_date date,
    birth_place_country_id integer,
    birth_place_city_id integer,
    gender_id integer,
    description text
);


--
-- TOC entry 1551 (class 1259 OID 25047)
-- Dependencies: 6
-- Name: product_categories; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE product_categories (
    product_category_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0),
    category_name character varying(100) NOT NULL,
    pattern_mask_format_id integer,
    description text
);


--
-- TOC entry 1552 (class 1259 OID 25053)
-- Dependencies: 6
-- Name: product_suppliers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE product_suppliers (
    product_id numeric(18,0) NOT NULL,
    supplier_id numeric(18,0) NOT NULL,
    description text
);


--
-- TOC entry 1553 (class 1259 OID 25059)
-- Dependencies: 1848 1849 1850 1851 1852 1853 6
-- Name: products; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE products (
    product_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0),
    category_id numeric(18,0) NOT NULL,
    product_name character varying(100) NOT NULL,
    product_code character varying(50) NOT NULL,
    measure_unit_id integer NOT NULL,
    is_complex boolean DEFAULT false NOT NULL,
    is_purchased boolean DEFAULT false NOT NULL,
    is_salable boolean DEFAULT true NOT NULL,
    is_obsolete boolean DEFAULT false NOT NULL,
    pattern_mask_format_id integer,
    product_color_id integer,
    minimum_quantity numeric(19,4) DEFAULT 1 NOT NULL,
    maximum_quantity numeric(19,4),
    default_quantity numeric(19,4),
    purchase_price numeric(19,4) NOT NULL,
    sale_price numeric(19,4) NOT NULL,
    list_price numeric(19,4) NOT NULL,
    quantity_per_package integer DEFAULT 1 NOT NULL,
    dimension_unit_id integer,
    dimension_width numeric(7,2),
    dimension_length numeric(7,2),
    dimension_height numeric(7,2),
    weight_unit_id integer,
    weight numeric(13,3),
    delivery_time integer,
    description text,
    producer_id numeric(18,0)
);


--
-- TOC entry 324 (class 1247 OID 25073)
-- Dependencies: 6 1554
-- Name: proxyinfo; Type: TYPE; Schema: public; Owner: -
--

/*CREATE TYPE proxyinfo AS (
	serverversionstr text,
	serverversionnum integer,
	proxyapiver integer,
	serverprocessid integer
);*/


--
-- TOC entry 1574 (class 1259 OID 25534)
-- Dependencies: 1858 6
-- Name: purchase_orders; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE purchase_orders (
    purchase_order_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0),
    order_number integer NOT NULL,
    supplier_id numeric(18,0) NOT NULL,
    supplier_order_number character varying(16),
    supplier_contact_id numeric(18,0),
    status_id integer NOT NULL,
    creation_time date DEFAULT now() NOT NULL,
    creator_id numeric(18,0) NOT NULL,
    sent_time date,
    sender_id numeric(18,0),
    first_delivery_time date,
    last_delivery_time date,
    finalizing_time date
);


--
-- TOC entry 1555 (class 1259 OID 25074)
-- Dependencies: 6
-- Name: resource_bundle; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE resource_bundle (
    resource_id integer NOT NULL,
    enum_class_id integer NOT NULL,
    enum_name character varying(64) NOT NULL
);


--
-- TOC entry 1556 (class 1259 OID 25077)
-- Dependencies: 1854 6
-- Name: sequence_identifiers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE sequence_identifiers (
    seq_id_key bigint NOT NULL,
    seq_id_name character varying(64) NOT NULL,
    seq_id_value numeric(38,0) DEFAULT 0 NOT NULL
);


--
-- TOC entry 330 (class 1247 OID 25083)
-- Dependencies: 6 1557
-- Name: targetinfo; Type: TYPE; Schema: public; Owner: -
--

/*CREATE TYPE targetinfo AS (
	target oid,
	schema oid,
	nargs integer,
	argtypes oidvector,
	targetname name,
	argmodes "char"[],
	argnames text[],
	targetlang oid,
	fqname text,
	returnsset boolean,
	returntype oid
);*/


--
-- TOC entry 1558 (class 1259 OID 25084)
-- Dependencies: 1856 1857 6
-- Name: users; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE users (
    user_id bigint NOT NULL,
    version integer NOT NULL,
    user_name character varying(32) NOT NULL,
    email_address character varying(64) NOT NULL,
    user_password character varying(64) NOT NULL,
    system_password character varying(64),
    system_password_validity date,
    is_active boolean DEFAULT true NOT NULL,
    is_new boolean DEFAULT true NOT NULL,
    creation_time time with time zone NOT NULL,
    creator_id bigint NOT NULL,
    person_id numeric(18,0),
    description text,
    small_image_uri character varying(1024),
    small_image bytea,
    medium_image_uri character varying(1024),
    medium_image bytea,
    user_uri character varying(1024),
    next_action_after_login character varying(1024)
);


--
-- TOC entry 335 (class 1247 OID 25094)
-- Dependencies: 6 1559
-- Name: var; Type: TYPE; Schema: public; Owner: -
--

/*CREATE TYPE var AS (
	name text,
	varclass character(1),
	linenumber integer,
	isunique boolean,
	isconst boolean,
	isnotnull boolean,
	dtype oid,
	value text
);*/


--
-- TOC entry 1573 (class 1259 OID 25516)
-- Dependencies: 6
-- Name: warehouse_products; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE warehouse_products (
    warehouse_id numeric(18,0) NOT NULL,
    product_id numeric(18,0) NOT NULL,
    quantity_in_stock numeric(19,4),
    ordered_quantity numeric(19,4),
    reserved_quantity numeric(19,4),
    sold_quantity numeric(19,4),
    quantity_due numeric(19,4),
    notes text
);


--
-- TOC entry 1572 (class 1259 OID 25489)
-- Dependencies: 6
-- Name: warehouses; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE warehouses (
    warehouse_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0),
    address_id numeric(18,0) NOT NULL,
    warehouseman_id numeric(18,0),
    description text
);


--
-- TOC entry 1560 (class 1259 OID 25115)
-- Dependencies: 6
-- Name: data_object_type_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE data_object_type_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2019 (class 0 OID 0)
-- Dependencies: 1560
-- Name: data_object_type_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('data_object_type_seq', 36, true);


--
-- TOC entry 1561 (class 1259 OID 25117)
-- Dependencies: 6
-- Name: data_objects_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE data_objects_seq
    INCREMENT BY 1
    MAXVALUE 999999999999999999
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2020 (class 0 OID 0)
-- Dependencies: 1561
-- Name: data_objects_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('data_objects_seq', 2, true);


--
-- TOC entry 1562 (class 1259 OID 25119)
-- Dependencies: 6
-- Name: enum_classes_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE enum_classes_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2021 (class 0 OID 0)
-- Dependencies: 1562
-- Name: enum_classes_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('enum_classes_seq', 1, true);


--
-- TOC entry 1563 (class 1259 OID 25121)
-- Dependencies: 6
-- Name: pattern_mask_formats_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE pattern_mask_formats_seq
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2022 (class 0 OID 0)
-- Dependencies: 1563
-- Name: pattern_mask_formats_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('pattern_mask_formats_seq', 1, false);


--
-- TOC entry 1564 (class 1259 OID 25123)
-- Dependencies: 6
-- Name: resource_bundle_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE resource_bundle_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2023 (class 0 OID 0)
-- Dependencies: 1564
-- Name: resource_bundle_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('resource_bundle_seq', 18, true);


--
-- TOC entry 1565 (class 1259 OID 25125)
-- Dependencies: 1556 6
-- Name: sequence_identifiers_seq_id_key_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE sequence_identifiers_seq_id_key_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2024 (class 0 OID 0)
-- Dependencies: 1565
-- Name: sequence_identifiers_seq_id_key_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE sequence_identifiers_seq_id_key_seq OWNED BY sequence_identifiers.seq_id_key;


--
-- TOC entry 2025 (class 0 OID 0)
-- Dependencies: 1565
-- Name: sequence_identifiers_seq_id_key_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('sequence_identifiers_seq_id_key_seq', 1, false);


--
-- TOC entry 1855 (class 2604 OID 25336)
-- Dependencies: 1565 1556
-- Name: seq_id_key; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE sequence_identifiers ALTER COLUMN seq_id_key SET DEFAULT nextval('sequence_identifiers_seq_id_key_seq'::regclass);


--
-- TOC entry 2005 (class 0 OID 25369)
-- Dependencies: 1567
-- Data for Name: addresses; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2007 (class 0 OID 25397)
-- Dependencies: 1569
-- Data for Name: cities; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1990 (class 0 OID 24995)
-- Dependencies: 1542
-- Data for Name: classified_objects; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1991 (class 0 OID 25001)
-- Dependencies: 1543
-- Data for Name: classifier_applied_for_dot; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1992 (class 0 OID 25004)
-- Dependencies: 1544
-- Data for Name: classifier_groups; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1993 (class 0 OID 25011)
-- Dependencies: 1545
-- Data for Name: classifiers; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2006 (class 0 OID 25387)
-- Dependencies: 1568
-- Data for Name: countries; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2008 (class 0 OID 25432)
-- Dependencies: 1570
-- Data for Name: currencies; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1994 (class 0 OID 25017)
-- Dependencies: 1546
-- Data for Name: data_object_types; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) VALUES (36, 'com.cosmos.acacia.crm.data.Product', NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) VALUES (35, 'com.cosmos.acacia.crm.data.ProductCategory', NULL, NULL, NULL, NULL, NULL);


--
-- TOC entry 1995 (class 0 OID 25023)
-- Dependencies: 1547
-- Data for Name: data_objects; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1, 1, 35, '2001-12-23 21:39:53.662522+02', 1, 1, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1203666091108, 1, 36, '2008-02-22 09:41:31.105+02', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1203666261915, 1, 36, '2008-02-22 09:44:21.914+02', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1203703973778, 1, 36, '2008-02-22 20:12:53.775+02', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1203704468562, 1, 36, '2008-02-22 20:21:08.559+02', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1203704551658, 1, 36, '2008-02-22 20:22:31.655+02', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1203704748832, 1, 36, '2008-02-22 20:25:48.83+02', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1203705098181, 1, 36, '2008-02-22 20:31:38.18+02', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1203705490890, 1, 36, '2008-02-22 20:38:10.889+02', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1203838106902, 1, 36, '2008-02-24 09:28:26.898+02', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1203838306634, 1, 36, '2008-02-24 09:31:46.633+02', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1203839033810, 3, 36, '2008-02-24 09:43:53.807+02', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1203863479289, 1, 36, '2008-02-24 16:31:19.286+02', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1203863458084, 6, 36, '2008-02-24 16:30:58.082+02', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);


--
-- TOC entry 1996 (class 0 OID 25035)
-- Dependencies: 1548
-- Data for Name: enum_classes; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO enum_classes (enum_class_id, enum_class_name) VALUES (1, 'com.cosmos.acacia.crm.enums.MeasurementUnit');


--
-- TOC entry 2004 (class 0 OID 25337)
-- Dependencies: 1566
-- Data for Name: organizations; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1997 (class 0 OID 25041)
-- Dependencies: 1550
-- Data for Name: pattern_mask_formats; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2009 (class 0 OID 25447)
-- Dependencies: 1571
-- Data for Name: persons; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1998 (class 0 OID 25047)
-- Dependencies: 1551
-- Data for Name: product_categories; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO product_categories (product_category_id, parent_id, category_name, pattern_mask_format_id, description) VALUES (1, NULL, '1st Product Category', NULL, NULL);


--
-- TOC entry 1999 (class 0 OID 25053)
-- Dependencies: 1552
-- Data for Name: product_suppliers; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2000 (class 0 OID 25059)
-- Dependencies: 1553
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO products (product_id, parent_id, category_id, product_name, product_code, measure_unit_id, is_complex, is_purchased, is_salable, is_obsolete, pattern_mask_format_id, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id) VALUES (1203863479289, NULL, 1, 'p2', 'pc2', 2, false, false, true, false, NULL, NULL, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO products (product_id, parent_id, category_id, product_name, product_code, measure_unit_id, is_complex, is_purchased, is_salable, is_obsolete, pattern_mask_format_id, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id) VALUES (1203863458084, NULL, 1, 'p1', 'pc1', 1, true, false, true, false, NULL, NULL, 1.0000, 3.0000, 2.0000, 100.2000, 200.0000, 250.0000, 1, 16, 1.20, 2.30, 3.50, NULL, 23.450, 12, 'My description', NULL);


--
-- TOC entry 2012 (class 0 OID 25534)
-- Dependencies: 1574
-- Data for Name: purchase_orders; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2001 (class 0 OID 25074)
-- Dependencies: 1555
-- Data for Name: resource_bundle; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (1, 1, 'Piece');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (2, 1, 'Centimeter');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (3, 1, 'Meter');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (4, 1, 'Decimeter');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (5, 1, 'Kilometer');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (6, 1, 'Millimeter');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (7, 1, 'Gram');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (8, 1, 'Kilogram');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (9, 1, 'TonMetric');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (10, 1, 'SquareCentimeter');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (11, 1, 'SquareMeter');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (12, 1, 'Area');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (13, 1, 'SquareKilometer');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (14, 1, 'CubicCentimeter');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (15, 1, 'CubicMeter');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (16, 1, 'CubicKilometer');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (17, 1, 'CubicMillimeter');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (18, 1, 'Liter');


--
-- TOC entry 2002 (class 0 OID 25077)
-- Dependencies: 1556
-- Data for Name: sequence_identifiers; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2003 (class 0 OID 25084)
-- Dependencies: 1558
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2011 (class 0 OID 25516)
-- Dependencies: 1573
-- Data for Name: warehouse_products; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2010 (class 0 OID 25489)
-- Dependencies: 1572
-- Data for Name: warehouses; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1912 (class 2606 OID 25376)
-- Dependencies: 1567 1567
-- Name: pk_addresses; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT pk_addresses PRIMARY KEY (address_id);


--
-- TOC entry 1918 (class 2606 OID 25404)
-- Dependencies: 1569 1569
-- Name: pk_cities; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY cities
    ADD CONSTRAINT pk_cities PRIMARY KEY (city_id);


--
-- TOC entry 1860 (class 2606 OID 25129)
-- Dependencies: 1542 1542 1542
-- Name: pk_classified_objects; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classified_objects
    ADD CONSTRAINT pk_classified_objects PRIMARY KEY (classifier_id, classified_object_id);


--
-- TOC entry 1862 (class 2606 OID 25131)
-- Dependencies: 1543 1543 1543
-- Name: pk_classifier_applied_for_dot; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifier_applied_for_dot
    ADD CONSTRAINT pk_classifier_applied_for_dot PRIMARY KEY (classifier_id, data_object_type_id);


--
-- TOC entry 1864 (class 2606 OID 25133)
-- Dependencies: 1544 1544
-- Name: pk_classifier_groups; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifier_groups
    ADD CONSTRAINT pk_classifier_groups PRIMARY KEY (classifier_group_id);


--
-- TOC entry 1870 (class 2606 OID 25135)
-- Dependencies: 1545 1545
-- Name: pk_classifiers; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT pk_classifiers PRIMARY KEY (classifier_id);


--
-- TOC entry 1914 (class 2606 OID 25394)
-- Dependencies: 1568 1568
-- Name: pk_countries; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY countries
    ADD CONSTRAINT pk_countries PRIMARY KEY (country_id);


--
-- TOC entry 1922 (class 2606 OID 25439)
-- Dependencies: 1570 1570
-- Name: pk_currencies; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY currencies
    ADD CONSTRAINT pk_currencies PRIMARY KEY (currency_id);


--
-- TOC entry 1876 (class 2606 OID 25137)
-- Dependencies: 1546 1546
-- Name: pk_data_object_types; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_types
    ADD CONSTRAINT pk_data_object_types PRIMARY KEY (data_object_type_id);


--
-- TOC entry 1880 (class 2606 OID 25139)
-- Dependencies: 1547 1547
-- Name: pk_data_objects; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT pk_data_objects PRIMARY KEY (data_object_id);


--
-- TOC entry 1882 (class 2606 OID 25141)
-- Dependencies: 1548 1548
-- Name: pk_enum_classes; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY enum_classes
    ADD CONSTRAINT pk_enum_classes PRIMARY KEY (enum_class_id);


--
-- TOC entry 1908 (class 2606 OID 25344)
-- Dependencies: 1566 1566
-- Name: pk_organizations; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT pk_organizations PRIMARY KEY (organization_id);


--
-- TOC entry 1886 (class 2606 OID 25143)
-- Dependencies: 1550 1550
-- Name: pk_pattern_mask_formats; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pattern_mask_formats
    ADD CONSTRAINT pk_pattern_mask_formats PRIMARY KEY (pattern_mask_format_id);


--
-- TOC entry 1926 (class 2606 OID 25454)
-- Dependencies: 1571 1571
-- Name: pk_persons; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT pk_persons PRIMARY KEY (person_id);


--
-- TOC entry 1890 (class 2606 OID 25145)
-- Dependencies: 1551 1551
-- Name: pk_product_categories; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT pk_product_categories PRIMARY KEY (product_category_id);


--
-- TOC entry 1894 (class 2606 OID 25147)
-- Dependencies: 1552 1552 1552
-- Name: pk_product_suppliers; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT pk_product_suppliers PRIMARY KEY (product_id, supplier_id);


--
-- TOC entry 1896 (class 2606 OID 25149)
-- Dependencies: 1553 1553
-- Name: pk_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY products
    ADD CONSTRAINT pk_products PRIMARY KEY (product_id);


--
-- TOC entry 1936 (class 2606 OID 25539)
-- Dependencies: 1574 1574
-- Name: pk_purchase_orders; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT pk_purchase_orders PRIMARY KEY (purchase_order_id);


--
-- TOC entry 1898 (class 2606 OID 25151)
-- Dependencies: 1555 1555
-- Name: pk_resource_bundle; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY resource_bundle
    ADD CONSTRAINT pk_resource_bundle PRIMARY KEY (resource_id);


--
-- TOC entry 1902 (class 2606 OID 25153)
-- Dependencies: 1558 1558
-- Name: pk_users; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT pk_users PRIMARY KEY (user_id);


--
-- TOC entry 1934 (class 2606 OID 25523)
-- Dependencies: 1573 1573 1573
-- Name: pk_warehouse_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY warehouse_products
    ADD CONSTRAINT pk_warehouse_products PRIMARY KEY (warehouse_id, product_id);


--
-- TOC entry 1930 (class 2606 OID 25496)
-- Dependencies: 1572 1572
-- Name: pk_warehouses; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY warehouses
    ADD CONSTRAINT pk_warehouses PRIMARY KEY (warehouse_id);


--
-- TOC entry 1920 (class 2606 OID 25406)
-- Dependencies: 1569 1569 1569
-- Name: uk_cities_country_id_city_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY cities
    ADD CONSTRAINT uk_cities_country_id_city_name UNIQUE (country_id, city_name);


--
-- TOC entry 1866 (class 2606 OID 25155)
-- Dependencies: 1544 1544 1544
-- Name: uk_classifier_groups_parent_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifier_groups
    ADD CONSTRAINT uk_classifier_groups_parent_code UNIQUE (parent_id, classifier_group_code);


--
-- TOC entry 1868 (class 2606 OID 25157)
-- Dependencies: 1544 1544 1544
-- Name: uk_classifier_groups_parent_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifier_groups
    ADD CONSTRAINT uk_classifier_groups_parent_name UNIQUE (parent_id, classifier_group_name);


--
-- TOC entry 1872 (class 2606 OID 25159)
-- Dependencies: 1545 1545 1545
-- Name: uk_classifiers_parent_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT uk_classifiers_parent_code UNIQUE (parent_id, classifier_code);


--
-- TOC entry 1874 (class 2606 OID 25161)
-- Dependencies: 1545 1545 1545
-- Name: uk_classifiers_parent_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT uk_classifiers_parent_name UNIQUE (parent_id, classifier_name);


--
-- TOC entry 1916 (class 2606 OID 25396)
-- Dependencies: 1568 1568
-- Name: uk_countries_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY countries
    ADD CONSTRAINT uk_countries_name UNIQUE (country_name);


--
-- TOC entry 1924 (class 2606 OID 25441)
-- Dependencies: 1570 1570
-- Name: uk_currencies_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY currencies
    ADD CONSTRAINT uk_currencies_name UNIQUE (currency_name);


--
-- TOC entry 1878 (class 2606 OID 25163)
-- Dependencies: 1546 1546
-- Name: uk_dot_data_object_type; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_types
    ADD CONSTRAINT uk_dot_data_object_type UNIQUE (data_object_type);


--
-- TOC entry 1884 (class 2606 OID 25165)
-- Dependencies: 1548 1548
-- Name: uk_enum_classes; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY enum_classes
    ADD CONSTRAINT uk_enum_classes UNIQUE (enum_class_name);


--
-- TOC entry 1910 (class 2606 OID 25368)
-- Dependencies: 1566 1566 1566
-- Name: uk_organizations_parent_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT uk_organizations_parent_name UNIQUE (parent_id, organization_name);


--
-- TOC entry 1888 (class 2606 OID 25167)
-- Dependencies: 1550 1550 1550
-- Name: uk_pattern_mask_formats_owner_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pattern_mask_formats
    ADD CONSTRAINT uk_pattern_mask_formats_owner_name UNIQUE (owner_id, pattern_name);


--
-- TOC entry 1928 (class 2606 OID 25483)
-- Dependencies: 1571 1571 1571 1571 1571 1571
-- Name: uk_persons_parent_by_names; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT uk_persons_parent_by_names UNIQUE (parent_id, first_name, second_name, last_name, extra_name);


--
-- TOC entry 1892 (class 2606 OID 25169)
-- Dependencies: 1551 1551 1551
-- Name: uk_product_categories_parent_category_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT uk_product_categories_parent_category_name UNIQUE (parent_id, category_name);


--
-- TOC entry 1938 (class 2606 OID 25541)
-- Dependencies: 1574 1574 1574
-- Name: uk_purchase_orders_parent_order_number; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT uk_purchase_orders_parent_order_number UNIQUE (parent_id, order_number);


--
-- TOC entry 1900 (class 2606 OID 25171)
-- Dependencies: 1556 1556
-- Name: uk_seq_id_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY sequence_identifiers
    ADD CONSTRAINT uk_seq_id_name UNIQUE (seq_id_name);


--
-- TOC entry 1904 (class 2606 OID 25173)
-- Dependencies: 1558 1558
-- Name: uk_users_email_address; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT uk_users_email_address UNIQUE (email_address);


--
-- TOC entry 1906 (class 2606 OID 25175)
-- Dependencies: 1558 1558
-- Name: uk_users_user_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT uk_users_user_name UNIQUE (user_name);


--
-- TOC entry 1932 (class 2606 OID 25515)
-- Dependencies: 1572 1572 1572
-- Name: uk_warehouses_parent_address; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY warehouses
    ADD CONSTRAINT uk_warehouses_parent_address UNIQUE (parent_id, address_id);


--
-- TOC entry 1963 (class 2606 OID 25176)
-- Dependencies: 1558 1558 1901
-- Name: fk6a68e0844bb6904; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk6a68e0844bb6904 FOREIGN KEY (creator_id) REFERENCES users(user_id);


--
-- TOC entry 1964 (class 2606 OID 25181)
-- Dependencies: 1558 1547 1879
-- Name: fk6a68e08a08870b9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk6a68e08a08870b9 FOREIGN KEY (person_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1946 (class 2606 OID 25186)
-- Dependencies: 1547 1879 1547
-- Name: fk74e5117f6017f920; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk74e5117f6017f920 FOREIGN KEY (linked_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1947 (class 2606 OID 25191)
-- Dependencies: 1547 1547 1879
-- Name: fk74e5117fdfc2026f; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk74e5117fdfc2026f FOREIGN KEY (parent_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1975 (class 2606 OID 25417)
-- Dependencies: 1917 1567 1569
-- Name: fk_addresses_city_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT fk_addresses_city_id FOREIGN KEY (city_id) REFERENCES cities(city_id);


--
-- TOC entry 1974 (class 2606 OID 25412)
-- Dependencies: 1913 1567 1568
-- Name: fk_addresses_country_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT fk_addresses_country_id FOREIGN KEY (country_id) REFERENCES countries(country_id);


--
-- TOC entry 1973 (class 2606 OID 25377)
-- Dependencies: 1567 1547 1879
-- Name: fk_addresses_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT fk_addresses_do_id FOREIGN KEY (address_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1977 (class 2606 OID 25407)
-- Dependencies: 1569 1913 1568
-- Name: fk_cities_country_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cities
    ADD CONSTRAINT fk_cities_country_id FOREIGN KEY (country_id) REFERENCES countries(country_id);


--
-- TOC entry 1939 (class 2606 OID 25196)
-- Dependencies: 1542 1869 1545
-- Name: fk_classified_objects_classifier_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classified_objects
    ADD CONSTRAINT fk_classified_objects_classifier_id FOREIGN KEY (classifier_id) REFERENCES classifiers(classifier_id);


--
-- TOC entry 1940 (class 2606 OID 25201)
-- Dependencies: 1879 1542 1547
-- Name: fk_classified_objects_object_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classified_objects
    ADD CONSTRAINT fk_classified_objects_object_id FOREIGN KEY (classified_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1941 (class 2606 OID 25206)
-- Dependencies: 1869 1545 1543
-- Name: fk_classifier_applied_for_dot_classifiers; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifier_applied_for_dot
    ADD CONSTRAINT fk_classifier_applied_for_dot_classifiers FOREIGN KEY (classifier_id) REFERENCES classifiers(classifier_id);


--
-- TOC entry 1942 (class 2606 OID 25211)
-- Dependencies: 1875 1546 1543
-- Name: fk_classifier_applied_for_dot_dot_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifier_applied_for_dot
    ADD CONSTRAINT fk_classifier_applied_for_dot_dot_id FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 1943 (class 2606 OID 25216)
-- Dependencies: 1547 1879 1544
-- Name: fk_classifier_groups_cg_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifier_groups
    ADD CONSTRAINT fk_classifier_groups_cg_id FOREIGN KEY (classifier_group_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1944 (class 2606 OID 25221)
-- Dependencies: 1544 1863 1545
-- Name: fk_classifiers_group_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT fk_classifiers_group_id FOREIGN KEY (classifier_group_id) REFERENCES classifier_groups(classifier_group_id);


--
-- TOC entry 1945 (class 2606 OID 25226)
-- Dependencies: 1547 1879 1545
-- Name: fk_classifiers_id_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT fk_classifiers_id_do_id FOREIGN KEY (classifier_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1976 (class 2606 OID 25442)
-- Dependencies: 1568 1570 1921
-- Name: fk_countries_currency_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY countries
    ADD CONSTRAINT fk_countries_currency_id FOREIGN KEY (currency_id) REFERENCES currencies(currency_id);


--
-- TOC entry 1948 (class 2606 OID 25231)
-- Dependencies: 1547 1879 1547
-- Name: fk_data_objects_linked_data_object_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk_data_objects_linked_data_object_id FOREIGN KEY (linked_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1949 (class 2606 OID 25236)
-- Dependencies: 1547 1547 1879
-- Name: fk_data_objects_parent_data_object_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk_data_objects_parent_data_object_id FOREIGN KEY (parent_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1950 (class 2606 OID 25241)
-- Dependencies: 1547 1879 1547
-- Name: fk_do_linked_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk_do_linked_id FOREIGN KEY (linked_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1951 (class 2606 OID 25246)
-- Dependencies: 1547 1879 1547
-- Name: fk_do_parent_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk_do_parent_id FOREIGN KEY (parent_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1971 (class 2606 OID 25427)
-- Dependencies: 1567 1911 1566
-- Name: fk_organizations_admin_addr_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk_organizations_admin_addr_id FOREIGN KEY (administration_address_id) REFERENCES addresses(address_id);


--
-- TOC entry 1972 (class 2606 OID 25484)
-- Dependencies: 1566 1570 1921
-- Name: fk_organizations_currency_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk_organizations_currency_id FOREIGN KEY (currency_id) REFERENCES currencies(currency_id);


--
-- TOC entry 1967 (class 2606 OID 25345)
-- Dependencies: 1879 1547 1566
-- Name: fk_organizations_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk_organizations_do_id FOREIGN KEY (organization_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1970 (class 2606 OID 25422)
-- Dependencies: 1566 1567 1911
-- Name: fk_organizations_reg_addr_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk_organizations_reg_addr_id FOREIGN KEY (registration_address_id) REFERENCES addresses(address_id);


--
-- TOC entry 1969 (class 2606 OID 25362)
-- Dependencies: 1566 1907 1566
-- Name: fk_organizations_reg_org_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk_organizations_reg_org_id FOREIGN KEY (registration_organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 1968 (class 2606 OID 25357)
-- Dependencies: 1566 1897 1555
-- Name: fk_organizations_type_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk_organizations_type_id FOREIGN KEY (organization_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 1980 (class 2606 OID 25465)
-- Dependencies: 1569 1917 1571
-- Name: fk_persons_city_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT fk_persons_city_id FOREIGN KEY (birth_place_city_id) REFERENCES cities(city_id);


--
-- TOC entry 1979 (class 2606 OID 25460)
-- Dependencies: 1571 1913 1568
-- Name: fk_persons_country_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT fk_persons_country_id FOREIGN KEY (birth_place_country_id) REFERENCES countries(country_id);


--
-- TOC entry 1978 (class 2606 OID 25455)
-- Dependencies: 1547 1879 1571
-- Name: fk_persons_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT fk_persons_do_id FOREIGN KEY (person_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1981 (class 2606 OID 25470)
-- Dependencies: 1571 1897 1555
-- Name: fk_persons_gender_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT fk_persons_gender_id FOREIGN KEY (gender_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 1952 (class 2606 OID 25251)
-- Dependencies: 1551 1550 1885
-- Name: fk_product_categories_pattern_mask_format_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT fk_product_categories_pattern_mask_format_id FOREIGN KEY (pattern_mask_format_id) REFERENCES pattern_mask_formats(pattern_mask_format_id);


--
-- TOC entry 1953 (class 2606 OID 25256)
-- Dependencies: 1547 1551 1879
-- Name: fk_product_categories_product_category_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT fk_product_categories_product_category_id FOREIGN KEY (product_category_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1955 (class 2606 OID 25261)
-- Dependencies: 1551 1553 1889
-- Name: fk_product_category_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_product_category_id FOREIGN KEY (category_id) REFERENCES product_categories(product_category_id);


--
-- TOC entry 1954 (class 2606 OID 25266)
-- Dependencies: 1552 1553 1895
-- Name: fk_product_suppliers_product_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk_product_suppliers_product_id FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 1956 (class 2606 OID 25271)
-- Dependencies: 1555 1553 1897
-- Name: fk_products_color_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_products_color_id FOREIGN KEY (product_color_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 1957 (class 2606 OID 25276)
-- Dependencies: 1897 1553 1555
-- Name: fk_products_dimension_unit_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_products_dimension_unit_id FOREIGN KEY (dimension_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 1958 (class 2606 OID 25281)
-- Dependencies: 1555 1897 1553
-- Name: fk_products_measure_unit_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_products_measure_unit_id FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 1959 (class 2606 OID 25286)
-- Dependencies: 1553 1885 1550
-- Name: fk_products_pattern_mask_format_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_products_pattern_mask_format_id FOREIGN KEY (pattern_mask_format_id) REFERENCES pattern_mask_formats(pattern_mask_format_id);


--
-- TOC entry 1960 (class 2606 OID 25291)
-- Dependencies: 1547 1553 1879
-- Name: fk_products_product_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_products_product_id FOREIGN KEY (product_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1961 (class 2606 OID 25296)
-- Dependencies: 1555 1897 1553
-- Name: fk_products_weight_unit_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_products_weight_unit_id FOREIGN KEY (weight_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 1986 (class 2606 OID 25547)
-- Dependencies: 1571 1925 1574
-- Name: fk_purchase_orders_contact_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fk_purchase_orders_contact_id FOREIGN KEY (supplier_contact_id) REFERENCES persons(person_id);


--
-- TOC entry 1987 (class 2606 OID 25552)
-- Dependencies: 1925 1574 1571
-- Name: fk_purchase_orders_creator_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fk_purchase_orders_creator_id FOREIGN KEY (creator_id) REFERENCES persons(person_id);


--
-- TOC entry 1985 (class 2606 OID 25542)
-- Dependencies: 1547 1879 1574
-- Name: fk_purchase_orders_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fk_purchase_orders_do_id FOREIGN KEY (purchase_order_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1988 (class 2606 OID 25557)
-- Dependencies: 1574 1925 1571
-- Name: fk_purchase_orders_sender_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fk_purchase_orders_sender_id FOREIGN KEY (sender_id) REFERENCES persons(person_id);


--
-- TOC entry 1989 (class 2606 OID 25562)
-- Dependencies: 1897 1555 1574
-- Name: fk_purchase_orders_status_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fk_purchase_orders_status_id FOREIGN KEY (status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 1962 (class 2606 OID 25301)
-- Dependencies: 1881 1548 1555
-- Name: fk_resource_bundle_enum_class_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY resource_bundle
    ADD CONSTRAINT fk_resource_bundle_enum_class_id FOREIGN KEY (enum_class_id) REFERENCES enum_classes(enum_class_id);


--
-- TOC entry 1965 (class 2606 OID 25306)
-- Dependencies: 1558 1558 1901
-- Name: fk_users_creator_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk_users_creator_id FOREIGN KEY (creator_id) REFERENCES users(user_id);


--
-- TOC entry 1966 (class 2606 OID 25311)
-- Dependencies: 1879 1547 1558
-- Name: fk_users_person_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk_users_person_id FOREIGN KEY (person_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1984 (class 2606 OID 25529)
-- Dependencies: 1895 1553 1573
-- Name: fk_warehouse_products_p_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouse_products
    ADD CONSTRAINT fk_warehouse_products_p_id FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 1983 (class 2606 OID 25524)
-- Dependencies: 1929 1573 1572
-- Name: fk_warehouse_products_wh_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouse_products
    ADD CONSTRAINT fk_warehouse_products_wh_id FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 1982 (class 2606 OID 25502)
-- Dependencies: 1911 1567 1572
-- Name: fk_warehouses_address_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouses
    ADD CONSTRAINT fk_warehouses_address_id FOREIGN KEY (address_id) REFERENCES addresses(address_id);


--
-- TOC entry 2017 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: -
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2008-02-29 13:56:38

--
-- PostgreSQL database dump complete
--

