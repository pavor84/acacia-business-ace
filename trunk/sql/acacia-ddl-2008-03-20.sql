--
-- PostgreSQL database dump
--

-- Started on 2008-03-20 19:00:45

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 411 (class 2612 OID 24991)
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: -
--

CREATE PROCEDURAL LANGUAGE plpgsql;


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 1609 (class 1259 OID 25369)
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
-- TOC entry 1631 (class 1259 OID 26229)
-- Dependencies: 1920 6
-- Name: bank_details; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE bank_details (
    bank_detail_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0) NOT NULL,
    is_default boolean DEFAULT false NOT NULL,
    currency_id integer NOT NULL,
    bank_id numeric(18,0) NOT NULL,
    bank_branch_id numeric(18,0) NOT NULL,
    bank_account character varying(22) NOT NULL,
    bank_contact_id numeric(18,0)
);


--
-- TOC entry 252 (class 1247 OID 24994)
-- Dependencies: 6 1583
-- Name: breakpoint; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE breakpoint AS (
	func oid,
	linenumber integer,
	targetname text
);


--
-- TOC entry 1611 (class 1259 OID 25397)
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
-- TOC entry 1584 (class 1259 OID 24995)
-- Dependencies: 6
-- Name: classified_objects; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE classified_objects (
    classifier_id numeric(18,0) NOT NULL,
    classified_object_id numeric(18,0) NOT NULL,
    description text
);


--
-- TOC entry 1585 (class 1259 OID 25001)
-- Dependencies: 6
-- Name: classifier_applied_for_dot; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE classifier_applied_for_dot (
    classifier_id numeric(18,0) NOT NULL,
    data_object_type_id integer NOT NULL
);


--
-- TOC entry 1586 (class 1259 OID 25004)
-- Dependencies: 1901 6
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
-- TOC entry 1587 (class 1259 OID 25011)
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
-- TOC entry 1630 (class 1259 OID 26202)
-- Dependencies: 6
-- Name: communication_contacts; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE communication_contacts (
    communication_contact_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0) NOT NULL,
    communication_type_id integer NOT NULL,
    communication_value character varying(64) NOT NULL
);


--
-- TOC entry 2228 (class 0 OID 0)
-- Dependencies: 1630
-- Name: COLUMN communication_contacts.communication_type_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN communication_contacts.communication_type_id IS 'Email (Work, Private), Phone (Work, Home, Fax), Mobile Phone (Work, Private), VoIP (SIP, H.323), Instant Communications (ICQ, Skype, Google Talk, MSN), Other';


--
-- TOC entry 1629 (class 1259 OID 26159)
-- Dependencies: 6
-- Name: contact_persons; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE contact_persons (
    contact_person_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0) NOT NULL,
    position_type_id numeric(18,0),
    contact_id numeric(18,0) NOT NULL,
    communication_contact_id numeric(18,0)
);


--
-- TOC entry 1610 (class 1259 OID 25387)
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
-- TOC entry 1612 (class 1259 OID 25432)
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
-- TOC entry 1632 (class 1259 OID 26260)
-- Dependencies: 6
-- Name: data_object_details; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE data_object_details (
    data_object_id numeric(18,0) NOT NULL,
    detail_code character varying(32) NOT NULL,
    detail_value character varying(64) NOT NULL,
    notes text
);


--
-- TOC entry 1634 (class 1259 OID 26333)
-- Dependencies: 6
-- Name: data_object_links; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE data_object_links (
    data_object_link_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0),
    linked_data_object_id numeric(18,0) NOT NULL,
    link_name character varying(255) NOT NULL
);


--
-- TOC entry 1588 (class 1259 OID 25017)
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
-- TOC entry 1589 (class 1259 OID 25023)
-- Dependencies: 1902 1903 1904 1905 1906 1907 6
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
-- TOC entry 1623 (class 1259 OID 25997)
-- Dependencies: 6
-- Name: delivery_certificate_items; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE delivery_certificate_items (
    certificate_item_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0) NOT NULL,
    product_id numeric(18,0) NOT NULL,
    measure_unit_id integer NOT NULL,
    quantity numeric(19,4) NOT NULL
);


--
-- TOC entry 1624 (class 1259 OID 26022)
-- Dependencies: 6
-- Name: delivery_certificate_serial_numbers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE delivery_certificate_serial_numbers (
    certificate_item_id numeric(18,0) NOT NULL,
    serial_number character varying(32) NOT NULL
);


--
-- TOC entry 1622 (class 1259 OID 25962)
-- Dependencies: 6
-- Name: delivery_certificates; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE delivery_certificates (
    delivery_certificate_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0),
    warehouse_id numeric(18,0) NOT NULL,
    warehouse_name character varying(64) NOT NULL,
    delivery_certificate_number bigint NOT NULL,
    delivery_certificate_date date NOT NULL,
    recipient_id numeric(18,0) NOT NULL,
    recipient_name character varying(48) NOT NULL,
    recipient_contact_id numeric(18,0),
    recipient_contact_name character varying(48),
    delivery_cert_method_type_id integer NOT NULL,
    creation_time date NOT NULL,
    creator_name character varying(48) NOT NULL,
    forwarder_id numeric(18,0),
    forwarder_name character varying(64),
    forwarder_contact_id numeric(18,0),
    forwarder_contact_name character varying(48),
    delivery_cert_reason_id integer NOT NULL,
    creator_id numeric(18,0) NOT NULL
);


--
-- TOC entry 1590 (class 1259 OID 25035)
-- Dependencies: 6
-- Name: enum_classes; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE enum_classes (
    enum_class_id integer NOT NULL,
    enum_class_name character varying(255) NOT NULL
);


--
-- TOC entry 310 (class 1247 OID 25040)
-- Dependencies: 6 1591
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
-- TOC entry 1617 (class 1259 OID 25718)
-- Dependencies: 6
-- Name: invoice_items; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE invoice_items (
    invoice_item_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0),
    product_id numeric(18,0) NOT NULL,
    measure_unit_id integer NOT NULL,
    ordered_quantity numeric(19,4) NOT NULL,
    shipped_quantity numeric(19,4),
    returned_quantity numeric(19,4),
    unit_price numeric(19,4) NOT NULL,
    extended_price numeric(19,4) NOT NULL,
    ship_date date,
    warehouse_id numeric(18,0)
);


--
-- TOC entry 1616 (class 1259 OID 25611)
-- Dependencies: 6
-- Name: invoices; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE invoices (
    invoice_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0),
    branch_id numeric(18,0) NOT NULL,
    branch_name character varying(64) NOT NULL,
    invoice_number bigint NOT NULL,
    invoice_date date NOT NULL,
    recipient_id numeric(18,0) NOT NULL,
    recipient_name character varying(128) NOT NULL,
    recipient_contact_id numeric(18,0) NOT NULL,
    recipient_contact_name character varying(48) NOT NULL,
    invoice_type_id integer NOT NULL,
    status_id integer NOT NULL,
    creation_time date NOT NULL,
    creator_id numeric(18,0) NOT NULL,
    creator_name character varying(48) NOT NULL,
    doc_delivery_method_id integer NOT NULL,
    transportation_method_id integer NOT NULL,
    shipping_agent_id numeric(18,0),
    transportation_price numeric(19,4),
    currency_id integer NOT NULL,
    invoice_sub_value numeric(19,4) NOT NULL,
    discount_percent numeric(4,2),
    discount_value numeric(19,4),
    invoice_value numeric(19,4) NOT NULL,
    excise_duty_percent numeric(4,2),
    excise_duty_value numeric(19,4),
    vat_condition_id integer NOT NULL,
    vat_percent numeric(4,2) NOT NULL,
    vat_value numeric(19,4) NOT NULL,
    total_invoice_value numeric(19,4) NOT NULL,
    payment_type_id integer NOT NULL,
    payment_terms_id integer NOT NULL,
    payment_due_date date,
    delivery_type_id integer NOT NULL,
    sent_time date,
    sender_id numeric(18,0),
    sender_name character varying(48),
    first_ship_date date,
    last_ship_date date,
    finalizing_date date
);


--
-- TOC entry 1621 (class 1259 OID 25939)
-- Dependencies: 6
-- Name: order_confirmation_items; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE order_confirmation_items (
    confirmation_item_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0) NOT NULL,
    product_id numeric(18,0) NOT NULL,
    measure_unit_id integer NOT NULL,
    confirmed_quantity numeric(19,4) NOT NULL,
    unit_price numeric(19,4) NOT NULL,
    extended_price numeric(19,4) NOT NULL,
    ship_date date
);


--
-- TOC entry 2229 (class 0 OID 0)
-- Dependencies: 1621
-- Name: COLUMN order_confirmation_items.parent_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN order_confirmation_items.parent_id IS 'order_confirmation_id';


