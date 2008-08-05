--
-- PostgreSQL database dump
--

-- Started on 2008-08-03 19:38:50

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 1613 (class 1259 OID 36675)
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
-- TOC entry 1614 (class 1259 OID 36681)
-- Dependencies: 6
-- Name: assembling_categories; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE assembling_categories (
    assembling_category_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0) NOT NULL,
    category_code character varying(50) NOT NULL,
    category_name character varying(100) NOT NULL,
    description text,
    parent_category_id numeric(18,0)
);


--
-- TOC entry 1615 (class 1259 OID 36687)
-- Dependencies: 1947 6
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
-- TOC entry 1616 (class 1259 OID 36694)
-- Dependencies: 1948 6
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
-- TOC entry 1617 (class 1259 OID 36701)
-- Dependencies: 1949 6
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
-- TOC entry 1618 (class 1259 OID 36708)
-- Dependencies: 1950 1951 6
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
-- TOC entry 1619 (class 1259 OID 36716)
-- Dependencies: 6
-- Name: business_partners; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE business_partners (
    partner_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0),
    id numeric(18,0)
);


--
-- TOC entry 1620 (class 1259 OID 36719)
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
-- TOC entry 1621 (class 1259 OID 36725)
-- Dependencies: 6
-- Name: classified_objects; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE classified_objects (
    classifier_id numeric(18,0) NOT NULL,
    classified_object_id numeric(18,0) NOT NULL,
    description text
);


--
-- TOC entry 1622 (class 1259 OID 36731)
-- Dependencies: 6
-- Name: classifier_applied_for_dot; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE classifier_applied_for_dot (
    classifier_id numeric(18,0) NOT NULL,
    data_object_type_id integer NOT NULL
);


--
-- TOC entry 1623 (class 1259 OID 36734)
-- Dependencies: 1952 6
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
-- TOC entry 1624 (class 1259 OID 36741)
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
-- TOC entry 1625 (class 1259 OID 36747)
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
-- TOC entry 2425 (class 0 OID 0)
-- Dependencies: 1625
-- Name: COLUMN communication_contacts.communication_type_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN communication_contacts.communication_type_id IS 'Email (Work, Private), Phone (Work, Home, Fax), Mobile Phone (Work, Private), VoIP (SIP, H.323), Instant Communications (ICQ, Skype, Google Talk, MSN), Other';


--
-- TOC entry 1626 (class 1259 OID 36750)
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
-- TOC entry 1627 (class 1259 OID 36756)
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
-- TOC entry 1628 (class 1259 OID 36759)
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
-- TOC entry 1629 (class 1259 OID 36762)
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
-- TOC entry 1630 (class 1259 OID 36768)
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
-- TOC entry 1631 (class 1259 OID 36774)
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
-- TOC entry 1632 (class 1259 OID 36780)
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
-- TOC entry 1633 (class 1259 OID 36783)
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
-- TOC entry 1634 (class 1259 OID 36789)
-- Dependencies: 1953 1954 1955 1956 1957 1958 6
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
-- TOC entry 1635 (class 1259 OID 36801)
-- Dependencies: 6
-- Name: delivery_certificate_assignments; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE delivery_certificate_assignments (
    delivery_certificate_id numeric(18,0) NOT NULL,
    document_id numeric(18,0) NOT NULL
);


--
-- TOC entry 1636 (class 1259 OID 36804)
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
-- TOC entry 1637 (class 1259 OID 36807)
-- Dependencies: 6
-- Name: delivery_certificate_serial_numbers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE delivery_certificate_serial_numbers (
    certificate_item_id numeric(18,0) NOT NULL,
    serial_number character varying(32) NOT NULL
);


--
-- TOC entry 1638 (class 1259 OID 36810)
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
    creator_id numeric(18,0) NOT NULL,
    forwarder_branch_id numeric(18,0),
    recipient_branch_id numeric(18,0)
);


--
-- TOC entry 2426 (class 0 OID 0)
-- Dependencies: 1638
-- Name: COLUMN delivery_certificates.forwarder_branch_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN delivery_certificates.forwarder_branch_id IS 'As the Forwarder is an organization, we have to know abranch_id in order to choose a contact person.';


--
-- TOC entry 2427 (class 0 OID 0)
-- Dependencies: 1638
-- Name: COLUMN delivery_certificates.recipient_branch_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN delivery_certificates.recipient_branch_id IS 'In case the recipient is an organization we have to know a branch in order to shoose a contact person.';


--
-- TOC entry 1639 (class 1259 OID 36813)
-- Dependencies: 6
-- Name: enum_classes; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE enum_classes (
    enum_class_id integer NOT NULL,
    enum_class_name character varying(255) NOT NULL
);


--
-- TOC entry 1640 (class 1259 OID 36816)
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
-- TOC entry 1641 (class 1259 OID 36819)
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
-- TOC entry 1642 (class 1259 OID 36822)
-- Dependencies: 6
-- Name: order_confirmation_items; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE order_confirmation_items (
    confirmation_item_id numeric(19,2) NOT NULL,
    confirmed_quantity numeric(19,2) NOT NULL,
    extended_price numeric(19,2) NOT NULL,
    notes character varying(255),
    parent_id numeric(19,2),
    ship_date_from date,
    ship_date_to date,
    unit_price numeric(19,2) NOT NULL,
    measure_unit_id integer,
    product_id numeric(19,2),
    currency_id integer,
    matched_quantity numeric(19,2),
    ship_week integer
);


--
-- TOC entry 1643 (class 1259 OID 36825)
-- Dependencies: 6
-- Name: order_confirmations; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE order_confirmations (
    order_confirmation_id numeric(19,2) NOT NULL,
    discount_amount numeric(19,2),
    discount_percent numeric(19,2),
    documentdate_date date NOT NULL,
    document_number character varying(255) NOT NULL,
    invoice_sub_value numeric(19,2) NOT NULL,
    notes character varying(255),
    parent_id numeric(19,2),
    supplier_contact_name character varying(255),
    supplier_name character varying(255) NOT NULL,
    total_value numeric(19,2) NOT NULL,
    transport_price numeric(19,2),
    vat numeric(19,2) NOT NULL,
    currency_id integer,
    document_type_id integer,
    supplier_partner_id numeric(19,2),
    supplier_contact_id numeric(19,2),
    branch_id numeric(19,2),
    ship_date_from date,
    ship_date_to date,
    ship_week integer
);


--
-- TOC entry 1644 (class 1259 OID 36831)
-- Dependencies: 6
-- Name: order_item_match; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE order_item_match (
    id numeric(19,2) NOT NULL,
    matchquantity numeric(19,2),
    purchaseorderitem_order_item_id numeric(19,2),
    orderconfirmationitem_confirmation_item_id numeric(19,2)
);


--
-- TOC entry 1645 (class 1259 OID 36834)
-- Dependencies: 1959 6
-- Name: organizations; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE organizations (
    organization_id numeric(19,0) NOT NULL,
    description character varying(255),
    nickname character varying(255),
    organization_name character varying(120) NOT NULL,
    registration_date date,
    share_capital numeric(19,2),
    unique_identifier_code character varying(255),
    vat_number character varying(255),
    organization_type_id integer,
    registration_address_id numeric(18,0),
    registration_organization_id numeric(19,2),
    administration_address_id numeric(18,0),
    currency_id integer,
    is_active boolean DEFAULT false NOT NULL
);


--
-- TOC entry 1646 (class 1259 OID 36841)
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
-- TOC entry 2428 (class 0 OID 0)
-- Dependencies: 1646
-- Name: COLUMN passports.passport_type_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN passports.passport_type_id IS 'Passport, Identity Card, Driving License';


--
-- TOC entry 1679 (class 1259 OID 38338)
-- Dependencies: 6
-- Name: pattern_mask_formats; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE pattern_mask_formats (
    pattern_mask_format_id numeric(19,2) NOT NULL,
    description character varying(255),
    format character varying(255) NOT NULL,
    format_type character(1) NOT NULL,
    parent_id numeric(19,2),
    pattern_name character varying(255) NOT NULL,
    owner_id numeric(19,2)
);


--
-- TOC entry 1647 (class 1259 OID 36850)
-- Dependencies: 6
-- Name: persons; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE persons (
    partner_id numeric(18,0) NOT NULL,
    birth_date date,
    description text,
    extra_name character varying(24),
    first_name character varying(24) NOT NULL,
    last_name character varying(24) NOT NULL,
    personal_unique_id character varying(16),
    second_name character varying(24),
    gender_id integer,
    birth_place_city_id numeric(18,0),
    birth_place_country_id integer
);


--
-- TOC entry 1648 (class 1259 OID 36856)
-- Dependencies: 1960 6
-- Name: position_types; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE position_types (
    position_type_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0),
    owner_type character(1) NOT NULL,
    position_type_name character varying(32) NOT NULL,
    description text,
    parent_position_type_id numeric(18,0),
    is_internal boolean DEFAULT false NOT NULL,
    user_group_id numeric(18,0)
);


--
-- TOC entry 2429 (class 0 OID 0)
-- Dependencies: 1648
-- Name: COLUMN position_types.owner_type; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN position_types.owner_type IS 'P - Person, O - Organization';


--
-- TOC entry 1649 (class 1259 OID 36863)
-- Dependencies: 6
-- Name: product_categories; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE product_categories (
    product_category_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0),
    category_name character varying(100) NOT NULL,
    description text,
    parent_cat_id numeric(19,2),
    pattern_mask_format_id numeric(19,2)
);


--
-- TOC entry 1650 (class 1259 OID 36869)
-- Dependencies: 6
-- Name: product_suppliers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE product_suppliers (
    product_id numeric(18,0) NOT NULL,
    supplier_id numeric(18,0) NOT NULL,
    description text
);


--
-- TOC entry 1651 (class 1259 OID 36875)
-- Dependencies: 6
-- Name: products; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE products (
    product_id numeric(18,0) NOT NULL,
    parent_id numeric(18,0),
    product_type character(2)
);


--
-- TOC entry 1652 (class 1259 OID 36878)
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
    ship_date date,
    currency_id integer,
    notes character varying(255),
    ship_date_from date,
    ship_date_to date
);


--
-- TOC entry 1653 (class 1259 OID 36881)
-- Dependencies: 6
-- Name: purchase_orders; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE purchase_orders (
    purchase_order_id numeric(19,2) NOT NULL,
    branch_name character varying(255) NOT NULL,
    creation_time date NOT NULL,
    creator_name character varying(255) NOT NULL,
    finalizing_time date,
    first_delivery_time date,
    last_delivery_time date,
    notes character varying(255),
    order_number numeric(19,2) NOT NULL,
    parent_id numeric(19,2),
    sender_name character varying(255),
    sent_time date,
    supplier_contact_name character varying(255),
    supplier_name character varying(255) NOT NULL,
    supplier_order_number numeric(19,2),
    sender_id numeric(19,2),
    supplier_contact_id numeric(19,2),
    creator_id numeric(19,2),
    doc_delivery_method_id integer,
    supplier_partner_id numeric(19,2),
    status_id integer,
    branch_id numeric(19,2)
);


--
-- TOC entry 1654 (class 1259 OID 36887)
-- Dependencies: 6
-- Name: real_products; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE real_products (
    product_id numeric(18,0) NOT NULL,
    simple_product_id numeric(18,0) NOT NULL
);


--
-- TOC entry 1655 (class 1259 OID 36890)
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
-- TOC entry 1656 (class 1259 OID 36893)
-- Dependencies: 6
-- Name: receipt_certificate_serial_numbers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE receipt_certificate_serial_numbers (
    certificate_item_id numeric(18,0) NOT NULL,
    serial_number character varying(32) NOT NULL
);


--
-- TOC entry 1657 (class 1259 OID 36896)
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
-- TOC entry 1658 (class 1259 OID 36899)
-- Dependencies: 6
-- Name: registration_codes; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE registration_codes (
    registration_code numeric(18,0) NOT NULL,
    email character varying(60)
);


--
-- TOC entry 1659 (class 1259 OID 36902)
-- Dependencies: 6
-- Name: resource_bundle; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE resource_bundle (
    resource_id integer NOT NULL,
    enum_class_id integer NOT NULL,
    enum_name character varying(64) NOT NULL
);


--
-- TOC entry 1660 (class 1259 OID 36905)
-- Dependencies: 1961 6
-- Name: sequence_identifiers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE sequence_identifiers (
    seq_id_key bigint NOT NULL,
    seq_id_name character varying(64) NOT NULL,
    seq_id_value numeric(38,0) DEFAULT 0 NOT NULL
);


--
-- TOC entry 1661 (class 1259 OID 36909)
-- Dependencies: 1963 1964 1965 1966 1967 6
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
    producer_id numeric(18,0),
    pattern_mask_format_id numeric(19,2)
);


--
-- TOC entry 1662 (class 1259 OID 36920)
-- Dependencies: 6
-- Name: user_groups; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE user_groups (
    user_group_id numeric(18,0) NOT NULL,
    name character varying(50),
    parent_id numeric(18,0),
    description text
);


--
-- TOC entry 1663 (class 1259 OID 36926)
-- Dependencies: 1968 1969 1970 1971 1972 6
-- Name: user_rights; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE user_rights (
    user_group_id numeric(18,0),
    user_id numeric(18,0),
    data_object_type_id integer,
    data_object_id numeric(18,0),
    can_read boolean DEFAULT false NOT NULL,
    can_create boolean DEFAULT false NOT NULL,
    can_modify boolean DEFAULT false NOT NULL,
    can_delete boolean DEFAULT false NOT NULL,
    user_right_id bigint NOT NULL,
    special_permission_id integer,
    excluded boolean DEFAULT false NOT NULL,
    expires timestamp without time zone
);


--
-- TOC entry 1664 (class 1259 OID 36934)
-- Dependencies: 1973 6
-- Name: users; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE users (
    user_id numeric(18,0) NOT NULL,
    version integer NOT NULL,
    user_name character varying(32) NOT NULL,
    email_address character varying(64) NOT NULL,
    user_password character varying(64) NOT NULL,
    system_password character varying(64),
    system_password_validity timestamp without time zone,
    is_new boolean DEFAULT true NOT NULL,
    creation_time time with time zone NOT NULL,
    creator_id bigint,
    description text,
    small_image_uri character varying(1024),
    medium_image_uri character varying(1024),
    user_uri character varying(1024),
    next_action_after_login character varying(1024),
    small_image oid,
    medium_image oid,
    person_id numeric(18,0)
);


--
-- TOC entry 1665 (class 1259 OID 36941)
-- Dependencies: 6
-- Name: users_organizations; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE users_organizations (
    user_id numeric(18,0) NOT NULL,
    organization_id numeric(18,0) NOT NULL,
    branch_id numeric(18,0),
    is_user_active boolean,
    person_id_todelete numeric(18,0),
    user_group_id numeric(18,0)
);


--
-- TOC entry 1666 (class 1259 OID 36944)
-- Dependencies: 6
-- Name: virtual_products; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE virtual_products (
    product_id numeric(18,0) NOT NULL,
    product_type character(2),
    parent_id numeric(18,0)
);


--
-- TOC entry 1680 (class 1259 OID 38351)
-- Dependencies: 6
-- Name: warehouse_products; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE warehouse_products (
    warehouse_product_id numeric(19,2) NOT NULL,
    default_quantity numeric(19,2),
    delivery_time integer,
    maximum_quantity numeric(19,2),
    minimum_quantity numeric(19,2),
    notes character varying(255),
    ordered_quantity numeric(19,2),
    parent_id numeric(19,2),
    purchase_price numeric(19,2),
    quantity_due numeric(19,2),
    quantity_in_stock numeric(19,2),
    reserved_quantity numeric(19,2),
    sale_price numeric(19,2),
    sold_quantity numeric(19,2),
    product_id numeric(19,2) NOT NULL,
    warehouse_id numeric(19,2) NOT NULL
);


--
-- TOC entry 1667 (class 1259 OID 36950)
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
-- TOC entry 1668 (class 1259 OID 36956)
-- Dependencies: 6
-- Name: countries_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE countries_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2430 (class 0 OID 0)
-- Dependencies: 1668
-- Name: countries_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('countries_seq', 57, true);


--
-- TOC entry 1669 (class 1259 OID 36958)
-- Dependencies: 6
-- Name: data_object_type_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE data_object_type_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2431 (class 0 OID 0)
-- Dependencies: 1669
-- Name: data_object_type_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('data_object_type_seq', 108, true);


--
-- TOC entry 1670 (class 1259 OID 36960)
-- Dependencies: 6
-- Name: data_objects_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE data_objects_seq
    INCREMENT BY 1
    MAXVALUE 999999999999999999
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2432 (class 0 OID 0)
-- Dependencies: 1670
-- Name: data_objects_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('data_objects_seq', 2, true);


--
-- TOC entry 1671 (class 1259 OID 36962)
-- Dependencies: 6
-- Name: enum_classes_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE enum_classes_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2433 (class 0 OID 0)
-- Dependencies: 1671
-- Name: enum_classes_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('enum_classes_seq', 24, true);


--
-- TOC entry 1672 (class 1259 OID 36964)
-- Dependencies: 6
-- Name: order_item_match_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE order_item_match_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2434 (class 0 OID 0)
-- Dependencies: 1672
-- Name: order_item_match_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('order_item_match_seq', 9, true);


--
-- TOC entry 1673 (class 1259 OID 36966)
-- Dependencies: 6
-- Name: pattern_mask_formats_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE pattern_mask_formats_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2435 (class 0 OID 0)
-- Dependencies: 1673
-- Name: pattern_mask_formats_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('pattern_mask_formats_seq', 59, true);


--
-- TOC entry 1674 (class 1259 OID 36968)
-- Dependencies: 6
-- Name: resource_bundle_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE resource_bundle_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2436 (class 0 OID 0)
-- Dependencies: 1674
-- Name: resource_bundle_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('resource_bundle_seq', 145, true);


--
-- TOC entry 1675 (class 1259 OID 36970)
-- Dependencies: 6 1660
-- Name: sequence_identifiers_seq_id_key_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE sequence_identifiers_seq_id_key_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2437 (class 0 OID 0)
-- Dependencies: 1675
-- Name: sequence_identifiers_seq_id_key_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE sequence_identifiers_seq_id_key_seq OWNED BY sequence_identifiers.seq_id_key;


--
-- TOC entry 2438 (class 0 OID 0)
-- Dependencies: 1675
-- Name: sequence_identifiers_seq_id_key_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('sequence_identifiers_seq_id_key_seq', 1, false);


--
-- TOC entry 1676 (class 1259 OID 36972)
-- Dependencies: 6
-- Name: user_rights_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE user_rights_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2439 (class 0 OID 0)
-- Dependencies: 1676
-- Name: user_rights_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('user_rights_seq', 22, true);


--
-- TOC entry 1677 (class 1259 OID 36974)
-- Dependencies: 6
-- Name: warehouse_product_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE warehouse_product_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2440 (class 0 OID 0)
-- Dependencies: 1677
-- Name: warehouse_product_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('warehouse_product_seq', 15, true);


--
-- TOC entry 1678 (class 1259 OID 36976)
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
-- TOC entry 2441 (class 0 OID 0)
-- Dependencies: 1678
-- Name: xyz_id_sequence; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('xyz_id_sequence', 1, false);


--
-- TOC entry 1962 (class 2604 OID 36978)
-- Dependencies: 1675 1660
-- Name: seq_id_key; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE sequence_identifiers ALTER COLUMN seq_id_key SET DEFAULT nextval('sequence_identifiers_seq_id_key_seq'::regclass);


--
-- TOC entry 2363 (class 0 OID 36675)
-- Dependencies: 1613
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
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1215450974157, 1209394438429, 'IBM-BUL', 5, 1209062694832, NULL, NULL, NULL);
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1216474805297, NULL, 'Axon', 5, 1209062694832, '1000', NULL, NULL);
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1217345457805, 1217345408763, 'Central office', NULL, NULL, NULL, NULL, NULL);
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1217345766750, 1217345422152, 'Central office', NULL, NULL, NULL, NULL, NULL);
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1217345796939, 1217345444119, 'Home address', NULL, NULL, NULL, NULL, NULL);
INSERT INTO addresses (address_id, parent_id, address_name, country_id, city_id, postal_code, postal_address, description) VALUES (1217513800243, 1217513786510, 'Central office', 17, 1211992723142, NULL, NULL, NULL);


--
-- TOC entry 2364 (class 0 OID 36681)
-- Dependencies: 1614
-- Data for Name: assembling_categories; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2365 (class 0 OID 36687)
-- Dependencies: 1615
-- Data for Name: assembling_schema_item_values; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2366 (class 0 OID 36694)
-- Dependencies: 1616
-- Data for Name: assembling_schema_items; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2367 (class 0 OID 36701)
-- Dependencies: 1617
-- Data for Name: assembling_schemas; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2368 (class 0 OID 36708)
-- Dependencies: 1618
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
-- TOC entry 2369 (class 0 OID 36716)
-- Dependencies: 1619
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
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1215850679229, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1215852590515, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1212035635016, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1209324665385, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1213474710565, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1213474965974, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1216474970646, 1209222047860, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1209394343621, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1209394438429, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1216480868532, 1209222047860, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1217070771135, 1209222047860, NULL);
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
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1214803184453, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1214828587594, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1215848248784, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1215852038688, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1215848249302, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1215848249915, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1215848250387, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1215848250968, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1215848251486, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1215848252067, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1215848252539, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1215848252948, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1215848253451, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1215848253798, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1215848254254, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1215848338492, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1215848494215, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1215849078969, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1215849243643, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1215852188856, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1215862892296, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1215852405742, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1215850369499, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1215852519035, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1217345408763, 1209222047860, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1217345422152, 1209222047860, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1217345444119, 1209222047860, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1217407278985, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1217409372641, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1217410126469, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1217410631001, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1217513786510, 1209222047860, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1217514926610, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1217540926938, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1217623255938, NULL, NULL);
INSERT INTO business_partners (partner_id, parent_id, id) VALUES (1217623859251, NULL, NULL);


--
-- TOC entry 2370 (class 0 OID 36719)
-- Dependencies: 1620
-- Data for Name: cities; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO cities (city_id, country_id, city_name, postal_code, city_code, city_phone_code, description) VALUES (1209062694832, 5, 'Sofia', NULL, NULL, NULL, NULL);
INSERT INTO cities (city_id, country_id, city_name, postal_code, city_code, city_phone_code, description) VALUES (1209243602313, 6, 'New York', NULL, NULL, NULL, NULL);
INSERT INTO cities (city_id, country_id, city_name, postal_code, city_code, city_phone_code, description) VALUES (1211992678579, 5, 'Varna', NULL, NULL, NULL, NULL);
INSERT INTO cities (city_id, country_id, city_name, postal_code, city_code, city_phone_code, description) VALUES (1211992723142, 17, 'Athens', NULL, NULL, NULL, NULL);
INSERT INTO cities (city_id, country_id, city_name, postal_code, city_code, city_phone_code, description) VALUES (1213963338689, 5, 'Shumen', '9700', NULL, '054', NULL);
INSERT INTO cities (city_id, country_id, city_name, postal_code, city_code, city_phone_code, description) VALUES (1214803174550, 5, 'Vidin', '3700', NULL, '094', NULL);


--
-- TOC entry 2371 (class 0 OID 36725)
-- Dependencies: 1621
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
INSERT INTO classified_objects (classifier_id, classified_object_id, description) VALUES (1216476241907, 1209582114840, NULL);
INSERT INTO classified_objects (classifier_id, classified_object_id, description) VALUES (1216476241907, 1209585931126, NULL);
INSERT INTO classified_objects (classifier_id, classified_object_id, description) VALUES (1216476241907, 1216480868532, NULL);
INSERT INTO classified_objects (classifier_id, classified_object_id, description) VALUES (1216476241907, 1209222047860, NULL);
INSERT INTO classified_objects (classifier_id, classified_object_id, description) VALUES (1213515923514, 1217345408763, NULL);
INSERT INTO classified_objects (classifier_id, classified_object_id, description) VALUES (1213515923514, 1217345422152, NULL);
INSERT INTO classified_objects (classifier_id, classified_object_id, description) VALUES (1213515923514, 1217345444119, NULL);
INSERT INTO classified_objects (classifier_id, classified_object_id, description) VALUES (1213515923514, 1217513786510, NULL);


--
-- TOC entry 2372 (class 0 OID 36731)
-- Dependencies: 1622
-- Data for Name: classifier_applied_for_dot; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO classifier_applied_for_dot (classifier_id, data_object_type_id) VALUES (1213515923514, 36);
INSERT INTO classifier_applied_for_dot (classifier_id, data_object_type_id) VALUES (1214578135971, 1);
INSERT INTO classifier_applied_for_dot (classifier_id, data_object_type_id) VALUES (1214582644985, 36);
INSERT INTO classifier_applied_for_dot (classifier_id, data_object_type_id) VALUES (1216476241907, 1);


--
-- TOC entry 2373 (class 0 OID 36734)
-- Dependencies: 1623
-- Data for Name: classifier_groups; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO classifier_groups (classifier_group_id, parent_id, is_system_group, classifier_group_code, classifier_group_name, description) VALUES (1211131456610, NULL, false, 'system', 'System', NULL);
INSERT INTO classifier_groups (classifier_group_id, parent_id, is_system_group, classifier_group_code, classifier_group_name, description) VALUES (1214577893297, NULL, false, 'tmp', 'temp', NULL);


--
-- TOC entry 2374 (class 0 OID 36741)
-- Dependencies: 1624
-- Data for Name: classifiers; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO classifiers (classifier_id, parent_id, classifier_code, classifier_name, description) VALUES (1211229847860, 1211131456610, 'bank', 'Bank', NULL);
INSERT INTO classifiers (classifier_id, parent_id, classifier_code, classifier_name, description) VALUES (1213515923514, 1211131456610, 'provider', 'Provider', NULL);
INSERT INTO classifiers (classifier_id, parent_id, classifier_code, classifier_name, description) VALUES (1214578135971, 1214577893297, 'tmp', 'tmp', NULL);
INSERT INTO classifiers (classifier_id, parent_id, classifier_code, classifier_name, description) VALUES (1214582644985, 1211131456610, 'tsts', 'tstas', NULL);
INSERT INTO classifiers (classifier_id, parent_id, classifier_code, classifier_name, description) VALUES (1214748427158, 1214577893297, '44', 'Classified', NULL);
INSERT INTO classifiers (classifier_id, parent_id, classifier_code, classifier_name, description) VALUES (1214749012672, 1214577893297, '55', 'ClassX', NULL);
INSERT INTO classifiers (classifier_id, parent_id, classifier_code, classifier_name, description) VALUES (1216476241907, 1211131456610, 'forwarder', 'Forwarder', 'Some organizations are forwarders for Delivery Certificates.');


--
-- TOC entry 2375 (class 0 OID 36747)
-- Dependencies: 1625
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
INSERT INTO communication_contacts (communication_contact_id, parent_id, communication_type_id, communication_value, contact_person_id) VALUES (1216474915457, 1216474805297, 44, '9975432', NULL);


--
-- TOC entry 2376 (class 0 OID 36750)
-- Dependencies: 1626
-- Data for Name: complex_product_items; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2377 (class 0 OID 36756)
-- Dependencies: 1627
-- Data for Name: complex_products; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2378 (class 0 OID 36759)
-- Dependencies: 1628
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
INSERT INTO contact_persons (contact_person_id, parent_id, position_type_id, contact_id) VALUES (1214803187723, 1210419278241, 1208595267969, 1214803184453);
INSERT INTO contact_persons (contact_person_id, parent_id, position_type_id, contact_id) VALUES (1215336873971, 1212035638798, 1215336678235, 1213476155246);
INSERT INTO contact_persons (contact_person_id, parent_id, position_type_id, contact_id) VALUES (1215358383376, 1209222423971, 1215342581282, 1211475930558);
INSERT INTO contact_persons (contact_person_id, parent_id, position_type_id, contact_id) VALUES (1215451068003, 1215450974157, 1215451052064, 1213963345878);
INSERT INTO contact_persons (contact_person_id, parent_id, position_type_id, contact_id) VALUES (1217070882762, 1209222409189, NULL, 1211475930558);
INSERT INTO contact_persons (contact_person_id, parent_id, position_type_id, contact_id) VALUES (1217071164250, 1212004334360, NULL, 1213512206579);
INSERT INTO contact_persons (contact_person_id, parent_id, position_type_id, contact_id) VALUES (1217345737392, 1217345457805, NULL, 1211472752091);
INSERT INTO contact_persons (contact_person_id, parent_id, position_type_id, contact_id) VALUES (1217345752103, 1217345457805, NULL, 1214287428969);
INSERT INTO contact_persons (contact_person_id, parent_id, position_type_id, contact_id) VALUES (1217345782783, 1217345766750, NULL, 1211472798404);
INSERT INTO contact_persons (contact_person_id, parent_id, position_type_id, contact_id) VALUES (1217345812528, 1217345796939, NULL, 1206833877407);
INSERT INTO contact_persons (contact_person_id, parent_id, position_type_id, contact_id) VALUES (1217513833603, 1217513800243, 1217513829280, 1214287428969);
INSERT INTO contact_persons (contact_person_id, parent_id, position_type_id, contact_id) VALUES (1217540927346, 1210856501325, NULL, 1217540926938);
INSERT INTO contact_persons (contact_person_id, parent_id, position_type_id, contact_id) VALUES (1217623256284, 1209222423971, NULL, 1217623255938);
INSERT INTO contact_persons (contact_person_id, parent_id, position_type_id, contact_id) VALUES (1217623859581, 1209222423971, NULL, 1217623859251);


--
-- TOC entry 2379 (class 0 OID 36762)
-- Dependencies: 1629
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


--
-- TOC entry 2380 (class 0 OID 36768)
-- Dependencies: 1630
-- Data for Name: currencies; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2381 (class 0 OID 36774)
-- Dependencies: 1631
-- Data for Name: data_object_details; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2382 (class 0 OID 36780)
-- Dependencies: 1632
-- Data for Name: data_object_links; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO data_object_links (data_object_link_id, parent_id, linked_data_object_id, link_name) VALUES (1214804095151, NULL, 1214803184453, 'Radoslav Mirchev Lozanov ');


