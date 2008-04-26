--
-- PostgreSQL database dump
--

-- Started on 2008-04-26 23:35:11

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

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 1547 (class 1259 OID 77042)
-- Dependencies: 6
-- Name: assembling_algorithms; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE assembling_algorithms (
    algorithm_id integer NOT NULL,
    algorithm_code character varying(64) NOT NULL,
    algorithm_name character varying(128) NOT NULL,
    description text
);


--
-- TOC entry 1551 (class 1259 OID 77077)
-- Dependencies: 6
-- Name: assembling_categories; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE assembling_categories (
    assembling_category_id bigint NOT NULL,
    category_code character varying(64) NOT NULL,
    category_name character varying(255) NOT NULL,
    description text
);


--
-- TOC entry 1556 (class 1259 OID 93534)
-- Dependencies: 1830 6
-- Name: assembling_schema_item_values; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE assembling_schema_item_values (
    item_value_id bigint NOT NULL,
    item_id bigint NOT NULL,
    min_constraint bytea,
    max_constraint bytea,
    virtual_product_id bigint NOT NULL,
    quantity numeric(19,4) DEFAULT 1 NOT NULL
);


--
-- TOC entry 1557 (class 1259 OID 93607)
-- Dependencies: 1831 1832 6
-- Name: assembling_schema_items; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE assembling_schema_items (
    item_id bigint NOT NULL,
    assembling_schema_id bigint NOT NULL,
    algorithm_id integer NOT NULL,
    message_code character varying(64) NOT NULL,
    message_text character varying(255) NOT NULL,
    data_type character varying(16) DEFAULT 'Integer'::character varying NOT NULL,
    min_selections integer,
    max_selections integer,
    quantity numeric(19,4) DEFAULT 1 NOT NULL,
    default_value bytea,
    description text
);


--
-- TOC entry 1905 (class 0 OID 0)
-- Dependencies: 1557
-- Name: COLUMN assembling_schema_items.data_type; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN assembling_schema_items.data_type IS 'Integer, Decimal, Date, String';


--
-- TOC entry 1546 (class 1259 OID 77034)
-- Dependencies: 1829 6
-- Name: assembling_schemas; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE assembling_schemas (
    product_id bigint NOT NULL,
    category_id bigint NOT NULL,
    schema_code character varying(64) NOT NULL,
    schema_name character varying(255) NOT NULL,
    is_obsolete boolean DEFAULT false NOT NULL,
    description text
);


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


--
-- TOC entry 1545 (class 1259 OID 68838)
-- Dependencies: 6
-- Name: complex_product_items; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE complex_product_items (
    complex_product_item_id bigint NOT NULL,
    complex_product_id bigint NOT NULL,
    order_position character varying(8),
    product_id bigint NOT NULL,
    quantity numeric(19,4) NOT NULL,
    unit_price numeric(19,4) NOT NULL,
    item_price numeric(19,4) NOT NULL,
    applied_algorithm_info character varying(128)
);


