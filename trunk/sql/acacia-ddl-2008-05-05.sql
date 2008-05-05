--
-- PostgreSQL database dump
--

-- Started on 2008-05-06 01:55:21

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 418 (class 2612 OID 24991)
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: -
--

CREATE PROCEDURAL LANGUAGE plpgsql;


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 1615 (class 1259 OID 25369)
-- Dependencies: 6
-- Name: addresses; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE addresses (
    address_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0),
    address_name character varying(64) NOT NULL,
    country_id integer,
    city_id numeric(18,0),
    postal_code character varying(16),
    postal_address character varying(128),
    description text
);


--
-- TOC entry 1635 (class 1259 OID 26229)
-- Dependencies: 1930 1931 6
-- Name: bank_details; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE bank_details (
    bank_detail_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0) NOT NULL,
    is_default boolean DEFAULT false NOT NULL,
    currency_id integer,
    bank_id numeric(18,0) NOT NULL,
    bank_branch_id numeric(18,0) NOT NULL,
    bank_account character varying(22),
    bank_contact_id numeric(18,0),
    bic character varying(12),
    iban character varying DEFAULT 50,
    swift_code character varying(50)
);


--
-- TOC entry 252 (class 1247 OID 24994)
-- Dependencies: 6 1590
-- Name: breakpoint; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE breakpoint AS (
	func oid,
	linenumber integer,
	targetname text
);


--
-- TOC entry 1640 (class 1259 OID 26923)
-- Dependencies: 6
-- Name: business_partners; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE business_partners (
    partner_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0),
    id numeric(18,0)
);


--
-- TOC entry 1617 (class 1259 OID 25397)
-- Dependencies: 6
-- Name: cities; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE cities (
    city_id numeric(18,0) NOT NULL,
    country_id integer NOT NULL,
    city_name character varying(64) NOT NULL,
    postal_code character varying(8),
    city_code character varying(3),
    city_phone_code character varying(6),
    description text
);


--
-- TOC entry 1591 (class 1259 OID 24995)
-- Dependencies: 6
-- Name: classified_objects; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE classified_objects (
    classifier_id numeric(18,0) NOT NULL,
    classified_object_id numeric(18,0) NOT NULL,
    description text
);


--
-- TOC entry 1592 (class 1259 OID 25001)
-- Dependencies: 6
-- Name: classifier_applied_for_dot; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE classifier_applied_for_dot (
    classifier_id numeric(18,0) NOT NULL,
    data_object_type_id integer NOT NULL
);


--
-- TOC entry 1593 (class 1259 OID 25004)
-- Dependencies: 1913 6
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
-- TOC entry 1594 (class 1259 OID 25011)
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
-- TOC entry 1634 (class 1259 OID 26202)
-- Dependencies: 6
-- Name: communication_contacts; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE communication_contacts (
    communication_contact_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0) NOT NULL,
    communication_type_id integer NOT NULL,
    communication_value character varying(64) NOT NULL,
    contact_person_id numeric(18,0)
);


--
-- TOC entry 2341 (class 0 OID 0)
-- Dependencies: 1634
-- Name: COLUMN communication_contacts.communication_type_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN communication_contacts.communication_type_id IS 'Email (Work, Private), Phone (Work, Home, Fax), Mobile Phone (Work, Private), VoIP (SIP, H.323), Instant Communications (ICQ, Skype, Google Talk, MSN), Other';


--
-- TOC entry 1633 (class 1259 OID 26159)
-- Dependencies: 6
-- Name: contact_persons; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE contact_persons (
    contact_person_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0) NOT NULL,
    position_type_id numeric(18,0),
    contact_id numeric(18,0) NOT NULL
);


--
-- TOC entry 1616 (class 1259 OID 25387)
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
-- TOC entry 1618 (class 1259 OID 25432)
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
-- TOC entry 1636 (class 1259 OID 26260)
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
-- TOC entry 1638 (class 1259 OID 26333)
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
-- TOC entry 1595 (class 1259 OID 25017)
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
-- TOC entry 1596 (class 1259 OID 25023)
-- Dependencies: 1914 1915 1916 1917 1918 1919 6
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
-- TOC entry 1627 (class 1259 OID 25997)
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
-- TOC entry 1628 (class 1259 OID 26022)
-- Dependencies: 6
-- Name: delivery_certificate_serial_numbers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE delivery_certificate_serial_numbers (
    certificate_item_id numeric(18,0) NOT NULL,
    serial_number character varying(32) NOT NULL
);


--
-- TOC entry 1626 (class 1259 OID 25962)
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
-- TOC entry 1597 (class 1259 OID 25035)
-- Dependencies: 6
-- Name: enum_classes; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE enum_classes (
    enum_class_id integer NOT NULL,
    enum_class_name character varying(255) NOT NULL
);


--
-- TOC entry 310 (class 1247 OID 25040)
-- Dependencies: 6 1598
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
-- TOC entry 1621 (class 1259 OID 25718)
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
-- TOC entry 1620 (class 1259 OID 25611)
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
-- TOC entry 1625 (class 1259 OID 25939)
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
-- TOC entry 2342 (class 0 OID 0)
-- Dependencies: 1625
-- Name: COLUMN order_confirmation_items.parent_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN order_confirmation_items.parent_id IS 'order_confirmation_id';


--
-- TOC entry 1624 (class 1259 OID 25909)
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
-- TOC entry 1641 (class 1259 OID 26928)
-- Dependencies: 6
-- Name: organizations; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE organizations (
    organization_id numeric(19,0) NOT NULL,
    description character varying(255),
    nickname character varying(255),
    organization_name character varying(255) NOT NULL,
    registration_date date,
    share_capital numeric(19,2),
    unique_identifier_code character varying(255),
    vat_number character varying(255),
    organization_type_id integer,
    registration_address_id numeric(18,0),
    registration_organization_id numeric(19,2),
    administration_address_id numeric(18,0),
    currency_id integer
);


--
-- TOC entry 1637 (class 1259 OID 26275)
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
-- TOC entry 2343 (class 0 OID 0)
-- Dependencies: 1637
-- Name: COLUMN passports.passport_type_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN passports.passport_type_id IS 'Passport, Identity Card, Driving License';


--
-- TOC entry 1599 (class 1259 OID 25041)
-- Dependencies: 6
-- Name: pattern_mask_formats; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE pattern_mask_formats (
    pattern_mask_format_id integer NOT NULL,
    pattern_name character varying(64) NOT NULL,
    format_type character(1) NOT NULL,
    format character varying(128) NOT NULL,
    description text,
    owner_id numeric(19,2)
);


--
-- TOC entry 2344 (class 0 OID 0)
-- Dependencies: 1599
-- Name: COLUMN pattern_mask_formats.format_type; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN pattern_mask_formats.format_type IS 'D for DateFormatter;
N for NumberFormatter;
M for MaskFormatter.';


--
-- TOC entry 1642 (class 1259 OID 26936)
-- Dependencies: 6
-- Name: persons; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE persons (
    partner_id numeric(18,0) NOT NULL,
    birth_date date,
    description character varying(255),
    extra_name character varying(255),
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    personal_unique_id character varying(255),
    second_name character varying(255),
    gender_id integer,
    birth_place_city_id numeric(18,0),
    birth_place_country_id integer
);


--
-- TOC entry 1632 (class 1259 OID 26146)
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
-- TOC entry 2345 (class 0 OID 0)
-- Dependencies: 1632
-- Name: COLUMN position_types.owner_type; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN position_types.owner_type IS 'P - Person, O - Organization';


--
-- TOC entry 1600 (class 1259 OID 25047)
-- Dependencies: 6
-- Name: product_categories; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE product_categories (
    product_category_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0),
    category_name character varying(100) NOT NULL,
    pattern_mask_format_id integer,
    description text,
    parent_cat_id numeric(19,2)
);


--
-- TOC entry 1601 (class 1259 OID 25053)
-- Dependencies: 6
-- Name: product_suppliers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE product_suppliers (
    product_id numeric(18,0) NOT NULL,
    supplier_id numeric(18,0) NOT NULL,
    description text
);


--
-- TOC entry 1646 (class 1259 OID 27726)
-- Dependencies: 6
-- Name: products; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE products (
    product_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0),
    product_type character(2)
);


--
-- TOC entry 321 (class 1247 OID 25073)
-- Dependencies: 6 1603
-- Name: proxyinfo; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE proxyinfo AS (
	serverversionstr text,
	serverversionnum integer,
	proxyapiver integer,
	serverprocessid integer
);


--
-- TOC entry 1623 (class 1259 OID 25851)
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
-- TOC entry 1622 (class 1259 OID 25808)
-- Dependencies: 1929 6
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
-- TOC entry 2346 (class 0 OID 0)
-- Dependencies: 1622
-- Name: COLUMN purchase_orders.branch_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN purchase_orders.branch_id IS 'equals to Address_id';


--
-- TOC entry 1630 (class 1259 OID 26074)
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
-- TOC entry 1631 (class 1259 OID 26099)
-- Dependencies: 6
-- Name: receipt_certificate_serial_numbers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE receipt_certificate_serial_numbers (
    certificate_item_id numeric(18,0) NOT NULL,
    serial_number character varying(32) NOT NULL
);


--
-- TOC entry 1629 (class 1259 OID 26037)
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
-- TOC entry 1604 (class 1259 OID 25074)
-- Dependencies: 6
-- Name: resource_bundle; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE resource_bundle (
    resource_id integer NOT NULL,
    enum_class_id integer NOT NULL,
    enum_name character varying(64) NOT NULL
);


--
-- TOC entry 1605 (class 1259 OID 25077)
-- Dependencies: 1925 6
-- Name: sequence_identifiers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE sequence_identifiers (
    seq_id_key bigint NOT NULL,
    seq_id_name character varying(64) NOT NULL,
    seq_id_value numeric(38,0) DEFAULT 0 NOT NULL
);


--
-- TOC entry 1602 (class 1259 OID 25059)
-- Dependencies: 1920 1921 1922 1923 1924 6
-- Name: simple_products; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE simple_products (
    product_id numeric(18,0) NOT NULL,
    category_id numeric(18,0) NOT NULL,
    product_name character varying(100) NOT NULL,
    product_code character varying(50) NOT NULL,
    measure_unit_id integer NOT NULL,
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
-- TOC entry 327 (class 1247 OID 25083)
-- Dependencies: 6 1606
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
-- TOC entry 1607 (class 1259 OID 25084)
-- Dependencies: 1927 1928 6
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
-- TOC entry 332 (class 1247 OID 25094)
-- Dependencies: 6 1608
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
-- TOC entry 1645 (class 1259 OID 27708)
-- Dependencies: 6
-- Name: warehouse_products; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE warehouse_products (
    warehouse_product_id integer NOT NULL,
    default_quantity numeric(19,2),
    delivery_time integer,
    maximum_quantity numeric(19,2),
    minimum_quantity numeric(19,2),
    notes character varying(255),
    ordered_quantity numeric(19,2),
    purchase_price numeric(19,2),
    quantity_due numeric(19,2),
    quantity_in_stock numeric(19,2),
    reserved_quantity numeric(19,2),
    sale_price numeric(19,2),
    sold_quantity numeric(19,2),
    warehouse_id numeric(19,2),
    product_id numeric(19,2)
);


--
-- TOC entry 1619 (class 1259 OID 25489)
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
-- TOC entry 1643 (class 1259 OID 27550)
-- Dependencies: 6
-- Name: countries_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE countries_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2347 (class 0 OID 0)
-- Dependencies: 1643
-- Name: countries_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('countries_seq', 9, true);


--
-- TOC entry 1609 (class 1259 OID 25115)
-- Dependencies: 6
-- Name: data_object_type_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE data_object_type_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2348 (class 0 OID 0)
-- Dependencies: 1609
-- Name: data_object_type_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('data_object_type_seq', 60, true);


--
-- TOC entry 1610 (class 1259 OID 25117)
-- Dependencies: 6
-- Name: data_objects_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE data_objects_seq
    INCREMENT BY 1
    MAXVALUE 999999999999999999
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2349 (class 0 OID 0)
-- Dependencies: 1610
-- Name: data_objects_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('data_objects_seq', 2, true);


--
-- TOC entry 1611 (class 1259 OID 25119)
-- Dependencies: 6
-- Name: enum_classes_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE enum_classes_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2350 (class 0 OID 0)
-- Dependencies: 1611
-- Name: enum_classes_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('enum_classes_seq', 15, true);


--
-- TOC entry 1612 (class 1259 OID 25121)
-- Dependencies: 6
-- Name: pattern_mask_formats_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE pattern_mask_formats_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2351 (class 0 OID 0)
-- Dependencies: 1612
-- Name: pattern_mask_formats_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('pattern_mask_formats_seq', 58, true);


--
-- TOC entry 1613 (class 1259 OID 25123)
-- Dependencies: 6
-- Name: resource_bundle_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE resource_bundle_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2352 (class 0 OID 0)
-- Dependencies: 1613
-- Name: resource_bundle_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('resource_bundle_seq', 60, true);


--
-- TOC entry 1614 (class 1259 OID 25125)
-- Dependencies: 6 1605
-- Name: sequence_identifiers_seq_id_key_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE sequence_identifiers_seq_id_key_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2353 (class 0 OID 0)
-- Dependencies: 1614
-- Name: sequence_identifiers_seq_id_key_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE sequence_identifiers_seq_id_key_seq OWNED BY sequence_identifiers.seq_id_key;


--
-- TOC entry 2354 (class 0 OID 0)
-- Dependencies: 1614
-- Name: sequence_identifiers_seq_id_key_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('sequence_identifiers_seq_id_key_seq', 1, false);


--
-- TOC entry 1644 (class 1259 OID 27706)
-- Dependencies: 6
-- Name: warehouse_product_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE warehouse_product_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2355 (class 0 OID 0)
-- Dependencies: 1644
-- Name: warehouse_product_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('warehouse_product_seq', 2, true);


--
-- TOC entry 1639 (class 1259 OID 26921)
-- Dependencies: 6
-- Name: xyz_id_sequence; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE xyz_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2356 (class 0 OID 0)
-- Dependencies: 1639
-- Name: xyz_id_sequence; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('xyz_id_sequence', 1, false);


--
-- TOC entry 1926 (class 2604 OID 25336)
-- Dependencies: 1614 1605
-- Name: seq_id_key; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE sequence_identifiers ALTER COLUMN seq_id_key SET DEFAULT nextval('sequence_identifiers_seq_id_key_seq'::regclass);


--
-- TOC entry 2307 (class 0 OID 25369)
-- Dependencies: 1615
-- Data for Name: addresses; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1206860833751, 1206833877407, 'addresstest', NULL, NULL, NULL, NULL, NULL);
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1209222423971, 1209222047860, 'newer', 5, 1209062694832, NULL, NULL, NULL);
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1208375541782, 1208326364860, 'administration address', 6, 1209062694832, NULL, NULL, NULL);
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1209222409189, 1209222047860, 'new', 5, NULL, NULL, NULL, NULL);
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1209393839117, 1209324665385, 'Branch1', NULL, NULL, NULL, NULL, NULL);
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1209396155129, 1209394438429, 'BBBB23', NULL, NULL, NULL, NULL, NULL);
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1209582468591, 1209582459571, 'Junglata', NULL, NULL, NULL, NULL, NULL);
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1206861699860, 1206833877407, 'Branch X', NULL, NULL, NULL, NULL, NULL);
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1206910857538, 1206910833184, 'Domashen', NULL, NULL, NULL, NULL, NULL);


--
-- TOC entry 2327 (class 0 OID 26229)
-- Dependencies: 1635
-- Data for Name: bank_details; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO bank_details (bank_detail_id, parent_id, is_default, currency_id, bank_id, bank_branch_id, bank_account, bank_contact_id, bic, iban, swift_code) VALUES (1209313835354, 1209222423971, true, NULL, 1209222047860, 1209222423971, NULL, NULL, NULL, NULL, NULL);


--
-- TOC entry 2331 (class 0 OID 26923)
-- Dependencies: 1640
-- Data for Name: business_partners; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1206833877407, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1206862211011, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1206910833184, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1208326364860, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1209222047860, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1209324665385, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1209394343621, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1209394438429, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1209582114840, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1209582459571, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1209582555898, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1209585931126, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1209668108613, NULL, NULL);