--
-- TOC entry 1620 (class 1259 OID 25909)
-- Dependencies: 6
-- Name: order_confirmations; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE order_confirmations (
    order_confirmation_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0),
    purchase_order_id numeric(18,0) NOT NULL,
    invoice_number character varying(16) NOT NULL,
    invoice_date date NOT NULL,
    status_id integer,
    sender_id numeric(18,0),
    sender_name character varying(48),
    transportation_price numeric(19,4),
    currency_id integer NOT NULL,
    invoice_sub_value numeric(19,4) NOT NULL,
    discount_percent numeric(4,2),
    discount_value numeric(19,4),
    invoice_value numeric(19,4) NOT NULL
);


--
-- TOC entry 1608 (class 1259 OID 25337)
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
-- TOC entry 1633 (class 1259 OID 26275)
-- Dependencies: 6
-- Name: passports; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE passports (
    passport_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0) NOT NULL,
    passport_type_id integer NOT NULL,
    passport_number character varying(16) NOT NULL,
    issue_date date NOT NULL,
    expiration_date date NOT NULL,
    issuer_id numeric(18,0) NOT NULL,
    issuer_branch_id numeric(18,0) NOT NULL,
    additional_info character varying(255)
);


--
-- TOC entry 2230 (class 0 OID 0)
-- Dependencies: 1633
-- Name: COLUMN passports.passport_type_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN passports.passport_type_id IS 'Passport, Identity Card, Driving License';


--
-- TOC entry 1592 (class 1259 OID 25041)
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
-- TOC entry 2231 (class 0 OID 0)
-- Dependencies: 1592
-- Name: COLUMN pattern_mask_formats.format_type; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN pattern_mask_formats.format_type IS 'D for DateFormatter;
N for NumberFormatter;
M for MaskFormatter.';


--
-- TOC entry 1613 (class 1259 OID 25447)
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
-- TOC entry 1628 (class 1259 OID 26146)
-- Dependencies: 6
-- Name: position_types; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE position_types (
    position_type_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0),
    owner_type character(1) NOT NULL,
    position_type_name character varying(32) NOT NULL,
    description text
);


--
-- TOC entry 2232 (class 0 OID 0)
-- Dependencies: 1628
-- Name: COLUMN position_types.owner_type; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN position_types.owner_type IS 'P - Person, O - Organization';


--
-- TOC entry 1593 (class 1259 OID 25047)
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
-- TOC entry 1594 (class 1259 OID 25053)
-- Dependencies: 6
-- Name: product_suppliers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE product_suppliers (
    product_id numeric(18,0) NOT NULL,
    supplier_id numeric(18,0) NOT NULL,
    description text
);


--
-- TOC entry 1595 (class 1259 OID 25059)
-- Dependencies: 1908 1909 1910 1911 1912 1913 6
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
-- Dependencies: 6 1596
-- Name: proxyinfo; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE proxyinfo AS (
	serverversionstr text,
	serverversionnum integer,
	proxyapiver integer,
	serverprocessid integer
);


--
-- TOC entry 1619 (class 1259 OID 25851)
-- Dependencies: 6
-- Name: purchase_order_items; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE purchase_order_items (
    order_item_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0) NOT NULL,
    product_id numeric(18,0) NOT NULL,
    measure_unit_id integer NOT NULL,
    ordered_quantity numeric(19,4) NOT NULL,
    confirmed_quantity numeric(19,4),
    delivered_quantity numeric(19,4),
    purchase_price numeric(19,4),
    ship_date date
);


--
-- TOC entry 1618 (class 1259 OID 25808)
-- Dependencies: 1919 6
-- Name: purchase_orders; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE purchase_orders (
    purchase_order_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0),
    branch_id numeric(18,0) NOT NULL,
    branch_name character varying(64) NOT NULL,
    order_number bigint NOT NULL,
    supplier_id numeric(18,0) NOT NULL,
    supplier_name character varying(128) NOT NULL,
    supplier_order_number character varying(16),
    supplier_contact_id numeric(18,0),
    supplier_contact_name character varying(48),
    status_id integer NOT NULL,
    creation_time date DEFAULT now() NOT NULL,
    creator_id numeric(18,0) NOT NULL,
    creator_name character varying(48) NOT NULL,
    doc_delivery_method_id integer NOT NULL,
    sent_time date,
    sender_id numeric(18,0),
    sender_name character varying(48),
    first_delivery_time date,
    last_delivery_time date,
    finalizing_time date
);


--
-- TOC entry 2233 (class 0 OID 0)
-- Dependencies: 1618
-- Name: COLUMN purchase_orders.branch_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN purchase_orders.branch_id IS 'equals to Address_id';


--
-- TOC entry 1626 (class 1259 OID 26074)
-- Dependencies: 6
-- Name: receipt_certificate_items; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE receipt_certificate_items (
    certificate_item_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0) NOT NULL,
    product_id numeric(18,0) NOT NULL,
    measure_unit_id integer NOT NULL,
    quantity numeric(19,4) NOT NULL
);


--
-- TOC entry 1627 (class 1259 OID 26099)
-- Dependencies: 6
-- Name: receipt_certificate_serial_numbers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE receipt_certificate_serial_numbers (
    certificate_item_id numeric(18,0) NOT NULL,
    serial_number character varying(32) NOT NULL
);


--
-- TOC entry 1625 (class 1259 OID 26037)
-- Dependencies: 6
-- Name: receipt_certificates; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE receipt_certificates (
    receipt_certificate_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0),
    warehouse_id numeric(18,0) NOT NULL,
    warehouse_name character varying(64) NOT NULL,
    receipt_certificate_number bigint NOT NULL,
    receipt_certificate_date date NOT NULL,
    deliverer_id numeric(18,0) NOT NULL,
    deliverer_name character varying(48) NOT NULL,
    deliverer_contact_id numeric(18,0),
    deliverer_contact_name character varying(48),
    receipt_cert_method_type_id integer NOT NULL,
    receipt_cert_reason_id integer NOT NULL,
    creation_time date NOT NULL,
    creator_name character varying(48) NOT NULL,
    forwarder_id numeric(18,0),
    forwarder_name character varying(64),
    forwarder_contact_id numeric(18,0),
    forwarder_contact_name character varying(48),
    creator_id numeric(18,0) NOT NULL
);


--
-- TOC entry 1597 (class 1259 OID 25074)
-- Dependencies: 6
-- Name: resource_bundle; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE resource_bundle (
    resource_id integer NOT NULL,
    enum_class_id integer NOT NULL,
    enum_name character varying(64) NOT NULL
);


--
-- TOC entry 1598 (class 1259 OID 25077)
-- Dependencies: 1914 6
-- Name: sequence_identifiers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE sequence_identifiers (
    seq_id_key bigint NOT NULL,
    seq_id_name character varying(64) NOT NULL,
    seq_id_value numeric(38,0) DEFAULT 0 NOT NULL
);


--
-- TOC entry 330 (class 1247 OID 25083)
-- Dependencies: 6 1599
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
-- TOC entry 1600 (class 1259 OID 25084)
-- Dependencies: 1916 1917 6
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
-- Dependencies: 6 1601
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
-- TOC entry 1615 (class 1259 OID 25516)
-- Dependencies: 1918 6
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
    minimum_quantity numeric(19,4) DEFAULT 1.0 NOT NULL,
    maximum_quantity numeric(19,4),
    default_quantity numeric(19,4),
    purchase_price numeric(19,4),
    sale_price numeric(19,4),
    delivery_time integer,
    notes text
);


--
-- TOC entry 1614 (class 1259 OID 25489)
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
-- TOC entry 1602 (class 1259 OID 25115)
-- Dependencies: 6
-- Name: data_object_type_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE data_object_type_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2234 (class 0 OID 0)
-- Dependencies: 1602
-- Name: data_object_type_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('data_object_type_seq', 37, true);


--
-- TOC entry 1603 (class 1259 OID 25117)
-- Dependencies: 6
-- Name: data_objects_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE data_objects_seq
    INCREMENT BY 1
    MAXVALUE 999999999999999999
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2235 (class 0 OID 0)
-- Dependencies: 1603
-- Name: data_objects_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('data_objects_seq', 2, true);


--
-- TOC entry 1604 (class 1259 OID 25119)
-- Dependencies: 6
-- Name: enum_classes_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE enum_classes_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2236 (class 0 OID 0)
-- Dependencies: 1604
-- Name: enum_classes_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('enum_classes_seq', 1, true);


--
-- TOC entry 1605 (class 1259 OID 25121)
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
-- TOC entry 2237 (class 0 OID 0)
-- Dependencies: 1605
-- Name: pattern_mask_formats_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('pattern_mask_formats_seq', 1, false);


--
-- TOC entry 1606 (class 1259 OID 25123)
-- Dependencies: 6
-- Name: resource_bundle_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE resource_bundle_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2238 (class 0 OID 0)
-- Dependencies: 1606
-- Name: resource_bundle_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('resource_bundle_seq', 18, true);


--
-- TOC entry 1607 (class 1259 OID 25125)
-- Dependencies: 1598 6
-- Name: sequence_identifiers_seq_id_key_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE sequence_identifiers_seq_id_key_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2239 (class 0 OID 0)
-- Dependencies: 1607
-- Name: sequence_identifiers_seq_id_key_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE sequence_identifiers_seq_id_key_seq OWNED BY sequence_identifiers.seq_id_key;


