--
-- PostgreSQL database dump
--

-- Started on 2009-11-05 12:27:31

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

DROP DATABASE acacia;

CREATE DATABASE acacia
  WITH OWNER = postgres
       ENCODING = 'UTF8';

--
-- TOC entry 1704 (class 1259 OID 306677)
-- Dependencies: 6
-- Name: addresses; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE addresses (
    address_id uuid NOT NULL,
    business_partner_id uuid NOT NULL,
    address_name character varying(64) NOT NULL,
    country_id uuid,
    city_id uuid,
    postal_code character varying(16),
    postal_address character varying(128)
);


--
-- TOC entry 1705 (class 1259 OID 306680)
-- Dependencies: 6
-- Name: assembling_categories; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE assembling_categories (
    assembling_category_id uuid NOT NULL,
    parent_id uuid NOT NULL,
    category_code character varying(50) NOT NULL,
    category_name character varying(100) NOT NULL,
    description text,
    parent_category_id uuid
);


--
-- TOC entry 1706 (class 1259 OID 306686)
-- Dependencies: 6
-- Name: assembling_messages; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE assembling_messages (
    message_id uuid NOT NULL,
    organization_id uuid NOT NULL,
    message_code character varying(50) NOT NULL,
    message_label character varying(50) NOT NULL,
    selection_text character varying(100) NOT NULL,
    selection_title character varying(100) NOT NULL,
    input_text character varying(100) NOT NULL,
    input_title character varying(100) NOT NULL,
    description text
);


--
-- TOC entry 1707 (class 1259 OID 306692)
-- Dependencies: 2084 6
-- Name: assembling_schema_item_values; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE assembling_schema_item_values (
    item_value_id uuid NOT NULL,
    item_id uuid NOT NULL,
    min_constraint bytea,
    max_constraint bytea,
    virtual_product_id uuid NOT NULL,
    quantity numeric(19,4) DEFAULT 1 NOT NULL
);


--
-- TOC entry 1708 (class 1259 OID 306699)
-- Dependencies: 2085 6
-- Name: assembling_schema_items; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE assembling_schema_items (
    item_id uuid NOT NULL,
    assembling_schema_id uuid NOT NULL,
    algorithm_id integer NOT NULL,
    message_id uuid,
    min_selections integer,
    max_selections integer,
    quantity numeric(19,4) DEFAULT 1 NOT NULL,
    default_value bytea,
    data_type_id integer NOT NULL,
    description text
);


--
-- TOC entry 1709 (class 1259 OID 306706)
-- Dependencies: 2086 2087 6
-- Name: assembling_schemas; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE assembling_schemas (
    product_id uuid NOT NULL,
    category_id uuid NOT NULL,
    schema_code character varying(50) NOT NULL,
    schema_name character varying(100) NOT NULL,
    is_obsolete boolean DEFAULT false NOT NULL,
    measure_unit_id integer NOT NULL,
    description text,
    is_applicable boolean DEFAULT false NOT NULL
);


--
-- TOC entry 1710 (class 1259 OID 306714)
-- Dependencies: 2088 2089 6
-- Name: bank_details; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE bank_details (
    bank_detail_id uuid NOT NULL,
    address_id uuid NOT NULL,
    is_default boolean DEFAULT false NOT NULL,
    currency_id integer,
    bank_id uuid NOT NULL,
    bank_branch_id uuid NOT NULL,
    bank_account character varying(22),
    bank_contact_id uuid,
    bic character varying(12),
    iban character varying DEFAULT 50,
    swift_code character varying(50)
);


--
-- TOC entry 1711 (class 1259 OID 306722)
-- Dependencies: 6
-- Name: banknote_quantity; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE banknote_quantity (
    banknote_quantity_id uuid NOT NULL,
    cash_reconcile_id uuid NOT NULL,
    quantity integer NOT NULL,
    currency_nominal_id uuid NOT NULL
);


--
-- TOC entry 1712 (class 1259 OID 306725)
-- Dependencies: 6
-- Name: business_document_status_log; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE business_document_status_log (
    document_id uuid NOT NULL,
    document_status_id integer NOT NULL,
    action_time timestamp with time zone NOT NULL,
    officer_id uuid NOT NULL
);


--
-- TOC entry 1713 (class 1259 OID 306728)
-- Dependencies: 6
-- Name: business_documents; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE business_documents (
    document_id uuid NOT NULL,
    document_type_id integer NOT NULL,
    document_status_id integer NOT NULL,
    discriminator_id character varying(4) NOT NULL,
    publisher_id uuid NOT NULL,
    publisher_branch_id uuid NOT NULL,
    publisher_officer_id uuid NOT NULL,
    document_number bigint,
    document_date date,
    creation_time timestamp with time zone
);


--
-- TOC entry 1714 (class 1259 OID 306731)
-- Dependencies: 6
-- Name: business_partners; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE business_partners (
    business_partner_id uuid NOT NULL,
    parent_business_partner_id uuid NOT NULL,
    default_currency_id integer NOT NULL,
    discriminator_id character(2) NOT NULL
);


--
-- TOC entry 2926 (class 0 OID 0)
-- Dependencies: 1714
-- Name: TABLE business_partners; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE business_partners IS 'O - for Organization
P - for Person';


--
-- TOC entry 1715 (class 1259 OID 306734)
-- Dependencies: 6
-- Name: business_unit_addresses; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE business_unit_addresses (
    business_unit_address_id uuid NOT NULL,
    business_unit_id uuid NOT NULL,
    address_id uuid NOT NULL,
    address_type_id integer NOT NULL,
    phone_id uuid,
    fax_id uuid,
    email_id uuid
);


--
-- TOC entry 2927 (class 0 OID 0)
-- Dependencies: 1715
-- Name: COLUMN business_unit_addresses.address_type_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN business_unit_addresses.address_type_id IS 'Bill To, Ship To, Other';


--
-- TOC entry 1716 (class 1259 OID 306737)
-- Dependencies: 2090 2091 2092 6
-- Name: business_units; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE business_units (
    business_unit_id uuid NOT NULL,
    organization_id uuid NOT NULL,
    parent_business_unit_id uuid,
    is_root boolean DEFAULT false NOT NULL,
    business_unit_type_id integer NOT NULL,
    business_unit_name character varying(100) NOT NULL,
    is_disabled boolean DEFAULT false NOT NULL,
    division_name character varying(100),
    web_site character varying(255),
    CONSTRAINT check_business_units_parent_not_null CHECK ((((is_root = false) AND (parent_business_unit_id IS NOT NULL)) OR ((is_root = true) AND (parent_business_unit_id IS NULL))))
);


--
-- TOC entry 1717 (class 1259 OID 306743)
-- Dependencies: 6
-- Name: cash_reconcile; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE cash_reconcile (
    cash_reconcile_id uuid NOT NULL,
    initial_bank_balance numeric(19,4),
    initial_cash_balance numeric(19,4),
    period_bank_expenses numeric(19,4),
    period_bank_revenue numeric(19,4),
    period_cash_expenses numeric(19,4),
    period_cash_revenue numeric(19,4),
    cashier_id uuid NOT NULL,
    currency_id integer
);


--
-- TOC entry 1718 (class 1259 OID 306746)
-- Dependencies: 6
-- Name: cash_reconcile_payment_summary; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE cash_reconcile_payment_summary (
    payment_summary_id uuid NOT NULL,
    amount numeric(19,4) NOT NULL,
    payment_type_id integer NOT NULL,
    currency_id integer NOT NULL,
    cash_reconcile_id uuid NOT NULL
);


--
-- TOC entry 1719 (class 1259 OID 306749)
-- Dependencies: 6
-- Name: cities; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE cities (
    city_id uuid NOT NULL,
    country_id uuid NOT NULL,
    city_name character varying(64) NOT NULL,
    postal_code character varying(8),
    city_code character varying(5) NOT NULL,
    city_phone_code character varying(6)
);


--
-- TOC entry 1720 (class 1259 OID 306752)
-- Dependencies: 6
-- Name: classified_objects; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE classified_objects (
    classifier_id uuid NOT NULL,
    classified_object_id uuid NOT NULL,
    description text
);


--
-- TOC entry 1721 (class 1259 OID 306758)
-- Dependencies: 6
-- Name: classifier_applied_for_dot; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE classifier_applied_for_dot (
    classifier_id uuid NOT NULL,
    data_object_type_id integer NOT NULL
);


--
-- TOC entry 1722 (class 1259 OID 306761)
-- Dependencies: 2093 6
-- Name: classifier_groups; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE classifier_groups (
    classifier_group_id uuid NOT NULL,
    is_system_group boolean DEFAULT false NOT NULL,
    classifier_group_code character varying(32) NOT NULL,
    classifier_group_name character varying(100) NOT NULL,
    description text,
    business_partner_id uuid NOT NULL
);


--
-- TOC entry 1723 (class 1259 OID 306768)
-- Dependencies: 6
-- Name: classifiers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE classifiers (
    classifier_id uuid NOT NULL,
    business_partner_id uuid NOT NULL,
    classifier_code character varying(32) NOT NULL,
    classifier_name character varying(128) NOT NULL,
    description text,
    classifier_group_id uuid NOT NULL
);


--
-- TOC entry 1724 (class 1259 OID 306774)
-- Dependencies: 6
-- Name: communication_contacts; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE communication_contacts (
    communication_contact_id uuid NOT NULL,
    address_id uuid NOT NULL,
    communication_type_id integer NOT NULL,
    communication_value character varying(64) NOT NULL
);


--
-- TOC entry 2928 (class 0 OID 0)
-- Dependencies: 1724
-- Name: COLUMN communication_contacts.communication_type_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN communication_contacts.communication_type_id IS 'Email (Work, Private), Phone (Work, Home, Fax), Mobile Phone (Work, Private), VoIP (SIP, H.323), Instant Communications (ICQ, Skype, Google Talk, MSN), Other';


--
-- TOC entry 1725 (class 1259 OID 306777)
-- Dependencies: 6
-- Name: complex_product_items; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE complex_product_items (
    complex_product_item_id uuid NOT NULL,
    complex_product_id uuid NOT NULL,
    product_id uuid NOT NULL,
    quantity numeric(19,4) NOT NULL,
    unit_price numeric(19,4) NOT NULL,
    item_price numeric(19,4) NOT NULL,
    applied_algorithm_id integer NOT NULL,
    applied_value bytea,
    due_quantity numeric(19,4)
);


--
-- TOC entry 1726 (class 1259 OID 306783)
-- Dependencies: 6
-- Name: complex_products; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE complex_products (
    product_id uuid NOT NULL,
    applied_schema_id uuid NOT NULL,
    sale_price numeric(19,4) NOT NULL
);


--
-- TOC entry 1727 (class 1259 OID 306786)
-- Dependencies: 6
-- Name: contact_persons; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE contact_persons (
    contact_person_id uuid NOT NULL,
    address_id uuid NOT NULL,
    position_type_id uuid,
    person_id uuid NOT NULL
);


--
-- TOC entry 1728 (class 1259 OID 306789)
-- Dependencies: 6
-- Name: countries; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE countries (
    country_id uuid NOT NULL,
    country_name character varying(64) NOT NULL,
    country_code_a2 character(2) NOT NULL,
    country_code_a3 character(3) NOT NULL,
    country_code_n3 character(3),
    country_phone_code character varying(6),
    currency_id integer NOT NULL
);


--
-- TOC entry 1729 (class 1259 OID 306792)
-- Dependencies: 2094 6
-- Name: currency_exchange_rates; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE currency_exchange_rates (
    organization_id uuid NOT NULL,
    valid_from timestamp with time zone NOT NULL,
    from_currency_id integer NOT NULL,
    to_currency_id integer NOT NULL,
    valid_until timestamp with time zone,
    exchange_rate numeric(10,6) NOT NULL,
    fixed_exchange_rate boolean DEFAULT false NOT NULL
);


--
-- TOC entry 1730 (class 1259 OID 306796)
-- Dependencies: 6
-- Name: currency_nominal; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE currency_nominal (
    currency_nominal_id uuid NOT NULL,
    nominal_value numeric(19,4) NOT NULL,
    currency_id integer NOT NULL
);


--
-- TOC entry 1731 (class 1259 OID 306799)
-- Dependencies: 6
-- Name: customer_discount_items; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE customer_discount_items (
    customer_discount_item_id uuid NOT NULL,
    discount_percent numeric(7,6) NOT NULL,
    discriminator_id character(2)
);


--
-- TOC entry 1732 (class 1259 OID 306802)
-- Dependencies: 2095 6
-- Name: customer_discount_items_by_categories; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE customer_discount_items_by_categories (
    customer_discount_item_id uuid NOT NULL,
    customer_discount_id uuid NOT NULL,
    category_id uuid NOT NULL,
    include_heirs boolean DEFAULT false NOT NULL
);


--
-- TOC entry 1733 (class 1259 OID 306806)
-- Dependencies: 6
-- Name: customer_discount_items_by_products; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE customer_discount_items_by_products (
    customer_discount_item_id uuid NOT NULL,
    customer_discount_id uuid NOT NULL,
    product_id uuid NOT NULL
);


--
-- TOC entry 1734 (class 1259 OID 306809)
-- Dependencies: 6
-- Name: customer_discounts; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE customer_discounts (
    customer_discount_id uuid NOT NULL,
    discount_percent numeric(7,6),
    organization_id uuid NOT NULL,
    customer_id uuid NOT NULL
);


--
-- TOC entry 1735 (class 1259 OID 306812)
-- Dependencies: 6
-- Name: customer_payment_match; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE customer_payment_match (
    customer_payment_match_id uuid NOT NULL,
    amount numeric(19,4) NOT NULL,
    creation_time timestamp with time zone,
    matchnumber integer,
    customer_payment_id uuid NOT NULL,
    invoice_id uuid NOT NULL
);


--
-- TOC entry 1736 (class 1259 OID 306815)
-- Dependencies: 6
-- Name: customer_payments; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE customer_payments (
    payment_id uuid NOT NULL,
    amount numeric(19,4) NOT NULL,
    completed_at timestamp with time zone,
    created_at timestamp with time zone,
    description character varying(255),
    document_number numeric(19,0),
    matchedamount numeric(19,4),
    parent_id uuid,
    paymentaccount bytea,
    paymentreturn boolean NOT NULL,
    reference_no character varying(255) NOT NULL,
    transaction_date timestamp with time zone,
    transaction_fee numeric(19,4),
    creator_id uuid,
    payment_type_id integer,
    customer_id uuid NOT NULL,
    status_id integer,
    completor_id uuid,
    branch_id uuid,
    cashier_id uuid,
    customer_contact_id uuid,
    currency_id integer
);


--
-- TOC entry 1737 (class 1259 OID 306821)
-- Dependencies: 6
-- Name: data_object_details; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE data_object_details (
    data_object_id uuid NOT NULL,
    detail_code character varying(32) NOT NULL,
    detail_value character varying(64) NOT NULL,
    notes text
);


--
-- TOC entry 1738 (class 1259 OID 306827)
-- Dependencies: 6
-- Name: data_object_links; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE data_object_links (
    data_object_link_id uuid NOT NULL,
    parent_id uuid,
    linked_data_object_id uuid NOT NULL,
    link_name character varying(255) NOT NULL
);


--
-- TOC entry 1739 (class 1259 OID 306830)
-- Dependencies: 6
-- Name: data_object_permissions; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE data_object_permissions (
    organization_id uuid NOT NULL,
    data_object_id uuid NOT NULL,
    user_right_type_id integer NOT NULL,
    permission_id integer NOT NULL
);


--
-- TOC entry 1740 (class 1259 OID 306833)
-- Dependencies: 6
-- Name: data_object_type_permissions; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE data_object_type_permissions (
    organization_id uuid NOT NULL,
    data_object_type_id integer NOT NULL,
    user_right_type_id integer NOT NULL,
    permission_id integer NOT NULL
);


--
-- TOC entry 1741 (class 1259 OID 306836)
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
-- TOC entry 1742 (class 1259 OID 306842)
-- Dependencies: 2096 2097 2098 2099 2100 2101 6
-- Name: data_objects; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE data_objects (
    data_object_id uuid NOT NULL,
    data_object_version integer NOT NULL,
    data_object_type_id integer NOT NULL,
    creation_time timestamp with time zone DEFAULT now() NOT NULL,
    creator_id uuid NOT NULL,
    owner_id uuid NOT NULL,
    is_deleted boolean DEFAULT false NOT NULL,
    is_read_only boolean DEFAULT false NOT NULL,
    is_system boolean DEFAULT false NOT NULL,
    is_folder boolean DEFAULT false NOT NULL,
    is_link boolean DEFAULT false NOT NULL,
    parent_data_object_id uuid,
    linked_data_object_id uuid,
    child_counter integer,
    notes text,
    small_image_uri character varying(1024),
    small_image bytea,
    medium_image_uri character varying(1024),
    medium_image bytea,
    data_object_uri character varying(1024),
    order_position integer
);


--
-- TOC entry 1743 (class 1259 OID 306854)
-- Dependencies: 6
-- Name: db_properties; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE db_properties (
    access_level character varying(32) NOT NULL,
    related_object_id uuid NOT NULL,
    property_key character varying(64) NOT NULL,
    property_value bytea
);


--
-- TOC entry 1744 (class 1259 OID 306860)
-- Dependencies: 6
-- Name: delivery_certificate_assignments; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE delivery_certificate_assignments (
    delivery_certificate_id uuid NOT NULL,
    document_id uuid NOT NULL,
    document_number character varying(64)
);


--
-- TOC entry 1745 (class 1259 OID 306863)
-- Dependencies: 6
-- Name: delivery_certificate_items; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE delivery_certificate_items (
    certificate_item_id uuid NOT NULL,
    parent_id uuid NOT NULL,
    product_id uuid NOT NULL,
    measure_unit_id integer NOT NULL,
    quantity numeric(19,4) NOT NULL,
    reference_item_id uuid
);


--
-- TOC entry 1746 (class 1259 OID 306866)
-- Dependencies: 6
-- Name: delivery_certificate_serial_numbers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE delivery_certificate_serial_numbers (
    certificate_item_id uuid NOT NULL,
    serial_number character varying(32) NOT NULL
);


--
-- TOC entry 1747 (class 1259 OID 306869)
-- Dependencies: 6
-- Name: delivery_certificates; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE delivery_certificates (
    delivery_certificate_id uuid NOT NULL,
    parent_id uuid,
    warehouse_id uuid NOT NULL,
    warehouse_name character varying(64) NOT NULL,
    delivery_certificate_number bigint,
    delivery_certificate_date timestamp with time zone,
    recipient_id uuid NOT NULL,
    recipient_name character varying(48) NOT NULL,
    recipient_contact_id uuid,
    recipient_contact_name character varying(48),
    delivery_cert_method_type_id integer NOT NULL,
    creation_time timestamp with time zone NOT NULL,
    creator_name character varying(48) NOT NULL,
    forwarder_id uuid,
    forwarder_name character varying(64),
    forwarder_contact_id uuid,
    forwarder_contact_name character varying(48),
    delivery_cert_reason_id integer NOT NULL,
    creator_id uuid NOT NULL,
    forwarder_branch_id uuid,
    recipient_branch_id uuid,
    creator_organization_id uuid,
    creator_branch_id uuid,
    forwarder_branch_name character varying(64),
    recipient_branch_name character varying(64),
    creator_organization_name character varying(64),
    creator_branch_name character varying(64),
    delivery_cert_status_id integer NOT NULL
);


--
-- TOC entry 2929 (class 0 OID 0)
-- Dependencies: 1747
-- Name: COLUMN delivery_certificates.forwarder_branch_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN delivery_certificates.forwarder_branch_id IS 'As the Forwarder is an organization, we have to know abranch_id in order to choose a contact person.';


--
-- TOC entry 2930 (class 0 OID 0)
-- Dependencies: 1747
-- Name: COLUMN delivery_certificates.recipient_branch_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN delivery_certificates.recipient_branch_id IS 'In case the recipient is an organization we have to know a branch in order to shoose a contact person.';


--
-- TOC entry 1748 (class 1259 OID 306875)
-- Dependencies: 6
-- Name: entity_sequences; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE entity_sequences (
    entity_id uuid NOT NULL,
    data_object_type_id integer NOT NULL,
    initial_value uuid,
    sequence_value uuid,
    dataobjectid uuid,
    dataobjecttypeid integer
);


--
-- TOC entry 1749 (class 1259 OID 306878)
-- Dependencies: 6
-- Name: enum_classes; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE enum_classes (
    enum_class_id integer NOT NULL,
    enum_class_name character varying(255) NOT NULL
);


--
-- TOC entry 1750 (class 1259 OID 306881)
-- Dependencies: 6
-- Name: expressions; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE expressions (
    organization_id uuid NOT NULL,
    expression_key character varying(255) NOT NULL,
    expression_value character varying(1024)
);


--
-- TOC entry 1751 (class 1259 OID 306887)
-- Dependencies: 6
-- Name: goods_receipt_dc_items; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE goods_receipt_dc_items (
    receipt_item_id uuid NOT NULL,
    delivery_certificate_item_id uuid NOT NULL
);


--
-- TOC entry 1752 (class 1259 OID 306890)
-- Dependencies: 6
-- Name: goods_receipt_items; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE goods_receipt_items (
    receipt_item_id uuid NOT NULL,
    goods_receipt_id uuid NOT NULL,
    product_id uuid NOT NULL,
    measure_unit_id integer NOT NULL,
    received_quantity numeric(19,4) NOT NULL,
    receipt_item_type character varying(2) NOT NULL
);


--
-- TOC entry 1753 (class 1259 OID 306893)
-- Dependencies: 6
-- Name: goods_receipt_pi_items; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE goods_receipt_pi_items (
    receipt_item_id uuid NOT NULL,
    invoice_item_id uuid NOT NULL
);


--
-- TOC entry 1754 (class 1259 OID 306896)
-- Dependencies: 6
-- Name: goods_receipts; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE goods_receipts (
    goods_receipt_id uuid NOT NULL,
    supplier_id uuid NOT NULL,
    supplier_branch_id uuid,
    supplier_contact_id uuid,
    related_document_type_id integer NOT NULL,
    related_document_id uuid NOT NULL
);


--
-- TOC entry 1755 (class 1259 OID 306899)
-- Dependencies: 6
-- Name: job_titles; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE job_titles (
    job_title_id uuid NOT NULL,
    business_unit_id uuid NOT NULL,
    functional_hierarchy_id integer NOT NULL,
    job_title character varying(100) NOT NULL,
    security_role_id uuid NOT NULL
);


--
-- TOC entry 2931 (class 0 OID 0)
-- Dependencies: 1755
-- Name: COLUMN job_titles.business_unit_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN job_titles.business_unit_id IS 'The parent';


--
-- TOC entry 1756 (class 1259 OID 306902)
-- Dependencies: 6
-- Name: order_confirmation_items; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE order_confirmation_items (
    confirmation_item_id uuid NOT NULL,
    confirmed_quantity numeric(19,4) NOT NULL,
    extended_price numeric(19,4) NOT NULL,
    notes character varying(255),
    parent_id uuid,
    ship_date_from date,
    ship_date_to date,
    unit_price numeric(19,4) NOT NULL,
    measure_unit_id integer,
    product_id uuid,
    currency_id integer,
    matched_quantity numeric(19,4),
    ship_week integer
);


--
-- TOC entry 1757 (class 1259 OID 306905)
-- Dependencies: 6
-- Name: order_confirmations; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE order_confirmations (
    order_confirmation_id uuid NOT NULL,
    discount_amount numeric(19,4),
    discount_percent numeric(19,4),
    documentdate_date timestamp with time zone NOT NULL,
    document_number character varying(255) NOT NULL,
    invoice_sub_value numeric(19,4) NOT NULL,
    notes character varying(255),
    parent_id uuid,
    supplier_contact_name character varying(255),
    supplier_name character varying(255) NOT NULL,
    total_value numeric(19,4) NOT NULL,
    transport_price numeric(19,4),
    vat numeric(19,4) NOT NULL,
    currency_id integer,
    document_type_id integer,
    supplier_partner_id uuid,
    supplier_contact_id uuid,
    branch_id uuid,
    ship_date_from date,
    ship_date_to date,
    ship_week integer
);


--
-- TOC entry 1758 (class 1259 OID 306911)
-- Dependencies: 6
-- Name: order_item_match; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE order_item_match (
    id uuid NOT NULL,
    matchquantity numeric(19,4),
    purchaseorderitem_order_item_id uuid,
    orderconfirmationitem_confirmation_item_id uuid
);


--
-- TOC entry 1759 (class 1259 OID 306914)
-- Dependencies: 6
-- Name: organization_configurations; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE organization_configurations (
    organization_id uuid NOT NULL,
    default_currency_id integer NOT NULL
);


--
-- TOC entry 1760 (class 1259 OID 306917)
-- Dependencies: 2102 6
-- Name: organizations; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE organizations (
    organization_id uuid NOT NULL,
    nickname character varying(32),
    organization_name character varying(128) NOT NULL,
    registration_date date,
    share_capital numeric(19,4),
    unique_identifier_code character varying(32),
    vat_number character varying(32),
    organization_type_id integer,
    registration_address_id uuid,
    registration_organization_id uuid,
    administration_address_id uuid,
    share_capital_currency_id integer,
    is_active boolean DEFAULT false NOT NULL,
    parent_business_partner_id uuid NOT NULL,
    registration_code character varying(32)
);


--
-- TOC entry 1761 (class 1259 OID 306921)
-- Dependencies: 6
-- Name: passports; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE passports (
    passport_id uuid NOT NULL,
    person_id uuid NOT NULL,
    passport_type_id integer NOT NULL,
    passport_number character varying(16) NOT NULL,
    issue_date date NOT NULL,
    expiration_date date NOT NULL,
    issuer_id uuid NOT NULL,
    issuer_branch_id uuid NOT NULL,
    additional_info character varying(255)
);