--
-- TOC entry 2309 (class 0 OID 25397)
-- Dependencies: 1617
-- Data for Name: cities; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO cities (city_id, country_id, city_name, postal_code, city_code, city_phone_code, description) VALUES (1209062694832, 5, 'Sofia', NULL, NULL, NULL, NULL);
INSERT INTO cities (city_id, country_id, city_name, postal_code, city_code, city_phone_code, description) VALUES (1209243602313, 6, 'New York', NULL, NULL, NULL, NULL);


--
-- TOC entry 2293 (class 0 OID 24995)
-- Dependencies: 1591
-- Data for Name: classified_objects; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2294 (class 0 OID 25001)
-- Dependencies: 1592
-- Data for Name: classifier_applied_for_dot; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2295 (class 0 OID 25004)
-- Dependencies: 1593
-- Data for Name: classifier_groups; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2296 (class 0 OID 25011)
-- Dependencies: 1594
-- Data for Name: classifiers; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2326 (class 0 OID 26202)
-- Dependencies: 1634
-- Data for Name: communication_contacts; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO communication_contacts (communication_contact_id, parent_id, communication_type_id, communication_value, contact_person_id) VALUES (1208205209751, 1206861699860, 46, '344649595', NULL);
INSERT INTO communication_contacts (communication_contact_id, parent_id, communication_type_id, communication_value, contact_person_id) VALUES (1208206388985, 1206861699860, 44, '53453', NULL);
INSERT INTO communication_contacts (communication_contact_id, parent_id, communication_type_id, communication_value, contact_person_id) VALUES (1208206890876, 1206861699860, 44, 'a', NULL);
INSERT INTO communication_contacts (communication_contact_id, parent_id, communication_type_id, communication_value, contact_person_id) VALUES (1208207298391, 1206861699860, 47, 'aaap', 1207689167157);
INSERT INTO communication_contacts (communication_contact_id, parent_id, communication_type_id, communication_value, contact_person_id) VALUES (1209310378705, 1209222423971, 46, '65464', NULL);
INSERT INTO communication_contacts (communication_contact_id, parent_id, communication_type_id, communication_value, contact_person_id) VALUES (1209310364376, 1209222423971, 44, '876', NULL);
INSERT INTO communication_contacts (communication_contact_id, parent_id, communication_type_id, communication_value, contact_person_id) VALUES (1209330251297, 1209222409189, 44, '999', NULL);


--
-- TOC entry 2325 (class 0 OID 26159)
-- Dependencies: 1633
-- Data for Name: contact_persons; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO contact_persons (contact_person_id, parent_id, position_type_id, contact_id) VALUES (1207689167157, 1206861699860, 1208929402376, 1206862211011);


--
-- TOC entry 2308 (class 0 OID 25387)
-- Dependencies: 1616
-- Data for Name: countries; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO countries (country_id, country_name, country_code_a2, country_code_a3, country_code_n3, country_phone_code, currency_id, description) VALUES (5, 'Bulgaria', NULL, NULL, NULL, NULL, 49, NULL);
INSERT INTO countries (country_id, country_name, country_code_a2, country_code_a3, country_code_n3, country_phone_code, currency_id, description) VALUES (6, 'United States', NULL, NULL, NULL, NULL, 50, NULL);
INSERT INTO countries (country_id, country_name, country_code_a2, country_code_a3, country_code_n3, country_phone_code, currency_id, description) VALUES (7, '0xgthk9dor', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO countries (country_id, country_name, country_code_a2, country_code_a3, country_code_n3, country_phone_code, currency_id, description) VALUES (8, '9t2hlwya49', NULL, NULL, NULL, NULL, NULL, NULL);


--
-- TOC entry 2310 (class 0 OID 25432)
-- Dependencies: 1618
-- Data for Name: currencies; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2328 (class 0 OID 26260)
-- Dependencies: 1636
-- Data for Name: data_object_details; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2330 (class 0 OID 26333)
-- Dependencies: 1638
-- Data for Name: data_object_links; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2297 (class 0 OID 25017)
-- Dependencies: 1595
-- Data for Name: data_object_types; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) VALUES (36, 'com.cosmos.acacia.crm.data.Product', NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) VALUES (35, 'com.cosmos.acacia.crm.data.ProductCategory', NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) VALUES (1, 'com.cosmos.acacia.crm.data.Organization', NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) VALUES (37, 'com.cosmos.acacia.crm.data.Person', NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) VALUES (40, 'com.cosmos.acacia.crm.data.Address', NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) VALUES (43, 'com.cosmos.acacia.crm.data.ContactPerson', NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) VALUES (45, 'com.cosmos.acacia.crm.data.CommunicationContact', NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) VALUES (46, 'com.cosmos.acacia.crm.data.SimpleProduct', NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) VALUES (47, 'com.cosmos.acacia.crm.data.PositionType', NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) VALUES (53, 'com.cosmos.acacia.crm.data.BankDetail', NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) VALUES (58, 'com.cosmos.acacia.crm.data.Passport', NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) VALUES (59, 'com.cosmos.acacia.crm.data.City', NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) VALUES (60, 'com.cosmos.acacia.crm.data.Warehouse', NULL, NULL, NULL, NULL, NULL);