--
-- TOC entry 2240 (class 0 OID 0)
-- Dependencies: 1607
-- Name: sequence_identifiers_seq_id_key_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('sequence_identifiers_seq_id_key_seq', 1, false);


--
-- TOC entry 1915 (class 2604 OID 25336)
-- Dependencies: 1607 1598
-- Name: seq_id_key; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE sequence_identifiers ALTER COLUMN seq_id_key SET DEFAULT nextval('sequence_identifiers_seq_id_key_seq'::regclass);


--
-- TOC entry 2197 (class 0 OID 25369)
-- Dependencies: 1609
-- Data for Name: addresses; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2219 (class 0 OID 26229)
-- Dependencies: 1631
-- Data for Name: bank_details; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2199 (class 0 OID 25397)
-- Dependencies: 1611
-- Data for Name: cities; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2182 (class 0 OID 24995)
-- Dependencies: 1584
-- Data for Name: classified_objects; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2183 (class 0 OID 25001)
-- Dependencies: 1585
-- Data for Name: classifier_applied_for_dot; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2184 (class 0 OID 25004)
-- Dependencies: 1586
-- Data for Name: classifier_groups; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2185 (class 0 OID 25011)
-- Dependencies: 1587
-- Data for Name: classifiers; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2218 (class 0 OID 26202)
-- Dependencies: 1630
-- Data for Name: communication_contacts; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2217 (class 0 OID 26159)
-- Dependencies: 1629
-- Data for Name: contact_persons; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2198 (class 0 OID 25387)
-- Dependencies: 1610
-- Data for Name: countries; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2200 (class 0 OID 25432)
-- Dependencies: 1612
-- Data for Name: currencies; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2220 (class 0 OID 26260)
-- Dependencies: 1632
-- Data for Name: data_object_details; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2222 (class 0 OID 26333)
-- Dependencies: 1634
-- Data for Name: data_object_links; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2186 (class 0 OID 25017)
-- Dependencies: 1588
-- Data for Name: data_object_types; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) VALUES (36, 'com.cosmos.acacia.crm.data.Product', NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) VALUES (35, 'com.cosmos.acacia.crm.data.ProductCategory', NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) VALUES (1, 'com.cosmos.acacia.crm.data.Organization', NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) VALUES (37, 'com.cosmos.acacia.crm.data.Person', NULL, NULL, NULL, NULL, NULL);


--
-- TOC entry 2187 (class 0 OID 25023)
-- Dependencies: 1589
-- Data for Name: data_objects; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1, 1, 35, '2001-12-23 21:39:53.662522+02', 1, 1, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1203863458084, 6, 36, '2008-02-24 16:30:58.082+02', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1205606553734, 2, 36, '2008-03-15 20:42:33.732+02', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (2, 2, 1, '2008-03-15 20:42:33.732+02', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1205657267969, 1, 36, '2008-03-16 10:47:47.966+02', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1203863479289, 3, 36, '2008-02-24 16:31:19.286+02', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1205661120192, 2, 36, '2008-03-16 11:52:00.191+02', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1205688426876, 3, 37, '2008-03-16 19:27:06.875+02', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1205752768447, 2, 37, '2008-03-17 13:19:28.442+02', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1205859120347, 1, 36, '2008-03-18 18:52:00.343+02', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1205859152950, 1, 36, '2008-03-18 18:52:32.947+02', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1205915841376, 2, 36, '2008-03-19 10:37:21.369+02', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);


--
-- TOC entry 2211 (class 0 OID 25997)
-- Dependencies: 1623
-- Data for Name: delivery_certificate_items; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2212 (class 0 OID 26022)
-- Dependencies: 1624
-- Data for Name: delivery_certificate_serial_numbers; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2210 (class 0 OID 25962)
-- Dependencies: 1622
-- Data for Name: delivery_certificates; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2188 (class 0 OID 25035)
-- Dependencies: 1590
-- Data for Name: enum_classes; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO enum_classes (enum_class_id, enum_class_name) VALUES (1, 'com.cosmos.acacia.crm.enums.MeasurementUnit');


--
-- TOC entry 2205 (class 0 OID 25718)
-- Dependencies: 1617
-- Data for Name: invoice_items; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2204 (class 0 OID 25611)
-- Dependencies: 1616
-- Data for Name: invoices; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2209 (class 0 OID 25939)
-- Dependencies: 1621
-- Data for Name: order_confirmation_items; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2208 (class 0 OID 25909)
-- Dependencies: 1620
-- Data for Name: order_confirmations; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2196 (class 0 OID 25337)
-- Dependencies: 1608
-- Data for Name: organizations; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2221 (class 0 OID 26275)
-- Dependencies: 1633
-- Data for Name: passports; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2189 (class 0 OID 25041)
-- Dependencies: 1592
-- Data for Name: pattern_mask_formats; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2201 (class 0 OID 25447)
-- Dependencies: 1613
-- Data for Name: persons; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO persons (person_id, parent_id, first_name, second_name, last_name, extra_name, personal_unique_id, birth_date, birth_place_country_id, birth_place_city_id, gender_id, description) VALUES (1205688426876, NULL, 'Bozhidar', 'Plamenov', 'Bozhanov', NULL, '8708190524', '1987-08-19', NULL, NULL, NULL, NULL);


--
-- TOC entry 2216 (class 0 OID 26146)
-- Dependencies: 1628
-- Data for Name: position_types; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2190 (class 0 OID 25047)
-- Dependencies: 1593
-- Data for Name: product_categories; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO product_categories (product_category_id, parent_id, category_name, pattern_mask_format_id, description) VALUES (1, NULL, '1st Product Category', NULL, NULL);