--
-- TOC entry 2383 (class 0 OID 36783)
-- Dependencies: 1633
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
INSERT INTO data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) VALUES (96, 'com.cosmos.acacia.crm.data.DataObjectLink', NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) VALUES (100, 'com.cosmos.acacia.crm.data.DeliveryCertificate', NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) VALUES (101, 'Miro', NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) VALUES (102, 'com.cosmos.acacia.crm.data.UserGroup', NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) VALUES (103, 'com.cosmos.acacia.crm.data.PurchaseOrder', NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) VALUES (104, 'com.cosmos.acacia.crm.data.PurchaseOrderItem', NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) VALUES (105, 'com.cosmos.acacia.crm.data.OrderConfirmation', NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) VALUES (106, 'com.cosmos.acacia.crm.data.OrderConfirmationItem', NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) VALUES (107, 'com.cosmos.acacia.crm.data.PatternMaskFormat', NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) VALUES (108, 'com.cosmos.acacia.crm.data.WarehouseProduct', NULL, NULL, NULL, NULL, NULL);


--
-- TOC entry 2384 (class 0 OID 36789)
-- Dependencies: 1634
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
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209406603688, 2, 1, '2008-04-28 21:16:43.687+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209393778001, 1, 43, '2008-04-28 17:42:57.962+03', 0, 0, false, false, false, false, false, 1209393762536, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209393839117, 1, 40, '2008-04-28 17:43:59.108+03', 0, 0, false, false, false, false, false, 1209324665385, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209393842895, 2, 58, '2008-04-28 17:44:02.885+03', 0, 0, true, false, false, false, false, 1209393667067, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209393762536, 3, 40, '2008-04-28 17:42:42.499+03', 0, 0, true, false, false, false, false, 1209393667067, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209393898024, 2, 37, '2008-04-28 17:44:58.014+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209393978637, 3, 37, '2008-04-28 17:46:18.626+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209394343621, 4, 1, '2008-04-28 17:52:23.609+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
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
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209585931126, 4, 1, '2008-04-30 23:05:31.125+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
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
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209582114840, 6, 1, '2008-04-30 22:01:54.837+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
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
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209394438429, 5, 1, '2008-04-28 17:53:58.416+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
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
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211475934958, 1, 43, '2008-05-22 20:05:34.937+03', 0, 0, false, false, false, false, false, 1209222409189, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211476817202, 1, 43, '2008-05-22 20:20:17.181+03', 0, 0, false, false, false, false, false, 1210856501325, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211482143869, 3, 1, '2008-05-22 21:49:03.859+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211481085303, 2, 47, '2008-05-22 21:31:25.297+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211482953453, 2, 40, '2008-05-22 22:02:33.429+03', 0, 0, true, false, false, false, false, 1211482143869, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211481269391, 2, 47, '2008-05-22 21:34:29.382+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211482665600, 2, 40, '2008-05-22 21:57:45.563+03', 0, 0, false, false, false, false, false, 1211482143869, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211482943730, 2, 40, '2008-05-22 22:02:23.699+03', 0, 0, true, false, false, false, false, 1211482143869, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211483549670, 1, 1, '2008-05-22 22:12:29.655+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211472885184, 2, 43, '2008-05-22 19:14:45.143+03', 0, 0, true, false, false, false, false, 1209222423971, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211486935439, 1, 45, '2008-05-22 23:08:55.421+03', 0, 0, false, false, false, false, false, 1209222423971, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211486998659, 1, 45, '2008-05-22 23:09:58.656+03', 0, 0, false, false, false, false, false, 1209582468591, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211489882752, 1, 53, '2008-05-22 23:58:02.734+03', 0, 0, false, false, false, false, false, 1209222423971, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
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
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211475930558, 2, 37, '2008-05-22 20:05:30.553+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211492793954, 3, 1, '2008-05-23 00:46:33.953+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
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
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1211990399266, 3, 1, '2008-05-28 18:59:59.265+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
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
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214803174550, 1, 59, '2008-06-30 08:19:34.549+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214578135971, 3, 62, '2008-06-27 17:48:55.953+03', 0, 0, false, false, false, false, false, 1214577893297, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213509854462, 2, 45, '2008-06-15 09:04:14.453+03', 0, 0, true, false, false, false, false, 1213509854335, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213509854367, 2, 37, '2008-06-15 09:04:14.359+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213509854335, 2, 40, '2008-06-15 09:04:14.328+03', 0, 0, true, false, false, false, false, 1213509854099, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213509854099, 2, 1, '2008-06-15 09:04:14.093+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
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
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214803184453, 1, 37, '2008-06-30 08:19:44.451+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213516221125, 2, 62, '2008-06-15 10:50:21.093+03', 0, 0, true, false, false, false, false, 1211131456610, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213952129954, 1, 60, '2008-06-20 11:55:29.937+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213963338689, 1, 59, '2008-06-20 15:02:18.687+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213963345878, 1, 37, '2008-06-20 15:02:25.875+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214287428969, 1, 37, '2008-06-24 09:03:48.968+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214334270672, 1, 89, '2008-06-24 22:04:30.671+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214606093220, 2, 62, '2008-06-28 01:34:53.218+03', 0, 0, true, false, false, false, false, 1211131456610, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214803187723, 1, 43, '2008-06-30 08:19:47.72+03', 0, 0, false, false, false, false, false, 1210419278241, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214582644985, 2, 62, '2008-06-27 19:04:04.765+03', 0, 0, false, false, false, false, false, 1211131456610, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214639571392, 2, 62, '2008-06-28 10:52:51.375+03', 0, 0, true, false, false, false, false, 1214639571251, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214639571251, 2, 61, '2008-06-28 10:52:51.25+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214690075898, 2, 95, '2008-06-29 00:54:35.895+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214730588490, 2, 95, '2008-06-29 12:09:48.489+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214739376412, 2, 95, '2008-06-29 14:36:16.411+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214740305788, 2, 95, '2008-06-29 14:51:45.786+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214748427158, 1, 62, '2008-06-29 17:07:07.035+03', 0, 0, false, false, false, false, false, 1214577893297, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214749012672, 1, 62, '2008-06-29 17:16:52.628+03', 0, 0, false, false, false, false, false, 1214577893297, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214803192268, 2, 95, '2008-06-30 08:19:52.263+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215288167266, 1, 100, '2008-07-05 23:02:47.265+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214683373782, 2, 89, '2008-06-28 23:02:53.781+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214804095151, 1, 96, '2008-06-30 08:34:55.146+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214828587594, 1, 37, '2008-06-30 15:23:07.593+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214836145032, 2, 95, '2008-06-30 17:29:05.031+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215336683064, 1, 47, '2008-07-06 12:31:23.062+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215336678235, 2, 47, '2008-07-06 12:31:18.234+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215336873971, 1, 43, '2008-07-06 12:34:33.968+03', 0, 0, false, false, false, false, false, 1212035638798, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215340892641, 3, 47, '2008-07-06 13:41:32.625+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1213512206579, 2, 37, '2008-06-15 09:43:26.578+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215342581282, 2, 47, '2008-07-06 14:09:41.281+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214775184802, 4, 95, '2008-06-30 00:33:04.801+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215358383376, 1, 43, '2008-07-06 18:33:03.375+03', 0, 0, false, false, false, false, false, 1209222423971, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215360765548, 2, 47, '2008-07-06 19:12:45.546+03', 0, 0, true, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215360744485, 2, 47, '2008-07-06 19:12:24.484+03', 0, 0, true, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215363959845, 2, 47, '2008-07-06 20:05:59.843+03', 0, 0, true, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215367843751, 2, 47, '2008-07-06 21:10:43.734+03', 0, 0, true, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215363937032, 2, 47, '2008-07-06 20:05:37.031+03', 0, 0, true, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215359026626, 3, 47, '2008-07-06 18:43:46.625+03', 0, 0, true, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215368904407, 2, 47, '2008-07-06 21:28:24.406+03', 0, 0, true, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215368591157, 2, 47, '2008-07-06 21:23:11.156+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215369537657, 2, 47, '2008-07-06 21:38:57.656+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215451052064, 1, 47, '2008-07-07 20:17:32.062+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215451068003, 1, 43, '2008-07-07 20:17:48+03', 0, 0, false, false, false, false, false, 1215450974157, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215450974157, 2, 40, '2008-07-07 20:16:14.156+03', 0, 0, false, false, false, false, false, 1209394438429, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215712421007, 2, 59, '2008-07-10 20:53:41.006+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1214852691344, 11, 95, '2008-06-30 22:04:51.343+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215848246017, 7, 95, '2008-07-12 10:37:26.015+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215848244876, 2, 1, '2008-07-12 10:37:24.859+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215848248784, 2, 1, '2008-07-12 10:37:28.781+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215848249302, 2, 1, '2008-07-12 10:37:29.296+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215848249915, 2, 1, '2008-07-12 10:37:29.906+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215848250387, 2, 1, '2008-07-12 10:37:30.375+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215848250968, 2, 1, '2008-07-12 10:37:30.953+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215848251486, 2, 1, '2008-07-12 10:37:31.468+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215848252067, 2, 1, '2008-07-12 10:37:32.046+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215848252539, 2, 1, '2008-07-12 10:37:32.515+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215848252948, 2, 1, '2008-07-12 10:37:32.921+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215848253451, 2, 1, '2008-07-12 10:37:33.421+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215848253798, 2, 1, '2008-07-12 10:37:33.765+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215848254254, 2, 1, '2008-07-12 10:37:34.218+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215848338492, 2, 1, '2008-07-12 10:38:58.453+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215849409011, 2, 1, '2008-07-12 10:56:48.984+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215849409168, 7, 95, '2008-07-12 10:56:49.14+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215849390999, 2, 1, '2008-07-12 10:56:30.984+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215849391156, 6, 95, '2008-07-12 10:56:31.14+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215848385668, 7, 95, '2008-07-12 10:39:45.625+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215848385338, 2, 1, '2008-07-12 10:39:45.296+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215848494215, 2, 1, '2008-07-12 10:41:34.171+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850368795, 6, 95, '2008-07-12 11:12:48.734+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215848563563, 5, 95, '2008-07-12 10:42:43.515+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215848563265, 2, 1, '2008-07-12 10:42:43.218+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215849078969, 2, 1, '2008-07-12 10:51:18.968+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215849243643, 2, 1, '2008-07-12 10:54:03.64+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215849393423, 2, 1, '2008-07-12 10:56:33.406+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215849393580, 6, 95, '2008-07-12 10:56:33.562+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215849347176, 2, 1, '2008-07-12 10:55:47.171+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215849347427, 6, 95, '2008-07-12 10:55:47.421+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215849411935, 2, 1, '2008-07-12 10:56:51.906+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215849412170, 6, 95, '2008-07-12 10:56:52.14+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215849365663, 2, 1, '2008-07-12 10:56:05.656+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215849365961, 6, 95, '2008-07-12 10:56:05.953+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850655634, 6, 95, '2008-07-12 11:17:35.562+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215849396440, 2, 1, '2008-07-12 10:56:36.421+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215849396582, 6, 95, '2008-07-12 10:56:36.562+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215849385102, 2, 1, '2008-07-12 10:56:25.093+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215849385385, 6, 95, '2008-07-12 10:56:25.375+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850339399, 2, 1, '2008-07-12 11:12:19.359+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850339603, 6, 95, '2008-07-12 11:12:19.562+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215849387182, 2, 1, '2008-07-12 10:56:27.171+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215849387324, 6, 95, '2008-07-12 10:56:27.312+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215849399067, 2, 1, '2008-07-12 10:56:39.046+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215849399240, 6, 95, '2008-07-12 10:56:39.218+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215849413593, 2, 1, '2008-07-12 10:56:53.562+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215849389106, 2, 1, '2008-07-12 10:56:29.093+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215849389279, 6, 95, '2008-07-12 10:56:29.265+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215849413844, 6, 95, '2008-07-12 10:56:53.812+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850347454, 2, 1, '2008-07-12 11:12:27.406+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850347705, 6, 95, '2008-07-12 11:12:27.656+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215849401710, 2, 1, '2008-07-12 10:56:41.687+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215849401867, 6, 95, '2008-07-12 10:56:41.843+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850362136, 2, 1, '2008-07-12 11:12:42.078+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215849415611, 2, 1, '2008-07-12 10:56:55.578+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215849415924, 6, 95, '2008-07-12 10:56:55.89+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850341870, 2, 1, '2008-07-12 11:12:21.828+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215849404243, 2, 1, '2008-07-12 10:56:44.218+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215849404447, 7, 95, '2008-07-12 10:56:44.421+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850342121, 6, 95, '2008-07-12 11:12:22.078+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850362434, 6, 95, '2008-07-12 11:12:42.375+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850357210, 2, 1, '2008-07-12 11:12:37.156+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850333395, 2, 1, '2008-07-12 11:12:13.359+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850333724, 6, 95, '2008-07-12 11:12:13.687+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850357398, 7, 95, '2008-07-12 11:12:37.343+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850343622, 2, 1, '2008-07-12 11:12:23.578+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850343920, 6, 95, '2008-07-12 11:12:23.875+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850336022, 2, 1, '2008-07-12 11:12:15.984+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850336257, 6, 95, '2008-07-12 11:12:16.218+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850349721, 2, 1, '2008-07-12 11:12:29.671+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850349957, 6, 95, '2008-07-12 11:12:29.906+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850345624, 2, 1, '2008-07-12 11:12:25.578+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850345890, 6, 95, '2008-07-12 11:12:25.843+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850653303, 2, 1, '2008-07-12 11:17:33.234+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850353380, 2, 1, '2008-07-12 11:12:33.328+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850353631, 7, 95, '2008-07-12 11:12:33.578+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850360290, 2, 1, '2008-07-12 11:12:40.234+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850360432, 6, 95, '2008-07-12 11:12:40.375+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850648674, 2, 1, '2008-07-12 11:17:28.609+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850369499, 1, 1, '2008-07-12 11:12:49.437+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850645844, 2, 1, '2008-07-12 11:17:25.781+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850648831, 6, 95, '2008-07-12 11:17:28.765+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850368513, 2, 1, '2008-07-12 11:12:48.453+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850646142, 6, 95, '2008-07-12 11:17:26.078+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850651332, 2, 1, '2008-07-12 11:17:31.265+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850657370, 6, 95, '2008-07-12 11:17:37.296+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850651536, 6, 95, '2008-07-12 11:17:31.468+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850653445, 6, 95, '2008-07-12 11:17:33.375+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850655086, 2, 1, '2008-07-12 11:17:35.015+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850657166, 2, 1, '2008-07-12 11:17:37.093+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850659091, 6, 95, '2008-07-12 11:17:39.015+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850658871, 2, 1, '2008-07-12 11:17:38.796+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850661296, 6, 95, '2008-07-12 11:17:41.218+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850661045, 2, 1, '2008-07-12 11:17:40.968+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850663111, 7, 95, '2008-07-12 11:17:43.031+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850662922, 2, 1, '2008-07-12 11:17:42.843+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850679229, 1, 1, '2008-07-12 11:17:59.14+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850669409, 2, 1, '2008-07-12 11:17:49.328+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850673036, 2, 1, '2008-07-12 11:17:52.953+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850669660, 7, 95, '2008-07-12 11:17:49.578+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850673255, 6, 95, '2008-07-12 11:17:53.171+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850675944, 2, 1, '2008-07-12 11:17:55.859+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850676257, 6, 95, '2008-07-12 11:17:56.171+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850678305, 2, 1, '2008-07-12 11:17:58.218+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852386366, 6, 95, '2008-07-12 11:46:26.203+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850678509, 6, 95, '2008-07-12 11:17:58.421+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852016949, 2, 1, '2008-07-12 11:40:16.843+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852017185, 6, 95, '2008-07-12 11:40:17.078+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850891340, 2, 1, '2008-07-12 11:21:31.25+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215850891762, 6, 95, '2008-07-12 11:21:31.671+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852160959, 2, 1, '2008-07-12 11:42:40.828+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852033291, 2, 1, '2008-07-12 11:40:33.171+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852033652, 6, 95, '2008-07-12 11:40:33.531+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852019201, 2, 1, '2008-07-12 11:40:19.093+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215851062654, 2, 1, '2008-07-12 11:24:22.562+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215851062968, 6, 95, '2008-07-12 11:24:22.875+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852019437, 6, 95, '2008-07-12 11:40:19.328+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852161132, 6, 95, '2008-07-12 11:42:41+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215851184578, 2, 1, '2008-07-12 11:26:24.484+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215851184876, 6, 95, '2008-07-12 11:26:24.781+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852168748, 2, 1, '2008-07-12 11:42:48.609+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852021516, 2, 1, '2008-07-12 11:40:21.406+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852021751, 6, 95, '2008-07-12 11:40:21.64+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215851647986, 2, 1, '2008-07-12 11:34:07.89+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215851648315, 6, 95, '2008-07-12 11:34:08.218+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852169015, 6, 95, '2008-07-12 11:42:48.875+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852035418, 2, 1, '2008-07-12 11:40:35.296+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852035560, 6, 95, '2008-07-12 11:40:35.437+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215851783769, 2, 1, '2008-07-12 11:36:23.671+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215851784099, 6, 95, '2008-07-12 11:36:24+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852023174, 2, 1, '2008-07-12 11:40:23.062+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852023441, 6, 95, '2008-07-12 11:40:23.328+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852009803, 2, 1, '2008-07-12 11:40:09.703+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852010210, 6, 95, '2008-07-12 11:40:10.109+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852038688, 1, 1, '2008-07-12 11:40:38.562+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852012211, 2, 1, '2008-07-12 11:40:12.109+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852012415, 6, 95, '2008-07-12 11:40:12.312+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852162617, 2, 1, '2008-07-12 11:42:42.484+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852025223, 2, 1, '2008-07-12 11:40:25.109+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852025490, 6, 95, '2008-07-12 11:40:25.375+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852162868, 6, 95, '2008-07-12 11:42:42.734+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852015322, 2, 1, '2008-07-12 11:40:15.218+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852015480, 6, 95, '2008-07-12 11:40:15.375+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852037780, 2, 1, '2008-07-12 11:40:37.656+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852037968, 6, 95, '2008-07-12 11:40:37.843+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852185508, 2, 1, '2008-07-12 11:43:05.359+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852027241, 2, 1, '2008-07-12 11:40:27.125+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852027492, 7, 95, '2008-07-12 11:40:27.375+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852185759, 6, 95, '2008-07-12 11:43:05.609+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852177316, 2, 1, '2008-07-12 11:42:57.171+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852155533, 2, 1, '2008-07-12 11:42:35.406+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852155768, 6, 95, '2008-07-12 11:42:35.64+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852030586, 2, 1, '2008-07-12 11:40:30.468+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852030744, 6, 95, '2008-07-12 11:40:30.625+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852177521, 7, 95, '2008-07-12 11:42:57.375+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852171359, 2, 1, '2008-07-12 11:42:51.218+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852164525, 2, 1, '2008-07-12 11:42:44.39+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852164823, 6, 95, '2008-07-12 11:42:44.687+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852158082, 2, 1, '2008-07-12 11:42:37.953+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852158317, 6, 95, '2008-07-12 11:42:38.187+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852171610, 6, 95, '2008-07-12 11:42:51.468+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852166433, 2, 1, '2008-07-12 11:42:46.296+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852166669, 6, 95, '2008-07-12 11:42:46.531+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852379796, 2, 1, '2008-07-12 11:46:19.64+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852379985, 6, 95, '2008-07-12 11:46:19.828+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852377700, 2, 1, '2008-07-12 11:46:17.546+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852174189, 2, 1, '2008-07-12 11:42:54.046+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852174394, 7, 95, '2008-07-12 11:42:54.25+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852188856, 1, 1, '2008-07-12 11:43:08.703+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852183240, 2, 1, '2008-07-12 11:43:03.093+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852183476, 7, 95, '2008-07-12 11:43:03.328+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852378217, 6, 95, '2008-07-12 11:46:18.062+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852384347, 2, 1, '2008-07-12 11:46:24.187+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852188135, 2, 1, '2008-07-12 11:43:07.984+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852188308, 6, 95, '2008-07-12 11:43:08.156+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852382579, 2, 1, '2008-07-12 11:46:22.421+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852387961, 6, 95, '2008-07-12 11:46:27.796+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852382799, 6, 95, '2008-07-12 11:46:22.64+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852384598, 6, 95, '2008-07-12 11:46:24.437+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852386146, 2, 1, '2008-07-12 11:46:25.984+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852387695, 2, 1, '2008-07-12 11:46:27.531+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852389776, 6, 95, '2008-07-12 11:46:29.609+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852389494, 2, 1, '2008-07-12 11:46:29.328+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852391887, 6, 95, '2008-07-12 11:46:31.718+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852391683, 2, 1, '2008-07-12 11:46:31.515+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852393858, 7, 95, '2008-07-12 11:46:33.687+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852393670, 2, 1, '2008-07-12 11:46:33.50+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852396828, 2, 1, '2008-07-12 11:46:36.656+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852397079, 7, 95, '2008-07-12 11:46:36.906+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852400736, 2, 1, '2008-07-12 11:46:40.562+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852405742, 1, 1, '2008-07-12 11:46:45.562+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852400893, 7, 95, '2008-07-12 11:46:40.718+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852403113, 2, 1, '2008-07-12 11:46:42.937+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852403348, 6, 95, '2008-07-12 11:46:43.171+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852404974, 2, 1, '2008-07-12 11:46:44.796+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852560413, 2, 1, '2008-07-12 11:49:20.203+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852560554, 6, 95, '2008-07-12 11:49:20.343+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852584665, 2, 1, '2008-07-12 11:49:44.437+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852405272, 6, 95, '2008-07-12 11:46:45.093+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852505681, 2, 1, '2008-07-12 11:48:25.484+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852505916, 7, 95, '2008-07-12 11:48:25.718+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852486727, 2, 1, '2008-07-12 11:48:06.546+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852487088, 6, 95, '2008-07-12 11:48:06.906+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852584854, 7, 95, '2008-07-12 11:49:44.625+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852855469, 2, 1, '2008-07-12 11:54:15.234+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852562915, 2, 1, '2008-07-12 11:49:22.703+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852488917, 2, 1, '2008-07-12 11:48:08.734+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852489105, 6, 95, '2008-07-12 11:48:08.921+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852509355, 2, 1, '2008-07-12 11:48:29.156+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852509606, 6, 95, '2008-07-12 11:48:29.406+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852563228, 6, 95, '2008-07-12 11:49:23.015+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852491575, 2, 1, '2008-07-12 11:48:11.39+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852491889, 6, 95, '2008-07-12 11:48:11.703+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852574253, 2, 1, '2008-07-12 11:49:34.031+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852574473, 6, 95, '2008-07-12 11:49:34.25+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852495249, 2, 1, '2008-07-12 11:48:15.062+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852495422, 6, 95, '2008-07-12 11:48:15.234+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852512013, 2, 1, '2008-07-12 11:48:31.812+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852512280, 7, 95, '2008-07-12 11:48:32.078+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852496954, 2, 1, '2008-07-12 11:48:16.765+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852497252, 6, 95, '2008-07-12 11:48:17.062+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852856298, 6, 95, '2008-07-12 11:54:16.062+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852565182, 2, 1, '2008-07-12 11:49:24.968+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852565418, 6, 95, '2008-07-12 11:49:25.203+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852515640, 2, 1, '2008-07-12 11:48:35.437+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852499128, 2, 1, '2008-07-12 11:48:18.937+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852499332, 6, 95, '2008-07-12 11:48:19.14+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852515797, 6, 95, '2008-07-12 11:48:35.593+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852500943, 2, 1, '2008-07-12 11:48:20.75+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852501194, 6, 95, '2008-07-12 11:48:21+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852519035, 1, 1, '2008-07-12 11:48:38.828+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852866400, 6, 95, '2008-07-12 11:54:26.156+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852517908, 2, 1, '2008-07-12 11:48:37.703+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852503460, 2, 1, '2008-07-12 11:48:23.265+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852503711, 6, 95, '2008-07-12 11:48:23.515+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852587105, 2, 1, '2008-07-12 11:49:46.875+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852518534, 6, 95, '2008-07-12 11:48:38.328+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852567966, 2, 1, '2008-07-12 11:49:27.75+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852568326, 6, 95, '2008-07-12 11:49:28.109+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852587418, 6, 95, '2008-07-12 11:49:47.187+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852558348, 2, 1, '2008-07-12 11:49:18.14+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852558818, 6, 95, '2008-07-12 11:49:18.609+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852577333, 2, 1, '2008-07-12 11:49:37.109+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852577521, 7, 95, '2008-07-12 11:49:37.296+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852569952, 2, 1, '2008-07-12 11:49:29.734+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852570172, 6, 95, '2008-07-12 11:49:29.953+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852889881, 7, 95, '2008-07-12 11:54:49.625+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852590515, 1, 1, '2008-07-12 11:49:50.281+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852581163, 2, 1, '2008-07-12 11:49:40.937+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852571782, 2, 1, '2008-07-12 11:49:31.562+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852571986, 6, 95, '2008-07-12 11:49:31.765+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852581555, 7, 95, '2008-07-12 11:49:41.328+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852858174, 2, 1, '2008-07-12 11:54:17.937+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852858519, 6, 95, '2008-07-12 11:54:18.281+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852589716, 2, 1, '2008-07-12 11:49:49.484+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852589873, 6, 95, '2008-07-12 11:49:49.64+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852863803, 2, 1, '2008-07-12 11:54:23.562+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852864023, 6, 95, '2008-07-12 11:54:23.781+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852885549, 2, 1, '2008-07-12 11:54:45.296+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852861489, 2, 1, '2008-07-12 11:54:21.25+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852861771, 6, 95, '2008-07-12 11:54:21.531+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852874528, 2, 1, '2008-07-12 11:54:34.281+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852869151, 2, 1, '2008-07-12 11:54:28.906+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852875029, 6, 95, '2008-07-12 11:54:34.781+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852866071, 2, 1, '2008-07-12 11:54:25.828+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852869402, 6, 95, '2008-07-12 11:54:29.156+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852877405, 2, 1, '2008-07-12 11:54:37.156+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852877734, 6, 95, '2008-07-12 11:54:37.484+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852881032, 2, 1, '2008-07-12 11:54:40.781+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852881627, 7, 95, '2008-07-12 11:54:41.375+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852885863, 7, 95, '2008-07-12 11:54:45.609+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852893070, 6, 95, '2008-07-12 11:54:52.812+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852889598, 2, 1, '2008-07-12 11:54:49.343+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852892757, 2, 1, '2008-07-12 11:54:52.50+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852896588, 6, 95, '2008-07-12 11:54:56.328+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852897182, 2, 1, '2008-07-12 11:54:56.921+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852980856, 6, 95, '2008-07-12 11:56:20.593+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852896305, 2, 1, '2008-07-12 11:54:56.046+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215852980402, 2, 1, '2008-07-12 11:56:20.14+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215853639473, 2, 1, '2008-07-12 12:07:19.456+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215853639677, 7, 95, '2008-07-12 12:07:19.659+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215855933215, 6, 95, '2008-07-12 12:45:33.159+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215853611301, 2, 1, '2008-07-12 12:06:51.284+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215853611943, 6, 95, '2008-07-12 12:06:51.941+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215856168068, 6, 95, '2008-07-12 12:49:28.003+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215855916421, 2, 1, '2008-07-12 12:45:16.378+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215853614834, 2, 1, '2008-07-12 12:06:54.831+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215853614991, 6, 95, '2008-07-12 12:06:54.987+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215853642600, 2, 1, '2008-07-12 12:07:22.581+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215853642804, 7, 95, '2008-07-12 12:07:22.784+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215855900380, 2, 1, '2008-07-12 12:45:00.347+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215853618321, 2, 1, '2008-07-12 12:06:58.316+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215853618540, 6, 95, '2008-07-12 12:06:58.534+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215855900740, 6, 95, '2008-07-12 12:45:00.706+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215855916703, 6, 95, '2008-07-12 12:45:16.659+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215853620573, 2, 1, '2008-07-12 12:07:00.566+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215853620730, 6, 95, '2008-07-12 12:07:00.722+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215853646352, 2, 1, '2008-07-12 12:07:26.331+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215853646509, 7, 95, '2008-07-12 12:07:26.487+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215853622575, 2, 1, '2008-07-12 12:07:02.566+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215853622779, 6, 95, '2008-07-12 12:07:02.769+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215855904007, 2, 1, '2008-07-12 12:45:03.972+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215855904195, 6, 95, '2008-07-12 12:45:04.159+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215853648901, 2, 1, '2008-07-12 12:07:28.878+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215853624733, 2, 1, '2008-07-12 12:07:04.722+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215853625140, 6, 95, '2008-07-12 12:07:05.128+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215853649058, 6, 95, '2008-07-12 12:07:29.034+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215853626844, 2, 1, '2008-07-12 12:07:06.831+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215853632845, 6, 95, '2008-07-12 12:07:12.831+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215853652374, 2, 1, '2008-07-12 12:07:32.347+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215855927007, 2, 1, '2008-07-12 12:45:26.956+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215855927664, 7, 95, '2008-07-12 12:45:27.612+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215853636768, 2, 1, '2008-07-12 12:07:16.753+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215853637035, 6, 95, '2008-07-12 12:07:17.019+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215855918548, 2, 1, '2008-07-12 12:45:18.503+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215853651184, 2, 1, '2008-07-12 12:07:31.159+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215853651451, 6, 95, '2008-07-12 12:07:31.425+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215855918705, 6, 95, '2008-07-12 12:45:18.659+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215855909556, 2, 1, '2008-07-12 12:45:09.519+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215855909822, 6, 95, '2008-07-12 12:45:09.784+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215855082078, 2, 1, '2008-07-12 12:31:22.05+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215855082376, 6, 95, '2008-07-12 12:31:22.347+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215855911589, 2, 1, '2008-07-12 12:45:11.55+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215855617627, 2, 1, '2008-07-12 12:40:17.597+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215855617972, 6, 95, '2008-07-12 12:40:17.941+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215855693285, 1, 47, '2008-07-12 12:41:33.253+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215855912231, 6, 95, '2008-07-12 12:45:12.191+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215855936750, 2, 1, '2008-07-12 12:45:36.691+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859299279, 6, 95, '2008-07-12 13:41:39.269+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215855921253, 2, 1, '2008-07-12 12:45:21.206+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215855921473, 6, 95, '2008-07-12 12:45:21.425+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215855914169, 2, 1, '2008-07-12 12:45:14.128+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215855914623, 6, 95, '2008-07-12 12:45:14.581+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215855935560, 2, 1, '2008-07-12 12:45:35.503+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215855935717, 6, 95, '2008-07-12 12:45:35.659+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215855930478, 2, 1, '2008-07-12 12:45:30.425+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215855930698, 7, 95, '2008-07-12 12:45:30.644+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215855923740, 2, 1, '2008-07-12 12:45:23.691+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215855924116, 7, 95, '2008-07-12 12:45:24.066+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215856138878, 2, 1, '2008-07-12 12:48:58.816+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215856139207, 6, 95, '2008-07-12 12:48:59.144+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215855933058, 2, 1, '2008-07-12 12:45:33.003+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215856109032, 2, 1, '2008-07-12 12:48:28.972+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215856109517, 6, 95, '2008-07-12 12:48:29.456+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859170369, 2, 1, '2008-07-12 13:39:30.347+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215858965569, 2, 1, '2008-07-12 13:36:05.566+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215856415473, 2, 1, '2008-07-12 12:53:35.456+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215858965960, 6, 95, '2008-07-12 13:36:05.956+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215856167614, 2, 1, '2008-07-12 12:49:27.55+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215856415818, 6, 95, '2008-07-12 12:53:35.816+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859084539, 2, 1, '2008-07-12 13:38:04.534+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859403343, 6, 95, '2008-07-12 13:43:23.331+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859084775, 6, 95, '2008-07-12 13:38:04.769+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859170714, 6, 95, '2008-07-12 13:39:30.706+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859298778, 2, 1, '2008-07-12 13:41:38.769+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859402967, 2, 1, '2008-07-12 13:43:22.956+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859498423, 6, 95, '2008-07-12 13:44:58.409+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859497969, 2, 1, '2008-07-12 13:44:57.956+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859531847, 6, 95, '2008-07-12 13:45:31.831+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859531487, 2, 1, '2008-07-12 13:45:31.472+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859534537, 6, 95, '2008-07-12 13:45:34.519+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859534317, 2, 1, '2008-07-12 13:45:34.30+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859538428, 2, 1, '2008-07-12 13:45:38.409+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859538601, 6, 95, '2008-07-12 13:45:38.581+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859550704, 2, 1, '2008-07-12 13:45:50.675+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859570011, 2, 1, '2008-07-12 13:46:09.972+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859540727, 2, 1, '2008-07-12 13:45:40.706+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859543635, 2, 1, '2008-07-12 13:45:43.612+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859546137, 2, 1, '2008-07-12 13:45:46.112+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859547968, 2, 1, '2008-07-12 13:45:47.941+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859566431, 2, 1, '2008-07-12 13:46:06.394+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859541025, 6, 95, '2008-07-12 13:45:41.003+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859543855, 6, 95, '2008-07-12 13:45:43.831+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859546357, 6, 95, '2008-07-12 13:45:46.331+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859548234, 6, 95, '2008-07-12 13:45:48.206+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859566963, 6, 95, '2008-07-12 13:46:06.925+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859551002, 6, 95, '2008-07-12 13:45:50.972+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859553487, 2, 1, '2008-07-12 13:45:53.456+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859570168, 6, 95, '2008-07-12 13:46:10.128+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859629483, 2, 1, '2008-07-12 13:47:09.441+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859553738, 7, 95, '2008-07-12 13:45:53.706+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859561851, 2, 1, '2008-07-12 13:46:01.816+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859570857, 2, 1, '2008-07-12 13:46:10.816+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859629687, 6, 95, '2008-07-12 13:47:09.644+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859557989, 2, 1, '2008-07-12 13:45:57.956+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859558287, 7, 95, '2008-07-12 13:45:58.253+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215860031875, 2, 1, '2008-07-12 13:53:51.80+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215860032267, 6, 95, '2008-07-12 13:53:52.191+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215860044385, 2, 1, '2008-07-12 13:54:04.30+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859562008, 7, 95, '2008-07-12 13:46:01.972+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215860044652, 6, 95, '2008-07-12 13:54:04.566+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859652563, 2, 1, '2008-07-12 13:47:32.503+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859652783, 7, 95, '2008-07-12 13:47:32.722+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859632594, 2, 1, '2008-07-12 13:47:12.55+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859632892, 6, 95, '2008-07-12 13:47:12.847+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215860108374, 2, 35, '2008-07-12 13:55:08.269+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215860034205, 2, 1, '2008-07-12 13:53:54.128+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859635877, 2, 1, '2008-07-12 13:47:15.831+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859636456, 6, 95, '2008-07-12 13:47:16.409+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215860034456, 6, 95, '2008-07-12 13:53:54.378+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859655799, 2, 1, '2008-07-12 13:47:35.737+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859656035, 7, 95, '2008-07-12 13:47:35.972+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859638301, 2, 1, '2008-07-12 13:47:18.253+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859638536, 6, 95, '2008-07-12 13:47:18.487+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215860108453, 2, 35, '2008-07-12 13:55:08.347+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859640053, 2, 1, '2008-07-12 13:47:20.003+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859640570, 6, 95, '2008-07-12 13:47:20.519+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215860055659, 2, 1, '2008-07-12 13:54:15.566+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859658348, 2, 1, '2008-07-12 13:47:38.284+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859658599, 6, 95, '2008-07-12 13:47:38.534+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215860046543, 2, 1, '2008-07-12 13:54:06.456+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859642430, 2, 1, '2008-07-12 13:47:22.378+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859642759, 6, 95, '2008-07-12 13:47:22.706+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215860037848, 2, 1, '2008-07-12 13:53:57.769+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215860038114, 6, 95, '2008-07-12 13:53:58.034+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859662102, 2, 1, '2008-07-12 13:47:42.034+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859644713, 2, 1, '2008-07-12 13:47:24.659+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859644964, 6, 95, '2008-07-12 13:47:24.909+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215860046982, 6, 95, '2008-07-12 13:54:06.894+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859661319, 2, 1, '2008-07-12 13:47:41.253+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859661570, 6, 95, '2008-07-12 13:47:41.503+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859646903, 2, 1, '2008-07-12 13:47:26.847+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859647123, 6, 95, '2008-07-12 13:47:27.066+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859665229, 2, 62, '2008-07-12 13:47:45.097+03', 0, 0, true, false, false, false, false, 1215859665103, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859665103, 2, 61, '2008-07-12 13:47:45.034+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859675433, 2, 46, '2008-07-12 13:47:55.362+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859649327, 2, 1, '2008-07-12 13:47:29.269+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859649640, 7, 95, '2008-07-12 13:47:29.581+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859706778, 2, 35, '2008-07-12 13:48:26.706+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859706935, 2, 35, '2008-07-12 13:48:26.862+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215860055941, 7, 95, '2008-07-12 13:54:15.847+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215859707296, 2, 35, '2008-07-12 13:48:27.222+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215860039897, 2, 1, '2008-07-12 13:53:59.816+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215860040210, 6, 95, '2008-07-12 13:54:00.128+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215860064495, 2, 1, '2008-07-12 13:54:24.394+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215860049498, 2, 1, '2008-07-12 13:54:09.409+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215860049859, 6, 95, '2008-07-12 13:54:09.769+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215860041992, 2, 1, '2008-07-12 13:54:01.909+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215860042618, 6, 95, '2008-07-12 13:54:02.534+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215860108985, 2, 35, '2008-07-12 13:55:08.878+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215860063649, 2, 1, '2008-07-12 13:54:23.55+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215860063806, 6, 95, '2008-07-12 13:54:23.706+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215860058817, 2, 1, '2008-07-12 13:54:18.722+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215860059052, 7, 95, '2008-07-12 13:54:18.956+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215860052172, 2, 1, '2008-07-12 13:54:12.081+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215860052814, 7, 95, '2008-07-12 13:54:12.722+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215860067575, 2, 62, '2008-07-12 13:54:27.441+03', 0, 0, true, false, false, false, false, 1215860067480, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215860067480, 2, 61, '2008-07-12 13:54:27.378+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215860078795, 2, 46, '2008-07-12 13:54:38.691+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215860061569, 2, 1, '2008-07-12 13:54:21.472+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215860061742, 6, 95, '2008-07-12 13:54:21.644+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215860449395, 2, 45, '2008-07-12 14:00:49.284+03', 0, 0, true, false, false, false, false, 1215860448753, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215861603240, 2, 1, '2008-07-12 14:20:03.128+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215862243512, 1, 47, '2008-07-12 14:30:43.394+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215861923494, 1, 47, '2008-07-12 14:25:23.378+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215860448832, 2, 37, '2008-07-12 14:00:48.722+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215860448753, 2, 40, '2008-07-12 14:00:48.644+03', 0, 0, true, false, false, false, false, 1215860448642, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215860448642, 2, 1, '2008-07-12 14:00:48.534+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215862098995, 1, 47, '2008-07-12 14:28:18.878+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215861603445, 2, 37, '2008-07-12 14:20:03.331+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215861603743, 2, 45, '2008-07-12 14:20:03.628+03', 0, 0, true, false, false, false, false, 1215861603366, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215861603366, 2, 40, '2008-07-12 14:20:03.253+03', 0, 0, true, false, false, false, false, 1215861603240, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215862317575, 1, 47, '2008-07-12 14:31:57.456+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215862348420, 1, 47, '2008-07-12 14:32:28.30+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215862892296, 1, 1, '2008-07-12 14:41:32.175+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215862892422, 2, 47, '2008-07-12 14:41:32.30+03', 0, 0, true, false, false, false, false, 1215862892296, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215862912407, 2, 1, '2008-07-12 14:41:52.284+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215862912502, 2, 47, '2008-07-12 14:41:52.378+03', 0, 0, true, false, false, false, false, 1215862912407, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215862954706, 2, 1, '2008-07-12 14:42:34.581+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215871907632, 6, 95, '2008-07-12 17:11:47.487+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215871923754, 6, 95, '2008-07-12 17:12:03.597+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215862955178, 2, 45, '2008-07-12 14:42:35.05+03', 0, 0, true, false, false, false, false, 1215862954817, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215862954896, 2, 37, '2008-07-12 14:42:34.769+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215862954817, 2, 40, '2008-07-12 14:42:34.691+03', 0, 0, true, false, false, false, false, 1215862954706, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215871174273, 2, 59, '2008-07-12 16:59:34.144+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215871738322, 2, 62, '2008-07-12 17:08:58.175+03', 0, 0, true, false, false, false, false, 1215871738211, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215871738211, 2, 61, '2008-07-12 17:08:58.081+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215871926929, 2, 62, '2008-07-12 17:12:06.769+03', 0, 0, true, false, false, false, false, 1215871926865, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215871926865, 2, 61, '2008-07-12 17:12:06.706+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215871909321, 2, 1, '2008-07-12 17:11:49.175+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215871909603, 6, 95, '2008-07-12 17:11:49.456+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215871895073, 2, 1, '2008-07-12 17:11:34.941+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215871895308, 6, 95, '2008-07-12 17:11:35.175+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216151637912, 7, 95, '2008-07-15 22:53:57.89+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215871938039, 2, 46, '2008-07-12 17:12:17.878+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215871897293, 2, 1, '2008-07-12 17:11:37.159+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215871897544, 6, 95, '2008-07-12 17:11:37.409+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215871911292, 2, 1, '2008-07-12 17:11:51.144+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215871911543, 7, 95, '2008-07-12 17:11:51.394+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215871900233, 2, 1, '2008-07-12 17:11:40.097+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215871900390, 6, 95, '2008-07-12 17:11:40.253+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215871969728, 2, 35, '2008-07-12 17:12:49.566+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215871969807, 2, 35, '2008-07-12 17:12:49.644+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215871902047, 2, 1, '2008-07-12 17:11:41.909+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215871902205, 6, 95, '2008-07-12 17:11:42.066+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215871970261, 2, 35, '2008-07-12 17:12:50.097+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215871914637, 2, 1, '2008-07-12 17:11:54.487+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215871914795, 7, 95, '2008-07-12 17:11:54.644+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215871903706, 2, 1, '2008-07-12 17:11:43.566+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215871903957, 6, 95, '2008-07-12 17:11:43.816+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216149297360, 4, 95, '2008-07-15 22:14:57.359+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216151628372, 2, 1, '2008-07-15 22:53:48.359+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215871905770, 2, 1, '2008-07-12 17:11:45.628+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215871905927, 6, 95, '2008-07-12 17:11:45.784+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216151628623, 6, 95, '2008-07-15 22:53:48.609+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215871918358, 2, 1, '2008-07-12 17:11:58.206+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216151622897, 2, 1, '2008-07-15 22:53:42.89+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215871907335, 2, 1, '2008-07-12 17:11:47.191+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215871918594, 7, 95, '2008-07-12 17:11:58.441+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216151623023, 6, 95, '2008-07-15 22:53:43.015+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216151614891, 2, 1, '2008-07-15 22:53:34.89+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216151615423, 6, 95, '2008-07-15 22:53:35.421+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215871921516, 2, 1, '2008-07-12 17:12:01.362+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215871921783, 6, 95, '2008-07-12 17:12:01.628+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215871924161, 2, 1, '2008-07-12 17:12:04.003+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216151641948, 2, 1, '2008-07-15 22:54:01.921+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1215871923597, 2, 1, '2008-07-12 17:12:03.441+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216151618081, 2, 1, '2008-07-15 22:53:38.078+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216151618238, 6, 95, '2008-07-15 22:53:38.234+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216326891860, 2, 95, '2008-07-17 23:34:51.859+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216151624587, 2, 1, '2008-07-15 22:53:44.578+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216151624791, 6, 95, '2008-07-15 22:53:44.781+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216151621286, 2, 1, '2008-07-15 22:53:41.281+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216151621396, 6, 95, '2008-07-15 22:53:41.39+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216151630327, 2, 1, '2008-07-15 22:53:50.312+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216151630469, 6, 95, '2008-07-15 22:53:50.453+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216151635065, 2, 1, '2008-07-15 22:53:55.046+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216151626511, 2, 1, '2008-07-15 22:53:46.50+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216151626637, 6, 95, '2008-07-15 22:53:46.625+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216151635270, 7, 95, '2008-07-15 22:53:55.25+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216151641353, 2, 1, '2008-07-15 22:54:01.328+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216151641510, 6, 95, '2008-07-15 22:54:01.484+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216151639538, 2, 1, '2008-07-15 22:53:59.515+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216151639742, 6, 95, '2008-07-15 22:53:59.718+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216151632173, 2, 1, '2008-07-15 22:53:52.156+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216151632299, 7, 95, '2008-07-15 22:53:52.281+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216151637786, 2, 1, '2008-07-15 22:53:57.765+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216152330293, 2, 1, '2008-07-15 23:05:30.265+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216327142954, 2, 95, '2008-07-17 23:39:02.953+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216152330497, 6, 95, '2008-07-15 23:05:30.468+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216152479498, 2, 1, '2008-07-15 23:07:59.468+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216472116438, 1, 100, '2008-07-19 15:55:16.421+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216474805297, 1, 40, '2008-07-19 16:40:05.296+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216152479749, 6, 95, '2008-07-15 23:07:59.718+03', 0, 0, true, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216474915457, 1, 45, '2008-07-19 16:41:55.453+03', 0, 0, false, false, false, false, false, 1216474805297, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216474970646, 1, 1, '2008-07-19 16:42:50.64+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216476241907, 2, 62, '2008-07-19 17:04:01.859+03', 0, 0, false, false, false, false, false, 1211131456610, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1216480868532, 1, 1, '2008-07-19 18:21:08.515+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217063155346, 2, 46, '2008-07-26 12:05:55.345+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217070308200, 2, 95, '2008-07-26 14:05:08.197+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217070771135, 1, 37, '2008-07-26 14:12:51.132+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217070882762, 2, 43, '2008-07-26 14:14:42.757+03', 0, 0, false, false, false, false, false, 1209222409189, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217070935442, 2, 95, '2008-07-26 14:15:35.437+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217071164250, 1, 43, '2008-07-26 14:19:24.244+03', 0, 0, false, false, false, false, false, 1212004334360, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217071168446, 2, 95, '2008-07-26 14:19:28.439+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217071696711, 2, 104, '2008-07-26 14:28:16.701+03', 0, 0, false, false, false, false, false, 1217071630343, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217071726740, 1, 104, '2008-07-26 14:28:46.73+03', 0, 0, false, false, false, false, false, 1217071630343, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217071630343, 2, 103, '2008-07-26 14:27:10.335+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217071781890, 1, 104, '2008-07-26 14:29:41.878+03', 0, 0, false, false, false, false, false, 1217071772215, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217071772215, 2, 103, '2008-07-26 14:29:32.204+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217071819011, 1, 104, '2008-07-26 14:30:18.997+03', 0, 0, false, false, false, false, false, 1217071803066, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217071819181, 1, 104, '2008-07-26 14:30:19.166+03', 0, 0, false, false, false, false, false, 1217071803066, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217071803066, 2, 103, '2008-07-26 14:30:03.053+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217071946277, 1, 104, '2008-07-26 14:32:26.26+03', 0, 0, false, false, false, false, false, 1217071919490, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217071919490, 2, 103, '2008-07-26 14:31:59.474+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217072462563, 1, 104, '2008-07-26 14:41:02.543+03', 0, 0, false, false, false, false, false, 1217072449079, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217072449079, 2, 103, '2008-07-26 14:40:49.061+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217072510782, 1, 106, '2008-07-26 14:41:50.761+03', 0, 0, false, false, false, false, false, 1217072496486, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217072552076, 1, 106, '2008-07-26 14:42:32.054+03', 0, 0, false, false, false, false, false, 1217072496486, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217072613710, 1, 106, '2008-07-26 14:43:33.686+03', 0, 0, false, false, false, false, false, 1217072598920, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217072622986, 1, 106, '2008-07-26 14:43:42.961+03', 0, 0, false, false, false, false, false, 1217072598920, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217072598920, 2, 105, '2008-07-26 14:43:18.897+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217072831308, 1, 106, '2008-07-26 14:47:11.281+03', 0, 0, false, false, false, false, false, 1217072805425, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217072805425, 2, 105, '2008-07-26 14:46:45.399+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217073464044, 1, 106, '2008-07-26 14:57:44.015+03', 0, 0, false, false, false, false, false, 1217073448418, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217073448418, 2, 105, '2008-07-26 14:57:28.39+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217073527765, 1, 106, '2008-07-26 14:58:47.735+03', 0, 0, false, false, false, false, false, 1217073448418, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217073662483, 1, 106, '2008-07-26 15:01:02.451+03', 0, 0, false, false, false, false, false, 1217073632644, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217073632644, 2, 105, '2008-07-26 15:00:32.613+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217073894084, 1, 104, '2008-07-26 15:04:54.05+03', 0, 0, false, false, false, false, false, 1217073858272, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217073858272, 2, 103, '2008-07-26 15:04:18.239+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217074358953, 1, 103, '2008-07-26 15:12:38.918+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217074744177, 1, 103, '2008-07-26 15:19:04.141+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217074761612, 1, 104, '2008-07-26 15:19:21.575+03', 0, 0, false, false, false, false, false, 1217074744177, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217075219686, 1, 103, '2008-07-26 15:26:59.647+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217075242011, 1, 104, '2008-07-26 15:27:21.972+03', 0, 0, false, false, false, false, false, 1217075219686, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217075259518, 1, 104, '2008-07-26 15:27:39.478+03', 0, 0, false, false, false, false, false, 1217075219686, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217075487662, 1, 103, '2008-07-26 15:31:27.621+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217075500165, 1, 104, '2008-07-26 15:31:40.123+03', 0, 0, false, false, false, false, false, 1217075487662, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217075836623, 1, 103, '2008-07-26 15:37:16.58+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217075848024, 1, 104, '2008-07-26 15:37:27.98+03', 0, 0, false, false, false, false, false, 1217075836623, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217076157491, 1, 103, '2008-07-26 15:42:37.446+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217076171707, 1, 104, '2008-07-26 15:42:51.661+03', 0, 0, false, false, false, false, false, 1217076157491, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217076433070, 1, 104, '2008-07-26 15:47:13.022+03', 0, 0, false, false, false, false, false, 1217076418362, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217076418362, 2, 103, '2008-07-26 15:46:58.315+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217077019725, 2, 105, '2008-07-26 15:56:59.676+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217077226599, 1, 104, '2008-07-26 16:00:26.546+03', 0, 0, false, false, false, false, false, 1217077206751, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217077206751, 3, 103, '2008-07-26 16:00:06.70+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217077226429, 2, 104, '2008-07-26 16:00:26.377+03', 0, 0, false, false, false, false, false, 1217077206751, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217077037905, 2, 106, '2008-07-26 15:57:17.855+03', 0, 0, false, false, false, false, false, 1217077019725, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217078443144, 1, 104, '2008-07-26 16:20:43.089+03', 0, 0, false, false, false, false, false, 1217078416388, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217078416388, 2, 103, '2008-07-26 16:20:16.334+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217331475938, 2, 102, '2008-07-29 14:37:55.937+03', 0, 0, true, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217331992157, 2, 102, '2008-07-29 14:46:32.156+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217333579766, 2, 102, '2008-07-29 15:12:59.765+03', 0, 0, true, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217340539126, 2, 102, '2008-07-29 17:08:59.125+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217345444119, 1, 37, '2008-07-29 18:30:44.116+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217053087891, 22, 102, '2008-07-26 09:18:07.89+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217345737392, 1, 43, '2008-07-29 18:35:37.387+03', 0, 0, false, false, false, false, false, 1217345457805, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217345752103, 1, 43, '2008-07-29 18:35:52.097+03', 0, 0, false, false, false, false, false, 1217345457805, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217345457805, 2, 40, '2008-07-29 18:30:57.801+03', 0, 0, false, false, false, false, false, 1217345408763, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217345408763, 2, 1, '2008-07-29 18:30:08.759+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217345782783, 1, 43, '2008-07-29 18:36:22.775+03', 0, 0, false, false, false, false, false, 1217345766750, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217345766750, 2, 40, '2008-07-29 18:36:06.743+03', 0, 0, false, false, false, false, false, 1217345422152, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217345422152, 2, 1, '2008-07-29 18:30:22.15+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217345812528, 1, 43, '2008-07-29 18:36:52.518+03', 0, 0, false, false, false, false, false, 1217345796939, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217345828141, 1, 103, '2008-07-29 18:37:08.13+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1209288962569, 6, 35, '2008-04-27 12:36:02.563+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217345796939, 2, 40, '2008-07-29 18:36:36.93+03', 0, 0, false, false, false, false, false, 1217345444119, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217345869202, 2, 107, '2008-07-29 18:37:49.19+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217346072123, 1, 60, '2008-07-29 18:41:12.108+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217345903966, 1, 107, '2008-07-29 18:38:23.953+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217068585269, 2, 46, '2008-07-26 13:36:25.268+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217346044742, 1, 60, '2008-07-29 18:40:44.728+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217346108350, 1, 108, '2008-07-29 18:41:48.333+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217346091438, 1, 108, '2008-07-29 18:41:31.422+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217407278985, 1, 1, '2008-07-30 11:41:18.984+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217409372641, 1, 1, '2008-07-30 12:16:12.64+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217410126469, 1, 1, '2008-07-30 12:28:46.468+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217410631001, 1, 1, '2008-07-30 12:37:11+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217410641955, 2, 95, '2008-07-30 12:37:21.953+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217503575535, 2, 106, '2008-07-31 14:26:15.531+03', 0, 0, false, false, false, false, false, 1217072496486, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217072496486, 3, 105, '2008-07-26 14:41:36.466+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217513829280, 1, 47, '2008-07-31 17:17:09.277+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217513833603, 1, 43, '2008-07-31 17:17:13.599+03', 0, 0, false, false, false, false, false, 1217513800243, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217513800243, 2, 40, '2008-07-31 17:16:40.24+03', 0, 0, false, false, false, false, false, 1217513786510, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217513786510, 2, 1, '2008-07-31 17:16:26.508+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217513915835, 2, 104, '2008-07-31 17:18:35.828+03', 0, 0, false, false, false, false, false, 1217513856396, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217513856396, 4, 103, '2008-07-31 17:17:36.391+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217513887379, 3, 104, '2008-07-31 17:18:07.373+03', 0, 0, false, false, false, false, false, 1217513856396, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217514133832, 3, 106, '2008-07-31 17:22:13.823+03', 0, 0, false, false, false, false, false, 1217514089109, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217514089109, 3, 105, '2008-07-31 17:21:29.101+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217514926610, 1, 37, '2008-07-31 17:35:26.609+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217514926611, 2, 95, '2008-07-31 17:35:26.609+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217514815112, 1, 106, '2008-07-31 17:33:35.101+03', 0, 0, false, false, false, false, false, 1217514418040, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217514837210, 1, 106, '2008-07-31 17:33:57.198+03', 0, 0, false, false, false, false, false, 1217514418040, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217514418040, 2, 105, '2008-07-31 17:26:58.03+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217540926938, 1, 37, '2008-08-01 00:48:46.937+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217540926955, 2, 95, '2008-08-01 00:48:46.953+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217540927346, 2, 43, '2008-08-01 00:48:47.343+03', 0, 0, false, false, false, false, false, 1210856501325, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217623255938, 1, 37, '2008-08-01 23:40:55.937+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217623256284, 1, 43, '2008-08-01 23:40:56.281+03', 0, 0, false, false, false, false, false, 1209222423971, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217623255955, 2, 95, '2008-08-01 23:40:55.953+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217623859251, 1, 37, '2008-08-01 23:50:59.25+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217623859581, 1, 43, '2008-08-01 23:50:59.578+03', 0, 0, false, false, false, false, false, 1209222423971, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217623859252, 2, 95, '2008-08-01 23:50:59.25+03', 0, 0, false, false, false, false, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217765063921, 2, 105, '2008-08-03 15:04:23.92+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217765108443, 1, 105, '2008-08-03 15:05:08.441+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217765164027, 1, 105, '2008-08-03 15:06:04.024+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217765175609, 2, 106, '2008-08-03 15:06:15.605+03', 0, 0, false, false, false, false, false, 1217765164027, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, order_position, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri) VALUES (1217765228385, 1, 103, '2008-08-03 15:07:08.38+03', 0, 0, false, false, false, false, false, 1209222047860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);


