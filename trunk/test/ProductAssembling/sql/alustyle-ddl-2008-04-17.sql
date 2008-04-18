--
-- PostgreSQL database dump
--

-- Started on 2008-04-18 00:11:57

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 355 (class 2612 OID 16386)
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: -
--

CREATE PROCEDURAL LANGUAGE plpgsql;


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 1543 (class 1259 OID 77042)
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
-- TOC entry 1548 (class 1259 OID 77077)
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
-- TOC entry 1550 (class 1259 OID 77085)
-- Dependencies: 6
-- Name: assembling_schema_items; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE assembling_schema_items (
    item_id bigint NOT NULL,
    assembling_schema_id bigint NOT NULL,
    algorithm_id integer NOT NULL,
    min_constraint numeric(19,4),
    max_constraint numeric(19,4),
    virtual_product_id bigint NOT NULL,
    quantity numeric(19,4) NOT NULL,
    default_value character varying(64),
    description text
);


--
-- TOC entry 1541 (class 1259 OID 77034)
-- Dependencies: 1821 6
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
-- Dependencies: 6 1527
-- Name: breakpoint; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE breakpoint AS (
	func oid,
	linenumber integer,
	targetname text
);


--
-- TOC entry 1540 (class 1259 OID 68838)
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
-- TOC entry 1532 (class 1259 OID 68782)
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
-- Dependencies: 6 1528
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
-- TOC entry 1533 (class 1259 OID 68785)
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
-- TOC entry 1534 (class 1259 OID 68788)
-- Dependencies: 6
-- Name: offers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE offers (
    offer_id bigint NOT NULL
);


--
-- TOC entry 1535 (class 1259 OID 68793)
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
-- Dependencies: 6 1531
-- Name: proxyinfo; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE proxyinfo AS (
	serverversionstr text,
	serverversionnum integer,
	proxyapiver integer,
	serverprocessid integer
);


--
-- TOC entry 1546 (class 1259 OID 77055)
-- Dependencies: 6
-- Name: real_products; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE real_products (
    product_id bigint NOT NULL,
    simple_product_id bigint NOT NULL
);


--
-- TOC entry 1536 (class 1259 OID 68797)
-- Dependencies: 6
-- Name: simple_products; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE simple_products (
    product_id bigint NOT NULL,
    product_code character varying(32) NOT NULL
);


--
-- TOC entry 313 (class 1247 OID 16411)
-- Dependencies: 6 1529
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
-- Dependencies: 6 1530
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
-- TOC entry 1544 (class 1259 OID 77048)
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
-- Dependencies: 6 313
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
-- TOC entry 1542 (class 1259 OID 77040)
-- Dependencies: 6 1543
-- Name: assembling_algorithms_algorithm_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE assembling_algorithms_algorithm_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1891 (class 0 OID 0)
-- Dependencies: 1542
-- Name: assembling_algorithms_algorithm_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE assembling_algorithms_algorithm_id_seq OWNED BY assembling_algorithms.algorithm_id;


--
-- TOC entry 1892 (class 0 OID 0)
-- Dependencies: 1542
-- Name: assembling_algorithms_algorithm_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('assembling_algorithms_algorithm_id_seq', 1, false);


--
-- TOC entry 1547 (class 1259 OID 77075)
-- Dependencies: 6 1548
-- Name: assembling_categories_assembling_category_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE assembling_categories_assembling_category_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1893 (class 0 OID 0)
-- Dependencies: 1547
-- Name: assembling_categories_assembling_category_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE assembling_categories_assembling_category_id_seq OWNED BY assembling_categories.assembling_category_id;


--
-- TOC entry 1894 (class 0 OID 0)
-- Dependencies: 1547
-- Name: assembling_categories_assembling_category_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('assembling_categories_assembling_category_id_seq', 1, false);


