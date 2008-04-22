--
-- PostgreSQL database dump
--

-- Started on 2008-04-23 01:20:55

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 362 (class 2612 OID 16386)
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: -
--

CREATE PROCEDURAL LANGUAGE plpgsql;


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 1549 (class 1259 OID 77042)
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
-- TOC entry 1553 (class 1259 OID 77077)
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
-- TOC entry 1560 (class 1259 OID 93534)
-- Dependencies: 1833 6
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
-- TOC entry 1561 (class 1259 OID 93561)
-- Dependencies: 1834 1835 6
-- Name: assembling_schema_items; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE assembling_schema_items (
    item_id bigint NOT NULL,
    assembling_schema_id bigint NOT NULL,
    algorithm_id integer NOT NULL,
    data_type character varying(16) DEFAULT 'Integer'::character varying NOT NULL,
    min_selections integer,
    max_selections integer,
    quantity numeric(19,4) DEFAULT 1 NOT NULL,
    default_value bytea,
    description text
);


--
-- TOC entry 1911 (class 0 OID 0)
-- Dependencies: 1561
-- Name: COLUMN assembling_schema_items.data_type; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN assembling_schema_items.data_type IS 'Integer, Decimal, Date, String';


--
-- TOC entry 1548 (class 1259 OID 77034)
-- Dependencies: 1831 6
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
-- Dependencies: 6 1534
-- Name: breakpoint; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE breakpoint AS (
	func oid,
	linenumber integer,
	targetname text
);


--
-- TOC entry 1547 (class 1259 OID 68838)
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
-- TOC entry 1539 (class 1259 OID 68782)
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
-- Dependencies: 6 1535
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
-- TOC entry 1540 (class 1259 OID 68785)
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
-- TOC entry 1541 (class 1259 OID 68788)
-- Dependencies: 6
-- Name: offers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE offers (
    offer_id bigint NOT NULL
);


--
-- TOC entry 1542 (class 1259 OID 68793)
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
-- Dependencies: 6 1538
-- Name: proxyinfo; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE proxyinfo AS (
	serverversionstr text,
	serverversionnum integer,
	proxyapiver integer,
	serverprocessid integer
);


--
-- TOC entry 1552 (class 1259 OID 77055)
-- Dependencies: 6
-- Name: real_products; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE real_products (
    product_id bigint NOT NULL,
    simple_product_id bigint NOT NULL
);


--
-- TOC entry 1543 (class 1259 OID 68797)
-- Dependencies: 6
-- Name: simple_products; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE simple_products (
    product_id bigint NOT NULL,
    product_code character varying(32) NOT NULL
);


--
-- TOC entry 313 (class 1247 OID 16411)
-- Dependencies: 6 1536
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
-- TOC entry 1556 (class 1259 OID 77154)
-- Dependencies: 6
-- Name: test_table; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE test_table (
    test_id integer NOT NULL,
    data_column bytea,
    data_type character varying(64)
);


--
-- TOC entry 315 (class 1247 OID 16414)
-- Dependencies: 6 1537
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
-- TOC entry 1550 (class 1259 OID 77048)
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
-- Dependencies: 313 6
-- Name: pldbg_get_target_info(text, "char"); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_get_target_info(signature text, targettype "char") RETURNS targetinfo
    AS '$libdir/targetinfo', 'pldbg_get_target_info'
    LANGUAGE c STRICT;


