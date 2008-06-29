--
-- PostgreSQL database dump
--

-- Started on 2008-06-29 20:42:38

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 443 (class 2612 OID 24991)
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: -
--

--CREATE PROCEDURAL LANGUAGE plpgsql;


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 1640 (class 1259 OID 25369)
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
-- TOC entry 1675 (class 1259 OID 27829)
-- Dependencies: 6
-- Name: assembling_categories; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE assembling_categories (
    assembling_category_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0),
    category_code character varying(50) NOT NULL,
    category_name character varying(100) NOT NULL,
    description text
);


--
-- TOC entry 1678 (class 1259 OID 27890)
-- Dependencies: 1970 6
-- Name: assembling_schema_item_values; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE assembling_schema_item_values (
    item_value_id numeric(18,0) NOT NULL,
    item_id numeric(18,0) NOT NULL,
    min_constraint bytea,
    max_constraint bytea,
    virtual_product_id numeric(18,0) NOT NULL,
    quantity numeric(19,4) DEFAULT 1 NOT NULL
);


--
-- TOC entry 1677 (class 1259 OID 27870)
-- Dependencies: 1969 6
-- Name: assembling_schema_items; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE assembling_schema_items (
    item_id numeric(18,0) NOT NULL,
    assembling_schema_id numeric(18,0) NOT NULL,
    algorithm_id integer NOT NULL,
    message_code character varying(50) NOT NULL,
    message_text character varying(100) NOT NULL,
    min_selections integer,
    max_selections integer,
    quantity numeric(19,4) DEFAULT 1 NOT NULL,
    default_value bytea,
    data_type_id integer NOT NULL,
    description text
);


--
-- TOC entry 1676 (class 1259 OID 27843)
-- Dependencies: 1968 6
-- Name: assembling_schemas; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE assembling_schemas (
    product_id numeric(18,0) NOT NULL,
    category_id numeric(18,0) NOT NULL,
    schema_code character varying(50) NOT NULL,
    schema_name character varying(100) NOT NULL,
    is_obsolete boolean DEFAULT false NOT NULL,
    description text
);


--
-- TOC entry 1660 (class 1259 OID 26229)
-- Dependencies: 1965 1966 6
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
-- Dependencies: 6 1615
-- Name: breakpoint; Type: TYPE; Schema: public; Owner: -
--

/*CREATE TYPE breakpoint AS (
	func oid,
	linenumber integer,
	targetname text
);*/


--
-- TOC entry 1665 (class 1259 OID 26923)
-- Dependencies: 6
-- Name: business_partners; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE business_partners (
    partner_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0),
    id numeric(18,0)
);


--
-- TOC entry 1642 (class 1259 OID 25397)
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
-- TOC entry 1616 (class 1259 OID 24995)
-- Dependencies: 6
-- Name: classified_objects; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE classified_objects (
    classifier_id numeric(18,0) NOT NULL,
    classified_object_id numeric(18,0) NOT NULL,
    description text
);


--
-- TOC entry 1617 (class 1259 OID 25001)
-- Dependencies: 6
-- Name: classifier_applied_for_dot; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE classifier_applied_for_dot (
    classifier_id numeric(18,0) NOT NULL,
    data_object_type_id integer NOT NULL
);


--
-- TOC entry 1618 (class 1259 OID 25004)
-- Dependencies: 1948 6
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
-- TOC entry 1619 (class 1259 OID 25011)
-- Dependencies: 6
-- Name: classifiers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE classifiers (
    classifier_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0),
    classifier_code character varying(32) NOT NULL,
    classifier_name character varying(128) NOT NULL,
    description text
);


--
-- TOC entry 1659 (class 1259 OID 26202)
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
-- TOC entry 2430 (class 0 OID 0)
-- Dependencies: 1659
-- Name: COLUMN communication_contacts.communication_type_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN communication_contacts.communication_type_id IS 'Email (Work, Private), Phone (Work, Home, Fax), Mobile Phone (Work, Private), VoIP (SIP, H.323), Instant Communications (ICQ, Skype, Google Talk, MSN), Other';


--
-- TOC entry 1679 (class 1259 OID 27917)
-- Dependencies: 6
-- Name: complex_product_items; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE complex_product_items (
    complex_product_item_id numeric(18,0) NOT NULL,
    complex_product_id numeric(18,0) NOT NULL,
    product_id numeric(18,0) NOT NULL,
    quantity numeric(19,4) NOT NULL,
    unit_price numeric(19,4) NOT NULL,
    item_price numeric(19,4) NOT NULL,
    applied_algorithm_id integer NOT NULL,
    applied_value bytea,
    order_position integer NOT NULL,
    parent_id numeric(18,0)
);


--
-- TOC entry 1672 (class 1259 OID 27769)
-- Dependencies: 6
-- Name: complex_products; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE complex_products (
    product_id numeric(18,0) NOT NULL,
    applied_schema_id numeric(18,0) NOT NULL,
    product_name character varying(100) NOT NULL,
    product_code character varying(50) NOT NULL,
    measure_unit_id integer NOT NULL,
    sale_price numeric(19,4) NOT NULL
);


--
-- TOC entry 1658 (class 1259 OID 26159)
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
-- TOC entry 1641 (class 1259 OID 25387)
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
-- TOC entry 1643 (class 1259 OID 25432)
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
-- TOC entry 1661 (class 1259 OID 26260)
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
-- TOC entry 1663 (class 1259 OID 26333)
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
-- TOC entry 1620 (class 1259 OID 25017)
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
-- TOC entry 1621 (class 1259 OID 25023)
-- Dependencies: 1949 1950 1951 1952 1953 1954 6
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
-- TOC entry 1652 (class 1259 OID 25997)
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
-- TOC entry 1653 (class 1259 OID 26022)
-- Dependencies: 6
-- Name: delivery_certificate_serial_numbers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE delivery_certificate_serial_numbers (
    certificate_item_id numeric(18,0) NOT NULL,
    serial_number character varying(32) NOT NULL
);


--
-- TOC entry 1651 (class 1259 OID 25962)
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
-- TOC entry 1622 (class 1259 OID 25035)
-- Dependencies: 6
-- Name: enum_classes; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE enum_classes (
    enum_class_id integer NOT NULL,
    enum_class_name character varying(255) NOT NULL
);


--
-- TOC entry 310 (class 1247 OID 25040)
-- Dependencies: 6 1623
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
-- TOC entry 1646 (class 1259 OID 25718)
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
-- TOC entry 1645 (class 1259 OID 25611)
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
-- TOC entry 1650 (class 1259 OID 25939)
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
-- TOC entry 2431 (class 0 OID 0)
-- Dependencies: 1650
-- Name: COLUMN order_confirmation_items.parent_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN order_confirmation_items.parent_id IS 'order_confirmation_id';


--
-- TOC entry 1649 (class 1259 OID 25909)
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
-- TOC entry 1666 (class 1259 OID 26928)
-- Dependencies: 1967 6
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
    currency_id integer,
    is_active boolean DEFAULT true NOT NULL
);


--
-- TOC entry 1662 (class 1259 OID 26275)
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
-- TOC entry 2432 (class 0 OID 0)
-- Dependencies: 1662
-- Name: COLUMN passports.passport_type_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN passports.passport_type_id IS 'Passport, Identity Card, Driving License';


--
-- TOC entry 1624 (class 1259 OID 25041)
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
-- TOC entry 2433 (class 0 OID 0)
-- Dependencies: 1624
-- Name: COLUMN pattern_mask_formats.format_type; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN pattern_mask_formats.format_type IS 'D for DateFormatter;
N for NumberFormatter;
M for MaskFormatter.';


--
-- TOC entry 1667 (class 1259 OID 26936)
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
-- TOC entry 1657 (class 1259 OID 26146)
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
-- TOC entry 2434 (class 0 OID 0)
-- Dependencies: 1657
-- Name: COLUMN position_types.owner_type; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN position_types.owner_type IS 'P - Person, O - Organization';


--
-- TOC entry 1625 (class 1259 OID 25047)
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
-- TOC entry 1626 (class 1259 OID 25053)
-- Dependencies: 6
-- Name: product_suppliers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE product_suppliers (
    product_id numeric(18,0) NOT NULL,
    supplier_id numeric(18,0) NOT NULL,
    description text
);


--
-- TOC entry 1671 (class 1259 OID 27726)
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
-- Dependencies: 6 1628
-- Name: proxyinfo; Type: TYPE; Schema: public; Owner: -
--

/*CREATE TYPE proxyinfo AS (
	serverversionstr text,
	serverversionnum integer,
	proxyapiver integer,
	serverprocessid integer
);*/


--
-- TOC entry 1648 (class 1259 OID 25851)
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
-- TOC entry 1647 (class 1259 OID 25808)
-- Dependencies: 1964 6
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
-- TOC entry 2435 (class 0 OID 0)
-- Dependencies: 1647
-- Name: COLUMN purchase_orders.branch_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN purchase_orders.branch_id IS 'equals to Address_id';


--
-- TOC entry 1674 (class 1259 OID 27812)
-- Dependencies: 6
-- Name: real_products; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE real_products (
    product_id numeric(18,0) NOT NULL,
    simple_product_id numeric(18,0) NOT NULL
);


--
-- TOC entry 1655 (class 1259 OID 26074)
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
-- TOC entry 1656 (class 1259 OID 26099)
-- Dependencies: 6
-- Name: receipt_certificate_serial_numbers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE receipt_certificate_serial_numbers (
    certificate_item_id numeric(18,0) NOT NULL,
    serial_number character varying(32) NOT NULL
);


--
-- TOC entry 1654 (class 1259 OID 26037)
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
-- TOC entry 1681 (class 1259 OID 36198)
-- Dependencies: 6
-- Name: registration_codes; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE registration_codes (
    registration_code numeric(18,0) NOT NULL,
    email character varying(60)
);


--
-- TOC entry 1629 (class 1259 OID 25074)
-- Dependencies: 6
-- Name: resource_bundle; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE resource_bundle (
    resource_id integer NOT NULL,
    enum_class_id integer NOT NULL,
    enum_name character varying(64) NOT NULL
);


--
-- TOC entry 1630 (class 1259 OID 25077)
-- Dependencies: 1960 6
-- Name: sequence_identifiers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE sequence_identifiers (
    seq_id_key bigint NOT NULL,
    seq_id_name character varying(64) NOT NULL,
    seq_id_value numeric(38,0) DEFAULT 0 NOT NULL
);


--
-- TOC entry 1627 (class 1259 OID 25059)
-- Dependencies: 1955 1956 1957 1958 1959 6
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
-- Dependencies: 6 1631
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
-- TOC entry 1632 (class 1259 OID 25084)
-- Dependencies: 1962 1963 6
-- Name: users; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE users (
    user_id numeric(18,0) NOT NULL,
    version integer NOT NULL,
    user_name character varying(32) NOT NULL,
    email_address character varying(64) NOT NULL,
    user_password character varying(64) NOT NULL,
    system_password character varying(64),
    system_password_validity date,
    is_active boolean DEFAULT true NOT NULL,
    is_new boolean DEFAULT true NOT NULL,
    creation_time time with time zone NOT NULL,
    creator_id bigint,
    person_id numeric(18,0),
    description text,
    small_image_uri character varying(1024),
    medium_image_uri character varying(1024),
    user_uri character varying(1024),
    next_action_after_login character varying(1024),
    small_image oid,
    medium_image oid
);


--
-- TOC entry 1680 (class 1259 OID 36183)
-- Dependencies: 6
-- Name: users_organizations; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE users_organizations (
    user_id numeric(18,0) NOT NULL,
    organization_id numeric(18,0) NOT NULL,
    branch_id numeric(18,0)
);


--
-- TOC entry 331 (class 1247 OID 25094)
-- Dependencies: 6 1633
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
-- TOC entry 1673 (class 1259 OID 27796)
-- Dependencies: 6
-- Name: virtual_products; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE virtual_products (
    product_id numeric(18,0) NOT NULL,
    product_type character(2),
    parent_id numeric(18,0)
);


--
-- TOC entry 1670 (class 1259 OID 27708)
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
-- TOC entry 1644 (class 1259 OID 25489)
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
-- TOC entry 1668 (class 1259 OID 27550)
-- Dependencies: 6
-- Name: countries_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE countries_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2436 (class 0 OID 0)
-- Dependencies: 1668
-- Name: countries_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('countries_seq', 35, true);


--
-- TOC entry 1634 (class 1259 OID 25115)
-- Dependencies: 6
-- Name: data_object_type_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE data_object_type_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2437 (class 0 OID 0)
-- Dependencies: 1634
-- Name: data_object_type_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('data_object_type_seq', 95, true);


--
-- TOC entry 1635 (class 1259 OID 25117)
-- Dependencies: 6
-- Name: data_objects_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE data_objects_seq
    INCREMENT BY 1
    MAXVALUE 999999999999999999
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2438 (class 0 OID 0)
-- Dependencies: 1635
-- Name: data_objects_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('data_objects_seq', 2, true);


--
-- TOC entry 1636 (class 1259 OID 25119)
-- Dependencies: 6
-- Name: enum_classes_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE enum_classes_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2439 (class 0 OID 0)
-- Dependencies: 1636
-- Name: enum_classes_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('enum_classes_seq', 17, true);


--
-- TOC entry 1637 (class 1259 OID 25121)
-- Dependencies: 6
-- Name: pattern_mask_formats_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE pattern_mask_formats_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2440 (class 0 OID 0)
-- Dependencies: 1637
-- Name: pattern_mask_formats_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('pattern_mask_formats_seq', 59, true);


--
-- TOC entry 1638 (class 1259 OID 25123)
-- Dependencies: 6
-- Name: resource_bundle_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE resource_bundle_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2441 (class 0 OID 0)
-- Dependencies: 1638
-- Name: resource_bundle_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('resource_bundle_seq', 117, true);


--
-- TOC entry 1639 (class 1259 OID 25125)
-- Dependencies: 1630 6
-- Name: sequence_identifiers_seq_id_key_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE sequence_identifiers_seq_id_key_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2442 (class 0 OID 0)
-- Dependencies: 1639
-- Name: sequence_identifiers_seq_id_key_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE sequence_identifiers_seq_id_key_seq OWNED BY sequence_identifiers.seq_id_key;


--
-- TOC entry 2443 (class 0 OID 0)
-- Dependencies: 1639
-- Name: sequence_identifiers_seq_id_key_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('sequence_identifiers_seq_id_key_seq', 1, false);


--
-- TOC entry 1669 (class 1259 OID 27706)
-- Dependencies: 6
-- Name: warehouse_product_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE warehouse_product_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2444 (class 0 OID 0)
-- Dependencies: 1669
-- Name: warehouse_product_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('warehouse_product_seq', 13, true);


--
-- TOC entry 1664 (class 1259 OID 26921)
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
-- TOC entry 2445 (class 0 OID 0)
-- Dependencies: 1664
-- Name: xyz_id_sequence; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('xyz_id_sequence', 1, false);


--
-- TOC entry 1961 (class 2604 OID 25336)
-- Dependencies: 1639 1630
-- Name: seq_id_key; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE sequence_identifiers ALTER COLUMN seq_id_key SET DEFAULT nextval('sequence_identifiers_seq_id_key_seq'::regclass);


--
-- TOC entry 2386 (class 0 OID 25369)
-- Dependencies: 1640
-- Data for Name: addresses; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1206860833751, 1206833877407, 'addresstest', NULL, NULL, NULL, NULL, NULL);
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1208375541782, 1208326364860, 'administration address', 6, 1209062694832, NULL, NULL, NULL);
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1209393839117, 1209324665385, 'Branch1', NULL, NULL, NULL, NULL, NULL);
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1209582468591, 1209582459571, 'Junglata', NULL, NULL, NULL, NULL, NULL);
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1206861699860, 1206833877407, 'Branch X', NULL, NULL, NULL, NULL, NULL);
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1206910857538, 1206910833184, 'Domashen', NULL, NULL, NULL, NULL, NULL);
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1210419278241, 1209394438429, 'JU', NULL, NULL, NULL, NULL, NULL);
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1209222409189, 1209222047860, 'Sofia Centur', 5, NULL, NULL, NULL, NULL);
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1209222423971, 1209222047860, 'Holland, Hilversum', 5, 1209062694832, NULL, NULL, NULL);
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1210856490268, 1209222047860, 'Erricsson Building', NULL, NULL, NULL, NULL, NULL);
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1210856501325, 1209222047860, 'Na Pichkovci', NULL, NULL, NULL, NULL, NULL);
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1210856514214, 1209222047860, 'At The Circus', NULL, NULL, NULL, NULL, NULL);
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1211475534335, 1209222047860, 'BranchX', NULL, NULL, NULL, NULL, NULL);
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1211482665600, 1211482143869, 'Green branch', 6, 1209243602313, '1444', 'pa
pa', 'sdfsdf');
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1211537604719, 1211492793954, 'testbankbranch1', NULL, NULL, NULL, NULL, NULL);
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1211538274351, 1211492793954, 'branch2', NULL, NULL, NULL, NULL, NULL);
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1211990404064, 1211990399266, 'Central office', NULL, NULL, NULL, NULL, NULL);
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1212004334360, 1209582114840, 'Central office', NULL, NULL, NULL, NULL, NULL);
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1212005450940, 1211472878364, 'Address 1', NULL, NULL, NULL, NULL, NULL);
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1212035638798, 1212035635016, 'Branch 1', NULL, NULL, NULL, NULL, NULL);
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1213479859086, NULL, 'home', NULL, NULL, NULL, NULL, NULL);
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1213512226752, NULL, 'asdf', NULL, NULL, NULL, NULL, NULL);
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1213512850266, NULL, 'asdf', NULL, NULL, NULL, NULL, NULL);
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1213514640256, 1213512206579, 'Home address', NULL, NULL, NULL, NULL, NULL);
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1213514856758, 1213512206579, 'Address 1', NULL, NULL, NULL, NULL, NULL);
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1213514956074, 1213514952057, 'Central office', NULL, NULL, NULL, NULL, NULL);


--
-- TOC entry 2418 (class 0 OID 27829)
-- Dependencies: 1675
-- Data for Name: assembling_categories; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO assembling_categories (assembling_category_id, parent_id, category_code, category_name, description) VALUES (1213369003444, NULL, 'ac1', 'ac1', NULL);
INSERT INTO assembling_categories (assembling_category_id, parent_id, category_code, category_name, description) VALUES (1213369014076, NULL, 'ac2', 'ac2', NULL);
INSERT INTO assembling_categories (assembling_category_id, parent_id, category_code, category_name, description) VALUES (1213369024087, NULL, 'ac3', 'ac3', NULL);
INSERT INTO assembling_categories (assembling_category_id, parent_id, category_code, category_name, description) VALUES (1213369047349, 1213369003444, 'ac1.1', 'ac1.1', NULL);
INSERT INTO assembling_categories (assembling_category_id, parent_id, category_code, category_name, description) VALUES (1213369062217, 1213369003444, 'ac1.1.1', 'ac1.1.1', NULL);
INSERT INTO assembling_categories (assembling_category_id, parent_id, category_code, category_name, description) VALUES (1213369076486, 1213369003444, 'ac1.1.2', 'ac1.1.2', NULL);
INSERT INTO assembling_categories (assembling_category_id, parent_id, category_code, category_name, description) VALUES (1213369094667, 1213369003444, 'ac1.1.2.1', 'ac1.1.2.1', NULL);
INSERT INTO assembling_categories (assembling_category_id, parent_id, category_code, category_name, description) VALUES (1213369109811, 1213369014076, 'ac2.1', 'ac2.1', NULL);
INSERT INTO assembling_categories (assembling_category_id, parent_id, category_code, category_name, description) VALUES (1213369126523, 1213369024087, 'ac3.1', 'ac3.1', NULL);


