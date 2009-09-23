--
-- PostgreSQL database dump
--

-- Started on 2009-09-23 16:09:21

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 532 (class 2612 OID 16386)
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: -
--

CREATE PROCEDURAL LANGUAGE plpgsql;


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

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
    parent_id uuid NOT NULL,
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
-- TOC entry 3031 (class 0 OID 0)
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
-- TOC entry 3032 (class 0 OID 0)
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
    parent_id uuid
);


--
-- TOC entry 1723 (class 1259 OID 306768)
-- Dependencies: 6
-- Name: classifiers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE classifiers (
    classifier_id uuid NOT NULL,
    parent_id uuid,
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
-- TOC entry 3033 (class 0 OID 0)
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
-- TOC entry 3034 (class 0 OID 0)
-- Dependencies: 1747
-- Name: COLUMN delivery_certificates.forwarder_branch_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN delivery_certificates.forwarder_branch_id IS 'As the Forwarder is an organization, we have to know abranch_id in order to choose a contact person.';


--
-- TOC entry 3035 (class 0 OID 0)
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
-- TOC entry 3036 (class 0 OID 0)
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
    parent_business_partner_id uuid NOT NULL
);


--
-- TOC entry 1761 (class 1259 OID 306921)
-- Dependencies: 6
-- Name: passports; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE passports (
    passport_id uuid NOT NULL,
    parent_id uuid NOT NULL,
    passport_type_id integer NOT NULL,
    passport_number character varying(16) NOT NULL,
    issue_date date NOT NULL,
    expiration_date date NOT NULL,
    issuer_id uuid NOT NULL,
    issuer_branch_id uuid NOT NULL,
    additional_info character varying(255)
);


--
-- TOC entry 3037 (class 0 OID 0)
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
-- TOC entry 3038 (class 0 OID 0)
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
-- TOC entry 3039 (class 0 OID 0)
-- Dependencies: 1787
-- Name: COLUMN security_roles.organization_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN security_roles.organization_id IS 'The parent of this entity';


--
-- TOC entry 3040 (class 0 OID 0)
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
-- Dependencies: 2121 6
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
    business_unit_id uuid NOT NULL
);


--
-- TOC entry 1795 (class 1259 OID 307068)
-- Dependencies: 2122 2123 2124 2125 2126 6
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
-- TOC entry 3041 (class 0 OID 0)
-- Dependencies: 1795
-- Name: COLUMN user_rights.owner_type_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN user_rights.owner_type_id IS 'U=User, G=User Group';


--
-- TOC entry 1796 (class 1259 OID 307076)
-- Dependencies: 2127 2128 2129 2130 2131 2132 6
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
-- Dependencies: 2133 2134 6
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
    ordereddeliverytime integer
);


--
-- TOC entry 1802 (class 1259 OID 307105)
-- Dependencies: 6
-- Name: warehouses; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE warehouses (
    warehouse_id uuid NOT NULL,
    parent_id uuid,
    address_id uuid NOT NULL,
    description text,
    index bigint,
    warehouseman_id uuid
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
-- TOC entry 3042 (class 0 OID 0)
-- Dependencies: 1803
-- Name: cash_recon_pay_summary_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('cash_recon_pay_summary_seq', 1, true);


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
-- TOC entry 3043 (class 0 OID 0)
-- Dependencies: 1804
-- Name: countries_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('countries_seq', 1, true);


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
-- TOC entry 3044 (class 0 OID 0)
-- Dependencies: 1805
-- Name: customer_payment_match_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('customer_payment_match_seq', 1, true);


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
-- TOC entry 3045 (class 0 OID 0)
-- Dependencies: 1806
-- Name: data_object_type_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('data_object_type_seq', 40, true);


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
-- TOC entry 3046 (class 0 OID 0)
-- Dependencies: 1807
-- Name: data_objects_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('data_objects_seq', 1, true);


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
-- TOC entry 3047 (class 0 OID 0)
-- Dependencies: 1808
-- Name: enum_classes_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('enum_classes_seq', 82, true);


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
-- TOC entry 3048 (class 0 OID 0)
-- Dependencies: 1809
-- Name: invoice_item_link_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('invoice_item_link_seq', 1, true);


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
-- TOC entry 3049 (class 0 OID 0)
-- Dependencies: 1810
-- Name: order_item_match_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('order_item_match_seq', 1, true);


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
-- TOC entry 3050 (class 0 OID 0)
-- Dependencies: 1811
-- Name: pattern_mask_formats_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('pattern_mask_formats_seq', 1, true);


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
-- TOC entry 3051 (class 0 OID 0)
-- Dependencies: 1812
-- Name: resource_bundle_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('resource_bundle_seq', 1102, true);


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
-- TOC entry 3052 (class 0 OID 0)
-- Dependencies: 1813
-- Name: sequence_identifiers_seq_id_key_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE sequence_identifiers_seq_id_key_seq OWNED BY sequence_identifiers.seq_id_key;


--
-- TOC entry 3053 (class 0 OID 0)
-- Dependencies: 1813
-- Name: sequence_identifiers_seq_id_key_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('sequence_identifiers_seq_id_key_seq', 1, false);


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
-- TOC entry 3054 (class 0 OID 0)
-- Dependencies: 1814
-- Name: user_rights_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('user_rights_seq', 1, true);


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
-- TOC entry 3055 (class 0 OID 0)
-- Dependencies: 1815
-- Name: warehouse_product_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('warehouse_product_seq', 1, true);


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
-- TOC entry 3056 (class 0 OID 0)
-- Dependencies: 1816
-- Name: xyz_id_sequence; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('xyz_id_sequence', 1, false);


--
-- TOC entry 2114 (class 2604 OID 307139)
-- Dependencies: 1813 1788
-- Name: seq_id_key; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE sequence_identifiers ALTER COLUMN seq_id_key SET DEFAULT nextval('sequence_identifiers_seq_id_key_seq'::regclass);


--
-- TOC entry 2926 (class 0 OID 306677)
-- Dependencies: 1704
-- Data for Name: addresses; Type: TABLE DATA; Schema: public; Owner: -
--

COPY addresses (address_id, business_partner_id, address_name, country_id, city_id, postal_code, postal_address) FROM stdin;
59f011d1-a6bc-40d5-8735-a6c338971f6c	6b77867c-3bca-4fa9-9b75-540b4f0546cc	Headquarter	73e01607-9465-4937-aca6-0ec6c7be6660	\N	1612	ap. 15, entr. B, fl. 1, 5 Vorino str., kv. Krasno selo
db77bc8d-3a32-4c28-bec0-ec9b72da2582	a620f116-ed18-4c69-9d0c-84ed0a41c5f1	Registration	\N	ac570f14-97cf-4c41-ab28-33739467c642	\N	aaa
\.


--
-- TOC entry 2927 (class 0 OID 306680)
-- Dependencies: 1705
-- Data for Name: assembling_categories; Type: TABLE DATA; Schema: public; Owner: -
--

COPY assembling_categories (assembling_category_id, parent_id, category_code, category_name, description, parent_category_id) FROM stdin;
\.


--
-- TOC entry 2928 (class 0 OID 306686)
-- Dependencies: 1706
-- Data for Name: assembling_messages; Type: TABLE DATA; Schema: public; Owner: -
--

COPY assembling_messages (message_id, organization_id, message_code, message_label, selection_text, selection_title, input_text, input_title, description) FROM stdin;
4277587d-d230-4913-8467-4e78095393f1	6b77867c-3bca-4fa9-9b75-540b4f0546cc	width	Width	Please select the width	Width selection	Please enter the width	Width input	\N
16f43bbf-4735-43f9-88d0-b234b67b0757	6b77867c-3bca-4fa9-9b75-540b4f0546cc	height	Height	Please select the height	Height selection	Please enter the height	Height input	\N
a1a4842d-6d74-4cfb-9d60-a9a6eea95034	6b77867c-3bca-4fa9-9b75-540b4f0546cc	length	Length	Please select the length	Length selection	Please enter the length	Length input	\N
47b3f779-421b-4b00-94a7-9ffa5c22db06	6b77867c-3bca-4fa9-9b75-540b4f0546cc	weight	Weight	Please select the weight	Weight selection	Please enter the weight	Weight input	\N
98f87623-72b1-4e8b-87d2-69cac2c87d11	6b77867c-3bca-4fa9-9b75-540b4f0546cc	color	Color	Please select the color	Color selection	Please enter the color	Color input	\N
35d7aaa3-db68-4cb9-bc1f-90ae3e78a416	6b77867c-3bca-4fa9-9b75-540b4f0546cc	product	Product	Please select the product	Product selection	Please enter the product	Product input	\N
2c86eae6-328d-4847-86fb-098fe52af3a9	6b77867c-3bca-4fa9-9b75-540b4f0546cc	schema	Schema	Please select the schema	Schema selection	Please enter the schema	Schema input	\N
473d9064-5249-459f-872a-98fa77cabeed	6b77867c-3bca-4fa9-9b75-540b4f0546cc	material	Material	Please select the material	Material selection	Please enter the material	Material input	\N
c5b12fe5-e5dd-4fb9-aff4-d992ede653b9	6b77867c-3bca-4fa9-9b75-540b4f0546cc	direction	Direction	Please select the direction	Direction selection	Please enter the direction	Direction input	\N
b817dd7c-d025-46a5-a069-3fb1cd9a1f42	6b77867c-3bca-4fa9-9b75-540b4f0546cc	profile	Profile	Please select the profile	Profile selection	Please enter the profile	Profile input	\N
6a84f052-4083-421d-a491-522db8e7bc4d	6b77867c-3bca-4fa9-9b75-540b4f0546cc	orientation	Orientation	Please select the orientation	Orientation selection	Please enter the orientation	Orientation input	\N
1ad8adcd-d3ee-4a38-aa90-5e700cc93134	6b77867c-3bca-4fa9-9b75-540b4f0546cc	specification	Specification	Please select the specification	Specification selection	Please enter the specification	Specification input	\N
\.


--
-- TOC entry 2929 (class 0 OID 306692)
-- Dependencies: 1707
-- Data for Name: assembling_schema_item_values; Type: TABLE DATA; Schema: public; Owner: -
--

COPY assembling_schema_item_values (item_value_id, item_id, min_constraint, max_constraint, virtual_product_id, quantity) FROM stdin;
\.


--
-- TOC entry 2930 (class 0 OID 306699)
-- Dependencies: 1708
-- Data for Name: assembling_schema_items; Type: TABLE DATA; Schema: public; Owner: -
--

COPY assembling_schema_items (item_id, assembling_schema_id, algorithm_id, message_id, min_selections, max_selections, quantity, default_value, data_type_id, description) FROM stdin;
\.


--
-- TOC entry 2931 (class 0 OID 306706)
-- Dependencies: 1709
-- Data for Name: assembling_schemas; Type: TABLE DATA; Schema: public; Owner: -
--

COPY assembling_schemas (product_id, category_id, schema_code, schema_name, is_obsolete, measure_unit_id, description, is_applicable) FROM stdin;
\.


--
-- TOC entry 2932 (class 0 OID 306714)
-- Dependencies: 1710
-- Data for Name: bank_details; Type: TABLE DATA; Schema: public; Owner: -
--

COPY bank_details (bank_detail_id, parent_id, is_default, currency_id, bank_id, bank_branch_id, bank_account, bank_contact_id, bic, iban, swift_code) FROM stdin;
\.


--
-- TOC entry 2933 (class 0 OID 306722)
-- Dependencies: 1711
-- Data for Name: banknote_quantity; Type: TABLE DATA; Schema: public; Owner: -
--

COPY banknote_quantity (banknote_quantity_id, cash_reconcile_id, quantity, currency_nominal_id) FROM stdin;
\.


--
-- TOC entry 2934 (class 0 OID 306725)
-- Dependencies: 1712
-- Data for Name: business_document_status_log; Type: TABLE DATA; Schema: public; Owner: -
--

COPY business_document_status_log (document_id, document_status_id, action_time, officer_id) FROM stdin;
\.


--
-- TOC entry 2935 (class 0 OID 306728)
-- Dependencies: 1713
-- Data for Name: business_documents; Type: TABLE DATA; Schema: public; Owner: -
--

COPY business_documents (document_id, document_type_id, document_status_id, discriminator_id, publisher_id, publisher_branch_id, publisher_officer_id, document_number, document_date, creation_time) FROM stdin;
\.


--
-- TOC entry 2936 (class 0 OID 306731)
-- Dependencies: 1714
-- Data for Name: business_partners; Type: TABLE DATA; Schema: public; Owner: -
--

COPY business_partners (business_partner_id, parent_business_partner_id, default_currency_id, discriminator_id) FROM stdin;
a549eda7-b362-4151-909f-8ed67cbdc5ab	a549eda7-b362-4151-909f-8ed67cbdc5ab	756	O 
dd33f937-b181-4756-93db-69c8f9e83c8a	a549eda7-b362-4151-909f-8ed67cbdc5ab	756	P 
55144634-4519-4401-b042-f8d8ea3747e8	55144634-4519-4401-b042-f8d8ea3747e8	756	O 
90deb642-f53d-4cf1-8182-58f148517c76	55144634-4519-4401-b042-f8d8ea3747e8	756	P 
d03b857f-b05b-47ae-bd0d-eefd83b83372	d03b857f-b05b-47ae-bd0d-eefd83b83372	756	O 
10bb09e9-c2f9-4901-bab3-c37e9bde8779	d03b857f-b05b-47ae-bd0d-eefd83b83372	756	P 
e5d5c576-ae35-43de-a67a-40f56ae598d0	e5d5c576-ae35-43de-a67a-40f56ae598d0	756	O 
f4a17bae-e34c-43c2-a251-3ba0a26f7355	e5d5c576-ae35-43de-a67a-40f56ae598d0	756	P 
70c2dab0-1a08-411b-b18e-7b8bf0aacaa3	70c2dab0-1a08-411b-b18e-7b8bf0aacaa3	756	O 
83524976-6550-4967-b8ae-3ef87bdb48bf	70c2dab0-1a08-411b-b18e-7b8bf0aacaa3	756	P 
c2228a74-4879-4338-9a6b-d92df52bffc9	c2228a74-4879-4338-9a6b-d92df52bffc9	756	O 
135a52f7-c6a2-4da9-b501-754d5134cf74	c2228a74-4879-4338-9a6b-d92df52bffc9	756	P 
d1a51325-c34c-4dec-acd6-6e9b36fe6a54	d1a51325-c34c-4dec-acd6-6e9b36fe6a54	756	O 
df009b07-c928-4402-9e66-8f763962474b	d1a51325-c34c-4dec-acd6-6e9b36fe6a54	756	P 
59f5e9f2-217c-401f-bd94-ae8b5d4ac477	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	756	O 
1d593a2b-4d70-43e5-ba35-e4497834ebf8	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	756	P 
17d7af2a-7efd-4443-9ffb-08636a8253c0	17d7af2a-7efd-4443-9ffb-08636a8253c0	756	O 
53e7cb98-7cd7-4396-ab75-e92e5a49d6bb	17d7af2a-7efd-4443-9ffb-08636a8253c0	756	P 
e7dc4455-6e3d-401d-b358-a7cd2b93f3f0	17d7af2a-7efd-4443-9ffb-08636a8253c0	738	P 
da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	756	O 
c38a4163-0d89-4b4b-9714-5216f2ff3bbb	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	756	P 
be102e1f-00f5-48b5-9cbc-3d2583527629	be102e1f-00f5-48b5-9cbc-3d2583527629	756	O 
a62f4b7b-9a73-4c3c-96d8-3e9697f09815	be102e1f-00f5-48b5-9cbc-3d2583527629	756	P 
40fa7e11-6ec0-40fc-a3d3-31c53aef0d2b	be102e1f-00f5-48b5-9cbc-3d2583527629	738	P 
3cc75b52-13a0-4695-be04-857014c9498d	be102e1f-00f5-48b5-9cbc-3d2583527629	738	O 
b861637a-7d71-4248-8ce1-543f479b1589	be102e1f-00f5-48b5-9cbc-3d2583527629	756	P 
153502dc-8b72-444a-abcd-1bfd8ee794ae	be102e1f-00f5-48b5-9cbc-3d2583527629	738	O 
b7ced214-7c98-48f9-a445-b80d907ae177	be102e1f-00f5-48b5-9cbc-3d2583527629	756	P 
81e50b6b-68a6-4e50-9103-95fe8eba86f3	81e50b6b-68a6-4e50-9103-95fe8eba86f3	756	O 
068397ba-eded-4a10-a976-bd5ce2c113a4	81e50b6b-68a6-4e50-9103-95fe8eba86f3	756	P 
679ae5a7-eaff-4807-8ee1-6455bfb1c3be	81e50b6b-68a6-4e50-9103-95fe8eba86f3	738	P 
d5676dfc-7bfa-4e9d-a99a-d0947f47a58e	81e50b6b-68a6-4e50-9103-95fe8eba86f3	738	O 
73b1122d-285c-4ee2-9378-a30caebe10de	81e50b6b-68a6-4e50-9103-95fe8eba86f3	756	P 
8d550848-9260-41a7-aefd-8969d4179b13	8d550848-9260-41a7-aefd-8969d4179b13	756	O 
28425f1b-8617-4f32-8118-fd5a9acc66f9	8d550848-9260-41a7-aefd-8969d4179b13	756	P 
9bbed255-4a67-42ff-bc7e-687d944da307	8d550848-9260-41a7-aefd-8969d4179b13	738	P 
dec13d93-c399-4df5-82f2-2a07a2d605b0	8d550848-9260-41a7-aefd-8969d4179b13	738	O 
07a8c30b-5884-4b4d-be19-3fd099badf6c	8d550848-9260-41a7-aefd-8969d4179b13	756	P 
70592cfd-348d-4255-a227-ab989eb38f25	70592cfd-348d-4255-a227-ab989eb38f25	756	O 
5ca3e3c1-aed8-4d3f-9939-cebc2c9502db	70592cfd-348d-4255-a227-ab989eb38f25	756	P 
df314b70-72cb-426b-82e2-ab25ddf83bd2	70592cfd-348d-4255-a227-ab989eb38f25	738	P 
e816f01f-d95b-4f51-88b8-4c6409a6b7ae	70592cfd-348d-4255-a227-ab989eb38f25	738	O 
ce1d5ccf-cf18-4516-bd35-7cdbf97c90a4	70592cfd-348d-4255-a227-ab989eb38f25	756	P 
6b77867c-3bca-4fa9-9b75-540b4f0546cc	6b77867c-3bca-4fa9-9b75-540b4f0546cc	756	O 
ced27fbe-fb72-4918-8f56-9e41b6dec8cc	6b77867c-3bca-4fa9-9b75-540b4f0546cc	756	P 
549fe6ba-e2cd-4da5-bceb-ce3da95b80e4	6b77867c-3bca-4fa9-9b75-540b4f0546cc	738	P 
a620f116-ed18-4c69-9d0c-84ed0a41c5f1	6b77867c-3bca-4fa9-9b75-540b4f0546cc	738	O 
ec3246e5-2ffd-4f56-8316-1510cf48c33d	6b77867c-3bca-4fa9-9b75-540b4f0546cc	756	P 
\.


--
-- TOC entry 2937 (class 0 OID 306734)
-- Dependencies: 1715
-- Data for Name: business_unit_addresses; Type: TABLE DATA; Schema: public; Owner: -
--

COPY business_unit_addresses (business_unit_address_id, business_unit_id, address_id, address_type_id, phone_id, fax_id, email_id) FROM stdin;
749494f8-301a-4b0b-b408-4b05658dfd34	4186c2e8-cdd8-4fab-bd29-04bfd7379f8c	59f011d1-a6bc-40d5-8735-a6c338971f6c	1072	\N	\N	\N
\.


--
-- TOC entry 2938 (class 0 OID 306737)
-- Dependencies: 1716
-- Data for Name: business_units; Type: TABLE DATA; Schema: public; Owner: -
--

COPY business_units (business_unit_id, organization_id, parent_business_unit_id, is_root, business_unit_type_id, business_unit_name, is_disabled, division_name, web_site) FROM stdin;
ea9e8e30-eb02-42de-95c2-74890823bbfa	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	t	1065	Root	f	\N	\N
c1ed4b0b-a3b3-44f9-bc2e-3f01a7d97f2f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	ea9e8e30-eb02-42de-95c2-74890823bbfa	f	1065	Users	f	\N	\N
4186c2e8-cdd8-4fab-bd29-04bfd7379f8c	a620f116-ed18-4c69-9d0c-84ed0a41c5f1	\N	t	1065	Root	f	\N	\N
\.


--
-- TOC entry 2939 (class 0 OID 306743)
-- Dependencies: 1717
-- Data for Name: cash_reconcile; Type: TABLE DATA; Schema: public; Owner: -
--

COPY cash_reconcile (cash_reconcile_id, initial_bank_balance, initial_cash_balance, period_bank_expenses, period_bank_revenue, period_cash_expenses, period_cash_revenue, cashier_id, currency_id) FROM stdin;
\.


--
-- TOC entry 2940 (class 0 OID 306746)
-- Dependencies: 1718
-- Data for Name: cash_reconcile_payment_summary; Type: TABLE DATA; Schema: public; Owner: -
--

COPY cash_reconcile_payment_summary (payment_summary_id, amount, payment_type_id, currency_id, cash_reconcile_id) FROM stdin;
\.


--
-- TOC entry 2941 (class 0 OID 306749)
-- Dependencies: 1719
-- Data for Name: cities; Type: TABLE DATA; Schema: public; Owner: -
--

COPY cities (city_id, country_id, city_name, postal_code, city_code, city_phone_code) FROM stdin;
ac570f14-97cf-4c41-ab28-33739467c642	73e01607-9465-4937-aca6-0ec6c7be6660	Sofia	1000	SOF	2
\.


--
-- TOC entry 2942 (class 0 OID 306752)
-- Dependencies: 1720
-- Data for Name: classified_objects; Type: TABLE DATA; Schema: public; Owner: -
--

COPY classified_objects (classifier_id, classified_object_id, description) FROM stdin;
2997673e-8ef1-4a5b-87ff-70827afc4462	3cc75b52-13a0-4695-be04-857014c9498d	\N
2997673e-8ef1-4a5b-87ff-70827afc4462	153502dc-8b72-444a-abcd-1bfd8ee794ae	\N
ccd29345-d196-4a44-8b70-ab54c7296671	dec13d93-c399-4df5-82f2-2a07a2d605b0	\N
f9beb75d-648e-455a-b8eb-176d95f6bf2d	e816f01f-d95b-4f51-88b8-4c6409a6b7ae	\N
\.


--
-- TOC entry 2943 (class 0 OID 306758)
-- Dependencies: 1721
-- Data for Name: classifier_applied_for_dot; Type: TABLE DATA; Schema: public; Owner: -
--

COPY classifier_applied_for_dot (classifier_id, data_object_type_id) FROM stdin;
\.


--
-- TOC entry 2944 (class 0 OID 306761)
-- Dependencies: 1722
-- Data for Name: classifier_groups; Type: TABLE DATA; Schema: public; Owner: -
--

COPY classifier_groups (classifier_group_id, is_system_group, classifier_group_code, classifier_group_name, description, parent_id) FROM stdin;
ec5fde4c-9918-4f45-8f79-23142298ddb4	t	System	System Group	The System Classifier Group	e5d5c576-ae35-43de-a67a-40f56ae598d0
b794bb09-ff92-4719-b778-523882a926ff	t	System	System Group	The System Classifier Group	c2228a74-4879-4338-9a6b-d92df52bffc9
322dedd6-18bd-4da9-ab38-7ff101f2f033	t	System	System Group	The System Classifier Group	59f5e9f2-217c-401f-bd94-ae8b5d4ac477
a6ad959e-74d2-4876-add3-a970476e6281	t	System	System Group	The System Classifier Group	17d7af2a-7efd-4443-9ffb-08636a8253c0
bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1	t	System	System Group	The System Classifier Group	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd
ba3bd467-3add-47fb-b425-f6f78734f0e1	t	System	System Group	The System Classifier Group	be102e1f-00f5-48b5-9cbc-3d2583527629
7c1668ee-4668-4625-966b-e447d70a19ee	t	System	System Group	The System Classifier Group	81e50b6b-68a6-4e50-9103-95fe8eba86f3
f0249923-3520-4d19-a6ab-8bc3574a7709	t	System	System Group	The System Classifier Group	8d550848-9260-41a7-aefd-8969d4179b13
276e6701-a1f1-4fe0-a4aa-31edf6880c0f	t	System	System Group	The System Classifier Group	70592cfd-348d-4255-a227-ab989eb38f25
2474c5bb-7e45-4244-a547-80bdf045e037	t	System	System Group	The System Classifier Group	6b77867c-3bca-4fa9-9b75-540b4f0546cc
\.


--
-- TOC entry 2945 (class 0 OID 306768)
-- Dependencies: 1723
-- Data for Name: classifiers; Type: TABLE DATA; Schema: public; Owner: -
--

COPY classifiers (classifier_id, parent_id, classifier_code, classifier_name, description, classifier_group_id) FROM stdin;
dc9f5540-bde6-4242-ae6a-d876edb643c1	e5d5c576-ae35-43de-a67a-40f56ae598d0	AdvertisementAgency	Advertisement Agency	Advertisement Agency	ec5fde4c-9918-4f45-8f79-23142298ddb4
c5dbee81-5d5c-4b3f-8721-17b151868b18	e5d5c576-ae35-43de-a67a-40f56ae598d0	Bank	Bank	Bank	ec5fde4c-9918-4f45-8f79-23142298ddb4
377aad89-e1ec-4ff3-b37b-749ab1eb3ca2	e5d5c576-ae35-43de-a67a-40f56ae598d0	Cashier	Cashier	Cashier	ec5fde4c-9918-4f45-8f79-23142298ddb4
cdb140df-5057-4fd2-8a81-a248cb1cd1aa	e5d5c576-ae35-43de-a67a-40f56ae598d0	College	College	College	ec5fde4c-9918-4f45-8f79-23142298ddb4
d85c3433-9b7a-4378-9db7-ace1022b4a66	e5d5c576-ae35-43de-a67a-40f56ae598d0	Courier	Courier	Courier	ec5fde4c-9918-4f45-8f79-23142298ddb4
6dfeb830-b52f-477d-a85f-c55ef9af9756	e5d5c576-ae35-43de-a67a-40f56ae598d0	Court	Court	Court	ec5fde4c-9918-4f45-8f79-23142298ddb4
30eac8d0-460b-44ce-a656-67f964848459	e5d5c576-ae35-43de-a67a-40f56ae598d0	Customer	Customer/Client	With this classifier are classified the customers.	ec5fde4c-9918-4f45-8f79-23142298ddb4
4aa6dcee-8778-45fa-b3da-5b20f89ab42c	e5d5c576-ae35-43de-a67a-40f56ae598d0	Customs	Customs	Customs	ec5fde4c-9918-4f45-8f79-23142298ddb4
a682b82b-6be9-4ad7-9c14-a1b6dced0b6c	e5d5c576-ae35-43de-a67a-40f56ae598d0	Dentist	Dentist	Dentist	ec5fde4c-9918-4f45-8f79-23142298ddb4
1f1c1d12-c3e2-4a5d-9f1b-1aa115be7028	e5d5c576-ae35-43de-a67a-40f56ae598d0	DoctorOfMedicine	Doctor of Medicine	Doctor of Medicine	ec5fde4c-9918-4f45-8f79-23142298ddb4
dc4f88bd-8b7d-4356-a332-de19cd5d0870	e5d5c576-ae35-43de-a67a-40f56ae598d0	Employee	Employee	Employee	ec5fde4c-9918-4f45-8f79-23142298ddb4
cf4f3b34-51b9-44ff-86d8-e1848538a59b	e5d5c576-ae35-43de-a67a-40f56ae598d0	Entertainment	Entertainment	Entertainment	ec5fde4c-9918-4f45-8f79-23142298ddb4
2a694fa5-4bfc-4483-9f92-b82b8348158b	e5d5c576-ae35-43de-a67a-40f56ae598d0	HealthInsuranceFund	Health Insurance Fund	Health Insurance Fund	ec5fde4c-9918-4f45-8f79-23142298ddb4
044770d4-8551-4812-8ca7-15e4ac9f8a34	e5d5c576-ae35-43de-a67a-40f56ae598d0	HighSchool	High School	High School	ec5fde4c-9918-4f45-8f79-23142298ddb4
8685a9f3-88cb-4a3c-a93f-85e113e07f1d	e5d5c576-ae35-43de-a67a-40f56ae598d0	HighTechnicalSchool	High Technical School	High Technical School	ec5fde4c-9918-4f45-8f79-23142298ddb4
008538aa-e24c-4900-b0e5-b92a5cd2a682	e5d5c576-ae35-43de-a67a-40f56ae598d0	Hospital	Hospital	Hospital	ec5fde4c-9918-4f45-8f79-23142298ddb4
697ee3a6-e644-490f-a40e-9fc4f6606c1e	e5d5c576-ae35-43de-a67a-40f56ae598d0	Hotel	Hotel	Hotel	ec5fde4c-9918-4f45-8f79-23142298ddb4
ccf0d12f-2782-4e74-9a10-06d4e1d1eb58	e5d5c576-ae35-43de-a67a-40f56ae598d0	HotelApartment	Hotel Apartment	Hotel Apartment	ec5fde4c-9918-4f45-8f79-23142298ddb4
cb5edd24-4c75-49a2-8417-a385e57713d1	e5d5c576-ae35-43de-a67a-40f56ae598d0	ITServiceProvider	IT Service Provider	IT Service Provider	ec5fde4c-9918-4f45-8f79-23142298ddb4
734a1045-ee6e-43e0-98ce-3e702349f68b	e5d5c576-ae35-43de-a67a-40f56ae598d0	InsuranceAgent	Insurance Agent	Insurance Agent	ec5fde4c-9918-4f45-8f79-23142298ddb4
04706977-7b4d-41bf-aff1-44211590bda8	e5d5c576-ae35-43de-a67a-40f56ae598d0	InsuranceCompany	Insurance Company	Insurance Company	ec5fde4c-9918-4f45-8f79-23142298ddb4
9dcbeaa0-1f29-4c97-a471-4edeb1dc57ea	e5d5c576-ae35-43de-a67a-40f56ae598d0	InternetServiceProvider	Internet Service Provider	Internet Service Provider	ec5fde4c-9918-4f45-8f79-23142298ddb4
1e5323b2-ee09-463d-a327-18e09414fb5d	e5d5c576-ae35-43de-a67a-40f56ae598d0	LanguageSecondarySchool	Language Secondary School	Language Secondary School	ec5fde4c-9918-4f45-8f79-23142298ddb4
564da66a-dbbb-4a5f-8435-7074366d974e	e5d5c576-ae35-43de-a67a-40f56ae598d0	Lawyer	Lawyer	Lawyer	ec5fde4c-9918-4f45-8f79-23142298ddb4
ace51a8c-53c7-49a5-86e7-b7d92e387d66	e5d5c576-ae35-43de-a67a-40f56ae598d0	MedicalCenter	Medical Center	Medical Center	ec5fde4c-9918-4f45-8f79-23142298ddb4
347e0a6e-c6dd-4905-bd9c-53600651c464	e5d5c576-ae35-43de-a67a-40f56ae598d0	Municipality	Municipality	Municipality	ec5fde4c-9918-4f45-8f79-23142298ddb4
de192000-2180-4089-b337-dbc0e5c2907a	e5d5c576-ae35-43de-a67a-40f56ae598d0	NationalHealthInsuranceFund	National Health Insurance Fund	National Health Insurance Fund	ec5fde4c-9918-4f45-8f79-23142298ddb4
58f8090e-3acc-4417-9254-7d857101cd03	e5d5c576-ae35-43de-a67a-40f56ae598d0	NationalRevenueAgency	National Revenue Agency	National Revenue Agency	ec5fde4c-9918-4f45-8f79-23142298ddb4
69106cab-32ae-4f30-b485-42abc393b1ad	e5d5c576-ae35-43de-a67a-40f56ae598d0	NationalSocialSecurityAgency	National Social Security Agency	National Social Security Agency	ec5fde4c-9918-4f45-8f79-23142298ddb4
be7514e2-0b0e-4220-a872-35379870871a	e5d5c576-ae35-43de-a67a-40f56ae598d0	Passport Office	Passport Office	Passport Office	ec5fde4c-9918-4f45-8f79-23142298ddb4
7af4dd72-6304-487c-b0ff-97c0aad05fb8	e5d5c576-ae35-43de-a67a-40f56ae598d0	PensionFund	Pension Fund	Pension Fund	ec5fde4c-9918-4f45-8f79-23142298ddb4
26fe890a-91ea-4902-bfbb-6586057ae748	e5d5c576-ae35-43de-a67a-40f56ae598d0	Police	Police	Police	ec5fde4c-9918-4f45-8f79-23142298ddb4
5412d16f-34c4-411c-9766-6b9146b8f255	e5d5c576-ae35-43de-a67a-40f56ae598d0	Polyclinic	Polyclinic	Polyclinic	ec5fde4c-9918-4f45-8f79-23142298ddb4
5ce0587e-de8f-493a-9d9b-a66ce9932b77	e5d5c576-ae35-43de-a67a-40f56ae598d0	PrimarySchool	Primary School	Primary School	ec5fde4c-9918-4f45-8f79-23142298ddb4
abb0251e-24aa-4a95-81d3-4ef1e4a7e7bb	e5d5c576-ae35-43de-a67a-40f56ae598d0	Producer	Producer	Producer	ec5fde4c-9918-4f45-8f79-23142298ddb4
72115fc2-f0b7-43fb-8972-e3b61fbc617e	e5d5c576-ae35-43de-a67a-40f56ae598d0	Prosecutor	Prosecutor	Prosecutor	ec5fde4c-9918-4f45-8f79-23142298ddb4
e0d2f909-c86a-41f3-aa4a-b0cf25d7feda	e5d5c576-ae35-43de-a67a-40f56ae598d0	RealEstateAgency	Real Estate Agency	Real Estate Agency	ec5fde4c-9918-4f45-8f79-23142298ddb4
d914368c-1747-43db-a5ac-d97cb6913783	e5d5c576-ae35-43de-a67a-40f56ae598d0	RegistryAgency	Registry Agency	Registry Agency	ec5fde4c-9918-4f45-8f79-23142298ddb4
9bbca90c-5e8b-43fe-9e5f-4354d7923de2	e5d5c576-ae35-43de-a67a-40f56ae598d0	RentACar	Rent a Car	Rent a Car	ec5fde4c-9918-4f45-8f79-23142298ddb4
13edd393-97fa-48a2-b472-7412d80a6c58	e5d5c576-ae35-43de-a67a-40f56ae598d0	SecondarySchool	Secondary School	Secondary School	ec5fde4c-9918-4f45-8f79-23142298ddb4
b7db351a-c2f9-46a4-9d2b-3dd16a5751d6	e5d5c576-ae35-43de-a67a-40f56ae598d0	ServiceProvider	Service Provider	Service Provider	ec5fde4c-9918-4f45-8f79-23142298ddb4
0a267a2f-0862-413f-a37e-3260b38753d1	e5d5c576-ae35-43de-a67a-40f56ae598d0	ShippingAgent	Shipping Agent	Shipping Agent	ec5fde4c-9918-4f45-8f79-23142298ddb4
f5ad83b9-9373-4055-9adb-d9bca514eab3	e5d5c576-ae35-43de-a67a-40f56ae598d0	SolicitorAssociation	Solicitor Association	Solicitor Association	ec5fde4c-9918-4f45-8f79-23142298ddb4
ec29ed10-36fe-454f-8cc6-7521b003151b	e5d5c576-ae35-43de-a67a-40f56ae598d0	Supplier	Supplier	Supplier	ec5fde4c-9918-4f45-8f79-23142298ddb4
3334eede-0bb5-4e3e-8836-96ec68b0d903	e5d5c576-ae35-43de-a67a-40f56ae598d0	TechnicalUniversity	Technical University	Technical University	ec5fde4c-9918-4f45-8f79-23142298ddb4
a74f0887-6545-4b08-82c4-81cdae01b0a8	e5d5c576-ae35-43de-a67a-40f56ae598d0	University	University	University	ec5fde4c-9918-4f45-8f79-23142298ddb4
51d97852-7225-4268-8c84-8abf1e97f909	c2228a74-4879-4338-9a6b-d92df52bffc9	AdvertisementAgency	Advertisement Agency	Advertisement Agency	b794bb09-ff92-4719-b778-523882a926ff
38062806-23cd-4f8d-9a18-e80bb5854d38	c2228a74-4879-4338-9a6b-d92df52bffc9	Bank	Bank	Bank	b794bb09-ff92-4719-b778-523882a926ff
be287249-351c-4fe4-bfc7-5633232fe705	c2228a74-4879-4338-9a6b-d92df52bffc9	Cashier	Cashier	Cashier	b794bb09-ff92-4719-b778-523882a926ff
1ffe0935-ffb1-4120-af98-ac9cb19c2923	c2228a74-4879-4338-9a6b-d92df52bffc9	College	College	College	b794bb09-ff92-4719-b778-523882a926ff
3060c25b-6ff8-43c4-9e55-2cec55182d1d	c2228a74-4879-4338-9a6b-d92df52bffc9	Courier	Courier	Courier	b794bb09-ff92-4719-b778-523882a926ff
bbffd6e8-9089-4a44-a4d8-cd9840440299	c2228a74-4879-4338-9a6b-d92df52bffc9	Court	Court	Court	b794bb09-ff92-4719-b778-523882a926ff
3a97750e-8fa7-4cd0-904a-5000821b922e	c2228a74-4879-4338-9a6b-d92df52bffc9	Customer	Customer/Client	With this classifier are classified the customers.	b794bb09-ff92-4719-b778-523882a926ff
dd8f5d16-1255-4279-a36e-1faa839fd081	c2228a74-4879-4338-9a6b-d92df52bffc9	Customs	Customs	Customs	b794bb09-ff92-4719-b778-523882a926ff
ee181178-52ec-4d1b-9b3b-456ea55afdf2	c2228a74-4879-4338-9a6b-d92df52bffc9	Dentist	Dentist	Dentist	b794bb09-ff92-4719-b778-523882a926ff
83360174-d5ab-4b07-ab87-276da300a78d	c2228a74-4879-4338-9a6b-d92df52bffc9	DoctorOfMedicine	Doctor of Medicine	Doctor of Medicine	b794bb09-ff92-4719-b778-523882a926ff
d231d9ef-a4e4-4d83-bdb3-b2d1711bd5c9	c2228a74-4879-4338-9a6b-d92df52bffc9	Employee	Employee	Employee	b794bb09-ff92-4719-b778-523882a926ff
04a6bf78-79d1-4bbb-aade-f30e9a51db53	c2228a74-4879-4338-9a6b-d92df52bffc9	Entertainment	Entertainment	Entertainment	b794bb09-ff92-4719-b778-523882a926ff
1d436c3a-d158-4fbe-a087-bb4b2703cd75	c2228a74-4879-4338-9a6b-d92df52bffc9	HealthInsuranceFund	Health Insurance Fund	Health Insurance Fund	b794bb09-ff92-4719-b778-523882a926ff
8786f270-d4d9-4d0c-8d0c-fedcdc5d7af2	c2228a74-4879-4338-9a6b-d92df52bffc9	HighSchool	High School	High School	b794bb09-ff92-4719-b778-523882a926ff
b015231c-2894-4da9-8a28-e46d0aeede95	c2228a74-4879-4338-9a6b-d92df52bffc9	HighTechnicalSchool	High Technical School	High Technical School	b794bb09-ff92-4719-b778-523882a926ff
f22b537e-10e0-4bc4-9baf-d41cd85ef3d0	c2228a74-4879-4338-9a6b-d92df52bffc9	Hospital	Hospital	Hospital	b794bb09-ff92-4719-b778-523882a926ff
0ba75bb9-1838-44a9-85f7-624e0b9ac9c8	c2228a74-4879-4338-9a6b-d92df52bffc9	Hotel	Hotel	Hotel	b794bb09-ff92-4719-b778-523882a926ff
6ff6e4f1-89c2-4536-8f2d-9464ec9047a3	c2228a74-4879-4338-9a6b-d92df52bffc9	HotelApartment	Hotel Apartment	Hotel Apartment	b794bb09-ff92-4719-b778-523882a926ff
502b9462-d9fb-42c9-ae8c-b804c420c0a1	c2228a74-4879-4338-9a6b-d92df52bffc9	ITServiceProvider	IT Service Provider	IT Service Provider	b794bb09-ff92-4719-b778-523882a926ff
14d8c929-65e7-4265-9fed-29a35d0f88ce	c2228a74-4879-4338-9a6b-d92df52bffc9	InsuranceAgent	Insurance Agent	Insurance Agent	b794bb09-ff92-4719-b778-523882a926ff
c5009d26-a083-46d0-a029-bd9c805ed573	c2228a74-4879-4338-9a6b-d92df52bffc9	InsuranceCompany	Insurance Company	Insurance Company	b794bb09-ff92-4719-b778-523882a926ff
df9d5f2d-f917-43cb-a1ab-1465fae6058b	c2228a74-4879-4338-9a6b-d92df52bffc9	InternetServiceProvider	Internet Service Provider	Internet Service Provider	b794bb09-ff92-4719-b778-523882a926ff
66cb408e-72e9-4d8d-9292-3602b2082640	c2228a74-4879-4338-9a6b-d92df52bffc9	LanguageSecondarySchool	Language Secondary School	Language Secondary School	b794bb09-ff92-4719-b778-523882a926ff
64e8807d-d75d-47fc-a458-a66fb3ccf89f	c2228a74-4879-4338-9a6b-d92df52bffc9	Lawyer	Lawyer	Lawyer	b794bb09-ff92-4719-b778-523882a926ff
a72e53ba-a3e4-4f0e-ac8d-ee0585ec93fc	c2228a74-4879-4338-9a6b-d92df52bffc9	MedicalCenter	Medical Center	Medical Center	b794bb09-ff92-4719-b778-523882a926ff
12cc7898-e946-48d1-bfae-a4ce5bd2b215	c2228a74-4879-4338-9a6b-d92df52bffc9	Municipality	Municipality	Municipality	b794bb09-ff92-4719-b778-523882a926ff
19efdc59-9698-4112-bbab-1bc83029afae	c2228a74-4879-4338-9a6b-d92df52bffc9	NationalHealthInsuranceFund	National Health Insurance Fund	National Health Insurance Fund	b794bb09-ff92-4719-b778-523882a926ff
127995ff-e57b-499c-839f-aa896d499f6e	c2228a74-4879-4338-9a6b-d92df52bffc9	NationalRevenueAgency	National Revenue Agency	National Revenue Agency	b794bb09-ff92-4719-b778-523882a926ff
6cf566cb-6e28-477a-b9b4-5b9b0816bcc8	c2228a74-4879-4338-9a6b-d92df52bffc9	NationalSocialSecurityAgency	National Social Security Agency	National Social Security Agency	b794bb09-ff92-4719-b778-523882a926ff
af075212-bc2f-4085-a96b-c18e3ce3ac9e	c2228a74-4879-4338-9a6b-d92df52bffc9	Passport Office	Passport Office	Passport Office	b794bb09-ff92-4719-b778-523882a926ff
9bddf071-e110-4b62-aac9-58d212ee0109	c2228a74-4879-4338-9a6b-d92df52bffc9	PensionFund	Pension Fund	Pension Fund	b794bb09-ff92-4719-b778-523882a926ff
49bf2848-00c8-4eba-b123-cc7671d8f5ad	c2228a74-4879-4338-9a6b-d92df52bffc9	Police	Police	Police	b794bb09-ff92-4719-b778-523882a926ff
30891bc1-add2-4684-b251-465201b24215	c2228a74-4879-4338-9a6b-d92df52bffc9	Polyclinic	Polyclinic	Polyclinic	b794bb09-ff92-4719-b778-523882a926ff
11f4f474-60b8-40e7-9aa3-8270f6553bf7	c2228a74-4879-4338-9a6b-d92df52bffc9	PrimarySchool	Primary School	Primary School	b794bb09-ff92-4719-b778-523882a926ff
8f0d3ad7-6b36-4cff-9329-50723a758c6d	c2228a74-4879-4338-9a6b-d92df52bffc9	Producer	Producer	Producer	b794bb09-ff92-4719-b778-523882a926ff
ff159a01-8555-4d02-bb61-e8ab0ebedfd4	c2228a74-4879-4338-9a6b-d92df52bffc9	Prosecutor	Prosecutor	Prosecutor	b794bb09-ff92-4719-b778-523882a926ff
12ee5d64-5fb0-4abf-8696-64b9cf7f8433	c2228a74-4879-4338-9a6b-d92df52bffc9	RealEstateAgency	Real Estate Agency	Real Estate Agency	b794bb09-ff92-4719-b778-523882a926ff
424f3f09-a537-4fc2-902d-ac07a722cf67	c2228a74-4879-4338-9a6b-d92df52bffc9	RegistryAgency	Registry Agency	Registry Agency	b794bb09-ff92-4719-b778-523882a926ff
06215ac9-bae3-494d-9a54-b6ec16ae9db5	c2228a74-4879-4338-9a6b-d92df52bffc9	RentACar	Rent a Car	Rent a Car	b794bb09-ff92-4719-b778-523882a926ff
a15906e2-be6a-407b-bc64-462ff3f32312	c2228a74-4879-4338-9a6b-d92df52bffc9	SecondarySchool	Secondary School	Secondary School	b794bb09-ff92-4719-b778-523882a926ff
ee849b35-a17f-4aa9-a9d5-70eb0bfa151c	c2228a74-4879-4338-9a6b-d92df52bffc9	ServiceProvider	Service Provider	Service Provider	b794bb09-ff92-4719-b778-523882a926ff
bf8840ae-618d-4082-8d3a-2f387435b02c	c2228a74-4879-4338-9a6b-d92df52bffc9	ShippingAgent	Shipping Agent	Shipping Agent	b794bb09-ff92-4719-b778-523882a926ff
ae2880ba-929d-4cc0-b776-f32cbc6fd85c	c2228a74-4879-4338-9a6b-d92df52bffc9	SolicitorAssociation	Solicitor Association	Solicitor Association	b794bb09-ff92-4719-b778-523882a926ff
f98d6d2f-7ab4-43c5-b2b7-925906c6cef1	c2228a74-4879-4338-9a6b-d92df52bffc9	Supplier	Supplier	Supplier	b794bb09-ff92-4719-b778-523882a926ff
4244e3a5-c462-4e37-a98a-e7d476a713c6	c2228a74-4879-4338-9a6b-d92df52bffc9	TechnicalUniversity	Technical University	Technical University	b794bb09-ff92-4719-b778-523882a926ff
b870308f-3a00-44c5-8559-b09f765f8db0	c2228a74-4879-4338-9a6b-d92df52bffc9	University	University	University	b794bb09-ff92-4719-b778-523882a926ff
8a7f061b-f5b4-4287-9efd-25f7303ce2bb	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	AdvertisementAgency	Advertisement Agency	Advertisement Agency	322dedd6-18bd-4da9-ab38-7ff101f2f033
e0aaa7b8-ab30-4b4d-991e-68f5f256d94d	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	Bank	Bank	Bank	322dedd6-18bd-4da9-ab38-7ff101f2f033
89b00415-6c91-47ee-b1d0-4de4d19680e7	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	Cashier	Cashier	Cashier	322dedd6-18bd-4da9-ab38-7ff101f2f033
c408185d-9f11-45e1-bc20-49a8dff9ae65	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	College	College	College	322dedd6-18bd-4da9-ab38-7ff101f2f033
9d703240-265e-4037-8646-f10773a0322c	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	Courier	Courier	Courier	322dedd6-18bd-4da9-ab38-7ff101f2f033
c3574a06-7868-4115-809c-1c31fe370bd4	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	Court	Court	Court	322dedd6-18bd-4da9-ab38-7ff101f2f033
94f63f2e-7f4d-4c57-87c3-126f65da05f6	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	Customer	Customer/Client	With this classifier are classified the customers.	322dedd6-18bd-4da9-ab38-7ff101f2f033
7c360975-d23d-4203-be0b-1eff6f498b0b	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	Customs	Customs	Customs	322dedd6-18bd-4da9-ab38-7ff101f2f033
01f313e9-669b-4c50-96fe-7d31ec09a09a	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	Dentist	Dentist	Dentist	322dedd6-18bd-4da9-ab38-7ff101f2f033
c40b1d60-c1a1-4f81-862f-4520b479a1cd	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	DoctorOfMedicine	Doctor of Medicine	Doctor of Medicine	322dedd6-18bd-4da9-ab38-7ff101f2f033
0c3e14c8-72a0-41cd-917b-45d5d14c4d8e	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	Employee	Employee	Employee	322dedd6-18bd-4da9-ab38-7ff101f2f033
7d1b4dae-b522-4574-b53e-2eb605592f12	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	Entertainment	Entertainment	Entertainment	322dedd6-18bd-4da9-ab38-7ff101f2f033
7de87a70-965c-4967-81b8-1ea393dc94e4	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	HealthInsuranceFund	Health Insurance Fund	Health Insurance Fund	322dedd6-18bd-4da9-ab38-7ff101f2f033
d09a27f8-a2ca-4e28-a17f-1e3776cff38e	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	HighSchool	High School	High School	322dedd6-18bd-4da9-ab38-7ff101f2f033
e421388e-88b2-4793-9988-f0a61908f056	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	HighTechnicalSchool	High Technical School	High Technical School	322dedd6-18bd-4da9-ab38-7ff101f2f033
4d7589ef-5e2c-4d59-8a8c-c744bc704cea	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	Hospital	Hospital	Hospital	322dedd6-18bd-4da9-ab38-7ff101f2f033
86f80393-65a1-420c-942e-8231fb598cc6	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	Hotel	Hotel	Hotel	322dedd6-18bd-4da9-ab38-7ff101f2f033
9987a6af-d215-4f2a-935b-1e31c2b862a9	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	HotelApartment	Hotel Apartment	Hotel Apartment	322dedd6-18bd-4da9-ab38-7ff101f2f033
d9473655-ffcb-481a-ad3c-89c37ddc06c2	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	ITServiceProvider	IT Service Provider	IT Service Provider	322dedd6-18bd-4da9-ab38-7ff101f2f033
a6d58bcb-8cf4-4fdc-87db-e65cad8e3b6b	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	InsuranceAgent	Insurance Agent	Insurance Agent	322dedd6-18bd-4da9-ab38-7ff101f2f033
d53f5942-9ed7-45ac-95e7-fdd5f7c0d6db	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	InsuranceCompany	Insurance Company	Insurance Company	322dedd6-18bd-4da9-ab38-7ff101f2f033
eb90db9e-2564-46b6-b2c4-3f393dbaa39f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	InternetServiceProvider	Internet Service Provider	Internet Service Provider	322dedd6-18bd-4da9-ab38-7ff101f2f033
1da48613-c7d3-4731-b925-ae7c5aafe190	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	LanguageSecondarySchool	Language Secondary School	Language Secondary School	322dedd6-18bd-4da9-ab38-7ff101f2f033
921b57e0-18f0-45a8-9b1c-67c51fe6a496	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	Lawyer	Lawyer	Lawyer	322dedd6-18bd-4da9-ab38-7ff101f2f033
bcf52a6b-6b35-416a-8c43-d9e9333834ce	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	MedicalCenter	Medical Center	Medical Center	322dedd6-18bd-4da9-ab38-7ff101f2f033
59b8cc21-1adc-4efd-909c-4d805fa4bbd1	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	Municipality	Municipality	Municipality	322dedd6-18bd-4da9-ab38-7ff101f2f033
93562511-2a48-48fa-9597-0e022d2b7274	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	NationalHealthInsuranceFund	National Health Insurance Fund	National Health Insurance Fund	322dedd6-18bd-4da9-ab38-7ff101f2f033
b6d6e889-b298-4103-b00d-c549fa9c4ea0	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	NationalRevenueAgency	National Revenue Agency	National Revenue Agency	322dedd6-18bd-4da9-ab38-7ff101f2f033
d320456a-ccd9-4317-bf45-cf81ecbb9de5	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	NationalSocialSecurityAgency	National Social Security Agency	National Social Security Agency	322dedd6-18bd-4da9-ab38-7ff101f2f033
c2af04b3-f8fb-4132-b24b-3526c0b80ad4	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	Passport Office	Passport Office	Passport Office	322dedd6-18bd-4da9-ab38-7ff101f2f033
7c145414-97ff-4743-ab4f-5a36eef3b8e5	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	PensionFund	Pension Fund	Pension Fund	322dedd6-18bd-4da9-ab38-7ff101f2f033
372f7fb8-660e-4e17-998c-625a6e1461f8	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	Police	Police	Police	322dedd6-18bd-4da9-ab38-7ff101f2f033
96d38279-4aa0-44e6-8b32-e98f21bbcf90	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	Polyclinic	Polyclinic	Polyclinic	322dedd6-18bd-4da9-ab38-7ff101f2f033
4dbab55f-e659-4e85-8e18-92cd328ea3fb	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	PrimarySchool	Primary School	Primary School	322dedd6-18bd-4da9-ab38-7ff101f2f033
85e90f74-18cc-4332-b518-d9177d2ed1a4	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	Producer	Producer	Producer	322dedd6-18bd-4da9-ab38-7ff101f2f033
07a3f8cd-fb3d-4e78-8ce5-e97de86eb62c	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	Prosecutor	Prosecutor	Prosecutor	322dedd6-18bd-4da9-ab38-7ff101f2f033
76edfbb4-dc68-44e0-94b3-a02c97b252fe	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	RealEstateAgency	Real Estate Agency	Real Estate Agency	322dedd6-18bd-4da9-ab38-7ff101f2f033
d67e25ea-dc6a-4a65-bc1f-aa2738056fd2	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	RegistryAgency	Registry Agency	Registry Agency	322dedd6-18bd-4da9-ab38-7ff101f2f033
56f2ad33-86bb-47f4-8d31-d5cda5343171	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	RentACar	Rent a Car	Rent a Car	322dedd6-18bd-4da9-ab38-7ff101f2f033
658c1ed4-b0d9-41ad-8b7f-3e4ac549db8e	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	SecondarySchool	Secondary School	Secondary School	322dedd6-18bd-4da9-ab38-7ff101f2f033
35cc7bec-0d66-4e6f-84f4-7f021bc776e6	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	ServiceProvider	Service Provider	Service Provider	322dedd6-18bd-4da9-ab38-7ff101f2f033
ce110f75-a6aa-49a8-a9d3-4c6fb194298b	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	ShippingAgent	Shipping Agent	Shipping Agent	322dedd6-18bd-4da9-ab38-7ff101f2f033
2393c53c-0394-4a2c-aac9-d9a402596799	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	SolicitorAssociation	Solicitor Association	Solicitor Association	322dedd6-18bd-4da9-ab38-7ff101f2f033
1dc87923-c4da-4083-92ca-0e2cdc6439db	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	Supplier	Supplier	Supplier	322dedd6-18bd-4da9-ab38-7ff101f2f033
246def71-6eb7-46ef-a739-6622e6301d3f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	TechnicalUniversity	Technical University	Technical University	322dedd6-18bd-4da9-ab38-7ff101f2f033
ab6fe3f7-d3a2-4c0f-a16d-168307d28be7	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	University	University	University	322dedd6-18bd-4da9-ab38-7ff101f2f033
c2ecba82-7ca1-42ab-ba65-7988b69545ff	17d7af2a-7efd-4443-9ffb-08636a8253c0	AdvertisementAgency	Advertisement Agency	Advertisement Agency	a6ad959e-74d2-4876-add3-a970476e6281
3c5a3d17-b125-4def-994f-f2c54cfe7f32	17d7af2a-7efd-4443-9ffb-08636a8253c0	Bank	Bank	Bank	a6ad959e-74d2-4876-add3-a970476e6281
50e682de-1df1-43eb-90fa-8e088cf67bf4	17d7af2a-7efd-4443-9ffb-08636a8253c0	Cashier	Cashier	Cashier	a6ad959e-74d2-4876-add3-a970476e6281
72059b0d-6f7c-427a-b9c9-e3babdf62768	17d7af2a-7efd-4443-9ffb-08636a8253c0	College	College	College	a6ad959e-74d2-4876-add3-a970476e6281
3310f0d4-7630-4bb3-8dcc-c4b5f862dab2	17d7af2a-7efd-4443-9ffb-08636a8253c0	Courier	Courier	Courier	a6ad959e-74d2-4876-add3-a970476e6281
04a3089e-86c0-4f5c-93aa-94de2862589b	17d7af2a-7efd-4443-9ffb-08636a8253c0	Court	Court	Court	a6ad959e-74d2-4876-add3-a970476e6281
c0d5765e-0caa-4b6c-886b-b36f9422d809	17d7af2a-7efd-4443-9ffb-08636a8253c0	Customer	Customer/Client	With this classifier are classified the customers.	a6ad959e-74d2-4876-add3-a970476e6281
d1cc91d0-17cd-4baf-9a33-94a40e7d0933	17d7af2a-7efd-4443-9ffb-08636a8253c0	Customs	Customs	Customs	a6ad959e-74d2-4876-add3-a970476e6281
a87788ec-9738-4868-b5bd-6b06034e0c99	17d7af2a-7efd-4443-9ffb-08636a8253c0	Dentist	Dentist	Dentist	a6ad959e-74d2-4876-add3-a970476e6281
5778185d-abd4-4a78-8715-894a38f140f4	17d7af2a-7efd-4443-9ffb-08636a8253c0	DoctorOfMedicine	Doctor of Medicine	Doctor of Medicine	a6ad959e-74d2-4876-add3-a970476e6281
4abe87ea-17d1-438d-9bb3-754ebeea61d2	17d7af2a-7efd-4443-9ffb-08636a8253c0	Employee	Employee	Employee	a6ad959e-74d2-4876-add3-a970476e6281
00b70052-975d-4d28-a415-976d8f9aada5	17d7af2a-7efd-4443-9ffb-08636a8253c0	Entertainment	Entertainment	Entertainment	a6ad959e-74d2-4876-add3-a970476e6281
e0f2c60c-210e-414c-9d2a-f796e4744499	17d7af2a-7efd-4443-9ffb-08636a8253c0	HealthInsuranceFund	Health Insurance Fund	Health Insurance Fund	a6ad959e-74d2-4876-add3-a970476e6281
91c43fd5-dade-4bb5-bf55-ac9fe5e3b1a9	17d7af2a-7efd-4443-9ffb-08636a8253c0	HighSchool	High School	High School	a6ad959e-74d2-4876-add3-a970476e6281
2aba0f4a-4450-43e1-ab16-e236c01beda3	17d7af2a-7efd-4443-9ffb-08636a8253c0	HighTechnicalSchool	High Technical School	High Technical School	a6ad959e-74d2-4876-add3-a970476e6281
9b25d741-2e29-4566-bafb-603042212764	17d7af2a-7efd-4443-9ffb-08636a8253c0	Hospital	Hospital	Hospital	a6ad959e-74d2-4876-add3-a970476e6281
d09f2cd7-146d-49a5-95de-da3eedd3de46	17d7af2a-7efd-4443-9ffb-08636a8253c0	Hotel	Hotel	Hotel	a6ad959e-74d2-4876-add3-a970476e6281
e85db78a-9dc1-4633-b7ba-09f41652b7ef	17d7af2a-7efd-4443-9ffb-08636a8253c0	HotelApartment	Hotel Apartment	Hotel Apartment	a6ad959e-74d2-4876-add3-a970476e6281
46b8eb7a-6243-4ca0-bdfc-459631109a45	17d7af2a-7efd-4443-9ffb-08636a8253c0	ITServiceProvider	IT Service Provider	IT Service Provider	a6ad959e-74d2-4876-add3-a970476e6281
e9b818f3-b0a4-471f-abe8-e60a021eaae1	17d7af2a-7efd-4443-9ffb-08636a8253c0	InsuranceAgent	Insurance Agent	Insurance Agent	a6ad959e-74d2-4876-add3-a970476e6281
b269e34b-fbd8-47b5-bd89-81e446e41fbc	17d7af2a-7efd-4443-9ffb-08636a8253c0	InsuranceCompany	Insurance Company	Insurance Company	a6ad959e-74d2-4876-add3-a970476e6281
b4d34f26-638c-4c8e-946d-ba4387b81294	17d7af2a-7efd-4443-9ffb-08636a8253c0	InternetServiceProvider	Internet Service Provider	Internet Service Provider	a6ad959e-74d2-4876-add3-a970476e6281
efd68ce0-96f4-4e0f-a364-dee453c61b80	17d7af2a-7efd-4443-9ffb-08636a8253c0	LanguageSecondarySchool	Language Secondary School	Language Secondary School	a6ad959e-74d2-4876-add3-a970476e6281
1e6d61ee-2146-43e2-a526-5ab62eeedb54	17d7af2a-7efd-4443-9ffb-08636a8253c0	Lawyer	Lawyer	Lawyer	a6ad959e-74d2-4876-add3-a970476e6281
fd0775cc-d9a6-4c55-aab5-5d3240c47053	17d7af2a-7efd-4443-9ffb-08636a8253c0	MedicalCenter	Medical Center	Medical Center	a6ad959e-74d2-4876-add3-a970476e6281
1fc538e2-ad4b-49b7-ba4f-f16a99987a61	17d7af2a-7efd-4443-9ffb-08636a8253c0	Municipality	Municipality	Municipality	a6ad959e-74d2-4876-add3-a970476e6281
f9ac5ccd-6d16-402c-b409-da19080829e3	17d7af2a-7efd-4443-9ffb-08636a8253c0	NationalHealthInsuranceFund	National Health Insurance Fund	National Health Insurance Fund	a6ad959e-74d2-4876-add3-a970476e6281
567d7d8d-0e68-4a8d-ad71-85ae7ac3190b	17d7af2a-7efd-4443-9ffb-08636a8253c0	NationalRevenueAgency	National Revenue Agency	National Revenue Agency	a6ad959e-74d2-4876-add3-a970476e6281
40a6eb2d-fe42-43da-9ddf-f31562f60900	17d7af2a-7efd-4443-9ffb-08636a8253c0	NationalSocialSecurityAgency	National Social Security Agency	National Social Security Agency	a6ad959e-74d2-4876-add3-a970476e6281
b86399a2-61f5-4515-952e-81d7c57f3aa3	17d7af2a-7efd-4443-9ffb-08636a8253c0	Passport Office	Passport Office	Passport Office	a6ad959e-74d2-4876-add3-a970476e6281
750c9543-f618-4496-b9d6-d7b38d63e34d	17d7af2a-7efd-4443-9ffb-08636a8253c0	PensionFund	Pension Fund	Pension Fund	a6ad959e-74d2-4876-add3-a970476e6281
cf67051f-c986-4946-9cb1-e8423df8dfb8	17d7af2a-7efd-4443-9ffb-08636a8253c0	Police	Police	Police	a6ad959e-74d2-4876-add3-a970476e6281
65d52a98-6c98-4ae6-b065-52b6c1a620b5	17d7af2a-7efd-4443-9ffb-08636a8253c0	Polyclinic	Polyclinic	Polyclinic	a6ad959e-74d2-4876-add3-a970476e6281
9cac7e73-1eb4-4cd4-89b0-0aeebbf68824	17d7af2a-7efd-4443-9ffb-08636a8253c0	PrimarySchool	Primary School	Primary School	a6ad959e-74d2-4876-add3-a970476e6281
2fedf216-d607-4297-95be-f5ac482cba3b	17d7af2a-7efd-4443-9ffb-08636a8253c0	Producer	Producer	Producer	a6ad959e-74d2-4876-add3-a970476e6281
a0ca49fc-5d19-4e5e-840e-c0eea1325a7a	17d7af2a-7efd-4443-9ffb-08636a8253c0	Prosecutor	Prosecutor	Prosecutor	a6ad959e-74d2-4876-add3-a970476e6281
54df6e19-a323-4675-875c-9721d06b5b7b	17d7af2a-7efd-4443-9ffb-08636a8253c0	RealEstateAgency	Real Estate Agency	Real Estate Agency	a6ad959e-74d2-4876-add3-a970476e6281
eb0a0139-0488-4fce-b0c1-f8709f6d9165	17d7af2a-7efd-4443-9ffb-08636a8253c0	RegistryAgency	Registry Agency	Registry Agency	a6ad959e-74d2-4876-add3-a970476e6281
f54c975b-5a12-4c96-aabb-ed17fcf951c2	17d7af2a-7efd-4443-9ffb-08636a8253c0	RentACar	Rent a Car	Rent a Car	a6ad959e-74d2-4876-add3-a970476e6281
1b4e76e2-48ed-4938-b297-767d04db0bad	17d7af2a-7efd-4443-9ffb-08636a8253c0	SecondarySchool	Secondary School	Secondary School	a6ad959e-74d2-4876-add3-a970476e6281
0bc30854-d46b-4b93-a52b-e38cdd53fdba	17d7af2a-7efd-4443-9ffb-08636a8253c0	ServiceProvider	Service Provider	Service Provider	a6ad959e-74d2-4876-add3-a970476e6281
61da4559-64da-41c9-bf09-a848abdabd0d	17d7af2a-7efd-4443-9ffb-08636a8253c0	ShippingAgent	Shipping Agent	Shipping Agent	a6ad959e-74d2-4876-add3-a970476e6281
bf2293f2-4592-4e4f-ac1f-0634ae2d6100	17d7af2a-7efd-4443-9ffb-08636a8253c0	SolicitorAssociation	Solicitor Association	Solicitor Association	a6ad959e-74d2-4876-add3-a970476e6281
177e1147-b46f-42a2-a2db-60c3d2a85170	17d7af2a-7efd-4443-9ffb-08636a8253c0	Supplier	Supplier	Supplier	a6ad959e-74d2-4876-add3-a970476e6281
cce5d543-1222-4194-925b-a108cabc45b7	17d7af2a-7efd-4443-9ffb-08636a8253c0	TechnicalUniversity	Technical University	Technical University	a6ad959e-74d2-4876-add3-a970476e6281
b2cb3b2b-032e-42a9-bef6-9f2eb300b1d4	17d7af2a-7efd-4443-9ffb-08636a8253c0	University	University	University	a6ad959e-74d2-4876-add3-a970476e6281
126abc97-671e-4769-bfb5-6dd47bb011f3	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	AdvertisementAgency	Advertisement Agency	Advertisement Agency	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
2e654e9d-ae6c-4510-b3f6-3528a294a1b6	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	Bank	Bank	Bank	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
d87d366f-db56-4a41-9663-96d6ef7c7446	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	Cashier	Cashier	Cashier	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
dc6b55ba-390d-4787-b764-42d38eb2120f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	College	College	College	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
5125c029-1aa4-49a3-a31d-b3e6de58b14b	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	Courier	Courier	Courier	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
e3a779f3-05fe-41d3-89af-2274bdad2e35	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	Court	Court	Court	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
6588836d-497e-433e-8768-f596e35da886	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	Customer	Customer/Client	With this classifier are classified the customers.	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
b17e8a26-f588-4d10-9587-29284e15e302	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	Customs	Customs	Customs	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
1a683908-592c-4640-8153-acf94da4e510	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	Dentist	Dentist	Dentist	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
828bcc53-02b9-4d6c-89fe-0262267cfbd1	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	DoctorOfMedicine	Doctor of Medicine	Doctor of Medicine	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
93d46465-0f18-487d-88fb-72d425c74c75	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	Employee	Employee	Employee	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
9ea9c140-2d75-422f-8bbd-e57282231284	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	Entertainment	Entertainment	Entertainment	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
e1b511f6-5c13-45e9-8488-76a464258958	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	HealthInsuranceFund	Health Insurance Fund	Health Insurance Fund	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
619d1c82-9f01-4f4a-bb5e-9c24b8256790	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	HighSchool	High School	High School	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
3ae2104b-b16d-4e3b-8628-08f18f78b558	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	HighTechnicalSchool	High Technical School	High Technical School	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
e44496fc-3dd4-4bb2-a46f-56207abcb2d6	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	Hospital	Hospital	Hospital	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
0e326303-13e5-4559-b6ec-d7d7118e20df	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	Hotel	Hotel	Hotel	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
5b4b8d54-e893-4c1a-aa45-db2612034d41	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	HotelApartment	Hotel Apartment	Hotel Apartment	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
b9e20c0a-d114-4b7e-ad47-52d13228dd70	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	ITServiceProvider	IT Service Provider	IT Service Provider	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
90b044e5-16c6-4981-81e7-6959b900c229	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	InsuranceAgent	Insurance Agent	Insurance Agent	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
b860975a-8a80-45aa-b5f9-081cf02e7f64	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	InsuranceCompany	Insurance Company	Insurance Company	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
14a680f9-8361-4be2-91c6-fce671786471	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	InternetServiceProvider	Internet Service Provider	Internet Service Provider	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
836dc28d-9437-460b-af1f-688332f0cee8	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	LanguageSecondarySchool	Language Secondary School	Language Secondary School	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
97db7a9b-ffe4-49f0-9503-bc37c45fcdcb	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	Lawyer	Lawyer	Lawyer	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
e7caccf7-dcde-4776-b213-f4f5721ff2d6	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	MedicalCenter	Medical Center	Medical Center	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
632b18bc-2b67-4600-b2a5-8ba33d78eae2	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	Municipality	Municipality	Municipality	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
7051d328-3f31-4c90-9e16-47ce7ef180e7	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	NationalHealthInsuranceFund	National Health Insurance Fund	National Health Insurance Fund	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
8b831094-f88c-43b6-b458-32767f1964fc	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	NationalRevenueAgency	National Revenue Agency	National Revenue Agency	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
035f3c74-e132-4394-95c4-f2fcebc43483	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	NationalSocialSecurityAgency	National Social Security Agency	National Social Security Agency	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
8413e24a-0ec8-4e56-9878-924775ae7774	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	Passport Office	Passport Office	Passport Office	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
14e96bce-16c3-4449-8c0f-7ecbe1fdd739	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	PensionFund	Pension Fund	Pension Fund	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
e79df852-10cf-469e-a8c7-b0b724d40f42	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	Police	Police	Police	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
1334264e-1446-456f-9427-1e7fed3386e6	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	Polyclinic	Polyclinic	Polyclinic	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
77542b66-36f8-4788-b0ee-f8f39b11053e	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	PrimarySchool	Primary School	Primary School	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
dca65005-30d4-4f96-ac74-a091d3bb26f5	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	Producer	Producer	Producer	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
e6dd79c2-88ab-4f09-a289-bcc306a626d5	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	Prosecutor	Prosecutor	Prosecutor	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
3dc365d1-849a-4fa1-bbed-bbea38577088	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	RealEstateAgency	Real Estate Agency	Real Estate Agency	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
fb6d9043-9356-4bf2-b794-bd2cf453c5da	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	RegistryAgency	Registry Agency	Registry Agency	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
ced27eca-d518-4414-b86d-6e3942b68830	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	RentACar	Rent a Car	Rent a Car	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
5e75f75b-ec6a-4a30-8825-4821a7ba9280	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	SecondarySchool	Secondary School	Secondary School	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
82e61a5a-d66a-456c-a69f-6d0131d1a739	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	ServiceProvider	Service Provider	Service Provider	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
81c5ce9d-6eff-401c-af2c-bae97c67703d	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	ShippingAgent	Shipping Agent	Shipping Agent	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
8d83ad50-f2c8-4646-8055-6dfb3963fcc0	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	SolicitorAssociation	Solicitor Association	Solicitor Association	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
389673e2-2398-4528-b7f0-a91de7057ef4	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	Supplier	Supplier	Supplier	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
64e8557f-b214-4773-9310-6b8d3d044cf1	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	TechnicalUniversity	Technical University	Technical University	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
0f36426d-85cc-4732-a66a-9d6e907e4a45	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	University	University	University	bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1
478e64cb-d3c1-4215-9fa4-7df08583e2f1	be102e1f-00f5-48b5-9cbc-3d2583527629	AdvertisementAgency	Advertisement Agency	Advertisement Agency	ba3bd467-3add-47fb-b425-f6f78734f0e1
e5571d71-a28f-412f-9600-16649fd362fb	be102e1f-00f5-48b5-9cbc-3d2583527629	Bank	Bank	Bank	ba3bd467-3add-47fb-b425-f6f78734f0e1
7a02381e-86e2-4d7f-be47-9117ec3d4057	be102e1f-00f5-48b5-9cbc-3d2583527629	Cashier	Cashier	Cashier	ba3bd467-3add-47fb-b425-f6f78734f0e1
4e6a4974-5db1-4d14-935e-1e2945064a03	be102e1f-00f5-48b5-9cbc-3d2583527629	College	College	College	ba3bd467-3add-47fb-b425-f6f78734f0e1
dc5d7727-6cdb-4668-8ad7-a286d916dac9	be102e1f-00f5-48b5-9cbc-3d2583527629	Courier	Courier	Courier	ba3bd467-3add-47fb-b425-f6f78734f0e1
6a2b6b6c-d033-4e6b-bb5f-a732299c315e	be102e1f-00f5-48b5-9cbc-3d2583527629	Court	Court	Court	ba3bd467-3add-47fb-b425-f6f78734f0e1
2997673e-8ef1-4a5b-87ff-70827afc4462	be102e1f-00f5-48b5-9cbc-3d2583527629	Customer	Customer/Client	With this classifier are classified the customers.	ba3bd467-3add-47fb-b425-f6f78734f0e1
20205a62-55a5-41a8-83c9-b8e8fe431553	be102e1f-00f5-48b5-9cbc-3d2583527629	Customs	Customs	Customs	ba3bd467-3add-47fb-b425-f6f78734f0e1
8b2acd93-58d7-49e3-a744-345341241db0	be102e1f-00f5-48b5-9cbc-3d2583527629	Dentist	Dentist	Dentist	ba3bd467-3add-47fb-b425-f6f78734f0e1
c63edb6c-9264-4731-8dfa-5e79b1a28e0e	be102e1f-00f5-48b5-9cbc-3d2583527629	DoctorOfMedicine	Doctor of Medicine	Doctor of Medicine	ba3bd467-3add-47fb-b425-f6f78734f0e1
c3753a3c-dd16-4b01-9580-1fa53bff6408	be102e1f-00f5-48b5-9cbc-3d2583527629	Employee	Employee	Employee	ba3bd467-3add-47fb-b425-f6f78734f0e1
c91f0649-c095-4cba-b2bb-ed957df27139	be102e1f-00f5-48b5-9cbc-3d2583527629	Entertainment	Entertainment	Entertainment	ba3bd467-3add-47fb-b425-f6f78734f0e1
3ffc89b6-b836-4bd2-8625-2d694b767326	be102e1f-00f5-48b5-9cbc-3d2583527629	HealthInsuranceFund	Health Insurance Fund	Health Insurance Fund	ba3bd467-3add-47fb-b425-f6f78734f0e1
8f364d33-7d9e-407d-8cb6-63fa159879f1	be102e1f-00f5-48b5-9cbc-3d2583527629	HighSchool	High School	High School	ba3bd467-3add-47fb-b425-f6f78734f0e1
17e0f059-5a02-4d2e-933a-eea29552dee1	be102e1f-00f5-48b5-9cbc-3d2583527629	HighTechnicalSchool	High Technical School	High Technical School	ba3bd467-3add-47fb-b425-f6f78734f0e1
e3c32de7-ac02-4365-a790-1b4d231eea7e	be102e1f-00f5-48b5-9cbc-3d2583527629	Hospital	Hospital	Hospital	ba3bd467-3add-47fb-b425-f6f78734f0e1
f0654d64-fbaa-489c-be38-4afedcc7f01e	be102e1f-00f5-48b5-9cbc-3d2583527629	Hotel	Hotel	Hotel	ba3bd467-3add-47fb-b425-f6f78734f0e1
f05eaa06-1f8a-4190-af9a-5341950b744a	be102e1f-00f5-48b5-9cbc-3d2583527629	HotelApartment	Hotel Apartment	Hotel Apartment	ba3bd467-3add-47fb-b425-f6f78734f0e1
88d24d5a-b737-41d7-834d-a61bad9eeb4b	be102e1f-00f5-48b5-9cbc-3d2583527629	ITServiceProvider	IT Service Provider	IT Service Provider	ba3bd467-3add-47fb-b425-f6f78734f0e1
abcafe6d-1c11-4823-a022-57e420b481eb	be102e1f-00f5-48b5-9cbc-3d2583527629	InsuranceAgent	Insurance Agent	Insurance Agent	ba3bd467-3add-47fb-b425-f6f78734f0e1
df245b92-81c6-4c1d-8d11-6056eeaa63c7	be102e1f-00f5-48b5-9cbc-3d2583527629	InsuranceCompany	Insurance Company	Insurance Company	ba3bd467-3add-47fb-b425-f6f78734f0e1
ed5202fa-ca2a-4638-a9e9-00bad0d6d54c	be102e1f-00f5-48b5-9cbc-3d2583527629	InternetServiceProvider	Internet Service Provider	Internet Service Provider	ba3bd467-3add-47fb-b425-f6f78734f0e1
3dd3f5dc-25bb-4591-85fc-058580a5d379	be102e1f-00f5-48b5-9cbc-3d2583527629	LanguageSecondarySchool	Language Secondary School	Language Secondary School	ba3bd467-3add-47fb-b425-f6f78734f0e1
95ca0ebc-9ba5-47de-b0c6-d320cf25f700	be102e1f-00f5-48b5-9cbc-3d2583527629	Lawyer	Lawyer	Lawyer	ba3bd467-3add-47fb-b425-f6f78734f0e1
9bf7459e-406b-41a7-a6e1-30707a374331	be102e1f-00f5-48b5-9cbc-3d2583527629	MedicalCenter	Medical Center	Medical Center	ba3bd467-3add-47fb-b425-f6f78734f0e1
1404bf91-96a4-4f0a-8bd4-7d7501d1896b	be102e1f-00f5-48b5-9cbc-3d2583527629	Municipality	Municipality	Municipality	ba3bd467-3add-47fb-b425-f6f78734f0e1
5b3ce239-9b4b-4329-859b-7b59aeada0e7	be102e1f-00f5-48b5-9cbc-3d2583527629	NationalHealthInsuranceFund	National Health Insurance Fund	National Health Insurance Fund	ba3bd467-3add-47fb-b425-f6f78734f0e1
0e8fc0f2-4738-4487-b373-8d593e0af13d	be102e1f-00f5-48b5-9cbc-3d2583527629	NationalRevenueAgency	National Revenue Agency	National Revenue Agency	ba3bd467-3add-47fb-b425-f6f78734f0e1
819df821-8513-41ca-adad-ffe233c96fc6	be102e1f-00f5-48b5-9cbc-3d2583527629	NationalSocialSecurityAgency	National Social Security Agency	National Social Security Agency	ba3bd467-3add-47fb-b425-f6f78734f0e1
e0edcc89-cd3f-4269-bbc5-ca2049bf7b79	be102e1f-00f5-48b5-9cbc-3d2583527629	Passport Office	Passport Office	Passport Office	ba3bd467-3add-47fb-b425-f6f78734f0e1
9ab3fa11-79d0-4d7a-a822-495d20117dab	be102e1f-00f5-48b5-9cbc-3d2583527629	PensionFund	Pension Fund	Pension Fund	ba3bd467-3add-47fb-b425-f6f78734f0e1
33b30199-e11c-4064-bdb4-7e1dff7d069f	be102e1f-00f5-48b5-9cbc-3d2583527629	Police	Police	Police	ba3bd467-3add-47fb-b425-f6f78734f0e1
43bbbce5-feca-4d79-bc97-52cb3731fc7f	be102e1f-00f5-48b5-9cbc-3d2583527629	Polyclinic	Polyclinic	Polyclinic	ba3bd467-3add-47fb-b425-f6f78734f0e1
960decb4-e275-4f10-ad4b-153259345acf	be102e1f-00f5-48b5-9cbc-3d2583527629	PrimarySchool	Primary School	Primary School	ba3bd467-3add-47fb-b425-f6f78734f0e1
b9ac4e28-af58-4e71-8fb0-48ff6b832035	be102e1f-00f5-48b5-9cbc-3d2583527629	Producer	Producer	Producer	ba3bd467-3add-47fb-b425-f6f78734f0e1
61ce5549-f274-4c2f-bc58-0ee1a6b3231a	be102e1f-00f5-48b5-9cbc-3d2583527629	Prosecutor	Prosecutor	Prosecutor	ba3bd467-3add-47fb-b425-f6f78734f0e1
17fa0e27-5196-42b6-89ff-2c11b81cfdc4	be102e1f-00f5-48b5-9cbc-3d2583527629	RealEstateAgency	Real Estate Agency	Real Estate Agency	ba3bd467-3add-47fb-b425-f6f78734f0e1
0a1d7e05-7862-45fc-9591-fd5a601258a7	be102e1f-00f5-48b5-9cbc-3d2583527629	RegistryAgency	Registry Agency	Registry Agency	ba3bd467-3add-47fb-b425-f6f78734f0e1
c1218b7a-bfb1-4ac3-9014-3b757e78be4d	be102e1f-00f5-48b5-9cbc-3d2583527629	RentACar	Rent a Car	Rent a Car	ba3bd467-3add-47fb-b425-f6f78734f0e1
5b08769a-c86a-493e-968c-16d08228b0dd	be102e1f-00f5-48b5-9cbc-3d2583527629	SecondarySchool	Secondary School	Secondary School	ba3bd467-3add-47fb-b425-f6f78734f0e1
40c70948-ab67-4c97-9c02-ff809495c43c	be102e1f-00f5-48b5-9cbc-3d2583527629	ServiceProvider	Service Provider	Service Provider	ba3bd467-3add-47fb-b425-f6f78734f0e1
f22492e5-e7d5-4916-bb3e-848978d1b998	be102e1f-00f5-48b5-9cbc-3d2583527629	ShippingAgent	Shipping Agent	Shipping Agent	ba3bd467-3add-47fb-b425-f6f78734f0e1
dff50de9-311b-4dd1-9f06-143892f2787e	be102e1f-00f5-48b5-9cbc-3d2583527629	SolicitorAssociation	Solicitor Association	Solicitor Association	ba3bd467-3add-47fb-b425-f6f78734f0e1
6e8eb498-60f7-4346-869c-ce9ef1e1f57a	be102e1f-00f5-48b5-9cbc-3d2583527629	Supplier	Supplier	Supplier	ba3bd467-3add-47fb-b425-f6f78734f0e1
65c0cddf-b417-4a5c-9bca-e8fd4f7835f6	be102e1f-00f5-48b5-9cbc-3d2583527629	TechnicalUniversity	Technical University	Technical University	ba3bd467-3add-47fb-b425-f6f78734f0e1
7e9c28ee-15ee-4d4b-a74d-24cb54dca086	be102e1f-00f5-48b5-9cbc-3d2583527629	University	University	University	ba3bd467-3add-47fb-b425-f6f78734f0e1
005c6932-cebb-4f07-bb88-1f9cf8c6b417	81e50b6b-68a6-4e50-9103-95fe8eba86f3	AdvertisementAgency	Advertisement Agency	Advertisement Agency	7c1668ee-4668-4625-966b-e447d70a19ee
ca45be59-4a4d-435f-b863-8f15db7fdcfa	81e50b6b-68a6-4e50-9103-95fe8eba86f3	Bank	Bank	Bank	7c1668ee-4668-4625-966b-e447d70a19ee
30f83a8a-0eb4-44a1-9c5a-cecdda74c646	81e50b6b-68a6-4e50-9103-95fe8eba86f3	Cashier	Cashier	Cashier	7c1668ee-4668-4625-966b-e447d70a19ee
3e9ed43f-6b0b-47d7-951f-8d2154c6dc1b	81e50b6b-68a6-4e50-9103-95fe8eba86f3	College	College	College	7c1668ee-4668-4625-966b-e447d70a19ee
336af087-1a89-41db-a5eb-d1dcb3699257	81e50b6b-68a6-4e50-9103-95fe8eba86f3	Courier	Courier	Courier	7c1668ee-4668-4625-966b-e447d70a19ee
5b67c098-5908-448a-820d-d558210bb385	81e50b6b-68a6-4e50-9103-95fe8eba86f3	Court	Court	Court	7c1668ee-4668-4625-966b-e447d70a19ee
d48df06d-c213-4b5c-a75f-708c8fd5adb3	81e50b6b-68a6-4e50-9103-95fe8eba86f3	Customer	Customer/Client	With this classifier are classified the customers.	7c1668ee-4668-4625-966b-e447d70a19ee
17ea8a86-8b8d-4ac2-bfdb-260ae324f1a8	81e50b6b-68a6-4e50-9103-95fe8eba86f3	Customs	Customs	Customs	7c1668ee-4668-4625-966b-e447d70a19ee
c6fc20ff-3ed2-4d53-9a1c-0e4370ccfda5	81e50b6b-68a6-4e50-9103-95fe8eba86f3	Dentist	Dentist	Dentist	7c1668ee-4668-4625-966b-e447d70a19ee
1961cf07-5ff6-4928-b48d-2d9e46764173	81e50b6b-68a6-4e50-9103-95fe8eba86f3	DoctorOfMedicine	Doctor of Medicine	Doctor of Medicine	7c1668ee-4668-4625-966b-e447d70a19ee
9e359232-6924-4894-ba03-a3568692188c	81e50b6b-68a6-4e50-9103-95fe8eba86f3	Employee	Employee	Employee	7c1668ee-4668-4625-966b-e447d70a19ee
a3e2f33b-e945-4507-a7fe-a037f558f316	81e50b6b-68a6-4e50-9103-95fe8eba86f3	Entertainment	Entertainment	Entertainment	7c1668ee-4668-4625-966b-e447d70a19ee
112a5651-5c59-45f7-9277-6762fa894051	81e50b6b-68a6-4e50-9103-95fe8eba86f3	HealthInsuranceFund	Health Insurance Fund	Health Insurance Fund	7c1668ee-4668-4625-966b-e447d70a19ee
3b00829e-3c16-4361-acb6-e3f46165818d	81e50b6b-68a6-4e50-9103-95fe8eba86f3	HighSchool	High School	High School	7c1668ee-4668-4625-966b-e447d70a19ee
0c9a7577-7f14-46a4-a731-aa9ad6e3ab03	81e50b6b-68a6-4e50-9103-95fe8eba86f3	HighTechnicalSchool	High Technical School	High Technical School	7c1668ee-4668-4625-966b-e447d70a19ee
0e3b8879-f288-4869-a168-ae7b992cc332	81e50b6b-68a6-4e50-9103-95fe8eba86f3	Hospital	Hospital	Hospital	7c1668ee-4668-4625-966b-e447d70a19ee
2c98fa56-6ab7-42ba-b402-00c07e84b7b7	81e50b6b-68a6-4e50-9103-95fe8eba86f3	Hotel	Hotel	Hotel	7c1668ee-4668-4625-966b-e447d70a19ee
602e9b16-1d6d-441c-9a58-78b97aec6f29	81e50b6b-68a6-4e50-9103-95fe8eba86f3	HotelApartment	Hotel Apartment	Hotel Apartment	7c1668ee-4668-4625-966b-e447d70a19ee
e3b53b43-30e6-485b-90b7-9a54c0d23cce	81e50b6b-68a6-4e50-9103-95fe8eba86f3	ITServiceProvider	IT Service Provider	IT Service Provider	7c1668ee-4668-4625-966b-e447d70a19ee
84192873-c898-4526-a631-869267e1f4c2	81e50b6b-68a6-4e50-9103-95fe8eba86f3	InsuranceAgent	Insurance Agent	Insurance Agent	7c1668ee-4668-4625-966b-e447d70a19ee
1ac4278a-2b43-4d63-9dca-20dbcc9253cc	81e50b6b-68a6-4e50-9103-95fe8eba86f3	InsuranceCompany	Insurance Company	Insurance Company	7c1668ee-4668-4625-966b-e447d70a19ee
665b7857-ac3c-4a8d-b217-b146437ff546	81e50b6b-68a6-4e50-9103-95fe8eba86f3	InternetServiceProvider	Internet Service Provider	Internet Service Provider	7c1668ee-4668-4625-966b-e447d70a19ee
493cbc26-5070-45cb-af8a-07d2bca30005	81e50b6b-68a6-4e50-9103-95fe8eba86f3	LanguageSecondarySchool	Language Secondary School	Language Secondary School	7c1668ee-4668-4625-966b-e447d70a19ee
a4ebbfc0-4b3c-4ea8-a2f3-d482c48bce65	81e50b6b-68a6-4e50-9103-95fe8eba86f3	Lawyer	Lawyer	Lawyer	7c1668ee-4668-4625-966b-e447d70a19ee
04ff1a7f-0478-4cd6-b7cd-2f3a4c839ed9	81e50b6b-68a6-4e50-9103-95fe8eba86f3	MedicalCenter	Medical Center	Medical Center	7c1668ee-4668-4625-966b-e447d70a19ee
71e68c8a-7683-4577-99c4-3879089b71a7	81e50b6b-68a6-4e50-9103-95fe8eba86f3	Municipality	Municipality	Municipality	7c1668ee-4668-4625-966b-e447d70a19ee
baa5d350-244e-4ea7-a3e3-b072248be94a	81e50b6b-68a6-4e50-9103-95fe8eba86f3	NationalHealthInsuranceFund	National Health Insurance Fund	National Health Insurance Fund	7c1668ee-4668-4625-966b-e447d70a19ee
4120b200-d542-4fca-983a-f72eced7fa21	81e50b6b-68a6-4e50-9103-95fe8eba86f3	NationalRevenueAgency	National Revenue Agency	National Revenue Agency	7c1668ee-4668-4625-966b-e447d70a19ee
6d8df62e-feb4-4362-a9f9-6b12376a7dbb	81e50b6b-68a6-4e50-9103-95fe8eba86f3	NationalSocialSecurityAgency	National Social Security Agency	National Social Security Agency	7c1668ee-4668-4625-966b-e447d70a19ee
2ee0c439-a483-4459-bfc4-f357048f3eb0	81e50b6b-68a6-4e50-9103-95fe8eba86f3	Passport Office	Passport Office	Passport Office	7c1668ee-4668-4625-966b-e447d70a19ee
15fd3ba5-c2ae-45ea-9a55-a3f015e4fabe	81e50b6b-68a6-4e50-9103-95fe8eba86f3	PensionFund	Pension Fund	Pension Fund	7c1668ee-4668-4625-966b-e447d70a19ee
fce46703-e06a-4d80-87be-7e04d2002156	81e50b6b-68a6-4e50-9103-95fe8eba86f3	Police	Police	Police	7c1668ee-4668-4625-966b-e447d70a19ee
8ea8d555-3f9f-433d-85ed-7b0ed9060bb8	81e50b6b-68a6-4e50-9103-95fe8eba86f3	Polyclinic	Polyclinic	Polyclinic	7c1668ee-4668-4625-966b-e447d70a19ee
f8451834-35f4-4ceb-b7fa-e446d5d58215	81e50b6b-68a6-4e50-9103-95fe8eba86f3	PrimarySchool	Primary School	Primary School	7c1668ee-4668-4625-966b-e447d70a19ee
6cbf0d65-e14d-4b17-867f-92fee635f813	81e50b6b-68a6-4e50-9103-95fe8eba86f3	Producer	Producer	Producer	7c1668ee-4668-4625-966b-e447d70a19ee
bf49be07-0518-4062-b2bf-de06a3e0c2cf	81e50b6b-68a6-4e50-9103-95fe8eba86f3	Prosecutor	Prosecutor	Prosecutor	7c1668ee-4668-4625-966b-e447d70a19ee
f311a345-db25-4b79-99cd-fa168685e036	81e50b6b-68a6-4e50-9103-95fe8eba86f3	RealEstateAgency	Real Estate Agency	Real Estate Agency	7c1668ee-4668-4625-966b-e447d70a19ee
b8e5cc02-27c3-4d52-8e3c-c9e4cf09dcba	81e50b6b-68a6-4e50-9103-95fe8eba86f3	RegistryAgency	Registry Agency	Registry Agency	7c1668ee-4668-4625-966b-e447d70a19ee
37246a6b-b9f0-4f70-82ff-7bd54eefec7d	81e50b6b-68a6-4e50-9103-95fe8eba86f3	RentACar	Rent a Car	Rent a Car	7c1668ee-4668-4625-966b-e447d70a19ee
6bbe29c5-0f3d-4e14-91d7-380170b6ea22	81e50b6b-68a6-4e50-9103-95fe8eba86f3	SecondarySchool	Secondary School	Secondary School	7c1668ee-4668-4625-966b-e447d70a19ee
aea9572a-ab14-4423-9e91-46a24d2871c2	81e50b6b-68a6-4e50-9103-95fe8eba86f3	ServiceProvider	Service Provider	Service Provider	7c1668ee-4668-4625-966b-e447d70a19ee
75b7e8d7-54d7-4639-a9ea-287ea1e160d3	81e50b6b-68a6-4e50-9103-95fe8eba86f3	ShippingAgent	Shipping Agent	Shipping Agent	7c1668ee-4668-4625-966b-e447d70a19ee
2876f9f6-11e3-4892-a5f1-495db0a694a0	81e50b6b-68a6-4e50-9103-95fe8eba86f3	SolicitorAssociation	Solicitor Association	Solicitor Association	7c1668ee-4668-4625-966b-e447d70a19ee
fe80a319-c61c-4608-8dfa-49c58d7f4fa1	81e50b6b-68a6-4e50-9103-95fe8eba86f3	Supplier	Supplier	Supplier	7c1668ee-4668-4625-966b-e447d70a19ee
9560144a-5f44-4ff1-8fca-f9272595c512	81e50b6b-68a6-4e50-9103-95fe8eba86f3	TechnicalUniversity	Technical University	Technical University	7c1668ee-4668-4625-966b-e447d70a19ee
775d24d4-043c-44af-b5be-4574c4b69dab	81e50b6b-68a6-4e50-9103-95fe8eba86f3	University	University	University	7c1668ee-4668-4625-966b-e447d70a19ee
60a6a6d2-12eb-4897-9805-9c1584a2e9a1	8d550848-9260-41a7-aefd-8969d4179b13	AdvertisementAgency	Advertisement Agency	Advertisement Agency	f0249923-3520-4d19-a6ab-8bc3574a7709
d7f97fd1-f884-4bdd-9e36-3825683f29ad	8d550848-9260-41a7-aefd-8969d4179b13	Bank	Bank	Bank	f0249923-3520-4d19-a6ab-8bc3574a7709
4f8503bb-bece-4a8a-bbc9-baa18547a5d0	8d550848-9260-41a7-aefd-8969d4179b13	Cashier	Cashier	Cashier	f0249923-3520-4d19-a6ab-8bc3574a7709
49d8d0af-5365-4eaf-a5f0-09ce071096eb	8d550848-9260-41a7-aefd-8969d4179b13	College	College	College	f0249923-3520-4d19-a6ab-8bc3574a7709
9902aee0-9c71-4d7f-8c1e-9137b06ec67e	8d550848-9260-41a7-aefd-8969d4179b13	Courier	Courier	Courier	f0249923-3520-4d19-a6ab-8bc3574a7709
dcfe2077-1424-4456-86a6-2d0d178d99ea	8d550848-9260-41a7-aefd-8969d4179b13	Court	Court	Court	f0249923-3520-4d19-a6ab-8bc3574a7709
ccd29345-d196-4a44-8b70-ab54c7296671	8d550848-9260-41a7-aefd-8969d4179b13	Customer	Customer/Client	With this classifier are classified the customers.	f0249923-3520-4d19-a6ab-8bc3574a7709
c8fb023e-66e3-4932-a4d0-9119f713cfe2	8d550848-9260-41a7-aefd-8969d4179b13	Customs	Customs	Customs	f0249923-3520-4d19-a6ab-8bc3574a7709
18da1218-b1eb-465f-b779-f31a9d036386	8d550848-9260-41a7-aefd-8969d4179b13	Dentist	Dentist	Dentist	f0249923-3520-4d19-a6ab-8bc3574a7709
0951ad80-adb7-43fc-9a0f-91afacada505	8d550848-9260-41a7-aefd-8969d4179b13	DoctorOfMedicine	Doctor of Medicine	Doctor of Medicine	f0249923-3520-4d19-a6ab-8bc3574a7709
98c76db0-a730-4561-aa4f-d7d639cbd045	8d550848-9260-41a7-aefd-8969d4179b13	Employee	Employee	Employee	f0249923-3520-4d19-a6ab-8bc3574a7709
356ba541-808d-4452-93b2-0041adfbe41f	8d550848-9260-41a7-aefd-8969d4179b13	Entertainment	Entertainment	Entertainment	f0249923-3520-4d19-a6ab-8bc3574a7709
abbedfa9-6e7d-443e-a1c4-1b3a8cff4f1f	8d550848-9260-41a7-aefd-8969d4179b13	HealthInsuranceFund	Health Insurance Fund	Health Insurance Fund	f0249923-3520-4d19-a6ab-8bc3574a7709
9586bbbc-f7ba-44a1-9a1d-8adebb5f0f58	8d550848-9260-41a7-aefd-8969d4179b13	HighSchool	High School	High School	f0249923-3520-4d19-a6ab-8bc3574a7709
20bab795-ac06-48cb-b74c-d45f23a5a2e2	8d550848-9260-41a7-aefd-8969d4179b13	HighTechnicalSchool	High Technical School	High Technical School	f0249923-3520-4d19-a6ab-8bc3574a7709
398fc3ff-dc3b-4d98-a2b7-293da4f2b98c	8d550848-9260-41a7-aefd-8969d4179b13	Hospital	Hospital	Hospital	f0249923-3520-4d19-a6ab-8bc3574a7709
0b9c2ea5-56d4-4e12-b98a-6a31fa7fda0a	8d550848-9260-41a7-aefd-8969d4179b13	Hotel	Hotel	Hotel	f0249923-3520-4d19-a6ab-8bc3574a7709
ab6a3dbf-2caf-42f6-9f6e-e82b052412b8	8d550848-9260-41a7-aefd-8969d4179b13	HotelApartment	Hotel Apartment	Hotel Apartment	f0249923-3520-4d19-a6ab-8bc3574a7709
08fcf0a7-e137-485e-a7c0-6e08dc066360	8d550848-9260-41a7-aefd-8969d4179b13	ITServiceProvider	IT Service Provider	IT Service Provider	f0249923-3520-4d19-a6ab-8bc3574a7709
07d22209-99ca-47af-b520-0c88ff59887a	8d550848-9260-41a7-aefd-8969d4179b13	InsuranceAgent	Insurance Agent	Insurance Agent	f0249923-3520-4d19-a6ab-8bc3574a7709
8119503b-8878-49d9-b9cd-50308f6ebbb5	8d550848-9260-41a7-aefd-8969d4179b13	InsuranceCompany	Insurance Company	Insurance Company	f0249923-3520-4d19-a6ab-8bc3574a7709
77282ae6-37ea-4b19-b091-44d58ac9634a	8d550848-9260-41a7-aefd-8969d4179b13	InternetServiceProvider	Internet Service Provider	Internet Service Provider	f0249923-3520-4d19-a6ab-8bc3574a7709
04c21985-72ca-4038-ab81-cf359ac5980f	8d550848-9260-41a7-aefd-8969d4179b13	LanguageSecondarySchool	Language Secondary School	Language Secondary School	f0249923-3520-4d19-a6ab-8bc3574a7709
a8a469c5-b078-4ce3-961d-7dabde7f898e	8d550848-9260-41a7-aefd-8969d4179b13	Lawyer	Lawyer	Lawyer	f0249923-3520-4d19-a6ab-8bc3574a7709
8135005e-299c-42a3-937a-325d7b87c0d6	8d550848-9260-41a7-aefd-8969d4179b13	MedicalCenter	Medical Center	Medical Center	f0249923-3520-4d19-a6ab-8bc3574a7709
513daf88-bb4e-4fb3-9d25-82eb73460d6f	8d550848-9260-41a7-aefd-8969d4179b13	Municipality	Municipality	Municipality	f0249923-3520-4d19-a6ab-8bc3574a7709
ffaacc20-13cb-4043-84aa-03beea569b01	8d550848-9260-41a7-aefd-8969d4179b13	NationalHealthInsuranceFund	National Health Insurance Fund	National Health Insurance Fund	f0249923-3520-4d19-a6ab-8bc3574a7709
a807d527-81ce-49b5-960b-e1e717fb6479	8d550848-9260-41a7-aefd-8969d4179b13	NationalRevenueAgency	National Revenue Agency	National Revenue Agency	f0249923-3520-4d19-a6ab-8bc3574a7709
bf89dc95-69ba-4259-9310-ecaaf0687940	8d550848-9260-41a7-aefd-8969d4179b13	NationalSocialSecurityAgency	National Social Security Agency	National Social Security Agency	f0249923-3520-4d19-a6ab-8bc3574a7709
78a74943-a2d9-42b6-ad12-ab89c102c8b1	8d550848-9260-41a7-aefd-8969d4179b13	Passport Office	Passport Office	Passport Office	f0249923-3520-4d19-a6ab-8bc3574a7709
33c20e29-e01f-479d-ab17-4a5b5ec0379c	8d550848-9260-41a7-aefd-8969d4179b13	PensionFund	Pension Fund	Pension Fund	f0249923-3520-4d19-a6ab-8bc3574a7709
2384a583-8d6b-4c07-91ab-e676e96db6a0	8d550848-9260-41a7-aefd-8969d4179b13	Police	Police	Police	f0249923-3520-4d19-a6ab-8bc3574a7709
f357a77a-d678-4526-823a-37c79a48e7da	8d550848-9260-41a7-aefd-8969d4179b13	Polyclinic	Polyclinic	Polyclinic	f0249923-3520-4d19-a6ab-8bc3574a7709
640caa25-90e8-4214-8896-991dccad076f	8d550848-9260-41a7-aefd-8969d4179b13	PrimarySchool	Primary School	Primary School	f0249923-3520-4d19-a6ab-8bc3574a7709
a7044b17-2e8e-4732-96d5-4ec6aff7dfc1	8d550848-9260-41a7-aefd-8969d4179b13	Producer	Producer	Producer	f0249923-3520-4d19-a6ab-8bc3574a7709
b7a43737-3bea-4f7b-8c1b-80c10d61d00d	8d550848-9260-41a7-aefd-8969d4179b13	Prosecutor	Prosecutor	Prosecutor	f0249923-3520-4d19-a6ab-8bc3574a7709
5b383034-2336-4a69-b947-e3a629538918	8d550848-9260-41a7-aefd-8969d4179b13	RealEstateAgency	Real Estate Agency	Real Estate Agency	f0249923-3520-4d19-a6ab-8bc3574a7709
098ea9f1-4d15-441b-b39c-14c09a2b589c	8d550848-9260-41a7-aefd-8969d4179b13	RegistryAgency	Registry Agency	Registry Agency	f0249923-3520-4d19-a6ab-8bc3574a7709
217e81d1-7b58-4b61-be5c-cfaf2b19a5f5	8d550848-9260-41a7-aefd-8969d4179b13	RentACar	Rent a Car	Rent a Car	f0249923-3520-4d19-a6ab-8bc3574a7709
a6b6afa6-e13f-484c-9070-470e6e3763bf	8d550848-9260-41a7-aefd-8969d4179b13	SecondarySchool	Secondary School	Secondary School	f0249923-3520-4d19-a6ab-8bc3574a7709
6fff1d10-7644-42c7-90a5-bf461556fb78	8d550848-9260-41a7-aefd-8969d4179b13	ServiceProvider	Service Provider	Service Provider	f0249923-3520-4d19-a6ab-8bc3574a7709
dab68ef6-21bc-4a18-85ce-985cc787dfe4	8d550848-9260-41a7-aefd-8969d4179b13	ShippingAgent	Shipping Agent	Shipping Agent	f0249923-3520-4d19-a6ab-8bc3574a7709
7331117d-a076-4ebc-8ebd-252430906bd6	8d550848-9260-41a7-aefd-8969d4179b13	SolicitorAssociation	Solicitor Association	Solicitor Association	f0249923-3520-4d19-a6ab-8bc3574a7709
5f05f88c-f83d-403e-a880-b555e1eb9be8	8d550848-9260-41a7-aefd-8969d4179b13	Supplier	Supplier	Supplier	f0249923-3520-4d19-a6ab-8bc3574a7709
71b52726-c4e7-4e36-bcbc-ebb18fcf4e03	8d550848-9260-41a7-aefd-8969d4179b13	TechnicalUniversity	Technical University	Technical University	f0249923-3520-4d19-a6ab-8bc3574a7709
73c639c7-e863-4165-a00b-bbdedccc54f1	8d550848-9260-41a7-aefd-8969d4179b13	University	University	University	f0249923-3520-4d19-a6ab-8bc3574a7709
aecf5829-8b81-4790-b45c-6d6f9623b613	70592cfd-348d-4255-a227-ab989eb38f25	AdvertisementAgency	Advertisement Agency	Advertisement Agency	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
661b42be-d7e5-4244-ad5f-27c63391a805	70592cfd-348d-4255-a227-ab989eb38f25	Bank	Bank	Bank	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
0598833a-2f3d-481b-a9d1-0f6ed0870343	70592cfd-348d-4255-a227-ab989eb38f25	Cashier	Cashier	Cashier	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
150dcd1a-0606-4075-9038-2f51c763403c	70592cfd-348d-4255-a227-ab989eb38f25	College	College	College	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
9e887f7b-f7ad-4ebf-a17e-1a3d85b3656b	70592cfd-348d-4255-a227-ab989eb38f25	Courier	Courier	Courier	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
4442544c-7466-4995-9378-41e9ecc077f9	70592cfd-348d-4255-a227-ab989eb38f25	Court	Court	Court	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
f9beb75d-648e-455a-b8eb-176d95f6bf2d	70592cfd-348d-4255-a227-ab989eb38f25	Customer	Customer/Client	With this classifier are classified the customers.	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
706c6f1c-9ceb-436b-b505-2883d1b15a4b	70592cfd-348d-4255-a227-ab989eb38f25	Customs	Customs	Customs	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
f76d421c-cc84-407a-8bea-15c8ec4e9de9	70592cfd-348d-4255-a227-ab989eb38f25	Dentist	Dentist	Dentist	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
5189d0e5-8c83-40ae-9b75-ea6fc27d2f87	70592cfd-348d-4255-a227-ab989eb38f25	DoctorOfMedicine	Doctor of Medicine	Doctor of Medicine	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
d297ff91-3696-4a07-9dc0-074a48b358ba	70592cfd-348d-4255-a227-ab989eb38f25	Employee	Employee	Employee	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
58c4f7cf-8956-4d37-b66e-f4168df53deb	70592cfd-348d-4255-a227-ab989eb38f25	Entertainment	Entertainment	Entertainment	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
9943a6e2-fc1a-48c3-8ec3-cd49f7787774	70592cfd-348d-4255-a227-ab989eb38f25	HealthInsuranceFund	Health Insurance Fund	Health Insurance Fund	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
9da1f331-96ae-419d-af92-049c4cae2182	70592cfd-348d-4255-a227-ab989eb38f25	HighSchool	High School	High School	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
4ef5bc2c-3257-4c77-8b2e-fa0f93202c8b	70592cfd-348d-4255-a227-ab989eb38f25	HighTechnicalSchool	High Technical School	High Technical School	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
887cd83b-0efd-474b-a98b-7f2143f48c7a	70592cfd-348d-4255-a227-ab989eb38f25	Hospital	Hospital	Hospital	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
b9e86a33-f6ad-40d7-90d9-411fb45d6552	70592cfd-348d-4255-a227-ab989eb38f25	Hotel	Hotel	Hotel	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
75536076-bdbf-4b32-899b-286dee175694	70592cfd-348d-4255-a227-ab989eb38f25	HotelApartment	Hotel Apartment	Hotel Apartment	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
fa1614d4-3d0b-4943-ad16-caa9af8f3111	70592cfd-348d-4255-a227-ab989eb38f25	ITServiceProvider	IT Service Provider	IT Service Provider	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
f07a4399-301a-4341-8c12-e7d1023e71a1	70592cfd-348d-4255-a227-ab989eb38f25	InsuranceAgent	Insurance Agent	Insurance Agent	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
977498d2-c16c-480a-9115-73a729753243	70592cfd-348d-4255-a227-ab989eb38f25	InsuranceCompany	Insurance Company	Insurance Company	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
d59e38f9-5f02-4748-b99a-e6eaa0c91ceb	70592cfd-348d-4255-a227-ab989eb38f25	InternetServiceProvider	Internet Service Provider	Internet Service Provider	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
271a2239-4c26-440b-a867-3d055f83635b	70592cfd-348d-4255-a227-ab989eb38f25	LanguageSecondarySchool	Language Secondary School	Language Secondary School	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
c851f9c2-708c-4d8a-b111-645d70d2d452	70592cfd-348d-4255-a227-ab989eb38f25	Lawyer	Lawyer	Lawyer	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
d0c18b8f-ae6e-4a57-b1f6-a08b65b45246	70592cfd-348d-4255-a227-ab989eb38f25	MedicalCenter	Medical Center	Medical Center	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
1f3d5329-f297-4256-804c-7ccbe58a7481	70592cfd-348d-4255-a227-ab989eb38f25	Municipality	Municipality	Municipality	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
cb007721-8dc0-4e6d-89a8-db9b6d94654e	70592cfd-348d-4255-a227-ab989eb38f25	NationalHealthInsuranceFund	National Health Insurance Fund	National Health Insurance Fund	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
c9498203-1e96-454a-8c95-ce7fdf9ff2ea	70592cfd-348d-4255-a227-ab989eb38f25	NationalRevenueAgency	National Revenue Agency	National Revenue Agency	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
298f2bfc-9170-4590-8fd0-29161e587e0f	70592cfd-348d-4255-a227-ab989eb38f25	NationalSocialSecurityAgency	National Social Security Agency	National Social Security Agency	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
5e5c3a6a-a4f3-4ce6-ba9f-552b0cdbdbad	70592cfd-348d-4255-a227-ab989eb38f25	Passport Office	Passport Office	Passport Office	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
bdb63318-e9a2-4c87-beb0-58dfddbb911f	70592cfd-348d-4255-a227-ab989eb38f25	PensionFund	Pension Fund	Pension Fund	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
597ffc09-c247-4375-b47d-6f273866c12a	70592cfd-348d-4255-a227-ab989eb38f25	Police	Police	Police	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
6fa2b4b5-e6bc-4ac9-96c7-3b8bf14a1a96	70592cfd-348d-4255-a227-ab989eb38f25	Polyclinic	Polyclinic	Polyclinic	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
c141ddfa-0237-4eae-ad0f-e5116808783d	70592cfd-348d-4255-a227-ab989eb38f25	PrimarySchool	Primary School	Primary School	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
35a8b8b3-9018-4d79-83c7-131df52329ef	70592cfd-348d-4255-a227-ab989eb38f25	Producer	Producer	Producer	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
0330f85d-51b7-4cca-98b9-e4897f7268d1	70592cfd-348d-4255-a227-ab989eb38f25	Prosecutor	Prosecutor	Prosecutor	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
138f3a67-b3ca-4f31-9d9c-2e0cd8e42c64	70592cfd-348d-4255-a227-ab989eb38f25	RealEstateAgency	Real Estate Agency	Real Estate Agency	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
4aff4065-88bb-4f70-b2d1-a4ef7f7bb607	70592cfd-348d-4255-a227-ab989eb38f25	RegistryAgency	Registry Agency	Registry Agency	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
304083d2-54b3-44d5-939b-8c249beab052	70592cfd-348d-4255-a227-ab989eb38f25	RentACar	Rent a Car	Rent a Car	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
45b41112-01db-4dd8-9b5f-91823fa73e1a	70592cfd-348d-4255-a227-ab989eb38f25	SecondarySchool	Secondary School	Secondary School	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
291db32d-8308-4118-9835-039905fdb781	70592cfd-348d-4255-a227-ab989eb38f25	ServiceProvider	Service Provider	Service Provider	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
d7a4c85b-86ff-4458-8e51-60f669b8e53e	70592cfd-348d-4255-a227-ab989eb38f25	ShippingAgent	Shipping Agent	Shipping Agent	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
d36fb3cb-f6ad-4633-bc37-6d5b693578f5	70592cfd-348d-4255-a227-ab989eb38f25	SolicitorAssociation	Solicitor Association	Solicitor Association	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
94b12590-a430-4b07-9413-74de89b5c383	70592cfd-348d-4255-a227-ab989eb38f25	Supplier	Supplier	Supplier	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
fd26bccf-56bf-444f-99c6-2b224f7c9889	70592cfd-348d-4255-a227-ab989eb38f25	TechnicalUniversity	Technical University	Technical University	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
4a425a2f-524a-43fe-a8e7-8cedae9ab8b3	70592cfd-348d-4255-a227-ab989eb38f25	University	University	University	276e6701-a1f1-4fe0-a4aa-31edf6880c0f
f7450b09-1e94-456d-926c-75286c13bab5	6b77867c-3bca-4fa9-9b75-540b4f0546cc	AdvertisementAgency	Advertisement Agency	Advertisement Agency	2474c5bb-7e45-4244-a547-80bdf045e037
c1ebb64b-f0bb-461f-be37-b4934a211e6c	6b77867c-3bca-4fa9-9b75-540b4f0546cc	Bank	Bank	Bank	2474c5bb-7e45-4244-a547-80bdf045e037
0f1ab287-3ffa-446b-b255-723f92222466	6b77867c-3bca-4fa9-9b75-540b4f0546cc	Cashier	Cashier	Cashier	2474c5bb-7e45-4244-a547-80bdf045e037
4105bc2d-c6c8-4f60-8416-b6dedbca2141	6b77867c-3bca-4fa9-9b75-540b4f0546cc	College	College	College	2474c5bb-7e45-4244-a547-80bdf045e037
649c5f51-823e-4c03-80a5-2a8fa82b0cf3	6b77867c-3bca-4fa9-9b75-540b4f0546cc	Courier	Courier	Courier	2474c5bb-7e45-4244-a547-80bdf045e037
4b92c0ce-c56a-41c9-b120-1e4fba38c4be	6b77867c-3bca-4fa9-9b75-540b4f0546cc	Court	Court	Court	2474c5bb-7e45-4244-a547-80bdf045e037
f4dc9708-780e-4188-b786-ddf4d7151588	6b77867c-3bca-4fa9-9b75-540b4f0546cc	Customer	Customer/Client	With this classifier are classified the customers.	2474c5bb-7e45-4244-a547-80bdf045e037
3e81de2c-0020-4aca-bb01-ee52c48f3e30	6b77867c-3bca-4fa9-9b75-540b4f0546cc	Customs	Customs	Customs	2474c5bb-7e45-4244-a547-80bdf045e037
caca0eff-2117-4ee2-970c-7cc9a9fcc79a	6b77867c-3bca-4fa9-9b75-540b4f0546cc	Dentist	Dentist	Dentist	2474c5bb-7e45-4244-a547-80bdf045e037
688ac98f-b3c5-4997-a05f-f598bbcbbd62	6b77867c-3bca-4fa9-9b75-540b4f0546cc	DoctorOfMedicine	Doctor of Medicine	Doctor of Medicine	2474c5bb-7e45-4244-a547-80bdf045e037
f4542415-c334-455a-be9a-dac24e1dd4b3	6b77867c-3bca-4fa9-9b75-540b4f0546cc	Employee	Employee	Employee	2474c5bb-7e45-4244-a547-80bdf045e037
4ae3f1fe-1d58-429b-bc3e-7d5e3630a131	6b77867c-3bca-4fa9-9b75-540b4f0546cc	Entertainment	Entertainment	Entertainment	2474c5bb-7e45-4244-a547-80bdf045e037
bde33e2a-2dc6-42eb-95d3-52fd15d6b01e	6b77867c-3bca-4fa9-9b75-540b4f0546cc	HealthInsuranceFund	Health Insurance Fund	Health Insurance Fund	2474c5bb-7e45-4244-a547-80bdf045e037
ae06ba49-ef88-4534-aca5-47d2da1d1f4d	6b77867c-3bca-4fa9-9b75-540b4f0546cc	HighSchool	High School	High School	2474c5bb-7e45-4244-a547-80bdf045e037
2b2ae13a-9fa7-4337-9519-3630df564972	6b77867c-3bca-4fa9-9b75-540b4f0546cc	HighTechnicalSchool	High Technical School	High Technical School	2474c5bb-7e45-4244-a547-80bdf045e037
253bfc93-2849-46dd-b11f-9abe40bf9826	6b77867c-3bca-4fa9-9b75-540b4f0546cc	Hospital	Hospital	Hospital	2474c5bb-7e45-4244-a547-80bdf045e037
aa82ef59-9d1a-4e63-b55c-5b630a383f30	6b77867c-3bca-4fa9-9b75-540b4f0546cc	Hotel	Hotel	Hotel	2474c5bb-7e45-4244-a547-80bdf045e037
1a710f2f-4ab2-4a56-be03-ee792255eadd	6b77867c-3bca-4fa9-9b75-540b4f0546cc	HotelApartment	Hotel Apartment	Hotel Apartment	2474c5bb-7e45-4244-a547-80bdf045e037
3c2b524f-a991-417b-bdf2-946b897aaf77	6b77867c-3bca-4fa9-9b75-540b4f0546cc	ITServiceProvider	IT Service Provider	IT Service Provider	2474c5bb-7e45-4244-a547-80bdf045e037
e25c4aa4-0e3f-4c75-8c8e-412cb6914af2	6b77867c-3bca-4fa9-9b75-540b4f0546cc	InsuranceAgent	Insurance Agent	Insurance Agent	2474c5bb-7e45-4244-a547-80bdf045e037
80481af2-97fd-4a9d-a848-54400817bb4d	6b77867c-3bca-4fa9-9b75-540b4f0546cc	InsuranceCompany	Insurance Company	Insurance Company	2474c5bb-7e45-4244-a547-80bdf045e037
57c807f8-c148-4d7d-9905-7ce6d401a23e	6b77867c-3bca-4fa9-9b75-540b4f0546cc	InternetServiceProvider	Internet Service Provider	Internet Service Provider	2474c5bb-7e45-4244-a547-80bdf045e037
053173c8-d063-4ad0-8bf4-d1451637ee28	6b77867c-3bca-4fa9-9b75-540b4f0546cc	LanguageSecondarySchool	Language Secondary School	Language Secondary School	2474c5bb-7e45-4244-a547-80bdf045e037
c09ba000-f933-496f-b8aa-62c71ce3eed4	6b77867c-3bca-4fa9-9b75-540b4f0546cc	Lawyer	Lawyer	Lawyer	2474c5bb-7e45-4244-a547-80bdf045e037
e7db4016-48df-4d22-9f60-15ab803d045a	6b77867c-3bca-4fa9-9b75-540b4f0546cc	MedicalCenter	Medical Center	Medical Center	2474c5bb-7e45-4244-a547-80bdf045e037
06566d64-5d6f-41d1-8617-55b54f20d192	6b77867c-3bca-4fa9-9b75-540b4f0546cc	Municipality	Municipality	Municipality	2474c5bb-7e45-4244-a547-80bdf045e037
ff5d0a9a-618b-4ee6-9be8-19627f5dfbeb	6b77867c-3bca-4fa9-9b75-540b4f0546cc	NationalHealthInsuranceFund	National Health Insurance Fund	National Health Insurance Fund	2474c5bb-7e45-4244-a547-80bdf045e037
5ffa9bfd-7620-4eec-b9d1-2c3c8ae84e30	6b77867c-3bca-4fa9-9b75-540b4f0546cc	NationalRevenueAgency	National Revenue Agency	National Revenue Agency	2474c5bb-7e45-4244-a547-80bdf045e037
7d523f92-d335-4d36-bd94-d2c2ab9a1007	6b77867c-3bca-4fa9-9b75-540b4f0546cc	NationalSocialSecurityAgency	National Social Security Agency	National Social Security Agency	2474c5bb-7e45-4244-a547-80bdf045e037
fee3a134-87b7-4f0f-a8db-5ea48eb0d98c	6b77867c-3bca-4fa9-9b75-540b4f0546cc	Passport Office	Passport Office	Passport Office	2474c5bb-7e45-4244-a547-80bdf045e037
d992929b-e83e-4c53-9bb4-5b32f8a14015	6b77867c-3bca-4fa9-9b75-540b4f0546cc	PensionFund	Pension Fund	Pension Fund	2474c5bb-7e45-4244-a547-80bdf045e037
daed3c68-3830-4375-8bf3-2105e71c14e5	6b77867c-3bca-4fa9-9b75-540b4f0546cc	Police	Police	Police	2474c5bb-7e45-4244-a547-80bdf045e037
a32800a4-2242-4e20-9afd-5ced80b5ea19	6b77867c-3bca-4fa9-9b75-540b4f0546cc	Polyclinic	Polyclinic	Polyclinic	2474c5bb-7e45-4244-a547-80bdf045e037
f32dd44b-7194-4ce4-bea7-6d4553c4dd7e	6b77867c-3bca-4fa9-9b75-540b4f0546cc	PrimarySchool	Primary School	Primary School	2474c5bb-7e45-4244-a547-80bdf045e037
24651a61-1d42-4d01-b28e-dfd942de10ca	6b77867c-3bca-4fa9-9b75-540b4f0546cc	Producer	Producer	Producer	2474c5bb-7e45-4244-a547-80bdf045e037
9b1780a2-a1df-4495-80dd-5f752523a4a6	6b77867c-3bca-4fa9-9b75-540b4f0546cc	Prosecutor	Prosecutor	Prosecutor	2474c5bb-7e45-4244-a547-80bdf045e037
adf17eb2-1888-4039-b079-162e79398274	6b77867c-3bca-4fa9-9b75-540b4f0546cc	RealEstateAgency	Real Estate Agency	Real Estate Agency	2474c5bb-7e45-4244-a547-80bdf045e037
58edcd3a-45bc-4e37-a317-538941b3b60f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	RegistryAgency	Registry Agency	Registry Agency	2474c5bb-7e45-4244-a547-80bdf045e037
8a839bc2-7829-4277-b4ca-659eca05cb9a	6b77867c-3bca-4fa9-9b75-540b4f0546cc	RentACar	Rent a Car	Rent a Car	2474c5bb-7e45-4244-a547-80bdf045e037
1259f85e-523a-41d7-bd3c-daaee480a0bb	6b77867c-3bca-4fa9-9b75-540b4f0546cc	SecondarySchool	Secondary School	Secondary School	2474c5bb-7e45-4244-a547-80bdf045e037
29031544-ad38-4460-a2b3-be62b9eb7e11	6b77867c-3bca-4fa9-9b75-540b4f0546cc	ServiceProvider	Service Provider	Service Provider	2474c5bb-7e45-4244-a547-80bdf045e037
ec6bb520-c52b-4176-887d-2271101510ac	6b77867c-3bca-4fa9-9b75-540b4f0546cc	ShippingAgent	Shipping Agent	Shipping Agent	2474c5bb-7e45-4244-a547-80bdf045e037
aca42890-54f1-4199-9d32-4a8fa6b63829	6b77867c-3bca-4fa9-9b75-540b4f0546cc	SolicitorAssociation	Solicitor Association	Solicitor Association	2474c5bb-7e45-4244-a547-80bdf045e037
d3b7c80d-6875-4f4c-a568-8f0c09afec8e	6b77867c-3bca-4fa9-9b75-540b4f0546cc	Supplier	Supplier	Supplier	2474c5bb-7e45-4244-a547-80bdf045e037
a62ae886-36e2-4976-86ff-81c030adaae2	6b77867c-3bca-4fa9-9b75-540b4f0546cc	TechnicalUniversity	Technical University	Technical University	2474c5bb-7e45-4244-a547-80bdf045e037
bf81f907-0aaa-41bf-9e0a-ff9a27f37a67	6b77867c-3bca-4fa9-9b75-540b4f0546cc	University	University	University	2474c5bb-7e45-4244-a547-80bdf045e037
\.


--
-- TOC entry 2946 (class 0 OID 306774)
-- Dependencies: 1724
-- Data for Name: communication_contacts; Type: TABLE DATA; Schema: public; Owner: -
--

COPY communication_contacts (communication_contact_id, address_id, communication_type_id, communication_value) FROM stdin;
758b97eb-f92e-4375-9043-43f9dfa7d966	59f011d1-a6bc-40d5-8735-a6c338971f6c	732	mnachev@gmail.com
55fc0001-3b68-4b5f-bfef-21fc652ae8b2	59f011d1-a6bc-40d5-8735-a6c338971f6c	727	(+359-88) 897-31-95
43b978fd-1d4e-46f1-94c3-f592d8b90a86	59f011d1-a6bc-40d5-8735-a6c338971f6c	730	mnachev66
\.


--
-- TOC entry 2947 (class 0 OID 306777)
-- Dependencies: 1725
-- Data for Name: complex_product_items; Type: TABLE DATA; Schema: public; Owner: -
--

COPY complex_product_items (complex_product_item_id, complex_product_id, product_id, quantity, unit_price, item_price, applied_algorithm_id, applied_value, due_quantity) FROM stdin;
\.


--
-- TOC entry 2948 (class 0 OID 306783)
-- Dependencies: 1726
-- Data for Name: complex_products; Type: TABLE DATA; Schema: public; Owner: -
--

COPY complex_products (product_id, applied_schema_id, sale_price) FROM stdin;
\.


--
-- TOC entry 2949 (class 0 OID 306786)
-- Dependencies: 1727
-- Data for Name: contact_persons; Type: TABLE DATA; Schema: public; Owner: -
--

COPY contact_persons (contact_person_id, address_id, position_type_id, person_id) FROM stdin;
dd7cf67d-21eb-44e1-b092-2c20666d427b	59f011d1-a6bc-40d5-8735-a6c338971f6c	\N	ced27fbe-fb72-4918-8f56-9e41b6dec8cc
6067aa3c-f4e1-433d-961a-1f7c1ac82ba8	db77bc8d-3a32-4c28-bec0-ec9b72da2582	\N	ec3246e5-2ffd-4f56-8316-1510cf48c33d
\.


--
-- TOC entry 2950 (class 0 OID 306789)
-- Dependencies: 1728
-- Data for Name: countries; Type: TABLE DATA; Schema: public; Owner: -
--

COPY countries (country_id, country_name, country_code_a2, country_code_a3, country_code_n3, country_phone_code, currency_id) FROM stdin;
3d2292c0-93a7-4955-bb72-05b77c3251fc	Albania	AL	ALB	\N	\N	734
9cee83f9-9488-477a-bad2-1b05a697367f	Algeria	DZ	DZA	\N	\N	753
c210ec08-f602-4fb7-b59f-671e19f0b148	Argentina	AR	ARG	\N	\N	735
f5122910-d634-47ba-a091-4b00eb64a369	Australia	AU	AUS	\N	\N	736
14a6f39c-d81c-4ebc-8ba5-b64d1cd3ea9e	Austria	AT	AUT	\N	\N	756
91dd1c91-1e37-4a5a-a22c-726239b4aa41	Bahrain	BH	BHR	\N	\N	739
c6b11cfc-523f-43c7-8ca2-512aee92af5c	Belarus	BY	BLR	\N	\N	742
570f3696-ab90-44d0-b592-a8e1500c6703	Belgium	BE	BEL	\N	\N	756
abd71ffa-8dd8-458b-88ff-cb93836859a4	Bolivia	BO	BOL	\N	\N	740
56627755-51a4-4833-a59f-4a1d35a60f79	Bosnia and Herzegovina	BA	BIH	\N	\N	737
64ec1d88-91f2-4bd5-9446-ba76b6b1018a	Brazil	BR	BRA	\N	\N	741
73e01607-9465-4937-aca6-0ec6c7be6660	Bulgaria	BG	BGR	\N	\N	738
532c189e-8708-4bf5-ae46-89e86ee8a508	Canada	CA	CAN	\N	\N	743
ad2c29d2-2218-4a90-9331-1172a75f0440	Chile	CL	CHL	\N	\N	745
73c01bd4-7394-4cc2-8602-a1c8b43c0a79	China	CN	CHN	\N	\N	746
6787253d-d843-40ab-b2bc-14e2c9089ea0	Colombia	CO	COL	\N	\N	747
da565db5-4d37-412f-b6d6-39e418b95aa8	Costa Rica	CR	CRI	\N	\N	748
7b19c142-a005-4be9-91c7-0909dba4c279	Croatia	HR	HRV	\N	\N	761
ac6e67de-4008-4823-b8e6-271d53c189e7	Cyprus	CY	CYP	\N	\N	756
0b97b7a7-ea6c-4b8c-942a-3c92a80bf8fa	Czech Republic	CZ	CZE	\N	\N	750
fb31702e-3b7c-4e78-bb70-daaba156f4c7	Denmark	DK	DNK	\N	\N	751
3f939531-29e8-4376-b954-4c5f322d49b4	Dominican Republic	DO	DOM	\N	\N	752
e697b053-7069-4e7e-88b3-4a46aef84e56	Ecuador	EC	ECU	\N	\N	805
7d58e948-94b8-4590-ace5-ac8fc3a86d13	Egypt	EG	EGY	\N	\N	755
ed29299a-88e4-4050-bece-482d4ebc9b5b	El Salvador	SV	SLV	\N	\N	798
8df9d6d6-ea83-4492-a604-e24d6930be05	Estonia	EE	EST	\N	\N	754
2865f5ca-9cdc-4807-80c8-93459246a21a	Finland	FI	FIN	\N	\N	756
793ccd01-c67a-4126-851f-660af355c417	France	FR	FRA	\N	\N	756
7a302159-fabf-46dd-bd00-e956519c46c1	Germany	DE	DEU	\N	\N	756
6b49fecf-4045-4fe8-83e8-a24cc779a239	Greece	GR	GRC	\N	\N	756
f3c3ede2-2d25-4625-bd92-e3d4d419fe7a	Guatemala	GT	GTM	\N	\N	758
0b20272d-cf0c-406d-8e99-216b3360c39e	Honduras	HN	HND	\N	\N	760
2952bb44-7615-49de-995e-71b94f391928	Hong Kong	HK	HKG	\N	\N	759
27ba5437-7de8-4778-ba8e-c4385f0c52b6	Hungary	HU	HUN	\N	\N	762
b98a18ce-6153-4888-859a-62c8f034caab	Iceland	IS	ISL	\N	\N	767
0b230aa4-b2b5-4529-8a83-a96a4b29b7a8	India	IN	IND	\N	\N	765
449d0150-bb83-4802-9656-dcd123cbe625	Indonesia	ID	IDN	\N	\N	763
fe45e3da-07d0-46a5-a8ab-56ef9a9c0710	Iraq	IQ	IRQ	\N	\N	766
ac5e8259-d042-486b-9f14-3ec57cff9131	Ireland	IE	IRL	\N	\N	756
890e8cff-722a-490e-b9ce-fc3adbca21b9	Israel	IL	ISR	\N	\N	764
9a0b256c-ba26-4e96-9ef9-1dd02d103807	Italy	IT	ITA	\N	\N	756
485c87b2-aa32-4b48-9547-137e5fe77632	Japan	JP	JPN	\N	\N	769
d1f4ca65-f18c-4c26-9db3-e0abac01b09b	Jordan	JO	JOR	\N	\N	768
33bfce03-ad35-48b6-bb55-574514e7488b	Kuwait	KW	KWT	\N	\N	771
bae54187-4443-42bd-b46e-0a6c43c786ca	Latvia	LV	LVA	\N	\N	774
69ce08dc-94ae-43a0-a3c5-9dd447b89ee9	Lebanon	LB	LBN	\N	\N	772
454c84e7-9265-4b3a-9fe8-07547493143a	Libya	LY	LBY	\N	\N	775
334b47b5-c44f-43f0-a00e-c30d0636508c	Lithuania	LT	LTU	\N	\N	773
ef2dd81a-cebd-47bd-88cf-ce99b33b414f	Luxembourg	LU	LUX	\N	\N	756
f259842d-b847-4b17-9b24-6f80be4b538d	Macedonia	MK	MKD	\N	\N	777
6af9af20-caee-451f-b8ef-901be46519e3	Malaysia	MY	MYS	\N	\N	779
268fd0e1-bf02-4aab-b295-2b314f03757d	Malta	MT	MLT	\N	\N	756
807286f9-1611-464c-a7c6-ae1fc2020ba9	Mexico	MX	MEX	\N	\N	778
fa36bb3c-67ed-4210-98e9-3e0403c2f0e2	Montenegro	ME	MNE	\N	\N	756
bc4b30df-5519-4479-9d59-358201613494	Morocco	MA	MAR	\N	\N	776
c3a97976-fb79-4451-8c35-901fcc76c52f	Netherlands	NL	NLD	\N	\N	756
a5eb2f40-cc2f-4b54-87c9-d2a27c9e4c74	New Zealand	NZ	NZL	\N	\N	782
0977fd52-e297-4a1c-a5bf-f27dc488788d	Nicaragua	NI	NIC	\N	\N	780
325a5e74-a08f-41b1-8266-88d25d7d6a44	Norway	NO	NOR	\N	\N	781
856570ed-46e4-4577-a3cf-f34d40eccf10	Oman	OM	OMN	\N	\N	783
c0b949d1-47e7-410c-a015-f02589133a65	Panama	PA	PAN	\N	\N	784
6a6dd6aa-ed45-4cff-962c-e186ccdbeb18	Paraguay	PY	PRY	\N	\N	788
bb5fe455-5311-478c-b89b-71689f5df1e3	Peru	PE	PER	\N	\N	785
22cc7885-dd38-4fd0-a871-074a9a8e7056	Philippines	PH	PHL	\N	\N	786
2d719097-c655-413c-9f38-ba08cd74b5a2	Poland	PL	POL	\N	\N	787
42c80efc-b7e3-4946-a322-1f83d16237aa	Portugal	PT	PRT	\N	\N	756
9f9e64a9-4b51-4641-98f1-8edaa17e6ddc	Puerto Rico	PR	PRI	\N	\N	805
bcdc4392-e7d3-4bd2-b60a-04ecb0212288	Qatar	QA	QAT	\N	\N	789
90659f0b-5940-41c0-84ab-a39b4b9806f5	Romania	RO	ROU	\N	\N	790
5391312a-4f6e-49b6-b7e5-477e128063ec	Russia	RU	RUS	\N	\N	792
b7a86127-8bf2-438c-8a43-859cd163480b	Saudi Arabia	SA	SAU	\N	\N	793
7af91677-67ce-41dd-8218-419a78f32abb	Serbia	RS	SRB	\N	\N	791
33213f51-57d2-4ec8-8b1a-bb70d6bb8aa4	Serbia and Montenegro	CS	SCG	\N	\N	749
e3c45978-edaf-4094-94b8-ce9009af7fe6	Singapore	SG	SGP	\N	\N	796
debcc516-3783-4e75-ab7f-92cbf93ad5cf	Slovakia	SK	SVK	\N	\N	797
0a2c0d37-7540-410d-bab8-2bb431970b25	Slovenia	SI	SVN	\N	\N	756
d8c3f215-07a0-4347-8a4f-91650d57f4ac	South Africa	ZA	ZAF	\N	\N	810
1021ac13-e318-4904-8203-33998c5103c2	South Korea	KR	KOR	\N	\N	770
fa26c411-c010-44ce-acf0-199b133480d1	Spain	ES	ESP	\N	\N	756
0981e343-67bd-465a-aaac-5cc60583dd9a	Sudan	SD	SDN	\N	\N	794
a7a46b2e-a11f-4ac8-bc7a-ac30e1292392	Sweden	SE	SWE	\N	\N	795
4bddbb87-89ca-45ad-bb49-93ef03091b2f	Switzerland	CH	CHE	\N	\N	744
f55dd39a-db59-47d2-af38-008cd8205b4e	Syria	SY	SYR	\N	\N	799
a939eff3-b0d3-4fb8-ac5a-ffb0821a9703	Taiwan	TW	TWN	\N	\N	803
b351de49-b92b-4c82-be0d-57dcb71de50e	Thailand	TH	THA	\N	\N	800
b0bf20c6-78b2-48ac-8cb6-c7edd90d6158	Tunisia	TN	TUN	\N	\N	801
ca5bc7eb-533a-4196-bc09-4924d07670be	Turkey	TR	TUR	\N	\N	802
3b978d7f-05fb-40e7-8d7e-a5c5b8242107	Ukraine	UA	UKR	\N	\N	804
81438d8b-69df-4e90-a074-dcf3b49fedd9	United Arab Emirates	AE	ARE	\N	\N	733
72d4b361-f27f-4f69-93fb-6867bf14f71b	United Kingdom	GB	GBR	\N	\N	757
f0fa8f08-513f-465c-b76c-a997a1c59f5b	United States	US	USA	\N	\N	805
e17d5e9f-3402-4c50-bf50-5cd2254ce70a	Uruguay	UY	URY	\N	\N	806
44673264-9f61-4cd5-b554-3e2fd00debf4	Venezuela	VE	VEN	\N	\N	807
81835c0e-5a43-46a1-915f-0ca994f0e67a	Vietnam	VN	VNM	\N	\N	808
fea5b22b-b181-4001-87cf-eee4bcb5d8e2	Yemen	YE	YEM	\N	\N	809
\.


--
-- TOC entry 2951 (class 0 OID 306792)
-- Dependencies: 1729
-- Data for Name: currency_exchange_rates; Type: TABLE DATA; Schema: public; Owner: -
--

COPY currency_exchange_rates (organization_id, valid_from, from_currency_id, to_currency_id, valid_until, exchange_rate, fixed_exchange_rate) FROM stdin;
e5d5c576-ae35-43de-a67a-40f56ae598d0	1999-07-05 00:00:00+03	756	738	\N	1.955830	t
c2228a74-4879-4338-9a6b-d92df52bffc9	1999-07-05 00:00:00+03	756	738	\N	1.955830	t
59f5e9f2-217c-401f-bd94-ae8b5d4ac477	1999-07-05 00:00:00+03	756	738	\N	1.955830	t
17d7af2a-7efd-4443-9ffb-08636a8253c0	1999-07-05 00:00:00+03	756	738	\N	1.955830	t
da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	1999-07-05 00:00:00+03	756	738	\N	1.955830	t
be102e1f-00f5-48b5-9cbc-3d2583527629	1999-07-05 00:00:00+03	756	738	\N	1.955830	t
81e50b6b-68a6-4e50-9103-95fe8eba86f3	1999-07-05 00:00:00+03	756	738	\N	1.955830	t
8d550848-9260-41a7-aefd-8969d4179b13	1999-07-05 00:00:00+03	756	738	\N	1.955830	t
70592cfd-348d-4255-a227-ab989eb38f25	1999-07-05 00:00:00+03	756	738	\N	1.955830	t
6b77867c-3bca-4fa9-9b75-540b4f0546cc	1999-07-05 00:00:00+03	756	738	\N	1.955830	t
\.


--
-- TOC entry 2952 (class 0 OID 306796)
-- Dependencies: 1730
-- Data for Name: currency_nominal; Type: TABLE DATA; Schema: public; Owner: -
--

COPY currency_nominal (currency_nominal_id, nominal_value, currency_id) FROM stdin;
7fd527c2-d8d3-4901-8124-25a0400a50c0	0.0100	756
212595ee-ada3-4a42-9194-784ec2245b19	0.0200	756
cf2ccafd-9f66-42ff-95de-725a2b1dafea	0.0500	756
410a3c1a-66e9-489c-94a2-502a2d32d62b	0.1000	756
8e29c046-bc4f-41fb-8465-c7bb07a198e0	0.2000	756
85c6ada7-ba49-4a09-8178-4ee8ed8c7753	0.5000	756
62b5be98-663d-4325-9d77-a40f9cd76328	1.0000	756
f4722a11-8692-4194-ba52-4c84343e31db	2.0000	756
62592e22-6b47-4919-b95d-1f637748c995	5.0000	756
a617b5cc-7637-419b-9b8c-e3a00d580186	10.0000	756
8ac0296a-ae3d-43ba-89b3-2b570d19a848	20.0000	756
4cd4c143-3fd0-42a9-ae88-7d309072ab93	50.0000	756
969dd7f9-e05e-4809-be26-4913e7844778	100.0000	756
ba7cce99-2171-4ea3-a101-7790bcd9ddce	200.0000	756
8edb0c8b-e4cc-4905-881c-95eeb94b5c73	500.0000	756
bf1a48d6-bd28-4580-8233-cefaf2bc9b8e	0.0100	805
4b011d40-e751-46e8-9848-d681055fd09b	0.0200	805
3dfafecb-a1ae-4fc5-be19-7a3f018ceeb7	0.0500	805
6ddcb918-de21-489e-9a6a-7c9648a7ae55	0.1000	805
76c90a1d-b263-419b-96c5-e54f43f19283	0.2000	805
1e6a3858-d31f-47ef-80bf-a7bfb55949be	0.5000	805
7fd16045-43b4-47d2-a9c1-7f784889a782	1.0000	805
eb244766-8aa8-4835-a736-a498685c8754	2.0000	805
db219363-d172-4a5a-b812-078666b2f599	5.0000	805
19b5de76-09dc-466c-950b-0c984cfb8585	10.0000	805
69bf9d2e-b502-4028-8b62-ab9e5fa40dd2	20.0000	805
57a98665-4d24-472c-bf3a-ffe094c6a034	50.0000	805
377e28d7-cf34-42a4-b348-9052daa0da0a	100.0000	805
8a0a2dc4-7515-4022-aff6-1a365ae3b2ce	0.0100	738
36dd2d1d-c848-4295-a4b4-256965e1bb4e	0.0200	738
45e16ccc-28b4-422b-adf6-1288275855f3	0.0500	738
cc69d884-dc59-4210-869c-9b9c518e25ab	0.1000	738
e88bf418-3854-4a6b-8ad5-beedebe12962	0.2000	738
3bf9743c-aa66-487e-8a25-38aa45b93c16	0.5000	738
4ccc6253-e1e4-4d59-9dc0-60e750f71f41	1.0000	738
3fe391fe-60bb-4f7d-907b-7ceec7149843	2.0000	738
2f751b9f-6859-4fe6-9c08-ed70520b038d	5.0000	738
cf39ec32-4efc-4152-828b-c30898bf5fa3	10.0000	738
6c463691-62ab-492d-b839-e0b8643bbfc5	20.0000	738
765d5704-9c89-486e-a0c6-08a6d2bd108d	50.0000	738
31202b17-cf09-453b-9149-da756b68e03e	100.0000	738
\.


--
-- TOC entry 2953 (class 0 OID 306799)
-- Dependencies: 1731
-- Data for Name: customer_discount_items; Type: TABLE DATA; Schema: public; Owner: -
--

COPY customer_discount_items (customer_discount_item_id, discount_percent, discriminator_id) FROM stdin;
\.


--
-- TOC entry 2954 (class 0 OID 306802)
-- Dependencies: 1732
-- Data for Name: customer_discount_items_by_categories; Type: TABLE DATA; Schema: public; Owner: -
--

COPY customer_discount_items_by_categories (customer_discount_item_id, customer_discount_id, category_id, include_heirs) FROM stdin;
\.


--
-- TOC entry 2955 (class 0 OID 306806)
-- Dependencies: 1733
-- Data for Name: customer_discount_items_by_products; Type: TABLE DATA; Schema: public; Owner: -
--

COPY customer_discount_items_by_products (customer_discount_item_id, customer_discount_id, product_id) FROM stdin;
\.


--
-- TOC entry 2956 (class 0 OID 306809)
-- Dependencies: 1734
-- Data for Name: customer_discounts; Type: TABLE DATA; Schema: public; Owner: -
--

COPY customer_discounts (customer_discount_id, discount_percent, organization_id, customer_id) FROM stdin;
\.


--
-- TOC entry 2957 (class 0 OID 306812)
-- Dependencies: 1735
-- Data for Name: customer_payment_match; Type: TABLE DATA; Schema: public; Owner: -
--

COPY customer_payment_match (customer_payment_match_id, amount, creation_time, matchnumber, customer_payment_id, invoice_id) FROM stdin;
\.


--
-- TOC entry 2958 (class 0 OID 306815)
-- Dependencies: 1736
-- Data for Name: customer_payments; Type: TABLE DATA; Schema: public; Owner: -
--

COPY customer_payments (payment_id, amount, completed_at, created_at, description, document_number, matchedamount, parent_id, paymentaccount, paymentreturn, reference_no, transaction_date, transaction_fee, creator_id, payment_type_id, customer_id, status_id, completor_id, branch_id, cashier_id, customer_contact_id, currency_id) FROM stdin;
\.


--
-- TOC entry 2959 (class 0 OID 306821)
-- Dependencies: 1737
-- Data for Name: data_object_details; Type: TABLE DATA; Schema: public; Owner: -
--

COPY data_object_details (data_object_id, detail_code, detail_value, notes) FROM stdin;
\.


--
-- TOC entry 2960 (class 0 OID 306827)
-- Dependencies: 1738
-- Data for Name: data_object_links; Type: TABLE DATA; Schema: public; Owner: -
--

COPY data_object_links (data_object_link_id, parent_id, linked_data_object_id, link_name) FROM stdin;
\.


--
-- TOC entry 2961 (class 0 OID 306830)
-- Dependencies: 1739
-- Data for Name: data_object_permissions; Type: TABLE DATA; Schema: public; Owner: -
--

COPY data_object_permissions (organization_id, data_object_id, user_right_type_id, permission_id) FROM stdin;
\.


--
-- TOC entry 2962 (class 0 OID 306833)
-- Dependencies: 1740
-- Data for Name: data_object_type_permissions; Type: TABLE DATA; Schema: public; Owner: -
--

COPY data_object_type_permissions (organization_id, data_object_type_id, user_right_type_id, permission_id) FROM stdin;
\.


--
-- TOC entry 2963 (class 0 OID 306836)
-- Dependencies: 1741
-- Data for Name: data_object_types; Type: TABLE DATA; Schema: public; Owner: -
--

COPY data_object_types (data_object_type_id, data_object_type, notes, small_image_uri, small_image, medium_image_uri, medium_image) FROM stdin;
8	com.cosmos.acacia.crm.data.contacts.Country	\N	\N	\N	\N	\N
14	com.cosmos.acacia.crm.data.cash.CurrencyNominal	\N	\N	\N	\N	\N
15	com.cosmos.acacia.crm.data.contacts.Organization	\N	\N	\N	\N	\N
16	com.cosmos.acacia.crm.data.contacts.City	\N	\N	\N	\N	\N
17	com.cosmos.acacia.crm.data.contacts.Address	\N	\N	\N	\N	\N
18	com.cosmos.acacia.crm.data.contacts.Person	\N	\N	\N	\N	\N
19	com.cosmos.acacia.crm.data.contacts.ContactPerson	\N	\N	\N	\N	\N
20	com.cosmos.acacia.crm.data.contacts.CommunicationContact	\N	\N	\N	\N	\N
21	com.cosmos.acacia.crm.data.contacts.PersonalCommunicationContact	\N	\N	\N	\N	\N
23	com.cosmos.acacia.crm.data.users.User	\N	\N	\N	\N	\N
24	com.cosmos.acacia.crm.data.users.UserOrganization	\N	\N	\N	\N	\N
25	com.cosmos.acacia.crm.data.assembling.AssemblingMessage	\N	\N	\N	\N	\N
26	com.cosmos.acacia.crm.data.ClassifierGroup	\N	\N	\N	\N	\N
27	com.cosmos.acacia.crm.data.Classifier	\N	\N	\N	\N	\N
28	com.cosmos.acacia.crm.data.users.BusinessUnit	\N	\N	\N	\N	\N
29	com.cosmos.acacia.crm.data.users.BusinessUnitAddress	\N	\N	\N	\N	\N
30	com.cosmos.acacia.crm.data.security.SecurityRole	\N	\N	\N	\N	\N
31	com.cosmos.acacia.crm.data.users.UserSecurityRole	\N	\N	\N	\N	\N
32	com.cosmos.acacia.crm.data.security.PrivilegeCategory	\N	\N	\N	\N	\N
33	com.cosmos.acacia.crm.data.security.EntityTypePrivilege	\N	\N	\N	\N	\N
34	com.cosmos.acacia.crm.data.security.PrivilegeRole	\N	\N	\N	\N	\N
35	com.cosmos.acacia.crm.data.users.UserGroupMember	\N	\N	\N	\N	\N
36	com.cosmos.acacia.crm.data.users.UserGroup	\N	\N	\N	\N	\N
37	com.cosmos.acacia.crm.data.users.Team	\N	\N	\N	\N	\N
38	com.cosmos.acacia.crm.data.users.JobTitle	\N	\N	\N	\N	\N
39	com.cosmos.acacia.crm.data.users.UserRegistration	\N	\N	\N	\N	\N
40	com.cosmos.acacia.crm.data.users.TeamMember	\N	\N	\N	\N	\N
\.


--
-- TOC entry 2964 (class 0 OID 306842)
-- Dependencies: 1742
-- Data for Name: data_objects; Type: TABLE DATA; Schema: public; Owner: -
--

COPY data_objects (data_object_id, data_object_version, data_object_type_id, creation_time, creator_id, owner_id, is_deleted, is_read_only, is_system, is_folder, is_link, parent_data_object_id, linked_data_object_id, child_counter, notes, small_image_uri, small_image, medium_image_uri, medium_image, data_object_uri, order_position) FROM stdin;
3d2292c0-93a7-4955-bb72-05b77c3251fc	1	8	2009-09-03 11:28:43.965+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	1
9cee83f9-9488-477a-bad2-1b05a697367f	1	8	2009-09-03 11:28:45.577+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	2
c210ec08-f602-4fb7-b59f-671e19f0b148	1	8	2009-09-03 11:28:47.031+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	3
f5122910-d634-47ba-a091-4b00eb64a369	1	8	2009-09-03 11:28:48.565+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	4
14a6f39c-d81c-4ebc-8ba5-b64d1cd3ea9e	1	8	2009-09-03 11:28:50.213+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	5
91dd1c91-1e37-4a5a-a22c-726239b4aa41	1	8	2009-09-03 11:28:51.626+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	6
c6b11cfc-523f-43c7-8ca2-512aee92af5c	1	8	2009-09-03 11:28:53.065+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	7
570f3696-ab90-44d0-b592-a8e1500c6703	1	8	2009-09-03 11:28:54.482+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	8
abd71ffa-8dd8-458b-88ff-cb93836859a4	1	8	2009-09-03 11:28:56.007+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	9
56627755-51a4-4833-a59f-4a1d35a60f79	1	8	2009-09-03 11:28:57.501+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	10
64ec1d88-91f2-4bd5-9446-ba76b6b1018a	1	8	2009-09-03 11:28:58.963+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	11
73e01607-9465-4937-aca6-0ec6c7be6660	1	8	2009-09-03 11:29:00.486+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	12
532c189e-8708-4bf5-ae46-89e86ee8a508	1	8	2009-09-03 11:29:01.946+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	13
ad2c29d2-2218-4a90-9331-1172a75f0440	1	8	2009-09-03 11:29:03.428+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	14
73c01bd4-7394-4cc2-8602-a1c8b43c0a79	1	8	2009-09-03 11:29:04.918+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	15
6787253d-d843-40ab-b2bc-14e2c9089ea0	1	8	2009-09-03 11:29:06.376+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	16
da565db5-4d37-412f-b6d6-39e418b95aa8	1	8	2009-09-03 11:29:07.849+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	17
7b19c142-a005-4be9-91c7-0909dba4c279	1	8	2009-09-03 11:29:09.287+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	18
ac6e67de-4008-4823-b8e6-271d53c189e7	1	8	2009-09-03 11:29:10.775+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	19
0b97b7a7-ea6c-4b8c-942a-3c92a80bf8fa	1	8	2009-09-03 11:29:12.234+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	20
fb31702e-3b7c-4e78-bb70-daaba156f4c7	1	8	2009-09-03 11:29:13.711+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	21
3f939531-29e8-4376-b954-4c5f322d49b4	1	8	2009-09-03 11:29:15.168+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	22
e697b053-7069-4e7e-88b3-4a46aef84e56	1	8	2009-09-03 11:29:16.694+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	23
7d58e948-94b8-4590-ace5-ac8fc3a86d13	1	8	2009-09-03 11:29:18.177+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	24
ed29299a-88e4-4050-bece-482d4ebc9b5b	1	8	2009-09-03 11:29:19.674+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	25
8df9d6d6-ea83-4492-a604-e24d6930be05	1	8	2009-09-03 11:29:21.196+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	26
2865f5ca-9cdc-4807-80c8-93459246a21a	1	8	2009-09-03 11:29:22.749+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	27
793ccd01-c67a-4126-851f-660af355c417	1	8	2009-09-03 11:29:24.282+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	28
7a302159-fabf-46dd-bd00-e956519c46c1	1	8	2009-09-03 11:29:25.812+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	29
6b49fecf-4045-4fe8-83e8-a24cc779a239	1	8	2009-09-03 11:29:27.314+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	30
f3c3ede2-2d25-4625-bd92-e3d4d419fe7a	1	8	2009-09-03 11:29:28.823+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	31
0b20272d-cf0c-406d-8e99-216b3360c39e	1	8	2009-09-03 11:29:30.317+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	32
2952bb44-7615-49de-995e-71b94f391928	1	8	2009-09-03 11:29:31.829+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	33
27ba5437-7de8-4778-ba8e-c4385f0c52b6	1	8	2009-09-03 11:29:33.37+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	34
b98a18ce-6153-4888-859a-62c8f034caab	1	8	2009-09-03 11:29:34.88+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	35
7fd527c2-d8d3-4901-8124-25a0400a50c0	1	14	2009-09-03 17:28:13.479+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	1
212595ee-ada3-4a42-9194-784ec2245b19	1	14	2009-09-03 17:28:13.519+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	2
cf2ccafd-9f66-42ff-95de-725a2b1dafea	1	14	2009-09-03 17:28:13.531+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	3
410a3c1a-66e9-489c-94a2-502a2d32d62b	1	14	2009-09-03 17:28:13.542+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	4
8e29c046-bc4f-41fb-8465-c7bb07a198e0	1	14	2009-09-03 17:28:13.554+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	5
85c6ada7-ba49-4a09-8178-4ee8ed8c7753	1	14	2009-09-03 17:28:13.565+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	6
62b5be98-663d-4325-9d77-a40f9cd76328	1	14	2009-09-03 17:28:13.577+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	7
f4722a11-8692-4194-ba52-4c84343e31db	1	14	2009-09-03 17:28:13.59+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	8
62592e22-6b47-4919-b95d-1f637748c995	1	14	2009-09-03 17:28:13.603+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	9
a617b5cc-7637-419b-9b8c-e3a00d580186	1	14	2009-09-03 17:28:13.615+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	10
8ac0296a-ae3d-43ba-89b3-2b570d19a848	1	14	2009-09-03 17:28:13.626+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	11
4cd4c143-3fd0-42a9-ae88-7d309072ab93	1	14	2009-09-03 17:28:13.638+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	12
969dd7f9-e05e-4809-be26-4913e7844778	1	14	2009-09-03 17:28:13.649+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	13
ba7cce99-2171-4ea3-a101-7790bcd9ddce	1	14	2009-09-03 17:28:13.662+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	14
8edb0c8b-e4cc-4905-881c-95eeb94b5c73	1	14	2009-09-03 17:28:13.673+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	15
bf1a48d6-bd28-4580-8233-cefaf2bc9b8e	1	14	2009-09-03 17:28:13.685+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	16
4b011d40-e751-46e8-9848-d681055fd09b	1	14	2009-09-03 17:28:13.722+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	17
59f5e9f2-217c-401f-bd94-ae8b5d4ac477	1	15	2009-09-10 11:20:54.132+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	1
2359111e-d725-438e-bb47-694f88b08138	1	17	2009-09-10 11:20:54.204+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	1
1d593a2b-4d70-43e5-ba35-e4497834ebf8	1	18	2009-09-10 11:20:54.237+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	1
634a4f5b-5dcb-4ca0-8880-bb7a67233c8b	1	19	2009-09-10 11:20:54.244+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2359111e-d725-438e-bb47-694f88b08138	\N	\N	\N	\N	\N	\N	\N	\N	1
d14a2d4b-f46a-4279-ae97-a3038475cd98	1	20	2009-09-10 11:20:54.254+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2359111e-d725-438e-bb47-694f88b08138	\N	\N	\N	\N	\N	\N	\N	\N	1
e20960f4-d35c-479f-93cc-1a807943e349	1	21	2009-09-10 11:20:54.269+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	634a4f5b-5dcb-4ca0-8880-bb7a67233c8b	\N	\N	\N	\N	\N	\N	\N	\N	1
8d7673ea-429e-4983-84ab-457e7318690b	1	20	2009-09-10 11:20:54.276+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2359111e-d725-438e-bb47-694f88b08138	\N	\N	\N	\N	\N	\N	\N	\N	2
0b230aa4-b2b5-4529-8a83-a96a4b29b7a8	1	8	2009-09-03 11:29:36.516+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	36
449d0150-bb83-4802-9656-dcd123cbe625	1	8	2009-09-03 11:29:38.11+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	37
fe45e3da-07d0-46a5-a8ab-56ef9a9c0710	1	8	2009-09-03 11:29:39.664+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	38
ac5e8259-d042-486b-9f14-3ec57cff9131	1	8	2009-09-03 11:29:41.219+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	39
890e8cff-722a-490e-b9ce-fc3adbca21b9	1	8	2009-09-03 11:29:42.817+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	40
9a0b256c-ba26-4e96-9ef9-1dd02d103807	1	8	2009-09-03 11:29:44.41+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	41
485c87b2-aa32-4b48-9547-137e5fe77632	1	8	2009-09-03 11:29:46.035+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	42
d1f4ca65-f18c-4c26-9db3-e0abac01b09b	1	8	2009-09-03 11:29:47.653+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	43
33bfce03-ad35-48b6-bb55-574514e7488b	1	8	2009-09-03 11:29:49.229+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	44
bae54187-4443-42bd-b46e-0a6c43c786ca	1	8	2009-09-03 11:29:50.876+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	45
69ce08dc-94ae-43a0-a3c5-9dd447b89ee9	1	8	2009-09-03 11:29:52.488+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	46
454c84e7-9265-4b3a-9fe8-07547493143a	1	8	2009-09-03 11:29:54.10+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	47
334b47b5-c44f-43f0-a00e-c30d0636508c	1	8	2009-09-03 11:29:55.807+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	48
ef2dd81a-cebd-47bd-88cf-ce99b33b414f	1	8	2009-09-03 11:29:57.491+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	49
f259842d-b847-4b17-9b24-6f80be4b538d	1	8	2009-09-03 11:29:59.162+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	50
6af9af20-caee-451f-b8ef-901be46519e3	1	8	2009-09-03 11:30:00.886+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	51
268fd0e1-bf02-4aab-b295-2b314f03757d	1	8	2009-09-03 11:30:02.58+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	52
807286f9-1611-464c-a7c6-ae1fc2020ba9	1	8	2009-09-03 11:30:04.296+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	53
fa36bb3c-67ed-4210-98e9-3e0403c2f0e2	1	8	2009-09-03 11:30:06.014+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	54
bc4b30df-5519-4479-9d59-358201613494	1	8	2009-09-03 11:30:07.666+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	55
c3a97976-fb79-4451-8c35-901fcc76c52f	1	8	2009-09-03 11:30:09.33+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	56
a5eb2f40-cc2f-4b54-87c9-d2a27c9e4c74	1	8	2009-09-03 11:30:11.058+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	57
0977fd52-e297-4a1c-a5bf-f27dc488788d	1	8	2009-09-03 11:30:12.797+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	58
325a5e74-a08f-41b1-8266-88d25d7d6a44	1	8	2009-09-03 11:30:14.594+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	59
856570ed-46e4-4577-a3cf-f34d40eccf10	1	8	2009-09-03 11:30:16.333+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	60
c0b949d1-47e7-410c-a015-f02589133a65	1	8	2009-09-03 11:30:18.076+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	61
6a6dd6aa-ed45-4cff-962c-e186ccdbeb18	1	8	2009-09-03 11:30:19.755+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	62
bb5fe455-5311-478c-b89b-71689f5df1e3	1	8	2009-09-03 11:30:21.421+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	63
22cc7885-dd38-4fd0-a871-074a9a8e7056	1	8	2009-09-03 11:30:23.133+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	64
2d719097-c655-413c-9f38-ba08cd74b5a2	1	8	2009-09-03 11:30:24.831+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	65
42c80efc-b7e3-4946-a322-1f83d16237aa	1	8	2009-09-03 11:30:26.518+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	66
9f9e64a9-4b51-4641-98f1-8edaa17e6ddc	1	8	2009-09-03 11:30:28.267+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	67
bcdc4392-e7d3-4bd2-b60a-04ecb0212288	1	8	2009-09-03 11:30:29.95+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	68
90659f0b-5940-41c0-84ab-a39b4b9806f5	1	8	2009-09-03 11:30:31.637+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	69
5391312a-4f6e-49b6-b7e5-477e128063ec	1	8	2009-09-03 11:30:33.382+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	70
3dfafecb-a1ae-4fc5-be19-7a3f018ceeb7	1	14	2009-09-03 17:28:13.734+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	18
b7a86127-8bf2-438c-8a43-859cd163480b	1	8	2009-09-03 11:30:35.13+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	71
6ddcb918-de21-489e-9a6a-7c9648a7ae55	1	14	2009-09-03 17:28:13.745+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	19
76c90a1d-b263-419b-96c5-e54f43f19283	1	14	2009-09-03 17:28:13.756+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	20
1e6a3858-d31f-47ef-80bf-a7bfb55949be	1	14	2009-09-03 17:28:13.768+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	21
7fd16045-43b4-47d2-a9c1-7f784889a782	1	14	2009-09-03 17:28:13.779+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	22
eb244766-8aa8-4835-a736-a498685c8754	1	14	2009-09-03 17:28:13.791+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	23
db219363-d172-4a5a-b812-078666b2f599	1	14	2009-09-03 17:28:13.802+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	24
19b5de76-09dc-466c-950b-0c984cfb8585	1	14	2009-09-03 17:28:13.814+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	25
69bf9d2e-b502-4028-8b62-ab9e5fa40dd2	1	14	2009-09-03 17:28:13.826+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	26
57a98665-4d24-472c-bf3a-ffe094c6a034	1	14	2009-09-03 17:28:13.838+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	27
377e28d7-cf34-42a4-b348-9052daa0da0a	1	14	2009-09-03 17:28:13.85+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	28
8a0a2dc4-7515-4022-aff6-1a365ae3b2ce	1	14	2009-09-03 17:28:13.862+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	29
36dd2d1d-c848-4295-a4b4-256965e1bb4e	1	14	2009-09-03 17:28:13.874+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	30
45e16ccc-28b4-422b-adf6-1288275855f3	1	14	2009-09-03 17:28:13.886+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	31
cc69d884-dc59-4210-869c-9b9c518e25ab	1	14	2009-09-03 17:28:13.898+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	32
e88bf418-3854-4a6b-8ad5-beedebe12962	1	14	2009-09-03 17:28:13.913+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	33
3bf9743c-aa66-487e-8a25-38aa45b93c16	1	14	2009-09-03 17:28:13.926+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	34
063c96a4-68e7-4369-9022-6afdd75b743d	1	21	2009-09-10 11:20:54.318+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	634a4f5b-5dcb-4ca0-8880-bb7a67233c8b	\N	\N	\N	\N	\N	\N	\N	\N	2
9dbf0dd2-456b-4c90-b464-b7fe05f7efae	1	20	2009-09-10 11:20:54.324+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2359111e-d725-438e-bb47-694f88b08138	\N	\N	\N	\N	\N	\N	\N	\N	3
a860ed6d-972c-4228-808c-587305b50b5c	1	21	2009-09-10 11:20:54.346+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	634a4f5b-5dcb-4ca0-8880-bb7a67233c8b	\N	\N	\N	\N	\N	\N	\N	\N	3
4525318e-b3bb-4352-86db-86b6c965ce7c	1	23	2009-09-10 11:20:54.365+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	1
0ba3db16-4c8a-4e96-8588-8902f3a96c9a	1	24	2009-09-10 11:20:54.374+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	5
14c36825-d016-49ef-a679-0f4f658ac6ae	1	28	2009-09-10 11:20:54.458+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	1
bef3731b-7436-45c2-998e-fdf1f882ad76	1	30	2009-09-10 11:20:54.469+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	1
7af91677-67ce-41dd-8218-419a78f32abb	1	8	2009-09-03 11:30:36.843+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	72
33213f51-57d2-4ec8-8b1a-bb70d6bb8aa4	1	8	2009-09-03 11:30:38.57+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	73
e3c45978-edaf-4094-94b8-ce9009af7fe6	1	8	2009-09-03 11:30:40.334+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	74
debcc516-3783-4e75-ab7f-92cbf93ad5cf	1	8	2009-09-03 11:30:42.101+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	75
0a2c0d37-7540-410d-bab8-2bb431970b25	1	8	2009-09-03 11:30:43.859+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	76
d8c3f215-07a0-4347-8a4f-91650d57f4ac	1	8	2009-09-03 11:30:45.634+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	77
1021ac13-e318-4904-8203-33998c5103c2	1	8	2009-09-03 11:30:47.394+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	78
fa26c411-c010-44ce-acf0-199b133480d1	1	8	2009-09-03 11:30:49.139+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	79
0981e343-67bd-465a-aaac-5cc60583dd9a	1	8	2009-09-03 11:30:50.943+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	80
a7a46b2e-a11f-4ac8-bc7a-ac30e1292392	1	8	2009-09-03 11:30:52.708+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	81
4bddbb87-89ca-45ad-bb49-93ef03091b2f	1	8	2009-09-03 11:30:54.503+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	82
f55dd39a-db59-47d2-af38-008cd8205b4e	1	8	2009-09-03 11:30:56.33+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	83
a939eff3-b0d3-4fb8-ac5a-ffb0821a9703	1	8	2009-09-03 11:30:58.147+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	84
b351de49-b92b-4c82-be0d-57dcb71de50e	1	8	2009-09-03 11:31:00.186+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	85
b0bf20c6-78b2-48ac-8cb6-c7edd90d6158	1	8	2009-09-03 11:31:02.008+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	86
ca5bc7eb-533a-4196-bc09-4924d07670be	1	8	2009-09-03 11:31:03.833+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	87
3b978d7f-05fb-40e7-8d7e-a5c5b8242107	1	8	2009-09-03 11:31:05.683+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	88
81438d8b-69df-4e90-a074-dcf3b49fedd9	1	8	2009-09-03 11:31:07.526+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	89
72d4b361-f27f-4f69-93fb-6867bf14f71b	1	8	2009-09-03 11:31:09.33+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	90
f0fa8f08-513f-465c-b76c-a997a1c59f5b	1	8	2009-09-03 11:31:11.211+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	91
e17d5e9f-3402-4c50-bf50-5cd2254ce70a	1	8	2009-09-03 11:31:13.086+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	92
44673264-9f61-4cd5-b554-3e2fd00debf4	1	8	2009-09-03 11:31:14.944+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	93
81835c0e-5a43-46a1-915f-0ca994f0e67a	1	8	2009-09-03 11:31:16.845+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	94
fea5b22b-b181-4001-87cf-eee4bcb5d8e2	1	8	2009-09-03 11:31:18.70+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	95
4ccc6253-e1e4-4d59-9dc0-60e750f71f41	1	14	2009-09-03 17:28:13.938+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	35
3fe391fe-60bb-4f7d-907b-7ceec7149843	1	14	2009-09-03 17:28:13.951+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	36
2f751b9f-6859-4fe6-9c08-ed70520b038d	1	14	2009-09-03 17:28:13.963+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	37
cf39ec32-4efc-4152-828b-c30898bf5fa3	1	14	2009-09-03 17:28:13.976+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	38
6c463691-62ab-492d-b839-e0b8643bbfc5	1	14	2009-09-03 17:28:13.988+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	39
765d5704-9c89-486e-a0c6-08a6d2bd108d	1	14	2009-09-03 17:28:14+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	40
31202b17-cf09-453b-9149-da756b68e03e	1	14	2009-09-03 17:28:14.013+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	41
3913542b-eee4-4ad6-9d09-837e4566ee06	1	15	2009-09-03 17:28:14.027+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	3913542b-eee4-4ad6-9d09-837e4566ee06	\N	\N	\N	\N	\N	\N	\N	\N	1
b95f06b6-5dbb-4dbe-9cfc-675db27360a3	1	15	2009-09-03 17:39:36.223+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	b95f06b6-5dbb-4dbe-9cfc-675db27360a3	\N	\N	\N	\N	\N	\N	\N	\N	1
ac570f14-97cf-4c41-ab28-33739467c642	1	16	2009-09-04 06:35:25.179+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	1
0d3b60e0-741c-470e-b8c7-f7b1ec2378cc	1	15	2009-09-04 06:35:25.453+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	0d3b60e0-741c-470e-b8c7-f7b1ec2378cc	\N	\N	\N	\N	\N	\N	\N	\N	1
579dea1f-be0d-442f-9a14-96dd98cacbe6	1	17	2009-09-04 06:35:25.679+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	0d3b60e0-741c-470e-b8c7-f7b1ec2378cc	\N	\N	\N	\N	\N	\N	\N	\N	1
a549eda7-b362-4151-909f-8ed67cbdc5ab	1	15	2009-09-04 15:25:19.067+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a549eda7-b362-4151-909f-8ed67cbdc5ab	\N	\N	\N	\N	\N	\N	\N	\N	1
0b4a24b7-8508-4bb0-ae06-56858479bd49	1	17	2009-09-04 15:25:19.125+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a549eda7-b362-4151-909f-8ed67cbdc5ab	\N	\N	\N	\N	\N	\N	\N	\N	1
dd33f937-b181-4756-93db-69c8f9e83c8a	1	18	2009-09-04 15:25:19.211+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a549eda7-b362-4151-909f-8ed67cbdc5ab	\N	\N	\N	\N	\N	\N	\N	\N	1
0ed807a1-ab5a-4fdb-85b7-c4da2d9043d8	1	19	2009-09-04 15:25:19.219+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	0b4a24b7-8508-4bb0-ae06-56858479bd49	\N	\N	\N	\N	\N	\N	\N	\N	1
bc9193e5-c019-4db6-8281-cad9ed70a9dd	1	20	2009-09-04 15:25:19.282+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	0b4a24b7-8508-4bb0-ae06-56858479bd49	\N	\N	\N	\N	\N	\N	\N	\N	1
b4aa50db-bea8-43c1-b78f-6eef5bbdc924	1	21	2009-09-04 15:25:19.341+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	0ed807a1-ab5a-4fdb-85b7-c4da2d9043d8	\N	\N	\N	\N	\N	\N	\N	\N	1
3b7f687e-a15f-42e9-90ab-630245e5725f	1	20	2009-09-04 15:25:19.489+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	0b4a24b7-8508-4bb0-ae06-56858479bd49	\N	\N	\N	\N	\N	\N	\N	\N	2
63cf5f43-4531-444d-8c65-77c9bc02f7bc	1	21	2009-09-04 15:25:19.547+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	0ed807a1-ab5a-4fdb-85b7-c4da2d9043d8	\N	\N	\N	\N	\N	\N	\N	\N	2
7c9a2dab-f502-4815-9ff2-a303c402f5ca	1	20	2009-09-04 15:25:19.554+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	0b4a24b7-8508-4bb0-ae06-56858479bd49	\N	\N	\N	\N	\N	\N	\N	\N	3
7acc5358-3e3d-4bac-87d9-409d486b26bd	1	21	2009-09-04 15:25:19.57+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	0ed807a1-ab5a-4fdb-85b7-c4da2d9043d8	\N	\N	\N	\N	\N	\N	\N	\N	3
bf7970b1-fa34-41f0-b804-57bf0d95c3d2	1	31	2009-09-10 11:20:54.481+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	0ba3db16-4c8a-4e96-8588-8902f3a96c9a	\N	\N	\N	\N	\N	\N	\N	\N	1
5256e8e8-6b0b-4feb-8afa-1322c753125b	1	32	2009-09-10 11:20:56.755+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	1
15aa3796-97bb-4845-8e41-1985d90bb231	1	32	2009-09-10 11:20:56.767+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	2
f567fade-1fb4-4929-aba6-9063c77d0500	1	32	2009-09-10 11:20:56.776+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	3
516573bb-5b78-4ef3-996f-f953697c940f	1	32	2009-09-10 11:20:56.785+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	4
93f632cc-ab5b-4bd9-8ec6-5c7a3b8309d4	1	32	2009-09-10 11:20:56.795+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	5
e9024e72-7172-4837-a3eb-3693fccdb784	1	32	2009-09-10 11:20:56.804+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	6
6152c6ea-d516-42d9-a2a8-a2b182700cef	1	32	2009-09-10 11:20:56.813+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	7
6cbdc05c-1c53-4923-aca0-66dd762c04e3	1	32	2009-09-10 11:20:56.822+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	8
e7dc4455-6e3d-401d-b358-a7cd2b93f3f0	1	18	2009-09-16 22:07:19.286+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	2
86ebff02-8986-491a-bff8-759080559669	1	23	2009-09-16 22:07:19.309+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	2
eef7ffa5-f7cf-4a71-a250-a4741608df9f	1	32	2009-09-10 11:20:56.831+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	9
55144634-4519-4401-b042-f8d8ea3747e8	1	15	2009-09-04 16:43:03.075+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	55144634-4519-4401-b042-f8d8ea3747e8	\N	\N	\N	\N	\N	\N	\N	\N	1
9a8629a4-e9ef-4865-a17c-0455b8d44e9b	1	17	2009-09-04 16:43:03.116+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	55144634-4519-4401-b042-f8d8ea3747e8	\N	\N	\N	\N	\N	\N	\N	\N	1
90deb642-f53d-4cf1-8182-58f148517c76	1	18	2009-09-04 16:43:03.147+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	55144634-4519-4401-b042-f8d8ea3747e8	\N	\N	\N	\N	\N	\N	\N	\N	1
ac275e03-0ee3-40e9-b0ad-e38207569100	1	19	2009-09-04 16:43:03.154+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	9a8629a4-e9ef-4865-a17c-0455b8d44e9b	\N	\N	\N	\N	\N	\N	\N	\N	1
b8d3f22e-d4f7-4148-a2b0-73d9cb402caf	1	20	2009-09-04 16:43:03.165+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	9a8629a4-e9ef-4865-a17c-0455b8d44e9b	\N	\N	\N	\N	\N	\N	\N	\N	1
7b370dac-4715-4b4f-b0b3-5bbea11f7e44	1	21	2009-09-04 16:43:03.179+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ac275e03-0ee3-40e9-b0ad-e38207569100	\N	\N	\N	\N	\N	\N	\N	\N	1
d8acc385-d444-446b-aa58-6eb06fdffa8b	1	20	2009-09-04 16:43:03.187+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	9a8629a4-e9ef-4865-a17c-0455b8d44e9b	\N	\N	\N	\N	\N	\N	\N	\N	2
1c796240-669c-473d-8b4a-0cc353dbba93	1	21	2009-09-04 16:43:03.199+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ac275e03-0ee3-40e9-b0ad-e38207569100	\N	\N	\N	\N	\N	\N	\N	\N	2
09ab6d6a-1dce-4b0c-ba9c-ec40b5de281b	1	20	2009-09-04 16:43:03.205+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	9a8629a4-e9ef-4865-a17c-0455b8d44e9b	\N	\N	\N	\N	\N	\N	\N	\N	3
f0dfe43e-8b61-4091-a74c-0fef167ee1f9	1	21	2009-09-04 16:43:03.215+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ac275e03-0ee3-40e9-b0ad-e38207569100	\N	\N	\N	\N	\N	\N	\N	\N	3
e39dfc32-e5ef-4613-b363-653e80b86c7b	1	23	2009-09-04 16:43:03.235+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	55144634-4519-4401-b042-f8d8ea3747e8	\N	\N	\N	\N	\N	\N	\N	\N	1
d03b857f-b05b-47ae-bd0d-eefd83b83372	1	15	2009-09-09 07:42:54.228+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d03b857f-b05b-47ae-bd0d-eefd83b83372	\N	\N	\N	\N	\N	\N	\N	\N	1
2a21a87f-6d38-4d91-aa87-93b03e7e9818	1	17	2009-09-09 07:42:54.305+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d03b857f-b05b-47ae-bd0d-eefd83b83372	\N	\N	\N	\N	\N	\N	\N	\N	1
10bb09e9-c2f9-4901-bab3-c37e9bde8779	1	18	2009-09-09 07:42:54.436+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d03b857f-b05b-47ae-bd0d-eefd83b83372	\N	\N	\N	\N	\N	\N	\N	\N	1
c2297ba2-f79b-4f0d-b819-25250eac0537	1	19	2009-09-09 07:42:54.444+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2a21a87f-6d38-4d91-aa87-93b03e7e9818	\N	\N	\N	\N	\N	\N	\N	\N	1
4892a6c6-5dd1-489d-822d-e8f8d2da3415	1	20	2009-09-09 07:42:54.50+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2a21a87f-6d38-4d91-aa87-93b03e7e9818	\N	\N	\N	\N	\N	\N	\N	\N	1
9f23c7ed-0183-406b-8e41-7a13a6e9a398	1	21	2009-09-09 07:42:54.514+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c2297ba2-f79b-4f0d-b819-25250eac0537	\N	\N	\N	\N	\N	\N	\N	\N	1
dac9cd37-2d17-4769-b42c-0b0d2e80797c	1	20	2009-09-09 07:42:54.522+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2a21a87f-6d38-4d91-aa87-93b03e7e9818	\N	\N	\N	\N	\N	\N	\N	\N	2
82d735b8-63c6-4aeb-b3fd-378f38b71051	1	21	2009-09-09 07:42:54.537+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c2297ba2-f79b-4f0d-b819-25250eac0537	\N	\N	\N	\N	\N	\N	\N	\N	2
37d0d11d-afe8-432c-8b27-d2dc8d86539e	1	20	2009-09-09 07:42:54.544+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2a21a87f-6d38-4d91-aa87-93b03e7e9818	\N	\N	\N	\N	\N	\N	\N	\N	3
4741b18f-5c93-4985-849d-d08cc6d6f3f6	1	21	2009-09-09 07:42:54.556+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c2297ba2-f79b-4f0d-b819-25250eac0537	\N	\N	\N	\N	\N	\N	\N	\N	3
3b479a21-9185-45af-bbbe-6e1f3e232581	1	23	2009-09-09 07:42:54.577+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d03b857f-b05b-47ae-bd0d-eefd83b83372	\N	\N	\N	\N	\N	\N	\N	\N	1
e5d5c576-ae35-43de-a67a-40f56ae598d0	1	15	2009-09-09 08:01:19.677+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	1
d4496096-f414-48e9-bdd2-7fc6f81fd673	1	17	2009-09-09 08:01:19.742+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	1
f4a17bae-e34c-43c2-a251-3ba0a26f7355	1	18	2009-09-09 08:01:19.781+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	1
8d73494b-59cd-46f9-90e1-a884f9dfa9e3	1	19	2009-09-09 08:01:19.788+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d4496096-f414-48e9-bdd2-7fc6f81fd673	\N	\N	\N	\N	\N	\N	\N	\N	1
559bf5aa-c38a-4be6-b1e7-686344013812	1	20	2009-09-09 08:01:19.80+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d4496096-f414-48e9-bdd2-7fc6f81fd673	\N	\N	\N	\N	\N	\N	\N	\N	1
e63e51a8-5152-4d9a-9255-a32e8821e011	1	21	2009-09-09 08:01:19.814+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8d73494b-59cd-46f9-90e1-a884f9dfa9e3	\N	\N	\N	\N	\N	\N	\N	\N	1
61734856-7d9e-4f93-bf93-c0309435d68d	1	20	2009-09-09 08:01:19.821+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d4496096-f414-48e9-bdd2-7fc6f81fd673	\N	\N	\N	\N	\N	\N	\N	\N	2
017234ac-fb19-4b13-bd9b-ca7fee1cb2e5	1	21	2009-09-09 08:01:19.834+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8d73494b-59cd-46f9-90e1-a884f9dfa9e3	\N	\N	\N	\N	\N	\N	\N	\N	2
78494fe1-8566-43ab-b068-5dcddf63d508	1	20	2009-09-09 08:01:19.84+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d4496096-f414-48e9-bdd2-7fc6f81fd673	\N	\N	\N	\N	\N	\N	\N	\N	3
d3d9d126-f4cd-4229-b7d5-812196a4e130	1	21	2009-09-09 08:01:19.851+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8d73494b-59cd-46f9-90e1-a884f9dfa9e3	\N	\N	\N	\N	\N	\N	\N	\N	3
154999a4-e532-4a03-b1e4-9290fc81c447	1	23	2009-09-09 08:01:19.872+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	1
7eec40a2-832b-433d-9f1f-55801228832c	1	24	2009-09-09 08:01:19.914+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	1
aa0165b9-5a3b-46ea-90a0-cd4279d6f070	1	25	2009-09-09 08:02:38.485+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	1
bd0152bd-08ef-4cc4-a2d9-d723c1a7caef	1	25	2009-09-09 08:02:38.495+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	2
3f990336-3f49-4cf3-88db-172300eebd82	1	25	2009-09-09 08:02:38.521+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	3
91f0bc11-5168-4d8e-a814-3e684a930044	1	25	2009-09-09 08:02:38.529+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	4
9adc8609-50d8-4cef-b1f8-9a54b056003d	1	25	2009-09-09 08:02:38.536+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	5
70efc357-a8dd-448c-a52b-edce67ee2872	1	25	2009-09-09 08:02:38.543+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	6
a421ed12-f36d-4069-93f9-c43eb3a43ba7	1	25	2009-09-09 08:02:38.549+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	7
53b98041-8010-4f56-9997-d11705a25418	1	25	2009-09-09 08:02:38.557+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	8
4dfb2999-bbd9-43a4-a606-9d54cde9738b	1	25	2009-09-09 08:02:38.564+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	9
cc8b0df1-4c80-4388-8afc-0dd2e8c5cec3	1	25	2009-09-09 08:02:38.569+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	10
420d0a7f-b605-4bd3-8bfb-03346a35254d	1	25	2009-09-09 08:02:38.576+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	11
98f9e7e6-3553-4bc1-b400-9458fd24753f	1	25	2009-09-09 08:02:38.582+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	12
ec5fde4c-9918-4f45-8f79-23142298ddb4	1	26	2009-09-09 08:02:38.626+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	1
43a5b7fd-63a2-4be1-8606-3da5a8931c20	1	32	2009-09-10 11:20:56.839+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	10
93d8d508-9cf4-4748-812d-c4090f9f5a5a	1	32	2009-09-10 11:20:56.848+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	11
db7ff011-6e32-48cf-99f2-10095c611bf7	1	32	2009-09-10 11:20:56.857+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	12
ceedfae5-bc63-4c5c-bd5b-2edd4f1e3e40	1	33	2009-09-10 11:20:56.871+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	bef3731b-7436-45c2-998e-fdf1f882ad76	\N	\N	\N	\N	\N	\N	\N	\N	1
75ad116d-3322-4fad-b023-2549a3587275	1	34	2009-09-10 11:20:56.884+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ceedfae5-bc63-4c5c-bd5b-2edd4f1e3e40	\N	\N	\N	\N	\N	\N	\N	\N	1
da8938b5-1044-4e95-9f76-b0602341f3f1	1	34	2009-09-10 11:20:56.896+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ceedfae5-bc63-4c5c-bd5b-2edd4f1e3e40	\N	\N	\N	\N	\N	\N	\N	\N	2
dc9f5540-bde6-4242-ae6a-d876edb643c1	1	27	2009-09-09 08:02:38.667+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	1
c5dbee81-5d5c-4b3f-8721-17b151868b18	1	27	2009-09-09 08:02:38.69+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	2
377aad89-e1ec-4ff3-b37b-749ab1eb3ca2	1	27	2009-09-09 08:02:38.699+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	3
cdb140df-5057-4fd2-8a81-a248cb1cd1aa	1	27	2009-09-09 08:02:38.709+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	4
d85c3433-9b7a-4378-9db7-ace1022b4a66	1	27	2009-09-09 08:02:38.721+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	5
6dfeb830-b52f-477d-a85f-c55ef9af9756	1	27	2009-09-09 08:02:38.733+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	6
30eac8d0-460b-44ce-a656-67f964848459	1	27	2009-09-09 08:02:38.744+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	7
4aa6dcee-8778-45fa-b3da-5b20f89ab42c	1	27	2009-09-09 08:02:38.755+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	8
a682b82b-6be9-4ad7-9c14-a1b6dced0b6c	1	27	2009-09-09 08:02:38.765+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	9
1f1c1d12-c3e2-4a5d-9f1b-1aa115be7028	1	27	2009-09-09 08:02:38.775+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	10
dc4f88bd-8b7d-4356-a332-de19cd5d0870	1	27	2009-09-09 08:02:38.785+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	11
cf4f3b34-51b9-44ff-86d8-e1848538a59b	1	27	2009-09-09 08:02:38.795+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	12
2a694fa5-4bfc-4483-9f92-b82b8348158b	1	27	2009-09-09 08:02:38.805+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	13
044770d4-8551-4812-8ca7-15e4ac9f8a34	1	27	2009-09-09 08:02:38.815+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	14
8685a9f3-88cb-4a3c-a93f-85e113e07f1d	1	27	2009-09-09 08:02:38.837+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	15
008538aa-e24c-4900-b0e5-b92a5cd2a682	1	27	2009-09-09 08:02:38.85+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	16
697ee3a6-e644-490f-a40e-9fc4f6606c1e	1	27	2009-09-09 08:02:38.862+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	17
ccf0d12f-2782-4e74-9a10-06d4e1d1eb58	1	27	2009-09-09 08:02:38.872+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	18
cb5edd24-4c75-49a2-8417-a385e57713d1	1	27	2009-09-09 08:02:38.882+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	19
734a1045-ee6e-43e0-98ce-3e702349f68b	1	27	2009-09-09 08:02:38.892+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	20
04706977-7b4d-41bf-aff1-44211590bda8	1	27	2009-09-09 08:02:38.902+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	21
9dcbeaa0-1f29-4c97-a471-4edeb1dc57ea	1	27	2009-09-09 08:02:38.913+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	22
1e5323b2-ee09-463d-a327-18e09414fb5d	1	27	2009-09-09 08:02:38.924+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	23
564da66a-dbbb-4a5f-8435-7074366d974e	1	27	2009-09-09 08:02:38.935+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	24
ace51a8c-53c7-49a5-86e7-b7d92e387d66	1	27	2009-09-09 08:02:38.947+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	25
347e0a6e-c6dd-4905-bd9c-53600651c464	1	27	2009-09-09 08:02:38.958+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	26
de192000-2180-4089-b337-dbc0e5c2907a	1	27	2009-09-09 08:02:38.969+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	27
58f8090e-3acc-4417-9254-7d857101cd03	1	27	2009-09-09 08:02:38.98+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	28
69106cab-32ae-4f30-b485-42abc393b1ad	1	27	2009-09-09 08:02:38.99+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	29
be7514e2-0b0e-4220-a872-35379870871a	1	27	2009-09-09 08:02:39+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	30
7af4dd72-6304-487c-b0ff-97c0aad05fb8	1	27	2009-09-09 08:02:39.01+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	31
26fe890a-91ea-4902-bfbb-6586057ae748	1	27	2009-09-09 08:02:39.021+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	32
2b55b8e0-1579-4234-bbfd-7e9da8396673	1	34	2009-09-10 11:20:57.054+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ceedfae5-bc63-4c5c-bd5b-2edd4f1e3e40	\N	\N	\N	\N	\N	\N	\N	\N	3
f14a1852-5721-44dd-8a97-39feeadf8c81	1	34	2009-09-10 11:20:57.065+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ceedfae5-bc63-4c5c-bd5b-2edd4f1e3e40	\N	\N	\N	\N	\N	\N	\N	\N	4
b16fd1a1-8634-4649-a021-b5dbe0b84ad4	1	34	2009-09-10 11:20:57.075+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ceedfae5-bc63-4c5c-bd5b-2edd4f1e3e40	\N	\N	\N	\N	\N	\N	\N	\N	5
c14623f3-563d-4c17-8d14-0930396990c6	1	34	2009-09-10 11:20:57.086+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ceedfae5-bc63-4c5c-bd5b-2edd4f1e3e40	\N	\N	\N	\N	\N	\N	\N	\N	6
82864ae9-de81-4bd0-a489-84ee6ffc9b63	1	34	2009-09-10 11:20:57.097+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ceedfae5-bc63-4c5c-bd5b-2edd4f1e3e40	\N	\N	\N	\N	\N	\N	\N	\N	7
e00ed998-e048-4230-a1d1-aa8ec810dd2b	1	34	2009-09-10 11:20:57.107+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ceedfae5-bc63-4c5c-bd5b-2edd4f1e3e40	\N	\N	\N	\N	\N	\N	\N	\N	8
c9e82f1c-51f8-41fc-a638-bbc2482d6bf2	1	34	2009-09-10 11:20:57.118+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ceedfae5-bc63-4c5c-bd5b-2edd4f1e3e40	\N	\N	\N	\N	\N	\N	\N	\N	9
76edfbb4-dc68-44e0-94b3-a02c97b252fe	1	27	2009-09-10 11:24:17.335+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	37
d67e25ea-dc6a-4a65-bc1f-aa2738056fd2	1	27	2009-09-10 11:24:17.345+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	38
56f2ad33-86bb-47f4-8d31-d5cda5343171	1	27	2009-09-10 11:24:17.356+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	39
658c1ed4-b0d9-41ad-8b7f-3e4ac549db8e	1	27	2009-09-10 11:24:17.365+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	40
35cc7bec-0d66-4e6f-84f4-7f021bc776e6	1	27	2009-09-10 11:24:17.375+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	41
ce110f75-a6aa-49a8-a9d3-4c6fb194298b	1	27	2009-09-10 11:24:17.386+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	42
2393c53c-0394-4a2c-aac9-d9a402596799	1	27	2009-09-10 11:24:17.397+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	43
1dc87923-c4da-4083-92ca-0e2cdc6439db	1	27	2009-09-10 11:24:17.408+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	44
246def71-6eb7-46ef-a739-6622e6301d3f	1	27	2009-09-10 11:24:17.419+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	45
ab6fe3f7-d3a2-4c0f-a16d-168307d28be7	1	27	2009-09-10 11:24:17.43+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	46
53e7cb98-7cd7-4396-ab75-e92e5a49d6bb	1	18	2009-09-10 11:57:43.544+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	1
99fe545b-ffe1-497f-b6a0-f2bf7f1bc9dc	1	19	2009-09-10 11:57:43.552+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	0d6c973b-71ea-4070-9c22-620fcc6005d5	\N	\N	\N	\N	\N	\N	\N	\N	1
36a9fe57-1879-466c-8646-d46bb10fc03f	1	20	2009-09-10 11:57:43.588+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	0d6c973b-71ea-4070-9c22-620fcc6005d5	\N	\N	\N	\N	\N	\N	\N	\N	1
80d091ef-8182-4b71-a921-42a703638f9b	1	21	2009-09-10 11:57:43.604+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	99fe545b-ffe1-497f-b6a0-f2bf7f1bc9dc	\N	\N	\N	\N	\N	\N	\N	\N	1
d5a6d882-91d8-4bbd-8622-8ee1175bf3af	1	20	2009-09-10 11:57:43.613+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	0d6c973b-71ea-4070-9c22-620fcc6005d5	\N	\N	\N	\N	\N	\N	\N	\N	2
dd6ad5e1-ed14-4cc6-83df-a236ad54865f	1	21	2009-09-10 11:57:43.625+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	99fe545b-ffe1-497f-b6a0-f2bf7f1bc9dc	\N	\N	\N	\N	\N	\N	\N	\N	2
5412d16f-34c4-411c-9766-6b9146b8f255	1	27	2009-09-09 08:02:39.031+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	33
5ce0587e-de8f-493a-9d9b-a66ce9932b77	1	27	2009-09-09 08:02:39.043+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	34
abb0251e-24aa-4a95-81d3-4ef1e4a7e7bb	1	27	2009-09-09 08:02:39.054+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	35
72115fc2-f0b7-43fb-8972-e3b61fbc617e	1	27	2009-09-09 08:02:39.066+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	36
e0d2f909-c86a-41f3-aa4a-b0cf25d7feda	1	27	2009-09-09 08:02:39.078+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	37
d914368c-1747-43db-a5ac-d97cb6913783	1	27	2009-09-09 08:02:39.089+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	38
9bbca90c-5e8b-43fe-9e5f-4354d7923de2	1	27	2009-09-09 08:02:39.101+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	39
13edd393-97fa-48a2-b472-7412d80a6c58	1	27	2009-09-09 08:02:39.113+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	40
b7db351a-c2f9-46a4-9d2b-3dd16a5751d6	1	27	2009-09-09 08:02:39.124+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	41
0a267a2f-0862-413f-a37e-3260b38753d1	1	27	2009-09-09 08:02:39.135+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	42
f5ad83b9-9373-4055-9adb-d9bca514eab3	1	27	2009-09-09 08:02:39.147+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	43
ec29ed10-36fe-454f-8cc6-7521b003151b	1	27	2009-09-09 08:02:39.159+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	44
3334eede-0bb5-4e3e-8836-96ec68b0d903	1	27	2009-09-09 08:02:39.171+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	45
a74f0887-6545-4b08-82c4-81cdae01b0a8	1	27	2009-09-09 08:02:39.183+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	46
2ab2ffc5-cc9d-4e8d-97ed-8c5438d5eb1c	1	28	2009-09-09 08:02:39.229+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	1
514c2c30-e78f-493a-b2b0-bf7c1eebad82	1	29	2009-09-09 08:02:39.242+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	2ab2ffc5-cc9d-4e8d-97ed-8c5438d5eb1c	\N	\N	\N	\N	\N	\N	\N	\N	1
2b859e80-89c5-439a-bbba-05cd8cd0a3d3	1	28	2009-09-09 08:02:39.313+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	2
154753f8-81d5-49c7-8121-20e8b5c19aae	1	29	2009-09-09 08:02:39.34+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	2b859e80-89c5-439a-bbba-05cd8cd0a3d3	\N	\N	\N	\N	\N	\N	\N	\N	1
f5307c8d-d889-43b1-8049-8dc2ddadcff8	1	28	2009-09-09 08:02:39.35+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	3
67adae23-e250-4f59-bdc2-b9c24a2531a4	1	29	2009-09-09 08:02:39.36+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	f5307c8d-d889-43b1-8049-8dc2ddadcff8	\N	\N	\N	\N	\N	\N	\N	\N	1
4226b59c-57f6-42e8-9f67-263d1b0dd00e	1	28	2009-09-09 08:02:39.372+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	4
0be63518-4430-48b4-b2b4-ad141c184fc9	1	29	2009-09-09 08:02:39.382+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	4226b59c-57f6-42e8-9f67-263d1b0dd00e	\N	\N	\N	\N	\N	\N	\N	\N	1
a415d4bf-1dd7-48bf-9c18-e6f93aef0f5c	1	28	2009-09-09 08:02:39.394+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	5
ab849a87-e5e7-424d-8122-7ceb9a51788c	1	29	2009-09-09 08:02:39.405+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	a415d4bf-1dd7-48bf-9c18-e6f93aef0f5c	\N	\N	\N	\N	\N	\N	\N	\N	1
7e8a23c3-03a7-47ff-b7b5-197a656f0c0b	1	28	2009-09-09 08:02:39.418+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	6
4ac4a674-af92-4a4a-8435-a396cb3534a5	1	29	2009-09-09 08:02:39.431+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	7e8a23c3-03a7-47ff-b7b5-197a656f0c0b	\N	\N	\N	\N	\N	\N	\N	\N	1
fc2ba973-8056-4885-b1b7-647b0ffffe9b	1	28	2009-09-09 08:02:39.444+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	e5d5c576-ae35-43de-a67a-40f56ae598d0	\N	\N	\N	\N	\N	\N	\N	\N	7
1e0c47c5-d4bf-4ef1-a828-5d8974887df4	1	29	2009-09-09 08:02:39.457+03	154999a4-e532-4a03-b1e4-9290fc81c447	d4496096-f414-48e9-bdd2-7fc6f81fd673	f	f	f	f	f	fc2ba973-8056-4885-b1b7-647b0ffffe9b	\N	\N	\N	\N	\N	\N	\N	\N	1
70c2dab0-1a08-411b-b18e-7b8bf0aacaa3	1	15	2009-09-10 00:00:15.85+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	70c2dab0-1a08-411b-b18e-7b8bf0aacaa3	\N	\N	\N	\N	\N	\N	\N	\N	1
841fa815-93ca-4570-8863-0134491b45ae	1	17	2009-09-10 00:00:15.932+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	70c2dab0-1a08-411b-b18e-7b8bf0aacaa3	\N	\N	\N	\N	\N	\N	\N	\N	1
83524976-6550-4967-b8ae-3ef87bdb48bf	1	18	2009-09-10 00:00:16.094+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	70c2dab0-1a08-411b-b18e-7b8bf0aacaa3	\N	\N	\N	\N	\N	\N	\N	\N	1
c511a203-8c59-4361-915d-98cd992169c1	1	19	2009-09-10 00:00:16.102+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	841fa815-93ca-4570-8863-0134491b45ae	\N	\N	\N	\N	\N	\N	\N	\N	1
ddbf4822-d9d0-4a41-993a-91036ae4c600	1	20	2009-09-10 00:00:16.132+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	841fa815-93ca-4570-8863-0134491b45ae	\N	\N	\N	\N	\N	\N	\N	\N	1
1016fefe-4644-4530-88af-e290187c8613	1	21	2009-09-10 00:00:16.148+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c511a203-8c59-4361-915d-98cd992169c1	\N	\N	\N	\N	\N	\N	\N	\N	1
1f11000e-c2ee-4b51-8111-b3ab7e99bbfb	1	20	2009-09-10 00:00:16.156+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	841fa815-93ca-4570-8863-0134491b45ae	\N	\N	\N	\N	\N	\N	\N	\N	2
54b0338e-1d9a-492e-8b33-1e4deeb3afde	1	21	2009-09-10 00:00:16.17+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c511a203-8c59-4361-915d-98cd992169c1	\N	\N	\N	\N	\N	\N	\N	\N	2
f689ce4e-5ed6-4a30-984e-962e18ec9670	1	20	2009-09-10 00:00:16.176+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	841fa815-93ca-4570-8863-0134491b45ae	\N	\N	\N	\N	\N	\N	\N	\N	3
ec433527-f06b-4cd3-b88f-b20042e9699e	1	21	2009-09-10 00:00:16.188+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c511a203-8c59-4361-915d-98cd992169c1	\N	\N	\N	\N	\N	\N	\N	\N	3
60ba5301-8b42-4605-926d-b355ea5da95d	1	23	2009-09-10 00:00:16.209+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	70c2dab0-1a08-411b-b18e-7b8bf0aacaa3	\N	\N	\N	\N	\N	\N	\N	\N	1
917016c5-9e2e-48f6-b16c-4ba6adf97af6	1	24	2009-09-10 00:00:16.229+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	2
c2228a74-4879-4338-9a6b-d92df52bffc9	1	15	2009-09-10 00:14:13.843+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	1
0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	1	17	2009-09-10 00:14:13.895+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	1
135a52f7-c6a2-4da9-b501-754d5134cf74	1	18	2009-09-10 00:14:13.933+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	1
fc4098ae-2dd5-42cc-959f-e19d3929a5f7	1	19	2009-09-10 00:14:13.94+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	\N	\N	\N	\N	\N	\N	\N	\N	1
a77cecda-aa4f-49da-b4e8-2afbf63912d9	1	20	2009-09-10 00:14:13.951+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	\N	\N	\N	\N	\N	\N	\N	\N	1
23189629-6f15-4c26-8d8a-afc19c21e211	1	21	2009-09-10 00:14:13.965+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	fc4098ae-2dd5-42cc-959f-e19d3929a5f7	\N	\N	\N	\N	\N	\N	\N	\N	1
9d7d0f8e-d460-408c-a543-1489b3844320	1	20	2009-09-10 00:14:13.973+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	\N	\N	\N	\N	\N	\N	\N	\N	2
d3788237-503d-42c0-a70b-791b51a1a8f5	1	21	2009-09-10 00:14:13.986+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	fc4098ae-2dd5-42cc-959f-e19d3929a5f7	\N	\N	\N	\N	\N	\N	\N	\N	2
1509468e-8aa2-4738-9923-8b973039d6fc	1	20	2009-09-10 00:14:13.991+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	\N	\N	\N	\N	\N	\N	\N	\N	3
c79ef6db-1f03-48de-8563-5a2a2736ed34	1	21	2009-09-10 00:14:14.002+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	fc4098ae-2dd5-42cc-959f-e19d3929a5f7	\N	\N	\N	\N	\N	\N	\N	\N	3
9b909a7c-bca5-4ffd-a241-b5b1f809cf1d	1	25	2009-09-10 11:24:16.695+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	1
9b8901fe-840e-4f88-bb73-ca3c351a4222	1	25	2009-09-10 11:24:16.702+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	2
53ad023a-6b40-440f-a29a-6b20b3bb6aaf	1	25	2009-09-10 11:24:16.708+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	3
0d8ca6c0-748e-4d3e-8d21-af8c61a7c2c3	1	25	2009-09-10 11:24:16.712+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	4
17d7af2a-7efd-4443-9ffb-08636a8253c0	1	15	2009-09-10 11:57:43.463+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	1
0d6c973b-71ea-4070-9c22-620fcc6005d5	1	17	2009-09-10 11:57:43.511+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	1
cf534448-c191-4972-88a1-39a336f6e958	1	23	2009-09-10 00:14:14.024+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	1
7f1eed0e-bd8a-4ad5-b6e4-4fb9dd28d7db	1	24	2009-09-10 00:14:14.034+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	3
77e6a175-5dfe-411c-b736-42668995019e	1	28	2009-09-10 00:14:14.071+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	1
a623dddc-90b5-420a-8688-895c10351b46	1	30	2009-09-10 00:14:14.099+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	1
b3ae2cb6-23b4-42be-bd83-62da519524e3	1	31	2009-09-10 00:14:14.113+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	7f1eed0e-bd8a-4ad5-b6e4-4fb9dd28d7db	\N	\N	\N	\N	\N	\N	\N	\N	1
2371eb61-2ad2-4ec2-bcb6-0335816c77ae	1	25	2009-09-10 00:17:10.975+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	1
b5662c0d-da70-4150-b372-f1ce0a1caa78	1	25	2009-09-10 00:17:10.983+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	2
e7e8ed4a-7a4f-4b82-8a51-f4384b24ae93	1	25	2009-09-10 00:17:10.988+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	3
67d607ed-f804-4bd9-a90f-16bc1f285fc8	1	25	2009-09-10 00:17:10.994+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	4
b9a2d230-17f7-42e3-835b-1c055f10ffda	1	25	2009-09-10 00:17:11+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	5
46c155d5-6a2f-4c4a-ad31-b7ed95a989d6	1	25	2009-09-10 00:17:11.004+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	6
be96b5bd-6450-4460-9ef0-7d2c0c7be59d	1	25	2009-09-10 00:17:11.009+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	7
8a1872ef-eef6-4df6-ac99-b2a54e561418	1	25	2009-09-10 00:17:11.013+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	8
02e3dfbe-f44f-408c-8d7d-442642a66a34	1	25	2009-09-10 00:17:11.018+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	9
c55351df-b51c-41a7-8e69-cc3f3169fc70	1	25	2009-09-10 00:17:11.022+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	10
7518b7f4-90c4-4e98-b80c-d0d568e651c4	1	25	2009-09-10 00:17:11.028+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	11
557b8b5a-55d8-453c-9b0d-57a8bb849af9	1	25	2009-09-10 00:17:11.033+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	12
b794bb09-ff92-4719-b778-523882a926ff	1	26	2009-09-10 00:17:11.05+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	1
51d97852-7225-4268-8c84-8abf1e97f909	1	27	2009-09-10 00:17:11.061+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	1
38062806-23cd-4f8d-9a18-e80bb5854d38	1	27	2009-09-10 00:17:11.07+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	2
be287249-351c-4fe4-bfc7-5633232fe705	1	27	2009-09-10 00:17:11.079+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	3
1ffe0935-ffb1-4120-af98-ac9cb19c2923	1	27	2009-09-10 00:17:11.089+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	4
3060c25b-6ff8-43c4-9e55-2cec55182d1d	1	27	2009-09-10 00:17:11.099+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	5
bbffd6e8-9089-4a44-a4d8-cd9840440299	1	27	2009-09-10 00:17:11.108+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	6
3a97750e-8fa7-4cd0-904a-5000821b922e	1	27	2009-09-10 00:17:11.116+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	7
dd8f5d16-1255-4279-a36e-1faa839fd081	1	27	2009-09-10 00:17:11.124+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	8
ee181178-52ec-4d1b-9b3b-456ea55afdf2	1	27	2009-09-10 00:17:11.133+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	9
83360174-d5ab-4b07-ab87-276da300a78d	1	27	2009-09-10 00:17:11.141+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	10
d231d9ef-a4e4-4d83-bdb3-b2d1711bd5c9	1	27	2009-09-10 00:17:11.149+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	11
04a6bf78-79d1-4bbb-aade-f30e9a51db53	1	27	2009-09-10 00:17:11.157+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	12
1d436c3a-d158-4fbe-a087-bb4b2703cd75	1	27	2009-09-10 00:17:11.166+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	13
8786f270-d4d9-4d0c-8d0c-fedcdc5d7af2	1	27	2009-09-10 00:17:11.174+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	14
b015231c-2894-4da9-8a28-e46d0aeede95	1	27	2009-09-10 00:17:11.203+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	15
f22b537e-10e0-4bc4-9baf-d41cd85ef3d0	1	27	2009-09-10 00:17:11.214+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	16
5322b1fd-5701-4eaf-97d8-89f1837a63ec	1	25	2009-09-10 11:24:16.717+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	5
eeca5647-9e47-4016-8008-af59db36b90e	1	25	2009-09-10 11:24:16.722+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	6
88a7df35-4ccc-49b8-b8b5-5472665911eb	1	25	2009-09-10 11:24:16.727+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	7
d3fc6d50-85ea-4e9a-8831-5a92ef7b407c	1	25	2009-09-10 11:24:16.731+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	8
de0b2c83-2837-4242-815c-b496c733e664	1	25	2009-09-10 11:24:16.736+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	9
93da0379-838d-4756-963b-002e2145773e	1	25	2009-09-10 11:24:16.741+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	10
bb60a497-d22c-45ee-8719-f446a7fa503b	1	25	2009-09-10 11:24:16.746+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	11
6d12e453-9c54-4e80-80c5-c59e62592451	1	25	2009-09-10 11:24:16.751+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	12
322dedd6-18bd-4da9-ab38-7ff101f2f033	1	26	2009-09-10 11:24:16.818+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	1
8a7f061b-f5b4-4287-9efd-25f7303ce2bb	1	27	2009-09-10 11:24:16.889+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	1
e0aaa7b8-ab30-4b4d-991e-68f5f256d94d	1	27	2009-09-10 11:24:16.985+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	2
89b00415-6c91-47ee-b1d0-4de4d19680e7	1	27	2009-09-10 11:24:16.994+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	3
c408185d-9f11-45e1-bc20-49a8dff9ae65	1	27	2009-09-10 11:24:17.002+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	4
9d703240-265e-4037-8646-f10773a0322c	1	27	2009-09-10 11:24:17.011+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	5
0ba75bb9-1838-44a9-85f7-624e0b9ac9c8	1	27	2009-09-10 00:17:11.223+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	17
6ff6e4f1-89c2-4536-8f2d-9464ec9047a3	1	27	2009-09-10 00:17:11.233+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	18
502b9462-d9fb-42c9-ae8c-b804c420c0a1	1	27	2009-09-10 00:17:11.242+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	19
14d8c929-65e7-4265-9fed-29a35d0f88ce	1	27	2009-09-10 00:17:11.251+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	20
c5009d26-a083-46d0-a029-bd9c805ed573	1	27	2009-09-10 00:17:11.26+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	21
df9d5f2d-f917-43cb-a1ab-1465fae6058b	1	27	2009-09-10 00:17:11.269+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	22
66cb408e-72e9-4d8d-9292-3602b2082640	1	27	2009-09-10 00:17:11.278+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	23
64e8807d-d75d-47fc-a458-a66fb3ccf89f	1	27	2009-09-10 00:17:11.288+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	24
a72e53ba-a3e4-4f0e-ac8d-ee0585ec93fc	1	27	2009-09-10 00:17:11.298+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	25
12cc7898-e946-48d1-bfae-a4ce5bd2b215	1	27	2009-09-10 00:17:11.308+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	26
19efdc59-9698-4112-bbab-1bc83029afae	1	27	2009-09-10 00:17:11.318+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	27
127995ff-e57b-499c-839f-aa896d499f6e	1	27	2009-09-10 00:17:11.329+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	28
6cf566cb-6e28-477a-b9b4-5b9b0816bcc8	1	27	2009-09-10 00:17:11.338+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	29
af075212-bc2f-4085-a96b-c18e3ce3ac9e	1	27	2009-09-10 00:17:11.349+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	30
9bddf071-e110-4b62-aac9-58d212ee0109	1	27	2009-09-10 00:17:11.359+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	31
49bf2848-00c8-4eba-b123-cc7671d8f5ad	1	27	2009-09-10 00:17:11.369+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	32
30891bc1-add2-4684-b251-465201b24215	1	27	2009-09-10 00:17:11.38+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	33
11f4f474-60b8-40e7-9aa3-8270f6553bf7	1	27	2009-09-10 00:17:11.391+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	34
8f0d3ad7-6b36-4cff-9329-50723a758c6d	1	27	2009-09-10 00:17:11.402+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	35
ff159a01-8555-4d02-bb61-e8ab0ebedfd4	1	27	2009-09-10 00:17:11.413+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	36
12ee5d64-5fb0-4abf-8696-64b9cf7f8433	1	27	2009-09-10 00:17:11.424+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	37
424f3f09-a537-4fc2-902d-ac07a722cf67	1	27	2009-09-10 00:17:11.436+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	38
06215ac9-bae3-494d-9a54-b6ec16ae9db5	1	27	2009-09-10 00:17:11.447+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	39
a15906e2-be6a-407b-bc64-462ff3f32312	1	27	2009-09-10 00:17:11.458+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	40
ee849b35-a17f-4aa9-a9d5-70eb0bfa151c	1	27	2009-09-10 00:17:11.469+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	41
bf8840ae-618d-4082-8d3a-2f387435b02c	1	27	2009-09-10 00:17:11.48+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	42
ae2880ba-929d-4cc0-b776-f32cbc6fd85c	1	27	2009-09-10 00:17:11.491+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	43
f98d6d2f-7ab4-43c5-b2b7-925906c6cef1	1	27	2009-09-10 00:17:11.502+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	44
4244e3a5-c462-4e37-a98a-e7d476a713c6	1	27	2009-09-10 00:17:11.513+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	45
b870308f-3a00-44c5-8559-b09f765f8db0	1	27	2009-09-10 00:17:11.524+03	cf534448-c191-4972-88a1-39a336f6e958	0e9d74c2-afd6-4b31-a54e-88bc2481e2ab	f	f	f	f	f	c2228a74-4879-4338-9a6b-d92df52bffc9	\N	\N	\N	\N	\N	\N	\N	\N	46
c3574a06-7868-4115-809c-1c31fe370bd4	1	27	2009-09-10 11:24:17.019+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	6
94f63f2e-7f4d-4c57-87c3-126f65da05f6	1	27	2009-09-10 11:24:17.029+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	7
7c360975-d23d-4203-be0b-1eff6f498b0b	1	27	2009-09-10 11:24:17.037+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	8
01f313e9-669b-4c50-96fe-7d31ec09a09a	1	27	2009-09-10 11:24:17.047+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	9
c40b1d60-c1a1-4f81-862f-4520b479a1cd	1	27	2009-09-10 11:24:17.055+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	10
0c3e14c8-72a0-41cd-917b-45d5d14c4d8e	1	27	2009-09-10 11:24:17.065+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	11
7d1b4dae-b522-4574-b53e-2eb605592f12	1	27	2009-09-10 11:24:17.074+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	12
d1a51325-c34c-4dec-acd6-6e9b36fe6a54	1	15	2009-09-10 10:20:47.455+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d1a51325-c34c-4dec-acd6-6e9b36fe6a54	\N	\N	\N	\N	\N	\N	\N	\N	1
b250c5f0-d9e9-445e-b06a-0feb624dec27	1	17	2009-09-10 10:20:47.492+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d1a51325-c34c-4dec-acd6-6e9b36fe6a54	\N	\N	\N	\N	\N	\N	\N	\N	1
df009b07-c928-4402-9e66-8f763962474b	1	18	2009-09-10 10:20:47.517+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d1a51325-c34c-4dec-acd6-6e9b36fe6a54	\N	\N	\N	\N	\N	\N	\N	\N	1
d746119b-337e-415b-ac51-43d904efe720	1	19	2009-09-10 10:20:47.524+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	b250c5f0-d9e9-445e-b06a-0feb624dec27	\N	\N	\N	\N	\N	\N	\N	\N	1
0737474a-3874-4bed-81f9-224fff156e12	1	20	2009-09-10 10:20:47.535+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	b250c5f0-d9e9-445e-b06a-0feb624dec27	\N	\N	\N	\N	\N	\N	\N	\N	1
156c9938-4a59-4f57-8527-3e401ba006e5	1	21	2009-09-10 10:20:47.548+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d746119b-337e-415b-ac51-43d904efe720	\N	\N	\N	\N	\N	\N	\N	\N	1
c7cecac1-2347-4608-80c3-5db5fdf424c3	1	20	2009-09-10 10:20:47.555+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	b250c5f0-d9e9-445e-b06a-0feb624dec27	\N	\N	\N	\N	\N	\N	\N	\N	2
cc1c67b3-8ee7-4e19-9c17-1f4d9ba586a5	1	21	2009-09-10 10:20:47.627+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d746119b-337e-415b-ac51-43d904efe720	\N	\N	\N	\N	\N	\N	\N	\N	2
7cca3daf-d44a-4e49-83b3-b93ec744f08b	1	20	2009-09-10 10:20:47.634+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	b250c5f0-d9e9-445e-b06a-0feb624dec27	\N	\N	\N	\N	\N	\N	\N	\N	3
ca8a453c-adc3-4d85-9922-500986bd6668	1	21	2009-09-10 10:20:47.646+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d746119b-337e-415b-ac51-43d904efe720	\N	\N	\N	\N	\N	\N	\N	\N	3
f4a24f61-0d5c-47ca-a511-59006c7be2d9	1	23	2009-09-10 10:20:47.672+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d1a51325-c34c-4dec-acd6-6e9b36fe6a54	\N	\N	\N	\N	\N	\N	\N	\N	1
3ff1586d-f449-462f-abdc-28574a4b2125	1	24	2009-09-10 10:20:47.683+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	4
f8a7227b-cce2-42b6-8d0a-1eb693e3f6c8	1	28	2009-09-10 10:20:47.696+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d1a51325-c34c-4dec-acd6-6e9b36fe6a54	\N	\N	\N	\N	\N	\N	\N	\N	1
8f22e230-1cc6-472d-8694-4722390ad098	1	30	2009-09-10 10:20:47.777+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d1a51325-c34c-4dec-acd6-6e9b36fe6a54	\N	\N	\N	\N	\N	\N	\N	\N	1
ce0fa7a9-7394-44f5-8b20-98a5ccf6930b	1	31	2009-09-10 10:20:47.79+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	3ff1586d-f449-462f-abdc-28574a4b2125	\N	\N	\N	\N	\N	\N	\N	\N	1
4d040b10-32b4-4334-b183-55e1aa41ba37	1	32	2009-09-10 10:20:50.019+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d1a51325-c34c-4dec-acd6-6e9b36fe6a54	\N	\N	\N	\N	\N	\N	\N	\N	1
ea6bb976-2b9b-47dc-a71d-6f3112cb8c85	1	32	2009-09-10 10:20:50.299+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d1a51325-c34c-4dec-acd6-6e9b36fe6a54	\N	\N	\N	\N	\N	\N	\N	\N	2
5919fcd7-06e0-4a70-8653-11435557b80b	1	32	2009-09-10 10:20:50.623+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d1a51325-c34c-4dec-acd6-6e9b36fe6a54	\N	\N	\N	\N	\N	\N	\N	\N	3
fcb8ea0d-2b52-48a7-9fcf-5bdcfd253974	1	32	2009-09-10 10:20:50.633+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d1a51325-c34c-4dec-acd6-6e9b36fe6a54	\N	\N	\N	\N	\N	\N	\N	\N	4
40186859-69a2-499a-841f-6bbb58c09586	1	32	2009-09-10 10:20:50.642+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d1a51325-c34c-4dec-acd6-6e9b36fe6a54	\N	\N	\N	\N	\N	\N	\N	\N	5
2c7c20ad-b4d6-4b9f-9eaf-35b213679414	1	32	2009-09-10 10:20:50.676+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d1a51325-c34c-4dec-acd6-6e9b36fe6a54	\N	\N	\N	\N	\N	\N	\N	\N	6
d10447f5-97be-4f3b-944b-7975d1aaa1b4	1	32	2009-09-10 10:20:50.686+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d1a51325-c34c-4dec-acd6-6e9b36fe6a54	\N	\N	\N	\N	\N	\N	\N	\N	7
b6070474-daed-470e-b578-86111038e762	1	32	2009-09-10 10:20:50.696+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d1a51325-c34c-4dec-acd6-6e9b36fe6a54	\N	\N	\N	\N	\N	\N	\N	\N	8
6f0f4b8d-1bef-4c7d-bac1-4613df3876f3	1	32	2009-09-10 10:20:50.705+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d1a51325-c34c-4dec-acd6-6e9b36fe6a54	\N	\N	\N	\N	\N	\N	\N	\N	9
1fbf2b78-5063-4dc5-92fd-0ec4a903574d	1	32	2009-09-10 10:20:50.714+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d1a51325-c34c-4dec-acd6-6e9b36fe6a54	\N	\N	\N	\N	\N	\N	\N	\N	10
679b77b7-dec0-4187-83fb-bc2dda2a690d	1	32	2009-09-10 10:20:50.723+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d1a51325-c34c-4dec-acd6-6e9b36fe6a54	\N	\N	\N	\N	\N	\N	\N	\N	11
91c82de9-ccb7-4ae6-9178-d7849be85b93	1	32	2009-09-10 10:20:50.733+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d1a51325-c34c-4dec-acd6-6e9b36fe6a54	\N	\N	\N	\N	\N	\N	\N	\N	12
be2b6641-a919-4b1c-9264-a5652808d529	1	33	2009-09-10 10:20:50.748+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8f22e230-1cc6-472d-8694-4722390ad098	\N	\N	\N	\N	\N	\N	\N	\N	1
7de87a70-965c-4967-81b8-1ea393dc94e4	1	27	2009-09-10 11:24:17.10+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	13
d09a27f8-a2ca-4e28-a17f-1e3776cff38e	1	27	2009-09-10 11:24:17.11+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	14
e421388e-88b2-4793-9988-f0a61908f056	1	27	2009-09-10 11:24:17.119+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	15
4d7589ef-5e2c-4d59-8a8c-c744bc704cea	1	27	2009-09-10 11:24:17.128+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	16
86f80393-65a1-420c-942e-8231fb598cc6	1	27	2009-09-10 11:24:17.137+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	17
9987a6af-d215-4f2a-935b-1e31c2b862a9	1	27	2009-09-10 11:24:17.146+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	18
d9473655-ffcb-481a-ad3c-89c37ddc06c2	1	27	2009-09-10 11:24:17.156+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	19
a6d58bcb-8cf4-4fdc-87db-e65cad8e3b6b	1	27	2009-09-10 11:24:17.165+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	20
d53f5942-9ed7-45ac-95e7-fdd5f7c0d6db	1	27	2009-09-10 11:24:17.175+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	21
eb90db9e-2564-46b6-b2c4-3f393dbaa39f	1	27	2009-09-10 11:24:17.185+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	22
1da48613-c7d3-4731-b925-ae7c5aafe190	1	27	2009-09-10 11:24:17.195+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	23
921b57e0-18f0-45a8-9b1c-67c51fe6a496	1	27	2009-09-10 11:24:17.205+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	24
bcf52a6b-6b35-416a-8c43-d9e9333834ce	1	27	2009-09-10 11:24:17.215+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	25
59b8cc21-1adc-4efd-909c-4d805fa4bbd1	1	27	2009-09-10 11:24:17.224+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	26
93562511-2a48-48fa-9597-0e022d2b7274	1	27	2009-09-10 11:24:17.234+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	27
b6d6e889-b298-4103-b00d-c549fa9c4ea0	1	27	2009-09-10 11:24:17.244+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	28
d320456a-ccd9-4317-bf45-cf81ecbb9de5	1	27	2009-09-10 11:24:17.254+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	29
c2af04b3-f8fb-4132-b24b-3526c0b80ad4	1	27	2009-09-10 11:24:17.263+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	30
7c145414-97ff-4743-ab4f-5a36eef3b8e5	1	27	2009-09-10 11:24:17.273+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	31
372f7fb8-660e-4e17-998c-625a6e1461f8	1	27	2009-09-10 11:24:17.284+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	32
96d38279-4aa0-44e6-8b32-e98f21bbcf90	1	27	2009-09-10 11:24:17.294+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	33
4dbab55f-e659-4e85-8e18-92cd328ea3fb	1	27	2009-09-10 11:24:17.305+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	34
85e90f74-18cc-4332-b518-d9177d2ed1a4	1	27	2009-09-10 11:24:17.315+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	35
07a3f8cd-fb3d-4e78-8ce5-e97de86eb62c	1	27	2009-09-10 11:24:17.325+03	4525318e-b3bb-4352-86db-86b6c965ce7c	2359111e-d725-438e-bb47-694f88b08138	f	f	f	f	f	59f5e9f2-217c-401f-bd94-ae8b5d4ac477	\N	\N	\N	\N	\N	\N	\N	\N	36
5e491ce3-bda4-487c-ae3b-b460ffb5ba4a	1	20	2009-09-10 11:57:43.631+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	0d6c973b-71ea-4070-9c22-620fcc6005d5	\N	\N	\N	\N	\N	\N	\N	\N	3
d35b302c-51e0-4a5f-816e-879379b14785	1	21	2009-09-10 11:57:43.645+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	99fe545b-ffe1-497f-b6a0-f2bf7f1bc9dc	\N	\N	\N	\N	\N	\N	\N	\N	3
adf38830-a4e6-4242-bc27-850344f22d1b	1	23	2009-09-10 11:57:43.666+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	1
c7a14d9b-b939-4348-9199-3515146fd527	1	24	2009-09-10 11:57:43.676+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	6
62843b4a-9d28-4172-8ad3-2863caff9b01	1	28	2009-09-10 11:57:43.688+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	1
a83c2350-f4e3-4753-bcd3-aa5224863308	1	30	2009-09-10 11:57:43.699+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	1
125c1659-423a-4b77-8337-49d59c864d67	1	31	2009-09-10 11:57:43.714+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c7a14d9b-b939-4348-9199-3515146fd527	\N	\N	\N	\N	\N	\N	\N	\N	1
1b250f5d-c1eb-41de-8d97-4bdc0e36f212	1	32	2009-09-10 11:57:47.122+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	1
08526571-ecd3-4677-bcff-e50aea6fe325	1	32	2009-09-10 11:57:47.135+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	2
0751c864-f47e-45fd-9080-599a3daef41a	1	32	2009-09-10 11:57:47.144+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	3
aa944218-52bf-4421-9a68-1ae52f8619cc	1	32	2009-09-10 11:57:47.154+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	4
915f6229-dd98-45d0-845f-3c6e88fafe12	1	32	2009-09-10 11:57:47.163+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	5
83845486-6da3-46f1-ba72-f64e848b475c	1	32	2009-09-10 11:57:47.172+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	6
81550d66-cf23-4644-9629-6df5a297d756	1	32	2009-09-10 11:57:47.182+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	7
9312dc3a-195e-44af-841d-8965eb7f7aad	1	32	2009-09-10 11:57:47.191+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	8
51961a25-a7fe-483e-8219-16ac7df8a634	1	32	2009-09-10 11:57:47.20+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	9
813e4e39-5918-43ea-ab88-520ee2a562c9	1	32	2009-09-10 11:57:47.208+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	10
c9eb0c9c-805a-4dd1-b3e5-ea21b9c3d3c4	1	32	2009-09-10 11:57:47.217+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	11
a234a892-18e3-4c00-8a21-a27caffbce2a	1	32	2009-09-10 11:57:47.227+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	12
bbe1e947-f954-45ce-9210-4d22eefd116b	1	33	2009-09-10 11:57:47.242+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a83c2350-f4e3-4753-bcd3-aa5224863308	\N	\N	\N	\N	\N	\N	\N	\N	1
88265dcf-39c2-40c0-abac-2c5b67ee38f7	1	34	2009-09-10 11:57:47.255+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	bbe1e947-f954-45ce-9210-4d22eefd116b	\N	\N	\N	\N	\N	\N	\N	\N	1
999c13e5-05b6-42e6-8958-77413765511c	1	34	2009-09-10 11:57:47.265+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	bbe1e947-f954-45ce-9210-4d22eefd116b	\N	\N	\N	\N	\N	\N	\N	\N	2
971a40f6-4eb8-46a4-9648-02ba09c7afaa	1	34	2009-09-10 11:57:47.276+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	bbe1e947-f954-45ce-9210-4d22eefd116b	\N	\N	\N	\N	\N	\N	\N	\N	3
f364a165-6045-480c-8589-4073cae69d02	1	34	2009-09-10 11:57:47.286+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	bbe1e947-f954-45ce-9210-4d22eefd116b	\N	\N	\N	\N	\N	\N	\N	\N	4
2f1866de-9616-4ebb-a9c7-2427c64932b0	1	34	2009-09-10 11:57:47.296+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	bbe1e947-f954-45ce-9210-4d22eefd116b	\N	\N	\N	\N	\N	\N	\N	\N	5
620a26b5-d0bd-4a81-8bf1-8b452e1ac101	1	34	2009-09-10 11:57:47.305+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	bbe1e947-f954-45ce-9210-4d22eefd116b	\N	\N	\N	\N	\N	\N	\N	\N	6
df2b7e67-ddbe-4913-acc0-a87650827cb7	1	34	2009-09-10 11:57:47.315+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	bbe1e947-f954-45ce-9210-4d22eefd116b	\N	\N	\N	\N	\N	\N	\N	\N	7
407006f0-5665-4e15-9860-be801dc21cdd	1	34	2009-09-10 11:57:47.325+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	bbe1e947-f954-45ce-9210-4d22eefd116b	\N	\N	\N	\N	\N	\N	\N	\N	8
608009bb-6682-471c-b224-f3ae5610d748	1	34	2009-09-10 11:57:47.334+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	bbe1e947-f954-45ce-9210-4d22eefd116b	\N	\N	\N	\N	\N	\N	\N	\N	9
e99d64c8-fc02-4d49-8fe4-dab4dac51038	1	33	2009-09-10 11:57:47.346+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a83c2350-f4e3-4753-bcd3-aa5224863308	\N	\N	\N	\N	\N	\N	\N	\N	2
d43036b4-810b-4950-85dc-5704340b4105	1	34	2009-09-10 11:57:47.362+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e99d64c8-fc02-4d49-8fe4-dab4dac51038	\N	\N	\N	\N	\N	\N	\N	\N	1
40c92877-fd5c-4e1f-9c40-d092014ea6ed	1	34	2009-09-10 11:57:47.372+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e99d64c8-fc02-4d49-8fe4-dab4dac51038	\N	\N	\N	\N	\N	\N	\N	\N	2
d19c7464-4df2-4034-b702-9247d41d622f	1	34	2009-09-10 11:57:47.384+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e99d64c8-fc02-4d49-8fe4-dab4dac51038	\N	\N	\N	\N	\N	\N	\N	\N	3
b00735da-a500-466d-98e4-67e5cf8ddbcb	1	34	2009-09-10 11:57:47.395+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e99d64c8-fc02-4d49-8fe4-dab4dac51038	\N	\N	\N	\N	\N	\N	\N	\N	4
b9a0affc-50ab-49e4-8781-9cf143ce43f9	1	34	2009-09-10 11:57:47.405+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e99d64c8-fc02-4d49-8fe4-dab4dac51038	\N	\N	\N	\N	\N	\N	\N	\N	5
ec15f905-37bb-4bbc-8293-1bda3629dc67	1	34	2009-09-10 11:57:47.417+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e99d64c8-fc02-4d49-8fe4-dab4dac51038	\N	\N	\N	\N	\N	\N	\N	\N	6
7e95137e-28d1-469c-9e2a-bc8a54e641e3	1	34	2009-09-10 11:57:47.427+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e99d64c8-fc02-4d49-8fe4-dab4dac51038	\N	\N	\N	\N	\N	\N	\N	\N	7
cf4e09bb-9392-4d56-9204-a3511a434892	1	34	2009-09-10 11:57:47.438+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e99d64c8-fc02-4d49-8fe4-dab4dac51038	\N	\N	\N	\N	\N	\N	\N	\N	8
b220b49b-e465-4534-81d3-c12ee6dbba00	1	34	2009-09-10 11:57:47.449+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e99d64c8-fc02-4d49-8fe4-dab4dac51038	\N	\N	\N	\N	\N	\N	\N	\N	9
b5ef01c4-df9c-498b-99f3-dc6233b811e4	1	33	2009-09-10 11:57:47.46+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a83c2350-f4e3-4753-bcd3-aa5224863308	\N	\N	\N	\N	\N	\N	\N	\N	3
1ded8865-db17-4590-9c44-d0627951cedd	1	34	2009-09-10 11:57:47.475+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	b5ef01c4-df9c-498b-99f3-dc6233b811e4	\N	\N	\N	\N	\N	\N	\N	\N	1
a3c6621b-c28f-4fe8-a77d-b5481077fe0d	1	34	2009-09-10 11:57:47.487+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	b5ef01c4-df9c-498b-99f3-dc6233b811e4	\N	\N	\N	\N	\N	\N	\N	\N	2
d3a0c704-13af-4b40-a33d-ba9b2a56a628	1	34	2009-09-10 11:57:47.498+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	b5ef01c4-df9c-498b-99f3-dc6233b811e4	\N	\N	\N	\N	\N	\N	\N	\N	3
e12a8de8-9342-40fd-a5b0-0d33a31d4a70	1	25	2009-09-10 15:11:40.391+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	1
f56883fd-3ae8-4480-a3a9-3bf5d5ea9f1c	1	25	2009-09-10 15:11:40.451+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	2
a8f31f0e-a105-446d-9810-84bfe1ecdedf	1	25	2009-09-10 15:11:40.459+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	3
0cd2ca1d-7de1-4688-94ab-72376237f605	1	25	2009-09-10 15:11:40.465+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	4
5b1ef79a-1019-4499-804f-f3a1fb65ab9e	1	25	2009-09-10 15:11:40.47+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	5
b2aabfc0-52a7-4025-a4df-00043c4c7605	1	25	2009-09-10 15:11:40.474+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	6
e85a5af5-65c7-4bab-9630-cad994e0f9a6	1	25	2009-09-10 15:11:40.479+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	7
48961e35-24fe-4e6a-ac7f-2b1d1b63fa86	1	25	2009-09-10 15:11:40.484+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	8
a97418b0-966e-4a6d-a559-872a5a8b4b63	1	25	2009-09-10 15:11:40.49+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	9
8e01767f-2e36-4ea0-9a21-30b6c8f08c70	1	25	2009-09-10 15:11:40.494+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	10
b820a2c5-c49d-44e6-9059-0327ed02bde1	1	25	2009-09-10 15:11:40.499+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	11
daeebc2c-1c32-466d-ba9f-64fd4069e4c9	1	25	2009-09-10 15:11:40.504+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	12
a6ad959e-74d2-4876-add3-a970476e6281	1	26	2009-09-10 15:11:40.523+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	1
c2ecba82-7ca1-42ab-ba65-7988b69545ff	1	27	2009-09-10 15:11:40.535+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	1
3d70cd2d-7190-413a-93ad-df8ba214ebc4	1	34	2009-09-10 11:57:47.508+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	b5ef01c4-df9c-498b-99f3-dc6233b811e4	\N	\N	\N	\N	\N	\N	\N	\N	4
1d558e02-8ae3-4208-9fed-f999f2654688	1	34	2009-09-10 11:57:47.52+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	b5ef01c4-df9c-498b-99f3-dc6233b811e4	\N	\N	\N	\N	\N	\N	\N	\N	5
6317454f-a7b6-4b6d-8c43-5b6d59b0cb73	1	34	2009-09-10 11:57:47.533+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	b5ef01c4-df9c-498b-99f3-dc6233b811e4	\N	\N	\N	\N	\N	\N	\N	\N	6
804be57e-c9f7-4a97-8282-298975b3c857	1	34	2009-09-10 11:57:47.546+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	b5ef01c4-df9c-498b-99f3-dc6233b811e4	\N	\N	\N	\N	\N	\N	\N	\N	7
c2664c0c-6ece-4729-bbf4-bf4450878c42	1	34	2009-09-10 11:57:47.56+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	b5ef01c4-df9c-498b-99f3-dc6233b811e4	\N	\N	\N	\N	\N	\N	\N	\N	8
d97b8a2b-d08d-498f-b0bc-326270824fc2	1	34	2009-09-10 11:57:47.574+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	b5ef01c4-df9c-498b-99f3-dc6233b811e4	\N	\N	\N	\N	\N	\N	\N	\N	9
2e2a7b84-a0e2-4ff1-88a4-92e10205d562	1	33	2009-09-10 11:57:47.586+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a83c2350-f4e3-4753-bcd3-aa5224863308	\N	\N	\N	\N	\N	\N	\N	\N	4
d361d936-7ea1-44fa-989e-7cd47cd5a13e	1	34	2009-09-10 11:57:47.602+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2e2a7b84-a0e2-4ff1-88a4-92e10205d562	\N	\N	\N	\N	\N	\N	\N	\N	1
8c57935b-68c0-4244-aa0f-f27c13a0fa99	1	34	2009-09-10 11:57:47.614+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2e2a7b84-a0e2-4ff1-88a4-92e10205d562	\N	\N	\N	\N	\N	\N	\N	\N	2
679be420-5225-420a-b19e-40923ca3011e	1	34	2009-09-10 11:57:47.625+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2e2a7b84-a0e2-4ff1-88a4-92e10205d562	\N	\N	\N	\N	\N	\N	\N	\N	3
8046391b-a110-49b8-9503-8c9ff2c6ce9b	1	34	2009-09-10 11:57:47.637+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2e2a7b84-a0e2-4ff1-88a4-92e10205d562	\N	\N	\N	\N	\N	\N	\N	\N	4
cb11ed79-bef7-4567-9c8d-041612f8a99f	1	34	2009-09-10 11:57:47.65+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2e2a7b84-a0e2-4ff1-88a4-92e10205d562	\N	\N	\N	\N	\N	\N	\N	\N	5
46a599bf-8e24-4bfa-bb89-0565101d9874	1	34	2009-09-10 11:57:47.661+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2e2a7b84-a0e2-4ff1-88a4-92e10205d562	\N	\N	\N	\N	\N	\N	\N	\N	6
823cdce2-c6e2-409c-80fc-5a4c2a98c9bd	1	34	2009-09-10 11:57:47.673+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2e2a7b84-a0e2-4ff1-88a4-92e10205d562	\N	\N	\N	\N	\N	\N	\N	\N	7
a775be8a-5c61-4569-82b8-35903f2f48bb	1	34	2009-09-10 11:57:47.686+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2e2a7b84-a0e2-4ff1-88a4-92e10205d562	\N	\N	\N	\N	\N	\N	\N	\N	8
ff6eb6b3-327a-4ea8-85fe-a7afd0f21325	1	34	2009-09-10 11:57:47.697+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2e2a7b84-a0e2-4ff1-88a4-92e10205d562	\N	\N	\N	\N	\N	\N	\N	\N	9
6cf6015e-13ec-411e-bca9-fb1df9cabb7f	1	33	2009-09-10 11:57:47.71+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a83c2350-f4e3-4753-bcd3-aa5224863308	\N	\N	\N	\N	\N	\N	\N	\N	5
aa44a955-db3d-4c40-9880-d6044d78f219	1	34	2009-09-10 11:57:47.726+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6cf6015e-13ec-411e-bca9-fb1df9cabb7f	\N	\N	\N	\N	\N	\N	\N	\N	1
f5b8c078-3813-44f3-8072-88a77bcfbe2b	1	34	2009-09-10 11:57:47.739+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6cf6015e-13ec-411e-bca9-fb1df9cabb7f	\N	\N	\N	\N	\N	\N	\N	\N	2
389bca86-5587-44c8-a809-164b4b74bfe5	1	34	2009-09-10 11:57:47.752+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6cf6015e-13ec-411e-bca9-fb1df9cabb7f	\N	\N	\N	\N	\N	\N	\N	\N	3
9b18831c-4814-41a2-9205-1a16a8209b82	1	34	2009-09-10 11:57:47.766+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6cf6015e-13ec-411e-bca9-fb1df9cabb7f	\N	\N	\N	\N	\N	\N	\N	\N	4
e60ebb81-ddf2-4fab-86d4-04c2948c605b	1	34	2009-09-10 11:57:47.779+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6cf6015e-13ec-411e-bca9-fb1df9cabb7f	\N	\N	\N	\N	\N	\N	\N	\N	5
d99d8e40-022c-4a60-99bf-a8ff4056241e	1	34	2009-09-10 11:57:47.791+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6cf6015e-13ec-411e-bca9-fb1df9cabb7f	\N	\N	\N	\N	\N	\N	\N	\N	6
9f204595-69df-420a-af81-f148cfe6f43a	1	34	2009-09-10 11:57:47.803+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6cf6015e-13ec-411e-bca9-fb1df9cabb7f	\N	\N	\N	\N	\N	\N	\N	\N	7
68b40cdd-83fe-42a9-ab9f-1bc7f00e8e7b	1	34	2009-09-10 11:57:47.816+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6cf6015e-13ec-411e-bca9-fb1df9cabb7f	\N	\N	\N	\N	\N	\N	\N	\N	8
81e3e068-5881-4250-ad35-90b546eaa274	1	34	2009-09-10 11:57:47.828+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6cf6015e-13ec-411e-bca9-fb1df9cabb7f	\N	\N	\N	\N	\N	\N	\N	\N	9
449f3e1e-af23-4d28-b429-fd2435372dc6	1	33	2009-09-10 11:57:47.841+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a83c2350-f4e3-4753-bcd3-aa5224863308	\N	\N	\N	\N	\N	\N	\N	\N	6
af176fe0-1512-4e42-94fb-aaa7141082a8	1	34	2009-09-10 11:57:47.858+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	449f3e1e-af23-4d28-b429-fd2435372dc6	\N	\N	\N	\N	\N	\N	\N	\N	1
bbfecf0a-2f66-4df7-9035-ddf8eca7e3b7	1	34	2009-09-10 11:57:47.87+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	449f3e1e-af23-4d28-b429-fd2435372dc6	\N	\N	\N	\N	\N	\N	\N	\N	2
ab36127e-5786-434c-b2d1-8433cbbceef3	1	34	2009-09-10 11:57:47.883+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	449f3e1e-af23-4d28-b429-fd2435372dc6	\N	\N	\N	\N	\N	\N	\N	\N	3
3c5a3d17-b125-4def-994f-f2c54cfe7f32	1	27	2009-09-10 15:11:40.545+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	2
807a610b-1a64-45f7-91fc-80ccce62e2bf	1	34	2009-09-10 11:57:47.895+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	449f3e1e-af23-4d28-b429-fd2435372dc6	\N	\N	\N	\N	\N	\N	\N	\N	4
50e682de-1df1-43eb-90fa-8e088cf67bf4	1	27	2009-09-10 15:11:40.555+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	3
72059b0d-6f7c-427a-b9c9-e3babdf62768	1	27	2009-09-10 15:11:40.566+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	4
3310f0d4-7630-4bb3-8dcc-c4b5f862dab2	1	27	2009-09-10 15:11:40.574+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	5
04a3089e-86c0-4f5c-93aa-94de2862589b	1	27	2009-09-10 15:11:40.583+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	6
c0d5765e-0caa-4b6c-886b-b36f9422d809	1	27	2009-09-10 15:11:40.592+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	7
d1cc91d0-17cd-4baf-9a33-94a40e7d0933	1	27	2009-09-10 15:11:40.601+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	8
a87788ec-9738-4868-b5bd-6b06034e0c99	1	27	2009-09-10 15:11:40.61+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	9
5778185d-abd4-4a78-8715-894a38f140f4	1	27	2009-09-10 15:11:40.619+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	10
4abe87ea-17d1-438d-9bb3-754ebeea61d2	1	27	2009-09-10 15:11:40.631+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	11
00b70052-975d-4d28-a415-976d8f9aada5	1	27	2009-09-10 15:11:40.641+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	12
e0f2c60c-210e-414c-9d2a-f796e4744499	1	27	2009-09-10 15:11:40.649+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	13
91c43fd5-dade-4bb5-bf55-ac9fe5e3b1a9	1	27	2009-09-10 15:11:40.658+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	14
2aba0f4a-4450-43e1-ab16-e236c01beda3	1	27	2009-09-10 15:11:40.667+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	15
9b25d741-2e29-4566-bafb-603042212764	1	27	2009-09-10 15:11:40.677+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	16
471567e6-e2f5-4327-bd0b-82d2b867fd87	1	34	2009-09-10 11:57:47.908+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	449f3e1e-af23-4d28-b429-fd2435372dc6	\N	\N	\N	\N	\N	\N	\N	\N	5
91f1630d-0841-4560-af71-55c8fa2c0711	1	34	2009-09-10 11:57:47.92+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	449f3e1e-af23-4d28-b429-fd2435372dc6	\N	\N	\N	\N	\N	\N	\N	\N	6
05dd5e0c-3065-4d68-b8ca-3837116ec697	1	34	2009-09-10 11:57:47.933+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	449f3e1e-af23-4d28-b429-fd2435372dc6	\N	\N	\N	\N	\N	\N	\N	\N	7
8572a44e-860c-4352-945b-48dd8a10c0ed	1	34	2009-09-10 11:57:47.946+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	449f3e1e-af23-4d28-b429-fd2435372dc6	\N	\N	\N	\N	\N	\N	\N	\N	8
4486e61e-7173-4835-8282-1a2b1c31fdc2	1	34	2009-09-10 11:57:47.958+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	449f3e1e-af23-4d28-b429-fd2435372dc6	\N	\N	\N	\N	\N	\N	\N	\N	9
ec806cf7-19d9-43e0-8b34-1e647b489630	1	33	2009-09-10 11:57:47.973+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a83c2350-f4e3-4753-bcd3-aa5224863308	\N	\N	\N	\N	\N	\N	\N	\N	7
1a664125-e346-449b-9f39-deb381ecfd50	1	34	2009-09-10 11:57:47.992+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ec806cf7-19d9-43e0-8b34-1e647b489630	\N	\N	\N	\N	\N	\N	\N	\N	1
0d8af149-3761-4c2d-a704-5439fcff492d	1	34	2009-09-10 11:57:48.005+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ec806cf7-19d9-43e0-8b34-1e647b489630	\N	\N	\N	\N	\N	\N	\N	\N	2
66027c04-3fcb-43ad-952f-ed14f0043956	1	34	2009-09-10 11:57:48.016+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ec806cf7-19d9-43e0-8b34-1e647b489630	\N	\N	\N	\N	\N	\N	\N	\N	3
a73e6ea7-a054-4585-adbd-dfc72445576c	1	34	2009-09-10 11:57:48.028+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ec806cf7-19d9-43e0-8b34-1e647b489630	\N	\N	\N	\N	\N	\N	\N	\N	4
24dd0b3a-e31a-41a7-912b-c5fa37621565	1	34	2009-09-10 11:57:48.042+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ec806cf7-19d9-43e0-8b34-1e647b489630	\N	\N	\N	\N	\N	\N	\N	\N	5
dad3397e-9db3-4780-9114-07ae7f158286	1	34	2009-09-10 11:57:48.055+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ec806cf7-19d9-43e0-8b34-1e647b489630	\N	\N	\N	\N	\N	\N	\N	\N	6
6c8029f2-1442-4f57-ad13-d5ce28d93a66	1	34	2009-09-10 11:57:48.068+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ec806cf7-19d9-43e0-8b34-1e647b489630	\N	\N	\N	\N	\N	\N	\N	\N	7
0a552af3-c683-4085-8a78-67c2f12d0141	1	34	2009-09-10 11:57:48.081+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ec806cf7-19d9-43e0-8b34-1e647b489630	\N	\N	\N	\N	\N	\N	\N	\N	8
b5c3b048-601b-4129-9417-3f4710b3ec80	1	34	2009-09-10 11:57:48.095+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ec806cf7-19d9-43e0-8b34-1e647b489630	\N	\N	\N	\N	\N	\N	\N	\N	9
e2970070-9c4c-4535-8203-c77a16366c99	1	33	2009-09-10 11:57:48.109+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a83c2350-f4e3-4753-bcd3-aa5224863308	\N	\N	\N	\N	\N	\N	\N	\N	8
45e7a7ad-2cf7-4f55-8f35-17b458b3a9ea	1	34	2009-09-10 11:57:48.126+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e2970070-9c4c-4535-8203-c77a16366c99	\N	\N	\N	\N	\N	\N	\N	\N	1
cce00e07-58ea-4d60-8652-6bf7545e4e59	1	34	2009-09-10 11:57:48.14+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e2970070-9c4c-4535-8203-c77a16366c99	\N	\N	\N	\N	\N	\N	\N	\N	2
a1fde904-9009-45eb-ac93-79039bfc3c97	1	34	2009-09-10 11:57:48.152+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e2970070-9c4c-4535-8203-c77a16366c99	\N	\N	\N	\N	\N	\N	\N	\N	3
f7d807c1-730e-4fc2-911c-6994071eae9c	1	34	2009-09-10 11:57:48.166+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e2970070-9c4c-4535-8203-c77a16366c99	\N	\N	\N	\N	\N	\N	\N	\N	4
22b9a349-a7e6-4734-85b9-470363732740	1	34	2009-09-10 11:57:48.18+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e2970070-9c4c-4535-8203-c77a16366c99	\N	\N	\N	\N	\N	\N	\N	\N	5
94ae286d-344e-452f-8c2e-d573f4eb1d99	1	34	2009-09-10 11:57:48.196+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e2970070-9c4c-4535-8203-c77a16366c99	\N	\N	\N	\N	\N	\N	\N	\N	6
6d395b55-32fc-495a-8ba5-47257e61c7de	1	34	2009-09-10 11:57:48.209+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e2970070-9c4c-4535-8203-c77a16366c99	\N	\N	\N	\N	\N	\N	\N	\N	7
634e50d1-064d-4417-878b-e1f09550b9aa	1	34	2009-09-10 11:57:48.223+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e2970070-9c4c-4535-8203-c77a16366c99	\N	\N	\N	\N	\N	\N	\N	\N	8
6b4c21eb-8056-4cd2-8052-480088d657f7	1	34	2009-09-10 11:57:48.237+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e2970070-9c4c-4535-8203-c77a16366c99	\N	\N	\N	\N	\N	\N	\N	\N	9
480be665-6307-4cc9-addd-436d4dbe5fff	1	33	2009-09-10 11:57:48.251+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a83c2350-f4e3-4753-bcd3-aa5224863308	\N	\N	\N	\N	\N	\N	\N	\N	9
c3621833-191b-4094-920c-2e3439f38463	1	34	2009-09-10 11:57:48.271+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	480be665-6307-4cc9-addd-436d4dbe5fff	\N	\N	\N	\N	\N	\N	\N	\N	1
293cba0a-c729-4094-afb5-b862bd232059	1	34	2009-09-10 11:57:48.285+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	480be665-6307-4cc9-addd-436d4dbe5fff	\N	\N	\N	\N	\N	\N	\N	\N	2
6d8b58f3-3397-440a-a90f-cad3d940cc69	1	34	2009-09-10 11:57:48.299+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	480be665-6307-4cc9-addd-436d4dbe5fff	\N	\N	\N	\N	\N	\N	\N	\N	3
699c02fd-72f8-4d04-8833-af134414ad9f	1	34	2009-09-10 11:57:48.312+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	480be665-6307-4cc9-addd-436d4dbe5fff	\N	\N	\N	\N	\N	\N	\N	\N	4
c83f2744-d6e7-4dd1-b45a-31f01c9a38ac	1	34	2009-09-10 11:57:48.326+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	480be665-6307-4cc9-addd-436d4dbe5fff	\N	\N	\N	\N	\N	\N	\N	\N	5
d09f2cd7-146d-49a5-95de-da3eedd3de46	1	27	2009-09-10 15:11:40.686+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	17
591521b0-89a3-487d-8053-c125b2676d32	1	34	2009-09-10 11:57:48.339+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	480be665-6307-4cc9-addd-436d4dbe5fff	\N	\N	\N	\N	\N	\N	\N	\N	6
e85db78a-9dc1-4633-b7ba-09f41652b7ef	1	27	2009-09-10 15:11:40.694+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	18
46b8eb7a-6243-4ca0-bdfc-459631109a45	1	27	2009-09-10 15:11:40.705+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	19
e9b818f3-b0a4-471f-abe8-e60a021eaae1	1	27	2009-09-10 15:11:40.714+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	20
b269e34b-fbd8-47b5-bd89-81e446e41fbc	1	27	2009-09-10 15:11:40.724+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	21
b4d34f26-638c-4c8e-946d-ba4387b81294	1	27	2009-09-10 15:11:40.734+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	22
efd68ce0-96f4-4e0f-a364-dee453c61b80	1	27	2009-09-10 15:11:40.744+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	23
1e6d61ee-2146-43e2-a526-5ab62eeedb54	1	27	2009-09-10 15:11:40.816+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	24
fd0775cc-d9a6-4c55-aab5-5d3240c47053	1	27	2009-09-10 15:11:40.826+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	25
1fc538e2-ad4b-49b7-ba4f-f16a99987a61	1	27	2009-09-10 15:11:40.836+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	26
f9ac5ccd-6d16-402c-b409-da19080829e3	1	27	2009-09-10 15:11:40.846+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	27
567d7d8d-0e68-4a8d-ad71-85ae7ac3190b	1	27	2009-09-10 15:11:40.857+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	28
40a6eb2d-fe42-43da-9ddf-f31562f60900	1	27	2009-09-10 15:11:40.868+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	29
b86399a2-61f5-4515-952e-81d7c57f3aa3	1	27	2009-09-10 15:11:40.878+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	30
750c9543-f618-4496-b9d6-d7b38d63e34d	1	27	2009-09-10 15:11:40.888+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	31
35ada535-eecc-4f1e-994b-897bf6649f74	1	34	2009-09-10 11:57:48.352+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	480be665-6307-4cc9-addd-436d4dbe5fff	\N	\N	\N	\N	\N	\N	\N	\N	7
89f53804-8ba7-49a8-b037-0e7f6ce9c2e7	1	34	2009-09-10 11:57:48.365+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	480be665-6307-4cc9-addd-436d4dbe5fff	\N	\N	\N	\N	\N	\N	\N	\N	8
8efe50c2-b227-4b24-a0ae-f0011aeaf047	1	34	2009-09-10 11:57:48.379+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	480be665-6307-4cc9-addd-436d4dbe5fff	\N	\N	\N	\N	\N	\N	\N	\N	9
633d090c-1ec7-422b-82aa-25ee5de40c4e	1	33	2009-09-10 11:57:48.393+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a83c2350-f4e3-4753-bcd3-aa5224863308	\N	\N	\N	\N	\N	\N	\N	\N	10
46da2323-d587-47e6-9a6c-a7d576de2bc1	1	34	2009-09-10 11:57:48.415+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	633d090c-1ec7-422b-82aa-25ee5de40c4e	\N	\N	\N	\N	\N	\N	\N	\N	1
3a9217ef-a44b-4432-b14e-50a7fc3d9ea1	1	34	2009-09-10 11:57:48.43+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	633d090c-1ec7-422b-82aa-25ee5de40c4e	\N	\N	\N	\N	\N	\N	\N	\N	2
82a48eda-f49a-403d-a6ea-93dd517a8e88	1	34	2009-09-10 11:57:48.444+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	633d090c-1ec7-422b-82aa-25ee5de40c4e	\N	\N	\N	\N	\N	\N	\N	\N	3
e11e4d6a-f907-4aa9-8b23-d002483702d2	1	34	2009-09-10 11:57:48.458+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	633d090c-1ec7-422b-82aa-25ee5de40c4e	\N	\N	\N	\N	\N	\N	\N	\N	4
f88dae65-5376-40fc-abec-eb43fdc3c8c6	1	34	2009-09-10 11:57:48.472+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	633d090c-1ec7-422b-82aa-25ee5de40c4e	\N	\N	\N	\N	\N	\N	\N	\N	5
d6e2a548-d386-4ed2-a16b-1b082be03281	1	34	2009-09-10 11:57:48.487+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	633d090c-1ec7-422b-82aa-25ee5de40c4e	\N	\N	\N	\N	\N	\N	\N	\N	6
2a92fe3f-861f-4a8d-a893-9c6591143681	1	34	2009-09-10 11:57:48.501+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	633d090c-1ec7-422b-82aa-25ee5de40c4e	\N	\N	\N	\N	\N	\N	\N	\N	7
e98ddc7c-1637-4e6c-99d8-680a12599262	1	34	2009-09-10 11:57:48.515+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	633d090c-1ec7-422b-82aa-25ee5de40c4e	\N	\N	\N	\N	\N	\N	\N	\N	8
b46dabdb-bc75-45fd-a601-0fbc89111f2a	1	34	2009-09-10 11:57:48.561+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	633d090c-1ec7-422b-82aa-25ee5de40c4e	\N	\N	\N	\N	\N	\N	\N	\N	9
daa7d6f8-4355-4008-b8af-2ab9746a3500	1	33	2009-09-10 11:57:48.576+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a83c2350-f4e3-4753-bcd3-aa5224863308	\N	\N	\N	\N	\N	\N	\N	\N	11
85c6f2a5-4d18-4d45-b8c4-04d4b3b4ab0b	1	34	2009-09-10 11:57:48.599+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	daa7d6f8-4355-4008-b8af-2ab9746a3500	\N	\N	\N	\N	\N	\N	\N	\N	1
7e6fae95-8e8c-44ea-9b70-0b0c55e6098a	1	34	2009-09-10 11:57:48.617+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	daa7d6f8-4355-4008-b8af-2ab9746a3500	\N	\N	\N	\N	\N	\N	\N	\N	2
e0f5ae23-8564-4de5-982b-e1df8cb9bb6d	1	34	2009-09-10 11:57:48.632+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	daa7d6f8-4355-4008-b8af-2ab9746a3500	\N	\N	\N	\N	\N	\N	\N	\N	3
a7e30952-581f-4f48-81e4-d1a18884109e	1	34	2009-09-10 11:57:48.646+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	daa7d6f8-4355-4008-b8af-2ab9746a3500	\N	\N	\N	\N	\N	\N	\N	\N	4
76080245-a35c-4660-8e61-9d244c640d06	1	34	2009-09-10 11:57:48.662+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	daa7d6f8-4355-4008-b8af-2ab9746a3500	\N	\N	\N	\N	\N	\N	\N	\N	5
698d43a7-b257-4b9c-8de5-a09982050298	1	34	2009-09-10 11:57:48.677+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	daa7d6f8-4355-4008-b8af-2ab9746a3500	\N	\N	\N	\N	\N	\N	\N	\N	6
5599552d-7f48-472b-abb9-41fe5c482998	1	34	2009-09-10 11:57:48.693+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	daa7d6f8-4355-4008-b8af-2ab9746a3500	\N	\N	\N	\N	\N	\N	\N	\N	7
aa531137-3f76-4fc6-89a4-87fe55d87c3d	1	34	2009-09-10 11:57:48.707+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	daa7d6f8-4355-4008-b8af-2ab9746a3500	\N	\N	\N	\N	\N	\N	\N	\N	8
bfb56378-f7e0-4d7d-a245-69230de08236	1	34	2009-09-10 11:57:48.723+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	daa7d6f8-4355-4008-b8af-2ab9746a3500	\N	\N	\N	\N	\N	\N	\N	\N	9
cf67051f-c986-4946-9cb1-e8423df8dfb8	1	27	2009-09-10 15:11:40.898+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	32
65d52a98-6c98-4ae6-b065-52b6c1a620b5	1	27	2009-09-10 15:11:40.908+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	33
9cac7e73-1eb4-4cd4-89b0-0aeebbf68824	1	27	2009-09-10 15:11:40.919+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	34
2fedf216-d607-4297-95be-f5ac482cba3b	1	27	2009-09-10 15:11:40.93+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	35
a0ca49fc-5d19-4e5e-840e-c0eea1325a7a	1	27	2009-09-10 15:11:40.941+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	36
54df6e19-a323-4675-875c-9721d06b5b7b	1	27	2009-09-10 15:11:40.951+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	37
eb0a0139-0488-4fce-b0c1-f8709f6d9165	1	27	2009-09-10 15:11:40.962+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	38
f54c975b-5a12-4c96-aabb-ed17fcf951c2	1	27	2009-09-10 15:11:40.973+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	39
1b4e76e2-48ed-4938-b297-767d04db0bad	1	27	2009-09-10 15:11:40.985+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	40
0bc30854-d46b-4b93-a52b-e38cdd53fdba	1	27	2009-09-10 15:11:40.996+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	41
61da4559-64da-41c9-bf09-a848abdabd0d	1	27	2009-09-10 15:11:41.006+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	42
bf2293f2-4592-4e4f-ac1f-0634ae2d6100	1	27	2009-09-10 15:11:41.017+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	43
177e1147-b46f-42a2-a2db-60c3d2a85170	1	27	2009-09-10 15:11:41.028+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	44
cce5d543-1222-4194-925b-a108cabc45b7	1	27	2009-09-10 15:11:41.04+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	45
b2cb3b2b-032e-42a9-bef6-9f2eb300b1d4	1	27	2009-09-10 15:11:41.053+03	adf38830-a4e6-4242-bc27-850344f22d1b	0d6c973b-71ea-4070-9c22-620fcc6005d5	f	f	f	f	f	17d7af2a-7efd-4443-9ffb-08636a8253c0	\N	\N	\N	\N	\N	\N	\N	\N	46
da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	1	15	2009-09-19 10:39:53.139+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	1
9c063e92-8e2c-40e7-8bce-e7accd360ec7	1	17	2009-09-19 10:39:53.261+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	1
c38a4163-0d89-4b4b-9714-5216f2ff3bbb	1	18	2009-09-19 10:39:53.465+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	1
abcd763f-0656-4f44-b678-9abd76c9a458	1	19	2009-09-19 10:39:53.473+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	9c063e92-8e2c-40e7-8bce-e7accd360ec7	\N	\N	\N	\N	\N	\N	\N	\N	1
4dab2e25-3980-4bc1-b66b-1716abba5264	1	20	2009-09-19 10:39:53.623+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	9c063e92-8e2c-40e7-8bce-e7accd360ec7	\N	\N	\N	\N	\N	\N	\N	\N	1
74c7d91c-9e74-46b9-be0f-6d94eb6025fb	1	21	2009-09-19 10:39:53.637+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	abcd763f-0656-4f44-b678-9abd76c9a458	\N	\N	\N	\N	\N	\N	\N	\N	1
6eba5b2a-17ed-439f-98b8-8dfc02f2226a	1	20	2009-09-19 10:39:53.645+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	9c063e92-8e2c-40e7-8bce-e7accd360ec7	\N	\N	\N	\N	\N	\N	\N	\N	2
12018716-11e6-4698-957e-5d3b2542a0a2	1	21	2009-09-19 10:39:53.71+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	abcd763f-0656-4f44-b678-9abd76c9a458	\N	\N	\N	\N	\N	\N	\N	\N	2
4951fb41-41d3-4c4a-81cb-a8f8ba063615	1	20	2009-09-19 10:39:53.716+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	9c063e92-8e2c-40e7-8bce-e7accd360ec7	\N	\N	\N	\N	\N	\N	\N	\N	3
6fbe9d78-8296-4751-ae96-fc34554ee898	1	21	2009-09-19 10:39:53.764+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	abcd763f-0656-4f44-b678-9abd76c9a458	\N	\N	\N	\N	\N	\N	\N	\N	3
7f8fe6b3-7ef2-4cbe-96d4-01342aced38e	1	23	2009-09-19 10:39:53.796+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	1
a8e231e3-c0ee-46ac-bf78-f875f1a70bc4	1	28	2009-09-19 10:39:53.807+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	1
62c2a7ba-e24d-47c0-be38-4d09fab7e2db	1	24	2009-09-19 10:39:53.878+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	7
5088d2ae-a9e6-4ed7-866f-c15b187074bb	1	30	2009-09-19 10:39:53.891+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	1
01052883-2042-4ed0-9089-b9750b1fd8c3	1	31	2009-09-19 10:39:53.902+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	62c2a7ba-e24d-47c0-be38-4d09fab7e2db	\N	\N	\N	\N	\N	\N	\N	\N	1
a7009cf1-7a53-48f2-9817-9b648a6d1d07	1	32	2009-09-19 10:40:01.815+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	1
c2e5d7f3-7c9d-4709-9a18-37d043d4ca52	1	32	2009-09-19 10:40:01.855+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	2
a03012dd-d518-47df-b0e5-8ffff2b94985	1	32	2009-09-19 10:40:01.868+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	3
2d636caa-1492-4d34-bb2f-c5b1a081311c	1	32	2009-09-19 10:40:01.879+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	4
fbeded15-7ab5-4894-9633-37e1d72523ac	1	32	2009-09-19 10:40:01.89+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	5
f2d73fb5-4eb8-469c-98b8-4872eb87b056	1	32	2009-09-19 10:40:01.901+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	6
bf98a523-550a-404c-b8e0-846cf1cfc7e9	1	32	2009-09-19 10:40:01.912+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	7
eed58cfb-8c7c-4574-9493-459ded6a3515	1	32	2009-09-19 10:40:01.922+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	8
7ff60842-8122-4d29-9ee1-6546b9fa572d	1	32	2009-09-19 10:40:01.933+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	9
ac16ca31-ad00-4864-89bd-04ae6ee34146	1	32	2009-09-19 10:40:01.943+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	10
d4a9a7b3-0997-41dd-8325-5691a5eff72a	1	32	2009-09-19 10:40:01.954+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	11
0385271d-225b-47bf-8b27-6e5e6c8f5602	1	32	2009-09-19 10:40:01.965+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	12
7b08a22a-adb5-4a2e-995a-72879ee2644b	1	33	2009-09-19 10:40:01.982+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	5088d2ae-a9e6-4ed7-866f-c15b187074bb	\N	\N	\N	\N	\N	\N	\N	\N	1
cc9e0936-a356-4e3a-8217-6f9859cf1826	1	34	2009-09-19 10:40:01.996+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	7b08a22a-adb5-4a2e-995a-72879ee2644b	\N	\N	\N	\N	\N	\N	\N	\N	1
6e6e57c2-79ab-46cc-9c99-30701344d851	1	34	2009-09-19 10:40:02.009+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	7b08a22a-adb5-4a2e-995a-72879ee2644b	\N	\N	\N	\N	\N	\N	\N	\N	2
bf45c3bd-2bf1-436e-8b0f-14c7dc9a9f93	1	34	2009-09-19 10:40:02.077+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	7b08a22a-adb5-4a2e-995a-72879ee2644b	\N	\N	\N	\N	\N	\N	\N	\N	3
8b1c8e8e-a107-43f9-ab69-4fe112e8a95e	1	34	2009-09-19 10:40:02.088+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	7b08a22a-adb5-4a2e-995a-72879ee2644b	\N	\N	\N	\N	\N	\N	\N	\N	4
28f03c46-70ea-4661-a2ce-baa41a389248	1	34	2009-09-19 10:40:02.099+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	7b08a22a-adb5-4a2e-995a-72879ee2644b	\N	\N	\N	\N	\N	\N	\N	\N	5
5cb1fc8a-df13-4a87-afed-13efb47564b9	1	34	2009-09-19 10:40:02.11+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	7b08a22a-adb5-4a2e-995a-72879ee2644b	\N	\N	\N	\N	\N	\N	\N	\N	6
c819da0b-3254-46f6-a476-e11ff59599c5	1	34	2009-09-19 10:40:02.121+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	7b08a22a-adb5-4a2e-995a-72879ee2644b	\N	\N	\N	\N	\N	\N	\N	\N	7
f637bdba-7e01-41fc-b8a7-0012acd4b3e8	1	34	2009-09-19 10:40:02.132+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	7b08a22a-adb5-4a2e-995a-72879ee2644b	\N	\N	\N	\N	\N	\N	\N	\N	8
9705c45b-41d2-4455-b408-3dfacb24a961	1	34	2009-09-19 10:40:02.143+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	7b08a22a-adb5-4a2e-995a-72879ee2644b	\N	\N	\N	\N	\N	\N	\N	\N	9
4e6a50df-f1bc-4db0-90c9-678b2f8619d8	1	33	2009-09-19 10:40:02.155+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	5088d2ae-a9e6-4ed7-866f-c15b187074bb	\N	\N	\N	\N	\N	\N	\N	\N	2
2deba95d-dfe6-4fa4-a108-a7c6a49e0f7f	1	34	2009-09-19 10:40:02.169+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	4e6a50df-f1bc-4db0-90c9-678b2f8619d8	\N	\N	\N	\N	\N	\N	\N	\N	1
ef936739-053f-40f5-ab2f-469039707415	1	34	2009-09-19 10:40:02.18+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	4e6a50df-f1bc-4db0-90c9-678b2f8619d8	\N	\N	\N	\N	\N	\N	\N	\N	2
7771f21f-e4b0-4344-bb48-f7094677c898	1	34	2009-09-19 10:40:02.19+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	4e6a50df-f1bc-4db0-90c9-678b2f8619d8	\N	\N	\N	\N	\N	\N	\N	\N	3
cfd6bff0-1603-4b6c-9cb7-d4475d73fdd5	1	34	2009-09-19 10:40:02.20+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	4e6a50df-f1bc-4db0-90c9-678b2f8619d8	\N	\N	\N	\N	\N	\N	\N	\N	4
b7b678bd-f154-4e9e-b67d-574b1eaf5420	1	34	2009-09-19 10:40:02.211+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	4e6a50df-f1bc-4db0-90c9-678b2f8619d8	\N	\N	\N	\N	\N	\N	\N	\N	5
937d1575-f831-4b4e-9675-129ae3d0de8c	1	34	2009-09-19 10:40:02.221+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	4e6a50df-f1bc-4db0-90c9-678b2f8619d8	\N	\N	\N	\N	\N	\N	\N	\N	6
9eff19ae-c359-4f2f-ae7f-cdd1a2544a91	1	34	2009-09-19 10:40:02.232+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	4e6a50df-f1bc-4db0-90c9-678b2f8619d8	\N	\N	\N	\N	\N	\N	\N	\N	7
bae3e6be-8a62-4b44-a6b8-367068d4a0d9	1	34	2009-09-19 10:40:02.243+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	4e6a50df-f1bc-4db0-90c9-678b2f8619d8	\N	\N	\N	\N	\N	\N	\N	\N	8
9a7e555a-6de0-4260-9dc2-3479cae5e8cc	1	34	2009-09-19 10:40:02.253+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	4e6a50df-f1bc-4db0-90c9-678b2f8619d8	\N	\N	\N	\N	\N	\N	\N	\N	9
1938a8db-7cf5-48a4-a695-faed54427747	1	33	2009-09-19 10:40:02.265+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	5088d2ae-a9e6-4ed7-866f-c15b187074bb	\N	\N	\N	\N	\N	\N	\N	\N	3
7fe20a1c-7418-4f5d-97e6-2ec49acec10b	1	34	2009-09-19 10:40:02.279+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	1938a8db-7cf5-48a4-a695-faed54427747	\N	\N	\N	\N	\N	\N	\N	\N	1
c06229a5-9310-4b35-8ded-f7bc9f7b7ba2	1	34	2009-09-19 10:40:02.289+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	1938a8db-7cf5-48a4-a695-faed54427747	\N	\N	\N	\N	\N	\N	\N	\N	2
f97204de-c022-4bdc-85c7-54842be82551	1	34	2009-09-19 10:40:02.299+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	1938a8db-7cf5-48a4-a695-faed54427747	\N	\N	\N	\N	\N	\N	\N	\N	3
cc19f7fe-409d-4bd3-8d00-efdda73aa833	1	34	2009-09-19 10:40:02.309+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	1938a8db-7cf5-48a4-a695-faed54427747	\N	\N	\N	\N	\N	\N	\N	\N	4
97349e7f-a4bd-4a24-9645-1d3220700d4e	1	34	2009-09-19 10:40:02.319+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	1938a8db-7cf5-48a4-a695-faed54427747	\N	\N	\N	\N	\N	\N	\N	\N	5
088bba75-8cc1-4a50-ab76-5ab00ef6fc2f	1	34	2009-09-19 10:40:02.33+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	1938a8db-7cf5-48a4-a695-faed54427747	\N	\N	\N	\N	\N	\N	\N	\N	6
ca8b4904-babd-4510-ac76-cf01057fb290	1	34	2009-09-19 10:40:02.341+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	1938a8db-7cf5-48a4-a695-faed54427747	\N	\N	\N	\N	\N	\N	\N	\N	7
cd127874-2892-4e58-b41f-0611cb4324be	1	34	2009-09-19 10:40:02.352+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	1938a8db-7cf5-48a4-a695-faed54427747	\N	\N	\N	\N	\N	\N	\N	\N	8
5d26e153-f743-4ede-8780-82dbba2c8ea7	1	34	2009-09-19 10:40:02.361+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	1938a8db-7cf5-48a4-a695-faed54427747	\N	\N	\N	\N	\N	\N	\N	\N	9
7546397d-fd68-4f84-a51c-f554b1d4405d	1	33	2009-09-19 10:40:02.374+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	5088d2ae-a9e6-4ed7-866f-c15b187074bb	\N	\N	\N	\N	\N	\N	\N	\N	4
651a693b-a1f6-446b-aaa3-f74801fd6fce	1	34	2009-09-19 10:40:02.387+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	7546397d-fd68-4f84-a51c-f554b1d4405d	\N	\N	\N	\N	\N	\N	\N	\N	1
1a232ffe-0f67-4666-ac31-b0839ad68eca	1	34	2009-09-19 10:40:02.398+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	7546397d-fd68-4f84-a51c-f554b1d4405d	\N	\N	\N	\N	\N	\N	\N	\N	2
c0cf0c9a-d497-4729-b273-ec36eeb39931	1	34	2009-09-19 10:40:02.409+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	7546397d-fd68-4f84-a51c-f554b1d4405d	\N	\N	\N	\N	\N	\N	\N	\N	3
d2a81b66-73f0-45dd-941c-9f6ac3642e12	1	34	2009-09-19 10:40:02.419+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	7546397d-fd68-4f84-a51c-f554b1d4405d	\N	\N	\N	\N	\N	\N	\N	\N	4
44de240f-e497-4281-9e80-650aa3ba5b54	1	34	2009-09-19 10:40:02.43+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	7546397d-fd68-4f84-a51c-f554b1d4405d	\N	\N	\N	\N	\N	\N	\N	\N	5
dfdafc44-f38f-444c-966c-7ff478244a07	1	34	2009-09-19 10:40:02.441+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	7546397d-fd68-4f84-a51c-f554b1d4405d	\N	\N	\N	\N	\N	\N	\N	\N	6
775572b6-5048-40ef-83ff-5346f96badc3	1	34	2009-09-19 10:40:02.452+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	7546397d-fd68-4f84-a51c-f554b1d4405d	\N	\N	\N	\N	\N	\N	\N	\N	7
af91bb84-4f8e-4644-aa12-45b41d133216	1	34	2009-09-19 10:40:02.463+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	7546397d-fd68-4f84-a51c-f554b1d4405d	\N	\N	\N	\N	\N	\N	\N	\N	8
6728adfa-f6ba-40b5-a580-0cab9fda38cf	1	34	2009-09-19 10:40:02.473+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	7546397d-fd68-4f84-a51c-f554b1d4405d	\N	\N	\N	\N	\N	\N	\N	\N	9
4d1ada12-1617-470d-86b6-71cf3d8f6cf5	1	33	2009-09-19 10:40:02.487+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	5088d2ae-a9e6-4ed7-866f-c15b187074bb	\N	\N	\N	\N	\N	\N	\N	\N	5
7044c093-be98-4647-b30c-e1b87cecdc82	1	34	2009-09-19 10:40:02.503+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	4d1ada12-1617-470d-86b6-71cf3d8f6cf5	\N	\N	\N	\N	\N	\N	\N	\N	1
e7817742-8b51-4723-ba60-30a17531bce3	1	34	2009-09-19 10:40:02.513+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	4d1ada12-1617-470d-86b6-71cf3d8f6cf5	\N	\N	\N	\N	\N	\N	\N	\N	2
c249e3ba-8b57-4710-acd7-2ec5284256ef	1	34	2009-09-19 10:40:02.524+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	4d1ada12-1617-470d-86b6-71cf3d8f6cf5	\N	\N	\N	\N	\N	\N	\N	\N	3
8c574bb9-3814-404b-bfed-266d105398b1	1	34	2009-09-19 10:40:02.535+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	4d1ada12-1617-470d-86b6-71cf3d8f6cf5	\N	\N	\N	\N	\N	\N	\N	\N	4
3d02337f-c81e-4ad3-a566-a0d9f7863c49	1	34	2009-09-19 10:40:02.546+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	4d1ada12-1617-470d-86b6-71cf3d8f6cf5	\N	\N	\N	\N	\N	\N	\N	\N	5
7bb253f6-bc7e-4b58-869c-e61d3974c545	1	34	2009-09-19 10:40:02.558+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	4d1ada12-1617-470d-86b6-71cf3d8f6cf5	\N	\N	\N	\N	\N	\N	\N	\N	6
b8bd4744-f39f-4abc-8471-fb1b108266e8	1	34	2009-09-19 10:40:02.57+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	4d1ada12-1617-470d-86b6-71cf3d8f6cf5	\N	\N	\N	\N	\N	\N	\N	\N	7
49c1a05c-ad8d-41dc-a89f-e1e9bfd89988	1	34	2009-09-19 10:40:02.591+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	4d1ada12-1617-470d-86b6-71cf3d8f6cf5	\N	\N	\N	\N	\N	\N	\N	\N	8
25bba0dd-1e4c-439f-bb4a-0f44757876d4	1	34	2009-09-19 10:40:02.603+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	4d1ada12-1617-470d-86b6-71cf3d8f6cf5	\N	\N	\N	\N	\N	\N	\N	\N	9
7cb0c2eb-7d1e-4a44-9325-9e805fb5ac11	1	33	2009-09-19 10:40:02.614+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	5088d2ae-a9e6-4ed7-866f-c15b187074bb	\N	\N	\N	\N	\N	\N	\N	\N	6
7890ba80-7e49-426c-9ff2-534d5ba5eac7	1	34	2009-09-19 10:40:02.629+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	7cb0c2eb-7d1e-4a44-9325-9e805fb5ac11	\N	\N	\N	\N	\N	\N	\N	\N	1
ef2cd69a-1260-4420-ba9d-08626fe8e1ff	1	34	2009-09-19 10:40:02.641+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	7cb0c2eb-7d1e-4a44-9325-9e805fb5ac11	\N	\N	\N	\N	\N	\N	\N	\N	2
1dad7093-9ba1-42f0-a4ec-825e4f5337b8	1	34	2009-09-19 10:40:02.652+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	7cb0c2eb-7d1e-4a44-9325-9e805fb5ac11	\N	\N	\N	\N	\N	\N	\N	\N	3
131ef2fd-9e47-4636-860e-6fa8b51b3121	1	34	2009-09-19 10:40:02.664+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	7cb0c2eb-7d1e-4a44-9325-9e805fb5ac11	\N	\N	\N	\N	\N	\N	\N	\N	4
15ba3e14-a631-4f4c-a4aa-2cc81c906a02	1	34	2009-09-19 10:40:02.69+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	7cb0c2eb-7d1e-4a44-9325-9e805fb5ac11	\N	\N	\N	\N	\N	\N	\N	\N	5
9fa0d876-f20b-4be3-85cb-d3b84a726b9f	1	34	2009-09-19 10:40:02.701+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	7cb0c2eb-7d1e-4a44-9325-9e805fb5ac11	\N	\N	\N	\N	\N	\N	\N	\N	6
aaf509ef-715c-4140-886f-66e72e029722	1	34	2009-09-19 10:40:02.713+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	7cb0c2eb-7d1e-4a44-9325-9e805fb5ac11	\N	\N	\N	\N	\N	\N	\N	\N	7
a9fcfa3a-5680-428f-919f-d4ced3621360	1	34	2009-09-19 10:40:02.725+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	7cb0c2eb-7d1e-4a44-9325-9e805fb5ac11	\N	\N	\N	\N	\N	\N	\N	\N	8
5d97b730-d795-42c0-b5e0-0a5f9b4d4a6d	1	34	2009-09-19 10:40:02.736+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	7cb0c2eb-7d1e-4a44-9325-9e805fb5ac11	\N	\N	\N	\N	\N	\N	\N	\N	9
aac0c591-ae77-4b55-94fe-4823ee2b2a94	1	33	2009-09-19 10:40:02.75+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	5088d2ae-a9e6-4ed7-866f-c15b187074bb	\N	\N	\N	\N	\N	\N	\N	\N	7
e46627de-fcea-4163-9cbb-be80823ed64a	1	34	2009-09-19 10:40:02.766+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	aac0c591-ae77-4b55-94fe-4823ee2b2a94	\N	\N	\N	\N	\N	\N	\N	\N	1
f1198612-6b26-4afd-b3ba-4338a87d802b	1	34	2009-09-19 10:40:02.778+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	aac0c591-ae77-4b55-94fe-4823ee2b2a94	\N	\N	\N	\N	\N	\N	\N	\N	2
eaf4d9df-0c46-4478-9fbb-66f907ce28b0	1	34	2009-09-19 10:40:02.79+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	aac0c591-ae77-4b55-94fe-4823ee2b2a94	\N	\N	\N	\N	\N	\N	\N	\N	3
974a46e0-fbde-44b3-8575-a66b76755047	1	34	2009-09-19 10:40:02.801+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	aac0c591-ae77-4b55-94fe-4823ee2b2a94	\N	\N	\N	\N	\N	\N	\N	\N	4
99948915-f1b2-428a-924d-a04579c9bc87	1	34	2009-09-19 10:40:02.812+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	aac0c591-ae77-4b55-94fe-4823ee2b2a94	\N	\N	\N	\N	\N	\N	\N	\N	5
f08c49db-b9d0-4b62-9f64-3871c9619f48	1	34	2009-09-19 10:40:02.823+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	aac0c591-ae77-4b55-94fe-4823ee2b2a94	\N	\N	\N	\N	\N	\N	\N	\N	6
f8465de1-6fef-4845-85d7-0edc7fc8cf36	1	34	2009-09-19 10:40:02.835+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	aac0c591-ae77-4b55-94fe-4823ee2b2a94	\N	\N	\N	\N	\N	\N	\N	\N	7
d1a9be1c-2b36-47e8-8dcc-f974a692ebb7	1	34	2009-09-19 10:40:02.846+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	aac0c591-ae77-4b55-94fe-4823ee2b2a94	\N	\N	\N	\N	\N	\N	\N	\N	8
1ae0a2bc-21ce-4d15-9451-5ebe0889cbe6	1	34	2009-09-19 10:40:02.858+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	aac0c591-ae77-4b55-94fe-4823ee2b2a94	\N	\N	\N	\N	\N	\N	\N	\N	9
b7a4b0e4-6062-493e-b29a-2383c6642857	1	33	2009-09-19 10:40:02.869+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	5088d2ae-a9e6-4ed7-866f-c15b187074bb	\N	\N	\N	\N	\N	\N	\N	\N	8
5f450c67-a1a8-4755-a53a-8cd729a7319f	1	34	2009-09-19 10:40:02.885+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	b7a4b0e4-6062-493e-b29a-2383c6642857	\N	\N	\N	\N	\N	\N	\N	\N	1
af84623e-1696-46fc-8625-10bde0043677	1	34	2009-09-19 10:40:02.897+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	b7a4b0e4-6062-493e-b29a-2383c6642857	\N	\N	\N	\N	\N	\N	\N	\N	2
bb8bff6d-16b2-4ac1-a239-b11bd506a79c	1	34	2009-09-19 10:40:02.909+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	b7a4b0e4-6062-493e-b29a-2383c6642857	\N	\N	\N	\N	\N	\N	\N	\N	3
efe0e2c0-c550-42c9-9066-8846274b53a9	1	34	2009-09-19 10:40:02.92+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	b7a4b0e4-6062-493e-b29a-2383c6642857	\N	\N	\N	\N	\N	\N	\N	\N	4
483e31a6-b974-46f9-a936-746cae70df1e	1	34	2009-09-19 10:40:02.932+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	b7a4b0e4-6062-493e-b29a-2383c6642857	\N	\N	\N	\N	\N	\N	\N	\N	5
6c9d28a4-5c82-4adf-be4f-096baac2cfcb	1	34	2009-09-19 10:40:02.944+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	b7a4b0e4-6062-493e-b29a-2383c6642857	\N	\N	\N	\N	\N	\N	\N	\N	6
948b75d9-ec59-45d7-a185-a04ff409a63c	1	34	2009-09-19 10:40:02.959+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	b7a4b0e4-6062-493e-b29a-2383c6642857	\N	\N	\N	\N	\N	\N	\N	\N	7
fa536025-5c5e-4224-af71-b13650dc478e	1	34	2009-09-19 10:40:02.973+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	b7a4b0e4-6062-493e-b29a-2383c6642857	\N	\N	\N	\N	\N	\N	\N	\N	8
c90c8aeb-4706-4626-bf49-45f2a7f06a7e	1	34	2009-09-19 10:40:02.986+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	b7a4b0e4-6062-493e-b29a-2383c6642857	\N	\N	\N	\N	\N	\N	\N	\N	9
e0a4f9cb-c398-40ec-be2c-d4382df9370f	1	33	2009-09-19 10:40:03+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	5088d2ae-a9e6-4ed7-866f-c15b187074bb	\N	\N	\N	\N	\N	\N	\N	\N	9
5dfcd2d4-f898-42b6-bbcf-35f046b082a1	1	34	2009-09-19 10:40:03.017+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e0a4f9cb-c398-40ec-be2c-d4382df9370f	\N	\N	\N	\N	\N	\N	\N	\N	1
e702ec58-91ef-4989-9e1b-4bae4f943575	1	34	2009-09-19 10:40:03.029+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e0a4f9cb-c398-40ec-be2c-d4382df9370f	\N	\N	\N	\N	\N	\N	\N	\N	2
9621a188-fe9a-48d2-9d65-0dcf93371bbb	1	34	2009-09-19 10:40:03.042+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e0a4f9cb-c398-40ec-be2c-d4382df9370f	\N	\N	\N	\N	\N	\N	\N	\N	3
c496a63a-1dc6-4183-94e5-7ed4300836dc	1	34	2009-09-19 10:40:03.055+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e0a4f9cb-c398-40ec-be2c-d4382df9370f	\N	\N	\N	\N	\N	\N	\N	\N	4
42551c03-d17f-4bae-a0a9-0e21bf4279fc	1	34	2009-09-19 10:40:03.068+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e0a4f9cb-c398-40ec-be2c-d4382df9370f	\N	\N	\N	\N	\N	\N	\N	\N	5
4dcfe241-b456-466d-aa47-3e6cad6ad73f	1	34	2009-09-19 10:40:03.081+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e0a4f9cb-c398-40ec-be2c-d4382df9370f	\N	\N	\N	\N	\N	\N	\N	\N	6
a84a97a2-28ae-4315-9f0c-9ceebebb857b	1	34	2009-09-19 10:40:03.095+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e0a4f9cb-c398-40ec-be2c-d4382df9370f	\N	\N	\N	\N	\N	\N	\N	\N	7
0449d42f-2ac3-4f4b-95a7-5df3e1b132a5	1	34	2009-09-19 10:40:03.108+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e0a4f9cb-c398-40ec-be2c-d4382df9370f	\N	\N	\N	\N	\N	\N	\N	\N	8
8d94a75f-c69d-4591-a643-50a959190f4f	1	34	2009-09-19 10:40:03.121+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e0a4f9cb-c398-40ec-be2c-d4382df9370f	\N	\N	\N	\N	\N	\N	\N	\N	9
018ccce0-4067-4ebb-bc9f-92b9c45324ba	1	33	2009-09-19 10:40:03.134+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	5088d2ae-a9e6-4ed7-866f-c15b187074bb	\N	\N	\N	\N	\N	\N	\N	\N	10
e5228421-6057-43c9-a3e1-16cf6ead17b1	1	34	2009-09-19 10:40:03.151+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	018ccce0-4067-4ebb-bc9f-92b9c45324ba	\N	\N	\N	\N	\N	\N	\N	\N	1
fda33b84-e461-44dc-978c-cdfcf08c5fa7	1	34	2009-09-19 10:40:03.163+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	018ccce0-4067-4ebb-bc9f-92b9c45324ba	\N	\N	\N	\N	\N	\N	\N	\N	2
9cb51de9-ff35-4719-81a9-8a06a230f2f9	1	34	2009-09-19 10:40:03.177+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	018ccce0-4067-4ebb-bc9f-92b9c45324ba	\N	\N	\N	\N	\N	\N	\N	\N	3
114e674c-9269-4bf0-a627-8cc6e455657d	1	34	2009-09-19 10:40:03.191+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	018ccce0-4067-4ebb-bc9f-92b9c45324ba	\N	\N	\N	\N	\N	\N	\N	\N	4
fe8613d0-4ea5-42fc-a0b4-84f6a3344bf0	1	34	2009-09-19 10:40:03.206+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	018ccce0-4067-4ebb-bc9f-92b9c45324ba	\N	\N	\N	\N	\N	\N	\N	\N	5
c7d06f25-154b-40b9-96db-aa43036d8e21	1	34	2009-09-19 10:40:03.219+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	018ccce0-4067-4ebb-bc9f-92b9c45324ba	\N	\N	\N	\N	\N	\N	\N	\N	6
eb31865c-90ef-4076-89bb-05e2b4e13aa2	1	34	2009-09-19 10:40:03.232+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	018ccce0-4067-4ebb-bc9f-92b9c45324ba	\N	\N	\N	\N	\N	\N	\N	\N	7
8698c249-22b5-42eb-8b5e-af59c7dc5b68	1	34	2009-09-19 10:40:03.245+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	018ccce0-4067-4ebb-bc9f-92b9c45324ba	\N	\N	\N	\N	\N	\N	\N	\N	8
3c8d67f5-13af-4900-a08c-9e8e395475db	1	34	2009-09-19 10:40:03.259+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	018ccce0-4067-4ebb-bc9f-92b9c45324ba	\N	\N	\N	\N	\N	\N	\N	\N	9
202fbcdc-af15-41c4-8a05-6baecc5cdb61	1	33	2009-09-19 10:40:03.273+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	5088d2ae-a9e6-4ed7-866f-c15b187074bb	\N	\N	\N	\N	\N	\N	\N	\N	11
bb257d22-25cc-47d0-8c8c-3c2a7fa0c924	1	34	2009-09-19 10:40:03.291+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	202fbcdc-af15-41c4-8a05-6baecc5cdb61	\N	\N	\N	\N	\N	\N	\N	\N	1
678dee9a-4a4e-42e7-b603-f06bcdb1e9f6	1	34	2009-09-19 10:40:03.306+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	202fbcdc-af15-41c4-8a05-6baecc5cdb61	\N	\N	\N	\N	\N	\N	\N	\N	2
2f5ab34b-7d66-490b-b8a6-b94986eec8ad	1	34	2009-09-19 10:40:03.319+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	202fbcdc-af15-41c4-8a05-6baecc5cdb61	\N	\N	\N	\N	\N	\N	\N	\N	3
8b53b5e6-d747-46f6-aa04-d20f63965e1a	1	34	2009-09-19 10:40:03.332+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	202fbcdc-af15-41c4-8a05-6baecc5cdb61	\N	\N	\N	\N	\N	\N	\N	\N	4
02126353-9b54-450a-a959-860432097a32	1	34	2009-09-19 10:40:03.344+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	202fbcdc-af15-41c4-8a05-6baecc5cdb61	\N	\N	\N	\N	\N	\N	\N	\N	5
7fb2cb12-3426-4467-bbf9-43433df0e2ed	1	34	2009-09-19 10:40:03.358+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	202fbcdc-af15-41c4-8a05-6baecc5cdb61	\N	\N	\N	\N	\N	\N	\N	\N	6
a200faf4-c69c-44cd-bcb5-81fbfc5c9574	1	34	2009-09-19 10:40:03.371+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	202fbcdc-af15-41c4-8a05-6baecc5cdb61	\N	\N	\N	\N	\N	\N	\N	\N	7
0e8a5425-3f8e-4f94-b9e8-a2f2e442e023	1	34	2009-09-19 10:40:03.385+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	202fbcdc-af15-41c4-8a05-6baecc5cdb61	\N	\N	\N	\N	\N	\N	\N	\N	8
4d739147-75eb-4df2-ae64-ed588d025fab	1	34	2009-09-19 10:40:03.399+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	202fbcdc-af15-41c4-8a05-6baecc5cdb61	\N	\N	\N	\N	\N	\N	\N	\N	9
1e5fbedd-da1a-4172-82ad-ec3642533be7	1	25	2009-09-19 10:48:15.972+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	1
0e5448d9-fc9b-48dd-8088-0a6fa2fcbd29	1	25	2009-09-19 10:48:15.996+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	2
669e3881-0d39-4594-9150-55675bde0d19	1	25	2009-09-19 10:48:16.004+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	3
b9b06566-8108-4ba0-ad1e-c38163866e70	1	25	2009-09-19 10:48:16.009+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	4
50b5a0be-7428-474d-ba49-8022bafc041c	1	25	2009-09-19 10:48:16.014+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	5
cf7d8334-f0b0-40da-a0c6-aaf0f5b343bc	1	25	2009-09-19 10:48:16.019+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	6
8eb1e632-7d54-46b2-95cb-231f7be09640	1	25	2009-09-19 10:48:16.024+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	7
fe5bd110-509d-41b2-ad0e-48a25d0ded15	1	25	2009-09-19 10:48:16.075+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	8
c26d6fcf-662c-456b-9f58-a4d48e0152cd	1	25	2009-09-19 10:48:16.08+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	9
e8709fa6-be68-4fd1-b1ed-9f6db277e313	1	25	2009-09-19 10:48:16.085+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	10
aca3abbc-90fe-4ec8-a6fa-91c92f668bb1	1	25	2009-09-19 10:48:16.091+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	11
30671598-b143-4bd3-b76f-f9818131f01c	1	25	2009-09-19 10:48:16.096+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	12
bf6a2292-7ed6-49ff-8ab0-eb5c5800eef1	1	26	2009-09-19 10:48:16.157+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	1
126abc97-671e-4769-bfb5-6dd47bb011f3	1	27	2009-09-19 10:48:16.244+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	1
2e654e9d-ae6c-4510-b3f6-3528a294a1b6	1	27	2009-09-19 10:48:16.336+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	2
d87d366f-db56-4a41-9663-96d6ef7c7446	1	27	2009-09-19 10:48:16.345+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	3
dc6b55ba-390d-4787-b764-42d38eb2120f	1	27	2009-09-19 10:48:16.355+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	4
5125c029-1aa4-49a3-a31d-b3e6de58b14b	1	27	2009-09-19 10:48:16.364+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	5
e3a779f3-05fe-41d3-89af-2274bdad2e35	1	27	2009-09-19 10:48:16.372+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	6
6588836d-497e-433e-8768-f596e35da886	1	27	2009-09-19 10:48:16.381+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	7
b17e8a26-f588-4d10-9587-29284e15e302	1	27	2009-09-19 10:48:16.391+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	8
1a683908-592c-4640-8153-acf94da4e510	1	27	2009-09-19 10:48:16.40+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	9
828bcc53-02b9-4d6c-89fe-0262267cfbd1	1	27	2009-09-19 10:48:16.408+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	10
93d46465-0f18-487d-88fb-72d425c74c75	1	27	2009-09-19 10:48:16.417+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	11
9ea9c140-2d75-422f-8bbd-e57282231284	1	27	2009-09-19 10:48:16.427+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	12
e1b511f6-5c13-45e9-8488-76a464258958	1	27	2009-09-19 10:48:16.436+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	13
619d1c82-9f01-4f4a-bb5e-9c24b8256790	1	27	2009-09-19 10:48:16.445+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	14
3ae2104b-b16d-4e3b-8628-08f18f78b558	1	27	2009-09-19 10:48:16.455+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	15
e44496fc-3dd4-4bb2-a46f-56207abcb2d6	1	27	2009-09-19 10:48:16.465+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	16
0e326303-13e5-4559-b6ec-d7d7118e20df	1	27	2009-09-19 10:48:16.474+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	17
5b4b8d54-e893-4c1a-aa45-db2612034d41	1	27	2009-09-19 10:48:16.484+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	18
b9e20c0a-d114-4b7e-ad47-52d13228dd70	1	27	2009-09-19 10:48:16.496+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	19
90b044e5-16c6-4981-81e7-6959b900c229	1	27	2009-09-19 10:48:16.506+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	20
b860975a-8a80-45aa-b5f9-081cf02e7f64	1	27	2009-09-19 10:48:16.515+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	21
14a680f9-8361-4be2-91c6-fce671786471	1	27	2009-09-19 10:48:16.526+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	22
836dc28d-9437-460b-af1f-688332f0cee8	1	27	2009-09-19 10:48:16.536+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	23
97db7a9b-ffe4-49f0-9503-bc37c45fcdcb	1	27	2009-09-19 10:48:16.546+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	24
e7caccf7-dcde-4776-b213-f4f5721ff2d6	1	27	2009-09-19 10:48:16.555+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	25
632b18bc-2b67-4600-b2a5-8ba33d78eae2	1	27	2009-09-19 10:48:16.565+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	26
7051d328-3f31-4c90-9e16-47ce7ef180e7	1	27	2009-09-19 10:48:16.575+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	27
8b831094-f88c-43b6-b458-32767f1964fc	1	27	2009-09-19 10:48:16.585+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	28
035f3c74-e132-4394-95c4-f2fcebc43483	1	27	2009-09-19 10:48:16.595+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	29
8413e24a-0ec8-4e56-9878-924775ae7774	1	27	2009-09-19 10:48:16.607+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	30
14e96bce-16c3-4449-8c0f-7ecbe1fdd739	1	27	2009-09-19 10:48:16.617+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	31
e79df852-10cf-469e-a8c7-b0b724d40f42	1	27	2009-09-19 10:48:16.627+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	32
1334264e-1446-456f-9427-1e7fed3386e6	1	27	2009-09-19 10:48:16.638+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	33
77542b66-36f8-4788-b0ee-f8f39b11053e	1	27	2009-09-19 10:48:16.649+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	34
dca65005-30d4-4f96-ac74-a091d3bb26f5	1	27	2009-09-19 10:48:16.661+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	35
e6dd79c2-88ab-4f09-a289-bcc306a626d5	1	27	2009-09-19 10:48:16.671+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	36
3dc365d1-849a-4fa1-bbed-bbea38577088	1	27	2009-09-19 10:48:16.681+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	37
fb6d9043-9356-4bf2-b794-bd2cf453c5da	1	27	2009-09-19 10:48:16.692+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	38
ced27eca-d518-4414-b86d-6e3942b68830	1	27	2009-09-19 10:48:16.703+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	39
5e75f75b-ec6a-4a30-8825-4821a7ba9280	1	27	2009-09-19 10:48:16.714+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	40
82e61a5a-d66a-456c-a69f-6d0131d1a739	1	27	2009-09-19 10:48:16.725+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	41
81c5ce9d-6eff-401c-af2c-bae97c67703d	1	27	2009-09-19 10:48:16.736+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	42
8d83ad50-f2c8-4646-8055-6dfb3963fcc0	1	27	2009-09-19 10:48:16.747+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	43
389673e2-2398-4528-b7f0-a91de7057ef4	1	27	2009-09-19 10:48:16.758+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	44
64e8557f-b214-4773-9310-6b8d3d044cf1	1	27	2009-09-19 10:48:16.77+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	45
0f36426d-85cc-4732-a66a-9d6e907e4a45	1	27	2009-09-19 10:48:16.781+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd	\N	\N	\N	\N	\N	\N	\N	\N	46
a015d690-6fe6-4e4f-89d6-e769c30f58db	1	28	2009-09-19 11:07:42.769+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	1
108d48fa-7ffe-4bee-b1d3-c7532d0505d2	1	28	2009-09-19 11:07:42.781+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	2
be102e1f-00f5-48b5-9cbc-3d2583527629	1	15	2009-09-19 11:07:42.58+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	1
3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	1	17	2009-09-19 11:07:42.632+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	1
a62f4b7b-9a73-4c3c-96d8-3e9697f09815	1	18	2009-09-19 11:07:42.669+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	1
151879ef-0e48-4685-ac16-7e437e12663f	1	19	2009-09-19 11:07:42.678+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	\N	\N	\N	\N	\N	\N	\N	\N	1
5e1cf84a-9b40-46a6-8975-9735d3cceb84	1	20	2009-09-19 11:07:42.689+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	\N	\N	\N	\N	\N	\N	\N	\N	1
8b5f9c3a-1406-4e47-bf65-3dd836edb261	1	21	2009-09-19 11:07:42.703+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	151879ef-0e48-4685-ac16-7e437e12663f	\N	\N	\N	\N	\N	\N	\N	\N	1
3fac3361-2957-4ee9-ab70-84bc21717367	1	20	2009-09-19 11:07:42.71+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	\N	\N	\N	\N	\N	\N	\N	\N	2
b7db80d9-3234-4041-8e55-9c6faecd2067	1	21	2009-09-19 11:07:42.722+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	151879ef-0e48-4685-ac16-7e437e12663f	\N	\N	\N	\N	\N	\N	\N	\N	2
429491d1-d190-4257-bda6-b8ae304c02f5	1	20	2009-09-19 11:07:42.728+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	\N	\N	\N	\N	\N	\N	\N	\N	3
8ebd7ce8-711a-4a80-a97c-d6e9eb2b4592	1	21	2009-09-19 11:07:42.738+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	151879ef-0e48-4685-ac16-7e437e12663f	\N	\N	\N	\N	\N	\N	\N	\N	3
82f1e267-d88a-44ae-b56f-3407e00f2193	1	23	2009-09-19 11:07:42.759+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	1
4be6fbf6-ee74-46fd-9a01-4105fe0275ec	1	24	2009-09-19 11:07:42.793+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	8
a521d276-8827-482e-b703-e81d9f62cb26	1	30	2009-09-19 11:07:42.804+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	1
869834c1-575e-4d73-b6dc-bddb231b96bf	1	31	2009-09-19 11:07:42.817+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	4be6fbf6-ee74-46fd-9a01-4105fe0275ec	\N	\N	\N	\N	\N	\N	\N	\N	1
1c9f4792-6eaa-446b-b26c-acb256cde07b	1	32	2009-09-19 11:07:45.83+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	1
4e10e826-0ce7-4962-84cd-0f2075a2401e	1	32	2009-09-19 11:07:45.841+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	2
7bfc2e55-258f-4af5-890a-24b2ad007d6f	1	32	2009-09-19 11:07:45.85+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	3
f0ac1eb4-9eae-489e-9554-b8d487b1d0e9	1	32	2009-09-19 11:07:45.859+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	4
1988ca35-ad9a-4bbe-bed1-bd7c0eabfbc7	1	32	2009-09-19 11:07:45.868+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	5
0a15831a-2524-494b-a9a3-7ec1e7d7217b	1	32	2009-09-19 11:07:45.878+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	6
3904dc70-5259-4f40-967e-a6b97ec1b846	1	32	2009-09-19 11:07:45.886+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	7
5b2d5332-951c-4aee-bcc5-2f4f44d3d854	1	32	2009-09-19 11:07:45.895+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	8
48e10769-7f21-438d-9c27-6c637106ccf4	1	32	2009-09-19 11:07:45.905+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	9
f15f540d-d40a-440a-91a2-c43fc3bf33b5	1	32	2009-09-19 11:07:45.917+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	10
082dc2e1-3eb1-4dec-98bb-a1e060aa2477	1	32	2009-09-19 11:07:45.926+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	11
c75694f1-28c2-46f2-9a9d-e3749983c577	1	32	2009-09-19 11:07:45.936+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	12
e13c349b-5883-4871-b548-3a65bd9b20df	1	33	2009-09-19 11:07:45.962+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a521d276-8827-482e-b703-e81d9f62cb26	\N	\N	\N	\N	\N	\N	\N	\N	1
09ea2dbd-2c8a-469f-8c08-f140ea481d95	1	34	2009-09-19 11:07:45.974+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e13c349b-5883-4871-b548-3a65bd9b20df	\N	\N	\N	\N	\N	\N	\N	\N	1
5756f597-becc-48ad-a6f2-12174c660c2f	1	34	2009-09-19 11:07:45.985+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e13c349b-5883-4871-b548-3a65bd9b20df	\N	\N	\N	\N	\N	\N	\N	\N	2
90e2335e-eb1d-41e6-9e8a-77176fa06a5a	1	34	2009-09-19 11:07:45.997+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e13c349b-5883-4871-b548-3a65bd9b20df	\N	\N	\N	\N	\N	\N	\N	\N	3
d218f02b-1402-4210-adb8-dad24625e95f	1	34	2009-09-19 11:07:46.006+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e13c349b-5883-4871-b548-3a65bd9b20df	\N	\N	\N	\N	\N	\N	\N	\N	4
0066d33d-b8cb-4436-8c50-7f78a44804a0	1	34	2009-09-19 11:07:46.016+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e13c349b-5883-4871-b548-3a65bd9b20df	\N	\N	\N	\N	\N	\N	\N	\N	5
ccd36808-38f1-446b-a969-246d4c1854e3	1	34	2009-09-19 11:07:46.026+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e13c349b-5883-4871-b548-3a65bd9b20df	\N	\N	\N	\N	\N	\N	\N	\N	6
72f9e33b-6444-448f-9deb-9c172ffee3d5	1	34	2009-09-19 11:07:46.035+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e13c349b-5883-4871-b548-3a65bd9b20df	\N	\N	\N	\N	\N	\N	\N	\N	7
d3908776-5e2c-4eea-b074-31110888c0b3	1	34	2009-09-19 11:07:46.044+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e13c349b-5883-4871-b548-3a65bd9b20df	\N	\N	\N	\N	\N	\N	\N	\N	8
6df5e9e2-b698-4acd-bfa1-5d7062274ff0	1	34	2009-09-19 11:07:46.053+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e13c349b-5883-4871-b548-3a65bd9b20df	\N	\N	\N	\N	\N	\N	\N	\N	9
45d26b4b-53e9-4bfe-bf47-8a19b5d717e9	1	33	2009-09-19 11:07:46.063+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a521d276-8827-482e-b703-e81d9f62cb26	\N	\N	\N	\N	\N	\N	\N	\N	2
0e2a1634-bf8e-413e-865a-7a390012a890	1	34	2009-09-19 11:07:46.075+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	45d26b4b-53e9-4bfe-bf47-8a19b5d717e9	\N	\N	\N	\N	\N	\N	\N	\N	1
a9e81610-af61-4d4b-8631-caac2d8f39db	1	34	2009-09-19 11:07:46.084+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	45d26b4b-53e9-4bfe-bf47-8a19b5d717e9	\N	\N	\N	\N	\N	\N	\N	\N	2
3e190b63-8eea-47ee-a610-a2524ec3baeb	1	34	2009-09-19 11:07:46.094+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	45d26b4b-53e9-4bfe-bf47-8a19b5d717e9	\N	\N	\N	\N	\N	\N	\N	\N	3
95ff1d30-ff31-418c-bba7-776a25777f08	1	34	2009-09-19 11:07:46.103+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	45d26b4b-53e9-4bfe-bf47-8a19b5d717e9	\N	\N	\N	\N	\N	\N	\N	\N	4
4b4b8c01-f51d-4f5e-a7a7-2c5ab80e2802	1	34	2009-09-19 11:07:46.113+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	45d26b4b-53e9-4bfe-bf47-8a19b5d717e9	\N	\N	\N	\N	\N	\N	\N	\N	5
f7187cfd-1293-4eaa-8008-299a0240f5e2	1	34	2009-09-19 11:07:46.123+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	45d26b4b-53e9-4bfe-bf47-8a19b5d717e9	\N	\N	\N	\N	\N	\N	\N	\N	6
916606bd-de88-4001-a95f-2ee77f81a9f3	1	34	2009-09-19 11:07:46.133+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	45d26b4b-53e9-4bfe-bf47-8a19b5d717e9	\N	\N	\N	\N	\N	\N	\N	\N	7
fd67257c-aad8-4784-83c3-72c09cf3295f	1	34	2009-09-19 11:07:46.143+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	45d26b4b-53e9-4bfe-bf47-8a19b5d717e9	\N	\N	\N	\N	\N	\N	\N	\N	8
08ef4122-c2ad-44df-afee-3a4572c3636b	1	34	2009-09-19 11:07:46.153+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	45d26b4b-53e9-4bfe-bf47-8a19b5d717e9	\N	\N	\N	\N	\N	\N	\N	\N	9
2a90661c-7924-4fb5-904a-9f66232cf837	1	33	2009-09-19 11:07:46.164+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a521d276-8827-482e-b703-e81d9f62cb26	\N	\N	\N	\N	\N	\N	\N	\N	3
0a63d737-c53b-4c7f-a1d2-d33b88d17034	1	34	2009-09-19 11:07:46.177+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2a90661c-7924-4fb5-904a-9f66232cf837	\N	\N	\N	\N	\N	\N	\N	\N	1
5f4de7f0-7630-4751-b4f7-f28baa1798d4	1	34	2009-09-19 11:07:46.188+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2a90661c-7924-4fb5-904a-9f66232cf837	\N	\N	\N	\N	\N	\N	\N	\N	2
c970f1f1-95f6-436e-8d07-863d6aafa045	1	34	2009-09-19 11:07:46.197+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2a90661c-7924-4fb5-904a-9f66232cf837	\N	\N	\N	\N	\N	\N	\N	\N	3
b0147a3e-d3b9-4172-a0f5-32014af2de7e	1	34	2009-09-19 11:07:46.207+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2a90661c-7924-4fb5-904a-9f66232cf837	\N	\N	\N	\N	\N	\N	\N	\N	4
aed31c9a-7cdd-428a-ac00-3cae43fcc324	1	34	2009-09-19 11:07:46.218+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2a90661c-7924-4fb5-904a-9f66232cf837	\N	\N	\N	\N	\N	\N	\N	\N	5
91b356dc-1916-42a2-902a-e30eb4924514	1	34	2009-09-19 11:07:46.229+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2a90661c-7924-4fb5-904a-9f66232cf837	\N	\N	\N	\N	\N	\N	\N	\N	6
8c3e2621-b421-40a4-a93f-02a6a766a8c2	1	34	2009-09-19 11:07:46.239+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2a90661c-7924-4fb5-904a-9f66232cf837	\N	\N	\N	\N	\N	\N	\N	\N	7
33486541-4bc5-41ed-b09e-fc6d8f961437	1	34	2009-09-19 11:07:46.249+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2a90661c-7924-4fb5-904a-9f66232cf837	\N	\N	\N	\N	\N	\N	\N	\N	8
72c42b6d-87c5-48ae-8f26-7faeddb7ea3d	1	34	2009-09-19 11:07:46.261+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2a90661c-7924-4fb5-904a-9f66232cf837	\N	\N	\N	\N	\N	\N	\N	\N	9
1459ca24-fc04-4b85-8b20-cf540a928be5	1	33	2009-09-19 11:07:46.274+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a521d276-8827-482e-b703-e81d9f62cb26	\N	\N	\N	\N	\N	\N	\N	\N	4
a839ffb4-f66d-44a5-9989-3be72ef575a0	1	34	2009-09-19 11:07:46.289+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	1459ca24-fc04-4b85-8b20-cf540a928be5	\N	\N	\N	\N	\N	\N	\N	\N	1
9059feb3-700a-4134-bc3f-db214eaaf820	1	34	2009-09-19 11:07:46.30+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	1459ca24-fc04-4b85-8b20-cf540a928be5	\N	\N	\N	\N	\N	\N	\N	\N	2
f2493f74-6ebe-4918-bb2a-356ba242c38c	1	34	2009-09-19 11:07:46.311+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	1459ca24-fc04-4b85-8b20-cf540a928be5	\N	\N	\N	\N	\N	\N	\N	\N	3
821c8d77-2067-400c-aec7-6e0cedaed7b0	1	34	2009-09-19 11:07:46.323+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	1459ca24-fc04-4b85-8b20-cf540a928be5	\N	\N	\N	\N	\N	\N	\N	\N	4
829d26cf-492b-44fe-97c6-1e75359bd2ca	1	34	2009-09-19 11:07:46.334+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	1459ca24-fc04-4b85-8b20-cf540a928be5	\N	\N	\N	\N	\N	\N	\N	\N	5
4f550ded-3bc9-4bef-87de-a677585599aa	1	34	2009-09-19 11:07:46.346+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	1459ca24-fc04-4b85-8b20-cf540a928be5	\N	\N	\N	\N	\N	\N	\N	\N	6
49cc63e0-e383-4ad7-aa7b-76339e7b87cc	1	34	2009-09-19 11:07:46.357+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	1459ca24-fc04-4b85-8b20-cf540a928be5	\N	\N	\N	\N	\N	\N	\N	\N	7
ea566790-5ce5-41ef-b372-82568003ed21	1	34	2009-09-19 11:07:46.367+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	1459ca24-fc04-4b85-8b20-cf540a928be5	\N	\N	\N	\N	\N	\N	\N	\N	8
b142b6c6-1df5-4ede-884e-fb7520f16d29	1	34	2009-09-19 11:07:46.378+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	1459ca24-fc04-4b85-8b20-cf540a928be5	\N	\N	\N	\N	\N	\N	\N	\N	9
f34abd62-91ba-43d4-b112-40e29b29f863	1	33	2009-09-19 11:07:46.389+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a521d276-8827-482e-b703-e81d9f62cb26	\N	\N	\N	\N	\N	\N	\N	\N	5
6abfbe09-c557-4313-8709-4c7dd9cb997c	1	34	2009-09-19 11:07:46.402+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	f34abd62-91ba-43d4-b112-40e29b29f863	\N	\N	\N	\N	\N	\N	\N	\N	1
e96e5fcd-0d5a-4009-b4fa-975f4f0adbcd	1	34	2009-09-19 11:07:46.414+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	f34abd62-91ba-43d4-b112-40e29b29f863	\N	\N	\N	\N	\N	\N	\N	\N	2
49a15d3f-2037-4c4e-8a51-cbd85fc8d940	1	34	2009-09-19 11:07:46.424+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	f34abd62-91ba-43d4-b112-40e29b29f863	\N	\N	\N	\N	\N	\N	\N	\N	3
f59eda87-fd36-4c05-9bcd-8f0cbff0d04c	1	34	2009-09-19 11:07:46.435+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	f34abd62-91ba-43d4-b112-40e29b29f863	\N	\N	\N	\N	\N	\N	\N	\N	4
84df8b7b-ae6e-40b0-884f-4efbbd116eec	1	34	2009-09-19 11:07:46.447+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	f34abd62-91ba-43d4-b112-40e29b29f863	\N	\N	\N	\N	\N	\N	\N	\N	5
633eb16b-e9f4-47a7-8eb6-d9bf4110bc01	1	34	2009-09-19 11:07:46.458+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	f34abd62-91ba-43d4-b112-40e29b29f863	\N	\N	\N	\N	\N	\N	\N	\N	6
450e49da-d3dc-4823-bfd9-6c015d204301	1	34	2009-09-19 11:07:46.468+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	f34abd62-91ba-43d4-b112-40e29b29f863	\N	\N	\N	\N	\N	\N	\N	\N	7
3dc52192-50d6-4663-a883-c9eafd8833e8	1	34	2009-09-19 11:07:46.479+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	f34abd62-91ba-43d4-b112-40e29b29f863	\N	\N	\N	\N	\N	\N	\N	\N	8
1e835dc3-f89e-4c38-96cb-2502e7f46c3f	1	34	2009-09-19 11:07:46.49+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	f34abd62-91ba-43d4-b112-40e29b29f863	\N	\N	\N	\N	\N	\N	\N	\N	9
aa673fd9-c925-4567-bef3-4f3e5c56a569	1	33	2009-09-19 11:07:46.502+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a521d276-8827-482e-b703-e81d9f62cb26	\N	\N	\N	\N	\N	\N	\N	\N	6
e17cfade-6f03-4b62-b1a3-665b3b051e1f	1	34	2009-09-19 11:07:46.517+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	aa673fd9-c925-4567-bef3-4f3e5c56a569	\N	\N	\N	\N	\N	\N	\N	\N	1
b7ed085e-3b01-4f29-8511-bf5713469cd9	1	34	2009-09-19 11:07:46.528+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	aa673fd9-c925-4567-bef3-4f3e5c56a569	\N	\N	\N	\N	\N	\N	\N	\N	2
8c80f5ed-d7ee-4509-a55a-a7c7085d660b	1	34	2009-09-19 11:07:46.541+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	aa673fd9-c925-4567-bef3-4f3e5c56a569	\N	\N	\N	\N	\N	\N	\N	\N	3
b2ea322a-1c83-42bf-a300-47e103e649d2	1	34	2009-09-19 11:07:46.553+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	aa673fd9-c925-4567-bef3-4f3e5c56a569	\N	\N	\N	\N	\N	\N	\N	\N	4
cffc0d42-9b25-4e8e-b091-34e54e1a0151	1	34	2009-09-19 11:07:46.565+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	aa673fd9-c925-4567-bef3-4f3e5c56a569	\N	\N	\N	\N	\N	\N	\N	\N	5
e43beb11-f09d-4cf8-b8ba-70b2f8cd88fd	1	34	2009-09-19 11:07:46.576+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	aa673fd9-c925-4567-bef3-4f3e5c56a569	\N	\N	\N	\N	\N	\N	\N	\N	6
76956e49-27f3-4479-b9bb-3f749c6b9a8c	1	34	2009-09-19 11:07:46.588+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	aa673fd9-c925-4567-bef3-4f3e5c56a569	\N	\N	\N	\N	\N	\N	\N	\N	7
431ae759-ad22-4969-8206-993b521fe887	1	34	2009-09-19 11:07:46.60+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	aa673fd9-c925-4567-bef3-4f3e5c56a569	\N	\N	\N	\N	\N	\N	\N	\N	8
875d6454-7395-41b3-b408-c59f8f7c3ea9	1	34	2009-09-19 11:07:46.611+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	aa673fd9-c925-4567-bef3-4f3e5c56a569	\N	\N	\N	\N	\N	\N	\N	\N	9
9e2b2d17-8b79-4ada-b6a4-4539e66cf1b8	1	33	2009-09-19 11:07:46.623+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a521d276-8827-482e-b703-e81d9f62cb26	\N	\N	\N	\N	\N	\N	\N	\N	7
fc8c5ddd-b4d5-46e1-8666-023193b83b41	1	34	2009-09-19 11:07:46.638+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	9e2b2d17-8b79-4ada-b6a4-4539e66cf1b8	\N	\N	\N	\N	\N	\N	\N	\N	1
1b099b8a-ce25-42b8-b36c-4a002e2345dc	1	34	2009-09-19 11:07:46.649+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	9e2b2d17-8b79-4ada-b6a4-4539e66cf1b8	\N	\N	\N	\N	\N	\N	\N	\N	2
4b5fb74c-5890-4b5c-b88f-108404f4a603	1	34	2009-09-19 11:07:46.662+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	9e2b2d17-8b79-4ada-b6a4-4539e66cf1b8	\N	\N	\N	\N	\N	\N	\N	\N	3
dcbf4eef-f890-49f9-bb1c-55ea1c994a72	1	34	2009-09-19 11:07:46.674+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	9e2b2d17-8b79-4ada-b6a4-4539e66cf1b8	\N	\N	\N	\N	\N	\N	\N	\N	4
c0fbaf0f-6b43-48ec-bedb-0797d55ececa	1	34	2009-09-19 11:07:46.686+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	9e2b2d17-8b79-4ada-b6a4-4539e66cf1b8	\N	\N	\N	\N	\N	\N	\N	\N	5
d308445e-50e8-4887-92ba-42d2aa4d3cf0	1	34	2009-09-19 11:07:46.697+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	9e2b2d17-8b79-4ada-b6a4-4539e66cf1b8	\N	\N	\N	\N	\N	\N	\N	\N	6
fae38063-27eb-40d1-bd2d-539692d62e2f	1	34	2009-09-19 11:07:46.709+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	9e2b2d17-8b79-4ada-b6a4-4539e66cf1b8	\N	\N	\N	\N	\N	\N	\N	\N	7
4a1ba791-1f03-4385-8047-99fceda4dc5c	1	34	2009-09-19 11:07:46.72+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	9e2b2d17-8b79-4ada-b6a4-4539e66cf1b8	\N	\N	\N	\N	\N	\N	\N	\N	8
7854c3bf-d184-4d71-a4d4-066499199bc0	1	34	2009-09-19 11:07:46.731+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	9e2b2d17-8b79-4ada-b6a4-4539e66cf1b8	\N	\N	\N	\N	\N	\N	\N	\N	9
173c30bc-2df9-45b6-b634-ff42dbeeab29	1	33	2009-09-19 11:07:46.744+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a521d276-8827-482e-b703-e81d9f62cb26	\N	\N	\N	\N	\N	\N	\N	\N	8
3c41b9f9-1192-4e48-9092-cf4ff8988573	1	34	2009-09-19 11:07:46.76+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	173c30bc-2df9-45b6-b634-ff42dbeeab29	\N	\N	\N	\N	\N	\N	\N	\N	1
9ab4b45b-7737-4062-a504-e304974e676e	1	34	2009-09-19 11:07:46.772+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	173c30bc-2df9-45b6-b634-ff42dbeeab29	\N	\N	\N	\N	\N	\N	\N	\N	2
667beb4f-2186-453b-b611-a82ee432a4fc	1	34	2009-09-19 11:07:46.784+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	173c30bc-2df9-45b6-b634-ff42dbeeab29	\N	\N	\N	\N	\N	\N	\N	\N	3
e335ed17-a137-4d90-b80a-a22b1250356d	1	34	2009-09-19 11:07:46.795+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	173c30bc-2df9-45b6-b634-ff42dbeeab29	\N	\N	\N	\N	\N	\N	\N	\N	4
986c5e7c-3997-4e4c-a6ad-1d0cded25098	1	34	2009-09-19 11:07:46.807+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	173c30bc-2df9-45b6-b634-ff42dbeeab29	\N	\N	\N	\N	\N	\N	\N	\N	5
c57bb88f-239d-4743-a92e-29025d4d7620	1	34	2009-09-19 11:07:46.818+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	173c30bc-2df9-45b6-b634-ff42dbeeab29	\N	\N	\N	\N	\N	\N	\N	\N	6
b3a122ac-82dc-4342-8ab5-0793f616a383	1	34	2009-09-19 11:07:46.829+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	173c30bc-2df9-45b6-b634-ff42dbeeab29	\N	\N	\N	\N	\N	\N	\N	\N	7
ed405289-eb16-4e51-bbe3-3ffd1446cdff	1	34	2009-09-19 11:07:46.841+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	173c30bc-2df9-45b6-b634-ff42dbeeab29	\N	\N	\N	\N	\N	\N	\N	\N	8
3fa531fb-0413-4022-a9ca-cd1ee8343b3d	1	34	2009-09-19 11:07:46.853+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	173c30bc-2df9-45b6-b634-ff42dbeeab29	\N	\N	\N	\N	\N	\N	\N	\N	9
4c26c947-c9ee-4d9e-8950-62fcec3f9eaa	1	33	2009-09-19 11:07:46.866+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a521d276-8827-482e-b703-e81d9f62cb26	\N	\N	\N	\N	\N	\N	\N	\N	9
c5122bf6-03c1-4a89-9e3e-ec76a663341f	1	34	2009-09-19 11:07:46.882+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	4c26c947-c9ee-4d9e-8950-62fcec3f9eaa	\N	\N	\N	\N	\N	\N	\N	\N	1
d252dd58-160a-478e-9dd0-821601834e57	1	34	2009-09-19 11:07:46.894+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	4c26c947-c9ee-4d9e-8950-62fcec3f9eaa	\N	\N	\N	\N	\N	\N	\N	\N	2
8350e825-211e-4d9e-bd4c-c459c8e8292b	1	34	2009-09-19 11:07:46.906+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	4c26c947-c9ee-4d9e-8950-62fcec3f9eaa	\N	\N	\N	\N	\N	\N	\N	\N	3
bd4002ad-e77a-40e2-8a77-939173ed6084	1	34	2009-09-19 11:07:46.918+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	4c26c947-c9ee-4d9e-8950-62fcec3f9eaa	\N	\N	\N	\N	\N	\N	\N	\N	4
e96d2dca-58d5-4661-8a8e-6461e6b7300c	1	34	2009-09-19 11:07:46.93+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	4c26c947-c9ee-4d9e-8950-62fcec3f9eaa	\N	\N	\N	\N	\N	\N	\N	\N	5
f180278a-67d1-4e38-8d77-33a2e8f5e0d1	1	34	2009-09-19 11:07:46.942+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	4c26c947-c9ee-4d9e-8950-62fcec3f9eaa	\N	\N	\N	\N	\N	\N	\N	\N	6
6018827f-4c89-43bd-8208-b941c5ea2567	1	34	2009-09-19 11:07:46.961+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	4c26c947-c9ee-4d9e-8950-62fcec3f9eaa	\N	\N	\N	\N	\N	\N	\N	\N	7
7371e6c2-d8e6-4e87-b916-c118ae1f5c21	1	34	2009-09-19 11:07:46.975+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	4c26c947-c9ee-4d9e-8950-62fcec3f9eaa	\N	\N	\N	\N	\N	\N	\N	\N	8
9b7dfdaf-18ab-464a-be26-66d103bb1be7	1	34	2009-09-19 11:07:46.987+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	4c26c947-c9ee-4d9e-8950-62fcec3f9eaa	\N	\N	\N	\N	\N	\N	\N	\N	9
211316e8-bf70-40b2-b70e-ab28b1617a42	1	33	2009-09-19 11:07:47.001+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a521d276-8827-482e-b703-e81d9f62cb26	\N	\N	\N	\N	\N	\N	\N	\N	10
96e78ee9-9505-4254-934f-b201112ff42f	1	34	2009-09-19 11:07:47.018+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	211316e8-bf70-40b2-b70e-ab28b1617a42	\N	\N	\N	\N	\N	\N	\N	\N	1
05ebfeed-85b9-4c8a-bb4e-670f5cfde967	1	34	2009-09-19 11:07:47.031+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	211316e8-bf70-40b2-b70e-ab28b1617a42	\N	\N	\N	\N	\N	\N	\N	\N	2
52c226a5-31f4-41bc-96b4-a015ceb6dbdf	1	34	2009-09-19 11:07:47.054+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	211316e8-bf70-40b2-b70e-ab28b1617a42	\N	\N	\N	\N	\N	\N	\N	\N	3
0e592b5d-90f3-4a7f-9f7c-e49a2a6da57a	1	34	2009-09-19 11:07:47.067+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	211316e8-bf70-40b2-b70e-ab28b1617a42	\N	\N	\N	\N	\N	\N	\N	\N	4
ade9f39c-5e5f-4abd-bda5-52963dbdee08	1	34	2009-09-19 11:07:47.08+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	211316e8-bf70-40b2-b70e-ab28b1617a42	\N	\N	\N	\N	\N	\N	\N	\N	5
c8b6d71d-4eb3-40c2-9b94-26568e269a68	1	34	2009-09-19 11:07:47.093+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	211316e8-bf70-40b2-b70e-ab28b1617a42	\N	\N	\N	\N	\N	\N	\N	\N	6
71275d25-1379-46ae-806b-6a5fd7f798bb	1	34	2009-09-19 11:07:47.106+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	211316e8-bf70-40b2-b70e-ab28b1617a42	\N	\N	\N	\N	\N	\N	\N	\N	7
cf164bf3-6796-4863-908f-5049484c8927	1	34	2009-09-19 11:07:47.119+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	211316e8-bf70-40b2-b70e-ab28b1617a42	\N	\N	\N	\N	\N	\N	\N	\N	8
17a00d51-d05b-448a-a411-cc2c2a296abe	1	34	2009-09-19 11:07:47.131+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	211316e8-bf70-40b2-b70e-ab28b1617a42	\N	\N	\N	\N	\N	\N	\N	\N	9
a7a3eac7-b5f6-4764-bbce-a5ad9d3b889a	1	33	2009-09-19 11:07:47.144+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a521d276-8827-482e-b703-e81d9f62cb26	\N	\N	\N	\N	\N	\N	\N	\N	11
1c037996-3714-4a01-953f-00c20eee55a0	1	34	2009-09-19 11:07:47.161+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a7a3eac7-b5f6-4764-bbce-a5ad9d3b889a	\N	\N	\N	\N	\N	\N	\N	\N	1
10615359-1fe3-42de-a370-bc2d0fb68124	1	34	2009-09-19 11:07:47.175+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a7a3eac7-b5f6-4764-bbce-a5ad9d3b889a	\N	\N	\N	\N	\N	\N	\N	\N	2
93cd3ab8-3ca3-48ad-8023-6e474decd5f3	1	34	2009-09-19 11:07:47.189+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a7a3eac7-b5f6-4764-bbce-a5ad9d3b889a	\N	\N	\N	\N	\N	\N	\N	\N	3
57fa39f0-e51e-4074-b72b-68893a914d5d	1	34	2009-09-19 11:07:47.202+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a7a3eac7-b5f6-4764-bbce-a5ad9d3b889a	\N	\N	\N	\N	\N	\N	\N	\N	4
322068a7-b703-4b94-bd21-d3c0a09f69e6	1	34	2009-09-19 11:07:47.215+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a7a3eac7-b5f6-4764-bbce-a5ad9d3b889a	\N	\N	\N	\N	\N	\N	\N	\N	5
712b1286-7a87-4bac-b82c-617334f6400d	1	34	2009-09-19 11:07:47.228+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a7a3eac7-b5f6-4764-bbce-a5ad9d3b889a	\N	\N	\N	\N	\N	\N	\N	\N	6
17c5452f-b91c-4eda-aa64-cf619feb63a4	1	34	2009-09-19 11:07:47.241+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a7a3eac7-b5f6-4764-bbce-a5ad9d3b889a	\N	\N	\N	\N	\N	\N	\N	\N	7
aadcf665-d3b9-41e4-bf45-8f49d378efa2	1	34	2009-09-19 11:07:47.255+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a7a3eac7-b5f6-4764-bbce-a5ad9d3b889a	\N	\N	\N	\N	\N	\N	\N	\N	8
cc3ce44d-db2b-4647-83c4-3b85d06c126b	1	34	2009-09-19 11:07:47.269+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a7a3eac7-b5f6-4764-bbce-a5ad9d3b889a	\N	\N	\N	\N	\N	\N	\N	\N	9
40fa7e11-6ec0-40fc-a3d3-31c53aef0d2b	1	18	2009-09-19 11:10:47.517+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	2
510b684e-3a91-4918-94c3-dbe7bed62386	1	23	2009-09-19 11:10:47.524+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	2
5ae35b9d-e08a-403d-bbf8-e9ccc35defbf	1	24	2009-09-19 11:10:47.626+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	9
573b0ea4-280b-49c8-8470-7404e37afa33	1	25	2009-09-19 11:34:40.576+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	1
06727013-1eed-425e-a3f6-1afa85b0b56d	1	25	2009-09-19 11:34:40.602+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	2
3f0dd565-947e-42ac-bd5b-0b40470fbb7e	1	25	2009-09-19 11:34:40.611+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	3
c1d4062c-e6c9-45a5-ad8b-6facc245ec0d	1	25	2009-09-19 11:34:40.615+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	4
37b7b3b6-8ace-4ea2-93eb-417c3fbabd69	1	25	2009-09-19 11:34:40.62+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	5
b3aedcbb-604c-491f-8af8-403fba1a21bc	1	25	2009-09-19 11:34:40.624+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	6
d26c1d67-5df6-4239-a962-9e7b6c7b6b95	1	25	2009-09-19 11:34:40.629+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	7
61390da5-7f67-438e-bdbe-4eafded7bffe	1	25	2009-09-19 11:34:40.634+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	8
f4abbb6e-c7f7-4a0f-8fb6-c7d7d64ccfb1	1	25	2009-09-19 11:34:40.639+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	9
b1cea64d-7f17-41f0-8f59-9a27a9b4631b	1	25	2009-09-19 11:34:40.645+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	10
0f060247-afef-40e1-a67d-91df886859b0	1	25	2009-09-19 11:34:40.65+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	11
36ef5273-dce3-48a5-956b-33d0e281f8b7	1	25	2009-09-19 11:34:40.655+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	12
ba3bd467-3add-47fb-b425-f6f78734f0e1	1	26	2009-09-19 11:34:40.672+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	1
478e64cb-d3c1-4215-9fa4-7df08583e2f1	1	27	2009-09-19 11:34:40.684+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	1
e5571d71-a28f-412f-9600-16649fd362fb	1	27	2009-09-19 11:34:40.73+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	2
7a02381e-86e2-4d7f-be47-9117ec3d4057	1	27	2009-09-19 11:34:40.761+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	3
4e6a4974-5db1-4d14-935e-1e2945064a03	1	27	2009-09-19 11:34:40.77+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	4
dc5d7727-6cdb-4668-8ad7-a286d916dac9	1	27	2009-09-19 11:34:40.779+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	5
6a2b6b6c-d033-4e6b-bb5f-a732299c315e	1	27	2009-09-19 11:34:40.787+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	6
2997673e-8ef1-4a5b-87ff-70827afc4462	1	27	2009-09-19 11:34:40.798+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	7
20205a62-55a5-41a8-83c9-b8e8fe431553	1	27	2009-09-19 11:34:40.806+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	8
8b2acd93-58d7-49e3-a744-345341241db0	1	27	2009-09-19 11:34:40.816+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	9
c63edb6c-9264-4731-8dfa-5e79b1a28e0e	1	27	2009-09-19 11:34:40.825+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	10
c3753a3c-dd16-4b01-9580-1fa53bff6408	1	27	2009-09-19 11:34:40.834+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	11
c91f0649-c095-4cba-b2bb-ed957df27139	1	27	2009-09-19 11:34:40.843+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	12
3ffc89b6-b836-4bd2-8625-2d694b767326	1	27	2009-09-19 11:34:40.852+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	13
8f364d33-7d9e-407d-8cb6-63fa159879f1	1	27	2009-09-19 11:34:40.861+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	14
17e0f059-5a02-4d2e-933a-eea29552dee1	1	27	2009-09-19 11:34:40.87+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	15
e3c32de7-ac02-4365-a790-1b4d231eea7e	1	27	2009-09-19 11:34:40.879+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	16
f0654d64-fbaa-489c-be38-4afedcc7f01e	1	27	2009-09-19 11:34:40.888+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	17
f05eaa06-1f8a-4190-af9a-5341950b744a	1	27	2009-09-19 11:34:40.898+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	18
88d24d5a-b737-41d7-834d-a61bad9eeb4b	1	27	2009-09-19 11:34:40.908+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	19
abcafe6d-1c11-4823-a022-57e420b481eb	1	27	2009-09-19 11:34:40.917+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	20
df245b92-81c6-4c1d-8d11-6056eeaa63c7	1	27	2009-09-19 11:34:40.927+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	21
ed5202fa-ca2a-4638-a9e9-00bad0d6d54c	1	27	2009-09-19 11:34:40.937+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	22
3dd3f5dc-25bb-4591-85fc-058580a5d379	1	27	2009-09-19 11:34:40.947+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	23
95ca0ebc-9ba5-47de-b0c6-d320cf25f700	1	27	2009-09-19 11:34:40.957+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	24
9bf7459e-406b-41a7-a6e1-30707a374331	1	27	2009-09-19 11:34:40.967+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	25
1404bf91-96a4-4f0a-8bd4-7d7501d1896b	1	27	2009-09-19 11:34:40.977+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	26
5b3ce239-9b4b-4329-859b-7b59aeada0e7	1	27	2009-09-19 11:34:40.988+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	27
0e8fc0f2-4738-4487-b373-8d593e0af13d	1	27	2009-09-19 11:34:40.999+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	28
819df821-8513-41ca-adad-ffe233c96fc6	1	27	2009-09-19 11:34:41.01+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	29
e0edcc89-cd3f-4269-bbc5-ca2049bf7b79	1	27	2009-09-19 11:34:41.021+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	30
9ab3fa11-79d0-4d7a-a822-495d20117dab	1	27	2009-09-19 11:34:41.032+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	31
33b30199-e11c-4064-bdb4-7e1dff7d069f	1	27	2009-09-19 11:34:41.043+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	32
43bbbce5-feca-4d79-bc97-52cb3731fc7f	1	27	2009-09-19 11:34:41.054+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	33
960decb4-e275-4f10-ad4b-153259345acf	1	27	2009-09-19 11:34:41.064+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	34
b9ac4e28-af58-4e71-8fb0-48ff6b832035	1	27	2009-09-19 11:34:41.076+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	35
61ce5549-f274-4c2f-bc58-0ee1a6b3231a	1	27	2009-09-19 11:34:41.086+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	36
17fa0e27-5196-42b6-89ff-2c11b81cfdc4	1	27	2009-09-19 11:34:41.098+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	37
0a1d7e05-7862-45fc-9591-fd5a601258a7	1	27	2009-09-19 11:34:41.109+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	38
c1218b7a-bfb1-4ac3-9014-3b757e78be4d	1	27	2009-09-19 11:34:41.12+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	39
5b08769a-c86a-493e-968c-16d08228b0dd	1	27	2009-09-19 11:34:41.132+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	40
40c70948-ab67-4c97-9c02-ff809495c43c	1	27	2009-09-19 11:34:41.144+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	41
f22492e5-e7d5-4916-bb3e-848978d1b998	1	27	2009-09-19 11:34:41.155+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	42
dff50de9-311b-4dd1-9f06-143892f2787e	1	27	2009-09-19 11:34:41.166+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	43
6e8eb498-60f7-4346-869c-ce9ef1e1f57a	1	27	2009-09-19 11:34:41.177+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	44
65c0cddf-b417-4a5c-9bca-e8fd4f7835f6	1	27	2009-09-19 11:34:41.188+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	45
7e9c28ee-15ee-4d4b-a74d-24cb54dca086	1	27	2009-09-19 11:34:41.199+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	46
c1fa5a15-70b2-4078-9c48-c3013bd818d3	1	20	2009-09-19 14:47:46.026+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	b74ccda7-9ada-42a3-8e01-e04434d46288	\N	\N	\N	\N	\N	\N	\N	\N	1
c29f08a5-e930-4d9f-91da-9fffeb0f1916	1	21	2009-09-19 14:47:46.042+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	0fefe9d8-4a07-4149-a290-a54b0fb862e1	\N	\N	\N	\N	\N	\N	\N	\N	1
1af25ddb-dc94-4e91-b5b9-8c6bf5e49ce2	1	20	2009-09-19 14:47:46.053+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	b74ccda7-9ada-42a3-8e01-e04434d46288	\N	\N	\N	\N	\N	\N	\N	\N	2
4564ff12-5f02-4562-9678-ff4f2599891e	1	21	2009-09-19 14:47:46.068+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	0fefe9d8-4a07-4149-a290-a54b0fb862e1	\N	\N	\N	\N	\N	\N	\N	\N	2
b861637a-7d71-4248-8ce1-543f479b1589	1	18	2009-09-19 13:25:46.536+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	3
61bf705e-de8f-4e93-81f3-c9d88b0da0c6	1	17	2009-09-19 13:25:46.546+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	3cc75b52-13a0-4695-be04-857014c9498d	\N	\N	\N	\N	\N	\N	\N	\N	1
3cc75b52-13a0-4695-be04-857014c9498d	2	15	2009-09-19 13:25:46.496+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	2
a5ce0828-3d08-419b-8b44-e2dea834a6a2	1	19	2009-09-19 13:25:46.553+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	61bf705e-de8f-4e93-81f3-c9d88b0da0c6	\N	\N	\N	\N	\N	\N	\N	\N	1
b7ced214-7c98-48f9-a445-b80d907ae177	1	18	2009-09-19 13:45:51.496+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	4
81ed587e-279a-474d-9290-adbfd2a4cea1	1	17	2009-09-19 13:45:51.506+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	153502dc-8b72-444a-abcd-1bfd8ee794ae	\N	\N	\N	\N	\N	\N	\N	\N	1
153502dc-8b72-444a-abcd-1bfd8ee794ae	2	15	2009-09-19 13:45:51.456+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	be102e1f-00f5-48b5-9cbc-3d2583527629	\N	\N	\N	\N	\N	\N	\N	\N	3
a1240905-4c68-419b-a4da-154480c7a70a	1	19	2009-09-19 13:45:51.514+03	510b684e-3a91-4918-94c3-dbe7bed62386	3bbdb5a7-9a65-4e61-8c9a-a7bceaedc7eb	f	f	f	f	f	81ed587e-279a-474d-9290-adbfd2a4cea1	\N	\N	\N	\N	\N	\N	\N	\N	1
81e50b6b-68a6-4e50-9103-95fe8eba86f3	1	15	2009-09-19 14:47:45.88+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	1
b74ccda7-9ada-42a3-8e01-e04434d46288	1	17	2009-09-19 14:47:45.925+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	1
068397ba-eded-4a10-a976-bd5ce2c113a4	1	18	2009-09-19 14:47:45.959+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	1
0fefe9d8-4a07-4149-a290-a54b0fb862e1	1	19	2009-09-19 14:47:45.971+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	b74ccda7-9ada-42a3-8e01-e04434d46288	\N	\N	\N	\N	\N	\N	\N	\N	1
cad3bc60-e140-4795-8210-ba347c8a7da8	1	20	2009-09-19 14:47:46.078+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	b74ccda7-9ada-42a3-8e01-e04434d46288	\N	\N	\N	\N	\N	\N	\N	\N	3
a3e11d78-7e84-4877-a2a7-dd735ee685c6	1	21	2009-09-19 14:47:46.089+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	0fefe9d8-4a07-4149-a290-a54b0fb862e1	\N	\N	\N	\N	\N	\N	\N	\N	3
cc217142-0ac4-40e6-82d0-f60037af6529	1	23	2009-09-19 14:47:46.112+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	1
94ca4a9a-4232-4fed-8fdd-2bf91c59b872	1	28	2009-09-19 14:47:46.122+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	1
63516496-e5ef-488a-9f54-1c234fbf9f16	1	28	2009-09-19 14:47:46.133+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	2
08cf881e-4ee4-4854-8d41-19378fb78856	1	24	2009-09-19 14:47:46.146+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	10
8c2c0ee6-1273-4d9b-83a1-0165268012f1	1	30	2009-09-19 14:47:46.16+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	1
82d81a67-8ebe-47f8-86c0-aa4af5147217	1	31	2009-09-19 14:47:46.174+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	08cf881e-4ee4-4854-8d41-19378fb78856	\N	\N	\N	\N	\N	\N	\N	\N	1
4ab0c310-7b87-4edd-b89d-09ba24a95fb3	1	32	2009-09-19 14:47:49.552+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	1
f3cd13de-bc71-491c-8efd-b38fd678c5ae	1	32	2009-09-19 14:47:49.564+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	2
4394e5ba-1dcb-44d9-ad2e-ece00b005215	1	32	2009-09-19 14:47:49.575+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	3
c0878a39-dbf0-482d-b533-f8afb1aa0d6d	1	32	2009-09-19 14:47:49.584+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	4
0eff433f-be85-450b-9b39-c27e834926ab	1	32	2009-09-19 14:47:49.594+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	5
caeed37d-7788-4601-9e3e-f0fa35e9b647	1	32	2009-09-19 14:47:49.605+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	6
a9bdb311-4096-4459-8c30-c5e277f55221	1	32	2009-09-19 14:47:49.615+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	7
e18e3475-dc1e-4584-a805-69664fa96066	1	32	2009-09-19 14:47:49.625+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	8
325b7951-1447-42b4-abc8-47b5b96a6a79	1	32	2009-09-19 14:47:49.637+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	9
c0f62d30-3600-46bd-8dba-e3bb84a94164	1	32	2009-09-19 14:47:49.648+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	10
320b0280-7ff2-4146-9ebf-95a9a902f4b6	1	32	2009-09-19 14:47:49.656+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	11
46ee07e8-eb2a-479c-ab5b-87f4f253f9b7	1	32	2009-09-19 14:47:49.667+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	12
584fee56-9341-4228-9d97-ca83c2bb1236	1	33	2009-09-19 14:47:49.686+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8c2c0ee6-1273-4d9b-83a1-0165268012f1	\N	\N	\N	\N	\N	\N	\N	\N	1
9862b5ec-b448-42fb-8f0d-bfa36e9e7d43	1	34	2009-09-19 14:47:49.701+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	584fee56-9341-4228-9d97-ca83c2bb1236	\N	\N	\N	\N	\N	\N	\N	\N	1
4392caec-4b9b-4678-b295-402872a075b3	1	34	2009-09-19 14:47:49.715+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	584fee56-9341-4228-9d97-ca83c2bb1236	\N	\N	\N	\N	\N	\N	\N	\N	2
2c0c82f0-831e-438a-acec-f9652a873255	1	34	2009-09-19 14:47:49.729+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	584fee56-9341-4228-9d97-ca83c2bb1236	\N	\N	\N	\N	\N	\N	\N	\N	3
bb292f43-8b2f-4679-accf-85bc6cc0e56f	1	34	2009-09-19 14:47:49.74+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	584fee56-9341-4228-9d97-ca83c2bb1236	\N	\N	\N	\N	\N	\N	\N	\N	4
bcb6c2da-07a1-41cc-a4e6-9faa7d37815b	1	34	2009-09-19 14:47:49.75+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	584fee56-9341-4228-9d97-ca83c2bb1236	\N	\N	\N	\N	\N	\N	\N	\N	5
75c1f812-cb4c-4cd9-8fa2-64dddf33fc25	1	34	2009-09-19 14:47:49.761+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	584fee56-9341-4228-9d97-ca83c2bb1236	\N	\N	\N	\N	\N	\N	\N	\N	6
bf7c4bd9-e894-4938-947e-0b62ec06667f	1	34	2009-09-19 14:47:49.774+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	584fee56-9341-4228-9d97-ca83c2bb1236	\N	\N	\N	\N	\N	\N	\N	\N	7
53fda4e6-4281-463f-b844-770fc9e93b3b	1	34	2009-09-19 14:47:49.785+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	584fee56-9341-4228-9d97-ca83c2bb1236	\N	\N	\N	\N	\N	\N	\N	\N	8
f0275a5d-4a4a-43fb-b34d-22f6c37699b3	1	34	2009-09-19 14:47:49.797+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	584fee56-9341-4228-9d97-ca83c2bb1236	\N	\N	\N	\N	\N	\N	\N	\N	9
053d4077-46c6-4b4b-9c31-f98856865247	1	33	2009-09-19 14:47:49.81+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8c2c0ee6-1273-4d9b-83a1-0165268012f1	\N	\N	\N	\N	\N	\N	\N	\N	2
7f3c3767-83a5-4551-827f-227baffeb00f	1	34	2009-09-19 14:47:49.827+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	053d4077-46c6-4b4b-9c31-f98856865247	\N	\N	\N	\N	\N	\N	\N	\N	1
d2e2d847-b390-42ab-b39c-9482bdf31fff	1	34	2009-09-19 14:47:49.838+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	053d4077-46c6-4b4b-9c31-f98856865247	\N	\N	\N	\N	\N	\N	\N	\N	2
68c277c6-3887-44f6-8c6c-d61119d83c65	1	34	2009-09-19 14:47:49.849+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	053d4077-46c6-4b4b-9c31-f98856865247	\N	\N	\N	\N	\N	\N	\N	\N	3
94b8024c-9ba9-4296-b835-65e181c42fec	1	34	2009-09-19 14:47:49.859+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	053d4077-46c6-4b4b-9c31-f98856865247	\N	\N	\N	\N	\N	\N	\N	\N	4
f9417126-d9d6-407b-9644-2398927f7e10	1	34	2009-09-19 14:47:49.871+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	053d4077-46c6-4b4b-9c31-f98856865247	\N	\N	\N	\N	\N	\N	\N	\N	5
733fef38-b1e3-4e45-84db-5c2ebbe05357	1	34	2009-09-19 14:47:49.882+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	053d4077-46c6-4b4b-9c31-f98856865247	\N	\N	\N	\N	\N	\N	\N	\N	6
af041f96-e6ff-4c05-98d7-b0b9d892ee8f	1	34	2009-09-19 14:47:49.893+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	053d4077-46c6-4b4b-9c31-f98856865247	\N	\N	\N	\N	\N	\N	\N	\N	7
dee4fcc7-4b85-4899-b189-ba5576530774	1	34	2009-09-19 14:47:49.906+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	053d4077-46c6-4b4b-9c31-f98856865247	\N	\N	\N	\N	\N	\N	\N	\N	8
b155d1d6-c7e4-4004-b990-3149c873868c	1	34	2009-09-19 14:47:49.918+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	053d4077-46c6-4b4b-9c31-f98856865247	\N	\N	\N	\N	\N	\N	\N	\N	9
29752fd8-c652-4590-adf6-3167bf305818	1	33	2009-09-19 14:47:49.931+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8c2c0ee6-1273-4d9b-83a1-0165268012f1	\N	\N	\N	\N	\N	\N	\N	\N	3
5bd0207f-0d7d-42d3-a430-42bdb79a99c4	1	34	2009-09-19 14:47:49.947+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	29752fd8-c652-4590-adf6-3167bf305818	\N	\N	\N	\N	\N	\N	\N	\N	1
54a503a9-244d-4fb4-bbac-d60c85817864	1	34	2009-09-19 14:47:49.958+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	29752fd8-c652-4590-adf6-3167bf305818	\N	\N	\N	\N	\N	\N	\N	\N	2
68d2fec8-50ed-43e7-864f-5fcc078f9e13	1	34	2009-09-19 14:47:49.97+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	29752fd8-c652-4590-adf6-3167bf305818	\N	\N	\N	\N	\N	\N	\N	\N	3
44cc8e95-acfe-4d92-a9b8-75f63714fce7	1	34	2009-09-19 14:47:49.982+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	29752fd8-c652-4590-adf6-3167bf305818	\N	\N	\N	\N	\N	\N	\N	\N	4
1a3cf64e-5bb6-4316-9f49-c0cd96a02ae7	1	34	2009-09-19 14:47:49.994+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	29752fd8-c652-4590-adf6-3167bf305818	\N	\N	\N	\N	\N	\N	\N	\N	5
aa3e4a88-8c8d-4407-ac15-eec27c81da78	1	34	2009-09-19 14:47:50.006+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	29752fd8-c652-4590-adf6-3167bf305818	\N	\N	\N	\N	\N	\N	\N	\N	6
6ccacbeb-5565-4ecb-aed1-c29b8fa25e66	1	34	2009-09-19 14:47:50.017+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	29752fd8-c652-4590-adf6-3167bf305818	\N	\N	\N	\N	\N	\N	\N	\N	7
2dc9d8ce-489d-4740-bfdb-eea4c56ab3cd	1	34	2009-09-19 14:47:50.029+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	29752fd8-c652-4590-adf6-3167bf305818	\N	\N	\N	\N	\N	\N	\N	\N	8
74f19d29-e12a-4893-8753-4c421e884716	1	34	2009-09-19 14:47:50.041+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	29752fd8-c652-4590-adf6-3167bf305818	\N	\N	\N	\N	\N	\N	\N	\N	9
2ebf4ab1-4d0c-43df-82f2-464b8645b093	1	33	2009-09-19 14:47:50.054+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8c2c0ee6-1273-4d9b-83a1-0165268012f1	\N	\N	\N	\N	\N	\N	\N	\N	4
1ded2060-717c-48ad-8159-e38f93a1b7c2	1	34	2009-09-19 14:47:50.069+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2ebf4ab1-4d0c-43df-82f2-464b8645b093	\N	\N	\N	\N	\N	\N	\N	\N	1
b1258faf-cde7-4626-a2a9-bf527c6c471b	1	34	2009-09-19 14:47:50.081+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2ebf4ab1-4d0c-43df-82f2-464b8645b093	\N	\N	\N	\N	\N	\N	\N	\N	2
bb5ddd67-7777-42ce-b3f1-829418e377e4	1	34	2009-09-19 14:47:50.094+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2ebf4ab1-4d0c-43df-82f2-464b8645b093	\N	\N	\N	\N	\N	\N	\N	\N	3
92135579-2d7b-4e43-ba18-2cfa543473f4	1	34	2009-09-19 14:47:50.106+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2ebf4ab1-4d0c-43df-82f2-464b8645b093	\N	\N	\N	\N	\N	\N	\N	\N	4
265221f3-9b62-4d3c-a4be-f8b70927e552	1	34	2009-09-19 14:47:50.138+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2ebf4ab1-4d0c-43df-82f2-464b8645b093	\N	\N	\N	\N	\N	\N	\N	\N	5
82e4557b-9582-4d9f-af17-0932b01c5151	1	34	2009-09-19 14:47:50.151+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2ebf4ab1-4d0c-43df-82f2-464b8645b093	\N	\N	\N	\N	\N	\N	\N	\N	6
5f3c970f-df93-4791-8973-bbff8fbef97d	1	34	2009-09-19 14:47:50.163+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2ebf4ab1-4d0c-43df-82f2-464b8645b093	\N	\N	\N	\N	\N	\N	\N	\N	7
1e9e8133-f258-42b3-aff7-7cfe7a9109f0	1	34	2009-09-19 14:47:50.174+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2ebf4ab1-4d0c-43df-82f2-464b8645b093	\N	\N	\N	\N	\N	\N	\N	\N	8
6d9ac596-801b-48b8-b10a-0efc180f0dd6	1	34	2009-09-19 14:47:50.186+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2ebf4ab1-4d0c-43df-82f2-464b8645b093	\N	\N	\N	\N	\N	\N	\N	\N	9
aaa7d1db-cf15-45eb-a1c6-a90d20878968	1	33	2009-09-19 14:47:50.20+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8c2c0ee6-1273-4d9b-83a1-0165268012f1	\N	\N	\N	\N	\N	\N	\N	\N	5
f9991dc7-3119-4675-8f05-ffc8987219bb	1	34	2009-09-19 14:47:50.217+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	aaa7d1db-cf15-45eb-a1c6-a90d20878968	\N	\N	\N	\N	\N	\N	\N	\N	1
c23d156f-428d-46bd-90dd-9151ce91606f	1	34	2009-09-19 14:47:50.229+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	aaa7d1db-cf15-45eb-a1c6-a90d20878968	\N	\N	\N	\N	\N	\N	\N	\N	2
6d619673-0f04-4746-9b50-799e442207ff	1	34	2009-09-19 14:47:50.241+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	aaa7d1db-cf15-45eb-a1c6-a90d20878968	\N	\N	\N	\N	\N	\N	\N	\N	3
f46cf99e-ae2f-40b4-814e-f721967ac3ed	1	34	2009-09-19 14:47:50.253+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	aaa7d1db-cf15-45eb-a1c6-a90d20878968	\N	\N	\N	\N	\N	\N	\N	\N	4
ea000e6a-aa66-4b14-9a29-c02bff1a640a	1	34	2009-09-19 14:47:50.264+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	aaa7d1db-cf15-45eb-a1c6-a90d20878968	\N	\N	\N	\N	\N	\N	\N	\N	5
345054c7-b382-4937-a050-80581ea9d9f3	1	34	2009-09-19 14:47:50.276+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	aaa7d1db-cf15-45eb-a1c6-a90d20878968	\N	\N	\N	\N	\N	\N	\N	\N	6
406929c7-f79e-4928-b46a-05a6eedc2662	1	34	2009-09-19 14:47:50.288+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	aaa7d1db-cf15-45eb-a1c6-a90d20878968	\N	\N	\N	\N	\N	\N	\N	\N	7
6d7a299f-4467-4722-a221-8dee1c6b3d7d	1	34	2009-09-19 14:47:50.301+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	aaa7d1db-cf15-45eb-a1c6-a90d20878968	\N	\N	\N	\N	\N	\N	\N	\N	8
386ce0b4-3f1f-459b-a576-04c0f44c3e44	1	34	2009-09-19 14:47:50.313+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	aaa7d1db-cf15-45eb-a1c6-a90d20878968	\N	\N	\N	\N	\N	\N	\N	\N	9
82192679-ca5e-411f-be55-482649d4ee87	1	33	2009-09-19 14:47:50.327+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8c2c0ee6-1273-4d9b-83a1-0165268012f1	\N	\N	\N	\N	\N	\N	\N	\N	6
4aef1878-7524-4382-91e4-56aadf0e7da9	1	34	2009-09-19 14:47:50.344+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	82192679-ca5e-411f-be55-482649d4ee87	\N	\N	\N	\N	\N	\N	\N	\N	1
12b6faa8-3ccb-432f-a057-b5a2d5f59c4b	1	34	2009-09-19 14:47:50.356+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	82192679-ca5e-411f-be55-482649d4ee87	\N	\N	\N	\N	\N	\N	\N	\N	2
d8841782-1754-4125-a075-b737a6e64d4f	1	34	2009-09-19 14:47:50.368+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	82192679-ca5e-411f-be55-482649d4ee87	\N	\N	\N	\N	\N	\N	\N	\N	3
ee54b2d5-055e-4898-9ff3-76b4b7508539	1	34	2009-09-19 14:47:50.379+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	82192679-ca5e-411f-be55-482649d4ee87	\N	\N	\N	\N	\N	\N	\N	\N	4
69e5b569-baf7-4225-b38a-27773ec65fd9	1	34	2009-09-19 14:47:50.392+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	82192679-ca5e-411f-be55-482649d4ee87	\N	\N	\N	\N	\N	\N	\N	\N	5
5e826334-c52f-4449-ac5b-af0b354da633	1	34	2009-09-19 14:47:50.404+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	82192679-ca5e-411f-be55-482649d4ee87	\N	\N	\N	\N	\N	\N	\N	\N	6
4990ad51-cfdd-4f05-8c04-36f410fd6cda	1	34	2009-09-19 14:47:50.417+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	82192679-ca5e-411f-be55-482649d4ee87	\N	\N	\N	\N	\N	\N	\N	\N	7
dc1f6bbc-f614-48da-a5a9-cb4cc7a68784	1	34	2009-09-19 14:47:50.43+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	82192679-ca5e-411f-be55-482649d4ee87	\N	\N	\N	\N	\N	\N	\N	\N	8
ab1a2c82-9b6d-482f-97de-3d467f80df38	1	34	2009-09-19 14:47:50.443+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	82192679-ca5e-411f-be55-482649d4ee87	\N	\N	\N	\N	\N	\N	\N	\N	9
55ec5feb-dfc2-47d4-a7a8-d0998439fd75	1	33	2009-09-19 14:47:50.456+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8c2c0ee6-1273-4d9b-83a1-0165268012f1	\N	\N	\N	\N	\N	\N	\N	\N	7
b2b94350-6713-47d3-8362-fc8be2fd4b12	1	34	2009-09-19 14:47:50.473+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	55ec5feb-dfc2-47d4-a7a8-d0998439fd75	\N	\N	\N	\N	\N	\N	\N	\N	1
83b778ac-781b-41d2-9cc9-5bce94a828f2	1	34	2009-09-19 14:47:50.486+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	55ec5feb-dfc2-47d4-a7a8-d0998439fd75	\N	\N	\N	\N	\N	\N	\N	\N	2
388469d9-5294-44e6-b01f-84b1979d02b0	1	34	2009-09-19 14:47:50.499+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	55ec5feb-dfc2-47d4-a7a8-d0998439fd75	\N	\N	\N	\N	\N	\N	\N	\N	3
a5e5d84c-c018-4137-b3d7-0bef3cc701a0	1	34	2009-09-19 14:47:50.512+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	55ec5feb-dfc2-47d4-a7a8-d0998439fd75	\N	\N	\N	\N	\N	\N	\N	\N	4
0736fec8-4adb-4cf1-a78d-e562327e6d8e	1	34	2009-09-19 14:47:50.525+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	55ec5feb-dfc2-47d4-a7a8-d0998439fd75	\N	\N	\N	\N	\N	\N	\N	\N	5
bceeccd4-f133-464f-828e-99284de4fa96	1	34	2009-09-19 14:47:50.539+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	55ec5feb-dfc2-47d4-a7a8-d0998439fd75	\N	\N	\N	\N	\N	\N	\N	\N	6
279bfc22-31fa-4226-9556-a8e77a51c4bc	1	34	2009-09-19 14:47:50.553+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	55ec5feb-dfc2-47d4-a7a8-d0998439fd75	\N	\N	\N	\N	\N	\N	\N	\N	7
7c5c61c4-f01c-4428-9c02-8c8144e0d6e8	1	34	2009-09-19 14:47:50.566+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	55ec5feb-dfc2-47d4-a7a8-d0998439fd75	\N	\N	\N	\N	\N	\N	\N	\N	8
ac575174-1538-4793-8731-35aaae66f508	1	34	2009-09-19 14:47:50.578+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	55ec5feb-dfc2-47d4-a7a8-d0998439fd75	\N	\N	\N	\N	\N	\N	\N	\N	9
164a8b69-9034-476b-a162-820dcb0d67e6	1	33	2009-09-19 14:47:50.591+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8c2c0ee6-1273-4d9b-83a1-0165268012f1	\N	\N	\N	\N	\N	\N	\N	\N	8
e9ca7870-0689-4a87-998d-d10e8802a043	1	34	2009-09-19 14:47:50.608+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	164a8b69-9034-476b-a162-820dcb0d67e6	\N	\N	\N	\N	\N	\N	\N	\N	1
9cb50ccf-1e7d-42be-aebe-05256fdcf501	1	34	2009-09-19 14:47:50.622+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	164a8b69-9034-476b-a162-820dcb0d67e6	\N	\N	\N	\N	\N	\N	\N	\N	2
09e6a97c-ca1b-45a1-9f02-84d989cb796e	1	34	2009-09-19 14:47:50.636+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	164a8b69-9034-476b-a162-820dcb0d67e6	\N	\N	\N	\N	\N	\N	\N	\N	3
7cc80d24-542c-4e29-a3bf-c197657e5fd0	1	34	2009-09-19 14:47:50.651+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	164a8b69-9034-476b-a162-820dcb0d67e6	\N	\N	\N	\N	\N	\N	\N	\N	4
77bc2a0e-863a-4af9-a780-fdefa21d7ca7	1	34	2009-09-19 14:47:50.666+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	164a8b69-9034-476b-a162-820dcb0d67e6	\N	\N	\N	\N	\N	\N	\N	\N	5
7cba4c03-2cb7-4820-8b6a-c5a5e3ae2b85	1	34	2009-09-19 14:47:50.68+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	164a8b69-9034-476b-a162-820dcb0d67e6	\N	\N	\N	\N	\N	\N	\N	\N	6
6ae96ea5-59b7-4e8b-b734-e9773d227b7e	1	34	2009-09-19 14:47:50.694+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	164a8b69-9034-476b-a162-820dcb0d67e6	\N	\N	\N	\N	\N	\N	\N	\N	7
8f1d97ba-bcb4-42ee-b103-f507a248359b	1	34	2009-09-19 14:47:50.707+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	164a8b69-9034-476b-a162-820dcb0d67e6	\N	\N	\N	\N	\N	\N	\N	\N	8
b1cdb007-9bbf-4775-8b18-1d41c749d6c8	1	34	2009-09-19 14:47:50.722+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	164a8b69-9034-476b-a162-820dcb0d67e6	\N	\N	\N	\N	\N	\N	\N	\N	9
c5d03027-dde0-4a1d-b996-b3eb7f2fca5d	1	33	2009-09-19 14:47:50.737+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8c2c0ee6-1273-4d9b-83a1-0165268012f1	\N	\N	\N	\N	\N	\N	\N	\N	9
30ee4b72-4764-4b47-b29f-099c6d5ace51	1	34	2009-09-19 14:47:50.757+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c5d03027-dde0-4a1d-b996-b3eb7f2fca5d	\N	\N	\N	\N	\N	\N	\N	\N	1
5d1521eb-687d-4264-9a9f-2eb4eb467fd0	1	34	2009-09-19 14:47:50.771+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c5d03027-dde0-4a1d-b996-b3eb7f2fca5d	\N	\N	\N	\N	\N	\N	\N	\N	2
8e6b527c-fe28-400e-ac84-a241ace6f076	1	34	2009-09-19 14:47:50.785+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c5d03027-dde0-4a1d-b996-b3eb7f2fca5d	\N	\N	\N	\N	\N	\N	\N	\N	3
a8cfef0a-24de-45ec-b5e8-dba2090b2073	1	34	2009-09-19 14:47:50.799+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c5d03027-dde0-4a1d-b996-b3eb7f2fca5d	\N	\N	\N	\N	\N	\N	\N	\N	4
88af00c6-c334-480f-97a7-26504ed29028	1	34	2009-09-19 14:47:50.815+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c5d03027-dde0-4a1d-b996-b3eb7f2fca5d	\N	\N	\N	\N	\N	\N	\N	\N	5
faa66897-e78c-438d-a74c-e39a81d7f6c3	1	34	2009-09-19 14:47:50.83+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c5d03027-dde0-4a1d-b996-b3eb7f2fca5d	\N	\N	\N	\N	\N	\N	\N	\N	6
f828c336-e601-49f3-bd7d-6f03d56d3101	1	34	2009-09-19 14:47:50.845+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c5d03027-dde0-4a1d-b996-b3eb7f2fca5d	\N	\N	\N	\N	\N	\N	\N	\N	7
2c80d318-087c-4f1e-8f52-7771aef1a17a	1	34	2009-09-19 14:47:50.858+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c5d03027-dde0-4a1d-b996-b3eb7f2fca5d	\N	\N	\N	\N	\N	\N	\N	\N	8
1413577b-1c46-4fa9-bfcc-cf1f7595ac2e	1	34	2009-09-19 14:47:50.872+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c5d03027-dde0-4a1d-b996-b3eb7f2fca5d	\N	\N	\N	\N	\N	\N	\N	\N	9
80e87cab-1269-4d5f-b339-84dddde6d8ac	1	33	2009-09-19 14:47:50.887+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8c2c0ee6-1273-4d9b-83a1-0165268012f1	\N	\N	\N	\N	\N	\N	\N	\N	10
913a5347-f5f2-4304-8f37-df1aa0987887	1	34	2009-09-19 14:47:50.905+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	80e87cab-1269-4d5f-b339-84dddde6d8ac	\N	\N	\N	\N	\N	\N	\N	\N	1
7751b42e-174d-40c2-a8c3-ac225135d3a1	1	34	2009-09-19 14:47:50.92+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	80e87cab-1269-4d5f-b339-84dddde6d8ac	\N	\N	\N	\N	\N	\N	\N	\N	2
b7c5161a-d432-4dfd-a9d1-1bf7ffa850fa	1	34	2009-09-19 14:47:50.934+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	80e87cab-1269-4d5f-b339-84dddde6d8ac	\N	\N	\N	\N	\N	\N	\N	\N	3
e2355256-cd7c-440c-9894-cbf79be46542	1	34	2009-09-19 14:47:50.948+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	80e87cab-1269-4d5f-b339-84dddde6d8ac	\N	\N	\N	\N	\N	\N	\N	\N	4
96d59c8b-c826-499b-8ea7-2aec238572a2	1	34	2009-09-19 14:47:50.962+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	80e87cab-1269-4d5f-b339-84dddde6d8ac	\N	\N	\N	\N	\N	\N	\N	\N	5
f02fb268-ba07-43c1-888f-09d9d93ccae1	1	34	2009-09-19 14:47:50.976+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	80e87cab-1269-4d5f-b339-84dddde6d8ac	\N	\N	\N	\N	\N	\N	\N	\N	6
d30b8ab4-f945-47ac-9efa-bb61319518b8	1	34	2009-09-19 14:47:50.99+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	80e87cab-1269-4d5f-b339-84dddde6d8ac	\N	\N	\N	\N	\N	\N	\N	\N	7
e734d3ad-9afb-4187-b0ff-49e5f38ed971	1	34	2009-09-19 14:47:51.005+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	80e87cab-1269-4d5f-b339-84dddde6d8ac	\N	\N	\N	\N	\N	\N	\N	\N	8
65aa222e-eaf0-48ce-b972-e8c622137e68	1	34	2009-09-19 14:47:51.02+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	80e87cab-1269-4d5f-b339-84dddde6d8ac	\N	\N	\N	\N	\N	\N	\N	\N	9
ba9ed34f-2add-4aa8-9ff3-4ec37e15e21d	1	33	2009-09-19 14:47:51.034+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8c2c0ee6-1273-4d9b-83a1-0165268012f1	\N	\N	\N	\N	\N	\N	\N	\N	11
234fbab1-5483-4da0-9ac7-ec94b2d18416	1	34	2009-09-19 14:47:51.054+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ba9ed34f-2add-4aa8-9ff3-4ec37e15e21d	\N	\N	\N	\N	\N	\N	\N	\N	1
ff947d90-f6fa-46d0-abd0-6f340ce70149	1	34	2009-09-19 14:47:51.069+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ba9ed34f-2add-4aa8-9ff3-4ec37e15e21d	\N	\N	\N	\N	\N	\N	\N	\N	2
d2b3499e-18c5-4450-a402-ea854c2814de	1	34	2009-09-19 14:47:51.085+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ba9ed34f-2add-4aa8-9ff3-4ec37e15e21d	\N	\N	\N	\N	\N	\N	\N	\N	3
d226b04b-f84b-4be8-af0c-56114962da33	1	34	2009-09-19 14:47:51.10+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ba9ed34f-2add-4aa8-9ff3-4ec37e15e21d	\N	\N	\N	\N	\N	\N	\N	\N	4
ebcb42f7-6b4e-41ed-ba79-61338f89832e	1	34	2009-09-19 14:47:51.115+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ba9ed34f-2add-4aa8-9ff3-4ec37e15e21d	\N	\N	\N	\N	\N	\N	\N	\N	5
92a276d5-3c25-4ced-a77e-795c79144c9d	1	34	2009-09-19 14:47:51.14+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ba9ed34f-2add-4aa8-9ff3-4ec37e15e21d	\N	\N	\N	\N	\N	\N	\N	\N	6
958e086a-6691-4557-a23d-83c160589d38	1	34	2009-09-19 14:47:51.156+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ba9ed34f-2add-4aa8-9ff3-4ec37e15e21d	\N	\N	\N	\N	\N	\N	\N	\N	7
2e608af2-db76-4853-8a6e-3833be26f9c1	1	34	2009-09-19 14:47:51.171+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ba9ed34f-2add-4aa8-9ff3-4ec37e15e21d	\N	\N	\N	\N	\N	\N	\N	\N	8
a2479d80-2b1d-441d-8f15-a339f322856b	1	34	2009-09-19 14:47:51.187+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ba9ed34f-2add-4aa8-9ff3-4ec37e15e21d	\N	\N	\N	\N	\N	\N	\N	\N	9
679ae5a7-eaff-4807-8ee1-6455bfb1c3be	1	18	2009-09-19 14:59:06.869+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	2
83b6fa36-822d-4a99-8fe3-ceac4a816502	1	23	2009-09-19 14:59:06.876+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	2
8183dc57-f34f-4a14-803a-3e9018e75a74	1	24	2009-09-19 14:59:06.979+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	11
2c32a28f-fb5e-4ff4-abdd-6278cb85e41c	1	25	2009-09-19 15:01:47.961+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	1
73b1122d-285c-4ee2-9378-a30caebe10de	1	18	2009-09-19 15:00:48.045+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	3
2071c9c7-a20d-401e-a6fa-ec011d63a92e	1	17	2009-09-19 15:00:48.052+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	d5676dfc-7bfa-4e9d-a99a-d0947f47a58e	\N	\N	\N	\N	\N	\N	\N	\N	1
d5676dfc-7bfa-4e9d-a99a-d0947f47a58e	2	15	2009-09-19 15:00:48.035+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	2
e4406651-4f53-49be-85e5-8cf72d6545db	1	19	2009-09-19 15:00:48.058+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	2071c9c7-a20d-401e-a6fa-ec011d63a92e	\N	\N	\N	\N	\N	\N	\N	\N	1
87580947-dcb9-4008-a9c8-aa0facdc82d8	1	25	2009-09-19 15:01:47.966+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	2
21a0a8e4-0234-4717-93c6-8fc41b0d343f	1	25	2009-09-19 15:01:47.971+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	3
84a78d7e-df35-45aa-a743-3d16ef381ef1	1	25	2009-09-19 15:01:47.976+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	4
da8a4b12-1865-4dd7-81b1-d368c0d186dc	1	25	2009-09-19 15:01:47.981+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	5
17694cf4-4ca9-49a7-b8f5-0230e74d9824	1	25	2009-09-19 15:01:47.986+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	6
a34a0e83-1124-49a3-8591-e103e29f143b	1	25	2009-09-19 15:01:47.991+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	7
cd67f97f-15aa-4aef-84d8-ed1c121abf39	1	25	2009-09-19 15:01:47.997+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	8
b96c26e3-4623-4af3-bef2-b637d185ceeb	1	25	2009-09-19 15:01:48.002+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	9
0186a62c-1fb3-4a4f-ad3e-e0950abf60e8	1	25	2009-09-19 15:01:48.007+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	10
d2da13c6-eff7-4b91-bb9a-823177dcce00	1	25	2009-09-19 15:01:48.012+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	11
41444084-44a3-40f0-863b-64a03460d796	1	25	2009-09-19 15:01:48.017+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	12
7c1668ee-4668-4625-966b-e447d70a19ee	1	26	2009-09-19 15:01:48.032+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	1
005c6932-cebb-4f07-bb88-1f9cf8c6b417	1	27	2009-09-19 15:01:48.043+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	1
ca45be59-4a4d-435f-b863-8f15db7fdcfa	1	27	2009-09-19 15:01:48.052+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	2
30f83a8a-0eb4-44a1-9c5a-cecdda74c646	1	27	2009-09-19 15:01:48.061+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	3
3e9ed43f-6b0b-47d7-951f-8d2154c6dc1b	1	27	2009-09-19 15:01:48.071+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	4
336af087-1a89-41db-a5eb-d1dcb3699257	1	27	2009-09-19 15:01:48.08+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	5
5b67c098-5908-448a-820d-d558210bb385	1	27	2009-09-19 15:01:48.088+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	6
d48df06d-c213-4b5c-a75f-708c8fd5adb3	1	27	2009-09-19 15:01:48.098+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	7
17ea8a86-8b8d-4ac2-bfdb-260ae324f1a8	1	27	2009-09-19 15:01:48.106+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	8
c6fc20ff-3ed2-4d53-9a1c-0e4370ccfda5	1	27	2009-09-19 15:01:48.115+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	9
1961cf07-5ff6-4928-b48d-2d9e46764173	1	27	2009-09-19 15:01:48.124+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	10
9e359232-6924-4894-ba03-a3568692188c	1	27	2009-09-19 15:01:48.133+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	11
a3e2f33b-e945-4507-a7fe-a037f558f316	1	27	2009-09-19 15:01:48.142+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	12
112a5651-5c59-45f7-9277-6762fa894051	1	27	2009-09-19 15:01:48.151+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	13
3b00829e-3c16-4361-acb6-e3f46165818d	1	27	2009-09-19 15:01:48.159+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	14
0c9a7577-7f14-46a4-a731-aa9ad6e3ab03	1	27	2009-09-19 15:01:48.17+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	15
0e3b8879-f288-4869-a168-ae7b992cc332	1	27	2009-09-19 15:01:48.18+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	16
2c98fa56-6ab7-42ba-b402-00c07e84b7b7	1	27	2009-09-19 15:01:48.192+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	17
602e9b16-1d6d-441c-9a58-78b97aec6f29	1	27	2009-09-19 15:01:48.202+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	18
e3b53b43-30e6-485b-90b7-9a54c0d23cce	1	27	2009-09-19 15:01:48.211+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	19
84192873-c898-4526-a631-869267e1f4c2	1	27	2009-09-19 15:01:48.221+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	20
1ac4278a-2b43-4d63-9dca-20dbcc9253cc	1	27	2009-09-19 15:01:48.231+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	21
665b7857-ac3c-4a8d-b217-b146437ff546	1	27	2009-09-19 15:01:48.24+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	22
493cbc26-5070-45cb-af8a-07d2bca30005	1	27	2009-09-19 15:01:48.25+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	23
a4ebbfc0-4b3c-4ea8-a2f3-d482c48bce65	1	27	2009-09-19 15:01:48.26+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	24
04ff1a7f-0478-4cd6-b7cd-2f3a4c839ed9	1	27	2009-09-19 15:01:48.27+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	25
71e68c8a-7683-4577-99c4-3879089b71a7	1	27	2009-09-19 15:01:48.288+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	26
baa5d350-244e-4ea7-a3e3-b072248be94a	1	27	2009-09-19 15:01:48.298+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	27
4120b200-d542-4fca-983a-f72eced7fa21	1	27	2009-09-19 15:01:48.309+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	28
6d8df62e-feb4-4362-a9f9-6b12376a7dbb	1	27	2009-09-19 15:01:48.319+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	29
2ee0c439-a483-4459-bfc4-f357048f3eb0	1	27	2009-09-19 15:01:48.33+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	30
15fd3ba5-c2ae-45ea-9a55-a3f015e4fabe	1	27	2009-09-19 15:01:48.34+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	31
fce46703-e06a-4d80-87be-7e04d2002156	1	27	2009-09-19 15:01:48.349+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	32
8ea8d555-3f9f-433d-85ed-7b0ed9060bb8	1	27	2009-09-19 15:01:48.359+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	33
f8451834-35f4-4ceb-b7fa-e446d5d58215	1	27	2009-09-19 15:01:48.396+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	34
6cbf0d65-e14d-4b17-867f-92fee635f813	1	27	2009-09-19 15:01:48.408+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	35
bf49be07-0518-4062-b2bf-de06a3e0c2cf	1	27	2009-09-19 15:01:48.42+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	36
f311a345-db25-4b79-99cd-fa168685e036	1	27	2009-09-19 15:01:48.431+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	37
b8e5cc02-27c3-4d52-8e3c-c9e4cf09dcba	1	27	2009-09-19 15:01:48.442+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	38
37246a6b-b9f0-4f70-82ff-7bd54eefec7d	1	27	2009-09-19 15:01:48.453+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	39
6bbe29c5-0f3d-4e14-91d7-380170b6ea22	1	27	2009-09-19 15:01:48.463+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	40
aea9572a-ab14-4423-9e91-46a24d2871c2	1	27	2009-09-19 15:01:48.475+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	41
75b7e8d7-54d7-4639-a9ea-287ea1e160d3	1	27	2009-09-19 15:01:48.486+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	42
2876f9f6-11e3-4892-a5f1-495db0a694a0	1	27	2009-09-19 15:01:48.496+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	43
fe80a319-c61c-4608-8dfa-49c58d7f4fa1	1	27	2009-09-19 15:01:48.507+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	44
9560144a-5f44-4ff1-8fca-f9272595c512	1	27	2009-09-19 15:01:48.519+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	45
775d24d4-043c-44af-b5be-4574c4b69dab	1	27	2009-09-19 15:01:48.53+03	83b6fa36-822d-4a99-8fe3-ceac4a816502	b74ccda7-9ada-42a3-8e01-e04434d46288	f	f	f	f	f	81e50b6b-68a6-4e50-9103-95fe8eba86f3	\N	\N	\N	\N	\N	\N	\N	\N	46
de29ddad-373f-4c4f-bfc3-e598f8c8a787	1	21	2009-09-21 22:06:03.204+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	495e35cb-eb58-4e04-a147-cecd2116ace4	\N	\N	\N	\N	\N	\N	\N	\N	3
ef1b906f-f3c4-4d23-b985-3166dd9f7021	1	23	2009-09-21 22:06:03.225+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	1
95a3be42-b26a-495e-86ca-b8c7a03cdf6e	1	28	2009-09-21 22:06:03.235+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	1
d7b9347e-39fc-45aa-b315-b4b573dc1292	1	28	2009-09-21 22:06:03.247+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	2
77918cf2-ab28-4d5f-ae31-3f40e6403204	1	24	2009-09-21 22:06:03.284+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	12
cb187b52-309b-41f3-a750-fb43e4e44c23	1	30	2009-09-21 22:06:03.295+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	1
8d550848-9260-41a7-aefd-8969d4179b13	1	15	2009-09-21 22:06:03.022+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	1
d5833cc1-d3c2-4acf-9b61-a12da1190a7f	1	17	2009-09-21 22:06:03.062+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	1
28425f1b-8617-4f32-8118-fd5a9acc66f9	1	18	2009-09-21 22:06:03.093+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	1
495e35cb-eb58-4e04-a147-cecd2116ace4	1	19	2009-09-21 22:06:03.103+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	\N	\N	\N	\N	\N	\N	\N	\N	1
bffeaf9c-1d88-4479-9156-d610d95dc162	1	20	2009-09-21 22:06:03.115+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	\N	\N	\N	\N	\N	\N	\N	\N	1
1245c083-64a4-4d36-b701-0f3ae6577848	1	21	2009-09-21 22:06:03.13+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	495e35cb-eb58-4e04-a147-cecd2116ace4	\N	\N	\N	\N	\N	\N	\N	\N	1
56b2509b-5464-407d-b2e2-7473d2463308	1	20	2009-09-21 22:06:03.137+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	\N	\N	\N	\N	\N	\N	\N	\N	2
041d3ff7-4ac2-4da7-8e4f-f0aecd7160b1	1	21	2009-09-21 22:06:03.149+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	495e35cb-eb58-4e04-a147-cecd2116ace4	\N	\N	\N	\N	\N	\N	\N	\N	2
40b64052-fbdb-4c68-94f2-5a9e3cade9ad	1	20	2009-09-21 22:06:03.156+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	\N	\N	\N	\N	\N	\N	\N	\N	3
975338fd-88af-423d-9b0e-47a7c92f3553	1	31	2009-09-21 22:06:03.347+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	77918cf2-ab28-4d5f-ae31-3f40e6403204	\N	\N	\N	\N	\N	\N	\N	\N	1
c7d50eaf-9558-4de9-ba38-455d41f98f68	1	32	2009-09-21 22:06:06.801+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	1
9d09bdc0-860b-4356-bcdb-ea5b99173c68	1	32	2009-09-21 22:06:06.868+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	2
2cd0316f-4c15-4155-8665-458e92e11c6f	1	32	2009-09-21 22:06:06.899+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	3
54b43010-2b31-4ddf-bfb6-3626824ca6df	1	32	2009-09-21 22:06:06.908+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	4
6b5cffeb-614a-421f-b065-3e29ae0ed32f	1	32	2009-09-21 22:06:06.918+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	5
f766d351-6c79-4de3-837a-edc06845e991	1	32	2009-09-21 22:06:06.928+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	6
73d0a259-6e42-46d8-b830-fb1dcb49013a	1	32	2009-09-21 22:06:06.937+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	7
bde92c12-9d26-4a79-be8c-739dbdad6df9	1	32	2009-09-21 22:06:06.947+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	8
f15ef4ec-5967-4b41-8e44-0c00c152892a	1	32	2009-09-21 22:06:06.957+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	9
09ec3491-80ec-418a-810e-de6a558a7774	1	32	2009-09-21 22:06:06.967+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	10
b86099e5-cdd7-42d1-8a0b-350206fc7a08	1	32	2009-09-21 22:06:06.978+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	11
5295d320-96c9-4de7-bee9-24e1b708aa49	1	32	2009-09-21 22:06:06.988+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	12
163b03b7-c2ce-4836-bcd4-3c88e32e6625	1	33	2009-09-21 22:06:07.005+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	cb187b52-309b-41f3-a750-fb43e4e44c23	\N	\N	\N	\N	\N	\N	\N	\N	1
26e13abb-8ac3-416c-9c74-fac4dfe4fcb1	1	34	2009-09-21 22:06:07.018+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	163b03b7-c2ce-4836-bcd4-3c88e32e6625	\N	\N	\N	\N	\N	\N	\N	\N	1
7be450f4-f475-4a0b-b9fe-0848ff76bdbf	1	34	2009-09-21 22:06:07.049+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	163b03b7-c2ce-4836-bcd4-3c88e32e6625	\N	\N	\N	\N	\N	\N	\N	\N	2
6399a675-2c6d-46bb-8228-c070dfa0723b	1	34	2009-09-21 22:06:07.062+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	163b03b7-c2ce-4836-bcd4-3c88e32e6625	\N	\N	\N	\N	\N	\N	\N	\N	3
58b491d6-e3be-420b-826e-a9a000cb3446	1	34	2009-09-21 22:06:07.074+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	163b03b7-c2ce-4836-bcd4-3c88e32e6625	\N	\N	\N	\N	\N	\N	\N	\N	4
868e3519-a581-4f4f-b9f2-68f34a29fdda	1	34	2009-09-21 22:06:07.084+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	163b03b7-c2ce-4836-bcd4-3c88e32e6625	\N	\N	\N	\N	\N	\N	\N	\N	5
5540b7b7-84ee-4eec-8265-a50924e008ad	1	34	2009-09-21 22:06:07.094+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	163b03b7-c2ce-4836-bcd4-3c88e32e6625	\N	\N	\N	\N	\N	\N	\N	\N	6
de656274-d940-4ede-b85a-d90d005bc87a	1	34	2009-09-21 22:06:07.104+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	163b03b7-c2ce-4836-bcd4-3c88e32e6625	\N	\N	\N	\N	\N	\N	\N	\N	7
928d0719-f96f-402f-aada-71db00ecbbf5	1	34	2009-09-21 22:06:07.115+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	163b03b7-c2ce-4836-bcd4-3c88e32e6625	\N	\N	\N	\N	\N	\N	\N	\N	8
aa7b088e-2644-4c8c-b2c2-7694a43846ac	1	34	2009-09-21 22:06:07.125+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	163b03b7-c2ce-4836-bcd4-3c88e32e6625	\N	\N	\N	\N	\N	\N	\N	\N	9
345fb187-083e-4f2a-acd5-dce9c60b5128	1	33	2009-09-21 22:06:07.136+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	cb187b52-309b-41f3-a750-fb43e4e44c23	\N	\N	\N	\N	\N	\N	\N	\N	2
3a8c5414-a471-42e3-b628-f51d80300320	1	34	2009-09-21 22:06:07.15+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	345fb187-083e-4f2a-acd5-dce9c60b5128	\N	\N	\N	\N	\N	\N	\N	\N	1
6c5a0262-817b-4fc3-9864-0ae7ab66284f	1	34	2009-09-21 22:06:07.161+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	345fb187-083e-4f2a-acd5-dce9c60b5128	\N	\N	\N	\N	\N	\N	\N	\N	2
709dec42-10c9-4fda-bfc6-593e7e69c1d2	1	34	2009-09-21 22:06:07.171+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	345fb187-083e-4f2a-acd5-dce9c60b5128	\N	\N	\N	\N	\N	\N	\N	\N	3
19c9ba1e-8314-4819-b030-a21a0ebbb3d0	1	34	2009-09-21 22:06:07.181+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	345fb187-083e-4f2a-acd5-dce9c60b5128	\N	\N	\N	\N	\N	\N	\N	\N	4
1b83fa0f-a5e0-4bad-a92e-e8857efc37aa	1	34	2009-09-21 22:06:07.192+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	345fb187-083e-4f2a-acd5-dce9c60b5128	\N	\N	\N	\N	\N	\N	\N	\N	5
e84ae2fa-d54b-44e0-ab66-973e660bead9	1	34	2009-09-21 22:06:07.202+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	345fb187-083e-4f2a-acd5-dce9c60b5128	\N	\N	\N	\N	\N	\N	\N	\N	6
47ae12a5-de06-4468-9e20-93b0bf02f41d	1	34	2009-09-21 22:06:07.212+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	345fb187-083e-4f2a-acd5-dce9c60b5128	\N	\N	\N	\N	\N	\N	\N	\N	7
00121c6b-c40a-4b75-9589-8a80efc73135	1	34	2009-09-21 22:06:07.222+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	345fb187-083e-4f2a-acd5-dce9c60b5128	\N	\N	\N	\N	\N	\N	\N	\N	8
6ce15cac-b650-4546-824b-80442cb88b75	1	34	2009-09-21 22:06:07.232+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	345fb187-083e-4f2a-acd5-dce9c60b5128	\N	\N	\N	\N	\N	\N	\N	\N	9
88640b2b-e1b1-4447-9992-05fa77d26bf5	1	33	2009-09-21 22:06:07.244+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	cb187b52-309b-41f3-a750-fb43e4e44c23	\N	\N	\N	\N	\N	\N	\N	\N	3
bb60bf8a-fc47-445d-aa32-f38d6b2cb8c9	1	34	2009-09-21 22:06:07.257+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	88640b2b-e1b1-4447-9992-05fa77d26bf5	\N	\N	\N	\N	\N	\N	\N	\N	1
d07b2831-2691-4236-b9eb-ee5b8a3f5de8	1	34	2009-09-21 22:06:07.268+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	88640b2b-e1b1-4447-9992-05fa77d26bf5	\N	\N	\N	\N	\N	\N	\N	\N	2
7ee10324-c778-4a78-b98a-2ab6313b1848	1	34	2009-09-21 22:06:07.279+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	88640b2b-e1b1-4447-9992-05fa77d26bf5	\N	\N	\N	\N	\N	\N	\N	\N	3
3108ac67-5b6f-44ac-ad82-7226715d1813	1	34	2009-09-21 22:06:07.29+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	88640b2b-e1b1-4447-9992-05fa77d26bf5	\N	\N	\N	\N	\N	\N	\N	\N	4
62cebd65-14b5-473a-a4b5-d4a57087b97f	1	34	2009-09-21 22:06:07.301+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	88640b2b-e1b1-4447-9992-05fa77d26bf5	\N	\N	\N	\N	\N	\N	\N	\N	5
3bdd00c1-4d37-48dc-921c-3e301d128a65	1	34	2009-09-21 22:06:07.312+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	88640b2b-e1b1-4447-9992-05fa77d26bf5	\N	\N	\N	\N	\N	\N	\N	\N	6
45a332c3-d661-46ac-bc38-14f4143218f1	1	34	2009-09-21 22:06:07.322+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	88640b2b-e1b1-4447-9992-05fa77d26bf5	\N	\N	\N	\N	\N	\N	\N	\N	7
326c3f28-1f60-4c69-a50d-0abad44081f6	1	34	2009-09-21 22:06:07.341+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	88640b2b-e1b1-4447-9992-05fa77d26bf5	\N	\N	\N	\N	\N	\N	\N	\N	8
32bff1fd-ab9e-475b-84bb-ee2442093852	1	34	2009-09-21 22:06:07.352+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	88640b2b-e1b1-4447-9992-05fa77d26bf5	\N	\N	\N	\N	\N	\N	\N	\N	9
3bdd9c7d-b670-478d-b5b6-48b278788939	1	33	2009-09-21 22:06:07.362+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	cb187b52-309b-41f3-a750-fb43e4e44c23	\N	\N	\N	\N	\N	\N	\N	\N	4
1395f4ec-a208-412d-9dac-4f0adfa28b21	1	34	2009-09-21 22:06:07.376+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	3bdd9c7d-b670-478d-b5b6-48b278788939	\N	\N	\N	\N	\N	\N	\N	\N	1
ca9462ff-c097-45d3-a1c0-2f0821b0900c	1	34	2009-09-21 22:06:07.386+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	3bdd9c7d-b670-478d-b5b6-48b278788939	\N	\N	\N	\N	\N	\N	\N	\N	2
4f499fd3-b8e1-4442-a477-ea9a42ba473d	1	34	2009-09-21 22:06:07.398+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	3bdd9c7d-b670-478d-b5b6-48b278788939	\N	\N	\N	\N	\N	\N	\N	\N	3
37c9ba27-0e5b-49e1-b118-327b72f346e7	1	34	2009-09-21 22:06:07.409+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	3bdd9c7d-b670-478d-b5b6-48b278788939	\N	\N	\N	\N	\N	\N	\N	\N	4
1a7d4723-8ed0-4af5-afa0-167b5133dcb1	1	34	2009-09-21 22:06:07.42+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	3bdd9c7d-b670-478d-b5b6-48b278788939	\N	\N	\N	\N	\N	\N	\N	\N	5
29bb0e81-0af7-470a-a8bf-5a5626656522	1	34	2009-09-21 22:06:07.432+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	3bdd9c7d-b670-478d-b5b6-48b278788939	\N	\N	\N	\N	\N	\N	\N	\N	6
5ffdcb5e-01aa-400f-9308-2adfbf543da0	1	34	2009-09-21 22:06:07.443+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	3bdd9c7d-b670-478d-b5b6-48b278788939	\N	\N	\N	\N	\N	\N	\N	\N	7
4d458c44-fe59-4fb3-a7a2-87b0b57ac502	1	34	2009-09-21 22:06:07.454+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	3bdd9c7d-b670-478d-b5b6-48b278788939	\N	\N	\N	\N	\N	\N	\N	\N	8
02cefa25-ea7f-45d2-ad3d-c42f20e07280	1	34	2009-09-21 22:06:07.466+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	3bdd9c7d-b670-478d-b5b6-48b278788939	\N	\N	\N	\N	\N	\N	\N	\N	9
039427c3-193d-47b0-8172-632c87480eb7	1	33	2009-09-21 22:06:07.478+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	cb187b52-309b-41f3-a750-fb43e4e44c23	\N	\N	\N	\N	\N	\N	\N	\N	5
bda12b11-beaa-4008-82bf-0e4285a2ebde	1	34	2009-09-21 22:06:07.494+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	039427c3-193d-47b0-8172-632c87480eb7	\N	\N	\N	\N	\N	\N	\N	\N	1
76c8328d-e4e5-494b-8474-62f2fd78e376	1	34	2009-09-21 22:06:07.505+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	039427c3-193d-47b0-8172-632c87480eb7	\N	\N	\N	\N	\N	\N	\N	\N	2
74f0f000-4ae0-419c-9f4e-e806f67655b7	1	34	2009-09-21 22:06:07.517+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	039427c3-193d-47b0-8172-632c87480eb7	\N	\N	\N	\N	\N	\N	\N	\N	3
83cc5aa5-f01d-4cb9-b53b-173bdcfed6a9	1	34	2009-09-21 22:06:07.528+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	039427c3-193d-47b0-8172-632c87480eb7	\N	\N	\N	\N	\N	\N	\N	\N	4
61e873ac-c9de-4a23-8bed-3cdde3a7f639	1	34	2009-09-21 22:06:07.539+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	039427c3-193d-47b0-8172-632c87480eb7	\N	\N	\N	\N	\N	\N	\N	\N	5
f9e8e0ce-ee14-4441-9c2e-5bb3d46f3b46	1	34	2009-09-21 22:06:07.551+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	039427c3-193d-47b0-8172-632c87480eb7	\N	\N	\N	\N	\N	\N	\N	\N	6
f6393963-e03b-475c-a438-48ea3bdfe376	1	34	2009-09-21 22:06:07.564+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	039427c3-193d-47b0-8172-632c87480eb7	\N	\N	\N	\N	\N	\N	\N	\N	7
d5010e81-b10f-4e3f-b068-b8302238eaea	1	34	2009-09-21 22:06:07.577+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	039427c3-193d-47b0-8172-632c87480eb7	\N	\N	\N	\N	\N	\N	\N	\N	8
1267859c-54c9-4388-bb06-9fc05f7b6fa3	1	34	2009-09-21 22:06:07.59+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	039427c3-193d-47b0-8172-632c87480eb7	\N	\N	\N	\N	\N	\N	\N	\N	9
c1ce2f6d-4e2c-4da7-b0e3-932b745cec40	1	33	2009-09-21 22:06:07.602+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	cb187b52-309b-41f3-a750-fb43e4e44c23	\N	\N	\N	\N	\N	\N	\N	\N	6
fb9be897-9276-40fc-803c-8b3c43e8f32a	1	34	2009-09-21 22:06:07.617+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c1ce2f6d-4e2c-4da7-b0e3-932b745cec40	\N	\N	\N	\N	\N	\N	\N	\N	1
766bfb8b-ce72-4482-8f53-627bc6115ac5	1	34	2009-09-21 22:06:07.629+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c1ce2f6d-4e2c-4da7-b0e3-932b745cec40	\N	\N	\N	\N	\N	\N	\N	\N	2
49a12009-395b-411f-9994-2d23e21f1c70	1	34	2009-09-21 22:06:07.64+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c1ce2f6d-4e2c-4da7-b0e3-932b745cec40	\N	\N	\N	\N	\N	\N	\N	\N	3
c38fc30e-3a74-448f-ade4-bce86e700f64	1	34	2009-09-21 22:06:07.652+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c1ce2f6d-4e2c-4da7-b0e3-932b745cec40	\N	\N	\N	\N	\N	\N	\N	\N	4
f5fbb4af-7e8f-485c-a02f-505ee1ba53c4	1	34	2009-09-21 22:06:07.663+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c1ce2f6d-4e2c-4da7-b0e3-932b745cec40	\N	\N	\N	\N	\N	\N	\N	\N	5
bb0f49e4-05b6-4452-abca-49333263bb54	1	34	2009-09-21 22:06:07.674+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c1ce2f6d-4e2c-4da7-b0e3-932b745cec40	\N	\N	\N	\N	\N	\N	\N	\N	6
da3c4616-357a-4201-919d-315b9b4fcbae	1	34	2009-09-21 22:06:07.686+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c1ce2f6d-4e2c-4da7-b0e3-932b745cec40	\N	\N	\N	\N	\N	\N	\N	\N	7
beafe565-bf35-4ea5-a450-15b8e077f979	1	34	2009-09-21 22:06:07.698+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c1ce2f6d-4e2c-4da7-b0e3-932b745cec40	\N	\N	\N	\N	\N	\N	\N	\N	8
cc46203a-bb1a-4c99-acf1-d9420fdd756a	1	34	2009-09-21 22:06:07.71+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c1ce2f6d-4e2c-4da7-b0e3-932b745cec40	\N	\N	\N	\N	\N	\N	\N	\N	9
a9d14c6d-6853-4d2e-9d09-afa7dfda87d7	1	33	2009-09-21 22:06:07.723+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	cb187b52-309b-41f3-a750-fb43e4e44c23	\N	\N	\N	\N	\N	\N	\N	\N	7
7e796684-f702-4d02-9633-57a339f87991	1	34	2009-09-21 22:06:07.738+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a9d14c6d-6853-4d2e-9d09-afa7dfda87d7	\N	\N	\N	\N	\N	\N	\N	\N	1
68ddbe6c-115e-4611-a114-9f27a9e056b6	1	34	2009-09-21 22:06:07.75+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a9d14c6d-6853-4d2e-9d09-afa7dfda87d7	\N	\N	\N	\N	\N	\N	\N	\N	2
ca068b13-dc7c-4129-8a6f-48726b6c9aa2	1	34	2009-09-21 22:06:07.762+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a9d14c6d-6853-4d2e-9d09-afa7dfda87d7	\N	\N	\N	\N	\N	\N	\N	\N	3
ea6550df-dd84-44fa-af54-410a32ee8039	1	34	2009-09-21 22:06:07.773+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a9d14c6d-6853-4d2e-9d09-afa7dfda87d7	\N	\N	\N	\N	\N	\N	\N	\N	4
73ca9445-65cb-4747-ab50-29868eca85bb	1	34	2009-09-21 22:06:07.785+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a9d14c6d-6853-4d2e-9d09-afa7dfda87d7	\N	\N	\N	\N	\N	\N	\N	\N	5
129ac3ea-1588-44d5-94d7-b464ed4588b4	1	34	2009-09-21 22:06:07.797+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a9d14c6d-6853-4d2e-9d09-afa7dfda87d7	\N	\N	\N	\N	\N	\N	\N	\N	6
373591ee-6bcd-4f2a-bef2-4273a8ab2374	1	34	2009-09-21 22:06:07.809+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a9d14c6d-6853-4d2e-9d09-afa7dfda87d7	\N	\N	\N	\N	\N	\N	\N	\N	7
470d4c9f-715b-4a03-b469-7926eeae15f8	1	34	2009-09-21 22:06:07.822+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a9d14c6d-6853-4d2e-9d09-afa7dfda87d7	\N	\N	\N	\N	\N	\N	\N	\N	8
b8617b75-8026-4e9a-b6dc-7aec9a2d5857	1	34	2009-09-21 22:06:07.834+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a9d14c6d-6853-4d2e-9d09-afa7dfda87d7	\N	\N	\N	\N	\N	\N	\N	\N	9
aa7c7f2c-7cb9-474c-ac7f-4c05d589ad5c	1	33	2009-09-21 22:06:07.848+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	cb187b52-309b-41f3-a750-fb43e4e44c23	\N	\N	\N	\N	\N	\N	\N	\N	8
8bc5dc7e-6f09-42f8-bed7-30e473fe5873	1	34	2009-09-21 22:06:07.864+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	aa7c7f2c-7cb9-474c-ac7f-4c05d589ad5c	\N	\N	\N	\N	\N	\N	\N	\N	1
30773adc-851c-47f5-824a-99db4bc183a6	1	34	2009-09-21 22:06:07.875+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	aa7c7f2c-7cb9-474c-ac7f-4c05d589ad5c	\N	\N	\N	\N	\N	\N	\N	\N	2
5c5c1e96-f995-43d4-be0d-ceb216dc1427	1	34	2009-09-21 22:06:07.887+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	aa7c7f2c-7cb9-474c-ac7f-4c05d589ad5c	\N	\N	\N	\N	\N	\N	\N	\N	3
df893dc5-1230-41a5-a417-9fedce40c0bb	1	34	2009-09-21 22:06:07.899+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	aa7c7f2c-7cb9-474c-ac7f-4c05d589ad5c	\N	\N	\N	\N	\N	\N	\N	\N	4
75a6b229-4c8d-41c0-b847-1c7274716c2f	1	34	2009-09-21 22:06:07.912+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	aa7c7f2c-7cb9-474c-ac7f-4c05d589ad5c	\N	\N	\N	\N	\N	\N	\N	\N	5
846ffbc8-ca7b-4ddd-b46f-2b50d5e85ad9	1	34	2009-09-21 22:06:07.925+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	aa7c7f2c-7cb9-474c-ac7f-4c05d589ad5c	\N	\N	\N	\N	\N	\N	\N	\N	6
d74f530a-70ed-4b4c-b1be-2383193c1077	1	34	2009-09-21 22:06:07.938+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	aa7c7f2c-7cb9-474c-ac7f-4c05d589ad5c	\N	\N	\N	\N	\N	\N	\N	\N	7
8eb98f2d-5077-44cb-917b-b3908f163a09	1	34	2009-09-21 22:06:07.95+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	aa7c7f2c-7cb9-474c-ac7f-4c05d589ad5c	\N	\N	\N	\N	\N	\N	\N	\N	8
2e9cdfd0-0fe0-418f-aac4-6d258f3f27b6	1	34	2009-09-21 22:06:07.962+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	aa7c7f2c-7cb9-474c-ac7f-4c05d589ad5c	\N	\N	\N	\N	\N	\N	\N	\N	9
445b2727-0b2f-41aa-b69f-f1184b489a68	1	33	2009-09-21 22:06:07.976+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	cb187b52-309b-41f3-a750-fb43e4e44c23	\N	\N	\N	\N	\N	\N	\N	\N	9
abf7946b-080d-43ae-89f8-1d18207a3719	1	34	2009-09-21 22:06:07.993+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	445b2727-0b2f-41aa-b69f-f1184b489a68	\N	\N	\N	\N	\N	\N	\N	\N	1
608e284b-15b1-4bd4-96b6-b7647e5ce62b	1	34	2009-09-21 22:06:08.006+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	445b2727-0b2f-41aa-b69f-f1184b489a68	\N	\N	\N	\N	\N	\N	\N	\N	2
4531a827-9774-4f88-b6b0-f91e3d48386f	1	34	2009-09-21 22:06:08.019+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	445b2727-0b2f-41aa-b69f-f1184b489a68	\N	\N	\N	\N	\N	\N	\N	\N	3
55827562-22af-4500-81cc-4de0b839fe37	1	34	2009-09-21 22:06:08.032+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	445b2727-0b2f-41aa-b69f-f1184b489a68	\N	\N	\N	\N	\N	\N	\N	\N	4
f208118b-380a-487b-891e-53ea08659e6a	1	34	2009-09-21 22:06:08.046+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	445b2727-0b2f-41aa-b69f-f1184b489a68	\N	\N	\N	\N	\N	\N	\N	\N	5
ed275c09-bde6-455a-b989-b70372b32e4a	1	34	2009-09-21 22:06:08.059+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	445b2727-0b2f-41aa-b69f-f1184b489a68	\N	\N	\N	\N	\N	\N	\N	\N	6
0a709fe9-68d2-4f89-a996-bd29e2dee8c3	1	34	2009-09-21 22:06:08.087+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	445b2727-0b2f-41aa-b69f-f1184b489a68	\N	\N	\N	\N	\N	\N	\N	\N	7
ec9ae883-eec1-4c42-bcd8-4f4e6d3647dd	1	34	2009-09-21 22:06:08.099+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	445b2727-0b2f-41aa-b69f-f1184b489a68	\N	\N	\N	\N	\N	\N	\N	\N	8
1f638b5c-5b8e-41a1-8508-b6ef424d30d1	1	34	2009-09-21 22:06:08.113+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	445b2727-0b2f-41aa-b69f-f1184b489a68	\N	\N	\N	\N	\N	\N	\N	\N	9
321c9a37-875e-4462-9dca-42e1773621ce	1	33	2009-09-21 22:06:08.127+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	cb187b52-309b-41f3-a750-fb43e4e44c23	\N	\N	\N	\N	\N	\N	\N	\N	10
f8eb6da7-b294-4aab-acb0-58b312d0fa61	1	34	2009-09-21 22:06:08.146+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	321c9a37-875e-4462-9dca-42e1773621ce	\N	\N	\N	\N	\N	\N	\N	\N	1
d2081e09-34e6-48a9-9f53-1468053805ed	1	34	2009-09-21 22:06:08.162+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	321c9a37-875e-4462-9dca-42e1773621ce	\N	\N	\N	\N	\N	\N	\N	\N	2
5530bee7-ca67-44b7-ac26-29fbf60e45d3	1	34	2009-09-21 22:06:08.176+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	321c9a37-875e-4462-9dca-42e1773621ce	\N	\N	\N	\N	\N	\N	\N	\N	3
3ad57856-cb56-47a3-8f91-b28d1fde95c5	1	34	2009-09-21 22:06:08.203+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	321c9a37-875e-4462-9dca-42e1773621ce	\N	\N	\N	\N	\N	\N	\N	\N	4
3025f0ec-4ac0-4d71-839f-e97476ff612b	1	34	2009-09-21 22:06:08.216+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	321c9a37-875e-4462-9dca-42e1773621ce	\N	\N	\N	\N	\N	\N	\N	\N	5
f52cc0fd-0cf6-4005-a91d-541cc1c18d6e	1	34	2009-09-21 22:06:08.228+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	321c9a37-875e-4462-9dca-42e1773621ce	\N	\N	\N	\N	\N	\N	\N	\N	6
bbdf5243-6755-4a6e-a880-31b3ccee9fc9	1	34	2009-09-21 22:06:08.241+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	321c9a37-875e-4462-9dca-42e1773621ce	\N	\N	\N	\N	\N	\N	\N	\N	7
e1490418-98b9-4952-ac58-446360f61b28	1	34	2009-09-21 22:06:08.255+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	321c9a37-875e-4462-9dca-42e1773621ce	\N	\N	\N	\N	\N	\N	\N	\N	8
564a3959-f39a-4243-b00c-22d527ac2b92	1	34	2009-09-21 22:06:08.269+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	321c9a37-875e-4462-9dca-42e1773621ce	\N	\N	\N	\N	\N	\N	\N	\N	9
0b9b5e21-0fe5-43bd-9fb9-47d120990117	1	33	2009-09-21 22:06:08.282+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	cb187b52-309b-41f3-a750-fb43e4e44c23	\N	\N	\N	\N	\N	\N	\N	\N	11
82958b02-5e41-4e05-af61-3fea34fbb906	1	34	2009-09-21 22:06:08.30+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	0b9b5e21-0fe5-43bd-9fb9-47d120990117	\N	\N	\N	\N	\N	\N	\N	\N	1
fc9d635e-6a3c-4314-be80-cd10090754e3	1	34	2009-09-21 22:06:08.314+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	0b9b5e21-0fe5-43bd-9fb9-47d120990117	\N	\N	\N	\N	\N	\N	\N	\N	2
6fdf7bb3-ab25-49c0-9a49-4113bcd9bc34	1	34	2009-09-21 22:06:08.327+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	0b9b5e21-0fe5-43bd-9fb9-47d120990117	\N	\N	\N	\N	\N	\N	\N	\N	3
d02ea7fc-539e-48e4-8386-40a93580be77	1	34	2009-09-21 22:06:08.341+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	0b9b5e21-0fe5-43bd-9fb9-47d120990117	\N	\N	\N	\N	\N	\N	\N	\N	4
e2bad21f-aa48-4678-81d6-9365208dbe10	1	34	2009-09-21 22:06:08.355+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	0b9b5e21-0fe5-43bd-9fb9-47d120990117	\N	\N	\N	\N	\N	\N	\N	\N	5
6f4aa46e-6bbc-42d0-853a-f8d06dfe5e67	1	34	2009-09-21 22:06:08.37+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	0b9b5e21-0fe5-43bd-9fb9-47d120990117	\N	\N	\N	\N	\N	\N	\N	\N	6
5bdf8e5e-1ad5-4dca-975f-65b559864eea	1	34	2009-09-21 22:06:08.384+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	0b9b5e21-0fe5-43bd-9fb9-47d120990117	\N	\N	\N	\N	\N	\N	\N	\N	7
2aa5db15-8acc-4158-ae61-ea3f7d67fdbe	1	34	2009-09-21 22:06:08.397+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	0b9b5e21-0fe5-43bd-9fb9-47d120990117	\N	\N	\N	\N	\N	\N	\N	\N	8
6b1f398a-dc9d-45ad-b40f-3677a4f24777	1	34	2009-09-21 22:06:08.411+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	0b9b5e21-0fe5-43bd-9fb9-47d120990117	\N	\N	\N	\N	\N	\N	\N	\N	9
9bbed255-4a67-42ff-bc7e-687d944da307	1	18	2009-09-21 22:08:01.157+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	2
77aeed6d-abb5-4878-a4d2-7b3693c33502	1	23	2009-09-21 22:08:01.165+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	2
47bdaabc-85f3-4d72-a211-b6c2e43c6680	1	24	2009-09-21 22:08:01.344+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	13
cfd7f656-4163-4ff0-bac4-383900c4ee0d	1	25	2009-09-21 22:24:42.881+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	1
b6c47fe6-9f3b-48a3-afe1-d48dd740fada	1	25	2009-09-21 22:24:42.902+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	2
273ce952-e973-4f7f-b800-75472ec95f8a	1	25	2009-09-21 22:24:42.94+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	3
f80661d3-a8d0-4043-86c9-188c96f985e2	1	25	2009-09-21 22:24:42.945+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	4
144ef086-97d5-4256-bcfd-274bf7d08854	1	25	2009-09-21 22:24:42.95+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	5
5c85543a-aee1-4700-b2c1-c0d158735f14	1	25	2009-09-21 22:24:42.956+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	6
ce182fca-ad34-4fa4-9a33-f8fb9f64c9ee	1	25	2009-09-21 22:24:42.961+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	7
24f3b4b6-ff09-4a84-9b70-279513391b87	1	25	2009-09-21 22:24:42.967+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	8
9a9c42b6-7c76-4b52-9a26-8b3a9364860c	1	25	2009-09-21 22:24:42.973+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	9
2972ee72-cc71-40fb-a43a-70e0421e3dee	1	25	2009-09-21 22:24:42.978+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	10
f3def985-ca1c-4b78-8fd6-51f9818e7d33	1	25	2009-09-21 22:24:42.983+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	11
71c23f8d-785e-476a-9cfa-04622da1a49d	1	25	2009-09-21 22:24:42.989+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	12
f0249923-3520-4d19-a6ab-8bc3574a7709	1	26	2009-09-21 22:24:43.051+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	1
60a6a6d2-12eb-4897-9805-9c1584a2e9a1	1	27	2009-09-21 22:24:43.129+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	1
d7f97fd1-f884-4bdd-9e36-3825683f29ad	1	27	2009-09-21 22:24:43.14+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	2
4f8503bb-bece-4a8a-bbc9-baa18547a5d0	1	27	2009-09-21 22:24:43.149+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	3
49d8d0af-5365-4eaf-a5f0-09ce071096eb	1	27	2009-09-21 22:24:43.159+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	4
9902aee0-9c71-4d7f-8c1e-9137b06ec67e	1	27	2009-09-21 22:24:43.168+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	5
dcfe2077-1424-4456-86a6-2d0d178d99ea	1	27	2009-09-21 22:24:43.186+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	6
ccd29345-d196-4a44-8b70-ab54c7296671	1	27	2009-09-21 22:24:43.196+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	7
c8fb023e-66e3-4932-a4d0-9119f713cfe2	1	27	2009-09-21 22:24:43.204+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	8
18da1218-b1eb-465f-b779-f31a9d036386	1	27	2009-09-21 22:24:43.213+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	9
0951ad80-adb7-43fc-9a0f-91afacada505	1	27	2009-09-21 22:24:43.221+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	10
98c76db0-a730-4561-aa4f-d7d639cbd045	1	27	2009-09-21 22:24:43.23+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	11
356ba541-808d-4452-93b2-0041adfbe41f	1	27	2009-09-21 22:24:43.238+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	12
abbedfa9-6e7d-443e-a1c4-1b3a8cff4f1f	1	27	2009-09-21 22:24:43.246+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	13
9586bbbc-f7ba-44a1-9a1d-8adebb5f0f58	1	27	2009-09-21 22:24:43.258+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	14
20bab795-ac06-48cb-b74c-d45f23a5a2e2	1	27	2009-09-21 22:24:43.269+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	15
398fc3ff-dc3b-4d98-a2b7-293da4f2b98c	1	27	2009-09-21 22:24:43.278+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	16
0b9c2ea5-56d4-4e12-b98a-6a31fa7fda0a	1	27	2009-09-21 22:24:43.286+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	17
ab6a3dbf-2caf-42f6-9f6e-e82b052412b8	1	27	2009-09-21 22:24:43.296+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	18
08fcf0a7-e137-485e-a7c0-6e08dc066360	1	27	2009-09-21 22:24:43.305+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	19
07d22209-99ca-47af-b520-0c88ff59887a	1	27	2009-09-21 22:24:43.314+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	20
8119503b-8878-49d9-b9cd-50308f6ebbb5	1	27	2009-09-21 22:24:43.324+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	21
77282ae6-37ea-4b19-b091-44d58ac9634a	1	27	2009-09-21 22:24:43.335+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	22
04c21985-72ca-4038-ab81-cf359ac5980f	1	27	2009-09-21 22:24:43.346+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	23
a8a469c5-b078-4ce3-961d-7dabde7f898e	1	27	2009-09-21 22:24:43.357+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	24
8135005e-299c-42a3-937a-325d7b87c0d6	1	27	2009-09-21 22:24:43.367+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	25
513daf88-bb4e-4fb3-9d25-82eb73460d6f	1	27	2009-09-21 22:24:43.378+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	26
ffaacc20-13cb-4043-84aa-03beea569b01	1	27	2009-09-21 22:24:43.389+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	27
a807d527-81ce-49b5-960b-e1e717fb6479	1	27	2009-09-21 22:24:43.399+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	28
bf89dc95-69ba-4259-9310-ecaaf0687940	1	27	2009-09-21 22:24:43.409+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	29
78a74943-a2d9-42b6-ad12-ab89c102c8b1	1	27	2009-09-21 22:24:43.421+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	30
33c20e29-e01f-479d-ab17-4a5b5ec0379c	1	27	2009-09-21 22:24:43.431+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	31
2384a583-8d6b-4c07-91ab-e676e96db6a0	1	27	2009-09-21 22:24:43.442+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	32
f357a77a-d678-4526-823a-37c79a48e7da	1	27	2009-09-21 22:24:43.453+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	33
640caa25-90e8-4214-8896-991dccad076f	1	27	2009-09-21 22:24:43.464+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	34
a7044b17-2e8e-4732-96d5-4ec6aff7dfc1	1	27	2009-09-21 22:24:43.477+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	35
b7a43737-3bea-4f7b-8c1b-80c10d61d00d	1	27	2009-09-21 22:24:43.489+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	36
5b383034-2336-4a69-b947-e3a629538918	1	27	2009-09-21 22:24:43.50+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	37
098ea9f1-4d15-441b-b39c-14c09a2b589c	1	27	2009-09-21 22:24:43.51+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	38
217e81d1-7b58-4b61-be5c-cfaf2b19a5f5	1	27	2009-09-21 22:24:43.522+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	39
a6b6afa6-e13f-484c-9070-470e6e3763bf	1	27	2009-09-21 22:24:43.533+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	40
6fff1d10-7644-42c7-90a5-bf461556fb78	1	27	2009-09-21 22:24:43.544+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	41
dab68ef6-21bc-4a18-85ce-985cc787dfe4	1	27	2009-09-21 22:24:43.555+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	42
7331117d-a076-4ebc-8ebd-252430906bd6	1	27	2009-09-21 22:24:43.568+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	43
5f05f88c-f83d-403e-a880-b555e1eb9be8	1	27	2009-09-21 22:24:43.579+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	44
71b52726-c4e7-4e36-bcbc-ebb18fcf4e03	1	27	2009-09-21 22:24:43.591+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	45
73c639c7-e863-4165-a00b-bbdedccc54f1	1	27	2009-09-21 22:24:43.603+03	ef1b906f-f3c4-4d23-b985-3166dd9f7021	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	46
41092507-d7ac-4ddb-ad98-5d06e141247f	1	32	2009-09-23 13:19:59.783+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	1
07a8c30b-5884-4b4d-be19-3fd099badf6c	1	18	2009-09-23 09:22:11.222+03	77aeed6d-abb5-4878-a4d2-7b3693c33502	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	3
4e8f18ac-a329-427c-a137-a9e463bfeb51	1	17	2009-09-23 09:22:11.252+03	77aeed6d-abb5-4878-a4d2-7b3693c33502	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	dec13d93-c399-4df5-82f2-2a07a2d605b0	\N	\N	\N	\N	\N	\N	\N	\N	1
dec13d93-c399-4df5-82f2-2a07a2d605b0	2	15	2009-09-23 09:22:11.099+03	77aeed6d-abb5-4878-a4d2-7b3693c33502	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	8d550848-9260-41a7-aefd-8969d4179b13	\N	\N	\N	\N	\N	\N	\N	\N	2
a0eb4cbe-6001-480a-a506-8ae6b38e4b52	1	19	2009-09-23 09:22:11.291+03	77aeed6d-abb5-4878-a4d2-7b3693c33502	d5833cc1-d3c2-4acf-9b61-a12da1190a7f	f	f	f	f	f	4e8f18ac-a329-427c-a137-a9e463bfeb51	\N	\N	\N	\N	\N	\N	\N	\N	1
70592cfd-348d-4255-a227-ab989eb38f25	1	15	2009-09-23 13:19:55.714+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	1
f0a77298-ccc2-4768-b59d-374ec43d86f6	1	17	2009-09-23 13:19:55.766+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	1
5ca3e3c1-aed8-4d3f-9939-cebc2c9502db	1	18	2009-09-23 13:19:55.829+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	1
6886364f-bedb-4b6c-ae11-d10d74802fa2	1	19	2009-09-23 13:19:55.84+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	f0a77298-ccc2-4768-b59d-374ec43d86f6	\N	\N	\N	\N	\N	\N	\N	\N	1
06b85f35-1b60-436d-ac5e-9e1cfd961ca6	1	20	2009-09-23 13:19:55.852+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	f0a77298-ccc2-4768-b59d-374ec43d86f6	\N	\N	\N	\N	\N	\N	\N	\N	1
0a92f564-c4d6-4546-bace-f9f4040e6d6f	1	21	2009-09-23 13:19:55.868+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6886364f-bedb-4b6c-ae11-d10d74802fa2	\N	\N	\N	\N	\N	\N	\N	\N	1
94df300b-4003-4991-9871-d051054c13f4	1	20	2009-09-23 13:19:55.877+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	f0a77298-ccc2-4768-b59d-374ec43d86f6	\N	\N	\N	\N	\N	\N	\N	\N	2
b1870895-e112-47c0-80a0-3fc6bc12f838	1	21	2009-09-23 13:19:55.929+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6886364f-bedb-4b6c-ae11-d10d74802fa2	\N	\N	\N	\N	\N	\N	\N	\N	2
c803c3e6-851a-4d84-bca7-40b875c4d691	1	20	2009-09-23 13:19:55.939+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	f0a77298-ccc2-4768-b59d-374ec43d86f6	\N	\N	\N	\N	\N	\N	\N	\N	3
7ff87b5d-33ea-4a41-827b-e2c47f0962c7	1	21	2009-09-23 13:19:55.963+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6886364f-bedb-4b6c-ae11-d10d74802fa2	\N	\N	\N	\N	\N	\N	\N	\N	3
a5f4c5b5-d891-43a6-a4bd-14678b5a9167	1	23	2009-09-23 13:19:55.986+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	1
fb469617-189b-4de4-8b9a-ae91e4db1dbb	1	28	2009-09-23 13:19:55.996+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	1
1cf2f4eb-3228-4061-9e0b-6005b725da2f	1	28	2009-09-23 13:19:56.012+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	2
8f1a3415-d52a-43c3-9346-95cc59572437	1	24	2009-09-23 13:19:56.062+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	14
a6cd79ea-c7dd-49c9-8e05-0253be55ef80	1	30	2009-09-23 13:19:56.076+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	1
c5db715d-4051-4632-b47d-dc606f70b401	1	31	2009-09-23 13:19:56.091+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8f1a3415-d52a-43c3-9346-95cc59572437	\N	\N	\N	\N	\N	\N	\N	\N	1
bd2dd202-1f69-4cbf-a5bf-413fc00a324c	1	32	2009-09-23 13:19:59.827+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	2
f6420101-5b33-4117-b974-931ed3f81335	1	32	2009-09-23 13:19:59.847+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	3
6e37263c-d419-41b5-a966-206f2110bcfd	1	32	2009-09-23 13:19:59.857+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	4
298377f5-ef83-47d3-b6cb-fa23ba474566	1	32	2009-09-23 13:19:59.866+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	5
930ccd6c-9265-4fbc-808b-be17bd723c90	1	32	2009-09-23 13:19:59.875+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	6
c368c079-21cd-4108-a1b6-f0a6632de415	1	32	2009-09-23 13:19:59.885+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	7
9df268fb-3b14-4e0a-a2ba-babe6a6f31cc	1	32	2009-09-23 13:19:59.895+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	8
3416f1fb-6e3a-46d3-886b-183670e66813	1	32	2009-09-23 13:19:59.905+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	9
394a6fc7-777d-4cd4-969a-2eed0ba069c9	1	32	2009-09-23 13:19:59.915+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	10
192ddbfc-f441-4d50-a388-d8bd38bc1386	1	32	2009-09-23 13:19:59.925+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	11
6d56942d-852e-4778-8250-445278d6c6db	1	32	2009-09-23 13:19:59.935+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	12
66be9d98-f75b-4b97-92a1-02dc5b041a99	1	33	2009-09-23 13:19:59.952+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a6cd79ea-c7dd-49c9-8e05-0253be55ef80	\N	\N	\N	\N	\N	\N	\N	\N	1
7cae0d71-5b19-4e84-a00e-d82bdc8bc748	1	34	2009-09-23 13:19:59.967+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	66be9d98-f75b-4b97-92a1-02dc5b041a99	\N	\N	\N	\N	\N	\N	\N	\N	1
f4aa8bca-b267-4593-b55b-67e058ee857c	1	34	2009-09-23 13:19:59.98+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	66be9d98-f75b-4b97-92a1-02dc5b041a99	\N	\N	\N	\N	\N	\N	\N	\N	2
1dae5896-38e0-4aeb-8b83-291017dfd73e	1	34	2009-09-23 13:19:59.994+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	66be9d98-f75b-4b97-92a1-02dc5b041a99	\N	\N	\N	\N	\N	\N	\N	\N	3
a986c5b2-51d5-4871-9763-8b38883c5c26	1	34	2009-09-23 13:20:00.005+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	66be9d98-f75b-4b97-92a1-02dc5b041a99	\N	\N	\N	\N	\N	\N	\N	\N	4
d59a6b2c-014b-4463-9e4e-180436b57861	1	34	2009-09-23 13:20:00.017+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	66be9d98-f75b-4b97-92a1-02dc5b041a99	\N	\N	\N	\N	\N	\N	\N	\N	5
284b2c3e-9ed2-4409-b7e1-95ba8d5663b2	1	34	2009-09-23 13:20:00.027+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	66be9d98-f75b-4b97-92a1-02dc5b041a99	\N	\N	\N	\N	\N	\N	\N	\N	6
e76fbec2-533f-4892-95e0-59ec1065eec8	1	34	2009-09-23 13:20:00.037+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	66be9d98-f75b-4b97-92a1-02dc5b041a99	\N	\N	\N	\N	\N	\N	\N	\N	7
0e982d0c-7b68-41d3-b5c2-37bdc59a040a	1	34	2009-09-23 13:20:00.048+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	66be9d98-f75b-4b97-92a1-02dc5b041a99	\N	\N	\N	\N	\N	\N	\N	\N	8
5ba9abff-9de7-4d62-86c4-d5f7e48f111b	1	34	2009-09-23 13:20:00.058+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	66be9d98-f75b-4b97-92a1-02dc5b041a99	\N	\N	\N	\N	\N	\N	\N	\N	9
288ff2fa-3c4d-4221-bd1e-04220b35eb4d	1	33	2009-09-23 13:20:00.069+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a6cd79ea-c7dd-49c9-8e05-0253be55ef80	\N	\N	\N	\N	\N	\N	\N	\N	2
ef101e31-5404-4691-bbd5-df1ea7871a80	1	34	2009-09-23 13:20:00.082+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	288ff2fa-3c4d-4221-bd1e-04220b35eb4d	\N	\N	\N	\N	\N	\N	\N	\N	1
b9ee5da6-0cab-44c6-9614-7ece324da335	1	34	2009-09-23 13:20:00.092+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	288ff2fa-3c4d-4221-bd1e-04220b35eb4d	\N	\N	\N	\N	\N	\N	\N	\N	2
bb5d0874-a2af-4014-99ce-0ca7d5b924b4	1	34	2009-09-23 13:20:00.102+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	288ff2fa-3c4d-4221-bd1e-04220b35eb4d	\N	\N	\N	\N	\N	\N	\N	\N	3
05648b08-02d2-4f8d-9860-dc018b48689a	1	34	2009-09-23 13:20:00.113+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	288ff2fa-3c4d-4221-bd1e-04220b35eb4d	\N	\N	\N	\N	\N	\N	\N	\N	4
893116db-399c-4488-9868-59c4b719bb22	1	34	2009-09-23 13:20:00.124+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	288ff2fa-3c4d-4221-bd1e-04220b35eb4d	\N	\N	\N	\N	\N	\N	\N	\N	5
935251ea-c5a0-4913-9e6d-0c9e4adcdb5c	1	34	2009-09-23 13:20:00.134+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	288ff2fa-3c4d-4221-bd1e-04220b35eb4d	\N	\N	\N	\N	\N	\N	\N	\N	6
3c4341d9-b5e4-4398-9a58-4f4dd31a2d97	1	34	2009-09-23 13:20:00.145+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	288ff2fa-3c4d-4221-bd1e-04220b35eb4d	\N	\N	\N	\N	\N	\N	\N	\N	7
4ee15ce9-19aa-4016-a1b9-6140890d65d6	1	34	2009-09-23 13:20:00.156+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	288ff2fa-3c4d-4221-bd1e-04220b35eb4d	\N	\N	\N	\N	\N	\N	\N	\N	8
233275a4-2d3a-4a57-b78e-4db2de36d49d	1	34	2009-09-23 13:20:00.167+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	288ff2fa-3c4d-4221-bd1e-04220b35eb4d	\N	\N	\N	\N	\N	\N	\N	\N	9
84e7bb8a-364d-4d7a-be16-a6226b8988b1	1	33	2009-09-23 13:20:00.177+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a6cd79ea-c7dd-49c9-8e05-0253be55ef80	\N	\N	\N	\N	\N	\N	\N	\N	3
36c8e348-ef4d-4772-9ca2-b613f28ad51a	1	34	2009-09-23 13:20:00.19+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	84e7bb8a-364d-4d7a-be16-a6226b8988b1	\N	\N	\N	\N	\N	\N	\N	\N	1
c2ce0a9f-01bb-4470-ac3e-c925f5683f52	1	34	2009-09-23 13:20:00.201+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	84e7bb8a-364d-4d7a-be16-a6226b8988b1	\N	\N	\N	\N	\N	\N	\N	\N	2
f158d28e-ab42-4ab1-8be6-7716e4349e19	1	34	2009-09-23 13:20:00.212+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	84e7bb8a-364d-4d7a-be16-a6226b8988b1	\N	\N	\N	\N	\N	\N	\N	\N	3
0385cc79-29ef-42c0-8e8e-7c8e7b84c16f	1	34	2009-09-23 13:20:00.223+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	84e7bb8a-364d-4d7a-be16-a6226b8988b1	\N	\N	\N	\N	\N	\N	\N	\N	4
0018075d-6f33-409a-b32e-1511fe9cf6cc	1	34	2009-09-23 13:20:00.235+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	84e7bb8a-364d-4d7a-be16-a6226b8988b1	\N	\N	\N	\N	\N	\N	\N	\N	5
6c097e9d-39ab-41e1-8933-fe8a4bfdd03e	1	34	2009-09-23 13:20:00.246+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	84e7bb8a-364d-4d7a-be16-a6226b8988b1	\N	\N	\N	\N	\N	\N	\N	\N	6
6cb39c15-1b56-46f4-b9b4-be0534d84696	1	34	2009-09-23 13:20:00.257+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	84e7bb8a-364d-4d7a-be16-a6226b8988b1	\N	\N	\N	\N	\N	\N	\N	\N	7
74b13355-e434-4d7b-be78-d4f6f2c2661c	1	34	2009-09-23 13:20:00.271+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	84e7bb8a-364d-4d7a-be16-a6226b8988b1	\N	\N	\N	\N	\N	\N	\N	\N	8
1f45766d-cfe2-42f9-a800-d485f5f0ca70	1	34	2009-09-23 13:20:00.283+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	84e7bb8a-364d-4d7a-be16-a6226b8988b1	\N	\N	\N	\N	\N	\N	\N	\N	9
e5232e44-0eaf-42c5-aed0-e5f61a3b8076	1	33	2009-09-23 13:20:00.297+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a6cd79ea-c7dd-49c9-8e05-0253be55ef80	\N	\N	\N	\N	\N	\N	\N	\N	4
1fe9f999-6c6e-44da-af06-cdeb798f0d6d	1	34	2009-09-23 13:20:00.313+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e5232e44-0eaf-42c5-aed0-e5f61a3b8076	\N	\N	\N	\N	\N	\N	\N	\N	1
7153f9e0-a5d6-40af-b252-b953625e5bb1	1	34	2009-09-23 13:20:00.324+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e5232e44-0eaf-42c5-aed0-e5f61a3b8076	\N	\N	\N	\N	\N	\N	\N	\N	2
05360f49-2ded-45a2-a9a6-7d14eaf0a027	1	34	2009-09-23 13:20:00.336+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e5232e44-0eaf-42c5-aed0-e5f61a3b8076	\N	\N	\N	\N	\N	\N	\N	\N	3
9c8e6c7e-27e0-4806-810b-f4191e910ea8	1	34	2009-09-23 13:20:00.347+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e5232e44-0eaf-42c5-aed0-e5f61a3b8076	\N	\N	\N	\N	\N	\N	\N	\N	4
1cfbc2fe-eeb6-47ff-8250-c3acad5ab68c	1	34	2009-09-23 13:20:00.358+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e5232e44-0eaf-42c5-aed0-e5f61a3b8076	\N	\N	\N	\N	\N	\N	\N	\N	5
e1796c24-792f-4885-8eea-eb2219852b15	1	34	2009-09-23 13:20:00.369+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e5232e44-0eaf-42c5-aed0-e5f61a3b8076	\N	\N	\N	\N	\N	\N	\N	\N	6
0c871690-046b-44d7-8277-6319e2a26438	1	34	2009-09-23 13:20:00.381+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e5232e44-0eaf-42c5-aed0-e5f61a3b8076	\N	\N	\N	\N	\N	\N	\N	\N	7
5aa52f1e-58be-4e92-b490-503fe3349a85	1	34	2009-09-23 13:20:00.392+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e5232e44-0eaf-42c5-aed0-e5f61a3b8076	\N	\N	\N	\N	\N	\N	\N	\N	8
01a5de0e-ba78-4d94-b9e5-8999abc931c5	1	34	2009-09-23 13:20:00.403+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	e5232e44-0eaf-42c5-aed0-e5f61a3b8076	\N	\N	\N	\N	\N	\N	\N	\N	9
2a756228-0fc3-4e5d-b22e-e19d29d836f3	1	33	2009-09-23 13:20:00.415+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a6cd79ea-c7dd-49c9-8e05-0253be55ef80	\N	\N	\N	\N	\N	\N	\N	\N	5
0e592d96-1bc1-43e2-8f03-56aae4927897	1	34	2009-09-23 13:20:00.431+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2a756228-0fc3-4e5d-b22e-e19d29d836f3	\N	\N	\N	\N	\N	\N	\N	\N	1
253f8554-4b47-4b52-8b29-bf111659afa4	1	34	2009-09-23 13:20:00.444+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2a756228-0fc3-4e5d-b22e-e19d29d836f3	\N	\N	\N	\N	\N	\N	\N	\N	2
b946762d-ec8a-412f-b0c7-d6c48f344e16	1	34	2009-09-23 13:20:00.457+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2a756228-0fc3-4e5d-b22e-e19d29d836f3	\N	\N	\N	\N	\N	\N	\N	\N	3
4897f620-9517-4b96-80a2-d7bd5b018899	1	34	2009-09-23 13:20:00.47+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2a756228-0fc3-4e5d-b22e-e19d29d836f3	\N	\N	\N	\N	\N	\N	\N	\N	4
5adec947-c394-4334-96df-35774469b394	1	34	2009-09-23 13:20:00.483+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2a756228-0fc3-4e5d-b22e-e19d29d836f3	\N	\N	\N	\N	\N	\N	\N	\N	5
e605c113-8a16-4572-a8f5-3783ea1bdfa2	1	34	2009-09-23 13:20:00.504+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2a756228-0fc3-4e5d-b22e-e19d29d836f3	\N	\N	\N	\N	\N	\N	\N	\N	6
15d41c7c-3758-4a60-a3d3-0d5f8dab4004	1	34	2009-09-23 13:20:00.516+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2a756228-0fc3-4e5d-b22e-e19d29d836f3	\N	\N	\N	\N	\N	\N	\N	\N	7
b08fb0b0-37d0-4b9b-80b9-9bbcd82f0d71	1	34	2009-09-23 13:20:00.53+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2a756228-0fc3-4e5d-b22e-e19d29d836f3	\N	\N	\N	\N	\N	\N	\N	\N	8
39311768-86b0-4097-a174-604dbc96152f	1	34	2009-09-23 13:20:00.543+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	2a756228-0fc3-4e5d-b22e-e19d29d836f3	\N	\N	\N	\N	\N	\N	\N	\N	9
a824962b-be6e-4b1e-8a37-2004e6ec7637	1	33	2009-09-23 13:20:00.556+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a6cd79ea-c7dd-49c9-8e05-0253be55ef80	\N	\N	\N	\N	\N	\N	\N	\N	6
7963ac02-a347-4320-952b-91109a5afd4c	1	34	2009-09-23 13:20:00.572+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a824962b-be6e-4b1e-8a37-2004e6ec7637	\N	\N	\N	\N	\N	\N	\N	\N	1
e89c8411-53db-4550-8af5-c8ee55829942	1	34	2009-09-23 13:20:00.584+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a824962b-be6e-4b1e-8a37-2004e6ec7637	\N	\N	\N	\N	\N	\N	\N	\N	2
203f0b59-0987-4246-ae1a-d4eded165482	1	34	2009-09-23 13:20:00.596+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a824962b-be6e-4b1e-8a37-2004e6ec7637	\N	\N	\N	\N	\N	\N	\N	\N	3
5403ffa2-557c-490f-ba33-79b8d3006d4b	1	34	2009-09-23 13:20:00.608+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a824962b-be6e-4b1e-8a37-2004e6ec7637	\N	\N	\N	\N	\N	\N	\N	\N	4
6a4e35d5-de98-4796-b415-2399eb705571	1	34	2009-09-23 13:20:00.621+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a824962b-be6e-4b1e-8a37-2004e6ec7637	\N	\N	\N	\N	\N	\N	\N	\N	5
3868e772-4873-40c1-bdb3-90997d4d386e	1	34	2009-09-23 13:20:00.634+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a824962b-be6e-4b1e-8a37-2004e6ec7637	\N	\N	\N	\N	\N	\N	\N	\N	6
8d37bbfe-a29d-4680-87f8-542cbd5a8292	1	34	2009-09-23 13:20:00.646+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a824962b-be6e-4b1e-8a37-2004e6ec7637	\N	\N	\N	\N	\N	\N	\N	\N	7
46063ad1-a172-4b21-a3f2-7ade1b204f83	1	34	2009-09-23 13:20:00.659+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a824962b-be6e-4b1e-8a37-2004e6ec7637	\N	\N	\N	\N	\N	\N	\N	\N	8
f8f4fc2b-6d66-4dc5-b60d-0b3dc3dd2acc	1	34	2009-09-23 13:20:00.672+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a824962b-be6e-4b1e-8a37-2004e6ec7637	\N	\N	\N	\N	\N	\N	\N	\N	9
34e6f8f6-d3aa-45b0-b938-0f700f621e9e	1	33	2009-09-23 13:20:00.683+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a6cd79ea-c7dd-49c9-8e05-0253be55ef80	\N	\N	\N	\N	\N	\N	\N	\N	7
633d0930-d0fa-4b21-a9fb-ef728d9d3f9f	1	34	2009-09-23 13:20:00.699+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	34e6f8f6-d3aa-45b0-b938-0f700f621e9e	\N	\N	\N	\N	\N	\N	\N	\N	1
05ead630-7ce5-4638-95c9-dac4d9dc043a	1	34	2009-09-23 13:20:00.712+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	34e6f8f6-d3aa-45b0-b938-0f700f621e9e	\N	\N	\N	\N	\N	\N	\N	\N	2
d52970e3-479a-4a70-a042-988ab5e98c5f	1	34	2009-09-23 13:20:00.724+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	34e6f8f6-d3aa-45b0-b938-0f700f621e9e	\N	\N	\N	\N	\N	\N	\N	\N	3
5808cd5c-37f4-4c05-8d3e-32eaf88c4db0	1	34	2009-09-23 13:20:00.736+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	34e6f8f6-d3aa-45b0-b938-0f700f621e9e	\N	\N	\N	\N	\N	\N	\N	\N	4
51cf3a4e-0b71-4d68-8c0f-0b00ba661da0	1	34	2009-09-23 13:20:00.749+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	34e6f8f6-d3aa-45b0-b938-0f700f621e9e	\N	\N	\N	\N	\N	\N	\N	\N	5
b5f9073e-47e4-4885-8238-89dd478728ed	1	34	2009-09-23 13:20:00.762+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	34e6f8f6-d3aa-45b0-b938-0f700f621e9e	\N	\N	\N	\N	\N	\N	\N	\N	6
56ced3e7-b891-4ce0-aa15-7bd0a0105bf9	1	34	2009-09-23 13:20:00.775+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	34e6f8f6-d3aa-45b0-b938-0f700f621e9e	\N	\N	\N	\N	\N	\N	\N	\N	7
52da57dc-fefa-43de-b050-ec14240bf7b1	1	34	2009-09-23 13:20:00.787+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	34e6f8f6-d3aa-45b0-b938-0f700f621e9e	\N	\N	\N	\N	\N	\N	\N	\N	8
9c4dbd0e-e5b3-4821-8b3b-f983ae463fc9	1	34	2009-09-23 13:20:00.80+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	34e6f8f6-d3aa-45b0-b938-0f700f621e9e	\N	\N	\N	\N	\N	\N	\N	\N	9
79044e97-09e0-48e6-b3dc-a3ba66f80fab	1	33	2009-09-23 13:20:00.813+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a6cd79ea-c7dd-49c9-8e05-0253be55ef80	\N	\N	\N	\N	\N	\N	\N	\N	8
fdafddf0-0f2a-4595-81eb-0ddd60d5d8e4	1	34	2009-09-23 13:20:00.831+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	79044e97-09e0-48e6-b3dc-a3ba66f80fab	\N	\N	\N	\N	\N	\N	\N	\N	1
af772c76-a173-4ad9-b4b2-49a858271453	1	34	2009-09-23 13:20:00.844+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	79044e97-09e0-48e6-b3dc-a3ba66f80fab	\N	\N	\N	\N	\N	\N	\N	\N	2
56aff107-8c76-461d-a218-6b8100386b5c	1	34	2009-09-23 13:20:00.858+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	79044e97-09e0-48e6-b3dc-a3ba66f80fab	\N	\N	\N	\N	\N	\N	\N	\N	3
84b4a596-8ecc-4b64-8d5f-fc947fadb654	1	34	2009-09-23 13:20:00.871+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	79044e97-09e0-48e6-b3dc-a3ba66f80fab	\N	\N	\N	\N	\N	\N	\N	\N	4
6b9606e6-5c35-4642-8bcd-cf48c74659d3	1	34	2009-09-23 13:20:00.884+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	79044e97-09e0-48e6-b3dc-a3ba66f80fab	\N	\N	\N	\N	\N	\N	\N	\N	5
8376f8a5-8441-45eb-836b-38e7e08d7322	1	34	2009-09-23 13:20:00.897+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	79044e97-09e0-48e6-b3dc-a3ba66f80fab	\N	\N	\N	\N	\N	\N	\N	\N	6
18c4ec42-4ecf-423d-9e67-53f9fdacc1f7	1	34	2009-09-23 13:20:00.91+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	79044e97-09e0-48e6-b3dc-a3ba66f80fab	\N	\N	\N	\N	\N	\N	\N	\N	7
b351ac6d-79b2-45ad-be68-3ae714c8caf3	1	34	2009-09-23 13:20:00.923+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	79044e97-09e0-48e6-b3dc-a3ba66f80fab	\N	\N	\N	\N	\N	\N	\N	\N	8
1444b3d3-ddb4-49d2-bf52-2473ef71c975	1	34	2009-09-23 13:20:00.935+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	79044e97-09e0-48e6-b3dc-a3ba66f80fab	\N	\N	\N	\N	\N	\N	\N	\N	9
4a601209-9476-432a-b5d5-ce3ae3efdd88	1	33	2009-09-23 13:20:00.948+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a6cd79ea-c7dd-49c9-8e05-0253be55ef80	\N	\N	\N	\N	\N	\N	\N	\N	9
7c02e2fc-e03e-49e6-a76c-b6fc113a1b10	1	34	2009-09-23 13:20:00.966+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	4a601209-9476-432a-b5d5-ce3ae3efdd88	\N	\N	\N	\N	\N	\N	\N	\N	1
2bd920ae-482b-4baa-a56e-e6c46c5e8d5f	1	34	2009-09-23 13:20:00.979+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	4a601209-9476-432a-b5d5-ce3ae3efdd88	\N	\N	\N	\N	\N	\N	\N	\N	2
9f2187ea-2ddf-4dc2-8e17-9d8885c209e3	1	34	2009-09-23 13:20:00.993+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	4a601209-9476-432a-b5d5-ce3ae3efdd88	\N	\N	\N	\N	\N	\N	\N	\N	3
34b8de0c-db91-4386-9b4d-de7ec8d743d0	1	34	2009-09-23 13:20:01.006+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	4a601209-9476-432a-b5d5-ce3ae3efdd88	\N	\N	\N	\N	\N	\N	\N	\N	4
238de077-b379-429f-85a7-17135065447c	1	34	2009-09-23 13:20:01.019+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	4a601209-9476-432a-b5d5-ce3ae3efdd88	\N	\N	\N	\N	\N	\N	\N	\N	5
f87986e9-c375-4319-b441-05d75a05179b	1	34	2009-09-23 13:20:01.032+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	4a601209-9476-432a-b5d5-ce3ae3efdd88	\N	\N	\N	\N	\N	\N	\N	\N	6
c8c66ebc-87ab-4e81-89f3-a6b789cfd76e	1	34	2009-09-23 13:20:01.045+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	4a601209-9476-432a-b5d5-ce3ae3efdd88	\N	\N	\N	\N	\N	\N	\N	\N	7
3664a841-9673-4f91-9350-009375bed18f	1	34	2009-09-23 13:20:01.059+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	4a601209-9476-432a-b5d5-ce3ae3efdd88	\N	\N	\N	\N	\N	\N	\N	\N	8
2f6a8a6d-ac8e-4c92-8612-21190d15db83	1	34	2009-09-23 13:20:01.072+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	4a601209-9476-432a-b5d5-ce3ae3efdd88	\N	\N	\N	\N	\N	\N	\N	\N	9
a8d420e6-b09e-4850-8730-c19d82f76a63	1	33	2009-09-23 13:20:01.087+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a6cd79ea-c7dd-49c9-8e05-0253be55ef80	\N	\N	\N	\N	\N	\N	\N	\N	10
44ca685e-60a1-4bcc-bdb7-1c00a571b841	1	34	2009-09-23 13:20:01.105+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a8d420e6-b09e-4850-8730-c19d82f76a63	\N	\N	\N	\N	\N	\N	\N	\N	1
fe1d8f00-1787-46af-92d2-fe6f9f06e1fd	1	34	2009-09-23 13:20:01.119+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a8d420e6-b09e-4850-8730-c19d82f76a63	\N	\N	\N	\N	\N	\N	\N	\N	2
e7df388a-39cd-4a74-a5e4-49b416c13dbe	1	34	2009-09-23 13:20:01.133+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a8d420e6-b09e-4850-8730-c19d82f76a63	\N	\N	\N	\N	\N	\N	\N	\N	3
aa56b728-56a5-4428-8409-5096f703d81b	1	34	2009-09-23 13:20:01.147+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a8d420e6-b09e-4850-8730-c19d82f76a63	\N	\N	\N	\N	\N	\N	\N	\N	4
9eab2f3d-2b5e-4825-9432-af0e7a07cf9f	1	34	2009-09-23 13:20:01.16+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a8d420e6-b09e-4850-8730-c19d82f76a63	\N	\N	\N	\N	\N	\N	\N	\N	5
f2e311d0-d783-4c81-bf90-305c30f1a4f6	1	34	2009-09-23 13:20:01.183+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a8d420e6-b09e-4850-8730-c19d82f76a63	\N	\N	\N	\N	\N	\N	\N	\N	6
00e5d8e3-2065-42ad-b47c-aae5f04c7fd6	1	34	2009-09-23 13:20:01.196+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a8d420e6-b09e-4850-8730-c19d82f76a63	\N	\N	\N	\N	\N	\N	\N	\N	7
57fe28c0-4206-4631-bc82-6b89fc05a7ba	1	34	2009-09-23 13:20:01.211+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a8d420e6-b09e-4850-8730-c19d82f76a63	\N	\N	\N	\N	\N	\N	\N	\N	8
38cc8853-c2b4-41a8-b94e-d30782ab40b0	1	34	2009-09-23 13:20:01.225+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a8d420e6-b09e-4850-8730-c19d82f76a63	\N	\N	\N	\N	\N	\N	\N	\N	9
fbde6d86-ad98-41d6-9c5b-1684ea76f6b9	1	33	2009-09-23 13:20:01.239+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a6cd79ea-c7dd-49c9-8e05-0253be55ef80	\N	\N	\N	\N	\N	\N	\N	\N	11
474920f8-f955-4275-85b7-1879c4379824	1	34	2009-09-23 13:20:01.258+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	fbde6d86-ad98-41d6-9c5b-1684ea76f6b9	\N	\N	\N	\N	\N	\N	\N	\N	1
414f4c00-4b39-476e-92d1-7b0b4414c822	1	34	2009-09-23 13:20:01.304+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	fbde6d86-ad98-41d6-9c5b-1684ea76f6b9	\N	\N	\N	\N	\N	\N	\N	\N	2
ce672a8b-9e7d-4f99-865b-ad0494b85a4f	1	34	2009-09-23 13:20:01.318+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	fbde6d86-ad98-41d6-9c5b-1684ea76f6b9	\N	\N	\N	\N	\N	\N	\N	\N	3
cc8fc8bf-5a06-4f1c-9702-27650f3d92d3	1	34	2009-09-23 13:20:01.332+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	fbde6d86-ad98-41d6-9c5b-1684ea76f6b9	\N	\N	\N	\N	\N	\N	\N	\N	4
74c4c616-bef2-4e58-af9f-0a007614dedd	1	34	2009-09-23 13:20:01.346+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	fbde6d86-ad98-41d6-9c5b-1684ea76f6b9	\N	\N	\N	\N	\N	\N	\N	\N	5
62e10c3e-49cf-4534-92b9-a96a9b980c47	1	34	2009-09-23 13:20:01.379+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	fbde6d86-ad98-41d6-9c5b-1684ea76f6b9	\N	\N	\N	\N	\N	\N	\N	\N	6
dac8728b-d9cf-43d6-b47a-505eedde1090	1	34	2009-09-23 13:20:01.394+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	fbde6d86-ad98-41d6-9c5b-1684ea76f6b9	\N	\N	\N	\N	\N	\N	\N	\N	7
32c63521-ba23-4c3f-bd06-59deaf3c038d	1	34	2009-09-23 13:20:01.408+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	fbde6d86-ad98-41d6-9c5b-1684ea76f6b9	\N	\N	\N	\N	\N	\N	\N	\N	8
692131d5-6152-41a6-98fe-97eed9498f48	1	34	2009-09-23 13:20:01.423+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	fbde6d86-ad98-41d6-9c5b-1684ea76f6b9	\N	\N	\N	\N	\N	\N	\N	\N	9
df314b70-72cb-426b-82e2-ab25ddf83bd2	1	18	2009-09-23 13:21:51.805+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	2
9420925b-d427-4779-9980-43f88f4afa8b	1	23	2009-09-23 13:21:51.813+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	2
20a04a1a-d205-45c8-bf48-fa5ce2a869e5	1	24	2009-09-23 13:21:51.919+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	15
8899b1d7-4571-435b-b7f9-c4d7ec7e58c2	1	25	2009-09-23 13:27:59.952+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	1
9356a4a3-a7f8-4ae6-bbc3-2caf930d8b3a	1	25	2009-09-23 13:27:59.959+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	2
d2a2d9fc-8d14-4e79-8c9f-afd98e51e654	1	25	2009-09-23 13:27:59.999+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	3
4308f5ab-3e57-4bad-b060-ffec2f4b50b0	1	25	2009-09-23 13:28:00.004+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	4
1c785a8f-6fd9-4296-85b9-7373e2d69173	1	25	2009-09-23 13:28:00.01+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	5
7aedafb7-139d-45ba-a89b-8f2c4a751d79	1	25	2009-09-23 13:28:00.015+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	6
82085ab9-1fd9-4348-b803-8ad1cd2e0fe6	1	25	2009-09-23 13:28:00.021+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	7
111aece2-08c1-48c4-8e42-133bf9314949	1	25	2009-09-23 13:28:00.026+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	8
3857cf93-f511-4273-9b29-db1449e08ed2	1	25	2009-09-23 13:28:00.032+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	9
c406d8ea-a3ef-4ffa-92b5-8a091af7a0d7	1	25	2009-09-23 13:28:00.037+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	10
55477523-8d85-4db5-90de-71e601daa140	1	25	2009-09-23 13:28:00.043+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	11
64c0e9c8-1c44-4bc6-ab83-c7d0694f6e82	1	25	2009-09-23 13:28:00.049+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	12
276e6701-a1f1-4fe0-a4aa-31edf6880c0f	1	26	2009-09-23 13:28:00.061+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	1
aecf5829-8b81-4790-b45c-6d6f9623b613	1	27	2009-09-23 13:28:00.071+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	1
661b42be-d7e5-4244-ad5f-27c63391a805	1	27	2009-09-23 13:28:00.096+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	2
0598833a-2f3d-481b-a9d1-0f6ed0870343	1	27	2009-09-23 13:28:00.132+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	3
150dcd1a-0606-4075-9038-2f51c763403c	1	27	2009-09-23 13:28:00.141+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	4
9e887f7b-f7ad-4ebf-a17e-1a3d85b3656b	1	27	2009-09-23 13:28:00.151+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	5
4442544c-7466-4995-9378-41e9ecc077f9	1	27	2009-09-23 13:28:00.162+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	6
f9beb75d-648e-455a-b8eb-176d95f6bf2d	1	27	2009-09-23 13:28:00.172+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	7
706c6f1c-9ceb-436b-b505-2883d1b15a4b	1	27	2009-09-23 13:28:00.182+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	8
f76d421c-cc84-407a-8bea-15c8ec4e9de9	1	27	2009-09-23 13:28:00.191+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	9
5189d0e5-8c83-40ae-9b75-ea6fc27d2f87	1	27	2009-09-23 13:28:00.20+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	10
d297ff91-3696-4a07-9dc0-074a48b358ba	1	27	2009-09-23 13:28:00.21+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	11
58c4f7cf-8956-4d37-b66e-f4168df53deb	1	27	2009-09-23 13:28:00.221+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	12
9943a6e2-fc1a-48c3-8ec3-cd49f7787774	1	27	2009-09-23 13:28:00.231+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	13
9da1f331-96ae-419d-af92-049c4cae2182	1	27	2009-09-23 13:28:00.268+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	14
4ef5bc2c-3257-4c77-8b2e-fa0f93202c8b	1	27	2009-09-23 13:28:00.278+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	15
887cd83b-0efd-474b-a98b-7f2143f48c7a	1	27	2009-09-23 13:28:00.289+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	16
b9e86a33-f6ad-40d7-90d9-411fb45d6552	1	27	2009-09-23 13:28:00.299+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	17
75536076-bdbf-4b32-899b-286dee175694	1	27	2009-09-23 13:28:00.31+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	18
fa1614d4-3d0b-4943-ad16-caa9af8f3111	1	27	2009-09-23 13:28:00.32+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	19
f07a4399-301a-4341-8c12-e7d1023e71a1	1	27	2009-09-23 13:28:00.331+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	20
977498d2-c16c-480a-9115-73a729753243	1	27	2009-09-23 13:28:00.341+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	21
d59e38f9-5f02-4748-b99a-e6eaa0c91ceb	1	27	2009-09-23 13:28:00.351+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	22
271a2239-4c26-440b-a867-3d055f83635b	1	27	2009-09-23 13:28:00.361+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	23
c851f9c2-708c-4d8a-b111-645d70d2d452	1	27	2009-09-23 13:28:00.372+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	24
d0c18b8f-ae6e-4a57-b1f6-a08b65b45246	1	27	2009-09-23 13:28:00.382+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	25
1f3d5329-f297-4256-804c-7ccbe58a7481	1	27	2009-09-23 13:28:00.392+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	26
cb007721-8dc0-4e6d-89a8-db9b6d94654e	1	27	2009-09-23 13:28:00.402+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	27
c9498203-1e96-454a-8c95-ce7fdf9ff2ea	1	27	2009-09-23 13:28:00.413+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	28
298f2bfc-9170-4590-8fd0-29161e587e0f	1	27	2009-09-23 13:28:00.424+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	29
5e5c3a6a-a4f3-4ce6-ba9f-552b0cdbdbad	1	27	2009-09-23 13:28:00.435+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	30
bdb63318-e9a2-4c87-beb0-58dfddbb911f	1	27	2009-09-23 13:28:00.446+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	31
597ffc09-c247-4375-b47d-6f273866c12a	1	27	2009-09-23 13:28:00.457+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	32
6fa2b4b5-e6bc-4ac9-96c7-3b8bf14a1a96	1	27	2009-09-23 13:28:00.468+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	33
c141ddfa-0237-4eae-ad0f-e5116808783d	1	27	2009-09-23 13:28:00.48+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	34
35a8b8b3-9018-4d79-83c7-131df52329ef	1	27	2009-09-23 13:28:00.492+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	35
0330f85d-51b7-4cca-98b9-e4897f7268d1	1	27	2009-09-23 13:28:00.502+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	36
138f3a67-b3ca-4f31-9d9c-2e0cd8e42c64	1	27	2009-09-23 13:28:00.52+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	37
4aff4065-88bb-4f70-b2d1-a4ef7f7bb607	1	27	2009-09-23 13:28:00.532+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	38
304083d2-54b3-44d5-939b-8c249beab052	1	27	2009-09-23 13:28:00.542+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	39
45b41112-01db-4dd8-9b5f-91823fa73e1a	1	27	2009-09-23 13:28:00.554+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	40
291db32d-8308-4118-9835-039905fdb781	1	27	2009-09-23 13:28:00.565+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	41
d7a4c85b-86ff-4458-8e51-60f669b8e53e	1	27	2009-09-23 13:28:00.576+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	42
d36fb3cb-f6ad-4633-bc37-6d5b693578f5	1	27	2009-09-23 13:28:00.587+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	43
94b12590-a430-4b07-9413-74de89b5c383	1	27	2009-09-23 13:28:00.598+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	44
fd26bccf-56bf-444f-99c6-2b224f7c9889	1	27	2009-09-23 13:28:00.61+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	45
4a425a2f-524a-43fe-a8e7-8cedae9ab8b3	1	27	2009-09-23 13:28:00.621+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	46
d8e93b72-5d27-475b-b92b-eb8eb5323682	1	30	2009-09-23 14:36:29.613+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	1
280179bd-b2f9-422e-9da6-b03a49d49094	1	28	2009-09-23 13:29:35.269+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	e816f01f-d95b-4f51-88b8-4c6409a6b7ae	\N	\N	\N	\N	\N	\N	\N	\N	1
e5628328-ac3e-48cf-b253-204322014d41	1	29	2009-09-23 13:29:35.28+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	280179bd-b2f9-422e-9da6-b03a49d49094	\N	\N	\N	\N	\N	\N	\N	\N	1
ce1d5ccf-cf18-4516-bd35-7cdbf97c90a4	1	18	2009-09-23 13:29:35.30+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	3
20035cba-13fe-4f9b-8259-9b87f981b49f	1	17	2009-09-23 13:29:35.35+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	e816f01f-d95b-4f51-88b8-4c6409a6b7ae	\N	\N	\N	\N	\N	\N	\N	\N	1
e816f01f-d95b-4f51-88b8-4c6409a6b7ae	2	15	2009-09-23 13:29:35.243+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	70592cfd-348d-4255-a227-ab989eb38f25	\N	\N	\N	\N	\N	\N	\N	\N	2
0e02fd3b-79ee-4026-b559-0f9804d6bebd	1	19	2009-09-23 13:29:35.359+03	9420925b-d427-4779-9980-43f88f4afa8b	f0a77298-ccc2-4768-b59d-374ec43d86f6	f	f	f	f	f	20035cba-13fe-4f9b-8259-9b87f981b49f	\N	\N	\N	\N	\N	\N	\N	\N	1
6b77867c-3bca-4fa9-9b75-540b4f0546cc	1	15	2009-09-23 14:36:28.871+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	1
59f011d1-a6bc-40d5-8735-a6c338971f6c	1	17	2009-09-23 14:36:29.393+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	1
ced27fbe-fb72-4918-8f56-9e41b6dec8cc	1	18	2009-09-23 14:36:29.433+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	1
dd7cf67d-21eb-44e1-b092-2c20666d427b	1	19	2009-09-23 14:36:29.443+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	59f011d1-a6bc-40d5-8735-a6c338971f6c	\N	\N	\N	\N	\N	\N	\N	\N	1
758b97eb-f92e-4375-9043-43f9dfa7d966	1	20	2009-09-23 14:36:29.454+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	59f011d1-a6bc-40d5-8735-a6c338971f6c	\N	\N	\N	\N	\N	\N	\N	\N	1
27917c66-59d4-4713-b47c-688baf702c7d	1	21	2009-09-23 14:36:29.469+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	dd7cf67d-21eb-44e1-b092-2c20666d427b	\N	\N	\N	\N	\N	\N	\N	\N	1
55fc0001-3b68-4b5f-bfef-21fc652ae8b2	1	20	2009-09-23 14:36:29.514+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	59f011d1-a6bc-40d5-8735-a6c338971f6c	\N	\N	\N	\N	\N	\N	\N	\N	2
f9de4a24-d06b-455c-a687-4d03dcd46121	1	21	2009-09-23 14:36:29.527+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	dd7cf67d-21eb-44e1-b092-2c20666d427b	\N	\N	\N	\N	\N	\N	\N	\N	2
43b978fd-1d4e-46f1-94c3-f592d8b90a86	1	20	2009-09-23 14:36:29.535+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	59f011d1-a6bc-40d5-8735-a6c338971f6c	\N	\N	\N	\N	\N	\N	\N	\N	3
de0fdfb5-806d-48cf-a244-865489ff1a7e	1	21	2009-09-23 14:36:29.546+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	dd7cf67d-21eb-44e1-b092-2c20666d427b	\N	\N	\N	\N	\N	\N	\N	\N	3
ff9dda9b-6646-468f-bf60-1c42ef2f2889	1	23	2009-09-23 14:36:29.568+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	1
ea9e8e30-eb02-42de-95c2-74890823bbfa	1	28	2009-09-23 14:36:29.577+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	1
c1ed4b0b-a3b3-44f9-bc2e-3f01a7d97f2f	1	28	2009-09-23 14:36:29.588+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	2
c1373196-f10a-4c5f-8a05-718bc854317e	1	24	2009-09-23 14:36:29.60+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	16
15c39c7d-6af7-46e5-8857-b218c2f3a563	1	31	2009-09-23 14:36:29.627+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c1373196-f10a-4c5f-8a05-718bc854317e	\N	\N	\N	\N	\N	\N	\N	\N	1
fdc81eef-dfbd-4443-ae15-050ac3e90560	1	32	2009-09-23 14:36:32.879+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	1
91e9d1a9-f379-495d-aa7d-7ed232124586	1	32	2009-09-23 14:36:32.89+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	2
23cf01a6-cb2e-41b1-9b03-5724d118a25b	1	32	2009-09-23 14:36:32.90+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	3
d307d2f2-4138-49bb-a5a7-884b68ecf3f3	1	32	2009-09-23 14:36:32.909+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	4
45b92b83-a65a-42d9-a062-da52c32d42b8	1	32	2009-09-23 14:36:32.918+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	5
87175028-9ee2-4ca5-bac9-38ac0f1801fe	1	32	2009-09-23 14:36:32.928+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	6
77a83545-b347-4508-b364-d803986f071e	1	32	2009-09-23 14:36:32.937+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	7
33beb635-7b4c-4bba-a4e0-fba8c6096e4f	1	32	2009-09-23 14:36:32.946+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	8
7d202c19-0834-4287-a48a-bd51097c560a	1	32	2009-09-23 14:36:32.955+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	9
a93bb75d-9989-4701-b881-a21b1274c490	1	32	2009-09-23 14:36:32.964+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	10
8e579f9a-24e3-426d-bbd9-2c51d9a1f22d	1	32	2009-09-23 14:36:32.975+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	11
5749c58f-2bca-4b8f-97ec-82703eb9b1da	1	32	2009-09-23 14:36:32.984+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	12
8a2102b7-9767-4803-990e-7a2cffd1a6cf	1	33	2009-09-23 14:36:32.998+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d8e93b72-5d27-475b-b92b-eb8eb5323682	\N	\N	\N	\N	\N	\N	\N	\N	1
f41de500-1ffe-4e2e-bd4d-21695897a1f8	1	34	2009-09-23 14:36:33.011+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8a2102b7-9767-4803-990e-7a2cffd1a6cf	\N	\N	\N	\N	\N	\N	\N	\N	1
c150cad6-708e-47b0-b90a-e5af01aea1a3	1	34	2009-09-23 14:36:33.023+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8a2102b7-9767-4803-990e-7a2cffd1a6cf	\N	\N	\N	\N	\N	\N	\N	\N	2
e47c52c2-7e20-4439-bf0a-cf79c743b9f1	1	34	2009-09-23 14:36:33.036+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8a2102b7-9767-4803-990e-7a2cffd1a6cf	\N	\N	\N	\N	\N	\N	\N	\N	3
39713898-3b9a-43fd-8f24-21d8c8a39b00	1	34	2009-09-23 14:36:33.046+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8a2102b7-9767-4803-990e-7a2cffd1a6cf	\N	\N	\N	\N	\N	\N	\N	\N	4
03429606-1903-4473-8106-fe21e4fe5264	1	34	2009-09-23 14:36:33.055+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8a2102b7-9767-4803-990e-7a2cffd1a6cf	\N	\N	\N	\N	\N	\N	\N	\N	5
730c9a24-d8d2-48a3-b477-fcdf51809ff2	1	34	2009-09-23 14:36:33.065+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8a2102b7-9767-4803-990e-7a2cffd1a6cf	\N	\N	\N	\N	\N	\N	\N	\N	6
84d4651f-03d7-4905-98d1-2324d670ef44	1	34	2009-09-23 14:36:33.075+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8a2102b7-9767-4803-990e-7a2cffd1a6cf	\N	\N	\N	\N	\N	\N	\N	\N	7
2bee3bb9-441d-440a-88df-118bff976188	1	34	2009-09-23 14:36:33.085+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8a2102b7-9767-4803-990e-7a2cffd1a6cf	\N	\N	\N	\N	\N	\N	\N	\N	8
29f57ba5-eb03-4a18-8771-7cc6481240f5	1	34	2009-09-23 14:36:33.096+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	8a2102b7-9767-4803-990e-7a2cffd1a6cf	\N	\N	\N	\N	\N	\N	\N	\N	9
c76b18e6-e461-4039-88e7-2783883e13e2	1	33	2009-09-23 14:36:33.106+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d8e93b72-5d27-475b-b92b-eb8eb5323682	\N	\N	\N	\N	\N	\N	\N	\N	2
1bff17d3-8a24-46b4-bdeb-b4f9b0befc5f	1	34	2009-09-23 14:36:33.118+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c76b18e6-e461-4039-88e7-2783883e13e2	\N	\N	\N	\N	\N	\N	\N	\N	1
f3a2fc27-fa4e-453c-80ea-3cc4119642e8	1	34	2009-09-23 14:36:33.128+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c76b18e6-e461-4039-88e7-2783883e13e2	\N	\N	\N	\N	\N	\N	\N	\N	2
89d1f570-ad88-4a74-af7f-08090877678e	1	34	2009-09-23 14:36:33.138+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c76b18e6-e461-4039-88e7-2783883e13e2	\N	\N	\N	\N	\N	\N	\N	\N	3
506f35db-ff45-4307-b972-b4c324a8e81a	1	34	2009-09-23 14:36:33.149+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c76b18e6-e461-4039-88e7-2783883e13e2	\N	\N	\N	\N	\N	\N	\N	\N	4
a2d95184-1103-4a9c-be8e-69151b33a5bc	1	34	2009-09-23 14:36:33.16+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c76b18e6-e461-4039-88e7-2783883e13e2	\N	\N	\N	\N	\N	\N	\N	\N	5
50f29fdf-e12b-4388-88ca-ee47ee57890c	1	34	2009-09-23 14:36:33.17+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c76b18e6-e461-4039-88e7-2783883e13e2	\N	\N	\N	\N	\N	\N	\N	\N	6
b110edd4-95f7-4b76-b48c-1a012f360801	1	34	2009-09-23 14:36:33.181+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c76b18e6-e461-4039-88e7-2783883e13e2	\N	\N	\N	\N	\N	\N	\N	\N	7
66131455-e2e0-42e6-a653-2729a72e50e9	1	34	2009-09-23 14:36:33.193+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c76b18e6-e461-4039-88e7-2783883e13e2	\N	\N	\N	\N	\N	\N	\N	\N	8
97fa9df9-7963-48a1-b27b-f3b65c9b5178	1	34	2009-09-23 14:36:33.203+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	c76b18e6-e461-4039-88e7-2783883e13e2	\N	\N	\N	\N	\N	\N	\N	\N	9
7cc2fd08-e5a8-4a1f-aba1-0232b3b250f7	1	33	2009-09-23 14:36:33.214+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d8e93b72-5d27-475b-b92b-eb8eb5323682	\N	\N	\N	\N	\N	\N	\N	\N	3
780fd1cf-675b-489c-a700-d4ce0588a142	1	34	2009-09-23 14:36:33.228+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	7cc2fd08-e5a8-4a1f-aba1-0232b3b250f7	\N	\N	\N	\N	\N	\N	\N	\N	1
f73de564-a7d9-4854-b632-7ff06ab826d4	1	34	2009-09-23 14:36:33.238+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	7cc2fd08-e5a8-4a1f-aba1-0232b3b250f7	\N	\N	\N	\N	\N	\N	\N	\N	2
506a22e7-ae3e-4716-8c7e-e49450a6e451	1	34	2009-09-23 14:36:33.248+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	7cc2fd08-e5a8-4a1f-aba1-0232b3b250f7	\N	\N	\N	\N	\N	\N	\N	\N	3
b073c070-6d67-413d-a4a9-97883957bdc8	1	34	2009-09-23 14:36:33.259+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	7cc2fd08-e5a8-4a1f-aba1-0232b3b250f7	\N	\N	\N	\N	\N	\N	\N	\N	4
89330cc7-84a9-4d4a-bbe7-56f39e121b31	1	34	2009-09-23 14:36:33.269+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	7cc2fd08-e5a8-4a1f-aba1-0232b3b250f7	\N	\N	\N	\N	\N	\N	\N	\N	5
e89095d5-cb2e-4047-8dc3-f458031f0099	1	34	2009-09-23 14:36:33.28+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	7cc2fd08-e5a8-4a1f-aba1-0232b3b250f7	\N	\N	\N	\N	\N	\N	\N	\N	6
97145f74-afa7-45d4-9dff-48e386946a7f	1	34	2009-09-23 14:36:33.293+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	7cc2fd08-e5a8-4a1f-aba1-0232b3b250f7	\N	\N	\N	\N	\N	\N	\N	\N	7
234b599f-bd06-4cb9-9275-346ee199c213	1	34	2009-09-23 14:36:33.305+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	7cc2fd08-e5a8-4a1f-aba1-0232b3b250f7	\N	\N	\N	\N	\N	\N	\N	\N	8
a1e08f7c-5db8-4f9a-91ae-a57e05e140f3	1	34	2009-09-23 14:36:33.316+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	7cc2fd08-e5a8-4a1f-aba1-0232b3b250f7	\N	\N	\N	\N	\N	\N	\N	\N	9
a5938af3-2283-47c0-83f1-3a3c1cb0c178	1	33	2009-09-23 14:36:33.327+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d8e93b72-5d27-475b-b92b-eb8eb5323682	\N	\N	\N	\N	\N	\N	\N	\N	4
db21f2b9-3981-4521-a09b-9ffc0bc66d48	1	34	2009-09-23 14:36:33.34+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a5938af3-2283-47c0-83f1-3a3c1cb0c178	\N	\N	\N	\N	\N	\N	\N	\N	1
08e5662b-1aa7-481e-9669-af0ebbee1871	1	34	2009-09-23 14:36:33.351+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a5938af3-2283-47c0-83f1-3a3c1cb0c178	\N	\N	\N	\N	\N	\N	\N	\N	2
224ba5b4-4c28-4aa1-936c-a607a788bef7	1	34	2009-09-23 14:36:33.362+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a5938af3-2283-47c0-83f1-3a3c1cb0c178	\N	\N	\N	\N	\N	\N	\N	\N	3
4ee06e9a-3499-4f15-8d3f-cd5c9fc474be	1	34	2009-09-23 14:36:33.373+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a5938af3-2283-47c0-83f1-3a3c1cb0c178	\N	\N	\N	\N	\N	\N	\N	\N	4
f4b043e0-2bfb-43cc-94b6-c9647db0ee67	1	34	2009-09-23 14:36:33.384+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a5938af3-2283-47c0-83f1-3a3c1cb0c178	\N	\N	\N	\N	\N	\N	\N	\N	5
1f52fc7d-7cd9-4ff0-9a12-d641a34d1ffa	1	34	2009-09-23 14:36:33.396+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a5938af3-2283-47c0-83f1-3a3c1cb0c178	\N	\N	\N	\N	\N	\N	\N	\N	6
72c25fda-2e70-45e0-a458-c983c81bf6dc	1	34	2009-09-23 14:36:33.408+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a5938af3-2283-47c0-83f1-3a3c1cb0c178	\N	\N	\N	\N	\N	\N	\N	\N	7
8bd546e7-0eb1-48af-babe-b0dbec30dadc	1	34	2009-09-23 14:36:33.419+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a5938af3-2283-47c0-83f1-3a3c1cb0c178	\N	\N	\N	\N	\N	\N	\N	\N	8
14556613-2e20-4871-8963-97ad314d9db8	1	34	2009-09-23 14:36:33.429+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	a5938af3-2283-47c0-83f1-3a3c1cb0c178	\N	\N	\N	\N	\N	\N	\N	\N	9
26f274f3-ce75-49bb-b4f2-6631b9f69022	1	33	2009-09-23 14:36:33.441+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d8e93b72-5d27-475b-b92b-eb8eb5323682	\N	\N	\N	\N	\N	\N	\N	\N	5
5b84d39b-b814-457b-bc62-eb853dc3a46d	1	34	2009-09-23 14:36:33.455+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	26f274f3-ce75-49bb-b4f2-6631b9f69022	\N	\N	\N	\N	\N	\N	\N	\N	1
ab192b31-0c7a-4107-a83d-65e530f33abb	1	34	2009-09-23 14:36:33.466+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	26f274f3-ce75-49bb-b4f2-6631b9f69022	\N	\N	\N	\N	\N	\N	\N	\N	2
448cdd9a-0e11-4654-90ac-6234f0d7821e	1	34	2009-09-23 14:36:33.477+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	26f274f3-ce75-49bb-b4f2-6631b9f69022	\N	\N	\N	\N	\N	\N	\N	\N	3
f1161f47-b2c7-4d3f-aaf8-bdc7a86ef380	1	34	2009-09-23 14:36:33.488+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	26f274f3-ce75-49bb-b4f2-6631b9f69022	\N	\N	\N	\N	\N	\N	\N	\N	4
9087c1bf-537f-4dd0-b789-cd13b2bde6af	1	34	2009-09-23 14:36:33.499+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	26f274f3-ce75-49bb-b4f2-6631b9f69022	\N	\N	\N	\N	\N	\N	\N	\N	5
09d1e846-5c98-4bcc-b56a-934bfa29ae94	1	34	2009-09-23 14:36:33.511+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	26f274f3-ce75-49bb-b4f2-6631b9f69022	\N	\N	\N	\N	\N	\N	\N	\N	6
bef8cc83-9704-4a80-a005-f78c479a9c9b	1	34	2009-09-23 14:36:33.522+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	26f274f3-ce75-49bb-b4f2-6631b9f69022	\N	\N	\N	\N	\N	\N	\N	\N	7
dc4fae61-e52f-4d49-8683-64a8fed54fb9	1	34	2009-09-23 14:36:33.534+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	26f274f3-ce75-49bb-b4f2-6631b9f69022	\N	\N	\N	\N	\N	\N	\N	\N	8
c38b5694-aea6-4292-ab51-06dce682e64f	1	34	2009-09-23 14:36:33.545+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	26f274f3-ce75-49bb-b4f2-6631b9f69022	\N	\N	\N	\N	\N	\N	\N	\N	9
6a49c137-c487-4401-b28c-4c30e4003b3d	1	33	2009-09-23 14:36:33.559+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d8e93b72-5d27-475b-b92b-eb8eb5323682	\N	\N	\N	\N	\N	\N	\N	\N	6
ffcefd36-4348-4c4d-8f75-d5c90ee6d438	1	34	2009-09-23 14:36:33.574+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6a49c137-c487-4401-b28c-4c30e4003b3d	\N	\N	\N	\N	\N	\N	\N	\N	1
ae1cd584-eed8-4ea5-821d-8076ec03f387	1	34	2009-09-23 14:36:33.586+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6a49c137-c487-4401-b28c-4c30e4003b3d	\N	\N	\N	\N	\N	\N	\N	\N	2
cf677dff-5dfa-4875-8356-8f3ee185e48d	1	34	2009-09-23 14:36:33.598+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6a49c137-c487-4401-b28c-4c30e4003b3d	\N	\N	\N	\N	\N	\N	\N	\N	3
5bf85582-70c3-4952-bfb9-f5e76fe2575a	1	34	2009-09-23 14:36:33.611+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6a49c137-c487-4401-b28c-4c30e4003b3d	\N	\N	\N	\N	\N	\N	\N	\N	4
1ace20dd-025a-4154-867b-86363d4e066b	1	34	2009-09-23 14:36:33.623+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6a49c137-c487-4401-b28c-4c30e4003b3d	\N	\N	\N	\N	\N	\N	\N	\N	5
dc1c97f1-1405-4a8d-a2dd-dcad772bcc02	1	34	2009-09-23 14:36:33.634+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6a49c137-c487-4401-b28c-4c30e4003b3d	\N	\N	\N	\N	\N	\N	\N	\N	6
df75bce8-952a-4ad8-bf4b-d444d51ce125	1	34	2009-09-23 14:36:33.646+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6a49c137-c487-4401-b28c-4c30e4003b3d	\N	\N	\N	\N	\N	\N	\N	\N	7
45a305db-d053-49fd-8160-516e16c56464	1	34	2009-09-23 14:36:33.658+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6a49c137-c487-4401-b28c-4c30e4003b3d	\N	\N	\N	\N	\N	\N	\N	\N	8
16659d72-da0e-4fef-aefc-b681277e9c84	1	34	2009-09-23 14:36:33.67+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6a49c137-c487-4401-b28c-4c30e4003b3d	\N	\N	\N	\N	\N	\N	\N	\N	9
ca7c6b62-aea6-473c-96c7-89eb4ad7ca57	1	33	2009-09-23 14:36:33.683+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d8e93b72-5d27-475b-b92b-eb8eb5323682	\N	\N	\N	\N	\N	\N	\N	\N	7
2d84d770-e903-4cd4-ab75-aef134ca7001	1	34	2009-09-23 14:36:33.698+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ca7c6b62-aea6-473c-96c7-89eb4ad7ca57	\N	\N	\N	\N	\N	\N	\N	\N	1
f758f8a3-eccb-4d30-9771-0a782f217751	1	34	2009-09-23 14:36:33.709+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ca7c6b62-aea6-473c-96c7-89eb4ad7ca57	\N	\N	\N	\N	\N	\N	\N	\N	2
e31c0d33-4cb4-482d-bcbe-1b1999c031e2	1	34	2009-09-23 14:36:33.721+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ca7c6b62-aea6-473c-96c7-89eb4ad7ca57	\N	\N	\N	\N	\N	\N	\N	\N	3
c461a377-232f-46cb-9534-31417ef9815d	1	34	2009-09-23 14:36:33.733+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ca7c6b62-aea6-473c-96c7-89eb4ad7ca57	\N	\N	\N	\N	\N	\N	\N	\N	4
1bfb7ed4-84f4-477c-93ac-c43bcbf64441	1	34	2009-09-23 14:36:33.745+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ca7c6b62-aea6-473c-96c7-89eb4ad7ca57	\N	\N	\N	\N	\N	\N	\N	\N	5
01f2b4c5-fede-46ac-b21a-6e0b40dfbf33	1	34	2009-09-23 14:36:33.758+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ca7c6b62-aea6-473c-96c7-89eb4ad7ca57	\N	\N	\N	\N	\N	\N	\N	\N	6
3c372828-91de-4634-a3f4-d0edfb0947dc	1	34	2009-09-23 14:36:33.77+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ca7c6b62-aea6-473c-96c7-89eb4ad7ca57	\N	\N	\N	\N	\N	\N	\N	\N	7
ea32cd1b-67b2-4945-8d57-6ce97291932f	1	34	2009-09-23 14:36:33.783+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ca7c6b62-aea6-473c-96c7-89eb4ad7ca57	\N	\N	\N	\N	\N	\N	\N	\N	8
e81749f1-df6d-48f3-a22d-b599e06c455a	1	34	2009-09-23 14:36:33.795+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	ca7c6b62-aea6-473c-96c7-89eb4ad7ca57	\N	\N	\N	\N	\N	\N	\N	\N	9
44e3f88d-a8af-458a-9ad4-9d00d0446a56	1	33	2009-09-23 14:36:33.808+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d8e93b72-5d27-475b-b92b-eb8eb5323682	\N	\N	\N	\N	\N	\N	\N	\N	8
5f2793c8-04f0-4972-a401-8bfa188270d8	1	34	2009-09-23 14:36:33.826+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	44e3f88d-a8af-458a-9ad4-9d00d0446a56	\N	\N	\N	\N	\N	\N	\N	\N	1
edd47dc1-6f9a-4efc-bade-6b6fe20f4b51	1	34	2009-09-23 14:36:33.837+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	44e3f88d-a8af-458a-9ad4-9d00d0446a56	\N	\N	\N	\N	\N	\N	\N	\N	2
89627ffa-1b1b-4625-b5ec-e0e0625cedfe	1	34	2009-09-23 14:36:33.85+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	44e3f88d-a8af-458a-9ad4-9d00d0446a56	\N	\N	\N	\N	\N	\N	\N	\N	3
e05b86cc-1db6-4b3d-b024-fd1630d212cf	1	34	2009-09-23 14:36:33.863+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	44e3f88d-a8af-458a-9ad4-9d00d0446a56	\N	\N	\N	\N	\N	\N	\N	\N	4
d467bad5-5052-4837-acae-9fc199eb60f8	1	34	2009-09-23 14:36:33.875+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	44e3f88d-a8af-458a-9ad4-9d00d0446a56	\N	\N	\N	\N	\N	\N	\N	\N	5
646166dd-c79d-4fef-a614-fbfee73a56b5	1	34	2009-09-23 14:36:33.888+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	44e3f88d-a8af-458a-9ad4-9d00d0446a56	\N	\N	\N	\N	\N	\N	\N	\N	6
2d5c4b47-8485-4a77-b148-594f4b4bbb64	1	34	2009-09-23 14:36:33.90+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	44e3f88d-a8af-458a-9ad4-9d00d0446a56	\N	\N	\N	\N	\N	\N	\N	\N	7
1b16407a-3f64-4d3c-b0ce-817afb724d11	1	34	2009-09-23 14:36:33.914+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	44e3f88d-a8af-458a-9ad4-9d00d0446a56	\N	\N	\N	\N	\N	\N	\N	\N	8
c9811c6f-c78e-40f1-9dff-49a6b567b327	1	34	2009-09-23 14:36:33.926+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	44e3f88d-a8af-458a-9ad4-9d00d0446a56	\N	\N	\N	\N	\N	\N	\N	\N	9
6bec13c7-f76a-43c8-b111-17f90ea26e76	1	33	2009-09-23 14:36:33.94+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d8e93b72-5d27-475b-b92b-eb8eb5323682	\N	\N	\N	\N	\N	\N	\N	\N	9
2e3b8921-98f9-4b6f-a489-05dccf98f6b7	1	34	2009-09-23 14:36:33.957+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6bec13c7-f76a-43c8-b111-17f90ea26e76	\N	\N	\N	\N	\N	\N	\N	\N	1
ab3d8023-a4af-4739-9cef-9810a653341e	1	34	2009-09-23 14:36:33.971+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6bec13c7-f76a-43c8-b111-17f90ea26e76	\N	\N	\N	\N	\N	\N	\N	\N	2
7869682f-7dad-428c-9ac8-eaf776125dbf	1	34	2009-09-23 14:36:33.984+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6bec13c7-f76a-43c8-b111-17f90ea26e76	\N	\N	\N	\N	\N	\N	\N	\N	3
ab17df25-42e7-41f3-ab34-199ad06f756c	1	34	2009-09-23 14:36:33.997+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6bec13c7-f76a-43c8-b111-17f90ea26e76	\N	\N	\N	\N	\N	\N	\N	\N	4
f4831933-c914-47fe-a3fd-46a26680a260	1	34	2009-09-23 14:36:34.01+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6bec13c7-f76a-43c8-b111-17f90ea26e76	\N	\N	\N	\N	\N	\N	\N	\N	5
a4083159-c5fc-4362-8ddf-9220baf40684	1	34	2009-09-23 14:36:34.023+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6bec13c7-f76a-43c8-b111-17f90ea26e76	\N	\N	\N	\N	\N	\N	\N	\N	6
b8926cf8-0569-4c96-93ef-01befcb1a80b	1	34	2009-09-23 14:36:34.038+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6bec13c7-f76a-43c8-b111-17f90ea26e76	\N	\N	\N	\N	\N	\N	\N	\N	7
947ec50d-3b33-404f-b180-644d1f0f64d2	1	34	2009-09-23 14:36:34.051+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6bec13c7-f76a-43c8-b111-17f90ea26e76	\N	\N	\N	\N	\N	\N	\N	\N	8
51519dfa-39f9-43e6-afad-cfbe3d3e8a11	1	34	2009-09-23 14:36:34.065+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6bec13c7-f76a-43c8-b111-17f90ea26e76	\N	\N	\N	\N	\N	\N	\N	\N	9
1aa721f7-bdcd-45d7-87d3-25c01d902ece	1	33	2009-09-23 14:36:34.078+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d8e93b72-5d27-475b-b92b-eb8eb5323682	\N	\N	\N	\N	\N	\N	\N	\N	10
b5ac3f3a-ce90-439d-bf66-248a861476d4	1	34	2009-09-23 14:36:34.095+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	1aa721f7-bdcd-45d7-87d3-25c01d902ece	\N	\N	\N	\N	\N	\N	\N	\N	1
ed6286ff-81a1-443c-8b95-a6671c1f9fc8	1	34	2009-09-23 14:36:34.108+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	1aa721f7-bdcd-45d7-87d3-25c01d902ece	\N	\N	\N	\N	\N	\N	\N	\N	2
cb36afe7-8f51-46e1-878a-cb306cb0ff20	1	34	2009-09-23 14:36:34.12+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	1aa721f7-bdcd-45d7-87d3-25c01d902ece	\N	\N	\N	\N	\N	\N	\N	\N	3
9e5b4b7f-611a-458c-981c-52c0278c051d	1	34	2009-09-23 14:36:34.133+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	1aa721f7-bdcd-45d7-87d3-25c01d902ece	\N	\N	\N	\N	\N	\N	\N	\N	4
06cd4882-0dc5-4d32-9ef5-63def7f2a172	1	34	2009-09-23 14:36:34.148+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	1aa721f7-bdcd-45d7-87d3-25c01d902ece	\N	\N	\N	\N	\N	\N	\N	\N	5
d5b547fa-4da1-4b84-ae5c-85216e9b97b7	1	34	2009-09-23 14:36:34.161+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	1aa721f7-bdcd-45d7-87d3-25c01d902ece	\N	\N	\N	\N	\N	\N	\N	\N	6
18325137-02f4-4e48-aaa6-a6800d083e33	1	34	2009-09-23 14:36:34.174+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	1aa721f7-bdcd-45d7-87d3-25c01d902ece	\N	\N	\N	\N	\N	\N	\N	\N	7
50f55e92-6116-4af2-ad65-49200b87b07a	1	34	2009-09-23 14:36:34.186+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	1aa721f7-bdcd-45d7-87d3-25c01d902ece	\N	\N	\N	\N	\N	\N	\N	\N	8
13a43eca-b163-48b0-bf25-b5434d95df54	1	34	2009-09-23 14:36:34.20+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	1aa721f7-bdcd-45d7-87d3-25c01d902ece	\N	\N	\N	\N	\N	\N	\N	\N	9
46fca86b-1be8-4bfc-a009-b5c2d9dfd3dc	1	33	2009-09-23 14:36:34.213+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	d8e93b72-5d27-475b-b92b-eb8eb5323682	\N	\N	\N	\N	\N	\N	\N	\N	11
d788907b-04ee-4457-847c-531ba45fe6a8	1	34	2009-09-23 14:36:34.241+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	46fca86b-1be8-4bfc-a009-b5c2d9dfd3dc	\N	\N	\N	\N	\N	\N	\N	\N	1
f262c6a1-05bb-45a7-8925-617753f3d798	1	34	2009-09-23 14:36:34.255+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	46fca86b-1be8-4bfc-a009-b5c2d9dfd3dc	\N	\N	\N	\N	\N	\N	\N	\N	2
011d952c-7f00-4347-b2d9-3c6dddf6b6fe	1	34	2009-09-23 14:36:34.269+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	46fca86b-1be8-4bfc-a009-b5c2d9dfd3dc	\N	\N	\N	\N	\N	\N	\N	\N	3
2c78cb67-322c-4f4d-90c8-1e763764d0ea	1	34	2009-09-23 14:36:34.328+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	46fca86b-1be8-4bfc-a009-b5c2d9dfd3dc	\N	\N	\N	\N	\N	\N	\N	\N	4
7a6de78b-2fd0-4c87-8b5f-fc83b4795312	1	34	2009-09-23 14:36:34.342+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	46fca86b-1be8-4bfc-a009-b5c2d9dfd3dc	\N	\N	\N	\N	\N	\N	\N	\N	5
8b659687-2e36-4a06-b0b8-7c69dc4dd02a	1	34	2009-09-23 14:36:34.354+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	46fca86b-1be8-4bfc-a009-b5c2d9dfd3dc	\N	\N	\N	\N	\N	\N	\N	\N	6
c35a2f2f-9e66-4085-81f9-65ce94462e5c	1	34	2009-09-23 14:36:34.369+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	46fca86b-1be8-4bfc-a009-b5c2d9dfd3dc	\N	\N	\N	\N	\N	\N	\N	\N	7
046233fd-53c9-4852-9eaa-17d295678245	1	34	2009-09-23 14:36:34.382+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	46fca86b-1be8-4bfc-a009-b5c2d9dfd3dc	\N	\N	\N	\N	\N	\N	\N	\N	8
e75ecdbe-79ea-4a32-9be6-6034bd4cd7f2	1	34	2009-09-23 14:36:34.395+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	46fca86b-1be8-4bfc-a009-b5c2d9dfd3dc	\N	\N	\N	\N	\N	\N	\N	\N	9
549fe6ba-e2cd-4da5-bceb-ce3da95b80e4	1	18	2009-09-23 14:38:06.917+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	2
dfe6f271-a7c6-4785-b2ff-5477931febca	1	23	2009-09-23 14:38:06.926+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	2
8f22a193-d1a3-454f-8612-4e818be4a48e	1	24	2009-09-23 14:38:07.026+03	00000000-0000-0000-0000-000000000000	00000000-0000-0000-0000-000000000000	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	17
4186c2e8-cdd8-4fab-bd29-04bfd7379f8c	1	28	2009-09-23 14:39:38.407+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	a620f116-ed18-4c69-9d0c-84ed0a41c5f1	\N	\N	\N	\N	\N	\N	\N	\N	1
749494f8-301a-4b0b-b408-4b05658dfd34	1	29	2009-09-23 14:39:38.418+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	4186c2e8-cdd8-4fab-bd29-04bfd7379f8c	\N	\N	\N	\N	\N	\N	\N	\N	1
ec3246e5-2ffd-4f56-8316-1510cf48c33d	1	18	2009-09-23 14:39:38.454+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	3
db77bc8d-3a32-4c28-bec0-ec9b72da2582	1	17	2009-09-23 14:39:38.46+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	a620f116-ed18-4c69-9d0c-84ed0a41c5f1	\N	\N	\N	\N	\N	\N	\N	\N	1
a620f116-ed18-4c69-9d0c-84ed0a41c5f1	2	15	2009-09-23 14:39:38.396+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	2
6067aa3c-f4e1-433d-961a-1f7c1ac82ba8	1	19	2009-09-23 14:39:38.47+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	db77bc8d-3a32-4c28-bec0-ec9b72da2582	\N	\N	\N	\N	\N	\N	\N	\N	1
4277587d-d230-4913-8467-4e78095393f1	1	25	2009-09-23 14:41:19.104+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	1
16f43bbf-4735-43f9-88d0-b234b67b0757	1	25	2009-09-23 14:41:19.109+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	2
a1a4842d-6d74-4cfb-9d60-a9a6eea95034	1	25	2009-09-23 14:41:19.114+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	3
47b3f779-421b-4b00-94a7-9ffa5c22db06	1	25	2009-09-23 14:41:19.119+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	4
98f87623-72b1-4e8b-87d2-69cac2c87d11	1	25	2009-09-23 14:41:19.124+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	5
35d7aaa3-db68-4cb9-bc1f-90ae3e78a416	1	25	2009-09-23 14:41:19.128+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	6
2c86eae6-328d-4847-86fb-098fe52af3a9	1	25	2009-09-23 14:41:19.133+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	7
473d9064-5249-459f-872a-98fa77cabeed	1	25	2009-09-23 14:41:19.137+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	8
c5b12fe5-e5dd-4fb9-aff4-d992ede653b9	1	25	2009-09-23 14:41:19.142+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	9
b817dd7c-d025-46a5-a069-3fb1cd9a1f42	1	25	2009-09-23 14:41:19.147+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	10
6a84f052-4083-421d-a491-522db8e7bc4d	1	25	2009-09-23 14:41:19.152+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	11
1ad8adcd-d3ee-4a38-aa90-5e700cc93134	1	25	2009-09-23 14:41:19.157+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	12
2474c5bb-7e45-4244-a547-80bdf045e037	1	26	2009-09-23 14:41:19.168+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	1
f7450b09-1e94-456d-926c-75286c13bab5	1	27	2009-09-23 14:41:19.179+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	1
c1ebb64b-f0bb-461f-be37-b4934a211e6c	1	27	2009-09-23 14:41:19.188+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	2
0f1ab287-3ffa-446b-b255-723f92222466	1	27	2009-09-23 14:41:19.197+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	3
4105bc2d-c6c8-4f60-8416-b6dedbca2141	1	27	2009-09-23 14:41:19.205+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	4
649c5f51-823e-4c03-80a5-2a8fa82b0cf3	1	27	2009-09-23 14:41:19.214+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	5
4b92c0ce-c56a-41c9-b120-1e4fba38c4be	1	27	2009-09-23 14:41:19.224+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	6
f4dc9708-780e-4188-b786-ddf4d7151588	1	27	2009-09-23 14:41:19.232+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	7
3e81de2c-0020-4aca-bb01-ee52c48f3e30	1	27	2009-09-23 14:41:19.24+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	8
caca0eff-2117-4ee2-970c-7cc9a9fcc79a	1	27	2009-09-23 14:41:19.249+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	9
688ac98f-b3c5-4997-a05f-f598bbcbbd62	1	27	2009-09-23 14:41:19.258+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	10
f4542415-c334-455a-be9a-dac24e1dd4b3	1	27	2009-09-23 14:41:19.268+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	11
4ae3f1fe-1d58-429b-bc3e-7d5e3630a131	1	27	2009-09-23 14:41:19.278+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	12
bde33e2a-2dc6-42eb-95d3-52fd15d6b01e	1	27	2009-09-23 14:41:19.288+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	13
ae06ba49-ef88-4534-aca5-47d2da1d1f4d	1	27	2009-09-23 14:41:19.298+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	14
2b2ae13a-9fa7-4337-9519-3630df564972	1	27	2009-09-23 14:41:19.308+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	15
253bfc93-2849-46dd-b11f-9abe40bf9826	1	27	2009-09-23 14:41:19.318+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	16
aa82ef59-9d1a-4e63-b55c-5b630a383f30	1	27	2009-09-23 14:41:19.327+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	17
1a710f2f-4ab2-4a56-be03-ee792255eadd	1	27	2009-09-23 14:41:19.337+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	18
3c2b524f-a991-417b-bdf2-946b897aaf77	1	27	2009-09-23 14:41:19.347+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	19
e25c4aa4-0e3f-4c75-8c8e-412cb6914af2	1	27	2009-09-23 14:41:19.357+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	20
80481af2-97fd-4a9d-a848-54400817bb4d	1	27	2009-09-23 14:41:19.368+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	21
57c807f8-c148-4d7d-9905-7ce6d401a23e	1	27	2009-09-23 14:41:19.378+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	22
053173c8-d063-4ad0-8bf4-d1451637ee28	1	27	2009-09-23 14:41:19.39+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	23
c09ba000-f933-496f-b8aa-62c71ce3eed4	1	27	2009-09-23 14:41:19.40+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	24
e7db4016-48df-4d22-9f60-15ab803d045a	1	27	2009-09-23 14:41:19.41+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	25
06566d64-5d6f-41d1-8617-55b54f20d192	1	27	2009-09-23 14:41:19.42+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	26
ff5d0a9a-618b-4ee6-9be8-19627f5dfbeb	1	27	2009-09-23 14:41:19.43+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	27
5ffa9bfd-7620-4eec-b9d1-2c3c8ae84e30	1	27	2009-09-23 14:41:19.44+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	28
7d523f92-d335-4d36-bd94-d2c2ab9a1007	1	27	2009-09-23 14:41:19.45+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	29
fee3a134-87b7-4f0f-a8db-5ea48eb0d98c	1	27	2009-09-23 14:41:19.46+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	30
d992929b-e83e-4c53-9bb4-5b32f8a14015	1	27	2009-09-23 14:41:19.47+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	31
daed3c68-3830-4375-8bf3-2105e71c14e5	1	27	2009-09-23 14:41:19.481+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	32
a32800a4-2242-4e20-9afd-5ced80b5ea19	1	27	2009-09-23 14:41:19.492+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	33
f32dd44b-7194-4ce4-bea7-6d4553c4dd7e	1	27	2009-09-23 14:41:19.503+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	34
24651a61-1d42-4d01-b28e-dfd942de10ca	1	27	2009-09-23 14:41:19.513+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	35
9b1780a2-a1df-4495-80dd-5f752523a4a6	1	27	2009-09-23 14:41:19.523+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	36
adf17eb2-1888-4039-b079-162e79398274	1	27	2009-09-23 14:41:19.534+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	37
58edcd3a-45bc-4e37-a317-538941b3b60f	1	27	2009-09-23 14:41:19.544+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	38
8a839bc2-7829-4277-b4ca-659eca05cb9a	1	27	2009-09-23 14:41:19.555+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	39
1259f85e-523a-41d7-bd3c-daaee480a0bb	1	27	2009-09-23 14:41:19.567+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	40
29031544-ad38-4460-a2b3-be62b9eb7e11	1	27	2009-09-23 14:41:19.578+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	41
ec6bb520-c52b-4176-887d-2271101510ac	1	27	2009-09-23 14:41:19.59+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	42
aca42890-54f1-4199-9d32-4a8fa6b63829	1	27	2009-09-23 14:41:19.602+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	43
d3b7c80d-6875-4f4c-a568-8f0c09afec8e	1	27	2009-09-23 14:41:19.613+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	44
a62ae886-36e2-4976-86ff-81c030adaae2	1	27	2009-09-23 14:41:19.625+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	45
bf81f907-0aaa-41bf-9e0a-ff9a27f37a67	1	27	2009-09-23 14:41:19.637+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	\N	\N	\N	\N	\N	\N	\N	\N	46
1c21fd93-c49a-4587-899e-ab2b43b91a17	1	24	2009-09-23 14:52:49.944+03	dfe6f271-a7c6-4785-b2ff-5477931febca	59f011d1-a6bc-40d5-8735-a6c338971f6c	f	f	f	f	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	18
\.


--
-- TOC entry 2965 (class 0 OID 306854)
-- Dependencies: 1743
-- Data for Name: db_properties; Type: TABLE DATA; Schema: public; Owner: -
--

COPY db_properties (access_level, related_object_id, property_key, property_value) FROM stdin;
\.


--
-- TOC entry 2966 (class 0 OID 306860)
-- Dependencies: 1744
-- Data for Name: delivery_certificate_assignments; Type: TABLE DATA; Schema: public; Owner: -
--

COPY delivery_certificate_assignments (delivery_certificate_id, document_id, document_number) FROM stdin;
\.


--
-- TOC entry 2967 (class 0 OID 306863)
-- Dependencies: 1745
-- Data for Name: delivery_certificate_items; Type: TABLE DATA; Schema: public; Owner: -
--

COPY delivery_certificate_items (certificate_item_id, parent_id, product_id, measure_unit_id, quantity, reference_item_id) FROM stdin;
\.


--
-- TOC entry 2968 (class 0 OID 306866)
-- Dependencies: 1746
-- Data for Name: delivery_certificate_serial_numbers; Type: TABLE DATA; Schema: public; Owner: -
--

COPY delivery_certificate_serial_numbers (certificate_item_id, serial_number) FROM stdin;
\.


--
-- TOC entry 2969 (class 0 OID 306869)
-- Dependencies: 1747
-- Data for Name: delivery_certificates; Type: TABLE DATA; Schema: public; Owner: -
--

COPY delivery_certificates (delivery_certificate_id, parent_id, warehouse_id, warehouse_name, delivery_certificate_number, delivery_certificate_date, recipient_id, recipient_name, recipient_contact_id, recipient_contact_name, delivery_cert_method_type_id, creation_time, creator_name, forwarder_id, forwarder_name, forwarder_contact_id, forwarder_contact_name, delivery_cert_reason_id, creator_id, forwarder_branch_id, recipient_branch_id, creator_organization_id, creator_branch_id, forwarder_branch_name, recipient_branch_name, creator_organization_name, creator_branch_name, delivery_cert_status_id) FROM stdin;
\.


--
-- TOC entry 2970 (class 0 OID 306875)
-- Dependencies: 1748
-- Data for Name: entity_sequences; Type: TABLE DATA; Schema: public; Owner: -
--

COPY entity_sequences (entity_id, data_object_type_id, initial_value, sequence_value, dataobjectid, dataobjecttypeid) FROM stdin;
\.


--
-- TOC entry 2971 (class 0 OID 306878)
-- Dependencies: 1749
-- Data for Name: enum_classes; Type: TABLE DATA; Schema: public; Owner: -
--

COPY enum_classes (enum_class_id, enum_class_name) FROM stdin;
39	com.cosmos.acacia.crm.enums.Gender
40	com.cosmos.acacia.crm.enums.MeasurementUnit
41	com.cosmos.acacia.crm.enums.OrganizationType
42	com.cosmos.acacia.crm.enums.ProductColor
43	com.cosmos.acacia.crm.enums.CommunicationType
44	com.cosmos.acacia.crm.enums.Currency
45	com.cosmos.acacia.crm.enums.PassportType
46	com.cosmos.acacia.crm.assembling.Algorithm$Type
47	com.cosmos.acacia.crm.enums.DataType
48	com.cosmos.acacia.crm.enums.DeliveryCertificateMethodType
49	com.cosmos.acacia.crm.enums.DeliveryCertificateReason
50	com.cosmos.acacia.crm.enums.DeliveryCertificateStatus
51	com.cosmos.acacia.crm.enums.InvoiceType
52	com.cosmos.acacia.crm.enums.DocumentDeliveryMethod
53	com.cosmos.acacia.crm.enums.TransportationMethod
54	com.cosmos.acacia.crm.enums.PaymentType
55	com.cosmos.acacia.crm.enums.PaymentTerm
56	com.cosmos.acacia.crm.enums.DeliveryType
57	com.cosmos.acacia.crm.enums.VatCondition
58	com.cosmos.acacia.crm.enums.InvoiceStatus
59	com.cosmos.acacia.crm.enums.DeliveryStatus
60	com.cosmos.acacia.crm.enums.PurchaseOrderStatus
61	com.cosmos.acacia.crm.enums.OrderConfirmationType
62	com.cosmos.acacia.crm.enums.SpecialPermission
63	com.cosmos.acacia.crm.enums.CustomerPaymentStatus
64	com.cosmos.acacia.crm.enums.CustomerPaymentType
65	com.cosmos.acacia.crm.enums.DocumentType
66	com.cosmos.acacia.crm.enums.DocumentStatus
67	com.cosmos.acacia.security.AccessRight
68	com.cosmos.acacia.crm.enums.PermissionCategory
69	com.cosmos.acacia.security.AccessLevel
70	com.cosmos.acacia.security.PrivilegeType
71	com.cosmos.acacia.crm.enums.BusinessActivity
72	com.cosmos.acacia.crm.enums.BusinessUnitType
73	com.cosmos.acacia.crm.enums.BusinessUnitAddressType
74	com.cosmos.acacia.crm.enums.AccountStatus
75	com.cosmos.acacia.crm.enums.FunctionalHierarchy
82	com.cosmos.acacia.security.PrivilegeCategoryType
\.


--
-- TOC entry 2972 (class 0 OID 306881)
-- Dependencies: 1750
-- Data for Name: expressions; Type: TABLE DATA; Schema: public; Owner: -
--

COPY expressions (organization_id, expression_key, expression_value) FROM stdin;
6b77867c-3bca-4fa9-9b75-540b4f0546cc	com.cosmos.acacia.crm.data.purchase.PurchaseInvoiceItem:receivedPrice	convertAmount(product.salesPrice, now(), product.currency, invoice.documentCurrency)
6b77867c-3bca-4fa9-9b75-540b4f0546cc	com.cosmos.acacia.crm.data.purchase.PurchaseInvoiceItem:extendedPrice	receivedPrice * receivedQuantity
\.


--
-- TOC entry 2973 (class 0 OID 306887)
-- Dependencies: 1751
-- Data for Name: goods_receipt_dc_items; Type: TABLE DATA; Schema: public; Owner: -
--

COPY goods_receipt_dc_items (receipt_item_id, delivery_certificate_item_id) FROM stdin;
\.


--
-- TOC entry 2974 (class 0 OID 306890)
-- Dependencies: 1752
-- Data for Name: goods_receipt_items; Type: TABLE DATA; Schema: public; Owner: -
--

COPY goods_receipt_items (receipt_item_id, goods_receipt_id, product_id, measure_unit_id, received_quantity, receipt_item_type) FROM stdin;
\.


--
-- TOC entry 2975 (class 0 OID 306893)
-- Dependencies: 1753
-- Data for Name: goods_receipt_pi_items; Type: TABLE DATA; Schema: public; Owner: -
--

COPY goods_receipt_pi_items (receipt_item_id, invoice_item_id) FROM stdin;
\.


--
-- TOC entry 2976 (class 0 OID 306896)
-- Dependencies: 1754
-- Data for Name: goods_receipts; Type: TABLE DATA; Schema: public; Owner: -
--

COPY goods_receipts (goods_receipt_id, supplier_id, supplier_branch_id, supplier_contact_id, related_document_type_id, related_document_id) FROM stdin;
\.


--
-- TOC entry 2977 (class 0 OID 306899)
-- Dependencies: 1755
-- Data for Name: job_titles; Type: TABLE DATA; Schema: public; Owner: -
--

COPY job_titles (job_title_id, business_unit_id, functional_hierarchy_id, job_title, security_role_id) FROM stdin;
\.


--
-- TOC entry 2978 (class 0 OID 306902)
-- Dependencies: 1756
-- Data for Name: order_confirmation_items; Type: TABLE DATA; Schema: public; Owner: -
--

COPY order_confirmation_items (confirmation_item_id, confirmed_quantity, extended_price, notes, parent_id, ship_date_from, ship_date_to, unit_price, measure_unit_id, product_id, currency_id, matched_quantity, ship_week) FROM stdin;
\.


--
-- TOC entry 2979 (class 0 OID 306905)
-- Dependencies: 1757
-- Data for Name: order_confirmations; Type: TABLE DATA; Schema: public; Owner: -
--

COPY order_confirmations (order_confirmation_id, discount_amount, discount_percent, documentdate_date, document_number, invoice_sub_value, notes, parent_id, supplier_contact_name, supplier_name, total_value, transport_price, vat, currency_id, document_type_id, supplier_partner_id, supplier_contact_id, branch_id, ship_date_from, ship_date_to, ship_week) FROM stdin;
\.


--
-- TOC entry 2980 (class 0 OID 306911)
-- Dependencies: 1758
-- Data for Name: order_item_match; Type: TABLE DATA; Schema: public; Owner: -
--

COPY order_item_match (id, matchquantity, purchaseorderitem_order_item_id, orderconfirmationitem_confirmation_item_id) FROM stdin;
\.


--
-- TOC entry 2981 (class 0 OID 306914)
-- Dependencies: 1759
-- Data for Name: organization_configurations; Type: TABLE DATA; Schema: public; Owner: -
--

COPY organization_configurations (organization_id, default_currency_id) FROM stdin;
\.


--
-- TOC entry 2982 (class 0 OID 306917)
-- Dependencies: 1760
-- Data for Name: organizations; Type: TABLE DATA; Schema: public; Owner: -
--

COPY organizations (organization_id, nickname, organization_name, registration_date, share_capital, unique_identifier_code, vat_number, organization_type_id, registration_address_id, registration_organization_id, administration_address_id, share_capital_currency_id, is_active, parent_business_partner_id) FROM stdin;
6b77867c-3bca-4fa9-9b75-540b4f0546cc	system	COSMOS Software Enterprises, Ltd.	\N	\N	\N	\N	\N	59f011d1-a6bc-40d5-8735-a6c338971f6c	\N	\N	\N	t	6b77867c-3bca-4fa9-9b75-540b4f0546cc
a620f116-ed18-4c69-9d0c-84ed0a41c5f1	\N	AluStyle	\N	\N	\N	123	\N	db77bc8d-3a32-4c28-bec0-ec9b72da2582	\N	\N	\N	f	6b77867c-3bca-4fa9-9b75-540b4f0546cc
\.


--
-- TOC entry 2983 (class 0 OID 306921)
-- Dependencies: 1761
-- Data for Name: passports; Type: TABLE DATA; Schema: public; Owner: -
--

COPY passports (passport_id, parent_id, passport_type_id, passport_number, issue_date, expiration_date, issuer_id, issuer_branch_id, additional_info) FROM stdin;
\.


--
-- TOC entry 2984 (class 0 OID 306924)
-- Dependencies: 1762
-- Data for Name: pattern_mask_formats; Type: TABLE DATA; Schema: public; Owner: -
--

COPY pattern_mask_formats (pattern_mask_format_id, description, format, format_type, parent_id, pattern_name, owner_id) FROM stdin;
\.


--
-- TOC entry 3025 (class 0 OID 310013)
-- Dependencies: 1817
-- Data for Name: personal_communication_contacts; Type: TABLE DATA; Schema: public; Owner: -
--

COPY personal_communication_contacts (personal_communication_contact_id, contact_person_id, communication_contact_id) FROM stdin;
27917c66-59d4-4713-b47c-688baf702c7d	dd7cf67d-21eb-44e1-b092-2c20666d427b	758b97eb-f92e-4375-9043-43f9dfa7d966
f9de4a24-d06b-455c-a687-4d03dcd46121	dd7cf67d-21eb-44e1-b092-2c20666d427b	55fc0001-3b68-4b5f-bfef-21fc652ae8b2
de0fdfb5-806d-48cf-a244-865489ff1a7e	dd7cf67d-21eb-44e1-b092-2c20666d427b	43b978fd-1d4e-46f1-94c3-f592d8b90a86
\.


--
-- TOC entry 2985 (class 0 OID 306930)
-- Dependencies: 1763
-- Data for Name: persons; Type: TABLE DATA; Schema: public; Owner: -
--

COPY persons (person_id, birth_date, extra_name, first_name, last_name, personal_unique_id, second_name, gender_id, birth_place_city_id, birth_place_country_id, parent_business_partner_id) FROM stdin;
90deb642-f53d-4cf1-8182-58f148517c76	1966-11-17	\N	Miroslav	Miroslav	\N	\N	664	\N	73e01607-9465-4937-aca6-0ec6c7be6660	55144634-4519-4401-b042-f8d8ea3747e8
10bb09e9-c2f9-4901-bab3-c37e9bde8779	1966-11-17	\N	Miroslav	Miroslav	\N	\N	664	\N	73e01607-9465-4937-aca6-0ec6c7be6660	d03b857f-b05b-47ae-bd0d-eefd83b83372
f4a17bae-e34c-43c2-a251-3ba0a26f7355	1966-11-17	\N	Miroslav	Miroslav	\N	\N	664	\N	73e01607-9465-4937-aca6-0ec6c7be6660	e5d5c576-ae35-43de-a67a-40f56ae598d0
83524976-6550-4967-b8ae-3ef87bdb48bf	1966-11-17	\N	Miroslav	Miroslav	\N	\N	664	\N	73e01607-9465-4937-aca6-0ec6c7be6660	70c2dab0-1a08-411b-b18e-7b8bf0aacaa3
135a52f7-c6a2-4da9-b501-754d5134cf74	1966-11-17	\N	Miroslav	Miroslav	\N	\N	664	\N	73e01607-9465-4937-aca6-0ec6c7be6660	c2228a74-4879-4338-9a6b-d92df52bffc9
df009b07-c928-4402-9e66-8f763962474b	1966-11-17	\N	Miroslav	Miroslav	\N	\N	664	\N	73e01607-9465-4937-aca6-0ec6c7be6660	d1a51325-c34c-4dec-acd6-6e9b36fe6a54
1d593a2b-4d70-43e5-ba35-e4497834ebf8	1966-11-17	\N	Miroslav	Miroslav	\N	\N	664	\N	73e01607-9465-4937-aca6-0ec6c7be6660	59f5e9f2-217c-401f-bd94-ae8b5d4ac477
53e7cb98-7cd7-4396-ab75-e92e5a49d6bb	1966-11-17	\N	Miroslav	Miroslav	\N	\N	664	\N	73e01607-9465-4937-aca6-0ec6c7be6660	17d7af2a-7efd-4443-9ffb-08636a8253c0
e7dc4455-6e3d-401d-b358-a7cd2b93f3f0	\N	\N	Miroslav	Nachev	\N	\N	\N	\N	\N	17d7af2a-7efd-4443-9ffb-08636a8253c0
c38a4163-0d89-4b4b-9714-5216f2ff3bbb	1966-11-17	\N	Miroslav	Miroslav	\N	\N	664	\N	73e01607-9465-4937-aca6-0ec6c7be6660	da1f088f-d0f6-4da0-bc3a-9c9f0c866cdd
a62f4b7b-9a73-4c3c-96d8-3e9697f09815	1966-11-17	\N	Miroslav	Miroslav	\N	\N	664	\N	73e01607-9465-4937-aca6-0ec6c7be6660	be102e1f-00f5-48b5-9cbc-3d2583527629
40fa7e11-6ec0-40fc-a3d3-31c53aef0d2b	\N	\N	Miroslav	Nachev	\N	\N	\N	\N	\N	be102e1f-00f5-48b5-9cbc-3d2583527629
b861637a-7d71-4248-8ce1-543f479b1589	\N	\N	Иржи	Хубенов	\N	\N	\N	\N	\N	be102e1f-00f5-48b5-9cbc-3d2583527629
b7ced214-7c98-48f9-a445-b80d907ae177	\N	\N	Miro	Iliev	\N	\N	\N	\N	\N	be102e1f-00f5-48b5-9cbc-3d2583527629
068397ba-eded-4a10-a976-bd5ce2c113a4	1966-11-17	\N	Miroslav	Miroslav	\N	\N	664	\N	73e01607-9465-4937-aca6-0ec6c7be6660	81e50b6b-68a6-4e50-9103-95fe8eba86f3
679ae5a7-eaff-4807-8ee1-6455bfb1c3be	\N	\N	Miroslav	Nachev	\N	\N	\N	\N	\N	81e50b6b-68a6-4e50-9103-95fe8eba86f3
73b1122d-285c-4ee2-9378-a30caebe10de	\N	\N	Иржи	Хубенов	\N	\N	\N	\N	\N	81e50b6b-68a6-4e50-9103-95fe8eba86f3
28425f1b-8617-4f32-8118-fd5a9acc66f9	1966-11-17	\N	Miroslav	Miroslav	\N	\N	664	\N	73e01607-9465-4937-aca6-0ec6c7be6660	8d550848-9260-41a7-aefd-8969d4179b13
9bbed255-4a67-42ff-bc7e-687d944da307	\N	\N	Miroslav	Nachev	\N	\N	\N	\N	\N	8d550848-9260-41a7-aefd-8969d4179b13
07a8c30b-5884-4b4d-be19-3fd099badf6c	\N	\N	Иржи	Хубенов	\N	\N	\N	\N	\N	8d550848-9260-41a7-aefd-8969d4179b13
5ca3e3c1-aed8-4d3f-9939-cebc2c9502db	1966-11-17	\N	Miroslav	Miroslav	\N	\N	664	\N	73e01607-9465-4937-aca6-0ec6c7be6660	70592cfd-348d-4255-a227-ab989eb38f25
df314b70-72cb-426b-82e2-ab25ddf83bd2	\N	\N	Miroslav	Nachev	\N	\N	\N	\N	\N	70592cfd-348d-4255-a227-ab989eb38f25
ce1d5ccf-cf18-4516-bd35-7cdbf97c90a4	\N	\N	Irji	Hubenov	\N	\N	\N	\N	\N	70592cfd-348d-4255-a227-ab989eb38f25
ced27fbe-fb72-4918-8f56-9e41b6dec8cc	1966-11-17	\N	Miroslav	Miroslav	\N	\N	664	\N	73e01607-9465-4937-aca6-0ec6c7be6660	6b77867c-3bca-4fa9-9b75-540b4f0546cc
549fe6ba-e2cd-4da5-bceb-ce3da95b80e4	\N	\N	Miroslav	Nachev	\N	\N	\N	\N	\N	6b77867c-3bca-4fa9-9b75-540b4f0546cc
ec3246e5-2ffd-4f56-8316-1510cf48c33d	\N	\N	Irji	Hubenov	\N	\N	\N	\N	\N	6b77867c-3bca-4fa9-9b75-540b4f0546cc
\.


--
-- TOC entry 2986 (class 0 OID 306934)
-- Dependencies: 1764
-- Data for Name: position_types; Type: TABLE DATA; Schema: public; Owner: -
--

COPY position_types (position_type_id, business_partner_id, position_type_name) FROM stdin;
\.


--
-- TOC entry 2987 (class 0 OID 306941)
-- Dependencies: 1765
-- Data for Name: pricelist_items; Type: TABLE DATA; Schema: public; Owner: -
--

COPY pricelist_items (item_id, pricelist_id, apply_client_discount, discount_percent, min_quantity, product_id) FROM stdin;
\.


--
-- TOC entry 2988 (class 0 OID 306944)
-- Dependencies: 1766
-- Data for Name: pricelists; Type: TABLE DATA; Schema: public; Owner: -
--

COPY pricelists (pricelist_id, active, valid_from, valid_to, default_discount, for_period, min_turnover, turnover_months, pricelist_name, parent_id, currency_id, general_pricelist) FROM stdin;
\.


--
-- TOC entry 2989 (class 0 OID 306950)
-- Dependencies: 1767
-- Data for Name: privilege_categories; Type: TABLE DATA; Schema: public; Owner: -
--

COPY privilege_categories (privilege_category_id, organization_id, category_name, privilege_type_id) FROM stdin;
fdc81eef-dfbd-4443-ae15-050ac3e90560	6b77867c-3bca-4fa9-9b75-540b4f0546cc	impl	1101
91e9d1a9-f379-495d-aa7d-7ed232124586	6b77867c-3bca-4fa9-9b75-540b4f0546cc	data	1101
23cf01a6-cb2e-41b1-9b03-5724d118a25b	6b77867c-3bca-4fa9-9b75-540b4f0546cc	assembling	1101
d307d2f2-4138-49bb-a5a7-884b68ecf3f3	6b77867c-3bca-4fa9-9b75-540b4f0546cc	cash	1101
45b92b83-a65a-42d9-a062-da52c32d42b8	6b77867c-3bca-4fa9-9b75-540b4f0546cc	contacts	1101
87175028-9ee2-4ca5-bac9-38ac0f1801fe	6b77867c-3bca-4fa9-9b75-540b4f0546cc	customer	1101
77a83545-b347-4508-b364-d803986f071e	6b77867c-3bca-4fa9-9b75-540b4f0546cc	product	1101
33beb635-7b4c-4bba-a4e0-fba8c6096e4f	6b77867c-3bca-4fa9-9b75-540b4f0546cc	purchase	1101
7d202c19-0834-4287-a48a-bd51097c560a	6b77867c-3bca-4fa9-9b75-540b4f0546cc	sales	1101
a93bb75d-9989-4701-b881-a21b1274c490	6b77867c-3bca-4fa9-9b75-540b4f0546cc	security	1101
8e579f9a-24e3-426d-bbd9-2c51d9a1f22d	6b77867c-3bca-4fa9-9b75-540b4f0546cc	users	1101
5749c58f-2bca-4b8f-97ec-82703eb9b1da	6b77867c-3bca-4fa9-9b75-540b4f0546cc	warehouse	1101
\.


--
-- TOC entry 2990 (class 0 OID 306953)
-- Dependencies: 1768
-- Data for Name: privilege_roles; Type: TABLE DATA; Schema: public; Owner: -
--

COPY privilege_roles (privilege_role_id, privilege_id, access_right_id, access_level_id) FROM stdin;
f41de500-1ffe-4e2e-bd4d-21695897a1f8	8a2102b7-9767-4803-990e-7a2cffd1a6cf	1015	1046
c150cad6-708e-47b0-b90a-e5af01aea1a3	8a2102b7-9767-4803-990e-7a2cffd1a6cf	1016	1046
e47c52c2-7e20-4439-bf0a-cf79c743b9f1	8a2102b7-9767-4803-990e-7a2cffd1a6cf	1017	1046
39713898-3b9a-43fd-8f24-21d8c8a39b00	8a2102b7-9767-4803-990e-7a2cffd1a6cf	1018	1046
03429606-1903-4473-8106-fe21e4fe5264	8a2102b7-9767-4803-990e-7a2cffd1a6cf	1019	1046
730c9a24-d8d2-48a3-b477-fcdf51809ff2	8a2102b7-9767-4803-990e-7a2cffd1a6cf	1020	1037
84d4651f-03d7-4905-98d1-2324d670ef44	8a2102b7-9767-4803-990e-7a2cffd1a6cf	1021	1037
2bee3bb9-441d-440a-88df-118bff976188	8a2102b7-9767-4803-990e-7a2cffd1a6cf	1022	1037
29f57ba5-eb03-4a18-8771-7cc6481240f5	8a2102b7-9767-4803-990e-7a2cffd1a6cf	1023	1037
1bff17d3-8a24-46b4-bdeb-b4f9b0befc5f	c76b18e6-e461-4039-88e7-2783883e13e2	1015	1046
f3a2fc27-fa4e-453c-80ea-3cc4119642e8	c76b18e6-e461-4039-88e7-2783883e13e2	1016	1046
89d1f570-ad88-4a74-af7f-08090877678e	c76b18e6-e461-4039-88e7-2783883e13e2	1017	1046
506f35db-ff45-4307-b972-b4c324a8e81a	c76b18e6-e461-4039-88e7-2783883e13e2	1018	1046
a2d95184-1103-4a9c-be8e-69151b33a5bc	c76b18e6-e461-4039-88e7-2783883e13e2	1019	1046
50f29fdf-e12b-4388-88ca-ee47ee57890c	c76b18e6-e461-4039-88e7-2783883e13e2	1020	1037
b110edd4-95f7-4b76-b48c-1a012f360801	c76b18e6-e461-4039-88e7-2783883e13e2	1021	1037
66131455-e2e0-42e6-a653-2729a72e50e9	c76b18e6-e461-4039-88e7-2783883e13e2	1022	1037
97fa9df9-7963-48a1-b27b-f3b65c9b5178	c76b18e6-e461-4039-88e7-2783883e13e2	1023	1037
780fd1cf-675b-489c-a700-d4ce0588a142	7cc2fd08-e5a8-4a1f-aba1-0232b3b250f7	1015	1046
f73de564-a7d9-4854-b632-7ff06ab826d4	7cc2fd08-e5a8-4a1f-aba1-0232b3b250f7	1016	1046
506a22e7-ae3e-4716-8c7e-e49450a6e451	7cc2fd08-e5a8-4a1f-aba1-0232b3b250f7	1017	1046
b073c070-6d67-413d-a4a9-97883957bdc8	7cc2fd08-e5a8-4a1f-aba1-0232b3b250f7	1018	1046
89330cc7-84a9-4d4a-bbe7-56f39e121b31	7cc2fd08-e5a8-4a1f-aba1-0232b3b250f7	1019	1046
e89095d5-cb2e-4047-8dc3-f458031f0099	7cc2fd08-e5a8-4a1f-aba1-0232b3b250f7	1020	1037
97145f74-afa7-45d4-9dff-48e386946a7f	7cc2fd08-e5a8-4a1f-aba1-0232b3b250f7	1021	1037
234b599f-bd06-4cb9-9275-346ee199c213	7cc2fd08-e5a8-4a1f-aba1-0232b3b250f7	1022	1037
a1e08f7c-5db8-4f9a-91ae-a57e05e140f3	7cc2fd08-e5a8-4a1f-aba1-0232b3b250f7	1023	1037
db21f2b9-3981-4521-a09b-9ffc0bc66d48	a5938af3-2283-47c0-83f1-3a3c1cb0c178	1015	1046
08e5662b-1aa7-481e-9669-af0ebbee1871	a5938af3-2283-47c0-83f1-3a3c1cb0c178	1016	1046
224ba5b4-4c28-4aa1-936c-a607a788bef7	a5938af3-2283-47c0-83f1-3a3c1cb0c178	1017	1046
4ee06e9a-3499-4f15-8d3f-cd5c9fc474be	a5938af3-2283-47c0-83f1-3a3c1cb0c178	1018	1046
f4b043e0-2bfb-43cc-94b6-c9647db0ee67	a5938af3-2283-47c0-83f1-3a3c1cb0c178	1019	1046
1f52fc7d-7cd9-4ff0-9a12-d641a34d1ffa	a5938af3-2283-47c0-83f1-3a3c1cb0c178	1020	1037
72c25fda-2e70-45e0-a458-c983c81bf6dc	a5938af3-2283-47c0-83f1-3a3c1cb0c178	1021	1037
8bd546e7-0eb1-48af-babe-b0dbec30dadc	a5938af3-2283-47c0-83f1-3a3c1cb0c178	1022	1037
14556613-2e20-4871-8963-97ad314d9db8	a5938af3-2283-47c0-83f1-3a3c1cb0c178	1023	1037
5b84d39b-b814-457b-bc62-eb853dc3a46d	26f274f3-ce75-49bb-b4f2-6631b9f69022	1015	1046
ab192b31-0c7a-4107-a83d-65e530f33abb	26f274f3-ce75-49bb-b4f2-6631b9f69022	1016	1046
448cdd9a-0e11-4654-90ac-6234f0d7821e	26f274f3-ce75-49bb-b4f2-6631b9f69022	1017	1046
f1161f47-b2c7-4d3f-aaf8-bdc7a86ef380	26f274f3-ce75-49bb-b4f2-6631b9f69022	1018	1046
9087c1bf-537f-4dd0-b789-cd13b2bde6af	26f274f3-ce75-49bb-b4f2-6631b9f69022	1019	1046
09d1e846-5c98-4bcc-b56a-934bfa29ae94	26f274f3-ce75-49bb-b4f2-6631b9f69022	1020	1037
bef8cc83-9704-4a80-a005-f78c479a9c9b	26f274f3-ce75-49bb-b4f2-6631b9f69022	1021	1037
dc4fae61-e52f-4d49-8683-64a8fed54fb9	26f274f3-ce75-49bb-b4f2-6631b9f69022	1022	1037
c38b5694-aea6-4292-ab51-06dce682e64f	26f274f3-ce75-49bb-b4f2-6631b9f69022	1023	1037
ffcefd36-4348-4c4d-8f75-d5c90ee6d438	6a49c137-c487-4401-b28c-4c30e4003b3d	1015	1046
ae1cd584-eed8-4ea5-821d-8076ec03f387	6a49c137-c487-4401-b28c-4c30e4003b3d	1016	1046
cf677dff-5dfa-4875-8356-8f3ee185e48d	6a49c137-c487-4401-b28c-4c30e4003b3d	1017	1046
5bf85582-70c3-4952-bfb9-f5e76fe2575a	6a49c137-c487-4401-b28c-4c30e4003b3d	1018	1046
1ace20dd-025a-4154-867b-86363d4e066b	6a49c137-c487-4401-b28c-4c30e4003b3d	1019	1046
dc1c97f1-1405-4a8d-a2dd-dcad772bcc02	6a49c137-c487-4401-b28c-4c30e4003b3d	1020	1037
df75bce8-952a-4ad8-bf4b-d444d51ce125	6a49c137-c487-4401-b28c-4c30e4003b3d	1021	1037
45a305db-d053-49fd-8160-516e16c56464	6a49c137-c487-4401-b28c-4c30e4003b3d	1022	1037
16659d72-da0e-4fef-aefc-b681277e9c84	6a49c137-c487-4401-b28c-4c30e4003b3d	1023	1037
2d84d770-e903-4cd4-ab75-aef134ca7001	ca7c6b62-aea6-473c-96c7-89eb4ad7ca57	1015	1046
f758f8a3-eccb-4d30-9771-0a782f217751	ca7c6b62-aea6-473c-96c7-89eb4ad7ca57	1016	1046
e31c0d33-4cb4-482d-bcbe-1b1999c031e2	ca7c6b62-aea6-473c-96c7-89eb4ad7ca57	1017	1046
c461a377-232f-46cb-9534-31417ef9815d	ca7c6b62-aea6-473c-96c7-89eb4ad7ca57	1018	1046
1bfb7ed4-84f4-477c-93ac-c43bcbf64441	ca7c6b62-aea6-473c-96c7-89eb4ad7ca57	1019	1046
01f2b4c5-fede-46ac-b21a-6e0b40dfbf33	ca7c6b62-aea6-473c-96c7-89eb4ad7ca57	1020	1037
3c372828-91de-4634-a3f4-d0edfb0947dc	ca7c6b62-aea6-473c-96c7-89eb4ad7ca57	1021	1037
ea32cd1b-67b2-4945-8d57-6ce97291932f	ca7c6b62-aea6-473c-96c7-89eb4ad7ca57	1022	1037
e81749f1-df6d-48f3-a22d-b599e06c455a	ca7c6b62-aea6-473c-96c7-89eb4ad7ca57	1023	1037
5f2793c8-04f0-4972-a401-8bfa188270d8	44e3f88d-a8af-458a-9ad4-9d00d0446a56	1015	1046
edd47dc1-6f9a-4efc-bade-6b6fe20f4b51	44e3f88d-a8af-458a-9ad4-9d00d0446a56	1016	1046
89627ffa-1b1b-4625-b5ec-e0e0625cedfe	44e3f88d-a8af-458a-9ad4-9d00d0446a56	1017	1046
e05b86cc-1db6-4b3d-b024-fd1630d212cf	44e3f88d-a8af-458a-9ad4-9d00d0446a56	1018	1046
d467bad5-5052-4837-acae-9fc199eb60f8	44e3f88d-a8af-458a-9ad4-9d00d0446a56	1019	1046
646166dd-c79d-4fef-a614-fbfee73a56b5	44e3f88d-a8af-458a-9ad4-9d00d0446a56	1020	1037
2d5c4b47-8485-4a77-b148-594f4b4bbb64	44e3f88d-a8af-458a-9ad4-9d00d0446a56	1021	1037
1b16407a-3f64-4d3c-b0ce-817afb724d11	44e3f88d-a8af-458a-9ad4-9d00d0446a56	1022	1037
c9811c6f-c78e-40f1-9dff-49a6b567b327	44e3f88d-a8af-458a-9ad4-9d00d0446a56	1023	1037
2e3b8921-98f9-4b6f-a489-05dccf98f6b7	6bec13c7-f76a-43c8-b111-17f90ea26e76	1015	1046
ab3d8023-a4af-4739-9cef-9810a653341e	6bec13c7-f76a-43c8-b111-17f90ea26e76	1016	1046
7869682f-7dad-428c-9ac8-eaf776125dbf	6bec13c7-f76a-43c8-b111-17f90ea26e76	1017	1046
ab17df25-42e7-41f3-ab34-199ad06f756c	6bec13c7-f76a-43c8-b111-17f90ea26e76	1018	1046
f4831933-c914-47fe-a3fd-46a26680a260	6bec13c7-f76a-43c8-b111-17f90ea26e76	1019	1046
a4083159-c5fc-4362-8ddf-9220baf40684	6bec13c7-f76a-43c8-b111-17f90ea26e76	1020	1037
b8926cf8-0569-4c96-93ef-01befcb1a80b	6bec13c7-f76a-43c8-b111-17f90ea26e76	1021	1037
947ec50d-3b33-404f-b180-644d1f0f64d2	6bec13c7-f76a-43c8-b111-17f90ea26e76	1022	1037
51519dfa-39f9-43e6-afad-cfbe3d3e8a11	6bec13c7-f76a-43c8-b111-17f90ea26e76	1023	1037
b5ac3f3a-ce90-439d-bf66-248a861476d4	1aa721f7-bdcd-45d7-87d3-25c01d902ece	1015	1046
ed6286ff-81a1-443c-8b95-a6671c1f9fc8	1aa721f7-bdcd-45d7-87d3-25c01d902ece	1016	1046
cb36afe7-8f51-46e1-878a-cb306cb0ff20	1aa721f7-bdcd-45d7-87d3-25c01d902ece	1017	1046
9e5b4b7f-611a-458c-981c-52c0278c051d	1aa721f7-bdcd-45d7-87d3-25c01d902ece	1018	1046
06cd4882-0dc5-4d32-9ef5-63def7f2a172	1aa721f7-bdcd-45d7-87d3-25c01d902ece	1019	1046
d5b547fa-4da1-4b84-ae5c-85216e9b97b7	1aa721f7-bdcd-45d7-87d3-25c01d902ece	1020	1037
18325137-02f4-4e48-aaa6-a6800d083e33	1aa721f7-bdcd-45d7-87d3-25c01d902ece	1021	1037
50f55e92-6116-4af2-ad65-49200b87b07a	1aa721f7-bdcd-45d7-87d3-25c01d902ece	1022	1037
13a43eca-b163-48b0-bf25-b5434d95df54	1aa721f7-bdcd-45d7-87d3-25c01d902ece	1023	1037
d788907b-04ee-4457-847c-531ba45fe6a8	46fca86b-1be8-4bfc-a009-b5c2d9dfd3dc	1015	1046
f262c6a1-05bb-45a7-8925-617753f3d798	46fca86b-1be8-4bfc-a009-b5c2d9dfd3dc	1016	1046
011d952c-7f00-4347-b2d9-3c6dddf6b6fe	46fca86b-1be8-4bfc-a009-b5c2d9dfd3dc	1017	1046
2c78cb67-322c-4f4d-90c8-1e763764d0ea	46fca86b-1be8-4bfc-a009-b5c2d9dfd3dc	1018	1046
7a6de78b-2fd0-4c87-8b5f-fc83b4795312	46fca86b-1be8-4bfc-a009-b5c2d9dfd3dc	1019	1046
8b659687-2e36-4a06-b0b8-7c69dc4dd02a	46fca86b-1be8-4bfc-a009-b5c2d9dfd3dc	1020	1037
c35a2f2f-9e66-4085-81f9-65ce94462e5c	46fca86b-1be8-4bfc-a009-b5c2d9dfd3dc	1021	1037
046233fd-53c9-4852-9eaa-17d295678245	46fca86b-1be8-4bfc-a009-b5c2d9dfd3dc	1022	1037
e75ecdbe-79ea-4a32-9be6-6034bd4cd7f2	46fca86b-1be8-4bfc-a009-b5c2d9dfd3dc	1023	1037
\.


--
-- TOC entry 2991 (class 0 OID 306956)
-- Dependencies: 1769
-- Data for Name: privileges; Type: TABLE DATA; Schema: public; Owner: -
--

COPY privileges (privilege_id, security_role_id, privilege_category_id, privilege_name, discriminator_id, data_object_type_id, data_object_id, permission_category_id, special_permission_id, expires) FROM stdin;
8a2102b7-9767-4803-990e-7a2cffd1a6cf	d8e93b72-5d27-475b-b92b-eb8eb5323682	8e579f9a-24e3-426d-bbd9-2c51d9a1f22d	BusinessUnit	EDOT	28	\N	\N	\N	\N
c76b18e6-e461-4039-88e7-2783883e13e2	d8e93b72-5d27-475b-b92b-eb8eb5323682	8e579f9a-24e3-426d-bbd9-2c51d9a1f22d	TeamMember	EDOT	40	\N	\N	\N	\N
7cc2fd08-e5a8-4a1f-aba1-0232b3b250f7	d8e93b72-5d27-475b-b92b-eb8eb5323682	8e579f9a-24e3-426d-bbd9-2c51d9a1f22d	Team	EDOT	37	\N	\N	\N	\N
a5938af3-2283-47c0-83f1-3a3c1cb0c178	d8e93b72-5d27-475b-b92b-eb8eb5323682	8e579f9a-24e3-426d-bbd9-2c51d9a1f22d	BusinessUnitAddress	EDOT	29	\N	\N	\N	\N
26f274f3-ce75-49bb-b4f2-6631b9f69022	d8e93b72-5d27-475b-b92b-eb8eb5323682	8e579f9a-24e3-426d-bbd9-2c51d9a1f22d	UserGroup	EDOT	36	\N	\N	\N	\N
6a49c137-c487-4401-b28c-4c30e4003b3d	d8e93b72-5d27-475b-b92b-eb8eb5323682	8e579f9a-24e3-426d-bbd9-2c51d9a1f22d	UserGroupMember	EDOT	35	\N	\N	\N	\N
ca7c6b62-aea6-473c-96c7-89eb4ad7ca57	d8e93b72-5d27-475b-b92b-eb8eb5323682	8e579f9a-24e3-426d-bbd9-2c51d9a1f22d	JobTitle	EDOT	38	\N	\N	\N	\N
44e3f88d-a8af-458a-9ad4-9d00d0446a56	d8e93b72-5d27-475b-b92b-eb8eb5323682	8e579f9a-24e3-426d-bbd9-2c51d9a1f22d	User	EDOT	23	\N	\N	\N	\N
6bec13c7-f76a-43c8-b111-17f90ea26e76	d8e93b72-5d27-475b-b92b-eb8eb5323682	8e579f9a-24e3-426d-bbd9-2c51d9a1f22d	UserRegistration	EDOT	39	\N	\N	\N	\N
1aa721f7-bdcd-45d7-87d3-25c01d902ece	d8e93b72-5d27-475b-b92b-eb8eb5323682	8e579f9a-24e3-426d-bbd9-2c51d9a1f22d	UserOrganization	EDOT	24	\N	\N	\N	\N
46fca86b-1be8-4bfc-a009-b5c2d9dfd3dc	d8e93b72-5d27-475b-b92b-eb8eb5323682	8e579f9a-24e3-426d-bbd9-2c51d9a1f22d	UserSecurityRole	EDOT	31	\N	\N	\N	\N
\.


--
-- TOC entry 2992 (class 0 OID 306960)
-- Dependencies: 1770
-- Data for Name: product_categories; Type: TABLE DATA; Schema: public; Owner: -
--

COPY product_categories (product_category_id, parent_id, category_name, description, parent_cat_id, pattern_mask_format_id, discount_percent, profit_percent, customs_duty_percent, excise_duty_percent, transport_percent, transport_value) FROM stdin;
\.


--
-- TOC entry 2993 (class 0 OID 306966)
-- Dependencies: 1771
-- Data for Name: product_percent_values; Type: TABLE DATA; Schema: public; Owner: -
--

COPY product_percent_values (percent_value_id, value_name, organization_id, value_type_id, percent_value) FROM stdin;
\.


--
-- TOC entry 2994 (class 0 OID 306969)
-- Dependencies: 1772
-- Data for Name: product_suppliers; Type: TABLE DATA; Schema: public; Owner: -
--

COPY product_suppliers (product_id, supplier_id, product_name, product_code, measure_unit_id, is_deliverable, is_obsolete, min_order_quantity, max_order_quantity, price_per_quantity, currency_id, order_price, delivery_time, description) FROM stdin;
\.


--
-- TOC entry 2995 (class 0 OID 306979)
-- Dependencies: 1773
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: -
--

COPY products (product_id, parent_id, product_type, product_code, product_name, measure_unit_id, currency_id) FROM stdin;
\.


--
-- TOC entry 2996 (class 0 OID 306982)
-- Dependencies: 1774
-- Data for Name: purchase_invoice_items; Type: TABLE DATA; Schema: public; Owner: -
--

COPY purchase_invoice_items (invoice_item_id, invoice_id, product_id, measure_unit_id, received_quantity, received_price, extended_price, tax_value, purchase_order_item_id, order_confirmation_item_id, customs_tariff_number) FROM stdin;
\.


--
-- TOC entry 2997 (class 0 OID 306985)
-- Dependencies: 1775
-- Data for Name: purchase_invoices; Type: TABLE DATA; Schema: public; Owner: -
--

COPY purchase_invoices (invoice_id, supplier_id, supplier_branch_id, supplier_contact_id, invoice_number, invoice_date, delivery_note, total_quantity, total_net_amount, total_tax, total_gross_amount, payment_terms_id, payment_deadline, bank_detail_id, delivery_terms_id, document_currency_id) FROM stdin;
\.


--
-- TOC entry 2998 (class 0 OID 306988)
-- Dependencies: 1776
-- Data for Name: purchase_order_items; Type: TABLE DATA; Schema: public; Owner: -
--

COPY purchase_order_items (order_item_id, parent_id, product_id, measure_unit_id, ordered_quantity, confirmed_quantity, delivered_quantity, purchase_price, ship_date, currency_id, notes, ship_date_from, ship_date_to) FROM stdin;
\.


--
-- TOC entry 2999 (class 0 OID 306991)
-- Dependencies: 1777
-- Data for Name: purchase_orders; Type: TABLE DATA; Schema: public; Owner: -
--

COPY purchase_orders (purchase_order_id, branch_name, creation_time, creator_name, finalizing_time, first_delivery_time, last_delivery_time, notes, order_number, parent_id, sender_name, sent_time, supplier_contact_name, supplier_name, supplier_order_number, sender_id, supplier_contact_id, creator_id, doc_delivery_method_id, supplier_partner_id, status_id, branch_id, deliverystatus_resource_id) FROM stdin;
\.


--
-- TOC entry 3000 (class 0 OID 306997)
-- Dependencies: 1778
-- Data for Name: real_products; Type: TABLE DATA; Schema: public; Owner: -
--

COPY real_products (product_id, simple_product_id) FROM stdin;
\.


--
-- TOC entry 3001 (class 0 OID 307000)
-- Dependencies: 1779
-- Data for Name: receipt_certificate_items; Type: TABLE DATA; Schema: public; Owner: -
--

COPY receipt_certificate_items (certificate_item_id, parent_id, product_id, measure_unit_id, quantity) FROM stdin;
\.


--
-- TOC entry 3002 (class 0 OID 307003)
-- Dependencies: 1780
-- Data for Name: receipt_certificate_serial_numbers; Type: TABLE DATA; Schema: public; Owner: -
--

COPY receipt_certificate_serial_numbers (certificate_item_id, serial_number) FROM stdin;
\.


--
-- TOC entry 3003 (class 0 OID 307006)
-- Dependencies: 1781
-- Data for Name: receipt_certificates; Type: TABLE DATA; Schema: public; Owner: -
--

COPY receipt_certificates (receipt_certificate_id, parent_id, warehouse_id, warehouse_name, receipt_certificate_number, receipt_certificate_date, deliverer_id, deliverer_name, deliverer_contact_id, deliverer_contact_name, receipt_cert_method_type_id, receipt_cert_reason_id, creation_time, creator_name, forwarder_id, forwarder_name, forwarder_contact_id, forwarder_contact_name, creator_id) FROM stdin;
\.


--
-- TOC entry 3004 (class 0 OID 307009)
-- Dependencies: 1782
-- Data for Name: registration_codes; Type: TABLE DATA; Schema: public; Owner: -
--

COPY registration_codes (registration_code, email_address, registration_time) FROM stdin;
53dda305-90a9-4ae6-bb90-3e879f6c6408	miro@space-comm.com	2009-09-23 14:37:24.253+03
\.


--
-- TOC entry 3005 (class 0 OID 307013)
-- Dependencies: 1783
-- Data for Name: resource_bundle; Type: TABLE DATA; Schema: public; Owner: -
--

COPY resource_bundle (resource_id, enum_class_id, enum_name) FROM stdin;
664	39	Male
665	39	Female
666	40	Piece
667	40	Centimeter
668	40	Meter
669	40	Decimeter
670	40	Kilometer
671	40	Millimeter
672	40	Gram
673	40	Kilogram
674	40	TonMetric
675	40	SquareCentimeter
676	40	SquareMeter
677	40	Area
678	40	SquareKilometer
679	40	CubicCentimeter
680	40	CubicMeter
681	40	CubicKilometer
682	40	CubicMillimeter
683	40	Liter
684	40	BiWeeksPerYear
685	40	Second
686	40	Minute
687	40	Hour
688	40	Day
689	40	DayPayroll
690	40	BiweekPayroll
691	40	YearPayroll
692	40	YearPayrollAcademic
693	40	Year
694	40	YearShort
695	40	Month
696	40	MilliSecond
697	40	MicroSecond
698	40	NanoSecond
699	40	PicoSecond
700	40	KilowattHour
701	40	WattHour
702	40	MegawattHour
703	41	NationalAgency
704	41	Agency
705	41	PrivateLimitedCompany
706	41	PublicLimitedCompany
707	41	CompanyWithLimitedLiability
708	41	StockCorporation
709	41	Incorporated
710	41	Corporation
711	41	SoleTrader
712	41	GovernmentOrganization
713	41	MunicipalOrganization
714	41	GeneralPartnership
715	41	LimitedPartnership
716	41	SingleMemberLimitedLiabilityCompany
717	41	LimitedLiabilityCompany
718	41	JointStockCompany
719	41	SingleShareholderJointStockCompany
720	41	PartnershipLimited
721	42	BlackDesktopComputer
722	42	SilverDesktopComputer
723	42	BlackMobileComputer
724	42	SilverMobileComputer
725	42	BlackServerComputer
726	43	Phone
727	43	Mobile
728	43	Fax
729	43	ICQ
730	43	Skype
731	43	GoogleTalk
732	43	Email
733	44	AED
734	44	ALL
735	44	ARS
736	44	AUD
737	44	BAM
738	44	BGN
739	44	BHD
740	44	BOB
741	44	BRL
742	44	BYR
743	44	CAD
744	44	CHF
745	44	CLP
746	44	CNY
747	44	COP
748	44	CRC
749	44	CSD
750	44	CZK
751	44	DKK
752	44	DOP
753	44	DZD
754	44	EEK
755	44	EGP
756	44	EUR
757	44	GBP
758	44	GTQ
759	44	HKD
760	44	HNL
761	44	HRK
762	44	HUF
763	44	IDR
764	44	ILS
765	44	INR
766	44	IQD
767	44	ISK
768	44	JOD
769	44	JPY
770	44	KRW
771	44	KWD
772	44	LBP
773	44	LTL
774	44	LVL
775	44	LYD
776	44	MAD
777	44	MKD
778	44	MXN
779	44	MYR
780	44	NIO
781	44	NOK
782	44	NZD
783	44	OMR
784	44	PAB
785	44	PEN
786	44	PHP
787	44	PLN
788	44	PYG
789	44	QAR
790	44	RON
791	44	RSD
792	44	RUB
793	44	SAR
794	44	SDG
795	44	SEK
796	44	SGD
797	44	SKK
798	44	SVC
799	44	SYP
800	44	THB
801	44	TND
802	44	TRY
803	44	TWD
804	44	UAH
805	44	USD
806	44	UYU
807	44	VEF
808	44	VND
809	44	YER
810	44	ZAR
811	45	National
812	45	International
813	46	UnconditionalSelection
814	46	UserSelection
815	46	UserSingleSelection
816	46	UserMultipleSelection
817	46	RangeSelection
818	46	RangeSingleSelection
819	46	RangeMultipleSelection
820	46	EqualsSelection
821	46	EqualsSingleSelection
822	46	EqualsMultipleSelection
823	47	IntegerType
824	47	DecimalType
825	47	Percent
826	47	DateType
827	47	TimeType
828	47	DayType
829	47	HourType
830	47	MinuteType
831	47	StringType
832	47	EnumerationType
833	48	InPlace
834	48	Forwarder
835	49	Invoice
836	49	ProformaInvoice
837	49	Offer
838	49	Test
839	49	Replacement
840	50	Draft
841	50	Delivered
842	51	VatInvoice
843	51	SimpleInvoice
844	51	DebitNoteInvoice
845	51	CretidNoteInvoice
846	52	InPlace
847	52	Email
848	52	WebServices
849	52	Fax
850	52	FaxAuto
851	52	PostMail
852	52	Courier
853	52	Combined
854	53	InPlace
855	53	Courier
856	53	PostMail
857	53	Email
858	53	RailwayCargo
859	53	AirCargo
860	53	AutoCargo
861	53	InternalCargo
862	53	OtherTransports
863	54	Cash
864	54	BankTransfer
865	54	CasheOnDelivery
866	55	InAdvance
867	55	AfterDelivery
868	55	BeforeAndAfter
869	55	Leasing
870	55	InstalmentPlan
871	55	None
872	56	DDP
873	56	CIF
874	56	FOB
875	57	VatPayable
876	57	NoVat
877	58	Open
878	58	WaitForPayment
879	58	Reopen
880	58	Cancelled
881	58	Paid
882	59	NotDelivered
883	59	PartlyDelivered
884	59	Delivered
885	60	Open
886	60	Sent
887	60	Confirmed
888	60	PartlyConfirmed
889	60	Rejected
890	60	Canceled
891	61	VatInvoice
892	61	SimpleInvoice
893	61	DebitNoteInvoice
894	61	CretidNoteInvoice
895	62	SystemAdministrator
896	62	OrganizationAdministrator
897	62	BranchAdministrator
898	62	CanViewDataFromAllBranches
899	62	Contact
900	62	Organization
901	62	Person
902	62	Lead
903	62	Opportunity
904	62	Activity
905	62	Note
906	62	EmailTemplate
907	62	Announcement
908	62	Subject
909	62	Queue
910	62	SavedView
911	62	Report
912	62	DuplicateDetectionRule
913	62	DataImport
914	62	DataExport
915	62	DataMap
916	62	OpportunityRelationship
917	62	RelationshipRole
918	62	CustomerRelationship
919	62	PublishEmailTemplate
920	62	AddReportingServicesReports
921	62	PublishReports
922	62	PublishDuplicateDetectionRules
923	62	MarketingList
924	62	Campaign
925	62	CreateQuickCampaign
926	62	Product
927	62	ProductPricing
928	62	Competitor
929	62	SalesLiterature
930	62	Quote
931	62	SalesOrder
932	62	SalesInvoice
933	62	Territory
934	62	OverrideQuotePricing
935	62	OverrideOrderPricing
936	62	OverrideInvoicePricing
937	62	PurchaseOrder
938	62	PurchaseInvoice
939	62	Article
940	62	ArticleTemplate
941	62	Case
942	62	Contract
943	62	ContractTemplate
944	62	PublishArticles
945	62	OrganizationSettings
946	62	BusinessUnit
947	62	User
948	62	UserSettings
949	62	Team
950	62	Role
951	62	License
952	62	Currency
953	62	AssignRole
954	62	AssignTerritoryToUser
955	62	BulkEdit
956	62	GoMobile
957	62	Print
958	62	Export
959	62	Merge
960	62	GoOffline
961	62	AddressBook
962	62	UpdateBusinessClosures
963	62	SendEmailAsAnotherUser
964	62	LanguageSettings
965	62	SendInvitation
966	62	Calendar
967	62	MyWorkHours
968	62	Service
969	62	FacilityEquipment
970	62	Site
971	62	SearchAvailability
972	62	BrowseAvailability
973	62	Entity
974	62	EntityAttribute
975	62	EntityOperation
976	62	EntityNotification
977	62	Relationship
978	62	Form
979	62	View
980	62	Workflow
981	62	SystemJob
982	62	ISVExtensions
983	62	ExecuteWorkflowJob
984	62	ExportCustomization
985	62	ImportCustomization
986	62	PublishCustomization
987	63	Open
988	63	Completed
989	64	Bank
990	64	Cash
991	65	DeliveryCertificate
992	65	SalesOffer
993	65	SalesProformaInvoice
994	65	SalesInvoice
995	65	CustomerPayment
996	65	GoodsReceipt
997	65	PurchaseOrder
998	65	PurchaseOrderConfirmation
999	65	PurchaseInvoice
1000	65	CashReconcile
1001	66	Draft
1002	66	Sent
1003	66	Completed
1004	66	WaitForPayment
1005	66	Confirmed
1006	66	Accepted
1007	66	PartlyConfirmed
1008	66	Reopen
1009	66	Rejected
1010	66	Cancelled
1011	66	Paid
1012	66	Delivered
1013	66	NotDelivered
1014	66	PartlyDelivered
1015	67	Create
1016	67	Read
1017	67	Modify
1018	67	Delete
1019	67	Execute
1020	67	Append
1021	67	AppendTo
1022	67	Assign
1023	67	Share
1024	68	CoreRecords
1025	68	Contacts
1026	68	Marketing
1027	68	Sales
1028	68	Supplies
1029	68	Service
1030	68	BusinessManagement
1031	68	ServiceManagement
1032	68	Customization
1033	68	CustomEntities
1034	68	Administration
1035	68	BranchAdministration
1036	68	Miscellaneous
1037	69	None
1038	69	Session
1039	69	ClientContact
1040	69	Client
1041	69	User
1042	69	BusinessUnit
1043	69	ParentChildBusinessUnit
1044	69	Organization
1045	69	ParentChildOrganization
1046	69	System
1047	70	UserOwnedEntity
1048	70	OrganizationOwnedEntity
1049	70	SystemOwnedEntity
1050	70	GlobalTask
1051	71	Administration
1052	71	Finance
1053	71	CustomerService
1054	71	CoreRecords
1055	71	Contacts
1056	71	Marketing
1057	71	Sales
1058	71	Supply
1059	71	Warehouse
1060	71	Service
1061	71	BusinessManagement
1062	71	ServiceManagement
1063	71	Customization
1064	71	CustomEntities
1065	72	Administrative
1066	72	Finance
1067	72	Customer
1068	72	Supplies
1069	72	Sales
1070	72	Marketing
1071	72	Warehouse
1072	73	BillTo
1073	73	ShipTo
1074	73	Other
1075	74	Enabled
1076	74	Disabled
1077	74	AwaitingActivation
1078	74	Expired
1079	74	Locked
1080	75	Manager
1081	75	DeputyManager
1082	75	FunctionalManager
1083	75	DeputyFunctionalManager
1084	75	SeniorOfficer
1085	75	Officer
1086	75	JuniorOfficer
1087	75	Trainee
1088	75	Visitor
1101	82	System
1102	82	UserDefined
\.


--
-- TOC entry 3006 (class 0 OID 307016)
-- Dependencies: 1784
-- Data for Name: sales_invoice_item_link; Type: TABLE DATA; Schema: public; Owner: -
--

COPY sales_invoice_item_link (id, template_doc_id, template_item_id, invoice_item_id) FROM stdin;
\.


--
-- TOC entry 3007 (class 0 OID 307019)
-- Dependencies: 1785
-- Data for Name: sales_invoice_items; Type: TABLE DATA; Schema: public; Owner: -
--

COPY sales_invoice_items (invoice_item_id, discount_amount, discount_percent, extended_price, notes, ordered_quantity, parent_id, product_description, returned_quantity, ship_date_from, ship_date_to, ship_week, shipped_quantity, unit_price, measure_unit_id, warehouse_id, product_id, duequantity, pricelistid, pricelistitemid) FROM stdin;
\.


--
-- TOC entry 3008 (class 0 OID 307025)
-- Dependencies: 1786
-- Data for Name: sales_invoices; Type: TABLE DATA; Schema: public; Owner: -
--

COPY sales_invoices (invoice_id, branch_name, completion_date, creation_time, creator_name, days_between_payments, discount_amount, discount_percent, excise_duty_percent, excise_duty_amount, invoice_date, invoice_number, invoice_sub_value, notes, parent_id, payment_due_date, payments_count, proforma, recipient_contact_name, recipient_name, sender_name, sent_time, ship_date_from, ship_date_to, ship_week, single_pay, total_value, transport_price, vat, vat_condition_notes, recipient_id, payment_terms_id, vat_condition_id, branch_id, supplier_contact_id, payment_type_id, invoice_type_id, delivery_type_id, doc_delivery_method_id, transportation_method_id, status_id, shippingagent_partner_id, currency_id, sender_id, creator_id, deliverystatus_resource_id, additionalterms, validto, attendee_id, paid_amount) FROM stdin;
\.


--
-- TOC entry 3009 (class 0 OID 307031)
-- Dependencies: 1787
-- Data for Name: security_roles; Type: TABLE DATA; Schema: public; Owner: -
--

COPY security_roles (security_role_id, organization_id, security_role_name, business_unit_id) FROM stdin;
d8e93b72-5d27-475b-b92b-eb8eb5323682	6b77867c-3bca-4fa9-9b75-540b4f0546cc	supervisor	ea9e8e30-eb02-42de-95c2-74890823bbfa
\.


--
-- TOC entry 3010 (class 0 OID 307034)
-- Dependencies: 1788
-- Data for Name: sequence_identifiers; Type: TABLE DATA; Schema: public; Owner: -
--

COPY sequence_identifiers (seq_id_key, seq_id_name, seq_id_value) FROM stdin;
\.


--
-- TOC entry 3011 (class 0 OID 307038)
-- Dependencies: 1789
-- Data for Name: simple_products; Type: TABLE DATA; Schema: public; Owner: -
--

COPY simple_products (product_id, category_id, is_purchased, is_salable, is_obsolete, product_color_id, minimum_quantity, maximum_quantity, default_quantity, list_price, quantity_per_package, dimension_unit_id, dimension_width, dimension_length, dimension_height, weight_unit_id, weight, delivery_time, description, producer_id, pattern_mask_format_id, price_per_quantity, discount_percent_id, profit_percent_id, customs_duty_percent_id, excise_duty_percent_id, transport_percent_id, transport_value) FROM stdin;
\.


--
-- TOC entry 3012 (class 0 OID 307050)
-- Dependencies: 1790
-- Data for Name: team_members; Type: TABLE DATA; Schema: public; Owner: -
--

COPY team_members (team_member_id, team_id, user_organization_id, status_id) FROM stdin;
\.


--
-- TOC entry 3013 (class 0 OID 307053)
-- Dependencies: 1791
-- Data for Name: teams; Type: TABLE DATA; Schema: public; Owner: -
--

COPY teams (team_id, team_name, organization_id, business_unit_id, status_id) FROM stdin;
\.


--
-- TOC entry 3014 (class 0 OID 307056)
-- Dependencies: 1792
-- Data for Name: user_group_members; Type: TABLE DATA; Schema: public; Owner: -
--

COPY user_group_members (user_group_member_id, user_group_id, user_organization_id) FROM stdin;
\.


--
-- TOC entry 3015 (class 0 OID 307059)
-- Dependencies: 1793
-- Data for Name: user_groups; Type: TABLE DATA; Schema: public; Owner: -
--

COPY user_groups (user_group_id, user_group_name, organization_id, description) FROM stdin;
\.


--
-- TOC entry 3016 (class 0 OID 307065)
-- Dependencies: 1794
-- Data for Name: user_organizations; Type: TABLE DATA; Schema: public; Owner: -
--

COPY user_organizations (user_id, organization_id, branch_id, is_user_active, job_title_id, manager_id, email_address, user_organization_id, business_unit_id) FROM stdin;
ff9dda9b-6646-468f-bf60-1c42ef2f2889	6b77867c-3bca-4fa9-9b75-540b4f0546cc	59f011d1-a6bc-40d5-8735-a6c338971f6c	t	\N	\N	\N	c1373196-f10a-4c5f-8a05-718bc854317e	ea9e8e30-eb02-42de-95c2-74890823bbfa
dfe6f271-a7c6-4785-b2ff-5477931febca	6b77867c-3bca-4fa9-9b75-540b4f0546cc	59f011d1-a6bc-40d5-8735-a6c338971f6c	t	\N	\N	\N	8f22a193-d1a3-454f-8612-4e818be4a48e	c1ed4b0b-a3b3-44f9-bc2e-3f01a7d97f2f
dfe6f271-a7c6-4785-b2ff-5477931febca	a620f116-ed18-4c69-9d0c-84ed0a41c5f1	db77bc8d-3a32-4c28-bec0-ec9b72da2582	f	\N	\N	\N	1c21fd93-c49a-4587-899e-ab2b43b91a17	4186c2e8-cdd8-4fab-bd29-04bfd7379f8c
\.


--
-- TOC entry 3017 (class 0 OID 307068)
-- Dependencies: 1795
-- Data for Name: user_rights; Type: TABLE DATA; Schema: public; Owner: -
--

COPY user_rights (user_right_id, access_rights, excluded, organization_id, owner_type_id, user_id, user_group_id, data_object_type_id, data_object_id, permission_category_id, special_permission_id, expires) FROM stdin;
\.


--
-- TOC entry 3018 (class 0 OID 307076)
-- Dependencies: 1796
-- Data for Name: user_rights_old; Type: TABLE DATA; Schema: public; Owner: -
--

COPY user_rights_old (user_group_id, user_id, data_object_type_id, data_object_id, can_read, can_create, can_modify, can_delete, user_right_id, special_permission_id, excluded, expires, can_execute) FROM stdin;
\.


--
-- TOC entry 3019 (class 0 OID 307085)
-- Dependencies: 1797
-- Data for Name: user_security_roles; Type: TABLE DATA; Schema: public; Owner: -
--

COPY user_security_roles (user_security_role_id, user_organization_id, security_role_id) FROM stdin;
15c39c7d-6af7-46e5-8857-b218c2f3a563	c1373196-f10a-4c5f-8a05-718bc854317e	d8e93b72-5d27-475b-b92b-eb8eb5323682
\.


--
-- TOC entry 3020 (class 0 OID 307088)
-- Dependencies: 1798
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: -
--

COPY users (user_id, version, user_name, email_address, user_password, system_password, system_password_validity, is_new, creator_id, next_action_after_login, person_id, creation_time, system_organization_id) FROM stdin;
ff9dda9b-6646-468f-bf60-1c42ef2f2889	0	supervisor	mnachev@gmail.com	917ecabdd1e761a698bab56f2bf84b55c6ed1f	\N	\N	f	\N	\N	ced27fbe-fb72-4918-8f56-9e41b6dec8cc	2009-09-23 14:36:29.564+03	6b77867c-3bca-4fa9-9b75-540b4f0546cc
dfe6f271-a7c6-4785-b2ff-5477931febca	0	miro	miro@space-comm.com	a128edd753e370711a458aa3e83fdb70c17412ca	\N	\N	f	\N	\N	549fe6ba-e2cd-4da5-bceb-ce3da95b80e4	2009-09-23 14:38:06.925+03	6b77867c-3bca-4fa9-9b75-540b4f0546cc
\.


--
-- TOC entry 3021 (class 0 OID 307096)
-- Dependencies: 1799
-- Data for Name: uuid_test; Type: TABLE DATA; Schema: public; Owner: -
--

COPY uuid_test (test_id, test_name) FROM stdin;
\.


--
-- TOC entry 3022 (class 0 OID 307099)
-- Dependencies: 1800
-- Data for Name: virtual_products; Type: TABLE DATA; Schema: public; Owner: -
--

COPY virtual_products (product_id, product_type, parent_id) FROM stdin;
\.


--
-- TOC entry 3023 (class 0 OID 307102)
-- Dependencies: 1801
-- Data for Name: warehouse_products; Type: TABLE DATA; Schema: public; Owner: -
--

COPY warehouse_products (warehouse_product_id, default_quantity, delivery_time, maximum_quantity, minimum_quantity, notes, ordered_quantity, parent_id, purchase_price, quantity_due, quantity_in_stock, reserved_quantity, sale_price, sold_quantity, product_id, warehouse_id, ordereddeliverytime) FROM stdin;
\.


--
-- TOC entry 3024 (class 0 OID 307105)
-- Dependencies: 1802
-- Data for Name: warehouses; Type: TABLE DATA; Schema: public; Owner: -
--

COPY warehouses (warehouse_id, parent_id, address_id, description, index, warehouseman_id) FROM stdin;
\.


--
-- TOC entry 2171 (class 2606 OID 307143)
-- Dependencies: 1714 1714
-- Name: business_partners_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY business_partners
    ADD CONSTRAINT business_partners_pkey PRIMARY KEY (business_partner_id);


--
-- TOC entry 2181 (class 2606 OID 307145)
-- Dependencies: 1717 1717
-- Name: cash_reconcile_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY cash_reconcile
    ADD CONSTRAINT cash_reconcile_pkey PRIMARY KEY (cash_reconcile_id);


--
-- TOC entry 2223 (class 2606 OID 307149)
-- Dependencies: 1731 1731
-- Name: customer_discount_items_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customer_discount_items
    ADD CONSTRAINT customer_discount_items_pkey PRIMARY KEY (customer_discount_item_id);


--
-- TOC entry 2237 (class 2606 OID 307151)
-- Dependencies: 1735 1735
-- Name: customer_payment_match_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customer_payment_match
    ADD CONSTRAINT customer_payment_match_pkey PRIMARY KEY (customer_payment_match_id);


--
-- TOC entry 2239 (class 2606 OID 307153)
-- Dependencies: 1736 1736
-- Name: customer_payments_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customer_payments
    ADD CONSTRAINT customer_payments_pkey PRIMARY KEY (payment_id);


--
-- TOC entry 2378 (class 2606 OID 307155)
-- Dependencies: 1784 1784
-- Name: invoice_item_link_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY sales_invoice_item_link
    ADD CONSTRAINT invoice_item_link_pkey PRIMARY KEY (id);


--
-- TOC entry 2380 (class 2606 OID 307157)
-- Dependencies: 1785 1785
-- Name: invoice_items_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY sales_invoice_items
    ADD CONSTRAINT invoice_items_pkey PRIMARY KEY (invoice_item_id);


--
-- TOC entry 2382 (class 2606 OID 307159)
-- Dependencies: 1786 1786
-- Name: invoices_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT invoices_pkey PRIMARY KEY (invoice_id);


--
-- TOC entry 2297 (class 2606 OID 307161)
-- Dependencies: 1756 1756
-- Name: order_confirmation_items_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY order_confirmation_items
    ADD CONSTRAINT order_confirmation_items_pkey PRIMARY KEY (confirmation_item_id);


--
-- TOC entry 2299 (class 2606 OID 307163)
-- Dependencies: 1757 1757
-- Name: order_confirmations_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT order_confirmations_pkey PRIMARY KEY (order_confirmation_id);


--
-- TOC entry 2301 (class 2606 OID 307165)
-- Dependencies: 1758 1758
-- Name: order_item_match_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY order_item_match
    ADD CONSTRAINT order_item_match_pkey PRIMARY KEY (id);


--
-- TOC entry 2305 (class 2606 OID 307167)
-- Dependencies: 1760 1760
-- Name: organizations_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT organizations_pkey PRIMARY KEY (organization_id);


--
-- TOC entry 2312 (class 2606 OID 307169)
-- Dependencies: 1762 1762
-- Name: pattern_mask_formats_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pattern_mask_formats
    ADD CONSTRAINT pattern_mask_formats_pkey PRIMARY KEY (pattern_mask_format_id);


--
-- TOC entry 2314 (class 2606 OID 307171)
-- Dependencies: 1763 1763
-- Name: persons_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT persons_pkey PRIMARY KEY (person_id);


--
-- TOC entry 2136 (class 2606 OID 307173)
-- Dependencies: 1704 1704
-- Name: pk_addresses; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT pk_addresses PRIMARY KEY (address_id);


--
-- TOC entry 2139 (class 2606 OID 307175)
-- Dependencies: 1705 1705
-- Name: pk_assembling_categories; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_categories
    ADD CONSTRAINT pk_assembling_categories PRIMARY KEY (assembling_category_id);


--
-- TOC entry 2145 (class 2606 OID 307177)
-- Dependencies: 1706 1706
-- Name: pk_assembling_messages; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_messages
    ADD CONSTRAINT pk_assembling_messages PRIMARY KEY (message_id);


--
-- TOC entry 2149 (class 2606 OID 307179)
-- Dependencies: 1707 1707
-- Name: pk_assembling_schema_item_values; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schema_item_values
    ADD CONSTRAINT pk_assembling_schema_item_values PRIMARY KEY (item_value_id);


--
-- TOC entry 2151 (class 2606 OID 307181)
-- Dependencies: 1708 1708
-- Name: pk_assembling_schema_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT pk_assembling_schema_items PRIMARY KEY (item_id);


--
-- TOC entry 2153 (class 2606 OID 307183)
-- Dependencies: 1709 1709
-- Name: pk_assembling_schemas; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT pk_assembling_schemas PRIMARY KEY (product_id);


--
-- TOC entry 2159 (class 2606 OID 307185)
-- Dependencies: 1710 1710
-- Name: pk_bank_details; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT pk_bank_details PRIMARY KEY (bank_detail_id);


--
-- TOC entry 2161 (class 2606 OID 309957)
-- Dependencies: 1711 1711
-- Name: pk_banknote_quantity; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY banknote_quantity
    ADD CONSTRAINT pk_banknote_quantity PRIMARY KEY (banknote_quantity_id);


--
-- TOC entry 2165 (class 2606 OID 307187)
-- Dependencies: 1712 1712 1712 1712
-- Name: pk_business_document_status_log; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY business_document_status_log
    ADD CONSTRAINT pk_business_document_status_log PRIMARY KEY (document_id, document_status_id, action_time);


--
-- TOC entry 2173 (class 2606 OID 307189)
-- Dependencies: 1715 1715
-- Name: pk_business_unit_addresses; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY business_unit_addresses
    ADD CONSTRAINT pk_business_unit_addresses PRIMARY KEY (business_unit_address_id);


--
-- TOC entry 2178 (class 2606 OID 307191)
-- Dependencies: 1716 1716
-- Name: pk_business_units; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY business_units
    ADD CONSTRAINT pk_business_units PRIMARY KEY (business_unit_id);


--
-- TOC entry 2183 (class 2606 OID 307193)
-- Dependencies: 1718 1718
-- Name: pk_cash_reconcile_payment_summary; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY cash_reconcile_payment_summary
    ADD CONSTRAINT pk_cash_reconcile_payment_summary PRIMARY KEY (payment_summary_id);


--
-- TOC entry 2187 (class 2606 OID 307195)
-- Dependencies: 1719 1719
-- Name: pk_cities; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY cities
    ADD CONSTRAINT pk_cities PRIMARY KEY (city_id);


--
-- TOC entry 2191 (class 2606 OID 307197)
-- Dependencies: 1720 1720 1720
-- Name: pk_classified_objects; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classified_objects
    ADD CONSTRAINT pk_classified_objects PRIMARY KEY (classifier_id, classified_object_id);


--
-- TOC entry 2193 (class 2606 OID 307199)
-- Dependencies: 1721 1721 1721
-- Name: pk_classifier_applied_for_dot; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifier_applied_for_dot
    ADD CONSTRAINT pk_classifier_applied_for_dot PRIMARY KEY (classifier_id, data_object_type_id);


--
-- TOC entry 2195 (class 2606 OID 307201)
-- Dependencies: 1722 1722
-- Name: pk_classifier_groups; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifier_groups
    ADD CONSTRAINT pk_classifier_groups PRIMARY KEY (classifier_group_id);


--
-- TOC entry 2197 (class 2606 OID 307203)
-- Dependencies: 1723 1723
-- Name: pk_classifiers; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT pk_classifiers PRIMARY KEY (classifier_id);


--
-- TOC entry 2201 (class 2606 OID 307205)
-- Dependencies: 1724 1724
-- Name: pk_communication_contacts; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT pk_communication_contacts PRIMARY KEY (communication_contact_id);


--
-- TOC entry 2204 (class 2606 OID 307207)
-- Dependencies: 1725 1725
-- Name: pk_complex_product_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT pk_complex_product_items PRIMARY KEY (complex_product_item_id);


--
-- TOC entry 2206 (class 2606 OID 307209)
-- Dependencies: 1726 1726
-- Name: pk_complex_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY complex_products
    ADD CONSTRAINT pk_complex_products PRIMARY KEY (product_id);


--
-- TOC entry 2208 (class 2606 OID 307211)
-- Dependencies: 1727 1727
-- Name: pk_contact_persons; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT pk_contact_persons PRIMARY KEY (contact_person_id);


--
-- TOC entry 2212 (class 2606 OID 307213)
-- Dependencies: 1728 1728
-- Name: pk_countries; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY countries
    ADD CONSTRAINT pk_countries PRIMARY KEY (country_id);


--
-- TOC entry 2217 (class 2606 OID 307215)
-- Dependencies: 1729 1729 1729 1729 1729
-- Name: pk_currency_exchange_rates; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY currency_exchange_rates
    ADD CONSTRAINT pk_currency_exchange_rates PRIMARY KEY (organization_id, valid_from, from_currency_id, to_currency_id);


--
-- TOC entry 2219 (class 2606 OID 307147)
-- Dependencies: 1730 1730
-- Name: pk_currency_nominal; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY currency_nominal
    ADD CONSTRAINT pk_currency_nominal PRIMARY KEY (currency_nominal_id);


--
-- TOC entry 2225 (class 2606 OID 307217)
-- Dependencies: 1732 1732
-- Name: pk_customer_discount_items_by_categories; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customer_discount_items_by_categories
    ADD CONSTRAINT pk_customer_discount_items_by_categories PRIMARY KEY (customer_discount_item_id);


--
-- TOC entry 2229 (class 2606 OID 307219)
-- Dependencies: 1733 1733
-- Name: pk_customer_discount_items_by_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customer_discount_items_by_products
    ADD CONSTRAINT pk_customer_discount_items_by_products PRIMARY KEY (customer_discount_item_id);


--
-- TOC entry 2233 (class 2606 OID 307221)
-- Dependencies: 1734 1734
-- Name: pk_customer_discounts; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customer_discounts
    ADD CONSTRAINT pk_customer_discounts PRIMARY KEY (customer_discount_id);


--
-- TOC entry 2241 (class 2606 OID 307223)
-- Dependencies: 1737 1737
-- Name: pk_data_object_details; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_details
    ADD CONSTRAINT pk_data_object_details PRIMARY KEY (data_object_id);


--
-- TOC entry 2245 (class 2606 OID 307225)
-- Dependencies: 1738 1738
-- Name: pk_data_object_links; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_links
    ADD CONSTRAINT pk_data_object_links PRIMARY KEY (data_object_link_id);


--
-- TOC entry 2249 (class 2606 OID 307227)
-- Dependencies: 1739 1739 1739 1739 1739
-- Name: pk_data_object_permissions; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_permissions
    ADD CONSTRAINT pk_data_object_permissions PRIMARY KEY (organization_id, data_object_id, user_right_type_id, permission_id);


--
-- TOC entry 2251 (class 2606 OID 307229)
-- Dependencies: 1740 1740 1740 1740 1740
-- Name: pk_data_object_type_permissions; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_type_permissions
    ADD CONSTRAINT pk_data_object_type_permissions PRIMARY KEY (organization_id, data_object_type_id, user_right_type_id, permission_id);


--
-- TOC entry 2253 (class 2606 OID 307231)
-- Dependencies: 1741 1741
-- Name: pk_data_object_types; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_types
    ADD CONSTRAINT pk_data_object_types PRIMARY KEY (data_object_type_id);


--
-- TOC entry 2257 (class 2606 OID 307233)
-- Dependencies: 1742 1742
-- Name: pk_data_objects; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT pk_data_objects PRIMARY KEY (data_object_id);


--
-- TOC entry 2259 (class 2606 OID 307235)
-- Dependencies: 1743 1743 1743 1743
-- Name: pk_db_properties; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY db_properties
    ADD CONSTRAINT pk_db_properties PRIMARY KEY (access_level, related_object_id, property_key);


--
-- TOC entry 2261 (class 2606 OID 307237)
-- Dependencies: 1744 1744
-- Name: pk_delivery_certificate_assignments; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY delivery_certificate_assignments
    ADD CONSTRAINT pk_delivery_certificate_assignments PRIMARY KEY (delivery_certificate_id);


--
-- TOC entry 2263 (class 2606 OID 307239)
-- Dependencies: 1745 1745
-- Name: pk_delivery_certificate_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT pk_delivery_certificate_items PRIMARY KEY (certificate_item_id);


--
-- TOC entry 2265 (class 2606 OID 307241)
-- Dependencies: 1746 1746 1746
-- Name: pk_delivery_certificate_serial_numbers; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY delivery_certificate_serial_numbers
    ADD CONSTRAINT pk_delivery_certificate_serial_numbers PRIMARY KEY (certificate_item_id, serial_number);


--
-- TOC entry 2273 (class 2606 OID 307243)
-- Dependencies: 1747 1747
-- Name: pk_delivery_certificates; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT pk_delivery_certificates PRIMARY KEY (delivery_certificate_id);


--
-- TOC entry 2167 (class 2606 OID 307245)
-- Dependencies: 1713 1713
-- Name: pk_document_entities; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY business_documents
    ADD CONSTRAINT pk_document_entities PRIMARY KEY (document_id);


--
-- TOC entry 2277 (class 2606 OID 307247)
-- Dependencies: 1748 1748 1748
-- Name: pk_entity_sequences; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY entity_sequences
    ADD CONSTRAINT pk_entity_sequences PRIMARY KEY (entity_id, data_object_type_id);


--
-- TOC entry 2279 (class 2606 OID 307249)
-- Dependencies: 1749 1749
-- Name: pk_enum_classes; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY enum_classes
    ADD CONSTRAINT pk_enum_classes PRIMARY KEY (enum_class_id);


--
-- TOC entry 2283 (class 2606 OID 307251)
-- Dependencies: 1750 1750 1750
-- Name: pk_expressions; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY expressions
    ADD CONSTRAINT pk_expressions PRIMARY KEY (organization_id, expression_key);


--
-- TOC entry 2285 (class 2606 OID 307253)
-- Dependencies: 1751 1751
-- Name: pk_goods_receipt_dc_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY goods_receipt_dc_items
    ADD CONSTRAINT pk_goods_receipt_dc_items PRIMARY KEY (receipt_item_id);


--
-- TOC entry 2287 (class 2606 OID 307255)
-- Dependencies: 1752 1752
-- Name: pk_goods_receipt_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY goods_receipt_items
    ADD CONSTRAINT pk_goods_receipt_items PRIMARY KEY (receipt_item_id);


--
-- TOC entry 2289 (class 2606 OID 307257)
-- Dependencies: 1753 1753
-- Name: pk_goods_receipt_pi_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY goods_receipt_pi_items
    ADD CONSTRAINT pk_goods_receipt_pi_items PRIMARY KEY (receipt_item_id);


--
-- TOC entry 2291 (class 2606 OID 307259)
-- Dependencies: 1754 1754
-- Name: pk_goods_receipts; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY goods_receipts
    ADD CONSTRAINT pk_goods_receipts PRIMARY KEY (goods_receipt_id);


--
-- TOC entry 2293 (class 2606 OID 307261)
-- Dependencies: 1755 1755
-- Name: pk_job_titles; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY job_titles
    ADD CONSTRAINT pk_job_titles PRIMARY KEY (job_title_id);


--
-- TOC entry 2303 (class 2606 OID 307263)
-- Dependencies: 1759 1759
-- Name: pk_organization_configurations; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY organization_configurations
    ADD CONSTRAINT pk_organization_configurations PRIMARY KEY (organization_id);


--
-- TOC entry 2308 (class 2606 OID 307265)
-- Dependencies: 1761 1761
-- Name: pk_passports; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT pk_passports PRIMARY KEY (passport_id);


--
-- TOC entry 2435 (class 2606 OID 310017)
-- Dependencies: 1817 1817
-- Name: pk_personal_communication_contacts; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY personal_communication_contacts
    ADD CONSTRAINT pk_personal_communication_contacts PRIMARY KEY (personal_communication_contact_id);


--
-- TOC entry 2318 (class 2606 OID 307267)
-- Dependencies: 1764 1764
-- Name: pk_position_types; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY position_types
    ADD CONSTRAINT pk_position_types PRIMARY KEY (position_type_id);


--
-- TOC entry 2321 (class 2606 OID 307269)
-- Dependencies: 1765 1765
-- Name: pk_pricelist_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pricelist_items
    ADD CONSTRAINT pk_pricelist_items PRIMARY KEY (item_id);


--
-- TOC entry 2323 (class 2606 OID 307271)
-- Dependencies: 1766 1766
-- Name: pk_pricelists; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pricelists
    ADD CONSTRAINT pk_pricelists PRIMARY KEY (pricelist_id);


--
-- TOC entry 2325 (class 2606 OID 307273)
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
-- TOC entry 2337 (class 2606 OID 307279)
-- Dependencies: 1770 1770
-- Name: pk_product_categories; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT pk_product_categories PRIMARY KEY (product_category_id);


--
-- TOC entry 2345 (class 2606 OID 307281)
-- Dependencies: 1772 1772 1772
-- Name: pk_product_suppliers; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT pk_product_suppliers PRIMARY KEY (product_id, supplier_id);


--
-- TOC entry 2390 (class 2606 OID 307283)
-- Dependencies: 1789 1789
-- Name: pk_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT pk_products PRIMARY KEY (product_id);


--
-- TOC entry 2347 (class 2606 OID 307285)
-- Dependencies: 1773 1773
-- Name: pk_products1; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY products
    ADD CONSTRAINT pk_products1 PRIMARY KEY (product_id);


--
-- TOC entry 2353 (class 2606 OID 307287)
-- Dependencies: 1774 1774
-- Name: pk_purchase_invoice_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT pk_purchase_invoice_items PRIMARY KEY (invoice_item_id);


--
-- TOC entry 2355 (class 2606 OID 307289)
-- Dependencies: 1775 1775
-- Name: pk_purchase_invoices; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY purchase_invoices
    ADD CONSTRAINT pk_purchase_invoices PRIMARY KEY (invoice_id);


--
-- TOC entry 2357 (class 2606 OID 307291)
-- Dependencies: 1776 1776
-- Name: pk_purchase_order_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT pk_purchase_order_items PRIMARY KEY (order_item_id);


--
-- TOC entry 2361 (class 2606 OID 307293)
-- Dependencies: 1778 1778
-- Name: pk_real_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY real_products
    ADD CONSTRAINT pk_real_products PRIMARY KEY (product_id);


--
-- TOC entry 2365 (class 2606 OID 307295)
-- Dependencies: 1779 1779
-- Name: pk_receipt_certificate_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT pk_receipt_certificate_items PRIMARY KEY (certificate_item_id);


--
-- TOC entry 2367 (class 2606 OID 307297)
-- Dependencies: 1780 1780 1780
-- Name: pk_receipt_certificate_serial_numbers; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY receipt_certificate_serial_numbers
    ADD CONSTRAINT pk_receipt_certificate_serial_numbers PRIMARY KEY (certificate_item_id, serial_number);


--
-- TOC entry 2369 (class 2606 OID 307299)
-- Dependencies: 1781 1781
-- Name: pk_receipt_certificates; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT pk_receipt_certificates PRIMARY KEY (receipt_certificate_id);


--
-- TOC entry 2373 (class 2606 OID 307301)
-- Dependencies: 1782 1782
-- Name: pk_registration_codes; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY registration_codes
    ADD CONSTRAINT pk_registration_codes PRIMARY KEY (registration_code);


--
-- TOC entry 2376 (class 2606 OID 307303)
-- Dependencies: 1783 1783
-- Name: pk_resource_bundle; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY resource_bundle
    ADD CONSTRAINT pk_resource_bundle PRIMARY KEY (resource_id);


--
-- TOC entry 2384 (class 2606 OID 307305)
-- Dependencies: 1787 1787
-- Name: pk_security_roles; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY security_roles
    ADD CONSTRAINT pk_security_roles PRIMARY KEY (security_role_id);


--
-- TOC entry 2392 (class 2606 OID 307307)
-- Dependencies: 1790 1790
-- Name: pk_team_members; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY team_members
    ADD CONSTRAINT pk_team_members PRIMARY KEY (team_member_id);


--
-- TOC entry 2396 (class 2606 OID 307309)
-- Dependencies: 1791 1791
-- Name: pk_teams; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY teams
    ADD CONSTRAINT pk_teams PRIMARY KEY (team_id);


--
-- TOC entry 2401 (class 2606 OID 307311)
-- Dependencies: 1792 1792
-- Name: pk_user_group_members; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY user_group_members
    ADD CONSTRAINT pk_user_group_members PRIMARY KEY (user_group_member_id);


--
-- TOC entry 2408 (class 2606 OID 307313)
-- Dependencies: 1794 1794
-- Name: pk_user_organizations; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY user_organizations
    ADD CONSTRAINT pk_user_organizations PRIMARY KEY (user_organization_id);


--
-- TOC entry 2412 (class 2606 OID 307315)
-- Dependencies: 1795 1795
-- Name: pk_user_rights; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY user_rights
    ADD CONSTRAINT pk_user_rights PRIMARY KEY (user_right_id);


--
-- TOC entry 2419 (class 2606 OID 307317)
-- Dependencies: 1797 1797
-- Name: pk_user_security_roles; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY user_security_roles
    ADD CONSTRAINT pk_user_security_roles PRIMARY KEY (user_security_role_id);


--
-- TOC entry 2423 (class 2606 OID 307319)
-- Dependencies: 1798 1798
-- Name: pk_users; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT pk_users PRIMARY KEY (user_id);


--
-- TOC entry 2427 (class 2606 OID 307321)
-- Dependencies: 1799 1799
-- Name: pk_uuid_test; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY uuid_test
    ADD CONSTRAINT pk_uuid_test PRIMARY KEY (test_id);


--
-- TOC entry 2429 (class 2606 OID 307323)
-- Dependencies: 1800 1800
-- Name: pk_virtual_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY virtual_products
    ADD CONSTRAINT pk_virtual_products PRIMARY KEY (product_id);


--
-- TOC entry 2433 (class 2606 OID 307325)
-- Dependencies: 1802 1802
-- Name: pk_warehouses; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY warehouses
    ADD CONSTRAINT pk_warehouses PRIMARY KEY (warehouse_id);


--
-- TOC entry 2341 (class 2606 OID 307327)
-- Dependencies: 1771 1771
-- Name: product_pricing_value_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY product_percent_values
    ADD CONSTRAINT product_pricing_value_pkey PRIMARY KEY (percent_value_id);


--
-- TOC entry 2359 (class 2606 OID 307329)
-- Dependencies: 1777 1777
-- Name: purchase_orders_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT purchase_orders_pkey PRIMARY KEY (purchase_order_id);


--
-- TOC entry 2141 (class 2606 OID 307331)
-- Dependencies: 1705 1705 1705 1705
-- Name: uk_assembling_categories_by_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_categories
    ADD CONSTRAINT uk_assembling_categories_by_code UNIQUE (parent_id, parent_category_id, category_code);


--
-- TOC entry 2143 (class 2606 OID 307333)
-- Dependencies: 1705 1705 1705 1705
-- Name: uk_assembling_categories_by_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_categories
    ADD CONSTRAINT uk_assembling_categories_by_name UNIQUE (parent_id, parent_category_id, category_name);


--
-- TOC entry 2147 (class 2606 OID 307335)
-- Dependencies: 1706 1706 1706
-- Name: uk_assembling_messages_org_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_messages
    ADD CONSTRAINT uk_assembling_messages_org_code UNIQUE (organization_id, message_code);


--
-- TOC entry 2155 (class 2606 OID 307337)
-- Dependencies: 1709 1709 1709
-- Name: uk_assembling_schemas_by_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT uk_assembling_schemas_by_code UNIQUE (category_id, schema_code);


--
-- TOC entry 2157 (class 2606 OID 307339)
-- Dependencies: 1709 1709 1709
-- Name: uk_assembling_schemas_by_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT uk_assembling_schemas_by_name UNIQUE (category_id, schema_name);


--
-- TOC entry 2163 (class 2606 OID 309974)
-- Dependencies: 1711 1711 1711
-- Name: uk_banknote_quantity_cash_reconcile_currency_nominal; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY banknote_quantity
    ADD CONSTRAINT uk_banknote_quantity_cash_reconcile_currency_nominal UNIQUE (cash_reconcile_id, currency_nominal_id);


--
-- TOC entry 2169 (class 2606 OID 307341)
-- Dependencies: 1713 1713 1713 1713
-- Name: uk_business_documents_publisher_doc_type_number; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY business_documents
    ADD CONSTRAINT uk_business_documents_publisher_doc_type_number UNIQUE (publisher_id, document_type_id, document_number);


--
-- TOC entry 2175 (class 2606 OID 307343)
-- Dependencies: 1715 1715 1715
-- Name: uk_business_unit_addresses; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY business_unit_addresses
    ADD CONSTRAINT uk_business_unit_addresses UNIQUE (business_unit_id, address_id);


--
-- TOC entry 2185 (class 2606 OID 309976)
-- Dependencies: 1718 1718 1718 1718
-- Name: uk_cash_reconcile_payment_summary; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY cash_reconcile_payment_summary
    ADD CONSTRAINT uk_cash_reconcile_payment_summary UNIQUE (cash_reconcile_id, payment_type_id, currency_id);


--
-- TOC entry 2199 (class 2606 OID 307347)
-- Dependencies: 1723 1723 1723
-- Name: uk_classifiers_parent_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT uk_classifiers_parent_code UNIQUE (parent_id, classifier_code);


--
-- TOC entry 2210 (class 2606 OID 307351)
-- Dependencies: 1727 1727 1727
-- Name: uk_contact_persons_address_person; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT uk_contact_persons_address_person UNIQUE (address_id, person_id);


--
-- TOC entry 2221 (class 2606 OID 309978)
-- Dependencies: 1730 1730 1730
-- Name: uk_currency_nominal; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY currency_nominal
    ADD CONSTRAINT uk_currency_nominal UNIQUE (currency_id, nominal_value);


--
-- TOC entry 2227 (class 2606 OID 307353)
-- Dependencies: 1732 1732 1732
-- Name: uk_customer_discount_items_by_categories; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customer_discount_items_by_categories
    ADD CONSTRAINT uk_customer_discount_items_by_categories UNIQUE (customer_discount_id, category_id);


--
-- TOC entry 2231 (class 2606 OID 307355)
-- Dependencies: 1733 1733 1733
-- Name: uk_customer_discount_items_by_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customer_discount_items_by_products
    ADD CONSTRAINT uk_customer_discount_items_by_products UNIQUE (customer_discount_id, product_id);


--
-- TOC entry 2235 (class 2606 OID 307357)
-- Dependencies: 1734 1734 1734
-- Name: uk_customer_discounts; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customer_discounts
    ADD CONSTRAINT uk_customer_discounts UNIQUE (organization_id, customer_id);


--
-- TOC entry 2243 (class 2606 OID 307359)
-- Dependencies: 1737 1737 1737
-- Name: uk_data_object_details_do_id_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_details
    ADD CONSTRAINT uk_data_object_details_do_id_code UNIQUE (data_object_id, detail_code);


--
-- TOC entry 2247 (class 2606 OID 307361)
-- Dependencies: 1738 1738 1738
-- Name: uk_data_object_links_parent_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_links
    ADD CONSTRAINT uk_data_object_links_parent_name UNIQUE (parent_id, link_name);


--
-- TOC entry 2275 (class 2606 OID 307363)
-- Dependencies: 1747 1747 1747
-- Name: uk_delivery_certificates_parent_cert_number; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT uk_delivery_certificates_parent_cert_number UNIQUE (parent_id, delivery_certificate_number);


--
-- TOC entry 2255 (class 2606 OID 307365)
-- Dependencies: 1741 1741
-- Name: uk_dot_data_object_type; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_types
    ADD CONSTRAINT uk_dot_data_object_type UNIQUE (data_object_type);


--
-- TOC entry 2281 (class 2606 OID 307367)
-- Dependencies: 1749 1749
-- Name: uk_enum_classes; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY enum_classes
    ADD CONSTRAINT uk_enum_classes UNIQUE (enum_class_name);


--
-- TOC entry 2295 (class 2606 OID 307369)
-- Dependencies: 1755 1755 1755
-- Name: uk_job_titles_bu_title; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY job_titles
    ADD CONSTRAINT uk_job_titles_bu_title UNIQUE (business_unit_id, job_title);


--
-- TOC entry 2310 (class 2606 OID 307371)
-- Dependencies: 1761 1761 1761 1761
-- Name: uk_passports_parent_type_number; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT uk_passports_parent_type_number UNIQUE (parent_id, passport_type_id, passport_number);


--
-- TOC entry 2437 (class 2606 OID 310019)
-- Dependencies: 1817 1817 1817
-- Name: uk_personal_communication_contacts; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY personal_communication_contacts
    ADD CONSTRAINT uk_personal_communication_contacts UNIQUE (contact_person_id, communication_contact_id);


--
-- TOC entry 2327 (class 2606 OID 307373)
-- Dependencies: 1767 1767 1767
-- Name: uk_privilege_categories_organization_category_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY privilege_categories
    ADD CONSTRAINT uk_privilege_categories_organization_category_name UNIQUE (organization_id, category_name);


--
-- TOC entry 2331 (class 2606 OID 307375)
-- Dependencies: 1768 1768 1768
-- Name: uk_privilege_roles_privilege_right; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY privilege_roles
    ADD CONSTRAINT uk_privilege_roles_privilege_right UNIQUE (privilege_id, access_right_id);


--
-- TOC entry 2335 (class 2606 OID 307377)
-- Dependencies: 1769 1769 1769
-- Name: uk_privileges_role_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY privileges
    ADD CONSTRAINT uk_privileges_role_name UNIQUE (security_role_id, privilege_name);


--
-- TOC entry 2339 (class 2606 OID 307379)
-- Dependencies: 1770 1770 1770
-- Name: uk_product_categories_parent_category_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT uk_product_categories_parent_category_name UNIQUE (parent_id, category_name);


--
-- TOC entry 2343 (class 2606 OID 307381)
-- Dependencies: 1771 1771 1771 1771
-- Name: uk_product_percent_values; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY product_percent_values
    ADD CONSTRAINT uk_product_percent_values UNIQUE (organization_id, value_type_id, value_name);


--
-- TOC entry 2349 (class 2606 OID 307383)
-- Dependencies: 1773 1773 1773
-- Name: uk_products_parent_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY products
    ADD CONSTRAINT uk_products_parent_code UNIQUE (parent_id, product_code);


--
-- TOC entry 2351 (class 2606 OID 307385)
-- Dependencies: 1773 1773 1773
-- Name: uk_products_parent_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY products
    ADD CONSTRAINT uk_products_parent_name UNIQUE (parent_id, product_name);


--
-- TOC entry 2363 (class 2606 OID 307387)
-- Dependencies: 1778 1778
-- Name: uk_real_products_by_simple_product; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY real_products
    ADD CONSTRAINT uk_real_products_by_simple_product UNIQUE (simple_product_id);


--
-- TOC entry 2371 (class 2606 OID 307389)
-- Dependencies: 1781 1781 1781
-- Name: uk_receipt_certificates_parent_cert_number; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT uk_receipt_certificates_parent_cert_number UNIQUE (parent_id, receipt_certificate_number);


--
-- TOC entry 2386 (class 2606 OID 307391)
-- Dependencies: 1787 1787 1787
-- Name: uk_security_roles_organization_role_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY security_roles
    ADD CONSTRAINT uk_security_roles_organization_role_name UNIQUE (organization_id, security_role_name);


--
-- TOC entry 2388 (class 2606 OID 307393)
-- Dependencies: 1788 1788
-- Name: uk_seq_id_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY sequence_identifiers
    ADD CONSTRAINT uk_seq_id_name UNIQUE (seq_id_name);


--
-- TOC entry 2394 (class 2606 OID 307395)
-- Dependencies: 1790 1790 1790
-- Name: uk_team_members_team_user; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY team_members
    ADD CONSTRAINT uk_team_members_team_user UNIQUE (team_id, user_organization_id);


--
-- TOC entry 2399 (class 2606 OID 307397)
-- Dependencies: 1791 1791 1791
-- Name: uk_teams_organization_team_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY teams
    ADD CONSTRAINT uk_teams_organization_team_name UNIQUE (organization_id, team_name);


--
-- TOC entry 2403 (class 2606 OID 307399)
-- Dependencies: 1792 1792 1792
-- Name: uk_user_group_members; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY user_group_members
    ADD CONSTRAINT uk_user_group_members UNIQUE (user_group_id, user_organization_id);


--
-- TOC entry 2410 (class 2606 OID 307401)
-- Dependencies: 1794 1794 1794
-- Name: uk_user_organizations; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY user_organizations
    ADD CONSTRAINT uk_user_organizations UNIQUE (user_id, organization_id);


--
-- TOC entry 2421 (class 2606 OID 307403)
-- Dependencies: 1797 1797 1797
-- Name: uk_user_security_roles_user_sr; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY user_security_roles
    ADD CONSTRAINT uk_user_security_roles_user_sr UNIQUE (user_organization_id, security_role_id);


--
-- TOC entry 2406 (class 2606 OID 307405)
-- Dependencies: 1793 1793
-- Name: user_groups_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY user_groups
    ADD CONSTRAINT user_groups_pkey PRIMARY KEY (user_group_id);


--
-- TOC entry 2417 (class 2606 OID 307407)
-- Dependencies: 1796 1796
-- Name: user_rights_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT user_rights_pkey PRIMARY KEY (user_right_id);


--
-- TOC entry 2431 (class 2606 OID 307409)
-- Dependencies: 1801 1801
-- Name: warehouse_products_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY warehouse_products
    ADD CONSTRAINT warehouse_products_pkey PRIMARY KEY (warehouse_product_id);


--
-- TOC entry 2266 (class 1259 OID 307411)
-- Dependencies: 1747
-- Name: fki_certificate_status; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX fki_certificate_status ON delivery_certificates USING btree (delivery_cert_status_id);


--
-- TOC entry 2267 (class 1259 OID 307412)
-- Dependencies: 1747
-- Name: fki_creator_branch; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX fki_creator_branch ON delivery_certificates USING btree (creator_branch_id);


--
-- TOC entry 2268 (class 1259 OID 307413)
-- Dependencies: 1747
-- Name: fki_creator_organization; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX fki_creator_organization ON delivery_certificates USING btree (creator_organization_id);


--
-- TOC entry 2269 (class 1259 OID 307414)
-- Dependencies: 1747
-- Name: fki_delivery_certificates_recipient; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX fki_delivery_certificates_recipient ON delivery_certificates USING btree (recipient_id);


--
-- TOC entry 2270 (class 1259 OID 307415)
-- Dependencies: 1747
-- Name: fki_forwarder_address_constraint; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX fki_forwarder_address_constraint ON delivery_certificates USING btree (forwarder_branch_id);


--
-- TOC entry 2271 (class 1259 OID 307416)
-- Dependencies: 1747
-- Name: fki_recipient_address_constraint; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX fki_recipient_address_constraint ON delivery_certificates USING btree (recipient_branch_id);


--
-- TOC entry 2176 (class 1259 OID 326388)
-- Dependencies: 1716 1716
-- Name: idx_business_units_parent_child; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX idx_business_units_parent_child ON business_units USING btree (parent_business_unit_id, business_unit_id);


--
-- TOC entry 2179 (class 1259 OID 326389)
-- Dependencies: 1716 1716
-- Name: uidx_business_units_name; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uidx_business_units_name ON business_units USING btree (organization_id, lower((business_unit_name)::text));


--
-- TOC entry 2397 (class 1259 OID 307418)
-- Dependencies: 1791 1791
-- Name: uidx_teams_organization_team_name; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uidx_teams_organization_team_name ON teams USING btree (organization_id, team_name);


--
-- TOC entry 2404 (class 1259 OID 307419)
-- Dependencies: 1793 1793
-- Name: uidx_user_groups_organization_ugname; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uidx_user_groups_organization_ugname ON user_groups USING btree (organization_id, user_group_name);


--
-- TOC entry 2413 (class 1259 OID 307420)
-- Dependencies: 1795 1795 1795 1795 1795 1795 1795 1795 1795 1795
-- Name: uidx_user_rights; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uidx_user_rights ON user_rights USING btree (organization_id, owner_type_id, user_id, user_group_id, access_rights, excluded, data_object_type_id, data_object_id, permission_category_id, special_permission_id);


--
-- TOC entry 2137 (class 1259 OID 326390)
-- Dependencies: 1704 1704
-- Name: uix_addresses_business_partner_address_name; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_addresses_business_partner_address_name ON addresses USING btree (business_partner_id, lower((address_name)::text));


--
-- TOC entry 2188 (class 1259 OID 310011)
-- Dependencies: 1719 1719
-- Name: uix_cities_country_city_code; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_cities_country_city_code ON cities USING btree (country_id, lower((city_code)::text));


--
-- TOC entry 2189 (class 1259 OID 307421)
-- Dependencies: 1719 1719
-- Name: uix_cities_country_city_name; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_cities_country_city_name ON cities USING btree (country_id, lower((city_name)::text));


--
-- TOC entry 2202 (class 1259 OID 310012)
-- Dependencies: 1724 1724 1724
-- Name: uix_communication_contacts_parent_type_value_contact_person; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_communication_contacts_parent_type_value_contact_person ON communication_contacts USING btree (address_id, communication_type_id, lower((communication_value)::text));


--
-- TOC entry 2213 (class 1259 OID 310004)
-- Dependencies: 1728
-- Name: uix_countries_code_a2; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_countries_code_a2 ON countries USING btree (lower((country_code_a2)::text));


--
-- TOC entry 2214 (class 1259 OID 310005)
-- Dependencies: 1728
-- Name: uix_countries_code_a3; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_countries_code_a3 ON countries USING btree (lower((country_code_a3)::text));


--
-- TOC entry 2215 (class 1259 OID 307422)
-- Dependencies: 1728
-- Name: uix_countries_country_name; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_countries_country_name ON countries USING btree (lower((country_name)::text));


--
-- TOC entry 2306 (class 1259 OID 307423)
-- Dependencies: 1760 1760
-- Name: uix_organizations_parent_business_partner_organization_name; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_organizations_parent_business_partner_organization_name ON organizations USING btree (parent_business_partner_id, lower((organization_name)::text));


--
-- TOC entry 2315 (class 1259 OID 307424)
-- Dependencies: 1763 1763 1763 1763 1763 1763 1763
-- Name: uix_persons_names_birth_date_city; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_persons_names_birth_date_city ON persons USING btree (parent_business_partner_id, lower((first_name)::text), lower((last_name)::text), lower((second_name)::text), lower((extra_name)::text), birth_date, birth_place_city_id);


--
-- TOC entry 2316 (class 1259 OID 307425)
-- Dependencies: 1763 1763 1763
-- Name: uix_persons_personal_unuque_id; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_persons_personal_unuque_id ON persons USING btree (parent_business_partner_id, birth_place_country_id, lower((personal_unique_id)::text));


--
-- TOC entry 2319 (class 1259 OID 310040)
-- Dependencies: 1764 1764
-- Name: uix_position_types; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_position_types ON position_types USING btree (business_partner_id, lower((position_type_name)::text));


--
-- TOC entry 2374 (class 1259 OID 307426)
-- Dependencies: 1782
-- Name: uix_registration_codes_email_address; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_registration_codes_email_address ON registration_codes USING btree (lower((email_address)::text));


--
-- TOC entry 2424 (class 1259 OID 307427)
-- Dependencies: 1798
-- Name: uix_users_email_address; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_users_email_address ON users USING btree (lower((email_address)::text));


--
-- TOC entry 2425 (class 1259 OID 307428)
-- Dependencies: 1798
-- Name: uix_users_username; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_users_username ON users USING btree (lower((user_name)::text));


--
-- TOC entry 2414 (class 1259 OID 307429)
-- Dependencies: 1796
-- Name: user_group_idx; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX user_group_idx ON user_rights_old USING btree (user_group_id);


--
-- TOC entry 2415 (class 1259 OID 307430)
-- Dependencies: 1796
-- Name: user_idx; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX user_idx ON user_rights_old USING btree (user_id);


--
-- TOC entry 2438 (class 2606 OID 307431)
-- Dependencies: 1714 1704 2170
-- Name: addresses_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT addresses_parent_id_fkey FOREIGN KEY (business_partner_id) REFERENCES business_partners(business_partner_id) ON DELETE CASCADE;


--
-- TOC entry 2466 (class 2606 OID 307436)
-- Dependencies: 1783 2375 1710
-- Name: bank_details_currency_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT bank_details_currency_id_fkey FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2467 (class 2606 OID 307441)
-- Dependencies: 1704 2135 1710
-- Name: bank_details_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT bank_details_parent_id_fkey FOREIGN KEY (parent_id) REFERENCES addresses(address_id) ON DELETE CASCADE;


--
-- TOC entry 2510 (class 2606 OID 307446)
-- Dependencies: 1719 1728 2211
-- Name: cities_country_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cities
    ADD CONSTRAINT cities_country_id_fkey FOREIGN KEY (country_id) REFERENCES countries(country_id) ON DELETE CASCADE;


--
-- TOC entry 2512 (class 2606 OID 307451)
-- Dependencies: 1720 1742 2256
-- Name: classified_objects_classified_object_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classified_objects
    ADD CONSTRAINT classified_objects_classified_object_id_fkey FOREIGN KEY (classified_object_id) REFERENCES data_objects(data_object_id) ON DELETE CASCADE;


--
-- TOC entry 2516 (class 2606 OID 307456)
-- Dependencies: 2196 1723 1721
-- Name: classifier_applied_for_dot_classifier_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifier_applied_for_dot
    ADD CONSTRAINT classifier_applied_for_dot_classifier_id_fkey FOREIGN KEY (classifier_id) REFERENCES classifiers(classifier_id) ON DELETE CASCADE;


--
-- TOC entry 2524 (class 2606 OID 307466)
-- Dependencies: 1704 2135 1724
-- Name: communication_contacts_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT communication_contacts_parent_id_fkey FOREIGN KEY (address_id) REFERENCES addresses(address_id) ON DELETE CASCADE;


--
-- TOC entry 2535 (class 2606 OID 307471)
-- Dependencies: 1763 2313 1727
-- Name: contact_persons_contact_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT contact_persons_contact_id_fkey FOREIGN KEY (person_id) REFERENCES persons(person_id) ON DELETE CASCADE;


--
-- TOC entry 2536 (class 2606 OID 307476)
-- Dependencies: 1727 1704 2135
-- Name: contact_persons_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT contact_persons_parent_id_fkey FOREIGN KEY (address_id) REFERENCES addresses(address_id) ON DELETE CASCADE;


--
-- TOC entry 2511 (class 2606 OID 307481)
-- Dependencies: 1742 1719 2256
-- Name: data_object_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cities
    ADD CONSTRAINT data_object_fk FOREIGN KEY (city_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2894 (class 2606 OID 307486)
-- Dependencies: 1796 2252 1741
-- Name: data_object_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT data_object_type FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id) ON DELETE CASCADE;


--
-- TOC entry 2895 (class 2606 OID 307491)
-- Dependencies: 1742 1796 2256
-- Name: data_objects; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT data_objects FOREIGN KEY (data_object_id) REFERENCES data_objects(data_object_id) ON DELETE CASCADE;


--
-- TOC entry 2792 (class 2606 OID 307496)
-- Dependencies: 1749 1783 2278
-- Name: fk11ef5dd39219a9be; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY resource_bundle
    ADD CONSTRAINT fk11ef5dd39219a9be FOREIGN KEY (enum_class_id) REFERENCES enum_classes(enum_class_id);


--
-- TOC entry 2445 (class 2606 OID 307501)
-- Dependencies: 1760 1706 2304
-- Name: fk2415657c51b04573; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_messages
    ADD CONSTRAINT fk2415657c51b04573 FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2802 (class 2606 OID 307506)
-- Dependencies: 2170 1714 1786
-- Name: fk25f222e6134fe2b0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e6134fe2b0 FOREIGN KEY (recipient_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2803 (class 2606 OID 307511)
-- Dependencies: 2375 1786 1783
-- Name: fk25f222e617174fab; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e617174fab FOREIGN KEY (transportation_method_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2804 (class 2606 OID 307516)
-- Dependencies: 1786 1783 2375
-- Name: fk25f222e61808129a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e61808129a FOREIGN KEY (doc_delivery_method_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2805 (class 2606 OID 307521)
-- Dependencies: 1786 1704 2135
-- Name: fk25f222e627a66c93; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e627a66c93 FOREIGN KEY (branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2806 (class 2606 OID 307526)
-- Dependencies: 1783 2375 1786
-- Name: fk25f222e63aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e63aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2807 (class 2606 OID 307531)
-- Dependencies: 2375 1786 1783
-- Name: fk25f222e63dc9408c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e63dc9408c FOREIGN KEY (payment_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2808 (class 2606 OID 307536)
-- Dependencies: 1786 2375 1783
-- Name: fk25f222e646685c7a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e646685c7a FOREIGN KEY (delivery_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2809 (class 2606 OID 307541)
-- Dependencies: 1763 2313 1786
-- Name: fk25f222e64da6f8bc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e64da6f8bc FOREIGN KEY (creator_id) REFERENCES persons(person_id);


--
-- TOC entry 2810 (class 2606 OID 307546)
-- Dependencies: 2170 1786 1714
-- Name: fk25f222e66d20f4c9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e66d20f4c9 FOREIGN KEY (shippingagent_partner_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2811 (class 2606 OID 307551)
-- Dependencies: 2207 1727 1786
-- Name: fk25f222e67ebcc009; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e67ebcc009 FOREIGN KEY (supplier_contact_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2812 (class 2606 OID 307556)
-- Dependencies: 2375 1783 1786
-- Name: fk25f222e696e3ba71; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e696e3ba71 FOREIGN KEY (payment_terms_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2813 (class 2606 OID 307561)
-- Dependencies: 1786 1783 2375
-- Name: fk25f222e69a24d298; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e69a24d298 FOREIGN KEY (deliverystatus_resource_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2814 (class 2606 OID 307566)
-- Dependencies: 2375 1786 1783
-- Name: fk25f222e69c49320d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e69c49320d FOREIGN KEY (status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2815 (class 2606 OID 307571)
-- Dependencies: 1727 1786 2207
-- Name: fk25f222e69ff294dc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e69ff294dc FOREIGN KEY (attendee_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2816 (class 2606 OID 307576)
-- Dependencies: 1783 1786 2375
-- Name: fk25f222e6a94f3ab3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e6a94f3ab3 FOREIGN KEY (invoice_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2817 (class 2606 OID 307581)
-- Dependencies: 1783 1786 2375
-- Name: fk25f222e6b07d659a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e6b07d659a FOREIGN KEY (vat_condition_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2818 (class 2606 OID 307586)
-- Dependencies: 1763 1786 2313
-- Name: fk25f222e6fd5b3613; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e6fd5b3613 FOREIGN KEY (sender_id) REFERENCES persons(person_id);


--
-- TOC entry 2442 (class 2606 OID 307591)
-- Dependencies: 1705 1705 2138
-- Name: fk265f5e4cdf9c931a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_categories
    ADD CONSTRAINT fk265f5e4cdf9c931a FOREIGN KEY (parent_category_id) REFERENCES assembling_categories(assembling_category_id);


--
-- TOC entry 2600 (class 2606 OID 307596)
-- Dependencies: 1742 1743 2256
-- Name: fk26dbbb94399bdd69; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY db_properties
    ADD CONSTRAINT fk26dbbb94399bdd69 FOREIGN KEY (related_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2527 (class 2606 OID 307601)
-- Dependencies: 1783 1725 2375
-- Name: fk281fab21a2454cf2; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT fk281fab21a2454cf2 FOREIGN KEY (applied_algorithm_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2528 (class 2606 OID 307606)
-- Dependencies: 1726 1725 2205
-- Name: fk281fab21d06049d2; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT fk281fab21d06049d2 FOREIGN KEY (complex_product_id) REFERENCES complex_products(product_id);


--
-- TOC entry 2529 (class 2606 OID 307611)
-- Dependencies: 1773 1725 2346
-- Name: fk281fab21f10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT fk281fab21f10b9721 FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2796 (class 2606 OID 307616)
-- Dependencies: 1783 1785 2375
-- Name: fk326ab82e1ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoice_items
    ADD CONSTRAINT fk326ab82e1ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2797 (class 2606 OID 307621)
-- Dependencies: 1802 1785 2432
-- Name: fk326ab82e9f1800e1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoice_items
    ADD CONSTRAINT fk326ab82e9f1800e1 FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2798 (class 2606 OID 307626)
-- Dependencies: 2346 1785 1773
-- Name: fk326ab82ef10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoice_items
    ADD CONSTRAINT fk326ab82ef10b9721 FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2669 (class 2606 OID 307631)
-- Dependencies: 1757 1704 2135
-- Name: fk327473ad27a66c93; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk327473ad27a66c93 FOREIGN KEY (branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2670 (class 2606 OID 307636)
-- Dependencies: 2375 1757 1783
-- Name: fk327473ad3aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk327473ad3aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2671 (class 2606 OID 307641)
-- Dependencies: 1714 1757 2170
-- Name: fk327473ad5aa049f4; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk327473ad5aa049f4 FOREIGN KEY (supplier_partner_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2672 (class 2606 OID 307646)
-- Dependencies: 1727 1757 2207
-- Name: fk327473ad7ebcc009; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk327473ad7ebcc009 FOREIGN KEY (supplier_contact_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2673 (class 2606 OID 307651)
-- Dependencies: 1783 1757 2375
-- Name: fk327473ada97faa1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk327473ada97faa1 FOREIGN KEY (document_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2549 (class 2606 OID 307656)
-- Dependencies: 1734 1732 2232
-- Name: fk34a6c58834ee818e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items_by_categories
    ADD CONSTRAINT fk34a6c58834ee818e FOREIGN KEY (customer_discount_id) REFERENCES customer_discounts(customer_discount_id);


--
-- TOC entry 2550 (class 2606 OID 307661)
-- Dependencies: 2336 1732 1770
-- Name: fk34a6c5886e2f83d0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items_by_categories
    ADD CONSTRAINT fk34a6c5886e2f83d0 FOREIGN KEY (category_id) REFERENCES product_categories(product_category_id);


--
-- TOC entry 2551 (class 2606 OID 307666)
-- Dependencies: 1732 1731 2222
-- Name: fk34a6c588d3502ad3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items_by_categories
    ADD CONSTRAINT fk34a6c588d3502ad3 FOREIGN KEY (customer_discount_item_id) REFERENCES customer_discount_items(customer_discount_item_id);


--
-- TOC entry 2468 (class 2606 OID 307671)
-- Dependencies: 2135 1710 1704
-- Name: fk363aa33f2f5fd250; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk363aa33f2f5fd250 FOREIGN KEY (bank_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2469 (class 2606 OID 307676)
-- Dependencies: 1783 1710 2375
-- Name: fk363aa33f3aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk363aa33f3aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2470 (class 2606 OID 307681)
-- Dependencies: 1760 1710 2304
-- Name: fk363aa33fee88a3ca; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk363aa33fee88a3ca FOREIGN KEY (bank_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2471 (class 2606 OID 307686)
-- Dependencies: 1763 1710 2313
-- Name: fk363aa33ff339b22b; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk363aa33ff339b22b FOREIGN KEY (bank_contact_id) REFERENCES persons(person_id);


--
-- TOC entry 2611 (class 2606 OID 307691)
-- Dependencies: 1714 1747 2170
-- Name: fk3edb4c27134fe2b0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c27134fe2b0 FOREIGN KEY (recipient_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2612 (class 2606 OID 307696)
-- Dependencies: 1763 1747 2313
-- Name: fk3edb4c27157c075; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c27157c075 FOREIGN KEY (forwarder_contact_id) REFERENCES persons(person_id);


--
-- TOC entry 2613 (class 2606 OID 307701)
-- Dependencies: 1747 2135 1704
-- Name: fk3edb4c273364a040; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c273364a040 FOREIGN KEY (creator_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2614 (class 2606 OID 307706)
-- Dependencies: 2375 1747 1783
-- Name: fk3edb4c2736e2c55d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c2736e2c55d FOREIGN KEY (delivery_cert_status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2615 (class 2606 OID 307711)
-- Dependencies: 2207 1727 1747
-- Name: fk3edb4c273b8b059c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c273b8b059c FOREIGN KEY (recipient_contact_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2616 (class 2606 OID 307716)
-- Dependencies: 1783 2375 1747
-- Name: fk3edb4c2746dd317; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c2746dd317 FOREIGN KEY (delivery_cert_method_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2617 (class 2606 OID 307721)
-- Dependencies: 1747 1763 2313
-- Name: fk3edb4c274da6f8bc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c274da6f8bc FOREIGN KEY (creator_id) REFERENCES persons(person_id);


--
-- TOC entry 2618 (class 2606 OID 307726)
-- Dependencies: 1747 2135 1704
-- Name: fk3edb4c277c77cded; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c277c77cded FOREIGN KEY (recipient_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2619 (class 2606 OID 307731)
-- Dependencies: 1783 1747 2375
-- Name: fk3edb4c278a6109cb; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c278a6109cb FOREIGN KEY (delivery_cert_reason_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2620 (class 2606 OID 307736)
-- Dependencies: 1747 2432 1802
-- Name: fk3edb4c279f1800e1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c279f1800e1 FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2621 (class 2606 OID 307741)
-- Dependencies: 2304 1760 1747
-- Name: fk3edb4c27b4524360; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c27b4524360 FOREIGN KEY (creator_organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2622 (class 2606 OID 307746)
-- Dependencies: 2135 1704 1747
-- Name: fk3edb4c27e581d2c6; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c27e581d2c6 FOREIGN KEY (forwarder_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2623 (class 2606 OID 307751)
-- Dependencies: 1747 2304 1760
-- Name: fk3edb4c27f2569c14; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c27f2569c14 FOREIGN KEY (forwarder_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2691 (class 2606 OID 307756)
-- Dependencies: 1714 2170 1762
-- Name: fk40afd1582b1363d6; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pattern_mask_formats
    ADD CONSTRAINT fk40afd1582b1363d6 FOREIGN KEY (owner_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2579 (class 2606 OID 307761)
-- Dependencies: 2375 1783 1739
-- Name: fk40c96239b01192e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_permissions
    ADD CONSTRAINT fk40c96239b01192e FOREIGN KEY (user_right_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2580 (class 2606 OID 307766)
-- Dependencies: 2375 1739 1783
-- Name: fk40c96239c2559310; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_permissions
    ADD CONSTRAINT fk40c96239c2559310 FOREIGN KEY (permission_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2581 (class 2606 OID 307771)
-- Dependencies: 2256 1742 1739
-- Name: fk40c96239d741e28; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_permissions
    ADD CONSTRAINT fk40c96239d741e28 FOREIGN KEY (data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2799 (class 2606 OID 307776)
-- Dependencies: 1785 1783 2375
-- Name: fk46500e3b1ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoice_items
    ADD CONSTRAINT fk46500e3b1ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2800 (class 2606 OID 307781)
-- Dependencies: 1785 2432 1802
-- Name: fk46500e3b9f1800e1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoice_items
    ADD CONSTRAINT fk46500e3b9f1800e1 FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2801 (class 2606 OID 307786)
-- Dependencies: 2346 1785 1773
-- Name: fk46500e3bf10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoice_items
    ADD CONSTRAINT fk46500e3bf10b9721 FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2547 (class 2606 OID 307791)
-- Dependencies: 1731 2256 1742
-- Name: fk51a781c3c35b8d6c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items
    ADD CONSTRAINT fk51a781c3c35b8d6c FOREIGN KEY (customer_discount_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2716 (class 2606 OID 307796)
-- Dependencies: 1770 1770 2336
-- Name: fk5519b36c1c57732d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT fk5519b36c1c57732d FOREIGN KEY (parent_cat_id) REFERENCES product_categories(product_category_id);


--
-- TOC entry 2717 (class 2606 OID 307801)
-- Dependencies: 1762 1770 2311
-- Name: fk5519b36c7a956e19; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT fk5519b36c7a956e19 FOREIGN KEY (pattern_mask_format_id) REFERENCES pattern_mask_formats(pattern_mask_format_id);


--
-- TOC entry 2645 (class 2606 OID 307806)
-- Dependencies: 1783 2375 1752
-- Name: fk582b0dd01ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_items
    ADD CONSTRAINT fk582b0dd01ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2646 (class 2606 OID 307811)
-- Dependencies: 1754 1752 2290
-- Name: fk582b0dd04d7c32a1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_items
    ADD CONSTRAINT fk582b0dd04d7c32a1 FOREIGN KEY (goods_receipt_id) REFERENCES goods_receipts(goods_receipt_id);


--
-- TOC entry 2647 (class 2606 OID 307816)
-- Dependencies: 2256 1742 1752
-- Name: fk582b0dd0cf28c922; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_items
    ADD CONSTRAINT fk582b0dd0cf28c922 FOREIGN KEY (receipt_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2648 (class 2606 OID 307821)
-- Dependencies: 1752 1773 2346
-- Name: fk582b0dd0f10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_items
    ADD CONSTRAINT fk582b0dd0f10b9721 FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2674 (class 2606 OID 307871)
-- Dependencies: 1758 1756 2296
-- Name: fk5f8caf2a59f71c23; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_item_match
    ADD CONSTRAINT fk5f8caf2a59f71c23 FOREIGN KEY (orderconfirmationitem_confirmation_item_id) REFERENCES order_confirmation_items(confirmation_item_id);


--
-- TOC entry 2675 (class 2606 OID 307876)
-- Dependencies: 1758 1776 2356
-- Name: fk5f8caf2a867a1de; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_item_match
    ADD CONSTRAINT fk5f8caf2a867a1de FOREIGN KEY (purchaseorderitem_order_item_id) REFERENCES purchase_order_items(order_item_id);


--
-- TOC entry 2448 (class 2606 OID 307881)
-- Dependencies: 1708 2150 1707
-- Name: fk61ac266056f0df10; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_item_values
    ADD CONSTRAINT fk61ac266056f0df10 FOREIGN KEY (item_id) REFERENCES assembling_schema_items(item_id);


--
-- TOC entry 2449 (class 2606 OID 307886)
-- Dependencies: 1800 1707 2428
-- Name: fk61ac266094a6e189; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_item_values
    ADD CONSTRAINT fk61ac266094a6e189 FOREIGN KEY (virtual_product_id) REFERENCES virtual_products(product_id);


--
-- TOC entry 2560 (class 2606 OID 307891)
-- Dependencies: 1714 1734 2170
-- Name: fk61e8f0315e5242cb; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discounts
    ADD CONSTRAINT fk61e8f0315e5242cb FOREIGN KEY (customer_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2561 (class 2606 OID 307896)
-- Dependencies: 1734 2256 1742
-- Name: fk61e8f031c8fb185a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discounts
    ADD CONSTRAINT fk61e8f031c8fb185a FOREIGN KEY (customer_discount_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2794 (class 2606 OID 307901)
-- Dependencies: 2379 1785 1784
-- Name: fk65f152a13e94bedc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoice_item_link
    ADD CONSTRAINT fk65f152a13e94bedc FOREIGN KEY (invoice_item_id) REFERENCES sales_invoice_items(invoice_item_id);


--
-- TOC entry 2720 (class 2606 OID 307906)
-- Dependencies: 1783 2375 1772
-- Name: fk725e8d71ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk725e8d71ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2721 (class 2606 OID 307911)
-- Dependencies: 2375 1783 1772
-- Name: fk725e8d73aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk725e8d73aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2722 (class 2606 OID 307916)
-- Dependencies: 2389 1789 1772
-- Name: fk725e8d7a330dfcf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk725e8d7a330dfcf FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2723 (class 2606 OID 307921)
-- Dependencies: 1714 2170 1772
-- Name: fk725e8d7aac55a1d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk725e8d7aac55a1d FOREIGN KEY (supplier_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2594 (class 2606 OID 307926)
-- Dependencies: 1742 2256 1742
-- Name: fk74e5117f2ff7d10e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk74e5117f2ff7d10e FOREIGN KEY (linked_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2595 (class 2606 OID 307931)
-- Dependencies: 1742 1741 2252
-- Name: fk74e5117fa44e2131; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk74e5117fa44e2131 FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2596 (class 2606 OID 307936)
-- Dependencies: 2256 1742 1742
-- Name: fk74e5117fafa1da5d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk74e5117fafa1da5d FOREIGN KEY (parent_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2770 (class 2606 OID 307941)
-- Dependencies: 1779 1783 2375
-- Name: fk7503fcd11ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk7503fcd11ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2771 (class 2606 OID 307946)
-- Dependencies: 1779 1789 2389
-- Name: fk7503fcd1a330dfcf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk7503fcd1a330dfcf FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2772 (class 2606 OID 307951)
-- Dependencies: 2389 1789 1779
-- Name: fk7503fcd1f10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk7503fcd1f10b9721 FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2666 (class 2606 OID 307956)
-- Dependencies: 1783 1756 2375
-- Name: fk7e6ecbc71ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmation_items
    ADD CONSTRAINT fk7e6ecbc71ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2667 (class 2606 OID 307961)
-- Dependencies: 1756 1783 2375
-- Name: fk7e6ecbc73aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmation_items
    ADD CONSTRAINT fk7e6ecbc73aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2668 (class 2606 OID 307966)
-- Dependencies: 1756 2389 1789
-- Name: fk7e6ecbc7a330dfcf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmation_items
    ADD CONSTRAINT fk7e6ecbc7a330dfcf FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2678 (class 2606 OID 307971)
-- Dependencies: 1760 1783 2375
-- Name: fk8258b9a0180e7eb9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk8258b9a0180e7eb9 FOREIGN KEY (organization_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2679 (class 2606 OID 307976)
-- Dependencies: 2304 1760 1760
-- Name: fk8258b9a08c46f1ed; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk8258b9a08c46f1ed FOREIGN KEY (registration_organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2766 (class 2606 OID 307981)
-- Dependencies: 1778 1789 2389
-- Name: fk82a39ae56103607c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY real_products
    ADD CONSTRAINT fk82a39ae56103607c FOREIGN KEY (simple_product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2767 (class 2606 OID 307986)
-- Dependencies: 1778 1800 2428
-- Name: fk82a39ae59f88efd5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY real_products
    ADD CONSTRAINT fk82a39ae59f88efd5 FOREIGN KEY (product_id) REFERENCES virtual_products(product_id);


--
-- TOC entry 2731 (class 2606 OID 307991)
-- Dependencies: 1756 2296 1774
-- Name: fk82d039701140d7eb; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT fk82d039701140d7eb FOREIGN KEY (order_confirmation_item_id) REFERENCES order_confirmation_items(confirmation_item_id);


--
-- TOC entry 2732 (class 2606 OID 307996)
-- Dependencies: 1783 2375 1774
-- Name: fk82d039701ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT fk82d039701ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2733 (class 2606 OID 308001)
-- Dependencies: 2356 1776 1774
-- Name: fk82d039705c6db99f; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT fk82d039705c6db99f FOREIGN KEY (purchase_order_item_id) REFERENCES purchase_order_items(order_item_id);


--
-- TOC entry 2734 (class 2606 OID 308006)
-- Dependencies: 2354 1774 1775
-- Name: fk82d039706f432245; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT fk82d039706f432245 FOREIGN KEY (invoice_id) REFERENCES purchase_invoices(invoice_id);


--
-- TOC entry 2735 (class 2606 OID 308011)
-- Dependencies: 1774 2256 1742
-- Name: fk82d03970802ad517; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT fk82d03970802ad517 FOREIGN KEY (invoice_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2736 (class 2606 OID 308016)
-- Dependencies: 1773 1774 2346
-- Name: fk82d03970f10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT fk82d03970f10b9721 FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2635 (class 2606 OID 308021)
-- Dependencies: 1748 1742 2256
-- Name: fk8881f7611698d59; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY entity_sequences
    ADD CONSTRAINT fk8881f7611698d59 FOREIGN KEY (entity_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2636 (class 2606 OID 308026)
-- Dependencies: 2252 1741 1748
-- Name: fk8881f76a44e2131; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY entity_sequences
    ADD CONSTRAINT fk8881f76a44e2131 FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2477 (class 2606 OID 308031)
-- Dependencies: 2166 1713 1712
-- Name: fk8b249c1c40f84bd3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_document_status_log
    ADD CONSTRAINT fk8b249c1c40f84bd3 FOREIGN KEY (document_id) REFERENCES business_documents(document_id);


--
-- TOC entry 2478 (class 2606 OID 308036)
-- Dependencies: 1712 2375 1783
-- Name: fk8b249c1c5df497e9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_document_status_log
    ADD CONSTRAINT fk8b249c1c5df497e9 FOREIGN KEY (document_status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2479 (class 2606 OID 308041)
-- Dependencies: 1712 1763 2313
-- Name: fk8b249c1cbf66cb72; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_document_status_log
    ADD CONSTRAINT fk8b249c1cbf66cb72 FOREIGN KEY (officer_id) REFERENCES persons(person_id);


--
-- TOC entry 2503 (class 2606 OID 308046)
-- Dependencies: 2375 1717 1783
-- Name: fk8c60fd423aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cash_reconcile
    ADD CONSTRAINT fk8c60fd423aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2504 (class 2606 OID 308051)
-- Dependencies: 1717 2313 1763
-- Name: fk8c60fd42826572c5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cash_reconcile
    ADD CONSTRAINT fk8c60fd42826572c5 FOREIGN KEY (cashier_id) REFERENCES persons(person_id);


--
-- TOC entry 2505 (class 2606 OID 308056)
-- Dependencies: 1713 2166 1717
-- Name: fk8c60fd42e14660c0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cash_reconcile
    ADD CONSTRAINT fk8c60fd42e14660c0 FOREIGN KEY (cash_reconcile_id) REFERENCES business_documents(document_id);


--
-- TOC entry 2566 (class 2606 OID 308061)
-- Dependencies: 1736 1704 2135
-- Name: fk8f9cba6e27a66c93; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_payments
    ADD CONSTRAINT fk8f9cba6e27a66c93 FOREIGN KEY (branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2567 (class 2606 OID 308066)
-- Dependencies: 1736 1783 2375
-- Name: fk8f9cba6e3aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_payments
    ADD CONSTRAINT fk8f9cba6e3aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2568 (class 2606 OID 308071)
-- Dependencies: 1783 1736 2375
-- Name: fk8f9cba6e3dc9408c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_payments
    ADD CONSTRAINT fk8f9cba6e3dc9408c FOREIGN KEY (payment_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2569 (class 2606 OID 308076)
-- Dependencies: 2313 1736 1763
-- Name: fk8f9cba6e4da6f8bc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_payments
    ADD CONSTRAINT fk8f9cba6e4da6f8bc FOREIGN KEY (creator_id) REFERENCES persons(person_id);


--
-- TOC entry 2570 (class 2606 OID 308081)
-- Dependencies: 2170 1736 1714
-- Name: fk8f9cba6e5e5242cb; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_payments
    ADD CONSTRAINT fk8f9cba6e5e5242cb FOREIGN KEY (customer_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2571 (class 2606 OID 308086)
-- Dependencies: 2313 1763 1736
-- Name: fk8f9cba6e826572c5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_payments
    ADD CONSTRAINT fk8f9cba6e826572c5 FOREIGN KEY (cashier_id) REFERENCES persons(person_id);


--
-- TOC entry 2572 (class 2606 OID 308091)
-- Dependencies: 1736 2375 1783
-- Name: fk8f9cba6e9c49320d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_payments
    ADD CONSTRAINT fk8f9cba6e9c49320d FOREIGN KEY (status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2573 (class 2606 OID 308096)
-- Dependencies: 2207 1727 1736
-- Name: fk8f9cba6eb1651ab7; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_payments
    ADD CONSTRAINT fk8f9cba6eb1651ab7 FOREIGN KEY (customer_contact_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2574 (class 2606 OID 308101)
-- Dependencies: 1763 1736 2313
-- Name: fk8f9cba6ee39a2279; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_payments
    ADD CONSTRAINT fk8f9cba6ee39a2279 FOREIGN KEY (completor_id) REFERENCES persons(person_id);


--
-- TOC entry 2576 (class 2606 OID 308106)
-- Dependencies: 2256 1742 1738
-- Name: fk9157692e2ff7d10e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_links
    ADD CONSTRAINT fk9157692e2ff7d10e FOREIGN KEY (linked_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2919 (class 2606 OID 308111)
-- Dependencies: 1802 2135 1704
-- Name: fk94f81e10a6877d01; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouses
    ADD CONSTRAINT fk94f81e10a6877d01 FOREIGN KEY (address_id) REFERENCES addresses(address_id);


--
-- TOC entry 2920 (class 2606 OID 308116)
-- Dependencies: 1802 2207 1727
-- Name: fk94f81e10fbdf54bf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouses
    ADD CONSTRAINT fk94f81e10fbdf54bf FOREIGN KEY (warehouseman_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2917 (class 2606 OID 308121)
-- Dependencies: 1802 1801 2432
-- Name: fk951433609f1800e1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouse_products
    ADD CONSTRAINT fk951433609f1800e1 FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2918 (class 2606 OID 308126)
-- Dependencies: 1789 1801 2389
-- Name: fk95143360a330dfcf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouse_products
    ADD CONSTRAINT fk95143360a330dfcf FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2777 (class 2606 OID 308131)
-- Dependencies: 2364 1780 1779
-- Name: fk98230d0e73d2d06a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_serial_numbers
    ADD CONSTRAINT fk98230d0e73d2d06a FOREIGN KEY (certificate_item_id) REFERENCES receipt_certificate_items(certificate_item_id);


--
-- TOC entry 2439 (class 2606 OID 308136)
-- Dependencies: 1704 1719 2186
-- Name: fk_addresses_city_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT fk_addresses_city_id FOREIGN KEY (city_id) REFERENCES cities(city_id);


--
-- TOC entry 2440 (class 2606 OID 308141)
-- Dependencies: 2211 1728 1704
-- Name: fk_addresses_country_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT fk_addresses_country_id FOREIGN KEY (country_id) REFERENCES countries(country_id);


--
-- TOC entry 2441 (class 2606 OID 308146)
-- Dependencies: 1704 2256 1742
-- Name: fk_addresses_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT fk_addresses_do_id FOREIGN KEY (address_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2443 (class 2606 OID 308151)
-- Dependencies: 2256 1705 1742
-- Name: fk_assembling_categories_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_categories
    ADD CONSTRAINT fk_assembling_categories_do FOREIGN KEY (assembling_category_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2444 (class 2606 OID 308156)
-- Dependencies: 1705 2138 1705
-- Name: fk_assembling_categories_parent_category; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_categories
    ADD CONSTRAINT fk_assembling_categories_parent_category FOREIGN KEY (parent_category_id) REFERENCES assembling_categories(assembling_category_id);


--
-- TOC entry 2446 (class 2606 OID 308161)
-- Dependencies: 2256 1706 1742
-- Name: fk_assembling_messages_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_messages
    ADD CONSTRAINT fk_assembling_messages_do FOREIGN KEY (message_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2447 (class 2606 OID 308166)
-- Dependencies: 2304 1760 1706
-- Name: fk_assembling_messages_organization; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_messages
    ADD CONSTRAINT fk_assembling_messages_organization FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2450 (class 2606 OID 308171)
-- Dependencies: 2150 1707 1708
-- Name: fk_assembling_schema_item_values_as_item; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_item_values
    ADD CONSTRAINT fk_assembling_schema_item_values_as_item FOREIGN KEY (item_id) REFERENCES assembling_schema_items(item_id);


--
-- TOC entry 2451 (class 2606 OID 308176)
-- Dependencies: 1800 2428 1707
-- Name: fk_assembling_schema_item_values_vp; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_item_values
    ADD CONSTRAINT fk_assembling_schema_item_values_vp FOREIGN KEY (virtual_product_id) REFERENCES virtual_products(product_id);


--
-- TOC entry 2452 (class 2606 OID 308181)
-- Dependencies: 1783 2375 1708
-- Name: fk_assembling_schema_items_algorithm; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT fk_assembling_schema_items_algorithm FOREIGN KEY (algorithm_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2453 (class 2606 OID 308186)
-- Dependencies: 2375 1708 1783
-- Name: fk_assembling_schema_items_data_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT fk_assembling_schema_items_data_type FOREIGN KEY (data_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2454 (class 2606 OID 308191)
-- Dependencies: 1706 2144 1708
-- Name: fk_assembling_schema_items_message; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT fk_assembling_schema_items_message FOREIGN KEY (message_id) REFERENCES assembling_messages(message_id);


--
-- TOC entry 2455 (class 2606 OID 308196)
-- Dependencies: 1709 2152 1708
-- Name: fk_assembling_schema_items_owner; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT fk_assembling_schema_items_owner FOREIGN KEY (assembling_schema_id) REFERENCES assembling_schemas(product_id);


--
-- TOC entry 2460 (class 2606 OID 308201)
-- Dependencies: 1709 2138 1705
-- Name: fk_assembling_schemas_categories; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT fk_assembling_schemas_categories FOREIGN KEY (category_id) REFERENCES assembling_categories(assembling_category_id);


--
-- TOC entry 2461 (class 2606 OID 308206)
-- Dependencies: 1783 1709 2375
-- Name: fk_assembling_schemas_measure_unit; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT fk_assembling_schemas_measure_unit FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2462 (class 2606 OID 308211)
-- Dependencies: 1709 2428 1800
-- Name: fk_assembling_schemas_virtual_products; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT fk_assembling_schemas_virtual_products FOREIGN KEY (product_id) REFERENCES virtual_products(product_id);


--
-- TOC entry 2472 (class 2606 OID 308216)
-- Dependencies: 1710 1704 2135
-- Name: fk_bank_details_bank_branch; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk_bank_details_bank_branch FOREIGN KEY (bank_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2473 (class 2606 OID 308221)
-- Dependencies: 2256 1710 1742
-- Name: fk_bank_details_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk_bank_details_do_id FOREIGN KEY (bank_detail_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2476 (class 2606 OID 309968)
-- Dependencies: 1711 2180 1717
-- Name: fk_banknote_quantity_cash_reconcile; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY banknote_quantity
    ADD CONSTRAINT fk_banknote_quantity_cash_reconcile FOREIGN KEY (cash_reconcile_id) REFERENCES cash_reconcile(cash_reconcile_id);


--
-- TOC entry 2475 (class 2606 OID 309963)
-- Dependencies: 2218 1730 1711
-- Name: fk_banknote_quantity_currency_nominal; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY banknote_quantity
    ADD CONSTRAINT fk_banknote_quantity_currency_nominal FOREIGN KEY (currency_nominal_id) REFERENCES currency_nominal(currency_nominal_id);


--
-- TOC entry 2474 (class 2606 OID 309958)
-- Dependencies: 1711 2256 1742
-- Name: fk_banknote_quantity_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY banknote_quantity
    ADD CONSTRAINT fk_banknote_quantity_do FOREIGN KEY (banknote_quantity_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2480 (class 2606 OID 308226)
-- Dependencies: 1713 2166 1712
-- Name: fk_business_document_status_log_documents; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_document_status_log
    ADD CONSTRAINT fk_business_document_status_log_documents FOREIGN KEY (document_id) REFERENCES business_documents(document_id);


--
-- TOC entry 2481 (class 2606 OID 308231)
-- Dependencies: 2313 1712 1763
-- Name: fk_business_document_status_log_persons; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_document_status_log
    ADD CONSTRAINT fk_business_document_status_log_persons FOREIGN KEY (officer_id) REFERENCES persons(person_id);


--
-- TOC entry 2482 (class 2606 OID 308236)
-- Dependencies: 2375 1783 1712
-- Name: fk_business_document_status_log_resource_bundle; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_document_status_log
    ADD CONSTRAINT fk_business_document_status_log_resource_bundle FOREIGN KEY (document_status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2483 (class 2606 OID 308241)
-- Dependencies: 1704 1713 2135
-- Name: fk_business_documents_publisher_branch_addresses; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_documents
    ADD CONSTRAINT fk_business_documents_publisher_branch_addresses FOREIGN KEY (publisher_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2484 (class 2606 OID 308246)
-- Dependencies: 1763 1713 2313
-- Name: fk_business_documents_publisher_contact_persons; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_documents
    ADD CONSTRAINT fk_business_documents_publisher_contact_persons FOREIGN KEY (publisher_officer_id) REFERENCES persons(person_id);


--
-- TOC entry 2485 (class 2606 OID 308251)
-- Dependencies: 2304 1760 1713
-- Name: fk_business_documents_publisher_organizations; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_documents
    ADD CONSTRAINT fk_business_documents_publisher_organizations FOREIGN KEY (publisher_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2489 (class 2606 OID 308256)
-- Dependencies: 1714 2375 1783
-- Name: fk_business_partners_currency; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_partners
    ADD CONSTRAINT fk_business_partners_currency FOREIGN KEY (default_currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2490 (class 2606 OID 308261)
-- Dependencies: 1714 1742 2256
-- Name: fk_business_partners_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_partners
    ADD CONSTRAINT fk_business_partners_do FOREIGN KEY (business_partner_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2491 (class 2606 OID 308266)
-- Dependencies: 1714 2170 1714
-- Name: fk_business_partners_parent; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_partners
    ADD CONSTRAINT fk_business_partners_parent FOREIGN KEY (parent_business_partner_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2492 (class 2606 OID 308271)
-- Dependencies: 1715 1704 2135
-- Name: fk_business_unit_addresses_address; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_unit_addresses
    ADD CONSTRAINT fk_business_unit_addresses_address FOREIGN KEY (address_id) REFERENCES addresses(address_id);


--
-- TOC entry 2493 (class 2606 OID 308276)
-- Dependencies: 1716 1715 2177
-- Name: fk_business_unit_addresses_bu; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_unit_addresses
    ADD CONSTRAINT fk_business_unit_addresses_bu FOREIGN KEY (business_unit_id) REFERENCES business_units(business_unit_id);


--
-- TOC entry 2494 (class 2606 OID 308281)
-- Dependencies: 2256 1715 1742
-- Name: fk_business_unit_addresses_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_unit_addresses
    ADD CONSTRAINT fk_business_unit_addresses_do FOREIGN KEY (business_unit_address_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2495 (class 2606 OID 308286)
-- Dependencies: 2200 1715 1724
-- Name: fk_business_unit_addresses_email; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_unit_addresses
    ADD CONSTRAINT fk_business_unit_addresses_email FOREIGN KEY (email_id) REFERENCES communication_contacts(communication_contact_id);


--
-- TOC entry 2496 (class 2606 OID 308291)
-- Dependencies: 2200 1715 1724
-- Name: fk_business_unit_addresses_fax; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_unit_addresses
    ADD CONSTRAINT fk_business_unit_addresses_fax FOREIGN KEY (fax_id) REFERENCES communication_contacts(communication_contact_id);


--
-- TOC entry 2497 (class 2606 OID 308296)
-- Dependencies: 1715 1724 2200
-- Name: fk_business_unit_addresses_phone; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_unit_addresses
    ADD CONSTRAINT fk_business_unit_addresses_phone FOREIGN KEY (phone_id) REFERENCES communication_contacts(communication_contact_id);


--
-- TOC entry 2498 (class 2606 OID 308301)
-- Dependencies: 1715 1783 2375
-- Name: fk_business_unit_addresses_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_unit_addresses
    ADD CONSTRAINT fk_business_unit_addresses_type FOREIGN KEY (address_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2499 (class 2606 OID 308306)
-- Dependencies: 1716 2177 1716
-- Name: fk_business_units_bu; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_units
    ADD CONSTRAINT fk_business_units_bu FOREIGN KEY (parent_business_unit_id) REFERENCES business_units(business_unit_id);


--
-- TOC entry 2500 (class 2606 OID 308311)
-- Dependencies: 1783 2375 1716
-- Name: fk_business_units_bu_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_units
    ADD CONSTRAINT fk_business_units_bu_type FOREIGN KEY (business_unit_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2501 (class 2606 OID 308316)
-- Dependencies: 2256 1716 1742
-- Name: fk_business_units_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_units
    ADD CONSTRAINT fk_business_units_do FOREIGN KEY (business_unit_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2502 (class 2606 OID 326396)
-- Dependencies: 1760 1716 2304
-- Name: fk_business_units_organizations; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_units
    ADD CONSTRAINT fk_business_units_organizations FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2506 (class 2606 OID 308326)
-- Dependencies: 2180 1718 1717
-- Name: fk_cash_reconcile_payment_summary_cash_reconcile; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cash_reconcile_payment_summary
    ADD CONSTRAINT fk_cash_reconcile_payment_summary_cash_reconcile FOREIGN KEY (cash_reconcile_id) REFERENCES cash_reconcile(cash_reconcile_id);


--
-- TOC entry 2507 (class 2606 OID 308331)
-- Dependencies: 2375 1783 1718
-- Name: fk_cash_reconcile_payment_summary_currency; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cash_reconcile_payment_summary
    ADD CONSTRAINT fk_cash_reconcile_payment_summary_currency FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2509 (class 2606 OID 309984)
-- Dependencies: 2256 1718 1742
-- Name: fk_cash_reconcile_payment_summary_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cash_reconcile_payment_summary
    ADD CONSTRAINT fk_cash_reconcile_payment_summary_do FOREIGN KEY (payment_summary_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2508 (class 2606 OID 308336)
-- Dependencies: 1718 1783 2375
-- Name: fk_cash_reconcile_payment_summary_payment_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cash_reconcile_payment_summary
    ADD CONSTRAINT fk_cash_reconcile_payment_summary_payment_type FOREIGN KEY (payment_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2624 (class 2606 OID 308341)
-- Dependencies: 1747 1783 2375
-- Name: fk_certificate_status; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_certificate_status FOREIGN KEY (delivery_cert_status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2513 (class 2606 OID 308346)
-- Dependencies: 1720 2196 1723
-- Name: fk_classified_objects_classifier_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classified_objects
    ADD CONSTRAINT fk_classified_objects_classifier_id FOREIGN KEY (classifier_id) REFERENCES classifiers(classifier_id);


--
-- TOC entry 2517 (class 2606 OID 308351)
-- Dependencies: 2252 1721 1741
-- Name: fk_classifier_applied_for_dot_dot_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifier_applied_for_dot
    ADD CONSTRAINT fk_classifier_applied_for_dot_dot_id FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2520 (class 2606 OID 308356)
-- Dependencies: 1722 1742 2256
-- Name: fk_classifier_groups_cg_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifier_groups
    ADD CONSTRAINT fk_classifier_groups_cg_id FOREIGN KEY (classifier_group_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2521 (class 2606 OID 308361)
-- Dependencies: 2194 1722 1723
-- Name: fk_classifiers_classifier_groups; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT fk_classifiers_classifier_groups FOREIGN KEY (classifier_group_id) REFERENCES classifier_groups(classifier_group_id);


--
-- TOC entry 2522 (class 2606 OID 308366)
-- Dependencies: 2256 1723 1742
-- Name: fk_classifiers_data_objects; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT fk_classifiers_data_objects FOREIGN KEY (classifier_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2525 (class 2606 OID 308371)
-- Dependencies: 1783 2375 1724
-- Name: fk_communication_contacts_comm_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT fk_communication_contacts_comm_type FOREIGN KEY (communication_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2526 (class 2606 OID 308376)
-- Dependencies: 1724 2256 1742
-- Name: fk_communication_contacts_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT fk_communication_contacts_do_id FOREIGN KEY (communication_contact_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2530 (class 2606 OID 308381)
-- Dependencies: 2375 1783 1725
-- Name: fk_complex_product_items_algorithm; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT fk_complex_product_items_algorithm FOREIGN KEY (applied_algorithm_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2531 (class 2606 OID 308386)
-- Dependencies: 1725 2205 1726
-- Name: fk_complex_product_items_cp; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT fk_complex_product_items_cp FOREIGN KEY (complex_product_id) REFERENCES complex_products(product_id);


--
-- TOC entry 2532 (class 2606 OID 308391)
-- Dependencies: 1773 2346 1725
-- Name: fk_complex_product_items_products; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT fk_complex_product_items_products FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2533 (class 2606 OID 308396)
-- Dependencies: 1726 2346 1773
-- Name: fk_complex_products_products; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_products
    ADD CONSTRAINT fk_complex_products_products FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2537 (class 2606 OID 308401)
-- Dependencies: 1742 1727 2256
-- Name: fk_contact_persons_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT fk_contact_persons_do_id FOREIGN KEY (contact_person_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2538 (class 2606 OID 308406)
-- Dependencies: 1764 1727 2317
-- Name: fk_contact_persons_position_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT fk_contact_persons_position_type FOREIGN KEY (position_type_id) REFERENCES position_types(position_type_id);


--
-- TOC entry 2539 (class 2606 OID 308411)
-- Dependencies: 1783 2375 1728
-- Name: fk_countries_currency; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY countries
    ADD CONSTRAINT fk_countries_currency FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2540 (class 2606 OID 308416)
-- Dependencies: 1742 1728 2256
-- Name: fk_countries_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY countries
    ADD CONSTRAINT fk_countries_do FOREIGN KEY (country_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2625 (class 2606 OID 308421)
-- Dependencies: 1747 1704 2135
-- Name: fk_creator_branch; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_creator_branch FOREIGN KEY (creator_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2626 (class 2606 OID 308426)
-- Dependencies: 1747 1760 2304
-- Name: fk_creator_organization; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_creator_organization FOREIGN KEY (creator_organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2541 (class 2606 OID 308431)
-- Dependencies: 2375 1783 1729
-- Name: fk_currency_exchange_rates_from_currency; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY currency_exchange_rates
    ADD CONSTRAINT fk_currency_exchange_rates_from_currency FOREIGN KEY (from_currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2542 (class 2606 OID 308436)
-- Dependencies: 1783 1729 2375
-- Name: fk_currency_exchange_rates_to_currency; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY currency_exchange_rates
    ADD CONSTRAINT fk_currency_exchange_rates_to_currency FOREIGN KEY (to_currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2545 (class 2606 OID 308441)
-- Dependencies: 1730 2375 1783
-- Name: fk_currency_nominal_currency; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY currency_nominal
    ADD CONSTRAINT fk_currency_nominal_currency FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2546 (class 2606 OID 309979)
-- Dependencies: 2256 1742 1730
-- Name: fk_currency_nominal_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY currency_nominal
    ADD CONSTRAINT fk_currency_nominal_do FOREIGN KEY (currency_nominal_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2552 (class 2606 OID 308446)
-- Dependencies: 2222 1732 1731
-- Name: fk_customer_discount_items_by_categories; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items_by_categories
    ADD CONSTRAINT fk_customer_discount_items_by_categories FOREIGN KEY (customer_discount_item_id) REFERENCES customer_discount_items(customer_discount_item_id);


--
-- TOC entry 2553 (class 2606 OID 308451)
-- Dependencies: 1732 1734 2232
-- Name: fk_customer_discount_items_by_categories_cd; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items_by_categories
    ADD CONSTRAINT fk_customer_discount_items_by_categories_cd FOREIGN KEY (customer_discount_id) REFERENCES customer_discounts(customer_discount_id);


--
-- TOC entry 2554 (class 2606 OID 308456)
-- Dependencies: 2336 1732 1770
-- Name: fk_customer_discount_items_by_categories_pc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items_by_categories
    ADD CONSTRAINT fk_customer_discount_items_by_categories_pc FOREIGN KEY (category_id) REFERENCES product_categories(product_category_id);


--
-- TOC entry 2555 (class 2606 OID 308461)
-- Dependencies: 1731 1733 2222
-- Name: fk_customer_discount_items_by_products; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items_by_products
    ADD CONSTRAINT fk_customer_discount_items_by_products FOREIGN KEY (customer_discount_item_id) REFERENCES customer_discount_items(customer_discount_item_id);


--
-- TOC entry 2556 (class 2606 OID 308466)
-- Dependencies: 1734 1733 2232
-- Name: fk_customer_discount_items_by_products_cd; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items_by_products
    ADD CONSTRAINT fk_customer_discount_items_by_products_cd FOREIGN KEY (customer_discount_id) REFERENCES customer_discounts(customer_discount_id);


--
-- TOC entry 2557 (class 2606 OID 308471)
-- Dependencies: 1731 1733 2222
-- Name: fk_customer_discount_items_by_products_cdi; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items_by_products
    ADD CONSTRAINT fk_customer_discount_items_by_products_cdi FOREIGN KEY (customer_discount_item_id) REFERENCES customer_discount_items(customer_discount_item_id);


--
-- TOC entry 2558 (class 2606 OID 308476)
-- Dependencies: 1773 1733 2346
-- Name: fk_customer_discount_items_by_products_p; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items_by_products
    ADD CONSTRAINT fk_customer_discount_items_by_products_p FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2559 (class 2606 OID 308481)
-- Dependencies: 1773 1733 2346
-- Name: fk_customer_discount_items_by_products_products; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items_by_products
    ADD CONSTRAINT fk_customer_discount_items_by_products_products FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2548 (class 2606 OID 308486)
-- Dependencies: 1742 1731 2256
-- Name: fk_customer_discount_items_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items
    ADD CONSTRAINT fk_customer_discount_items_do FOREIGN KEY (customer_discount_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2562 (class 2606 OID 308491)
-- Dependencies: 1742 1734 2256
-- Name: fk_customer_discounts_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discounts
    ADD CONSTRAINT fk_customer_discounts_do FOREIGN KEY (customer_discount_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2563 (class 2606 OID 308496)
-- Dependencies: 1734 1760 2304
-- Name: fk_customer_discounts_organization; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discounts
    ADD CONSTRAINT fk_customer_discounts_organization FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2564 (class 2606 OID 308501)
-- Dependencies: 1735 2238 1736
-- Name: fk_customer_payment_match_cp; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_payment_match
    ADD CONSTRAINT fk_customer_payment_match_cp FOREIGN KEY (customer_payment_id) REFERENCES customer_payments(payment_id);


--
-- TOC entry 2565 (class 2606 OID 308506)
-- Dependencies: 1735 1786 2381
-- Name: fk_customer_payment_match_invoice; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_payment_match
    ADD CONSTRAINT fk_customer_payment_match_invoice FOREIGN KEY (invoice_id) REFERENCES sales_invoices(invoice_id);


--
-- TOC entry 2575 (class 2606 OID 308511)
-- Dependencies: 1737 1742 2256
-- Name: fk_data_object_details_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_details
    ADD CONSTRAINT fk_data_object_details_do_id FOREIGN KEY (data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2577 (class 2606 OID 308516)
-- Dependencies: 1742 1738 2256
-- Name: fk_data_object_links_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_links
    ADD CONSTRAINT fk_data_object_links_do_id FOREIGN KEY (data_object_link_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2578 (class 2606 OID 308521)
-- Dependencies: 1738 1742 2256
-- Name: fk_data_object_links_linked_object; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_links
    ADD CONSTRAINT fk_data_object_links_linked_object FOREIGN KEY (linked_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2582 (class 2606 OID 308526)
-- Dependencies: 1739 1742 2256
-- Name: fk_data_object_permissions_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_permissions
    ADD CONSTRAINT fk_data_object_permissions_do FOREIGN KEY (data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2583 (class 2606 OID 308531)
-- Dependencies: 1739 1760 2304
-- Name: fk_data_object_permissions_organizations; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_permissions
    ADD CONSTRAINT fk_data_object_permissions_organizations FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2584 (class 2606 OID 308536)
-- Dependencies: 2375 1739 1783
-- Name: fk_data_object_permissions_permissions; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_permissions
    ADD CONSTRAINT fk_data_object_permissions_permissions FOREIGN KEY (permission_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2585 (class 2606 OID 308541)
-- Dependencies: 1739 2375 1783
-- Name: fk_data_object_permissions_urt; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_permissions
    ADD CONSTRAINT fk_data_object_permissions_urt FOREIGN KEY (user_right_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2586 (class 2606 OID 308546)
-- Dependencies: 2252 1741 1740
-- Name: fk_data_object_type_permissions_dot; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_type_permissions
    ADD CONSTRAINT fk_data_object_type_permissions_dot FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2587 (class 2606 OID 308551)
-- Dependencies: 1740 2304 1760
-- Name: fk_data_object_type_permissions_organizations; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_type_permissions
    ADD CONSTRAINT fk_data_object_type_permissions_organizations FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2588 (class 2606 OID 308556)
-- Dependencies: 2375 1740 1783
-- Name: fk_data_object_type_permissions_permissions; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_type_permissions
    ADD CONSTRAINT fk_data_object_type_permissions_permissions FOREIGN KEY (permission_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2589 (class 2606 OID 308561)
-- Dependencies: 1740 2375 1783
-- Name: fk_data_object_type_permissions_urt; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_type_permissions
    ADD CONSTRAINT fk_data_object_type_permissions_urt FOREIGN KEY (user_right_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2597 (class 2606 OID 308566)
-- Dependencies: 2252 1742 1741
-- Name: fk_data_objects_do_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk_data_objects_do_type FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2598 (class 2606 OID 308571)
-- Dependencies: 2256 1742 1742
-- Name: fk_data_objects_linked_data_object_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk_data_objects_linked_data_object_id FOREIGN KEY (linked_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2599 (class 2606 OID 308576)
-- Dependencies: 1742 1742 2256
-- Name: fk_data_objects_parent_data_object_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk_data_objects_parent_data_object_id FOREIGN KEY (parent_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2601 (class 2606 OID 308581)
-- Dependencies: 1742 2256 1743
-- Name: fk_db_properties_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY db_properties
    ADD CONSTRAINT fk_db_properties_do FOREIGN KEY (related_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2603 (class 2606 OID 308586)
-- Dependencies: 1745 2346 1773
-- Name: fk_delivery_certifiate_items_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fk_delivery_certifiate_items_product FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2602 (class 2606 OID 308591)
-- Dependencies: 1744 2272 1747
-- Name: fk_delivery_certificate; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_assignments
    ADD CONSTRAINT fk_delivery_certificate FOREIGN KEY (delivery_certificate_id) REFERENCES delivery_certificates(delivery_certificate_id);


--
-- TOC entry 2604 (class 2606 OID 308596)
-- Dependencies: 2272 1747 1745
-- Name: fk_delivery_certificate_items_delivery_cert; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fk_delivery_certificate_items_delivery_cert FOREIGN KEY (parent_id) REFERENCES delivery_certificates(delivery_certificate_id);


--
-- TOC entry 2605 (class 2606 OID 308601)
-- Dependencies: 2256 1745 1742
-- Name: fk_delivery_certificate_items_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fk_delivery_certificate_items_do_id FOREIGN KEY (certificate_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2606 (class 2606 OID 308606)
-- Dependencies: 1783 1745 2375
-- Name: fk_delivery_certificate_items_measure_unit; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fk_delivery_certificate_items_measure_unit FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2609 (class 2606 OID 308611)
-- Dependencies: 2262 1746 1745
-- Name: fk_delivery_certificate_serial_numbers_dci; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_serial_numbers
    ADD CONSTRAINT fk_delivery_certificate_serial_numbers_dci FOREIGN KEY (certificate_item_id) REFERENCES delivery_certificate_items(certificate_item_id);


--
-- TOC entry 2627 (class 2606 OID 308616)
-- Dependencies: 1742 1747 2256
-- Name: fk_delivery_certificates_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_delivery_certificates_do_id FOREIGN KEY (delivery_certificate_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2628 (class 2606 OID 308621)
-- Dependencies: 2375 1747 1783
-- Name: fk_delivery_certificates_reason; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_delivery_certificates_reason FOREIGN KEY (delivery_cert_reason_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2629 (class 2606 OID 308626)
-- Dependencies: 2170 1714 1747
-- Name: fk_delivery_certificates_recipient; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_delivery_certificates_recipient FOREIGN KEY (recipient_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2630 (class 2606 OID 308631)
-- Dependencies: 1747 1783 2375
-- Name: fk_delivery_certificates_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_delivery_certificates_type FOREIGN KEY (delivery_cert_method_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2631 (class 2606 OID 308636)
-- Dependencies: 2432 1802 1747
-- Name: fk_delivery_certificates_warehouse; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_delivery_certificates_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2486 (class 2606 OID 308641)
-- Dependencies: 2256 1742 1713
-- Name: fk_document_entities_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_documents
    ADD CONSTRAINT fk_document_entities_do FOREIGN KEY (document_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2487 (class 2606 OID 308646)
-- Dependencies: 1713 1783 2375
-- Name: fk_document_entities_status_resource_bundle; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_documents
    ADD CONSTRAINT fk_document_entities_status_resource_bundle FOREIGN KEY (document_status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2488 (class 2606 OID 308651)
-- Dependencies: 1713 1783 2375
-- Name: fk_document_entities_type_resource_bundle; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_documents
    ADD CONSTRAINT fk_document_entities_type_resource_bundle FOREIGN KEY (document_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2637 (class 2606 OID 308656)
-- Dependencies: 1748 1742 2256
-- Name: fk_entity_sequences_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY entity_sequences
    ADD CONSTRAINT fk_entity_sequences_do FOREIGN KEY (entity_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2638 (class 2606 OID 308661)
-- Dependencies: 1748 1741 2252
-- Name: fk_entity_sequences_dot; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY entity_sequences
    ADD CONSTRAINT fk_entity_sequences_dot FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2639 (class 2606 OID 308666)
-- Dependencies: 1750 1760 2304
-- Name: fk_expressions_organizations; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY expressions
    ADD CONSTRAINT fk_expressions_organizations FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2632 (class 2606 OID 308671)
-- Dependencies: 1747 1704 2135
-- Name: fk_forwarder_address_constraint; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_forwarder_address_constraint FOREIGN KEY (forwarder_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2641 (class 2606 OID 308676)
-- Dependencies: 1751 1745 2262
-- Name: fk_goods_receipt_dc_items_delivery_certificate_items; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_dc_items
    ADD CONSTRAINT fk_goods_receipt_dc_items_delivery_certificate_items FOREIGN KEY (delivery_certificate_item_id) REFERENCES delivery_certificate_items(certificate_item_id);


--
-- TOC entry 2642 (class 2606 OID 308681)
-- Dependencies: 1751 1752 2286
-- Name: fk_goods_receipt_dc_items_gri; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_dc_items
    ADD CONSTRAINT fk_goods_receipt_dc_items_gri FOREIGN KEY (receipt_item_id) REFERENCES goods_receipt_items(receipt_item_id);


--
-- TOC entry 2649 (class 2606 OID 308686)
-- Dependencies: 1752 1742 2256
-- Name: fk_goods_receipt_items_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_items
    ADD CONSTRAINT fk_goods_receipt_items_do FOREIGN KEY (receipt_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2650 (class 2606 OID 308691)
-- Dependencies: 1752 1754 2290
-- Name: fk_goods_receipt_items_goods_receipts; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_items
    ADD CONSTRAINT fk_goods_receipt_items_goods_receipts FOREIGN KEY (goods_receipt_id) REFERENCES goods_receipts(goods_receipt_id);


--
-- TOC entry 2651 (class 2606 OID 308696)
-- Dependencies: 1752 1783 2375
-- Name: fk_goods_receipt_items_measure_unit_rb; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_items
    ADD CONSTRAINT fk_goods_receipt_items_measure_unit_rb FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2652 (class 2606 OID 308701)
-- Dependencies: 1752 1773 2346
-- Name: fk_goods_receipt_items_products; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_items
    ADD CONSTRAINT fk_goods_receipt_items_products FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2653 (class 2606 OID 308706)
-- Dependencies: 1753 1752 2286
-- Name: fk_goods_receipt_pi_items_gri; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_pi_items
    ADD CONSTRAINT fk_goods_receipt_pi_items_gri FOREIGN KEY (receipt_item_id) REFERENCES goods_receipt_items(receipt_item_id);


--
-- TOC entry 2654 (class 2606 OID 308711)
-- Dependencies: 1753 1774 2352
-- Name: fk_goods_receipt_pi_items_poi; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_pi_items
    ADD CONSTRAINT fk_goods_receipt_pi_items_poi FOREIGN KEY (invoice_item_id) REFERENCES purchase_invoice_items(invoice_item_id);


--
-- TOC entry 2661 (class 2606 OID 309999)
-- Dependencies: 1713 1754 2166
-- Name: fk_goods_receipts_business_document; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipts
    ADD CONSTRAINT fk_goods_receipts_business_document FOREIGN KEY (goods_receipt_id) REFERENCES business_documents(document_id);


--
-- TOC entry 2657 (class 2606 OID 308736)
-- Dependencies: 1754 1783 2375
-- Name: fk_goods_receipts_doc_type_resource_bundle; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipts
    ADD CONSTRAINT fk_goods_receipts_doc_type_resource_bundle FOREIGN KEY (related_document_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2658 (class 2606 OID 308746)
-- Dependencies: 1754 1714 2170
-- Name: fk_goods_receipts_supplier_b_partners; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipts
    ADD CONSTRAINT fk_goods_receipts_supplier_b_partners FOREIGN KEY (supplier_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2659 (class 2606 OID 308751)
-- Dependencies: 1754 1704 2135
-- Name: fk_goods_receipts_supplier_branch_addresses; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipts
    ADD CONSTRAINT fk_goods_receipts_supplier_branch_addresses FOREIGN KEY (supplier_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2660 (class 2606 OID 308756)
-- Dependencies: 1754 1763 2313
-- Name: fk_goods_receipts_supplier_contact_persons; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipts
    ADD CONSTRAINT fk_goods_receipts_supplier_contact_persons FOREIGN KEY (supplier_contact_id) REFERENCES persons(person_id);


--
-- TOC entry 2662 (class 2606 OID 308761)
-- Dependencies: 1755 1716 2177
-- Name: fk_job_titles_business_units; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY job_titles
    ADD CONSTRAINT fk_job_titles_business_units FOREIGN KEY (business_unit_id) REFERENCES business_units(business_unit_id);


--
-- TOC entry 2663 (class 2606 OID 308766)
-- Dependencies: 1755 1742 2256
-- Name: fk_job_titles_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY job_titles
    ADD CONSTRAINT fk_job_titles_do FOREIGN KEY (job_title_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2664 (class 2606 OID 308771)
-- Dependencies: 1755 1783 2375
-- Name: fk_job_titles_functional_hierarchy; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY job_titles
    ADD CONSTRAINT fk_job_titles_functional_hierarchy FOREIGN KEY (functional_hierarchy_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2665 (class 2606 OID 308776)
-- Dependencies: 1755 1787 2383
-- Name: fk_job_titles_security_roles; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY job_titles
    ADD CONSTRAINT fk_job_titles_security_roles FOREIGN KEY (security_role_id) REFERENCES security_roles(security_role_id);


--
-- TOC entry 2676 (class 2606 OID 308781)
-- Dependencies: 1759 1783 2375
-- Name: fk_organization_configurations_currency; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organization_configurations
    ADD CONSTRAINT fk_organization_configurations_currency FOREIGN KEY (default_currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2677 (class 2606 OID 308786)
-- Dependencies: 1759 1760 2304
-- Name: fk_organization_configurations_org; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organization_configurations
    ADD CONSTRAINT fk_organization_configurations_org FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2680 (class 2606 OID 308791)
-- Dependencies: 1760 1714 2170
-- Name: fk_organizations_business_partner; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk_organizations_business_partner FOREIGN KEY (organization_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2684 (class 2606 OID 308796)
-- Dependencies: 1761 1742 2256
-- Name: fk_passports_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT fk_passports_do_id FOREIGN KEY (passport_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2685 (class 2606 OID 308801)
-- Dependencies: 1761 1704 2135
-- Name: fk_passports_issuer_branch; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT fk_passports_issuer_branch FOREIGN KEY (issuer_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2686 (class 2606 OID 308806)
-- Dependencies: 1761 1783 2375
-- Name: fk_passports_pass_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT fk_passports_pass_type FOREIGN KEY (passport_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2925 (class 2606 OID 310030)
-- Dependencies: 1724 2200 1817
-- Name: fk_personal_communication_contacts_cc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY personal_communication_contacts
    ADD CONSTRAINT fk_personal_communication_contacts_cc FOREIGN KEY (communication_contact_id) REFERENCES communication_contacts(communication_contact_id);


--
-- TOC entry 2924 (class 2606 OID 310025)
-- Dependencies: 1817 2207 1727
-- Name: fk_personal_communication_contacts_cp; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY personal_communication_contacts
    ADD CONSTRAINT fk_personal_communication_contacts_cp FOREIGN KEY (contact_person_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2923 (class 2606 OID 310020)
-- Dependencies: 1742 1817 2256
-- Name: fk_personal_communication_contacts_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY personal_communication_contacts
    ADD CONSTRAINT fk_personal_communication_contacts_do FOREIGN KEY (personal_communication_contact_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2692 (class 2606 OID 308811)
-- Dependencies: 1763 1719 2186
-- Name: fk_persons_birth_place_city; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT fk_persons_birth_place_city FOREIGN KEY (birth_place_city_id) REFERENCES cities(city_id);


--
-- TOC entry 2693 (class 2606 OID 308816)
-- Dependencies: 1763 1728 2211
-- Name: fk_persons_birth_place_country; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT fk_persons_birth_place_country FOREIGN KEY (birth_place_country_id) REFERENCES countries(country_id);


--
-- TOC entry 2694 (class 2606 OID 308821)
-- Dependencies: 1763 1783 2375
-- Name: fk_persons_gender; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT fk_persons_gender FOREIGN KEY (gender_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2695 (class 2606 OID 308826)
-- Dependencies: 1763 1714 2170
-- Name: fk_persons_partner; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT fk_persons_partner FOREIGN KEY (person_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2697 (class 2606 OID 310035)
-- Dependencies: 1764 1714 2170
-- Name: fk_position_types_business_partner; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY position_types
    ADD CONSTRAINT fk_position_types_business_partner FOREIGN KEY (business_partner_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2696 (class 2606 OID 308831)
-- Dependencies: 1764 1742 2256
-- Name: fk_position_types_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY position_types
    ADD CONSTRAINT fk_position_types_do_id FOREIGN KEY (position_type_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2698 (class 2606 OID 308836)
-- Dependencies: 1765 1766 2322
-- Name: fk_pricelist_items_pricelist; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pricelist_items
    ADD CONSTRAINT fk_pricelist_items_pricelist FOREIGN KEY (pricelist_id) REFERENCES pricelists(pricelist_id);


--
-- TOC entry 2699 (class 2606 OID 308841)
-- Dependencies: 1765 1789 2389
-- Name: fk_pricelist_items_simple_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pricelist_items
    ADD CONSTRAINT fk_pricelist_items_simple_product FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2700 (class 2606 OID 308846)
-- Dependencies: 1766 1783 2375
-- Name: fk_pricelists_curency; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pricelists
    ADD CONSTRAINT fk_pricelists_curency FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2701 (class 2606 OID 308851)
-- Dependencies: 1766 1742 2256
-- Name: fk_pricelists_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pricelists
    ADD CONSTRAINT fk_pricelists_do FOREIGN KEY (pricelist_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2702 (class 2606 OID 308856)
-- Dependencies: 1767 1760 2304
-- Name: fk_privilege_categories; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privilege_categories
    ADD CONSTRAINT fk_privilege_categories FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2703 (class 2606 OID 308861)
-- Dependencies: 1767 1742 2256
-- Name: fk_privilege_categories_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privilege_categories
    ADD CONSTRAINT fk_privilege_categories_do FOREIGN KEY (privilege_category_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2704 (class 2606 OID 308866)
-- Dependencies: 1767 1783 2375
-- Name: fk_privilege_categories_privilege_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privilege_categories
    ADD CONSTRAINT fk_privilege_categories_privilege_type FOREIGN KEY (privilege_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2705 (class 2606 OID 308871)
-- Dependencies: 1768 1783 2375
-- Name: fk_privilege_roles_access_level; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privilege_roles
    ADD CONSTRAINT fk_privilege_roles_access_level FOREIGN KEY (access_level_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2706 (class 2606 OID 308876)
-- Dependencies: 1768 1783 2375
-- Name: fk_privilege_roles_access_rights; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privilege_roles
    ADD CONSTRAINT fk_privilege_roles_access_rights FOREIGN KEY (access_right_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2707 (class 2606 OID 308881)
-- Dependencies: 1768 1742 2256
-- Name: fk_privilege_roles_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privilege_roles
    ADD CONSTRAINT fk_privilege_roles_do FOREIGN KEY (privilege_role_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2708 (class 2606 OID 308886)
-- Dependencies: 1768 1769 2332
-- Name: fk_privilege_roles_privileges; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privilege_roles
    ADD CONSTRAINT fk_privilege_roles_privileges FOREIGN KEY (privilege_id) REFERENCES privileges(privilege_id);


--
-- TOC entry 2709 (class 2606 OID 308891)
-- Dependencies: 1769 1742 2256
-- Name: fk_privileges_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privileges
    ADD CONSTRAINT fk_privileges_do FOREIGN KEY (privilege_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2710 (class 2606 OID 308896)
-- Dependencies: 1769 1742 2256
-- Name: fk_privileges_entity; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privileges
    ADD CONSTRAINT fk_privileges_entity FOREIGN KEY (data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2711 (class 2606 OID 308901)
-- Dependencies: 1769 1741 2252
-- Name: fk_privileges_entity_types; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privileges
    ADD CONSTRAINT fk_privileges_entity_types FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2712 (class 2606 OID 308906)
-- Dependencies: 1769 1783 2375
-- Name: fk_privileges_permission_categories; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privileges
    ADD CONSTRAINT fk_privileges_permission_categories FOREIGN KEY (permission_category_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2713 (class 2606 OID 308911)
-- Dependencies: 1769 1767 2324
-- Name: fk_privileges_privilege_categories; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privileges
    ADD CONSTRAINT fk_privileges_privilege_categories FOREIGN KEY (privilege_category_id) REFERENCES privilege_categories(privilege_category_id);


--
-- TOC entry 2714 (class 2606 OID 308916)
-- Dependencies: 1769 1787 2383
-- Name: fk_privileges_security_roles; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privileges
    ADD CONSTRAINT fk_privileges_security_roles FOREIGN KEY (security_role_id) REFERENCES security_roles(security_role_id);


--
-- TOC entry 2715 (class 2606 OID 308921)
-- Dependencies: 1769 1783 2375
-- Name: fk_privileges_special_permissions; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privileges
    ADD CONSTRAINT fk_privileges_special_permissions FOREIGN KEY (special_permission_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2718 (class 2606 OID 308926)
-- Dependencies: 1770 1742 2256
-- Name: fk_product_categories_product_category_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT fk_product_categories_product_category_id FOREIGN KEY (product_category_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2839 (class 2606 OID 308931)
-- Dependencies: 1789 1770 2336
-- Name: fk_product_category_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_product_category_id FOREIGN KEY (category_id) REFERENCES product_categories(product_category_id);


--
-- TOC entry 2719 (class 2606 OID 308936)
-- Dependencies: 1771 1760 2304
-- Name: fk_product_percent_values_organization; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_percent_values
    ADD CONSTRAINT fk_product_percent_values_organization FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2724 (class 2606 OID 308941)
-- Dependencies: 1772 1783 2375
-- Name: fk_product_suppliers_currency; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk_product_suppliers_currency FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2725 (class 2606 OID 308946)
-- Dependencies: 1772 1789 2389
-- Name: fk_product_suppliers_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk_product_suppliers_product FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2726 (class 2606 OID 308951)
-- Dependencies: 1772 1783 2375
-- Name: fk_product_suppliers_resources; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk_product_suppliers_resources FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2727 (class 2606 OID 308956)
-- Dependencies: 1772 1714 2170
-- Name: fk_product_suppliers_supplier; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk_product_suppliers_supplier FOREIGN KEY (supplier_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2728 (class 2606 OID 308961)
-- Dependencies: 1773 1742 2256
-- Name: fk_products1_product_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_products1_product_id FOREIGN KEY (product_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2840 (class 2606 OID 308966)
-- Dependencies: 1789 1783 2375
-- Name: fk_products_color_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_products_color_id FOREIGN KEY (product_color_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2729 (class 2606 OID 308971)
-- Dependencies: 1773 1783 2375
-- Name: fk_products_currency_resource; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_products_currency_resource FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2841 (class 2606 OID 308976)
-- Dependencies: 1789 1783 2375
-- Name: fk_products_dimension_unit_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_products_dimension_unit_id FOREIGN KEY (dimension_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2730 (class 2606 OID 308981)
-- Dependencies: 1773 1783 2375
-- Name: fk_products_measure_unit_resource; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_products_measure_unit_resource FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2842 (class 2606 OID 308986)
-- Dependencies: 1789 1783 2375
-- Name: fk_products_weight_unit_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_products_weight_unit_id FOREIGN KEY (weight_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2737 (class 2606 OID 308991)
-- Dependencies: 1774 1742 2256
-- Name: fk_purchase_invoice_items_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT fk_purchase_invoice_items_do FOREIGN KEY (invoice_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2738 (class 2606 OID 308996)
-- Dependencies: 1774 1775 2354
-- Name: fk_purchase_invoice_items_goods_receipts; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT fk_purchase_invoice_items_goods_receipts FOREIGN KEY (invoice_id) REFERENCES purchase_invoices(invoice_id);


--
-- TOC entry 2739 (class 2606 OID 309001)
-- Dependencies: 1774 1783 2375
-- Name: fk_purchase_invoice_items_measure_unit_rb; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT fk_purchase_invoice_items_measure_unit_rb FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2740 (class 2606 OID 309006)
-- Dependencies: 1774 1756 2296
-- Name: fk_purchase_invoice_items_order_confirmations; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT fk_purchase_invoice_items_order_confirmations FOREIGN KEY (order_confirmation_item_id) REFERENCES order_confirmation_items(confirmation_item_id);


--
-- TOC entry 2741 (class 2606 OID 309011)
-- Dependencies: 1774 1776 2356
-- Name: fk_purchase_invoice_items_poi; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT fk_purchase_invoice_items_poi FOREIGN KEY (purchase_order_item_id) REFERENCES purchase_order_items(order_item_id);


--
-- TOC entry 2742 (class 2606 OID 309016)
-- Dependencies: 1774 1773 2346
-- Name: fk_purchase_invoice_items_products; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT fk_purchase_invoice_items_products FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2743 (class 2606 OID 309021)
-- Dependencies: 1775 1710 2158
-- Name: fk_purchase_invoices_bank_details; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoices
    ADD CONSTRAINT fk_purchase_invoices_bank_details FOREIGN KEY (bank_detail_id) REFERENCES bank_details(bank_detail_id);


--
-- TOC entry 2744 (class 2606 OID 309026)
-- Dependencies: 1775 1713 2166
-- Name: fk_purchase_invoices_business_documents; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoices
    ADD CONSTRAINT fk_purchase_invoices_business_documents FOREIGN KEY (invoice_id) REFERENCES business_documents(document_id);


--
-- TOC entry 2745 (class 2606 OID 309031)
-- Dependencies: 1775 1783 2375
-- Name: fk_purchase_invoices_currency; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoices
    ADD CONSTRAINT fk_purchase_invoices_currency FOREIGN KEY (document_currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2746 (class 2606 OID 309036)
-- Dependencies: 1775 1783 2375
-- Name: fk_purchase_invoices_delivery_resource_bundle; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoices
    ADD CONSTRAINT fk_purchase_invoices_delivery_resource_bundle FOREIGN KEY (delivery_terms_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2747 (class 2606 OID 309041)
-- Dependencies: 1775 1783 2375
-- Name: fk_purchase_invoices_payment_resource_bundle; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoices
    ADD CONSTRAINT fk_purchase_invoices_payment_resource_bundle FOREIGN KEY (payment_terms_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2748 (class 2606 OID 309046)
-- Dependencies: 1775 1714 2170
-- Name: fk_purchase_invoices_supplier_b_partners; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoices
    ADD CONSTRAINT fk_purchase_invoices_supplier_b_partners FOREIGN KEY (supplier_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2749 (class 2606 OID 309051)
-- Dependencies: 1775 1704 2135
-- Name: fk_purchase_invoices_supplier_branch_addresses; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoices
    ADD CONSTRAINT fk_purchase_invoices_supplier_branch_addresses FOREIGN KEY (supplier_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2750 (class 2606 OID 309056)
-- Dependencies: 1775 1727 2207
-- Name: fk_purchase_invoices_supplier_contact_persons; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoices
    ADD CONSTRAINT fk_purchase_invoices_supplier_contact_persons FOREIGN KEY (supplier_contact_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2751 (class 2606 OID 309061)
-- Dependencies: 1776 1742 2256
-- Name: fk_purchase_order_items_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fk_purchase_order_items_do_id FOREIGN KEY (order_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2752 (class 2606 OID 309066)
-- Dependencies: 1776 1783 2375
-- Name: fk_purchase_order_items_measure_unit; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fk_purchase_order_items_measure_unit FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2753 (class 2606 OID 309071)
-- Dependencies: 1776 1789 2389
-- Name: fk_purchase_order_items_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fk_purchase_order_items_product FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2768 (class 2606 OID 309076)
-- Dependencies: 1778 1789 2389
-- Name: fk_real_products_simple_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY real_products
    ADD CONSTRAINT fk_real_products_simple_product FOREIGN KEY (simple_product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2769 (class 2606 OID 309081)
-- Dependencies: 1778 1800 2428
-- Name: fk_real_products_virtual_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY real_products
    ADD CONSTRAINT fk_real_products_virtual_product FOREIGN KEY (product_id) REFERENCES virtual_products(product_id);


--
-- TOC entry 2773 (class 2606 OID 309086)
-- Dependencies: 2256 1742 1779
-- Name: fk_receipt_certificate_items_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk_receipt_certificate_items_do_id FOREIGN KEY (certificate_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2774 (class 2606 OID 309091)
-- Dependencies: 1779 1783 2375
-- Name: fk_receipt_certificate_items_measure_unit; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk_receipt_certificate_items_measure_unit FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2775 (class 2606 OID 309096)
-- Dependencies: 1779 1789 2389
-- Name: fk_receipt_certificate_items_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk_receipt_certificate_items_product FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2776 (class 2606 OID 309101)
-- Dependencies: 1779 1781 2368
-- Name: fk_receipt_certificate_items_receipt_cert; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk_receipt_certificate_items_receipt_cert FOREIGN KEY (parent_id) REFERENCES receipt_certificates(receipt_certificate_id);


--
-- TOC entry 2778 (class 2606 OID 309106)
-- Dependencies: 1780 1779 2364
-- Name: fk_receipt_certificate_serial_numbers_dci; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_serial_numbers
    ADD CONSTRAINT fk_receipt_certificate_serial_numbers_dci FOREIGN KEY (certificate_item_id) REFERENCES receipt_certificate_items(certificate_item_id);


--
-- TOC entry 2779 (class 2606 OID 309111)
-- Dependencies: 1781 1738 2244
-- Name: fk_receipt_certificates_deliverer; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_deliverer FOREIGN KEY (deliverer_id) REFERENCES data_object_links(data_object_link_id);


--
-- TOC entry 2780 (class 2606 OID 309116)
-- Dependencies: 1781 1742 2256
-- Name: fk_receipt_certificates_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_do_id FOREIGN KEY (receipt_certificate_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2781 (class 2606 OID 309121)
-- Dependencies: 1781 1783 2375
-- Name: fk_receipt_certificates_reason; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_reason FOREIGN KEY (receipt_cert_reason_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2782 (class 2606 OID 309126)
-- Dependencies: 1781 1783 2375
-- Name: fk_receipt_certificates_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_type FOREIGN KEY (receipt_cert_method_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2783 (class 2606 OID 309131)
-- Dependencies: 1781 1802 2432
-- Name: fk_receipt_certificates_warehouse; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2633 (class 2606 OID 309136)
-- Dependencies: 1747 1704 2135
-- Name: fk_recipient_address_constraint; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_recipient_address_constraint FOREIGN KEY (recipient_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2634 (class 2606 OID 309141)
-- Dependencies: 1747 1727 2207
-- Name: fk_recipient_contact_person; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_recipient_contact_person FOREIGN KEY (recipient_contact_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2793 (class 2606 OID 309146)
-- Dependencies: 1783 1749 2278
-- Name: fk_resource_bundle_enum_class_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY resource_bundle
    ADD CONSTRAINT fk_resource_bundle_enum_class_id FOREIGN KEY (enum_class_id) REFERENCES enum_classes(enum_class_id);


--
-- TOC entry 2836 (class 2606 OID 309151)
-- Dependencies: 1787 1716 2177
-- Name: fk_security_roles_business_units; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY security_roles
    ADD CONSTRAINT fk_security_roles_business_units FOREIGN KEY (business_unit_id) REFERENCES business_units(business_unit_id);


--
-- TOC entry 2837 (class 2606 OID 309156)
-- Dependencies: 1787 1742 2256
-- Name: fk_security_roles_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY security_roles
    ADD CONSTRAINT fk_security_roles_do FOREIGN KEY (security_role_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2838 (class 2606 OID 309161)
-- Dependencies: 1787 1760 2304
-- Name: fk_security_roles_organizations; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY security_roles
    ADD CONSTRAINT fk_security_roles_organizations FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2843 (class 2606 OID 309166)
-- Dependencies: 1789 1771 2340
-- Name: fk_simple_products_customs_duty; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_simple_products_customs_duty FOREIGN KEY (customs_duty_percent_id) REFERENCES product_percent_values(percent_value_id);


--
-- TOC entry 2844 (class 2606 OID 309171)
-- Dependencies: 1789 1771 2340
-- Name: fk_simple_products_discount; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_simple_products_discount FOREIGN KEY (discount_percent_id) REFERENCES product_percent_values(percent_value_id);


--
-- TOC entry 2845 (class 2606 OID 309176)
-- Dependencies: 1789 1771 2340
-- Name: fk_simple_products_excise_duty; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_simple_products_excise_duty FOREIGN KEY (excise_duty_percent_id) REFERENCES product_percent_values(percent_value_id);


--
-- TOC entry 2846 (class 2606 OID 309181)
-- Dependencies: 1789 1773 2346
-- Name: fk_simple_products_products; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_simple_products_products FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2847 (class 2606 OID 309186)
-- Dependencies: 1789 1771 2340
-- Name: fk_simple_products_profit; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_simple_products_profit FOREIGN KEY (profit_percent_id) REFERENCES product_percent_values(percent_value_id);


--
-- TOC entry 2848 (class 2606 OID 309191)
-- Dependencies: 1789 1771 2340
-- Name: fk_simple_products_transport; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_simple_products_transport FOREIGN KEY (transport_percent_id) REFERENCES product_percent_values(percent_value_id);


--
-- TOC entry 2867 (class 2606 OID 309196)
-- Dependencies: 1790 1742 2256
-- Name: fk_team_members_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY team_members
    ADD CONSTRAINT fk_team_members_do FOREIGN KEY (team_member_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2868 (class 2606 OID 309201)
-- Dependencies: 1790 1783 2375
-- Name: fk_team_members_status; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY team_members
    ADD CONSTRAINT fk_team_members_status FOREIGN KEY (status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2869 (class 2606 OID 309206)
-- Dependencies: 1790 1791 2395
-- Name: fk_team_members_teams; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY team_members
    ADD CONSTRAINT fk_team_members_teams FOREIGN KEY (team_id) REFERENCES teams(team_id);


--
-- TOC entry 2870 (class 2606 OID 309211)
-- Dependencies: 1790 1794 2407
-- Name: fk_team_members_users; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY team_members
    ADD CONSTRAINT fk_team_members_users FOREIGN KEY (user_organization_id) REFERENCES user_organizations(user_organization_id);


--
-- TOC entry 2871 (class 2606 OID 309216)
-- Dependencies: 1791 1716 2177
-- Name: fk_teams_business_units; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY teams
    ADD CONSTRAINT fk_teams_business_units FOREIGN KEY (business_unit_id) REFERENCES business_units(business_unit_id);


--
-- TOC entry 2872 (class 2606 OID 309221)
-- Dependencies: 1791 1742 2256
-- Name: fk_teams_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY teams
    ADD CONSTRAINT fk_teams_do FOREIGN KEY (team_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2873 (class 2606 OID 309226)
-- Dependencies: 1791 1760 2304
-- Name: fk_teams_organizations; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY teams
    ADD CONSTRAINT fk_teams_organizations FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2874 (class 2606 OID 309231)
-- Dependencies: 1791 1783 2375
-- Name: fk_teams_status; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY teams
    ADD CONSTRAINT fk_teams_status FOREIGN KEY (status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2875 (class 2606 OID 309236)
-- Dependencies: 1792 1742 2256
-- Name: fk_user_group_members_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_group_members
    ADD CONSTRAINT fk_user_group_members_do FOREIGN KEY (user_group_member_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2876 (class 2606 OID 309241)
-- Dependencies: 1794 1792 2407
-- Name: fk_user_group_members_u; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_group_members
    ADD CONSTRAINT fk_user_group_members_u FOREIGN KEY (user_organization_id) REFERENCES user_organizations(user_organization_id);


--
-- TOC entry 2877 (class 2606 OID 309246)
-- Dependencies: 1792 1793 2405
-- Name: fk_user_group_members_ug; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_group_members
    ADD CONSTRAINT fk_user_group_members_ug FOREIGN KEY (user_group_id) REFERENCES user_groups(user_group_id);


--
-- TOC entry 2878 (class 2606 OID 309251)
-- Dependencies: 1793 1742 2256
-- Name: fk_user_groups_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_groups
    ADD CONSTRAINT fk_user_groups_do FOREIGN KEY (user_group_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2880 (class 2606 OID 309256)
-- Dependencies: 1794 1704 2135
-- Name: fk_user_organizations_branch; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_organizations
    ADD CONSTRAINT fk_user_organizations_branch FOREIGN KEY (branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2886 (class 2606 OID 326401)
-- Dependencies: 2177 1794 1716
-- Name: fk_user_organizations_business_units; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_organizations
    ADD CONSTRAINT fk_user_organizations_business_units FOREIGN KEY (business_unit_id) REFERENCES business_units(business_unit_id);


--
-- TOC entry 2881 (class 2606 OID 309266)
-- Dependencies: 1794 1742 2256
-- Name: fk_user_organizations_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_organizations
    ADD CONSTRAINT fk_user_organizations_do FOREIGN KEY (user_organization_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2882 (class 2606 OID 309271)
-- Dependencies: 1794 1755 2292
-- Name: fk_user_organizations_job_title; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_organizations
    ADD CONSTRAINT fk_user_organizations_job_title FOREIGN KEY (job_title_id) REFERENCES job_titles(job_title_id);


--
-- TOC entry 2883 (class 2606 OID 309276)
-- Dependencies: 1794 1798 2422
-- Name: fk_user_organizations_manager; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_organizations
    ADD CONSTRAINT fk_user_organizations_manager FOREIGN KEY (manager_id) REFERENCES users(user_id);


--
-- TOC entry 2884 (class 2606 OID 309281)
-- Dependencies: 1794 1798 2422
-- Name: fk_user_organizations_user; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_organizations
    ADD CONSTRAINT fk_user_organizations_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE;


--
-- TOC entry 2887 (class 2606 OID 309286)
-- Dependencies: 2256 1795 1742
-- Name: fk_user_rights_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights
    ADD CONSTRAINT fk_user_rights_do FOREIGN KEY (data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2888 (class 2606 OID 309291)
-- Dependencies: 1741 2252 1795
-- Name: fk_user_rights_dot; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights
    ADD CONSTRAINT fk_user_rights_dot FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2889 (class 2606 OID 309296)
-- Dependencies: 2304 1795 1760
-- Name: fk_user_rights_organizations; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights
    ADD CONSTRAINT fk_user_rights_organizations FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2890 (class 2606 OID 309301)
-- Dependencies: 1783 1795 2375
-- Name: fk_user_rights_permission_categories; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights
    ADD CONSTRAINT fk_user_rights_permission_categories FOREIGN KEY (permission_category_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2891 (class 2606 OID 309306)
-- Dependencies: 1783 2375 1795
-- Name: fk_user_rights_permissions; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights
    ADD CONSTRAINT fk_user_rights_permissions FOREIGN KEY (special_permission_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2892 (class 2606 OID 309311)
-- Dependencies: 2405 1795 1793
-- Name: fk_user_rights_user_groups; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights
    ADD CONSTRAINT fk_user_rights_user_groups FOREIGN KEY (user_group_id) REFERENCES user_groups(user_group_id);


--
-- TOC entry 2893 (class 2606 OID 309316)
-- Dependencies: 1798 1795 2422
-- Name: fk_user_rights_users; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights
    ADD CONSTRAINT fk_user_rights_users FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- TOC entry 2909 (class 2606 OID 309321)
-- Dependencies: 2256 1797 1742
-- Name: fk_user_security_roles; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_security_roles
    ADD CONSTRAINT fk_user_security_roles FOREIGN KEY (user_security_role_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2910 (class 2606 OID 309326)
-- Dependencies: 2383 1797 1787
-- Name: fk_user_security_roles_sr; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_security_roles
    ADD CONSTRAINT fk_user_security_roles_sr FOREIGN KEY (security_role_id) REFERENCES security_roles(security_role_id);


--
-- TOC entry 2911 (class 2606 OID 309331)
-- Dependencies: 1794 1797 2407
-- Name: fk_user_security_roles_users; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_security_roles
    ADD CONSTRAINT fk_user_security_roles_users FOREIGN KEY (user_organization_id) REFERENCES user_organizations(user_organization_id);


--
-- TOC entry 2912 (class 2606 OID 309336)
-- Dependencies: 1798 2422 1798
-- Name: fk_users_creator; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk_users_creator FOREIGN KEY (creator_id) REFERENCES users(user_id);


--
-- TOC entry 2913 (class 2606 OID 309341)
-- Dependencies: 1798 1742 2256
-- Name: fk_users_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk_users_do FOREIGN KEY (user_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2915 (class 2606 OID 310041)
-- Dependencies: 2304 1798 1760
-- Name: fk_users_system_organization; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk_users_system_organization FOREIGN KEY (system_organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2916 (class 2606 OID 309346)
-- Dependencies: 1742 2256 1800
-- Name: fk_virtual_products_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY virtual_products
    ADD CONSTRAINT fk_virtual_products_do FOREIGN KEY (product_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2921 (class 2606 OID 309351)
-- Dependencies: 2135 1704 1802
-- Name: fk_warehouses_address_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouses
    ADD CONSTRAINT fk_warehouses_address_id FOREIGN KEY (address_id) REFERENCES addresses(address_id);


--
-- TOC entry 2922 (class 2606 OID 309356)
-- Dependencies: 2256 1742 1802
-- Name: fk_warehouses_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouses
    ADD CONSTRAINT fk_warehouses_do_id FOREIGN KEY (warehouse_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2754 (class 2606 OID 309361)
-- Dependencies: 1776 1783 2375
-- Name: fka00989511ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fka00989511ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2755 (class 2606 OID 309366)
-- Dependencies: 2375 1783 1776
-- Name: fka00989513aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fka00989513aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2756 (class 2606 OID 309371)
-- Dependencies: 1776 1789 2389
-- Name: fka0098951a330dfcf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fka0098951a330dfcf FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2757 (class 2606 OID 309376)
-- Dependencies: 2389 1789 1776
-- Name: fka0098951f10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fka0098951f10b9721 FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2643 (class 2606 OID 309381)
-- Dependencies: 1751 2262 1745
-- Name: fka5191bf0c6b416d1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_dc_items
    ADD CONSTRAINT fka5191bf0c6b416d1 FOREIGN KEY (delivery_certificate_item_id) REFERENCES delivery_certificate_items(certificate_item_id);


--
-- TOC entry 2644 (class 2606 OID 309386)
-- Dependencies: 1752 1751 2286
-- Name: fka5191bf0de886089; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_dc_items
    ADD CONSTRAINT fka5191bf0de886089 FOREIGN KEY (receipt_item_id) REFERENCES goods_receipt_items(receipt_item_id);


--
-- TOC entry 2640 (class 2606 OID 309391)
-- Dependencies: 1750 2304 1760
-- Name: fka76c0db51b04573; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY expressions
    ADD CONSTRAINT fka76c0db51b04573 FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2456 (class 2606 OID 309396)
-- Dependencies: 1708 1783 2375
-- Name: fka7bdcfd22644a070; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT fka7bdcfd22644a070 FOREIGN KEY (algorithm_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2457 (class 2606 OID 309401)
-- Dependencies: 1708 2152 1709
-- Name: fka7bdcfd2a7031f5f; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT fka7bdcfd2a7031f5f FOREIGN KEY (assembling_schema_id) REFERENCES assembling_schemas(product_id);


--
-- TOC entry 2458 (class 2606 OID 309406)
-- Dependencies: 1708 2375 1783
-- Name: fka7bdcfd2bca61a30; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT fka7bdcfd2bca61a30 FOREIGN KEY (data_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2459 (class 2606 OID 309411)
-- Dependencies: 1708 2144 1706
-- Name: fka7bdcfd2dcdf9385; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT fka7bdcfd2dcdf9385 FOREIGN KEY (message_id) REFERENCES assembling_messages(message_id);


--
-- TOC entry 2607 (class 2606 OID 309416)
-- Dependencies: 1745 2375 1783
-- Name: fka8be2f8d1ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fka8be2f8d1ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2608 (class 2606 OID 309421)
-- Dependencies: 2346 1773 1745
-- Name: fka8be2f8df10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fka8be2f8df10b9721 FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2543 (class 2606 OID 309426)
-- Dependencies: 1729 2375 1783
-- Name: fkaa34146576597079; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY currency_exchange_rates
    ADD CONSTRAINT fkaa34146576597079 FOREIGN KEY (from_currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2544 (class 2606 OID 309431)
-- Dependencies: 2375 1729 1783
-- Name: fkaa341465d8e0b5ca; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY currency_exchange_rates
    ADD CONSTRAINT fkaa341465d8e0b5ca FOREIGN KEY (to_currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2610 (class 2606 OID 309486)
-- Dependencies: 1745 1746 2262
-- Name: fkb3b02dd2d961ef9c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_serial_numbers
    ADD CONSTRAINT fkb3b02dd2d961ef9c FOREIGN KEY (certificate_item_id) REFERENCES delivery_certificate_items(certificate_item_id);


--
-- TOC entry 2819 (class 2606 OID 309491)
-- Dependencies: 2170 1786 1714
-- Name: fkbbc878b9134fe2b0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b9134fe2b0 FOREIGN KEY (recipient_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2820 (class 2606 OID 309496)
-- Dependencies: 2375 1783 1786
-- Name: fkbbc878b917174fab; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b917174fab FOREIGN KEY (transportation_method_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2821 (class 2606 OID 309501)
-- Dependencies: 2375 1783 1786
-- Name: fkbbc878b91808129a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b91808129a FOREIGN KEY (doc_delivery_method_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2822 (class 2606 OID 309506)
-- Dependencies: 1786 2135 1704
-- Name: fkbbc878b927a66c93; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b927a66c93 FOREIGN KEY (branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2823 (class 2606 OID 309511)
-- Dependencies: 2375 1786 1783
-- Name: fkbbc878b93aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b93aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2824 (class 2606 OID 309516)
-- Dependencies: 1786 2375 1783
-- Name: fkbbc878b93dc9408c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b93dc9408c FOREIGN KEY (payment_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2825 (class 2606 OID 309521)
-- Dependencies: 1786 1783 2375
-- Name: fkbbc878b946685c7a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b946685c7a FOREIGN KEY (delivery_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2826 (class 2606 OID 309526)
-- Dependencies: 2313 1763 1786
-- Name: fkbbc878b94da6f8bc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b94da6f8bc FOREIGN KEY (creator_id) REFERENCES persons(person_id);


--
-- TOC entry 2827 (class 2606 OID 309531)
-- Dependencies: 1786 1714 2170
-- Name: fkbbc878b96d20f4c9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b96d20f4c9 FOREIGN KEY (shippingagent_partner_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2828 (class 2606 OID 309536)
-- Dependencies: 1727 1786 2207
-- Name: fkbbc878b97ebcc009; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b97ebcc009 FOREIGN KEY (supplier_contact_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2829 (class 2606 OID 309541)
-- Dependencies: 1783 2375 1786
-- Name: fkbbc878b996e3ba71; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b996e3ba71 FOREIGN KEY (payment_terms_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2830 (class 2606 OID 309546)
-- Dependencies: 1786 2375 1783
-- Name: fkbbc878b99a24d298; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b99a24d298 FOREIGN KEY (deliverystatus_resource_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2831 (class 2606 OID 309551)
-- Dependencies: 2375 1786 1783
-- Name: fkbbc878b99c49320d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b99c49320d FOREIGN KEY (status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2832 (class 2606 OID 309556)
-- Dependencies: 2207 1786 1727
-- Name: fkbbc878b99ff294dc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b99ff294dc FOREIGN KEY (attendee_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2833 (class 2606 OID 309561)
-- Dependencies: 1786 2375 1783
-- Name: fkbbc878b9a94f3ab3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b9a94f3ab3 FOREIGN KEY (invoice_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2834 (class 2606 OID 309566)
-- Dependencies: 1783 1786 2375
-- Name: fkbbc878b9b07d659a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b9b07d659a FOREIGN KEY (vat_condition_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2835 (class 2606 OID 309571)
-- Dependencies: 2313 1786 1763
-- Name: fkbbc878b9fd5b3613; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b9fd5b3613 FOREIGN KEY (sender_id) REFERENCES persons(person_id);


--
-- TOC entry 2655 (class 2606 OID 309581)
-- Dependencies: 2352 1753 1774
-- Name: fkc10c99ea219348c0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_pi_items
    ADD CONSTRAINT fkc10c99ea219348c0 FOREIGN KEY (invoice_item_id) REFERENCES purchase_invoice_items(invoice_item_id);


--
-- TOC entry 2656 (class 2606 OID 309586)
-- Dependencies: 1752 2286 1753
-- Name: fkc10c99eade886089; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_pi_items
    ADD CONSTRAINT fkc10c99eade886089 FOREIGN KEY (receipt_item_id) REFERENCES goods_receipt_items(receipt_item_id);


--
-- TOC entry 2590 (class 2606 OID 309591)
-- Dependencies: 1740 1760 2304
-- Name: fkc1a9b66a51b04573; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_type_permissions
    ADD CONSTRAINT fkc1a9b66a51b04573 FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2591 (class 2606 OID 309596)
-- Dependencies: 1741 1740 2252
-- Name: fkc1a9b66aa44e2131; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_type_permissions
    ADD CONSTRAINT fkc1a9b66aa44e2131 FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2592 (class 2606 OID 309601)
-- Dependencies: 1740 1783 2375
-- Name: fkc1a9b66ab01192e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_type_permissions
    ADD CONSTRAINT fkc1a9b66ab01192e FOREIGN KEY (user_right_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2593 (class 2606 OID 309606)
-- Dependencies: 1740 1783 2375
-- Name: fkc1a9b66ac2559310; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_type_permissions
    ADD CONSTRAINT fkc1a9b66ac2559310 FOREIGN KEY (permission_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2758 (class 2606 OID 309611)
-- Dependencies: 1777 1783 2375
-- Name: fkc307e7e31808129a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e31808129a FOREIGN KEY (doc_delivery_method_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2759 (class 2606 OID 309616)
-- Dependencies: 1777 2135 1704
-- Name: fkc307e7e327a66c93; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e327a66c93 FOREIGN KEY (branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2760 (class 2606 OID 309621)
-- Dependencies: 1777 1763 2313
-- Name: fkc307e7e34da6f8bc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e34da6f8bc FOREIGN KEY (creator_id) REFERENCES persons(person_id);


--
-- TOC entry 2761 (class 2606 OID 309626)
-- Dependencies: 1777 2170 1714
-- Name: fkc307e7e35aa049f4; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e35aa049f4 FOREIGN KEY (supplier_partner_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2762 (class 2606 OID 309631)
-- Dependencies: 1727 1777 2207
-- Name: fkc307e7e37ebcc009; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e37ebcc009 FOREIGN KEY (supplier_contact_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2763 (class 2606 OID 309636)
-- Dependencies: 1783 2375 1777
-- Name: fkc307e7e39a24d298; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e39a24d298 FOREIGN KEY (deliverystatus_resource_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2764 (class 2606 OID 309641)
-- Dependencies: 2375 1783 1777
-- Name: fkc307e7e39c49320d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e39c49320d FOREIGN KEY (status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2765 (class 2606 OID 309646)
-- Dependencies: 2313 1777 1763
-- Name: fkc307e7e3fd5b3613; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e3fd5b3613 FOREIGN KEY (sender_id) REFERENCES persons(person_id);


--
-- TOC entry 2849 (class 2606 OID 309651)
-- Dependencies: 1783 2375 1789
-- Name: fkc42bd16427acd874; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd16427acd874 FOREIGN KEY (weight_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2850 (class 2606 OID 309656)
-- Dependencies: 2336 1770 1789
-- Name: fkc42bd1646e2f83d0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd1646e2f83d0 FOREIGN KEY (category_id) REFERENCES product_categories(product_category_id);


--
-- TOC entry 2851 (class 2606 OID 309661)
-- Dependencies: 2170 1714 1789
-- Name: fkc42bd1646e436697; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd1646e436697 FOREIGN KEY (producer_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2852 (class 2606 OID 309666)
-- Dependencies: 1762 1789 2311
-- Name: fkc42bd1647a956e19; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd1647a956e19 FOREIGN KEY (pattern_mask_format_id) REFERENCES pattern_mask_formats(pattern_mask_format_id);


--
-- TOC entry 2853 (class 2606 OID 309671)
-- Dependencies: 1789 1783 2375
-- Name: fkc42bd1648f60524c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd1648f60524c FOREIGN KEY (product_color_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2854 (class 2606 OID 309676)
-- Dependencies: 1789 1783 2375
-- Name: fkc42bd1649d6c3562; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd1649d6c3562 FOREIGN KEY (dimension_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2514 (class 2606 OID 309681)
-- Dependencies: 1720 1723 2196
-- Name: fkc436c2e88e8748f3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classified_objects
    ADD CONSTRAINT fkc436c2e88e8748f3 FOREIGN KEY (classifier_id) REFERENCES classifiers(classifier_id);


--
-- TOC entry 2515 (class 2606 OID 309686)
-- Dependencies: 1720 2256 1742
-- Name: fkc436c2e8cf1f1951; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classified_objects
    ADD CONSTRAINT fkc436c2e8cf1f1951 FOREIGN KEY (classified_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2687 (class 2606 OID 309691)
-- Dependencies: 1704 2135 1761
-- Name: fkc84af6a180bd868d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT fkc84af6a180bd868d FOREIGN KEY (issuer_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2688 (class 2606 OID 309696)
-- Dependencies: 1761 1783 2375
-- Name: fkc84af6a1ad6ece98; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT fkc84af6a1ad6ece98 FOREIGN KEY (passport_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2689 (class 2606 OID 309701)
-- Dependencies: 1761 2304 1760
-- Name: fkc84af6a1db08a2d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT fkc84af6a1db08a2d FOREIGN KEY (issuer_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2463 (class 2606 OID 309706)
-- Dependencies: 1783 1709 2375
-- Name: fke7efd4c21ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT fke7efd4c21ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2464 (class 2606 OID 309711)
-- Dependencies: 1705 1709 2138
-- Name: fke7efd4c26d4edb2f; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT fke7efd4c26d4edb2f FOREIGN KEY (category_id) REFERENCES assembling_categories(assembling_category_id);


--
-- TOC entry 2465 (class 2606 OID 309716)
-- Dependencies: 1800 2428 1709
-- Name: fke7efd4c29f88efd5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT fke7efd4c29f88efd5 FOREIGN KEY (product_id) REFERENCES virtual_products(product_id);


--
-- TOC entry 2855 (class 2606 OID 309721)
-- Dependencies: 1789 1783 2375
-- Name: fke810995127acd874; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke810995127acd874 FOREIGN KEY (weight_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2856 (class 2606 OID 309726)
-- Dependencies: 1770 1789 2336
-- Name: fke81099516e2f83d0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke81099516e2f83d0 FOREIGN KEY (category_id) REFERENCES product_categories(product_category_id);


--
-- TOC entry 2857 (class 2606 OID 309731)
-- Dependencies: 1789 1714 2170
-- Name: fke81099516e436697; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke81099516e436697 FOREIGN KEY (producer_id) REFERENCES business_partners(business_partner_id);


--
-- TOC entry 2858 (class 2606 OID 309736)
-- Dependencies: 1789 1762 2311
-- Name: fke81099517a956e19; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke81099517a956e19 FOREIGN KEY (pattern_mask_format_id) REFERENCES pattern_mask_formats(pattern_mask_format_id);


--
-- TOC entry 2859 (class 2606 OID 309741)
-- Dependencies: 1771 2340 1789
-- Name: fke810995185c08915; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke810995185c08915 FOREIGN KEY (discount_percent_id) REFERENCES product_percent_values(percent_value_id);


--
-- TOC entry 2860 (class 2606 OID 309746)
-- Dependencies: 1789 2375 1783
-- Name: fke81099518f60524c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke81099518f60524c FOREIGN KEY (product_color_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2861 (class 2606 OID 309751)
-- Dependencies: 1789 1771 2340
-- Name: fke810995193879943; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke810995193879943 FOREIGN KEY (customs_duty_percent_id) REFERENCES product_percent_values(percent_value_id);


--
-- TOC entry 2862 (class 2606 OID 309756)
-- Dependencies: 1783 1789 2375
-- Name: fke81099519d6c3562; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke81099519d6c3562 FOREIGN KEY (dimension_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2863 (class 2606 OID 309761)
-- Dependencies: 1789 1771 2340
-- Name: fke8109951b75e188c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke8109951b75e188c FOREIGN KEY (excise_duty_percent_id) REFERENCES product_percent_values(percent_value_id);


--
-- TOC entry 2864 (class 2606 OID 309766)
-- Dependencies: 1789 1771 2340
-- Name: fke8109951e459462d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke8109951e459462d FOREIGN KEY (transport_percent_id) REFERENCES product_percent_values(percent_value_id);


--
-- TOC entry 2865 (class 2606 OID 309771)
-- Dependencies: 1789 2340 1771
-- Name: fke8109951f05d1032; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke8109951f05d1032 FOREIGN KEY (profit_percent_id) REFERENCES product_percent_values(percent_value_id);


--
-- TOC entry 2866 (class 2606 OID 309776)
-- Dependencies: 1789 1773 2346
-- Name: fke8109951f10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke8109951f10b9721 FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2784 (class 2606 OID 309781)
-- Dependencies: 2313 1781 1763
-- Name: fke9334463157c075; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke9334463157c075 FOREIGN KEY (forwarder_contact_id) REFERENCES persons(person_id);


--
-- TOC entry 2785 (class 2606 OID 309786)
-- Dependencies: 1763 1781 2313
-- Name: fke93344634da6f8bc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke93344634da6f8bc FOREIGN KEY (creator_id) REFERENCES persons(person_id);


--
-- TOC entry 2786 (class 2606 OID 309791)
-- Dependencies: 1763 1781 2313
-- Name: fke93344636faaa615; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke93344636faaa615 FOREIGN KEY (deliverer_contact_id) REFERENCES persons(person_id);


--
-- TOC entry 2787 (class 2606 OID 309796)
-- Dependencies: 1738 1781 2244
-- Name: fke933446370294164; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke933446370294164 FOREIGN KEY (deliverer_id) REFERENCES data_object_links(data_object_link_id);


--
-- TOC entry 2788 (class 2606 OID 309801)
-- Dependencies: 1802 1781 2432
-- Name: fke93344639f1800e1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke93344639f1800e1 FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2789 (class 2606 OID 309806)
-- Dependencies: 1783 1781 2375
-- Name: fke9334463d6755f5b; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke9334463d6755f5b FOREIGN KEY (receipt_cert_method_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2790 (class 2606 OID 309811)
-- Dependencies: 1760 1781 2304
-- Name: fke9334463f2569c14; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke9334463f2569c14 FOREIGN KEY (forwarder_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2791 (class 2606 OID 309816)
-- Dependencies: 1783 2375 1781
-- Name: fke9334463fe9be307; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke9334463fe9be307 FOREIGN KEY (receipt_cert_reason_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2518 (class 2606 OID 309821)
-- Dependencies: 1721 2196 1723
-- Name: fkefe6ccf38e8748f3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifier_applied_for_dot
    ADD CONSTRAINT fkefe6ccf38e8748f3 FOREIGN KEY (classifier_id) REFERENCES classifiers(classifier_id);


--
-- TOC entry 2519 (class 2606 OID 309826)
-- Dependencies: 2252 1741 1721
-- Name: fkefe6ccf3a44e2131; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifier_applied_for_dot
    ADD CONSTRAINT fkefe6ccf3a44e2131 FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2896 (class 2606 OID 309831)
-- Dependencies: 1793 1796 2405
-- Name: fkf4b9c8cb12dbbc4a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT fkf4b9c8cb12dbbc4a FOREIGN KEY (user_group_id) REFERENCES user_groups(user_group_id);


--
-- TOC entry 2897 (class 2606 OID 309836)
-- Dependencies: 1796 1783 2375
-- Name: fkf4b9c8cb324c6e0a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT fkf4b9c8cb324c6e0a FOREIGN KEY (special_permission_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2898 (class 2606 OID 309841)
-- Dependencies: 2252 1741 1796
-- Name: fkf4b9c8cba44e2131; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT fkf4b9c8cba44e2131 FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2899 (class 2606 OID 309846)
-- Dependencies: 2422 1796 1798
-- Name: fkf4b9c8cbb4a34773; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT fkf4b9c8cbb4a34773 FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- TOC entry 2900 (class 2606 OID 309851)
-- Dependencies: 1796 1742 2256
-- Name: fkf4b9c8cbd741e28; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT fkf4b9c8cbd741e28 FOREIGN KEY (data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2523 (class 2606 OID 309856)
-- Dependencies: 2194 1722 1723
-- Name: fkf7ea2a728508c4de; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT fkf7ea2a728508c4de FOREIGN KEY (classifier_group_id) REFERENCES classifier_groups(classifier_group_id);


--
-- TOC entry 2901 (class 2606 OID 309861)
-- Dependencies: 1796 1793 2405
-- Name: fkfdef48b312dbbc4a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT fkfdef48b312dbbc4a FOREIGN KEY (user_group_id) REFERENCES user_groups(user_group_id);


--
-- TOC entry 2902 (class 2606 OID 309866)
-- Dependencies: 2375 1783 1796
-- Name: fkfdef48b3324c6e0a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT fkfdef48b3324c6e0a FOREIGN KEY (special_permission_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2903 (class 2606 OID 309871)
-- Dependencies: 1796 2252 1741
-- Name: fkfdef48b3a44e2131; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT fkfdef48b3a44e2131 FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2904 (class 2606 OID 309876)
-- Dependencies: 2422 1796 1798
-- Name: fkfdef48b3b4a34773; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT fkfdef48b3b4a34773 FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- TOC entry 2905 (class 2606 OID 309881)
-- Dependencies: 1742 2256 1796
-- Name: fkfdef48b3d741e28; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT fkfdef48b3d741e28 FOREIGN KEY (data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2795 (class 2606 OID 309886)
-- Dependencies: 1784 1785 2379
-- Name: fkfefcb143e94bedc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoice_item_link
    ADD CONSTRAINT fkfefcb143e94bedc FOREIGN KEY (invoice_item_id) REFERENCES sales_invoice_items(invoice_item_id);


--
-- TOC entry 2534 (class 2606 OID 309891)
-- Dependencies: 1726 1773 2346
-- Name: fkff77e413f10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_products
    ADD CONSTRAINT fkff77e413f10b9721 FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2681 (class 2606 OID 309896)
-- Dependencies: 2135 1704 1760
-- Name: organizations_administration_address_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT organizations_administration_address_id_fkey FOREIGN KEY (administration_address_id) REFERENCES addresses(address_id) ON DELETE SET NULL;


--
-- TOC entry 2682 (class 2606 OID 309901)
-- Dependencies: 2375 1760 1783
-- Name: organizations_currency_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT organizations_currency_id_fkey FOREIGN KEY (share_capital_currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2683 (class 2606 OID 309906)
-- Dependencies: 1760 1704 2135
-- Name: organizations_registration_address_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT organizations_registration_address_id_fkey FOREIGN KEY (registration_address_id) REFERENCES addresses(address_id) ON DELETE SET NULL;


--
-- TOC entry 2690 (class 2606 OID 309911)
-- Dependencies: 1763 1761 2313
-- Name: passports_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT passports_parent_id_fkey FOREIGN KEY (parent_id) REFERENCES persons(person_id) ON DELETE CASCADE;


--
-- TOC entry 2879 (class 2606 OID 309926)
-- Dependencies: 1760 1793 2304
-- Name: user_groups_organizations; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_groups
    ADD CONSTRAINT user_groups_organizations FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2906 (class 2606 OID 309931)
-- Dependencies: 1796 1783 2375
-- Name: user_rights_special_permission_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT user_rights_special_permission_id_fkey FOREIGN KEY (special_permission_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2907 (class 2606 OID 309936)
-- Dependencies: 1796 1793 2405
-- Name: user_rights_user_group_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT user_rights_user_group_id_fkey FOREIGN KEY (user_group_id) REFERENCES user_groups(user_group_id) ON DELETE CASCADE;


--
-- TOC entry 2908 (class 2606 OID 309941)
-- Dependencies: 1798 2422 1796
-- Name: user_rights_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT user_rights_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE;


--
-- TOC entry 2885 (class 2606 OID 309946)
-- Dependencies: 2304 1760 1794
-- Name: users_organizations_organization_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_organizations
    ADD CONSTRAINT users_organizations_organization_id_fkey FOREIGN KEY (organization_id) REFERENCES organizations(organization_id) ON DELETE CASCADE;


--
-- TOC entry 2914 (class 2606 OID 309951)
-- Dependencies: 1763 1798 2313
-- Name: users_person_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_person_id_fkey FOREIGN KEY (person_id) REFERENCES persons(person_id);


--
-- TOC entry 3030 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: -
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2009-09-23 16:09:23

--
-- PostgreSQL database dump complete
--