--
-- TOC entry 2385 (class 0 OID 36801)
-- Dependencies: 1635
-- Data for Name: delivery_certificate_assignments; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO delivery_certificate_assignments (delivery_certificate_id, document_id) VALUES (1216472116438, 1214683373782);


--
-- TOC entry 2386 (class 0 OID 36804)
-- Dependencies: 1636
-- Data for Name: delivery_certificate_items; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2387 (class 0 OID 36807)
-- Dependencies: 1637
-- Data for Name: delivery_certificate_serial_numbers; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2388 (class 0 OID 36810)
-- Dependencies: 1638
-- Data for Name: delivery_certificates; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO delivery_certificates (delivery_certificate_id, parent_id, warehouse_id, warehouse_name, delivery_certificate_number, delivery_certificate_date, recipient_id, recipient_name, recipient_contact_id, recipient_contact_name, delivery_cert_method_type_id, creation_time, creator_name, forwarder_id, forwarder_name, forwarder_contact_id, forwarder_contact_name, delivery_cert_reason_id, creator_id, forwarder_branch_id, recipient_branch_id) VALUES (1215288167266, NULL, 1213952129954, 'Massy Warehouse', 3876423452385, '2008-07-05', 1209394438429, 'IBM', 1206862211011, 'Miroslav Nachev', 125, '2008-07-05', 'Velev', NULL, NULL, NULL, NULL, 127, 1213963345878, NULL, NULL);
INSERT INTO delivery_certificates (delivery_certificate_id, parent_id, warehouse_id, warehouse_name, delivery_certificate_number, delivery_certificate_date, recipient_id, recipient_name, recipient_contact_id, recipient_contact_name, delivery_cert_method_type_id, creation_time, creator_name, forwarder_id, forwarder_name, forwarder_contact_id, forwarder_contact_name, delivery_cert_reason_id, creator_id, forwarder_branch_id, recipient_branch_id) VALUES (1216472116438, NULL, 1213952129954, 'Massy Warehouse', 2111525321, '2008-07-19', 1209394438429, 'IBM', 1214803184453, 'Radoslav Lozanov', 126, '2008-07-19', 'Velev', 1209222047860, 'SmartMinds', NULL, NULL, 127, 1213963345878, NULL, NULL);


--
-- TOC entry 2389 (class 0 OID 36813)
-- Dependencies: 1639
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
INSERT INTO enum_classes (enum_class_id, enum_class_name) VALUES (20, 'com.cosmos.acacia.crm.enums.DeliveryCertificateMethodType');
INSERT INTO enum_classes (enum_class_id, enum_class_name) VALUES (21, 'com.cosmos.acacia.crm.enums.DeliveryCertificateReason');
INSERT INTO enum_classes (enum_class_id, enum_class_name) VALUES (22, 'com.cosmos.acacia.crm.enums.SpecialPermission');
INSERT INTO enum_classes (enum_class_id, enum_class_name) VALUES (23, 'com.cosmos.acacia.crm.enums.PurchaseOrderStatus');
INSERT INTO enum_classes (enum_class_id, enum_class_name) VALUES (24, 'com.cosmos.acacia.crm.enums.OrderConfirmationType');