--
-- TOC entry 2298 (class 0 OID 25023)
-- Dependencies: 1596
-- Data for Name: data_objects; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1207686553813, 2, 43, '2008-04-08 23:29:13.328+03', 0, 0, true, false, false, false, false, 1206861699860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208326364860, 14, 1, '2008-04-16 09:12:44.859+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1206833877407, 5, 37, '2008-03-30 01:37:57.406+02', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208205209751, 1, 45, '2008-04-14 23:33:29.515+03', 0, 0, false, false, false, false, false, 1206861699860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208206388985, 1, 45, '2008-04-14 23:53:08.796+03', 0, 0, false, false, false, false, false, 1206861699860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208206890876, 1, 45, '2008-04-15 00:01:30.703+03', 0, 0, false, false, false, false, false, 1206861699860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208207298391, 2, 45, '2008-04-15 00:08:18.234+03', 0, 0, false, false, false, false, false, 1206861699860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208548536829, 2, 40, '2008-04-18 22:55:36.671+03', 0, 0, true, false, false, false, false, 1208326364860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208531981537, 2, 46, '2008-04-18 18:19:41.53+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1205859120347, 5, 36, '2008-03-18 18:52:00.343+02', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208455865620, 2, 40, '2008-04-17 21:11:05.415+03', 0, 0, true, false, false, false, false, 1208326364860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208547508844, 2, 40, '2008-04-18 22:38:28.703+03', 0, 0, true, false, false, false, false, 1208326364860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1205657267969, 3, 36, '2008-03-16 10:47:47.966+02', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208379025580, 3, 40, '2008-04-16 23:50:25.578+03', 0, 0, true, false, false, false, false, 1208326364860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1203863458084, 10, 36, '2008-02-24 16:30:58.082+02', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1205606553734, 2, 36, '2008-03-15 20:42:33.732+02', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208376714173, 2, 40, '2008-04-16 23:11:54.171+03', 0, 0, true, false, false, false, false, 1208326364860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208376678844, 2, 40, '2008-04-16 23:11:18.328+03', 0, 0, true, false, false, false, false, 1208326364860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (2, 2, 1, '2008-03-15 20:42:33.732+02', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1206892267210, 4, 36, '2008-03-30 18:51:07.209+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1205661120192, 2, 36, '2008-03-16 11:52:00.191+02', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208497403469, 2, 40, '2008-04-18 08:43:23.296+03', 0, 0, true, false, false, false, false, 1208326364860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1205752768447, 2, 37, '2008-03-17 13:19:28.442+02', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1206831789627, 2, 37, '2008-03-30 01:03:09.625+02', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208380237485, 2, 40, '2008-04-17 00:10:37.312+03', 0, 0, true, false, false, false, false, 1208326364860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1206835148766, 2, 37, '2008-03-30 01:59:08.765+02', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1206832902282, 3, 37, '2008-03-30 01:21:42.265+02', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1206831774532, 2, 37, '2008-03-30 01:02:54.515+02', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1206795224376, 2, 37, '2008-03-29 14:53:44.375+02', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1206793024001, 2, 37, '2008-03-29 14:17:04+02', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1206860553969, 1, 40, '2008-03-30 10:02:33.968+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1206860833751, 1, 40, '2008-03-30 10:07:13.75+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1205915841376, 6, 36, '2008-03-19 10:37:21.369+02', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1206910833184, 1, 37, '2008-03-31 00:00:33.108+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1206910857538, 1, 40, '2008-03-31 00:00:57.456+03', 0, 0, false, false, false, false, false, 1206910833184, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208532320320, 2, 46, '2008-04-18 18:25:20.312+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208378949141, 2, 40, '2008-04-16 23:49:08.953+03', 0, 0, true, false, false, false, false, 1208326364860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208381147266, 2, 40, '2008-04-17 00:25:47.093+03', 0, 0, true, false, false, false, false, 1208326364860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208381806860, 2, 40, '2008-04-17 00:36:46.671+03', 0, 0, true, false, false, false, false, 1208326364860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208381851752, 2, 40, '2008-04-17 00:37:31.75+03', 0, 0, true, false, false, false, false, 1208326364860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208532375657, 1, 46, '2008-04-18 18:26:15.647+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208532574240, 2, 46, '2008-04-18 18:29:34.223+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208532404521, 1, 46, '2008-04-18 18:26:44.51+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1206862211011, 2, 37, '2008-03-30 10:30:11.007+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208532445938, 1, 46, '2008-04-18 18:27:25.926+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208532483793, 1, 46, '2008-04-18 18:28:03.779+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208532506415, 1, 46, '2008-04-18 18:28:26.401+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1, 4, 35, '2001-12-23 21:39:53.662522+02', 1, 1, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208523315311, 1, 46, '2008-04-18 15:55:15.31+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208523480799, 2, 46, '2008-04-18 15:58:00.796+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1205859152950, 4, 36, '2008-03-18 18:52:32.947+02', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208532522729, 1, 46, '2008-04-18 18:28:42.713+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208528961652, 2, 46, '2008-04-18 17:29:21.648+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208529136169, 2, 46, '2008-04-18 17:32:16.164+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208531803052, 1, 46, '2008-04-18 18:16:43.046+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208528557550, 2, 46, '2008-04-18 17:22:37.547+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208532601328, 1, 46, '2008-04-18 18:30:01.31+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208532628130, 1, 46, '2008-04-18 18:30:28.111+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208532654307, 1, 46, '2008-04-18 18:30:54.287+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208532716832, 1, 46, '2008-04-18 18:31:56.811+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208548136751, 2, 40, '2008-04-18 22:48:56.593+03', 0, 0, true, false, false, false, false, 1208326364860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208546065735, 2, 40, '2008-04-18 22:14:25.562+03', 0, 0, true, false, false, false, false, 1208326364860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208547740672, 2, 40, '2008-04-18 22:42:20.515+03', 0, 0, true, false, false, false, false, 1208326364860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208548934844, 2, 40, '2008-04-18 23:02:14.625+03', 0, 0, true, false, false, false, false, 1208326364860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208549770047, 2, 40, '2008-04-18 23:16:09.89+03', 0, 0, true, false, false, false, false, 1208326364860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208550093032, 2, 40, '2008-04-18 23:21:32.859+03', 0, 0, true, false, false, false, false, 1208326364860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208105700768, 11, 36, '2008-04-13 19:55:00.767+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208595267969, 2, 47, '2008-04-19 11:54:27.968+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208597113782, 2, 47, '2008-04-19 12:25:13.781+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208597146158, 1, 43, '2008-04-19 12:25:46.14+03', 0, 0, false, false, false, false, false, 1208375541782, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208532361606, 2, 46, '2008-04-18 18:26:01.597+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208532545822, 2, 46, '2008-04-18 18:29:05.806+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1203863479289, 7, 36, '2008-02-24 16:31:19.286+02', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1206861699860, 2, 40, '2008-03-30 10:21:39.687+03', 0, 0, false, false, false, false, false, 1206833877407, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1207689167157, 3, 43, '2008-04-09 00:12:47+03', 0, 0, false, false, false, false, false, 1206861699860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208375541782, 3, 40, '2008-04-16 22:52:21.609+03', 0, 0, false, false, false, false, false, 1208326364860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208640799735, 2, 53, '2008-04-20 00:33:19.562+03', 0, 0, true, false, false, false, false, 1208375541782, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208723817938, 2, 58, '2008-04-20 23:36:57.781+03', 0, 0, false, false, false, false, false, 1206833877407, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209291672387, 1, 45, '2008-04-27 13:21:12.343+03', 0, 0, false, false, false, false, false, 1209291672166, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208775156424, 2, 35, '2008-04-21 13:52:36.42+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208775396075, 3, 35, '2008-04-21 13:56:36.073+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209291716374, 1, 40, '2008-04-27 13:21:56.343+03', 0, 0, false, false, false, false, false, 1209291716279, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209291716610, 1, 45, '2008-04-27 13:21:56.546+03', 0, 0, false, false, false, false, false, 1209291716374, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208927516422, 1, 47, '2008-04-23 08:11:56.421+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208929402376, 2, 47, '2008-04-23 08:43:22.375+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209062694832, 1, 59, '2008-04-24 21:44:54.831+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209222047860, 6, 1, '2008-04-26 18:00:47.843+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209306831803, 2, 37, '2008-04-27 17:33:51.796+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209288962569, 4, 35, '2008-04-27 12:36:02.563+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209243602313, 1, 59, '2008-04-27 00:00:02.312+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209283607767, 2, 43, '2008-04-27 11:06:47.734+03', 0, 0, false, false, false, false, false, 1209222409189, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209306831645, 2, 1, '2008-04-27 17:33:51.64+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209291716437, 3, 37, '2008-04-27 13:21:56.421+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209294750644, 2, 37, '2008-04-27 14:12:30.64+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209292294099, 2, 45, '2008-04-27 13:31:34.015+03', 0, 0, true, false, false, false, false, 1209292293737, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209287328586, 2, 35, '2008-04-27 12:08:48.582+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209287339476, 2, 35, '2008-04-27 12:08:59.471+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209292293737, 2, 40, '2008-04-27 13:31:33.687+03', 0, 0, true, false, false, false, false, 1209292293627, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209292293816, 2, 37, '2008-04-27 13:31:33.796+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209292293627, 2, 1, '2008-04-27 13:31:33.609+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209306676457, 2, 45, '2008-04-27 17:31:16.421+03', 0, 0, true, false, false, false, false, 1209306676111, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209306676111, 2, 40, '2008-04-27 17:31:16.078+03', 0, 0, true, false, false, false, false, 1209306676001, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209291162267, 2, 1, '2008-04-27 13:12:42.265+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208791743432, 8, 35, '2008-04-21 18:29:03.431+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209291672055, 2, 1, '2008-04-27 13:21:12.046+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209306856555, 2, 40, '2008-04-27 17:34:16.328+03', 0, 0, true, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208867479790, 9, 35, '2008-04-22 15:31:19.788+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208867495266, 4, 35, '2008-04-22 15:31:35.263+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209287223010, 4, 35, '2008-04-27 12:07:03.008+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209291672229, 2, 37, '2008-04-27 13:21:12.218+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209291162409, 1, 40, '2008-04-27 13:12:42.375+03', 0, 0, false, false, false, false, false, 1209291162267, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209291343802, 1, 40, '2008-04-27 13:15:43.781+03', 0, 0, false, false, false, false, false, 1209291343692, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209291672166, 1, 40, '2008-04-27 13:21:12.125+03', 0, 0, false, false, false, false, false, 1209291672055, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209291343692, 2, 1, '2008-04-27 13:15:43.687+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209291015157, 2, 1, '2008-04-27 13:10:15.14+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209294699376, 1, 40, '2008-04-27 14:11:39.14+03', 0, 0, false, false, false, false, false, 1209291716279, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209306676190, 2, 37, '2008-04-27 17:31:16.187+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209291716279, 3, 1, '2008-04-27 13:21:56.265+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209294750565, 1, 40, '2008-04-27 14:12:30.546+03', 0, 0, false, false, false, false, false, 1209294750486, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209294750486, 2, 1, '2008-04-27 14:12:30.484+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209296149845, 1, 40, '2008-04-27 14:35:49.812+03', 0, 0, false, false, false, false, false, 1209296149735, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209310605706, 7, 37, '2008-04-27 18:36:45.703+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209296149735, 2, 1, '2008-04-27 14:35:49.734+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209306676001, 2, 1, '2008-04-27 17:31:16+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209291162488, 2, 37, '2008-04-27 13:12:42.484+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209306881994, 2, 40, '2008-04-27 17:34:41.968+03', 0, 0, true, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209306831992, 2, 45, '2008-04-27 17:33:51.984+03', 0, 0, true, false, false, false, false, 1209306831756, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209306831756, 2, 40, '2008-04-27 17:33:51.734+03', 0, 0, true, false, false, false, false, 1209306831645, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209289065622, 3, 35, '2008-04-27 12:37:45.615+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209307753399, 2, 35, '2008-04-27 17:49:13.398+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209309646907, 1, 43, '2008-04-27 18:20:46.531+03', 0, 0, false, false, false, false, false, 1209222423971, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209310364376, 1, 45, '2008-04-27 18:32:44.031+03', 0, 0, false, false, false, false, false, 1209222423971, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209310378705, 1, 45, '2008-04-27 18:32:58.671+03', 0, 0, false, false, false, false, false, 1209222423971, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209296149924, 2, 37, '2008-04-27 14:35:49.921+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209291343866, 2, 37, '2008-04-27 13:15:43.859+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209313405837, 1, 46, '2008-04-27 19:23:25.828+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209313835354, 1, 53, '2008-04-27 19:30:35.343+03', 0, 0, false, false, false, false, false, 1209222423971, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209325233507, 2, 37, '2008-04-27 22:40:33.504+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209325420605, 1, 40, '2008-04-27 22:43:37.547+03', 0, 0, false, false, false, false, false, 1209325233507, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209285840583, 6, 35, '2008-04-27 11:44:00.581+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209325564201, 1, 47, '2008-04-27 22:46:04.197+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209314036230, 4, 58, '2008-04-27 19:33:56.203+03', 0, 0, false, false, false, false, false, 1209310605706, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209325584859, 1, 43, '2008-04-27 22:46:24.41+03', 0, 0, false, false, false, false, false, 1209325420605, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209325676046, 1, 45, '2008-04-27 22:47:55.57+03', 0, 0, false, false, false, false, false, 1209325420605, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209325859137, 1, 53, '2008-04-27 22:50:58.907+03', 0, 0, false, false, false, false, false, 1209325420605, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209325988448, 1, 45, '2008-04-27 22:53:07.352+03', 0, 0, false, false, false, false, false, 1209325420605, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209330251297, 1, 45, '2008-04-28 00:04:11.109+03', 0, 0, false, false, false, false, false, 1209222409189, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209283574907, 4, 37, '2008-04-27 11:06:14.89+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209222409189, 3, 40, '2008-04-26 18:06:49.156+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209324665385, 2, 1, '2008-04-27 22:31:05.383+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209222423971, 7, 40, '2008-04-26 18:07:03.812+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209287255900, 5, 35, '2008-04-27 12:07:35.897+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1206792765907, 4, 37, '2008-03-29 14:12:45.906+02', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208867453036, 10, 35, '2008-04-22 15:30:53.029+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209382379144, 2, 35, '2008-04-28 14:32:59.141+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209382456058, 2, 35, '2008-04-28 14:34:16.054+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209382353703, 2, 35, '2008-04-28 14:32:33.701+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209382331315, 2, 35, '2008-04-28 14:32:11.313+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209382472939, 2, 35, '2008-04-28 14:34:32.934+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209382497917, 2, 35, '2008-04-28 14:34:57.911+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209383710100, 2, 35, '2008-04-28 14:55:10.092+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209383716112, 2, 35, '2008-04-28 14:55:16.104+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209406606190, 2, 37, '2008-04-28 21:16:46.187+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209383775891, 2, 35, '2008-04-28 14:56:15.881+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209383769742, 2, 35, '2008-04-28 14:56:09.733+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209383847565, 2, 35, '2008-04-28 14:57:27.554+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209406603688, 2, 1, '2008-04-28 21:16:43.687+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209393778001, 1, 43, '2008-04-28 17:42:57.962+03', 0, 0, false, false, false, false, false, 1209393762536, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209393839117, 1, 40, '2008-04-28 17:43:59.108+03', 0, 0, false, false, false, false, false, 1209324665385, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209393842895, 2, 58, '2008-04-28 17:44:02.885+03', 0, 0, true, false, false, false, false, 1209393667067, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209393762536, 3, 40, '2008-04-28 17:42:42.499+03', 0, 0, true, false, false, false, false, 1209393667067, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209393898024, 2, 37, '2008-04-28 17:44:58.014+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209393978637, 3, 37, '2008-04-28 17:46:18.626+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209394343621, 2, 1, '2008-04-28 17:52:23.609+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209396155129, 1, 40, '2008-04-28 18:22:34.925+03', 0, 0, false, false, false, false, false, 1209394438429, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209585016376, 2, 37, '2008-04-30 22:50:16.375+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209582346354, 2, 37, '2008-04-30 22:05:46.35+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209396227704, 2, 58, '2008-04-28 18:23:47.546+03', 0, 0, true, false, false, false, false, 1209393667067, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209396590448, 1, 43, '2008-04-28 18:29:50.413+03', 0, 0, false, false, false, false, false, 1209396580958, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209582523616, 3, 37, '2008-04-30 22:08:43.608+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209396580958, 2, 40, '2008-04-28 18:29:40.955+03', 0, 0, true, false, false, false, false, 1209393667067, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209585311094, 2, 37, '2008-04-30 22:55:11.093+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209408906002, 1, 59, '2008-04-28 21:55:06+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209383949927, 2, 35, '2008-04-28 14:59:09.915+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209393667067, 4, 37, '2008-04-28 17:41:07.046+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209585931126, 2, 1, '2008-04-30 23:05:31.125+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209582053793, 2, 37, '2008-04-30 22:00:53.791+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209406608441, 2, 45, '2008-04-28 21:16:48.39+03', 0, 0, true, false, false, false, false, 1209406604986, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209406604986, 2, 40, '2008-04-28 21:16:44.781+03', 0, 0, true, false, false, false, false, 1209406603688, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209582468591, 1, 40, '2008-04-30 22:07:48.564+03', 0, 0, false, false, false, false, false, 1209582459571, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209582459571, 2, 37, '2008-04-30 22:07:39.566+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209582511777, 2, 37, '2008-04-30 22:08:31.77+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209582530906, 1, 40, '2008-04-30 22:08:50.888+03', 0, 0, false, false, false, false, false, 1209582523616, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209668478266, 2, 37, '2008-05-01 22:01:18.265+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209582555898, 2, 37, '2008-04-30 22:09:15.887+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209394438429, 3, 1, '2008-04-28 17:53:58.416+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209407366014, 3, 35, '2008-04-28 21:29:26.009+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209642658844, 2, 37, '2008-05-01 14:50:58.843+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209639570926, 2, 46, '2008-05-01 13:59:30.925+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209639591779, 2, 35, '2008-05-01 13:59:51.777+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209639591826, 2, 35, '2008-05-01 13:59:51.823+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209407337166, 3, 35, '2008-04-28 21:28:57.162+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209583403342, 4, 35, '2008-04-30 22:23:23.33+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209639592089, 2, 35, '2008-05-01 13:59:52.085+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209583631365, 2, 35, '2008-04-30 22:27:11.352+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209407327164, 4, 35, '2008-04-28 21:28:47.161+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209583256051, 3, 35, '2008-04-30 22:20:56.04+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209403563985, 5, 35, '2008-04-28 20:26:03.984+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209407225556, 3, 35, '2008-04-28 21:27:05.555+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209642675564, 2, 37, '2008-05-01 14:51:15.562+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209407251282, 5, 35, '2008-04-28 21:27:31.28+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209668486845, 2, 37, '2008-05-01 22:01:26.843+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209585033361, 2, 37, '2008-04-30 22:50:33.359+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209642901797, 2, 37, '2008-05-01 14:55:01.796+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209582114840, 4, 1, '2008-04-30 22:01:54.837+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209584841985, 4, 37, '2008-04-30 22:47:21.984+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209667724938, 2, 37, '2008-05-01 21:48:44.937+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209668002720, 2, 37, '2008-05-01 21:53:22.718+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210027840944, 1, 46, '2008-05-06 01:50:40.941+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209668108613, 1, 37, '2008-05-01 21:55:08.609+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209668497503, 2, 37, '2008-05-01 22:01:37.50+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209669000469, 2, 37, '2008-05-01 22:10:00.453+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209669503329, 2, 37, '2008-05-01 22:18:23.328+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209670535657, 2, 37, '2008-05-01 22:35:35.656+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209670543580, 2, 37, '2008-05-01 22:35:43.578+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209671274641, 2, 37, '2008-05-01 22:47:54.625+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209931667245, 1, 60, '2008-05-04 23:07:47.241+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209668088034, 3, 37, '2008-05-01 21:54:48.031+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209930745031, 2, 60, '2008-05-04 22:52:25.029+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);


--
-- TOC entry 2319 (class 0 OID 25997)
-- Dependencies: 1627
-- Data for Name: delivery_certificate_items; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2320 (class 0 OID 26022)
-- Dependencies: 1628
-- Data for Name: delivery_certificate_serial_numbers; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2318 (class 0 OID 25962)
-- Dependencies: 1626
-- Data for Name: delivery_certificates; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2299 (class 0 OID 25035)
-- Dependencies: 1597
-- Data for Name: enum_classes; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO enum_classes (enum_class_id, enum_class_name) VALUES (1, 'com.cosmos.acacia.crm.enums.MeasurementUnit');
INSERT INTO enum_classes (enum_class_id, enum_class_name) VALUES (2, 'com.cosmos.acacia.crm.enums.Gender');
INSERT INTO enum_classes (enum_class_id, enum_class_name) VALUES (3, 'com.cosmos.acacia.crm.enums.OrganizationType');
INSERT INTO enum_classes (enum_class_id, enum_class_name) VALUES (4, 'com.cosmos.acacia.crm.enums.ProductColor');
INSERT INTO enum_classes (enum_class_id, enum_class_name) VALUES (5, 'com.cosmos.acacia.crm.enums.CommunicationType');
INSERT INTO enum_classes (enum_class_id, enum_class_name) VALUES (6, 'com.cosmos.acacia.crm.enums.Currency');
INSERT INTO enum_classes (enum_class_id, enum_class_name) VALUES (7, 'com.cosmos.acacia.crm.enums.DeliveryType');
INSERT INTO enum_classes (enum_class_id, enum_class_name) VALUES (8, 'com.cosmos.acacia.crm.enums.DocumentDeliveryMethod');
INSERT INTO enum_classes (enum_class_id, enum_class_name) VALUES (9, 'com.cosmos.acacia.crm.enums.InvoiceStatus');
INSERT INTO enum_classes (enum_class_id, enum_class_name) VALUES (10, 'com.cosmos.acacia.crm.enums.InvoiceType');
INSERT INTO enum_classes (enum_class_id, enum_class_name) VALUES (11, 'com.cosmos.acacia.crm.enums.PaymentTerm');
INSERT INTO enum_classes (enum_class_id, enum_class_name) VALUES (12, 'com.cosmos.acacia.crm.enums.PaymentType');
INSERT INTO enum_classes (enum_class_id, enum_class_name) VALUES (13, 'com.cosmos.acacia.crm.enums.TransportationMethod');
INSERT INTO enum_classes (enum_class_id, enum_class_name) VALUES (14, 'com.cosmos.acacia.crm.enums.VatCondition');
INSERT INTO enum_classes (enum_class_id, enum_class_name) VALUES (15, 'com.cosmos.acacia.crm.enums.PassportType');


--
-- TOC entry 2313 (class 0 OID 25718)
-- Dependencies: 1621
-- Data for Name: invoice_items; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2312 (class 0 OID 25611)
-- Dependencies: 1620
-- Data for Name: invoices; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2317 (class 0 OID 25939)
-- Dependencies: 1625
-- Data for Name: order_confirmation_items; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2316 (class 0 OID 25909)
-- Dependencies: 1624
-- Data for Name: order_confirmations; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2332 (class 0 OID 26928)
-- Dependencies: 1641
-- Data for Name: organizations; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id) VALUES (1208326364860, NULL, 'a', 'aa', '2008-04-16', 5.00, '65464564', '5454654', 21, NULL, NULL, NULL, 48);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id) VALUES (1209222047860, NULL, NULL, 'oooooo', '2008-04-09', 9.00, NULL, '99', 22, NULL, 1208326364860.00, NULL, 48);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id) VALUES (1209394343621, NULL, 'op', 'MicrosoftsdfsdfMicrosoftsdfsdfMicrosoftsdfsdfMicrosoftsdfsdfMicrosoftsdfsdfMicrosoftsdfsdfMicrosoftsdfsdfMicrosoftsdfsdfMicrosoftsdfsdfMicrosoftsdfsdfMicrosoftsdfsdfMicrosoftsdfsdf', '2008-04-13', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id) VALUES (1209394438429, 'sdfcsdfsd', 'IBM inc', 'IBM', '2008-04-18', 3000000000.00, NULL, '32423453464erg', 28, NULL, NULL, NULL, 48);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id) VALUES (1209585931126, NULL, 'Civic', 'Honda', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id) VALUES (1209324665385, NULL, NULL, 'dfsdfsdf', NULL, NULL, NULL, NULL, NULL, NULL, 1209324665385.00, NULL, NULL);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id) VALUES (1209582114840, NULL, NULL, 'Yamaha', '2008-04-25', NULL, NULL, NULL, NULL, NULL, 1209394438429.00, NULL, NULL);


--
-- TOC entry 2329 (class 0 OID 26275)
-- Dependencies: 1637
-- Data for Name: passports; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO passports (passport_id, parent_id, passport_type_id, passport_number, issue_date, expiration_date, issuer_id, issuer_branch_id, additional_info) VALUES (1208723817938, 1206833877407, 59, '4532', '2008-04-20', '2008-04-18', 1208326364860, 1208375541782, 'ja');


--
-- TOC entry 2300 (class 0 OID 25041)
-- Dependencies: 1599
-- Data for Name: pattern_mask_formats; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO pattern_mask_formats (pattern_mask_format_id, pattern_name, format_type, format, description, owner_id) VALUES (27, 'dupi', '-', '###234', 'sdfasdf', NULL);
INSERT INTO pattern_mask_formats (pattern_mask_format_id, pattern_name, format_type, format, description, owner_id) VALUES (28, '3245234', '-', '51613461', '46346134t	rg', 1206910833184.00);
INSERT INTO pattern_mask_formats (pattern_mask_format_id, pattern_name, format_type, format, description, owner_id) VALUES (48, 'NewFormat', '-', '###-002', NULL, NULL);
INSERT INTO pattern_mask_formats (pattern_mask_format_id, pattern_name, format_type, format, description, owner_id) VALUES (47, 'formatXXX', '-', '--- [234234] - //&&', NULL, NULL);
INSERT INTO pattern_mask_formats (pattern_mask_format_id, pattern_name, format_type, format, description, owner_id) VALUES (33, 'qertqer', '-', '##-# (##)', 'rgq adfg adfgadfg adfg', 1206833877407.00);
INSERT INTO pattern_mask_formats (pattern_mask_format_id, pattern_name, format_type, format, description, owner_id) VALUES (34, 'qwer', '-', '###-#00', 'sdfasdfasdf', 1206833877407.00);
INSERT INTO pattern_mask_formats (pattern_mask_format_id, pattern_name, format_type, format, description, owner_id) VALUES (36, 'werttt', '-', '#-###-###-##', 'werer', 1206833877407.00);
INSERT INTO pattern_mask_formats (pattern_mask_format_id, pattern_name, format_type, format, description, owner_id) VALUES (50, 'General', '-', '#-#-#-#-#', NULL, 1206833877407.00);
INSERT INTO pattern_mask_formats (pattern_mask_format_id, pattern_name, format_type, format, description, owner_id) VALUES (55, 'Registration Plate BG', '-', '## #### ##', NULL, NULL);
INSERT INTO pattern_mask_formats (pattern_mask_format_id, pattern_name, format_type, format, description, owner_id) VALUES (30, 'qwerw', '-', '---#', NULL, 1206862211011.00);
INSERT INTO pattern_mask_formats (pattern_mask_format_id, pattern_name, format_type, format, description, owner_id) VALUES (56, 'Mark II', '-', 'A-### mark II', NULL, NULL);
INSERT INTO pattern_mask_formats (pattern_mask_format_id, pattern_name, format_type, format, description, owner_id) VALUES (38, 'asdasda', '-', 'dasdasdasdasd###################################################################################################################', 'dsfdf', NULL);
INSERT INTO pattern_mask_formats (pattern_mask_format_id, pattern_name, format_type, format, description, owner_id) VALUES (43, '7567567567', '-', '56456', NULL, NULL);
INSERT INTO pattern_mask_formats (pattern_mask_format_id, pattern_name, format_type, format, description, owner_id) VALUES (49, 'Spec', '-', '[#### ####]', NULL, NULL);
INSERT INTO pattern_mask_formats (pattern_mask_format_id, pattern_name, format_type, format, description, owner_id) VALUES (44, 'YUIYUIY', '-', '#', NULL, 1206833877407.00);


