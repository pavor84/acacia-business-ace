--
-- PostgreSQL database dump
--

-- Started on 2008-02-26 18:27:42

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 360 (class 2612 OID 16386)
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: -
--

CREATE PROCEDURAL LANGUAGE plpgsql;


SET search_path = public, pg_catalog;

--
-- TOC entry 273 (class 1247 OID 16405)
-- Dependencies: 6 1532
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
-- TOC entry 1555 (class 1259 OID 17425)
-- Dependencies: 6
-- Name: classified_objects; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE classified_objects (
    classifier_id numeric(18,0) NOT NULL,
    classified_object_id numeric(18,0) NOT NULL,
    description text
);


--
-- TOC entry 1553 (class 1259 OID 17388)
-- Dependencies: 6
-- Name: classifier_applied_for_dot; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE classifier_applied_for_dot (
    classifier_id numeric(18,0) NOT NULL,
    data_object_type_id integer NOT NULL
);


--
-- TOC entry 1552 (class 1259 OID 17370)
-- Dependencies: 1838 6
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
-- TOC entry 1554 (class 1259 OID 17393)
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
-- TOC entry 1537 (class 1259 OID 16762)
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
-- TOC entry 1538 (class 1259 OID 16768)
-- Dependencies: 1822 1823 1824 1825 1826 1827 6
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
-- TOC entry 1539 (class 1259 OID 16780)
-- Dependencies: 6
-- Name: enum_classes; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE enum_classes (
    enum_class_id integer NOT NULL,
    enum_class_name character varying(255) NOT NULL
);


--
-- TOC entry 275 (class 1247 OID 16408)
-- Dependencies: 6 1533
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
-- TOC entry 1540 (class 1259 OID 16783)
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
-- TOC entry 1930 (class 0 OID 0)
-- Dependencies: 1540
-- Name: COLUMN pattern_mask_formats.format_type; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN pattern_mask_formats.format_type IS 'D for DateFormatter;
N for NumberFormatter;
M for MaskFormatter.';


--
-- TOC entry 1541 (class 1259 OID 16789)
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
-- TOC entry 1542 (class 1259 OID 16795)
-- Dependencies: 1828 1829 1830 1831 1832 1833 6
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
-- TOC entry 317 (class 1247 OID 16417)
-- Dependencies: 6 1536
-- Name: proxyinfo; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE proxyinfo AS (
	serverversionstr text,
	serverversionnum integer,
	proxyapiver integer,
	serverprocessid integer
);


--
-- TOC entry 1543 (class 1259 OID 16807)
-- Dependencies: 6
-- Name: resource_bundle; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE resource_bundle (
    resource_id integer NOT NULL,
    enum_class_id integer NOT NULL,
    enum_name character varying(64) NOT NULL
);


--
-- TOC entry 1544 (class 1259 OID 16810)
-- Dependencies: 1834 6
-- Name: sequence_identifiers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE sequence_identifiers (
    seq_id_key bigint NOT NULL,
    seq_id_name character varying(64) NOT NULL,
    seq_id_value numeric(38,0) DEFAULT 0 NOT NULL
);