--
-- TOC entry 2191 (class 0 OID 25053)
-- Dependencies: 1594
-- Data for Name: product_suppliers; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2192 (class 0 OID 25059)
-- Dependencies: 1595
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO products (product_id, parent_id, category_id, product_name, product_code, measure_unit_id, is_complex, is_purchased, is_salable, is_obsolete, pattern_mask_format_id, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id) VALUES (1203863458084, NULL, 1, 'p1', 'pc1', 1, true, false, true, false, NULL, NULL, 1.0000, 3.0000, 2.0000, 100.2000, 200.0000, 250.0000, 1, 16, 1.20, 2.30, 3.50, NULL, 23.450, 12, 'My description', NULL);
INSERT INTO products (product_id, parent_id, category_id, product_name, product_code, measure_unit_id, is_complex, is_purchased, is_salable, is_obsolete, pattern_mask_format_id, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id) VALUES (1205657267969, NULL, 1, '3rd Product', 'p3', 1, false, false, true, false, NULL, NULL, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO products (product_id, parent_id, category_id, product_name, product_code, measure_unit_id, is_complex, is_purchased, is_salable, is_obsolete, pattern_mask_format_id, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id) VALUES (1203863479289, NULL, 1, 'p2', 'pc2', 2, false, false, true, false, NULL, NULL, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO products (product_id, parent_id, category_id, product_name, product_code, measure_unit_id, is_complex, is_purchased, is_salable, is_obsolete, pattern_mask_format_id, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id) VALUES (1205859120347, NULL, 1, 'nano', '1234', 1, false, false, true, false, NULL, NULL, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, 16, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO products (product_id, parent_id, category_id, product_name, product_code, measure_unit_id, is_complex, is_purchased, is_salable, is_obsolete, pattern_mask_format_id, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id) VALUES (1205859152950, NULL, 1, 'nano3', '4324', 1, false, false, true, false, NULL, NULL, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO products (product_id, parent_id, category_id, product_name, product_code, measure_unit_id, is_complex, is_purchased, is_salable, is_obsolete, pattern_mask_format_id, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id) VALUES (1205915841376, NULL, 1, 'dfsdf', 'p38', 1, false, false, true, false, NULL, NULL, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);


--
-- TOC entry 2207 (class 0 OID 25851)
-- Dependencies: 1619
-- Data for Name: purchase_order_items; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2206 (class 0 OID 25808)
-- Dependencies: 1618
-- Data for Name: purchase_orders; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2214 (class 0 OID 26074)
-- Dependencies: 1626
-- Data for Name: receipt_certificate_items; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2215 (class 0 OID 26099)
-- Dependencies: 1627
-- Data for Name: receipt_certificate_serial_numbers; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2213 (class 0 OID 26037)
-- Dependencies: 1625
-- Data for Name: receipt_certificates; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2193 (class 0 OID 25074)
-- Dependencies: 1597
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
-- TOC entry 2194 (class 0 OID 25077)
-- Dependencies: 1598
-- Data for Name: sequence_identifiers; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2195 (class 0 OID 25084)
-- Dependencies: 1600
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2203 (class 0 OID 25516)
-- Dependencies: 1615
-- Data for Name: warehouse_products; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2202 (class 0 OID 25489)
-- Dependencies: 1614
-- Data for Name: warehouses; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1974 (class 2606 OID 25376)
-- Dependencies: 1609 1609
-- Name: pk_addresses; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT pk_addresses PRIMARY KEY (address_id);


--
-- TOC entry 2036 (class 2606 OID 26234)
-- Dependencies: 1631 1631
-- Name: pk_bank_details; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT pk_bank_details PRIMARY KEY (bank_detail_id);


--
-- TOC entry 1980 (class 2606 OID 25404)
-- Dependencies: 1611 1611
-- Name: pk_cities; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY cities
    ADD CONSTRAINT pk_cities PRIMARY KEY (city_id);


--
-- TOC entry 1922 (class 2606 OID 25129)
-- Dependencies: 1584 1584 1584
-- Name: pk_classified_objects; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classified_objects
    ADD CONSTRAINT pk_classified_objects PRIMARY KEY (classifier_id, classified_object_id);


--
-- TOC entry 1924 (class 2606 OID 25131)
-- Dependencies: 1585 1585 1585
-- Name: pk_classifier_applied_for_dot; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifier_applied_for_dot
    ADD CONSTRAINT pk_classifier_applied_for_dot PRIMARY KEY (classifier_id, data_object_type_id);


--
-- TOC entry 1926 (class 2606 OID 25133)
-- Dependencies: 1586 1586
-- Name: pk_classifier_groups; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifier_groups
    ADD CONSTRAINT pk_classifier_groups PRIMARY KEY (classifier_group_id);


--
-- TOC entry 1932 (class 2606 OID 25135)
-- Dependencies: 1587 1587
-- Name: pk_classifiers; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT pk_classifiers PRIMARY KEY (classifier_id);


--
-- TOC entry 2032 (class 2606 OID 26206)
-- Dependencies: 1630 1630
-- Name: pk_communication_contacts; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT pk_communication_contacts PRIMARY KEY (communication_contact_id);


--
-- TOC entry 2030 (class 2606 OID 26163)
-- Dependencies: 1629 1629
-- Name: pk_contact_persons; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT pk_contact_persons PRIMARY KEY (contact_person_id);


--
-- TOC entry 1976 (class 2606 OID 25394)
-- Dependencies: 1610 1610
-- Name: pk_countries; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY countries
    ADD CONSTRAINT pk_countries PRIMARY KEY (country_id);


--
-- TOC entry 1984 (class 2606 OID 25439)
-- Dependencies: 1612 1612
-- Name: pk_currencies; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY currencies
    ADD CONSTRAINT pk_currencies PRIMARY KEY (currency_id);


--
-- TOC entry 2038 (class 2606 OID 26267)
-- Dependencies: 1632 1632
-- Name: pk_data_object_details; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_details
    ADD CONSTRAINT pk_data_object_details PRIMARY KEY (data_object_id);


--
-- TOC entry 2046 (class 2606 OID 26337)
-- Dependencies: 1634 1634
-- Name: pk_data_object_links; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_links
    ADD CONSTRAINT pk_data_object_links PRIMARY KEY (data_object_link_id);


--
-- TOC entry 1938 (class 2606 OID 25137)
-- Dependencies: 1588 1588
-- Name: pk_data_object_types; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_types
    ADD CONSTRAINT pk_data_object_types PRIMARY KEY (data_object_type_id);


--
-- TOC entry 1942 (class 2606 OID 25139)
-- Dependencies: 1589 1589
-- Name: pk_data_objects; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT pk_data_objects PRIMARY KEY (data_object_id);


--
-- TOC entry 2016 (class 2606 OID 26001)
-- Dependencies: 1623 1623
-- Name: pk_delivery_certificate_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT pk_delivery_certificate_items PRIMARY KEY (certificate_item_id);


--
-- TOC entry 2018 (class 2606 OID 26026)
-- Dependencies: 1624 1624 1624
-- Name: pk_delivery_certificate_serial_numbers; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY delivery_certificate_serial_numbers
    ADD CONSTRAINT pk_delivery_certificate_serial_numbers PRIMARY KEY (certificate_item_id, serial_number);


--
-- TOC entry 2012 (class 2606 OID 25966)
-- Dependencies: 1622 1622
-- Name: pk_delivery_certificates; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT pk_delivery_certificates PRIMARY KEY (delivery_certificate_id);


--
-- TOC entry 1944 (class 2606 OID 25141)
-- Dependencies: 1590 1590
-- Name: pk_enum_classes; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY enum_classes
    ADD CONSTRAINT pk_enum_classes PRIMARY KEY (enum_class_id);


--
-- TOC entry 2000 (class 2606 OID 25725)
-- Dependencies: 1617 1617
-- Name: pk_invoice_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY invoice_items
    ADD CONSTRAINT pk_invoice_items PRIMARY KEY (invoice_item_id);


--
-- TOC entry 1996 (class 2606 OID 25615)
-- Dependencies: 1616 1616
-- Name: pk_invoices; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT pk_invoices PRIMARY KEY (invoice_id);


--
-- TOC entry 2010 (class 2606 OID 25946)
-- Dependencies: 1621 1621
-- Name: pk_order_confirmation_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY order_confirmation_items
    ADD CONSTRAINT pk_order_confirmation_items PRIMARY KEY (confirmation_item_id);


--
-- TOC entry 2008 (class 2606 OID 25913)
-- Dependencies: 1620 1620
-- Name: pk_order_confirmations; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT pk_order_confirmations PRIMARY KEY (order_confirmation_id);


--
-- TOC entry 1970 (class 2606 OID 25344)
-- Dependencies: 1608 1608
-- Name: pk_organizations; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT pk_organizations PRIMARY KEY (organization_id);


--
-- TOC entry 2042 (class 2606 OID 26299)
-- Dependencies: 1633 1633
-- Name: pk_passports; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT pk_passports PRIMARY KEY (passport_id);


--
-- TOC entry 1948 (class 2606 OID 25143)
-- Dependencies: 1592 1592
-- Name: pk_pattern_mask_formats; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pattern_mask_formats
    ADD CONSTRAINT pk_pattern_mask_formats PRIMARY KEY (pattern_mask_format_id);


--
-- TOC entry 1988 (class 2606 OID 25454)
-- Dependencies: 1613 1613
-- Name: pk_persons; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT pk_persons PRIMARY KEY (person_id);


--
-- TOC entry 2028 (class 2606 OID 26153)
-- Dependencies: 1628 1628
-- Name: pk_position_types; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY position_types
    ADD CONSTRAINT pk_position_types PRIMARY KEY (position_type_id);


--
-- TOC entry 1952 (class 2606 OID 25145)
-- Dependencies: 1593 1593
-- Name: pk_product_categories; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT pk_product_categories PRIMARY KEY (product_category_id);


--
-- TOC entry 1956 (class 2606 OID 25147)
-- Dependencies: 1594 1594 1594
-- Name: pk_product_suppliers; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT pk_product_suppliers PRIMARY KEY (product_id, supplier_id);


--
-- TOC entry 1958 (class 2606 OID 25149)
-- Dependencies: 1595 1595
-- Name: pk_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY products
    ADD CONSTRAINT pk_products PRIMARY KEY (product_id);


--
-- TOC entry 2006 (class 2606 OID 25858)
-- Dependencies: 1619 1619
-- Name: pk_purchase_order_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT pk_purchase_order_items PRIMARY KEY (order_item_id);


--
-- TOC entry 2002 (class 2606 OID 25813)
-- Dependencies: 1618 1618
-- Name: pk_purchase_orders; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT pk_purchase_orders PRIMARY KEY (purchase_order_id);


--
-- TOC entry 2024 (class 2606 OID 26078)
-- Dependencies: 1626 1626
-- Name: pk_receipt_certificate_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT pk_receipt_certificate_items PRIMARY KEY (certificate_item_id);


--
-- TOC entry 2026 (class 2606 OID 26103)
-- Dependencies: 1627 1627 1627
-- Name: pk_receipt_certificate_serial_numbers; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY receipt_certificate_serial_numbers
    ADD CONSTRAINT pk_receipt_certificate_serial_numbers PRIMARY KEY (certificate_item_id, serial_number);


--
-- TOC entry 2020 (class 2606 OID 26041)
-- Dependencies: 1625 1625
-- Name: pk_receipt_certificates; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT pk_receipt_certificates PRIMARY KEY (receipt_certificate_id);


--
-- TOC entry 1960 (class 2606 OID 25151)
-- Dependencies: 1597 1597
-- Name: pk_resource_bundle; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY resource_bundle
    ADD CONSTRAINT pk_resource_bundle PRIMARY KEY (resource_id);


--
-- TOC entry 1964 (class 2606 OID 25153)
-- Dependencies: 1600 1600
-- Name: pk_users; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT pk_users PRIMARY KEY (user_id);


--
-- TOC entry 1994 (class 2606 OID 25523)
-- Dependencies: 1615 1615 1615
-- Name: pk_warehouse_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY warehouse_products
    ADD CONSTRAINT pk_warehouse_products PRIMARY KEY (warehouse_id, product_id);


--
-- TOC entry 1992 (class 2606 OID 25496)
-- Dependencies: 1614 1614
-- Name: pk_warehouses; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY warehouses
    ADD CONSTRAINT pk_warehouses PRIMARY KEY (warehouse_id);


--
-- TOC entry 1982 (class 2606 OID 25406)
-- Dependencies: 1611 1611 1611
-- Name: uk_cities_country_id_city_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY cities
    ADD CONSTRAINT uk_cities_country_id_city_name UNIQUE (country_id, city_name);


--
-- TOC entry 1928 (class 2606 OID 25155)
-- Dependencies: 1586 1586 1586
-- Name: uk_classifier_groups_parent_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifier_groups
    ADD CONSTRAINT uk_classifier_groups_parent_code UNIQUE (parent_id, classifier_group_code);


--
-- TOC entry 1930 (class 2606 OID 25157)
-- Dependencies: 1586 1586 1586
-- Name: uk_classifier_groups_parent_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifier_groups
    ADD CONSTRAINT uk_classifier_groups_parent_name UNIQUE (parent_id, classifier_group_name);


--
-- TOC entry 1934 (class 2606 OID 25159)
-- Dependencies: 1587 1587 1587
-- Name: uk_classifiers_parent_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT uk_classifiers_parent_code UNIQUE (parent_id, classifier_code);


--
-- TOC entry 1936 (class 2606 OID 25161)
-- Dependencies: 1587 1587 1587
-- Name: uk_classifiers_parent_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT uk_classifiers_parent_name UNIQUE (parent_id, classifier_name);


--
-- TOC entry 2034 (class 2606 OID 26223)
-- Dependencies: 1630 1630 1630 1630
-- Name: uk_communication_contacts_parent_type_value; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT uk_communication_contacts_parent_type_value UNIQUE (parent_id, communication_type_id, communication_value);


--
-- TOC entry 1978 (class 2606 OID 25396)
-- Dependencies: 1610 1610
-- Name: uk_countries_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY countries
    ADD CONSTRAINT uk_countries_name UNIQUE (country_name);


--
-- TOC entry 1986 (class 2606 OID 25441)
-- Dependencies: 1612 1612
-- Name: uk_currencies_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY currencies
    ADD CONSTRAINT uk_currencies_name UNIQUE (currency_name);


--
-- TOC entry 2040 (class 2606 OID 26269)
-- Dependencies: 1632 1632 1632
-- Name: uk_data_object_details_do_id_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_details
    ADD CONSTRAINT uk_data_object_details_do_id_code UNIQUE (data_object_id, detail_code);


--
-- TOC entry 2048 (class 2606 OID 26354)
-- Dependencies: 1634 1634 1634
-- Name: uk_data_object_links_parent_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_links
    ADD CONSTRAINT uk_data_object_links_parent_name UNIQUE (parent_id, link_name);


--
-- TOC entry 2014 (class 2606 OID 25968)
-- Dependencies: 1622 1622 1622
-- Name: uk_delivery_certificates_parent_cert_number; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT uk_delivery_certificates_parent_cert_number UNIQUE (parent_id, delivery_certificate_number);


--
-- TOC entry 1940 (class 2606 OID 25163)
-- Dependencies: 1588 1588
-- Name: uk_dot_data_object_type; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_types
    ADD CONSTRAINT uk_dot_data_object_type UNIQUE (data_object_type);


--
-- TOC entry 1946 (class 2606 OID 25165)
-- Dependencies: 1590 1590
-- Name: uk_enum_classes; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY enum_classes
    ADD CONSTRAINT uk_enum_classes UNIQUE (enum_class_name);


--
-- TOC entry 1998 (class 2606 OID 25617)
-- Dependencies: 1616 1616 1616
-- Name: uk_invoices_invoice_number; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT uk_invoices_invoice_number UNIQUE (parent_id, invoice_number);


--
-- TOC entry 1972 (class 2606 OID 25368)
-- Dependencies: 1608 1608 1608
-- Name: uk_organizations_parent_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT uk_organizations_parent_name UNIQUE (parent_id, organization_name);


--
-- TOC entry 2044 (class 2606 OID 26317)
-- Dependencies: 1633 1633 1633 1633
-- Name: uk_passports_parent_type_number; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT uk_passports_parent_type_number UNIQUE (parent_id, passport_type_id, passport_number);


--
-- TOC entry 1950 (class 2606 OID 25167)
-- Dependencies: 1592 1592 1592
-- Name: uk_pattern_mask_formats_owner_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pattern_mask_formats
    ADD CONSTRAINT uk_pattern_mask_formats_owner_name UNIQUE (owner_id, pattern_name);


--
-- TOC entry 1990 (class 2606 OID 25483)
-- Dependencies: 1613 1613 1613 1613 1613 1613
-- Name: uk_persons_parent_by_names; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT uk_persons_parent_by_names UNIQUE (parent_id, first_name, second_name, last_name, extra_name);


--
-- TOC entry 1954 (class 2606 OID 25169)
-- Dependencies: 1593 1593 1593
-- Name: uk_product_categories_parent_category_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT uk_product_categories_parent_category_name UNIQUE (parent_id, category_name);


--
-- TOC entry 2004 (class 2606 OID 26110)
-- Dependencies: 1618 1618 1618
-- Name: uk_purchase_orders_parent_order_number; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT uk_purchase_orders_parent_order_number UNIQUE (parent_id, order_number);


--
-- TOC entry 2022 (class 2606 OID 26043)
-- Dependencies: 1625 1625 1625
-- Name: uk_receipt_certificates_parent_cert_number; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT uk_receipt_certificates_parent_cert_number UNIQUE (parent_id, receipt_certificate_number);


--
-- TOC entry 1962 (class 2606 OID 25171)
-- Dependencies: 1598 1598
-- Name: uk_seq_id_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY sequence_identifiers
    ADD CONSTRAINT uk_seq_id_name UNIQUE (seq_id_name);


--
-- TOC entry 1966 (class 2606 OID 25173)
-- Dependencies: 1600 1600
-- Name: uk_users_email_address; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT uk_users_email_address UNIQUE (email_address);


--
-- TOC entry 1968 (class 2606 OID 25175)
-- Dependencies: 1600 1600
-- Name: uk_users_user_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT uk_users_user_name UNIQUE (user_name);


--
-- TOC entry 2071 (class 2606 OID 25176)
-- Dependencies: 1963 1600 1600
-- Name: fk6a68e0844bb6904; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk6a68e0844bb6904 FOREIGN KEY (creator_id) REFERENCES users(user_id);


--
-- TOC entry 2072 (class 2606 OID 25181)
-- Dependencies: 1600 1941 1589
-- Name: fk6a68e08a08870b9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk6a68e08a08870b9 FOREIGN KEY (person_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2081 (class 2606 OID 25417)
-- Dependencies: 1609 1979 1611
-- Name: fk_addresses_city_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT fk_addresses_city_id FOREIGN KEY (city_id) REFERENCES cities(city_id);


--
-- TOC entry 2082 (class 2606 OID 25412)
-- Dependencies: 1609 1975 1610
-- Name: fk_addresses_country_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT fk_addresses_country_id FOREIGN KEY (country_id) REFERENCES countries(country_id);


--
-- TOC entry 2083 (class 2606 OID 25377)
-- Dependencies: 1609 1941 1589
-- Name: fk_addresses_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT fk_addresses_do_id FOREIGN KEY (address_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2172 (class 2606 OID 26245)
-- Dependencies: 1612 1983 1631
-- Name: fk_bank_details; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk_bank_details FOREIGN KEY (currency_id) REFERENCES currencies(currency_id);


--
-- TOC entry 2173 (class 2606 OID 26240)
-- Dependencies: 1609 1973 1631
-- Name: fk_bank_details_address; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk_bank_details_address FOREIGN KEY (parent_id) REFERENCES addresses(address_id);


--
-- TOC entry 2171 (class 2606 OID 26250)
-- Dependencies: 1609 1631 1973
-- Name: fk_bank_details_bank_branch; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk_bank_details_bank_branch FOREIGN KEY (bank_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2170 (class 2606 OID 26255)
-- Dependencies: 1631 1987 1613
-- Name: fk_bank_details_bank_contact; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk_bank_details_bank_contact FOREIGN KEY (bank_contact_id) REFERENCES persons(person_id);


--
-- TOC entry 2174 (class 2606 OID 26235)
-- Dependencies: 1631 1589 1941
-- Name: fk_bank_details_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk_bank_details_do_id FOREIGN KEY (bank_detail_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2085 (class 2606 OID 25407)
-- Dependencies: 1610 1975 1611
-- Name: fk_cities_country_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cities
    ADD CONSTRAINT fk_cities_country_id FOREIGN KEY (country_id) REFERENCES countries(country_id);


--
-- TOC entry 2050 (class 2606 OID 25196)
-- Dependencies: 1931 1584 1587
-- Name: fk_classified_objects_classifier_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classified_objects
    ADD CONSTRAINT fk_classified_objects_classifier_id FOREIGN KEY (classifier_id) REFERENCES classifiers(classifier_id);


--
-- TOC entry 2049 (class 2606 OID 25201)
-- Dependencies: 1941 1584 1589
-- Name: fk_classified_objects_object_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classified_objects
    ADD CONSTRAINT fk_classified_objects_object_id FOREIGN KEY (classified_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2052 (class 2606 OID 25206)
-- Dependencies: 1585 1931 1587
-- Name: fk_classifier_applied_for_dot_classifiers; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifier_applied_for_dot
    ADD CONSTRAINT fk_classifier_applied_for_dot_classifiers FOREIGN KEY (classifier_id) REFERENCES classifiers(classifier_id);


--
-- TOC entry 2051 (class 2606 OID 25211)
-- Dependencies: 1585 1937 1588
-- Name: fk_classifier_applied_for_dot_dot_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifier_applied_for_dot
    ADD CONSTRAINT fk_classifier_applied_for_dot_dot_id FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2053 (class 2606 OID 25216)
-- Dependencies: 1589 1586 1941
-- Name: fk_classifier_groups_cg_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifier_groups
    ADD CONSTRAINT fk_classifier_groups_cg_id FOREIGN KEY (classifier_group_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2055 (class 2606 OID 25221)
-- Dependencies: 1587 1925 1586
-- Name: fk_classifiers_group_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT fk_classifiers_group_id FOREIGN KEY (classifier_group_id) REFERENCES classifier_groups(classifier_group_id);


--
-- TOC entry 2054 (class 2606 OID 25226)
-- Dependencies: 1587 1941 1589
-- Name: fk_classifiers_id_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT fk_classifiers_id_do_id FOREIGN KEY (classifier_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2168 (class 2606 OID 26212)
-- Dependencies: 1973 1609 1630
-- Name: fk_communication_contacts_address; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT fk_communication_contacts_address FOREIGN KEY (parent_id) REFERENCES addresses(address_id);


--
-- TOC entry 2167 (class 2606 OID 26217)
-- Dependencies: 1630 1597 1959
-- Name: fk_communication_contacts_comm_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT fk_communication_contacts_comm_type FOREIGN KEY (communication_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2169 (class 2606 OID 26207)
-- Dependencies: 1589 1630 1941
-- Name: fk_communication_contacts_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT fk_communication_contacts_do_id FOREIGN KEY (communication_contact_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2163 (class 2606 OID 26193)
-- Dependencies: 1609 1973 1629
-- Name: fk_contact_persons_address; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT fk_contact_persons_address FOREIGN KEY (parent_id) REFERENCES addresses(address_id);


--
-- TOC entry 2162 (class 2606 OID 26224)
-- Dependencies: 1629 1630 2031
-- Name: fk_contact_persons_comm_contact; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT fk_contact_persons_comm_contact FOREIGN KEY (communication_contact_id) REFERENCES communication_contacts(communication_contact_id);


--
-- TOC entry 2164 (class 2606 OID 26184)
-- Dependencies: 1613 1987 1629
-- Name: fk_contact_persons_contact; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT fk_contact_persons_contact FOREIGN KEY (contact_id) REFERENCES persons(person_id);


--
-- TOC entry 2166 (class 2606 OID 26164)
-- Dependencies: 1589 1629 1941
-- Name: fk_contact_persons_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT fk_contact_persons_do_id FOREIGN KEY (contact_person_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2165 (class 2606 OID 26174)
-- Dependencies: 2027 1629 1628
-- Name: fk_contact_persons_position_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT fk_contact_persons_position_type FOREIGN KEY (position_type_id) REFERENCES position_types(position_type_id);


--
-- TOC entry 2084 (class 2606 OID 25442)
-- Dependencies: 1983 1612 1610
-- Name: fk_countries_currency_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY countries
    ADD CONSTRAINT fk_countries_currency_id FOREIGN KEY (currency_id) REFERENCES currencies(currency_id);


--
-- TOC entry 2175 (class 2606 OID 26270)
-- Dependencies: 1632 1941 1589
-- Name: fk_data_object_details_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_details
    ADD CONSTRAINT fk_data_object_details_do_id FOREIGN KEY (data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2181 (class 2606 OID 26338)
-- Dependencies: 1941 1634 1589
-- Name: fk_data_object_links_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_links
    ADD CONSTRAINT fk_data_object_links_do_id FOREIGN KEY (data_object_link_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2180 (class 2606 OID 26343)
-- Dependencies: 1941 1634 1589
-- Name: fk_data_object_links_linked_object; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_links
    ADD CONSTRAINT fk_data_object_links_linked_object FOREIGN KEY (linked_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2058 (class 2606 OID 26348)
-- Dependencies: 1937 1588 1589
-- Name: fk_data_objects_do_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk_data_objects_do_type FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2056 (class 2606 OID 25231)
-- Dependencies: 1941 1589 1589
-- Name: fk_data_objects_linked_data_object_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk_data_objects_linked_data_object_id FOREIGN KEY (linked_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2057 (class 2606 OID 25236)
-- Dependencies: 1589 1589 1941
-- Name: fk_data_objects_parent_data_object_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk_data_objects_parent_data_object_id FOREIGN KEY (parent_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2144 (class 2606 OID 26007)
-- Dependencies: 1622 1623 2011
-- Name: fk_delivery_certificate_items_delivery_cert; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fk_delivery_certificate_items_delivery_cert FOREIGN KEY (parent_id) REFERENCES delivery_certificates(delivery_certificate_id);


--
-- TOC entry 2145 (class 2606 OID 26002)
-- Dependencies: 1623 1941 1589
-- Name: fk_delivery_certificate_items_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fk_delivery_certificate_items_do_id FOREIGN KEY (certificate_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2142 (class 2606 OID 26017)
-- Dependencies: 1597 1623 1959
-- Name: fk_delivery_certificate_items_measure_unit; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fk_delivery_certificate_items_measure_unit FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2143 (class 2606 OID 26012)
-- Dependencies: 1623 1595 1957
-- Name: fk_delivery_certificate_items_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fk_delivery_certificate_items_product FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2146 (class 2606 OID 26027)
-- Dependencies: 1624 2015 1623
-- Name: fk_delivery_certificate_serial_numbers_dci; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_serial_numbers
    ADD CONSTRAINT fk_delivery_certificate_serial_numbers_dci FOREIGN KEY (certificate_item_id) REFERENCES delivery_certificate_items(certificate_item_id);


--
-- TOC entry 2140 (class 2606 OID 26117)
-- Dependencies: 1987 1622 1613
-- Name: fk_delivery_certificates_creator; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_delivery_certificates_creator FOREIGN KEY (creator_id) REFERENCES persons(person_id);


--
-- TOC entry 2134 (class 2606 OID 25972)
-- Dependencies: 1589 1622 1941
-- Name: fk_delivery_certificates_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_delivery_certificates_do_id FOREIGN KEY (delivery_certificate_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2141 (class 2606 OID 26323)
-- Dependencies: 1608 1969 1622
-- Name: fk_delivery_certificates_forwarder; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_delivery_certificates_forwarder FOREIGN KEY (forwarder_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2138 (class 2606 OID 25992)
-- Dependencies: 1987 1622 1613
-- Name: fk_delivery_certificates_forwarder_contact; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_delivery_certificates_forwarder_contact FOREIGN KEY (forwarder_contact_id) REFERENCES persons(person_id);


--
-- TOC entry 2139 (class 2606 OID 26032)
-- Dependencies: 1597 1959 1622
-- Name: fk_delivery_certificates_reason; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_delivery_certificates_reason FOREIGN KEY (delivery_cert_reason_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2136 (class 2606 OID 25982)
-- Dependencies: 1613 1622 1987
-- Name: fk_delivery_certificates_recipient_contact; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_delivery_certificates_recipient_contact FOREIGN KEY (recipient_contact_id) REFERENCES persons(person_id);


--
-- TOC entry 2137 (class 2606 OID 25987)
-- Dependencies: 1959 1622 1597
-- Name: fk_delivery_certificates_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_delivery_certificates_type FOREIGN KEY (delivery_cert_method_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2135 (class 2606 OID 25977)
-- Dependencies: 1991 1614 1622
-- Name: fk_delivery_certificates_warehouse; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_delivery_certificates_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2109 (class 2606 OID 25728)
-- Dependencies: 1617 1589 1941
-- Name: fk_invoice_items_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoice_items
    ADD CONSTRAINT fk_invoice_items_do_id FOREIGN KEY (invoice_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2110 (class 2606 OID 25733)
-- Dependencies: 1617 1616 1995
-- Name: fk_invoice_items_invoice_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoice_items
    ADD CONSTRAINT fk_invoice_items_invoice_id FOREIGN KEY (parent_id) REFERENCES invoices(invoice_id);


--
-- TOC entry 2113 (class 2606 OID 25748)
-- Dependencies: 1597 1959 1617
-- Name: fk_invoice_items_measure_unit_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoice_items
    ADD CONSTRAINT fk_invoice_items_measure_unit_id FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2111 (class 2606 OID 25738)
-- Dependencies: 1595 1957 1617
-- Name: fk_invoice_items_product_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoice_items
    ADD CONSTRAINT fk_invoice_items_product_id FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2112 (class 2606 OID 25743)
-- Dependencies: 1617 1991 1614
-- Name: fk_invoice_items_warehouse_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoice_items
    ADD CONSTRAINT fk_invoice_items_warehouse_id FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2095 (class 2606 OID 25618)
-- Dependencies: 1973 1616 1609
-- Name: fk_invoices_branch_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_branch_id FOREIGN KEY (branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2096 (class 2606 OID 25623)
-- Dependencies: 1987 1616 1613
-- Name: fk_invoices_creator_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_creator_id FOREIGN KEY (creator_id) REFERENCES persons(person_id);


--
-- TOC entry 2097 (class 2606 OID 25628)
-- Dependencies: 1597 1616 1959
-- Name: fk_invoices_currency_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_currency_id FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2098 (class 2606 OID 25633)
-- Dependencies: 1616 1597 1959
-- Name: fk_invoices_delivery_type_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_delivery_type_id FOREIGN KEY (delivery_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2099 (class 2606 OID 25638)
-- Dependencies: 1941 1616 1589
-- Name: fk_invoices_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_do_id FOREIGN KEY (invoice_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2100 (class 2606 OID 25643)
-- Dependencies: 1597 1616 1959
-- Name: fk_invoices_doc_delivery_method_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_doc_delivery_method_id FOREIGN KEY (doc_delivery_method_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2101 (class 2606 OID 25648)
-- Dependencies: 1597 1959 1616
-- Name: fk_invoices_invoice_type_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_invoice_type_id FOREIGN KEY (invoice_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2102 (class 2606 OID 25653)
-- Dependencies: 1959 1597 1616
-- Name: fk_invoices_payment_terms_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_payment_terms_id FOREIGN KEY (payment_terms_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2103 (class 2606 OID 25658)
-- Dependencies: 1959 1597 1616
-- Name: fk_invoices_payment_type_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_payment_type_id FOREIGN KEY (payment_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2104 (class 2606 OID 25663)
-- Dependencies: 1613 1987 1616
-- Name: fk_invoices_recipient_contact; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_recipient_contact FOREIGN KEY (recipient_contact_id) REFERENCES persons(person_id);


--
-- TOC entry 2105 (class 2606 OID 25668)
-- Dependencies: 1987 1613 1616
-- Name: fk_invoices_sender_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_sender_id FOREIGN KEY (sender_id) REFERENCES persons(person_id);


--
-- TOC entry 2106 (class 2606 OID 25673)
-- Dependencies: 1597 1959 1616
-- Name: fk_invoices_status_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_status_id FOREIGN KEY (status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2107 (class 2606 OID 25678)
-- Dependencies: 1959 1597 1616
-- Name: fk_invoices_transportation_method_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_transportation_method_id FOREIGN KEY (transportation_method_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2108 (class 2606 OID 25683)
-- Dependencies: 1597 1959 1616
-- Name: fk_invoices_vat_condition_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_vat_condition_id FOREIGN KEY (vat_condition_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2131 (class 2606 OID 25957)
-- Dependencies: 1621 1959 1597
-- Name: fk_order_confirmation_items_measure_unit; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmation_items
    ADD CONSTRAINT fk_order_confirmation_items_measure_unit FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2133 (class 2606 OID 25947)
-- Dependencies: 1620 2007 1621
-- Name: fk_order_confirmation_items_poc_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmation_items
    ADD CONSTRAINT fk_order_confirmation_items_poc_id FOREIGN KEY (parent_id) REFERENCES order_confirmations(order_confirmation_id);


--
-- TOC entry 2132 (class 2606 OID 25952)
-- Dependencies: 1957 1621 1595
-- Name: fk_order_confirmation_items_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmation_items
    ADD CONSTRAINT fk_order_confirmation_items_product FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2130 (class 2606 OID 25914)
-- Dependencies: 1597 1959 1620
-- Name: fk_order_confirmations_currency; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk_order_confirmations_currency FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2129 (class 2606 OID 25919)
-- Dependencies: 1941 1589 1620
-- Name: fk_order_confirmations_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk_order_confirmations_do_id FOREIGN KEY (order_confirmation_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2128 (class 2606 OID 25924)
-- Dependencies: 1618 1620 2001
-- Name: fk_order_confirmations_po_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk_order_confirmations_po_id FOREIGN KEY (purchase_order_id) REFERENCES purchase_orders(purchase_order_id);


--
-- TOC entry 2127 (class 2606 OID 25929)
-- Dependencies: 1620 1987 1613
-- Name: fk_order_confirmations_sender; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk_order_confirmations_sender FOREIGN KEY (sender_id) REFERENCES persons(person_id);


--
-- TOC entry 2126 (class 2606 OID 25934)
-- Dependencies: 1620 1597 1959
-- Name: fk_order_confirmations_status; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk_order_confirmations_status FOREIGN KEY (status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2079 (class 2606 OID 25427)
-- Dependencies: 1609 1608 1973
-- Name: fk_organizations_admin_addr_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk_organizations_admin_addr_id FOREIGN KEY (administration_address_id) REFERENCES addresses(address_id);


--
-- TOC entry 2080 (class 2606 OID 25484)
-- Dependencies: 1983 1612 1608
-- Name: fk_organizations_currency_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk_organizations_currency_id FOREIGN KEY (currency_id) REFERENCES currencies(currency_id);


--
-- TOC entry 2075 (class 2606 OID 25345)
-- Dependencies: 1941 1608 1589
-- Name: fk_organizations_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk_organizations_do_id FOREIGN KEY (organization_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2078 (class 2606 OID 25422)
-- Dependencies: 1609 1973 1608
-- Name: fk_organizations_reg_addr_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk_organizations_reg_addr_id FOREIGN KEY (registration_address_id) REFERENCES addresses(address_id);


--
-- TOC entry 2077 (class 2606 OID 25362)
-- Dependencies: 1608 1969 1608
-- Name: fk_organizations_reg_org_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk_organizations_reg_org_id FOREIGN KEY (registration_organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2076 (class 2606 OID 25357)
-- Dependencies: 1608 1597 1959
-- Name: fk_organizations_type_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk_organizations_type_id FOREIGN KEY (organization_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2177 (class 2606 OID 26300)
-- Dependencies: 1941 1589 1633
-- Name: fk_passports_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT fk_passports_do_id FOREIGN KEY (passport_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2176 (class 2606 OID 26328)
-- Dependencies: 1969 1633 1608
-- Name: fk_passports_issuer; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT fk_passports_issuer FOREIGN KEY (issuer_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2178 (class 2606 OID 26293)
-- Dependencies: 1609 1633 1973
-- Name: fk_passports_issuer_branch; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT fk_passports_issuer_branch FOREIGN KEY (issuer_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2179 (class 2606 OID 26288)
-- Dependencies: 1597 1959 1633
-- Name: fk_passports_pass_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT fk_passports_pass_type FOREIGN KEY (passport_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2088 (class 2606 OID 25465)
-- Dependencies: 1613 1979 1611
-- Name: fk_persons_city_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT fk_persons_city_id FOREIGN KEY (birth_place_city_id) REFERENCES cities(city_id);


--
-- TOC entry 2087 (class 2606 OID 25460)
-- Dependencies: 1975 1613 1610
-- Name: fk_persons_country_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT fk_persons_country_id FOREIGN KEY (birth_place_country_id) REFERENCES countries(country_id);


--
-- TOC entry 2086 (class 2606 OID 25455)
-- Dependencies: 1613 1941 1589
-- Name: fk_persons_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT fk_persons_do_id FOREIGN KEY (person_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2089 (class 2606 OID 25470)
-- Dependencies: 1613 1959 1597
-- Name: fk_persons_gender_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT fk_persons_gender_id FOREIGN KEY (gender_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2161 (class 2606 OID 26154)
-- Dependencies: 1628 1941 1589
-- Name: fk_position_types_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY position_types
    ADD CONSTRAINT fk_position_types_do_id FOREIGN KEY (position_type_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2060 (class 2606 OID 25251)
-- Dependencies: 1592 1593 1947
-- Name: fk_product_categories_pattern_mask_format_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT fk_product_categories_pattern_mask_format_id FOREIGN KEY (pattern_mask_format_id) REFERENCES pattern_mask_formats(pattern_mask_format_id);


--
-- TOC entry 2059 (class 2606 OID 25256)
-- Dependencies: 1593 1941 1589
-- Name: fk_product_categories_product_category_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT fk_product_categories_product_category_id FOREIGN KEY (product_category_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2063 (class 2606 OID 25261)
-- Dependencies: 1951 1595 1593
-- Name: fk_product_category_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_product_category_id FOREIGN KEY (category_id) REFERENCES product_categories(product_category_id);


--
-- TOC entry 2062 (class 2606 OID 25266)
-- Dependencies: 1595 1594 1957
-- Name: fk_product_suppliers_product_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk_product_suppliers_product_id FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2061 (class 2606 OID 26365)
-- Dependencies: 2045 1594 1634
-- Name: fk_product_suppliers_supplier; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk_product_suppliers_supplier FOREIGN KEY (supplier_id) REFERENCES data_object_links(data_object_link_id);


--
-- TOC entry 2064 (class 2606 OID 25271)
-- Dependencies: 1595 1597 1959
-- Name: fk_products_color_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_products_color_id FOREIGN KEY (product_color_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2065 (class 2606 OID 25276)
-- Dependencies: 1959 1595 1597
-- Name: fk_products_dimension_unit_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_products_dimension_unit_id FOREIGN KEY (dimension_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2066 (class 2606 OID 25281)
-- Dependencies: 1595 1597 1959
-- Name: fk_products_measure_unit_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_products_measure_unit_id FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2067 (class 2606 OID 25286)
-- Dependencies: 1947 1595 1592
-- Name: fk_products_pattern_mask_format_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_products_pattern_mask_format_id FOREIGN KEY (pattern_mask_format_id) REFERENCES pattern_mask_formats(pattern_mask_format_id);


--
-- TOC entry 2068 (class 2606 OID 25291)
-- Dependencies: 1595 1941 1589
-- Name: fk_products_product_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_products_product_id FOREIGN KEY (product_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2069 (class 2606 OID 25296)
-- Dependencies: 1959 1597 1595
-- Name: fk_products_weight_unit_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_products_weight_unit_id FOREIGN KEY (weight_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2125 (class 2606 OID 25859)
-- Dependencies: 1941 1589 1619
-- Name: fk_purchase_order_items_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fk_purchase_order_items_do_id FOREIGN KEY (order_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2122 (class 2606 OID 25874)
-- Dependencies: 1959 1597 1619
-- Name: fk_purchase_order_items_measure_unit; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fk_purchase_order_items_measure_unit FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2124 (class 2606 OID 25864)
-- Dependencies: 1618 2001 1619
-- Name: fk_purchase_order_items_po_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fk_purchase_order_items_po_id FOREIGN KEY (parent_id) REFERENCES purchase_orders(purchase_order_id);


--
-- TOC entry 2123 (class 2606 OID 25869)
-- Dependencies: 1619 1595 1957
-- Name: fk_purchase_order_items_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fk_purchase_order_items_product FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2114 (class 2606 OID 25816)
-- Dependencies: 1618 1973 1609
-- Name: fk_purchase_orders_branch_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fk_purchase_orders_branch_id FOREIGN KEY (branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2115 (class 2606 OID 25821)
-- Dependencies: 1618 1987 1613
-- Name: fk_purchase_orders_contact_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fk_purchase_orders_contact_id FOREIGN KEY (supplier_contact_id) REFERENCES persons(person_id);


--
-- TOC entry 2116 (class 2606 OID 25826)
-- Dependencies: 1618 1987 1613
-- Name: fk_purchase_orders_creator_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fk_purchase_orders_creator_id FOREIGN KEY (creator_id) REFERENCES persons(person_id);


--
-- TOC entry 2117 (class 2606 OID 25831)
-- Dependencies: 1941 1589 1618
-- Name: fk_purchase_orders_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fk_purchase_orders_do_id FOREIGN KEY (purchase_order_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2118 (class 2606 OID 25836)
-- Dependencies: 1959 1597 1618
-- Name: fk_purchase_orders_doc_delivery_method; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fk_purchase_orders_doc_delivery_method FOREIGN KEY (doc_delivery_method_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2119 (class 2606 OID 25841)
-- Dependencies: 1987 1613 1618
-- Name: fk_purchase_orders_sender_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fk_purchase_orders_sender_id FOREIGN KEY (sender_id) REFERENCES persons(person_id);


--
-- TOC entry 2120 (class 2606 OID 25846)
-- Dependencies: 1959 1618 1597
-- Name: fk_purchase_orders_status_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fk_purchase_orders_status_id FOREIGN KEY (status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2121 (class 2606 OID 26360)
-- Dependencies: 1618 1634 2045
-- Name: fk_purchase_orders_supplier; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fk_purchase_orders_supplier FOREIGN KEY (supplier_id) REFERENCES data_object_links(data_object_link_id);


--
-- TOC entry 2158 (class 2606 OID 26084)
-- Dependencies: 1626 1589 1941
-- Name: fk_receipt_certificate_items_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk_receipt_certificate_items_do_id FOREIGN KEY (certificate_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2157 (class 2606 OID 26089)
-- Dependencies: 1597 1959 1626
-- Name: fk_receipt_certificate_items_measure_unit; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk_receipt_certificate_items_measure_unit FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2156 (class 2606 OID 26094)
-- Dependencies: 1595 1957 1626
-- Name: fk_receipt_certificate_items_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk_receipt_certificate_items_product FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2159 (class 2606 OID 26079)
-- Dependencies: 2019 1626 1625
-- Name: fk_receipt_certificate_items_receipt_cert; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk_receipt_certificate_items_receipt_cert FOREIGN KEY (parent_id) REFERENCES receipt_certificates(receipt_certificate_id);


--
-- TOC entry 2160 (class 2606 OID 26104)
-- Dependencies: 1626 1627 2023
-- Name: fk_receipt_certificate_serial_numbers_dci; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_serial_numbers
    ADD CONSTRAINT fk_receipt_certificate_serial_numbers_dci FOREIGN KEY (certificate_item_id) REFERENCES receipt_certificate_items(certificate_item_id);


--
-- TOC entry 2153 (class 2606 OID 26122)
-- Dependencies: 1613 1625 1987
-- Name: fk_receipt_certificates_creator; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_creator FOREIGN KEY (creator_id) REFERENCES persons(person_id);


--
-- TOC entry 2155 (class 2606 OID 26355)
-- Dependencies: 1634 2045 1625
-- Name: fk_receipt_certificates_deliverer; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_deliverer FOREIGN KEY (deliverer_id) REFERENCES data_object_links(data_object_link_id);


--
-- TOC entry 2150 (class 2606 OID 26059)
-- Dependencies: 1987 1625 1613
-- Name: fk_receipt_certificates_deliverer_contact; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_deliverer_contact FOREIGN KEY (deliverer_contact_id) REFERENCES persons(person_id);


--
-- TOC entry 2147 (class 2606 OID 26044)
-- Dependencies: 1625 1941 1589
-- Name: fk_receipt_certificates_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_do_id FOREIGN KEY (receipt_certificate_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2154 (class 2606 OID 26318)
-- Dependencies: 1608 1969 1625
-- Name: fk_receipt_certificates_forwarder; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_forwarder FOREIGN KEY (forwarder_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2148 (class 2606 OID 26049)
-- Dependencies: 1613 1625 1987
-- Name: fk_receipt_certificates_forwarder_contact; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_forwarder_contact FOREIGN KEY (forwarder_contact_id) REFERENCES persons(person_id);


--
-- TOC entry 2149 (class 2606 OID 26054)
-- Dependencies: 1625 1597 1959
-- Name: fk_receipt_certificates_reason; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_reason FOREIGN KEY (receipt_cert_reason_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2151 (class 2606 OID 26064)
-- Dependencies: 1597 1625 1959
-- Name: fk_receipt_certificates_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_type FOREIGN KEY (receipt_cert_method_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2152 (class 2606 OID 26069)
-- Dependencies: 1991 1614 1625
-- Name: fk_receipt_certificates_warehouse; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2070 (class 2606 OID 25301)
-- Dependencies: 1943 1597 1590
-- Name: fk_resource_bundle_enum_class_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY resource_bundle
    ADD CONSTRAINT fk_resource_bundle_enum_class_id FOREIGN KEY (enum_class_id) REFERENCES enum_classes(enum_class_id);


--
-- TOC entry 2073 (class 2606 OID 25306)
-- Dependencies: 1600 1600 1963
-- Name: fk_users_creator_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk_users_creator_id FOREIGN KEY (creator_id) REFERENCES users(user_id);


--
-- TOC entry 2074 (class 2606 OID 25311)
-- Dependencies: 1600 1589 1941
-- Name: fk_users_person_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk_users_person_id FOREIGN KEY (person_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2093 (class 2606 OID 25529)
-- Dependencies: 1957 1615 1595
-- Name: fk_warehouse_products_p_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouse_products
    ADD CONSTRAINT fk_warehouse_products_p_id FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2094 (class 2606 OID 25524)
-- Dependencies: 1615 1991 1614
-- Name: fk_warehouse_products_wh_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouse_products
    ADD CONSTRAINT fk_warehouse_products_wh_id FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2092 (class 2606 OID 25502)
-- Dependencies: 1614 1609 1973
-- Name: fk_warehouses_address_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouses
    ADD CONSTRAINT fk_warehouses_address_id FOREIGN KEY (address_id) REFERENCES addresses(address_id);


--
-- TOC entry 2090 (class 2606 OID 26132)
-- Dependencies: 1614 1941 1589
-- Name: fk_warehouses_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouses
    ADD CONSTRAINT fk_warehouses_do_id FOREIGN KEY (warehouse_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2091 (class 2606 OID 26127)
-- Dependencies: 1614 1613 1987
-- Name: fk_warehouses_whman; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouses
    ADD CONSTRAINT fk_warehouses_whman FOREIGN KEY (warehouseman_id) REFERENCES persons(person_id);


--
-- TOC entry 2227 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: -
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2008-03-20 19:01:00

--
-- PostgreSQL database dump complete
--