--
-- TOC entry 2333 (class 0 OID 26936)
-- Dependencies: 1642
-- Data for Name: persons; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1206910833184, NULL, NULL, NULL, 'Силвия', 'Начева', NULL, NULL, NULL, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1206862211011, NULL, NULL, NULL, 'Miroslav', 'Nachev', NULL, NULL, 19, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1206833877407, NULL, NULL, NULL, 'aborigen', 'aborigenov', NULL, NULL, 19, 1209062694832, 5);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1209582459571, '2008-04-26', 'Golema maimuna', 'The King', 'King', 'Kong', 'kk1', 'Monkey', 19, 1209062694832, 5);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1209582555898, NULL, NULL, NULL, 'KKO', 'LPPP', NULL, NULL, NULL, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1209668108613, NULL, NULL, NULL, 'fdsadfsa', 'fdsafadfafs', NULL, NULL, NULL, NULL, NULL);


--
-- TOC entry 2324 (class 0 OID 26146)
-- Dependencies: 1632
-- Data for Name: position_types; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO position_types (position_type_id, parent_id, owner_type, position_type_name, description) VALUES (1208595267969, NULL, 'O', 'Boss', NULL);
INSERT INTO position_types (position_type_id, parent_id, owner_type, position_type_name, description) VALUES (1208597113782, NULL, 'O', 'Cleaner', NULL);
INSERT INTO position_types (position_type_id, parent_id, owner_type, position_type_name, description) VALUES (1208927516422, NULL, 'P', 'Brother', NULL);
INSERT INTO position_types (position_type_id, parent_id, owner_type, position_type_name, description) VALUES (1208929402376, NULL, 'P', 'Distant Cousin', NULL);
INSERT INTO position_types (position_type_id, parent_id, owner_type, position_type_name, description) VALUES (1209325564201, NULL, 'P', 'Баща', NULL);


--
-- TOC entry 2301 (class 0 OID 25047)
-- Dependencies: 1600
-- Data for Name: product_categories; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO product_categories (product_category_id, parent_id, category_name, pattern_mask_format_id, description, parent_cat_id) VALUES (1209407225556, NULL, 'CatX', 56, 'Haho hihie
obalaa', 1208867495266.00);
INSERT INTO product_categories (product_category_id, parent_id, category_name, pattern_mask_format_id, description, parent_cat_id) VALUES (1209288962569, NULL, 'Cat007', NULL, NULL, NULL);
INSERT INTO product_categories (product_category_id, parent_id, category_name, pattern_mask_format_id, description, parent_cat_id) VALUES (1209403563985, NULL, 'NewWPattern', NULL, NULL, 1209407225556.00);
INSERT INTO product_categories (product_category_id, parent_id, category_name, pattern_mask_format_id, description, parent_cat_id) VALUES (1208791743432, NULL, 'Cat 11y', 30, NULL, 1209407225556.00);
INSERT INTO product_categories (product_category_id, parent_id, category_name, pattern_mask_format_id, description, parent_cat_id) VALUES (1208867495266, NULL, '2nd Product Categoryy', NULL, NULL, NULL);
INSERT INTO product_categories (product_category_id, parent_id, category_name, pattern_mask_format_id, description, parent_cat_id) VALUES (1, NULL, '1st Category', NULL, 'sdfsdfsdfsdfwersdfsdfsdfsdfsdf', 1208867495266.00);
INSERT INTO product_categories (product_category_id, parent_id, category_name, pattern_mask_format_id, description, parent_cat_id) VALUES (1209407251282, NULL, 'Food And Drinks', NULL, NULL, 1209407225556.00);


--
-- TOC entry 2302 (class 0 OID 25053)
-- Dependencies: 1601
-- Data for Name: product_suppliers; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2335 (class 0 OID 27726)
-- Dependencies: 1646
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO products (product_id, parent_id, product_type) VALUES (1205657267969, NULL, NULL);
INSERT INTO products (product_id, parent_id, product_type) VALUES (1208532375657, NULL, NULL);
INSERT INTO products (product_id, parent_id, product_type) VALUES (1208523315311, NULL, NULL);
INSERT INTO products (product_id, parent_id, product_type) VALUES (1208532404521, NULL, NULL);
INSERT INTO products (product_id, parent_id, product_type) VALUES (1208532445938, NULL, NULL);
INSERT INTO products (product_id, parent_id, product_type) VALUES (1203863479289, NULL, NULL);
INSERT INTO products (product_id, parent_id, product_type) VALUES (1203863458084, NULL, NULL);
INSERT INTO products (product_id, parent_id, product_type) VALUES (1208532483793, NULL, NULL);
INSERT INTO products (product_id, parent_id, product_type) VALUES (1208532506415, NULL, NULL);
INSERT INTO products (product_id, parent_id, product_type) VALUES (1208531803052, NULL, NULL);
INSERT INTO products (product_id, parent_id, product_type) VALUES (1208532522729, NULL, NULL);
INSERT INTO products (product_id, parent_id, product_type) VALUES (1208532601328, NULL, NULL);
INSERT INTO products (product_id, parent_id, product_type) VALUES (1208531981537, NULL, NULL);
INSERT INTO products (product_id, parent_id, product_type) VALUES (1208532628130, NULL, NULL);
INSERT INTO products (product_id, parent_id, product_type) VALUES (1208532654307, NULL, NULL);
INSERT INTO products (product_id, parent_id, product_type) VALUES (1208532716832, NULL, NULL);
INSERT INTO products (product_id, parent_id, product_type) VALUES (1208532361606, NULL, NULL);
INSERT INTO products (product_id, parent_id, product_type) VALUES (1208532545822, NULL, NULL);
INSERT INTO products (product_id, parent_id, product_type) VALUES (1208532574240, NULL, NULL);
INSERT INTO products (product_id, parent_id, product_type) VALUES (1209313405837, NULL, NULL);
INSERT INTO products (product_id, parent_id, product_type) VALUES (1205915841376, NULL, NULL);
INSERT INTO products (product_id, parent_id, product_type) VALUES (1208105700768, NULL, NULL);
INSERT INTO products (product_id, parent_id, product_type) VALUES (1210027840944, NULL, NULL);


--
-- TOC entry 2315 (class 0 OID 25851)
-- Dependencies: 1623
-- Data for Name: purchase_order_items; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2314 (class 0 OID 25808)
-- Dependencies: 1622
-- Data for Name: purchase_orders; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2322 (class 0 OID 26074)
-- Dependencies: 1630
-- Data for Name: receipt_certificate_items; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2323 (class 0 OID 26099)
-- Dependencies: 1631
-- Data for Name: receipt_certificate_serial_numbers; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2321 (class 0 OID 26037)
-- Dependencies: 1629
-- Data for Name: receipt_certificates; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2304 (class 0 OID 25074)
-- Dependencies: 1604
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
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (44, 5, 'Phone');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (45, 5, 'Mobile');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (46, 5, 'ICQ');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (47, 5, 'Skype');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (48, 6, 'Euro');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (49, 6, 'Leva');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (50, 6, 'Dollar');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (59, 15, 'National');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (60, 15, 'International');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (19, 2, 'Male');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (20, 2, 'Female');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (21, 3, 'NationalAgency');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (22, 3, 'Agency');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (23, 3, 'PrivateLimitedCompany');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (24, 3, 'PublicLimitedCompany');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (25, 3, 'CompanyWithLimitedLiability');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (26, 3, 'StockCorporation');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (27, 3, 'Incorporated');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (28, 3, 'Corporation');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (29, 3, 'SoleTrader');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (30, 3, 'GovernmentOrganization');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (31, 3, 'MunicipalOrganization');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (32, 3, 'GeneralPartnership');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (33, 3, 'LimitedPartnership');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (34, 3, 'SingleMemberLimitedLiabilityCompany');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (35, 3, 'LimitedLiabilityCompany');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (36, 3, 'JointStockCompany');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (37, 3, 'SingleShareholderJointStockCompany');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (38, 3, 'PartnershipLimited');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (39, 4, 'BlackDesktopComputer');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (40, 4, 'SilverDesktopComputer');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (41, 4, 'BlackMobileComputer');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (42, 4, 'SilverMobileComputer');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (43, 4, 'BlackServerComputer');


--
-- TOC entry 2305 (class 0 OID 25077)
-- Dependencies: 1605
-- Data for Name: sequence_identifiers; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2303 (class 0 OID 25059)
-- Dependencies: 1602
-- Data for Name: simple_products; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, pattern_mask_format_id, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id) VALUES (1205657267969, 1, 'Nescafe 2in1', 'E1400', 1, false, true, false, 48, NULL, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, pattern_mask_format_id, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id) VALUES (1208532375657, 1, 'Monitor', '67567567', 1, false, true, false, 33, NULL, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, pattern_mask_format_id, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id) VALUES (1208523315311, 1, 'Memory Stick', '555003', 1, false, true, false, 50, 39, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, pattern_mask_format_id, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id) VALUES (1208532404521, 1, 'Calculator', 'yuiyu-=pui3ry3p8[', 1, false, true, false, 49, NULL, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, pattern_mask_format_id, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id) VALUES (1208532445938, 1, 'Door with handle', 'ghjgk;opo[p][gf', 1, false, true, false, 50, NULL, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, pattern_mask_format_id, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id) VALUES (1203863479289, 1, 'TV Tuner', 'pc2', 2, false, true, false, 30, 40, 1.0000, 2.5460, 2.3230, 100.2000, 200.0000, 250.0000, 1, NULL, 7.55, 7.87, 34.43, NULL, 40.000, 3, 'blabla', NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, pattern_mask_format_id, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id) VALUES (1203863458084, 1, '3in1', '66799033312', 1, false, true, false, 36, NULL, 1.0000, 3.0000, 2.0000, 100.2000, 200.0000, 250.0000, 1, 16, 1.20, 2.30, 3.50, 8, 23.450, 12, 'My description', NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, pattern_mask_format_id, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id) VALUES (1208532483793, 1, 'Toilet paper', 'oooops', 1, false, true, false, 44, NULL, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, pattern_mask_format_id, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id) VALUES (1208532506415, 1, 'Shoes', '567567567', 1, false, true, false, 36, NULL, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, pattern_mask_format_id, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id) VALUES (1208531803052, 1, 'Amplifier, Brand: Pioneer, Model: A 656 Mark II', '000099083411445', 1, false, true, false, 36, 39, 1.0000, NULL, NULL, 300.0000, 270.0000, 240.0000, 1, 14, 50.00, 63.23, 14.43, 8, 16.000, 3, 'Very powerful', NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, pattern_mask_format_id, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id) VALUES (1208532522729, 1, 'Desk', 'uiuiu454545', 1, false, true, false, 33, NULL, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, pattern_mask_format_id, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id) VALUES (1208532601328, 1, 'Windows 98', 'bugggggsss', 1, false, true, false, 44, NULL, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, pattern_mask_format_id, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id) VALUES (1208531981537, 1, 'Pizza, with onion', '01', 1, false, false, false, 34, 40, 10.0000, 123123123124.0000, 20.0000, 423423423423.0000, 0.2342, 454545454545.0000, 1, 18, NULL, NULL, NULL, NULL, 8090909090.000, 453434534, 'PizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizza', 1208326364860);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, pattern_mask_format_id, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id) VALUES (1208532628130, 1, 'Camera Web Small', '0093343--3434', 1, false, true, false, 44, NULL, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, pattern_mask_format_id, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id) VALUES (1208532654307, 1, 'Plant Artificial', '99223232', 1, false, true, false, 43, NULL, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, pattern_mask_format_id, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id) VALUES (1208532716832, 1, 'Car, Opel Astra 85kW', 'CA3434CA', 1, false, true, false, 55, NULL, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, pattern_mask_format_id, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id) VALUES (1208532361606, 1, 'Laptop', '456456456', 1, false, true, false, 50, 39, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, 16, NULL, NULL, NULL, 8, NULL, NULL, NULL, 1206833877407);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, pattern_mask_format_id, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id) VALUES (1208532545822, 1, 'Box of wood', '988 dfdff 09809', 1, false, true, false, 36, 40, 234523453453.0000, 999999999999.0000, 456456456456.0000, 345435.0000, 3456345.0000, 999999999999.0000, 345345, 16, 99999.00, 34535.00, 345.00, 7, 345345.000, 345, 'afjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjh', 1206862211011);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, pattern_mask_format_id, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id) VALUES (1208532574240, 1, 'Cables high quality', '0093434', 1, false, true, false, 30, NULL, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, pattern_mask_format_id, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id) VALUES (1209313405837, 1, 'testpc', '3453', 1, true, true, false, 43, NULL, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, pattern_mask_format_id, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id) VALUES (1205915841376, 1208867495266, 'Memory Card', 'p38', 1, false, true, false, 49, NULL, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, pattern_mask_format_id, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id) VALUES (1208105700768, 1209403563985, 'TestovProduct1', '22334455', 1, true, true, true, 33, 39, 999999999999.0000, 999999999999.0000, 999999999999.0000, 999999999999.0000, 999999999999.0000, 999999999999.0000, 999999999, 16, 99999.00, 99999.00, 99999.00, 9, 9999999999.000, 999999999, 'Very heavy, be careful', 1206833877407);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, pattern_mask_format_id, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id) VALUES (1210027840944, 1208867495266, 'm1', 'm1', 1, false, true, false, 55, NULL, 1.0000, NULL, NULL, 100.0000, 100.0000, 100.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);


--
-- TOC entry 2306 (class 0 OID 25084)
-- Dependencies: 1607
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2334 (class 0 OID 27708)
-- Dependencies: 1645
-- Data for Name: warehouse_products; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO warehouse_products (warehouse_product_id, default_quantity, delivery_time, maximum_quantity, minimum_quantity, notes, ordered_quantity, purchase_price, quantity_due, quantity_in_stock, reserved_quantity, sale_price, sold_quantity, warehouse_id, product_id) VALUES (2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0.00, NULL, NULL, NULL, NULL, NULL);