--
-- TOC entry 313 (class 1247 OID 16411)
-- Dependencies: 6 1534
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
-- TOC entry 1545 (class 1259 OID 16814)
-- Dependencies: 1836 1837 6
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
-- Dependencies: 6 1535
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
-- Dependencies: 6
-- Name: pldbg_abort_target(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_abort_target(session integer) RETURNS SETOF boolean
    AS '$libdir/pldbgapi', 'pldbg_abort_target'
    LANGUAGE c STRICT;


--
-- TOC entry 22 (class 1255 OID 16420)
-- Dependencies: 6
-- Name: pldbg_attach_to_port(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_attach_to_port(portnumber integer) RETURNS integer
    AS '$libdir/pldbgapi', 'pldbg_attach_to_port'
    LANGUAGE c STRICT;


--
-- TOC entry 23 (class 1255 OID 16421)
-- Dependencies: 6 273
-- Name: pldbg_continue(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_continue(session integer) RETURNS breakpoint
    AS '$libdir/pldbgapi', 'pldbg_continue'
    LANGUAGE c STRICT;


--
-- TOC entry 24 (class 1255 OID 16422)
-- Dependencies: 6
-- Name: pldbg_create_listener(); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_create_listener() RETURNS integer
    AS '$libdir/pldbgapi', 'pldbg_create_listener'
    LANGUAGE c STRICT;


--
-- TOC entry 25 (class 1255 OID 16423)
-- Dependencies: 6
-- Name: pldbg_deposit_value(integer, text, integer, text); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_deposit_value(session integer, varname text, linenumber integer, value text) RETURNS boolean
    AS '$libdir/pldbgapi', 'pldbg_deposit_value'
    LANGUAGE c STRICT;


--
-- TOC entry 26 (class 1255 OID 16424)
-- Dependencies: 6
-- Name: pldbg_drop_breakpoint(integer, oid, integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_drop_breakpoint(session integer, func oid, linenumber integer) RETURNS boolean
    AS '$libdir/pldbgapi', 'pldbg_drop_breakpoint'
    LANGUAGE c STRICT;


--
-- TOC entry 27 (class 1255 OID 16425)
-- Dependencies: 273 6
-- Name: pldbg_get_breakpoints(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_get_breakpoints(session integer) RETURNS SETOF breakpoint
    AS '$libdir/pldbgapi', 'pldbg_get_breakpoints'
    LANGUAGE c STRICT;


--
-- TOC entry 30 (class 1255 OID 16428)
-- Dependencies: 317 6
-- Name: pldbg_get_proxy_info(); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_get_proxy_info() RETURNS proxyinfo
    AS '$libdir/pldbgapi', 'pldbg_get_proxy_info'
    LANGUAGE c STRICT;


--
-- TOC entry 28 (class 1255 OID 16426)
-- Dependencies: 6
-- Name: pldbg_get_source(integer, oid); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_get_source(session integer, func oid) RETURNS text
    AS '$libdir/pldbgapi', 'pldbg_get_source'
    LANGUAGE c STRICT;


--
-- TOC entry 29 (class 1255 OID 16427)
-- Dependencies: 6 275
-- Name: pldbg_get_stack(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_get_stack(session integer) RETURNS SETOF frame
    AS '$libdir/pldbgapi', 'pldbg_get_stack'
    LANGUAGE c STRICT;


--
-- TOC entry 39 (class 1255 OID 16437)
-- Dependencies: 6 313
-- Name: pldbg_get_target_info(text, "char"); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_get_target_info(signature text, targettype "char") RETURNS targetinfo
    AS '$libdir/targetinfo', 'pldbg_get_target_info'
    LANGUAGE c STRICT;


--
-- TOC entry 31 (class 1255 OID 16429)
-- Dependencies: 315 6
-- Name: pldbg_get_variables(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_get_variables(session integer) RETURNS SETOF var
    AS '$libdir/pldbgapi', 'pldbg_get_variables'
    LANGUAGE c STRICT;


--
-- TOC entry 32 (class 1255 OID 16430)
-- Dependencies: 6 273
-- Name: pldbg_select_frame(integer, integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_select_frame(session integer, frame integer) RETURNS breakpoint
    AS '$libdir/pldbgapi', 'pldbg_select_frame'
    LANGUAGE c STRICT;


--
-- TOC entry 33 (class 1255 OID 16431)
-- Dependencies: 6
-- Name: pldbg_set_breakpoint(integer, oid, integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_set_breakpoint(session integer, func oid, linenumber integer) RETURNS boolean
    AS '$libdir/pldbgapi', 'pldbg_set_breakpoint'
    LANGUAGE c STRICT;


--
-- TOC entry 34 (class 1255 OID 16432)
-- Dependencies: 6
-- Name: pldbg_set_global_breakpoint(integer, oid, integer, integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_set_global_breakpoint(session integer, func oid, linenumber integer, targetpid integer) RETURNS boolean
    AS '$libdir/pldbgapi', 'pldbg_set_global_breakpoint'
    LANGUAGE c;


--
-- TOC entry 35 (class 1255 OID 16433)
-- Dependencies: 6 273
-- Name: pldbg_step_into(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_step_into(session integer) RETURNS breakpoint
    AS '$libdir/pldbgapi', 'pldbg_step_into'
    LANGUAGE c STRICT;


--
-- TOC entry 36 (class 1255 OID 16434)
-- Dependencies: 273 6
-- Name: pldbg_step_over(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_step_over(session integer) RETURNS breakpoint
    AS '$libdir/pldbgapi', 'pldbg_step_over'
    LANGUAGE c STRICT;


--
-- TOC entry 37 (class 1255 OID 16435)
-- Dependencies: 273 6
-- Name: pldbg_wait_for_breakpoint(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_wait_for_breakpoint(session integer) RETURNS breakpoint
    AS '$libdir/pldbgapi', 'pldbg_wait_for_breakpoint'
    LANGUAGE c STRICT;


--
-- TOC entry 38 (class 1255 OID 16436)
-- Dependencies: 6
-- Name: pldbg_wait_for_target(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_wait_for_target(session integer) RETURNS integer
    AS '$libdir/pldbgapi', 'pldbg_wait_for_target'
    LANGUAGE c STRICT;


--
-- TOC entry 20 (class 1255 OID 16418)
-- Dependencies: 6
-- Name: plpgsql_oid_debug(oid); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION plpgsql_oid_debug(functionoid oid) RETURNS integer
    AS '$libdir/plugins/plugin_debugger', 'plpgsql_oid_debug'
    LANGUAGE c STRICT;


--
-- TOC entry 1546 (class 1259 OID 16822)
-- Dependencies: 6
-- Name: data_object_type_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE data_object_type_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1931 (class 0 OID 0)
-- Dependencies: 1546
-- Name: data_object_type_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('data_object_type_seq', 36, true);


--
-- TOC entry 1547 (class 1259 OID 16824)
-- Dependencies: 6
-- Name: data_objects_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE data_objects_seq
    INCREMENT BY 1
    MAXVALUE 999999999999999999
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1932 (class 0 OID 0)
-- Dependencies: 1547
-- Name: data_objects_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('data_objects_seq', 2, true);


--
-- TOC entry 1548 (class 1259 OID 16826)
-- Dependencies: 6
-- Name: enum_classes_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE enum_classes_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1933 (class 0 OID 0)
-- Dependencies: 1548
-- Name: enum_classes_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('enum_classes_seq', 1, true);


--
-- TOC entry 1549 (class 1259 OID 16828)
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
-- TOC entry 1934 (class 0 OID 0)
-- Dependencies: 1549
-- Name: pattern_mask_formats_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('pattern_mask_formats_seq', 1, false);


--
-- TOC entry 1550 (class 1259 OID 16830)
-- Dependencies: 6
-- Name: resource_bundle_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE resource_bundle_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1935 (class 0 OID 0)
-- Dependencies: 1550
-- Name: resource_bundle_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('resource_bundle_seq', 18, true);


--
-- TOC entry 1551 (class 1259 OID 16832)
-- Dependencies: 6 1544
-- Name: sequence_identifiers_seq_id_key_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE sequence_identifiers_seq_id_key_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1936 (class 0 OID 0)
-- Dependencies: 1551
-- Name: sequence_identifiers_seq_id_key_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE sequence_identifiers_seq_id_key_seq OWNED BY sequence_identifiers.seq_id_key;


--
-- TOC entry 1937 (class 0 OID 0)
-- Dependencies: 1551
-- Name: sequence_identifiers_seq_id_key_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('sequence_identifiers_seq_id_key_seq', 1, false);


--
-- TOC entry 1835 (class 2604 OID 16834)
-- Dependencies: 1551 1544
-- Name: seq_id_key; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE sequence_identifiers ALTER COLUMN seq_id_key SET DEFAULT nextval('sequence_identifiers_seq_id_key_seq'::regclass);


--
-- TOC entry 1924 (class 0 OID 17425)
-- Dependencies: 1555
-- Data for Name: classified_objects; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1922 (class 0 OID 17388)
-- Dependencies: 1553
-- Data for Name: classifier_applied_for_dot; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1921 (class 0 OID 17370)
-- Dependencies: 1552
-- Data for Name: classifier_groups; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1923 (class 0 OID 17393)
-- Dependencies: 1554
-- Data for Name: classifiers; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1912 (class 0 OID 16762)
-- Dependencies: 1537
-- Data for Name: data_object_types; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) VALUES (36, 'com.cosmos.acacia.crm.data.Product', NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) VALUES (35, 'com.cosmos.acacia.crm.data.ProductCategory', NULL, NULL, NULL, NULL, NULL);


--
-- TOC entry 1913 (class 0 OID 16768)
-- Dependencies: 1538
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
-- TOC entry 1914 (class 0 OID 16780)
-- Dependencies: 1539
-- Data for Name: enum_classes; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO enum_classes (enum_class_id, enum_class_name) VALUES (1, 'com.cosmos.acacia.crm.enums.MeasurementUnit');


--
-- TOC entry 1915 (class 0 OID 16783)
-- Dependencies: 1540
-- Data for Name: pattern_mask_formats; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1916 (class 0 OID 16789)
-- Dependencies: 1541
-- Data for Name: product_categories; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO product_categories (product_category_id, parent_id, category_name, pattern_mask_format_id, description) VALUES (1, NULL, '1st Product Category', NULL, NULL);


--
-- TOC entry 1917 (class 0 OID 16795)
-- Dependencies: 1542
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO products (product_id, parent_id, category_id, product_name, product_code, measure_unit_id, is_complex, is_purchased, is_salable, is_obsolete, pattern_mask_format_id, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id) VALUES (1203863479289, NULL, 1, 'p2', 'pc2', 2, false, false, true, false, NULL, NULL, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO products (product_id, parent_id, category_id, product_name, product_code, measure_unit_id, is_complex, is_purchased, is_salable, is_obsolete, pattern_mask_format_id, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id) VALUES (1203863458084, NULL, 1, 'p1', 'pc1', 1, true, false, true, false, NULL, NULL, 1.0000, 3.0000, 2.0000, 100.2000, 200.0000, 250.0000, 1, 16, 1.20, 2.30, 3.50, NULL, 23.450, 12, 'My description', NULL);


--
-- TOC entry 1918 (class 0 OID 16807)
-- Dependencies: 1543
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
-- TOC entry 1919 (class 0 OID 16810)
-- Dependencies: 1544
-- Data for Name: sequence_identifiers; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1920 (class 0 OID 16814)
-- Dependencies: 1545
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1884 (class 2606 OID 17432)
-- Dependencies: 1555 1555 1555
-- Name: pk_classified_objects; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classified_objects
    ADD CONSTRAINT pk_classified_objects PRIMARY KEY (classifier_id, classified_object_id);


--
-- TOC entry 1876 (class 2606 OID 17392)
-- Dependencies: 1553 1553 1553
-- Name: pk_classifier_applied_for_dot; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifier_applied_for_dot
    ADD CONSTRAINT pk_classifier_applied_for_dot PRIMARY KEY (classifier_id, data_object_type_id);


--
-- TOC entry 1870 (class 2606 OID 17378)
-- Dependencies: 1552 1552
-- Name: pk_classifier_groups; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifier_groups
    ADD CONSTRAINT pk_classifier_groups PRIMARY KEY (classifier_group_id);


--
-- TOC entry 1878 (class 2606 OID 17400)
-- Dependencies: 1554 1554
-- Name: pk_classifiers; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT pk_classifiers PRIMARY KEY (classifier_id);


--
-- TOC entry 1840 (class 2606 OID 16836)
-- Dependencies: 1537 1537
-- Name: pk_data_object_types; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_types
    ADD CONSTRAINT pk_data_object_types PRIMARY KEY (data_object_type_id);


--
-- TOC entry 1844 (class 2606 OID 16838)
-- Dependencies: 1538 1538
-- Name: pk_data_objects; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT pk_data_objects PRIMARY KEY (data_object_id);


--
-- TOC entry 1846 (class 2606 OID 16840)
-- Dependencies: 1539 1539
-- Name: pk_enum_classes; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY enum_classes
    ADD CONSTRAINT pk_enum_classes PRIMARY KEY (enum_class_id);


--
-- TOC entry 1850 (class 2606 OID 16842)
-- Dependencies: 1540 1540
-- Name: pk_pattern_mask_formats; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pattern_mask_formats
    ADD CONSTRAINT pk_pattern_mask_formats PRIMARY KEY (pattern_mask_format_id);


--
-- TOC entry 1854 (class 2606 OID 16844)
-- Dependencies: 1541 1541
-- Name: pk_product_categories; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT pk_product_categories PRIMARY KEY (product_category_id);


--
-- TOC entry 1858 (class 2606 OID 16846)
-- Dependencies: 1542 1542
-- Name: pk_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY products
    ADD CONSTRAINT pk_products PRIMARY KEY (product_id);


--
-- TOC entry 1860 (class 2606 OID 16848)
-- Dependencies: 1543 1543
-- Name: pk_resource_bundle; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY resource_bundle
    ADD CONSTRAINT pk_resource_bundle PRIMARY KEY (resource_id);


--
-- TOC entry 1864 (class 2606 OID 16850)
-- Dependencies: 1545 1545
-- Name: pk_users; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT pk_users PRIMARY KEY (user_id);


--
-- TOC entry 1872 (class 2606 OID 17380)
-- Dependencies: 1552 1552 1552
-- Name: uk_classifier_groups_parent_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifier_groups
    ADD CONSTRAINT uk_classifier_groups_parent_code UNIQUE (parent_id, classifier_group_code);


--
-- TOC entry 1874 (class 2606 OID 17387)
-- Dependencies: 1552 1552 1552
-- Name: uk_classifier_groups_parent_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifier_groups
    ADD CONSTRAINT uk_classifier_groups_parent_name UNIQUE (parent_id, classifier_group_name);


--
-- TOC entry 1880 (class 2606 OID 17407)
-- Dependencies: 1554 1554 1554
-- Name: uk_classifiers_parent_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT uk_classifiers_parent_code UNIQUE (parent_id, classifier_code);


--
-- TOC entry 1882 (class 2606 OID 17409)
-- Dependencies: 1554 1554 1554
-- Name: uk_classifiers_parent_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT uk_classifiers_parent_name UNIQUE (parent_id, classifier_name);


--
-- TOC entry 1842 (class 2606 OID 16852)
-- Dependencies: 1537 1537
-- Name: uk_dot_data_object_type; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_types
    ADD CONSTRAINT uk_dot_data_object_type UNIQUE (data_object_type);


--
-- TOC entry 1848 (class 2606 OID 16854)
-- Dependencies: 1539 1539
-- Name: uk_enum_classes; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY enum_classes
    ADD CONSTRAINT uk_enum_classes UNIQUE (enum_class_name);


--
-- TOC entry 1852 (class 2606 OID 16856)
-- Dependencies: 1540 1540 1540
-- Name: uk_pattern_mask_formats_owner_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pattern_mask_formats
    ADD CONSTRAINT uk_pattern_mask_formats_owner_name UNIQUE (owner_id, pattern_name);


--
-- TOC entry 1856 (class 2606 OID 16858)
-- Dependencies: 1541 1541 1541
-- Name: uk_product_categories_parent_category_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT uk_product_categories_parent_category_name UNIQUE (parent_id, category_name);


--
-- TOC entry 1862 (class 2606 OID 16860)
-- Dependencies: 1544 1544
-- Name: uk_seq_id_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY sequence_identifiers
    ADD CONSTRAINT uk_seq_id_name UNIQUE (seq_id_name);


--
-- TOC entry 1866 (class 2606 OID 16862)
-- Dependencies: 1545 1545
-- Name: uk_users_email_address; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT uk_users_email_address UNIQUE (email_address);


--
-- TOC entry 1868 (class 2606 OID 16864)
-- Dependencies: 1545 1545
-- Name: uk_users_user_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT uk_users_user_name UNIQUE (user_name);


--
-- TOC entry 1901 (class 2606 OID 16865)
-- Dependencies: 1545 1545 1863
-- Name: fk6a68e0844bb6904; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk6a68e0844bb6904 FOREIGN KEY (creator_id) REFERENCES users(user_id);


--
-- TOC entry 1902 (class 2606 OID 16870)
-- Dependencies: 1545 1538 1843
-- Name: fk6a68e08a08870b9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk6a68e08a08870b9 FOREIGN KEY (person_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1885 (class 2606 OID 16875)
-- Dependencies: 1538 1843 1538
-- Name: fk74e5117f6017f920; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk74e5117f6017f920 FOREIGN KEY (linked_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1886 (class 2606 OID 16880)
-- Dependencies: 1843 1538 1538
-- Name: fk74e5117fdfc2026f; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk74e5117fdfc2026f FOREIGN KEY (parent_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1910 (class 2606 OID 17433)
-- Dependencies: 1877 1555 1554
-- Name: fk_classified_objects_classifier_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classified_objects
    ADD CONSTRAINT fk_classified_objects_classifier_id FOREIGN KEY (classifier_id) REFERENCES classifiers(classifier_id);


--
-- TOC entry 1911 (class 2606 OID 17438)
-- Dependencies: 1538 1555 1843
-- Name: fk_classified_objects_object_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classified_objects
    ADD CONSTRAINT fk_classified_objects_object_id FOREIGN KEY (classified_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1906 (class 2606 OID 17415)
-- Dependencies: 1553 1554 1877
-- Name: fk_classifier_applied_for_dot_classifiers; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifier_applied_for_dot
    ADD CONSTRAINT fk_classifier_applied_for_dot_classifiers FOREIGN KEY (classifier_id) REFERENCES classifiers(classifier_id);


--
-- TOC entry 1907 (class 2606 OID 17420)
-- Dependencies: 1839 1553 1537
-- Name: fk_classifier_applied_for_dot_dot_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifier_applied_for_dot
    ADD CONSTRAINT fk_classifier_applied_for_dot_dot_id FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 1905 (class 2606 OID 17381)
-- Dependencies: 1843 1552 1538
-- Name: fk_classifier_groups_cg_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifier_groups
    ADD CONSTRAINT fk_classifier_groups_cg_id FOREIGN KEY (classifier_group_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1909 (class 2606 OID 17410)
-- Dependencies: 1552 1869 1554
-- Name: fk_classifiers_group_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT fk_classifiers_group_id FOREIGN KEY (classifier_group_id) REFERENCES classifier_groups(classifier_group_id);


--
-- TOC entry 1908 (class 2606 OID 17401)
-- Dependencies: 1554 1538 1843
-- Name: fk_classifiers_id_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT fk_classifiers_id_do_id FOREIGN KEY (classifier_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1887 (class 2606 OID 16885)
-- Dependencies: 1843 1538 1538
-- Name: fk_data_objects_linked_data_object_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk_data_objects_linked_data_object_id FOREIGN KEY (linked_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1888 (class 2606 OID 16890)
-- Dependencies: 1538 1843 1538
-- Name: fk_data_objects_parent_data_object_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk_data_objects_parent_data_object_id FOREIGN KEY (parent_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1889 (class 2606 OID 16895)
-- Dependencies: 1843 1538 1538
-- Name: fk_do_linked_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk_do_linked_id FOREIGN KEY (linked_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1890 (class 2606 OID 16900)
-- Dependencies: 1538 1538 1843
-- Name: fk_do_parent_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk_do_parent_id FOREIGN KEY (parent_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1891 (class 2606 OID 16905)
-- Dependencies: 1541 1849 1540
-- Name: fk_product_categories_pattern_mask_format_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT fk_product_categories_pattern_mask_format_id FOREIGN KEY (pattern_mask_format_id) REFERENCES pattern_mask_formats(pattern_mask_format_id);


--
-- TOC entry 1892 (class 2606 OID 16910)
-- Dependencies: 1541 1843 1538
-- Name: fk_product_categories_product_category_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT fk_product_categories_product_category_id FOREIGN KEY (product_category_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1893 (class 2606 OID 16915)
-- Dependencies: 1541 1542 1853
-- Name: fk_product_category_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_product_category_id FOREIGN KEY (category_id) REFERENCES product_categories(product_category_id);


--
-- TOC entry 1894 (class 2606 OID 16920)
-- Dependencies: 1543 1542 1859
-- Name: fk_products_color_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_products_color_id FOREIGN KEY (product_color_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 1895 (class 2606 OID 16925)
-- Dependencies: 1859 1543 1542
-- Name: fk_products_dimension_unit_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_products_dimension_unit_id FOREIGN KEY (dimension_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 1896 (class 2606 OID 16930)
-- Dependencies: 1859 1542 1543
-- Name: fk_products_measure_unit_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_products_measure_unit_id FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 1897 (class 2606 OID 16935)
-- Dependencies: 1542 1849 1540
-- Name: fk_products_pattern_mask_format_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_products_pattern_mask_format_id FOREIGN KEY (pattern_mask_format_id) REFERENCES pattern_mask_formats(pattern_mask_format_id);


--
-- TOC entry 1898 (class 2606 OID 16940)
-- Dependencies: 1843 1538 1542
-- Name: fk_products_product_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_products_product_id FOREIGN KEY (product_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1899 (class 2606 OID 16945)
-- Dependencies: 1859 1542 1543
-- Name: fk_products_weight_unit_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_products_weight_unit_id FOREIGN KEY (weight_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 1900 (class 2606 OID 16950)
-- Dependencies: 1543 1539 1845
-- Name: fk_resource_bundle_enum_class_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY resource_bundle
    ADD CONSTRAINT fk_resource_bundle_enum_class_id FOREIGN KEY (enum_class_id) REFERENCES enum_classes(enum_class_id);


--
-- TOC entry 1903 (class 2606 OID 16955)
-- Dependencies: 1545 1545 1863
-- Name: fk_users_creator_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk_users_creator_id FOREIGN KEY (creator_id) REFERENCES users(user_id);


--
-- TOC entry 1904 (class 2606 OID 16960)
-- Dependencies: 1538 1843 1545
-- Name: fk_users_person_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk_users_person_id FOREIGN KEY (person_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 1929 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: -
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2008-02-26 18:27:43

--
-- PostgreSQL database dump complete
--