--
-- TOC entry 2932 (class 0 OID 0)
-- Dependencies: 1761
-- Name: COLUMN passports.passport_type_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN passports.passport_type_id IS 'Passport, Identity Card, Driving License';


--
-- TOC entry 1762 (class 1259 OID 306924)
-- Dependencies: 6
-- Name: pattern_mask_formats; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE pattern_mask_formats (
    pattern_mask_format_id uuid NOT NULL,
    description character varying(255),
    format character varying(255) NOT NULL,
    format_type character(1) NOT NULL,
    parent_id uuid,
    pattern_name character varying(255) NOT NULL,
    owner_id uuid
);


--
-- TOC entry 1817 (class 1259 OID 310013)
-- Dependencies: 6
-- Name: personal_communication_contacts; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE personal_communication_contacts (
    personal_communication_contact_id uuid NOT NULL,
    contact_person_id uuid NOT NULL,
    communication_contact_id uuid NOT NULL
);


--
-- TOC entry 1763 (class 1259 OID 306930)
-- Dependencies: 2103 6
-- Name: persons; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE persons (
    person_id uuid NOT NULL,
    birth_date date,
    extra_name character varying(24),
    first_name character varying(24) NOT NULL,
    last_name character varying(24) NOT NULL,
    personal_unique_id character varying(16),
    second_name character varying(24),
    gender_id integer,
    birth_place_city_id uuid,
    birth_place_country_id uuid,
    parent_business_partner_id uuid NOT NULL,
    CONSTRAINT check_persons_country_peronal_unique_id CHECK (((personal_unique_id IS NULL) OR ((personal_unique_id IS NOT NULL) AND (birth_place_country_id IS NOT NULL))))
);


--
-- TOC entry 1764 (class 1259 OID 306934)
-- Dependencies: 6
-- Name: position_types; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE position_types (
    position_type_id uuid NOT NULL,
    business_partner_id uuid NOT NULL,
    position_type_name character varying(32) NOT NULL
);


--
-- TOC entry 1765 (class 1259 OID 306941)
-- Dependencies: 6
-- Name: pricelist_items; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE pricelist_items (
    item_id uuid NOT NULL,
    pricelist_id uuid NOT NULL,
    apply_client_discount boolean,
    discount_percent numeric(19,4),
    min_quantity numeric(19,4),
    product_id uuid
);


--
-- TOC entry 1766 (class 1259 OID 306944)
-- Dependencies: 2104 2105 2106 6
-- Name: pricelists; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE pricelists (
    pricelist_id uuid NOT NULL,
    active boolean DEFAULT false,
    valid_from timestamp with time zone,
    valid_to timestamp with time zone,
    default_discount numeric(19,4),
    for_period boolean DEFAULT false NOT NULL,
    min_turnover numeric(19,4),
    turnover_months integer,
    pricelist_name character varying(128) NOT NULL,
    parent_id uuid,
    currency_id integer,
    general_pricelist boolean DEFAULT false NOT NULL
);


--
-- TOC entry 1767 (class 1259 OID 306950)
-- Dependencies: 6
-- Name: privilege_categories; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE privilege_categories (
    privilege_category_id uuid NOT NULL,
    organization_id uuid NOT NULL,
    category_name character varying(100) NOT NULL,
    privilege_type_id integer NOT NULL
);


--
-- TOC entry 1768 (class 1259 OID 306953)
-- Dependencies: 6
-- Name: privilege_roles; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE privilege_roles (
    privilege_role_id uuid NOT NULL,
    privilege_id uuid NOT NULL,
    access_right_id integer NOT NULL,
    access_level_id integer NOT NULL
);


--
-- TOC entry 1769 (class 1259 OID 306956)
-- Dependencies: 2107 6
-- Name: privileges; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE privileges (
    privilege_id uuid NOT NULL,
    security_role_id uuid NOT NULL,
    privilege_category_id uuid NOT NULL,
    privilege_name character varying(100) NOT NULL,
    discriminator_id character varying(4) NOT NULL,
    data_object_type_id integer,
    data_object_id uuid,
    permission_category_id integer,
    special_permission_id integer,
    expires timestamp with time zone,
    CONSTRAINT check_privileges CHECK ((NOT ((((data_object_type_id IS NULL) AND (data_object_id IS NULL)) AND (permission_category_id IS NULL)) AND (special_permission_id IS NULL))))
);


--
-- TOC entry 2933 (class 0 OID 0)
-- Dependencies: 1769
-- Name: COLUMN privileges.security_role_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN privileges.security_role_id IS 'The parent of privilege';


--
-- TOC entry 1770 (class 1259 OID 306960)
-- Dependencies: 6
-- Name: product_categories; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE product_categories (
    product_category_id uuid NOT NULL,
    parent_id uuid,
    category_name character varying(100) NOT NULL,
    description text,
    parent_cat_id uuid,
    pattern_mask_format_id uuid,
    discount_percent numeric(8,6),
    profit_percent numeric(8,6),
    customs_duty_percent numeric(8,6),
    excise_duty_percent numeric(8,6),
    transport_percent numeric(8,6),
    transport_value numeric(19,4)
);


--
-- TOC entry 1771 (class 1259 OID 306966)
-- Dependencies: 6
-- Name: product_percent_values; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE product_percent_values (
    percent_value_id uuid NOT NULL,
    value_name character varying(128) NOT NULL,
    organization_id uuid NOT NULL,
    value_type_id integer NOT NULL,
    percent_value numeric(8,6) NOT NULL
);


--
-- TOC entry 1772 (class 1259 OID 306969)
-- Dependencies: 2108 2109 2110 2111 6
-- Name: product_suppliers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE product_suppliers (
    product_id uuid NOT NULL,
    supplier_id uuid NOT NULL,
    product_name character varying(100) NOT NULL,
    product_code character varying(50) NOT NULL,
    measure_unit_id integer NOT NULL,
    is_deliverable boolean DEFAULT true NOT NULL,
    is_obsolete boolean DEFAULT false NOT NULL,
    min_order_quantity numeric(19,4) DEFAULT 1 NOT NULL,
    max_order_quantity numeric(19,4),
    price_per_quantity integer DEFAULT 1 NOT NULL,
    currency_id integer,
    order_price numeric(19,4) NOT NULL,
    delivery_time integer,
    description text
);


--
-- TOC entry 1773 (class 1259 OID 306979)
-- Dependencies: 6
-- Name: products; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE products (
    product_id uuid NOT NULL,
    parent_id uuid NOT NULL,
    product_type character(2) NOT NULL,
    product_code character varying(50) NOT NULL,
    product_name character varying(100) NOT NULL,
    measure_unit_id integer NOT NULL,
    currency_id integer NOT NULL
);


--
-- TOC entry 1774 (class 1259 OID 306982)
-- Dependencies: 6
-- Name: purchase_invoice_items; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE purchase_invoice_items (
    invoice_item_id uuid NOT NULL,
    invoice_id uuid NOT NULL,
    product_id uuid NOT NULL,
    measure_unit_id integer NOT NULL,
    received_quantity numeric(19,4) NOT NULL,
    received_price numeric(19,4) NOT NULL,
    extended_price numeric(19,4) NOT NULL,
    tax_value numeric(19,4),
    purchase_order_item_id uuid,
    order_confirmation_item_id uuid,
    customs_tariff_number character varying(16)
);


--
-- TOC entry 1775 (class 1259 OID 306985)
-- Dependencies: 6
-- Name: purchase_invoices; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE purchase_invoices (
    invoice_id uuid NOT NULL,
    supplier_id uuid NOT NULL,
    supplier_branch_id uuid,
    supplier_contact_id uuid,
    invoice_number character varying(16) NOT NULL,
    invoice_date date NOT NULL,
    delivery_note character varying(16),
    total_quantity numeric(19,4),
    total_net_amount numeric(19,4),
    total_tax numeric(19,4),
    total_gross_amount numeric(19,4),
    payment_terms_id integer NOT NULL,
    payment_deadline timestamp with time zone NOT NULL,
    bank_detail_id uuid,
    delivery_terms_id integer NOT NULL,
    document_currency_id integer NOT NULL
);