--
-- TOC entry 2311 (class 0 OID 25489)
-- Dependencies: 1619
-- Data for Name: warehouses; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO warehouses (warehouse_id, parent_id, address_id, warehouseman_id, description) VALUES (1209931667245, NULL, 1206910857538, 1209582555898, 'DescriptionX
');


--
-- TOC entry 2050 (class 2606 OID 27095)
-- Dependencies: 1640 1640
-- Name: business_partners_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY business_partners
    ADD CONSTRAINT business_partners_pkey PRIMARY KEY (partner_id);


--
-- TOC entry 2052 (class 2606 OID 27507)
-- Dependencies: 1641 1641
-- Name: organizations_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT organizations_pkey PRIMARY KEY (organization_id);


--
-- TOC entry 2054 (class 2606 OID 27147)
-- Dependencies: 1642 1642
-- Name: persons_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT persons_pkey PRIMARY KEY (partner_id);


--
-- TOC entry 1979 (class 2606 OID 25376)
-- Dependencies: 1615 1615
-- Name: pk_addresses; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT pk_addresses PRIMARY KEY (address_id);


--
-- TOC entry 2036 (class 2606 OID 26234)
-- Dependencies: 1635 1635
-- Name: pk_bank_details; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT pk_bank_details PRIMARY KEY (bank_detail_id);


--
-- TOC entry 1985 (class 2606 OID 27353)
-- Dependencies: 1617 1617
-- Name: pk_cities; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY cities
    ADD CONSTRAINT pk_cities PRIMARY KEY (city_id);


--
-- TOC entry 1933 (class 2606 OID 25129)
-- Dependencies: 1591 1591 1591
-- Name: pk_classified_objects; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classified_objects
    ADD CONSTRAINT pk_classified_objects PRIMARY KEY (classifier_id, classified_object_id);


--
-- TOC entry 1935 (class 2606 OID 25131)
-- Dependencies: 1592 1592 1592
-- Name: pk_classifier_applied_for_dot; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifier_applied_for_dot
    ADD CONSTRAINT pk_classifier_applied_for_dot PRIMARY KEY (classifier_id, data_object_type_id);


--
-- TOC entry 1937 (class 2606 OID 25133)
-- Dependencies: 1593 1593
-- Name: pk_classifier_groups; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifier_groups
    ADD CONSTRAINT pk_classifier_groups PRIMARY KEY (classifier_group_id);


--
-- TOC entry 1943 (class 2606 OID 25135)
-- Dependencies: 1594 1594
-- Name: pk_classifiers; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT pk_classifiers PRIMARY KEY (classifier_id);


--
-- TOC entry 2032 (class 2606 OID 26206)
-- Dependencies: 1634 1634
-- Name: pk_communication_contacts; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT pk_communication_contacts PRIMARY KEY (communication_contact_id);


--
-- TOC entry 2029 (class 2606 OID 26163)
-- Dependencies: 1633 1633
-- Name: pk_contact_persons; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT pk_contact_persons PRIMARY KEY (contact_person_id);


--
-- TOC entry 1981 (class 2606 OID 25394)
-- Dependencies: 1616 1616
-- Name: pk_countries; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY countries
    ADD CONSTRAINT pk_countries PRIMARY KEY (country_id);


--
-- TOC entry 1989 (class 2606 OID 25439)
-- Dependencies: 1618 1618
-- Name: pk_currencies; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY currencies
    ADD CONSTRAINT pk_currencies PRIMARY KEY (currency_id);


--
-- TOC entry 2038 (class 2606 OID 26267)
-- Dependencies: 1636 1636
-- Name: pk_data_object_details; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_details
    ADD CONSTRAINT pk_data_object_details PRIMARY KEY (data_object_id);


--
-- TOC entry 2046 (class 2606 OID 26337)
-- Dependencies: 1638 1638
-- Name: pk_data_object_links; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_links
    ADD CONSTRAINT pk_data_object_links PRIMARY KEY (data_object_link_id);


--
-- TOC entry 1949 (class 2606 OID 25137)
-- Dependencies: 1595 1595
-- Name: pk_data_object_types; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_types
    ADD CONSTRAINT pk_data_object_types PRIMARY KEY (data_object_type_id);


--
-- TOC entry 1953 (class 2606 OID 25139)
-- Dependencies: 1596 1596
-- Name: pk_data_objects; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT pk_data_objects PRIMARY KEY (data_object_id);


--
-- TOC entry 2015 (class 2606 OID 26001)
-- Dependencies: 1627 1627
-- Name: pk_delivery_certificate_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT pk_delivery_certificate_items PRIMARY KEY (certificate_item_id);


--
-- TOC entry 2017 (class 2606 OID 26026)
-- Dependencies: 1628 1628 1628
-- Name: pk_delivery_certificate_serial_numbers; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY delivery_certificate_serial_numbers
    ADD CONSTRAINT pk_delivery_certificate_serial_numbers PRIMARY KEY (certificate_item_id, serial_number);


--
-- TOC entry 2011 (class 2606 OID 25966)
-- Dependencies: 1626 1626
-- Name: pk_delivery_certificates; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT pk_delivery_certificates PRIMARY KEY (delivery_certificate_id);


--
-- TOC entry 1955 (class 2606 OID 25141)
-- Dependencies: 1597 1597
-- Name: pk_enum_classes; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY enum_classes
    ADD CONSTRAINT pk_enum_classes PRIMARY KEY (enum_class_id);


--
-- TOC entry 1999 (class 2606 OID 25725)
-- Dependencies: 1621 1621
-- Name: pk_invoice_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY invoice_items
    ADD CONSTRAINT pk_invoice_items PRIMARY KEY (invoice_item_id);


--
-- TOC entry 1995 (class 2606 OID 25615)
-- Dependencies: 1620 1620
-- Name: pk_invoices; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT pk_invoices PRIMARY KEY (invoice_id);


--
-- TOC entry 2009 (class 2606 OID 25946)
-- Dependencies: 1625 1625
-- Name: pk_order_confirmation_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY order_confirmation_items
    ADD CONSTRAINT pk_order_confirmation_items PRIMARY KEY (confirmation_item_id);


--
-- TOC entry 2007 (class 2606 OID 25913)
-- Dependencies: 1624 1624
-- Name: pk_order_confirmations; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT pk_order_confirmations PRIMARY KEY (order_confirmation_id);


--
-- TOC entry 2042 (class 2606 OID 26299)
-- Dependencies: 1637 1637
-- Name: pk_passports; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT pk_passports PRIMARY KEY (passport_id);


--
-- TOC entry 1959 (class 2606 OID 25143)
-- Dependencies: 1599 1599
-- Name: pk_pattern_mask_formats; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pattern_mask_formats
    ADD CONSTRAINT pk_pattern_mask_formats PRIMARY KEY (pattern_mask_format_id);


--
-- TOC entry 2027 (class 2606 OID 26153)
-- Dependencies: 1632 1632
-- Name: pk_position_types; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY position_types
    ADD CONSTRAINT pk_position_types PRIMARY KEY (position_type_id);


--
-- TOC entry 1961 (class 2606 OID 25145)
-- Dependencies: 1600 1600
-- Name: pk_product_categories; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT pk_product_categories PRIMARY KEY (product_category_id);


--
-- TOC entry 1965 (class 2606 OID 25147)
-- Dependencies: 1601 1601 1601
-- Name: pk_product_suppliers; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT pk_product_suppliers PRIMARY KEY (product_id, supplier_id);


--
-- TOC entry 1967 (class 2606 OID 25149)
-- Dependencies: 1602 1602
-- Name: pk_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT pk_products PRIMARY KEY (product_id);


--
-- TOC entry 2058 (class 2606 OID 27753)
-- Dependencies: 1646 1646
-- Name: pk_products1; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY products
    ADD CONSTRAINT pk_products1 PRIMARY KEY (product_id);


--
-- TOC entry 2005 (class 2606 OID 25858)
-- Dependencies: 1623 1623
-- Name: pk_purchase_order_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT pk_purchase_order_items PRIMARY KEY (order_item_id);


--
-- TOC entry 2001 (class 2606 OID 25813)
-- Dependencies: 1622 1622
-- Name: pk_purchase_orders; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT pk_purchase_orders PRIMARY KEY (purchase_order_id);


--
-- TOC entry 2023 (class 2606 OID 26078)
-- Dependencies: 1630 1630
-- Name: pk_receipt_certificate_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT pk_receipt_certificate_items PRIMARY KEY (certificate_item_id);


--
-- TOC entry 2025 (class 2606 OID 26103)
-- Dependencies: 1631 1631 1631
-- Name: pk_receipt_certificate_serial_numbers; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY receipt_certificate_serial_numbers
    ADD CONSTRAINT pk_receipt_certificate_serial_numbers PRIMARY KEY (certificate_item_id, serial_number);


--
-- TOC entry 2019 (class 2606 OID 26041)
-- Dependencies: 1629 1629
-- Name: pk_receipt_certificates; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT pk_receipt_certificates PRIMARY KEY (receipt_certificate_id);


--
-- TOC entry 1969 (class 2606 OID 25151)
-- Dependencies: 1604 1604
-- Name: pk_resource_bundle; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY resource_bundle
    ADD CONSTRAINT pk_resource_bundle PRIMARY KEY (resource_id);


--
-- TOC entry 1973 (class 2606 OID 25153)
-- Dependencies: 1607 1607
-- Name: pk_users; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT pk_users PRIMARY KEY (user_id);


--
-- TOC entry 1993 (class 2606 OID 25496)
-- Dependencies: 1619 1619
-- Name: pk_warehouses; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY warehouses
    ADD CONSTRAINT pk_warehouses PRIMARY KEY (warehouse_id);


--
-- TOC entry 1987 (class 2606 OID 25406)
-- Dependencies: 1617 1617 1617
-- Name: uk_cities_country_id_city_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY cities
    ADD CONSTRAINT uk_cities_country_id_city_name UNIQUE (country_id, city_name);


--
-- TOC entry 1939 (class 2606 OID 25155)
-- Dependencies: 1593 1593 1593
-- Name: uk_classifier_groups_parent_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifier_groups
    ADD CONSTRAINT uk_classifier_groups_parent_code UNIQUE (parent_id, classifier_group_code);


--
-- TOC entry 1941 (class 2606 OID 25157)
-- Dependencies: 1593 1593 1593
-- Name: uk_classifier_groups_parent_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifier_groups
    ADD CONSTRAINT uk_classifier_groups_parent_name UNIQUE (parent_id, classifier_group_name);


--
-- TOC entry 1945 (class 2606 OID 25159)
-- Dependencies: 1594 1594 1594
-- Name: uk_classifiers_parent_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT uk_classifiers_parent_code UNIQUE (parent_id, classifier_code);


--
-- TOC entry 1947 (class 2606 OID 25161)
-- Dependencies: 1594 1594 1594
-- Name: uk_classifiers_parent_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT uk_classifiers_parent_name UNIQUE (parent_id, classifier_name);


--
-- TOC entry 2034 (class 2606 OID 27140)
-- Dependencies: 1634 1634 1634 1634 1634
-- Name: uk_communication_contacts_parent_type_value_contact_person; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT uk_communication_contacts_parent_type_value_contact_person UNIQUE (parent_id, communication_type_id, communication_value, contact_person_id);


--
-- TOC entry 1983 (class 2606 OID 25396)
-- Dependencies: 1616 1616
-- Name: uk_countries_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY countries
    ADD CONSTRAINT uk_countries_name UNIQUE (country_name);


--
-- TOC entry 1991 (class 2606 OID 25441)
-- Dependencies: 1618 1618
-- Name: uk_currencies_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY currencies
    ADD CONSTRAINT uk_currencies_name UNIQUE (currency_name);


--
-- TOC entry 2040 (class 2606 OID 26269)
-- Dependencies: 1636 1636 1636
-- Name: uk_data_object_details_do_id_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_details
    ADD CONSTRAINT uk_data_object_details_do_id_code UNIQUE (data_object_id, detail_code);


--
-- TOC entry 2048 (class 2606 OID 26354)
-- Dependencies: 1638 1638 1638
-- Name: uk_data_object_links_parent_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_links
    ADD CONSTRAINT uk_data_object_links_parent_name UNIQUE (parent_id, link_name);


--
-- TOC entry 2013 (class 2606 OID 25968)
-- Dependencies: 1626 1626 1626
-- Name: uk_delivery_certificates_parent_cert_number; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT uk_delivery_certificates_parent_cert_number UNIQUE (parent_id, delivery_certificate_number);


--
-- TOC entry 1951 (class 2606 OID 25163)
-- Dependencies: 1595 1595
-- Name: uk_dot_data_object_type; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_types
    ADD CONSTRAINT uk_dot_data_object_type UNIQUE (data_object_type);


--
-- TOC entry 1957 (class 2606 OID 25165)
-- Dependencies: 1597 1597
-- Name: uk_enum_classes; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY enum_classes
    ADD CONSTRAINT uk_enum_classes UNIQUE (enum_class_name);


--
-- TOC entry 1997 (class 2606 OID 25617)
-- Dependencies: 1620 1620 1620
-- Name: uk_invoices_invoice_number; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT uk_invoices_invoice_number UNIQUE (parent_id, invoice_number);


--
-- TOC entry 2044 (class 2606 OID 26317)
-- Dependencies: 1637 1637 1637 1637
-- Name: uk_passports_parent_type_number; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT uk_passports_parent_type_number UNIQUE (parent_id, passport_type_id, passport_number);


--
-- TOC entry 1963 (class 2606 OID 25169)
-- Dependencies: 1600 1600 1600
-- Name: uk_product_categories_parent_category_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT uk_product_categories_parent_category_name UNIQUE (parent_id, category_name);


--
-- TOC entry 2003 (class 2606 OID 26110)
-- Dependencies: 1622 1622 1622
-- Name: uk_purchase_orders_parent_order_number; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT uk_purchase_orders_parent_order_number UNIQUE (parent_id, order_number);


--
-- TOC entry 2021 (class 2606 OID 26043)
-- Dependencies: 1629 1629 1629
-- Name: uk_receipt_certificates_parent_cert_number; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT uk_receipt_certificates_parent_cert_number UNIQUE (parent_id, receipt_certificate_number);


--
-- TOC entry 1971 (class 2606 OID 25171)
-- Dependencies: 1605 1605
-- Name: uk_seq_id_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY sequence_identifiers
    ADD CONSTRAINT uk_seq_id_name UNIQUE (seq_id_name);


--
-- TOC entry 1975 (class 2606 OID 25173)
-- Dependencies: 1607 1607
-- Name: uk_users_email_address; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT uk_users_email_address UNIQUE (email_address);


--
-- TOC entry 1977 (class 2606 OID 25175)
-- Dependencies: 1607 1607
-- Name: uk_users_user_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT uk_users_user_name UNIQUE (user_name);


--
-- TOC entry 2056 (class 2606 OID 27712)
-- Dependencies: 1645 1645
-- Name: warehouse_products_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY warehouse_products
    ADD CONSTRAINT warehouse_products_pkey PRIMARY KEY (warehouse_product_id);


--
-- TOC entry 2030 (class 1259 OID 27138)
-- Dependencies: 1634
-- Name: fk_contact_person_id; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX fk_contact_person_id ON communication_contacts USING btree (contact_person_id);


--
-- TOC entry 2120 (class 2606 OID 27604)
-- Dependencies: 1615 1640 2049
-- Name: addresses_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT addresses_parent_id_fkey FOREIGN KEY (parent_id) REFERENCES business_partners(partner_id) ON DELETE CASCADE;


--
-- TOC entry 2260 (class 2606 OID 27382)
-- Dependencies: 1635 1604 1968
-- Name: bank_details_currency_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT bank_details_currency_id_fkey FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2263 (class 2606 OID 27641)
-- Dependencies: 1615 1978 1635
-- Name: bank_details_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT bank_details_parent_id_fkey FOREIGN KEY (parent_id) REFERENCES addresses(address_id) ON DELETE CASCADE;


--
-- TOC entry 2126 (class 2606 OID 27636)
-- Dependencies: 1980 1617 1616
-- Name: cities_country_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cities
    ADD CONSTRAINT cities_country_id_fkey FOREIGN KEY (country_id) REFERENCES countries(country_id) ON DELETE CASCADE;


--
-- TOC entry 2254 (class 2606 OID 27594)
-- Dependencies: 1634 1633 2028
-- Name: communication_contacts_contact_person_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT communication_contacts_contact_person_id_fkey FOREIGN KEY (contact_person_id) REFERENCES contact_persons(contact_person_id) ON DELETE SET NULL;


--
-- TOC entry 2253 (class 2606 OID 27589)
-- Dependencies: 1634 1978 1615
-- Name: communication_contacts_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT communication_contacts_parent_id_fkey FOREIGN KEY (parent_id) REFERENCES addresses(address_id) ON DELETE CASCADE;


--
-- TOC entry 2248 (class 2606 OID 27609)
-- Dependencies: 1642 1633 2053
-- Name: contact_persons_contact_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT contact_persons_contact_id_fkey FOREIGN KEY (contact_id) REFERENCES persons(partner_id) ON DELETE CASCADE;


--
-- TOC entry 2247 (class 2606 OID 27584)
-- Dependencies: 1978 1615 1633
-- Name: contact_persons_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT contact_persons_parent_id_fkey FOREIGN KEY (parent_id) REFERENCES addresses(address_id) ON DELETE CASCADE;


--
-- TOC entry 2123 (class 2606 OID 27552)
-- Dependencies: 1604 1616 1968
-- Name: countries_currency_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY countries
    ADD CONSTRAINT countries_currency_id_fkey FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2125 (class 2606 OID 27354)
-- Dependencies: 1617 1952 1596
-- Name: data_object_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cities
    ADD CONSTRAINT data_object_fk FOREIGN KEY (city_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2110 (class 2606 OID 26886)
-- Dependencies: 1604 1597 1954
-- Name: fk11ef5dd39219a9be; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY resource_bundle
    ADD CONSTRAINT fk11ef5dd39219a9be FOREIGN KEY (enum_class_id) REFERENCES enum_classes(enum_class_id);


--
-- TOC entry 2246 (class 2606 OID 26461)
-- Dependencies: 1632 2026 1633
-- Name: fk1e007a9f37f88bc6; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT fk1e007a9f37f88bc6 FOREIGN KEY (position_type_id) REFERENCES position_types(position_type_id);


--
-- TOC entry 2249 (class 2606 OID 27691)
-- Dependencies: 2053 1633 1642
-- Name: fk1e007a9f3c66048; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT fk1e007a9f3c66048 FOREIGN KEY (contact_id) REFERENCES persons(partner_id);


--
-- TOC entry 2145 (class 2606 OID 26581)
-- Dependencies: 1604 1620 1968
-- Name: fk25f222e617174fab; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e617174fab FOREIGN KEY (transportation_method_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2152 (class 2606 OID 26631)
-- Dependencies: 1968 1604 1620
-- Name: fk25f222e61808129a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e61808129a FOREIGN KEY (doc_delivery_method_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2153 (class 2606 OID 26636)
-- Dependencies: 1620 1978 1615
-- Name: fk25f222e627a66c93; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e627a66c93 FOREIGN KEY (branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2144 (class 2606 OID 26576)
-- Dependencies: 1968 1620 1604
-- Name: fk25f222e63aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e63aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2154 (class 2606 OID 26641)
-- Dependencies: 1968 1620 1604
-- Name: fk25f222e63dc9408c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e63dc9408c FOREIGN KEY (payment_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2149 (class 2606 OID 26606)
-- Dependencies: 1620 1604 1968
-- Name: fk25f222e646685c7a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e646685c7a FOREIGN KEY (delivery_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2156 (class 2606 OID 27183)
-- Dependencies: 1620 1642 2053
-- Name: fk25f222e649212a2e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e649212a2e FOREIGN KEY (recipient_contact_id) REFERENCES persons(partner_id);


--
-- TOC entry 2155 (class 2606 OID 27178)
-- Dependencies: 1620 1642 2053
-- Name: fk25f222e64da6f8bc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e64da6f8bc FOREIGN KEY (creator_id) REFERENCES persons(partner_id);


--
-- TOC entry 2148 (class 2606 OID 26596)
-- Dependencies: 1620 2045 1638
-- Name: fk25f222e693edee7d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e693edee7d FOREIGN KEY (recipient_id) REFERENCES data_object_links(data_object_link_id);


--
-- TOC entry 2147 (class 2606 OID 26591)
-- Dependencies: 1968 1604 1620
-- Name: fk25f222e696e3ba71; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e696e3ba71 FOREIGN KEY (payment_terms_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2146 (class 2606 OID 26586)
-- Dependencies: 1968 1604 1620
-- Name: fk25f222e69c49320d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e69c49320d FOREIGN KEY (status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2151 (class 2606 OID 26621)
-- Dependencies: 1604 1620 1968
-- Name: fk25f222e6a94f3ab3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e6a94f3ab3 FOREIGN KEY (invoice_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2143 (class 2606 OID 26571)
-- Dependencies: 1968 1620 1604
-- Name: fk25f222e6b07d659a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e6b07d659a FOREIGN KEY (vat_condition_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2150 (class 2606 OID 26611)
-- Dependencies: 1638 1620 2045
-- Name: fk25f222e6f61f3d82; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e6f61f3d82 FOREIGN KEY (shipping_agent_id) REFERENCES data_object_links(data_object_link_id);


--
-- TOC entry 2157 (class 2606 OID 27188)
-- Dependencies: 1620 1642 2053
-- Name: fk25f222e6fd5b3613; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e6fd5b3613 FOREIGN KEY (sender_id) REFERENCES persons(partner_id);


--
-- TOC entry 2163 (class 2606 OID 26556)
-- Dependencies: 1604 1968 1621
-- Name: fk326ab82e1ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoice_items
    ADD CONSTRAINT fk326ab82e1ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2164 (class 2606 OID 26561)
-- Dependencies: 1619 1992 1621
-- Name: fk326ab82e9f1800e1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoice_items
    ADD CONSTRAINT fk326ab82e9f1800e1 FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2166 (class 2606 OID 27421)
-- Dependencies: 1621 1966 1602
-- Name: fk326ab82ea330dfcf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoice_items
    ADD CONSTRAINT fk326ab82ea330dfcf FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2165 (class 2606 OID 26566)
-- Dependencies: 1602 1966 1621
-- Name: fk326ab82ef10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoice_items
    ADD CONSTRAINT fk326ab82ef10b9721 FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2190 (class 2606 OID 26656)
-- Dependencies: 1968 1604 1624
-- Name: fk327473ad3aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk327473ad3aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2192 (class 2606 OID 26671)
-- Dependencies: 1604 1968 1624
-- Name: fk327473ad9c49320d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk327473ad9c49320d FOREIGN KEY (status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2191 (class 2606 OID 26661)
-- Dependencies: 1622 1624 2000
-- Name: fk327473ad9fe0967e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk327473ad9fe0967e FOREIGN KEY (purchase_order_id) REFERENCES purchase_orders(purchase_order_id);


--
-- TOC entry 2193 (class 2606 OID 27193)
-- Dependencies: 1624 1642 2053
-- Name: fk327473adfd5b3613; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk327473adfd5b3613 FOREIGN KEY (sender_id) REFERENCES persons(partner_id);


--
-- TOC entry 2119 (class 2606 OID 26391)
-- Dependencies: 1615 1616 1980
-- Name: fk34207ba2977341c1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT fk34207ba2977341c1 FOREIGN KEY (country_id) REFERENCES countries(country_id);


--
-- TOC entry 2121 (class 2606 OID 27614)
-- Dependencies: 1615 1984 1617
-- Name: fk34207ba2ec30e373; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT fk34207ba2ec30e373 FOREIGN KEY (city_id) REFERENCES cities(city_id);


--
-- TOC entry 2264 (class 2606 OID 27676)
-- Dependencies: 1635 1615 1978
-- Name: fk363aa33f2f5fd250; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk363aa33f2f5fd250 FOREIGN KEY (bank_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2261 (class 2606 OID 27401)
-- Dependencies: 1635 1604 1968
-- Name: fk363aa33f3aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk363aa33f3aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2258 (class 2606 OID 26406)
-- Dependencies: 1635 1988 1618
-- Name: fk363aa33fa622533; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk363aa33fa622533 FOREIGN KEY (currency_id) REFERENCES currencies(currency_id);


--
-- TOC entry 2262 (class 2606 OID 27533)
-- Dependencies: 2051 1641 1635
-- Name: fk363aa33fee88a3ca; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk363aa33fee88a3ca FOREIGN KEY (bank_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2259 (class 2606 OID 27153)
-- Dependencies: 1635 1642 2053
-- Name: fk363aa33ff339b22b; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk363aa33ff339b22b FOREIGN KEY (bank_contact_id) REFERENCES persons(partner_id);


--
-- TOC entry 2208 (class 2606 OID 27163)
-- Dependencies: 1626 1642 2053
-- Name: fk3edb4c27157c075; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c27157c075 FOREIGN KEY (forwarder_contact_id) REFERENCES persons(partner_id);


--
-- TOC entry 2207 (class 2606 OID 26551)
-- Dependencies: 1968 1604 1626
-- Name: fk3edb4c2746dd317; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c2746dd317 FOREIGN KEY (delivery_cert_method_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2209 (class 2606 OID 27168)
-- Dependencies: 1626 1642 2053
-- Name: fk3edb4c2749212a2e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c2749212a2e FOREIGN KEY (recipient_contact_id) REFERENCES persons(partner_id);


--
-- TOC entry 2210 (class 2606 OID 27173)
-- Dependencies: 1626 1642 2053
-- Name: fk3edb4c274da6f8bc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c274da6f8bc FOREIGN KEY (creator_id) REFERENCES persons(partner_id);


--
-- TOC entry 2206 (class 2606 OID 26536)
-- Dependencies: 1604 1968 1626
-- Name: fk3edb4c278a6109cb; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c278a6109cb FOREIGN KEY (delivery_cert_reason_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2205 (class 2606 OID 26531)
-- Dependencies: 1638 1626 2045
-- Name: fk3edb4c2793edee7d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c2793edee7d FOREIGN KEY (recipient_id) REFERENCES data_object_links(data_object_link_id);


--
-- TOC entry 2204 (class 2606 OID 26521)
-- Dependencies: 1992 1626 1619
-- Name: fk3edb4c279f1800e1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c279f1800e1 FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2211 (class 2606 OID 27518)
-- Dependencies: 1641 1626 2051
-- Name: fk3edb4c27f2569c14; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c27f2569c14 FOREIGN KEY (forwarder_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2078 (class 2606 OID 27701)
-- Dependencies: 2049 1599 1640
-- Name: fk40afd1582b1363d6; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pattern_mask_formats
    ADD CONSTRAINT fk40afd1582b1363d6 FOREIGN KEY (owner_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2124 (class 2606 OID 27696)
-- Dependencies: 1968 1616 1604
-- Name: fk509f9ab43aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY countries
    ADD CONSTRAINT fk509f9ab43aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2082 (class 2606 OID 27545)
-- Dependencies: 1600 1960 1600
-- Name: fk5519b36c1c57732d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT fk5519b36c1c57732d FOREIGN KEY (parent_cat_id) REFERENCES product_categories(product_category_id);


--
-- TOC entry 2081 (class 2606 OID 26741)
-- Dependencies: 1600 1958 1599
-- Name: fk5519b36c7a956e19; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT fk5519b36c7a956e19 FOREIGN KEY (pattern_mask_format_id) REFERENCES pattern_mask_formats(pattern_mask_format_id);


--
-- TOC entry 2111 (class 2606 OID 25176)
-- Dependencies: 1972 1607 1607
-- Name: fk6a68e0844bb6904; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk6a68e0844bb6904 FOREIGN KEY (creator_id) REFERENCES users(user_id);


--
-- TOC entry 2115 (class 2606 OID 26891)
-- Dependencies: 1607 1596 1952
-- Name: fk6a68e08706848a7; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk6a68e08706848a7 FOREIGN KEY (person_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2112 (class 2606 OID 25181)
-- Dependencies: 1596 1952 1607
-- Name: fk6a68e08a08870b9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk6a68e08a08870b9 FOREIGN KEY (person_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2116 (class 2606 OID 26896)
-- Dependencies: 1607 1607 1972
-- Name: fk6a68e08f9f4b72; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk6a68e08f9f4b72 FOREIGN KEY (creator_id) REFERENCES users(user_id);


--
-- TOC entry 2276 (class 2606 OID 27114)
-- Dependencies: 1640 1596 1952
-- Name: fk6b9ae64a3d8dbb7d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_partners
    ADD CONSTRAINT fk6b9ae64a3d8dbb7d FOREIGN KEY (id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2086 (class 2606 OID 26751)
-- Dependencies: 2045 1601 1638
-- Name: fk725e8d72b6365ea; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk725e8d72b6365ea FOREIGN KEY (supplier_id) REFERENCES data_object_links(data_object_link_id);


--
-- TOC entry 2087 (class 2606 OID 27451)
-- Dependencies: 1966 1602 1601
-- Name: fk725e8d7a330dfcf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk725e8d7a330dfcf FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2085 (class 2606 OID 26746)
-- Dependencies: 1966 1601 1602
-- Name: fk725e8d7f10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk725e8d7f10b9721 FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2074 (class 2606 OID 26486)
-- Dependencies: 1952 1596 1596
-- Name: fk74e5117f2ff7d10e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk74e5117f2ff7d10e FOREIGN KEY (linked_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2076 (class 2606 OID 26496)
-- Dependencies: 1948 1595 1596
-- Name: fk74e5117fa44e2131; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk74e5117fa44e2131 FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2075 (class 2606 OID 26491)
-- Dependencies: 1952 1596 1596
-- Name: fk74e5117fafa1da5d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk74e5117fafa1da5d FOREIGN KEY (parent_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2238 (class 2606 OID 26831)
-- Dependencies: 1630 1604 1968
-- Name: fk7503fcd11ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk7503fcd11ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2240 (class 2606 OID 27461)
-- Dependencies: 1630 1966 1602
-- Name: fk7503fcd1a330dfcf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk7503fcd1a330dfcf FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2239 (class 2606 OID 26836)
-- Dependencies: 1630 1602 1966
-- Name: fk7503fcd1f10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk7503fcd1f10b9721 FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2197 (class 2606 OID 26646)
-- Dependencies: 1968 1625 1604
-- Name: fk7e6ecbc71ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmation_items
    ADD CONSTRAINT fk7e6ecbc71ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2199 (class 2606 OID 27426)
-- Dependencies: 1625 1602 1966
-- Name: fk7e6ecbc7a330dfcf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmation_items
    ADD CONSTRAINT fk7e6ecbc7a330dfcf FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2198 (class 2606 OID 26651)
-- Dependencies: 1602 1625 1966
-- Name: fk7e6ecbc7f10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmation_items
    ADD CONSTRAINT fk7e6ecbc7f10b9721 FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2285 (class 2606 OID 27513)
-- Dependencies: 2049 1640 1641
-- Name: fk8258b9a017025956; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk8258b9a017025956 FOREIGN KEY (organization_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2277 (class 2606 OID 27014)
-- Dependencies: 1641 1604 1968
-- Name: fk8258b9a0180e7eb9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk8258b9a0180e7eb9 FOREIGN KEY (organization_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2281 (class 2606 OID 27431)
-- Dependencies: 1641 1968 1604
-- Name: fk8258b9a03aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk8258b9a03aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2282 (class 2606 OID 27436)
-- Dependencies: 1615 1978 1641
-- Name: fk8258b9a05416c47; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk8258b9a05416c47 FOREIGN KEY (registration_address_id) REFERENCES addresses(address_id);


--
-- TOC entry 2283 (class 2606 OID 27441)
-- Dependencies: 1978 1641 1615
-- Name: fk8258b9a08b780b82; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk8258b9a08b780b82 FOREIGN KEY (administration_address_id) REFERENCES addresses(address_id);


--
-- TOC entry 2284 (class 2606 OID 27508)
-- Dependencies: 1641 1641 2051
-- Name: fk8258b9a08c46f1ed; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk8258b9a08c46f1ed FOREIGN KEY (registration_organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2275 (class 2606 OID 27245)
-- Dependencies: 1638 1952 1596
-- Name: fk9157692e2ff7d10e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_links
    ADD CONSTRAINT fk9157692e2ff7d10e FOREIGN KEY (linked_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2131 (class 2606 OID 27228)
-- Dependencies: 1619 2053 1642
-- Name: fk94f81e109757951; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouses
    ADD CONSTRAINT fk94f81e109757951 FOREIGN KEY (warehouseman_id) REFERENCES persons(partner_id);


--
-- TOC entry 2130 (class 2606 OID 26916)
-- Dependencies: 1619 1615 1978
-- Name: fk94f81e10a6877d01; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouses
    ADD CONSTRAINT fk94f81e10a6877d01 FOREIGN KEY (address_id) REFERENCES addresses(address_id);


--
-- TOC entry 2290 (class 2606 OID 27713)
-- Dependencies: 1619 1992 1645
-- Name: fk951433609f1800e1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouse_products
    ADD CONSTRAINT fk951433609f1800e1 FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2291 (class 2606 OID 27718)
-- Dependencies: 1602 1966 1645
-- Name: fk95143360a330dfcf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouse_products
    ADD CONSTRAINT fk95143360a330dfcf FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2242 (class 2606 OID 26841)
-- Dependencies: 1631 1630 2022
-- Name: fk98230d0e73d2d06a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_serial_numbers
    ADD CONSTRAINT fk98230d0e73d2d06a FOREIGN KEY (certificate_item_id) REFERENCES receipt_certificate_items(certificate_item_id);


--
-- TOC entry 2122 (class 2606 OID 27619)
-- Dependencies: 1617 1615 1984
-- Name: fk_addresses_city_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT fk_addresses_city_id FOREIGN KEY (city_id) REFERENCES cities(city_id);


--
-- TOC entry 2118 (class 2606 OID 25412)
-- Dependencies: 1980 1615 1616
-- Name: fk_addresses_country_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT fk_addresses_country_id FOREIGN KEY (country_id) REFERENCES countries(country_id);


--
-- TOC entry 2117 (class 2606 OID 25377)
-- Dependencies: 1596 1615 1952
-- Name: fk_addresses_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT fk_addresses_do_id FOREIGN KEY (address_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2257 (class 2606 OID 26250)
-- Dependencies: 1978 1615 1635
-- Name: fk_bank_details_bank_branch; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk_bank_details_bank_branch FOREIGN KEY (bank_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2256 (class 2606 OID 26235)
-- Dependencies: 1635 1952 1596
-- Name: fk_bank_details_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk_bank_details_do_id FOREIGN KEY (bank_detail_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2059 (class 2606 OID 25196)
-- Dependencies: 1591 1594 1942
-- Name: fk_classified_objects_classifier_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classified_objects
    ADD CONSTRAINT fk_classified_objects_classifier_id FOREIGN KEY (classifier_id) REFERENCES classifiers(classifier_id);


--
-- TOC entry 2060 (class 2606 OID 25201)
-- Dependencies: 1591 1952 1596
-- Name: fk_classified_objects_object_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classified_objects
    ADD CONSTRAINT fk_classified_objects_object_id FOREIGN KEY (classified_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2063 (class 2606 OID 25206)
-- Dependencies: 1942 1594 1592
-- Name: fk_classifier_applied_for_dot_classifiers; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifier_applied_for_dot
    ADD CONSTRAINT fk_classifier_applied_for_dot_classifiers FOREIGN KEY (classifier_id) REFERENCES classifiers(classifier_id);


--
-- TOC entry 2064 (class 2606 OID 25211)
-- Dependencies: 1948 1595 1592
-- Name: fk_classifier_applied_for_dot_dot_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifier_applied_for_dot
    ADD CONSTRAINT fk_classifier_applied_for_dot_dot_id FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2067 (class 2606 OID 25216)
-- Dependencies: 1593 1596 1952
-- Name: fk_classifier_groups_cg_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifier_groups
    ADD CONSTRAINT fk_classifier_groups_cg_id FOREIGN KEY (classifier_group_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2068 (class 2606 OID 25221)
-- Dependencies: 1594 1593 1936
-- Name: fk_classifiers_group_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT fk_classifiers_group_id FOREIGN KEY (classifier_group_id) REFERENCES classifier_groups(classifier_group_id);


--
-- TOC entry 2069 (class 2606 OID 25226)
-- Dependencies: 1594 1596 1952
-- Name: fk_classifiers_id_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT fk_classifiers_id_do_id FOREIGN KEY (classifier_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2251 (class 2606 OID 26217)
-- Dependencies: 1968 1604 1634
-- Name: fk_communication_contacts_comm_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT fk_communication_contacts_comm_type FOREIGN KEY (communication_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2250 (class 2606 OID 26207)
-- Dependencies: 1952 1596 1634
-- Name: fk_communication_contacts_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT fk_communication_contacts_do_id FOREIGN KEY (communication_contact_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2244 (class 2606 OID 26164)
-- Dependencies: 1633 1952 1596
-- Name: fk_contact_persons_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT fk_contact_persons_do_id FOREIGN KEY (contact_person_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2245 (class 2606 OID 26174)
-- Dependencies: 1633 2026 1632
-- Name: fk_contact_persons_position_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT fk_contact_persons_position_type FOREIGN KEY (position_type_id) REFERENCES position_types(position_type_id);


--
-- TOC entry 2265 (class 2606 OID 26270)
-- Dependencies: 1952 1596 1636
-- Name: fk_data_object_details_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_details
    ADD CONSTRAINT fk_data_object_details_do_id FOREIGN KEY (data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2273 (class 2606 OID 26338)
-- Dependencies: 1638 1596 1952
-- Name: fk_data_object_links_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_links
    ADD CONSTRAINT fk_data_object_links_do_id FOREIGN KEY (data_object_link_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2274 (class 2606 OID 26343)
-- Dependencies: 1638 1596 1952
-- Name: fk_data_object_links_linked_object; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_links
    ADD CONSTRAINT fk_data_object_links_linked_object FOREIGN KEY (linked_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2073 (class 2606 OID 26348)
-- Dependencies: 1596 1595 1948
-- Name: fk_data_objects_do_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk_data_objects_do_type FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2071 (class 2606 OID 25231)
-- Dependencies: 1596 1596 1952
-- Name: fk_data_objects_linked_data_object_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk_data_objects_linked_data_object_id FOREIGN KEY (linked_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2072 (class 2606 OID 25236)
-- Dependencies: 1596 1596 1952
-- Name: fk_data_objects_parent_data_object_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk_data_objects_parent_data_object_id FOREIGN KEY (parent_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2213 (class 2606 OID 26007)
-- Dependencies: 2010 1626 1627
-- Name: fk_delivery_certificate_items_delivery_cert; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fk_delivery_certificate_items_delivery_cert FOREIGN KEY (parent_id) REFERENCES delivery_certificates(delivery_certificate_id);


--
-- TOC entry 2212 (class 2606 OID 26002)
-- Dependencies: 1952 1627 1596
-- Name: fk_delivery_certificate_items_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fk_delivery_certificate_items_do_id FOREIGN KEY (certificate_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2215 (class 2606 OID 26017)
-- Dependencies: 1604 1968 1627
-- Name: fk_delivery_certificate_items_measure_unit; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fk_delivery_certificate_items_measure_unit FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2214 (class 2606 OID 26012)
-- Dependencies: 1627 1966 1602
-- Name: fk_delivery_certificate_items_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fk_delivery_certificate_items_product FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2219 (class 2606 OID 26027)
-- Dependencies: 2014 1627 1628
-- Name: fk_delivery_certificate_serial_numbers_dci; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_serial_numbers
    ADD CONSTRAINT fk_delivery_certificate_serial_numbers_dci FOREIGN KEY (certificate_item_id) REFERENCES delivery_certificate_items(certificate_item_id);


--
-- TOC entry 2200 (class 2606 OID 25972)
-- Dependencies: 1626 1952 1596
-- Name: fk_delivery_certificates_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_delivery_certificates_do_id FOREIGN KEY (delivery_certificate_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2203 (class 2606 OID 26032)
-- Dependencies: 1626 1968 1604
-- Name: fk_delivery_certificates_reason; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_delivery_certificates_reason FOREIGN KEY (delivery_cert_reason_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2202 (class 2606 OID 25987)
-- Dependencies: 1626 1968 1604
-- Name: fk_delivery_certificates_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_delivery_certificates_type FOREIGN KEY (delivery_cert_method_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2201 (class 2606 OID 25977)
-- Dependencies: 1626 1992 1619
-- Name: fk_delivery_certificates_warehouse; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_delivery_certificates_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2158 (class 2606 OID 25728)
-- Dependencies: 1596 1621 1952
-- Name: fk_invoice_items_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoice_items
    ADD CONSTRAINT fk_invoice_items_do_id FOREIGN KEY (invoice_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2159 (class 2606 OID 25733)
-- Dependencies: 1994 1621 1620
-- Name: fk_invoice_items_invoice_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoice_items
    ADD CONSTRAINT fk_invoice_items_invoice_id FOREIGN KEY (parent_id) REFERENCES invoices(invoice_id);


--
-- TOC entry 2162 (class 2606 OID 25748)
-- Dependencies: 1604 1968 1621
-- Name: fk_invoice_items_measure_unit_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoice_items
    ADD CONSTRAINT fk_invoice_items_measure_unit_id FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2160 (class 2606 OID 25738)
-- Dependencies: 1621 1966 1602
-- Name: fk_invoice_items_product_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoice_items
    ADD CONSTRAINT fk_invoice_items_product_id FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2161 (class 2606 OID 25743)
-- Dependencies: 1619 1621 1992
-- Name: fk_invoice_items_warehouse_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoice_items
    ADD CONSTRAINT fk_invoice_items_warehouse_id FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2132 (class 2606 OID 25618)
-- Dependencies: 1978 1620 1615
-- Name: fk_invoices_branch_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_branch_id FOREIGN KEY (branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2133 (class 2606 OID 25628)
-- Dependencies: 1604 1620 1968
-- Name: fk_invoices_currency_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_currency_id FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2134 (class 2606 OID 25633)
-- Dependencies: 1604 1620 1968
-- Name: fk_invoices_delivery_type_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_delivery_type_id FOREIGN KEY (delivery_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2135 (class 2606 OID 25638)
-- Dependencies: 1596 1620 1952
-- Name: fk_invoices_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_do_id FOREIGN KEY (invoice_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2136 (class 2606 OID 25643)
-- Dependencies: 1604 1620 1968
-- Name: fk_invoices_doc_delivery_method_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_doc_delivery_method_id FOREIGN KEY (doc_delivery_method_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2137 (class 2606 OID 25648)
-- Dependencies: 1968 1620 1604
-- Name: fk_invoices_invoice_type_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_invoice_type_id FOREIGN KEY (invoice_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2138 (class 2606 OID 25653)
-- Dependencies: 1620 1604 1968
-- Name: fk_invoices_payment_terms_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_payment_terms_id FOREIGN KEY (payment_terms_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2139 (class 2606 OID 25658)
-- Dependencies: 1968 1620 1604
-- Name: fk_invoices_payment_type_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_payment_type_id FOREIGN KEY (payment_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2140 (class 2606 OID 25673)
-- Dependencies: 1604 1620 1968
-- Name: fk_invoices_status_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_status_id FOREIGN KEY (status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2141 (class 2606 OID 25678)
-- Dependencies: 1968 1620 1604
-- Name: fk_invoices_transportation_method_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_transportation_method_id FOREIGN KEY (transportation_method_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2142 (class 2606 OID 25683)
-- Dependencies: 1620 1604 1968
-- Name: fk_invoices_vat_condition_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_vat_condition_id FOREIGN KEY (vat_condition_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2196 (class 2606 OID 25957)
-- Dependencies: 1968 1625 1604
-- Name: fk_order_confirmation_items_measure_unit; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmation_items
    ADD CONSTRAINT fk_order_confirmation_items_measure_unit FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2194 (class 2606 OID 25947)
-- Dependencies: 1624 2006 1625
-- Name: fk_order_confirmation_items_poc_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmation_items
    ADD CONSTRAINT fk_order_confirmation_items_poc_id FOREIGN KEY (parent_id) REFERENCES order_confirmations(order_confirmation_id);


--
-- TOC entry 2195 (class 2606 OID 25952)
-- Dependencies: 1625 1602 1966
-- Name: fk_order_confirmation_items_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmation_items
    ADD CONSTRAINT fk_order_confirmation_items_product FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2186 (class 2606 OID 25914)
-- Dependencies: 1968 1624 1604
-- Name: fk_order_confirmations_currency; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk_order_confirmations_currency FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2187 (class 2606 OID 25919)
-- Dependencies: 1624 1596 1952
-- Name: fk_order_confirmations_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk_order_confirmations_do_id FOREIGN KEY (order_confirmation_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2188 (class 2606 OID 25924)
-- Dependencies: 2000 1624 1622
-- Name: fk_order_confirmations_po_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk_order_confirmations_po_id FOREIGN KEY (purchase_order_id) REFERENCES purchase_orders(purchase_order_id);


--
-- TOC entry 2189 (class 2606 OID 25934)
-- Dependencies: 1968 1604 1624
-- Name: fk_order_confirmations_status; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk_order_confirmations_status FOREIGN KEY (status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2268 (class 2606 OID 26300)
-- Dependencies: 1637 1596 1952
-- Name: fk_passports_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT fk_passports_do_id FOREIGN KEY (passport_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2267 (class 2606 OID 26293)
-- Dependencies: 1637 1615 1978
-- Name: fk_passports_issuer_branch; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT fk_passports_issuer_branch FOREIGN KEY (issuer_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2266 (class 2606 OID 26288)
-- Dependencies: 1637 1968 1604
-- Name: fk_passports_pass_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT fk_passports_pass_type FOREIGN KEY (passport_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2243 (class 2606 OID 26154)
-- Dependencies: 1632 1596 1952
-- Name: fk_position_types_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY position_types
    ADD CONSTRAINT fk_position_types_do_id FOREIGN KEY (position_type_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2079 (class 2606 OID 25251)
-- Dependencies: 1600 1599 1958
-- Name: fk_product_categories_pattern_mask_format_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT fk_product_categories_pattern_mask_format_id FOREIGN KEY (pattern_mask_format_id) REFERENCES pattern_mask_formats(pattern_mask_format_id);


--
-- TOC entry 2080 (class 2606 OID 25256)
-- Dependencies: 1600 1596 1952
-- Name: fk_product_categories_product_category_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT fk_product_categories_product_category_id FOREIGN KEY (product_category_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2088 (class 2606 OID 25261)
-- Dependencies: 1602 1600 1960
-- Name: fk_product_category_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_product_category_id FOREIGN KEY (category_id) REFERENCES product_categories(product_category_id);


--
-- TOC entry 2083 (class 2606 OID 25266)
-- Dependencies: 1602 1601 1966
-- Name: fk_product_suppliers_product_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk_product_suppliers_product_id FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2084 (class 2606 OID 26365)
-- Dependencies: 2045 1601 1638
-- Name: fk_product_suppliers_supplier; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk_product_suppliers_supplier FOREIGN KEY (supplier_id) REFERENCES data_object_links(data_object_link_id);


--
-- TOC entry 2292 (class 2606 OID 27759)
-- Dependencies: 1646 1596 1952
-- Name: fk_products1_product_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_products1_product_id FOREIGN KEY (product_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2089 (class 2606 OID 25271)
-- Dependencies: 1968 1602 1604
-- Name: fk_products_color_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_products_color_id FOREIGN KEY (product_color_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2090 (class 2606 OID 25276)
-- Dependencies: 1604 1602 1968
-- Name: fk_products_dimension_unit_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_products_dimension_unit_id FOREIGN KEY (dimension_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2091 (class 2606 OID 25281)
-- Dependencies: 1604 1602 1968
-- Name: fk_products_measure_unit_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_products_measure_unit_id FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2092 (class 2606 OID 25286)
-- Dependencies: 1599 1602 1958
-- Name: fk_products_pattern_mask_format_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_products_pattern_mask_format_id FOREIGN KEY (pattern_mask_format_id) REFERENCES pattern_mask_formats(pattern_mask_format_id);


--
-- TOC entry 2093 (class 2606 OID 25296)
-- Dependencies: 1604 1602 1968
-- Name: fk_products_weight_unit_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_products_weight_unit_id FOREIGN KEY (weight_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2179 (class 2606 OID 25859)
-- Dependencies: 1623 1596 1952
-- Name: fk_purchase_order_items_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fk_purchase_order_items_do_id FOREIGN KEY (order_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2182 (class 2606 OID 25874)
-- Dependencies: 1623 1604 1968
-- Name: fk_purchase_order_items_measure_unit; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fk_purchase_order_items_measure_unit FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2180 (class 2606 OID 25864)
-- Dependencies: 1623 1622 2000
-- Name: fk_purchase_order_items_po_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fk_purchase_order_items_po_id FOREIGN KEY (parent_id) REFERENCES purchase_orders(purchase_order_id);


--
-- TOC entry 2181 (class 2606 OID 25869)
-- Dependencies: 1966 1623 1602
-- Name: fk_purchase_order_items_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fk_purchase_order_items_product FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2167 (class 2606 OID 25816)
-- Dependencies: 1615 1622 1978
-- Name: fk_purchase_orders_branch_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fk_purchase_orders_branch_id FOREIGN KEY (branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2168 (class 2606 OID 25831)
-- Dependencies: 1952 1596 1622
-- Name: fk_purchase_orders_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fk_purchase_orders_do_id FOREIGN KEY (purchase_order_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2169 (class 2606 OID 25836)
-- Dependencies: 1968 1604 1622
-- Name: fk_purchase_orders_doc_delivery_method; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fk_purchase_orders_doc_delivery_method FOREIGN KEY (doc_delivery_method_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2170 (class 2606 OID 25846)
-- Dependencies: 1604 1968 1622
-- Name: fk_purchase_orders_status_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fk_purchase_orders_status_id FOREIGN KEY (status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2171 (class 2606 OID 26360)
-- Dependencies: 1638 1622 2045
-- Name: fk_purchase_orders_supplier; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fk_purchase_orders_supplier FOREIGN KEY (supplier_id) REFERENCES data_object_links(data_object_link_id);


--
-- TOC entry 2235 (class 2606 OID 26084)
-- Dependencies: 1596 1630 1952
-- Name: fk_receipt_certificate_items_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk_receipt_certificate_items_do_id FOREIGN KEY (certificate_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2236 (class 2606 OID 26089)
-- Dependencies: 1968 1630 1604
-- Name: fk_receipt_certificate_items_measure_unit; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk_receipt_certificate_items_measure_unit FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2237 (class 2606 OID 26094)
-- Dependencies: 1630 1966 1602
-- Name: fk_receipt_certificate_items_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk_receipt_certificate_items_product FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2234 (class 2606 OID 26079)
-- Dependencies: 1630 2018 1629
-- Name: fk_receipt_certificate_items_receipt_cert; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk_receipt_certificate_items_receipt_cert FOREIGN KEY (parent_id) REFERENCES receipt_certificates(receipt_certificate_id);


--
-- TOC entry 2241 (class 2606 OID 26104)
-- Dependencies: 1630 1631 2022
-- Name: fk_receipt_certificate_serial_numbers_dci; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_serial_numbers
    ADD CONSTRAINT fk_receipt_certificate_serial_numbers_dci FOREIGN KEY (certificate_item_id) REFERENCES receipt_certificate_items(certificate_item_id);


--
-- TOC entry 2225 (class 2606 OID 26355)
-- Dependencies: 1629 1638 2045
-- Name: fk_receipt_certificates_deliverer; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_deliverer FOREIGN KEY (deliverer_id) REFERENCES data_object_links(data_object_link_id);


--
-- TOC entry 2221 (class 2606 OID 26044)
-- Dependencies: 1596 1629 1952
-- Name: fk_receipt_certificates_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_do_id FOREIGN KEY (receipt_certificate_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2222 (class 2606 OID 26054)
-- Dependencies: 1629 1968 1604
-- Name: fk_receipt_certificates_reason; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_reason FOREIGN KEY (receipt_cert_reason_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2223 (class 2606 OID 26064)
-- Dependencies: 1629 1604 1968
-- Name: fk_receipt_certificates_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_type FOREIGN KEY (receipt_cert_method_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2224 (class 2606 OID 26069)
-- Dependencies: 1619 1629 1992
-- Name: fk_receipt_certificates_warehouse; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2109 (class 2606 OID 25301)
-- Dependencies: 1597 1604 1954
-- Name: fk_resource_bundle_enum_class_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY resource_bundle
    ADD CONSTRAINT fk_resource_bundle_enum_class_id FOREIGN KEY (enum_class_id) REFERENCES enum_classes(enum_class_id);


--
-- TOC entry 2108 (class 2606 OID 27764)
-- Dependencies: 2057 1602 1646
-- Name: fk_simple_products_products; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_simple_products_products FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2113 (class 2606 OID 25306)
-- Dependencies: 1972 1607 1607
-- Name: fk_users_creator_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk_users_creator_id FOREIGN KEY (creator_id) REFERENCES users(user_id);


--
-- TOC entry 2114 (class 2606 OID 25311)
-- Dependencies: 1607 1596 1952
-- Name: fk_users_person_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk_users_person_id FOREIGN KEY (person_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2128 (class 2606 OID 25502)
-- Dependencies: 1615 1619 1978
-- Name: fk_warehouses_address_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouses
    ADD CONSTRAINT fk_warehouses_address_id FOREIGN KEY (address_id) REFERENCES addresses(address_id);


--
-- TOC entry 2129 (class 2606 OID 26132)
-- Dependencies: 1619 1596 1952
-- Name: fk_warehouses_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouses
    ADD CONSTRAINT fk_warehouses_do_id FOREIGN KEY (warehouse_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2183 (class 2606 OID 26786)
-- Dependencies: 1623 1604 1968
-- Name: fka00989511ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fka00989511ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2185 (class 2606 OID 27456)
-- Dependencies: 1602 1623 1966
-- Name: fka0098951a330dfcf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fka0098951a330dfcf FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2184 (class 2606 OID 26791)
-- Dependencies: 1623 1602 1966
-- Name: fka0098951f10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fka0098951f10b9721 FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2216 (class 2606 OID 26501)
-- Dependencies: 1627 1604 1968
-- Name: fka8be2f8d1ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fka8be2f8d1ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2218 (class 2606 OID 27416)
-- Dependencies: 1966 1602 1627
-- Name: fka8be2f8da330dfcf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fka8be2f8da330dfcf FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2217 (class 2606 OID 26506)
-- Dependencies: 1602 1966 1627
-- Name: fka8be2f8df10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fka8be2f8df10b9721 FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2255 (class 2606 OID 27686)
-- Dependencies: 1633 1634 2028
-- Name: fkac65329c8da5f302; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT fkac65329c8da5f302 FOREIGN KEY (contact_person_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2252 (class 2606 OID 26456)
-- Dependencies: 1968 1634 1604
-- Name: fkac65329c90a50e5c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT fkac65329c90a50e5c FOREIGN KEY (communication_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2127 (class 2606 OID 27681)
-- Dependencies: 1617 1616 1980
-- Name: fkaeedbb49977341c1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cities
    ADD CONSTRAINT fkaeedbb49977341c1 FOREIGN KEY (country_id) REFERENCES countries(country_id);


--
-- TOC entry 2220 (class 2606 OID 26511)
-- Dependencies: 2014 1628 1627
-- Name: fkb3b02dd2d961ef9c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_serial_numbers
    ADD CONSTRAINT fkb3b02dd2d961ef9c FOREIGN KEY (certificate_item_id) REFERENCES delivery_certificate_items(certificate_item_id);


--
-- TOC entry 2173 (class 2606 OID 26806)
-- Dependencies: 1622 1604 1968
-- Name: fkc307e7e31808129a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e31808129a FOREIGN KEY (doc_delivery_method_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2175 (class 2606 OID 26816)
-- Dependencies: 1622 1615 1978
-- Name: fkc307e7e327a66c93; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e327a66c93 FOREIGN KEY (branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2174 (class 2606 OID 26811)
-- Dependencies: 1622 1638 2045
-- Name: fkc307e7e32b6365ea; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e32b6365ea FOREIGN KEY (supplier_id) REFERENCES data_object_links(data_object_link_id);


--
-- TOC entry 2177 (class 2606 OID 27203)
-- Dependencies: 1622 1642 2053
-- Name: fkc307e7e34da6f8bc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e34da6f8bc FOREIGN KEY (creator_id) REFERENCES persons(partner_id);


--
-- TOC entry 2178 (class 2606 OID 27208)
-- Dependencies: 1622 2053 1642
-- Name: fkc307e7e38c52e49b; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e38c52e49b FOREIGN KEY (supplier_contact_id) REFERENCES persons(partner_id);


--
-- TOC entry 2172 (class 2606 OID 26801)
-- Dependencies: 1622 1604 1968
-- Name: fkc307e7e39c49320d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e39c49320d FOREIGN KEY (status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2176 (class 2606 OID 27198)
-- Dependencies: 1622 1642 2053
-- Name: fkc307e7e3fd5b3613; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e3fd5b3613 FOREIGN KEY (sender_id) REFERENCES persons(partner_id);


--
-- TOC entry 2095 (class 2606 OID 26761)
-- Dependencies: 1602 1968 1604
-- Name: fkc42bd1641ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd1641ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2097 (class 2606 OID 26771)
-- Dependencies: 1968 1604 1602
-- Name: fkc42bd16427acd874; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd16427acd874 FOREIGN KEY (weight_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2098 (class 2606 OID 26776)
-- Dependencies: 1600 1602 1960
-- Name: fkc42bd1646e2f83d0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd1646e2f83d0 FOREIGN KEY (category_id) REFERENCES product_categories(product_category_id);


--
-- TOC entry 2107 (class 2606 OID 27734)
-- Dependencies: 1602 1640 2049
-- Name: fkc42bd1646e436697; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd1646e436697 FOREIGN KEY (producer_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2096 (class 2606 OID 26766)
-- Dependencies: 1602 1958 1599
-- Name: fkc42bd1647a956e19; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd1647a956e19 FOREIGN KEY (pattern_mask_format_id) REFERENCES pattern_mask_formats(pattern_mask_format_id);


--
-- TOC entry 2094 (class 2606 OID 26756)
-- Dependencies: 1968 1602 1604
-- Name: fkc42bd1648f60524c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd1648f60524c FOREIGN KEY (product_color_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2099 (class 2606 OID 26781)
-- Dependencies: 1968 1602 1604
-- Name: fkc42bd1649d6c3562; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd1649d6c3562 FOREIGN KEY (dimension_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2061 (class 2606 OID 26431)
-- Dependencies: 1942 1594 1591
-- Name: fkc436c2e88e8748f3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classified_objects
    ADD CONSTRAINT fkc436c2e88e8748f3 FOREIGN KEY (classifier_id) REFERENCES classifiers(classifier_id);


--
-- TOC entry 2062 (class 2606 OID 26436)
-- Dependencies: 1591 1952 1596
-- Name: fkc436c2e8cf1f1951; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classified_objects
    ADD CONSTRAINT fkc436c2e8cf1f1951 FOREIGN KEY (classified_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2269 (class 2606 OID 26706)
-- Dependencies: 1978 1615 1637
-- Name: fkc84af6a180bd868d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT fkc84af6a180bd868d FOREIGN KEY (issuer_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2270 (class 2606 OID 26716)
-- Dependencies: 1604 1968 1637
-- Name: fkc84af6a1ad6ece98; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT fkc84af6a1ad6ece98 FOREIGN KEY (passport_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2271 (class 2606 OID 27523)
-- Dependencies: 1641 1637 2051
-- Name: fkc84af6a1db08a2d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT fkc84af6a1db08a2d FOREIGN KEY (issuer_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2287 (class 2606 OID 27044)
-- Dependencies: 1642 1616 1980
-- Name: fkd78fcfbe16ffc779; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT fkd78fcfbe16ffc779 FOREIGN KEY (birth_place_country_id) REFERENCES countries(country_id);


--
-- TOC entry 2286 (class 2606 OID 27034)
-- Dependencies: 1642 1604 1968
-- Name: fkd78fcfbe2663e8de; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT fkd78fcfbe2663e8de FOREIGN KEY (gender_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2289 (class 2606 OID 27557)
-- Dependencies: 1617 1984 1642
-- Name: fkd78fcfbe4250f8bb; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT fkd78fcfbe4250f8bb FOREIGN KEY (birth_place_city_id) REFERENCES cities(city_id);


--
-- TOC entry 2288 (class 2606 OID 27148)
-- Dependencies: 1642 1640 2049
-- Name: fkd78fcfbed213c5a1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT fkd78fcfbed213c5a1 FOREIGN KEY (partner_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2100 (class 2606 OID 27466)
-- Dependencies: 1602 1604 1968
-- Name: fke81099511ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke81099511ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2103 (class 2606 OID 27481)
-- Dependencies: 1604 1968 1602
-- Name: fke810995127acd874; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke810995127acd874 FOREIGN KEY (weight_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2104 (class 2606 OID 27486)
-- Dependencies: 1600 1602 1960
-- Name: fke81099516e2f83d0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke81099516e2f83d0 FOREIGN KEY (category_id) REFERENCES product_categories(product_category_id);


--
-- TOC entry 2106 (class 2606 OID 27729)
-- Dependencies: 2049 1640 1602
-- Name: fke81099516e436697; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke81099516e436697 FOREIGN KEY (producer_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2102 (class 2606 OID 27476)
-- Dependencies: 1602 1958 1599
-- Name: fke81099517a956e19; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke81099517a956e19 FOREIGN KEY (pattern_mask_format_id) REFERENCES pattern_mask_formats(pattern_mask_format_id);


--
-- TOC entry 2101 (class 2606 OID 27471)
-- Dependencies: 1602 1604 1968
-- Name: fke81099518f60524c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke81099518f60524c FOREIGN KEY (product_color_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2105 (class 2606 OID 27496)
-- Dependencies: 1604 1968 1602
-- Name: fke81099519d6c3562; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke81099519d6c3562 FOREIGN KEY (dimension_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2230 (class 2606 OID 27213)
-- Dependencies: 1629 2053 1642
-- Name: fke9334463157c075; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke9334463157c075 FOREIGN KEY (forwarder_contact_id) REFERENCES persons(partner_id);


--
-- TOC entry 2231 (class 2606 OID 27218)
-- Dependencies: 1642 2053 1629
-- Name: fke93344634da6f8bc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke93344634da6f8bc FOREIGN KEY (creator_id) REFERENCES persons(partner_id);


--
-- TOC entry 2232 (class 2606 OID 27223)
-- Dependencies: 1629 1642 2053
-- Name: fke93344636faaa615; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke93344636faaa615 FOREIGN KEY (deliverer_contact_id) REFERENCES persons(partner_id);


--
-- TOC entry 2229 (class 2606 OID 26881)
-- Dependencies: 1629 1638 2045
-- Name: fke933446370294164; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke933446370294164 FOREIGN KEY (deliverer_id) REFERENCES data_object_links(data_object_link_id);


--
-- TOC entry 2226 (class 2606 OID 26851)
-- Dependencies: 1619 1629 1992
-- Name: fke93344639f1800e1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke93344639f1800e1 FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2227 (class 2606 OID 26856)
-- Dependencies: 1629 1604 1968
-- Name: fke9334463d6755f5b; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke9334463d6755f5b FOREIGN KEY (receipt_cert_method_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2233 (class 2606 OID 27528)
-- Dependencies: 2051 1641 1629
-- Name: fke9334463f2569c14; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke9334463f2569c14 FOREIGN KEY (forwarder_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2228 (class 2606 OID 26861)
-- Dependencies: 1629 1604 1968
-- Name: fke9334463fe9be307; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke9334463fe9be307 FOREIGN KEY (receipt_cert_reason_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2065 (class 2606 OID 26441)
-- Dependencies: 1592 1942 1594
-- Name: fkefe6ccf38e8748f3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifier_applied_for_dot
    ADD CONSTRAINT fkefe6ccf38e8748f3 FOREIGN KEY (classifier_id) REFERENCES classifiers(classifier_id);


--
-- TOC entry 2066 (class 2606 OID 26446)
-- Dependencies: 1592 1948 1595
-- Name: fkefe6ccf3a44e2131; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifier_applied_for_dot
    ADD CONSTRAINT fkefe6ccf3a44e2131 FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2070 (class 2606 OID 26451)
-- Dependencies: 1594 1593 1936
-- Name: fkf7ea2a728508c4de; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT fkf7ea2a728508c4de FOREIGN KEY (classifier_group_id) REFERENCES classifier_groups(classifier_group_id);


--
-- TOC entry 2279 (class 2606 OID 27338)
-- Dependencies: 1615 1978 1641
-- Name: organizations_administration_address_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT organizations_administration_address_id_fkey FOREIGN KEY (administration_address_id) REFERENCES addresses(address_id) ON DELETE SET NULL;


--
-- TOC entry 2280 (class 2606 OID 27396)
-- Dependencies: 1604 1968 1641
-- Name: organizations_currency_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT organizations_currency_id_fkey FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2278 (class 2606 OID 27333)
-- Dependencies: 1641 1978 1615
-- Name: organizations_registration_address_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT organizations_registration_address_id_fkey FOREIGN KEY (registration_address_id) REFERENCES addresses(address_id) ON DELETE SET NULL;


--
-- TOC entry 2272 (class 2606 OID 27631)
-- Dependencies: 1642 2053 1637
-- Name: passports_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT passports_parent_id_fkey FOREIGN KEY (parent_id) REFERENCES persons(partner_id) ON DELETE CASCADE;


--
-- TOC entry 2077 (class 2606 OID 27671)
-- Dependencies: 2049 1599 1640
-- Name: pattern_mask_formats_owner_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pattern_mask_formats
    ADD CONSTRAINT pattern_mask_formats_owner_id_fkey FOREIGN KEY (owner_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2340 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: -
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2008-05-06 01:55:30

--
-- PostgreSQL database dump complete
--