--
-- TOC entry 2421 (class 0 OID 27890)
-- Dependencies: 1678
-- Data for Name: assembling_schema_item_values; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2420 (class 0 OID 27870)
-- Dependencies: 1677
-- Data for Name: assembling_schema_items; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2419 (class 0 OID 27843)
-- Dependencies: 1676
-- Data for Name: assembling_schemas; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2406 (class 0 OID 26229)
-- Dependencies: 1660
-- Data for Name: bank_details; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO bank_details (bank_detail_id, parent_id, is_default, currency_id, bank_id, bank_branch_id, bank_account, bank_contact_id, bic, iban, swift_code) VALUES (1209313835354, 1209222423971, true, NULL, 1209222047860, 1209222423971, NULL, NULL, NULL, NULL, NULL);
INSERT INTO bank_details (bank_detail_id, parent_id, is_default, currency_id, bank_id, bank_branch_id, bank_account, bank_contact_id, bic, iban, swift_code) VALUES (1211489882752, 1209222423971, false, 48, 1209324665385, 1209393839117, '432', NULL, NULL, '123', NULL);
INSERT INTO bank_details (bank_detail_id, parent_id, is_default, currency_id, bank_id, bank_branch_id, bank_account, bank_contact_id, bic, iban, swift_code) VALUES (1211537657497, 1211482665600, false, NULL, 1208326364860, 1211537604719, '234234234 sdfs fsfs', NULL, NULL, '123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123123123123o123i1po23ipo123ipo1i23po1i23po1i23po1i23po1i23poi131po23i1po23ipo12i3po1i23po1i23po1i3poi123', NULL);
INSERT INTO bank_details (bank_detail_id, parent_id, is_default, currency_id, bank_id, bank_branch_id, bank_account, bank_contact_id, bic, iban, swift_code) VALUES (1211538469688, 1211482665600, false, 48, 1211492793954, 1211537604719, '123', 1206833877407, '123', '123', '123');
INSERT INTO bank_details (bank_detail_id, parent_id, is_default, currency_id, bank_id, bank_branch_id, bank_account, bank_contact_id, bic, iban, swift_code) VALUES (1211575685798, 1209222423971, false, NULL, 1211492793954, 1211538274351, NULL, 1211472798404, NULL, NULL, NULL);
INSERT INTO bank_details (bank_detail_id, parent_id, is_default, currency_id, bank_id, bank_branch_id, bank_account, bank_contact_id, bic, iban, swift_code) VALUES (1212035661315, 1212035638798, false, 50, 1209222047860, 1209222423971, NULL, 1211472878364, NULL, NULL, NULL);
INSERT INTO bank_details (bank_detail_id, parent_id, is_default, currency_id, bank_id, bank_branch_id, bank_account, bank_contact_id, bic, iban, swift_code) VALUES (1213514894852, 1213514856758, false, NULL, 1208326364860, 1208375541782, NULL, NULL, NULL, NULL, NULL);


--
-- TOC entry 2410 (class 0 OID 26923)
-- Dependencies: 1665
-- Data for Name: business_partners; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1210418654556, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1206833877407, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1206862211011, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1206910833184, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1208326364860, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1209222047860, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1210419431552, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1210505228236, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1211472752091, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1211472798404, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1211472878364, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1211475930558, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1211482143869, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1211483549670, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1211492793954, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1211990399266, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1212035635016, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1209324665385, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1213474710565, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1213474965974, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1209394343621, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1209394438429, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1213476155246, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1209582114840, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1209582459571, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1213480193688, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1209582555898, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1209585931126, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1213512206579, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1213514952057, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1213963345878, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1214287428969, NULL, NULL);


--
-- TOC entry 2388 (class 0 OID 25397)
-- Dependencies: 1642
-- Data for Name: cities; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO cities (city_id, country_id, city_name, postal_code, city_code, city_phone_code, description) VALUES (1209062694832, 5, 'Sofia', NULL, NULL, NULL, NULL);
INSERT INTO cities (city_id, country_id, city_name, postal_code, city_code, city_phone_code, description) VALUES (1209243602313, 6, 'New York', NULL, NULL, NULL, NULL);
INSERT INTO cities (city_id, country_id, city_name, postal_code, city_code, city_phone_code, description) VALUES (1211992678579, 5, 'Varna', NULL, NULL, NULL, NULL);
INSERT INTO cities (city_id, country_id, city_name, postal_code, city_code, city_phone_code, description) VALUES (1211992723142, 17, 'Athens', NULL, NULL, NULL, NULL);
INSERT INTO cities (city_id, country_id, city_name, postal_code, city_code, city_phone_code, description) VALUES (1213963338689, 5, 'Shumen', '9700', NULL, '054', NULL);


--
-- TOC entry 2372 (class 0 OID 24995)
-- Dependencies: 1616
-- Data for Name: classified_objects; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO classified_objects (classifier_id, classified_object_id, description) VALUES (1211229847860, 1209582555898, NULL);
INSERT INTO classified_objects (classifier_id, classified_object_id, description) VALUES (1211229847860, 1208326364860, NULL);
INSERT INTO classified_objects (classifier_id, classified_object_id, description) VALUES (1211229847860, 1209324665385, NULL);
INSERT INTO classified_objects (classifier_id, classified_object_id, description) VALUES (1211229847860, 1211492793954, NULL);
INSERT INTO classified_objects (classifier_id, classified_object_id, description) VALUES (1213515923514, 1214577908205, NULL);
INSERT INTO classified_objects (classifier_id, classified_object_id, description) VALUES (1211229847860, 1213515932561, NULL);
INSERT INTO classified_objects (classifier_id, classified_object_id, description) VALUES (1214578135971, 1213514952057, NULL);
INSERT INTO classified_objects (classifier_id, classified_object_id, description) VALUES (1214582644985, 1214287428969, NULL);
INSERT INTO classified_objects (classifier_id, classified_object_id, description) VALUES (1214748427158, 1210856702896, NULL);
INSERT INTO classified_objects (classifier_id, classified_object_id, description) VALUES (1214749012672, 1211476820946, NULL);


--
-- TOC entry 2373 (class 0 OID 25001)
-- Dependencies: 1617
-- Data for Name: classifier_applied_for_dot; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO classifier_applied_for_dot (classifier_id, data_object_type_id) VALUES (1213515923514, 36);
INSERT INTO classifier_applied_for_dot (classifier_id, data_object_type_id) VALUES (1214578135971, 1);


--
-- TOC entry 2374 (class 0 OID 25004)
-- Dependencies: 1618
-- Data for Name: classifier_groups; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO classifier_groups (classifier_group_id, parent_id, is_system_group, classifier_group_code, classifier_group_name, description) VALUES (1211131456610, NULL, false, 'system', 'System', NULL);
INSERT INTO classifier_groups (classifier_group_id, parent_id, is_system_group, classifier_group_code, classifier_group_name, description) VALUES (1214577893297, NULL, false, 'tmp', 'temp', NULL);


--
-- TOC entry 2375 (class 0 OID 25011)
-- Dependencies: 1619
-- Data for Name: classifiers; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO classifiers (classifier_id, parent_id, classifier_code, classifier_name, description) VALUES (1211229847860, 1211131456610, 'bank', 'Bank', NULL);
INSERT INTO classifiers (classifier_id, parent_id, classifier_code, classifier_name, description) VALUES (1213515923514, 1211131456610, 'provider', 'Provider', NULL);
INSERT INTO classifiers (classifier_id, parent_id, classifier_code, classifier_name, description) VALUES (1214578135971, 1214577893297, 'tmp', 'tmp', NULL);
INSERT INTO classifiers (classifier_id, parent_id, classifier_code, classifier_name, description) VALUES (1214582644985, 1211131456610, 'tsts', 'tstas', NULL);
INSERT INTO classifiers (classifier_id, parent_id, classifier_code, classifier_name, description) VALUES (1214748427158, 1214577893297, '44', 'Classified', NULL);
INSERT INTO classifiers (classifier_id, parent_id, classifier_code, classifier_name, description) VALUES (1214749012672, 1214577893297, '55', 'ClassX', NULL);


--
-- TOC entry 2405 (class 0 OID 26202)
-- Dependencies: 1659
-- Data for Name: communication_contacts; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO communication_contacts (communication_contact_id, parent_id, communication_type_id, communication_value, contact_person_id) VALUES (1208205209751, 1206861699860, 46, '344649595', NULL);
INSERT INTO communication_contacts (communication_contact_id, parent_id, communication_type_id, communication_value, contact_person_id) VALUES (1208206388985, 1206861699860, 44, '53453', NULL);
INSERT INTO communication_contacts (communication_contact_id, parent_id, communication_type_id, communication_value, contact_person_id) VALUES (1208206890876, 1206861699860, 44, 'a', NULL);
INSERT INTO communication_contacts (communication_contact_id, parent_id, communication_type_id, communication_value, contact_person_id) VALUES (1208207298391, 1206861699860, 47, 'aaap', 1207689167157);
INSERT INTO communication_contacts (communication_contact_id, parent_id, communication_type_id, communication_value, contact_person_id) VALUES (1209310378705, 1209222423971, 46, '65464', NULL);
INSERT INTO communication_contacts (communication_contact_id, parent_id, communication_type_id, communication_value, contact_person_id) VALUES (1209310364376, 1209222423971, 44, '876', NULL);
INSERT INTO communication_contacts (communication_contact_id, parent_id, communication_type_id, communication_value, contact_person_id) VALUES (1209330251297, 1209222409189, 44, '999', NULL);
INSERT INTO communication_contacts (communication_contact_id, parent_id, communication_type_id, communication_value, contact_person_id) VALUES (1211486935439, 1209222423971, 44, '1234', 1211486863532);
INSERT INTO communication_contacts (communication_contact_id, parent_id, communication_type_id, communication_value, contact_person_id) VALUES (1211486998659, 1209582468591, 45, '543', NULL);
INSERT INTO communication_contacts (communication_contact_id, parent_id, communication_type_id, communication_value, contact_person_id) VALUES (1211539127406, 1211482665600, 46, '34r34435345353434', NULL);
INSERT INTO communication_contacts (communication_contact_id, parent_id, communication_type_id, communication_value, contact_person_id) VALUES (1211539394415, 1211482665600, 45, 'fghfgh', NULL);
INSERT INTO communication_contacts (communication_contact_id, parent_id, communication_type_id, communication_value, contact_person_id) VALUES (1211539407937, 1211482665600, 46, 'sdf34234234', 1211482680727);
INSERT INTO communication_contacts (communication_contact_id, parent_id, communication_type_id, communication_value, contact_person_id) VALUES (1211539412384, 1211482665600, 47, '234234234', 1211482680727);
INSERT INTO communication_contacts (communication_contact_id, parent_id, communication_type_id, communication_value, contact_person_id) VALUES (1212005477755, 1212005450940, 44, 'uuu', NULL);
INSERT INTO communication_contacts (communication_contact_id, parent_id, communication_type_id, communication_value, contact_person_id) VALUES (1213514905338, 1213514856758, 44, 'fds', NULL);


--
-- TOC entry 2422 (class 0 OID 27917)
-- Dependencies: 1679
-- Data for Name: complex_product_items; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2415 (class 0 OID 27769)
-- Dependencies: 1672
-- Data for Name: complex_products; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2404 (class 0 OID 26159)
-- Dependencies: 1658
-- Data for Name: contact_persons; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO contact_persons (contact_person_id, parent_id, position_type_id, contact_id) VALUES (1207689167157, 1209222423971, 1208929402376, 1206862211011);
INSERT INTO contact_persons (contact_person_id, parent_id, position_type_id, contact_id) VALUES (1211470998665, 1210856514214, NULL, 1206833877407);
INSERT INTO contact_persons (contact_person_id, parent_id, position_type_id, contact_id) VALUES (1211472755765, 1210856490268, NULL, 1211472752091);
INSERT INTO contact_persons (contact_person_id, parent_id, position_type_id, contact_id) VALUES (1211472802439, 1209222409189, NULL, 1211472798404);
INSERT INTO contact_persons (contact_person_id, parent_id, position_type_id, contact_id) VALUES (1211472830461, 1209222409189, NULL, 1209582459571);
INSERT INTO contact_persons (contact_person_id, parent_id, position_type_id, contact_id) VALUES (1211475934958, 1209222409189, NULL, 1211475930558);
INSERT INTO contact_persons (contact_person_id, parent_id, position_type_id, contact_id) VALUES (1211476817202, 1210856501325, NULL, 1211475930558);
INSERT INTO contact_persons (contact_person_id, parent_id, position_type_id, contact_id) VALUES (1211482680727, 1211482665600, NULL, 1209582555898);
INSERT INTO contact_persons (contact_person_id, parent_id, position_type_id, contact_id) VALUES (1211538197159, 1211537604719, NULL, 1206833877407);
INSERT INTO contact_persons (contact_person_id, parent_id, position_type_id, contact_id) VALUES (1211538285108, 1211538274351, NULL, 1211472798404);
INSERT INTO contact_persons (contact_person_id, parent_id, position_type_id, contact_id) VALUES (1211538293869, 1211538274351, NULL, 1209582555898);
INSERT INTO contact_persons (contact_person_id, parent_id, position_type_id, contact_id) VALUES (1211539428093, 1211482665600, NULL, 1211472878364);
INSERT INTO contact_persons (contact_person_id, parent_id, position_type_id, contact_id) VALUES (1211486863532, 1209222423971, 1208595267969, 1211475930558);
INSERT INTO contact_persons (contact_person_id, parent_id, position_type_id, contact_id) VALUES (1211576215909, 1209222423971, 1208597113782, 1211472878364);
INSERT INTO contact_persons (contact_person_id, parent_id, position_type_id, contact_id) VALUES (1211990419018, 1211990404064, 1208597113782, 1209582459571);
INSERT INTO contact_persons (contact_person_id, parent_id, position_type_id, contact_id) VALUES (1212005469332, 1212005450940, 1209325564201, 1211472752091);
INSERT INTO contact_persons (contact_person_id, parent_id, position_type_id, contact_id) VALUES (1212035676300, 1212035638798, 1208597113782, 1209582555898);
INSERT INTO contact_persons (contact_person_id, parent_id, position_type_id, contact_id) VALUES (1212956867269, 1211475534335, 1208597113782, 1211475930558);
INSERT INTO contact_persons (contact_person_id, parent_id, position_type_id, contact_id) VALUES (1213514652038, 1213514640256, 1209325564201, 1211472752091);
INSERT INTO contact_persons (contact_person_id, parent_id, position_type_id, contact_id) VALUES (1213514968013, 1213514956074, 1208597113782, 1211472798404);