--
-- TOC entry 2390 (class 0 OID 36816)
-- Dependencies: 1640
-- Data for Name: invoice_items; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2391 (class 0 OID 36819)
-- Dependencies: 1641
-- Data for Name: invoices; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO invoices (invoice_id, parent_id, branch_id, branch_name, invoice_number, invoice_date, recipient_id, recipient_name, recipient_contact_id, recipient_contact_name, invoice_type_id, status_id, creation_time, creator_id, creator_name, doc_delivery_method_id, transportation_method_id, shipping_agent_id, transportation_price, currency_id, invoice_sub_value, discount_percent, discount_value, invoice_value, excise_duty_percent, excise_duty_value, vat_condition_id, vat_percent, vat_value, total_invoice_value, payment_type_id, payment_terms_id, payment_due_date, delivery_type_id, sent_time, sender_id, sender_name, first_ship_date, last_ship_date, finalizing_date) VALUES (1214683373782, NULL, 1206861699860, 'Branch X', 1011001101, '2008-06-28', 1206862211011, 'IBM', 1206862211011, 'Miroslav Nachev', 81, 78, '2008-06-28', 1211482143869, 'Р”Р°РЅРёРµР» Р’РµР»РµРІ', 113, 94, 1214804095151, NULL, 49, 454366.0000, NULL, NULL, 454366.0000, NULL, NULL, 103, 0.20, 277.0000, 454366.0000, 105, 108, '2008-06-24', 88, NULL, NULL, NULL, NULL, NULL, NULL);


--
-- TOC entry 2392 (class 0 OID 36822)
-- Dependencies: 1642
-- Data for Name: order_confirmation_items; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO order_confirmation_items (confirmation_item_id, confirmed_quantity, extended_price, notes, parent_id, ship_date_from, ship_date_to, unit_price, measure_unit_id, product_id, currency_id, matched_quantity, ship_week) VALUES (1217072510782.00, 1.00, 1099.23, NULL, 1217072496486.00, NULL, NULL, 1099.23, 1, 1217063155346.00, 49, NULL, NULL);
INSERT INTO order_confirmation_items (confirmation_item_id, confirmed_quantity, extended_price, notes, parent_id, ship_date_from, ship_date_to, unit_price, measure_unit_id, product_id, currency_id, matched_quantity, ship_week) VALUES (1217072552076.00, 300.00, 168.00, NULL, 1217072496486.00, NULL, NULL, 0.56, 1, 1217068585269.00, 49, NULL, NULL);
INSERT INTO order_confirmation_items (confirmation_item_id, confirmed_quantity, extended_price, notes, parent_id, ship_date_from, ship_date_to, unit_price, measure_unit_id, product_id, currency_id, matched_quantity, ship_week) VALUES (1217072613710.00, 29.00, 23.20, NULL, 1217072598920.00, NULL, NULL, 0.80, 1, 1217068585269.00, 49, NULL, NULL);
INSERT INTO order_confirmation_items (confirmation_item_id, confirmed_quantity, extended_price, notes, parent_id, ship_date_from, ship_date_to, unit_price, measure_unit_id, product_id, currency_id, matched_quantity, ship_week) VALUES (1217072622986.00, 4.00, 20.00, NULL, 1217072598920.00, NULL, NULL, 5.00, 1, 1217068585269.00, 49, NULL, NULL);
INSERT INTO order_confirmation_items (confirmation_item_id, confirmed_quantity, extended_price, notes, parent_id, ship_date_from, ship_date_to, unit_price, measure_unit_id, product_id, currency_id, matched_quantity, ship_week) VALUES (1217072831308.00, 778.00, 816.90, 'somenotes', 1217072805425.00, '2008-07-09', '2008-07-31', 1.05, 1, 1217068585269.00, 49, NULL, NULL);
INSERT INTO order_confirmation_items (confirmation_item_id, confirmed_quantity, extended_price, notes, parent_id, ship_date_from, ship_date_to, unit_price, measure_unit_id, product_id, currency_id, matched_quantity, ship_week) VALUES (1217073464044.00, 89.00, 106.80, NULL, 1217073448418.00, NULL, NULL, 1.20, 1, 1217063155346.00, 49, NULL, NULL);
INSERT INTO order_confirmation_items (confirmation_item_id, confirmed_quantity, extended_price, notes, parent_id, ship_date_from, ship_date_to, unit_price, measure_unit_id, product_id, currency_id, matched_quantity, ship_week) VALUES (1217073527765.00, 3.00, 9.00, NULL, 1217073448418.00, NULL, NULL, 3.00, 1, 1217068585269.00, 49, NULL, NULL);
INSERT INTO order_confirmation_items (confirmation_item_id, confirmed_quantity, extended_price, notes, parent_id, ship_date_from, ship_date_to, unit_price, measure_unit_id, product_id, currency_id, matched_quantity, ship_week) VALUES (1217073662483.00, 7.00, 49.00, NULL, 1217073632644.00, NULL, NULL, 7.00, 1, 1217063155346.00, 49, NULL, NULL);
INSERT INTO order_confirmation_items (confirmation_item_id, confirmed_quantity, extended_price, notes, parent_id, ship_date_from, ship_date_to, unit_price, measure_unit_id, product_id, currency_id, matched_quantity, ship_week) VALUES (1217077037905.00, 5.00, 25.00, NULL, 1217077019725.00, '2008-07-26', '2008-07-31', 5.00, 1, 1217063155346.00, 49, 5.00, NULL);
INSERT INTO order_confirmation_items (confirmation_item_id, confirmed_quantity, extended_price, notes, parent_id, ship_date_from, ship_date_to, unit_price, measure_unit_id, product_id, currency_id, matched_quantity, ship_week) VALUES (1217503575535.00, 3.00, 6.00, NULL, 1217072496486.00, '2008-09-26', '2008-10-07', 2.00, 1, 1217063155346.00, 49, NULL, 39);
INSERT INTO order_confirmation_items (confirmation_item_id, confirmed_quantity, extended_price, notes, parent_id, ship_date_from, ship_date_to, unit_price, measure_unit_id, product_id, currency_id, matched_quantity, ship_week) VALUES (1217514133832.00, 30.00, 360.00, NULL, 1217514089109.00, '2008-08-04', NULL, 12.00, 1, 1217063155346.00, 49, 30.00, 32);
INSERT INTO order_confirmation_items (confirmation_item_id, confirmed_quantity, extended_price, notes, parent_id, ship_date_from, ship_date_to, unit_price, measure_unit_id, product_id, currency_id, matched_quantity, ship_week) VALUES (1217514815112.00, 35.00, 490.00, NULL, 1217514418040.00, '2008-09-07', NULL, 14.00, 1, 1217063155346.00, 49, NULL, 37);
INSERT INTO order_confirmation_items (confirmation_item_id, confirmed_quantity, extended_price, notes, parent_id, ship_date_from, ship_date_to, unit_price, measure_unit_id, product_id, currency_id, matched_quantity, ship_week) VALUES (1217514837210.00, 35.00, 700.00, NULL, 1217514418040.00, NULL, NULL, 20.00, 1, 1217068585269.00, 49, NULL, NULL);
INSERT INTO order_confirmation_items (confirmation_item_id, confirmed_quantity, extended_price, notes, parent_id, ship_date_from, ship_date_to, unit_price, measure_unit_id, product_id, currency_id, matched_quantity, ship_week) VALUES (1217765175609.00, 5.00, 25.00, NULL, 1217765164027.00, '2008-02-10', '2008-02-15', 5.00, 1, 1217068585269.00, 49, NULL, 7);


--
-- TOC entry 2393 (class 0 OID 36825)
-- Dependencies: 1643
-- Data for Name: order_confirmations; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO order_confirmations (order_confirmation_id, discount_amount, discount_percent, documentdate_date, document_number, invoice_sub_value, notes, parent_id, supplier_contact_name, supplier_name, total_value, transport_price, vat, currency_id, document_type_id, supplier_partner_id, supplier_contact_id, branch_id, ship_date_from, ship_date_to, ship_week) VALUES (1217072598920.00, 0.00, 0.00, '2008-07-26', '909011102', 0.00, NULL, 1209222047860.00, 'Radoslav Mirchev Lozanov ', 'IBM', 0.00, 0.00, 20.00, 49, 142, 1209394438429.00, 1214803187723.00, 1209222423971.00, NULL, NULL, NULL);
INSERT INTO order_confirmations (order_confirmation_id, discount_amount, discount_percent, documentdate_date, document_number, invoice_sub_value, notes, parent_id, supplier_contact_name, supplier_name, total_value, transport_price, vat, currency_id, document_type_id, supplier_partner_id, supplier_contact_id, branch_id, ship_date_from, ship_date_to, ship_week) VALUES (1217072805425.00, 0.00, 0.00, '2008-07-26', '767676', 0.00, NULL, 1209222047860.00, 'Yamanja Bongo-Bongo Jumaikova ', 'Yamaha', 0.00, 0.00, 20.00, 49, 142, 1209582114840.00, 1217071164250.00, 1209222423971.00, NULL, NULL, NULL);
INSERT INTO order_confirmations (order_confirmation_id, discount_amount, discount_percent, documentdate_date, document_number, invoice_sub_value, notes, parent_id, supplier_contact_name, supplier_name, total_value, transport_price, vat, currency_id, document_type_id, supplier_partner_id, supplier_contact_id, branch_id, ship_date_from, ship_date_to, ship_week) VALUES (1217073448418.00, 0.00, 0.00, '2008-07-26', '440545', 115.80, NULL, 1209222047860.00, 'Yamanja Bongo-Bongo Jumaikova ', 'Yamaha', 115.80, 0.00, 0.00, 49, 143, 1209582114840.00, 1217071164250.00, 1209222423971.00, NULL, NULL, NULL);
INSERT INTO order_confirmations (order_confirmation_id, discount_amount, discount_percent, documentdate_date, document_number, invoice_sub_value, notes, parent_id, supplier_contact_name, supplier_name, total_value, transport_price, vat, currency_id, document_type_id, supplier_partner_id, supplier_contact_id, branch_id, ship_date_from, ship_date_to, ship_week) VALUES (1217073632644.00, 0.00, 0.00, '2008-07-26', '46565656', 0.00, NULL, 1209222047860.00, 'Yamanja Bongo-Bongo Jumaikova ', 'Yamaha', 0.00, 0.00, 20.00, 49, 142, 1209582114840.00, 1217071164250.00, 1209222423971.00, NULL, NULL, NULL);
INSERT INTO order_confirmations (order_confirmation_id, discount_amount, discount_percent, documentdate_date, document_number, invoice_sub_value, notes, parent_id, supplier_contact_name, supplier_name, total_value, transport_price, vat, currency_id, document_type_id, supplier_partner_id, supplier_contact_id, branch_id, ship_date_from, ship_date_to, ship_week) VALUES (1217077019725.00, 0.50, 2.00, '2008-07-26', '666', 25.00, NULL, 1209222047860.00, 'Yamanja Bongo-Bongo Jumaikova ', 'Yamaha', 29.50, 5.00, 0.00, 49, 143, 1209582114840.00, 1217071164250.00, 1209222409189.00, NULL, NULL, NULL);
INSERT INTO order_confirmations (order_confirmation_id, discount_amount, discount_percent, documentdate_date, document_number, invoice_sub_value, notes, parent_id, supplier_contact_name, supplier_name, total_value, transport_price, vat, currency_id, document_type_id, supplier_partner_id, supplier_contact_id, branch_id, ship_date_from, ship_date_to, ship_week) VALUES (1217072496486.00, 63.66, 5.00, '2008-07-26', '1233112', 1273.23, NULL, 1209222047860.00, 'Daniel  Velev ', 'IBM', 1564.21, 100.00, 20.00, 49, 142, 1209394438429.00, 1215451068003.00, 1209222423971.00, NULL, NULL, NULL);
INSERT INTO order_confirmations (order_confirmation_id, discount_amount, discount_percent, documentdate_date, document_number, invoice_sub_value, notes, parent_id, supplier_contact_name, supplier_name, total_value, transport_price, vat, currency_id, document_type_id, supplier_partner_id, supplier_contact_id, branch_id, ship_date_from, ship_date_to, ship_week) VALUES (1217514089109.00, 0.00, 0.00, '2008-07-31', 'SM-0001', 360.00, NULL, 1209222047860.00, 'Marco Van Basten ', 'G-U', 432.00, 0.00, 20.00, 49, 142, 1217513786510.00, 1217513833603.00, 1209222423971.00, NULL, NULL, NULL);
INSERT INTO order_confirmations (order_confirmation_id, discount_amount, discount_percent, documentdate_date, document_number, invoice_sub_value, notes, parent_id, supplier_contact_name, supplier_name, total_value, transport_price, vat, currency_id, document_type_id, supplier_partner_id, supplier_contact_id, branch_id, ship_date_from, ship_date_to, ship_week) VALUES (1217514418040.00, 0.00, 0.00, '2008-07-31', 'SM-0002', 1190.00, NULL, 1209222047860.00, 'Marco Van Basten ', 'G-U', 1428.00, 0.00, 20.00, 49, 142, 1217513786510.00, 1217513833603.00, 1209222423971.00, NULL, NULL, NULL);
INSERT INTO order_confirmations (order_confirmation_id, discount_amount, discount_percent, documentdate_date, document_number, invoice_sub_value, notes, parent_id, supplier_contact_name, supplier_name, total_value, transport_price, vat, currency_id, document_type_id, supplier_partner_id, supplier_contact_id, branch_id, ship_date_from, ship_date_to, ship_week) VALUES (1217765063921.00, 0.00, 0.00, '2008-08-03', '66453234', 0.00, NULL, 1209222047860.00, 'Bai  Ganio BG MAN', 'Provider of Wood', 0.00, 0.00, 20.00, 49, 142, 1217345422152.00, 1217345782783.00, 1209222423971.00, '2008-06-01', '2008-06-27', 23);
INSERT INTO order_confirmations (order_confirmation_id, discount_amount, discount_percent, documentdate_date, document_number, invoice_sub_value, notes, parent_id, supplier_contact_name, supplier_name, total_value, transport_price, vat, currency_id, document_type_id, supplier_partner_id, supplier_contact_id, branch_id, ship_date_from, ship_date_to, ship_week) VALUES (1217765108443.00, 0.00, 0.00, '2008-08-03', '6', 0.00, NULL, 1209222047860.00, 'Bai  Ganio BG MAN', 'Provider of Wood', 0.00, 0.00, 20.00, 49, 142, 1217345422152.00, 1217345782783.00, 1209222423971.00, '2008-08-09', '2008-08-09', 32);
INSERT INTO order_confirmations (order_confirmation_id, discount_amount, discount_percent, documentdate_date, document_number, invoice_sub_value, notes, parent_id, supplier_contact_name, supplier_name, total_value, transport_price, vat, currency_id, document_type_id, supplier_partner_id, supplier_contact_id, branch_id, ship_date_from, ship_date_to, ship_week) VALUES (1217765164027.00, 0.00, 0.00, '2008-08-03', '56', 0.00, NULL, 1209222047860.00, 'Marco Van Basten ', 'Provider of Stocks', 0.00, 0.00, 20.00, 49, 142, 1217345408763.00, 1217345752103.00, 1209222423971.00, '2008-06-03', '2008-08-30', 23);


--
-- TOC entry 2394 (class 0 OID 36831)
-- Dependencies: 1644
-- Data for Name: order_item_match; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO order_item_match (id, matchquantity, purchaseorderitem_order_item_id, orderconfirmationitem_confirmation_item_id) VALUES (8.00, 5.00, 1217077226429.00, 1217077037905.00);
INSERT INTO order_item_match (id, matchquantity, purchaseorderitem_order_item_id, orderconfirmationitem_confirmation_item_id) VALUES (9.00, 30.00, 1217513887379.00, 1217514133832.00);


--
-- TOC entry 2395 (class 0 OID 36834)
-- Dependencies: 1645
-- Data for Name: organizations; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id, is_active) VALUES (1211492793954, NULL, NULL, 'testbank', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id, is_active) VALUES (1211482143869, NULL, NULL, 'Branding Inc.', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id, is_active) VALUES (1209585931126, NULL, 'Civic', 'Honda', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id, is_active) VALUES (1209582114840, NULL, NULL, 'Yamaha', '2008-04-25', NULL, NULL, NULL, NULL, NULL, 1209394438429.00, NULL, NULL, true);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id, is_active) VALUES (1211990399266, NULL, NULL, 'testaaa', NULL, NULL, NULL, NULL, 22, NULL, NULL, NULL, NULL, true);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id, is_active) VALUES (1209394343621, NULL, 'op', 'Microsoftasdfasfsafsdafdafasfsadfas', '2008-04-13', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id, is_active) VALUES (1208326364860, NULL, 'a', 'aa', '2008-04-16', 5.00, '65464564', '5454654', 21, NULL, NULL, NULL, 48, true);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id, is_active) VALUES (1209324665385, NULL, NULL, 'dfsdfsdf', NULL, NULL, NULL, NULL, NULL, NULL, 1209324665385.00, NULL, NULL, true);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id, is_active) VALUES (1209394438429, 'sdfcsdfsd', 'IBM inc', 'IBM', '2008-04-18', 3000000000.00, NULL, '32423453464erg', 28, NULL, 1209582114840.00, NULL, 48, true);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id, is_active) VALUES (1209222047860, NULL, NULL, 'SmartMinds', '2008-04-09', 9.00, NULL, '99', 22, NULL, 1208326364860.00, NULL, 48, true);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id, is_active) VALUES (1211483549670, NULL, NULL, 'asdfsdf', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id, is_active) VALUES (1212035635016, NULL, NULL, 'lkjj', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id, is_active) VALUES (1213514952057, NULL, NULL, 'tes org', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id, is_active) VALUES (1216474970646, NULL, NULL, 'Opel', '2008-07-19', 300000000.00, NULL, '44552557686', 28, NULL, NULL, NULL, NULL, false);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id, is_active) VALUES (1216480868532, NULL, NULL, 'Moskvich', '2008-07-09', 32.00, NULL, '565465653646', 32, NULL, NULL, NULL, NULL, false);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id, is_active) VALUES (1217345408763, NULL, NULL, 'Provider of Stocks', NULL, NULL, NULL, NULL, 23, NULL, NULL, NULL, NULL, false);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id, is_active) VALUES (1217345422152, NULL, NULL, 'Provider of Wood', NULL, NULL, NULL, NULL, 28, NULL, NULL, NULL, NULL, false);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id, is_active) VALUES (1217407278985, NULL, NULL, 'NewRegOrg', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id, is_active) VALUES (1217409372641, NULL, NULL, 'testNewRegOrg', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id, is_active) VALUES (1217410126469, NULL, NULL, 'alabalala', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id, is_active) VALUES (1217410631001, NULL, NULL, 'againtest', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false);
INSERT INTO organizations (organization_id, description, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, currency_id, is_active) VALUES (1217513786510, NULL, 'GU', 'G-U', NULL, NULL, NULL, NULL, 28, NULL, NULL, NULL, NULL, false);


--
-- TOC entry 2396 (class 0 OID 36841)
-- Dependencies: 1646
-- Data for Name: passports; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO passports (passport_id, parent_id, passport_type_id, passport_number, issue_date, expiration_date, issuer_id, issuer_branch_id, additional_info) VALUES (1208723817938, 1206833877407, 59, '4532', '2008-04-20', '2008-04-18', 1208326364860, 1208375541782, 'ja');
INSERT INTO passports (passport_id, parent_id, passport_type_id, passport_number, issue_date, expiration_date, issuer_id, issuer_branch_id, additional_info) VALUES (1211747457079, 1211472878364, 59, '1234', '2008-05-27', '2008-05-31', 1209222047860, 1209222423971, NULL);
INSERT INTO passports (passport_id, parent_id, passport_type_id, passport_number, issue_date, expiration_date, issuer_id, issuer_branch_id, additional_info) VALUES (1212005439127, 1211472878364, 59, '7777j', '2008-05-02', '2008-05-04', 1209394438429, 1210419278241, NULL);


--
-- TOC entry 2418 (class 0 OID 38338)
-- Dependencies: 1679
-- Data for Name: pattern_mask_formats; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO pattern_mask_formats (pattern_mask_format_id, description, format, format_type, parent_id, pattern_name, owner_id) VALUES (1217345869202.00, 'Kat', '## #### ##', '-', 1209222047860.00, 'Registration Plate BG', 1217345444119.00);
INSERT INTO pattern_mask_formats (pattern_mask_format_id, description, format, format_type, parent_id, pattern_name, owner_id) VALUES (1217345903966.00, NULL, '# # # #', '-', 1209222047860.00, 'Spaced', NULL);


--
-- TOC entry 2397 (class 0 OID 36850)
-- Dependencies: 1647
-- Data for Name: persons; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1206910833184, NULL, NULL, NULL, 'S', 'N', NULL, NULL, NULL, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1206862211011, NULL, NULL, NULL, 'Miroslav', 'Nachev', NULL, NULL, 19, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1206833877407, NULL, NULL, NULL, 'aborigen', 'aborigenov', NULL, NULL, 19, 1209062694832, 5);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1210418654556, '2008-05-21', NULL, 'ename', 'fname', 'lname', '54545', 'sname', NULL, NULL, 6);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1210419431552, NULL, NULL, NULL, 'asdf', 'asdf', NULL, NULL, NULL, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1210505228236, NULL, NULL, NULL, 'asdf', 'asdf', NULL, NULL, NULL, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1211472752091, NULL, NULL, NULL, 'Hans ', 'Van Nispen', NULL, NULL, 20, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1211472798404, NULL, NULL, 'BG MAN', 'Bai', 'Ganio', NULL, NULL, NULL, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1211472878364, NULL, NULL, 'Hairy A** Man', 'Edwin', 'Poot', NULL, 'R.', NULL, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1213474710565, NULL, NULL, 'gxnst', '9alc4', 'ln608', NULL, 'r5s6h', NULL, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1213474965974, NULL, NULL, 't2yva', '5f95w', 'jngwt', NULL, 'g6171', NULL, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1213476155246, NULL, NULL, '5r2bb', '1pupr', 'fgbuz', NULL, 'rggdd', NULL, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1213480193688, NULL, NULL, 'dm3kc', '3kpzz', '4otcc', NULL, 'vqq7t', NULL, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1211482143869, NULL, NULL, NULL, 'M', 'N', NULL, NULL, 19, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1209582459571, '2008-04-26', 'Golema maimuna', 'The King', 'King', 'Kong', 'kk1', 'Monkey', 19, 1209062694832, 5);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1213963345878, NULL, NULL, NULL, 'Daniel', 'Velev', NULL, NULL, NULL, 1213963338689, 5);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1214287428969, NULL, NULL, NULL, 'Marco', 'Basten', NULL, 'Van', 19, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1209582555898, NULL, NULL, NULL, 'KKO', 'LPPP', NULL, NULL, NULL, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1214803184453, NULL, NULL, NULL, 'Radoslav', 'Lozanov', '7912081787', 'Mirchev', 19, 1214803174550, 5);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1214828587594, NULL, NULL, NULL, 'asdf', 'fda', NULL, NULL, NULL, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1217070771135, NULL, NULL, NULL, 'Petar', 'Milev', NULL, NULL, 19, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1211475930558, NULL, NULL, NULL, 'Pesho', 'Milev', NULL, NULL, NULL, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1213512206579, NULL, NULL, NULL, 'Yamanja', 'Jumaikova', NULL, 'Bongo-Bongo', 19, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1217345444119, NULL, NULL, NULL, 'Provider', 'Medov', NULL, NULL, 19, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1217514926610, NULL, NULL, 'Bozho', 'bozhidar', 'Bozhanov', NULL, 'Plamenov', NULL, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1217540926938, NULL, NULL, '', 'ime', 'familiya', NULL, 'prezime', NULL, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1217623255938, NULL, NULL, 'Usernameche', 'ime', 'familiya', NULL, 'prezime', NULL, NULL, NULL);
INSERT INTO persons (partner_id, birth_date, description, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id) VALUES (1217623859251, NULL, NULL, '', 'Martin', 'Heisterkampf', NULL, 'alabala', NULL, NULL, NULL);


--
-- TOC entry 2398 (class 0 OID 36856)
-- Dependencies: 1648
-- Data for Name: position_types; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO position_types (position_type_id, parent_id, owner_type, position_type_name, description, parent_position_type_id, is_internal, user_group_id) VALUES (1215336683064, 1209222047860, 'O', 'Cheater', NULL, NULL, false, NULL);
INSERT INTO position_types (position_type_id, parent_id, owner_type, position_type_name, description, parent_position_type_id, is_internal, user_group_id) VALUES (1215336678235, 1209222047860, 'O', 'Bossy', 'a', NULL, false, NULL);
INSERT INTO position_types (position_type_id, parent_id, owner_type, position_type_name, description, parent_position_type_id, is_internal, user_group_id) VALUES (1209325564201, NULL, 'P', 'Р‘Р°С‰Р°С‚Р°', NULL, NULL, false, NULL);
INSERT INTO position_types (position_type_id, parent_id, owner_type, position_type_name, description, parent_position_type_id, is_internal, user_group_id) VALUES (1208927516422, NULL, 'P', 'Brother', NULL, NULL, false, NULL);
INSERT INTO position_types (position_type_id, parent_id, owner_type, position_type_name, description, parent_position_type_id, is_internal, user_group_id) VALUES (1208929402376, NULL, 'P', 'Sister', NULL, NULL, false, NULL);
INSERT INTO position_types (position_type_id, parent_id, owner_type, position_type_name, description, parent_position_type_id, is_internal, user_group_id) VALUES (1208597113782, NULL, 'O', 'Cleaner', NULL, NULL, false, NULL);
INSERT INTO position_types (position_type_id, parent_id, owner_type, position_type_name, description, parent_position_type_id, is_internal, user_group_id) VALUES (1208595267969, NULL, 'O', 'Boss', NULL, NULL, false, NULL);
INSERT INTO position_types (position_type_id, parent_id, owner_type, position_type_name, description, parent_position_type_id, is_internal, user_group_id) VALUES (1215340892641, 1209222047860, 'O', 'Chief', NULL, NULL, true, NULL);
INSERT INTO position_types (position_type_id, parent_id, owner_type, position_type_name, description, parent_position_type_id, is_internal, user_group_id) VALUES (1215342581282, 1209222047860, 'O', 'subchief', NULL, 1215340892641, true, NULL);
INSERT INTO position_types (position_type_id, parent_id, owner_type, position_type_name, description, parent_position_type_id, is_internal, user_group_id) VALUES (1215368591157, 1209222047860, 'O', 'subsubchief', NULL, 1215369537657, true, NULL);
INSERT INTO position_types (position_type_id, parent_id, owner_type, position_type_name, description, parent_position_type_id, is_internal, user_group_id) VALUES (1215369537657, 1209222047860, 'O', 'asdfas', NULL, 1215342581282, true, NULL);
INSERT INTO position_types (position_type_id, parent_id, owner_type, position_type_name, description, parent_position_type_id, is_internal, user_group_id) VALUES (1215451052064, 1209222047860, 'O', 'TL', NULL, NULL, false, NULL);
INSERT INTO position_types (position_type_id, parent_id, owner_type, position_type_name, description, parent_position_type_id, is_internal, user_group_id) VALUES (1215862243512, NULL, 'O', 'wbdz67plf621ihhj', NULL, NULL, false, NULL);
INSERT INTO position_types (position_type_id, parent_id, owner_type, position_type_name, description, parent_position_type_id, is_internal, user_group_id) VALUES (1215862317575, NULL, 'O', '60y8va91b5s6amlp', NULL, NULL, false, NULL);
INSERT INTO position_types (position_type_id, parent_id, owner_type, position_type_name, description, parent_position_type_id, is_internal, user_group_id) VALUES (1215862348420, NULL, 'O', 'efgfio2hmyi5n87k', NULL, NULL, false, NULL);
INSERT INTO position_types (position_type_id, parent_id, owner_type, position_type_name, description, parent_position_type_id, is_internal, user_group_id) VALUES (1217513829280, 1209222047860, 'O', 'Sales Manager', NULL, NULL, false, NULL);