--
-- TOC entry 1549 (class 1259 OID 77083)
-- Dependencies: 6 1550
-- Name: assembling_schema_items_assembling_schema_item_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE assembling_schema_items_assembling_schema_item_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1895 (class 0 OID 0)
-- Dependencies: 1549
-- Name: assembling_schema_items_assembling_schema_item_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE assembling_schema_items_assembling_schema_item_id_seq OWNED BY assembling_schema_items.item_id;


--
-- TOC entry 1896 (class 0 OID 0)
-- Dependencies: 1549
-- Name: assembling_schema_items_assembling_schema_item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('assembling_schema_items_assembling_schema_item_id_seq', 1, false);


--
-- TOC entry 1539 (class 1259 OID 68836)
-- Dependencies: 6 1540
-- Name: complex_product_items_complex_product_item_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE complex_product_items_complex_product_item_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1897 (class 0 OID 0)
-- Dependencies: 1539
-- Name: complex_product_items_complex_product_item_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE complex_product_items_complex_product_item_id_seq OWNED BY complex_product_items.complex_product_item_id;


--
-- TOC entry 1898 (class 0 OID 0)
-- Dependencies: 1539
-- Name: complex_product_items_complex_product_item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('complex_product_items_complex_product_item_id_seq', 1, false);


--
-- TOC entry 1537 (class 1259 OID 68800)
-- Dependencies: 1533 6
-- Name: offer_items_offer_item_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE offer_items_offer_item_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1899 (class 0 OID 0)
-- Dependencies: 1537
-- Name: offer_items_offer_item_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE offer_items_offer_item_id_seq OWNED BY offer_items.offer_item_id;


--
-- TOC entry 1900 (class 0 OID 0)
-- Dependencies: 1537
-- Name: offer_items_offer_item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('offer_items_offer_item_id_seq', 1, false);


--
-- TOC entry 1538 (class 1259 OID 68802)
-- Dependencies: 1534 6
-- Name: offers_offer_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE offers_offer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1901 (class 0 OID 0)
-- Dependencies: 1538
-- Name: offers_offer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE offers_offer_id_seq OWNED BY offers.offer_id;


--
-- TOC entry 1902 (class 0 OID 0)
-- Dependencies: 1538
-- Name: offers_offer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('offers_offer_id_seq', 1, false);


--
-- TOC entry 1545 (class 1259 OID 77053)
-- Dependencies: 6
-- Name: products_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE products_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1903 (class 0 OID 0)
-- Dependencies: 1545
-- Name: products_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('products_seq', 6, true);


--
-- TOC entry 1551 (class 1259 OID 77111)
-- Dependencies: 6
-- Name: virtual_products_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE virtual_products_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1904 (class 0 OID 0)
-- Dependencies: 1551
-- Name: virtual_products_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('virtual_products_seq', 1, false);


--
-- TOC entry 1822 (class 2604 OID 77045)
-- Dependencies: 1542 1543 1543
-- Name: algorithm_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE assembling_algorithms ALTER COLUMN algorithm_id SET DEFAULT nextval('assembling_algorithms_algorithm_id_seq'::regclass);


--
-- TOC entry 1823 (class 2604 OID 77080)
-- Dependencies: 1547 1548 1548
-- Name: assembling_category_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE assembling_categories ALTER COLUMN assembling_category_id SET DEFAULT nextval('assembling_categories_assembling_category_id_seq'::regclass);


--
-- TOC entry 1824 (class 2604 OID 77088)
-- Dependencies: 1550 1549 1550
-- Name: item_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE assembling_schema_items ALTER COLUMN item_id SET DEFAULT nextval('assembling_schema_items_assembling_schema_item_id_seq'::regclass);


--
-- TOC entry 1820 (class 2604 OID 68841)
-- Dependencies: 1540 1539 1540
-- Name: complex_product_item_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE complex_product_items ALTER COLUMN complex_product_item_id SET DEFAULT nextval('complex_product_items_complex_product_item_id_seq'::regclass);