--
-- TOC entry 2387 (class 0 OID 25387)
-- Dependencies: 1641
-- Data for Name: countries; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO countries (country_id, country_name, country_code_a2, country_code_a3, country_code_n3, country_phone_code, currency_id, description) VALUES (5, 'Bulgaria', NULL, NULL, NULL, NULL, 49, NULL);
INSERT INTO countries (country_id, country_name, country_code_a2, country_code_a3, country_code_n3, country_phone_code, currency_id, description) VALUES (6, 'United States', NULL, NULL, NULL, NULL, 50, NULL);
INSERT INTO countries (country_id, country_name, country_code_a2, country_code_a3, country_code_n3, country_phone_code, currency_id, description) VALUES (8, '9t2hlwya49', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO countries (country_id, country_name, country_code_a2, country_code_a3, country_code_n3, country_phone_code, currency_id, description) VALUES (7, '0xgthk9door', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO countries (country_id, country_name, country_code_a2, country_code_a3, country_code_n3, country_phone_code, currency_id, description) VALUES (16, 'Bongo-Bongo', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO countries (country_id, country_name, country_code_a2, country_code_a3, country_code_n3, country_phone_code, currency_id, description) VALUES (17, 'Greece', NULL, 'GR ', NULL, NULL, NULL, NULL);
INSERT INTO countries (country_id, country_name, country_code_a2, country_code_a3, country_code_n3, country_phone_code, currency_id, description) VALUES (18, 'Turkey', NULL, 'TT2', 'TT ', '13312', NULL, NULL);
INSERT INTO countries (country_id, country_name, country_code_a2, country_code_a3, country_code_n3, country_phone_code, currency_id, description) VALUES (21, 'China', NULL, NULL, NULL, '(+359)', NULL, NULL);
INSERT INTO countries (country_id, country_name, country_code_a2, country_code_a3, country_code_n3, country_phone_code, currency_id, description) VALUES (22, 'x91iyx8a5e', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO countries (country_id, country_name, country_code_a2, country_code_a3, country_code_n3, country_phone_code, currency_id, description) VALUES (23, 'kxdjzugi05', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO countries (country_id, country_name, country_code_a2, country_code_a3, country_code_n3, country_phone_code, currency_id, description) VALUES (24, 'c5n9ggkh6o', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO countries (country_id, country_name, country_code_a2, country_code_a3, country_code_n3, country_phone_code, currency_id, description) VALUES (25, 'n6qiqp5m9j', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO countries (country_id, country_name, country_code_a2, country_code_a3, country_code_n3, country_phone_code, currency_id, description) VALUES (26, 'xeatj9wqgi', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO countries (country_id, country_name, country_code_a2, country_code_a3, country_code_n3, country_phone_code, currency_id, description) VALUES (28, '1rtfgrpsdw', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO countries (country_id, country_name, country_code_a2, country_code_a3, country_code_n3, country_phone_code, currency_id, description) VALUES (29, '2cq9askhzi', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO countries (country_id, country_name, country_code_a2, country_code_a3, country_code_n3, country_phone_code, currency_id, description) VALUES (30, '39ojqk7qg3', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO countries (country_id, country_name, country_code_a2, country_code_a3, country_code_n3, country_phone_code, currency_id, description) VALUES (31, '03dw80ll6w', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO countries (country_id, country_name, country_code_a2, country_code_a3, country_code_n3, country_phone_code, currency_id, description) VALUES (32, 'ine6yy5aqy', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO countries (country_id, country_name, country_code_a2, country_code_a3, country_code_n3, country_phone_code, currency_id, description) VALUES (33, 'ywvrt185yh', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO countries (country_id, country_name, country_code_a2, country_code_a3, country_code_n3, country_phone_code, currency_id, description) VALUES (34, 'sg8cor1swv', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO countries (country_id, country_name, country_code_a2, country_code_a3, country_code_n3, country_phone_code, currency_id, description) VALUES (35, 'e7j26zcvbn', NULL, NULL, NULL, NULL, NULL, NULL);


--
-- TOC entry 2389 (class 0 OID 25432)
-- Dependencies: 1643
-- Data for Name: currencies; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2407 (class 0 OID 26260)
-- Dependencies: 1661
-- Data for Name: data_object_details; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2409 (class 0 OID 26333)
-- Dependencies: 1663
-- Data for Name: data_object_links; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2376 (class 0 OID 25017)
-- Dependencies: 1620
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
INSERT INTO data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) VALUES (61, 'com.cosmos.acacia.crm.data.ClassifierGroup', NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) VALUES (62, 'com.cosmos.acacia.crm.data.Classifier', NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) VALUES (72, 'com.cosmos.acacia.crm.data.assembling.AssemblingCategory', NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) VALUES (89, 'com.cosmos.acacia.crm.data.Invoice', NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) VALUES (95, 'com.cosmos.acacia.crm.data.User', NULL, NULL, NULL, NULL, NULL);


--
-- TOC entry 2377 (class 0 OID 25023)
-- Dependencies: 1621
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
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210418679567, 2, 37, '2008-05-10 14:24:39.564+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
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
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208523315311, 1, 46, '2008-04-18 15:55:15.31+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208523480799, 2, 46, '2008-04-18 15:58:00.796+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1205859152950, 4, 36, '2008-03-18 18:52:32.947+02', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208528961652, 2, 46, '2008-04-18 17:29:21.648+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208529136169, 2, 46, '2008-04-18 17:32:16.164+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
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
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210419227116, 2, 40, '2008-05-10 14:33:47.077+03', 0, 0, true, false, false, false, false, 1210419217337, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210419300490, 1, 40, '2008-05-10 14:35:00.448+03', 0, 0, false, false, false, false, false, 1210419291313, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1206861699860, 2, 40, '2008-03-30 10:21:39.687+03', 0, 0, false, false, false, false, false, 1206833877407, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1207689167157, 3, 43, '2008-04-09 00:12:47+03', 0, 0, false, false, false, false, false, 1206861699860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208375541782, 3, 40, '2008-04-16 22:52:21.609+03', 0, 0, false, false, false, false, false, 1208326364860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210419291313, 2, 1, '2008-05-10 14:34:51.305+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208532522729, 2, 46, '2008-04-18 18:28:42.713+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1, 5, 35, '2001-12-23 21:39:53.662522+02', 1, 1, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208640799735, 2, 53, '2008-04-20 00:33:19.562+03', 0, 0, true, false, false, false, false, 1208375541782, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210417990209, 2, 37, '2008-05-10 14:13:10.203+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210419278241, 1, 40, '2008-05-10 14:34:38.201+03', 0, 0, false, false, false, false, false, 1209394438429, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208723817938, 2, 58, '2008-04-20 23:36:57.781+03', 0, 0, false, false, false, false, false, 1206833877407, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209291672387, 1, 45, '2008-04-27 13:21:12.343+03', 0, 0, false, false, false, false, false, 1209291672166, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208775156424, 2, 35, '2008-04-21 13:52:36.42+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208775396075, 3, 35, '2008-04-21 13:56:36.073+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209291716374, 1, 40, '2008-04-27 13:21:56.343+03', 0, 0, false, false, false, false, false, 1209291716279, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210419217337, 2, 1, '2008-05-10 14:33:37.333+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209291716610, 1, 45, '2008-04-27 13:21:56.546+03', 0, 0, false, false, false, false, false, 1209291716374, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208927516422, 1, 47, '2008-04-23 08:11:56.421+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209062694832, 1, 59, '2008-04-24 21:44:54.831+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209222409189, 5, 40, '2008-04-26 18:06:49.156+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209306831803, 2, 37, '2008-04-27 17:33:51.796+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209222423971, 9, 40, '2008-04-26 18:07:03.812+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
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
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209288962569, 5, 35, '2008-04-27 12:36:02.563+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209291672055, 2, 1, '2008-04-27 13:21:12.046+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209306856555, 2, 40, '2008-04-27 17:34:16.328+03', 0, 0, true, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208867479790, 9, 35, '2008-04-22 15:31:19.788+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208867495266, 6, 35, '2008-04-22 15:31:35.263+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
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
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209222047860, 8, 1, '2008-04-26 18:00:47.843+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
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
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208929402376, 3, 47, '2008-04-23 08:43:22.375+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
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
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209324665385, 2, 1, '2008-04-27 22:31:05.383+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
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
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210418654556, 1, 37, '2008-05-10 14:24:14.544+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209396155129, 2, 40, '2008-04-28 18:22:34.925+03', 0, 0, true, false, false, false, false, 1209394438429, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209394438429, 4, 1, '2008-04-28 17:53:58.416+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209406603688, 2, 1, '2008-04-28 21:16:43.687+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209393778001, 1, 43, '2008-04-28 17:42:57.962+03', 0, 0, false, false, false, false, false, 1209393762536, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209393839117, 1, 40, '2008-04-28 17:43:59.108+03', 0, 0, false, false, false, false, false, 1209324665385, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209393842895, 2, 58, '2008-04-28 17:44:02.885+03', 0, 0, true, false, false, false, false, 1209393667067, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209393762536, 3, 40, '2008-04-28 17:42:42.499+03', 0, 0, true, false, false, false, false, 1209393667067, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209393898024, 2, 37, '2008-04-28 17:44:58.014+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209393978637, 3, 37, '2008-04-28 17:46:18.626+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209394343621, 2, 1, '2008-04-28 17:52:23.609+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209585016376, 2, 37, '2008-04-30 22:50:16.375+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209582346354, 2, 37, '2008-04-30 22:05:46.35+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209396227704, 2, 58, '2008-04-28 18:23:47.546+03', 0, 0, true, false, false, false, false, 1209393667067, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209396590448, 1, 43, '2008-04-28 18:29:50.413+03', 0, 0, false, false, false, false, false, 1209396580958, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209582523616, 3, 37, '2008-04-30 22:08:43.608+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209396580958, 2, 40, '2008-04-28 18:29:40.955+03', 0, 0, true, false, false, false, false, 1209393667067, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209585311094, 2, 37, '2008-04-30 22:55:11.093+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209408906002, 1, 59, '2008-04-28 21:55:06+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210419222991, 2, 40, '2008-05-10 14:33:42.918+03', 0, 0, true, false, false, false, false, 1210419217337, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
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
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210419295848, 1, 40, '2008-05-10 14:34:55.805+03', 0, 0, false, false, false, false, false, 1210419291313, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
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
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209403563985, 11, 35, '2008-04-28 20:26:03.984+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209931667245, 2, 60, '2008-05-04 23:07:47.241+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209642675564, 2, 37, '2008-05-01 14:51:15.562+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209668108613, 2, 37, '2008-05-01 21:55:08.609+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209668486845, 2, 37, '2008-05-01 22:01:26.843+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209585033361, 2, 37, '2008-04-30 22:50:33.359+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209642901797, 2, 37, '2008-05-01 14:55:01.796+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209582114840, 4, 1, '2008-04-30 22:01:54.837+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209584841985, 4, 37, '2008-04-30 22:47:21.984+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209667724938, 2, 37, '2008-05-01 21:48:44.937+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209668002720, 2, 37, '2008-05-01 21:53:22.718+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210027840944, 1, 46, '2008-05-06 01:50:40.941+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209668497503, 2, 37, '2008-05-01 22:01:37.50+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209669000469, 2, 37, '2008-05-01 22:10:00.453+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209669503329, 2, 37, '2008-05-01 22:18:23.328+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209670535657, 2, 37, '2008-05-01 22:35:35.656+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209670543580, 2, 37, '2008-05-01 22:35:43.578+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209671274641, 2, 37, '2008-05-01 22:47:54.625+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209668088034, 3, 37, '2008-05-01 21:54:48.031+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209930745031, 2, 60, '2008-05-04 22:52:25.029+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210419364076, 1, 40, '2008-05-10 14:36:03.957+03', 0, 0, false, false, false, false, false, 1210419360879, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210419360879, 2, 37, '2008-05-10 14:36:00.868+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210419431552, 1, 37, '2008-05-10 14:37:11.539+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210419439231, 2, 37, '2008-05-10 14:37:19.217+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209407225556, 5, 35, '2008-04-28 21:27:05.555+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210419628702, 1, 40, '2008-05-10 14:40:28.618+03', 0, 0, false, false, false, false, false, 1210419625451, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210419625451, 2, 1, '2008-05-10 14:40:25.436+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210505180329, 2, 37, '2008-05-11 14:26:20.328+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210505228236, 2, 37, '2008-05-11 14:27:08.234+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210856514214, 1, 40, '2008-05-15 16:01:54.178+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210518707184, 2, 35, '2008-05-11 18:11:47.177+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211481202527, 2, 47, '2008-05-22 21:33:22.52+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211101190532, 2, 61, '2008-05-18 11:59:50.531+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211131483517, 2, 62, '2008-05-18 20:24:43.468+03', 0, 0, true, false, false, false, false, 1211131456610, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211470998665, 1, 43, '2008-05-22 18:43:17.215+03', 0, 0, false, false, false, false, false, 1210856514214, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210518215623, 3, 35, '2008-05-11 18:03:35.618+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210518130325, 2, 35, '2008-05-11 18:02:10.321+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210518257129, 6, 35, '2008-05-11 18:04:17.122+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210513246946, 5, 35, '2008-05-11 16:40:46.944+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208531803052, 3, 46, '2008-04-18 18:16:43.046+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210856556559, 3, 60, '2008-05-15 16:02:36.554+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211472752091, 1, 37, '2008-05-22 19:12:32.09+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210620273286, 1, 35, '2008-05-12 22:24:33.285+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211472755765, 1, 43, '2008-05-22 19:12:35.551+03', 0, 0, false, false, false, false, false, 1210856490268, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210620471938, 1, 35, '2008-05-12 22:27:51.936+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210620499609, 2, 35, '2008-05-12 22:28:19.606+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210518068080, 2, 35, '2008-05-11 18:01:08.077+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210856490268, 2, 40, '2008-05-15 16:01:30.231+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1208791743432, 12, 35, '2008-04-21 18:29:03.431+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210513038084, 3, 35, '2008-05-11 16:37:18.068+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210620733627, 2, 35, '2008-05-12 22:32:13.623+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209407251282, 11, 35, '2008-04-28 21:27:31.28+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210621181773, 1, 35, '2008-05-12 22:39:41.768+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210702269535, 1, 35, '2008-05-13 21:11:09.532+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210702277197, 1, 35, '2008-05-13 21:11:17.195+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210702289908, 1, 35, '2008-05-13 21:11:29.903+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210702309820, 1, 35, '2008-05-13 21:11:49.813+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210702323310, 1, 35, '2008-05-13 21:12:03.30+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210702341065, 1, 35, '2008-05-13 21:12:21.055+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210702350546, 1, 35, '2008-05-13 21:12:30.535+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210702358576, 1, 35, '2008-05-13 21:12:38.564+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210702302770, 2, 35, '2008-05-13 21:11:42.764+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210702416576, 2, 35, '2008-05-13 21:13:36.558+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210702404826, 2, 35, '2008-05-13 21:13:24.809+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210702396345, 2, 35, '2008-05-13 21:13:16.329+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210702388134, 2, 35, '2008-05-13 21:13:08.119+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210702376353, 2, 35, '2008-05-13 21:12:56.339+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210702368093, 2, 35, '2008-05-13 21:12:48.08+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210856501325, 1, 40, '2008-05-15 16:01:41.287+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211472798404, 1, 37, '2008-05-22 19:13:18.401+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211472802439, 1, 43, '2008-05-22 19:13:22.375+03', 0, 0, false, false, false, false, false, 1209222409189, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211472830461, 1, 43, '2008-05-22 19:13:50.423+03', 0, 0, false, false, false, false, false, 1209222409189, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210856702896, 3, 60, '2008-05-15 16:05:02.89+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211472939069, 2, 60, '2008-05-22 19:15:39.061+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210856473268, 2, 40, '2008-05-15 16:01:12.832+03', 0, 0, true, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211475534335, 1, 40, '2008-05-22 19:58:54.061+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211475930558, 1, 37, '2008-05-22 20:05:30.553+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211475934958, 1, 43, '2008-05-22 20:05:34.937+03', 0, 0, false, false, false, false, false, 1209222409189, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211476817202, 1, 43, '2008-05-22 20:20:17.181+03', 0, 0, false, false, false, false, false, 1210856501325, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211481085303, 2, 47, '2008-05-22 21:31:25.297+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211482953453, 2, 40, '2008-05-22 22:02:33.429+03', 0, 0, true, false, false, false, false, 1211482143869, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211481269391, 2, 47, '2008-05-22 21:34:29.382+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211482143869, 1, 1, '2008-05-22 21:49:03.859+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211482665600, 2, 40, '2008-05-22 21:57:45.563+03', 0, 0, false, false, false, false, false, 1211482143869, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211482943730, 2, 40, '2008-05-22 22:02:23.699+03', 0, 0, true, false, false, false, false, 1211482143869, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211483549670, 1, 1, '2008-05-22 22:12:29.655+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211472885184, 2, 43, '2008-05-22 19:14:45.143+03', 0, 0, true, false, false, false, false, 1209222423971, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211486935439, 1, 45, '2008-05-22 23:08:55.421+03', 0, 0, false, false, false, false, false, 1209222423971, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211486998659, 1, 45, '2008-05-22 23:09:58.656+03', 0, 0, false, false, false, false, false, 1209582468591, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211489882752, 1, 53, '2008-05-22 23:58:02.734+03', 0, 0, false, false, false, false, false, 1209222423971, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211492793954, 1, 1, '2008-05-23 00:46:33.953+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211537657497, 2, 53, '2008-05-23 13:14:17.399+03', 0, 0, false, false, false, false, false, 1211482665600, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211538197159, 1, 43, '2008-05-23 13:23:17.088+03', 0, 0, false, false, false, false, false, 1211537604719, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211537604719, 2, 40, '2008-05-23 13:13:23.494+03', 0, 0, false, false, false, false, false, 1211492793954, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211538285108, 1, 43, '2008-05-23 13:24:45.065+03', 0, 0, false, false, false, false, false, 1211538274351, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211482680727, 2, 43, '2008-05-22 21:58:00.694+03', 0, 0, false, false, false, false, false, 1211482665600, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211472878364, 2, 37, '2008-05-22 19:14:38.358+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211486863532, 4, 43, '2008-05-22 23:07:43.265+03', 0, 0, false, false, false, false, false, 1209222423971, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211131456610, 2, 61, '2008-05-18 20:24:16.609+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211229847860, 3, 62, '2008-05-19 23:44:07.765+03', 0, 0, false, false, false, false, false, 1211131456610, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210702283927, 2, 35, '2008-05-13 21:11:23.924+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210702297698, 2, 35, '2008-05-13 21:11:37.693+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210702318119, 2, 35, '2008-05-13 21:11:58.111+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1210519124507, 4, 35, '2008-05-11 18:18:44.506+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211476820946, 3, 60, '2008-05-22 20:20:20.941+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211538293869, 1, 43, '2008-05-23 13:24:53.829+03', 0, 0, false, false, false, false, false, 1211538274351, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211538274351, 2, 40, '2008-05-23 13:24:34.28+03', 0, 0, false, false, false, false, false, 1211492793954, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211538469688, 2, 53, '2008-05-23 13:27:49.678+03', 0, 0, false, false, false, false, false, 1211482665600, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211539127406, 2, 45, '2008-05-23 13:38:47.359+03', 0, 0, false, false, false, false, false, 1211482665600, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211990404064, 1, 40, '2008-05-28 19:00:04.046+03', 0, 0, false, false, false, false, false, 1211990399266, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211539121113, 2, 43, '2008-05-23 13:38:41.029+03', 0, 0, true, false, false, false, false, 1211482665600, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211539088020, 2, 45, '2008-05-23 13:38:07.954+03', 0, 0, true, false, false, false, false, 1211482665600, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211539357873, 2, 45, '2008-05-23 13:42:37.793+03', 0, 0, true, false, false, false, false, 1211482665600, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211539394415, 1, 45, '2008-05-23 13:43:14.365+03', 0, 0, false, false, false, false, false, 1211482665600, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211539385572, 2, 43, '2008-05-23 13:43:05.485+03', 0, 0, true, false, false, false, false, 1211482665600, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211539407937, 1, 45, '2008-05-23 13:43:27.886+03', 0, 0, false, false, false, false, false, 1211482665600, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211539412384, 1, 45, '2008-05-23 13:43:32.333+03', 0, 0, false, false, false, false, false, 1211482665600, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211539428093, 2, 43, '2008-05-23 13:43:48.038+03', 0, 0, false, false, false, false, false, 1211482665600, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211539018121, 3, 45, '2008-05-23 13:36:58.035+03', 0, 0, true, false, false, false, false, 1211482665600, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211539849291, 2, 45, '2008-05-23 13:50:49.205+03', 0, 0, true, false, false, false, false, 1211482665600, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211991399095, 3, 40, '2008-05-28 19:16:39.078+03', 0, 0, false, false, false, false, false, 1211991394751, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211552393308, 2, 59, '2008-05-23 17:19:53.283+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211575685798, 1, 53, '2008-05-23 23:48:05.781+03', 0, 0, false, false, false, false, false, 1209222423971, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211575645922, 2, 43, '2008-05-23 23:47:25.734+03', 0, 0, true, false, false, false, false, 1209222423971, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211576215909, 1, 43, '2008-05-23 23:56:55.875+03', 0, 0, false, false, false, false, false, 1209222423971, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211992741956, 1, 40, '2008-05-28 19:39:01.937+03', 0, 0, false, false, false, false, false, 1211991394751, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213476155246, 1, 37, '2008-06-14 23:42:35.234+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211990419018, 1, 43, '2008-05-28 19:00:18.828+03', 0, 0, false, false, false, false, false, 1211990404064, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1212956274794, 2, 46, '2008-06-08 23:17:54.79+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1212956867269, 1, 43, '2008-06-08 23:27:47.248+03', 0, 0, false, false, false, false, false, 1211475534335, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211991410878, 1, 43, '2008-05-28 19:16:50.859+03', 0, 0, false, false, false, false, false, 1211991399095, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211991416347, 1, 45, '2008-05-28 19:16:56.328+03', 0, 0, false, false, false, false, false, 1211991399095, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1212956870463, 2, 60, '2008-06-08 23:27:50.459+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211992678579, 1, 59, '2008-05-28 19:37:58.578+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213369003444, 1, 72, '2008-06-13 17:56:43.443+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211737364325, 2, 60, '2008-05-25 20:42:44.322+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211747457079, 1, 58, '2008-05-25 23:30:56.578+03', 0, 0, false, false, false, false, false, 1211472878364, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211747497502, 2, 47, '2008-05-25 23:31:37.50+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211748054362, 2, 47, '2008-05-25 23:40:54.359+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213369014076, 1, 72, '2008-06-13 17:56:54.074+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211991090297, 2, 1, '2008-05-28 19:11:30.296+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211991238860, 2, 1, '2008-05-28 19:13:58.859+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211991394751, 4, 1, '2008-05-28 19:16:34.75+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211992723142, 1, 59, '2008-05-28 19:38:43.14+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1212004334360, 1, 40, '2008-05-28 22:52:14.109+03', 0, 0, false, false, false, false, false, 1209582114840, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211990399266, 2, 1, '2008-05-28 18:59:59.265+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1212005439127, 1, 58, '2008-05-28 23:10:39.078+03', 0, 0, false, false, false, false, false, 1211472878364, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1212005469332, 1, 43, '2008-05-28 23:11:09.203+03', 0, 0, false, false, false, false, false, 1212005450940, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1212005477755, 1, 45, '2008-05-28 23:11:17.734+03', 0, 0, false, false, false, false, false, 1212005450940, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1212005450940, 2, 40, '2008-05-28 23:10:50.843+03', 0, 0, false, false, false, false, false, 1211472878364, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1212035334220, 1, 40, '2008-05-29 07:28:54.187+03', 0, 0, false, false, false, false, false, 1212035329626, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1212035353003, 1, 53, '2008-05-29 07:29:12.984+03', 0, 0, false, false, false, false, false, 1212035334220, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1212035360457, 1, 45, '2008-05-29 07:29:20.437+03', 0, 0, false, false, false, false, false, 1212035334220, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1212035401162, 1, 40, '2008-05-29 07:30:01.156+03', 0, 0, false, false, false, false, false, 1212035394176, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1212035419507, 1, 53, '2008-05-29 07:30:19.484+03', 0, 0, false, false, false, false, false, 1212035401162, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1212035394176, 2, 1, '2008-05-29 07:29:54.171+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1212035329626, 2, 1, '2008-05-29 07:28:49.625+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1212035661315, 1, 53, '2008-05-29 07:34:21.296+03', 0, 0, false, false, false, false, false, 1212035638798, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1212035676300, 1, 43, '2008-05-29 07:34:36.281+03', 0, 0, false, false, false, false, false, 1212035638798, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1212035638798, 2, 40, '2008-05-29 07:33:58.781+03', 0, 0, false, false, false, false, false, 1212035635016, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1212035635016, 2, 1, '2008-05-29 07:33:55.015+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213369024087, 1, 72, '2008-06-13 17:57:04.084+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213369047349, 1, 72, '2008-06-13 17:57:27.344+03', 0, 0, false, false, false, false, false, 1213369003444, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213369062217, 1, 72, '2008-06-13 17:57:42.211+03', 0, 0, false, false, false, false, false, 1213369003444, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213369076486, 1, 72, '2008-06-13 17:57:56.48+03', 0, 0, false, false, false, false, false, 1213369003444, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213369094667, 1, 72, '2008-06-13 17:58:14.66+03', 0, 0, false, false, false, false, false, 1213369003444, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213369109811, 1, 72, '2008-06-13 17:58:29.803+03', 0, 0, false, false, false, false, false, 1213369014076, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213369126523, 1, 72, '2008-06-13 17:58:46.514+03', 0, 0, false, false, false, false, false, 1213369024087, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213474710502, 1, 40, '2008-06-14 23:18:30.484+03', 0, 0, false, false, false, false, false, 1213474710376, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213474710565, 1, 37, '2008-06-14 23:18:30.562+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213474965926, 1, 40, '2008-06-14 23:22:45.921+03', 0, 0, false, false, false, false, false, 1213474965879, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213474965974, 1, 37, '2008-06-14 23:22:45.968+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213476154964, 1, 40, '2008-06-14 23:42:34.953+03', 0, 0, false, false, false, false, false, 1213476154900, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213479859086, 1, 40, '2008-06-15 00:44:19.085+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213480192839, 1, 40, '2008-06-15 00:49:52.836+03', 0, 0, false, false, false, false, false, 1213480191278, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213480193688, 1, 37, '2008-06-15 00:49:53.685+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213480191278, 2, 1, '2008-06-15 00:49:51.277+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213476154900, 2, 1, '2008-06-14 23:42:34.89+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213475482118, 2, 1, '2008-06-14 23:31:22.109+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213475423476, 2, 1, '2008-06-14 23:30:23.468+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213475272257, 2, 1, '2008-06-14 23:27:52.25+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213474965879, 2, 1, '2008-06-14 23:22:45.875+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213474710376, 2, 1, '2008-06-14 23:18:30.359+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213480568707, 2, 40, '2008-06-15 00:56:08.705+03', 0, 0, true, false, false, false, false, 1213480567168, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213480576025, 2, 45, '2008-06-15 00:56:16.021+03', 0, 0, true, false, false, false, false, 1213480568707, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213480569579, 2, 37, '2008-06-15 00:56:09.576+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213480567168, 2, 1, '2008-06-15 00:56:07.167+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214577893297, 1, 61, '2008-06-27 17:44:53.296+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214577908205, 2, 61, '2008-06-27 17:45:08.203+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213515923514, 2, 62, '2008-06-15 10:45:23.484+03', 0, 0, false, false, false, false, false, 1211131456610, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213481638191, 2, 45, '2008-06-15 01:13:58.187+03', 0, 0, true, false, false, false, false, 1213481637986, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213515932561, 3, 62, '2008-06-15 10:45:32.531+03', 0, 0, true, false, false, false, false, 1211131456610, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213481638034, 2, 37, '2008-06-15 01:13:58.031+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213481637986, 2, 40, '2008-06-15 01:13:57.984+03', 0, 0, true, false, false, false, false, 1213481637891, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213481637891, 2, 1, '2008-06-15 01:13:57.875+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213481850551, 1, 59, '2008-06-15 01:17:30.546+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214578135971, 3, 62, '2008-06-27 17:48:55.953+03', 0, 0, false, false, false, false, false, 1214577893297, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213509854462, 2, 45, '2008-06-15 09:04:14.453+03', 0, 0, true, false, false, false, false, 1213509854335, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213509854367, 2, 37, '2008-06-15 09:04:14.359+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213509854335, 2, 40, '2008-06-15 09:04:14.328+03', 0, 0, true, false, false, false, false, 1213509854099, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213509854099, 2, 1, '2008-06-15 09:04:14.093+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213512206579, 1, 37, '2008-06-15 09:43:26.578+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213512226752, 1, 40, '2008-06-15 09:43:46.75+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213512850266, 1, 40, '2008-06-15 09:54:10.265+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213513964721, 1, 43, '2008-06-15 10:12:44.718+03', 0, 0, false, false, false, false, false, 1213513946548, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214605521032, 2, 62, '2008-06-28 01:25:20.875+03', 0, 0, true, false, false, false, false, 1211131456610, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213513946548, 2, 40, '2008-06-15 10:12:26.546+03', 0, 0, true, false, false, false, false, 1213512206579, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213514022629, 2, 40, '2008-06-15 10:13:42.625+03', 0, 0, true, false, false, false, false, 1213512206579, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213514374176, 2, 40, '2008-06-15 10:19:34.171+03', 0, 0, true, false, false, false, false, 1213512206579, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213514652038, 1, 43, '2008-06-15 10:24:12.031+03', 0, 0, false, false, false, false, false, 1213514640256, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213514640256, 2, 40, '2008-06-15 10:24:00.25+03', 0, 0, false, false, false, false, false, 1213512206579, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213514894852, 1, 53, '2008-06-15 10:28:14.843+03', 0, 0, false, false, false, false, false, 1213514856758, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213514905338, 1, 45, '2008-06-15 10:28:25.328+03', 0, 0, false, false, false, false, false, 1213514856758, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213514856758, 2, 40, '2008-06-15 10:27:36.75+03', 0, 0, false, false, false, false, false, 1213512206579, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213514968013, 1, 43, '2008-06-15 10:29:28+03', 0, 0, false, false, false, false, false, 1213514956074, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213514956074, 2, 40, '2008-06-15 10:29:16.062+03', 0, 0, false, false, false, false, false, 1213514952057, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213514952057, 2, 1, '2008-06-15 10:29:12.046+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213516221125, 2, 62, '2008-06-15 10:50:21.093+03', 0, 0, true, false, false, false, false, 1211131456610, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213952129954, 1, 60, '2008-06-20 11:55:29.937+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213963338689, 1, 59, '2008-06-20 15:02:18.687+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213963345878, 1, 37, '2008-06-20 15:02:25.875+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214287428969, 1, 37, '2008-06-24 09:03:48.968+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214334270672, 1, 89, '2008-06-24 22:04:30.671+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214606093220, 2, 62, '2008-06-28 01:34:53.218+03', 0, 0, true, false, false, false, false, 1211131456610, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214582644985, 2, 62, '2008-06-27 19:04:04.765+03', 0, 0, false, false, false, false, false, 1211131456610, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214639571392, 2, 62, '2008-06-28 10:52:51.375+03', 0, 0, true, false, false, false, false, 1214639571251, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214639571251, 2, 61, '2008-06-28 10:52:51.25+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214683373782, 1, 89, '2008-06-28 23:02:53.781+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214690075898, 2, 95, '2008-06-29 00:54:35.895+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214730588490, 2, 95, '2008-06-29 12:09:48.489+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214739376412, 2, 95, '2008-06-29 14:36:16.411+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214740305788, 2, 95, '2008-06-29 14:51:45.786+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214748427158, 1, 62, '2008-06-29 17:07:07.035+03', 0, 0, false, false, false, false, false, 1214577893297, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214749012672, 1, 62, '2008-06-29 17:16:52.628+03', 0, 0, false, false, false, false, false, 1214577893297, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);


--
-- TOC entry 2398 (class 0 OID 25997)
-- Dependencies: 1652
-- Data for Name: delivery_certificate_items; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2399 (class 0 OID 26022)
-- Dependencies: 1653
-- Data for Name: delivery_certificate_serial_numbers; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2397 (class 0 OID 25962)
-- Dependencies: 1651
-- Data for Name: delivery_certificates; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2378 (class 0 OID 25035)
-- Dependencies: 1622
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
INSERT INTO enum_classes (enum_class_id, enum_class_name) VALUES (16, 'com.cosmos.acacia.crm.assembling.Algorithm$Type');
INSERT INTO enum_classes (enum_class_id, enum_class_name) VALUES (17, 'com.cosmos.acacia.crm.enums.AssemblingSchemaItemDataType');


--
-- TOC entry 2392 (class 0 OID 25718)
-- Dependencies: 1646
-- Data for Name: invoice_items; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2391 (class 0 OID 25611)
-- Dependencies: 1645
-- Data for Name: invoices; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO invoices (invoice_id, parent_id, branch_id, branch_name, invoice_number, invoice_date, recipient_id, recipient_name, recipient_contact_id, recipient_contact_name, invoice_type_id, status_id, creation_time, creator_id, creator_name, doc_delivery_method_id, transportation_method_id, shipping_agent_id, transportation_price, currency_id, invoice_sub_value, discount_percent, discount_value, invoice_value, excise_duty_percent, excise_duty_value, vat_condition_id, vat_percent, vat_value, total_invoice_value, payment_type_id, payment_terms_id, payment_due_date, delivery_type_id, sent_time, sender_id, sender_name, first_ship_date, last_ship_date, finalizing_date) VALUES (1214683373782, NULL, 1206861699860, 'Branch X', 1011001101, '2008-06-28', 1209394438429, 'IBM', 1206862211011, 'Miroslav Nachev', 81, 78, '2008-06-28', 1211482143869, 'Даниел Велев', 113, 94, NULL, NULL, 49, 372744.0000, NULL, NULL, 74573256.0000, NULL, NULL, 103, 0.20, 277.0000, 454366.0000, 105, 108, NULL, 88, NULL, NULL, NULL, NULL, NULL, NULL);


--
-- TOC entry 2396 (class 0 OID 25939)
-- Dependencies: 1650
-- Data for Name: order_confirmation_items; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2395 (class 0 OID 25909)
-- Dependencies: 1649
-- Data for Name: order_confirmations; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2411 (class 0 OID 26928)
-- Dependencies: 1666
-- Data for Name: organizations; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id, is_active) VALUES (1208326364860, NULL, 'a', 'aa', '2008-04-16', 5.00, '65464564', '5454654', 21, NULL, NULL, NULL, 48, true);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id, is_active) VALUES (1209394343621, NULL, 'op', 'MicrosoftsdfsdfMicrosoftsdfsdfMicrosoftsdfsdfMicrosoftsdfsdfMicrosoftsdfsdfMicrosoftsdfsdfMicrosoftsdfsdfMicrosoftsdfsdfMicrosoftsdfsdfMicrosoftsdfsdfMicrosoftsdfsdfMicrosoftsdfsdf', '2008-04-13', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id, is_active) VALUES (1209585931126, NULL, 'Civic', 'Honda', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id, is_active) VALUES (1209324665385, NULL, NULL, 'dfsdfsdf', NULL, NULL, NULL, NULL, NULL, NULL, 1209324665385.00, NULL, NULL, true);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id, is_active) VALUES (1209582114840, NULL, NULL, 'Yamaha', '2008-04-25', NULL, NULL, NULL, NULL, NULL, 1209394438429.00, NULL, NULL, true);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id, is_active) VALUES (1209394438429, 'sdfcsdfsd', 'IBM inc', 'IBM', '2008-04-18', 3000000000.00, NULL, '32423453464erg', 28, NULL, 1209582114840.00, NULL, 48, true);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id, is_active) VALUES (1209222047860, NULL, NULL, 'SmartMinds', '2008-04-09', 9.00, NULL, '99', 22, NULL, 1208326364860.00, NULL, 48, true);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id, is_active) VALUES (1211482143869, NULL, NULL, 'Branding Inc.', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id, is_active) VALUES (1211483549670, NULL, NULL, 'asdfsdf', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id, is_active) VALUES (1211492793954, NULL, NULL, 'testbank', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id, is_active) VALUES (1211990399266, NULL, NULL, 'testaaa', NULL, NULL, NULL, NULL, 22, NULL, NULL, NULL, NULL, true);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id, is_active) VALUES (1212035635016, NULL, NULL, 'lkjj', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id, is_active) VALUES (1213514952057, NULL, NULL, 'tes org', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);


--
-- TOC entry 2408 (class 0 OID 26275)
-- Dependencies: 1662
-- Data for Name: passports; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO passports (passport_id, parent_id, passport_type_id, passport_number, issue_date, expiration_date, issuer_id, issuer_branch_id, additional_info) VALUES (1208723817938, 1206833877407, 59, '4532', '2008-04-20', '2008-04-18', 1208326364860, 1208375541782, 'ja');
INSERT INTO passports (passport_id, parent_id, passport_type_id, passport_number, issue_date, expiration_date, issuer_id, issuer_branch_id, additional_info) VALUES (1211747457079, 1211472878364, 59, '1234', '2008-05-27', '2008-05-31', 1209222047860, 1209222423971, NULL);
INSERT INTO passports (passport_id, parent_id, passport_type_id, passport_number, issue_date, expiration_date, issuer_id, issuer_branch_id, additional_info) VALUES (1212005439127, 1211472878364, 59, '7777j', '2008-05-02', '2008-05-04', 1209394438429, 1210419278241, NULL);


--
-- TOC entry 2379 (class 0 OID 25041)
-- Dependencies: 1624
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
-- TOC entry 2412 (class 0 OID 26936)
-- Dependencies: 1667
-- Data for Name: persons; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1206910833184, NULL, NULL, NULL, 'Силвия', 'Начева', NULL, NULL, NULL, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1206862211011, NULL, NULL, NULL, 'Miroslav', 'Nachev', NULL, NULL, 19, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1206833877407, NULL, NULL, NULL, 'aborigen', 'aborigenov', NULL, NULL, 19, 1209062694832, 5);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1210418654556, '2008-05-21', NULL, 'ename', 'fname', 'lname', '54545', 'sname', NULL, NULL, 6);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1210419431552, NULL, NULL, NULL, 'asdf', 'asdf', NULL, NULL, NULL, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1210505228236, NULL, NULL, NULL, 'asdf', 'asdf', NULL, NULL, NULL, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1211472752091, NULL, NULL, NULL, 'Hans ', 'Van Nispen', NULL, NULL, 20, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1211472798404, NULL, NULL, 'BG MAN', 'Bai', 'Ganio', NULL, NULL, NULL, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1211475930558, NULL, NULL, NULL, 'Mister', 'Man', NULL, NULL, NULL, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1211472878364, NULL, NULL, 'Hairy A** Man', 'Edwin', 'Poot', NULL, 'R.', NULL, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1213474710565, NULL, NULL, 'gxnst', '9alc4', 'ln608', NULL, 'r5s6h', NULL, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1213474965974, NULL, NULL, 't2yva', '5f95w', 'jngwt', NULL, 'g6171', NULL, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1213476155246, NULL, NULL, '5r2bb', '1pupr', 'fgbuz', NULL, 'rggdd', NULL, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1213480193688, NULL, NULL, 'dm3kc', '3kpzz', '4otcc', NULL, 'vqq7t', NULL, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1213512206579, NULL, NULL, NULL, 'test person', 'test name', NULL, NULL, 19, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1211482143869, NULL, NULL, NULL, 'Даниел', 'Велев', NULL, NULL, 19, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1209582459571, '2008-04-26', 'Golema maimuna', 'The King', 'King', 'Kong', 'kk1', 'Monkey', 19, 1209062694832, 5);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1213963345878, NULL, NULL, NULL, 'Daniel', 'Velev', NULL, NULL, NULL, 1213963338689, 5);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1214287428969, NULL, NULL, NULL, 'Marco', 'Basten', NULL, 'Van', 19, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1209582555898, NULL, NULL, NULL, 'KKO', 'LPPP', NULL, NULL, NULL, NULL, NULL);


--
-- TOC entry 2403 (class 0 OID 26146)
-- Dependencies: 1657
-- Data for Name: position_types; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO position_types (position_type_id, parent_id, owner_type, position_type_name, description) VALUES (1208595267969, NULL, 'O', 'Boss', NULL);
INSERT INTO position_types (position_type_id, parent_id, owner_type, position_type_name, description) VALUES (1208597113782, NULL, 'O', 'Cleaner', NULL);
INSERT INTO position_types (position_type_id, parent_id, owner_type, position_type_name, description) VALUES (1208927516422, NULL, 'P', 'Brother', NULL);
INSERT INTO position_types (position_type_id, parent_id, owner_type, position_type_name, description) VALUES (1208929402376, NULL, 'P', 'Sister', NULL);
INSERT INTO position_types (position_type_id, parent_id, owner_type, position_type_name, description) VALUES (1209325564201, NULL, 'P', 'Бащата', NULL);


--
-- TOC entry 2380 (class 0 OID 25047)
-- Dependencies: 1625
-- Data for Name: product_categories; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO product_categories (product_category_id, parent_id, category_name, pattern_mask_format_id, description, parent_cat_id) VALUES (1210702269535, NULL, '111', NULL, NULL, 1209288962569.00);
INSERT INTO product_categories (product_category_id, parent_id, category_name, pattern_mask_format_id, description, parent_cat_id) VALUES (1210702277197, NULL, '222', NULL, NULL, 1209288962569.00);
INSERT INTO product_categories (product_category_id, parent_id, category_name, pattern_mask_format_id, description, parent_cat_id) VALUES (1210702289908, NULL, '444', NULL, NULL, 1209288962569.00);
INSERT INTO product_categories (product_category_id, parent_id, category_name, pattern_mask_format_id, description, parent_cat_id) VALUES (1210702309820, NULL, '777', NULL, NULL, 1209288962569.00);
INSERT INTO product_categories (product_category_id, parent_id, category_name, pattern_mask_format_id, description, parent_cat_id) VALUES (1210702323310, NULL, '999', NULL, NULL, 1209288962569.00);
INSERT INTO product_categories (product_category_id, parent_id, category_name, pattern_mask_format_id, description, parent_cat_id) VALUES (1210702341065, NULL, '123', NULL, NULL, 1210702269535.00);
INSERT INTO product_categories (product_category_id, parent_id, category_name, pattern_mask_format_id, description, parent_cat_id) VALUES (1210702350546, NULL, '234', NULL, NULL, 1210702341065.00);
INSERT INTO product_categories (product_category_id, parent_id, category_name, pattern_mask_format_id, description, parent_cat_id) VALUES (1210702358576, NULL, '345', NULL, NULL, 1210702350546.00);
INSERT INTO product_categories (product_category_id, parent_id, category_name, pattern_mask_format_id, description, parent_cat_id) VALUES (1210702302770, NULL, '66666666666666666666666666666666666666666666666', NULL, NULL, 1209288962569.00);
INSERT INTO product_categories (product_category_id, parent_id, category_name, pattern_mask_format_id, description, parent_cat_id) VALUES (1210702297698, NULL, '555', NULL, NULL, 1210702358576.00);
INSERT INTO product_categories (product_category_id, parent_id, category_name, pattern_mask_format_id, description, parent_cat_id) VALUES (1210702283927, NULL, '333', NULL, NULL, 1208867495266.00);
INSERT INTO product_categories (product_category_id, parent_id, category_name, pattern_mask_format_id, description, parent_cat_id) VALUES (1210702318119, NULL, '888', NULL, NULL, 1208867495266.00);
INSERT INTO product_categories (product_category_id, parent_id, category_name, pattern_mask_format_id, description, parent_cat_id) VALUES (1210519124507, NULL, 'Long Scrap', 50, 'Very long,
Strenght 10', 1210702297698.00);
INSERT INTO product_categories (product_category_id, parent_id, category_name, pattern_mask_format_id, description, parent_cat_id) VALUES (1209407225556, NULL, 'Ice-Cream', 56, 'Haho hihie
obalaa', 1210513038084.00);
INSERT INTO product_categories (product_category_id, parent_id, category_name, pattern_mask_format_id, description, parent_cat_id) VALUES (1208867495266, NULL, 'Hi Tech Goods', NULL, NULL, NULL);
INSERT INTO product_categories (product_category_id, parent_id, category_name, pattern_mask_format_id, description, parent_cat_id) VALUES (1209288962569, NULL, 'Hiking', NULL, NULL, NULL);
INSERT INTO product_categories (product_category_id, parent_id, category_name, pattern_mask_format_id, description, parent_cat_id) VALUES (1210620273286, NULL, 'For Fridge', NULL, NULL, NULL);
INSERT INTO product_categories (product_category_id, parent_id, category_name, pattern_mask_format_id, description, parent_cat_id) VALUES (1210620471938, NULL, 'Strawberry icecream', 48, 'good taste', 1209407225556.00);
INSERT INTO product_categories (product_category_id, parent_id, category_name, pattern_mask_format_id, description, parent_cat_id) VALUES (1210620499609, NULL, 'Caramel with chocolate', NULL, NULL, 1209407225556.00);
INSERT INTO product_categories (product_category_id, parent_id, category_name, pattern_mask_format_id, description, parent_cat_id) VALUES (1, NULL, 'Laser Gun', NULL, 'very powerful, but dangerous', 1208867495266.00);
INSERT INTO product_categories (product_category_id, parent_id, category_name, pattern_mask_format_id, description, parent_cat_id) VALUES (1210518068080, NULL, 'Coca Cola', NULL, NULL, 1210620733627.00);
INSERT INTO product_categories (product_category_id, parent_id, category_name, pattern_mask_format_id, description, parent_cat_id) VALUES (1209403563985, NULL, 'Pinacolada', NULL, NULL, 1210620733627.00);
INSERT INTO product_categories (product_category_id, parent_id, category_name, pattern_mask_format_id, description, parent_cat_id) VALUES (1208791743432, NULL, 'Limonade', NULL, NULL, 1210620733627.00);
INSERT INTO product_categories (product_category_id, parent_id, category_name, pattern_mask_format_id, description, parent_cat_id) VALUES (1209407251282, NULL, 'Food And Drinks', 48, 'description cat form x', 1210620273286.00);
INSERT INTO product_categories (product_category_id, parent_id, category_name, pattern_mask_format_id, description, parent_cat_id) VALUES (1210620733627, NULL, 'Drinks', 48, 'all that can be drinked', 1209407251282.00);
INSERT INTO product_categories (product_category_id, parent_id, category_name, pattern_mask_format_id, description, parent_cat_id) VALUES (1210621181773, NULL, 'Fast food', NULL, NULL, 1209407251282.00);
INSERT INTO product_categories (product_category_id, parent_id, category_name, pattern_mask_format_id, description, parent_cat_id) VALUES (1210513038084, NULL, 'Fried chicken', NULL, NULL, 1210621181773.00);


--
-- TOC entry 2381 (class 0 OID 25053)
-- Dependencies: 1626
-- Data for Name: product_suppliers; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2414 (class 0 OID 27726)
-- Dependencies: 1671
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
-- TOC entry 2394 (class 0 OID 25851)
-- Dependencies: 1648
-- Data for Name: purchase_order_items; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2393 (class 0 OID 25808)
-- Dependencies: 1647
-- Data for Name: purchase_orders; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2417 (class 0 OID 27812)
-- Dependencies: 1674
-- Data for Name: real_products; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2401 (class 0 OID 26074)
-- Dependencies: 1655
-- Data for Name: receipt_certificate_items; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2402 (class 0 OID 26099)
-- Dependencies: 1656
-- Data for Name: receipt_certificate_serial_numbers; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2400 (class 0 OID 26037)
-- Dependencies: 1654
-- Data for Name: receipt_certificates; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2424 (class 0 OID 36198)
-- Dependencies: 1681
-- Data for Name: registration_codes; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO registration_codes (registration_code, email) VALUES (666056, 'gfds');


--
-- TOC entry 2383 (class 0 OID 25074)
-- Dependencies: 1629
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
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (61, 16, 'UnconditionalSelection');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (62, 16, 'UserSelection');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (63, 16, 'UserSingleSelection');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (64, 16, 'UserMultipleSelection');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (65, 16, 'RangeSelection');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (66, 16, 'RangeSingleSelection');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (67, 16, 'RangeMultipleSelection');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (68, 16, 'EqualsSelection');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (69, 16, 'EqualsSingleSelection');
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
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (70, 16, 'EqualsMultipleSelection');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (71, 17, 'Integer');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (72, 17, 'Decimal');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (73, 17, 'Date');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (74, 17, 'String');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (75, 9, 'Open');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (76, 9, 'Sent');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (77, 9, 'PartlyDelivered');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (78, 9, 'Delivered');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (79, 9, 'PartlyCanceled');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (80, 9, 'Canceled');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (81, 10, 'VatInvoice');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (82, 10, 'SimpleInvoice');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (83, 10, 'DebitNoteInvoice');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (84, 10, 'CretidNoteInvoice');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (85, 7, 'InPlace');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (86, 7, 'Courier');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (87, 7, 'PostMail');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (88, 7, 'Email');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (89, 7, 'RailwayCargo');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (90, 7, 'AirCargo');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (91, 7, 'AutoCargo');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (92, 7, 'InternalCargo');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (93, 7, 'OtherTransports');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (94, 13, 'InPlace');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (95, 13, 'Courier');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (96, 13, 'PostMail');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (97, 13, 'Email');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (98, 13, 'RailwayCargo');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (99, 13, 'AirCargo');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (100, 13, 'AutoCargo');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (101, 13, 'InternalCargo');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (102, 13, 'OtherTransports');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (103, 14, 'VatPayable');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (104, 14, 'NoVat');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (105, 12, 'Cash');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (106, 12, 'BankTransfer');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (107, 12, 'CasheOnDelivery');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (108, 11, 'InAdvance');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (109, 11, 'None');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (110, 8, 'InPlace');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (111, 8, 'Email');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (112, 8, 'WebServices');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (113, 8, 'Fax');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (114, 8, 'FaxAuto');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (115, 8, 'PostMail');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (116, 8, 'Courier');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (117, 8, 'Combined');


--
-- TOC entry 2384 (class 0 OID 25077)
-- Dependencies: 1630
-- Data for Name: sequence_identifiers; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2382 (class 0 OID 25059)
-- Dependencies: 1627
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
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, pattern_mask_format_id, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id) VALUES (1208532522729, 1, 'Desk', 'AB41233', 1, false, true, false, 33, 40, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, pattern_mask_format_id, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id) VALUES (1208531803052, 1210702297698, 'Amplifier, Brand: Pioneer, Model: A 656 Mark II', '000099083411445', 1, false, true, false, 36, 39, 1.0000, 234.5678, NULL, 300.0000, 270.0000, 240.0000, 1, 14, 50.00, 63.23, 14.43, 8, 16.000, 3, 'Very powerful', NULL);


--
-- TOC entry 2385 (class 0 OID 25084)
-- Dependencies: 1632
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO users (user_id, version, user_name, email_address, user_password, system_password, system_password_validity, is_active, is_new, creation_time, creator_id, person_id, description, small_image_uri, medium_image_uri, user_uri, next_action_after_login, small_image, medium_image) VALUES (1214690075898, 0, 'Glam', 'fgds', 'Ð3â*ãH®µfÂ
ì5…M©—
', NULL, NULL, true, false, '00:54:34.567+03', 1214690075898, 1211475930558, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO users (user_id, version, user_name, email_address, user_password, system_password, system_password_validity, is_active, is_new, creation_time, creator_id, person_id, description, small_image_uri, medium_image_uri, user_uri, next_action_after_login, small_image, medium_image) VALUES (1214730588490, 0, 'adminold', 'fdsa', 'Ð3â*ãH®µfÂ
ì5…M©—', NULL, NULL, true, false, '12:09:47.817+03', 1214730588490, 1211475930558, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO users (user_id, version, user_name, email_address, user_password, system_password, system_password_validity, is_active, is_new, creation_time, creator_id, person_id, description, small_image_uri, medium_image_uri, user_uri, next_action_after_login, small_image, medium_image) VALUES (1214739376412, 0, 'admin', 'asdf', 'Ð3â*ãH®µfÂ
ì5…M©—', NULL, NULL, true, false, '14:36:16.223+03', 1214739376412, 1211475930558, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO users (user_id, version, user_name, email_address, user_password, system_password, system_password_validity, is_active, is_new, creation_time, creator_id, person_id, description, small_image_uri, medium_image_uri, user_uri, next_action_after_login, small_image, medium_image) VALUES (1214740305788, 0, 'test', 'fds', '©J�åÌ±›¦LsÓ‘é‡˜/»Ó', NULL, NULL, false, false, '14:51:45.786+03', 1214740305788, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);


--
-- TOC entry 2423 (class 0 OID 36183)
-- Dependencies: 1680
-- Data for Name: users_organizations; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO users_organizations (user_id, organization_id, branch_id) VALUES (1214690075898, 1209222047860, NULL);
INSERT INTO users_organizations (user_id, organization_id, branch_id) VALUES (1214730588490, 1209222047860, NULL);
INSERT INTO users_organizations (user_id, organization_id, branch_id) VALUES (1214739376412, 1209222047860, NULL);


--
-- TOC entry 2416 (class 0 OID 27796)
-- Dependencies: 1673
-- Data for Name: virtual_products; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2413 (class 0 OID 27708)
-- Dependencies: 1670
-- Data for Name: warehouse_products; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO warehouse_products (warehouse_product_id, default_quantity, delivery_time, maximum_quantity, minimum_quantity, notes, ordered_quantity, purchase_price, quantity_due, quantity_in_stock, reserved_quantity, sale_price, sold_quantity, warehouse_id, product_id) VALUES (7, 6.00, 8, 4.00, 2.00, 'Some notes', 0.00, 14.00, 0.00, 0.00, 0.00, 12.00, 0.00, 1210856702896.00, 1208532361606.00);
INSERT INTO warehouse_products (warehouse_product_id, default_quantity, delivery_time, maximum_quantity, minimum_quantity, notes, ordered_quantity, purchase_price, quantity_due, quantity_in_stock, reserved_quantity, sale_price, sold_quantity, warehouse_id, product_id) VALUES (8, NULL, NULL, NULL, NULL, NULL, 0.00, NULL, 0.00, 0.00, 0.00, NULL, 0.00, 1210856702896.00, 1208531981537.00);
INSERT INTO warehouse_products (warehouse_product_id, default_quantity, delivery_time, maximum_quantity, minimum_quantity, notes, ordered_quantity, purchase_price, quantity_due, quantity_in_stock, reserved_quantity, sale_price, sold_quantity, warehouse_id, product_id) VALUES (9, NULL, NULL, NULL, NULL, NULL, 0.00, NULL, 0.00, 0.00, 0.00, NULL, 0.00, 1211476820946.00, 1208523315311.00);
INSERT INTO warehouse_products (warehouse_product_id, default_quantity, delivery_time, maximum_quantity, minimum_quantity, notes, ordered_quantity, purchase_price, quantity_due, quantity_in_stock, reserved_quantity, sale_price, sold_quantity, warehouse_id, product_id) VALUES (10, 8.00, NULL, NULL, 4.00, NULL, 0.00, NULL, 0.00, 0.00, 0.00, NULL, 0.00, 1211476820946.00, 1208531981537.00);
INSERT INTO warehouse_products (warehouse_product_id, default_quantity, delivery_time, maximum_quantity, minimum_quantity, notes, ordered_quantity, purchase_price, quantity_due, quantity_in_stock, reserved_quantity, sale_price, sold_quantity, warehouse_id, product_id) VALUES (11, NULL, NULL, NULL, NULL, NULL, 0.00, NULL, 0.00, 0.00, 0.00, NULL, 0.00, 1213952129954.00, 1208532361606.00);
INSERT INTO warehouse_products (warehouse_product_id, default_quantity, delivery_time, maximum_quantity, minimum_quantity, notes, ordered_quantity, purchase_price, quantity_due, quantity_in_stock, reserved_quantity, sale_price, sold_quantity, warehouse_id, product_id) VALUES (12, 20.00, NULL, 50.00, NULL, NULL, 0.00, NULL, 0.00, 0.00, 0.00, NULL, 0.00, 1213952129954.00, 1208532574240.00);
INSERT INTO warehouse_products (warehouse_product_id, default_quantity, delivery_time, maximum_quantity, minimum_quantity, notes, ordered_quantity, purchase_price, quantity_due, quantity_in_stock, reserved_quantity, sale_price, sold_quantity, warehouse_id, product_id) VALUES (13, NULL, NULL, 90.00, NULL, NULL, 0.00, NULL, 0.00, 0.00, 0.00, NULL, 0.00, 1210856556559.00, 1208531981537.00);


--
-- TOC entry 2390 (class 0 OID 25489)
-- Dependencies: 1644
-- Data for Name: warehouses; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO warehouses (warehouse_id, parent_id, address_id, warehouseman_id, description) VALUES (1210856556559, NULL, 1210856514214, 1206833877407, 'Very large');
INSERT INTO warehouses (warehouse_id, parent_id, address_id, warehouseman_id, description) VALUES (1210856702896, NULL, 1210856490268, 1211472752091, 'Warehouse for programmers');
INSERT INTO warehouses (warehouse_id, parent_id, address_id, warehouseman_id, description) VALUES (1211476820946, NULL, 1210856501325, 1211475930558, 'the man of mistery..');
INSERT INTO warehouses (warehouse_id, parent_id, address_id, warehouseman_id, description) VALUES (1213952129954, NULL, 1211475534335, 1211475930558, 'Massy Warehouse');


--
-- TOC entry 2090 (class 2606 OID 27095)
-- Dependencies: 1665 1665
-- Name: business_partners_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY business_partners
    ADD CONSTRAINT business_partners_pkey PRIMARY KEY (partner_id);


--
-- TOC entry 2092 (class 2606 OID 27507)
-- Dependencies: 1666 1666
-- Name: organizations_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT organizations_pkey PRIMARY KEY (organization_id);


--
-- TOC entry 2094 (class 2606 OID 27147)
-- Dependencies: 1667 1667
-- Name: persons_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT persons_pkey PRIMARY KEY (partner_id);


--
-- TOC entry 2018 (class 2606 OID 25376)
-- Dependencies: 1640 1640
-- Name: pk_addresses; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT pk_addresses PRIMARY KEY (address_id);


--
-- TOC entry 2108 (class 2606 OID 27833)
-- Dependencies: 1675 1675
-- Name: pk_assembling_categories; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_categories
    ADD CONSTRAINT pk_assembling_categories PRIMARY KEY (assembling_category_id);


--
-- TOC entry 2122 (class 2606 OID 27898)
-- Dependencies: 1678 1678
-- Name: pk_assembling_schema_item_values; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schema_item_values
    ADD CONSTRAINT pk_assembling_schema_item_values PRIMARY KEY (item_value_id);


--
-- TOC entry 2120 (class 2606 OID 27879)
-- Dependencies: 1677 1677
-- Name: pk_assembling_schema_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT pk_assembling_schema_items PRIMARY KEY (item_id);


--
-- TOC entry 2114 (class 2606 OID 27847)
-- Dependencies: 1676 1676
-- Name: pk_assembling_schemas; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT pk_assembling_schemas PRIMARY KEY (product_id);


--
-- TOC entry 2076 (class 2606 OID 26234)
-- Dependencies: 1660 1660
-- Name: pk_bank_details; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT pk_bank_details PRIMARY KEY (bank_detail_id);


--
-- TOC entry 2024 (class 2606 OID 27353)
-- Dependencies: 1642 1642
-- Name: pk_cities; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY cities
    ADD CONSTRAINT pk_cities PRIMARY KEY (city_id);


--
-- TOC entry 1972 (class 2606 OID 25129)
-- Dependencies: 1616 1616 1616
-- Name: pk_classified_objects; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classified_objects
    ADD CONSTRAINT pk_classified_objects PRIMARY KEY (classifier_id, classified_object_id);


--
-- TOC entry 1974 (class 2606 OID 25131)
-- Dependencies: 1617 1617 1617
-- Name: pk_classifier_applied_for_dot; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifier_applied_for_dot
    ADD CONSTRAINT pk_classifier_applied_for_dot PRIMARY KEY (classifier_id, data_object_type_id);


--
-- TOC entry 1976 (class 2606 OID 25133)
-- Dependencies: 1618 1618
-- Name: pk_classifier_groups; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifier_groups
    ADD CONSTRAINT pk_classifier_groups PRIMARY KEY (classifier_group_id);


--
-- TOC entry 1982 (class 2606 OID 25135)
-- Dependencies: 1619 1619
-- Name: pk_classifiers; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT pk_classifiers PRIMARY KEY (classifier_id);


--
-- TOC entry 2072 (class 2606 OID 26206)
-- Dependencies: 1659 1659
-- Name: pk_communication_contacts; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT pk_communication_contacts PRIMARY KEY (communication_contact_id);


--
-- TOC entry 2124 (class 2606 OID 27924)
-- Dependencies: 1679 1679
-- Name: pk_complex_product_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT pk_complex_product_items PRIMARY KEY (complex_product_item_id);


--
-- TOC entry 2100 (class 2606 OID 27773)
-- Dependencies: 1672 1672
-- Name: pk_complex_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY complex_products
    ADD CONSTRAINT pk_complex_products PRIMARY KEY (product_id);


--
-- TOC entry 2069 (class 2606 OID 26163)
-- Dependencies: 1658 1658
-- Name: pk_contact_persons; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT pk_contact_persons PRIMARY KEY (contact_person_id);


--
-- TOC entry 2020 (class 2606 OID 25394)
-- Dependencies: 1641 1641
-- Name: pk_countries; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY countries
    ADD CONSTRAINT pk_countries PRIMARY KEY (country_id);


--
-- TOC entry 2028 (class 2606 OID 25439)
-- Dependencies: 1643 1643
-- Name: pk_currencies; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY currencies
    ADD CONSTRAINT pk_currencies PRIMARY KEY (currency_id);


--
-- TOC entry 2078 (class 2606 OID 26267)
-- Dependencies: 1661 1661
-- Name: pk_data_object_details; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_details
    ADD CONSTRAINT pk_data_object_details PRIMARY KEY (data_object_id);


--
-- TOC entry 2086 (class 2606 OID 26337)
-- Dependencies: 1663 1663
-- Name: pk_data_object_links; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_links
    ADD CONSTRAINT pk_data_object_links PRIMARY KEY (data_object_link_id);


--
-- TOC entry 1988 (class 2606 OID 25137)
-- Dependencies: 1620 1620
-- Name: pk_data_object_types; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_types
    ADD CONSTRAINT pk_data_object_types PRIMARY KEY (data_object_type_id);


--
-- TOC entry 1992 (class 2606 OID 25139)
-- Dependencies: 1621 1621
-- Name: pk_data_objects; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT pk_data_objects PRIMARY KEY (data_object_id);


--
-- TOC entry 2055 (class 2606 OID 26001)
-- Dependencies: 1652 1652
-- Name: pk_delivery_certificate_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT pk_delivery_certificate_items PRIMARY KEY (certificate_item_id);


--
-- TOC entry 2057 (class 2606 OID 26026)
-- Dependencies: 1653 1653 1653
-- Name: pk_delivery_certificate_serial_numbers; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY delivery_certificate_serial_numbers
    ADD CONSTRAINT pk_delivery_certificate_serial_numbers PRIMARY KEY (certificate_item_id, serial_number);


--
-- TOC entry 2051 (class 2606 OID 25966)
-- Dependencies: 1651 1651
-- Name: pk_delivery_certificates; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT pk_delivery_certificates PRIMARY KEY (delivery_certificate_id);


--
-- TOC entry 1994 (class 2606 OID 25141)
-- Dependencies: 1622 1622
-- Name: pk_enum_classes; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY enum_classes
    ADD CONSTRAINT pk_enum_classes PRIMARY KEY (enum_class_id);


--
-- TOC entry 2039 (class 2606 OID 25725)
-- Dependencies: 1646 1646
-- Name: pk_invoice_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY invoice_items
    ADD CONSTRAINT pk_invoice_items PRIMARY KEY (invoice_item_id);


--
-- TOC entry 2035 (class 2606 OID 25615)
-- Dependencies: 1645 1645
-- Name: pk_invoices; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT pk_invoices PRIMARY KEY (invoice_id);


--
-- TOC entry 2049 (class 2606 OID 25946)
-- Dependencies: 1650 1650
-- Name: pk_order_confirmation_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY order_confirmation_items
    ADD CONSTRAINT pk_order_confirmation_items PRIMARY KEY (confirmation_item_id);


--
-- TOC entry 2047 (class 2606 OID 25913)
-- Dependencies: 1649 1649
-- Name: pk_order_confirmations; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT pk_order_confirmations PRIMARY KEY (order_confirmation_id);


--
-- TOC entry 2082 (class 2606 OID 26299)
-- Dependencies: 1662 1662
-- Name: pk_passports; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT pk_passports PRIMARY KEY (passport_id);


--
-- TOC entry 1998 (class 2606 OID 25143)
-- Dependencies: 1624 1624
-- Name: pk_pattern_mask_formats; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pattern_mask_formats
    ADD CONSTRAINT pk_pattern_mask_formats PRIMARY KEY (pattern_mask_format_id);


--
-- TOC entry 2067 (class 2606 OID 26153)
-- Dependencies: 1657 1657
-- Name: pk_position_types; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY position_types
    ADD CONSTRAINT pk_position_types PRIMARY KEY (position_type_id);


--
-- TOC entry 2000 (class 2606 OID 25145)
-- Dependencies: 1625 1625
-- Name: pk_product_categories; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT pk_product_categories PRIMARY KEY (product_category_id);


--
-- TOC entry 2004 (class 2606 OID 25147)
-- Dependencies: 1626 1626 1626
-- Name: pk_product_suppliers; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT pk_product_suppliers PRIMARY KEY (product_id, supplier_id);


--
-- TOC entry 2006 (class 2606 OID 25149)
-- Dependencies: 1627 1627
-- Name: pk_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT pk_products PRIMARY KEY (product_id);


--
-- TOC entry 2098 (class 2606 OID 27753)
-- Dependencies: 1671 1671
-- Name: pk_products1; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY products
    ADD CONSTRAINT pk_products1 PRIMARY KEY (product_id);


--
-- TOC entry 2045 (class 2606 OID 25858)
-- Dependencies: 1648 1648
-- Name: pk_purchase_order_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT pk_purchase_order_items PRIMARY KEY (order_item_id);


--
-- TOC entry 2041 (class 2606 OID 25813)
-- Dependencies: 1647 1647
-- Name: pk_purchase_orders; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT pk_purchase_orders PRIMARY KEY (purchase_order_id);


--
-- TOC entry 2104 (class 2606 OID 27816)
-- Dependencies: 1674 1674
-- Name: pk_real_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY real_products
    ADD CONSTRAINT pk_real_products PRIMARY KEY (product_id);


--
-- TOC entry 2063 (class 2606 OID 26078)
-- Dependencies: 1655 1655
-- Name: pk_receipt_certificate_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT pk_receipt_certificate_items PRIMARY KEY (certificate_item_id);


--
-- TOC entry 2065 (class 2606 OID 26103)
-- Dependencies: 1656 1656 1656
-- Name: pk_receipt_certificate_serial_numbers; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY receipt_certificate_serial_numbers
    ADD CONSTRAINT pk_receipt_certificate_serial_numbers PRIMARY KEY (certificate_item_id, serial_number);


--
-- TOC entry 2059 (class 2606 OID 26041)
-- Dependencies: 1654 1654
-- Name: pk_receipt_certificates; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT pk_receipt_certificates PRIMARY KEY (receipt_certificate_id);


--
-- TOC entry 2008 (class 2606 OID 25151)
-- Dependencies: 1629 1629
-- Name: pk_resource_bundle; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY resource_bundle
    ADD CONSTRAINT pk_resource_bundle PRIMARY KEY (resource_id);


--
-- TOC entry 2012 (class 2606 OID 36156)
-- Dependencies: 1632 1632
-- Name: pk_users; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT pk_users PRIMARY KEY (user_id);


--
-- TOC entry 2102 (class 2606 OID 27802)
-- Dependencies: 1673 1673
-- Name: pk_virtual_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY virtual_products
    ADD CONSTRAINT pk_virtual_products PRIMARY KEY (product_id);


--
-- TOC entry 2032 (class 2606 OID 25496)
-- Dependencies: 1644 1644
-- Name: pk_warehouses; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY warehouses
    ADD CONSTRAINT pk_warehouses PRIMARY KEY (warehouse_id);


--
-- TOC entry 2128 (class 2606 OID 36202)
-- Dependencies: 1681 1681
-- Name: registration_codes_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY registration_codes
    ADD CONSTRAINT registration_codes_pkey PRIMARY KEY (registration_code);


--
-- TOC entry 2110 (class 2606 OID 27840)
-- Dependencies: 1675 1675 1675
-- Name: uk_assembling_categories_by_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_categories
    ADD CONSTRAINT uk_assembling_categories_by_code UNIQUE (parent_id, category_code);


--
-- TOC entry 2112 (class 2606 OID 27842)
-- Dependencies: 1675 1675 1675
-- Name: uk_assembling_categories_by_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_categories
    ADD CONSTRAINT uk_assembling_categories_by_name UNIQUE (parent_id, category_name);


--
-- TOC entry 2116 (class 2606 OID 36141)
-- Dependencies: 1676 1676 1676
-- Name: uk_assembling_schemas_by_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT uk_assembling_schemas_by_code UNIQUE (category_id, schema_code);


--
-- TOC entry 2118 (class 2606 OID 36143)
-- Dependencies: 1676 1676 1676
-- Name: uk_assembling_schemas_by_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT uk_assembling_schemas_by_name UNIQUE (category_id, schema_name);


--
-- TOC entry 2026 (class 2606 OID 25406)
-- Dependencies: 1642 1642 1642
-- Name: uk_cities_country_id_city_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY cities
    ADD CONSTRAINT uk_cities_country_id_city_name UNIQUE (country_id, city_name);


--
-- TOC entry 1978 (class 2606 OID 25155)
-- Dependencies: 1618 1618 1618
-- Name: uk_classifier_groups_parent_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifier_groups
    ADD CONSTRAINT uk_classifier_groups_parent_code UNIQUE (parent_id, classifier_group_code);


--
-- TOC entry 1980 (class 2606 OID 25157)
-- Dependencies: 1618 1618 1618
-- Name: uk_classifier_groups_parent_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifier_groups
    ADD CONSTRAINT uk_classifier_groups_parent_name UNIQUE (parent_id, classifier_group_name);


--
-- TOC entry 1984 (class 2606 OID 25159)
-- Dependencies: 1619 1619 1619
-- Name: uk_classifiers_parent_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT uk_classifiers_parent_code UNIQUE (parent_id, classifier_code);


--
-- TOC entry 1986 (class 2606 OID 25161)
-- Dependencies: 1619 1619 1619
-- Name: uk_classifiers_parent_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT uk_classifiers_parent_name UNIQUE (parent_id, classifier_name);


--
-- TOC entry 2074 (class 2606 OID 27140)
-- Dependencies: 1659 1659 1659 1659 1659
-- Name: uk_communication_contacts_parent_type_value_contact_person; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT uk_communication_contacts_parent_type_value_contact_person UNIQUE (parent_id, communication_type_id, communication_value, contact_person_id);


--
-- TOC entry 2022 (class 2606 OID 25396)
-- Dependencies: 1641 1641
-- Name: uk_countries_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY countries
    ADD CONSTRAINT uk_countries_name UNIQUE (country_name);


--
-- TOC entry 2030 (class 2606 OID 25441)
-- Dependencies: 1643 1643
-- Name: uk_currencies_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY currencies
    ADD CONSTRAINT uk_currencies_name UNIQUE (currency_name);


--
-- TOC entry 2080 (class 2606 OID 26269)
-- Dependencies: 1661 1661 1661
-- Name: uk_data_object_details_do_id_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_details
    ADD CONSTRAINT uk_data_object_details_do_id_code UNIQUE (data_object_id, detail_code);


--
-- TOC entry 2088 (class 2606 OID 26354)
-- Dependencies: 1663 1663 1663
-- Name: uk_data_object_links_parent_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_links
    ADD CONSTRAINT uk_data_object_links_parent_name UNIQUE (parent_id, link_name);


--
-- TOC entry 2053 (class 2606 OID 25968)
-- Dependencies: 1651 1651 1651
-- Name: uk_delivery_certificates_parent_cert_number; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT uk_delivery_certificates_parent_cert_number UNIQUE (parent_id, delivery_certificate_number);


--
-- TOC entry 1990 (class 2606 OID 25163)
-- Dependencies: 1620 1620
-- Name: uk_dot_data_object_type; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_types
    ADD CONSTRAINT uk_dot_data_object_type UNIQUE (data_object_type);


--
-- TOC entry 1996 (class 2606 OID 25165)
-- Dependencies: 1622 1622
-- Name: uk_enum_classes; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY enum_classes
    ADD CONSTRAINT uk_enum_classes UNIQUE (enum_class_name);


--
-- TOC entry 2037 (class 2606 OID 25617)
-- Dependencies: 1645 1645 1645
-- Name: uk_invoices_invoice_number; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT uk_invoices_invoice_number UNIQUE (parent_id, invoice_number);


--
-- TOC entry 2084 (class 2606 OID 26317)
-- Dependencies: 1662 1662 1662 1662
-- Name: uk_passports_parent_type_number; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT uk_passports_parent_type_number UNIQUE (parent_id, passport_type_id, passport_number);


--
-- TOC entry 2002 (class 2606 OID 25169)
-- Dependencies: 1625 1625 1625
-- Name: uk_product_categories_parent_category_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT uk_product_categories_parent_category_name UNIQUE (parent_id, category_name);


--
-- TOC entry 2043 (class 2606 OID 26110)
-- Dependencies: 1647 1647 1647
-- Name: uk_purchase_orders_parent_order_number; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT uk_purchase_orders_parent_order_number UNIQUE (parent_id, order_number);


--
-- TOC entry 2106 (class 2606 OID 27818)
-- Dependencies: 1674 1674
-- Name: uk_real_products_by_simple_product; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY real_products
    ADD CONSTRAINT uk_real_products_by_simple_product UNIQUE (simple_product_id);


--
-- TOC entry 2061 (class 2606 OID 26043)
-- Dependencies: 1654 1654 1654
-- Name: uk_receipt_certificates_parent_cert_number; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT uk_receipt_certificates_parent_cert_number UNIQUE (parent_id, receipt_certificate_number);


--
-- TOC entry 2010 (class 2606 OID 25171)
-- Dependencies: 1630 1630
-- Name: uk_seq_id_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY sequence_identifiers
    ADD CONSTRAINT uk_seq_id_name UNIQUE (seq_id_name);


--
-- TOC entry 2014 (class 2606 OID 25173)
-- Dependencies: 1632 1632
-- Name: uk_users_email_address; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT uk_users_email_address UNIQUE (email_address);


--
-- TOC entry 2016 (class 2606 OID 25175)
-- Dependencies: 1632 1632
-- Name: uk_users_user_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT uk_users_user_name UNIQUE (user_name);


--
-- TOC entry 2126 (class 2606 OID 36187)
-- Dependencies: 1680 1680 1680
-- Name: users_organizations_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY users_organizations
    ADD CONSTRAINT users_organizations_pkey PRIMARY KEY (user_id, organization_id);


--
-- TOC entry 2096 (class 2606 OID 27712)
-- Dependencies: 1670 1670
-- Name: warehouse_products_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY warehouse_products
    ADD CONSTRAINT warehouse_products_pkey PRIMARY KEY (warehouse_product_id);


--
-- TOC entry 2070 (class 1259 OID 27138)
-- Dependencies: 1659
-- Name: fk_contact_person_id; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX fk_contact_person_id ON communication_contacts USING btree (contact_person_id);


--
-- TOC entry 2033 (class 1259 OID 36154)
-- Dependencies: 1645
-- Name: fki_fk25f222e693edee7d; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX fki_fk25f222e693edee7d ON invoices USING btree (recipient_id);


--
-- TOC entry 2185 (class 2606 OID 27604)
-- Dependencies: 2089 1665 1640
-- Name: addresses_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT addresses_parent_id_fkey FOREIGN KEY (parent_id) REFERENCES business_partners(partner_id) ON DELETE CASCADE;


--
-- TOC entry 2324 (class 2606 OID 27382)
-- Dependencies: 2007 1660 1629
-- Name: bank_details_currency_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT bank_details_currency_id_fkey FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2326 (class 2606 OID 27641)
-- Dependencies: 1660 1640 2017
-- Name: bank_details_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT bank_details_parent_id_fkey FOREIGN KEY (parent_id) REFERENCES addresses(address_id) ON DELETE CASCADE;


--
-- TOC entry 2191 (class 2606 OID 27636)
-- Dependencies: 2019 1641 1642
-- Name: cities_country_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cities
    ADD CONSTRAINT cities_country_id_fkey FOREIGN KEY (country_id) REFERENCES countries(country_id) ON DELETE CASCADE;


--
-- TOC entry 2130 (class 2606 OID 27953)
-- Dependencies: 1991 1616 1621
-- Name: classified_objects_classified_object_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classified_objects
    ADD CONSTRAINT classified_objects_classified_object_id_fkey FOREIGN KEY (classified_object_id) REFERENCES data_objects(data_object_id) ON DELETE CASCADE;


--
-- TOC entry 2132 (class 2606 OID 27948)
-- Dependencies: 1981 1617 1619
-- Name: classifier_applied_for_dot_classifier_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifier_applied_for_dot
    ADD CONSTRAINT classifier_applied_for_dot_classifier_id_fkey FOREIGN KEY (classifier_id) REFERENCES classifiers(classifier_id) ON DELETE CASCADE;


--
-- TOC entry 2135 (class 2606 OID 27943)
-- Dependencies: 1619 1618 1975
-- Name: classifiers_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT classifiers_parent_id_fkey FOREIGN KEY (parent_id) REFERENCES classifier_groups(classifier_group_id) ON DELETE CASCADE;


--
-- TOC entry 2319 (class 2606 OID 27594)
-- Dependencies: 1659 1658 2068
-- Name: communication_contacts_contact_person_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT communication_contacts_contact_person_id_fkey FOREIGN KEY (contact_person_id) REFERENCES contact_persons(contact_person_id) ON DELETE SET NULL;


--
-- TOC entry 2318 (class 2606 OID 27589)
-- Dependencies: 1640 1659 2017
-- Name: communication_contacts_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT communication_contacts_parent_id_fkey FOREIGN KEY (parent_id) REFERENCES addresses(address_id) ON DELETE CASCADE;


--
-- TOC entry 2313 (class 2606 OID 27609)
-- Dependencies: 1658 1667 2093
-- Name: contact_persons_contact_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT contact_persons_contact_id_fkey FOREIGN KEY (contact_id) REFERENCES persons(partner_id) ON DELETE CASCADE;


--
-- TOC entry 2312 (class 2606 OID 27584)
-- Dependencies: 2017 1658 1640
-- Name: contact_persons_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT contact_persons_parent_id_fkey FOREIGN KEY (parent_id) REFERENCES addresses(address_id) ON DELETE CASCADE;


--
-- TOC entry 2188 (class 2606 OID 27552)
-- Dependencies: 1641 2007 1629
-- Name: countries_currency_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY countries
    ADD CONSTRAINT countries_currency_id_fkey FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2190 (class 2606 OID 27354)
-- Dependencies: 1642 1621 1991
-- Name: data_object_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cities
    ADD CONSTRAINT data_object_fk FOREIGN KEY (city_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2175 (class 2606 OID 26886)
-- Dependencies: 1629 1622 1993
-- Name: fk11ef5dd39219a9be; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY resource_bundle
    ADD CONSTRAINT fk11ef5dd39219a9be FOREIGN KEY (enum_class_id) REFERENCES enum_classes(enum_class_id);


--
-- TOC entry 2311 (class 2606 OID 26461)
-- Dependencies: 2066 1658 1657
-- Name: fk1e007a9f37f88bc6; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT fk1e007a9f37f88bc6 FOREIGN KEY (position_type_id) REFERENCES position_types(position_type_id);


--
-- TOC entry 2314 (class 2606 OID 27691)
-- Dependencies: 1658 2093 1667
-- Name: fk1e007a9f3c66048; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT fk1e007a9f3c66048 FOREIGN KEY (contact_id) REFERENCES persons(partner_id);


--
-- TOC entry 2210 (class 2606 OID 26581)
-- Dependencies: 1645 1629 2007
-- Name: fk25f222e617174fab; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e617174fab FOREIGN KEY (transportation_method_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2216 (class 2606 OID 26631)
-- Dependencies: 1629 1645 2007
-- Name: fk25f222e61808129a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e61808129a FOREIGN KEY (doc_delivery_method_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2217 (class 2606 OID 26636)
-- Dependencies: 2017 1645 1640
-- Name: fk25f222e627a66c93; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e627a66c93 FOREIGN KEY (branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2209 (class 2606 OID 26576)
-- Dependencies: 1629 1645 2007
-- Name: fk25f222e63aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e63aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2218 (class 2606 OID 26641)
-- Dependencies: 2007 1645 1629
-- Name: fk25f222e63dc9408c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e63dc9408c FOREIGN KEY (payment_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2213 (class 2606 OID 26606)
-- Dependencies: 1645 1629 2007
-- Name: fk25f222e646685c7a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e646685c7a FOREIGN KEY (delivery_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2220 (class 2606 OID 27183)
-- Dependencies: 1645 1667 2093
-- Name: fk25f222e649212a2e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e649212a2e FOREIGN KEY (recipient_contact_id) REFERENCES persons(partner_id);


--
-- TOC entry 2219 (class 2606 OID 27178)
-- Dependencies: 1645 1667 2093
-- Name: fk25f222e64da6f8bc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e64da6f8bc FOREIGN KEY (creator_id) REFERENCES persons(partner_id);


--
-- TOC entry 2222 (class 2606 OID 36149)
-- Dependencies: 1665 2089 1645
-- Name: fk25f222e693edee7d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e693edee7d FOREIGN KEY (recipient_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2212 (class 2606 OID 26591)
-- Dependencies: 1645 1629 2007
-- Name: fk25f222e696e3ba71; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e696e3ba71 FOREIGN KEY (payment_terms_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2211 (class 2606 OID 26586)
-- Dependencies: 1645 1629 2007
-- Name: fk25f222e69c49320d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e69c49320d FOREIGN KEY (status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2215 (class 2606 OID 26621)
-- Dependencies: 1645 2007 1629
-- Name: fk25f222e6a94f3ab3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e6a94f3ab3 FOREIGN KEY (invoice_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2208 (class 2606 OID 26571)
-- Dependencies: 1629 1645 2007
-- Name: fk25f222e6b07d659a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e6b07d659a FOREIGN KEY (vat_condition_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2214 (class 2606 OID 26611)
-- Dependencies: 1645 1663 2085
-- Name: fk25f222e6f61f3d82; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e6f61f3d82 FOREIGN KEY (shipping_agent_id) REFERENCES data_object_links(data_object_link_id);


--
-- TOC entry 2221 (class 2606 OID 27188)
-- Dependencies: 1645 2093 1667
-- Name: fk25f222e6fd5b3613; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e6fd5b3613 FOREIGN KEY (sender_id) REFERENCES persons(partner_id);


--
-- TOC entry 2228 (class 2606 OID 26556)
-- Dependencies: 2007 1629 1646
-- Name: fk326ab82e1ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoice_items
    ADD CONSTRAINT fk326ab82e1ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2229 (class 2606 OID 26561)
-- Dependencies: 2031 1644 1646
-- Name: fk326ab82e9f1800e1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoice_items
    ADD CONSTRAINT fk326ab82e9f1800e1 FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2231 (class 2606 OID 27421)
-- Dependencies: 1646 2005 1627
-- Name: fk326ab82ea330dfcf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoice_items
    ADD CONSTRAINT fk326ab82ea330dfcf FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2230 (class 2606 OID 26566)
-- Dependencies: 1646 1627 2005
-- Name: fk326ab82ef10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoice_items
    ADD CONSTRAINT fk326ab82ef10b9721 FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2255 (class 2606 OID 26656)
-- Dependencies: 2007 1649 1629
-- Name: fk327473ad3aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk327473ad3aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2257 (class 2606 OID 26671)
-- Dependencies: 1649 2007 1629
-- Name: fk327473ad9c49320d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk327473ad9c49320d FOREIGN KEY (status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2256 (class 2606 OID 26661)
-- Dependencies: 1649 2040 1647
-- Name: fk327473ad9fe0967e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk327473ad9fe0967e FOREIGN KEY (purchase_order_id) REFERENCES purchase_orders(purchase_order_id);


--
-- TOC entry 2258 (class 2606 OID 27193)
-- Dependencies: 1667 2093 1649
-- Name: fk327473adfd5b3613; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk327473adfd5b3613 FOREIGN KEY (sender_id) REFERENCES persons(partner_id);


--
-- TOC entry 2184 (class 2606 OID 26391)
-- Dependencies: 1640 2019 1641
-- Name: fk34207ba2977341c1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT fk34207ba2977341c1 FOREIGN KEY (country_id) REFERENCES countries(country_id);


--
-- TOC entry 2186 (class 2606 OID 27614)
-- Dependencies: 2023 1640 1642
-- Name: fk34207ba2ec30e373; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT fk34207ba2ec30e373 FOREIGN KEY (city_id) REFERENCES cities(city_id);


--
-- TOC entry 2327 (class 2606 OID 27676)
-- Dependencies: 1660 1640 2017
-- Name: fk363aa33f2f5fd250; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk363aa33f2f5fd250 FOREIGN KEY (bank_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2325 (class 2606 OID 27533)
-- Dependencies: 2091 1666 1660
-- Name: fk363aa33fee88a3ca; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk363aa33fee88a3ca FOREIGN KEY (bank_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2323 (class 2606 OID 27153)
-- Dependencies: 1660 1667 2093
-- Name: fk363aa33ff339b22b; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk363aa33ff339b22b FOREIGN KEY (bank_contact_id) REFERENCES persons(partner_id);


--
-- TOC entry 2273 (class 2606 OID 27163)
-- Dependencies: 1651 1667 2093
-- Name: fk3edb4c27157c075; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c27157c075 FOREIGN KEY (forwarder_contact_id) REFERENCES persons(partner_id);


--
-- TOC entry 2272 (class 2606 OID 26551)
-- Dependencies: 2007 1651 1629
-- Name: fk3edb4c2746dd317; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c2746dd317 FOREIGN KEY (delivery_cert_method_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2274 (class 2606 OID 27168)
-- Dependencies: 1651 1667 2093
-- Name: fk3edb4c2749212a2e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c2749212a2e FOREIGN KEY (recipient_contact_id) REFERENCES persons(partner_id);


--
-- TOC entry 2275 (class 2606 OID 27173)
-- Dependencies: 1651 1667 2093
-- Name: fk3edb4c274da6f8bc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c274da6f8bc FOREIGN KEY (creator_id) REFERENCES persons(partner_id);


--
-- TOC entry 2271 (class 2606 OID 26536)
-- Dependencies: 1629 2007 1651
-- Name: fk3edb4c278a6109cb; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c278a6109cb FOREIGN KEY (delivery_cert_reason_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2270 (class 2606 OID 26531)
-- Dependencies: 1651 2085 1663
-- Name: fk3edb4c2793edee7d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c2793edee7d FOREIGN KEY (recipient_id) REFERENCES data_object_links(data_object_link_id);


--
-- TOC entry 2269 (class 2606 OID 26521)
-- Dependencies: 1644 2031 1651
-- Name: fk3edb4c279f1800e1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c279f1800e1 FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2276 (class 2606 OID 27518)
-- Dependencies: 1666 1651 2091
-- Name: fk3edb4c27f2569c14; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c27f2569c14 FOREIGN KEY (forwarder_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2143 (class 2606 OID 27701)
-- Dependencies: 1624 1665 2089
-- Name: fk40afd1582b1363d6; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pattern_mask_formats
    ADD CONSTRAINT fk40afd1582b1363d6 FOREIGN KEY (owner_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2189 (class 2606 OID 27696)
-- Dependencies: 2007 1641 1629
-- Name: fk509f9ab43aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY countries
    ADD CONSTRAINT fk509f9ab43aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2147 (class 2606 OID 27545)
-- Dependencies: 1999 1625 1625
-- Name: fk5519b36c1c57732d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT fk5519b36c1c57732d FOREIGN KEY (parent_cat_id) REFERENCES product_categories(product_category_id);


--
-- TOC entry 2146 (class 2606 OID 26741)
-- Dependencies: 1997 1625 1624
-- Name: fk5519b36c7a956e19; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT fk5519b36c7a956e19 FOREIGN KEY (pattern_mask_format_id) REFERENCES pattern_mask_formats(pattern_mask_format_id);


--
-- TOC entry 2179 (class 2606 OID 36157)
-- Dependencies: 2011 1632 1632
-- Name: fk6a68e0844bb6904; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk6a68e0844bb6904 FOREIGN KEY (creator_id) REFERENCES users(user_id);


--
-- TOC entry 2178 (class 2606 OID 26891)
-- Dependencies: 1632 1621 1991
-- Name: fk6a68e08706848a7; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk6a68e08706848a7 FOREIGN KEY (person_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2176 (class 2606 OID 25181)
-- Dependencies: 1621 1991 1632
-- Name: fk6a68e08a08870b9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk6a68e08a08870b9 FOREIGN KEY (person_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2181 (class 2606 OID 36167)
-- Dependencies: 1632 1632 2011
-- Name: fk6a68e08f9f4b72; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk6a68e08f9f4b72 FOREIGN KEY (creator_id) REFERENCES users(user_id);


--
-- TOC entry 2339 (class 2606 OID 27114)
-- Dependencies: 1665 1621 1991
-- Name: fk6b9ae64a3d8dbb7d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_partners
    ADD CONSTRAINT fk6b9ae64a3d8dbb7d FOREIGN KEY (id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2151 (class 2606 OID 26751)
-- Dependencies: 1626 2085 1663
-- Name: fk725e8d72b6365ea; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk725e8d72b6365ea FOREIGN KEY (supplier_id) REFERENCES data_object_links(data_object_link_id);


--
-- TOC entry 2152 (class 2606 OID 27451)
-- Dependencies: 1626 2005 1627
-- Name: fk725e8d7a330dfcf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk725e8d7a330dfcf FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2150 (class 2606 OID 26746)
-- Dependencies: 2005 1626 1627
-- Name: fk725e8d7f10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk725e8d7f10b9721 FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2139 (class 2606 OID 26486)
-- Dependencies: 1621 1621 1991
-- Name: fk74e5117f2ff7d10e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk74e5117f2ff7d10e FOREIGN KEY (linked_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2141 (class 2606 OID 26496)
-- Dependencies: 1987 1620 1621
-- Name: fk74e5117fa44e2131; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk74e5117fa44e2131 FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2140 (class 2606 OID 26491)
-- Dependencies: 1621 1621 1991
-- Name: fk74e5117fafa1da5d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk74e5117fafa1da5d FOREIGN KEY (parent_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2303 (class 2606 OID 26831)
-- Dependencies: 2007 1655 1629
-- Name: fk7503fcd11ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk7503fcd11ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2305 (class 2606 OID 27461)
-- Dependencies: 2005 1655 1627
-- Name: fk7503fcd1a330dfcf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk7503fcd1a330dfcf FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2304 (class 2606 OID 26836)
-- Dependencies: 1627 2005 1655
-- Name: fk7503fcd1f10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk7503fcd1f10b9721 FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2262 (class 2606 OID 26646)
-- Dependencies: 2007 1629 1650
-- Name: fk7e6ecbc71ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmation_items
    ADD CONSTRAINT fk7e6ecbc71ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2264 (class 2606 OID 27426)
-- Dependencies: 1627 2005 1650
-- Name: fk7e6ecbc7a330dfcf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmation_items
    ADD CONSTRAINT fk7e6ecbc7a330dfcf FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2263 (class 2606 OID 26651)
-- Dependencies: 2005 1650 1627
-- Name: fk7e6ecbc7f10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmation_items
    ADD CONSTRAINT fk7e6ecbc7f10b9721 FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2346 (class 2606 OID 27513)
-- Dependencies: 1665 2089 1666
-- Name: fk8258b9a017025956; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk8258b9a017025956 FOREIGN KEY (organization_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2340 (class 2606 OID 27014)
-- Dependencies: 1666 1629 2007
-- Name: fk8258b9a0180e7eb9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk8258b9a0180e7eb9 FOREIGN KEY (organization_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2344 (class 2606 OID 27431)
-- Dependencies: 2007 1666 1629
-- Name: fk8258b9a03aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk8258b9a03aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2345 (class 2606 OID 27508)
-- Dependencies: 2091 1666 1666
-- Name: fk8258b9a08c46f1ed; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk8258b9a08c46f1ed FOREIGN KEY (registration_organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2338 (class 2606 OID 27245)
-- Dependencies: 1663 1991 1621
-- Name: fk9157692e2ff7d10e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_links
    ADD CONSTRAINT fk9157692e2ff7d10e FOREIGN KEY (linked_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2196 (class 2606 OID 27228)
-- Dependencies: 1667 1644 2093
-- Name: fk94f81e109757951; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouses
    ADD CONSTRAINT fk94f81e109757951 FOREIGN KEY (warehouseman_id) REFERENCES persons(partner_id);


--
-- TOC entry 2195 (class 2606 OID 26916)
-- Dependencies: 1644 1640 2017
-- Name: fk94f81e10a6877d01; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouses
    ADD CONSTRAINT fk94f81e10a6877d01 FOREIGN KEY (address_id) REFERENCES addresses(address_id);


--
-- TOC entry 2351 (class 2606 OID 27713)
-- Dependencies: 1644 1670 2031
-- Name: fk951433609f1800e1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouse_products
    ADD CONSTRAINT fk951433609f1800e1 FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2352 (class 2606 OID 27718)
-- Dependencies: 1627 1670 2005
-- Name: fk95143360a330dfcf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouse_products
    ADD CONSTRAINT fk95143360a330dfcf FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2307 (class 2606 OID 26841)
-- Dependencies: 2062 1656 1655
-- Name: fk98230d0e73d2d06a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_serial_numbers
    ADD CONSTRAINT fk98230d0e73d2d06a FOREIGN KEY (certificate_item_id) REFERENCES receipt_certificate_items(certificate_item_id);


--
-- TOC entry 2187 (class 2606 OID 27619)
-- Dependencies: 1642 2023 1640
-- Name: fk_addresses_city_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT fk_addresses_city_id FOREIGN KEY (city_id) REFERENCES cities(city_id);


--
-- TOC entry 2183 (class 2606 OID 25412)
-- Dependencies: 1640 2019 1641
-- Name: fk_addresses_country_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT fk_addresses_country_id FOREIGN KEY (country_id) REFERENCES countries(country_id);


--
-- TOC entry 2182 (class 2606 OID 25377)
-- Dependencies: 1640 1621 1991
-- Name: fk_addresses_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT fk_addresses_do_id FOREIGN KEY (address_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2359 (class 2606 OID 27834)
-- Dependencies: 1991 1675 1621
-- Name: fk_assembling_categories_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_categories
    ADD CONSTRAINT fk_assembling_categories_do FOREIGN KEY (assembling_category_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2365 (class 2606 OID 27899)
-- Dependencies: 1678 2119 1677
-- Name: fk_assembling_schema_item_values_as_item; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_item_values
    ADD CONSTRAINT fk_assembling_schema_item_values_as_item FOREIGN KEY (item_id) REFERENCES assembling_schema_items(item_id);


--
-- TOC entry 2366 (class 2606 OID 27904)
-- Dependencies: 2101 1678 1673
-- Name: fk_assembling_schema_item_values_vp; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_item_values
    ADD CONSTRAINT fk_assembling_schema_item_values_vp FOREIGN KEY (virtual_product_id) REFERENCES virtual_products(product_id);


--
-- TOC entry 2362 (class 2606 OID 27880)
-- Dependencies: 2007 1629 1677
-- Name: fk_assembling_schema_items_algorithm; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT fk_assembling_schema_items_algorithm FOREIGN KEY (algorithm_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2364 (class 2606 OID 36144)
-- Dependencies: 2007 1629 1677
-- Name: fk_assembling_schema_items_data_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT fk_assembling_schema_items_data_type FOREIGN KEY (data_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2363 (class 2606 OID 27885)
-- Dependencies: 2113 1677 1676
-- Name: fk_assembling_schema_items_owner; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT fk_assembling_schema_items_owner FOREIGN KEY (assembling_schema_id) REFERENCES assembling_schemas(product_id);


--
-- TOC entry 2361 (class 2606 OID 27853)
-- Dependencies: 1675 2107 1676
-- Name: fk_assembling_schemas_categories; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT fk_assembling_schemas_categories FOREIGN KEY (category_id) REFERENCES assembling_categories(assembling_category_id);


--
-- TOC entry 2360 (class 2606 OID 27848)
-- Dependencies: 2101 1676 1673
-- Name: fk_assembling_schemas_virtual_products; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT fk_assembling_schemas_virtual_products FOREIGN KEY (product_id) REFERENCES virtual_products(product_id);


--
-- TOC entry 2322 (class 2606 OID 26250)
-- Dependencies: 2017 1660 1640
-- Name: fk_bank_details_bank_branch; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk_bank_details_bank_branch FOREIGN KEY (bank_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2321 (class 2606 OID 26235)
-- Dependencies: 1660 1991 1621
-- Name: fk_bank_details_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk_bank_details_do_id FOREIGN KEY (bank_detail_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2129 (class 2606 OID 25196)
-- Dependencies: 1619 1981 1616
-- Name: fk_classified_objects_classifier_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classified_objects
    ADD CONSTRAINT fk_classified_objects_classifier_id FOREIGN KEY (classifier_id) REFERENCES classifiers(classifier_id);


--
-- TOC entry 2131 (class 2606 OID 25211)
-- Dependencies: 1617 1987 1620
-- Name: fk_classifier_applied_for_dot_dot_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifier_applied_for_dot
    ADD CONSTRAINT fk_classifier_applied_for_dot_dot_id FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2133 (class 2606 OID 25216)
-- Dependencies: 1618 1621 1991
-- Name: fk_classifier_groups_cg_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifier_groups
    ADD CONSTRAINT fk_classifier_groups_cg_id FOREIGN KEY (classifier_group_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2134 (class 2606 OID 25226)
-- Dependencies: 1621 1619 1991
-- Name: fk_classifiers_id_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT fk_classifiers_id_do_id FOREIGN KEY (classifier_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2316 (class 2606 OID 26217)
-- Dependencies: 1629 2007 1659
-- Name: fk_communication_contacts_comm_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT fk_communication_contacts_comm_type FOREIGN KEY (communication_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2315 (class 2606 OID 26207)
-- Dependencies: 1659 1991 1621
-- Name: fk_communication_contacts_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT fk_communication_contacts_do_id FOREIGN KEY (communication_contact_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2367 (class 2606 OID 27925)
-- Dependencies: 2007 1679 1629
-- Name: fk_complex_product_items_algorithm; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT fk_complex_product_items_algorithm FOREIGN KEY (applied_algorithm_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2368 (class 2606 OID 27930)
-- Dependencies: 1672 2099 1679
-- Name: fk_complex_product_items_cp; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT fk_complex_product_items_cp FOREIGN KEY (complex_product_id) REFERENCES complex_products(product_id);


--
-- TOC entry 2369 (class 2606 OID 27935)
-- Dependencies: 1671 2097 1679
-- Name: fk_complex_product_items_products; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT fk_complex_product_items_products FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2354 (class 2606 OID 27774)
-- Dependencies: 1672 2097 1671
-- Name: fk_complex_products_products; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_products
    ADD CONSTRAINT fk_complex_products_products FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2355 (class 2606 OID 27779)
-- Dependencies: 2007 1672 1629
-- Name: fk_complex_products_resource_bundle; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_products
    ADD CONSTRAINT fk_complex_products_resource_bundle FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2309 (class 2606 OID 26164)
-- Dependencies: 1658 1991 1621
-- Name: fk_contact_persons_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT fk_contact_persons_do_id FOREIGN KEY (contact_person_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2310 (class 2606 OID 26174)
-- Dependencies: 1657 2066 1658
-- Name: fk_contact_persons_position_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT fk_contact_persons_position_type FOREIGN KEY (position_type_id) REFERENCES position_types(position_type_id);


--
-- TOC entry 2328 (class 2606 OID 26270)
-- Dependencies: 1991 1661 1621
-- Name: fk_data_object_details_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_details
    ADD CONSTRAINT fk_data_object_details_do_id FOREIGN KEY (data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2336 (class 2606 OID 26338)
-- Dependencies: 1991 1621 1663
-- Name: fk_data_object_links_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_links
    ADD CONSTRAINT fk_data_object_links_do_id FOREIGN KEY (data_object_link_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2337 (class 2606 OID 26343)
-- Dependencies: 1663 1621 1991
-- Name: fk_data_object_links_linked_object; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_links
    ADD CONSTRAINT fk_data_object_links_linked_object FOREIGN KEY (linked_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2138 (class 2606 OID 26348)
-- Dependencies: 1621 1620 1987
-- Name: fk_data_objects_do_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk_data_objects_do_type FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2136 (class 2606 OID 25231)
-- Dependencies: 1991 1621 1621
-- Name: fk_data_objects_linked_data_object_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk_data_objects_linked_data_object_id FOREIGN KEY (linked_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2137 (class 2606 OID 25236)
-- Dependencies: 1991 1621 1621
-- Name: fk_data_objects_parent_data_object_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk_data_objects_parent_data_object_id FOREIGN KEY (parent_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2278 (class 2606 OID 26007)
-- Dependencies: 1652 2050 1651
-- Name: fk_delivery_certificate_items_delivery_cert; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fk_delivery_certificate_items_delivery_cert FOREIGN KEY (parent_id) REFERENCES delivery_certificates(delivery_certificate_id);


--
-- TOC entry 2277 (class 2606 OID 26002)
-- Dependencies: 1621 1652 1991
-- Name: fk_delivery_certificate_items_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fk_delivery_certificate_items_do_id FOREIGN KEY (certificate_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2280 (class 2606 OID 26017)
-- Dependencies: 1629 2007 1652
-- Name: fk_delivery_certificate_items_measure_unit; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fk_delivery_certificate_items_measure_unit FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2279 (class 2606 OID 26012)
-- Dependencies: 1652 2005 1627
-- Name: fk_delivery_certificate_items_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fk_delivery_certificate_items_product FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2284 (class 2606 OID 26027)
-- Dependencies: 1652 2054 1653
-- Name: fk_delivery_certificate_serial_numbers_dci; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_serial_numbers
    ADD CONSTRAINT fk_delivery_certificate_serial_numbers_dci FOREIGN KEY (certificate_item_id) REFERENCES delivery_certificate_items(certificate_item_id);


--
-- TOC entry 2265 (class 2606 OID 25972)
-- Dependencies: 1621 1991 1651
-- Name: fk_delivery_certificates_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_delivery_certificates_do_id FOREIGN KEY (delivery_certificate_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2268 (class 2606 OID 26032)
-- Dependencies: 2007 1651 1629
-- Name: fk_delivery_certificates_reason; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_delivery_certificates_reason FOREIGN KEY (delivery_cert_reason_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2267 (class 2606 OID 25987)
-- Dependencies: 2007 1629 1651
-- Name: fk_delivery_certificates_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_delivery_certificates_type FOREIGN KEY (delivery_cert_method_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2266 (class 2606 OID 25977)
-- Dependencies: 1644 1651 2031
-- Name: fk_delivery_certificates_warehouse; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_delivery_certificates_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2223 (class 2606 OID 25728)
-- Dependencies: 1991 1621 1646
-- Name: fk_invoice_items_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoice_items
    ADD CONSTRAINT fk_invoice_items_do_id FOREIGN KEY (invoice_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2224 (class 2606 OID 25733)
-- Dependencies: 1645 2034 1646
-- Name: fk_invoice_items_invoice_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoice_items
    ADD CONSTRAINT fk_invoice_items_invoice_id FOREIGN KEY (parent_id) REFERENCES invoices(invoice_id);


--
-- TOC entry 2227 (class 2606 OID 25748)
-- Dependencies: 2007 1646 1629
-- Name: fk_invoice_items_measure_unit_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoice_items
    ADD CONSTRAINT fk_invoice_items_measure_unit_id FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2225 (class 2606 OID 25738)
-- Dependencies: 2005 1646 1627
-- Name: fk_invoice_items_product_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoice_items
    ADD CONSTRAINT fk_invoice_items_product_id FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2226 (class 2606 OID 25743)
-- Dependencies: 1644 1646 2031
-- Name: fk_invoice_items_warehouse_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoice_items
    ADD CONSTRAINT fk_invoice_items_warehouse_id FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2197 (class 2606 OID 25618)
-- Dependencies: 2017 1645 1640
-- Name: fk_invoices_branch_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_branch_id FOREIGN KEY (branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2198 (class 2606 OID 25628)
-- Dependencies: 1645 2007 1629
-- Name: fk_invoices_currency_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_currency_id FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2199 (class 2606 OID 25633)
-- Dependencies: 1645 2007 1629
-- Name: fk_invoices_delivery_type_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_delivery_type_id FOREIGN KEY (delivery_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2200 (class 2606 OID 25638)
-- Dependencies: 1991 1621 1645
-- Name: fk_invoices_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_do_id FOREIGN KEY (invoice_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2201 (class 2606 OID 25643)
-- Dependencies: 1629 2007 1645
-- Name: fk_invoices_doc_delivery_method_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_doc_delivery_method_id FOREIGN KEY (doc_delivery_method_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2202 (class 2606 OID 25648)
-- Dependencies: 1629 2007 1645
-- Name: fk_invoices_invoice_type_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_invoice_type_id FOREIGN KEY (invoice_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2203 (class 2606 OID 25653)
-- Dependencies: 1629 1645 2007
-- Name: fk_invoices_payment_terms_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_payment_terms_id FOREIGN KEY (payment_terms_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2204 (class 2606 OID 25658)
-- Dependencies: 2007 1629 1645
-- Name: fk_invoices_payment_type_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_payment_type_id FOREIGN KEY (payment_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2205 (class 2606 OID 25673)
-- Dependencies: 2007 1645 1629
-- Name: fk_invoices_status_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_status_id FOREIGN KEY (status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2206 (class 2606 OID 25678)
-- Dependencies: 1645 2007 1629
-- Name: fk_invoices_transportation_method_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_transportation_method_id FOREIGN KEY (transportation_method_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2207 (class 2606 OID 25683)
-- Dependencies: 1629 2007 1645
-- Name: fk_invoices_vat_condition_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_vat_condition_id FOREIGN KEY (vat_condition_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2261 (class 2606 OID 25957)
-- Dependencies: 2007 1650 1629
-- Name: fk_order_confirmation_items_measure_unit; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmation_items
    ADD CONSTRAINT fk_order_confirmation_items_measure_unit FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2259 (class 2606 OID 25947)
-- Dependencies: 1649 1650 2046
-- Name: fk_order_confirmation_items_poc_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmation_items
    ADD CONSTRAINT fk_order_confirmation_items_poc_id FOREIGN KEY (parent_id) REFERENCES order_confirmations(order_confirmation_id);


--
-- TOC entry 2260 (class 2606 OID 25952)
-- Dependencies: 2005 1650 1627
-- Name: fk_order_confirmation_items_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmation_items
    ADD CONSTRAINT fk_order_confirmation_items_product FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2251 (class 2606 OID 25914)
-- Dependencies: 2007 1629 1649
-- Name: fk_order_confirmations_currency; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk_order_confirmations_currency FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2252 (class 2606 OID 25919)
-- Dependencies: 1649 1621 1991
-- Name: fk_order_confirmations_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk_order_confirmations_do_id FOREIGN KEY (order_confirmation_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2253 (class 2606 OID 25924)
-- Dependencies: 1649 1647 2040
-- Name: fk_order_confirmations_po_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk_order_confirmations_po_id FOREIGN KEY (purchase_order_id) REFERENCES purchase_orders(purchase_order_id);


--
-- TOC entry 2254 (class 2606 OID 25934)
-- Dependencies: 2007 1649 1629
-- Name: fk_order_confirmations_status; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk_order_confirmations_status FOREIGN KEY (status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2331 (class 2606 OID 26300)
-- Dependencies: 1662 1991 1621
-- Name: fk_passports_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT fk_passports_do_id FOREIGN KEY (passport_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2330 (class 2606 OID 26293)
-- Dependencies: 1662 2017 1640
-- Name: fk_passports_issuer_branch; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT fk_passports_issuer_branch FOREIGN KEY (issuer_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2329 (class 2606 OID 26288)
-- Dependencies: 1662 2007 1629
-- Name: fk_passports_pass_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT fk_passports_pass_type FOREIGN KEY (passport_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2308 (class 2606 OID 26154)
-- Dependencies: 1621 1991 1657
-- Name: fk_position_types_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY position_types
    ADD CONSTRAINT fk_position_types_do_id FOREIGN KEY (position_type_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2144 (class 2606 OID 25251)
-- Dependencies: 1625 1624 1997
-- Name: fk_product_categories_pattern_mask_format_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT fk_product_categories_pattern_mask_format_id FOREIGN KEY (pattern_mask_format_id) REFERENCES pattern_mask_formats(pattern_mask_format_id);


--
-- TOC entry 2145 (class 2606 OID 25256)
-- Dependencies: 1625 1621 1991
-- Name: fk_product_categories_product_category_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT fk_product_categories_product_category_id FOREIGN KEY (product_category_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2153 (class 2606 OID 25261)
-- Dependencies: 1627 1625 1999
-- Name: fk_product_category_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_product_category_id FOREIGN KEY (category_id) REFERENCES product_categories(product_category_id);


--
-- TOC entry 2148 (class 2606 OID 25266)
-- Dependencies: 1626 1627 2005
-- Name: fk_product_suppliers_product_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk_product_suppliers_product_id FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2149 (class 2606 OID 26365)
-- Dependencies: 1663 1626 2085
-- Name: fk_product_suppliers_supplier; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk_product_suppliers_supplier FOREIGN KEY (supplier_id) REFERENCES data_object_links(data_object_link_id);


--
-- TOC entry 2353 (class 2606 OID 27759)
-- Dependencies: 1621 1671 1991
-- Name: fk_products1_product_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_products1_product_id FOREIGN KEY (product_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2154 (class 2606 OID 25271)
-- Dependencies: 2007 1629 1627
-- Name: fk_products_color_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_products_color_id FOREIGN KEY (product_color_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2155 (class 2606 OID 25276)
-- Dependencies: 1629 1627 2007
-- Name: fk_products_dimension_unit_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_products_dimension_unit_id FOREIGN KEY (dimension_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2156 (class 2606 OID 25281)
-- Dependencies: 1629 1627 2007
-- Name: fk_products_measure_unit_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_products_measure_unit_id FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2157 (class 2606 OID 25286)
-- Dependencies: 1997 1624 1627
-- Name: fk_products_pattern_mask_format_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_products_pattern_mask_format_id FOREIGN KEY (pattern_mask_format_id) REFERENCES pattern_mask_formats(pattern_mask_format_id);


--
-- TOC entry 2158 (class 2606 OID 25296)
-- Dependencies: 2007 1627 1629
-- Name: fk_products_weight_unit_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_products_weight_unit_id FOREIGN KEY (weight_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2244 (class 2606 OID 25859)
-- Dependencies: 1991 1621 1648
-- Name: fk_purchase_order_items_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fk_purchase_order_items_do_id FOREIGN KEY (order_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2247 (class 2606 OID 25874)
-- Dependencies: 1629 2007 1648
-- Name: fk_purchase_order_items_measure_unit; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fk_purchase_order_items_measure_unit FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2245 (class 2606 OID 25864)
-- Dependencies: 1647 2040 1648
-- Name: fk_purchase_order_items_po_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fk_purchase_order_items_po_id FOREIGN KEY (parent_id) REFERENCES purchase_orders(purchase_order_id);


--
-- TOC entry 2246 (class 2606 OID 25869)
-- Dependencies: 2005 1648 1627
-- Name: fk_purchase_order_items_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fk_purchase_order_items_product FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2232 (class 2606 OID 25816)
-- Dependencies: 1640 2017 1647
-- Name: fk_purchase_orders_branch_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fk_purchase_orders_branch_id FOREIGN KEY (branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2233 (class 2606 OID 25831)
-- Dependencies: 1647 1621 1991
-- Name: fk_purchase_orders_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fk_purchase_orders_do_id FOREIGN KEY (purchase_order_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2234 (class 2606 OID 25836)
-- Dependencies: 2007 1629 1647
-- Name: fk_purchase_orders_doc_delivery_method; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fk_purchase_orders_doc_delivery_method FOREIGN KEY (doc_delivery_method_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2235 (class 2606 OID 25846)
-- Dependencies: 1629 1647 2007
-- Name: fk_purchase_orders_status_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fk_purchase_orders_status_id FOREIGN KEY (status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2236 (class 2606 OID 26360)
-- Dependencies: 1647 2085 1663
-- Name: fk_purchase_orders_supplier; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fk_purchase_orders_supplier FOREIGN KEY (supplier_id) REFERENCES data_object_links(data_object_link_id);


--
-- TOC entry 2357 (class 2606 OID 27819)
-- Dependencies: 2005 1674 1627
-- Name: fk_real_products_simple_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY real_products
    ADD CONSTRAINT fk_real_products_simple_product FOREIGN KEY (simple_product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2358 (class 2606 OID 27824)
-- Dependencies: 1673 1674 2101
-- Name: fk_real_products_virtual_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY real_products
    ADD CONSTRAINT fk_real_products_virtual_product FOREIGN KEY (product_id) REFERENCES virtual_products(product_id);


--
-- TOC entry 2300 (class 2606 OID 26084)
-- Dependencies: 1621 1655 1991
-- Name: fk_receipt_certificate_items_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk_receipt_certificate_items_do_id FOREIGN KEY (certificate_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2301 (class 2606 OID 26089)
-- Dependencies: 1655 1629 2007
-- Name: fk_receipt_certificate_items_measure_unit; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk_receipt_certificate_items_measure_unit FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2302 (class 2606 OID 26094)
-- Dependencies: 2005 1655 1627
-- Name: fk_receipt_certificate_items_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk_receipt_certificate_items_product FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2299 (class 2606 OID 26079)
-- Dependencies: 2058 1654 1655
-- Name: fk_receipt_certificate_items_receipt_cert; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk_receipt_certificate_items_receipt_cert FOREIGN KEY (parent_id) REFERENCES receipt_certificates(receipt_certificate_id);


--
-- TOC entry 2306 (class 2606 OID 26104)
-- Dependencies: 1656 1655 2062
-- Name: fk_receipt_certificate_serial_numbers_dci; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_serial_numbers
    ADD CONSTRAINT fk_receipt_certificate_serial_numbers_dci FOREIGN KEY (certificate_item_id) REFERENCES receipt_certificate_items(certificate_item_id);


--
-- TOC entry 2290 (class 2606 OID 26355)
-- Dependencies: 1663 1654 2085
-- Name: fk_receipt_certificates_deliverer; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_deliverer FOREIGN KEY (deliverer_id) REFERENCES data_object_links(data_object_link_id);


--
-- TOC entry 2286 (class 2606 OID 26044)
-- Dependencies: 1991 1621 1654
-- Name: fk_receipt_certificates_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_do_id FOREIGN KEY (receipt_certificate_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2287 (class 2606 OID 26054)
-- Dependencies: 1654 1629 2007
-- Name: fk_receipt_certificates_reason; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_reason FOREIGN KEY (receipt_cert_reason_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2288 (class 2606 OID 26064)
-- Dependencies: 1629 1654 2007
-- Name: fk_receipt_certificates_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_type FOREIGN KEY (receipt_cert_method_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2289 (class 2606 OID 26069)
-- Dependencies: 1654 1644 2031
-- Name: fk_receipt_certificates_warehouse; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2174 (class 2606 OID 25301)
-- Dependencies: 1993 1629 1622
-- Name: fk_resource_bundle_enum_class_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY resource_bundle
    ADD CONSTRAINT fk_resource_bundle_enum_class_id FOREIGN KEY (enum_class_id) REFERENCES enum_classes(enum_class_id);


--
-- TOC entry 2173 (class 2606 OID 27764)
-- Dependencies: 2097 1627 1671
-- Name: fk_simple_products_products; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_simple_products_products FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2180 (class 2606 OID 36162)
-- Dependencies: 2011 1632 1632
-- Name: fk_users_creator_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk_users_creator_id FOREIGN KEY (creator_id) REFERENCES users(user_id);


--
-- TOC entry 2177 (class 2606 OID 25311)
-- Dependencies: 1632 1991 1621
-- Name: fk_users_person_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk_users_person_id FOREIGN KEY (person_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2356 (class 2606 OID 27807)
-- Dependencies: 1991 1673 1621
-- Name: fk_virtual_products_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY virtual_products
    ADD CONSTRAINT fk_virtual_products_do FOREIGN KEY (product_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2193 (class 2606 OID 25502)
-- Dependencies: 1640 1644 2017
-- Name: fk_warehouses_address_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouses
    ADD CONSTRAINT fk_warehouses_address_id FOREIGN KEY (address_id) REFERENCES addresses(address_id);


--
-- TOC entry 2194 (class 2606 OID 26132)
-- Dependencies: 1991 1644 1621
-- Name: fk_warehouses_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouses
    ADD CONSTRAINT fk_warehouses_do_id FOREIGN KEY (warehouse_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2248 (class 2606 OID 26786)
-- Dependencies: 1648 2007 1629
-- Name: fka00989511ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fka00989511ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2250 (class 2606 OID 27456)
-- Dependencies: 1648 1627 2005
-- Name: fka0098951a330dfcf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fka0098951a330dfcf FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2249 (class 2606 OID 26791)
-- Dependencies: 1648 2005 1627
-- Name: fka0098951f10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fka0098951f10b9721 FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2281 (class 2606 OID 26501)
-- Dependencies: 1629 1652 2007
-- Name: fka8be2f8d1ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fka8be2f8d1ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2283 (class 2606 OID 27416)
-- Dependencies: 2005 1627 1652
-- Name: fka8be2f8da330dfcf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fka8be2f8da330dfcf FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2282 (class 2606 OID 26506)
-- Dependencies: 1627 1652 2005
-- Name: fka8be2f8df10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fka8be2f8df10b9721 FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2320 (class 2606 OID 27686)
-- Dependencies: 1658 1659 2068
-- Name: fkac65329c8da5f302; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT fkac65329c8da5f302 FOREIGN KEY (contact_person_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2317 (class 2606 OID 26456)
-- Dependencies: 2007 1659 1629
-- Name: fkac65329c90a50e5c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT fkac65329c90a50e5c FOREIGN KEY (communication_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2192 (class 2606 OID 27681)
-- Dependencies: 2019 1641 1642
-- Name: fkaeedbb49977341c1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cities
    ADD CONSTRAINT fkaeedbb49977341c1 FOREIGN KEY (country_id) REFERENCES countries(country_id);


--
-- TOC entry 2285 (class 2606 OID 26511)
-- Dependencies: 1652 2054 1653
-- Name: fkb3b02dd2d961ef9c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_serial_numbers
    ADD CONSTRAINT fkb3b02dd2d961ef9c FOREIGN KEY (certificate_item_id) REFERENCES delivery_certificate_items(certificate_item_id);


--
-- TOC entry 2238 (class 2606 OID 26806)
-- Dependencies: 1647 1629 2007
-- Name: fkc307e7e31808129a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e31808129a FOREIGN KEY (doc_delivery_method_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2240 (class 2606 OID 26816)
-- Dependencies: 1647 1640 2017
-- Name: fkc307e7e327a66c93; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e327a66c93 FOREIGN KEY (branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2239 (class 2606 OID 26811)
-- Dependencies: 1647 1663 2085
-- Name: fkc307e7e32b6365ea; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e32b6365ea FOREIGN KEY (supplier_id) REFERENCES data_object_links(data_object_link_id);


--
-- TOC entry 2242 (class 2606 OID 27203)
-- Dependencies: 2093 1667 1647
-- Name: fkc307e7e34da6f8bc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e34da6f8bc FOREIGN KEY (creator_id) REFERENCES persons(partner_id);


--
-- TOC entry 2243 (class 2606 OID 27208)
-- Dependencies: 2093 1647 1667
-- Name: fkc307e7e38c52e49b; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e38c52e49b FOREIGN KEY (supplier_contact_id) REFERENCES persons(partner_id);


--
-- TOC entry 2237 (class 2606 OID 26801)
-- Dependencies: 1629 2007 1647
-- Name: fkc307e7e39c49320d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e39c49320d FOREIGN KEY (status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2241 (class 2606 OID 27198)
-- Dependencies: 1667 2093 1647
-- Name: fkc307e7e3fd5b3613; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e3fd5b3613 FOREIGN KEY (sender_id) REFERENCES persons(partner_id);


--
-- TOC entry 2160 (class 2606 OID 26761)
-- Dependencies: 1629 2007 1627
-- Name: fkc42bd1641ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd1641ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2162 (class 2606 OID 26771)
-- Dependencies: 1627 2007 1629
-- Name: fkc42bd16427acd874; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd16427acd874 FOREIGN KEY (weight_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2163 (class 2606 OID 26776)
-- Dependencies: 1627 1625 1999
-- Name: fkc42bd1646e2f83d0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd1646e2f83d0 FOREIGN KEY (category_id) REFERENCES product_categories(product_category_id);


--
-- TOC entry 2172 (class 2606 OID 27734)
-- Dependencies: 2089 1627 1665
-- Name: fkc42bd1646e436697; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd1646e436697 FOREIGN KEY (producer_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2161 (class 2606 OID 26766)
-- Dependencies: 1624 1997 1627
-- Name: fkc42bd1647a956e19; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd1647a956e19 FOREIGN KEY (pattern_mask_format_id) REFERENCES pattern_mask_formats(pattern_mask_format_id);


--
-- TOC entry 2159 (class 2606 OID 26756)
-- Dependencies: 1629 1627 2007
-- Name: fkc42bd1648f60524c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd1648f60524c FOREIGN KEY (product_color_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2164 (class 2606 OID 26781)
-- Dependencies: 1627 2007 1629
-- Name: fkc42bd1649d6c3562; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd1649d6c3562 FOREIGN KEY (dimension_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2332 (class 2606 OID 26706)
-- Dependencies: 1640 2017 1662
-- Name: fkc84af6a180bd868d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT fkc84af6a180bd868d FOREIGN KEY (issuer_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2333 (class 2606 OID 26716)
-- Dependencies: 2007 1629 1662
-- Name: fkc84af6a1ad6ece98; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT fkc84af6a1ad6ece98 FOREIGN KEY (passport_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2334 (class 2606 OID 27523)
-- Dependencies: 1666 1662 2091
-- Name: fkc84af6a1db08a2d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT fkc84af6a1db08a2d FOREIGN KEY (issuer_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2348 (class 2606 OID 27044)
-- Dependencies: 1667 1641 2019
-- Name: fkd78fcfbe16ffc779; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT fkd78fcfbe16ffc779 FOREIGN KEY (birth_place_country_id) REFERENCES countries(country_id);


--
-- TOC entry 2347 (class 2606 OID 27034)
-- Dependencies: 1667 1629 2007
-- Name: fkd78fcfbe2663e8de; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT fkd78fcfbe2663e8de FOREIGN KEY (gender_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2350 (class 2606 OID 27557)
-- Dependencies: 1642 2023 1667
-- Name: fkd78fcfbe4250f8bb; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT fkd78fcfbe4250f8bb FOREIGN KEY (birth_place_city_id) REFERENCES cities(city_id);


--
-- TOC entry 2349 (class 2606 OID 27148)
-- Dependencies: 1667 1665 2089
-- Name: fkd78fcfbed213c5a1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT fkd78fcfbed213c5a1 FOREIGN KEY (partner_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2165 (class 2606 OID 27466)
-- Dependencies: 1629 1627 2007
-- Name: fke81099511ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke81099511ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2168 (class 2606 OID 27481)
-- Dependencies: 1627 2007 1629
-- Name: fke810995127acd874; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke810995127acd874 FOREIGN KEY (weight_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2169 (class 2606 OID 27486)
-- Dependencies: 1625 1999 1627
-- Name: fke81099516e2f83d0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke81099516e2f83d0 FOREIGN KEY (category_id) REFERENCES product_categories(product_category_id);


--
-- TOC entry 2171 (class 2606 OID 27729)
-- Dependencies: 1665 1627 2089
-- Name: fke81099516e436697; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke81099516e436697 FOREIGN KEY (producer_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2167 (class 2606 OID 27476)
-- Dependencies: 1627 1997 1624
-- Name: fke81099517a956e19; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke81099517a956e19 FOREIGN KEY (pattern_mask_format_id) REFERENCES pattern_mask_formats(pattern_mask_format_id);


--
-- TOC entry 2166 (class 2606 OID 27471)
-- Dependencies: 1629 2007 1627
-- Name: fke81099518f60524c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke81099518f60524c FOREIGN KEY (product_color_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2170 (class 2606 OID 27496)
-- Dependencies: 1629 2007 1627
-- Name: fke81099519d6c3562; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke81099519d6c3562 FOREIGN KEY (dimension_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2295 (class 2606 OID 27213)
-- Dependencies: 1654 2093 1667
-- Name: fke9334463157c075; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke9334463157c075 FOREIGN KEY (forwarder_contact_id) REFERENCES persons(partner_id);


--
-- TOC entry 2296 (class 2606 OID 27218)
-- Dependencies: 1654 2093 1667
-- Name: fke93344634da6f8bc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke93344634da6f8bc FOREIGN KEY (creator_id) REFERENCES persons(partner_id);


--
-- TOC entry 2297 (class 2606 OID 27223)
-- Dependencies: 1654 1667 2093
-- Name: fke93344636faaa615; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke93344636faaa615 FOREIGN KEY (deliverer_contact_id) REFERENCES persons(partner_id);


--
-- TOC entry 2294 (class 2606 OID 26881)
-- Dependencies: 1654 1663 2085
-- Name: fke933446370294164; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke933446370294164 FOREIGN KEY (deliverer_id) REFERENCES data_object_links(data_object_link_id);


--
-- TOC entry 2291 (class 2606 OID 26851)
-- Dependencies: 2031 1644 1654
-- Name: fke93344639f1800e1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke93344639f1800e1 FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2292 (class 2606 OID 26856)
-- Dependencies: 2007 1629 1654
-- Name: fke9334463d6755f5b; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke9334463d6755f5b FOREIGN KEY (receipt_cert_method_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2298 (class 2606 OID 27528)
-- Dependencies: 1654 1666 2091
-- Name: fke9334463f2569c14; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke9334463f2569c14 FOREIGN KEY (forwarder_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2293 (class 2606 OID 26861)
-- Dependencies: 1654 1629 2007
-- Name: fke9334463fe9be307; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke9334463fe9be307 FOREIGN KEY (receipt_cert_reason_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2342 (class 2606 OID 27338)
-- Dependencies: 1640 2017 1666
-- Name: organizations_administration_address_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT organizations_administration_address_id_fkey FOREIGN KEY (administration_address_id) REFERENCES addresses(address_id) ON DELETE SET NULL;


--
-- TOC entry 2343 (class 2606 OID 27396)
-- Dependencies: 2007 1629 1666
-- Name: organizations_currency_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT organizations_currency_id_fkey FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2341 (class 2606 OID 27333)
-- Dependencies: 2017 1640 1666
-- Name: organizations_registration_address_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT organizations_registration_address_id_fkey FOREIGN KEY (registration_address_id) REFERENCES addresses(address_id) ON DELETE SET NULL;


--
-- TOC entry 2335 (class 2606 OID 27631)
-- Dependencies: 2093 1667 1662
-- Name: passports_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT passports_parent_id_fkey FOREIGN KEY (parent_id) REFERENCES persons(partner_id) ON DELETE CASCADE;


--
-- TOC entry 2142 (class 2606 OID 27671)
-- Dependencies: 2089 1624 1665
-- Name: pattern_mask_formats_owner_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pattern_mask_formats
    ADD CONSTRAINT pattern_mask_formats_owner_id_fkey FOREIGN KEY (owner_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2371 (class 2606 OID 36193)
-- Dependencies: 1666 2091 1680
-- Name: users_organizations_organization_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users_organizations
    ADD CONSTRAINT users_organizations_organization_id_fkey FOREIGN KEY (organization_id) REFERENCES organizations(organization_id) ON DELETE CASCADE;


--
-- TOC entry 2370 (class 2606 OID 36188)
-- Dependencies: 1632 2011 1680
-- Name: users_organizations_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users_organizations
    ADD CONSTRAINT users_organizations_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE;


--
-- TOC entry 2429 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: -
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2008-06-29 20:45:09

--
-- PostgreSQL database dump complete
--