--
-- TOC entry 2399 (class 0 OID 36863)
-- Dependencies: 1649
-- Data for Name: product_categories; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO product_categories (product_category_id, parent_id, category_name, description, parent_cat_id, pattern_mask_format_id) VALUES (1210702269535, NULL, '111', NULL, 1209288962569.00, NULL);
INSERT INTO product_categories (product_category_id, parent_id, category_name, description, parent_cat_id, pattern_mask_format_id) VALUES (1210702277197, NULL, '222', NULL, 1209288962569.00, NULL);
INSERT INTO product_categories (product_category_id, parent_id, category_name, description, parent_cat_id, pattern_mask_format_id) VALUES (1210702289908, NULL, '444', NULL, 1209288962569.00, NULL);
INSERT INTO product_categories (product_category_id, parent_id, category_name, description, parent_cat_id, pattern_mask_format_id) VALUES (1210702309820, NULL, '777', NULL, 1209288962569.00, NULL);
INSERT INTO product_categories (product_category_id, parent_id, category_name, description, parent_cat_id, pattern_mask_format_id) VALUES (1210702323310, NULL, '999', NULL, 1209288962569.00, NULL);
INSERT INTO product_categories (product_category_id, parent_id, category_name, description, parent_cat_id, pattern_mask_format_id) VALUES (1210702341065, NULL, '123', NULL, 1210702269535.00, NULL);
INSERT INTO product_categories (product_category_id, parent_id, category_name, description, parent_cat_id, pattern_mask_format_id) VALUES (1210702350546, NULL, '234', NULL, 1210702341065.00, NULL);
INSERT INTO product_categories (product_category_id, parent_id, category_name, description, parent_cat_id, pattern_mask_format_id) VALUES (1210702358576, NULL, '345', NULL, 1210702350546.00, NULL);
INSERT INTO product_categories (product_category_id, parent_id, category_name, description, parent_cat_id, pattern_mask_format_id) VALUES (1210702302770, NULL, '66666666666666666666666666666666666666666666666', NULL, 1209288962569.00, NULL);
INSERT INTO product_categories (product_category_id, parent_id, category_name, description, parent_cat_id, pattern_mask_format_id) VALUES (1210702297698, NULL, '555', NULL, 1210702358576.00, NULL);
INSERT INTO product_categories (product_category_id, parent_id, category_name, description, parent_cat_id, pattern_mask_format_id) VALUES (1210702283927, NULL, '333', NULL, 1208867495266.00, NULL);
INSERT INTO product_categories (product_category_id, parent_id, category_name, description, parent_cat_id, pattern_mask_format_id) VALUES (1210702318119, NULL, '888', NULL, 1208867495266.00, NULL);
INSERT INTO product_categories (product_category_id, parent_id, category_name, description, parent_cat_id, pattern_mask_format_id) VALUES (1210519124507, NULL, 'Long Scrap', 'Very long,
Strenght 10', 1210702297698.00, NULL);
INSERT INTO product_categories (product_category_id, parent_id, category_name, description, parent_cat_id, pattern_mask_format_id) VALUES (1209407225556, NULL, 'Ice-Cream', 'Haho hihie
obalaa', 1210513038084.00, NULL);
INSERT INTO product_categories (product_category_id, parent_id, category_name, description, parent_cat_id, pattern_mask_format_id) VALUES (1208867495266, NULL, 'Hi Tech Goods', NULL, NULL, NULL);
INSERT INTO product_categories (product_category_id, parent_id, category_name, description, parent_cat_id, pattern_mask_format_id) VALUES (1210620273286, NULL, 'For Fridge', NULL, NULL, NULL);
INSERT INTO product_categories (product_category_id, parent_id, category_name, description, parent_cat_id, pattern_mask_format_id) VALUES (1210620471938, NULL, 'Strawberry icecream', 'good taste', 1209407225556.00, NULL);
INSERT INTO product_categories (product_category_id, parent_id, category_name, description, parent_cat_id, pattern_mask_format_id) VALUES (1210620499609, NULL, 'Caramel with chocolate', NULL, 1209407225556.00, NULL);
INSERT INTO product_categories (product_category_id, parent_id, category_name, description, parent_cat_id, pattern_mask_format_id) VALUES (1, NULL, 'Laser Gun', 'very powerful, but dangerous', 1208867495266.00, NULL);
INSERT INTO product_categories (product_category_id, parent_id, category_name, description, parent_cat_id, pattern_mask_format_id) VALUES (1210518068080, NULL, 'Coca Cola', NULL, 1210620733627.00, NULL);
INSERT INTO product_categories (product_category_id, parent_id, category_name, description, parent_cat_id, pattern_mask_format_id) VALUES (1209403563985, NULL, 'Pinacolada', NULL, 1210620733627.00, NULL);
INSERT INTO product_categories (product_category_id, parent_id, category_name, description, parent_cat_id, pattern_mask_format_id) VALUES (1208791743432, NULL, 'Limonade', NULL, 1210620733627.00, NULL);
INSERT INTO product_categories (product_category_id, parent_id, category_name, description, parent_cat_id, pattern_mask_format_id) VALUES (1209407251282, NULL, 'Food And Drinks', 'description cat form x', 1210620273286.00, NULL);
INSERT INTO product_categories (product_category_id, parent_id, category_name, description, parent_cat_id, pattern_mask_format_id) VALUES (1210620733627, NULL, 'Drinks', 'all that can be drinked', 1209407251282.00, NULL);
INSERT INTO product_categories (product_category_id, parent_id, category_name, description, parent_cat_id, pattern_mask_format_id) VALUES (1210621181773, NULL, 'Fast food', NULL, 1209407251282.00, NULL);
INSERT INTO product_categories (product_category_id, parent_id, category_name, description, parent_cat_id, pattern_mask_format_id) VALUES (1210513038084, NULL, 'Fried chicken', NULL, 1210621181773.00, NULL);
INSERT INTO product_categories (product_category_id, parent_id, category_name, description, parent_cat_id, pattern_mask_format_id) VALUES (1209288962569, NULL, 'Hiking', NULL, NULL, 1217345903966.00);


--
-- TOC entry 2400 (class 0 OID 36869)
-- Dependencies: 1650
-- Data for Name: product_suppliers; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2401 (class 0 OID 36875)
-- Dependencies: 1651
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
INSERT INTO products (product_id, parent_id, product_type) VALUES (1217063155346, 1209222047860, NULL);
INSERT INTO products (product_id, parent_id, product_type) VALUES (1217068585269, 1209222047860, NULL);


--
-- TOC entry 2402 (class 0 OID 36878)
-- Dependencies: 1652
-- Data for Name: purchase_order_items; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO purchase_order_items (order_item_id, parent_id, product_id, measure_unit_id, ordered_quantity, confirmed_quantity, delivered_quantity, purchase_price, ship_date, currency_id, notes, ship_date_from, ship_date_to) VALUES (1217071696711, 1217071630343, 1217063155346, 1, 14.0000, NULL, NULL, 100.0000, NULL, 49, NULL, NULL, NULL);
INSERT INTO purchase_order_items (order_item_id, parent_id, product_id, measure_unit_id, ordered_quantity, confirmed_quantity, delivered_quantity, purchase_price, ship_date, currency_id, notes, ship_date_from, ship_date_to) VALUES (1217071726740, 1217071630343, 1217068585269, 13, 60.0000, NULL, NULL, 100.0000, NULL, 49, NULL, NULL, NULL);
INSERT INTO purchase_order_items (order_item_id, parent_id, product_id, measure_unit_id, ordered_quantity, confirmed_quantity, delivered_quantity, purchase_price, ship_date, currency_id, notes, ship_date_from, ship_date_to) VALUES (1217071781890, 1217071772215, 1217068585269, 1, 250.0000, NULL, NULL, 100.0000, NULL, 49, NULL, NULL, NULL);
INSERT INTO purchase_order_items (order_item_id, parent_id, product_id, measure_unit_id, ordered_quantity, confirmed_quantity, delivered_quantity, purchase_price, ship_date, currency_id, notes, ship_date_from, ship_date_to) VALUES (1217071819011, 1217071803066, 1217068585269, 1, 10.0000, NULL, NULL, 100.0000, NULL, 49, NULL, NULL, NULL);
INSERT INTO purchase_order_items (order_item_id, parent_id, product_id, measure_unit_id, ordered_quantity, confirmed_quantity, delivered_quantity, purchase_price, ship_date, currency_id, notes, ship_date_from, ship_date_to) VALUES (1217071819181, 1217071803066, 1217063155346, 5, 40.0000, NULL, NULL, 100.0000, NULL, 49, NULL, NULL, NULL);
INSERT INTO purchase_order_items (order_item_id, parent_id, product_id, measure_unit_id, ordered_quantity, confirmed_quantity, delivered_quantity, purchase_price, ship_date, currency_id, notes, ship_date_from, ship_date_to) VALUES (1217071946277, 1217071919490, 1217063155346, 1, 12.0000, NULL, NULL, 100.0000, NULL, 49, NULL, NULL, NULL);
INSERT INTO purchase_order_items (order_item_id, parent_id, product_id, measure_unit_id, ordered_quantity, confirmed_quantity, delivered_quantity, purchase_price, ship_date, currency_id, notes, ship_date_from, ship_date_to) VALUES (1217072462563, 1217072449079, 1217063155346, 1, 67.0000, NULL, NULL, 100.0000, NULL, 49, NULL, NULL, NULL);
INSERT INTO purchase_order_items (order_item_id, parent_id, product_id, measure_unit_id, ordered_quantity, confirmed_quantity, delivered_quantity, purchase_price, ship_date, currency_id, notes, ship_date_from, ship_date_to) VALUES (1217073894084, 1217073858272, 1217063155346, 1, 23.0000, NULL, NULL, 100.0000, NULL, 49, NULL, NULL, NULL);
INSERT INTO purchase_order_items (order_item_id, parent_id, product_id, measure_unit_id, ordered_quantity, confirmed_quantity, delivered_quantity, purchase_price, ship_date, currency_id, notes, ship_date_from, ship_date_to) VALUES (1217074761612, 1217074744177, 1217068585269, 1, 5.0000, NULL, NULL, 100.0000, NULL, 49, NULL, NULL, NULL);
INSERT INTO purchase_order_items (order_item_id, parent_id, product_id, measure_unit_id, ordered_quantity, confirmed_quantity, delivered_quantity, purchase_price, ship_date, currency_id, notes, ship_date_from, ship_date_to) VALUES (1217075242011, 1217075219686, 1217063155346, 1, 55.0000, NULL, NULL, 100.0000, NULL, 49, NULL, NULL, NULL);
INSERT INTO purchase_order_items (order_item_id, parent_id, product_id, measure_unit_id, ordered_quantity, confirmed_quantity, delivered_quantity, purchase_price, ship_date, currency_id, notes, ship_date_from, ship_date_to) VALUES (1217075259518, 1217075219686, 1217068585269, 1, 45.0000, NULL, NULL, 100.0000, NULL, 49, NULL, NULL, NULL);
INSERT INTO purchase_order_items (order_item_id, parent_id, product_id, measure_unit_id, ordered_quantity, confirmed_quantity, delivered_quantity, purchase_price, ship_date, currency_id, notes, ship_date_from, ship_date_to) VALUES (1217075500165, 1217075487662, 1217063155346, 1, 1.0000, NULL, NULL, 100.0000, NULL, 49, NULL, NULL, NULL);
INSERT INTO purchase_order_items (order_item_id, parent_id, product_id, measure_unit_id, ordered_quantity, confirmed_quantity, delivered_quantity, purchase_price, ship_date, currency_id, notes, ship_date_from, ship_date_to) VALUES (1217075848024, 1217075836623, 1217063155346, 1, 7.0000, NULL, NULL, 100.0000, NULL, 49, NULL, NULL, NULL);
INSERT INTO purchase_order_items (order_item_id, parent_id, product_id, measure_unit_id, ordered_quantity, confirmed_quantity, delivered_quantity, purchase_price, ship_date, currency_id, notes, ship_date_from, ship_date_to) VALUES (1217076171707, 1217076157491, 1217063155346, 1, 34.0000, NULL, NULL, 100.0000, NULL, 49, NULL, NULL, NULL);
INSERT INTO purchase_order_items (order_item_id, parent_id, product_id, measure_unit_id, ordered_quantity, confirmed_quantity, delivered_quantity, purchase_price, ship_date, currency_id, notes, ship_date_from, ship_date_to) VALUES (1217076433070, 1217076418362, 1217063155346, 1, 4.0000, NULL, NULL, 100.0000, NULL, 49, NULL, NULL, NULL);
INSERT INTO purchase_order_items (order_item_id, parent_id, product_id, measure_unit_id, ordered_quantity, confirmed_quantity, delivered_quantity, purchase_price, ship_date, currency_id, notes, ship_date_from, ship_date_to) VALUES (1217077226599, 1217077206751, 1217068585269, 12, 10.0000, NULL, NULL, 100.0000, NULL, 49, NULL, NULL, NULL);
INSERT INTO purchase_order_items (order_item_id, parent_id, product_id, measure_unit_id, ordered_quantity, confirmed_quantity, delivered_quantity, purchase_price, ship_date, currency_id, notes, ship_date_from, ship_date_to) VALUES (1217077226429, 1217077206751, 1217063155346, 12, 20.0000, 5.0000, NULL, 100.0000, NULL, 49, NULL, NULL, NULL);
INSERT INTO purchase_order_items (order_item_id, parent_id, product_id, measure_unit_id, ordered_quantity, confirmed_quantity, delivered_quantity, purchase_price, ship_date, currency_id, notes, ship_date_from, ship_date_to) VALUES (1217078443144, 1217078416388, 1217063155346, 2, 3.0000, NULL, NULL, 100.0000, NULL, 49, NULL, NULL, NULL);
INSERT INTO purchase_order_items (order_item_id, parent_id, product_id, measure_unit_id, ordered_quantity, confirmed_quantity, delivered_quantity, purchase_price, ship_date, currency_id, notes, ship_date_from, ship_date_to) VALUES (1217513915835, 1217513856396, 1217068585269, 14, 100.0000, NULL, NULL, 100.0000, NULL, 49, NULL, NULL, NULL);
INSERT INTO purchase_order_items (order_item_id, parent_id, product_id, measure_unit_id, ordered_quantity, confirmed_quantity, delivered_quantity, purchase_price, ship_date, currency_id, notes, ship_date_from, ship_date_to) VALUES (1217513887379, 1217513856396, 1217063155346, 15, 150.0000, 30.0000, NULL, 100.0000, NULL, 49, NULL, NULL, NULL);


--
-- TOC entry 2403 (class 0 OID 36881)
-- Dependencies: 1653
-- Data for Name: purchase_orders; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO purchase_orders (purchase_order_id, branch_name, creation_time, creator_name, finalizing_time, first_delivery_time, last_delivery_time, notes, order_number, parent_id, sender_name, sent_time, supplier_contact_name, supplier_name, supplier_order_number, sender_id, supplier_contact_id, creator_id, doc_delivery_method_id, supplier_partner_id, status_id, branch_id) VALUES (1217071630343.00, 'Holland, Hilversum', '2008-07-26', 'Edwin R. Poot Hairy A** Man', NULL, NULL, NULL, NULL, 1000000000.00, 1209222047860.00, 'Edwin R. Poot Hairy A** Man', '2008-07-26', 'Daniel  Velev ', 'IBM', 1.00, 1211472878364.00, 1215451068003.00, 1211472878364.00, 116, 1209394438429.00, 135, 1209222423971.00);
INSERT INTO purchase_orders (purchase_order_id, branch_name, creation_time, creator_name, finalizing_time, first_delivery_time, last_delivery_time, notes, order_number, parent_id, sender_name, sent_time, supplier_contact_name, supplier_name, supplier_order_number, sender_id, supplier_contact_id, creator_id, doc_delivery_method_id, supplier_partner_id, status_id, branch_id) VALUES (1217071772215.00, 'Holland, Hilversum', '2008-07-26', 'Edwin R. Poot Hairy A** Man', NULL, NULL, NULL, NULL, 1000000001.00, 1209222047860.00, 'Edwin R. Poot Hairy A** Man', '2008-07-26', 'Radoslav Mirchev Lozanov ', 'IBM', 2.00, 1211472878364.00, 1214803187723.00, 1211472878364.00, 116, 1209394438429.00, 135, 1209222423971.00);
INSERT INTO purchase_orders (purchase_order_id, branch_name, creation_time, creator_name, finalizing_time, first_delivery_time, last_delivery_time, notes, order_number, parent_id, sender_name, sent_time, supplier_contact_name, supplier_name, supplier_order_number, sender_id, supplier_contact_id, creator_id, doc_delivery_method_id, supplier_partner_id, status_id, branch_id) VALUES (1217071803066.00, 'Holland, Hilversum', '2008-07-26', 'Edwin R. Poot Hairy A** Man', NULL, NULL, NULL, 'waiting for this products', 1000000002.00, 1209222047860.00, NULL, NULL, 'Radoslav Mirchev Lozanov ', 'IBM', 3.00, NULL, 1214803187723.00, 1211472878364.00, 115, 1209394438429.00, 134, 1209222423971.00);
INSERT INTO purchase_orders (purchase_order_id, branch_name, creation_time, creator_name, finalizing_time, first_delivery_time, last_delivery_time, notes, order_number, parent_id, sender_name, sent_time, supplier_contact_name, supplier_name, supplier_order_number, sender_id, supplier_contact_id, creator_id, doc_delivery_method_id, supplier_partner_id, status_id, branch_id) VALUES (1217071919490.00, 'Holland, Hilversum', '2008-07-26', 'Edwin R. Poot Hairy A** Man', NULL, NULL, NULL, NULL, 1000000003.00, 1209222047860.00, NULL, NULL, 'Yamanja Bongo-Bongo Jumaikova ', 'Yamaha', 1.00, NULL, 1217071164250.00, 1211472878364.00, 110, 1209582114840.00, 134, 1209222423971.00);
INSERT INTO purchase_orders (purchase_order_id, branch_name, creation_time, creator_name, finalizing_time, first_delivery_time, last_delivery_time, notes, order_number, parent_id, sender_name, sent_time, supplier_contact_name, supplier_name, supplier_order_number, sender_id, supplier_contact_id, creator_id, doc_delivery_method_id, supplier_partner_id, status_id, branch_id) VALUES (1217072449079.00, 'Holland, Hilversum', '2008-07-26', 'Edwin R. Poot Hairy A** Man', NULL, NULL, NULL, NULL, 1000000004.00, 1209222047860.00, NULL, NULL, 'Yamanja Bongo-Bongo Jumaikova ', 'Yamaha', 2.00, NULL, 1217071164250.00, 1211472878364.00, 116, 1209582114840.00, 134, 1209222423971.00);
INSERT INTO purchase_orders (purchase_order_id, branch_name, creation_time, creator_name, finalizing_time, first_delivery_time, last_delivery_time, notes, order_number, parent_id, sender_name, sent_time, supplier_contact_name, supplier_name, supplier_order_number, sender_id, supplier_contact_id, creator_id, doc_delivery_method_id, supplier_partner_id, status_id, branch_id) VALUES (1217073858272.00, 'Holland, Hilversum', '2008-07-26', 'Edwin R. Poot Hairy A** Man', NULL, NULL, NULL, NULL, 1000000005.00, 1209222047860.00, NULL, NULL, 'Hans   Van Nispen ', 'SmartMinds', 1.00, NULL, 1211472755765.00, 1211472878364.00, 116, 1209222047860.00, 134, 1209222423971.00);
INSERT INTO purchase_orders (purchase_order_id, branch_name, creation_time, creator_name, finalizing_time, first_delivery_time, last_delivery_time, notes, order_number, parent_id, sender_name, sent_time, supplier_contact_name, supplier_name, supplier_order_number, sender_id, supplier_contact_id, creator_id, doc_delivery_method_id, supplier_partner_id, status_id, branch_id) VALUES (1217074358953.00, 'Holland, Hilversum', '2008-07-26', 'Edwin R. Poot Hairy A** Man', NULL, NULL, NULL, NULL, 1000000006.00, 1209222047860.00, NULL, NULL, 'Daniel  Velev ', 'IBM', 4.00, NULL, 1215451068003.00, 1211472878364.00, 111, 1209394438429.00, 134, 1209222423971.00);
INSERT INTO purchase_orders (purchase_order_id, branch_name, creation_time, creator_name, finalizing_time, first_delivery_time, last_delivery_time, notes, order_number, parent_id, sender_name, sent_time, supplier_contact_name, supplier_name, supplier_order_number, sender_id, supplier_contact_id, creator_id, doc_delivery_method_id, supplier_partner_id, status_id, branch_id) VALUES (1217074744177.00, 'Holland, Hilversum', '2008-07-26', 'Edwin R. Poot Hairy A** Man', NULL, NULL, NULL, NULL, 1000000007.00, 1209222047860.00, NULL, NULL, 'Yamanja Bongo-Bongo Jumaikova ', 'Yamaha', 3.00, NULL, 1217071164250.00, 1211472878364.00, 113, 1209582114840.00, 134, 1209222423971.00);
INSERT INTO purchase_orders (purchase_order_id, branch_name, creation_time, creator_name, finalizing_time, first_delivery_time, last_delivery_time, notes, order_number, parent_id, sender_name, sent_time, supplier_contact_name, supplier_name, supplier_order_number, sender_id, supplier_contact_id, creator_id, doc_delivery_method_id, supplier_partner_id, status_id, branch_id) VALUES (1217075219686.00, 'Holland, Hilversum', '2008-07-26', 'Edwin R. Poot Hairy A** Man', NULL, NULL, NULL, NULL, 1000000008.00, 1209222047860.00, NULL, NULL, 'Yamanja Bongo-Bongo Jumaikova ', 'Yamaha', 4.00, NULL, 1217071164250.00, 1211472878364.00, 116, 1209582114840.00, 134, 1209222423971.00);
INSERT INTO purchase_orders (purchase_order_id, branch_name, creation_time, creator_name, finalizing_time, first_delivery_time, last_delivery_time, notes, order_number, parent_id, sender_name, sent_time, supplier_contact_name, supplier_name, supplier_order_number, sender_id, supplier_contact_id, creator_id, doc_delivery_method_id, supplier_partner_id, status_id, branch_id) VALUES (1217075487662.00, 'Holland, Hilversum', '2008-07-26', 'Edwin R. Poot Hairy A** Man', NULL, NULL, NULL, NULL, 1000000009.00, 1209222047860.00, NULL, NULL, 'Yamanja Bongo-Bongo Jumaikova ', 'Yamaha', 5.00, NULL, 1217071164250.00, 1211472878364.00, 114, 1209582114840.00, 134, 1209222423971.00);
INSERT INTO purchase_orders (purchase_order_id, branch_name, creation_time, creator_name, finalizing_time, first_delivery_time, last_delivery_time, notes, order_number, parent_id, sender_name, sent_time, supplier_contact_name, supplier_name, supplier_order_number, sender_id, supplier_contact_id, creator_id, doc_delivery_method_id, supplier_partner_id, status_id, branch_id) VALUES (1217075836623.00, 'Holland, Hilversum', '2008-07-26', 'Edwin R. Poot Hairy A** Man', NULL, NULL, NULL, NULL, 1000000010.00, 1209222047860.00, NULL, NULL, 'Yamanja Bongo-Bongo Jumaikova ', 'Yamaha', 6.00, NULL, 1217071164250.00, 1211472878364.00, 113, 1209582114840.00, 134, 1209222423971.00);
INSERT INTO purchase_orders (purchase_order_id, branch_name, creation_time, creator_name, finalizing_time, first_delivery_time, last_delivery_time, notes, order_number, parent_id, sender_name, sent_time, supplier_contact_name, supplier_name, supplier_order_number, sender_id, supplier_contact_id, creator_id, doc_delivery_method_id, supplier_partner_id, status_id, branch_id) VALUES (1217076157491.00, 'Holland, Hilversum', '2008-07-26', 'Edwin R. Poot Hairy A** Man', NULL, NULL, NULL, NULL, 1000000011.00, 1209222047860.00, NULL, NULL, 'Yamanja Bongo-Bongo Jumaikova ', 'Yamaha', 7.00, NULL, 1217071164250.00, 1211472878364.00, 116, 1209582114840.00, 134, 1209222423971.00);
INSERT INTO purchase_orders (purchase_order_id, branch_name, creation_time, creator_name, finalizing_time, first_delivery_time, last_delivery_time, notes, order_number, parent_id, sender_name, sent_time, supplier_contact_name, supplier_name, supplier_order_number, sender_id, supplier_contact_id, creator_id, doc_delivery_method_id, supplier_partner_id, status_id, branch_id) VALUES (1217076418362.00, 'Holland, Hilversum', '2008-07-26', 'Edwin R. Poot Hairy A** Man', NULL, NULL, NULL, 'gfgfgf', 1000000012.00, 1209222047860.00, NULL, NULL, 'Daniel  Velev ', 'IBM', 5.00, NULL, 1215451068003.00, 1211472878364.00, 115, 1209394438429.00, 134, 1209222423971.00);
INSERT INTO purchase_orders (purchase_order_id, branch_name, creation_time, creator_name, finalizing_time, first_delivery_time, last_delivery_time, notes, order_number, parent_id, sender_name, sent_time, supplier_contact_name, supplier_name, supplier_order_number, sender_id, supplier_contact_id, creator_id, doc_delivery_method_id, supplier_partner_id, status_id, branch_id) VALUES (1217077206751.00, 'Sofia Centur', '2008-07-26', 'Pesho  Milev ', NULL, NULL, NULL, 'blabla', 2000000000.00, 1209222047860.00, 'Pesho  Milev ', '2008-07-26', 'Yamanja Bongo-Bongo Jumaikova ', 'Yamaha', 8.00, 1211475930558.00, 1217071164250.00, 1211475930558.00, 117, 1209582114840.00, 137, 1209222409189.00);
INSERT INTO purchase_orders (purchase_order_id, branch_name, creation_time, creator_name, finalizing_time, first_delivery_time, last_delivery_time, notes, order_number, parent_id, sender_name, sent_time, supplier_contact_name, supplier_name, supplier_order_number, sender_id, supplier_contact_id, creator_id, doc_delivery_method_id, supplier_partner_id, status_id, branch_id) VALUES (1217078416388.00, 'Sofia Centur', '2008-07-26', 'Pesho  Milev ', NULL, NULL, NULL, NULL, 2000000001.00, 1209222047860.00, NULL, NULL, 'aborigen  aborigenov ', 'SmartMinds', 2.00, NULL, 1211470998665.00, 1211475930558.00, 112, 1209222047860.00, 134, 1209222409189.00);
INSERT INTO purchase_orders (purchase_order_id, branch_name, creation_time, creator_name, finalizing_time, first_delivery_time, last_delivery_time, notes, order_number, parent_id, sender_name, sent_time, supplier_contact_name, supplier_name, supplier_order_number, sender_id, supplier_contact_id, creator_id, doc_delivery_method_id, supplier_partner_id, status_id, branch_id) VALUES (1217345828141.00, 'Holland, Hilversum', '2008-07-29', 'Edwin R. Poot Hairy A** Man', NULL, NULL, NULL, NULL, 1000000013.00, 1209222047860.00, NULL, NULL, 'Hans   Van Nispen ', 'Provider of Stocks', 1.00, NULL, 1217345737392.00, 1211472878364.00, 116, 1217345408763.00, 134, 1209222423971.00);
INSERT INTO purchase_orders (purchase_order_id, branch_name, creation_time, creator_name, finalizing_time, first_delivery_time, last_delivery_time, notes, order_number, parent_id, sender_name, sent_time, supplier_contact_name, supplier_name, supplier_order_number, sender_id, supplier_contact_id, creator_id, doc_delivery_method_id, supplier_partner_id, status_id, branch_id) VALUES (1217513856396.00, 'Holland, Hilversum', '2008-07-31', 'Edwin R. Poot Hairy A** Man', NULL, NULL, NULL, NULL, 1000000014.00, 1209222047860.00, 'Edwin R. Poot Hairy A** Man', '2008-07-31', 'Marco Van Basten ', 'G-U', 1.00, 1211472878364.00, 1217513833603.00, 1211472878364.00, 116, 1217513786510.00, 137, 1209222423971.00);
INSERT INTO purchase_orders (purchase_order_id, branch_name, creation_time, creator_name, finalizing_time, first_delivery_time, last_delivery_time, notes, order_number, parent_id, sender_name, sent_time, supplier_contact_name, supplier_name, supplier_order_number, sender_id, supplier_contact_id, creator_id, doc_delivery_method_id, supplier_partner_id, status_id, branch_id) VALUES (1217765228385.00, 'Holland, Hilversum', '2008-08-03', 'Edwin R. Poot Hairy A** Man', NULL, NULL, NULL, NULL, 1000000015.00, 1209222047860.00, NULL, NULL, 'Marco Van Basten ', 'Provider of Stocks', 2.00, NULL, 1217345752103.00, 1211472878364.00, 116, 1217345408763.00, 134, 1209222423971.00);


--
-- TOC entry 2404 (class 0 OID 36887)
-- Dependencies: 1654
-- Data for Name: real_products; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2405 (class 0 OID 36890)
-- Dependencies: 1655
-- Data for Name: receipt_certificate_items; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2406 (class 0 OID 36893)
-- Dependencies: 1656
-- Data for Name: receipt_certificate_serial_numbers; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2407 (class 0 OID 36896)
-- Dependencies: 1657
-- Data for Name: receipt_certificates; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2408 (class 0 OID 36899)
-- Dependencies: 1658
-- Data for Name: registration_codes; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2409 (class 0 OID 36902)
-- Dependencies: 1659
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
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (125, 20, 'InPlace');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (126, 20, 'Forwarder');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (127, 21, 'Invoice');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (128, 21, 'ProformaInvoice');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (129, 21, 'Offer');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (130, 21, 'Test');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (131, 21, 'Replacement');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (132, 22, 'SpecialPermission1');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (133, 22, 'SpecialPermission2');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (134, 23, 'Open');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (135, 23, 'Sent');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (136, 23, 'Confirmed');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (137, 23, 'PartlyConfirmed');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (138, 23, 'PartlyDelivered');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (139, 23, 'Delivered');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (140, 23, 'Rejected');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (141, 23, 'Canceled');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (142, 24, 'VatInvoice');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (143, 24, 'SimpleInvoice');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (144, 24, 'DebitNoteInvoice');
INSERT INTO resource_bundle (resource_id, enum_class_id, enum_name) VALUES (145, 24, 'CretidNoteInvoice');


--
-- TOC entry 2410 (class 0 OID 36905)
-- Dependencies: 1660
-- Data for Name: sequence_identifiers; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2411 (class 0 OID 36909)
-- Dependencies: 1661
-- Data for Name: simple_products; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id, pattern_mask_format_id) VALUES (1205657267969, 1, 'Nescafe 2in1', 'E1400', 1, false, true, false, NULL, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id, pattern_mask_format_id) VALUES (1208532375657, 1, 'Monitor', '67567567', 1, false, true, false, NULL, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id, pattern_mask_format_id) VALUES (1208523315311, 1, 'Memory Stick', '555003', 1, false, true, false, 39, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id, pattern_mask_format_id) VALUES (1208532404521, 1, 'Calculator', 'yuiyu-=pui3ry3p8[', 1, false, true, false, NULL, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id, pattern_mask_format_id) VALUES (1208532445938, 1, 'Door with handle', 'ghjgk;opo[p][gf', 1, false, true, false, NULL, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id, pattern_mask_format_id) VALUES (1203863479289, 1, 'TV Tuner', 'pc2', 2, false, true, false, 40, 1.0000, 2.5460, 2.3230, 100.2000, 200.0000, 250.0000, 1, NULL, 7.55, 7.87, 34.43, NULL, 40.000, 3, 'blabla', NULL, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id, pattern_mask_format_id) VALUES (1203863458084, 1, '3in1', '66799033312', 1, false, true, false, NULL, 1.0000, 3.0000, 2.0000, 100.2000, 200.0000, 250.0000, 1, 16, 1.20, 2.30, 3.50, 8, 23.450, 12, 'My description', NULL, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id, pattern_mask_format_id) VALUES (1208532483793, 1, 'Toilet paper', 'oooops', 1, false, true, false, NULL, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id, pattern_mask_format_id) VALUES (1208532506415, 1, 'Shoes', '567567567', 1, false, true, false, NULL, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id, pattern_mask_format_id) VALUES (1208532601328, 1, 'Windows 98', 'bugggggsss', 1, false, true, false, NULL, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id, pattern_mask_format_id) VALUES (1208531981537, 1, 'Pizza, with onion', '01', 1, false, false, false, 40, 10.0000, 123123123124.0000, 20.0000, 423423423423.0000, 0.2342, 454545454545.0000, 1, 18, NULL, NULL, NULL, NULL, 8090909090.000, 453434534, 'PizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizzaPizza', 1208326364860, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id, pattern_mask_format_id) VALUES (1208532628130, 1, 'Camera Web Small', '0093343--3434', 1, false, true, false, NULL, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id, pattern_mask_format_id) VALUES (1208532654307, 1, 'Plant Artificial', '99223232', 1, false, true, false, NULL, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id, pattern_mask_format_id) VALUES (1208532716832, 1, 'Car, Opel Astra 85kW', 'CA3434CA', 1, false, true, false, NULL, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id, pattern_mask_format_id) VALUES (1208532361606, 1, 'Laptop', '456456456', 1, false, true, false, 39, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, 16, NULL, NULL, NULL, 8, NULL, NULL, NULL, 1206833877407, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id, pattern_mask_format_id) VALUES (1208532545822, 1, 'Box of wood', '988 dfdff 09809', 1, false, true, false, 40, 234523453453.0000, 999999999999.0000, 456456456456.0000, 345435.0000, 3456345.0000, 999999999999.0000, 345345, 16, 99999.00, 34535.00, 345.00, 7, 345345.000, 345, 'afjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjhafjkashdfjkhsdfjh', 1206862211011, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id, pattern_mask_format_id) VALUES (1208532574240, 1, 'Cables high quality', '0093434', 1, false, true, false, NULL, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id, pattern_mask_format_id) VALUES (1209313405837, 1, 'testpc', '3453', 1, true, true, false, NULL, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id, pattern_mask_format_id) VALUES (1205915841376, 1208867495266, 'Memory Card', 'p38', 1, false, true, false, NULL, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id, pattern_mask_format_id) VALUES (1208105700768, 1209403563985, 'TestovProduct1', '22334455', 1, true, true, true, 39, 999999999999.0000, 999999999999.0000, 999999999999.0000, 999999999999.0000, 999999999999.0000, 999999999999.0000, 999999999, 16, 99999.00, 99999.00, 99999.00, 9, 9999999999.000, 999999999, 'Very heavy, be careful', 1206833877407, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id, pattern_mask_format_id) VALUES (1208532522729, 1, 'Desk', 'AB41233', 1, false, true, false, 40, 1.0000, NULL, NULL, 100.2000, 200.0000, 250.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id, pattern_mask_format_id) VALUES (1210027840944, 1208867495266, 'Money  Maker 2', 'm1', 1, false, true, false, NULL, 1.0000, NULL, NULL, 100.0000, 100.0000, 100.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id, pattern_mask_format_id) VALUES (1208531803052, 1210702297698, 'Amplifier, Brand: Pioneer, Model: A 656 Mark II', '000099083411445', 1, false, true, false, 39, 1.0000, 234.5678, NULL, 300.0000, 270.0000, 240.0000, 1, 14, 50.00, 63.23, 14.43, 8, 16.000, 3, 'Very powerful', NULL, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id, pattern_mask_format_id) VALUES (1217063155346, 1209288962569, 'Camera', '666232377', 1, false, true, false, NULL, 1.0000, NULL, NULL, 100.0000, 100.0000, 100.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO simple_products (product_id, category_id, product_name, product_code, measure_unit_id, is_purchased, is_salable, is_obsolete, product_color_id, minimum_quantity, maximum_quantity, default_quantity, purchase_price, sale_price, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id, pattern_mask_format_id) VALUES (1217068585269, 1210519124507, 'Cofee-shop', '89832323', 1, false, true, false, NULL, 1.0000, NULL, NULL, 100.0000, 100.0000, 100.0000, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1217345869202.00);


--
-- TOC entry 2412 (class 0 OID 36920)
-- Dependencies: 1662
-- Data for Name: user_groups; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO user_groups (user_group_id, name, parent_id, description) VALUES (1217053087891, 'admins', 1209222047860, NULL);
INSERT INTO user_groups (user_group_id, name, parent_id, description) VALUES (1217331992157, 'moderators', 1209222047860, NULL);
INSERT INTO user_groups (user_group_id, name, parent_id, description) VALUES (1217340539126, 'asda', 1209222047860, NULL);


--
-- TOC entry 2413 (class 0 OID 36926)
-- Dependencies: 1663
-- Data for Name: user_rights; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO user_rights (user_group_id, user_id, data_object_type_id, data_object_id, can_read, can_create, can_modify, can_delete, user_right_id, special_permission_id, excluded, expires) VALUES (1217053087891, NULL, 35, NULL, false, false, false, false, 19, NULL, true, NULL);
INSERT INTO user_rights (user_group_id, user_id, data_object_type_id, data_object_id, can_read, can_create, can_modify, can_delete, user_right_id, special_permission_id, excluded, expires) VALUES (1217053087891, NULL, 1, NULL, false, false, false, false, 20, NULL, false, NULL);
INSERT INTO user_rights (user_group_id, user_id, data_object_type_id, data_object_id, can_read, can_create, can_modify, can_delete, user_right_id, special_permission_id, excluded, expires) VALUES (NULL, 1214836145032, 37, NULL, false, false, false, false, 21, NULL, true, NULL);
INSERT INTO user_rights (user_group_id, user_id, data_object_type_id, data_object_id, can_read, can_create, can_modify, can_delete, user_right_id, special_permission_id, excluded, expires) VALUES (NULL, 1217070935442, 1, NULL, false, false, false, false, 22, NULL, true, NULL);


--
-- TOC entry 2414 (class 0 OID 36934)
-- Dependencies: 1664
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO users (user_id, version, user_name, email_address, user_password, system_password, system_password_validity, is_new, creation_time, creator_id, description, small_image_uri, medium_image_uri, user_uri, next_action_after_login, small_image, medium_image, person_id) VALUES (1214740305788, 0, 'test', 'fds', 'a', NULL, NULL, false, '14:51:45.786+03', 1214740305788, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO users (user_id, version, user_name, email_address, user_password, system_password, system_password_validity, is_new, creation_time, creator_id, description, small_image_uri, medium_image_uri, user_uri, next_action_after_login, small_image, medium_image, person_id) VALUES (1214739376412, 0, 'adminold2', 'asdf', 'a', NULL, NULL, false, '14:36:16.223+03', 1214739376412, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO users (user_id, version, user_name, email_address, user_password, system_password, system_password_validity, is_new, creation_time, creator_id, description, small_image_uri, medium_image_uri, user_uri, next_action_after_login, small_image, medium_image, person_id) VALUES (1214730588490, 0, 'adminold', 'fdsa', 'a', NULL, NULL, false, '12:09:47.817+03', 1214730588490, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO users (user_id, version, user_name, email_address, user_password, system_password, system_password_validity, is_new, creation_time, creator_id, description, small_image_uri, medium_image_uri, user_uri, next_action_after_login, small_image, medium_image, person_id) VALUES (1214690075898, 0, 'Glam', 'fgds', 'a
', NULL, NULL, false, '00:54:34.567+03', 1214690075898, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO users (user_id, version, user_name, email_address, user_password, system_password, system_password_validity, is_new, creation_time, creator_id, description, small_image_uri, medium_image_uri, user_uri, next_action_after_login, small_image, medium_image, person_id) VALUES (1214775184802, 0, 'admina', 'asd@abv.bg', 'd033e22ae348aeb566fc214aec3585c4da997', NULL, NULL, false, '23:33:04.614+02', 1214775184802, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO users (user_id, version, user_name, email_address, user_password, system_password, system_password_validity, is_new, creation_time, creator_id, description, small_image_uri, medium_image_uri, user_uri, next_action_after_login, small_image, medium_image, person_id) VALUES (1214852691344, 0, 'Glamdring', 'gfds', '65a7de18bbd9bfe9ac475e46d820676e1fa56787', NULL, '2008-07-04 00:36:25.437', false, '21:04:51.25+02', 1214852691344, NULL, NULL, NULL, NULL, 'change', NULL, NULL, NULL);
INSERT INTO users (user_id, version, user_name, email_address, user_password, system_password, system_password_validity, is_new, creation_time, creator_id, description, small_image_uri, medium_image_uri, user_uri, next_action_after_login, small_image, medium_image, person_id) VALUES (1216149297360, 0, 'Edwin', 'oi5upo4r@t6h7vvt.com', '1a86385f9353c8d57fba67ea7e2e984a8287c43', NULL, NULL, false, '22:14:57.265+03', 1216149297360, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO users (user_id, version, user_name, email_address, user_password, system_password, system_password_validity, is_new, creation_time, creator_id, description, small_image_uri, medium_image_uri, user_uri, next_action_after_login, small_image, medium_image, person_id) VALUES (1216326891860, 0, 'testuserbranch', 'mytyvdc8@wlpzu2g.com', 'e96c3139ab9597ed9591e95b0fa6aba7c6ff6e5', NULL, NULL, false, '23:34:51.75+03', 1216326891860, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO users (user_id, version, user_name, email_address, user_password, system_password, system_password_validity, is_new, creation_time, creator_id, description, small_image_uri, medium_image_uri, user_uri, next_action_after_login, small_image, medium_image, person_id) VALUES (1216327142954, 0, 'branchtest', 'cnnzw6ub@i6d7h4l.com', '1a86385f9353c8d57fba67ea7e2e984a8287c43', NULL, NULL, false, '23:39:02.875+03', 1216327142954, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO users (user_id, version, user_name, email_address, user_password, system_password, system_password_validity, is_new, creation_time, creator_id, description, small_image_uri, medium_image_uri, user_uri, next_action_after_login, small_image, medium_image, person_id) VALUES (1217070308200, 0, 'edwin', 'edwin@duhai.com', 'c4402ab84915d1497ae2c793caa5d2a9a42fb5e', NULL, NULL, false, '14:05:08.19+03', 1217070308200, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO users (user_id, version, user_name, email_address, user_password, system_password, system_password_validity, is_new, creation_time, creator_id, description, small_image_uri, medium_image_uri, user_uri, next_action_after_login, small_image, medium_image, person_id) VALUES (1214803192268, 0, 'radoslav.lozanov', 'radoslav.lozanov@gmail.com', 'd033e22ae348aeb566fc214aec3585c4da997', NULL, NULL, false, '08:19:52.216+03', 1214803192268, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO users (user_id, version, user_name, email_address, user_password, system_password, system_password_validity, is_new, creation_time, creator_id, description, small_image_uri, medium_image_uri, user_uri, next_action_after_login, small_image, medium_image, person_id) VALUES (1217514926611, 0, 'userWithPerson', 'asd', '1a86385f9353c8d57fba67ea7e2e984a8287c43', NULL, NULL, false, '17:35:26.609+03', 1217514926611, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1217514926610);
INSERT INTO users (user_id, version, user_name, email_address, user_password, system_password, system_password_validity, is_new, creation_time, creator_id, description, small_image_uri, medium_image_uri, user_uri, next_action_after_login, small_image, medium_image, person_id) VALUES (1217410641955, 0, 'testtest', 'wif1iytr@k0v7rg0.com', 'ac6932654960b53c12368626b18f27fc147e4cc', NULL, NULL, false, '12:37:21.953+03', 1217410641955, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1211472878364);
INSERT INTO users (user_id, version, user_name, email_address, user_password, system_password, system_password_validity, is_new, creation_time, creator_id, description, small_image_uri, medium_image_uri, user_uri, next_action_after_login, small_image, medium_image, person_id) VALUES (1217071168446, 0, 'yamanja', 'yamanja@vundaba.com', 'c4402ab84915d1497ae2c793caa5d2a9a42fb5e', NULL, NULL, false, '14:19:28.431+03', 1217071168446, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1211472878364);
INSERT INTO users (user_id, version, user_name, email_address, user_password, system_password, system_password_validity, is_new, creation_time, creator_id, description, small_image_uri, medium_image_uri, user_uri, next_action_after_login, small_image, medium_image, person_id) VALUES (1214836145032, 0, 'admin', 'afds', '2643f6a6441ffdf4f82d7655dcfff7422aa322', NULL, NULL, false, '17:29:04.937+03', 1214836145032, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1211472878364);
INSERT INTO users (user_id, version, user_name, email_address, user_password, system_password, system_password_validity, is_new, creation_time, creator_id, description, small_image_uri, medium_image_uri, user_uri, next_action_after_login, small_image, medium_image, person_id) VALUES (1217070935442, 0, 'milev', 'milev@sm.com', 'c4402ab84915d1497ae2c793caa5d2a9a42fb5e', NULL, NULL, false, '14:15:35.431+03', 1217070935442, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1211472878364);
INSERT INTO users (user_id, version, user_name, email_address, user_password, system_password, system_password_validity, is_new, creation_time, creator_id, description, small_image_uri, medium_image_uri, user_uri, next_action_after_login, small_image, medium_image, person_id) VALUES (1217540926955, 0, 'user', 'fdsaaaa', 'dbd83c2a2237a6658303ae76513c0c350248ca6', NULL, NULL, false, '00:48:46.937+03', 1217540926955, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1217540926938);
INSERT INTO users (user_id, version, user_name, email_address, user_password, system_password, system_password_validity, is_new, creation_time, creator_id, description, small_image_uri, medium_image_uri, user_uri, next_action_after_login, small_image, medium_image, person_id) VALUES (1217623255955, 0, 'Usernameche', 'alabala', '585cbbd988ac351b7750cb3baa769c9843ba2ae', NULL, NULL, false, '23:40:55.937+03', 1217623255955, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1217623255938);
INSERT INTO users (user_id, version, user_name, email_address, user_password, system_password, system_password_validity, is_new, creation_time, creator_id, description, small_image_uri, medium_image_uri, user_uri, next_action_after_login, small_image, medium_image, person_id) VALUES (1217623859252, 0, 'usernamchence', 'gfdss', '585cbbd988ac351b7750cb3baa769c9843ba2ae', NULL, NULL, false, '23:50:59.25+03', 1217623859252, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1217623859251);


--
-- TOC entry 2415 (class 0 OID 36941)
-- Dependencies: 1665
-- Data for Name: users_organizations; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO users_organizations (user_id, organization_id, branch_id, is_user_active, person_id_todelete, user_group_id) VALUES (1214690075898, 1209222047860, NULL, true, NULL, NULL);
INSERT INTO users_organizations (user_id, organization_id, branch_id, is_user_active, person_id_todelete, user_group_id) VALUES (1214730588490, 1209222047860, NULL, true, NULL, NULL);
INSERT INTO users_organizations (user_id, organization_id, branch_id, is_user_active, person_id_todelete, user_group_id) VALUES (1214775184802, 1209222047860, NULL, true, NULL, NULL);
INSERT INTO users_organizations (user_id, organization_id, branch_id, is_user_active, person_id_todelete, user_group_id) VALUES (1214803192268, 1209394438429, NULL, true, NULL, NULL);
INSERT INTO users_organizations (user_id, organization_id, branch_id, is_user_active, person_id_todelete, user_group_id) VALUES (1214852691344, 1209582114840, NULL, true, NULL, NULL);
INSERT INTO users_organizations (user_id, organization_id, branch_id, is_user_active, person_id_todelete, user_group_id) VALUES (1216327142954, 1209222047860, 1209222423971, true, 1211475930558, NULL);
INSERT INTO users_organizations (user_id, organization_id, branch_id, is_user_active, person_id_todelete, user_group_id) VALUES (1216326891860, 1209222047860, NULL, false, 1211472878364, NULL);
INSERT INTO users_organizations (user_id, organization_id, branch_id, is_user_active, person_id_todelete, user_group_id) VALUES (1216149297360, 1209222047860, NULL, true, 1211472878364, NULL);
INSERT INTO users_organizations (user_id, organization_id, branch_id, is_user_active, person_id_todelete, user_group_id) VALUES (1217070308200, 1209222047860, 1209222423971, true, 1211472878364, NULL);
INSERT INTO users_organizations (user_id, organization_id, branch_id, is_user_active, person_id_todelete, user_group_id) VALUES (1217070935442, 1209222047860, 1209222409189, true, 1211475930558, NULL);
INSERT INTO users_organizations (user_id, organization_id, branch_id, is_user_active, person_id_todelete, user_group_id) VALUES (1217071168446, 1209582114840, 1212004334360, true, 1213512206579, NULL);
INSERT INTO users_organizations (user_id, organization_id, branch_id, is_user_active, person_id_todelete, user_group_id) VALUES (1214836145032, 1209222047860, 1209222423971, true, 1211472878364, NULL);
INSERT INTO users_organizations (user_id, organization_id, branch_id, is_user_active, person_id_todelete, user_group_id) VALUES (1217410641955, 1217410631001, NULL, true, NULL, NULL);
INSERT INTO users_organizations (user_id, organization_id, branch_id, is_user_active, person_id_todelete, user_group_id) VALUES (1217514926611, 1209222047860, 1209222423971, false, NULL, NULL);
INSERT INTO users_organizations (user_id, organization_id, branch_id, is_user_active, person_id_todelete, user_group_id) VALUES (1217540926955, 1209222047860, 1210856501325, false, NULL, NULL);
INSERT INTO users_organizations (user_id, organization_id, branch_id, is_user_active, person_id_todelete, user_group_id) VALUES (1217623255955, 1209222047860, 1209222423971, false, NULL, NULL);
INSERT INTO users_organizations (user_id, organization_id, branch_id, is_user_active, person_id_todelete, user_group_id) VALUES (1217623859252, 1209222047860, 1209222423971, false, NULL, NULL);


--
-- TOC entry 2416 (class 0 OID 36944)
-- Dependencies: 1666
-- Data for Name: virtual_products; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2419 (class 0 OID 38351)
-- Dependencies: 1680
-- Data for Name: warehouse_products; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO warehouse_products (warehouse_product_id, default_quantity, delivery_time, maximum_quantity, minimum_quantity, notes, ordered_quantity, parent_id, purchase_price, quantity_due, quantity_in_stock, reserved_quantity, sale_price, sold_quantity, product_id, warehouse_id) VALUES (1217346091438.00, 7.00, NULL, 6.00, 5.00, NULL, 0.00, 1209222047860.00, NULL, 0.00, 0.00, 0.00, NULL, 0.00, 1217063155346.00, 1217346044742.00);
INSERT INTO warehouse_products (warehouse_product_id, default_quantity, delivery_time, maximum_quantity, minimum_quantity, notes, ordered_quantity, parent_id, purchase_price, quantity_due, quantity_in_stock, reserved_quantity, sale_price, sold_quantity, product_id, warehouse_id) VALUES (1217346108350.00, 45.00, NULL, 67.00, 67.00, NULL, 0.00, 1209222047860.00, NULL, 0.00, 0.00, 0.00, NULL, 0.00, 1217068585269.00, 1217346044742.00);


--
-- TOC entry 2417 (class 0 OID 36950)
-- Dependencies: 1667
-- Data for Name: warehouses; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO warehouses (warehouse_id, parent_id, address_id, warehouseman_id, description) VALUES (1210856556559, NULL, 1210856514214, 1206833877407, 'Very large');
INSERT INTO warehouses (warehouse_id, parent_id, address_id, warehouseman_id, description) VALUES (1210856702896, NULL, 1210856490268, 1211472752091, 'Warehouse for programmers');
INSERT INTO warehouses (warehouse_id, parent_id, address_id, warehouseman_id, description) VALUES (1211476820946, NULL, 1210856501325, 1211475930558, 'the man of mistery..');
INSERT INTO warehouses (warehouse_id, parent_id, address_id, warehouseman_id, description) VALUES (1213952129954, NULL, 1211475534335, 1211475930558, 'Massy Warehouse');
INSERT INTO warehouses (warehouse_id, parent_id, address_id, warehouseman_id, description) VALUES (1217346044742, 1209222047860, 1211475534335, 1211475930558, NULL);
INSERT INTO warehouses (warehouse_id, parent_id, address_id, warehouseman_id, description) VALUES (1217346072123, 1209222047860, 1209222423971, 1211475930558, NULL);


--
-- TOC entry 1995 (class 2606 OID 36980)
-- Dependencies: 1619 1619
-- Name: business_partners_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY business_partners
    ADD CONSTRAINT business_partners_pkey PRIMARY KEY (partner_id);


--
-- TOC entry 2050 (class 2606 OID 36982)
-- Dependencies: 1635 1635 1635
-- Name: fk_delivery_certificate_assignments; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY delivery_certificate_assignments
    ADD CONSTRAINT fk_delivery_certificate_assignments PRIMARY KEY (delivery_certificate_id, document_id);


--
-- TOC entry 2074 (class 2606 OID 36984)
-- Dependencies: 1642 1642
-- Name: order_confirmation_items_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY order_confirmation_items
    ADD CONSTRAINT order_confirmation_items_pkey PRIMARY KEY (confirmation_item_id);


--
-- TOC entry 2076 (class 2606 OID 36986)
-- Dependencies: 1643 1643
-- Name: order_confirmations_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT order_confirmations_pkey PRIMARY KEY (order_confirmation_id);


--
-- TOC entry 2078 (class 2606 OID 36988)
-- Dependencies: 1644 1644
-- Name: order_item_match_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY order_item_match
    ADD CONSTRAINT order_item_match_pkey PRIMARY KEY (id);


--
-- TOC entry 2080 (class 2606 OID 36990)
-- Dependencies: 1645 1645
-- Name: organizations_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT organizations_pkey PRIMARY KEY (organization_id);


--
-- TOC entry 2140 (class 2606 OID 38345)
-- Dependencies: 1679 1679
-- Name: pattern_mask_formats_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pattern_mask_formats
    ADD CONSTRAINT pattern_mask_formats_pkey PRIMARY KEY (pattern_mask_format_id);


--
-- TOC entry 2086 (class 2606 OID 36992)
-- Dependencies: 1647 1647
-- Name: persons_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT persons_pkey PRIMARY KEY (partner_id);


--
-- TOC entry 1975 (class 2606 OID 36994)
-- Dependencies: 1613 1613
-- Name: pk_addresses; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT pk_addresses PRIMARY KEY (address_id);


--
-- TOC entry 1977 (class 2606 OID 36996)
-- Dependencies: 1614 1614
-- Name: pk_assembling_categories; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_categories
    ADD CONSTRAINT pk_assembling_categories PRIMARY KEY (assembling_category_id);


--
-- TOC entry 1983 (class 2606 OID 36998)
-- Dependencies: 1615 1615
-- Name: pk_assembling_schema_item_values; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schema_item_values
    ADD CONSTRAINT pk_assembling_schema_item_values PRIMARY KEY (item_value_id);


--
-- TOC entry 1985 (class 2606 OID 37000)
-- Dependencies: 1616 1616
-- Name: pk_assembling_schema_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT pk_assembling_schema_items PRIMARY KEY (item_id);


--
-- TOC entry 1987 (class 2606 OID 37002)
-- Dependencies: 1617 1617
-- Name: pk_assembling_schemas; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT pk_assembling_schemas PRIMARY KEY (product_id);


--
-- TOC entry 1993 (class 2606 OID 37004)
-- Dependencies: 1618 1618
-- Name: pk_bank_details; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT pk_bank_details PRIMARY KEY (bank_detail_id);


--
-- TOC entry 1997 (class 2606 OID 37006)
-- Dependencies: 1620 1620
-- Name: pk_cities; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY cities
    ADD CONSTRAINT pk_cities PRIMARY KEY (city_id);


--
-- TOC entry 2001 (class 2606 OID 37008)
-- Dependencies: 1621 1621 1621
-- Name: pk_classified_objects; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classified_objects
    ADD CONSTRAINT pk_classified_objects PRIMARY KEY (classifier_id, classified_object_id);


--
-- TOC entry 2003 (class 2606 OID 37010)
-- Dependencies: 1622 1622 1622
-- Name: pk_classifier_applied_for_dot; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifier_applied_for_dot
    ADD CONSTRAINT pk_classifier_applied_for_dot PRIMARY KEY (classifier_id, data_object_type_id);


--
-- TOC entry 2005 (class 2606 OID 37012)
-- Dependencies: 1623 1623
-- Name: pk_classifier_groups; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifier_groups
    ADD CONSTRAINT pk_classifier_groups PRIMARY KEY (classifier_group_id);


--
-- TOC entry 2011 (class 2606 OID 37014)
-- Dependencies: 1624 1624
-- Name: pk_classifiers; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT pk_classifiers PRIMARY KEY (classifier_id);


--
-- TOC entry 2018 (class 2606 OID 37016)
-- Dependencies: 1625 1625
-- Name: pk_communication_contacts; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT pk_communication_contacts PRIMARY KEY (communication_contact_id);


--
-- TOC entry 2022 (class 2606 OID 37018)
-- Dependencies: 1626 1626
-- Name: pk_complex_product_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT pk_complex_product_items PRIMARY KEY (complex_product_item_id);


--
-- TOC entry 2024 (class 2606 OID 37020)
-- Dependencies: 1627 1627
-- Name: pk_complex_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY complex_products
    ADD CONSTRAINT pk_complex_products PRIMARY KEY (product_id);


--
-- TOC entry 2026 (class 2606 OID 37022)
-- Dependencies: 1628 1628
-- Name: pk_contact_persons; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT pk_contact_persons PRIMARY KEY (contact_person_id);


--
-- TOC entry 2028 (class 2606 OID 37024)
-- Dependencies: 1629 1629
-- Name: pk_countries; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY countries
    ADD CONSTRAINT pk_countries PRIMARY KEY (country_id);


--
-- TOC entry 2032 (class 2606 OID 37026)
-- Dependencies: 1630 1630
-- Name: pk_currencies; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY currencies
    ADD CONSTRAINT pk_currencies PRIMARY KEY (currency_id);


--
-- TOC entry 2036 (class 2606 OID 37028)
-- Dependencies: 1631 1631
-- Name: pk_data_object_details; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_details
    ADD CONSTRAINT pk_data_object_details PRIMARY KEY (data_object_id);


--
-- TOC entry 2040 (class 2606 OID 37030)
-- Dependencies: 1632 1632
-- Name: pk_data_object_links; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_links
    ADD CONSTRAINT pk_data_object_links PRIMARY KEY (data_object_link_id);


--
-- TOC entry 2044 (class 2606 OID 37032)
-- Dependencies: 1633 1633
-- Name: pk_data_object_types; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_types
    ADD CONSTRAINT pk_data_object_types PRIMARY KEY (data_object_type_id);


--
-- TOC entry 2048 (class 2606 OID 37034)
-- Dependencies: 1634 1634
-- Name: pk_data_objects; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT pk_data_objects PRIMARY KEY (data_object_id);


--
-- TOC entry 2052 (class 2606 OID 37036)
-- Dependencies: 1636 1636
-- Name: pk_delivery_certificate_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT pk_delivery_certificate_items PRIMARY KEY (certificate_item_id);


--
-- TOC entry 2054 (class 2606 OID 37038)
-- Dependencies: 1637 1637 1637
-- Name: pk_delivery_certificate_serial_numbers; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY delivery_certificate_serial_numbers
    ADD CONSTRAINT pk_delivery_certificate_serial_numbers PRIMARY KEY (certificate_item_id, serial_number);


--
-- TOC entry 2059 (class 2606 OID 37040)
-- Dependencies: 1638 1638
-- Name: pk_delivery_certificates; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT pk_delivery_certificates PRIMARY KEY (delivery_certificate_id);


--
-- TOC entry 2063 (class 2606 OID 37042)
-- Dependencies: 1639 1639
-- Name: pk_enum_classes; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY enum_classes
    ADD CONSTRAINT pk_enum_classes PRIMARY KEY (enum_class_id);


--
-- TOC entry 2067 (class 2606 OID 37044)
-- Dependencies: 1640 1640
-- Name: pk_invoice_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY invoice_items
    ADD CONSTRAINT pk_invoice_items PRIMARY KEY (invoice_item_id);


--
-- TOC entry 2070 (class 2606 OID 37046)
-- Dependencies: 1641 1641
-- Name: pk_invoices; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT pk_invoices PRIMARY KEY (invoice_id);


--
-- TOC entry 2082 (class 2606 OID 37048)
-- Dependencies: 1646 1646
-- Name: pk_passports; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT pk_passports PRIMARY KEY (passport_id);


--
-- TOC entry 2088 (class 2606 OID 37052)
-- Dependencies: 1648 1648
-- Name: pk_position_types; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY position_types
    ADD CONSTRAINT pk_position_types PRIMARY KEY (position_type_id);


--
-- TOC entry 2090 (class 2606 OID 37054)
-- Dependencies: 1649 1649
-- Name: pk_product_categories; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT pk_product_categories PRIMARY KEY (product_category_id);


--
-- TOC entry 2094 (class 2606 OID 37056)
-- Dependencies: 1650 1650 1650
-- Name: pk_product_suppliers; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT pk_product_suppliers PRIMARY KEY (product_id, supplier_id);


--
-- TOC entry 2120 (class 2606 OID 37058)
-- Dependencies: 1661 1661
-- Name: pk_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT pk_products PRIMARY KEY (product_id);


--
-- TOC entry 2096 (class 2606 OID 37060)
-- Dependencies: 1651 1651
-- Name: pk_products1; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY products
    ADD CONSTRAINT pk_products1 PRIMARY KEY (product_id);


--
-- TOC entry 2098 (class 2606 OID 37062)
-- Dependencies: 1652 1652
-- Name: pk_purchase_order_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT pk_purchase_order_items PRIMARY KEY (order_item_id);


--
-- TOC entry 2102 (class 2606 OID 37064)
-- Dependencies: 1654 1654
-- Name: pk_real_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY real_products
    ADD CONSTRAINT pk_real_products PRIMARY KEY (product_id);


--
-- TOC entry 2106 (class 2606 OID 37066)
-- Dependencies: 1655 1655
-- Name: pk_receipt_certificate_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT pk_receipt_certificate_items PRIMARY KEY (certificate_item_id);


--
-- TOC entry 2108 (class 2606 OID 37068)
-- Dependencies: 1656 1656 1656
-- Name: pk_receipt_certificate_serial_numbers; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY receipt_certificate_serial_numbers
    ADD CONSTRAINT pk_receipt_certificate_serial_numbers PRIMARY KEY (certificate_item_id, serial_number);


--
-- TOC entry 2110 (class 2606 OID 37070)
-- Dependencies: 1657 1657
-- Name: pk_receipt_certificates; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT pk_receipt_certificates PRIMARY KEY (receipt_certificate_id);


--
-- TOC entry 2116 (class 2606 OID 37072)
-- Dependencies: 1659 1659
-- Name: pk_resource_bundle; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY resource_bundle
    ADD CONSTRAINT pk_resource_bundle PRIMARY KEY (resource_id);


--
-- TOC entry 2128 (class 2606 OID 37074)
-- Dependencies: 1664 1664
-- Name: pk_users; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT pk_users PRIMARY KEY (user_id);


--
-- TOC entry 2136 (class 2606 OID 37076)
-- Dependencies: 1666 1666
-- Name: pk_virtual_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY virtual_products
    ADD CONSTRAINT pk_virtual_products PRIMARY KEY (product_id);


--
-- TOC entry 2138 (class 2606 OID 37078)
-- Dependencies: 1667 1667
-- Name: pk_warehouses; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY warehouses
    ADD CONSTRAINT pk_warehouses PRIMARY KEY (warehouse_id);


--
-- TOC entry 2100 (class 2606 OID 37080)
-- Dependencies: 1653 1653
-- Name: purchase_orders_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT purchase_orders_pkey PRIMARY KEY (purchase_order_id);


--
-- TOC entry 2114 (class 2606 OID 37082)
-- Dependencies: 1658 1658
-- Name: registration_codes_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY registration_codes
    ADD CONSTRAINT registration_codes_pkey PRIMARY KEY (registration_code);


--
-- TOC entry 1979 (class 2606 OID 37084)
-- Dependencies: 1614 1614 1614 1614
-- Name: uk_assembling_categories_by_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_categories
    ADD CONSTRAINT uk_assembling_categories_by_code UNIQUE (parent_id, parent_category_id, category_code);


--
-- TOC entry 1981 (class 2606 OID 37086)
-- Dependencies: 1614 1614 1614 1614
-- Name: uk_assembling_categories_by_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_categories
    ADD CONSTRAINT uk_assembling_categories_by_name UNIQUE (parent_id, parent_category_id, category_name);


--
-- TOC entry 1989 (class 2606 OID 37088)
-- Dependencies: 1617 1617 1617
-- Name: uk_assembling_schemas_by_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT uk_assembling_schemas_by_code UNIQUE (category_id, schema_code);


--
-- TOC entry 1991 (class 2606 OID 37090)
-- Dependencies: 1617 1617 1617
-- Name: uk_assembling_schemas_by_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT uk_assembling_schemas_by_name UNIQUE (category_id, schema_name);


--
-- TOC entry 1999 (class 2606 OID 37092)
-- Dependencies: 1620 1620 1620
-- Name: uk_cities_country_id_city_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY cities
    ADD CONSTRAINT uk_cities_country_id_city_name UNIQUE (country_id, city_name);


--
-- TOC entry 2007 (class 2606 OID 37094)
-- Dependencies: 1623 1623 1623
-- Name: uk_classifier_groups_parent_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifier_groups
    ADD CONSTRAINT uk_classifier_groups_parent_code UNIQUE (parent_id, classifier_group_code);


--
-- TOC entry 2009 (class 2606 OID 37096)
-- Dependencies: 1623 1623 1623
-- Name: uk_classifier_groups_parent_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifier_groups
    ADD CONSTRAINT uk_classifier_groups_parent_name UNIQUE (parent_id, classifier_group_name);


--
-- TOC entry 2013 (class 2606 OID 37098)
-- Dependencies: 1624 1624 1624
-- Name: uk_classifiers_parent_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT uk_classifiers_parent_code UNIQUE (parent_id, classifier_code);


--
-- TOC entry 2015 (class 2606 OID 37100)
-- Dependencies: 1624 1624 1624
-- Name: uk_classifiers_parent_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT uk_classifiers_parent_name UNIQUE (parent_id, classifier_name);


--
-- TOC entry 2020 (class 2606 OID 37102)
-- Dependencies: 1625 1625 1625 1625 1625
-- Name: uk_communication_contacts_parent_type_value_contact_person; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT uk_communication_contacts_parent_type_value_contact_person UNIQUE (parent_id, communication_type_id, communication_value, contact_person_id);


--
-- TOC entry 2030 (class 2606 OID 37104)
-- Dependencies: 1629 1629
-- Name: uk_countries_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY countries
    ADD CONSTRAINT uk_countries_name UNIQUE (country_name);


--
-- TOC entry 2034 (class 2606 OID 37106)
-- Dependencies: 1630 1630
-- Name: uk_currencies_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY currencies
    ADD CONSTRAINT uk_currencies_name UNIQUE (currency_name);


--
-- TOC entry 2038 (class 2606 OID 37108)
-- Dependencies: 1631 1631 1631
-- Name: uk_data_object_details_do_id_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_details
    ADD CONSTRAINT uk_data_object_details_do_id_code UNIQUE (data_object_id, detail_code);


--
-- TOC entry 2042 (class 2606 OID 37110)
-- Dependencies: 1632 1632 1632
-- Name: uk_data_object_links_parent_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_links
    ADD CONSTRAINT uk_data_object_links_parent_name UNIQUE (parent_id, link_name);


--
-- TOC entry 2061 (class 2606 OID 37112)
-- Dependencies: 1638 1638 1638
-- Name: uk_delivery_certificates_parent_cert_number; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT uk_delivery_certificates_parent_cert_number UNIQUE (parent_id, delivery_certificate_number);


--
-- TOC entry 2046 (class 2606 OID 37114)
-- Dependencies: 1633 1633
-- Name: uk_dot_data_object_type; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_types
    ADD CONSTRAINT uk_dot_data_object_type UNIQUE (data_object_type);


--
-- TOC entry 2065 (class 2606 OID 37116)
-- Dependencies: 1639 1639
-- Name: uk_enum_classes; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY enum_classes
    ADD CONSTRAINT uk_enum_classes UNIQUE (enum_class_name);


--
-- TOC entry 2072 (class 2606 OID 37118)
-- Dependencies: 1641 1641 1641
-- Name: uk_invoices_invoice_number; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT uk_invoices_invoice_number UNIQUE (parent_id, invoice_number);


--
-- TOC entry 2084 (class 2606 OID 37120)
-- Dependencies: 1646 1646 1646 1646
-- Name: uk_passports_parent_type_number; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT uk_passports_parent_type_number UNIQUE (parent_id, passport_type_id, passport_number);


--
-- TOC entry 2092 (class 2606 OID 37122)
-- Dependencies: 1649 1649 1649
-- Name: uk_product_categories_parent_category_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT uk_product_categories_parent_category_name UNIQUE (parent_id, category_name);


--
-- TOC entry 2104 (class 2606 OID 37124)
-- Dependencies: 1654 1654
-- Name: uk_real_products_by_simple_product; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY real_products
    ADD CONSTRAINT uk_real_products_by_simple_product UNIQUE (simple_product_id);


--
-- TOC entry 2112 (class 2606 OID 37126)
-- Dependencies: 1657 1657 1657
-- Name: uk_receipt_certificates_parent_cert_number; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT uk_receipt_certificates_parent_cert_number UNIQUE (parent_id, receipt_certificate_number);


--
-- TOC entry 2118 (class 2606 OID 37128)
-- Dependencies: 1660 1660
-- Name: uk_seq_id_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY sequence_identifiers
    ADD CONSTRAINT uk_seq_id_name UNIQUE (seq_id_name);


--
-- TOC entry 2130 (class 2606 OID 37130)
-- Dependencies: 1664 1664
-- Name: uk_users_email_address; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT uk_users_email_address UNIQUE (email_address);


--
-- TOC entry 2132 (class 2606 OID 37132)
-- Dependencies: 1664 1664
-- Name: uk_users_user_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT uk_users_user_name UNIQUE (user_name);


--
-- TOC entry 2122 (class 2606 OID 37134)
-- Dependencies: 1662 1662
-- Name: user_groups_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY user_groups
    ADD CONSTRAINT user_groups_pkey PRIMARY KEY (user_group_id);


--
-- TOC entry 2126 (class 2606 OID 37136)
-- Dependencies: 1663 1663
-- Name: user_rights_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY user_rights
    ADD CONSTRAINT user_rights_pkey PRIMARY KEY (user_right_id);


--
-- TOC entry 2134 (class 2606 OID 37138)
-- Dependencies: 1665 1665 1665
-- Name: users_organizations_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY users_organizations
    ADD CONSTRAINT users_organizations_pkey PRIMARY KEY (user_id, organization_id);


--
-- TOC entry 2142 (class 2606 OID 38355)
-- Dependencies: 1680 1680
-- Name: warehouse_products_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY warehouse_products
    ADD CONSTRAINT warehouse_products_pkey PRIMARY KEY (warehouse_product_id);


--
-- TOC entry 2016 (class 1259 OID 37141)
-- Dependencies: 1625
-- Name: fk_contact_person_id; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX fk_contact_person_id ON communication_contacts USING btree (contact_person_id);


--
-- TOC entry 2055 (class 1259 OID 37142)
-- Dependencies: 1638
-- Name: fki_delivery_certificates_recipient; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX fki_delivery_certificates_recipient ON delivery_certificates USING btree (recipient_id);


--
-- TOC entry 2068 (class 1259 OID 37143)
-- Dependencies: 1641
-- Name: fki_fk25f222e693edee7d; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX fki_fk25f222e693edee7d ON invoices USING btree (recipient_id);


--
-- TOC entry 2056 (class 1259 OID 37144)
-- Dependencies: 1638
-- Name: fki_forwarder_address_constraint; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX fki_forwarder_address_constraint ON delivery_certificates USING btree (forwarder_branch_id);


--
-- TOC entry 2057 (class 1259 OID 37145)
-- Dependencies: 1638
-- Name: fki_recipient_address_constraint; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX fki_recipient_address_constraint ON delivery_certificates USING btree (recipient_branch_id);


--
-- TOC entry 2123 (class 1259 OID 37146)
-- Dependencies: 1663
-- Name: user_group_idx; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX user_group_idx ON user_rights USING btree (user_group_id);


--
-- TOC entry 2124 (class 1259 OID 37147)
-- Dependencies: 1663
-- Name: user_idx; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX user_idx ON user_rights USING btree (user_id);


--
-- TOC entry 2143 (class 2606 OID 37148)
-- Dependencies: 1619 1613 1994
-- Name: addresses_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT addresses_parent_id_fkey FOREIGN KEY (parent_id) REFERENCES business_partners(partner_id) ON DELETE CASCADE;


--
-- TOC entry 2156 (class 2606 OID 37153)
-- Dependencies: 1618 1659 2115
-- Name: bank_details_currency_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT bank_details_currency_id_fkey FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2157 (class 2606 OID 37158)
-- Dependencies: 1618 1613 1974
-- Name: bank_details_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT bank_details_parent_id_fkey FOREIGN KEY (parent_id) REFERENCES addresses(address_id) ON DELETE CASCADE;


--
-- TOC entry 2163 (class 2606 OID 37163)
-- Dependencies: 1620 1629 2027
-- Name: cities_country_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cities
    ADD CONSTRAINT cities_country_id_fkey FOREIGN KEY (country_id) REFERENCES countries(country_id) ON DELETE CASCADE;


--
-- TOC entry 2165 (class 2606 OID 37168)
-- Dependencies: 2047 1621 1634
-- Name: classified_objects_classified_object_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classified_objects
    ADD CONSTRAINT classified_objects_classified_object_id_fkey FOREIGN KEY (classified_object_id) REFERENCES data_objects(data_object_id) ON DELETE CASCADE;


--
-- TOC entry 2167 (class 2606 OID 37173)
-- Dependencies: 1624 2010 1622
-- Name: classifier_applied_for_dot_classifier_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifier_applied_for_dot
    ADD CONSTRAINT classifier_applied_for_dot_classifier_id_fkey FOREIGN KEY (classifier_id) REFERENCES classifiers(classifier_id) ON DELETE CASCADE;


--
-- TOC entry 2170 (class 2606 OID 37178)
-- Dependencies: 2004 1623 1624
-- Name: classifiers_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT classifiers_parent_id_fkey FOREIGN KEY (parent_id) REFERENCES classifier_groups(classifier_group_id) ON DELETE CASCADE;


--
-- TOC entry 2172 (class 2606 OID 37183)
-- Dependencies: 1628 2025 1625
-- Name: communication_contacts_contact_person_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT communication_contacts_contact_person_id_fkey FOREIGN KEY (contact_person_id) REFERENCES contact_persons(contact_person_id) ON DELETE SET NULL;


--
-- TOC entry 2173 (class 2606 OID 37188)
-- Dependencies: 1625 1974 1613
-- Name: communication_contacts_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT communication_contacts_parent_id_fkey FOREIGN KEY (parent_id) REFERENCES addresses(address_id) ON DELETE CASCADE;


--
-- TOC entry 2181 (class 2606 OID 37193)
-- Dependencies: 2085 1628 1647
-- Name: contact_persons_contact_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT contact_persons_contact_id_fkey FOREIGN KEY (contact_id) REFERENCES persons(partner_id) ON DELETE CASCADE;


--
-- TOC entry 2182 (class 2606 OID 37198)
-- Dependencies: 1613 1628 1974
-- Name: contact_persons_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT contact_persons_parent_id_fkey FOREIGN KEY (parent_id) REFERENCES addresses(address_id) ON DELETE CASCADE;


--
-- TOC entry 2185 (class 2606 OID 37203)
-- Dependencies: 1659 1629 2115
-- Name: countries_currency_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY countries
    ADD CONSTRAINT countries_currency_id_fkey FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2164 (class 2606 OID 37208)
-- Dependencies: 2047 1620 1634
-- Name: data_object_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cities
    ADD CONSTRAINT data_object_fk FOREIGN KEY (city_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2343 (class 2606 OID 37213)
-- Dependencies: 2043 1633 1663
-- Name: data_object_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights
    ADD CONSTRAINT data_object_type FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id) ON DELETE CASCADE;


--
-- TOC entry 2344 (class 2606 OID 37218)
-- Dependencies: 2047 1663 1634
-- Name: data_objects; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights
    ADD CONSTRAINT data_objects FOREIGN KEY (data_object_id) REFERENCES data_objects(data_object_id) ON DELETE CASCADE;


--
-- TOC entry 2322 (class 2606 OID 37223)
-- Dependencies: 2062 1639 1659
-- Name: fk11ef5dd39219a9be; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY resource_bundle
    ADD CONSTRAINT fk11ef5dd39219a9be FOREIGN KEY (enum_class_id) REFERENCES enum_classes(enum_class_id);


--
-- TOC entry 2222 (class 2606 OID 37238)
-- Dependencies: 2115 1641 1659
-- Name: fk25f222e617174fab; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e617174fab FOREIGN KEY (transportation_method_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2223 (class 2606 OID 37243)
-- Dependencies: 1641 2115 1659
-- Name: fk25f222e61808129a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e61808129a FOREIGN KEY (doc_delivery_method_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2224 (class 2606 OID 37248)
-- Dependencies: 1641 2115 1659
-- Name: fk25f222e63aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e63aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2225 (class 2606 OID 37253)
-- Dependencies: 2115 1641 1659
-- Name: fk25f222e63dc9408c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e63dc9408c FOREIGN KEY (payment_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2226 (class 2606 OID 37258)
-- Dependencies: 2115 1659 1641
-- Name: fk25f222e646685c7a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e646685c7a FOREIGN KEY (delivery_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2227 (class 2606 OID 37263)
-- Dependencies: 2085 1641 1647
-- Name: fk25f222e649212a2e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e649212a2e FOREIGN KEY (recipient_contact_id) REFERENCES persons(partner_id);


--
-- TOC entry 2228 (class 2606 OID 37268)
-- Dependencies: 2085 1641 1647
-- Name: fk25f222e64da6f8bc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e64da6f8bc FOREIGN KEY (creator_id) REFERENCES persons(partner_id);


--
-- TOC entry 2229 (class 2606 OID 37273)
-- Dependencies: 1619 1994 1641
-- Name: fk25f222e693edee7d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e693edee7d FOREIGN KEY (recipient_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2230 (class 2606 OID 37278)
-- Dependencies: 1659 1641 2115
-- Name: fk25f222e696e3ba71; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e696e3ba71 FOREIGN KEY (payment_terms_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2231 (class 2606 OID 37283)
-- Dependencies: 2115 1641 1659
-- Name: fk25f222e69c49320d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e69c49320d FOREIGN KEY (status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2232 (class 2606 OID 37288)
-- Dependencies: 2115 1641 1659
-- Name: fk25f222e6a94f3ab3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e6a94f3ab3 FOREIGN KEY (invoice_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2233 (class 2606 OID 37293)
-- Dependencies: 1641 2115 1659
-- Name: fk25f222e6b07d659a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e6b07d659a FOREIGN KEY (vat_condition_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2234 (class 2606 OID 37298)
-- Dependencies: 1632 2039 1641
-- Name: fk25f222e6f61f3d82; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e6f61f3d82 FOREIGN KEY (shipping_agent_id) REFERENCES data_object_links(data_object_link_id);


--
-- TOC entry 2235 (class 2606 OID 37303)
-- Dependencies: 2085 1641 1647
-- Name: fk25f222e6fd5b3613; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk25f222e6fd5b3613 FOREIGN KEY (sender_id) REFERENCES persons(partner_id);


--
-- TOC entry 2213 (class 2606 OID 37308)
-- Dependencies: 1659 2115 1640
-- Name: fk326ab82e1ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoice_items
    ADD CONSTRAINT fk326ab82e1ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2214 (class 2606 OID 37313)
-- Dependencies: 1667 2137 1640
-- Name: fk326ab82e9f1800e1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoice_items
    ADD CONSTRAINT fk326ab82e9f1800e1 FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2215 (class 2606 OID 37318)
-- Dependencies: 1640 1661 2119
-- Name: fk326ab82ea330dfcf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoice_items
    ADD CONSTRAINT fk326ab82ea330dfcf FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2216 (class 2606 OID 37323)
-- Dependencies: 2119 1661 1640
-- Name: fk326ab82ef10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoice_items
    ADD CONSTRAINT fk326ab82ef10b9721 FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2250 (class 2606 OID 37328)
-- Dependencies: 1613 1974 1643
-- Name: fk327473ad27a66c93; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk327473ad27a66c93 FOREIGN KEY (branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2251 (class 2606 OID 37333)
-- Dependencies: 1659 1643 2115
-- Name: fk327473ad3aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk327473ad3aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2252 (class 2606 OID 37338)
-- Dependencies: 1994 1619 1643
-- Name: fk327473ad5aa049f4; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk327473ad5aa049f4 FOREIGN KEY (supplier_partner_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2253 (class 2606 OID 37343)
-- Dependencies: 1628 1643 2025
-- Name: fk327473ad7ebcc009; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk327473ad7ebcc009 FOREIGN KEY (supplier_contact_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2254 (class 2606 OID 37348)
-- Dependencies: 1659 2115 1643
-- Name: fk327473ada97faa1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk327473ada97faa1 FOREIGN KEY (document_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2158 (class 2606 OID 37353)
-- Dependencies: 1645 2079 1618
-- Name: fk363aa33fee88a3ca; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk363aa33fee88a3ca FOREIGN KEY (bank_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2159 (class 2606 OID 37358)
-- Dependencies: 2085 1618 1647
-- Name: fk363aa33ff339b22b; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk363aa33ff339b22b FOREIGN KEY (bank_contact_id) REFERENCES persons(partner_id);


--
-- TOC entry 2199 (class 2606 OID 37363)
-- Dependencies: 1647 1638 2085
-- Name: fk3edb4c27157c075; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c27157c075 FOREIGN KEY (forwarder_contact_id) REFERENCES persons(partner_id);


--
-- TOC entry 2200 (class 2606 OID 37368)
-- Dependencies: 1659 1638 2115
-- Name: fk3edb4c2746dd317; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c2746dd317 FOREIGN KEY (delivery_cert_method_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2201 (class 2606 OID 37373)
-- Dependencies: 1647 1638 2085
-- Name: fk3edb4c2749212a2e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c2749212a2e FOREIGN KEY (recipient_contact_id) REFERENCES persons(partner_id);


--
-- TOC entry 2202 (class 2606 OID 37378)
-- Dependencies: 2085 1638 1647
-- Name: fk3edb4c274da6f8bc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c274da6f8bc FOREIGN KEY (creator_id) REFERENCES persons(partner_id);


--
-- TOC entry 2203 (class 2606 OID 37383)
-- Dependencies: 1638 1659 2115
-- Name: fk3edb4c278a6109cb; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c278a6109cb FOREIGN KEY (delivery_cert_reason_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2204 (class 2606 OID 37388)
-- Dependencies: 1638 1667 2137
-- Name: fk3edb4c279f1800e1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c279f1800e1 FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2205 (class 2606 OID 37393)
-- Dependencies: 1645 2079 1638
-- Name: fk3edb4c27f2569c14; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c27f2569c14 FOREIGN KEY (forwarder_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2360 (class 2606 OID 38346)
-- Dependencies: 1994 1619 1679
-- Name: fk40afd1582b1363d6; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pattern_mask_formats
    ADD CONSTRAINT fk40afd1582b1363d6 FOREIGN KEY (owner_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2275 (class 2606 OID 37408)
-- Dependencies: 1649 2089 1649
-- Name: fk5519b36c1c57732d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT fk5519b36c1c57732d FOREIGN KEY (parent_cat_id) REFERENCES product_categories(product_category_id);


--
-- TOC entry 2277 (class 2606 OID 38371)
-- Dependencies: 2139 1679 1649
-- Name: fk5519b36c7a956e19; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT fk5519b36c7a956e19 FOREIGN KEY (pattern_mask_format_id) REFERENCES pattern_mask_formats(pattern_mask_format_id);


--
-- TOC entry 2255 (class 2606 OID 37418)
-- Dependencies: 1644 1642 2073
-- Name: fk5f8caf2a59f71c23; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_item_match
    ADD CONSTRAINT fk5f8caf2a59f71c23 FOREIGN KEY (orderconfirmationitem_confirmation_item_id) REFERENCES order_confirmation_items(confirmation_item_id);


--
-- TOC entry 2256 (class 2606 OID 37423)
-- Dependencies: 2097 1644 1652
-- Name: fk5f8caf2a867a1de; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_item_match
    ADD CONSTRAINT fk5f8caf2a867a1de FOREIGN KEY (purchaseorderitem_order_item_id) REFERENCES purchase_order_items(order_item_id);


--
-- TOC entry 2348 (class 2606 OID 37428)
-- Dependencies: 1664 2127 1664
-- Name: fk6a68e0844bb6904; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk6a68e0844bb6904 FOREIGN KEY (creator_id) REFERENCES users(user_id);


--
-- TOC entry 2349 (class 2606 OID 37433)
-- Dependencies: 1664 2127 1664
-- Name: fk6a68e08f9f4b72; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk6a68e08f9f4b72 FOREIGN KEY (creator_id) REFERENCES users(user_id);


--
-- TOC entry 2162 (class 2606 OID 37438)
-- Dependencies: 1634 2047 1619
-- Name: fk6b9ae64a3d8dbb7d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_partners
    ADD CONSTRAINT fk6b9ae64a3d8dbb7d FOREIGN KEY (id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2278 (class 2606 OID 37443)
-- Dependencies: 2039 1650 1632
-- Name: fk725e8d72b6365ea; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk725e8d72b6365ea FOREIGN KEY (supplier_id) REFERENCES data_object_links(data_object_link_id);


--
-- TOC entry 2279 (class 2606 OID 37448)
-- Dependencies: 2119 1661 1650
-- Name: fk725e8d7a330dfcf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk725e8d7a330dfcf FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2280 (class 2606 OID 37453)
-- Dependencies: 1650 1661 2119
-- Name: fk725e8d7f10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk725e8d7f10b9721 FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2300 (class 2606 OID 37473)
-- Dependencies: 1659 2115 1655
-- Name: fk7503fcd11ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk7503fcd11ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2301 (class 2606 OID 37478)
-- Dependencies: 1661 1655 2119
-- Name: fk7503fcd1a330dfcf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk7503fcd1a330dfcf FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2302 (class 2606 OID 37483)
-- Dependencies: 1661 1655 2119
-- Name: fk7503fcd1f10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk7503fcd1f10b9721 FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2247 (class 2606 OID 37488)
-- Dependencies: 1642 1659 2115
-- Name: fk7e6ecbc71ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmation_items
    ADD CONSTRAINT fk7e6ecbc71ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2248 (class 2606 OID 37493)
-- Dependencies: 1659 2115 1642
-- Name: fk7e6ecbc73aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmation_items
    ADD CONSTRAINT fk7e6ecbc73aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2249 (class 2606 OID 37498)
-- Dependencies: 2119 1661 1642
-- Name: fk7e6ecbc7a330dfcf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmation_items
    ADD CONSTRAINT fk7e6ecbc7a330dfcf FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2257 (class 2606 OID 37503)
-- Dependencies: 1645 1994 1619
-- Name: fk8258b9a017025956; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk8258b9a017025956 FOREIGN KEY (organization_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2258 (class 2606 OID 37508)
-- Dependencies: 1645 2115 1659
-- Name: fk8258b9a0180e7eb9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk8258b9a0180e7eb9 FOREIGN KEY (organization_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2259 (class 2606 OID 37518)
-- Dependencies: 1645 1645 2079
-- Name: fk8258b9a08c46f1ed; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk8258b9a08c46f1ed FOREIGN KEY (registration_organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2187 (class 2606 OID 37523)
-- Dependencies: 2047 1632 1634
-- Name: fk9157692e2ff7d10e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_links
    ADD CONSTRAINT fk9157692e2ff7d10e FOREIGN KEY (linked_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2357 (class 2606 OID 37528)
-- Dependencies: 1667 2085 1647
-- Name: fk94f81e109757951; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouses
    ADD CONSTRAINT fk94f81e109757951 FOREIGN KEY (warehouseman_id) REFERENCES persons(partner_id);


--
-- TOC entry 2361 (class 2606 OID 38356)
-- Dependencies: 1680 2137 1667
-- Name: fk951433609f1800e1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouse_products
    ADD CONSTRAINT fk951433609f1800e1 FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2362 (class 2606 OID 38361)
-- Dependencies: 2119 1661 1680
-- Name: fk95143360a330dfcf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouse_products
    ADD CONSTRAINT fk95143360a330dfcf FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2307 (class 2606 OID 37543)
-- Dependencies: 2105 1656 1655
-- Name: fk98230d0e73d2d06a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_serial_numbers
    ADD CONSTRAINT fk98230d0e73d2d06a FOREIGN KEY (certificate_item_id) REFERENCES receipt_certificate_items(certificate_item_id);


--
-- TOC entry 2144 (class 2606 OID 37548)
-- Dependencies: 1996 1613 1620
-- Name: fk_addresses_city_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT fk_addresses_city_id FOREIGN KEY (city_id) REFERENCES cities(city_id);


--
-- TOC entry 2145 (class 2606 OID 37553)
-- Dependencies: 2027 1629 1613
-- Name: fk_addresses_country_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT fk_addresses_country_id FOREIGN KEY (country_id) REFERENCES countries(country_id);


--
-- TOC entry 2146 (class 2606 OID 37558)
-- Dependencies: 2047 1634 1613
-- Name: fk_addresses_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT fk_addresses_do_id FOREIGN KEY (address_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2147 (class 2606 OID 37563)
-- Dependencies: 1614 1634 2047
-- Name: fk_assembling_categories_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_categories
    ADD CONSTRAINT fk_assembling_categories_do FOREIGN KEY (assembling_category_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2148 (class 2606 OID 37568)
-- Dependencies: 1614 1614 1976
-- Name: fk_assembling_categories_parent_category; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_categories
    ADD CONSTRAINT fk_assembling_categories_parent_category FOREIGN KEY (parent_category_id) REFERENCES assembling_categories(assembling_category_id);


--
-- TOC entry 2149 (class 2606 OID 37573)
-- Dependencies: 1615 1616 1984
-- Name: fk_assembling_schema_item_values_as_item; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_item_values
    ADD CONSTRAINT fk_assembling_schema_item_values_as_item FOREIGN KEY (item_id) REFERENCES assembling_schema_items(item_id);


--
-- TOC entry 2150 (class 2606 OID 37578)
-- Dependencies: 1666 1615 2135
-- Name: fk_assembling_schema_item_values_vp; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_item_values
    ADD CONSTRAINT fk_assembling_schema_item_values_vp FOREIGN KEY (virtual_product_id) REFERENCES virtual_products(product_id);


--
-- TOC entry 2151 (class 2606 OID 37583)
-- Dependencies: 1659 1616 2115
-- Name: fk_assembling_schema_items_algorithm; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT fk_assembling_schema_items_algorithm FOREIGN KEY (algorithm_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2152 (class 2606 OID 37588)
-- Dependencies: 2115 1659 1616
-- Name: fk_assembling_schema_items_data_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT fk_assembling_schema_items_data_type FOREIGN KEY (data_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2153 (class 2606 OID 37593)
-- Dependencies: 1986 1616 1617
-- Name: fk_assembling_schema_items_owner; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT fk_assembling_schema_items_owner FOREIGN KEY (assembling_schema_id) REFERENCES assembling_schemas(product_id);


--
-- TOC entry 2154 (class 2606 OID 37598)
-- Dependencies: 1976 1614 1617
-- Name: fk_assembling_schemas_categories; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT fk_assembling_schemas_categories FOREIGN KEY (category_id) REFERENCES assembling_categories(assembling_category_id);


--
-- TOC entry 2155 (class 2606 OID 37603)
-- Dependencies: 1617 2135 1666
-- Name: fk_assembling_schemas_virtual_products; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT fk_assembling_schemas_virtual_products FOREIGN KEY (product_id) REFERENCES virtual_products(product_id);


--
-- TOC entry 2160 (class 2606 OID 37608)
-- Dependencies: 1974 1618 1613
-- Name: fk_bank_details_bank_branch; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk_bank_details_bank_branch FOREIGN KEY (bank_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2161 (class 2606 OID 37613)
-- Dependencies: 1634 2047 1618
-- Name: fk_bank_details_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk_bank_details_do_id FOREIGN KEY (bank_detail_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2166 (class 2606 OID 37618)
-- Dependencies: 1621 2010 1624
-- Name: fk_classified_objects_classifier_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classified_objects
    ADD CONSTRAINT fk_classified_objects_classifier_id FOREIGN KEY (classifier_id) REFERENCES classifiers(classifier_id);


--
-- TOC entry 2168 (class 2606 OID 37623)
-- Dependencies: 1622 1633 2043
-- Name: fk_classifier_applied_for_dot_dot_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifier_applied_for_dot
    ADD CONSTRAINT fk_classifier_applied_for_dot_dot_id FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2169 (class 2606 OID 37628)
-- Dependencies: 1623 1634 2047
-- Name: fk_classifier_groups_cg_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifier_groups
    ADD CONSTRAINT fk_classifier_groups_cg_id FOREIGN KEY (classifier_group_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2171 (class 2606 OID 37633)
-- Dependencies: 1624 1634 2047
-- Name: fk_classifiers_id_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT fk_classifiers_id_do_id FOREIGN KEY (classifier_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2174 (class 2606 OID 37638)
-- Dependencies: 1625 1659 2115
-- Name: fk_communication_contacts_comm_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT fk_communication_contacts_comm_type FOREIGN KEY (communication_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2175 (class 2606 OID 37643)
-- Dependencies: 1625 1634 2047
-- Name: fk_communication_contacts_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT fk_communication_contacts_do_id FOREIGN KEY (communication_contact_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2176 (class 2606 OID 37648)
-- Dependencies: 1659 2115 1626
-- Name: fk_complex_product_items_algorithm; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT fk_complex_product_items_algorithm FOREIGN KEY (applied_algorithm_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2177 (class 2606 OID 37653)
-- Dependencies: 1626 1627 2023
-- Name: fk_complex_product_items_cp; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT fk_complex_product_items_cp FOREIGN KEY (complex_product_id) REFERENCES complex_products(product_id);


--
-- TOC entry 2178 (class 2606 OID 37658)
-- Dependencies: 1626 1651 2095
-- Name: fk_complex_product_items_products; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT fk_complex_product_items_products FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2179 (class 2606 OID 37663)
-- Dependencies: 1627 2095 1651
-- Name: fk_complex_products_products; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_products
    ADD CONSTRAINT fk_complex_products_products FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2180 (class 2606 OID 37668)
-- Dependencies: 1627 2115 1659
-- Name: fk_complex_products_resource_bundle; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_products
    ADD CONSTRAINT fk_complex_products_resource_bundle FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2183 (class 2606 OID 37673)
-- Dependencies: 2047 1634 1628
-- Name: fk_contact_persons_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT fk_contact_persons_do_id FOREIGN KEY (contact_person_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2184 (class 2606 OID 37678)
-- Dependencies: 1628 2087 1648
-- Name: fk_contact_persons_position_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT fk_contact_persons_position_type FOREIGN KEY (position_type_id) REFERENCES position_types(position_type_id);


--
-- TOC entry 2186 (class 2606 OID 37683)
-- Dependencies: 2047 1634 1631
-- Name: fk_data_object_details_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_details
    ADD CONSTRAINT fk_data_object_details_do_id FOREIGN KEY (data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2188 (class 2606 OID 37688)
-- Dependencies: 1634 2047 1632
-- Name: fk_data_object_links_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_links
    ADD CONSTRAINT fk_data_object_links_do_id FOREIGN KEY (data_object_link_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2189 (class 2606 OID 37693)
-- Dependencies: 1632 2047 1634
-- Name: fk_data_object_links_linked_object; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_links
    ADD CONSTRAINT fk_data_object_links_linked_object FOREIGN KEY (linked_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2190 (class 2606 OID 37698)
-- Dependencies: 1633 1634 2043
-- Name: fk_data_objects_do_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk_data_objects_do_type FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2191 (class 2606 OID 37703)
-- Dependencies: 2047 1634 1634
-- Name: fk_data_objects_linked_data_object_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk_data_objects_linked_data_object_id FOREIGN KEY (linked_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2192 (class 2606 OID 37708)
-- Dependencies: 2047 1634 1634
-- Name: fk_data_objects_parent_data_object_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk_data_objects_parent_data_object_id FOREIGN KEY (parent_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2193 (class 2606 OID 37713)
-- Dependencies: 1638 1635 2058
-- Name: fk_delivery_certificate; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_assignments
    ADD CONSTRAINT fk_delivery_certificate FOREIGN KEY (delivery_certificate_id) REFERENCES delivery_certificates(delivery_certificate_id);


--
-- TOC entry 2194 (class 2606 OID 37718)
-- Dependencies: 2058 1638 1636
-- Name: fk_delivery_certificate_items_delivery_cert; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fk_delivery_certificate_items_delivery_cert FOREIGN KEY (parent_id) REFERENCES delivery_certificates(delivery_certificate_id);


--
-- TOC entry 2195 (class 2606 OID 37723)
-- Dependencies: 1634 1636 2047
-- Name: fk_delivery_certificate_items_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fk_delivery_certificate_items_do_id FOREIGN KEY (certificate_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2196 (class 2606 OID 37728)
-- Dependencies: 2115 1636 1659
-- Name: fk_delivery_certificate_items_measure_unit; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fk_delivery_certificate_items_measure_unit FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2197 (class 2606 OID 37733)
-- Dependencies: 1661 2119 1636
-- Name: fk_delivery_certificate_items_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fk_delivery_certificate_items_product FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2198 (class 2606 OID 37738)
-- Dependencies: 1637 2051 1636
-- Name: fk_delivery_certificate_serial_numbers_dci; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_serial_numbers
    ADD CONSTRAINT fk_delivery_certificate_serial_numbers_dci FOREIGN KEY (certificate_item_id) REFERENCES delivery_certificate_items(certificate_item_id);


--
-- TOC entry 2206 (class 2606 OID 37743)
-- Dependencies: 2047 1634 1638
-- Name: fk_delivery_certificates_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_delivery_certificates_do_id FOREIGN KEY (delivery_certificate_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2207 (class 2606 OID 37748)
-- Dependencies: 1659 2115 1638
-- Name: fk_delivery_certificates_reason; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_delivery_certificates_reason FOREIGN KEY (delivery_cert_reason_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2208 (class 2606 OID 37753)
-- Dependencies: 1619 1994 1638
-- Name: fk_delivery_certificates_recipient; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_delivery_certificates_recipient FOREIGN KEY (recipient_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2209 (class 2606 OID 37758)
-- Dependencies: 1638 1659 2115
-- Name: fk_delivery_certificates_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_delivery_certificates_type FOREIGN KEY (delivery_cert_method_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2210 (class 2606 OID 37763)
-- Dependencies: 1638 1667 2137
-- Name: fk_delivery_certificates_warehouse; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_delivery_certificates_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2211 (class 2606 OID 37768)
-- Dependencies: 1638 1613 1974
-- Name: fk_forwarder_address_constraint; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_forwarder_address_constraint FOREIGN KEY (forwarder_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2217 (class 2606 OID 37773)
-- Dependencies: 1640 1634 2047
-- Name: fk_invoice_items_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoice_items
    ADD CONSTRAINT fk_invoice_items_do_id FOREIGN KEY (invoice_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2218 (class 2606 OID 37778)
-- Dependencies: 1640 1641 2069
-- Name: fk_invoice_items_invoice_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoice_items
    ADD CONSTRAINT fk_invoice_items_invoice_id FOREIGN KEY (parent_id) REFERENCES invoices(invoice_id);


--
-- TOC entry 2219 (class 2606 OID 37783)
-- Dependencies: 1640 1659 2115
-- Name: fk_invoice_items_measure_unit_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoice_items
    ADD CONSTRAINT fk_invoice_items_measure_unit_id FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2220 (class 2606 OID 37788)
-- Dependencies: 1640 1661 2119
-- Name: fk_invoice_items_product_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoice_items
    ADD CONSTRAINT fk_invoice_items_product_id FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2221 (class 2606 OID 37793)
-- Dependencies: 1640 1667 2137
-- Name: fk_invoice_items_warehouse_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoice_items
    ADD CONSTRAINT fk_invoice_items_warehouse_id FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2236 (class 2606 OID 37798)
-- Dependencies: 1641 1613 1974
-- Name: fk_invoices_branch_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_branch_id FOREIGN KEY (branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2237 (class 2606 OID 37803)
-- Dependencies: 1641 1659 2115
-- Name: fk_invoices_currency_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_currency_id FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2238 (class 2606 OID 37808)
-- Dependencies: 1641 1659 2115
-- Name: fk_invoices_delivery_type_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_delivery_type_id FOREIGN KEY (delivery_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2239 (class 2606 OID 37813)
-- Dependencies: 1634 1641 2047
-- Name: fk_invoices_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_do_id FOREIGN KEY (invoice_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2240 (class 2606 OID 37818)
-- Dependencies: 1641 1659 2115
-- Name: fk_invoices_doc_delivery_method_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_doc_delivery_method_id FOREIGN KEY (doc_delivery_method_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2241 (class 2606 OID 37823)
-- Dependencies: 1641 1659 2115
-- Name: fk_invoices_invoice_type_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_invoice_type_id FOREIGN KEY (invoice_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2242 (class 2606 OID 37828)
-- Dependencies: 1641 1659 2115
-- Name: fk_invoices_payment_terms_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_payment_terms_id FOREIGN KEY (payment_terms_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2243 (class 2606 OID 37833)
-- Dependencies: 1659 1641 2115
-- Name: fk_invoices_payment_type_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_payment_type_id FOREIGN KEY (payment_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2244 (class 2606 OID 37838)
-- Dependencies: 1641 2115 1659
-- Name: fk_invoices_status_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_status_id FOREIGN KEY (status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2245 (class 2606 OID 37843)
-- Dependencies: 2115 1641 1659
-- Name: fk_invoices_transportation_method_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_transportation_method_id FOREIGN KEY (transportation_method_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2246 (class 2606 OID 37848)
-- Dependencies: 1659 2115 1641
-- Name: fk_invoices_vat_condition_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_vat_condition_id FOREIGN KEY (vat_condition_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2263 (class 2606 OID 37853)
-- Dependencies: 2047 1646 1634
-- Name: fk_passports_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT fk_passports_do_id FOREIGN KEY (passport_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2264 (class 2606 OID 37858)
-- Dependencies: 1613 1646 1974
-- Name: fk_passports_issuer_branch; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT fk_passports_issuer_branch FOREIGN KEY (issuer_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2265 (class 2606 OID 37863)
-- Dependencies: 2115 1646 1659
-- Name: fk_passports_pass_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT fk_passports_pass_type FOREIGN KEY (passport_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2272 (class 2606 OID 37868)
-- Dependencies: 1648 2047 1634
-- Name: fk_position_types_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY position_types
    ADD CONSTRAINT fk_position_types_do_id FOREIGN KEY (position_type_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2276 (class 2606 OID 37878)
-- Dependencies: 1649 2047 1634
-- Name: fk_product_categories_product_category_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT fk_product_categories_product_category_id FOREIGN KEY (product_category_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2324 (class 2606 OID 37883)
-- Dependencies: 1661 1649 2089
-- Name: fk_product_category_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_product_category_id FOREIGN KEY (category_id) REFERENCES product_categories(product_category_id);


--
-- TOC entry 2281 (class 2606 OID 37888)
-- Dependencies: 2119 1661 1650
-- Name: fk_product_suppliers_product_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk_product_suppliers_product_id FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2282 (class 2606 OID 37893)
-- Dependencies: 1632 2039 1650
-- Name: fk_product_suppliers_supplier; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk_product_suppliers_supplier FOREIGN KEY (supplier_id) REFERENCES data_object_links(data_object_link_id);


--
-- TOC entry 2283 (class 2606 OID 37898)
-- Dependencies: 1634 2047 1651
-- Name: fk_products1_product_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_products1_product_id FOREIGN KEY (product_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2325 (class 2606 OID 37903)
-- Dependencies: 1661 2115 1659
-- Name: fk_products_color_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_products_color_id FOREIGN KEY (product_color_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2326 (class 2606 OID 37908)
-- Dependencies: 1659 2115 1661
-- Name: fk_products_dimension_unit_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_products_dimension_unit_id FOREIGN KEY (dimension_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2327 (class 2606 OID 37913)
-- Dependencies: 2115 1659 1661
-- Name: fk_products_measure_unit_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_products_measure_unit_id FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2328 (class 2606 OID 37923)
-- Dependencies: 1661 2115 1659
-- Name: fk_products_weight_unit_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_products_weight_unit_id FOREIGN KEY (weight_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2284 (class 2606 OID 37928)
-- Dependencies: 2047 1634 1652
-- Name: fk_purchase_order_items_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fk_purchase_order_items_do_id FOREIGN KEY (order_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2285 (class 2606 OID 37933)
-- Dependencies: 1659 1652 2115
-- Name: fk_purchase_order_items_measure_unit; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fk_purchase_order_items_measure_unit FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2286 (class 2606 OID 37938)
-- Dependencies: 1661 1652 2119
-- Name: fk_purchase_order_items_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fk_purchase_order_items_product FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2298 (class 2606 OID 37943)
-- Dependencies: 2119 1654 1661
-- Name: fk_real_products_simple_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY real_products
    ADD CONSTRAINT fk_real_products_simple_product FOREIGN KEY (simple_product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2299 (class 2606 OID 37948)
-- Dependencies: 2135 1654 1666
-- Name: fk_real_products_virtual_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY real_products
    ADD CONSTRAINT fk_real_products_virtual_product FOREIGN KEY (product_id) REFERENCES virtual_products(product_id);


--
-- TOC entry 2303 (class 2606 OID 37953)
-- Dependencies: 2047 1655 1634
-- Name: fk_receipt_certificate_items_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk_receipt_certificate_items_do_id FOREIGN KEY (certificate_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2304 (class 2606 OID 37958)
-- Dependencies: 1659 1655 2115
-- Name: fk_receipt_certificate_items_measure_unit; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk_receipt_certificate_items_measure_unit FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2305 (class 2606 OID 37963)
-- Dependencies: 2119 1655 1661
-- Name: fk_receipt_certificate_items_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk_receipt_certificate_items_product FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2306 (class 2606 OID 37968)
-- Dependencies: 1655 2109 1657
-- Name: fk_receipt_certificate_items_receipt_cert; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk_receipt_certificate_items_receipt_cert FOREIGN KEY (parent_id) REFERENCES receipt_certificates(receipt_certificate_id);


--
-- TOC entry 2308 (class 2606 OID 37973)
-- Dependencies: 2105 1655 1656
-- Name: fk_receipt_certificate_serial_numbers_dci; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_serial_numbers
    ADD CONSTRAINT fk_receipt_certificate_serial_numbers_dci FOREIGN KEY (certificate_item_id) REFERENCES receipt_certificate_items(certificate_item_id);


--
-- TOC entry 2309 (class 2606 OID 37978)
-- Dependencies: 1632 2039 1657
-- Name: fk_receipt_certificates_deliverer; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_deliverer FOREIGN KEY (deliverer_id) REFERENCES data_object_links(data_object_link_id);


--
-- TOC entry 2310 (class 2606 OID 37983)
-- Dependencies: 1634 2047 1657
-- Name: fk_receipt_certificates_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_do_id FOREIGN KEY (receipt_certificate_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2311 (class 2606 OID 37988)
-- Dependencies: 1657 1659 2115
-- Name: fk_receipt_certificates_reason; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_reason FOREIGN KEY (receipt_cert_reason_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2312 (class 2606 OID 37993)
-- Dependencies: 1657 1659 2115
-- Name: fk_receipt_certificates_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_type FOREIGN KEY (receipt_cert_method_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2313 (class 2606 OID 37998)
-- Dependencies: 1657 1667 2137
-- Name: fk_receipt_certificates_warehouse; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2212 (class 2606 OID 38003)
-- Dependencies: 1974 1613 1638
-- Name: fk_recipient_address_constraint; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_recipient_address_constraint FOREIGN KEY (recipient_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2323 (class 2606 OID 38008)
-- Dependencies: 1639 2062 1659
-- Name: fk_resource_bundle_enum_class_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY resource_bundle
    ADD CONSTRAINT fk_resource_bundle_enum_class_id FOREIGN KEY (enum_class_id) REFERENCES enum_classes(enum_class_id);


--
-- TOC entry 2329 (class 2606 OID 38013)
-- Dependencies: 1661 1651 2095
-- Name: fk_simple_products_products; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_simple_products_products FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2350 (class 2606 OID 38018)
-- Dependencies: 2127 1664 1664
-- Name: fk_users_creator_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk_users_creator_id FOREIGN KEY (creator_id) REFERENCES users(user_id);


--
-- TOC entry 2356 (class 2606 OID 38023)
-- Dependencies: 1634 1666 2047
-- Name: fk_virtual_products_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY virtual_products
    ADD CONSTRAINT fk_virtual_products_do FOREIGN KEY (product_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2358 (class 2606 OID 38028)
-- Dependencies: 1613 1667 1974
-- Name: fk_warehouses_address_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouses
    ADD CONSTRAINT fk_warehouses_address_id FOREIGN KEY (address_id) REFERENCES addresses(address_id);


--
-- TOC entry 2359 (class 2606 OID 38033)
-- Dependencies: 1634 2047 1667
-- Name: fk_warehouses_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouses
    ADD CONSTRAINT fk_warehouses_do_id FOREIGN KEY (warehouse_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2287 (class 2606 OID 38038)
-- Dependencies: 1652 1659 2115
-- Name: fka00989511ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fka00989511ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2288 (class 2606 OID 38043)
-- Dependencies: 1652 1659 2115
-- Name: fka00989513aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fka00989513aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2289 (class 2606 OID 38048)
-- Dependencies: 1652 1661 2119
-- Name: fka0098951a330dfcf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fka0098951a330dfcf FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2290 (class 2606 OID 38053)
-- Dependencies: 1661 2119 1652
-- Name: fka0098951f10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fka0098951f10b9721 FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2291 (class 2606 OID 38093)
-- Dependencies: 1659 2115 1653
-- Name: fkc307e7e31808129a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e31808129a FOREIGN KEY (doc_delivery_method_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2292 (class 2606 OID 38098)
-- Dependencies: 1974 1613 1653
-- Name: fkc307e7e327a66c93; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e327a66c93 FOREIGN KEY (branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2293 (class 2606 OID 38103)
-- Dependencies: 1653 2085 1647
-- Name: fkc307e7e34da6f8bc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e34da6f8bc FOREIGN KEY (creator_id) REFERENCES persons(partner_id);


--
-- TOC entry 2294 (class 2606 OID 38108)
-- Dependencies: 1653 1994 1619
-- Name: fkc307e7e35aa049f4; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e35aa049f4 FOREIGN KEY (supplier_partner_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2295 (class 2606 OID 38113)
-- Dependencies: 1653 2025 1628
-- Name: fkc307e7e37ebcc009; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e37ebcc009 FOREIGN KEY (supplier_contact_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2296 (class 2606 OID 38118)
-- Dependencies: 2115 1659 1653
-- Name: fkc307e7e39c49320d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e39c49320d FOREIGN KEY (status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2297 (class 2606 OID 38123)
-- Dependencies: 1653 2085 1647
-- Name: fkc307e7e3fd5b3613; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e3fd5b3613 FOREIGN KEY (sender_id) REFERENCES persons(partner_id);


--
-- TOC entry 2330 (class 2606 OID 38128)
-- Dependencies: 1659 2115 1661
-- Name: fkc42bd1641ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd1641ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2331 (class 2606 OID 38133)
-- Dependencies: 1659 2115 1661
-- Name: fkc42bd16427acd874; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd16427acd874 FOREIGN KEY (weight_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2332 (class 2606 OID 38138)
-- Dependencies: 1649 2089 1661
-- Name: fkc42bd1646e2f83d0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd1646e2f83d0 FOREIGN KEY (category_id) REFERENCES product_categories(product_category_id);


--
-- TOC entry 2333 (class 2606 OID 38143)
-- Dependencies: 1994 1661 1619
-- Name: fkc42bd1646e436697; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd1646e436697 FOREIGN KEY (producer_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2342 (class 2606 OID 38366)
-- Dependencies: 1661 2139 1679
-- Name: fkc42bd1647a956e19; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd1647a956e19 FOREIGN KEY (pattern_mask_format_id) REFERENCES pattern_mask_formats(pattern_mask_format_id);


--
-- TOC entry 2334 (class 2606 OID 38153)
-- Dependencies: 1661 2115 1659
-- Name: fkc42bd1648f60524c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd1648f60524c FOREIGN KEY (product_color_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2335 (class 2606 OID 38158)
-- Dependencies: 1659 1661 2115
-- Name: fkc42bd1649d6c3562; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd1649d6c3562 FOREIGN KEY (dimension_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2266 (class 2606 OID 38168)
-- Dependencies: 1645 1646 2079
-- Name: fkc84af6a1db08a2d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT fkc84af6a1db08a2d FOREIGN KEY (issuer_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2268 (class 2606 OID 38173)
-- Dependencies: 1647 1629 2027
-- Name: fkd78fcfbe16ffc779; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT fkd78fcfbe16ffc779 FOREIGN KEY (birth_place_country_id) REFERENCES countries(country_id);


--
-- TOC entry 2269 (class 2606 OID 38178)
-- Dependencies: 2115 1659 1647
-- Name: fkd78fcfbe2663e8de; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT fkd78fcfbe2663e8de FOREIGN KEY (gender_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2270 (class 2606 OID 38183)
-- Dependencies: 1647 1620 1996
-- Name: fkd78fcfbe4250f8bb; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT fkd78fcfbe4250f8bb FOREIGN KEY (birth_place_city_id) REFERENCES cities(city_id);


--
-- TOC entry 2271 (class 2606 OID 38188)
-- Dependencies: 1994 1647 1619
-- Name: fkd78fcfbed213c5a1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT fkd78fcfbed213c5a1 FOREIGN KEY (partner_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2336 (class 2606 OID 38193)
-- Dependencies: 1659 1661 2115
-- Name: fke81099511ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke81099511ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2337 (class 2606 OID 38198)
-- Dependencies: 2115 1661 1659
-- Name: fke810995127acd874; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke810995127acd874 FOREIGN KEY (weight_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2338 (class 2606 OID 38203)
-- Dependencies: 2089 1661 1649
-- Name: fke81099516e2f83d0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke81099516e2f83d0 FOREIGN KEY (category_id) REFERENCES product_categories(product_category_id);


--
-- TOC entry 2339 (class 2606 OID 38208)
-- Dependencies: 1661 1619 1994
-- Name: fke81099516e436697; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke81099516e436697 FOREIGN KEY (producer_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2340 (class 2606 OID 38218)
-- Dependencies: 1659 2115 1661
-- Name: fke81099518f60524c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke81099518f60524c FOREIGN KEY (product_color_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2341 (class 2606 OID 38223)
-- Dependencies: 2115 1661 1659
-- Name: fke81099519d6c3562; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke81099519d6c3562 FOREIGN KEY (dimension_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2314 (class 2606 OID 38228)
-- Dependencies: 1647 1657 2085
-- Name: fke9334463157c075; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke9334463157c075 FOREIGN KEY (forwarder_contact_id) REFERENCES persons(partner_id);


--
-- TOC entry 2315 (class 2606 OID 38233)
-- Dependencies: 1657 1647 2085
-- Name: fke93344634da6f8bc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke93344634da6f8bc FOREIGN KEY (creator_id) REFERENCES persons(partner_id);


--
-- TOC entry 2316 (class 2606 OID 38238)
-- Dependencies: 1657 1647 2085
-- Name: fke93344636faaa615; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke93344636faaa615 FOREIGN KEY (deliverer_contact_id) REFERENCES persons(partner_id);


--
-- TOC entry 2317 (class 2606 OID 38243)
-- Dependencies: 1657 1632 2039
-- Name: fke933446370294164; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke933446370294164 FOREIGN KEY (deliverer_id) REFERENCES data_object_links(data_object_link_id);


--
-- TOC entry 2318 (class 2606 OID 38248)
-- Dependencies: 1657 1667 2137
-- Name: fke93344639f1800e1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke93344639f1800e1 FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2319 (class 2606 OID 38253)
-- Dependencies: 1657 1659 2115
-- Name: fke9334463d6755f5b; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke9334463d6755f5b FOREIGN KEY (receipt_cert_method_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2320 (class 2606 OID 38258)
-- Dependencies: 1657 1645 2079
-- Name: fke9334463f2569c14; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke9334463f2569c14 FOREIGN KEY (forwarder_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2321 (class 2606 OID 38263)
-- Dependencies: 1657 1659 2115
-- Name: fke9334463fe9be307; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke9334463fe9be307 FOREIGN KEY (receipt_cert_reason_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2260 (class 2606 OID 38268)
-- Dependencies: 1645 1974 1613
-- Name: organizations_administration_address_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT organizations_administration_address_id_fkey FOREIGN KEY (administration_address_id) REFERENCES addresses(address_id) ON DELETE SET NULL;


--
-- TOC entry 2261 (class 2606 OID 38273)
-- Dependencies: 1645 1659 2115
-- Name: organizations_currency_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT organizations_currency_id_fkey FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2262 (class 2606 OID 38278)
-- Dependencies: 1974 1613 1645
-- Name: organizations_registration_address_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT organizations_registration_address_id_fkey FOREIGN KEY (registration_address_id) REFERENCES addresses(address_id) ON DELETE SET NULL;


--
-- TOC entry 2267 (class 2606 OID 38283)
-- Dependencies: 1646 1647 2085
-- Name: passports_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT passports_parent_id_fkey FOREIGN KEY (parent_id) REFERENCES persons(partner_id) ON DELETE CASCADE;


--
-- TOC entry 2273 (class 2606 OID 38293)
-- Dependencies: 1648 1648 2087
-- Name: position_types_parent_position_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY position_types
    ADD CONSTRAINT position_types_parent_position_type_id_fkey FOREIGN KEY (parent_position_type_id) REFERENCES position_types(position_type_id);


--
-- TOC entry 2274 (class 2606 OID 38298)
-- Dependencies: 1648 1662 2121
-- Name: position_types_user_group_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY position_types
    ADD CONSTRAINT position_types_user_group_id_fkey FOREIGN KEY (user_group_id) REFERENCES user_groups(user_group_id) ON DELETE SET NULL;


--
-- TOC entry 2345 (class 2606 OID 38303)
-- Dependencies: 1663 1659 2115
-- Name: user_rights_special_permission_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights
    ADD CONSTRAINT user_rights_special_permission_id_fkey FOREIGN KEY (special_permission_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2346 (class 2606 OID 38308)
-- Dependencies: 1663 1662 2121
-- Name: user_rights_user_group_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights
    ADD CONSTRAINT user_rights_user_group_id_fkey FOREIGN KEY (user_group_id) REFERENCES user_groups(user_group_id) ON DELETE CASCADE;


--
-- TOC entry 2347 (class 2606 OID 38313)
-- Dependencies: 1663 2127 1664
-- Name: user_rights_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights
    ADD CONSTRAINT user_rights_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE;


--
-- TOC entry 2352 (class 2606 OID 38318)
-- Dependencies: 1645 1665 2079
-- Name: users_organizations_organization_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users_organizations
    ADD CONSTRAINT users_organizations_organization_id_fkey FOREIGN KEY (organization_id) REFERENCES organizations(organization_id) ON DELETE CASCADE;


--
-- TOC entry 2355 (class 2606 OID 38381)
-- Dependencies: 1647 1665 2085
-- Name: users_organizations_person_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users_organizations
    ADD CONSTRAINT users_organizations_person_id_fkey FOREIGN KEY (person_id_todelete) REFERENCES persons(partner_id) ON DELETE CASCADE;


--
-- TOC entry 2353 (class 2606 OID 38328)
-- Dependencies: 1662 2121 1665
-- Name: users_organizations_user_group_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users_organizations
    ADD CONSTRAINT users_organizations_user_group_id_fkey FOREIGN KEY (user_group_id) REFERENCES user_groups(user_group_id) ON DELETE SET NULL;


--
-- TOC entry 2354 (class 2606 OID 38333)
-- Dependencies: 1664 2127 1665
-- Name: users_organizations_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users_organizations
    ADD CONSTRAINT users_organizations_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE;


--
-- TOC entry 2351 (class 2606 OID 38376)
-- Dependencies: 2085 1647 1664
-- Name: users_person_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_person_id_fkey FOREIGN KEY (person_id) REFERENCES persons(partner_id);


--
-- TOC entry 2424 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: -
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2008-08-03 19:39:05

--
-- PostgreSQL database dump complete
--