--
-- TOC entry 31 (class 1255 OID 16429)
-- Dependencies: 6 315
-- Name: pldbg_get_variables(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_get_variables(session integer) RETURNS SETOF var
    AS '$libdir/pldbgapi', 'pldbg_get_variables'
    LANGUAGE c STRICT;


--
-- TOC entry 32 (class 1255 OID 16430)
-- Dependencies: 273 6
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
-- Dependencies: 273 6
-- Name: pldbg_step_into(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pldbg_step_into(session integer) RETURNS breakpoint
    AS '$libdir/pldbgapi', 'pldbg_step_into'
    LANGUAGE c STRICT;


--
-- TOC entry 36 (class 1255 OID 16434)
-- Dependencies: 6 273
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
-- TOC entry 1559 (class 1259 OID 85344)
-- Dependencies: 6
-- Name: assembling_algorithms_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE assembling_algorithms_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1912 (class 0 OID 0)
-- Dependencies: 1559
-- Name: assembling_algorithms_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('assembling_algorithms_seq', 1, false);


--
-- TOC entry 1558 (class 1259 OID 85342)
-- Dependencies: 6
-- Name: assembling_categories_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE assembling_categories_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1913 (class 0 OID 0)
-- Dependencies: 1558
-- Name: assembling_categories_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('assembling_categories_seq', 4, true);


--
-- TOC entry 1546 (class 1259 OID 68836)
-- Dependencies: 6 1547
-- Name: complex_product_items_complex_product_item_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE complex_product_items_complex_product_item_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1914 (class 0 OID 0)
-- Dependencies: 1546
-- Name: complex_product_items_complex_product_item_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE complex_product_items_complex_product_item_id_seq OWNED BY complex_product_items.complex_product_item_id;


--
-- TOC entry 1915 (class 0 OID 0)
-- Dependencies: 1546
-- Name: complex_product_items_complex_product_item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('complex_product_items_complex_product_item_id_seq', 1, false);


--
-- TOC entry 1544 (class 1259 OID 68800)
-- Dependencies: 6 1540
-- Name: offer_items_offer_item_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE offer_items_offer_item_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1916 (class 0 OID 0)
-- Dependencies: 1544
-- Name: offer_items_offer_item_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE offer_items_offer_item_id_seq OWNED BY offer_items.offer_item_id;


--
-- TOC entry 1917 (class 0 OID 0)
-- Dependencies: 1544
-- Name: offer_items_offer_item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('offer_items_offer_item_id_seq', 1, false);


--
-- TOC entry 1545 (class 1259 OID 68802)
-- Dependencies: 1541 6
-- Name: offers_offer_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE offers_offer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1918 (class 0 OID 0)
-- Dependencies: 1545
-- Name: offers_offer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE offers_offer_id_seq OWNED BY offers.offer_id;


--
-- TOC entry 1919 (class 0 OID 0)
-- Dependencies: 1545
-- Name: offers_offer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('offers_offer_id_seq', 1, false);


--
-- TOC entry 1551 (class 1259 OID 77053)
-- Dependencies: 6
-- Name: products_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE products_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1920 (class 0 OID 0)
-- Dependencies: 1551
-- Name: products_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('products_seq', 18, true);


--
-- TOC entry 1557 (class 1259 OID 77163)
-- Dependencies: 6
-- Name: test_table_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE test_table_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1921 (class 0 OID 0)
-- Dependencies: 1557
-- Name: test_table_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('test_table_seq', 3, true);


--
-- TOC entry 1555 (class 1259 OID 77152)
-- Dependencies: 1556 6
-- Name: test_table_test_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE test_table_test_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1922 (class 0 OID 0)
-- Dependencies: 1555
-- Name: test_table_test_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE test_table_test_id_seq OWNED BY test_table.test_id;


--
-- TOC entry 1923 (class 0 OID 0)
-- Dependencies: 1555
-- Name: test_table_test_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('test_table_test_id_seq', 1, false);


--
-- TOC entry 1554 (class 1259 OID 77111)
-- Dependencies: 6
-- Name: virtual_products_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE virtual_products_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1924 (class 0 OID 0)
-- Dependencies: 1554
-- Name: virtual_products_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('virtual_products_seq', 8, true);


--
-- TOC entry 1830 (class 2604 OID 68841)
-- Dependencies: 1547 1546 1547
-- Name: complex_product_item_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE complex_product_items ALTER COLUMN complex_product_item_id SET DEFAULT nextval('complex_product_items_complex_product_item_id_seq'::regclass);


--
-- TOC entry 1828 (class 2604 OID 68804)
-- Dependencies: 1544 1540
-- Name: offer_item_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE offer_items ALTER COLUMN offer_item_id SET DEFAULT nextval('offer_items_offer_item_id_seq'::regclass);


--
-- TOC entry 1829 (class 2604 OID 68805)
-- Dependencies: 1545 1541
-- Name: offer_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE offers ALTER COLUMN offer_id SET DEFAULT nextval('offers_offer_id_seq'::regclass);


--
-- TOC entry 1832 (class 2604 OID 77157)
-- Dependencies: 1556 1555 1556
-- Name: test_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE test_table ALTER COLUMN test_id SET DEFAULT nextval('test_table_test_id_seq'::regclass);


--
-- TOC entry 1899 (class 0 OID 77042)
-- Dependencies: 1549
-- Data for Name: assembling_algorithms; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1902 (class 0 OID 77077)
-- Dependencies: 1553
-- Data for Name: assembling_categories; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1904 (class 0 OID 93534)
-- Dependencies: 1560
-- Data for Name: assembling_schema_item_values; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1905 (class 0 OID 93561)
-- Dependencies: 1561
-- Data for Name: assembling_schema_items; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1898 (class 0 OID 77034)
-- Dependencies: 1548
-- Data for Name: assembling_schemas; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1897 (class 0 OID 68838)
-- Dependencies: 1547
-- Data for Name: complex_product_items; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1892 (class 0 OID 68782)
-- Dependencies: 1539
-- Data for Name: complex_products; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1893 (class 0 OID 68785)
-- Dependencies: 1540
-- Data for Name: offer_items; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1894 (class 0 OID 68788)
-- Dependencies: 1541
-- Data for Name: offers; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1895 (class 0 OID 68793)
-- Dependencies: 1542
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO products (product_id, product_type, product_price) VALUES (1, NULL, 12.3000);
INSERT INTO products (product_id, product_type, product_price) VALUES (16, NULL, 12.3000);


--
-- TOC entry 1901 (class 0 OID 77055)
-- Dependencies: 1552
-- Data for Name: real_products; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1896 (class 0 OID 68797)
-- Dependencies: 1543
-- Data for Name: simple_products; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1903 (class 0 OID 77154)
-- Dependencies: 1556
-- Data for Name: test_table; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO test_table (test_id, data_column, data_type) VALUES (1, '\\254\\355\\000\\005sr\\000\\021java.lang.Integer\\022\\342\\240\\244\\367\\201\\2078\\002\\000\\001I\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\001', 'Integer');
INSERT INTO test_table (test_id, data_column, data_type) VALUES (2, '\\254\\355\\000\\005sr\\000\\016java.lang.Long;\\213\\344\\220\\314\\217#\\337\\002\\000\\001J\\000\\005valuexr\\000\\020java.lang.Number\\206\\254\\225\\035\\013\\224\\340\\213\\002\\000\\000xp\\000\\000\\000\\000\\000\\000\\000\\002', 'Long');
INSERT INTO test_table (test_id, data_column, data_type) VALUES (3, '\\254\\355\\000\\005t\\000\\004Miro', 'String');


--
-- TOC entry 1900 (class 0 OID 77048)
-- Dependencies: 1550
-- Data for Name: virtual_products; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1855 (class 2606 OID 77047)
-- Dependencies: 1549 1549
-- Name: pk_assembling_algorithms; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_algorithms
    ADD CONSTRAINT pk_assembling_algorithms PRIMARY KEY (algorithm_id);


--
-- TOC entry 1867 (class 2606 OID 77082)
-- Dependencies: 1553 1553
-- Name: pk_assembling_categories; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_categories
    ADD CONSTRAINT pk_assembling_categories PRIMARY KEY (assembling_category_id);


--
-- TOC entry 1875 (class 2606 OID 93541)
-- Dependencies: 1560 1560
-- Name: pk_assembling_schema_item_values; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schema_item_values
    ADD CONSTRAINT pk_assembling_schema_item_values PRIMARY KEY (item_value_id);


--
-- TOC entry 1877 (class 2606 OID 93569)
-- Dependencies: 1561 1561
-- Name: pk_assembling_schema_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT pk_assembling_schema_items PRIMARY KEY (item_id);


--
-- TOC entry 1849 (class 2606 OID 77039)
-- Dependencies: 1548 1548
-- Name: pk_assembling_schemas; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT pk_assembling_schemas PRIMARY KEY (product_id);


--
-- TOC entry 1847 (class 2606 OID 68843)
-- Dependencies: 1547 1547
-- Name: pk_complex_product_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT pk_complex_product_items PRIMARY KEY (complex_product_item_id);


--
-- TOC entry 1837 (class 2606 OID 68807)
-- Dependencies: 1539 1539
-- Name: pk_complex_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY complex_products
    ADD CONSTRAINT pk_complex_products PRIMARY KEY (product_id);


--
-- TOC entry 1843 (class 2606 OID 68809)
-- Dependencies: 1542 1542
-- Name: pk_merchandises; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY products
    ADD CONSTRAINT pk_merchandises PRIMARY KEY (product_id);


--
-- TOC entry 1839 (class 2606 OID 68811)
-- Dependencies: 1540 1540
-- Name: pk_offer_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY offer_items
    ADD CONSTRAINT pk_offer_items PRIMARY KEY (offer_item_id);


--
-- TOC entry 1841 (class 2606 OID 68813)
-- Dependencies: 1541 1541
-- Name: pk_offers; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY offers
    ADD CONSTRAINT pk_offers PRIMARY KEY (offer_id);


--
-- TOC entry 1863 (class 2606 OID 77059)
-- Dependencies: 1552 1552
-- Name: pk_real_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY real_products
    ADD CONSTRAINT pk_real_products PRIMARY KEY (product_id);


--
-- TOC entry 1845 (class 2606 OID 68815)
-- Dependencies: 1543 1543
-- Name: pk_simple_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT pk_simple_products PRIMARY KEY (product_id);


--
-- TOC entry 1873 (class 2606 OID 77159)
-- Dependencies: 1556 1556
-- Name: pk_test_table; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY test_table
    ADD CONSTRAINT pk_test_table PRIMARY KEY (test_id);


--
-- TOC entry 1861 (class 2606 OID 77052)
-- Dependencies: 1550 1550
-- Name: pk_virtual_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY virtual_products
    ADD CONSTRAINT pk_virtual_products PRIMARY KEY (product_id);


--
-- TOC entry 1857 (class 2606 OID 77117)
-- Dependencies: 1549 1549
-- Name: uk_assembling_algorithms_by_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_algorithms
    ADD CONSTRAINT uk_assembling_algorithms_by_code UNIQUE (algorithm_code);


--
-- TOC entry 1859 (class 2606 OID 77119)
-- Dependencies: 1549 1549
-- Name: uk_assembling_algorithms_by_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_algorithms
    ADD CONSTRAINT uk_assembling_algorithms_by_name UNIQUE (algorithm_name);


--
-- TOC entry 1869 (class 2606 OID 77124)
-- Dependencies: 1553 1553
-- Name: uk_assembling_categories_by_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_categories
    ADD CONSTRAINT uk_assembling_categories_by_code UNIQUE (category_code);


--
-- TOC entry 1871 (class 2606 OID 77126)
-- Dependencies: 1553 1553
-- Name: uk_assembling_categories_by_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_categories
    ADD CONSTRAINT uk_assembling_categories_by_name UNIQUE (category_name);


--
-- TOC entry 1851 (class 2606 OID 77142)
-- Dependencies: 1548 1548
-- Name: uk_assembling_schemas_by_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT uk_assembling_schemas_by_code UNIQUE (schema_code);


--
-- TOC entry 1853 (class 2606 OID 77144)
-- Dependencies: 1548 1548
-- Name: uk_assembling_schemas_by_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT uk_assembling_schemas_by_name UNIQUE (schema_name);


--
-- TOC entry 1865 (class 2606 OID 77151)
-- Dependencies: 1552 1552
-- Name: uk_real_products_by_simple_product; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY real_products
    ADD CONSTRAINT uk_real_products_by_simple_product UNIQUE (simple_product_id);


--
-- TOC entry 1889 (class 2606 OID 93602)
-- Dependencies: 1561 1560 1876
-- Name: fk_assembling_schema_item_values_as_item; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_item_values
    ADD CONSTRAINT fk_assembling_schema_item_values_as_item FOREIGN KEY (item_id) REFERENCES assembling_schema_items(item_id);


--
-- TOC entry 1888 (class 2606 OID 93597)
-- Dependencies: 1550 1560 1860
-- Name: fk_assembling_schema_item_values_vp; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_item_values
    ADD CONSTRAINT fk_assembling_schema_item_values_vp FOREIGN KEY (virtual_product_id) REFERENCES virtual_products(product_id);


--
-- TOC entry 1890 (class 2606 OID 93570)
-- Dependencies: 1549 1561 1854
-- Name: fk_assembling_schema_items_algorithm; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT fk_assembling_schema_items_algorithm FOREIGN KEY (algorithm_id) REFERENCES assembling_algorithms(algorithm_id);


--
-- TOC entry 1891 (class 2606 OID 93575)
-- Dependencies: 1548 1561 1848
-- Name: fk_assembling_schema_items_owner; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT fk_assembling_schema_items_owner FOREIGN KEY (assembling_schema_id) REFERENCES assembling_schemas(product_id);


--
-- TOC entry 1885 (class 2606 OID 77136)
-- Dependencies: 1553 1866 1548
-- Name: fk_assembling_schemas_category; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT fk_assembling_schemas_category FOREIGN KEY (category_id) REFERENCES assembling_categories(assembling_category_id);


--
-- TOC entry 1884 (class 2606 OID 77065)
-- Dependencies: 1860 1548 1550
-- Name: fk_assembling_schemas_virtual_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT fk_assembling_schemas_virtual_product FOREIGN KEY (product_id) REFERENCES virtual_products(product_id);


--
-- TOC entry 1882 (class 2606 OID 68844)
-- Dependencies: 1547 1836 1539
-- Name: fk_complex_product_items_cp; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT fk_complex_product_items_cp FOREIGN KEY (complex_product_id) REFERENCES complex_products(product_id);


--
-- TOC entry 1883 (class 2606 OID 68849)
-- Dependencies: 1547 1542 1842
-- Name: fk_complex_product_items_products; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT fk_complex_product_items_products FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 1878 (class 2606 OID 68816)
-- Dependencies: 1842 1542 1539
-- Name: fk_complex_products_merchandises; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_products
    ADD CONSTRAINT fk_complex_products_merchandises FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 1879 (class 2606 OID 68821)
-- Dependencies: 1542 1540 1842
-- Name: fk_offer_items_merchandises; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY offer_items
    ADD CONSTRAINT fk_offer_items_merchandises FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 1880 (class 2606 OID 68826)
-- Dependencies: 1541 1540 1840
-- Name: fk_offer_items_offers; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY offer_items
    ADD CONSTRAINT fk_offer_items_offers FOREIGN KEY (offer_id) REFERENCES offers(offer_id);


--
-- TOC entry 1886 (class 2606 OID 77060)
-- Dependencies: 1552 1844 1543
-- Name: fk_real_products_simple_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY real_products
    ADD CONSTRAINT fk_real_products_simple_product FOREIGN KEY (simple_product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 1887 (class 2606 OID 77070)
-- Dependencies: 1550 1860 1552
-- Name: fk_real_products_virtual_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY real_products
    ADD CONSTRAINT fk_real_products_virtual_product FOREIGN KEY (product_id) REFERENCES virtual_products(product_id);


--
-- TOC entry 1881 (class 2606 OID 68831)
-- Dependencies: 1543 1842 1542
-- Name: fk_simple_products_merchandises; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_simple_products_merchandises FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 1910 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: -
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2008-04-23 01:21:04

--
-- PostgreSQL database dump complete
--

