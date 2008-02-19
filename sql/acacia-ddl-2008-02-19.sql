--
-- PostgreSQL database dump
--

-- Started on 2008-02-19 18:53:50

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 350 (class 2612 OID 16386)
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: -
--

CREATE PROCEDURAL LANGUAGE plpgsql;


SET search_path = public, pg_catalog;

--
-- TOC entry 273 (class 1247 OID 16405)
-- Dependencies: 3 1522
-- Name: breakpoint; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE breakpoint AS (
	func oid,
	linenumber integer,
	targetname text
);


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 1527 (class 1259 OID 16520)
-- Dependencies: 3
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
-- TOC entry 1528 (class 1259 OID 16530)
-- Dependencies: 1808 1809 1810 1811 1812 3
-- Name: data_objects; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE data_objects (
    data_object_id numeric(18,0) NOT NULL,
    data_object_version integer NOT NULL,
    data_object_type_id integer NOT NULL,
    creation_time timestamp with time zone NOT NULL,
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
-- TOC entry 1536 (class 1259 OID 33181)
-- Dependencies: 3
-- Name: enum_classes; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE enum_classes (
    enum_class_id integer NOT NULL,
    enum_class_name character varying(255) NOT NULL
);


--
-- TOC entry 275 (class 1247 OID 16408)
-- Dependencies: 3 1523
-- Name: frame; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE frame AS (
	level integer,
	targetname text,
	func oid,
	linenumber integer,
	args text
);


--
-- TOC entry 1540 (class 1259 OID 33270)
-- Dependencies: 3
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
-- TOC entry 1886 (class 0 OID 0)
-- Dependencies: 1540
-- Name: COLUMN pattern_mask_formats.format_type; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN pattern_mask_formats.format_type IS 'D for DateFormatter;
N for NumberFormatter;
M for MaskFormatter.';


--
-- TOC entry 1539 (class 1259 OID 33212)
-- Dependencies: 3
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
-- TOC entry 1534 (class 1259 OID 33152)
-- Dependencies: 1817 1818 1819 1820 1821 1822 3
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
    product_color character varying(45),
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
-- TOC entry 317 (class 1247 OID 16417)
-- Dependencies: 3 1526
-- Name: proxyinfo; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE proxyinfo AS (
	serverversionstr text,
	serverversionnum integer,
	proxyapiver integer,
	serverprocessid integer
);


--
-- TOC entry 1535 (class 1259 OID 33171)
-- Dependencies: 3
-- Name: resource_bundle; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE resource_bundle (
    resource_id integer NOT NULL,
    enum_class_id integer NOT NULL,
    enum_name character varying(64) NOT NULL
);


--
-- TOC entry 1532 (class 1259 OID 16710)
-- Dependencies: 1816 3
-- Name: sequence_identifiers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE sequence_identifiers (
    seq_id_key bigint NOT NULL,
    seq_id_name character varying(64) NOT NULL,
    seq_id_value numeric(38,0) DEFAULT 0 NOT NULL
);


--
-- TOC entry 313 (class 1247 OID 16411)
-- Dependencies: 3 1524
-- Name: targetinfo; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE targetinfo AS (
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
);


--
-- TOC entry 1529 (class 1259 OID 16571)
-- Dependencies: 1813 1814 3
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
-- TOC entry 315 (class 1247 OID 16414)
-- Dependencies: 3 1525
-- Name: var; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE var AS (
	name text,
	varclass character(1),
	linenumber integer,
	isunique boolean,
	isconst boolean,
	isnotnull boolean,
	dtype oid,
	value text
);


--
-- TOC entry 21 (class 1255 OID 16419)
-- Dependencies: 3
-- Name: pldbg_abort_target(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_abort_target(session integer) RETURNS SETOF boolean
    AS '$libdir/pldbgapi', 'pldbg_abort_target'
    LANGUAGE c STRICT;


--
-- TOC entry 22 (class 1255 OID 16420)
-- Dependencies: 3
-- Name: pldbg_attach_to_port(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_attach_to_port(portnumber integer) RETURNS integer
    AS '$libdir/pldbgapi', 'pldbg_attach_to_port'
    LANGUAGE c STRICT;


--
-- TOC entry 23 (class 1255 OID 16421)
-- Dependencies: 3 273
-- Name: pldbg_continue(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_continue(session integer) RETURNS breakpoint
    AS '$libdir/pldbgapi', 'pldbg_continue'
    LANGUAGE c STRICT;


--
-- TOC entry 24 (class 1255 OID 16422)
-- Dependencies: 3
-- Name: pldbg_create_listener(); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_create_listener() RETURNS integer
    AS '$libdir/pldbgapi', 'pldbg_create_listener'
    LANGUAGE c STRICT;


--
-- TOC entry 25 (class 1255 OID 16423)
-- Dependencies: 3
-- Name: pldbg_deposit_value(integer, text, integer, text); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_deposit_value(session integer, varname text, linenumber integer, value text) RETURNS boolean
    AS '$libdir/pldbgapi', 'pldbg_deposit_value'
    LANGUAGE c STRICT;


--
-- TOC entry 26 (class 1255 OID 16424)
-- Dependencies: 3
-- Name: pldbg_drop_breakpoint(integer, oid, integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_drop_breakpoint(session integer, func oid, linenumber integer) RETURNS boolean
    AS '$libdir/pldbgapi', 'pldbg_drop_breakpoint'
    LANGUAGE c STRICT;


--
-- TOC entry 27 (class 1255 OID 16425)
-- Dependencies: 3 273
-- Name: pldbg_get_breakpoints(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_get_breakpoints(session integer) RETURNS SETOF breakpoint
    AS '$libdir/pldbgapi', 'pldbg_get_breakpoints'
    LANGUAGE c STRICT;


--
-- TOC entry 30 (class 1255 OID 16428)
-- Dependencies: 317 3
-- Name: pldbg_get_proxy_info(); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_get_proxy_info() RETURNS proxyinfo
    AS '$libdir/pldbgapi', 'pldbg_get_proxy_info'
    LANGUAGE c STRICT;


--
-- TOC entry 28 (class 1255 OID 16426)
-- Dependencies: 3
-- Name: pldbg_get_source(integer, oid); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_get_source(session integer, func oid) RETURNS text
    AS '$libdir/pldbgapi', 'pldbg_get_source'
    LANGUAGE c STRICT;


--
-- TOC entry 29 (class 1255 OID 16427)
-- Dependencies: 275 3
-- Name: pldbg_get_stack(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_get_stack(session integer) RETURNS SETOF frame
    AS '$libdir/pldbgapi', 'pldbg_get_stack'
    LANGUAGE c STRICT;


--
-- TOC entry 39 (class 1255 OID 16437)
-- Dependencies: 3 313
-- Name: pldbg_get_target_info(text, "char"); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_get_target_info(signature text, targettype "char") RETURNS targetinfo
    AS '$libdir/targetinfo', 'pldbg_get_target_info'
    LANGUAGE c STRICT;


--
-- TOC entry 31 (class 1255 OID 16429)
-- Dependencies: 315 3
-- Name: pldbg_get_variables(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_get_variables(session integer) RETURNS SETOF var
    AS '$libdir/pldbgapi', 'pldbg_get_variables'
    LANGUAGE c STRICT;


--
-- TOC entry 32 (class 1255 OID 16430)
-- Dependencies: 273 3
-- Name: pldbg_select_frame(integer, integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_select_frame(session integer, frame integer) RETURNS breakpoint
    AS '$libdir/pldbgapi', 'pldbg_select_frame'
    LANGUAGE c STRICT;


--
-- TOC entry 33 (class 1255 OID 16431)
-- Dependencies: 3
-- Name: pldbg_set_breakpoint(integer, oid, integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_set_breakpoint(session integer, func oid, linenumber integer) RETURNS boolean
    AS '$libdir/pldbgapi', 'pldbg_set_breakpoint'
    LANGUAGE c STRICT;


--
-- TOC entry 34 (class 1255 OID 16432)
-- Dependencies: 3
-- Name: pldbg_set_global_breakpoint(integer, oid, integer, integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_set_global_breakpoint(session integer, func oid, linenumber integer, targetpid integer) RETURNS boolean
    AS '$libdir/pldbgapi', 'pldbg_set_global_breakpoint'
    LANGUAGE c;


--
-- TOC entry 35 (class 1255 OID 16433)
-- Dependencies: 3 273
-- Name: pldbg_step_into(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_step_into(session integer) RETURNS breakpoint
    AS '$libdir/pldbgapi', 'pldbg_step_into'
    LANGUAGE c STRICT;


--
-- TOC entry 36 (class 1255 OID 16434)
-- Dependencies: 3 273
-- Name: pldbg_step_over(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_step_over(session integer) RETURNS breakpoint
    AS '$libdir/pldbgapi', 'pldbg_step_over'
    LANGUAGE c STRICT;


--
-- TOC entry 37 (class 1255 OID 16435)
-- Dependencies: 273 3
-- Name: pldbg_wait_for_breakpoint(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_wait_for_breakpoint(session integer) RETURNS breakpoint
    AS '$libdir/pldbgapi', 'pldbg_wait_for_breakpoint'
    LANGUAGE c STRICT;


--
-- TOC entry 38 (class 1255 OID 16436)
-- Dependencies: 3
-- Name: pldbg_wait_for_target(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_wait_for_target(session integer) RETURNS integer
    AS '$libdir/pldbgapi', 'pldbg_wait_for_target'
    LANGUAGE c STRICT;


--
-- TOC entry 20 (class 1255 OID 16418)
-- Dependencies: 3
-- Name: plpgsql_oid_debug(oid); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION plpgsql_oid_debug(functionoid oid) RETURNS integer
    AS '$libdir/plugins/plugin_debugger', 'plpgsql_oid_debug'
    LANGUAGE c STRICT;


--
-- TOC entry 1530 (class 1259 OID 16625)
-- Dependencies: 3
-- Name: data_object_type_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE data_object_type_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1887 (class 0 OID 0)
-- Dependencies: 1530
-- Name: data_object_type_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('data_object_type_seq', 36, true);


--
-- TOC entry 1531 (class 1259 OID 16695)
-- Dependencies: 3
-- Name: data_objects_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE data_objects_seq
    INCREMENT BY 1
    MAXVALUE 999999999999999999
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1888 (class 0 OID 0)
-- Dependencies: 1531
-- Name: data_objects_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('data_objects_seq', 2, true);


--
-- TOC entry 1537 (class 1259 OID 33193)
-- Dependencies: 3
-- Name: enum_classes_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE enum_classes_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1889 (class 0 OID 0)
-- Dependencies: 1537
-- Name: enum_classes_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('enum_classes_seq', 1, true);


--
-- TOC entry 1541 (class 1259 OID 33290)
-- Dependencies: 3
-- Name: pattern_mask_formats_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE pattern_mask_formats_seq
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1890 (class 0 OID 0)
-- Dependencies: 1541
-- Name: pattern_mask_formats_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('pattern_mask_formats_seq', 1, false);


--
-- TOC entry 1538 (class 1259 OID 33195)
-- Dependencies: 3
-- Name: resource_bundle_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE resource_bundle_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1891 (class 0 OID 0)
-- Dependencies: 1538
-- Name: resource_bundle_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('resource_bundle_seq', 18, true);


--
-- TOC entry 1533 (class 1259 OID 16716)
-- Dependencies: 3 1532
-- Name: sequence_identifiers_seq_id_key_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE sequence_identifiers_seq_id_key_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1892 (class 0 OID 0)
-- Dependencies: 1533
-- Name: sequence_identifiers_seq_id_key_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE sequence_identifiers_seq_id_key_seq OWNED BY sequence_identifiers.seq_id_key;


--
-- TOC entry 1893 (class 0 OID 0)
-- Dependencies: 1533
-- Name: sequence_identifiers_seq_id_key_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('sequence_identifiers_seq_id_key_seq', 1, false);


--
-- TOC entry 1815 (class 2604 OID 16718)
-- Dependencies: 1533 1532
-- Name: seq_id_key; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE sequence_identifiers ALTER COLUMN seq_id_key SET DEFAULT nextval('sequence_identifiers_seq_id_key_seq'::regclass);


--
-- TOC entry 1872 (class 0 OID 16520)
-- Dependencies: 1527
-- Data for Name: data_object_types; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) VALUES (36, 'com.cosmos.acacia.crm.data.Product', NULL, NULL, NULL, NULL, NULL);


--
-- TOC entry 1873 (class 0 OID 16530)
-- Dependencies: 1528
-- Data for Name: data_objects; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1878 (class 0 OID 33181)
-- Dependencies: 1536
-- Data for Name: enum_classes; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO enum_classes (enum_class_id, enum_class_name) VALUES (1, 'com.cosmos.acacia.crm.enums.MeasurementUnit');


--
-- TOC entry 1880 (class 0 OID 33270)
-- Dependencies: 1540
-- Data for Name: pattern_mask_formats; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1879 (class 0 OID 33212)
-- Dependencies: 1539
-- Data for Name: product_categories; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1876 (class 0 OID 33152)
-- Dependencies: 1534
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1877 (class 0 OID 33171)
-- Dependencies: 1535
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
-- TOC entry 1875 (class 0 OID 16710)
-- Dependencies: 1532
-- Data for Name: sequence_identifiers; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1874 (class 0 OID 16571)
-- Dependencies: 1529
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1824 (class 2606 OID 16527)
-- Dependencies: 1527 1527
-- Name: pk_data_object_types; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_types
    ADD CONSTRAINT pk_data_object_types PRIMARY KEY (data_object_type_id);


--
-- TOC entry 1828 (class 2606 OID 16542)
-- Dependencies: 1528 1528
-- Name: pk_data_objects; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT pk_data_objects PRIMARY KEY (data_object_id);


--
-- TOC entry 1842 (class 2606 OID 33185)
-- Dependencies: 1536 1536
-- Name: pk_enum_classes; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY enum_classes
    ADD CONSTRAINT pk_enum_classes PRIMARY KEY (enum_class_id);


--
-- TOC entry 1850 (class 2606 OID 33277)
-- Dependencies: 1540 1540
-- Name: pk_pattern_mask_formats; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pattern_mask_formats
    ADD CONSTRAINT pk_pattern_mask_formats PRIMARY KEY (pattern_mask_format_id);


--
-- TOC entry 1846 (class 2606 OID 33219)
-- Dependencies: 1539 1539
-- Name: pk_product_categories; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT pk_product_categories PRIMARY KEY (product_category_id);


--
-- TOC entry 1838 (class 2606 OID 33165)
-- Dependencies: 1534 1534
-- Name: pk_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY products
    ADD CONSTRAINT pk_products PRIMARY KEY (product_id);


--
-- TOC entry 1840 (class 2606 OID 33178)
-- Dependencies: 1535 1535
-- Name: pk_resource_bundle; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY resource_bundle
    ADD CONSTRAINT pk_resource_bundle PRIMARY KEY (resource_id);


--
-- TOC entry 1830 (class 2606 OID 16580)
-- Dependencies: 1529 1529
-- Name: pk_users; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT pk_users PRIMARY KEY (user_id);


--
-- TOC entry 1826 (class 2606 OID 16529)
-- Dependencies: 1527 1527
-- Name: uk_dot_data_object_type; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_types
    ADD CONSTRAINT uk_dot_data_object_type UNIQUE (data_object_type);


--
-- TOC entry 1844 (class 2606 OID 33187)
-- Dependencies: 1536 1536
-- Name: uk_enum_classes; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY enum_classes
    ADD CONSTRAINT uk_enum_classes UNIQUE (enum_class_name);


--
-- TOC entry 1852 (class 2606 OID 33279)
-- Dependencies: 1540 1540 1540
-- Name: uk_pattern_mask_formats_owner_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pattern_mask_formats
    ADD CONSTRAINT uk_pattern_mask_formats_owner_name UNIQUE (owner_id, pattern_name);


--
-- TOC entry 1848 (class 2606 OID 33241)
-- Dependencies: 1539 1539 1539
-- Name: uk_product_categories_parent_category_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT uk_product_categories_parent_category_name UNIQUE (parent_id, category_name);


--
-- TOC entry 1836 (class 2606 OID 16730)
-- Dependencies: 1532 1532
-- Name: uk_seq_id_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY sequence_identifiers
    ADD CONSTRAINT uk_seq_id_name UNIQUE (seq_id_name);


--
-- TOC entry 1832 (class 2606 OID 16582)
-- Dependencies: 1529 1529
-- Name: uk_users_email_address; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT uk_users_email_address UNIQUE (email_address);


--
-- TOC entry 1834 (class 2606 OID 16584)
-- Dependencies: 1529 1529
-- Name: uk_users_user_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT uk_users_user_name UNIQUE (user_name);


--
-- TOC entry 1862 (class 2606 OID 16620)
-- Dependencies: 1829 1529 1529
-- Name: fk6a68e0844bb6904; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk6a68e0844bb6904 FOREIGN KEY (creator_id) REFERENCES users(user_id);


--
-- TOC entry 1861 (class 2606 OID 16615)
-- Dependencies: 1529 1528 1827
-- Name: fk6a68e08a08870b9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk6a68e08a08870b9 FOREIGN KEY (person_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1857 (class 2606 OID 16605)
-- Dependencies: 1827 1528 1528
-- Name: fk74e5117f6017f920; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk74e5117f6017f920 FOREIGN KEY (linked_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1858 (class 2606 OID 16610)
-- Dependencies: 1528 1528 1827
-- Name: fk74e5117fdfc2026f; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk74e5117fdfc2026f FOREIGN KEY (parent_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1856 (class 2606 OID 16600)
-- Dependencies: 1528 1827 1528
-- Name: fk_data_objects_linked_data_object_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk_data_objects_linked_data_object_id FOREIGN KEY (linked_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1855 (class 2606 OID 16595)
-- Dependencies: 1528 1528 1827
-- Name: fk_data_objects_parent_data_object_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk_data_objects_parent_data_object_id FOREIGN KEY (parent_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1853 (class 2606 OID 16543)
-- Dependencies: 1827 1528 1528
-- Name: fk_do_linked_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk_do_linked_id FOREIGN KEY (linked_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1854 (class 2606 OID 16548)
-- Dependencies: 1528 1827 1528
-- Name: fk_do_parent_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk_do_parent_id FOREIGN KEY (parent_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1871 (class 2606 OID 33280)
-- Dependencies: 1539 1540 1849
-- Name: fk_product_categories_pattern_mask_format_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT fk_product_categories_pattern_mask_format_id FOREIGN KEY (pattern_mask_format_id) REFERENCES pattern_mask_formats(pattern_mask_format_id);


--
-- TOC entry 1870 (class 2606 OID 33220)
-- Dependencies: 1827 1528 1539
-- Name: fk_product_categories_product_category_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT fk_product_categories_product_category_id FOREIGN KEY (product_category_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1867 (class 2606 OID 33235)
-- Dependencies: 1845 1539 1534
-- Name: fk_product_category_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_product_category_id FOREIGN KEY (category_id) REFERENCES product_categories(product_category_id);


--
-- TOC entry 1866 (class 2606 OID 33207)
-- Dependencies: 1534 1839 1535
-- Name: fk_products_dimension_unit_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_products_dimension_unit_id FOREIGN KEY (dimension_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 1864 (class 2606 OID 33197)
-- Dependencies: 1839 1535 1534
-- Name: fk_products_measure_unit_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_products_measure_unit_id FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 1868 (class 2606 OID 33285)
-- Dependencies: 1534 1540 1849
-- Name: fk_products_pattern_mask_format_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_products_pattern_mask_format_id FOREIGN KEY (pattern_mask_format_id) REFERENCES pattern_mask_formats(pattern_mask_format_id);


--
-- TOC entry 1863 (class 2606 OID 33166)
-- Dependencies: 1827 1528 1534
-- Name: fk_products_product_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_products_product_id FOREIGN KEY (product_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1865 (class 2606 OID 33202)
-- Dependencies: 1534 1839 1535
-- Name: fk_products_weight_unit_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_products_weight_unit_id FOREIGN KEY (weight_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 1869 (class 2606 OID 33188)
-- Dependencies: 1535 1841 1536
-- Name: fk_resource_bundle_enum_class_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY resource_bundle
    ADD CONSTRAINT fk_resource_bundle_enum_class_id FOREIGN KEY (enum_class_id) REFERENCES enum_classes(enum_class_id);


--
-- TOC entry 1859 (class 2606 OID 16585)
-- Dependencies: 1529 1829 1529
-- Name: fk_users_creator_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk_users_creator_id FOREIGN KEY (creator_id) REFERENCES users(user_id);


--
-- TOC entry 1860 (class 2606 OID 16590)
-- Dependencies: 1529 1528 1827
-- Name: fk_users_person_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk_users_person_id FOREIGN KEY (person_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1885 (class 0 OID 0)
-- Dependencies: 3
-- Name: public; Type: ACL; Schema: -; Owner: -
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM "PostgreSQL";
GRANT ALL ON SCHEMA public TO "PostgreSQL";
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2008-02-19 18:53:51

--
-- PostgreSQL database dump complete
--