--
-- TOC entry 1818 (class 2604 OID 68804)
-- Dependencies: 1537 1533
-- Name: offer_item_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE offer_items ALTER COLUMN offer_item_id SET DEFAULT nextval('offer_items_offer_item_id_seq'::regclass);


--
-- TOC entry 1819 (class 2604 OID 68805)
-- Dependencies: 1538 1534
-- Name: offer_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE offers ALTER COLUMN offer_id SET DEFAULT nextval('offers_offer_id_seq'::regclass);


--
-- TOC entry 1881 (class 0 OID 77042)
-- Dependencies: 1543
-- Data for Name: assembling_algorithms; Type: TABLE DATA; Schema: public; Owner: -
--

COPY assembling_algorithms (algorithm_id, algorithm_code, algorithm_name, description) FROM stdin;
\.


--
-- TOC entry 1884 (class 0 OID 77077)
-- Dependencies: 1548
-- Data for Name: assembling_categories; Type: TABLE DATA; Schema: public; Owner: -
--

COPY assembling_categories (assembling_category_id, category_code, category_name, description) FROM stdin;
\.


--
-- TOC entry 1885 (class 0 OID 77085)
-- Dependencies: 1550
-- Data for Name: assembling_schema_items; Type: TABLE DATA; Schema: public; Owner: -
--

COPY assembling_schema_items (item_id, assembling_schema_id, algorithm_id, min_constraint, max_constraint, virtual_product_id, quantity, default_value, description) FROM stdin;
\.


--
-- TOC entry 1880 (class 0 OID 77034)
-- Dependencies: 1541
-- Data for Name: assembling_schemas; Type: TABLE DATA; Schema: public; Owner: -
--

COPY assembling_schemas (product_id, category_id, schema_code, schema_name, is_obsolete, description) FROM stdin;
\.


--
-- TOC entry 1879 (class 0 OID 68838)
-- Dependencies: 1540
-- Data for Name: complex_product_items; Type: TABLE DATA; Schema: public; Owner: -
--

COPY complex_product_items (complex_product_item_id, complex_product_id, order_position, product_id, quantity, unit_price, item_price, applied_algorithm_info) FROM stdin;
\.


--
-- TOC entry 1874 (class 0 OID 68782)
-- Dependencies: 1532
-- Data for Name: complex_products; Type: TABLE DATA; Schema: public; Owner: -
--

COPY complex_products (product_id, product_code, assembling_schema_info) FROM stdin;
\.


--
-- TOC entry 1875 (class 0 OID 68785)
-- Dependencies: 1533
-- Data for Name: offer_items; Type: TABLE DATA; Schema: public; Owner: -
--

COPY offer_items (offer_item_id, offer_id, product_id, item_description, quantity, unit_price, item_price) FROM stdin;
\.


--
-- TOC entry 1876 (class 0 OID 68788)
-- Dependencies: 1534
-- Data for Name: offers; Type: TABLE DATA; Schema: public; Owner: -
--

COPY offers (offer_id) FROM stdin;
\.


--
-- TOC entry 1877 (class 0 OID 68793)
-- Dependencies: 1535
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: -
--

COPY products (product_id, product_type, product_price) FROM stdin;
1	\N	12.3000
\.


--
-- TOC entry 1883 (class 0 OID 77055)
-- Dependencies: 1546
-- Data for Name: real_products; Type: TABLE DATA; Schema: public; Owner: -
--

COPY real_products (product_id, simple_product_id) FROM stdin;
\.


--
-- TOC entry 1878 (class 0 OID 68797)
-- Dependencies: 1536
-- Data for Name: simple_products; Type: TABLE DATA; Schema: public; Owner: -
--

COPY simple_products (product_id, product_code) FROM stdin;
1	SP
\.


--
-- TOC entry 1882 (class 0 OID 77048)
-- Dependencies: 1544
-- Data for Name: virtual_products; Type: TABLE DATA; Schema: public; Owner: -
--