--
-- TOC entry 1537 (class 1259 OID 68782)
-- Dependencies: 6
-- Name: complex_products; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE complex_products (
    product_id bigint NOT NULL,
    product_code character varying(32) NOT NULL,
    assembling_schema_info character varying(128)
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
-- TOC entry 1538 (class 1259 OID 68785)
-- Dependencies: 6
-- Name: offer_items; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE offer_items (
    offer_item_id bigint NOT NULL,
    offer_id bigint NOT NULL,
    product_id bigint NOT NULL,
    item_description character varying(128),
    quantity numeric(19,4),
    unit_price numeric(19,4),
    item_price numeric(19,4)
);


--
-- TOC entry 1539 (class 1259 OID 68788)
-- Dependencies: 6
-- Name: offers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE offers (
    offer_id bigint NOT NULL
);


--
-- TOC entry 1540 (class 1259 OID 68793)
-- Dependencies: 6
-- Name: products; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE products (
    product_id bigint NOT NULL,
    product_type character(2),
    product_price numeric(19,4)
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
-- TOC entry 1550 (class 1259 OID 77055)
-- Dependencies: 6
-- Name: real_products; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE real_products (
    product_id bigint NOT NULL,
    simple_product_id bigint NOT NULL
);


--
-- TOC entry 1541 (class 1259 OID 68797)
-- Dependencies: 6
-- Name: simple_products; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE simple_products (
    product_id bigint NOT NULL,
    product_code character varying(32) NOT NULL
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
-- TOC entry 1548 (class 1259 OID 77048)
-- Dependencies: 6
-- Name: virtual_products; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE virtual_products (
    product_id bigint NOT NULL,
    product_type character(2)
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
-- Dependencies: 273 6
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
-- Dependencies: 6 273
-- Name: pldbg_get_breakpoints(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_get_breakpoints(session integer) RETURNS SETOF breakpoint
    AS '$libdir/pldbgapi', 'pldbg_get_breakpoints'
    LANGUAGE c STRICT;


--
-- TOC entry 30 (class 1255 OID 16428)
-- Dependencies: 6 317
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
-- Dependencies: 6 273
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
-- TOC entry 1555 (class 1259 OID 85344)
-- Dependencies: 6
-- Name: assembling_algorithms_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE assembling_algorithms_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1906 (class 0 OID 0)
-- Dependencies: 1555
-- Name: assembling_algorithms_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('assembling_algorithms_seq', 10, true);


--
-- TOC entry 1554 (class 1259 OID 85342)
-- Dependencies: 6
-- Name: assembling_categories_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE assembling_categories_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1907 (class 0 OID 0)
-- Dependencies: 1554
-- Name: assembling_categories_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('assembling_categories_seq', 8, true);


--
-- TOC entry 1559 (class 1259 OID 93634)
-- Dependencies: 6
-- Name: assembling_schema_item_values_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE assembling_schema_item_values_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1908 (class 0 OID 0)
-- Dependencies: 1559
-- Name: assembling_schema_item_values_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('assembling_schema_item_values_seq', 144, true);


--
-- TOC entry 1558 (class 1259 OID 93632)
-- Dependencies: 6
-- Name: assembling_schema_items_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE assembling_schema_items_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1909 (class 0 OID 0)
-- Dependencies: 1558
-- Name: assembling_schema_items_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('assembling_schema_items_seq', 40, true);


--
-- TOC entry 1544 (class 1259 OID 68836)
-- Dependencies: 6 1545
-- Name: complex_product_items_complex_product_item_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE complex_product_items_complex_product_item_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1910 (class 0 OID 0)
-- Dependencies: 1544
-- Name: complex_product_items_complex_product_item_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE complex_product_items_complex_product_item_id_seq OWNED BY complex_product_items.complex_product_item_id;


--
-- TOC entry 1911 (class 0 OID 0)
-- Dependencies: 1544
-- Name: complex_product_items_complex_product_item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('complex_product_items_complex_product_item_id_seq', 1, false);


--
-- TOC entry 1542 (class 1259 OID 68800)
-- Dependencies: 6 1538
-- Name: offer_items_offer_item_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE offer_items_offer_item_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1912 (class 0 OID 0)
-- Dependencies: 1542
-- Name: offer_items_offer_item_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE offer_items_offer_item_id_seq OWNED BY offer_items.offer_item_id;


--
-- TOC entry 1913 (class 0 OID 0)
-- Dependencies: 1542
-- Name: offer_items_offer_item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('offer_items_offer_item_id_seq', 1, false);


--
-- TOC entry 1543 (class 1259 OID 68802)
-- Dependencies: 6 1539
-- Name: offers_offer_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE offers_offer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1914 (class 0 OID 0)
-- Dependencies: 1543
-- Name: offers_offer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE offers_offer_id_seq OWNED BY offers.offer_id;


--
-- TOC entry 1915 (class 0 OID 0)
-- Dependencies: 1543
-- Name: offers_offer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('offers_offer_id_seq', 1, false);


--
-- TOC entry 1549 (class 1259 OID 77053)
-- Dependencies: 6
-- Name: products_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE products_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1916 (class 0 OID 0)
-- Dependencies: 1549
-- Name: products_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('products_seq', 99, true);


--
-- TOC entry 1553 (class 1259 OID 77163)
-- Dependencies: 6
-- Name: test_table_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE test_table_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1917 (class 0 OID 0)
-- Dependencies: 1553
-- Name: test_table_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('test_table_seq', 3, true);


--
-- TOC entry 1552 (class 1259 OID 77111)
-- Dependencies: 6
-- Name: virtual_products_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE virtual_products_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1918 (class 0 OID 0)
-- Dependencies: 1552
-- Name: virtual_products_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('virtual_products_seq', 92, true);


--
-- TOC entry 1828 (class 2604 OID 68841)
-- Dependencies: 1545 1544 1545
-- Name: complex_product_item_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE complex_product_items ALTER COLUMN complex_product_item_id SET DEFAULT nextval('complex_product_items_complex_product_item_id_seq'::regclass);


--
-- TOC entry 1826 (class 2604 OID 68804)
-- Dependencies: 1542 1538
-- Name: offer_item_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE offer_items ALTER COLUMN offer_item_id SET DEFAULT nextval('offer_items_offer_item_id_seq'::regclass);


--
-- TOC entry 1827 (class 2604 OID 68805)
-- Dependencies: 1543 1539
-- Name: offer_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE offers ALTER COLUMN offer_id SET DEFAULT nextval('offers_offer_id_seq'::regclass);


--
-- TOC entry 1894 (class 0 OID 77042)
-- Dependencies: 1547
-- Data for Name: assembling_algorithms; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO assembling_algorithms (algorithm_id, algorithm_code, algorithm_name, description) VALUES (1, 'UnconditionalSelection', 'UnconditionalSelection', NULL);
INSERT INTO assembling_algorithms (algorithm_id, algorithm_code, algorithm_name, description) VALUES (2, 'UserSelection', 'UserSelection', NULL);
INSERT INTO assembling_algorithms (algorithm_id, algorithm_code, algorithm_name, description) VALUES (3, 'UserSingleSelection', 'UserSingleSelection', NULL);
INSERT INTO assembling_algorithms (algorithm_id, algorithm_code, algorithm_name, description) VALUES (4, 'UserMultipleSelection', 'UserMultipleSelection', NULL);
INSERT INTO assembling_algorithms (algorithm_id, algorithm_code, algorithm_name, description) VALUES (5, 'RangeSelection', 'RangeSelection', NULL);
INSERT INTO assembling_algorithms (algorithm_id, algorithm_code, algorithm_name, description) VALUES (6, 'RangeSingleSelection', 'RangeSingleSelection', NULL);
INSERT INTO assembling_algorithms (algorithm_id, algorithm_code, algorithm_name, description) VALUES (7, 'RangeMultipleSelection', 'RangeMultipleSelection', NULL);
INSERT INTO assembling_algorithms (algorithm_id, algorithm_code, algorithm_name, description) VALUES (8, 'EqualsSelection', 'EqualsSelection', NULL);
INSERT INTO assembling_algorithms (algorithm_id, algorithm_code, algorithm_name, description) VALUES (9, 'EqualsSingleSelection', 'EqualsSingleSelection', NULL);
INSERT INTO assembling_algorithms (algorithm_id, algorithm_code, algorithm_name, description) VALUES (10, 'EqualsMultipleSelection', 'EqualsMultipleSelection', NULL);


--
-- TOC entry 1897 (class 0 OID 77077)
-- Dependencies: 1551
-- Data for Name: assembling_categories; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO assembling_categories (assembling_category_id, category_code, category_name, description) VALUES (8, 'Test Assembling Category', 'Test Assembling Category', NULL);


--
-- TOC entry 1898 (class 0 OID 93534)
-- Dependencies: 1556
-- Data for Name: assembling_schema_item_values; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (73, 21, NULL, NULL, 17, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (74, 21, NULL, NULL, 18, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (75, 22, NULL, NULL, 19, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (76, 22, NULL, NULL, 20, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (77, 22, NULL, NULL, 21, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (78, 23, NULL, NULL, 22, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (79, 23, NULL, NULL, 23, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (80, 23, NULL, NULL, 24, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (81, 24, NULL, NULL, 25, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (82, 24, NULL, NULL, 26, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (83, 24, NULL, NULL, 27, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (84, 24, NULL, NULL, 28, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (85, 25, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\001', '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\005', 29, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (86, 25, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\002', '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\004', 30, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (87, 25, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\004', '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\005', 31, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (88, 25, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\007', '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\010', 32, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (89, 26, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\001', '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\005', 33, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (90, 26, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\002', '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\004', 34, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (91, 26, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\004', '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\005', 35, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (92, 26, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\007', '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\010', 36, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (93, 27, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\001', '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\005', 37, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (94, 27, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\002', '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\004', 38, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (95, 27, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\004', '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\005', 39, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (96, 27, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\007', '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\010', 40, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (97, 28, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\001', NULL, 41, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (98, 28, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\002', NULL, 42, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (99, 28, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\002', NULL, 43, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (100, 28, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\004', NULL, 44, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (101, 29, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\001', NULL, 45, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (102, 29, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\002', NULL, 46, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (103, 29, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\002', NULL, 47, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (104, 29, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\004', NULL, 48, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (105, 30, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\001', NULL, 49, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (106, 30, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\002', NULL, 50, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (107, 30, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\002', NULL, 51, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (108, 30, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\004', NULL, 52, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (109, 31, NULL, NULL, 17, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (110, 31, NULL, NULL, 19, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (111, 32, NULL, NULL, 21, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (112, 32, NULL, NULL, 23, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (113, 32, NULL, NULL, 25, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (114, 33, NULL, NULL, 27, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (115, 33, NULL, NULL, 29, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (116, 33, NULL, NULL, 31, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (117, 34, NULL, NULL, 33, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (118, 34, NULL, NULL, 35, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (119, 34, NULL, NULL, 37, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (120, 34, NULL, NULL, 39, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (121, 35, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\001', '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\005', 41, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (122, 35, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\002', '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\004', 43, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (123, 35, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\004', '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\005', 45, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (124, 35, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\007', '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\010', 47, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (125, 36, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\001', '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\005', 49, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (126, 36, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\002', '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\004', 51, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (127, 36, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\004', '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\005', 53, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (128, 36, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\007', '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\010', 55, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (129, 37, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\001', '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\005', 57, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (130, 37, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\002', '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\004', 59, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (131, 37, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\004', '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\005', 61, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (132, 37, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\007', '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\010', 63, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (133, 38, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\001', NULL, 65, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (134, 38, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\002', NULL, 67, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (135, 38, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\002', NULL, 69, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (136, 38, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\004', NULL, 71, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (137, 39, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\001', NULL, 73, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (138, 39, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\002', NULL, 75, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (139, 39, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\002', NULL, 77, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (140, 39, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\004', NULL, 79, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (141, 40, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\001', NULL, 81, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (142, 40, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\002', NULL, 83, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (143, 40, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\002', NULL, 85, 1.0000);
INSERT INTO assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) VALUES (144, 40, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\004', NULL, 87, 1.0000);


--
-- TOC entry 1899 (class 0 OID 93607)
-- Dependencies: 1557
-- Data for Name: assembling_schema_items; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO assembling_schema_items (item_id, assembling_schema_id, algorithm_id, message_code, message_text, data_type, min_selections, max_selections, quantity, default_value, description) VALUES (21, 91, 1, 'UnconditionalSelection', 'UnconditionalSelection', 'Integer', NULL, NULL, 1.0000, NULL, NULL);
INSERT INTO assembling_schema_items (item_id, assembling_schema_id, algorithm_id, message_code, message_text, data_type, min_selections, max_selections, quantity, default_value, description) VALUES (22, 91, 2, 'UserSelection', 'UserSelection', 'Integer', NULL, NULL, 1.0000, NULL, NULL);
INSERT INTO assembling_schema_items (item_id, assembling_schema_id, algorithm_id, message_code, message_text, data_type, min_selections, max_selections, quantity, default_value, description) VALUES (23, 91, 3, 'UserSingleSelection', 'UserSingleSelection', 'Integer', 1, 1, 1.0000, NULL, NULL);
INSERT INTO assembling_schema_items (item_id, assembling_schema_id, algorithm_id, message_code, message_text, data_type, min_selections, max_selections, quantity, default_value, description) VALUES (24, 91, 4, 'UserMultipleSelection', 'UserMultipleSelection', 'Integer', 2, 3, 1.0000, NULL, NULL);
INSERT INTO assembling_schema_items (item_id, assembling_schema_id, algorithm_id, message_code, message_text, data_type, min_selections, max_selections, quantity, default_value, description) VALUES (25, 91, 5, 'RangeSelection', 'RangeSelection', 'Integer', NULL, NULL, 1.0000, NULL, NULL);
INSERT INTO assembling_schema_items (item_id, assembling_schema_id, algorithm_id, message_code, message_text, data_type, min_selections, max_selections, quantity, default_value, description) VALUES (26, 91, 6, 'RangeSingleSelection', 'RangeSingleSelection', 'Integer', 1, 1, 1.0000, NULL, NULL);
INSERT INTO assembling_schema_items (item_id, assembling_schema_id, algorithm_id, message_code, message_text, data_type, min_selections, max_selections, quantity, default_value, description) VALUES (27, 91, 7, 'RangeMultipleSelection', 'RangeMultipleSelection', 'Integer', 2, 3, 1.0000, NULL, NULL);
INSERT INTO assembling_schema_items (item_id, assembling_schema_id, algorithm_id, message_code, message_text, data_type, min_selections, max_selections, quantity, default_value, description) VALUES (28, 91, 8, 'EqualsSelection', 'EqualsSelection', 'Integer', NULL, NULL, 1.0000, NULL, NULL);
INSERT INTO assembling_schema_items (item_id, assembling_schema_id, algorithm_id, message_code, message_text, data_type, min_selections, max_selections, quantity, default_value, description) VALUES (29, 91, 9, 'EqualsSingleSelection', 'EqualsSingleSelection', 'Integer', 1, 1, 1.0000, NULL, NULL);
INSERT INTO assembling_schema_items (item_id, assembling_schema_id, algorithm_id, message_code, message_text, data_type, min_selections, max_selections, quantity, default_value, description) VALUES (30, 91, 10, 'EqualsMultipleSelection', 'EqualsMultipleSelection', 'Integer', 2, 3, 1.0000, NULL, NULL);
INSERT INTO assembling_schema_items (item_id, assembling_schema_id, algorithm_id, message_code, message_text, data_type, min_selections, max_selections, quantity, default_value, description) VALUES (31, 92, 1, 'UnconditionalSelection', 'UnconditionalSelection', 'Integer', NULL, NULL, 1.0000, NULL, NULL);
INSERT INTO assembling_schema_items (item_id, assembling_schema_id, algorithm_id, message_code, message_text, data_type, min_selections, max_selections, quantity, default_value, description) VALUES (32, 92, 2, 'UserSelection', 'UserSelection', 'Integer', NULL, NULL, 1.0000, NULL, NULL);
INSERT INTO assembling_schema_items (item_id, assembling_schema_id, algorithm_id, message_code, message_text, data_type, min_selections, max_selections, quantity, default_value, description) VALUES (33, 92, 3, 'UserSingleSelection', 'UserSingleSelection', 'Integer', 1, 1, 1.0000, NULL, NULL);
INSERT INTO assembling_schema_items (item_id, assembling_schema_id, algorithm_id, message_code, message_text, data_type, min_selections, max_selections, quantity, default_value, description) VALUES (34, 92, 4, 'UserMultipleSelection', 'UserMultipleSelection', 'Integer', 2, 3, 1.0000, NULL, NULL);
INSERT INTO assembling_schema_items (item_id, assembling_schema_id, algorithm_id, message_code, message_text, data_type, min_selections, max_selections, quantity, default_value, description) VALUES (35, 92, 5, 'RangeSelection', 'RangeSelection', 'Integer', NULL, NULL, 1.0000, NULL, NULL);
INSERT INTO assembling_schema_items (item_id, assembling_schema_id, algorithm_id, message_code, message_text, data_type, min_selections, max_selections, quantity, default_value, description) VALUES (36, 92, 6, 'RangeSingleSelection', 'RangeSingleSelection', 'Integer', 1, 1, 1.0000, NULL, NULL);
INSERT INTO assembling_schema_items (item_id, assembling_schema_id, algorithm_id, message_code, message_text, data_type, min_selections, max_selections, quantity, default_value, description) VALUES (37, 92, 7, 'RangeMultipleSelection', 'RangeMultipleSelection', 'Integer', 2, 3, 1.0000, NULL, NULL);
INSERT INTO assembling_schema_items (item_id, assembling_schema_id, algorithm_id, message_code, message_text, data_type, min_selections, max_selections, quantity, default_value, description) VALUES (38, 92, 8, 'EqualsSelection', 'EqualsSelection', 'Integer', NULL, NULL, 1.0000, NULL, NULL);
INSERT INTO assembling_schema_items (item_id, assembling_schema_id, algorithm_id, message_code, message_text, data_type, min_selections, max_selections, quantity, default_value, description) VALUES (39, 92, 9, 'EqualsSingleSelection', 'EqualsSingleSelection', 'Integer', 1, 1, 1.0000, NULL, NULL);
INSERT INTO assembling_schema_items (item_id, assembling_schema_id, algorithm_id, message_code, message_text, data_type, min_selections, max_selections, quantity, default_value, description) VALUES (40, 92, 10, 'EqualsMultipleSelection', 'EqualsMultipleSelection', 'Integer', 2, 3, 1.0000, NULL, NULL);


--
-- TOC entry 1893 (class 0 OID 77034)
-- Dependencies: 1546
-- Data for Name: assembling_schemas; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO assembling_schemas (product_id, category_id, schema_code, schema_name, is_obsolete, description) VALUES (91, 8, 'Test Schema Code 01', 'Test Schema Code 01', false, NULL);
INSERT INTO assembling_schemas (product_id, category_id, schema_code, schema_name, is_obsolete, description) VALUES (92, 8, 'Test Schema Code 02', 'Test Schema Code 02', false, NULL);


--
-- TOC entry 1892 (class 0 OID 68838)
-- Dependencies: 1545
-- Data for Name: complex_product_items; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1887 (class 0 OID 68782)
-- Dependencies: 1537
-- Data for Name: complex_products; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1888 (class 0 OID 68785)
-- Dependencies: 1538
-- Data for Name: offer_items; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1889 (class 0 OID 68788)
-- Dependencies: 1539
-- Data for Name: offers; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1890 (class 0 OID 68793)
-- Dependencies: 1540
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO products (product_id, product_type, product_price) VALUES (28, NULL, 1.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (29, NULL, 2.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (30, NULL, 3.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (31, NULL, 4.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (32, NULL, 5.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (33, NULL, 6.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (34, NULL, 7.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (35, NULL, 8.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (36, NULL, 9.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (37, NULL, 10.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (38, NULL, 11.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (39, NULL, 12.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (40, NULL, 13.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (41, NULL, 14.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (42, NULL, 15.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (43, NULL, 16.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (44, NULL, 17.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (45, NULL, 18.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (46, NULL, 19.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (47, NULL, 20.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (48, NULL, 21.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (49, NULL, 22.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (50, NULL, 23.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (51, NULL, 24.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (52, NULL, 25.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (53, NULL, 26.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (54, NULL, 27.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (55, NULL, 28.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (56, NULL, 29.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (57, NULL, 30.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (58, NULL, 31.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (59, NULL, 32.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (60, NULL, 33.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (61, NULL, 34.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (62, NULL, 35.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (63, NULL, 36.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (64, NULL, 37.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (65, NULL, 38.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (66, NULL, 39.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (67, NULL, 40.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (68, NULL, 41.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (69, NULL, 42.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (70, NULL, 43.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (71, NULL, 44.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (72, NULL, 45.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (73, NULL, 46.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (74, NULL, 47.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (75, NULL, 48.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (76, NULL, 49.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (77, NULL, 50.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (78, NULL, 51.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (79, NULL, 52.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (80, NULL, 53.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (81, NULL, 54.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (82, NULL, 55.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (83, NULL, 56.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (84, NULL, 57.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (85, NULL, 58.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (86, NULL, 59.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (87, NULL, 60.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (88, NULL, 61.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (89, NULL, 62.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (90, NULL, 63.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (91, NULL, 64.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (92, NULL, 65.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (93, NULL, 66.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (94, NULL, 67.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (95, NULL, 68.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (96, NULL, 69.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (97, NULL, 70.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (98, NULL, 71.0000);
INSERT INTO products (product_id, product_type, product_price) VALUES (99, NULL, 72.0000);


--
-- TOC entry 1896 (class 0 OID 77055)
-- Dependencies: 1550
-- Data for Name: real_products; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO real_products (product_id, simple_product_id) VALUES (17, 28);
INSERT INTO real_products (product_id, simple_product_id) VALUES (18, 29);
INSERT INTO real_products (product_id, simple_product_id) VALUES (19, 30);
INSERT INTO real_products (product_id, simple_product_id) VALUES (20, 31);
INSERT INTO real_products (product_id, simple_product_id) VALUES (21, 32);
INSERT INTO real_products (product_id, simple_product_id) VALUES (22, 33);
INSERT INTO real_products (product_id, simple_product_id) VALUES (23, 34);
INSERT INTO real_products (product_id, simple_product_id) VALUES (24, 35);
INSERT INTO real_products (product_id, simple_product_id) VALUES (25, 36);
INSERT INTO real_products (product_id, simple_product_id) VALUES (26, 37);
INSERT INTO real_products (product_id, simple_product_id) VALUES (27, 38);
INSERT INTO real_products (product_id, simple_product_id) VALUES (28, 39);
INSERT INTO real_products (product_id, simple_product_id) VALUES (29, 40);
INSERT INTO real_products (product_id, simple_product_id) VALUES (30, 41);
INSERT INTO real_products (product_id, simple_product_id) VALUES (31, 42);
INSERT INTO real_products (product_id, simple_product_id) VALUES (32, 43);
INSERT INTO real_products (product_id, simple_product_id) VALUES (33, 44);
INSERT INTO real_products (product_id, simple_product_id) VALUES (34, 45);
INSERT INTO real_products (product_id, simple_product_id) VALUES (35, 46);
INSERT INTO real_products (product_id, simple_product_id) VALUES (36, 47);
INSERT INTO real_products (product_id, simple_product_id) VALUES (37, 48);
INSERT INTO real_products (product_id, simple_product_id) VALUES (38, 49);
INSERT INTO real_products (product_id, simple_product_id) VALUES (39, 50);
INSERT INTO real_products (product_id, simple_product_id) VALUES (40, 51);
INSERT INTO real_products (product_id, simple_product_id) VALUES (41, 52);
INSERT INTO real_products (product_id, simple_product_id) VALUES (42, 53);
INSERT INTO real_products (product_id, simple_product_id) VALUES (43, 54);
INSERT INTO real_products (product_id, simple_product_id) VALUES (44, 55);
INSERT INTO real_products (product_id, simple_product_id) VALUES (45, 56);
INSERT INTO real_products (product_id, simple_product_id) VALUES (46, 57);
INSERT INTO real_products (product_id, simple_product_id) VALUES (47, 58);
INSERT INTO real_products (product_id, simple_product_id) VALUES (48, 59);
INSERT INTO real_products (product_id, simple_product_id) VALUES (49, 60);
INSERT INTO real_products (product_id, simple_product_id) VALUES (50, 61);
INSERT INTO real_products (product_id, simple_product_id) VALUES (51, 62);
INSERT INTO real_products (product_id, simple_product_id) VALUES (52, 63);
INSERT INTO real_products (product_id, simple_product_id) VALUES (53, 64);
INSERT INTO real_products (product_id, simple_product_id) VALUES (54, 65);
INSERT INTO real_products (product_id, simple_product_id) VALUES (55, 66);
INSERT INTO real_products (product_id, simple_product_id) VALUES (56, 67);
INSERT INTO real_products (product_id, simple_product_id) VALUES (57, 68);
INSERT INTO real_products (product_id, simple_product_id) VALUES (58, 69);
INSERT INTO real_products (product_id, simple_product_id) VALUES (59, 70);
INSERT INTO real_products (product_id, simple_product_id) VALUES (60, 71);
INSERT INTO real_products (product_id, simple_product_id) VALUES (61, 72);
INSERT INTO real_products (product_id, simple_product_id) VALUES (62, 73);
INSERT INTO real_products (product_id, simple_product_id) VALUES (63, 74);
INSERT INTO real_products (product_id, simple_product_id) VALUES (64, 75);
INSERT INTO real_products (product_id, simple_product_id) VALUES (65, 76);
INSERT INTO real_products (product_id, simple_product_id) VALUES (66, 77);
INSERT INTO real_products (product_id, simple_product_id) VALUES (67, 78);
INSERT INTO real_products (product_id, simple_product_id) VALUES (68, 79);
INSERT INTO real_products (product_id, simple_product_id) VALUES (69, 80);
INSERT INTO real_products (product_id, simple_product_id) VALUES (70, 81);
INSERT INTO real_products (product_id, simple_product_id) VALUES (71, 82);
INSERT INTO real_products (product_id, simple_product_id) VALUES (72, 83);
INSERT INTO real_products (product_id, simple_product_id) VALUES (73, 84);
INSERT INTO real_products (product_id, simple_product_id) VALUES (74, 85);
INSERT INTO real_products (product_id, simple_product_id) VALUES (75, 86);
INSERT INTO real_products (product_id, simple_product_id) VALUES (76, 87);
INSERT INTO real_products (product_id, simple_product_id) VALUES (77, 88);
INSERT INTO real_products (product_id, simple_product_id) VALUES (78, 89);
INSERT INTO real_products (product_id, simple_product_id) VALUES (79, 90);
INSERT INTO real_products (product_id, simple_product_id) VALUES (80, 91);
INSERT INTO real_products (product_id, simple_product_id) VALUES (81, 92);
INSERT INTO real_products (product_id, simple_product_id) VALUES (82, 93);
INSERT INTO real_products (product_id, simple_product_id) VALUES (83, 94);
INSERT INTO real_products (product_id, simple_product_id) VALUES (84, 95);
INSERT INTO real_products (product_id, simple_product_id) VALUES (85, 96);
INSERT INTO real_products (product_id, simple_product_id) VALUES (86, 97);
INSERT INTO real_products (product_id, simple_product_id) VALUES (87, 98);
INSERT INTO real_products (product_id, simple_product_id) VALUES (88, 99);


--
-- TOC entry 1891 (class 0 OID 68797)
-- Dependencies: 1541
-- Data for Name: simple_products; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO simple_products (product_id, product_code) VALUES (28, 'sp-pc-1');
INSERT INTO simple_products (product_id, product_code) VALUES (29, 'sp-pc-2');
INSERT INTO simple_products (product_id, product_code) VALUES (30, 'sp-pc-3');
INSERT INTO simple_products (product_id, product_code) VALUES (31, 'sp-pc-4');
INSERT INTO simple_products (product_id, product_code) VALUES (32, 'sp-pc-5');
INSERT INTO simple_products (product_id, product_code) VALUES (33, 'sp-pc-6');
INSERT INTO simple_products (product_id, product_code) VALUES (34, 'sp-pc-7');
INSERT INTO simple_products (product_id, product_code) VALUES (35, 'sp-pc-8');
INSERT INTO simple_products (product_id, product_code) VALUES (36, 'sp-pc-9');
INSERT INTO simple_products (product_id, product_code) VALUES (37, 'sp-pc-10');
INSERT INTO simple_products (product_id, product_code) VALUES (38, 'sp-pc-11');
INSERT INTO simple_products (product_id, product_code) VALUES (39, 'sp-pc-12');
INSERT INTO simple_products (product_id, product_code) VALUES (40, 'sp-pc-13');
INSERT INTO simple_products (product_id, product_code) VALUES (41, 'sp-pc-14');
INSERT INTO simple_products (product_id, product_code) VALUES (42, 'sp-pc-15');
INSERT INTO simple_products (product_id, product_code) VALUES (43, 'sp-pc-16');
INSERT INTO simple_products (product_id, product_code) VALUES (44, 'sp-pc-17');
INSERT INTO simple_products (product_id, product_code) VALUES (45, 'sp-pc-18');
INSERT INTO simple_products (product_id, product_code) VALUES (46, 'sp-pc-19');
INSERT INTO simple_products (product_id, product_code) VALUES (47, 'sp-pc-20');
INSERT INTO simple_products (product_id, product_code) VALUES (48, 'sp-pc-21');
INSERT INTO simple_products (product_id, product_code) VALUES (49, 'sp-pc-22');
INSERT INTO simple_products (product_id, product_code) VALUES (50, 'sp-pc-23');
INSERT INTO simple_products (product_id, product_code) VALUES (51, 'sp-pc-24');
INSERT INTO simple_products (product_id, product_code) VALUES (52, 'sp-pc-25');
INSERT INTO simple_products (product_id, product_code) VALUES (53, 'sp-pc-26');
INSERT INTO simple_products (product_id, product_code) VALUES (54, 'sp-pc-27');
INSERT INTO simple_products (product_id, product_code) VALUES (55, 'sp-pc-28');
INSERT INTO simple_products (product_id, product_code) VALUES (56, 'sp-pc-29');
INSERT INTO simple_products (product_id, product_code) VALUES (57, 'sp-pc-30');
INSERT INTO simple_products (product_id, product_code) VALUES (58, 'sp-pc-31');
INSERT INTO simple_products (product_id, product_code) VALUES (59, 'sp-pc-32');
INSERT INTO simple_products (product_id, product_code) VALUES (60, 'sp-pc-33');
INSERT INTO simple_products (product_id, product_code) VALUES (61, 'sp-pc-34');
INSERT INTO simple_products (product_id, product_code) VALUES (62, 'sp-pc-35');
INSERT INTO simple_products (product_id, product_code) VALUES (63, 'sp-pc-36');
INSERT INTO simple_products (product_id, product_code) VALUES (64, 'sp-pc-37');
INSERT INTO simple_products (product_id, product_code) VALUES (65, 'sp-pc-38');
INSERT INTO simple_products (product_id, product_code) VALUES (66, 'sp-pc-39');
INSERT INTO simple_products (product_id, product_code) VALUES (67, 'sp-pc-40');
INSERT INTO simple_products (product_id, product_code) VALUES (68, 'sp-pc-41');
INSERT INTO simple_products (product_id, product_code) VALUES (69, 'sp-pc-42');
INSERT INTO simple_products (product_id, product_code) VALUES (70, 'sp-pc-43');
INSERT INTO simple_products (product_id, product_code) VALUES (71, 'sp-pc-44');
INSERT INTO simple_products (product_id, product_code) VALUES (72, 'sp-pc-45');
INSERT INTO simple_products (product_id, product_code) VALUES (73, 'sp-pc-46');
INSERT INTO simple_products (product_id, product_code) VALUES (74, 'sp-pc-47');
INSERT INTO simple_products (product_id, product_code) VALUES (75, 'sp-pc-48');
INSERT INTO simple_products (product_id, product_code) VALUES (76, 'sp-pc-49');
INSERT INTO simple_products (product_id, product_code) VALUES (77, 'sp-pc-50');
INSERT INTO simple_products (product_id, product_code) VALUES (78, 'sp-pc-51');
INSERT INTO simple_products (product_id, product_code) VALUES (79, 'sp-pc-52');
INSERT INTO simple_products (product_id, product_code) VALUES (80, 'sp-pc-53');
INSERT INTO simple_products (product_id, product_code) VALUES (81, 'sp-pc-54');
INSERT INTO simple_products (product_id, product_code) VALUES (82, 'sp-pc-55');
INSERT INTO simple_products (product_id, product_code) VALUES (83, 'sp-pc-56');
INSERT INTO simple_products (product_id, product_code) VALUES (84, 'sp-pc-57');
INSERT INTO simple_products (product_id, product_code) VALUES (85, 'sp-pc-58');
INSERT INTO simple_products (product_id, product_code) VALUES (86, 'sp-pc-59');
INSERT INTO simple_products (product_id, product_code) VALUES (87, 'sp-pc-60');
INSERT INTO simple_products (product_id, product_code) VALUES (88, 'sp-pc-61');
INSERT INTO simple_products (product_id, product_code) VALUES (89, 'sp-pc-62');
INSERT INTO simple_products (product_id, product_code) VALUES (90, 'sp-pc-63');
INSERT INTO simple_products (product_id, product_code) VALUES (91, 'sp-pc-64');
INSERT INTO simple_products (product_id, product_code) VALUES (92, 'sp-pc-65');
INSERT INTO simple_products (product_id, product_code) VALUES (93, 'sp-pc-66');
INSERT INTO simple_products (product_id, product_code) VALUES (94, 'sp-pc-67');
INSERT INTO simple_products (product_id, product_code) VALUES (95, 'sp-pc-68');
INSERT INTO simple_products (product_id, product_code) VALUES (96, 'sp-pc-69');
INSERT INTO simple_products (product_id, product_code) VALUES (97, 'sp-pc-70');
INSERT INTO simple_products (product_id, product_code) VALUES (98, 'sp-pc-71');
INSERT INTO simple_products (product_id, product_code) VALUES (99, 'sp-pc-72');


--
-- TOC entry 1895 (class 0 OID 77048)
-- Dependencies: 1548
-- Data for Name: virtual_products; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO virtual_products (product_id, product_type) VALUES (15, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (16, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (17, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (18, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (19, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (20, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (21, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (22, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (23, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (24, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (25, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (26, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (27, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (28, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (29, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (30, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (31, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (32, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (33, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (34, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (35, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (36, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (37, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (38, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (39, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (40, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (41, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (42, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (43, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (44, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (45, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (46, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (47, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (48, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (49, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (50, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (51, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (52, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (53, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (54, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (55, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (56, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (57, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (58, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (59, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (60, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (61, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (62, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (63, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (64, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (65, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (66, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (67, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (68, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (69, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (70, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (71, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (72, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (73, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (74, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (75, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (76, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (77, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (78, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (79, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (80, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (81, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (82, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (83, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (84, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (85, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (86, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (87, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (88, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (89, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (90, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (91, NULL);
INSERT INTO virtual_products (product_id, product_type) VALUES (92, NULL);


--
-- TOC entry 1852 (class 2606 OID 77047)
-- Dependencies: 1547 1547
-- Name: pk_assembling_algorithms; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_algorithms
    ADD CONSTRAINT pk_assembling_algorithms PRIMARY KEY (algorithm_id);


--
-- TOC entry 1864 (class 2606 OID 77082)
-- Dependencies: 1551 1551
-- Name: pk_assembling_categories; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_categories
    ADD CONSTRAINT pk_assembling_categories PRIMARY KEY (assembling_category_id);


--
-- TOC entry 1870 (class 2606 OID 93541)
-- Dependencies: 1556 1556
-- Name: pk_assembling_schema_item_values; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schema_item_values
    ADD CONSTRAINT pk_assembling_schema_item_values PRIMARY KEY (item_value_id);


--
-- TOC entry 1872 (class 2606 OID 93616)
-- Dependencies: 1557 1557
-- Name: pk_assembling_schema_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT pk_assembling_schema_items PRIMARY KEY (item_id);


--
-- TOC entry 1846 (class 2606 OID 77039)
-- Dependencies: 1546 1546
-- Name: pk_assembling_schemas; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT pk_assembling_schemas PRIMARY KEY (product_id);


--
-- TOC entry 1844 (class 2606 OID 68843)
-- Dependencies: 1545 1545
-- Name: pk_complex_product_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT pk_complex_product_items PRIMARY KEY (complex_product_item_id);


--
-- TOC entry 1834 (class 2606 OID 68807)
-- Dependencies: 1537 1537
-- Name: pk_complex_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY complex_products
    ADD CONSTRAINT pk_complex_products PRIMARY KEY (product_id);


--
-- TOC entry 1840 (class 2606 OID 68809)
-- Dependencies: 1540 1540
-- Name: pk_merchandises; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY products
    ADD CONSTRAINT pk_merchandises PRIMARY KEY (product_id);


--
-- TOC entry 1836 (class 2606 OID 68811)
-- Dependencies: 1538 1538
-- Name: pk_offer_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY offer_items
    ADD CONSTRAINT pk_offer_items PRIMARY KEY (offer_item_id);


--
-- TOC entry 1838 (class 2606 OID 68813)
-- Dependencies: 1539 1539
-- Name: pk_offers; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY offers
    ADD CONSTRAINT pk_offers PRIMARY KEY (offer_id);


--
-- TOC entry 1860 (class 2606 OID 77059)
-- Dependencies: 1550 1550
-- Name: pk_real_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY real_products
    ADD CONSTRAINT pk_real_products PRIMARY KEY (product_id);


--
-- TOC entry 1842 (class 2606 OID 68815)
-- Dependencies: 1541 1541
-- Name: pk_simple_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT pk_simple_products PRIMARY KEY (product_id);


--
-- TOC entry 1858 (class 2606 OID 77052)
-- Dependencies: 1548 1548
-- Name: pk_virtual_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY virtual_products
    ADD CONSTRAINT pk_virtual_products PRIMARY KEY (product_id);


--
-- TOC entry 1854 (class 2606 OID 77117)
-- Dependencies: 1547 1547
-- Name: uk_assembling_algorithms_by_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_algorithms
    ADD CONSTRAINT uk_assembling_algorithms_by_code UNIQUE (algorithm_code);


--
-- TOC entry 1856 (class 2606 OID 77119)
-- Dependencies: 1547 1547
-- Name: uk_assembling_algorithms_by_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_algorithms
    ADD CONSTRAINT uk_assembling_algorithms_by_name UNIQUE (algorithm_name);


--
-- TOC entry 1866 (class 2606 OID 77124)
-- Dependencies: 1551 1551
-- Name: uk_assembling_categories_by_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_categories
    ADD CONSTRAINT uk_assembling_categories_by_code UNIQUE (category_code);


--
-- TOC entry 1868 (class 2606 OID 77126)
-- Dependencies: 1551 1551
-- Name: uk_assembling_categories_by_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_categories
    ADD CONSTRAINT uk_assembling_categories_by_name UNIQUE (category_name);


--
-- TOC entry 1848 (class 2606 OID 77142)
-- Dependencies: 1546 1546
-- Name: uk_assembling_schemas_by_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT uk_assembling_schemas_by_code UNIQUE (schema_code);


--
-- TOC entry 1850 (class 2606 OID 77144)
-- Dependencies: 1546 1546
-- Name: uk_assembling_schemas_by_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT uk_assembling_schemas_by_name UNIQUE (schema_name);


--
-- TOC entry 1862 (class 2606 OID 77151)
-- Dependencies: 1550 1550
-- Name: uk_real_products_by_simple_product; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY real_products
    ADD CONSTRAINT uk_real_products_by_simple_product UNIQUE (simple_product_id);


--
-- TOC entry 1884 (class 2606 OID 93627)
-- Dependencies: 1557 1871 1556
-- Name: fk_assembling_schema_item_values_as_item; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_item_values
    ADD CONSTRAINT fk_assembling_schema_item_values_as_item FOREIGN KEY (item_id) REFERENCES assembling_schema_items(item_id);


--
-- TOC entry 1883 (class 2606 OID 93597)
-- Dependencies: 1857 1556 1548
-- Name: fk_assembling_schema_item_values_vp; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_item_values
    ADD CONSTRAINT fk_assembling_schema_item_values_vp FOREIGN KEY (virtual_product_id) REFERENCES virtual_products(product_id);


--
-- TOC entry 1885 (class 2606 OID 93617)
-- Dependencies: 1547 1851 1557
-- Name: fk_assembling_schema_items_algorithm; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT fk_assembling_schema_items_algorithm FOREIGN KEY (algorithm_id) REFERENCES assembling_algorithms(algorithm_id);


--
-- TOC entry 1886 (class 2606 OID 93622)
-- Dependencies: 1557 1845 1546
-- Name: fk_assembling_schema_items_owner; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT fk_assembling_schema_items_owner FOREIGN KEY (assembling_schema_id) REFERENCES assembling_schemas(product_id);


--
-- TOC entry 1880 (class 2606 OID 77136)
-- Dependencies: 1863 1551 1546
-- Name: fk_assembling_schemas_category; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT fk_assembling_schemas_category FOREIGN KEY (category_id) REFERENCES assembling_categories(assembling_category_id);


--
-- TOC entry 1879 (class 2606 OID 77065)
-- Dependencies: 1857 1548 1546
-- Name: fk_assembling_schemas_virtual_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT fk_assembling_schemas_virtual_product FOREIGN KEY (product_id) REFERENCES virtual_products(product_id);


--
-- TOC entry 1877 (class 2606 OID 68844)
-- Dependencies: 1545 1833 1537
-- Name: fk_complex_product_items_cp; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT fk_complex_product_items_cp FOREIGN KEY (complex_product_id) REFERENCES complex_products(product_id);


--
-- TOC entry 1878 (class 2606 OID 68849)
-- Dependencies: 1540 1545 1839
-- Name: fk_complex_product_items_products; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT fk_complex_product_items_products FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 1873 (class 2606 OID 68816)
-- Dependencies: 1537 1839 1540
-- Name: fk_complex_products_merchandises; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_products
    ADD CONSTRAINT fk_complex_products_merchandises FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 1874 (class 2606 OID 68821)
-- Dependencies: 1538 1540 1839
-- Name: fk_offer_items_merchandises; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY offer_items
    ADD CONSTRAINT fk_offer_items_merchandises FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 1875 (class 2606 OID 68826)
-- Dependencies: 1538 1539 1837
-- Name: fk_offer_items_offers; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY offer_items
    ADD CONSTRAINT fk_offer_items_offers FOREIGN KEY (offer_id) REFERENCES offers(offer_id);


--
-- TOC entry 1881 (class 2606 OID 77060)
-- Dependencies: 1550 1541 1841
-- Name: fk_real_products_simple_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY real_products
    ADD CONSTRAINT fk_real_products_simple_product FOREIGN KEY (simple_product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 1882 (class 2606 OID 77070)
-- Dependencies: 1548 1550 1857
-- Name: fk_real_products_virtual_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY real_products
    ADD CONSTRAINT fk_real_products_virtual_product FOREIGN KEY (product_id) REFERENCES virtual_products(product_id);


--
-- TOC entry 1876 (class 2606 OID 68831)
-- Dependencies: 1839 1541 1540
-- Name: fk_simple_products_merchandises; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_simple_products_merchandises FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 1904 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: -
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2008-04-26 23:35:13

--
-- PostgreSQL database dump complete
--