--
-- TOC entry 1776 (class 1259 OID 306988)
-- Dependencies: 6
-- Name: purchase_order_items; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE purchase_order_items (
    order_item_id uuid NOT NULL,
    parent_id uuid NOT NULL,
    product_id uuid NOT NULL,
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
-- TOC entry 1777 (class 1259 OID 306991)
-- Dependencies: 6
-- Name: purchase_orders; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE purchase_orders (
    purchase_order_id uuid NOT NULL,
    branch_name character varying(255) NOT NULL,
    creation_time timestamp with time zone NOT NULL,
    creator_name character varying(255) NOT NULL,
    finalizing_time timestamp with time zone,
    first_delivery_time timestamp with time zone,
    last_delivery_time timestamp with time zone,
    notes character varying(255),
    order_number numeric(19,0) NOT NULL,
    parent_id uuid,
    sender_name character varying(255),
    sent_time timestamp with time zone,
    supplier_contact_name character varying(255),
    supplier_name character varying(255) NOT NULL,
    supplier_order_number numeric(19,0),
    sender_id uuid,
    supplier_contact_id uuid,
    creator_id uuid,
    doc_delivery_method_id integer,
    supplier_partner_id uuid,
    status_id integer,
    branch_id uuid,
    deliverystatus_resource_id integer
);


--
-- TOC entry 1778 (class 1259 OID 306997)
-- Dependencies: 6
-- Name: real_products; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE real_products (
    product_id uuid NOT NULL,
    simple_product_id uuid NOT NULL
);


--
-- TOC entry 1779 (class 1259 OID 307000)
-- Dependencies: 6
-- Name: receipt_certificate_items; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE receipt_certificate_items (
    certificate_item_id uuid NOT NULL,
    parent_id uuid NOT NULL,
    product_id uuid NOT NULL,
    measure_unit_id integer NOT NULL,
    quantity numeric(19,4) NOT NULL
);


--
-- TOC entry 1780 (class 1259 OID 307003)
-- Dependencies: 6
-- Name: receipt_certificate_serial_numbers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE receipt_certificate_serial_numbers (
    certificate_item_id uuid NOT NULL,
    serial_number character varying(32) NOT NULL
);


--
-- TOC entry 1781 (class 1259 OID 307006)
-- Dependencies: 6
-- Name: receipt_certificates; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE receipt_certificates (
    receipt_certificate_id uuid NOT NULL,
    parent_id uuid,
    warehouse_id uuid NOT NULL,
    warehouse_name character varying(64) NOT NULL,
    receipt_certificate_number bigint NOT NULL,
    receipt_certificate_date timestamp with time zone NOT NULL,
    deliverer_id uuid NOT NULL,
    deliverer_name character varying(48) NOT NULL,
    deliverer_contact_id uuid,
    deliverer_contact_name character varying(48),
    receipt_cert_method_type_id integer NOT NULL,
    receipt_cert_reason_id integer NOT NULL,
    creation_time timestamp with time zone NOT NULL,
    creator_name character varying(48) NOT NULL,
    forwarder_id uuid,
    forwarder_name character varying(64),
    forwarder_contact_id uuid,
    forwarder_contact_name character varying(48),
    creator_id uuid NOT NULL
);


--
-- TOC entry 1782 (class 1259 OID 307009)
-- Dependencies: 2112 6
-- Name: registration_codes; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE registration_codes (
    registration_code uuid NOT NULL,
    email_address character varying(64) NOT NULL,
    registration_time timestamp with time zone DEFAULT now() NOT NULL
);


--
-- TOC entry 1783 (class 1259 OID 307013)
-- Dependencies: 6
-- Name: resource_bundle; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE resource_bundle (
    resource_id integer NOT NULL,
    enum_class_id integer NOT NULL,
    enum_name character varying(64) NOT NULL
);


--
-- TOC entry 1784 (class 1259 OID 307016)
-- Dependencies: 6
-- Name: sales_invoice_item_link; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE sales_invoice_item_link (
    id integer NOT NULL,
    template_doc_id uuid NOT NULL,
    template_item_id uuid NOT NULL,
    invoice_item_id uuid NOT NULL
);


--
-- TOC entry 1785 (class 1259 OID 307019)
-- Dependencies: 6
-- Name: sales_invoice_items; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE sales_invoice_items (
    invoice_item_id uuid NOT NULL,
    discount_amount numeric(19,4),
    discount_percent numeric(19,4),
    extended_price numeric(19,4) NOT NULL,
    notes character varying(255),
    ordered_quantity numeric(19,4) NOT NULL,
    parent_id uuid,
    product_description character varying(255),
    returned_quantity numeric(19,4),
    ship_date_from date,
    ship_date_to date,
    ship_week integer,
    shipped_quantity numeric(19,4),
    unit_price numeric(19,4) NOT NULL,
    measure_unit_id integer NOT NULL,
    warehouse_id uuid,
    product_id uuid,
    duequantity numeric(19,4),
    pricelistid uuid,
    pricelistitemid uuid
);


--
-- TOC entry 1786 (class 1259 OID 307025)
-- Dependencies: 6
-- Name: sales_invoices; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE sales_invoices (
    invoice_id uuid NOT NULL,
    branch_name character varying(255) NOT NULL,
    completion_date timestamp with time zone,
    creation_time timestamp with time zone,
    creator_name character varying(255),
    days_between_payments integer,
    discount_amount numeric(19,4),
    discount_percent numeric(19,4),
    excise_duty_percent numeric(19,4),
    excise_duty_amount numeric(19,4),
    invoice_date timestamp with time zone,
    invoice_number numeric(19,0),
    invoice_sub_value numeric(19,4) NOT NULL,
    notes character varying(255),
    parent_id uuid,
    payment_due_date date,
    payments_count integer,
    proforma boolean NOT NULL,
    recipient_contact_name character varying(255) NOT NULL,
    recipient_name character varying(255) NOT NULL,
    sender_name character varying(255),
    sent_time timestamp with time zone,
    ship_date_from date,
    ship_date_to date,
    ship_week integer,
    single_pay numeric(19,4),
    total_value numeric(19,4) NOT NULL,
    transport_price numeric(19,4),
    vat numeric(19,4) NOT NULL,
    vat_condition_notes character varying(255),
    recipient_id uuid NOT NULL,
    payment_terms_id integer,
    vat_condition_id integer,
    branch_id uuid,
    supplier_contact_id uuid,
    payment_type_id integer,
    invoice_type_id integer NOT NULL,
    delivery_type_id integer,
    doc_delivery_method_id integer,
    transportation_method_id integer,
    status_id integer,
    shippingagent_partner_id uuid,
    currency_id integer,
    sender_id uuid,
    creator_id uuid,
    deliverystatus_resource_id integer,
    additionalterms character varying(255),
    validto date,
    attendee_id uuid,
    paid_amount numeric(19,4)
);


--
-- TOC entry 1787 (class 1259 OID 307031)
-- Dependencies: 6
-- Name: security_roles; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE security_roles (
    security_role_id uuid NOT NULL,
    organization_id uuid NOT NULL,
    security_role_name character varying(100) NOT NULL,
    business_unit_id uuid NOT NULL
);


--
-- TOC entry 2934 (class 0 OID 0)
-- Dependencies: 1787
-- Name: COLUMN security_roles.organization_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN security_roles.organization_id IS 'The parent of this entity';


--
-- TOC entry 2935 (class 0 OID 0)
-- Dependencies: 1787
-- Name: COLUMN security_roles.business_unit_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN security_roles.business_unit_id IS 'The business unit where this security role can be applied.';


--
-- TOC entry 1788 (class 1259 OID 307034)
-- Dependencies: 2113 6
-- Name: sequence_identifiers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE sequence_identifiers (
    seq_id_key bigint NOT NULL,
    seq_id_name character varying(64) NOT NULL,
    seq_id_value numeric(38,0) DEFAULT 0 NOT NULL
);


--
-- TOC entry 1789 (class 1259 OID 307038)
-- Dependencies: 2115 2116 2117 2118 2119 2120 6
-- Name: simple_products; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE simple_products (
    product_id uuid NOT NULL,
    category_id uuid NOT NULL,
    is_purchased boolean DEFAULT false NOT NULL,
    is_salable boolean DEFAULT true NOT NULL,
    is_obsolete boolean DEFAULT false NOT NULL,
    product_color_id integer,
    minimum_quantity numeric(19,4) DEFAULT 1 NOT NULL,
    maximum_quantity numeric(19,4),
    default_quantity numeric(19,4),
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
    producer_id uuid,
    pattern_mask_format_id uuid,
    price_per_quantity integer DEFAULT 1 NOT NULL,
    discount_percent_id uuid,
    profit_percent_id uuid,
    customs_duty_percent_id uuid,
    excise_duty_percent_id uuid,
    transport_percent_id uuid,
    transport_value numeric(19,4)
);


--
-- TOC entry 1790 (class 1259 OID 307050)
-- Dependencies: 6
-- Name: team_members; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE team_members (
    team_member_id uuid NOT NULL,
    team_id uuid NOT NULL,
    user_organization_id uuid NOT NULL,
    status_id integer NOT NULL
);


--
-- TOC entry 1791 (class 1259 OID 307053)
-- Dependencies: 6
-- Name: teams; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE teams (
    team_id uuid NOT NULL,
    team_name character varying(50) NOT NULL,
    organization_id uuid NOT NULL,
    business_unit_id uuid NOT NULL,
    status_id integer NOT NULL
);


--
-- TOC entry 1792 (class 1259 OID 307056)
-- Dependencies: 6
-- Name: user_group_members; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE user_group_members (
    user_group_member_id uuid NOT NULL,
    user_group_id uuid NOT NULL,
    user_organization_id uuid NOT NULL
);


--
-- TOC entry 1793 (class 1259 OID 307059)
-- Dependencies: 6
-- Name: user_groups; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE user_groups (
    user_group_id uuid NOT NULL,
    user_group_name character varying(50) NOT NULL,
    organization_id uuid NOT NULL,
    description text
);


--
-- TOC entry 1794 (class 1259 OID 307065)
-- Dependencies: 2121 2122 6
-- Name: user_organizations; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE user_organizations (
    user_id uuid NOT NULL,
    organization_id uuid NOT NULL,
    branch_id uuid NOT NULL,
    is_user_active boolean DEFAULT false NOT NULL,
    job_title_id uuid,
    manager_id uuid,
    email_address character varying(64),
    user_organization_id uuid NOT NULL,
    business_unit_id uuid NOT NULL,
    default_organization boolean DEFAULT false NOT NULL
);


--
-- TOC entry 1795 (class 1259 OID 307068)
-- Dependencies: 2123 2124 2125 2126 2127 6
-- Name: user_rights; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE user_rights (
    user_right_id bigint NOT NULL,
    access_rights integer DEFAULT 0 NOT NULL,
    excluded boolean DEFAULT false NOT NULL,
    organization_id uuid NOT NULL,
    owner_type_id character(1) NOT NULL,
    user_id uuid,
    user_group_id uuid,
    data_object_type_id integer,
    data_object_id uuid,
    permission_category_id integer,
    special_permission_id integer,
    expires timestamp with time zone,
    CONSTRAINT check_user_rights CHECK ((NOT ((((data_object_type_id IS NULL) AND (data_object_id IS NULL)) AND (permission_category_id IS NULL)) AND (special_permission_id IS NULL)))),
    CONSTRAINT check_user_rights_owner_not_null CHECK ((NOT ((user_id IS NOT NULL) AND (user_group_id IS NOT NULL)))),
    CONSTRAINT check_user_rights_owner_null CHECK ((NOT ((user_id IS NULL) AND (user_group_id IS NULL))))
);


--
-- TOC entry 2936 (class 0 OID 0)
-- Dependencies: 1795
-- Name: COLUMN user_rights.owner_type_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN user_rights.owner_type_id IS 'U=User, G=User Group';


--
-- TOC entry 1796 (class 1259 OID 307076)
-- Dependencies: 2128 2129 2130 2131 2132 2133 6
-- Name: user_rights_old; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE user_rights_old (
    user_group_id uuid,
    user_id uuid,
    data_object_type_id integer,
    data_object_id uuid,
    can_read boolean DEFAULT false NOT NULL,
    can_create boolean DEFAULT false NOT NULL,
    can_modify boolean DEFAULT false NOT NULL,
    can_delete boolean DEFAULT false NOT NULL,
    user_right_id bigint NOT NULL,
    special_permission_id integer,
    excluded boolean DEFAULT false NOT NULL,
    expires timestamp with time zone,
    can_execute boolean DEFAULT false NOT NULL
);


--
-- TOC entry 1797 (class 1259 OID 307085)
-- Dependencies: 6
-- Name: user_security_roles; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE user_security_roles (
    user_security_role_id uuid NOT NULL,
    user_organization_id uuid NOT NULL,
    security_role_id uuid NOT NULL
);


--
-- TOC entry 1798 (class 1259 OID 307088)
-- Dependencies: 2134 2135 6
-- Name: users; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE users (
    user_id uuid NOT NULL,
    version integer NOT NULL,
    user_name character varying(32) NOT NULL,
    email_address character varying(64) NOT NULL,
    user_password character varying(64) NOT NULL,
    system_password character varying(64),
    system_password_validity timestamp with time zone,
    is_new boolean DEFAULT true NOT NULL,
    creator_id uuid,
    next_action_after_login character varying(1024),
    person_id uuid,
    creation_time timestamp with time zone DEFAULT now() NOT NULL,
    system_organization_id uuid NOT NULL
);


--
-- TOC entry 1799 (class 1259 OID 307096)
-- Dependencies: 6
-- Name: uuid_test; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE uuid_test (
    test_id uuid NOT NULL,
    test_name character varying(100)
);


--
-- TOC entry 1800 (class 1259 OID 307099)
-- Dependencies: 6
-- Name: virtual_products; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE virtual_products (
    product_id uuid NOT NULL,
    product_type character(2),
    parent_id uuid
);


--
-- TOC entry 1801 (class 1259 OID 307102)
-- Dependencies: 6
-- Name: warehouse_products; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE warehouse_products (
    warehouse_product_id uuid NOT NULL,
    default_quantity numeric(19,4),
    delivery_time integer,
    maximum_quantity numeric(19,4),
    minimum_quantity numeric(19,4),
    notes character varying(255),
    ordered_quantity numeric(19,4),
    parent_id uuid,
    purchase_price numeric(19,4),
    quantity_due numeric(19,4),
    quantity_in_stock numeric(19,4),
    reserved_quantity numeric(19,4),
    sale_price numeric(19,4),
    sold_quantity numeric(19,4),
    product_id uuid NOT NULL,
    warehouse_id uuid NOT NULL,
    ordered_delivery_time integer,
    not_invoiced_quantity numeric(19,4)
);


--
-- TOC entry 1802 (class 1259 OID 307105)
-- Dependencies: 6
-- Name: warehouses; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE warehouses (
    warehouse_id uuid NOT NULL,
    business_unit_id uuid NOT NULL,
    head_of_stock_id uuid NOT NULL
);


--
-- TOC entry 1803 (class 1259 OID 307111)
-- Dependencies: 6
-- Name: cash_recon_pay_summary_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE cash_recon_pay_summary_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1804 (class 1259 OID 307113)
-- Dependencies: 6
-- Name: countries_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE countries_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1805 (class 1259 OID 307115)
-- Dependencies: 6
-- Name: customer_payment_match_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE customer_payment_match_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1806 (class 1259 OID 307117)
-- Dependencies: 6
-- Name: data_object_type_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE data_object_type_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1807 (class 1259 OID 307119)
-- Dependencies: 6
-- Name: data_objects_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE data_objects_seq
    INCREMENT BY 1
    MAXVALUE 999999999999999999
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1808 (class 1259 OID 307121)
-- Dependencies: 6
-- Name: enum_classes_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE enum_classes_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1809 (class 1259 OID 307123)
-- Dependencies: 6
-- Name: invoice_item_link_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE invoice_item_link_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1810 (class 1259 OID 307125)
-- Dependencies: 6
-- Name: order_item_match_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE order_item_match_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1811 (class 1259 OID 307127)
-- Dependencies: 6
-- Name: pattern_mask_formats_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE pattern_mask_formats_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1812 (class 1259 OID 307129)
-- Dependencies: 6
-- Name: resource_bundle_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE resource_bundle_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1813 (class 1259 OID 307131)
-- Dependencies: 6 1788
-- Name: sequence_identifiers_seq_id_key_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE sequence_identifiers_seq_id_key_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2937 (class 0 OID 0)
-- Dependencies: 1813
-- Name: sequence_identifiers_seq_id_key_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE sequence_identifiers_seq_id_key_seq OWNED BY sequence_identifiers.seq_id_key;


--
-- TOC entry 1814 (class 1259 OID 307133)
-- Dependencies: 6
-- Name: user_rights_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE user_rights_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1815 (class 1259 OID 307135)
-- Dependencies: 6
-- Name: warehouse_product_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE warehouse_product_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1816 (class 1259 OID 307137)
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
-- TOC entry 2114 (class 2604 OID 307139)
-- Dependencies: 1813 1788
-- Name: seq_id_key; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE sequence_identifiers ALTER COLUMN seq_id_key SET DEFAULT nextval('sequence_identifiers_seq_id_key_seq'::regclass);


--
-- TOC entry 2174 (class 2606 OID 307143)
-- Dependencies: 1714 1714
-- Name: business_partners_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY business_partners
    ADD CONSTRAINT business_partners_pkey PRIMARY KEY (business_partner_id);


--
-- TOC entry 2184 (class 2606 OID 307145)
-- Dependencies: 1717 1717
-- Name: cash_reconcile_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY cash_reconcile
    ADD CONSTRAINT cash_reconcile_pkey PRIMARY KEY (cash_reconcile_id);


--
-- TOC entry 2226 (class 2606 OID 307149)
-- Dependencies: 1731 1731
-- Name: customer_discount_items_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customer_discount_items
    ADD CONSTRAINT customer_discount_items_pkey PRIMARY KEY (customer_discount_item_id);


--
-- TOC entry 2240 (class 2606 OID 307151)
-- Dependencies: 1735 1735
-- Name: customer_payment_match_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customer_payment_match
    ADD CONSTRAINT customer_payment_match_pkey PRIMARY KEY (customer_payment_match_id);


--
-- TOC entry 2242 (class 2606 OID 307153)
-- Dependencies: 1736 1736
-- Name: customer_payments_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customer_payments
    ADD CONSTRAINT customer_payments_pkey PRIMARY KEY (payment_id);


--
-- TOC entry 2377 (class 2606 OID 307155)
-- Dependencies: 1784 1784
-- Name: invoice_item_link_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY sales_invoice_item_link
    ADD CONSTRAINT invoice_item_link_pkey PRIMARY KEY (id);


--
-- TOC entry 2379 (class 2606 OID 307157)
-- Dependencies: 1785 1785
-- Name: invoice_items_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY sales_invoice_items
    ADD CONSTRAINT invoice_items_pkey PRIMARY KEY (invoice_item_id);


--
-- TOC entry 2381 (class 2606 OID 307159)
-- Dependencies: 1786 1786
-- Name: invoices_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT invoices_pkey PRIMARY KEY (invoice_id);


--
-- TOC entry 2299 (class 2606 OID 307161)
-- Dependencies: 1756 1756
-- Name: order_confirmation_items_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY order_confirmation_items
    ADD CONSTRAINT order_confirmation_items_pkey PRIMARY KEY (confirmation_item_id);


--
-- TOC entry 2301 (class 2606 OID 307163)
-- Dependencies: 1757 1757
-- Name: order_confirmations_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT order_confirmations_pkey PRIMARY KEY (order_confirmation_id);


--
-- TOC entry 2303 (class 2606 OID 307165)
-- Dependencies: 1758 1758
-- Name: order_item_match_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY order_item_match
    ADD CONSTRAINT order_item_match_pkey PRIMARY KEY (id);


--
-- TOC entry 2307 (class 2606 OID 307167)
-- Dependencies: 1760 1760
-- Name: organizations_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT organizations_pkey PRIMARY KEY (organization_id);


--
-- TOC entry 2313 (class 2606 OID 307169)
-- Dependencies: 1762 1762
-- Name: pattern_mask_formats_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pattern_mask_formats
    ADD CONSTRAINT pattern_mask_formats_pkey PRIMARY KEY (pattern_mask_format_id);


--
-- TOC entry 2315 (class 2606 OID 307171)
-- Dependencies: 1763 1763
-- Name: persons_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT persons_pkey PRIMARY KEY (person_id);


--
-- TOC entry 2137 (class 2606 OID 307173)
-- Dependencies: 1704 1704
-- Name: pk_addresses; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT pk_addresses PRIMARY KEY (address_id);


--
-- TOC entry 2140 (class 2606 OID 307175)
-- Dependencies: 1705 1705
-- Name: pk_assembling_categories; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_categories
    ADD CONSTRAINT pk_assembling_categories PRIMARY KEY (assembling_category_id);


--
-- TOC entry 2146 (class 2606 OID 307177)
-- Dependencies: 1706 1706
-- Name: pk_assembling_messages; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_messages
    ADD CONSTRAINT pk_assembling_messages PRIMARY KEY (message_id);


--
-- TOC entry 2150 (class 2606 OID 307179)
-- Dependencies: 1707 1707
-- Name: pk_assembling_schema_item_values; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schema_item_values
    ADD CONSTRAINT pk_assembling_schema_item_values PRIMARY KEY (item_value_id);


--
-- TOC entry 2152 (class 2606 OID 307181)
-- Dependencies: 1708 1708
-- Name: pk_assembling_schema_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT pk_assembling_schema_items PRIMARY KEY (item_id);


--
-- TOC entry 2154 (class 2606 OID 307183)
-- Dependencies: 1709 1709
-- Name: pk_assembling_schemas; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT pk_assembling_schemas PRIMARY KEY (product_id);


--
-- TOC entry 2160 (class 2606 OID 307185)
-- Dependencies: 1710 1710
-- Name: pk_bank_details; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT pk_bank_details PRIMARY KEY (bank_detail_id);


--
-- TOC entry 2164 (class 2606 OID 309957)
-- Dependencies: 1711 1711
-- Name: pk_banknote_quantity; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY banknote_quantity
    ADD CONSTRAINT pk_banknote_quantity PRIMARY KEY (banknote_quantity_id);


--
-- TOC entry 2168 (class 2606 OID 307187)
-- Dependencies: 1712 1712 1712 1712
-- Name: pk_business_document_status_log; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY business_document_status_log
    ADD CONSTRAINT pk_business_document_status_log PRIMARY KEY (document_id, document_status_id, action_time);


--
-- TOC entry 2176 (class 2606 OID 307189)
-- Dependencies: 1715 1715
-- Name: pk_business_unit_addresses; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY business_unit_addresses
    ADD CONSTRAINT pk_business_unit_addresses PRIMARY KEY (business_unit_address_id);


--
-- TOC entry 2181 (class 2606 OID 307191)
-- Dependencies: 1716 1716
-- Name: pk_business_units; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY business_units
    ADD CONSTRAINT pk_business_units PRIMARY KEY (business_unit_id);


--
-- TOC entry 2186 (class 2606 OID 307193)
-- Dependencies: 1718 1718
-- Name: pk_cash_reconcile_payment_summary; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY cash_reconcile_payment_summary
    ADD CONSTRAINT pk_cash_reconcile_payment_summary PRIMARY KEY (payment_summary_id);


--
-- TOC entry 2190 (class 2606 OID 307195)
-- Dependencies: 1719 1719
-- Name: pk_cities; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY cities
    ADD CONSTRAINT pk_cities PRIMARY KEY (city_id);


--
-- TOC entry 2194 (class 2606 OID 307197)
-- Dependencies: 1720 1720 1720
-- Name: pk_classified_objects; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classified_objects
    ADD CONSTRAINT pk_classified_objects PRIMARY KEY (classifier_id, classified_object_id);


--
-- TOC entry 2196 (class 2606 OID 307199)
-- Dependencies: 1721 1721 1721
-- Name: pk_classifier_applied_for_dot; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifier_applied_for_dot
    ADD CONSTRAINT pk_classifier_applied_for_dot PRIMARY KEY (classifier_id, data_object_type_id);


--
-- TOC entry 2198 (class 2606 OID 307201)
-- Dependencies: 1722 1722
-- Name: pk_classifier_groups; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifier_groups
    ADD CONSTRAINT pk_classifier_groups PRIMARY KEY (classifier_group_id);


--
-- TOC entry 2201 (class 2606 OID 307203)
-- Dependencies: 1723 1723
-- Name: pk_classifiers; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT pk_classifiers PRIMARY KEY (classifier_id);


--
-- TOC entry 2204 (class 2606 OID 307205)
-- Dependencies: 1724 1724
-- Name: pk_communication_contacts; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT pk_communication_contacts PRIMARY KEY (communication_contact_id);


--
-- TOC entry 2207 (class 2606 OID 307207)
-- Dependencies: 1725 1725
-- Name: pk_complex_product_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT pk_complex_product_items PRIMARY KEY (complex_product_item_id);


--
-- TOC entry 2209 (class 2606 OID 307209)
-- Dependencies: 1726 1726
-- Name: pk_complex_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY complex_products
    ADD CONSTRAINT pk_complex_products PRIMARY KEY (product_id);


--
-- TOC entry 2211 (class 2606 OID 307211)
-- Dependencies: 1727 1727
-- Name: pk_contact_persons; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT pk_contact_persons PRIMARY KEY (contact_person_id);


--
-- TOC entry 2215 (class 2606 OID 307213)
-- Dependencies: 1728 1728
-- Name: pk_countries; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY countries
    ADD CONSTRAINT pk_countries PRIMARY KEY (country_id);


--
-- TOC entry 2220 (class 2606 OID 307215)
-- Dependencies: 1729 1729 1729 1729 1729
-- Name: pk_currency_exchange_rates; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY currency_exchange_rates
    ADD CONSTRAINT pk_currency_exchange_rates PRIMARY KEY (organization_id, valid_from, from_currency_id, to_currency_id);


--
-- TOC entry 2222 (class 2606 OID 307147)
-- Dependencies: 1730 1730
-- Name: pk_currency_nominal; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY currency_nominal
    ADD CONSTRAINT pk_currency_nominal PRIMARY KEY (currency_nominal_id);


--
-- TOC entry 2228 (class 2606 OID 307217)
-- Dependencies: 1732 1732
-- Name: pk_customer_discount_items_by_categories; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customer_discount_items_by_categories
    ADD CONSTRAINT pk_customer_discount_items_by_categories PRIMARY KEY (customer_discount_item_id);


--
-- TOC entry 2232 (class 2606 OID 307219)
-- Dependencies: 1733 1733
-- Name: pk_customer_discount_items_by_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customer_discount_items_by_products
    ADD CONSTRAINT pk_customer_discount_items_by_products PRIMARY KEY (customer_discount_item_id);


--
-- TOC entry 2236 (class 2606 OID 307221)
-- Dependencies: 1734 1734
-- Name: pk_customer_discounts; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customer_discounts
    ADD CONSTRAINT pk_customer_discounts PRIMARY KEY (customer_discount_id);


--
-- TOC entry 2244 (class 2606 OID 307223)
-- Dependencies: 1737 1737
-- Name: pk_data_object_details; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_details
    ADD CONSTRAINT pk_data_object_details PRIMARY KEY (data_object_id);


--
-- TOC entry 2248 (class 2606 OID 307225)
-- Dependencies: 1738 1738
-- Name: pk_data_object_links; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_links
    ADD CONSTRAINT pk_data_object_links PRIMARY KEY (data_object_link_id);


--
-- TOC entry 2252 (class 2606 OID 307227)
-- Dependencies: 1739 1739 1739 1739 1739
-- Name: pk_data_object_permissions; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_permissions
    ADD CONSTRAINT pk_data_object_permissions PRIMARY KEY (organization_id, data_object_id, user_right_type_id, permission_id);


--
-- TOC entry 2254 (class 2606 OID 307229)
-- Dependencies: 1740 1740 1740 1740 1740
-- Name: pk_data_object_type_permissions; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_type_permissions
    ADD CONSTRAINT pk_data_object_type_permissions PRIMARY KEY (organization_id, data_object_type_id, user_right_type_id, permission_id);


--
-- TOC entry 2256 (class 2606 OID 307231)
-- Dependencies: 1741 1741
-- Name: pk_data_object_types; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_types
    ADD CONSTRAINT pk_data_object_types PRIMARY KEY (data_object_type_id);


--
-- TOC entry 2260 (class 2606 OID 307233)
-- Dependencies: 1742 1742
-- Name: pk_data_objects; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT pk_data_objects PRIMARY KEY (data_object_id);


--
-- TOC entry 2262 (class 2606 OID 307235)
-- Dependencies: 1743 1743 1743 1743
-- Name: pk_db_properties; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY db_properties
    ADD CONSTRAINT pk_db_properties PRIMARY KEY (access_level, related_object_id, property_key);


--
-- TOC entry 2264 (class 2606 OID 307237)
-- Dependencies: 1744 1744
-- Name: pk_delivery_certificate_assignments; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY delivery_certificate_assignments
    ADD CONSTRAINT pk_delivery_certificate_assignments PRIMARY KEY (delivery_certificate_id);


--
-- TOC entry 2266 (class 2606 OID 307239)
-- Dependencies: 1745 1745
-- Name: pk_delivery_certificate_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT pk_delivery_certificate_items PRIMARY KEY (certificate_item_id);


--
-- TOC entry 2268 (class 2606 OID 307241)
-- Dependencies: 1746 1746 1746
-- Name: pk_delivery_certificate_serial_numbers; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY delivery_certificate_serial_numbers
    ADD CONSTRAINT pk_delivery_certificate_serial_numbers PRIMARY KEY (certificate_item_id, serial_number);


--
-- TOC entry 2276 (class 2606 OID 307243)
-- Dependencies: 1747 1747
-- Name: pk_delivery_certificates; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT pk_delivery_certificates PRIMARY KEY (delivery_certificate_id);


--
-- TOC entry 2170 (class 2606 OID 307245)
-- Dependencies: 1713 1713
-- Name: pk_document_entities; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY business_documents
    ADD CONSTRAINT pk_document_entities PRIMARY KEY (document_id);


--
-- TOC entry 2280 (class 2606 OID 307247)
-- Dependencies: 1748 1748 1748
-- Name: pk_entity_sequences; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY entity_sequences
    ADD CONSTRAINT pk_entity_sequences PRIMARY KEY (entity_id, data_object_type_id);


--
-- TOC entry 2282 (class 2606 OID 307249)
-- Dependencies: 1749 1749
-- Name: pk_enum_classes; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY enum_classes
    ADD CONSTRAINT pk_enum_classes PRIMARY KEY (enum_class_id);


--
-- TOC entry 2286 (class 2606 OID 307251)
-- Dependencies: 1750 1750 1750
-- Name: pk_expressions; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY expressions
    ADD CONSTRAINT pk_expressions PRIMARY KEY (organization_id, expression_key);


--
-- TOC entry 2288 (class 2606 OID 307253)
-- Dependencies: 1751 1751
-- Name: pk_goods_receipt_dc_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY goods_receipt_dc_items
    ADD CONSTRAINT pk_goods_receipt_dc_items PRIMARY KEY (receipt_item_id);


--
-- TOC entry 2290 (class 2606 OID 307255)
-- Dependencies: 1752 1752
-- Name: pk_goods_receipt_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY goods_receipt_items
    ADD CONSTRAINT pk_goods_receipt_items PRIMARY KEY (receipt_item_id);


--
-- TOC entry 2292 (class 2606 OID 307257)
-- Dependencies: 1753 1753
-- Name: pk_goods_receipt_pi_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY goods_receipt_pi_items
    ADD CONSTRAINT pk_goods_receipt_pi_items PRIMARY KEY (receipt_item_id);


--
-- TOC entry 2294 (class 2606 OID 307259)
-- Dependencies: 1754 1754
-- Name: pk_goods_receipts; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY goods_receipts
    ADD CONSTRAINT pk_goods_receipts PRIMARY KEY (goods_receipt_id);


--
-- TOC entry 2296 (class 2606 OID 307261)
-- Dependencies: 1755 1755
-- Name: pk_job_titles; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY job_titles
    ADD CONSTRAINT pk_job_titles PRIMARY KEY (job_title_id);


--
-- TOC entry 2305 (class 2606 OID 307263)
-- Dependencies: 1759 1759
-- Name: pk_organization_configurations; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY organization_configurations
    ADD CONSTRAINT pk_organization_configurations PRIMARY KEY (organization_id);


--
-- TOC entry 2310 (class 2606 OID 307265)
-- Dependencies: 1761 1761
-- Name: pk_passports; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT pk_passports PRIMARY KEY (passport_id);


--
-- TOC entry 2433 (class 2606 OID 310017)
-- Dependencies: 1817 1817
-- Name: pk_personal_communication_contacts; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY personal_communication_contacts
    ADD CONSTRAINT pk_personal_communication_contacts PRIMARY KEY (personal_communication_contact_id);


--
-- TOC entry 2319 (class 2606 OID 307267)
-- Dependencies: 1764 1764
-- Name: pk_position_types; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY position_types
    ADD CONSTRAINT pk_position_types PRIMARY KEY (position_type_id);


--
-- TOC entry 2322 (class 2606 OID 307269)
-- Dependencies: 1765 1765
-- Name: pk_pricelist_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pricelist_items
    ADD CONSTRAINT pk_pricelist_items PRIMARY KEY (item_id);


--
-- TOC entry 2324 (class 2606 OID 307271)
-- Dependencies: 1766 1766
-- Name: pk_pricelists; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pricelists
    ADD CONSTRAINT pk_pricelists PRIMARY KEY (pricelist_id);


--
-- TOC entry 2326 (class 2606 OID 307273)
-- Dependencies: 1767 1767
-- Name: pk_privilege_categories; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY privilege_categories
    ADD CONSTRAINT pk_privilege_categories PRIMARY KEY (privilege_category_id);


--
-- TOC entry 2329 (class 2606 OID 307275)
-- Dependencies: 1768 1768
-- Name: pk_privilege_roles; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY privilege_roles
    ADD CONSTRAINT pk_privilege_roles PRIMARY KEY (privilege_role_id);


--
-- TOC entry 2333 (class 2606 OID 307277)
-- Dependencies: 1769 1769
-- Name: pk_privileges; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY privileges
    ADD CONSTRAINT pk_privileges PRIMARY KEY (privilege_id);


--
-- TOC entry 2336 (class 2606 OID 307279)
-- Dependencies: 1770 1770
-- Name: pk_product_categories; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT pk_product_categories PRIMARY KEY (product_category_id);


--
-- TOC entry 2344 (class 2606 OID 307281)
-- Dependencies: 1772 1772 1772
-- Name: pk_product_suppliers; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT pk_product_suppliers PRIMARY KEY (product_id, supplier_id);


--
-- TOC entry 2388 (class 2606 OID 307283)
-- Dependencies: 1789 1789
-- Name: pk_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT pk_products PRIMARY KEY (product_id);


--
-- TOC entry 2346 (class 2606 OID 307285)
-- Dependencies: 1773 1773
-- Name: pk_products1; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY products
    ADD CONSTRAINT pk_products1 PRIMARY KEY (product_id);


--
-- TOC entry 2352 (class 2606 OID 307287)
-- Dependencies: 1774 1774
-- Name: pk_purchase_invoice_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT pk_purchase_invoice_items PRIMARY KEY (invoice_item_id);


--
-- TOC entry 2354 (class 2606 OID 307289)
-- Dependencies: 1775 1775
-- Name: pk_purchase_invoices; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY purchase_invoices
    ADD CONSTRAINT pk_purchase_invoices PRIMARY KEY (invoice_id);


--
-- TOC entry 2356 (class 2606 OID 307291)
-- Dependencies: 1776 1776
-- Name: pk_purchase_order_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT pk_purchase_order_items PRIMARY KEY (order_item_id);


--
-- TOC entry 2360 (class 2606 OID 307293)
-- Dependencies: 1778 1778
-- Name: pk_real_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY real_products
    ADD CONSTRAINT pk_real_products PRIMARY KEY (product_id);


--
-- TOC entry 2364 (class 2606 OID 307295)
-- Dependencies: 1779 1779
-- Name: pk_receipt_certificate_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT pk_receipt_certificate_items PRIMARY KEY (certificate_item_id);


--
-- TOC entry 2366 (class 2606 OID 307297)
-- Dependencies: 1780 1780 1780
-- Name: pk_receipt_certificate_serial_numbers; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY receipt_certificate_serial_numbers
    ADD CONSTRAINT pk_receipt_certificate_serial_numbers PRIMARY KEY (certificate_item_id, serial_number);


--
-- TOC entry 2368 (class 2606 OID 307299)
-- Dependencies: 1781 1781
-- Name: pk_receipt_certificates; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT pk_receipt_certificates PRIMARY KEY (receipt_certificate_id);


--
-- TOC entry 2372 (class 2606 OID 307301)
-- Dependencies: 1782 1782
-- Name: pk_registration_codes; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY registration_codes
    ADD CONSTRAINT pk_registration_codes PRIMARY KEY (registration_code);


--
-- TOC entry 2375 (class 2606 OID 307303)
-- Dependencies: 1783 1783
-- Name: pk_resource_bundle; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY resource_bundle
    ADD CONSTRAINT pk_resource_bundle PRIMARY KEY (resource_id);


--
-- TOC entry 2383 (class 2606 OID 307305)
-- Dependencies: 1787 1787
-- Name: pk_security_roles; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY security_roles
    ADD CONSTRAINT pk_security_roles PRIMARY KEY (security_role_id);


--
-- TOC entry 2390 (class 2606 OID 307307)
-- Dependencies: 1790 1790
-- Name: pk_team_members; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY team_members
    ADD CONSTRAINT pk_team_members PRIMARY KEY (team_member_id);


--
-- TOC entry 2394 (class 2606 OID 307309)
-- Dependencies: 1791 1791
-- Name: pk_teams; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY teams
    ADD CONSTRAINT pk_teams PRIMARY KEY (team_id);


--
-- TOC entry 2399 (class 2606 OID 307311)
-- Dependencies: 1792 1792
-- Name: pk_user_group_members; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY user_group_members
    ADD CONSTRAINT pk_user_group_members PRIMARY KEY (user_group_member_id);


--
-- TOC entry 2406 (class 2606 OID 307313)
-- Dependencies: 1794 1794
-- Name: pk_user_organizations; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY user_organizations
    ADD CONSTRAINT pk_user_organizations PRIMARY KEY (user_organization_id);


--
-- TOC entry 2410 (class 2606 OID 307315)
-- Dependencies: 1795 1795
-- Name: pk_user_rights; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY user_rights
    ADD CONSTRAINT pk_user_rights PRIMARY KEY (user_right_id);


--
-- TOC entry 2417 (class 2606 OID 307317)
-- Dependencies: 1797 1797
-- Name: pk_user_security_roles; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY user_security_roles
    ADD CONSTRAINT pk_user_security_roles PRIMARY KEY (user_security_role_id);


--
-- TOC entry 2421 (class 2606 OID 307319)
-- Dependencies: 1798 1798
-- Name: pk_users; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT pk_users PRIMARY KEY (user_id);


--
-- TOC entry 2425 (class 2606 OID 307321)
-- Dependencies: 1799 1799
-- Name: pk_uuid_test; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY uuid_test
    ADD CONSTRAINT pk_uuid_test PRIMARY KEY (test_id);


--
-- TOC entry 2427 (class 2606 OID 307323)
-- Dependencies: 1800 1800
-- Name: pk_virtual_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY virtual_products
    ADD CONSTRAINT pk_virtual_products PRIMARY KEY (product_id);


--
-- TOC entry 2431 (class 2606 OID 307325)
-- Dependencies: 1802 1802
-- Name: pk_warehouses; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY warehouses
    ADD CONSTRAINT pk_warehouses PRIMARY KEY (warehouse_id);


--
-- TOC entry 2340 (class 2606 OID 307327)
-- Dependencies: 1771 1771
-- Name: product_pricing_value_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY product_percent_values
    ADD CONSTRAINT product_pricing_value_pkey PRIMARY KEY (percent_value_id);


--
-- TOC entry 2358 (class 2606 OID 307329)
-- Dependencies: 1777 1777
-- Name: purchase_orders_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT purchase_orders_pkey PRIMARY KEY (purchase_order_id);


--
-- TOC entry 2142 (class 2606 OID 307331)
-- Dependencies: 1705 1705 1705 1705
-- Name: uk_assembling_categories_by_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_categories
    ADD CONSTRAINT uk_assembling_categories_by_code UNIQUE (parent_id, parent_category_id, category_code);


--
-- TOC entry 2144 (class 2606 OID 307333)
-- Dependencies: 1705 1705 1705 1705
-- Name: uk_assembling_categories_by_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_categories
    ADD CONSTRAINT uk_assembling_categories_by_name UNIQUE (parent_id, parent_category_id, category_name);


--
-- TOC entry 2148 (class 2606 OID 307335)
-- Dependencies: 1706 1706 1706
-- Name: uk_assembling_messages_org_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_messages
    ADD CONSTRAINT uk_assembling_messages_org_code UNIQUE (organization_id, message_code);


--
-- TOC entry 2156 (class 2606 OID 307337)
-- Dependencies: 1709 1709 1709
-- Name: uk_assembling_schemas_by_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT uk_assembling_schemas_by_code UNIQUE (category_id, schema_code);


--
-- TOC entry 2158 (class 2606 OID 307339)
-- Dependencies: 1709 1709 1709
-- Name: uk_assembling_schemas_by_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT uk_assembling_schemas_by_name UNIQUE (category_id, schema_name);


--
-- TOC entry 2162 (class 2606 OID 326427)
-- Dependencies: 1710 1710 1710 1710 1710
-- Name: uk_bank_details; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT uk_bank_details UNIQUE (address_id, currency_id, bank_id, bank_branch_id);


--
-- TOC entry 2166 (class 2606 OID 309974)
-- Dependencies: 1711 1711 1711
-- Name: uk_banknote_quantity_cash_reconcile_currency_nominal; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY banknote_quantity
    ADD CONSTRAINT uk_banknote_quantity_cash_reconcile_currency_nominal UNIQUE (cash_reconcile_id, currency_nominal_id);


--
-- TOC entry 2172 (class 2606 OID 307341)
-- Dependencies: 1713 1713 1713 1713
-- Name: uk_business_documents_publisher_doc_type_number; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY business_documents
    ADD CONSTRAINT uk_business_documents_publisher_doc_type_number UNIQUE (publisher_id, document_type_id, document_number);


--
-- TOC entry 2178 (class 2606 OID 307343)
-- Dependencies: 1715 1715 1715
-- Name: uk_business_unit_addresses; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY business_unit_addresses
    ADD CONSTRAINT uk_business_unit_addresses UNIQUE (business_unit_id, address_id);


--
-- TOC entry 2188 (class 2606 OID 309976)
-- Dependencies: 1718 1718 1718 1718
-- Name: uk_cash_reconcile_payment_summary; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY cash_reconcile_payment_summary
    ADD CONSTRAINT uk_cash_reconcile_payment_summary UNIQUE (cash_reconcile_id, payment_type_id, currency_id);


--
-- TOC entry 2213 (class 2606 OID 307351)
-- Dependencies: 1727 1727 1727
-- Name: uk_contact_persons_address_person; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT uk_contact_persons_address_person UNIQUE (address_id, person_id);


--
-- TOC entry 2224 (class 2606 OID 309978)
-- Dependencies: 1730 1730 1730
-- Name: uk_currency_nominal; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY currency_nominal
    ADD CONSTRAINT uk_currency_nominal UNIQUE (currency_id, nominal_value);


--
-- TOC entry 2230 (class 2606 OID 307353)
-- Dependencies: 1732 1732 1732
-- Name: uk_customer_discount_items_by_categories; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customer_discount_items_by_categories
    ADD CONSTRAINT uk_customer_discount_items_by_categories UNIQUE (customer_discount_id, category_id);


--
-- TOC entry 2234 (class 2606 OID 307355)
-- Dependencies: 1733 1733 1733
-- Name: uk_customer_discount_items_by_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customer_discount_items_by_products
    ADD CONSTRAINT uk_customer_discount_items_by_products UNIQUE (customer_discount_id, product_id);


--
-- TOC entry 2238 (class 2606 OID 307357)
-- Dependencies: 1734 1734 1734
-- Name: uk_customer_discounts; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customer_discounts
    ADD CONSTRAINT uk_customer_discounts UNIQUE (organization_id, customer_id);


--
-- TOC entry 2246 (class 2606 OID 307359)
-- Dependencies: 1737 1737 1737
-- Name: uk_data_object_details_do_id_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_details
    ADD CONSTRAINT uk_data_object_details_do_id_code UNIQUE (data_object_id, detail_code);


--
-- TOC entry 2250 (class 2606 OID 307361)
-- Dependencies: 1738 1738 1738
-- Name: uk_data_object_links_parent_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_links
    ADD CONSTRAINT uk_data_object_links_parent_name UNIQUE (parent_id, link_name);


--
-- TOC entry 2278 (class 2606 OID 307363)
-- Dependencies: 1747 1747 1747
-- Name: uk_delivery_certificates_parent_cert_number; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT uk_delivery_certificates_parent_cert_number UNIQUE (parent_id, delivery_certificate_number);


--
-- TOC entry 2258 (class 2606 OID 307365)
-- Dependencies: 1741 1741
-- Name: uk_dot_data_object_type; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_types
    ADD CONSTRAINT uk_dot_data_object_type UNIQUE (data_object_type);


--
-- TOC entry 2284 (class 2606 OID 307367)
-- Dependencies: 1749 1749
-- Name: uk_enum_classes; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY enum_classes
    ADD CONSTRAINT uk_enum_classes UNIQUE (enum_class_name);


--
-- TOC entry 2435 (class 2606 OID 310019)
-- Dependencies: 1817 1817 1817
-- Name: uk_personal_communication_contacts; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY personal_communication_contacts
    ADD CONSTRAINT uk_personal_communication_contacts UNIQUE (contact_person_id, communication_contact_id);


--
-- TOC entry 2331 (class 2606 OID 307375)
-- Dependencies: 1768 1768 1768
-- Name: uk_privilege_roles_privilege_right; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY privilege_roles
    ADD CONSTRAINT uk_privilege_roles_privilege_right UNIQUE (privilege_id, access_right_id);


--
-- TOC entry 2338 (class 2606 OID 307379)
-- Dependencies: 1770 1770 1770
-- Name: uk_product_categories_parent_category_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT uk_product_categories_parent_category_name UNIQUE (parent_id, category_name);


--
-- TOC entry 2342 (class 2606 OID 307381)
-- Dependencies: 1771 1771 1771 1771
-- Name: uk_product_percent_values; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY product_percent_values
    ADD CONSTRAINT uk_product_percent_values UNIQUE (organization_id, value_type_id, value_name);


--
-- TOC entry 2348 (class 2606 OID 307383)
-- Dependencies: 1773 1773 1773
-- Name: uk_products_parent_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY products
    ADD CONSTRAINT uk_products_parent_code UNIQUE (parent_id, product_code);


--
-- TOC entry 2350 (class 2606 OID 307385)
-- Dependencies: 1773 1773 1773
-- Name: uk_products_parent_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY products
    ADD CONSTRAINT uk_products_parent_name UNIQUE (parent_id, product_name);


--
-- TOC entry 2362 (class 2606 OID 307387)
-- Dependencies: 1778 1778
-- Name: uk_real_products_by_simple_product; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY real_products
    ADD CONSTRAINT uk_real_products_by_simple_product UNIQUE (simple_product_id);


--
-- TOC entry 2370 (class 2606 OID 307389)
-- Dependencies: 1781 1781 1781
-- Name: uk_receipt_certificates_parent_cert_number; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT uk_receipt_certificates_parent_cert_number UNIQUE (parent_id, receipt_certificate_number);


--
-- TOC entry 2386 (class 2606 OID 307393)
-- Dependencies: 1788 1788
-- Name: uk_seq_id_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY sequence_identifiers
    ADD CONSTRAINT uk_seq_id_name UNIQUE (seq_id_name);


--
-- TOC entry 2392 (class 2606 OID 307395)
-- Dependencies: 1790 1790 1790
-- Name: uk_team_members_team_user; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY team_members
    ADD CONSTRAINT uk_team_members_team_user UNIQUE (team_id, user_organization_id);


--
-- TOC entry 2397 (class 2606 OID 307397)
-- Dependencies: 1791 1791 1791
-- Name: uk_teams_organization_team_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY teams
    ADD CONSTRAINT uk_teams_organization_team_name UNIQUE (organization_id, team_name);


--
-- TOC entry 2401 (class 2606 OID 307399)
-- Dependencies: 1792 1792 1792
-- Name: uk_user_group_members; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY user_group_members
    ADD CONSTRAINT uk_user_group_members UNIQUE (user_group_id, user_organization_id);


--
-- TOC entry 2408 (class 2606 OID 307401)
-- Dependencies: 1794 1794 1794
-- Name: uk_user_organizations; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY user_organizations
    ADD CONSTRAINT uk_user_organizations UNIQUE (user_id, organization_id);


--
-- TOC entry 2419 (class 2606 OID 307403)
-- Dependencies: 1797 1797 1797
-- Name: uk_user_security_roles_user_sr; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY user_security_roles
    ADD CONSTRAINT uk_user_security_roles_user_sr UNIQUE (user_organization_id, security_role_id);


--
-- TOC entry 2404 (class 2606 OID 307405)
-- Dependencies: 1793 1793
-- Name: user_groups_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY user_groups
    ADD CONSTRAINT user_groups_pkey PRIMARY KEY (user_group_id);


--
-- TOC entry 2415 (class 2606 OID 307407)
-- Dependencies: 1796 1796
-- Name: user_rights_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT user_rights_pkey PRIMARY KEY (user_right_id);


--
-- TOC entry 2429 (class 2606 OID 307409)
-- Dependencies: 1801 1801
-- Name: warehouse_products_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY warehouse_products
    ADD CONSTRAINT warehouse_products_pkey PRIMARY KEY (warehouse_product_id);


--
-- TOC entry 2269 (class 1259 OID 307411)
-- Dependencies: 1747
-- Name: fki_certificate_status; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX fki_certificate_status ON delivery_certificates USING btree (delivery_cert_status_id);


--
-- TOC entry 2270 (class 1259 OID 307412)
-- Dependencies: 1747
-- Name: fki_creator_branch; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX fki_creator_branch ON delivery_certificates USING btree (creator_branch_id);


--
-- TOC entry 2271 (class 1259 OID 307413)
-- Dependencies: 1747
-- Name: fki_creator_organization; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX fki_creator_organization ON delivery_certificates USING btree (creator_organization_id);


--
-- TOC entry 2272 (class 1259 OID 307414)
-- Dependencies: 1747
-- Name: fki_delivery_certificates_recipient; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX fki_delivery_certificates_recipient ON delivery_certificates USING btree (recipient_id);


--
-- TOC entry 2273 (class 1259 OID 307415)
-- Dependencies: 1747
-- Name: fki_forwarder_address_constraint; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX fki_forwarder_address_constraint ON delivery_certificates USING btree (forwarder_branch_id);


--
-- TOC entry 2274 (class 1259 OID 307416)
-- Dependencies: 1747
-- Name: fki_recipient_address_constraint; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX fki_recipient_address_constraint ON delivery_certificates USING btree (recipient_branch_id);


--
-- TOC entry 2179 (class 1259 OID 326388)
-- Dependencies: 1716 1716
-- Name: idx_business_units_parent_child; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX idx_business_units_parent_child ON business_units USING btree (parent_business_unit_id, business_unit_id);


--
-- TOC entry 2182 (class 1259 OID 326389)
-- Dependencies: 1716 1716
-- Name: uidx_business_units_name; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uidx_business_units_name ON business_units USING btree (organization_id, lower((business_unit_name)::text));


--
-- TOC entry 2395 (class 1259 OID 307418)
-- Dependencies: 1791 1791
-- Name: uidx_teams_organization_team_name; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uidx_teams_organization_team_name ON teams USING btree (organization_id, team_name);


--
-- TOC entry 2402 (class 1259 OID 307419)
-- Dependencies: 1793 1793
-- Name: uidx_user_groups_organization_ugname; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uidx_user_groups_organization_ugname ON user_groups USING btree (organization_id, user_group_name);


--
-- TOC entry 2411 (class 1259 OID 307420)
-- Dependencies: 1795 1795 1795 1795 1795 1795 1795 1795 1795 1795
-- Name: uidx_user_rights; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uidx_user_rights ON user_rights USING btree (organization_id, owner_type_id, user_id, user_group_id, access_rights, excluded, data_object_type_id, data_object_id, permission_category_id, special_permission_id);


--
-- TOC entry 2138 (class 1259 OID 326390)
-- Dependencies: 1704 1704
-- Name: uix_addresses_business_partner_address_name; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_addresses_business_partner_address_name ON addresses USING btree (business_partner_id, lower((address_name)::text));


--
-- TOC entry 2191 (class 1259 OID 310011)
-- Dependencies: 1719 1719
-- Name: uix_cities_country_city_code; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_cities_country_city_code ON cities USING btree (country_id, lower((city_code)::text));


--
-- TOC entry 2192 (class 1259 OID 307421)
-- Dependencies: 1719 1719
-- Name: uix_cities_country_city_name; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_cities_country_city_name ON cities USING btree (country_id, lower((city_name)::text));


--
-- TOC entry 2199 (class 1259 OID 326425)
-- Dependencies: 1722 1722
-- Name: uix_classifier_groups_business_partner_classifier_group_code; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_classifier_groups_business_partner_classifier_group_code ON classifier_groups USING btree (business_partner_id, lower((classifier_group_code)::text));


--
-- TOC entry 2202 (class 1259 OID 326419)
-- Dependencies: 1723 1723
-- Name: uix_classifiers_business_partner_classifier_code; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_classifiers_business_partner_classifier_code ON classifiers USING btree (business_partner_id, lower((classifier_code)::text));


--
-- TOC entry 2205 (class 1259 OID 310012)
-- Dependencies: 1724 1724 1724
-- Name: uix_communication_contacts_parent_type_value_contact_person; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_communication_contacts_parent_type_value_contact_person ON communication_contacts USING btree (address_id, communication_type_id, lower((communication_value)::text));


--
-- TOC entry 2216 (class 1259 OID 310004)
-- Dependencies: 1728
-- Name: uix_countries_code_a2; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_countries_code_a2 ON countries USING btree (lower((country_code_a2)::text));


--
-- TOC entry 2217 (class 1259 OID 310005)
-- Dependencies: 1728
-- Name: uix_countries_code_a3; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_countries_code_a3 ON countries USING btree (lower((country_code_a3)::text));


--
-- TOC entry 2218 (class 1259 OID 307422)
-- Dependencies: 1728
-- Name: uix_countries_country_name; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_countries_country_name ON countries USING btree (lower((country_name)::text));


--
-- TOC entry 2297 (class 1259 OID 334581)
-- Dependencies: 1755 1755
-- Name: uix_job_titles_business_unit_job_title; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_job_titles_business_unit_job_title ON job_titles USING btree (business_unit_id, lower((job_title)::text));


--
-- TOC entry 2308 (class 1259 OID 307423)
-- Dependencies: 1760 1760
-- Name: uix_organizations_parent_business_partner_organization_name; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_organizations_parent_business_partner_organization_name ON organizations USING btree (parent_business_partner_id, lower((organization_name)::text));


--
-- TOC entry 2311 (class 1259 OID 334580)
-- Dependencies: 1761 1761 1761
-- Name: uix_passports_person_type_number; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_passports_person_type_number ON passports USING btree (person_id, passport_type_id, lower((passport_number)::text));


--
-- TOC entry 2316 (class 1259 OID 307424)
-- Dependencies: 1763 1763 1763 1763 1763 1763 1763
-- Name: uix_persons_names_birth_date_city; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_persons_names_birth_date_city ON persons USING btree (parent_business_partner_id, lower((first_name)::text), lower((last_name)::text), lower((second_name)::text), lower((extra_name)::text), birth_date, birth_place_city_id);


--
-- TOC entry 2317 (class 1259 OID 307425)
-- Dependencies: 1763 1763 1763
-- Name: uix_persons_personal_unuque_id; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_persons_personal_unuque_id ON persons USING btree (parent_business_partner_id, birth_place_country_id, lower((personal_unique_id)::text));


--
-- TOC entry 2320 (class 1259 OID 310040)
-- Dependencies: 1764 1764
-- Name: uix_position_types; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_position_types ON position_types USING btree (business_partner_id, lower((position_type_name)::text));


--
-- TOC entry 2327 (class 1259 OID 342773)
-- Dependencies: 1767 1767
-- Name: uix_privilege_categories_organization_category_name; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_privilege_categories_organization_category_name ON privilege_categories USING btree (organization_id, lower((category_name)::text));


--
-- TOC entry 2334 (class 1259 OID 342774)
-- Dependencies: 1769 1769
-- Name: uix_privileges_role_name; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_privileges_role_name ON privileges USING btree (security_role_id, lower((privilege_name)::text));


--
-- TOC entry 2373 (class 1259 OID 307426)
-- Dependencies: 1782
-- Name: uix_registration_codes_email_address; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_registration_codes_email_address ON registration_codes USING btree (lower((email_address)::text));


--
-- TOC entry 2384 (class 1259 OID 342775)
-- Dependencies: 1787 1787
-- Name: uix_security_roles_organization_role_name; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_security_roles_organization_role_name ON security_roles USING btree (organization_id, lower((security_role_name)::text));


--
-- TOC entry 2422 (class 1259 OID 307427)
-- Dependencies: 1798
-- Name: uix_users_email_address; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_users_email_address ON users USING btree (lower((email_address)::text));


--
-- TOC entry 2423 (class 1259 OID 307428)
-- Dependencies: 1798
-- Name: uix_users_username; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_users_username ON users USING btree (lower((user_name)::text));


--
-- TOC entry 2412 (class 1259 OID 307429)
-- Dependencies: 1796
-- Name: user_group_idx; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX user_group_idx ON user_rights_old USING btree (user_group_id);


--
-- TOC entry 2413 (class 1259 OID 307430)
-- Dependencies: 1796
-- Name: user_idx; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX user_idx ON user_rights_old USING btree (user_id);


--
-- TOC entry 2436 (class 2606 OID 307431)
-- Dependencies: 1704 1714 2173
-- Name: addresses_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT addresses_parent_id_fkey FOREIGN KEY (business_partner_id) REFERENCES business_partners(business_partner_id) ON DELETE CASCADE;


--
-- TOC entry 2464 (class 2606 OID 307436)
-- Dependencies: 1710 1783 2374
-- Name: bank_details_currency_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT bank_details_currency_id_fkey FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2465 (class 2606 OID 307441)
-- Dependencies: 1710 1704 2136
-- Name: bank_details_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT bank_details_parent_id_fkey FOREIGN KEY (address_id) REFERENCES addresses(address_id) ON DELETE CASCADE;


--
-- TOC entry 2508 (class 2606 OID 307446)
-- Dependencies: 1728 1719 2214
-- Name: cities_country_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cities
    ADD CONSTRAINT cities_country_id_fkey FOREIGN KEY (country_id) REFERENCES countries(country_id) ON DELETE CASCADE;


--
-- TOC entry 2510 (class 2606 OID 307451)
-- Dependencies: 1720 1742 2259
-- Name: classified_objects_classified_object_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classified_objects
    ADD CONSTRAINT classified_objects_classified_object_id_fkey FOREIGN KEY (classified_object_id) REFERENCES data_objects(data_object_id) ON DELETE CASCADE;


--
-- TOC entry 2514 (class 2606 OID 307456)
-- Dependencies: 2200 1721 1723
-- Name: classifier_applied_for_dot_classifier_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifier_applied_for_dot
    ADD CONSTRAINT classifier_applied_for_dot_classifier_id_fkey FOREIGN KEY (classifier_id) REFERENCES classifiers(classifier_id) ON DELETE CASCADE;


--
-- TOC entry 2523 (class 2606 OID 307466)
-- Dependencies: 2136 1724 1704
-- Name: communication_contacts_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT communication_contacts_parent_id_fkey FOREIGN KEY (address_id) REFERENCES addresses(address_id) ON DELETE CASCADE;


--
-- TOC entry 2534 (class 2606 OID 307471)
-- Dependencies: 2314 1763 1727
-- Name: contact_persons_contact_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT contact_persons_contact_id_fkey FOREIGN KEY (person_id) REFERENCES persons(person_id) ON DELETE CASCADE;


--
-- TOC entry 2535 (class 2606 OID 307476)
-- Dependencies: 2136 1704 1727
-- Name: contact_persons_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT contact_persons_parent_id_fkey FOREIGN KEY (address_id) REFERENCES addresses(address_id) ON DELETE CASCADE;


--
-- TOC entry 2509 (class 2606 OID 307481)
-- Dependencies: 2259 1742 1719
-- Name: data_object_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cities
    ADD CONSTRAINT data_object_fk FOREIGN KEY (city_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2891 (class 2606 OID 307486)
-- Dependencies: 2255 1741 1796
-- Name: data_object_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT data_object_type FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id) ON DELETE CASCADE;


--
-- TOC entry 2892 (class 2606 OID 307491)
-- Dependencies: 2259 1742 1796
-- Name: data_objects; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT data_objects FOREIGN KEY (data_object_id) REFERENCES data_objects(data_object_id) ON DELETE CASCADE;


--
-- TOC entry 2789 (class 2606 OID 307496)
-- Dependencies: 1749 1783 2281
-- Name: fk11ef5dd39219a9be; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY resource_bundle
    ADD CONSTRAINT fk11ef5dd39219a9be FOREIGN KEY (enum_class_id) REFERENCES enum_classes(enum_class_id);


--
-- TOC entry 2443 (class 2606 OID 307501)
-- Dependencies: 2306 1760 1706
-- Name: fk2415657c51b04573; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_messages
    ADD CONSTRAINT fk2415657c51b04573 FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2799 (class 2606 OID 307506)
-- Dependencies: 1786 2173 1714
-- Name: fk25f222e6134fe2b0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e6134fe2b0 FOREIGN KEY (recipient_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2800 (class 2606 OID 307511)
-- Dependencies: 1783 1786 2374
-- Name: fk25f222e617174fab; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e617174fab FOREIGN KEY (transportation_method_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2801 (class 2606 OID 307516)
-- Dependencies: 1786 2374 1783
-- Name: fk25f222e61808129a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e61808129a FOREIGN KEY (doc_delivery_method_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2802 (class 2606 OID 307521)
-- Dependencies: 1704 2136 1786
-- Name: fk25f222e627a66c93; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e627a66c93 FOREIGN KEY (branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2803 (class 2606 OID 307526)
-- Dependencies: 2374 1783 1786
-- Name: fk25f222e63aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e63aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2804 (class 2606 OID 307531)
-- Dependencies: 2374 1783 1786
-- Name: fk25f222e63dc9408c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e63dc9408c FOREIGN KEY (payment_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2805 (class 2606 OID 307536)
-- Dependencies: 1786 1783 2374
-- Name: fk25f222e646685c7a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e646685c7a FOREIGN KEY (delivery_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2806 (class 2606 OID 307541)
-- Dependencies: 1763 1786 2314
-- Name: fk25f222e64da6f8bc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e64da6f8bc FOREIGN KEY (creator_id) REFERENCES persons(person_id);


--
-- TOC entry 2807 (class 2606 OID 307546)
-- Dependencies: 1786 2173 1714
-- Name: fk25f222e66d20f4c9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e66d20f4c9 FOREIGN KEY (shippingagent_partner_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2808 (class 2606 OID 307551)
-- Dependencies: 1786 2210 1727
-- Name: fk25f222e67ebcc009; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e67ebcc009 FOREIGN KEY (supplier_contact_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2809 (class 2606 OID 307556)
-- Dependencies: 2374 1783 1786
-- Name: fk25f222e696e3ba71; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e696e3ba71 FOREIGN KEY (payment_terms_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2810 (class 2606 OID 307561)
-- Dependencies: 1783 2374 1786
-- Name: fk25f222e69a24d298; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e69a24d298 FOREIGN KEY (deliverystatus_resource_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2811 (class 2606 OID 307566)
-- Dependencies: 1783 2374 1786
-- Name: fk25f222e69c49320d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e69c49320d FOREIGN KEY (status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2812 (class 2606 OID 307571)
-- Dependencies: 2210 1786 1727
-- Name: fk25f222e69ff294dc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e69ff294dc FOREIGN KEY (attendee_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2813 (class 2606 OID 307576)
-- Dependencies: 2374 1786 1783
-- Name: fk25f222e6a94f3ab3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e6a94f3ab3 FOREIGN KEY (invoice_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2814 (class 2606 OID 307581)
-- Dependencies: 2374 1786 1783
-- Name: fk25f222e6b07d659a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e6b07d659a FOREIGN KEY (vat_condition_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2815 (class 2606 OID 307586)
-- Dependencies: 2314 1786 1763
-- Name: fk25f222e6fd5b3613; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e6fd5b3613 FOREIGN KEY (sender_id) REFERENCES persons(person_id);


--
-- TOC entry 2440 (class 2606 OID 307591)
-- Dependencies: 2139 1705 1705
-- Name: fk265f5e4cdf9c931a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_categories
    ADD CONSTRAINT fk265f5e4cdf9c931a FOREIGN KEY (parent_category_id) REFERENCES assembling_categories(assembling_category_id);


--
-- TOC entry 2599 (class 2606 OID 307596)
-- Dependencies: 1743 1742 2259
-- Name: fk26dbbb94399bdd69; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY db_properties
    ADD CONSTRAINT fk26dbbb94399bdd69 FOREIGN KEY (related_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2526 (class 2606 OID 307601)
-- Dependencies: 1725 1783 2374
-- Name: fk281fab21a2454cf2; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT fk281fab21a2454cf2 FOREIGN KEY (applied_algorithm_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2527 (class 2606 OID 307606)
-- Dependencies: 1725 1726 2208
-- Name: fk281fab21d06049d2; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT fk281fab21d06049d2 FOREIGN KEY (complex_product_id) REFERENCES complex_products(product_id);


--
-- TOC entry 2528 (class 2606 OID 307611)
-- Dependencies: 1725 1773 2345
-- Name: fk281fab21f10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT fk281fab21f10b9721 FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2793 (class 2606 OID 307616)
-- Dependencies: 1785 1783 2374
-- Name: fk326ab82e1ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoice_items
    ADD CONSTRAINT fk326ab82e1ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2794 (class 2606 OID 307621)
-- Dependencies: 1785 1802 2430
-- Name: fk326ab82e9f1800e1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoice_items
    ADD CONSTRAINT fk326ab82e9f1800e1 FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2795 (class 2606 OID 307626)
-- Dependencies: 2345 1785 1773
-- Name: fk326ab82ef10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoice_items
    ADD CONSTRAINT fk326ab82ef10b9721 FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2668 (class 2606 OID 307631)
-- Dependencies: 1704 1757 2136
-- Name: fk327473ad27a66c93; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk327473ad27a66c93 FOREIGN KEY (branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2669 (class 2606 OID 307636)
-- Dependencies: 1783 1757 2374
-- Name: fk327473ad3aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk327473ad3aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2670 (class 2606 OID 307641)
-- Dependencies: 1714 1757 2173
-- Name: fk327473ad5aa049f4; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk327473ad5aa049f4 FOREIGN KEY (supplier_partner_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2671 (class 2606 OID 307646)
-- Dependencies: 1727 1757 2210
-- Name: fk327473ad7ebcc009; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk327473ad7ebcc009 FOREIGN KEY (supplier_contact_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2672 (class 2606 OID 307651)
-- Dependencies: 2374 1757 1783
-- Name: fk327473ada97faa1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk327473ada97faa1 FOREIGN KEY (document_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2548 (class 2606 OID 307656)
-- Dependencies: 1732 1734 2235
-- Name: fk34a6c58834ee818e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items_by_categories
    ADD CONSTRAINT fk34a6c58834ee818e FOREIGN KEY (customer_discount_id) REFERENCES customer_discounts(customer_discount_id);


--
-- TOC entry 2549 (class 2606 OID 307661)
-- Dependencies: 2335 1732 1770
-- Name: fk34a6c5886e2f83d0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items_by_categories
    ADD CONSTRAINT fk34a6c5886e2f83d0 FOREIGN KEY (category_id) REFERENCES product_categories(product_category_id);


--
-- TOC entry 2550 (class 2606 OID 307666)
-- Dependencies: 1731 1732 2225
-- Name: fk34a6c588d3502ad3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items_by_categories
    ADD CONSTRAINT fk34a6c588d3502ad3 FOREIGN KEY (customer_discount_item_id) REFERENCES customer_discount_items(customer_discount_item_id);


--
-- TOC entry 2466 (class 2606 OID 307671)
-- Dependencies: 1704 1710 2136
-- Name: fk363aa33f2f5fd250; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk363aa33f2f5fd250 FOREIGN KEY (bank_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2467 (class 2606 OID 307676)
-- Dependencies: 1783 1710 2374
-- Name: fk363aa33f3aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk363aa33f3aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2468 (class 2606 OID 307681)
-- Dependencies: 1760 1710 2306
-- Name: fk363aa33fee88a3ca; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk363aa33fee88a3ca FOREIGN KEY (bank_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2469 (class 2606 OID 307686)
-- Dependencies: 1763 1710 2314
-- Name: fk363aa33ff339b22b; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk363aa33ff339b22b FOREIGN KEY (bank_contact_id) REFERENCES persons(person_id);


--
-- TOC entry 2610 (class 2606 OID 307691)
-- Dependencies: 1747 1714 2173
-- Name: fk3edb4c27134fe2b0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c27134fe2b0 FOREIGN KEY (recipient_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2611 (class 2606 OID 307696)
-- Dependencies: 1747 1763 2314
-- Name: fk3edb4c27157c075; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c27157c075 FOREIGN KEY (forwarder_contact_id) REFERENCES persons(person_id);


--
-- TOC entry 2612 (class 2606 OID 307701)
-- Dependencies: 2136 1747 1704
-- Name: fk3edb4c273364a040; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c273364a040 FOREIGN KEY (creator_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2613 (class 2606 OID 307706)
-- Dependencies: 1783 1747 2374
-- Name: fk3edb4c2736e2c55d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c2736e2c55d FOREIGN KEY (delivery_cert_status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2614 (class 2606 OID 307711)
-- Dependencies: 1727 1747 2210
-- Name: fk3edb4c273b8b059c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c273b8b059c FOREIGN KEY (recipient_contact_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2615 (class 2606 OID 307716)
-- Dependencies: 1783 1747 2374
-- Name: fk3edb4c2746dd317; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c2746dd317 FOREIGN KEY (delivery_cert_method_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2616 (class 2606 OID 307721)
-- Dependencies: 1763 1747 2314
-- Name: fk3edb4c274da6f8bc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c274da6f8bc FOREIGN KEY (creator_id) REFERENCES persons(person_id);


--
-- TOC entry 2617 (class 2606 OID 307726)
-- Dependencies: 2136 1747 1704
-- Name: fk3edb4c277c77cded; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c277c77cded FOREIGN KEY (recipient_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2618 (class 2606 OID 307731)
-- Dependencies: 1747 1783 2374
-- Name: fk3edb4c278a6109cb; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c278a6109cb FOREIGN KEY (delivery_cert_reason_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2619 (class 2606 OID 307736)
-- Dependencies: 2430 1747 1802
-- Name: fk3edb4c279f1800e1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c279f1800e1 FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2620 (class 2606 OID 307741)
-- Dependencies: 1760 1747 2306
-- Name: fk3edb4c27b4524360; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c27b4524360 FOREIGN KEY (creator_organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2621 (class 2606 OID 307746)
-- Dependencies: 1704 1747 2136
-- Name: fk3edb4c27e581d2c6; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c27e581d2c6 FOREIGN KEY (forwarder_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2622 (class 2606 OID 307751)
-- Dependencies: 1760 1747 2306
-- Name: fk3edb4c27f2569c14; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c27f2569c14 FOREIGN KEY (forwarder_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2688 (class 2606 OID 307756)
-- Dependencies: 1714 1762 2173
-- Name: fk40afd1582b1363d6; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pattern_mask_formats
    ADD CONSTRAINT fk40afd1582b1363d6 FOREIGN KEY (owner_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2578 (class 2606 OID 307761)
-- Dependencies: 1739 2374 1783
-- Name: fk40c96239b01192e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_permissions
    ADD CONSTRAINT fk40c96239b01192e FOREIGN KEY (user_right_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2579 (class 2606 OID 307766)
-- Dependencies: 2374 1783 1739
-- Name: fk40c96239c2559310; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_permissions
    ADD CONSTRAINT fk40c96239c2559310 FOREIGN KEY (permission_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2580 (class 2606 OID 307771)
-- Dependencies: 2259 1739 1742
-- Name: fk40c96239d741e28; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_permissions
    ADD CONSTRAINT fk40c96239d741e28 FOREIGN KEY (data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2796 (class 2606 OID 307776)
-- Dependencies: 2374 1785 1783
-- Name: fk46500e3b1ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoice_items
    ADD CONSTRAINT fk46500e3b1ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2797 (class 2606 OID 307781)
-- Dependencies: 1785 1802 2430
-- Name: fk46500e3b9f1800e1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoice_items
    ADD CONSTRAINT fk46500e3b9f1800e1 FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2798 (class 2606 OID 307786)
-- Dependencies: 1773 2345 1785
-- Name: fk46500e3bf10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoice_items
    ADD CONSTRAINT fk46500e3bf10b9721 FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2546 (class 2606 OID 307791)
-- Dependencies: 2259 1731 1742
-- Name: fk51a781c3c35b8d6c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items
    ADD CONSTRAINT fk51a781c3c35b8d6c FOREIGN KEY (customer_discount_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2713 (class 2606 OID 307796)
-- Dependencies: 2335 1770 1770
-- Name: fk5519b36c1c57732d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT fk5519b36c1c57732d FOREIGN KEY (parent_cat_id) REFERENCES product_categories(product_category_id);


--
-- TOC entry 2714 (class 2606 OID 307801)
-- Dependencies: 2312 1762 1770
-- Name: fk5519b36c7a956e19; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT fk5519b36c7a956e19 FOREIGN KEY (pattern_mask_format_id) REFERENCES pattern_mask_formats(pattern_mask_format_id);


--
-- TOC entry 2644 (class 2606 OID 307806)
-- Dependencies: 1783 2374 1752
-- Name: fk582b0dd01ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_items
    ADD CONSTRAINT fk582b0dd01ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2645 (class 2606 OID 307811)
-- Dependencies: 2293 1754 1752
-- Name: fk582b0dd04d7c32a1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_items
    ADD CONSTRAINT fk582b0dd04d7c32a1 FOREIGN KEY (goods_receipt_id) REFERENCES goods_receipts(goods_receipt_id);


--
-- TOC entry 2646 (class 2606 OID 307816)
-- Dependencies: 1752 2259 1742
-- Name: fk582b0dd0cf28c922; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_items
    ADD CONSTRAINT fk582b0dd0cf28c922 FOREIGN KEY (receipt_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2647 (class 2606 OID 307821)
-- Dependencies: 1773 1752 2345
-- Name: fk582b0dd0f10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_items
    ADD CONSTRAINT fk582b0dd0f10b9721 FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2673 (class 2606 OID 307871)
-- Dependencies: 1756 1758 2298
-- Name: fk5f8caf2a59f71c23; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_item_match
    ADD CONSTRAINT fk5f8caf2a59f71c23 FOREIGN KEY (orderconfirmationitem_confirmation_item_id) REFERENCES order_confirmation_items(confirmation_item_id);


--
-- TOC entry 2674 (class 2606 OID 307876)
-- Dependencies: 1776 2355 1758
-- Name: fk5f8caf2a867a1de; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_item_match
    ADD CONSTRAINT fk5f8caf2a867a1de FOREIGN KEY (purchaseorderitem_order_item_id) REFERENCES purchase_order_items(order_item_id);


--
-- TOC entry 2446 (class 2606 OID 307881)
-- Dependencies: 1708 2151 1707
-- Name: fk61ac266056f0df10; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_item_values
    ADD CONSTRAINT fk61ac266056f0df10 FOREIGN KEY (item_id) REFERENCES assembling_schema_items(item_id);


--
-- TOC entry 2447 (class 2606 OID 307886)
-- Dependencies: 1800 2426 1707
-- Name: fk61ac266094a6e189; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_item_values
    ADD CONSTRAINT fk61ac266094a6e189 FOREIGN KEY (virtual_product_id) REFERENCES virtual_products(product_id);


--
-- TOC entry 2559 (class 2606 OID 307891)
-- Dependencies: 1714 1734 2173
-- Name: fk61e8f0315e5242cb; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discounts
    ADD CONSTRAINT fk61e8f0315e5242cb FOREIGN KEY (customer_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2560 (class 2606 OID 307896)
-- Dependencies: 1742 1734 2259
-- Name: fk61e8f031c8fb185a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discounts
    ADD CONSTRAINT fk61e8f031c8fb185a FOREIGN KEY (customer_discount_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2791 (class 2606 OID 307901)
-- Dependencies: 1785 1784 2378
-- Name: fk65f152a13e94bedc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoice_item_link
    ADD CONSTRAINT fk65f152a13e94bedc FOREIGN KEY (invoice_item_id) REFERENCES sales_invoice_items(invoice_item_id);


--
-- TOC entry 2717 (class 2606 OID 307906)
-- Dependencies: 1783 1772 2374
-- Name: fk725e8d71ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk725e8d71ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2718 (class 2606 OID 307911)
-- Dependencies: 2374 1783 1772
-- Name: fk725e8d73aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk725e8d73aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2719 (class 2606 OID 307916)
-- Dependencies: 2387 1772 1789
-- Name: fk725e8d7a330dfcf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk725e8d7a330dfcf FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2720 (class 2606 OID 307921)
-- Dependencies: 2173 1772 1714
-- Name: fk725e8d7aac55a1d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk725e8d7aac55a1d FOREIGN KEY (supplier_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2593 (class 2606 OID 307926)
-- Dependencies: 2259 1742 1742
-- Name: fk74e5117f2ff7d10e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk74e5117f2ff7d10e FOREIGN KEY (linked_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2594 (class 2606 OID 307931)
-- Dependencies: 1741 2255 1742
-- Name: fk74e5117fa44e2131; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk74e5117fa44e2131 FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2595 (class 2606 OID 307936)
-- Dependencies: 1742 1742 2259
-- Name: fk74e5117fafa1da5d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk74e5117fafa1da5d FOREIGN KEY (parent_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2767 (class 2606 OID 307941)
-- Dependencies: 1783 1779 2374
-- Name: fk7503fcd11ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk7503fcd11ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2768 (class 2606 OID 307946)
-- Dependencies: 1779 1789 2387
-- Name: fk7503fcd1a330dfcf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk7503fcd1a330dfcf FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2769 (class 2606 OID 307951)
-- Dependencies: 1779 1789 2387
-- Name: fk7503fcd1f10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk7503fcd1f10b9721 FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2665 (class 2606 OID 307956)
-- Dependencies: 1756 1783 2374
-- Name: fk7e6ecbc71ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmation_items
    ADD CONSTRAINT fk7e6ecbc71ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2666 (class 2606 OID 307961)
-- Dependencies: 1756 2374 1783
-- Name: fk7e6ecbc73aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmation_items
    ADD CONSTRAINT fk7e6ecbc73aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2667 (class 2606 OID 307966)
-- Dependencies: 1756 1789 2387
-- Name: fk7e6ecbc7a330dfcf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmation_items
    ADD CONSTRAINT fk7e6ecbc7a330dfcf FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2677 (class 2606 OID 307971)
-- Dependencies: 1760 1783 2374
-- Name: fk8258b9a0180e7eb9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk8258b9a0180e7eb9 FOREIGN KEY (organization_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2678 (class 2606 OID 307976)
-- Dependencies: 1760 1760 2306
-- Name: fk8258b9a08c46f1ed; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk8258b9a08c46f1ed FOREIGN KEY (registration_organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2763 (class 2606 OID 307981)
-- Dependencies: 1778 1789 2387
-- Name: fk82a39ae56103607c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY real_products
    ADD CONSTRAINT fk82a39ae56103607c FOREIGN KEY (simple_product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2764 (class 2606 OID 307986)
-- Dependencies: 1778 1800 2426
-- Name: fk82a39ae59f88efd5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY real_products
    ADD CONSTRAINT fk82a39ae59f88efd5 FOREIGN KEY (product_id) REFERENCES virtual_products(product_id);


--
-- TOC entry 2728 (class 2606 OID 307991)
-- Dependencies: 1774 1756 2298
-- Name: fk82d039701140d7eb; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT fk82d039701140d7eb FOREIGN KEY (order_confirmation_item_id) REFERENCES order_confirmation_items(confirmation_item_id);


--
-- TOC entry 2729 (class 2606 OID 307996)
-- Dependencies: 1774 1783 2374
-- Name: fk82d039701ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT fk82d039701ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2730 (class 2606 OID 308001)
-- Dependencies: 1774 1776 2355
-- Name: fk82d039705c6db99f; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT fk82d039705c6db99f FOREIGN KEY (purchase_order_item_id) REFERENCES purchase_order_items(order_item_id);


--
-- TOC entry 2731 (class 2606 OID 308006)
-- Dependencies: 1774 1775 2353
-- Name: fk82d039706f432245; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT fk82d039706f432245 FOREIGN KEY (invoice_id) REFERENCES purchase_invoices(invoice_id);


--
-- TOC entry 2732 (class 2606 OID 308011)
-- Dependencies: 1774 1742 2259
-- Name: fk82d03970802ad517; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT fk82d03970802ad517 FOREIGN KEY (invoice_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2733 (class 2606 OID 308016)
-- Dependencies: 1774 1773 2345
-- Name: fk82d03970f10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT fk82d03970f10b9721 FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2634 (class 2606 OID 308021)
-- Dependencies: 1748 1742 2259
-- Name: fk8881f7611698d59; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY entity_sequences
    ADD CONSTRAINT fk8881f7611698d59 FOREIGN KEY (entity_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2635 (class 2606 OID 308026)
-- Dependencies: 1748 1741 2255
-- Name: fk8881f76a44e2131; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY entity_sequences
    ADD CONSTRAINT fk8881f76a44e2131 FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2475 (class 2606 OID 308031)
-- Dependencies: 2169 1713 1712
-- Name: fk8b249c1c40f84bd3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_document_status_log
    ADD CONSTRAINT fk8b249c1c40f84bd3 FOREIGN KEY (document_id) REFERENCES business_documents(document_id);


--
-- TOC entry 2476 (class 2606 OID 308036)
-- Dependencies: 1783 1712 2374
-- Name: fk8b249c1c5df497e9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_document_status_log
    ADD CONSTRAINT fk8b249c1c5df497e9 FOREIGN KEY (document_status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2477 (class 2606 OID 308041)
-- Dependencies: 1712 1763 2314
-- Name: fk8b249c1cbf66cb72; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_document_status_log
    ADD CONSTRAINT fk8b249c1cbf66cb72 FOREIGN KEY (officer_id) REFERENCES persons(person_id);


--
-- TOC entry 2501 (class 2606 OID 308046)
-- Dependencies: 1717 1783 2374
-- Name: fk8c60fd423aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cash_reconcile
    ADD CONSTRAINT fk8c60fd423aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2502 (class 2606 OID 308051)
-- Dependencies: 1717 1763 2314
-- Name: fk8c60fd42826572c5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cash_reconcile
    ADD CONSTRAINT fk8c60fd42826572c5 FOREIGN KEY (cashier_id) REFERENCES persons(person_id);


--
-- TOC entry 2503 (class 2606 OID 308056)
-- Dependencies: 1717 1713 2169
-- Name: fk8c60fd42e14660c0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cash_reconcile
    ADD CONSTRAINT fk8c60fd42e14660c0 FOREIGN KEY (cash_reconcile_id) REFERENCES business_documents(document_id);


--
-- TOC entry 2565 (class 2606 OID 308061)
-- Dependencies: 1736 1704 2136
-- Name: fk8f9cba6e27a66c93; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_payments
    ADD CONSTRAINT fk8f9cba6e27a66c93 FOREIGN KEY (branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2566 (class 2606 OID 308066)
-- Dependencies: 1736 1783 2374
-- Name: fk8f9cba6e3aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_payments
    ADD CONSTRAINT fk8f9cba6e3aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2567 (class 2606 OID 308071)
-- Dependencies: 1736 1783 2374
-- Name: fk8f9cba6e3dc9408c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_payments
    ADD CONSTRAINT fk8f9cba6e3dc9408c FOREIGN KEY (payment_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2568 (class 2606 OID 308076)
-- Dependencies: 1736 1763 2314
-- Name: fk8f9cba6e4da6f8bc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_payments
    ADD CONSTRAINT fk8f9cba6e4da6f8bc FOREIGN KEY (creator_id) REFERENCES persons(person_id);


--
-- TOC entry 2569 (class 2606 OID 308081)
-- Dependencies: 1736 1714 2173
-- Name: fk8f9cba6e5e5242cb; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_payments
    ADD CONSTRAINT fk8f9cba6e5e5242cb FOREIGN KEY (customer_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2570 (class 2606 OID 308086)
-- Dependencies: 1736 1763 2314
-- Name: fk8f9cba6e826572c5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_payments
    ADD CONSTRAINT fk8f9cba6e826572c5 FOREIGN KEY (cashier_id) REFERENCES persons(person_id);


--
-- TOC entry 2571 (class 2606 OID 308091)
-- Dependencies: 1783 1736 2374
-- Name: fk8f9cba6e9c49320d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_payments
    ADD CONSTRAINT fk8f9cba6e9c49320d FOREIGN KEY (status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2572 (class 2606 OID 308096)
-- Dependencies: 1736 2210 1727
-- Name: fk8f9cba6eb1651ab7; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_payments
    ADD CONSTRAINT fk8f9cba6eb1651ab7 FOREIGN KEY (customer_contact_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2573 (class 2606 OID 308101)
-- Dependencies: 1763 2314 1736
-- Name: fk8f9cba6ee39a2279; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_payments
    ADD CONSTRAINT fk8f9cba6ee39a2279 FOREIGN KEY (completor_id) REFERENCES persons(person_id);


--
-- TOC entry 2575 (class 2606 OID 308106)
-- Dependencies: 2259 1742 1738
-- Name: fk9157692e2ff7d10e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_links
    ADD CONSTRAINT fk9157692e2ff7d10e FOREIGN KEY (linked_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2914 (class 2606 OID 308121)
-- Dependencies: 1802 2430 1801
-- Name: fk951433609f1800e1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouse_products
    ADD CONSTRAINT fk951433609f1800e1 FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2915 (class 2606 OID 308126)
-- Dependencies: 1789 2387 1801
-- Name: fk95143360a330dfcf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouse_products
    ADD CONSTRAINT fk95143360a330dfcf FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2774 (class 2606 OID 308131)
-- Dependencies: 2363 1780 1779
-- Name: fk98230d0e73d2d06a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_serial_numbers
    ADD CONSTRAINT fk98230d0e73d2d06a FOREIGN KEY (certificate_item_id) REFERENCES receipt_certificate_items(certificate_item_id);


--
-- TOC entry 2437 (class 2606 OID 308136)
-- Dependencies: 2189 1719 1704
-- Name: fk_addresses_city_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT fk_addresses_city_id FOREIGN KEY (city_id) REFERENCES cities(city_id);


--
-- TOC entry 2438 (class 2606 OID 308141)
-- Dependencies: 1704 1728 2214
-- Name: fk_addresses_country_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT fk_addresses_country_id FOREIGN KEY (country_id) REFERENCES countries(country_id);


--
-- TOC entry 2439 (class 2606 OID 308146)
-- Dependencies: 1704 1742 2259
-- Name: fk_addresses_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT fk_addresses_do_id FOREIGN KEY (address_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2441 (class 2606 OID 308151)
-- Dependencies: 2259 1705 1742
-- Name: fk_assembling_categories_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_categories
    ADD CONSTRAINT fk_assembling_categories_do FOREIGN KEY (assembling_category_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2442 (class 2606 OID 308156)
-- Dependencies: 2139 1705 1705
-- Name: fk_assembling_categories_parent_category; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_categories
    ADD CONSTRAINT fk_assembling_categories_parent_category FOREIGN KEY (parent_category_id) REFERENCES assembling_categories(assembling_category_id);


--
-- TOC entry 2444 (class 2606 OID 308161)
-- Dependencies: 1742 2259 1706
-- Name: fk_assembling_messages_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_messages
    ADD CONSTRAINT fk_assembling_messages_do FOREIGN KEY (message_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2445 (class 2606 OID 308166)
-- Dependencies: 1706 1760 2306
-- Name: fk_assembling_messages_organization; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_messages
    ADD CONSTRAINT fk_assembling_messages_organization FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2448 (class 2606 OID 308171)
-- Dependencies: 1707 1708 2151
-- Name: fk_assembling_schema_item_values_as_item; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_item_values
    ADD CONSTRAINT fk_assembling_schema_item_values_as_item FOREIGN KEY (item_id) REFERENCES assembling_schema_items(item_id);


--
-- TOC entry 2449 (class 2606 OID 308176)
-- Dependencies: 1707 1800 2426
-- Name: fk_assembling_schema_item_values_vp; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_item_values
    ADD CONSTRAINT fk_assembling_schema_item_values_vp FOREIGN KEY (virtual_product_id) REFERENCES virtual_products(product_id);


--
-- TOC entry 2450 (class 2606 OID 308181)
-- Dependencies: 1708 1783 2374
-- Name: fk_assembling_schema_items_algorithm; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT fk_assembling_schema_items_algorithm FOREIGN KEY (algorithm_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2451 (class 2606 OID 308186)
-- Dependencies: 2374 1783 1708
-- Name: fk_assembling_schema_items_data_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT fk_assembling_schema_items_data_type FOREIGN KEY (data_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2452 (class 2606 OID 308191)
-- Dependencies: 1708 1706 2145
-- Name: fk_assembling_schema_items_message; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT fk_assembling_schema_items_message FOREIGN KEY (message_id) REFERENCES assembling_messages(message_id);


--
-- TOC entry 2453 (class 2606 OID 308196)
-- Dependencies: 1708 1709 2153
-- Name: fk_assembling_schema_items_owner; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT fk_assembling_schema_items_owner FOREIGN KEY (assembling_schema_id) REFERENCES assembling_schemas(product_id);


--
-- TOC entry 2458 (class 2606 OID 308201)
-- Dependencies: 1709 1705 2139
-- Name: fk_assembling_schemas_categories; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT fk_assembling_schemas_categories FOREIGN KEY (category_id) REFERENCES assembling_categories(assembling_category_id);


--
-- TOC entry 2459 (class 2606 OID 308206)
-- Dependencies: 1709 2374 1783
-- Name: fk_assembling_schemas_measure_unit; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT fk_assembling_schemas_measure_unit FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2460 (class 2606 OID 308211)
-- Dependencies: 1800 2426 1709
-- Name: fk_assembling_schemas_virtual_products; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT fk_assembling_schemas_virtual_products FOREIGN KEY (product_id) REFERENCES virtual_products(product_id);


--
-- TOC entry 2470 (class 2606 OID 308216)
-- Dependencies: 1710 2136 1704
-- Name: fk_bank_details_bank_branch; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk_bank_details_bank_branch FOREIGN KEY (bank_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2471 (class 2606 OID 308221)
-- Dependencies: 1742 1710 2259
-- Name: fk_bank_details_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk_bank_details_do_id FOREIGN KEY (bank_detail_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2474 (class 2606 OID 309968)
-- Dependencies: 2183 1717 1711
-- Name: fk_banknote_quantity_cash_reconcile; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY banknote_quantity
    ADD CONSTRAINT fk_banknote_quantity_cash_reconcile FOREIGN KEY (cash_reconcile_id) REFERENCES cash_reconcile(cash_reconcile_id);


--
-- TOC entry 2473 (class 2606 OID 309963)
-- Dependencies: 2221 1711 1730
-- Name: fk_banknote_quantity_currency_nominal; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY banknote_quantity
    ADD CONSTRAINT fk_banknote_quantity_currency_nominal FOREIGN KEY (currency_nominal_id) REFERENCES currency_nominal(currency_nominal_id);


--
-- TOC entry 2472 (class 2606 OID 309958)
-- Dependencies: 1711 1742 2259
-- Name: fk_banknote_quantity_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY banknote_quantity
    ADD CONSTRAINT fk_banknote_quantity_do FOREIGN KEY (banknote_quantity_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2478 (class 2606 OID 308226)
-- Dependencies: 2169 1713 1712
-- Name: fk_business_document_status_log_documents; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_document_status_log
    ADD CONSTRAINT fk_business_document_status_log_documents FOREIGN KEY (document_id) REFERENCES business_documents(document_id);


--
-- TOC entry 2479 (class 2606 OID 308231)
-- Dependencies: 2314 1712 1763
-- Name: fk_business_document_status_log_persons; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_document_status_log
    ADD CONSTRAINT fk_business_document_status_log_persons FOREIGN KEY (officer_id) REFERENCES persons(person_id);


--
-- TOC entry 2480 (class 2606 OID 308236)
-- Dependencies: 1783 2374 1712
-- Name: fk_business_document_status_log_resource_bundle; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_document_status_log
    ADD CONSTRAINT fk_business_document_status_log_resource_bundle FOREIGN KEY (document_status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2481 (class 2606 OID 308241)
-- Dependencies: 2136 1713 1704
-- Name: fk_business_documents_publisher_branch_addresses; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_documents
    ADD CONSTRAINT fk_business_documents_publisher_branch_addresses FOREIGN KEY (publisher_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2482 (class 2606 OID 308246)
-- Dependencies: 1713 2314 1763
-- Name: fk_business_documents_publisher_contact_persons; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_documents
    ADD CONSTRAINT fk_business_documents_publisher_contact_persons FOREIGN KEY (publisher_officer_id) REFERENCES persons(person_id);


--
-- TOC entry 2483 (class 2606 OID 308251)
-- Dependencies: 2306 1713 1760
-- Name: fk_business_documents_publisher_organizations; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_documents
    ADD CONSTRAINT fk_business_documents_publisher_organizations FOREIGN KEY (publisher_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2487 (class 2606 OID 308256)
-- Dependencies: 1714 2374 1783
-- Name: fk_business_partners_currency; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_partners
    ADD CONSTRAINT fk_business_partners_currency FOREIGN KEY (default_currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2488 (class 2606 OID 308261)
-- Dependencies: 2259 1714 1742
-- Name: fk_business_partners_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_partners
    ADD CONSTRAINT fk_business_partners_do FOREIGN KEY (business_partner_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2489 (class 2606 OID 308266)
-- Dependencies: 1714 2173 1714
-- Name: fk_business_partners_parent; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_partners
    ADD CONSTRAINT fk_business_partners_parent FOREIGN KEY (parent_business_partner_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2490 (class 2606 OID 308271)
-- Dependencies: 1715 2136 1704
-- Name: fk_business_unit_addresses_address; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_unit_addresses
    ADD CONSTRAINT fk_business_unit_addresses_address FOREIGN KEY (address_id) REFERENCES addresses(address_id);


--
-- TOC entry 2491 (class 2606 OID 308276)
-- Dependencies: 1715 1716 2180
-- Name: fk_business_unit_addresses_bu; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_unit_addresses
    ADD CONSTRAINT fk_business_unit_addresses_bu FOREIGN KEY (business_unit_id) REFERENCES business_units(business_unit_id);


--
-- TOC entry 2492 (class 2606 OID 308281)
-- Dependencies: 2259 1742 1715
-- Name: fk_business_unit_addresses_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_unit_addresses
    ADD CONSTRAINT fk_business_unit_addresses_do FOREIGN KEY (business_unit_address_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2493 (class 2606 OID 308286)
-- Dependencies: 1715 1724 2203
-- Name: fk_business_unit_addresses_email; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_unit_addresses
    ADD CONSTRAINT fk_business_unit_addresses_email FOREIGN KEY (email_id) REFERENCES communication_contacts(communication_contact_id);


--
-- TOC entry 2494 (class 2606 OID 308291)
-- Dependencies: 1715 2203 1724
-- Name: fk_business_unit_addresses_fax; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_unit_addresses
    ADD CONSTRAINT fk_business_unit_addresses_fax FOREIGN KEY (fax_id) REFERENCES communication_contacts(communication_contact_id);


--
-- TOC entry 2495 (class 2606 OID 308296)
-- Dependencies: 1724 2203 1715
-- Name: fk_business_unit_addresses_phone; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_unit_addresses
    ADD CONSTRAINT fk_business_unit_addresses_phone FOREIGN KEY (phone_id) REFERENCES communication_contacts(communication_contact_id);


--
-- TOC entry 2496 (class 2606 OID 308301)
-- Dependencies: 1783 2374 1715
-- Name: fk_business_unit_addresses_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_unit_addresses
    ADD CONSTRAINT fk_business_unit_addresses_type FOREIGN KEY (address_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2497 (class 2606 OID 308306)
-- Dependencies: 2180 1716 1716
-- Name: fk_business_units_bu; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_units
    ADD CONSTRAINT fk_business_units_bu FOREIGN KEY (parent_business_unit_id) REFERENCES business_units(business_unit_id);


--
-- TOC entry 2498 (class 2606 OID 308311)
-- Dependencies: 1716 1783 2374
-- Name: fk_business_units_bu_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_units
    ADD CONSTRAINT fk_business_units_bu_type FOREIGN KEY (business_unit_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2499 (class 2606 OID 308316)
-- Dependencies: 1742 2259 1716
-- Name: fk_business_units_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_units
    ADD CONSTRAINT fk_business_units_do FOREIGN KEY (business_unit_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2500 (class 2606 OID 326396)
-- Dependencies: 1716 1760 2306
-- Name: fk_business_units_organizations; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_units
    ADD CONSTRAINT fk_business_units_organizations FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2504 (class 2606 OID 308326)
-- Dependencies: 1718 1717 2183
-- Name: fk_cash_reconcile_payment_summary_cash_reconcile; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cash_reconcile_payment_summary
    ADD CONSTRAINT fk_cash_reconcile_payment_summary_cash_reconcile FOREIGN KEY (cash_reconcile_id) REFERENCES cash_reconcile(cash_reconcile_id);


--
-- TOC entry 2505 (class 2606 OID 308331)
-- Dependencies: 1783 1718 2374
-- Name: fk_cash_reconcile_payment_summary_currency; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cash_reconcile_payment_summary
    ADD CONSTRAINT fk_cash_reconcile_payment_summary_currency FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2507 (class 2606 OID 309984)
-- Dependencies: 2259 1742 1718
-- Name: fk_cash_reconcile_payment_summary_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cash_reconcile_payment_summary
    ADD CONSTRAINT fk_cash_reconcile_payment_summary_do FOREIGN KEY (payment_summary_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2506 (class 2606 OID 308336)
-- Dependencies: 1718 2374 1783
-- Name: fk_cash_reconcile_payment_summary_payment_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cash_reconcile_payment_summary
    ADD CONSTRAINT fk_cash_reconcile_payment_summary_payment_type FOREIGN KEY (payment_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2623 (class 2606 OID 308341)
-- Dependencies: 1747 1783 2374
-- Name: fk_certificate_status; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_certificate_status FOREIGN KEY (delivery_cert_status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2511 (class 2606 OID 308346)
-- Dependencies: 2200 1720 1723
-- Name: fk_classified_objects_classifier_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classified_objects
    ADD CONSTRAINT fk_classified_objects_classifier_id FOREIGN KEY (classifier_id) REFERENCES classifiers(classifier_id);


--
-- TOC entry 2515 (class 2606 OID 308351)
-- Dependencies: 1741 1721 2255
-- Name: fk_classifier_applied_for_dot_dot_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifier_applied_for_dot
    ADD CONSTRAINT fk_classifier_applied_for_dot_dot_id FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2519 (class 2606 OID 326420)
-- Dependencies: 2173 1714 1722
-- Name: fk_classifier_groups_business_partners; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifier_groups
    ADD CONSTRAINT fk_classifier_groups_business_partners FOREIGN KEY (business_partner_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2518 (class 2606 OID 308356)
-- Dependencies: 1742 2259 1722
-- Name: fk_classifier_groups_cg_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifier_groups
    ADD CONSTRAINT fk_classifier_groups_cg_id FOREIGN KEY (classifier_group_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2522 (class 2606 OID 326414)
-- Dependencies: 1714 2173 1723
-- Name: fk_classifiers_business_partners; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT fk_classifiers_business_partners FOREIGN KEY (business_partner_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2520 (class 2606 OID 308361)
-- Dependencies: 1723 1722 2197
-- Name: fk_classifiers_classifier_groups; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT fk_classifiers_classifier_groups FOREIGN KEY (classifier_group_id) REFERENCES classifier_groups(classifier_group_id);


--
-- TOC entry 2521 (class 2606 OID 308366)
-- Dependencies: 1742 2259 1723
-- Name: fk_classifiers_data_objects; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT fk_classifiers_data_objects FOREIGN KEY (classifier_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2524 (class 2606 OID 308371)
-- Dependencies: 2374 1724 1783
-- Name: fk_communication_contacts_comm_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT fk_communication_contacts_comm_type FOREIGN KEY (communication_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2525 (class 2606 OID 308376)
-- Dependencies: 2259 1742 1724
-- Name: fk_communication_contacts_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT fk_communication_contacts_do_id FOREIGN KEY (communication_contact_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2529 (class 2606 OID 308381)
-- Dependencies: 1725 2374 1783
-- Name: fk_complex_product_items_algorithm; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT fk_complex_product_items_algorithm FOREIGN KEY (applied_algorithm_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2530 (class 2606 OID 308386)
-- Dependencies: 2208 1725 1726
-- Name: fk_complex_product_items_cp; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT fk_complex_product_items_cp FOREIGN KEY (complex_product_id) REFERENCES complex_products(product_id);


--
-- TOC entry 2531 (class 2606 OID 308391)
-- Dependencies: 1725 2345 1773
-- Name: fk_complex_product_items_products; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT fk_complex_product_items_products FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2532 (class 2606 OID 308396)
-- Dependencies: 2345 1726 1773
-- Name: fk_complex_products_products; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_products
    ADD CONSTRAINT fk_complex_products_products FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2536 (class 2606 OID 308401)
-- Dependencies: 2259 1742 1727
-- Name: fk_contact_persons_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT fk_contact_persons_do_id FOREIGN KEY (contact_person_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2537 (class 2606 OID 308406)
-- Dependencies: 2318 1727 1764
-- Name: fk_contact_persons_position_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT fk_contact_persons_position_type FOREIGN KEY (position_type_id) REFERENCES position_types(position_type_id);


--
-- TOC entry 2538 (class 2606 OID 308411)
-- Dependencies: 1783 1728 2374
-- Name: fk_countries_currency; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY countries
    ADD CONSTRAINT fk_countries_currency FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2539 (class 2606 OID 308416)
-- Dependencies: 1728 2259 1742
-- Name: fk_countries_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY countries
    ADD CONSTRAINT fk_countries_do FOREIGN KEY (country_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2624 (class 2606 OID 308421)
-- Dependencies: 1704 2136 1747
-- Name: fk_creator_branch; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_creator_branch FOREIGN KEY (creator_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2625 (class 2606 OID 308426)
-- Dependencies: 1747 2306 1760
-- Name: fk_creator_organization; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_creator_organization FOREIGN KEY (creator_organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2540 (class 2606 OID 308431)
-- Dependencies: 1729 1783 2374
-- Name: fk_currency_exchange_rates_from_currency; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY currency_exchange_rates
    ADD CONSTRAINT fk_currency_exchange_rates_from_currency FOREIGN KEY (from_currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2541 (class 2606 OID 308436)
-- Dependencies: 1783 1729 2374
-- Name: fk_currency_exchange_rates_to_currency; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY currency_exchange_rates
    ADD CONSTRAINT fk_currency_exchange_rates_to_currency FOREIGN KEY (to_currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2544 (class 2606 OID 308441)
-- Dependencies: 1783 1730 2374
-- Name: fk_currency_nominal_currency; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY currency_nominal
    ADD CONSTRAINT fk_currency_nominal_currency FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2545 (class 2606 OID 309979)
-- Dependencies: 1730 1742 2259
-- Name: fk_currency_nominal_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY currency_nominal
    ADD CONSTRAINT fk_currency_nominal_do FOREIGN KEY (currency_nominal_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2551 (class 2606 OID 308446)
-- Dependencies: 1731 1732 2225
-- Name: fk_customer_discount_items_by_categories; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items_by_categories
    ADD CONSTRAINT fk_customer_discount_items_by_categories FOREIGN KEY (customer_discount_item_id) REFERENCES customer_discount_items(customer_discount_item_id);


--
-- TOC entry 2552 (class 2606 OID 308451)
-- Dependencies: 1734 1732 2235
-- Name: fk_customer_discount_items_by_categories_cd; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items_by_categories
    ADD CONSTRAINT fk_customer_discount_items_by_categories_cd FOREIGN KEY (customer_discount_id) REFERENCES customer_discounts(customer_discount_id);


--
-- TOC entry 2553 (class 2606 OID 308456)
-- Dependencies: 1770 1732 2335
-- Name: fk_customer_discount_items_by_categories_pc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items_by_categories
    ADD CONSTRAINT fk_customer_discount_items_by_categories_pc FOREIGN KEY (category_id) REFERENCES product_categories(product_category_id);


--
-- TOC entry 2554 (class 2606 OID 308461)
-- Dependencies: 2225 1731 1733
-- Name: fk_customer_discount_items_by_products; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items_by_products
    ADD CONSTRAINT fk_customer_discount_items_by_products FOREIGN KEY (customer_discount_item_id) REFERENCES customer_discount_items(customer_discount_item_id);


--
-- TOC entry 2555 (class 2606 OID 308466)
-- Dependencies: 2235 1733 1734
-- Name: fk_customer_discount_items_by_products_cd; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items_by_products
    ADD CONSTRAINT fk_customer_discount_items_by_products_cd FOREIGN KEY (customer_discount_id) REFERENCES customer_discounts(customer_discount_id);


--
-- TOC entry 2556 (class 2606 OID 308471)
-- Dependencies: 1733 2225 1731
-- Name: fk_customer_discount_items_by_products_cdi; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items_by_products
    ADD CONSTRAINT fk_customer_discount_items_by_products_cdi FOREIGN KEY (customer_discount_item_id) REFERENCES customer_discount_items(customer_discount_item_id);


--
-- TOC entry 2557 (class 2606 OID 308476)
-- Dependencies: 1773 1733 2345
-- Name: fk_customer_discount_items_by_products_p; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items_by_products
    ADD CONSTRAINT fk_customer_discount_items_by_products_p FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2558 (class 2606 OID 308481)
-- Dependencies: 1773 1733 2345
-- Name: fk_customer_discount_items_by_products_products; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items_by_products
    ADD CONSTRAINT fk_customer_discount_items_by_products_products FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2547 (class 2606 OID 308486)
-- Dependencies: 2259 1731 1742
-- Name: fk_customer_discount_items_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items
    ADD CONSTRAINT fk_customer_discount_items_do FOREIGN KEY (customer_discount_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2561 (class 2606 OID 308491)
-- Dependencies: 2259 1734 1742
-- Name: fk_customer_discounts_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discounts
    ADD CONSTRAINT fk_customer_discounts_do FOREIGN KEY (customer_discount_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2562 (class 2606 OID 308496)
-- Dependencies: 1760 2306 1734
-- Name: fk_customer_discounts_organization; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discounts
    ADD CONSTRAINT fk_customer_discounts_organization FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2563 (class 2606 OID 308501)
-- Dependencies: 2241 1735 1736
-- Name: fk_customer_payment_match_cp; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_payment_match
    ADD CONSTRAINT fk_customer_payment_match_cp FOREIGN KEY (customer_payment_id) REFERENCES customer_payments(payment_id);


--
-- TOC entry 2564 (class 2606 OID 308506)
-- Dependencies: 2380 1786 1735
-- Name: fk_customer_payment_match_invoice; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_payment_match
    ADD CONSTRAINT fk_customer_payment_match_invoice FOREIGN KEY (invoice_id) REFERENCES sales_invoices(invoice_id);


--
-- TOC entry 2574 (class 2606 OID 308511)
-- Dependencies: 2259 1742 1737
-- Name: fk_data_object_details_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_details
    ADD CONSTRAINT fk_data_object_details_do_id FOREIGN KEY (data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2576 (class 2606 OID 308516)
-- Dependencies: 1742 2259 1738
-- Name: fk_data_object_links_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_links
    ADD CONSTRAINT fk_data_object_links_do_id FOREIGN KEY (data_object_link_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2577 (class 2606 OID 308521)
-- Dependencies: 1742 2259 1738
-- Name: fk_data_object_links_linked_object; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_links
    ADD CONSTRAINT fk_data_object_links_linked_object FOREIGN KEY (linked_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2581 (class 2606 OID 308526)
-- Dependencies: 2259 1742 1739
-- Name: fk_data_object_permissions_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_permissions
    ADD CONSTRAINT fk_data_object_permissions_do FOREIGN KEY (data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2582 (class 2606 OID 308531)
-- Dependencies: 1739 2306 1760
-- Name: fk_data_object_permissions_organizations; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_permissions
    ADD CONSTRAINT fk_data_object_permissions_organizations FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2583 (class 2606 OID 308536)
-- Dependencies: 2374 1739 1783
-- Name: fk_data_object_permissions_permissions; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_permissions
    ADD CONSTRAINT fk_data_object_permissions_permissions FOREIGN KEY (permission_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2584 (class 2606 OID 308541)
-- Dependencies: 2374 1783 1739
-- Name: fk_data_object_permissions_urt; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_permissions
    ADD CONSTRAINT fk_data_object_permissions_urt FOREIGN KEY (user_right_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2585 (class 2606 OID 308546)
-- Dependencies: 1741 2255 1740
-- Name: fk_data_object_type_permissions_dot; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_type_permissions
    ADD CONSTRAINT fk_data_object_type_permissions_dot FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2586 (class 2606 OID 308551)
-- Dependencies: 2306 1760 1740
-- Name: fk_data_object_type_permissions_organizations; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_type_permissions
    ADD CONSTRAINT fk_data_object_type_permissions_organizations FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2587 (class 2606 OID 308556)
-- Dependencies: 1740 2374 1783
-- Name: fk_data_object_type_permissions_permissions; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_type_permissions
    ADD CONSTRAINT fk_data_object_type_permissions_permissions FOREIGN KEY (permission_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2588 (class 2606 OID 308561)
-- Dependencies: 2374 1740 1783
-- Name: fk_data_object_type_permissions_urt; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_type_permissions
    ADD CONSTRAINT fk_data_object_type_permissions_urt FOREIGN KEY (user_right_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2596 (class 2606 OID 308566)
-- Dependencies: 1742 1741 2255
-- Name: fk_data_objects_do_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk_data_objects_do_type FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2597 (class 2606 OID 308571)
-- Dependencies: 1742 1742 2259
-- Name: fk_data_objects_linked_data_object_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk_data_objects_linked_data_object_id FOREIGN KEY (linked_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2598 (class 2606 OID 308576)
-- Dependencies: 2259 1742 1742
-- Name: fk_data_objects_parent_data_object_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk_data_objects_parent_data_object_id FOREIGN KEY (parent_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2600 (class 2606 OID 308581)
-- Dependencies: 2259 1742 1743
-- Name: fk_db_properties_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY db_properties
    ADD CONSTRAINT fk_db_properties_do FOREIGN KEY (related_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2602 (class 2606 OID 308586)
-- Dependencies: 1773 2345 1745
-- Name: fk_delivery_certifiate_items_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fk_delivery_certifiate_items_product FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2601 (class 2606 OID 308591)
-- Dependencies: 1744 2275 1747
-- Name: fk_delivery_certificate; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_assignments
    ADD CONSTRAINT fk_delivery_certificate FOREIGN KEY (delivery_certificate_id) REFERENCES delivery_certificates(delivery_certificate_id);


--
-- TOC entry 2603 (class 2606 OID 308596)
-- Dependencies: 2275 1745 1747
-- Name: fk_delivery_certificate_items_delivery_cert; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fk_delivery_certificate_items_delivery_cert FOREIGN KEY (parent_id) REFERENCES delivery_certificates(delivery_certificate_id);


--
-- TOC entry 2604 (class 2606 OID 308601)
-- Dependencies: 1745 1742 2259
-- Name: fk_delivery_certificate_items_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fk_delivery_certificate_items_do_id FOREIGN KEY (certificate_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2605 (class 2606 OID 308606)
-- Dependencies: 1745 2374 1783
-- Name: fk_delivery_certificate_items_measure_unit; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fk_delivery_certificate_items_measure_unit FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2608 (class 2606 OID 308611)
-- Dependencies: 1746 2265 1745
-- Name: fk_delivery_certificate_serial_numbers_dci; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_serial_numbers
    ADD CONSTRAINT fk_delivery_certificate_serial_numbers_dci FOREIGN KEY (certificate_item_id) REFERENCES delivery_certificate_items(certificate_item_id);


--
-- TOC entry 2626 (class 2606 OID 308616)
-- Dependencies: 1747 1742 2259
-- Name: fk_delivery_certificates_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_delivery_certificates_do_id FOREIGN KEY (delivery_certificate_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2627 (class 2606 OID 308621)
-- Dependencies: 1783 2374 1747
-- Name: fk_delivery_certificates_reason; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_delivery_certificates_reason FOREIGN KEY (delivery_cert_reason_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2628 (class 2606 OID 308626)
-- Dependencies: 1714 2173 1747
-- Name: fk_delivery_certificates_recipient; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_delivery_certificates_recipient FOREIGN KEY (recipient_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2629 (class 2606 OID 308631)
-- Dependencies: 1783 1747 2374
-- Name: fk_delivery_certificates_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_delivery_certificates_type FOREIGN KEY (delivery_cert_method_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2630 (class 2606 OID 308636)
-- Dependencies: 1802 2430 1747
-- Name: fk_delivery_certificates_warehouse; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_delivery_certificates_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2484 (class 2606 OID 308641)
-- Dependencies: 2259 1742 1713
-- Name: fk_document_entities_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_documents
    ADD CONSTRAINT fk_document_entities_do FOREIGN KEY (document_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2485 (class 2606 OID 308646)
-- Dependencies: 1713 1783 2374
-- Name: fk_document_entities_status_resource_bundle; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_documents
    ADD CONSTRAINT fk_document_entities_status_resource_bundle FOREIGN KEY (document_status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2486 (class 2606 OID 308651)
-- Dependencies: 1783 2374 1713
-- Name: fk_document_entities_type_resource_bundle; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_documents
    ADD CONSTRAINT fk_document_entities_type_resource_bundle FOREIGN KEY (document_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2636 (class 2606 OID 308656)
-- Dependencies: 1742 2259 1748
-- Name: fk_entity_sequences_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY entity_sequences
    ADD CONSTRAINT fk_entity_sequences_do FOREIGN KEY (entity_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2637 (class 2606 OID 308661)
-- Dependencies: 2255 1741 1748
-- Name: fk_entity_sequences_dot; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY entity_sequences
    ADD CONSTRAINT fk_entity_sequences_dot FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2638 (class 2606 OID 308666)
-- Dependencies: 1750 1760 2306
-- Name: fk_expressions_organizations; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY expressions
    ADD CONSTRAINT fk_expressions_organizations FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2631 (class 2606 OID 308671)
-- Dependencies: 2136 1747 1704
-- Name: fk_forwarder_address_constraint; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_forwarder_address_constraint FOREIGN KEY (forwarder_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2640 (class 2606 OID 308676)
-- Dependencies: 1745 1751 2265
-- Name: fk_goods_receipt_dc_items_delivery_certificate_items; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_dc_items
    ADD CONSTRAINT fk_goods_receipt_dc_items_delivery_certificate_items FOREIGN KEY (delivery_certificate_item_id) REFERENCES delivery_certificate_items(certificate_item_id);


--
-- TOC entry 2641 (class 2606 OID 308681)
-- Dependencies: 1752 1751 2289
-- Name: fk_goods_receipt_dc_items_gri; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_dc_items
    ADD CONSTRAINT fk_goods_receipt_dc_items_gri FOREIGN KEY (receipt_item_id) REFERENCES goods_receipt_items(receipt_item_id);


--
-- TOC entry 2648 (class 2606 OID 308686)
-- Dependencies: 1742 1752 2259
-- Name: fk_goods_receipt_items_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_items
    ADD CONSTRAINT fk_goods_receipt_items_do FOREIGN KEY (receipt_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2649 (class 2606 OID 308691)
-- Dependencies: 1754 1752 2293
-- Name: fk_goods_receipt_items_goods_receipts; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_items
    ADD CONSTRAINT fk_goods_receipt_items_goods_receipts FOREIGN KEY (goods_receipt_id) REFERENCES goods_receipts(goods_receipt_id);


--
-- TOC entry 2650 (class 2606 OID 308696)
-- Dependencies: 1783 1752 2374
-- Name: fk_goods_receipt_items_measure_unit_rb; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_items
    ADD CONSTRAINT fk_goods_receipt_items_measure_unit_rb FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2651 (class 2606 OID 308701)
-- Dependencies: 1773 1752 2345
-- Name: fk_goods_receipt_items_products; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_items
    ADD CONSTRAINT fk_goods_receipt_items_products FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2652 (class 2606 OID 308706)
-- Dependencies: 1752 1753 2289
-- Name: fk_goods_receipt_pi_items_gri; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_pi_items
    ADD CONSTRAINT fk_goods_receipt_pi_items_gri FOREIGN KEY (receipt_item_id) REFERENCES goods_receipt_items(receipt_item_id);


--
-- TOC entry 2653 (class 2606 OID 308711)
-- Dependencies: 1774 1753 2351
-- Name: fk_goods_receipt_pi_items_poi; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_pi_items
    ADD CONSTRAINT fk_goods_receipt_pi_items_poi FOREIGN KEY (invoice_item_id) REFERENCES purchase_invoice_items(invoice_item_id);


--
-- TOC entry 2660 (class 2606 OID 309999)
-- Dependencies: 1754 2169 1713
-- Name: fk_goods_receipts_business_document; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipts
    ADD CONSTRAINT fk_goods_receipts_business_document FOREIGN KEY (goods_receipt_id) REFERENCES business_documents(document_id);


--
-- TOC entry 2656 (class 2606 OID 308736)
-- Dependencies: 1783 1754 2374
-- Name: fk_goods_receipts_doc_type_resource_bundle; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipts
    ADD CONSTRAINT fk_goods_receipts_doc_type_resource_bundle FOREIGN KEY (related_document_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2657 (class 2606 OID 308746)
-- Dependencies: 1714 1754 2173
-- Name: fk_goods_receipts_supplier_b_partners; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipts
    ADD CONSTRAINT fk_goods_receipts_supplier_b_partners FOREIGN KEY (supplier_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2658 (class 2606 OID 308751)
-- Dependencies: 1704 1754 2136
-- Name: fk_goods_receipts_supplier_branch_addresses; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipts
    ADD CONSTRAINT fk_goods_receipts_supplier_branch_addresses FOREIGN KEY (supplier_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2659 (class 2606 OID 308756)
-- Dependencies: 2314 1754 1763
-- Name: fk_goods_receipts_supplier_contact_persons; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipts
    ADD CONSTRAINT fk_goods_receipts_supplier_contact_persons FOREIGN KEY (supplier_contact_id) REFERENCES persons(person_id);


--
-- TOC entry 2661 (class 2606 OID 308761)
-- Dependencies: 1755 1716 2180
-- Name: fk_job_titles_business_units; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY job_titles
    ADD CONSTRAINT fk_job_titles_business_units FOREIGN KEY (business_unit_id) REFERENCES business_units(business_unit_id);


--
-- TOC entry 2662 (class 2606 OID 308766)
-- Dependencies: 2259 1755 1742
-- Name: fk_job_titles_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY job_titles
    ADD CONSTRAINT fk_job_titles_do FOREIGN KEY (job_title_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2663 (class 2606 OID 308771)
-- Dependencies: 1783 1755 2374
-- Name: fk_job_titles_functional_hierarchy; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY job_titles
    ADD CONSTRAINT fk_job_titles_functional_hierarchy FOREIGN KEY (functional_hierarchy_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2664 (class 2606 OID 308776)
-- Dependencies: 1787 1755 2382
-- Name: fk_job_titles_security_roles; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY job_titles
    ADD CONSTRAINT fk_job_titles_security_roles FOREIGN KEY (security_role_id) REFERENCES security_roles(security_role_id);


--
-- TOC entry 2675 (class 2606 OID 308781)
-- Dependencies: 1783 1759 2374
-- Name: fk_organization_configurations_currency; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organization_configurations
    ADD CONSTRAINT fk_organization_configurations_currency FOREIGN KEY (default_currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2676 (class 2606 OID 308786)
-- Dependencies: 1760 1759 2306
-- Name: fk_organization_configurations_org; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organization_configurations
    ADD CONSTRAINT fk_organization_configurations_org FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2679 (class 2606 OID 308791)
-- Dependencies: 2173 1760 1714
-- Name: fk_organizations_business_partner; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk_organizations_business_partner FOREIGN KEY (organization_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2683 (class 2606 OID 308796)
-- Dependencies: 1761 1742 2259
-- Name: fk_passports_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT fk_passports_do_id FOREIGN KEY (passport_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2684 (class 2606 OID 308801)
-- Dependencies: 2136 1761 1704
-- Name: fk_passports_issuer_branch; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT fk_passports_issuer_branch FOREIGN KEY (issuer_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2685 (class 2606 OID 308806)
-- Dependencies: 1783 1761 2374
-- Name: fk_passports_pass_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT fk_passports_pass_type FOREIGN KEY (passport_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2921 (class 2606 OID 310030)
-- Dependencies: 2203 1817 1724
-- Name: fk_personal_communication_contacts_cc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY personal_communication_contacts
    ADD CONSTRAINT fk_personal_communication_contacts_cc FOREIGN KEY (communication_contact_id) REFERENCES communication_contacts(communication_contact_id);


--
-- TOC entry 2920 (class 2606 OID 310025)
-- Dependencies: 1727 2210 1817
-- Name: fk_personal_communication_contacts_cp; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY personal_communication_contacts
    ADD CONSTRAINT fk_personal_communication_contacts_cp FOREIGN KEY (contact_person_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2919 (class 2606 OID 310020)
-- Dependencies: 2259 1742 1817
-- Name: fk_personal_communication_contacts_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY personal_communication_contacts
    ADD CONSTRAINT fk_personal_communication_contacts_do FOREIGN KEY (personal_communication_contact_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2689 (class 2606 OID 308811)
-- Dependencies: 1719 1763 2189
-- Name: fk_persons_birth_place_city; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT fk_persons_birth_place_city FOREIGN KEY (birth_place_city_id) REFERENCES cities(city_id);


--
-- TOC entry 2690 (class 2606 OID 308816)
-- Dependencies: 1728 1763 2214
-- Name: fk_persons_birth_place_country; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT fk_persons_birth_place_country FOREIGN KEY (birth_place_country_id) REFERENCES countries(country_id);


--
-- TOC entry 2691 (class 2606 OID 308821)
-- Dependencies: 1783 1763 2374
-- Name: fk_persons_gender; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT fk_persons_gender FOREIGN KEY (gender_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2692 (class 2606 OID 308826)
-- Dependencies: 1714 1763 2173
-- Name: fk_persons_partner; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT fk_persons_partner FOREIGN KEY (person_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2694 (class 2606 OID 310035)
-- Dependencies: 1714 2173 1764
-- Name: fk_position_types_business_partner; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY position_types
    ADD CONSTRAINT fk_position_types_business_partner FOREIGN KEY (business_partner_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2693 (class 2606 OID 308831)
-- Dependencies: 2259 1764 1742
-- Name: fk_position_types_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY position_types
    ADD CONSTRAINT fk_position_types_do_id FOREIGN KEY (position_type_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2695 (class 2606 OID 308836)
-- Dependencies: 1765 2323 1766
-- Name: fk_pricelist_items_pricelist; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pricelist_items
    ADD CONSTRAINT fk_pricelist_items_pricelist FOREIGN KEY (pricelist_id) REFERENCES pricelists(pricelist_id);


--
-- TOC entry 2696 (class 2606 OID 308841)
-- Dependencies: 2387 1789 1765
-- Name: fk_pricelist_items_simple_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pricelist_items
    ADD CONSTRAINT fk_pricelist_items_simple_product FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2697 (class 2606 OID 308846)
-- Dependencies: 1783 2374 1766
-- Name: fk_pricelists_curency; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pricelists
    ADD CONSTRAINT fk_pricelists_curency FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2698 (class 2606 OID 308851)
-- Dependencies: 1766 2259 1742
-- Name: fk_pricelists_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pricelists
    ADD CONSTRAINT fk_pricelists_do FOREIGN KEY (pricelist_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2699 (class 2606 OID 308856)
-- Dependencies: 1760 2306 1767
-- Name: fk_privilege_categories; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privilege_categories
    ADD CONSTRAINT fk_privilege_categories FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2700 (class 2606 OID 308861)
-- Dependencies: 2259 1742 1767
-- Name: fk_privilege_categories_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privilege_categories
    ADD CONSTRAINT fk_privilege_categories_do FOREIGN KEY (privilege_category_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2701 (class 2606 OID 308866)
-- Dependencies: 2374 1767 1783
-- Name: fk_privilege_categories_privilege_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privilege_categories
    ADD CONSTRAINT fk_privilege_categories_privilege_type FOREIGN KEY (privilege_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2702 (class 2606 OID 308871)
-- Dependencies: 1783 2374 1768
-- Name: fk_privilege_roles_access_level; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privilege_roles
    ADD CONSTRAINT fk_privilege_roles_access_level FOREIGN KEY (access_level_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2703 (class 2606 OID 308876)
-- Dependencies: 2374 1783 1768
-- Name: fk_privilege_roles_access_rights; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privilege_roles
    ADD CONSTRAINT fk_privilege_roles_access_rights FOREIGN KEY (access_right_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2704 (class 2606 OID 308881)
-- Dependencies: 1768 1742 2259
-- Name: fk_privilege_roles_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privilege_roles
    ADD CONSTRAINT fk_privilege_roles_do FOREIGN KEY (privilege_role_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2705 (class 2606 OID 308886)
-- Dependencies: 1768 1769 2332
-- Name: fk_privilege_roles_privileges; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privilege_roles
    ADD CONSTRAINT fk_privilege_roles_privileges FOREIGN KEY (privilege_id) REFERENCES privileges(privilege_id);


--
-- TOC entry 2706 (class 2606 OID 308891)
-- Dependencies: 1742 1769 2259
-- Name: fk_privileges_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privileges
    ADD CONSTRAINT fk_privileges_do FOREIGN KEY (privilege_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2707 (class 2606 OID 308896)
-- Dependencies: 2259 1742 1769
-- Name: fk_privileges_entity; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privileges
    ADD CONSTRAINT fk_privileges_entity FOREIGN KEY (data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2708 (class 2606 OID 308901)
-- Dependencies: 1741 1769 2255
-- Name: fk_privileges_entity_types; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privileges
    ADD CONSTRAINT fk_privileges_entity_types FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2709 (class 2606 OID 308906)
-- Dependencies: 1769 1783 2374
-- Name: fk_privileges_permission_categories; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privileges
    ADD CONSTRAINT fk_privileges_permission_categories FOREIGN KEY (permission_category_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2710 (class 2606 OID 308911)
-- Dependencies: 2325 1767 1769
-- Name: fk_privileges_privilege_categories; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privileges
    ADD CONSTRAINT fk_privileges_privilege_categories FOREIGN KEY (privilege_category_id) REFERENCES privilege_categories(privilege_category_id);


--
-- TOC entry 2711 (class 2606 OID 308916)
-- Dependencies: 2382 1769 1787
-- Name: fk_privileges_security_roles; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privileges
    ADD CONSTRAINT fk_privileges_security_roles FOREIGN KEY (security_role_id) REFERENCES security_roles(security_role_id);


--
-- TOC entry 2712 (class 2606 OID 308921)
-- Dependencies: 2374 1783 1769
-- Name: fk_privileges_special_permissions; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privileges
    ADD CONSTRAINT fk_privileges_special_permissions FOREIGN KEY (special_permission_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2715 (class 2606 OID 308926)
-- Dependencies: 2259 1770 1742
-- Name: fk_product_categories_product_category_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT fk_product_categories_product_category_id FOREIGN KEY (product_category_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2836 (class 2606 OID 308931)
-- Dependencies: 1770 1789 2335
-- Name: fk_product_category_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_product_category_id FOREIGN KEY (category_id) REFERENCES product_categories(product_category_id);


--
-- TOC entry 2716 (class 2606 OID 308936)
-- Dependencies: 1760 2306 1771
-- Name: fk_product_percent_values_organization; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_percent_values
    ADD CONSTRAINT fk_product_percent_values_organization FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2721 (class 2606 OID 308941)
-- Dependencies: 2374 1772 1783
-- Name: fk_product_suppliers_currency; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk_product_suppliers_currency FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2722 (class 2606 OID 308946)
-- Dependencies: 1772 1789 2387
-- Name: fk_product_suppliers_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk_product_suppliers_product FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2723 (class 2606 OID 308951)
-- Dependencies: 1783 2374 1772
-- Name: fk_product_suppliers_resources; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk_product_suppliers_resources FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2724 (class 2606 OID 308956)
-- Dependencies: 1714 2173 1772
-- Name: fk_product_suppliers_supplier; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk_product_suppliers_supplier FOREIGN KEY (supplier_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2725 (class 2606 OID 308961)
-- Dependencies: 2259 1742 1773
-- Name: fk_products1_product_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_products1_product_id FOREIGN KEY (product_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2837 (class 2606 OID 308966)
-- Dependencies: 1783 1789 2374
-- Name: fk_products_color_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_products_color_id FOREIGN KEY (product_color_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2726 (class 2606 OID 308971)
-- Dependencies: 1773 2374 1783
-- Name: fk_products_currency_resource; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_products_currency_resource FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2838 (class 2606 OID 308976)
-- Dependencies: 2374 1789 1783
-- Name: fk_products_dimension_unit_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_products_dimension_unit_id FOREIGN KEY (dimension_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2727 (class 2606 OID 308981)
-- Dependencies: 1783 1773 2374
-- Name: fk_products_measure_unit_resource; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_products_measure_unit_resource FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2839 (class 2606 OID 308986)
-- Dependencies: 1789 1783 2374
-- Name: fk_products_weight_unit_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_products_weight_unit_id FOREIGN KEY (weight_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2734 (class 2606 OID 308991)
-- Dependencies: 1742 1774 2259
-- Name: fk_purchase_invoice_items_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT fk_purchase_invoice_items_do FOREIGN KEY (invoice_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2735 (class 2606 OID 308996)
-- Dependencies: 1775 1774 2353
-- Name: fk_purchase_invoice_items_goods_receipts; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT fk_purchase_invoice_items_goods_receipts FOREIGN KEY (invoice_id) REFERENCES purchase_invoices(invoice_id);


--
-- TOC entry 2736 (class 2606 OID 309001)
-- Dependencies: 2374 1774 1783
-- Name: fk_purchase_invoice_items_measure_unit_rb; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT fk_purchase_invoice_items_measure_unit_rb FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2737 (class 2606 OID 309006)
-- Dependencies: 2298 1774 1756
-- Name: fk_purchase_invoice_items_order_confirmations; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT fk_purchase_invoice_items_order_confirmations FOREIGN KEY (order_confirmation_item_id) REFERENCES order_confirmation_items(confirmation_item_id);


--
-- TOC entry 2738 (class 2606 OID 309011)
-- Dependencies: 1774 1776 2355
-- Name: fk_purchase_invoice_items_poi; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT fk_purchase_invoice_items_poi FOREIGN KEY (purchase_order_item_id) REFERENCES purchase_order_items(order_item_id);


--
-- TOC entry 2739 (class 2606 OID 309016)
-- Dependencies: 1774 2345 1773
-- Name: fk_purchase_invoice_items_products; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT fk_purchase_invoice_items_products FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2740 (class 2606 OID 309021)
-- Dependencies: 1710 1775 2159
-- Name: fk_purchase_invoices_bank_details; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoices
    ADD CONSTRAINT fk_purchase_invoices_bank_details FOREIGN KEY (bank_detail_id) REFERENCES bank_details(bank_detail_id);


--
-- TOC entry 2741 (class 2606 OID 309026)
-- Dependencies: 1775 1713 2169
-- Name: fk_purchase_invoices_business_documents; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoices
    ADD CONSTRAINT fk_purchase_invoices_business_documents FOREIGN KEY (invoice_id) REFERENCES business_documents(document_id);


--
-- TOC entry 2742 (class 2606 OID 309031)
-- Dependencies: 1783 1775 2374
-- Name: fk_purchase_invoices_currency; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoices
    ADD CONSTRAINT fk_purchase_invoices_currency FOREIGN KEY (document_currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2743 (class 2606 OID 309036)
-- Dependencies: 1775 1783 2374
-- Name: fk_purchase_invoices_delivery_resource_bundle; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoices
    ADD CONSTRAINT fk_purchase_invoices_delivery_resource_bundle FOREIGN KEY (delivery_terms_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2744 (class 2606 OID 309041)
-- Dependencies: 1783 1775 2374
-- Name: fk_purchase_invoices_payment_resource_bundle; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoices
    ADD CONSTRAINT fk_purchase_invoices_payment_resource_bundle FOREIGN KEY (payment_terms_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2745 (class 2606 OID 309046)
-- Dependencies: 2173 1714 1775
-- Name: fk_purchase_invoices_supplier_b_partners; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoices
    ADD CONSTRAINT fk_purchase_invoices_supplier_b_partners FOREIGN KEY (supplier_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2746 (class 2606 OID 309051)
-- Dependencies: 1704 2136 1775
-- Name: fk_purchase_invoices_supplier_branch_addresses; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoices
    ADD CONSTRAINT fk_purchase_invoices_supplier_branch_addresses FOREIGN KEY (supplier_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2747 (class 2606 OID 309056)
-- Dependencies: 2210 1727 1775
-- Name: fk_purchase_invoices_supplier_contact_persons; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoices
    ADD CONSTRAINT fk_purchase_invoices_supplier_contact_persons FOREIGN KEY (supplier_contact_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2748 (class 2606 OID 309061)
-- Dependencies: 1742 1776 2259
-- Name: fk_purchase_order_items_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fk_purchase_order_items_do_id FOREIGN KEY (order_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2749 (class 2606 OID 309066)
-- Dependencies: 1776 2374 1783
-- Name: fk_purchase_order_items_measure_unit; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fk_purchase_order_items_measure_unit FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2750 (class 2606 OID 309071)
-- Dependencies: 1789 1776 2387
-- Name: fk_purchase_order_items_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fk_purchase_order_items_product FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2765 (class 2606 OID 309076)
-- Dependencies: 2387 1778 1789
-- Name: fk_real_products_simple_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY real_products
    ADD CONSTRAINT fk_real_products_simple_product FOREIGN KEY (simple_product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2766 (class 2606 OID 309081)
-- Dependencies: 2426 1800 1778
-- Name: fk_real_products_virtual_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY real_products
    ADD CONSTRAINT fk_real_products_virtual_product FOREIGN KEY (product_id) REFERENCES virtual_products(product_id);


--
-- TOC entry 2770 (class 2606 OID 309086)
-- Dependencies: 2259 1742 1779
-- Name: fk_receipt_certificate_items_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk_receipt_certificate_items_do_id FOREIGN KEY (certificate_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2771 (class 2606 OID 309091)
-- Dependencies: 1779 2374 1783
-- Name: fk_receipt_certificate_items_measure_unit; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk_receipt_certificate_items_measure_unit FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2772 (class 2606 OID 309096)
-- Dependencies: 1789 2387 1779
-- Name: fk_receipt_certificate_items_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk_receipt_certificate_items_product FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2773 (class 2606 OID 309101)
-- Dependencies: 1779 2367 1781
-- Name: fk_receipt_certificate_items_receipt_cert; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk_receipt_certificate_items_receipt_cert FOREIGN KEY (parent_id) REFERENCES receipt_certificates(receipt_certificate_id);


--
-- TOC entry 2775 (class 2606 OID 309106)
-- Dependencies: 1779 2363 1780
-- Name: fk_receipt_certificate_serial_numbers_dci; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_serial_numbers
    ADD CONSTRAINT fk_receipt_certificate_serial_numbers_dci FOREIGN KEY (certificate_item_id) REFERENCES receipt_certificate_items(certificate_item_id);


--
-- TOC entry 2776 (class 2606 OID 309111)
-- Dependencies: 1738 1781 2247
-- Name: fk_receipt_certificates_deliverer; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_deliverer FOREIGN KEY (deliverer_id) REFERENCES data_object_links(data_object_link_id);


--
-- TOC entry 2777 (class 2606 OID 309116)
-- Dependencies: 2259 1742 1781
-- Name: fk_receipt_certificates_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_do_id FOREIGN KEY (receipt_certificate_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2778 (class 2606 OID 309121)
-- Dependencies: 1781 1783 2374
-- Name: fk_receipt_certificates_reason; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_reason FOREIGN KEY (receipt_cert_reason_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2779 (class 2606 OID 309126)
-- Dependencies: 1783 2374 1781
-- Name: fk_receipt_certificates_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_type FOREIGN KEY (receipt_cert_method_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2780 (class 2606 OID 309131)
-- Dependencies: 2430 1802 1781
-- Name: fk_receipt_certificates_warehouse; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2632 (class 2606 OID 309136)
-- Dependencies: 2136 1704 1747
-- Name: fk_recipient_address_constraint; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_recipient_address_constraint FOREIGN KEY (recipient_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2633 (class 2606 OID 309141)
-- Dependencies: 1747 1727 2210
-- Name: fk_recipient_contact_person; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_recipient_contact_person FOREIGN KEY (recipient_contact_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2790 (class 2606 OID 309146)
-- Dependencies: 1783 2281 1749
-- Name: fk_resource_bundle_enum_class_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY resource_bundle
    ADD CONSTRAINT fk_resource_bundle_enum_class_id FOREIGN KEY (enum_class_id) REFERENCES enum_classes(enum_class_id);


--
-- TOC entry 2833 (class 2606 OID 309151)
-- Dependencies: 1787 2180 1716
-- Name: fk_security_roles_business_units; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY security_roles
    ADD CONSTRAINT fk_security_roles_business_units FOREIGN KEY (business_unit_id) REFERENCES business_units(business_unit_id);


--
-- TOC entry 2834 (class 2606 OID 309156)
-- Dependencies: 2259 1787 1742
-- Name: fk_security_roles_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY security_roles
    ADD CONSTRAINT fk_security_roles_do FOREIGN KEY (security_role_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2835 (class 2606 OID 309161)
-- Dependencies: 2306 1760 1787
-- Name: fk_security_roles_organizations; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY security_roles
    ADD CONSTRAINT fk_security_roles_organizations FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2840 (class 2606 OID 309166)
-- Dependencies: 2339 1771 1789
-- Name: fk_simple_products_customs_duty; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_simple_products_customs_duty FOREIGN KEY (customs_duty_percent_id) REFERENCES product_percent_values(percent_value_id);


--
-- TOC entry 2841 (class 2606 OID 309171)
-- Dependencies: 2339 1789 1771
-- Name: fk_simple_products_discount; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_simple_products_discount FOREIGN KEY (discount_percent_id) REFERENCES product_percent_values(percent_value_id);


--
-- TOC entry 2842 (class 2606 OID 309176)
-- Dependencies: 1771 2339 1789
-- Name: fk_simple_products_excise_duty; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_simple_products_excise_duty FOREIGN KEY (excise_duty_percent_id) REFERENCES product_percent_values(percent_value_id);


--
-- TOC entry 2843 (class 2606 OID 309181)
-- Dependencies: 1773 1789 2345
-- Name: fk_simple_products_products; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_simple_products_products FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2844 (class 2606 OID 309186)
-- Dependencies: 1789 2339 1771
-- Name: fk_simple_products_profit; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_simple_products_profit FOREIGN KEY (profit_percent_id) REFERENCES product_percent_values(percent_value_id);


--
-- TOC entry 2845 (class 2606 OID 309191)
-- Dependencies: 2339 1789 1771
-- Name: fk_simple_products_transport; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_simple_products_transport FOREIGN KEY (transport_percent_id) REFERENCES product_percent_values(percent_value_id);


--
-- TOC entry 2864 (class 2606 OID 309196)
-- Dependencies: 1790 1742 2259
-- Name: fk_team_members_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY team_members
    ADD CONSTRAINT fk_team_members_do FOREIGN KEY (team_member_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2865 (class 2606 OID 309201)
-- Dependencies: 1790 2374 1783
-- Name: fk_team_members_status; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY team_members
    ADD CONSTRAINT fk_team_members_status FOREIGN KEY (status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2866 (class 2606 OID 309206)
-- Dependencies: 1791 2393 1790
-- Name: fk_team_members_teams; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY team_members
    ADD CONSTRAINT fk_team_members_teams FOREIGN KEY (team_id) REFERENCES teams(team_id);


--
-- TOC entry 2867 (class 2606 OID 309211)
-- Dependencies: 1794 2405 1790
-- Name: fk_team_members_users; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY team_members
    ADD CONSTRAINT fk_team_members_users FOREIGN KEY (user_organization_id) REFERENCES user_organizations(user_organization_id);


--
-- TOC entry 2868 (class 2606 OID 309216)
-- Dependencies: 1716 1791 2180
-- Name: fk_teams_business_units; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY teams
    ADD CONSTRAINT fk_teams_business_units FOREIGN KEY (business_unit_id) REFERENCES business_units(business_unit_id);


--
-- TOC entry 2869 (class 2606 OID 309221)
-- Dependencies: 1791 1742 2259
-- Name: fk_teams_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY teams
    ADD CONSTRAINT fk_teams_do FOREIGN KEY (team_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2870 (class 2606 OID 309226)
-- Dependencies: 1760 1791 2306
-- Name: fk_teams_organizations; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY teams
    ADD CONSTRAINT fk_teams_organizations FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2871 (class 2606 OID 309231)
-- Dependencies: 2374 1791 1783
-- Name: fk_teams_status; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY teams
    ADD CONSTRAINT fk_teams_status FOREIGN KEY (status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2872 (class 2606 OID 309236)
-- Dependencies: 2259 1742 1792
-- Name: fk_user_group_members_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_group_members
    ADD CONSTRAINT fk_user_group_members_do FOREIGN KEY (user_group_member_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2873 (class 2606 OID 309241)
-- Dependencies: 2405 1792 1794
-- Name: fk_user_group_members_u; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_group_members
    ADD CONSTRAINT fk_user_group_members_u FOREIGN KEY (user_organization_id) REFERENCES user_organizations(user_organization_id);


--
-- TOC entry 2874 (class 2606 OID 309246)
-- Dependencies: 1792 2403 1793
-- Name: fk_user_group_members_ug; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_group_members
    ADD CONSTRAINT fk_user_group_members_ug FOREIGN KEY (user_group_id) REFERENCES user_groups(user_group_id);


--
-- TOC entry 2875 (class 2606 OID 309251)
-- Dependencies: 1793 2259 1742
-- Name: fk_user_groups_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_groups
    ADD CONSTRAINT fk_user_groups_do FOREIGN KEY (user_group_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2877 (class 2606 OID 309256)
-- Dependencies: 2136 1794 1704
-- Name: fk_user_organizations_branch; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_organizations
    ADD CONSTRAINT fk_user_organizations_branch FOREIGN KEY (branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2883 (class 2606 OID 326401)
-- Dependencies: 2180 1716 1794
-- Name: fk_user_organizations_business_units; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_organizations
    ADD CONSTRAINT fk_user_organizations_business_units FOREIGN KEY (business_unit_id) REFERENCES business_units(business_unit_id);


--
-- TOC entry 2878 (class 2606 OID 309266)
-- Dependencies: 1794 2259 1742
-- Name: fk_user_organizations_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_organizations
    ADD CONSTRAINT fk_user_organizations_do FOREIGN KEY (user_organization_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2879 (class 2606 OID 309271)
-- Dependencies: 1794 2295 1755
-- Name: fk_user_organizations_job_title; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_organizations
    ADD CONSTRAINT fk_user_organizations_job_title FOREIGN KEY (job_title_id) REFERENCES job_titles(job_title_id);


--
-- TOC entry 2880 (class 2606 OID 309276)
-- Dependencies: 2420 1794 1798
-- Name: fk_user_organizations_manager; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_organizations
    ADD CONSTRAINT fk_user_organizations_manager FOREIGN KEY (manager_id) REFERENCES users(user_id);


--
-- TOC entry 2881 (class 2606 OID 309281)
-- Dependencies: 1798 2420 1794
-- Name: fk_user_organizations_user; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_organizations
    ADD CONSTRAINT fk_user_organizations_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE;


--
-- TOC entry 2884 (class 2606 OID 309286)
-- Dependencies: 1742 2259 1795
-- Name: fk_user_rights_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights
    ADD CONSTRAINT fk_user_rights_do FOREIGN KEY (data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2885 (class 2606 OID 309291)
-- Dependencies: 1741 1795 2255
-- Name: fk_user_rights_dot; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights
    ADD CONSTRAINT fk_user_rights_dot FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2886 (class 2606 OID 309296)
-- Dependencies: 1760 2306 1795
-- Name: fk_user_rights_organizations; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights
    ADD CONSTRAINT fk_user_rights_organizations FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2887 (class 2606 OID 309301)
-- Dependencies: 2374 1783 1795
-- Name: fk_user_rights_permission_categories; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights
    ADD CONSTRAINT fk_user_rights_permission_categories FOREIGN KEY (permission_category_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2888 (class 2606 OID 309306)
-- Dependencies: 2374 1783 1795
-- Name: fk_user_rights_permissions; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights
    ADD CONSTRAINT fk_user_rights_permissions FOREIGN KEY (special_permission_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2889 (class 2606 OID 309311)
-- Dependencies: 1795 2403 1793
-- Name: fk_user_rights_user_groups; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights
    ADD CONSTRAINT fk_user_rights_user_groups FOREIGN KEY (user_group_id) REFERENCES user_groups(user_group_id);


--
-- TOC entry 2890 (class 2606 OID 309316)
-- Dependencies: 1798 2420 1795
-- Name: fk_user_rights_users; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights
    ADD CONSTRAINT fk_user_rights_users FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- TOC entry 2906 (class 2606 OID 309321)
-- Dependencies: 1797 1742 2259
-- Name: fk_user_security_roles; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_security_roles
    ADD CONSTRAINT fk_user_security_roles FOREIGN KEY (user_security_role_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2907 (class 2606 OID 309326)
-- Dependencies: 1797 1787 2382
-- Name: fk_user_security_roles_sr; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_security_roles
    ADD CONSTRAINT fk_user_security_roles_sr FOREIGN KEY (security_role_id) REFERENCES security_roles(security_role_id);


--
-- TOC entry 2908 (class 2606 OID 309331)
-- Dependencies: 1797 1794 2405
-- Name: fk_user_security_roles_users; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_security_roles
    ADD CONSTRAINT fk_user_security_roles_users FOREIGN KEY (user_organization_id) REFERENCES user_organizations(user_organization_id);


--
-- TOC entry 2909 (class 2606 OID 309336)
-- Dependencies: 1798 1798 2420
-- Name: fk_users_creator; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk_users_creator FOREIGN KEY (creator_id) REFERENCES users(user_id);


--
-- TOC entry 2910 (class 2606 OID 309341)
-- Dependencies: 1798 1742 2259
-- Name: fk_users_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk_users_do FOREIGN KEY (user_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2912 (class 2606 OID 310041)
-- Dependencies: 1798 2306 1760
-- Name: fk_users_system_organization; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk_users_system_organization FOREIGN KEY (system_organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2913 (class 2606 OID 309346)
-- Dependencies: 2259 1800 1742
-- Name: fk_virtual_products_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY virtual_products
    ADD CONSTRAINT fk_virtual_products_do FOREIGN KEY (product_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2918 (class 2606 OID 334587)
-- Dependencies: 1802 2180 1716
-- Name: fk_warehouses_business_unit; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouses
    ADD CONSTRAINT fk_warehouses_business_unit FOREIGN KEY (business_unit_id) REFERENCES business_units(business_unit_id);


--
-- TOC entry 2916 (class 2606 OID 309356)
-- Dependencies: 2259 1802 1742
-- Name: fk_warehouses_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouses
    ADD CONSTRAINT fk_warehouses_do_id FOREIGN KEY (warehouse_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2917 (class 2606 OID 334582)
-- Dependencies: 1802 1794 2405
-- Name: fk_warehouses_head_of_stock; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouses
    ADD CONSTRAINT fk_warehouses_head_of_stock FOREIGN KEY (head_of_stock_id) REFERENCES user_organizations(user_organization_id);


--
-- TOC entry 2751 (class 2606 OID 309361)
-- Dependencies: 2374 1776 1783
-- Name: fka00989511ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fka00989511ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2752 (class 2606 OID 309366)
-- Dependencies: 2374 1783 1776
-- Name: fka00989513aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fka00989513aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2753 (class 2606 OID 309371)
-- Dependencies: 1776 1789 2387
-- Name: fka0098951a330dfcf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fka0098951a330dfcf FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2754 (class 2606 OID 309376)
-- Dependencies: 1789 2387 1776
-- Name: fka0098951f10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fka0098951f10b9721 FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2642 (class 2606 OID 309381)
-- Dependencies: 1745 2265 1751
-- Name: fka5191bf0c6b416d1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_dc_items
    ADD CONSTRAINT fka5191bf0c6b416d1 FOREIGN KEY (delivery_certificate_item_id) REFERENCES delivery_certificate_items(certificate_item_id);


--
-- TOC entry 2643 (class 2606 OID 309386)
-- Dependencies: 2289 1752 1751
-- Name: fka5191bf0de886089; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_dc_items
    ADD CONSTRAINT fka5191bf0de886089 FOREIGN KEY (receipt_item_id) REFERENCES goods_receipt_items(receipt_item_id);


--
-- TOC entry 2639 (class 2606 OID 309391)
-- Dependencies: 1760 2306 1750
-- Name: fka76c0db51b04573; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY expressions
    ADD CONSTRAINT fka76c0db51b04573 FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2454 (class 2606 OID 309396)
-- Dependencies: 2374 1783 1708
-- Name: fka7bdcfd22644a070; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT fka7bdcfd22644a070 FOREIGN KEY (algorithm_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2455 (class 2606 OID 309401)
-- Dependencies: 2153 1708 1709
-- Name: fka7bdcfd2a7031f5f; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT fka7bdcfd2a7031f5f FOREIGN KEY (assembling_schema_id) REFERENCES assembling_schemas(product_id);


--
-- TOC entry 2456 (class 2606 OID 309406)
-- Dependencies: 1783 2374 1708
-- Name: fka7bdcfd2bca61a30; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT fka7bdcfd2bca61a30 FOREIGN KEY (data_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2457 (class 2606 OID 309411)
-- Dependencies: 1708 2145 1706
-- Name: fka7bdcfd2dcdf9385; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT fka7bdcfd2dcdf9385 FOREIGN KEY (message_id) REFERENCES assembling_messages(message_id);


--
-- TOC entry 2606 (class 2606 OID 309416)
-- Dependencies: 1745 2374 1783
-- Name: fka8be2f8d1ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fka8be2f8d1ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2607 (class 2606 OID 309421)
-- Dependencies: 2345 1745 1773
-- Name: fka8be2f8df10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fka8be2f8df10b9721 FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2542 (class 2606 OID 309426)
-- Dependencies: 1783 1729 2374
-- Name: fkaa34146576597079; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY currency_exchange_rates
    ADD CONSTRAINT fkaa34146576597079 FOREIGN KEY (from_currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2543 (class 2606 OID 309431)
-- Dependencies: 1783 2374 1729
-- Name: fkaa341465d8e0b5ca; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY currency_exchange_rates
    ADD CONSTRAINT fkaa341465d8e0b5ca FOREIGN KEY (to_currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2609 (class 2606 OID 309486)
-- Dependencies: 2265 1746 1745
-- Name: fkb3b02dd2d961ef9c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_serial_numbers
    ADD CONSTRAINT fkb3b02dd2d961ef9c FOREIGN KEY (certificate_item_id) REFERENCES delivery_certificate_items(certificate_item_id);


--
-- TOC entry 2816 (class 2606 OID 309491)
-- Dependencies: 2173 1714 1786
-- Name: fkbbc878b9134fe2b0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b9134fe2b0 FOREIGN KEY (recipient_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2817 (class 2606 OID 309496)
-- Dependencies: 1783 1786 2374
-- Name: fkbbc878b917174fab; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b917174fab FOREIGN KEY (transportation_method_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2818 (class 2606 OID 309501)
-- Dependencies: 2374 1786 1783
-- Name: fkbbc878b91808129a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b91808129a FOREIGN KEY (doc_delivery_method_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2819 (class 2606 OID 309506)
-- Dependencies: 2136 1704 1786
-- Name: fkbbc878b927a66c93; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b927a66c93 FOREIGN KEY (branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2820 (class 2606 OID 309511)
-- Dependencies: 1783 1786 2374
-- Name: fkbbc878b93aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b93aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2821 (class 2606 OID 309516)
-- Dependencies: 1783 2374 1786
-- Name: fkbbc878b93dc9408c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b93dc9408c FOREIGN KEY (payment_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2822 (class 2606 OID 309521)
-- Dependencies: 2374 1786 1783
-- Name: fkbbc878b946685c7a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b946685c7a FOREIGN KEY (delivery_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2823 (class 2606 OID 309526)
-- Dependencies: 1786 1763 2314
-- Name: fkbbc878b94da6f8bc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b94da6f8bc FOREIGN KEY (creator_id) REFERENCES persons(person_id);


--
-- TOC entry 2824 (class 2606 OID 309531)
-- Dependencies: 1786 2173 1714
-- Name: fkbbc878b96d20f4c9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b96d20f4c9 FOREIGN KEY (shippingagent_partner_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2825 (class 2606 OID 309536)
-- Dependencies: 1727 2210 1786
-- Name: fkbbc878b97ebcc009; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b97ebcc009 FOREIGN KEY (supplier_contact_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2826 (class 2606 OID 309541)
-- Dependencies: 1786 1783 2374
-- Name: fkbbc878b996e3ba71; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b996e3ba71 FOREIGN KEY (payment_terms_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2827 (class 2606 OID 309546)
-- Dependencies: 1786 2374 1783
-- Name: fkbbc878b99a24d298; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b99a24d298 FOREIGN KEY (deliverystatus_resource_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2828 (class 2606 OID 309551)
-- Dependencies: 2374 1783 1786
-- Name: fkbbc878b99c49320d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b99c49320d FOREIGN KEY (status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2829 (class 2606 OID 309556)
-- Dependencies: 1786 1727 2210
-- Name: fkbbc878b99ff294dc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b99ff294dc FOREIGN KEY (attendee_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2830 (class 2606 OID 309561)
-- Dependencies: 2374 1783 1786
-- Name: fkbbc878b9a94f3ab3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b9a94f3ab3 FOREIGN KEY (invoice_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2831 (class 2606 OID 309566)
-- Dependencies: 2374 1786 1783
-- Name: fkbbc878b9b07d659a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b9b07d659a FOREIGN KEY (vat_condition_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2832 (class 2606 OID 309571)
-- Dependencies: 1786 2314 1763
-- Name: fkbbc878b9fd5b3613; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b9fd5b3613 FOREIGN KEY (sender_id) REFERENCES persons(person_id);


--
-- TOC entry 2654 (class 2606 OID 309581)
-- Dependencies: 1774 1753 2351
-- Name: fkc10c99ea219348c0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_pi_items
    ADD CONSTRAINT fkc10c99ea219348c0 FOREIGN KEY (invoice_item_id) REFERENCES purchase_invoice_items(invoice_item_id);


--
-- TOC entry 2655 (class 2606 OID 309586)
-- Dependencies: 1753 2289 1752
-- Name: fkc10c99eade886089; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_pi_items
    ADD CONSTRAINT fkc10c99eade886089 FOREIGN KEY (receipt_item_id) REFERENCES goods_receipt_items(receipt_item_id);


--
-- TOC entry 2589 (class 2606 OID 309591)
-- Dependencies: 2306 1760 1740
-- Name: fkc1a9b66a51b04573; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_type_permissions
    ADD CONSTRAINT fkc1a9b66a51b04573 FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2590 (class 2606 OID 309596)
-- Dependencies: 1741 2255 1740
-- Name: fkc1a9b66aa44e2131; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_type_permissions
    ADD CONSTRAINT fkc1a9b66aa44e2131 FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2591 (class 2606 OID 309601)
-- Dependencies: 2374 1740 1783
-- Name: fkc1a9b66ab01192e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_type_permissions
    ADD CONSTRAINT fkc1a9b66ab01192e FOREIGN KEY (user_right_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2592 (class 2606 OID 309606)
-- Dependencies: 1740 1783 2374
-- Name: fkc1a9b66ac2559310; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_type_permissions
    ADD CONSTRAINT fkc1a9b66ac2559310 FOREIGN KEY (permission_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2755 (class 2606 OID 309611)
-- Dependencies: 2374 1783 1777
-- Name: fkc307e7e31808129a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e31808129a FOREIGN KEY (doc_delivery_method_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2756 (class 2606 OID 309616)
-- Dependencies: 2136 1777 1704
-- Name: fkc307e7e327a66c93; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e327a66c93 FOREIGN KEY (branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2757 (class 2606 OID 309621)
-- Dependencies: 2314 1777 1763
-- Name: fkc307e7e34da6f8bc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e34da6f8bc FOREIGN KEY (creator_id) REFERENCES persons(person_id);


--
-- TOC entry 2758 (class 2606 OID 309626)
-- Dependencies: 2173 1777 1714
-- Name: fkc307e7e35aa049f4; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e35aa049f4 FOREIGN KEY (supplier_partner_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2759 (class 2606 OID 309631)
-- Dependencies: 1777 2210 1727
-- Name: fkc307e7e37ebcc009; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e37ebcc009 FOREIGN KEY (supplier_contact_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2760 (class 2606 OID 309636)
-- Dependencies: 2374 1777 1783
-- Name: fkc307e7e39a24d298; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e39a24d298 FOREIGN KEY (deliverystatus_resource_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2761 (class 2606 OID 309641)
-- Dependencies: 1783 1777 2374
-- Name: fkc307e7e39c49320d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e39c49320d FOREIGN KEY (status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2762 (class 2606 OID 309646)
-- Dependencies: 1763 1777 2314
-- Name: fkc307e7e3fd5b3613; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e3fd5b3613 FOREIGN KEY (sender_id) REFERENCES persons(person_id);


--
-- TOC entry 2846 (class 2606 OID 309651)
-- Dependencies: 1789 2374 1783
-- Name: fkc42bd16427acd874; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd16427acd874 FOREIGN KEY (weight_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2847 (class 2606 OID 309656)
-- Dependencies: 1789 2335 1770
-- Name: fkc42bd1646e2f83d0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd1646e2f83d0 FOREIGN KEY (category_id) REFERENCES product_categories(product_category_id);


--
-- TOC entry 2848 (class 2606 OID 309661)
-- Dependencies: 2173 1714 1789
-- Name: fkc42bd1646e436697; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd1646e436697 FOREIGN KEY (producer_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2849 (class 2606 OID 309666)
-- Dependencies: 1789 2312 1762
-- Name: fkc42bd1647a956e19; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd1647a956e19 FOREIGN KEY (pattern_mask_format_id) REFERENCES pattern_mask_formats(pattern_mask_format_id);


--
-- TOC entry 2850 (class 2606 OID 309671)
-- Dependencies: 1783 2374 1789
-- Name: fkc42bd1648f60524c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd1648f60524c FOREIGN KEY (product_color_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2851 (class 2606 OID 309676)
-- Dependencies: 1789 1783 2374
-- Name: fkc42bd1649d6c3562; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd1649d6c3562 FOREIGN KEY (dimension_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2512 (class 2606 OID 309681)
-- Dependencies: 1723 2200 1720
-- Name: fkc436c2e88e8748f3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classified_objects
    ADD CONSTRAINT fkc436c2e88e8748f3 FOREIGN KEY (classifier_id) REFERENCES classifiers(classifier_id);


--
-- TOC entry 2513 (class 2606 OID 309686)
-- Dependencies: 2259 1742 1720
-- Name: fkc436c2e8cf1f1951; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classified_objects
    ADD CONSTRAINT fkc436c2e8cf1f1951 FOREIGN KEY (classified_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2686 (class 2606 OID 309701)
-- Dependencies: 2306 1761 1760
-- Name: fkc84af6a1db08a2d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT fkc84af6a1db08a2d FOREIGN KEY (issuer_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2461 (class 2606 OID 309706)
-- Dependencies: 1709 1783 2374
-- Name: fke7efd4c21ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT fke7efd4c21ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2462 (class 2606 OID 309711)
-- Dependencies: 1709 1705 2139
-- Name: fke7efd4c26d4edb2f; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT fke7efd4c26d4edb2f FOREIGN KEY (category_id) REFERENCES assembling_categories(assembling_category_id);


--
-- TOC entry 2463 (class 2606 OID 309716)
-- Dependencies: 1709 2426 1800
-- Name: fke7efd4c29f88efd5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT fke7efd4c29f88efd5 FOREIGN KEY (product_id) REFERENCES virtual_products(product_id);


--
-- TOC entry 2852 (class 2606 OID 309721)
-- Dependencies: 1789 1783 2374
-- Name: fke810995127acd874; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke810995127acd874 FOREIGN KEY (weight_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2853 (class 2606 OID 309726)
-- Dependencies: 2335 1789 1770
-- Name: fke81099516e2f83d0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke81099516e2f83d0 FOREIGN KEY (category_id) REFERENCES product_categories(product_category_id);


--
-- TOC entry 2854 (class 2606 OID 309731)
-- Dependencies: 2173 1789 1714
-- Name: fke81099516e436697; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke81099516e436697 FOREIGN KEY (producer_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2855 (class 2606 OID 309736)
-- Dependencies: 2312 1762 1789
-- Name: fke81099517a956e19; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke81099517a956e19 FOREIGN KEY (pattern_mask_format_id) REFERENCES pattern_mask_formats(pattern_mask_format_id);


--
-- TOC entry 2856 (class 2606 OID 309741)
-- Dependencies: 1771 1789 2339
-- Name: fke810995185c08915; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke810995185c08915 FOREIGN KEY (discount_percent_id) REFERENCES product_percent_values(percent_value_id);


--
-- TOC entry 2857 (class 2606 OID 309746)
-- Dependencies: 1783 1789 2374
-- Name: fke81099518f60524c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke81099518f60524c FOREIGN KEY (product_color_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2858 (class 2606 OID 309751)
-- Dependencies: 2339 1789 1771
-- Name: fke810995193879943; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke810995193879943 FOREIGN KEY (customs_duty_percent_id) REFERENCES product_percent_values(percent_value_id);


--
-- TOC entry 2859 (class 2606 OID 309756)
-- Dependencies: 1789 1783 2374
-- Name: fke81099519d6c3562; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke81099519d6c3562 FOREIGN KEY (dimension_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2860 (class 2606 OID 309761)
-- Dependencies: 1771 2339 1789
-- Name: fke8109951b75e188c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke8109951b75e188c FOREIGN KEY (excise_duty_percent_id) REFERENCES product_percent_values(percent_value_id);


--
-- TOC entry 2861 (class 2606 OID 309766)
-- Dependencies: 1771 2339 1789
-- Name: fke8109951e459462d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke8109951e459462d FOREIGN KEY (transport_percent_id) REFERENCES product_percent_values(percent_value_id);


--
-- TOC entry 2862 (class 2606 OID 309771)
-- Dependencies: 1789 1771 2339
-- Name: fke8109951f05d1032; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke8109951f05d1032 FOREIGN KEY (profit_percent_id) REFERENCES product_percent_values(percent_value_id);


--
-- TOC entry 2863 (class 2606 OID 309776)
-- Dependencies: 1773 2345 1789
-- Name: fke8109951f10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke8109951f10b9721 FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2781 (class 2606 OID 309781)
-- Dependencies: 1781 1763 2314
-- Name: fke9334463157c075; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke9334463157c075 FOREIGN KEY (forwarder_contact_id) REFERENCES persons(person_id);


--
-- TOC entry 2782 (class 2606 OID 309786)
-- Dependencies: 1781 1763 2314
-- Name: fke93344634da6f8bc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke93344634da6f8bc FOREIGN KEY (creator_id) REFERENCES persons(person_id);


--
-- TOC entry 2783 (class 2606 OID 309791)
-- Dependencies: 2314 1763 1781
-- Name: fke93344636faaa615; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke93344636faaa615 FOREIGN KEY (deliverer_contact_id) REFERENCES persons(person_id);


--
-- TOC entry 2784 (class 2606 OID 309796)
-- Dependencies: 2247 1738 1781
-- Name: fke933446370294164; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke933446370294164 FOREIGN KEY (deliverer_id) REFERENCES data_object_links(data_object_link_id);


--
-- TOC entry 2785 (class 2606 OID 309801)
-- Dependencies: 1802 1781 2430
-- Name: fke93344639f1800e1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke93344639f1800e1 FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2786 (class 2606 OID 309806)
-- Dependencies: 2374 1783 1781
-- Name: fke9334463d6755f5b; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke9334463d6755f5b FOREIGN KEY (receipt_cert_method_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2787 (class 2606 OID 309811)
-- Dependencies: 1781 2306 1760
-- Name: fke9334463f2569c14; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke9334463f2569c14 FOREIGN KEY (forwarder_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2788 (class 2606 OID 309816)
-- Dependencies: 1781 1783 2374
-- Name: fke9334463fe9be307; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke9334463fe9be307 FOREIGN KEY (receipt_cert_reason_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2516 (class 2606 OID 309821)
-- Dependencies: 2200 1721 1723
-- Name: fkefe6ccf38e8748f3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifier_applied_for_dot
    ADD CONSTRAINT fkefe6ccf38e8748f3 FOREIGN KEY (classifier_id) REFERENCES classifiers(classifier_id);


--
-- TOC entry 2517 (class 2606 OID 309826)
-- Dependencies: 2255 1721 1741
-- Name: fkefe6ccf3a44e2131; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifier_applied_for_dot
    ADD CONSTRAINT fkefe6ccf3a44e2131 FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2893 (class 2606 OID 309831)
-- Dependencies: 1793 2403 1796
-- Name: fkf4b9c8cb12dbbc4a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT fkf4b9c8cb12dbbc4a FOREIGN KEY (user_group_id) REFERENCES user_groups(user_group_id);


--
-- TOC entry 2894 (class 2606 OID 309836)
-- Dependencies: 1796 2374 1783
-- Name: fkf4b9c8cb324c6e0a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT fkf4b9c8cb324c6e0a FOREIGN KEY (special_permission_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2895 (class 2606 OID 309841)
-- Dependencies: 1741 1796 2255
-- Name: fkf4b9c8cba44e2131; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT fkf4b9c8cba44e2131 FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2896 (class 2606 OID 309846)
-- Dependencies: 2420 1798 1796
-- Name: fkf4b9c8cbb4a34773; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT fkf4b9c8cbb4a34773 FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- TOC entry 2897 (class 2606 OID 309851)
-- Dependencies: 1742 1796 2259
-- Name: fkf4b9c8cbd741e28; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT fkf4b9c8cbd741e28 FOREIGN KEY (data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2898 (class 2606 OID 309861)
-- Dependencies: 2403 1796 1793
-- Name: fkfdef48b312dbbc4a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT fkfdef48b312dbbc4a FOREIGN KEY (user_group_id) REFERENCES user_groups(user_group_id);


--
-- TOC entry 2899 (class 2606 OID 309866)
-- Dependencies: 2374 1796 1783
-- Name: fkfdef48b3324c6e0a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT fkfdef48b3324c6e0a FOREIGN KEY (special_permission_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2900 (class 2606 OID 309871)
-- Dependencies: 1796 2255 1741
-- Name: fkfdef48b3a44e2131; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT fkfdef48b3a44e2131 FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2901 (class 2606 OID 309876)
-- Dependencies: 1796 2420 1798
-- Name: fkfdef48b3b4a34773; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT fkfdef48b3b4a34773 FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- TOC entry 2902 (class 2606 OID 309881)
-- Dependencies: 1796 2259 1742
-- Name: fkfdef48b3d741e28; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT fkfdef48b3d741e28 FOREIGN KEY (data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2792 (class 2606 OID 309886)
-- Dependencies: 1784 2378 1785
-- Name: fkfefcb143e94bedc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoice_item_link
    ADD CONSTRAINT fkfefcb143e94bedc FOREIGN KEY (invoice_item_id) REFERENCES sales_invoice_items(invoice_item_id);


--
-- TOC entry 2533 (class 2606 OID 309891)
-- Dependencies: 2345 1773 1726
-- Name: fkff77e413f10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_products
    ADD CONSTRAINT fkff77e413f10b9721 FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2680 (class 2606 OID 309896)
-- Dependencies: 1760 2136 1704
-- Name: organizations_administration_address_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT organizations_administration_address_id_fkey FOREIGN KEY (administration_address_id) REFERENCES addresses(address_id) ON DELETE SET NULL;


--
-- TOC entry 2681 (class 2606 OID 309901)
-- Dependencies: 1783 2374 1760
-- Name: organizations_currency_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT organizations_currency_id_fkey FOREIGN KEY (share_capital_currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2682 (class 2606 OID 309906)
-- Dependencies: 2136 1704 1760
-- Name: organizations_registration_address_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT organizations_registration_address_id_fkey FOREIGN KEY (registration_address_id) REFERENCES addresses(address_id) ON DELETE SET NULL;


--
-- TOC entry 2687 (class 2606 OID 309911)
-- Dependencies: 1763 1761 2314
-- Name: passports_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT passports_parent_id_fkey FOREIGN KEY (person_id) REFERENCES persons(person_id) ON DELETE CASCADE;


--
-- TOC entry 2876 (class 2606 OID 309926)
-- Dependencies: 1760 1793 2306
-- Name: user_groups_organizations; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_groups
    ADD CONSTRAINT user_groups_organizations FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2903 (class 2606 OID 309931)
-- Dependencies: 2374 1796 1783
-- Name: user_rights_special_permission_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT user_rights_special_permission_id_fkey FOREIGN KEY (special_permission_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2904 (class 2606 OID 309936)
-- Dependencies: 1796 2403 1793
-- Name: user_rights_user_group_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT user_rights_user_group_id_fkey FOREIGN KEY (user_group_id) REFERENCES user_groups(user_group_id) ON DELETE CASCADE;


--
-- TOC entry 2905 (class 2606 OID 309941)
-- Dependencies: 2420 1796 1798
-- Name: user_rights_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT user_rights_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE;


--
-- TOC entry 2882 (class 2606 OID 309946)
-- Dependencies: 1794 2306 1760
-- Name: users_organizations_organization_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_organizations
    ADD CONSTRAINT users_organizations_organization_id_fkey FOREIGN KEY (organization_id) REFERENCES organizations(organization_id) ON DELETE CASCADE;


--
-- TOC entry 2911 (class 2606 OID 309951)
-- Dependencies: 1763 2314 1798
-- Name: users_person_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_person_id_fkey FOREIGN KEY (person_id) REFERENCES persons(person_id);


--
-- TOC entry 2925 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: -
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2009-11-05 12:27:43

--
-- PostgreSQL database dump complete
--