COPY virtual_products (product_id, product_type) FROM stdin;
\.


--
-- TOC entry 1844 (class 2606 OID 77047)
-- Dependencies: 1543 1543
-- Name: pk_assembling_algorithms; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_algorithms
    ADD CONSTRAINT pk_assembling_algorithms PRIMARY KEY (algorithm_id);


--
-- TOC entry 1854 (class 2606 OID 77082)
-- Dependencies: 1548 1548
-- Name: pk_assembling_categories; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_categories
    ADD CONSTRAINT pk_assembling_categories PRIMARY KEY (assembling_category_id);


--
-- TOC entry 1860 (class 2606 OID 77095)
-- Dependencies: 1550 1550
-- Name: pk_assembling_schema_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT pk_assembling_schema_items PRIMARY KEY (item_id);


--
-- TOC entry 1838 (class 2606 OID 77039)
-- Dependencies: 1541 1541
-- Name: pk_assembling_schemas; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT pk_assembling_schemas PRIMARY KEY (product_id);


--
-- TOC entry 1836 (class 2606 OID 68843)
-- Dependencies: 1540 1540
-- Name: pk_complex_product_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT pk_complex_product_items PRIMARY KEY (complex_product_item_id);


--
-- TOC entry 1826 (class 2606 OID 68807)
-- Dependencies: 1532 1532
-- Name: pk_complex_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY complex_products
    ADD CONSTRAINT pk_complex_products PRIMARY KEY (product_id);


--
-- TOC entry 1832 (class 2606 OID 68809)
-- Dependencies: 1535 1535
-- Name: pk_merchandises; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY products
    ADD CONSTRAINT pk_merchandises PRIMARY KEY (product_id);


--
-- TOC entry 1828 (class 2606 OID 68811)
-- Dependencies: 1533 1533
-- Name: pk_offer_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY offer_items
    ADD CONSTRAINT pk_offer_items PRIMARY KEY (offer_item_id);


--
-- TOC entry 1830 (class 2606 OID 68813)
-- Dependencies: 1534 1534
-- Name: pk_offers; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY offers
    ADD CONSTRAINT pk_offers PRIMARY KEY (offer_id);


--
-- TOC entry 1852 (class 2606 OID 77059)
-- Dependencies: 1546 1546
-- Name: pk_real_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY real_products
    ADD CONSTRAINT pk_real_products PRIMARY KEY (product_id);


--
-- TOC entry 1834 (class 2606 OID 68815)
-- Dependencies: 1536 1536
-- Name: pk_simple_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT pk_simple_products PRIMARY KEY (product_id);


--
-- TOC entry 1850 (class 2606 OID 77052)
-- Dependencies: 1544 1544
-- Name: pk_virtual_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY virtual_products
    ADD CONSTRAINT pk_virtual_products PRIMARY KEY (product_id);


--
-- TOC entry 1846 (class 2606 OID 77117)
-- Dependencies: 1543 1543
-- Name: uk_assembling_algorithms_by_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_algorithms
    ADD CONSTRAINT uk_assembling_algorithms_by_code UNIQUE (algorithm_code);


--
-- TOC entry 1848 (class 2606 OID 77119)
-- Dependencies: 1543 1543
-- Name: uk_assembling_algorithms_by_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_algorithms
    ADD CONSTRAINT uk_assembling_algorithms_by_name UNIQUE (algorithm_name);


--
-- TOC entry 1856 (class 2606 OID 77124)
-- Dependencies: 1548 1548
-- Name: uk_assembling_categories_by_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_categories
    ADD CONSTRAINT uk_assembling_categories_by_code UNIQUE (category_code);


--
-- TOC entry 1858 (class 2606 OID 77126)
-- Dependencies: 1548 1548
-- Name: uk_assembling_categories_by_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_categories
    ADD CONSTRAINT uk_assembling_categories_by_name UNIQUE (category_name);


--
-- TOC entry 1840 (class 2606 OID 77142)
-- Dependencies: 1541 1541
-- Name: uk_assembling_schemas_by_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT uk_assembling_schemas_by_code UNIQUE (schema_code);


--
-- TOC entry 1842 (class 2606 OID 77144)
-- Dependencies: 1541 1541
-- Name: uk_assembling_schemas_by_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT uk_assembling_schemas_by_name UNIQUE (schema_name);


--
-- TOC entry 1872 (class 2606 OID 77101)
-- Dependencies: 1543 1843 1550
-- Name: fk_assembling_schema_items_algorithm; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT fk_assembling_schema_items_algorithm FOREIGN KEY (algorithm_id) REFERENCES assembling_algorithms(algorithm_id);


--
-- TOC entry 1871 (class 2606 OID 77096)
-- Dependencies: 1837 1541 1550
-- Name: fk_assembling_schema_items_owner; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT fk_assembling_schema_items_owner FOREIGN KEY (assembling_schema_id) REFERENCES assembling_schemas(product_id);


--
-- TOC entry 1873 (class 2606 OID 77106)
-- Dependencies: 1544 1550 1849
-- Name: fk_assembling_schema_items_virtual_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT fk_assembling_schema_items_virtual_product FOREIGN KEY (virtual_product_id) REFERENCES virtual_products(product_id);


--
-- TOC entry 1868 (class 2606 OID 77136)
-- Dependencies: 1853 1541 1548
-- Name: fk_assembling_schemas_category; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT fk_assembling_schemas_category FOREIGN KEY (category_id) REFERENCES assembling_categories(assembling_category_id);


--
-- TOC entry 1867 (class 2606 OID 77065)
-- Dependencies: 1541 1849 1544
-- Name: fk_assembling_schemas_virtual_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT fk_assembling_schemas_virtual_product FOREIGN KEY (product_id) REFERENCES virtual_products(product_id);


--
-- TOC entry 1865 (class 2606 OID 68844)
-- Dependencies: 1540 1825 1532
-- Name: fk_complex_product_items_cp; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT fk_complex_product_items_cp FOREIGN KEY (complex_product_id) REFERENCES complex_products(product_id);


--
-- TOC entry 1866 (class 2606 OID 68849)
-- Dependencies: 1831 1540 1535
-- Name: fk_complex_product_items_products; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT fk_complex_product_items_products FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 1861 (class 2606 OID 68816)
-- Dependencies: 1831 1532 1535
-- Name: fk_complex_products_merchandises; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_products
    ADD CONSTRAINT fk_complex_products_merchandises FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 1862 (class 2606 OID 68821)
-- Dependencies: 1535 1533 1831
-- Name: fk_offer_items_merchandises; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY offer_items
    ADD CONSTRAINT fk_offer_items_merchandises FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 1863 (class 2606 OID 68826)
-- Dependencies: 1534 1829 1533
-- Name: fk_offer_items_offers; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY offer_items
    ADD CONSTRAINT fk_offer_items_offers FOREIGN KEY (offer_id) REFERENCES offers(offer_id);


--
-- TOC entry 1869 (class 2606 OID 77060)
-- Dependencies: 1833 1536 1546
-- Name: fk_real_products_simple_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY real_products
    ADD CONSTRAINT fk_real_products_simple_product FOREIGN KEY (simple_product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 1870 (class 2606 OID 77070)
-- Dependencies: 1544 1546 1849
-- Name: fk_real_products_virtual_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY real_products
    ADD CONSTRAINT fk_real_products_virtual_product FOREIGN KEY (product_id) REFERENCES virtual_products(product_id);


--
-- TOC entry 1864 (class 2606 OID 68831)
-- Dependencies: 1536 1535 1831
-- Name: fk_simple_products_merchandises; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_simple_products_merchandises FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 1890 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: -
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2008-04-18 00:11:58

--
-- PostgreSQL database dump complete
--

