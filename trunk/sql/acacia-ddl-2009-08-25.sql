--
-- PostgreSQL database dump
--

-- Started on 2009-08-25 13:58:12

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 538 (class 2612 OID 16386)
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: -
--

CREATE PROCEDURAL LANGUAGE plpgsql;


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 1710 (class 1259 OID 288709)
-- Dependencies: 6
-- Name: addresses; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE addresses (
    address_id uuid NOT NULL,
    parent_id uuid,
    address_name character varying(64) NOT NULL,
    country_id uuid,
    city_id uuid,
    postal_code character varying(16),
    postal_address character varying(128),
    description text,
    user_group_id uuid
);


--
-- TOC entry 1711 (class 1259 OID 288715)
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
-- TOC entry 1712 (class 1259 OID 288721)
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
-- TOC entry 1713 (class 1259 OID 288727)
-- Dependencies: 2090 6
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
-- TOC entry 1714 (class 1259 OID 288734)
-- Dependencies: 2091 6
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
-- TOC entry 1715 (class 1259 OID 288741)
-- Dependencies: 2092 2093 6
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
-- TOC entry 1716 (class 1259 OID 288749)
-- Dependencies: 2094 2095 6
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
-- TOC entry 1717 (class 1259 OID 288757)
-- Dependencies: 6
-- Name: banknote_quantity; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE banknote_quantity (
    banknote_amt_id uuid NOT NULL,
    parent_id uuid,
    quantity numeric(19,4) NOT NULL,
    currencynominal_nominal_id uuid
);


--
-- TOC entry 1718 (class 1259 OID 288760)
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
-- TOC entry 1719 (class 1259 OID 288763)
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
    document_date timestamp with time zone,
    creation_time timestamp with time zone
);


--
-- TOC entry 1720 (class 1259 OID 288766)
-- Dependencies: 6
-- Name: business_partners; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE business_partners (
    partner_id uuid NOT NULL,
    parent_id uuid NOT NULL,
    default_currency_id integer NOT NULL
);


--
-- TOC entry 1721 (class 1259 OID 288769)
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
-- TOC entry 3050 (class 0 OID 0)
-- Dependencies: 1721
-- Name: COLUMN business_unit_addresses.address_type_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN business_unit_addresses.address_type_id IS 'Bill To, Ship To, Other';


--
-- TOC entry 1722 (class 1259 OID 288772)
-- Dependencies: 2096 2097 2098 6
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
-- TOC entry 1723 (class 1259 OID 288778)
-- Dependencies: 6
-- Name: cash_recon_pay_summary; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE cash_recon_pay_summary (
    cash_recon_pay_summary_id integer NOT NULL,
    amount numeric(19,4) NOT NULL,
    paymenttype integer NOT NULL,
    currency_id integer NOT NULL,
    cash_reconcile_id uuid
);


--
-- TOC entry 1724 (class 1259 OID 288781)
-- Dependencies: 6
-- Name: cash_reconcile; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE cash_reconcile (
    reconcile_id uuid NOT NULL,
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
-- TOC entry 1725 (class 1259 OID 288784)
-- Dependencies: 6
-- Name: cities; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE cities (
    city_id uuid NOT NULL,
    country_id uuid NOT NULL,
    city_name character varying(64) NOT NULL,
    postal_code character varying(8),
    city_code character varying(3),
    city_phone_code character varying(6),
    description text
);


--
-- TOC entry 1726 (class 1259 OID 288790)
-- Dependencies: 6
-- Name: classified_objects; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE classified_objects (
    classifier_id uuid NOT NULL,
    classified_object_id uuid NOT NULL,
    description text
);


--
-- TOC entry 1727 (class 1259 OID 288796)
-- Dependencies: 6
-- Name: classifier_applied_for_dot; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE classifier_applied_for_dot (
    classifier_id uuid NOT NULL,
    data_object_type_id integer NOT NULL
);


--
-- TOC entry 1728 (class 1259 OID 288799)
-- Dependencies: 2099 6
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
-- TOC entry 1729 (class 1259 OID 288806)
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
-- TOC entry 1730 (class 1259 OID 288812)
-- Dependencies: 6
-- Name: communication_contacts; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE communication_contacts (
    communication_contact_id uuid NOT NULL,
    address_id uuid NOT NULL,
    communication_type_id integer NOT NULL,
    communication_value character varying(64) NOT NULL,
    contact_person_id uuid
);


--
-- TOC entry 3051 (class 0 OID 0)
-- Dependencies: 1730
-- Name: COLUMN communication_contacts.communication_type_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN communication_contacts.communication_type_id IS 'Email (Work, Private), Phone (Work, Home, Fax), Mobile Phone (Work, Private), VoIP (SIP, H.323), Instant Communications (ICQ, Skype, Google Talk, MSN), Other';


--
-- TOC entry 1731 (class 1259 OID 288815)
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
-- TOC entry 1732 (class 1259 OID 288821)
-- Dependencies: 6
-- Name: complex_products; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE complex_products (
    product_id uuid NOT NULL,
    applied_schema_id uuid NOT NULL,
    sale_price numeric(19,4) NOT NULL
);


--
-- TOC entry 1733 (class 1259 OID 288824)
-- Dependencies: 6
-- Name: contact_persons; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE contact_persons (
    contact_person_id uuid NOT NULL,
    parent_id uuid NOT NULL,
    position_type_id uuid,
    contact_id uuid NOT NULL
);


--
-- TOC entry 1734 (class 1259 OID 288827)
-- Dependencies: 6
-- Name: countries; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE countries (
    country_id uuid NOT NULL,
    country_name character varying(64) NOT NULL,
    country_code_a2 character(2),
    country_code_a3 character(3),
    country_code_n3 character(3),
    country_phone_code character varying(6),
    currency_id integer,
    description text
);


--
-- TOC entry 1735 (class 1259 OID 288833)
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
-- TOC entry 1736 (class 1259 OID 288839)
-- Dependencies: 2100 6
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
-- TOC entry 1737 (class 1259 OID 288843)
-- Dependencies: 6
-- Name: currency_nominal; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE currency_nominal (
    nominal_id uuid NOT NULL,
    nominal numeric(19,4) NOT NULL,
    currency_id integer
);


--
-- TOC entry 1738 (class 1259 OID 288846)
-- Dependencies: 6
-- Name: customer_discount_items; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE customer_discount_items (
    customer_discount_item_id uuid NOT NULL,
    discount_percent numeric(7,6) NOT NULL,
    discriminator_id character(2)
);


--
-- TOC entry 1739 (class 1259 OID 288849)
-- Dependencies: 2101 6
-- Name: customer_discount_items_by_categories; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE customer_discount_items_by_categories (
    customer_discount_item_id uuid NOT NULL,
    customer_discount_id uuid NOT NULL,
    category_id uuid NOT NULL,
    include_heirs boolean DEFAULT false NOT NULL
);


--
-- TOC entry 1740 (class 1259 OID 288853)
-- Dependencies: 6
-- Name: customer_discount_items_by_products; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE customer_discount_items_by_products (
    customer_discount_item_id uuid NOT NULL,
    customer_discount_id uuid NOT NULL,
    product_id uuid NOT NULL
);


--
-- TOC entry 1741 (class 1259 OID 288856)
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
-- TOC entry 1742 (class 1259 OID 288859)
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
-- TOC entry 1743 (class 1259 OID 288862)
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
-- TOC entry 1744 (class 1259 OID 288868)
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
-- TOC entry 1745 (class 1259 OID 288874)
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
-- TOC entry 1746 (class 1259 OID 288877)
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
-- TOC entry 1747 (class 1259 OID 288880)
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
-- TOC entry 1748 (class 1259 OID 288883)
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
-- TOC entry 1749 (class 1259 OID 288889)
-- Dependencies: 2102 2103 2104 2105 2106 2107 6
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
-- TOC entry 1750 (class 1259 OID 288901)
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
-- TOC entry 1751 (class 1259 OID 288907)
-- Dependencies: 6
-- Name: delivery_certificate_assignments; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE delivery_certificate_assignments (
    delivery_certificate_id uuid NOT NULL,
    document_id uuid NOT NULL,
    document_number character varying(64)
);


--
-- TOC entry 1752 (class 1259 OID 288910)
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
-- TOC entry 1753 (class 1259 OID 288913)
-- Dependencies: 6
-- Name: delivery_certificate_serial_numbers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE delivery_certificate_serial_numbers (
    certificate_item_id uuid NOT NULL,
    serial_number character varying(32) NOT NULL
);


--
-- TOC entry 1754 (class 1259 OID 288916)
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
-- TOC entry 3052 (class 0 OID 0)
-- Dependencies: 1754
-- Name: COLUMN delivery_certificates.forwarder_branch_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN delivery_certificates.forwarder_branch_id IS 'As the Forwarder is an organization, we have to know abranch_id in order to choose a contact person.';


--
-- TOC entry 3053 (class 0 OID 0)
-- Dependencies: 1754
-- Name: COLUMN delivery_certificates.recipient_branch_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN delivery_certificates.recipient_branch_id IS 'In case the recipient is an organization we have to know a branch in order to shoose a contact person.';


--
-- TOC entry 1755 (class 1259 OID 288922)
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
-- TOC entry 1756 (class 1259 OID 288925)
-- Dependencies: 6
-- Name: enum_classes; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE enum_classes (
    enum_class_id integer NOT NULL,
    enum_class_name character varying(255) NOT NULL
);


--
-- TOC entry 1757 (class 1259 OID 288928)
-- Dependencies: 6
-- Name: expressions; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE expressions (
    organization_id uuid NOT NULL,
    expression_key character varying(255) NOT NULL,
    expression_value character varying(1024)
);


--
-- TOC entry 1758 (class 1259 OID 288934)
-- Dependencies: 6
-- Name: goods_receipt_dc_items; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE goods_receipt_dc_items (
    receipt_item_id uuid NOT NULL,
    delivery_certificate_item_id uuid NOT NULL
);


--
-- TOC entry 1759 (class 1259 OID 288937)
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
-- TOC entry 1760 (class 1259 OID 288940)
-- Dependencies: 6
-- Name: goods_receipt_pi_items; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE goods_receipt_pi_items (
    receipt_item_id uuid NOT NULL,
    invoice_item_id uuid NOT NULL
);


--
-- TOC entry 1761 (class 1259 OID 288943)
-- Dependencies: 6
-- Name: goods_receipts; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE goods_receipts (
    goods_receipt_id uuid NOT NULL,
    receipt_number bigint,
    receipt_date timestamp with time zone,
    receipt_status_id integer NOT NULL,
    consignee_id uuid NOT NULL,
    consignee_branch_id uuid NOT NULL,
    storekeeper_id uuid NOT NULL,
    supplier_id uuid NOT NULL,
    supplier_branch_id uuid,
    supplier_contact_id uuid,
    related_document_type_id integer NOT NULL,
    related_document_id uuid NOT NULL
);


--
-- TOC entry 1762 (class 1259 OID 288946)
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
-- TOC entry 3054 (class 0 OID 0)
-- Dependencies: 1762
-- Name: COLUMN job_titles.business_unit_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN job_titles.business_unit_id IS 'The parent';


--
-- TOC entry 1763 (class 1259 OID 288949)
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
-- TOC entry 1764 (class 1259 OID 288952)
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
-- TOC entry 1765 (class 1259 OID 288958)
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
-- TOC entry 1766 (class 1259 OID 288961)
-- Dependencies: 6
-- Name: organization_configurations; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE organization_configurations (
    organization_id uuid NOT NULL,
    default_currency_id integer NOT NULL
);


--
-- TOC entry 1767 (class 1259 OID 288964)
-- Dependencies: 2108 6
-- Name: organizations; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE organizations (
    organization_id uuid NOT NULL,
    description character varying(255),
    nickname character varying(255),
    organization_name character varying(120) NOT NULL,
    registration_date date,
    share_capital numeric(19,4),
    unique_identifier_code character varying(255),
    vat_number character varying(255),
    organization_type_id integer,
    registration_address_id uuid,
    registration_organization_id uuid,
    administration_address_id uuid,
    share_capital_currency_id integer,
    is_active boolean DEFAULT false NOT NULL
);


--
-- TOC entry 1768 (class 1259 OID 288971)
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
-- TOC entry 3055 (class 0 OID 0)
-- Dependencies: 1768
-- Name: COLUMN passports.passport_type_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN passports.passport_type_id IS 'Passport, Identity Card, Driving License';


--
-- TOC entry 1769 (class 1259 OID 288974)
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
-- TOC entry 1770 (class 1259 OID 288980)
-- Dependencies: 6
-- Name: persons; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE persons (
    partner_id uuid NOT NULL,
    birth_date date,
    description text,
    extra_name character varying(24),
    first_name character varying(24) NOT NULL,
    last_name character varying(24) NOT NULL,
    personal_unique_id character varying(16),
    second_name character varying(24),
    gender_id integer,
    birth_place_city_id uuid,
    birth_place_country_id uuid
);


--
-- TOC entry 1771 (class 1259 OID 288986)
-- Dependencies: 2109 6
-- Name: position_types; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE position_types (
    position_type_id uuid NOT NULL,
    parent_id uuid,
    owner_type character(1) NOT NULL,
    position_type_name character varying(32) NOT NULL,
    description text,
    parent_position_type_id uuid,
    is_internal boolean DEFAULT false NOT NULL,
    user_group_id uuid
);


--
-- TOC entry 3056 (class 0 OID 0)
-- Dependencies: 1771
-- Name: COLUMN position_types.owner_type; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN position_types.owner_type IS 'P - Person, O - Organization';


--
-- TOC entry 1772 (class 1259 OID 288993)
-- Dependencies: 6
-- Name: pricelist_items; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE pricelist_items (
    item_id uuid NOT NULL,
    parent_id uuid,
    apply_client_discount boolean,
    discount_percent numeric(19,4),
    min_quantity numeric(19,4),
    product_id uuid
);


--
-- TOC entry 1773 (class 1259 OID 288996)
-- Dependencies: 6
-- Name: pricelists; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE pricelists (
    pricelist_id uuid NOT NULL,
    active boolean,
    active_from timestamp with time zone,
    active_to timestamp with time zone,
    default_discount numeric(19,4),
    forperiod boolean,
    min_turnover numeric(19,4),
    months integer,
    name character varying(255) NOT NULL,
    parent_id uuid,
    currency_id integer,
    generalpricelist boolean
);


--
-- TOC entry 1774 (class 1259 OID 288999)
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
-- TOC entry 1775 (class 1259 OID 289002)
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
-- TOC entry 1776 (class 1259 OID 289005)
-- Dependencies: 2110 6
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
-- TOC entry 3057 (class 0 OID 0)
-- Dependencies: 1776
-- Name: COLUMN privileges.security_role_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN privileges.security_role_id IS 'The parent of privilege';


--
-- TOC entry 1777 (class 1259 OID 289009)
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
-- TOC entry 1778 (class 1259 OID 289015)
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
-- TOC entry 1779 (class 1259 OID 289018)
-- Dependencies: 2111 2112 2113 2114 6
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
-- TOC entry 1780 (class 1259 OID 289028)
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
-- TOC entry 1781 (class 1259 OID 289031)
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
-- TOC entry 1782 (class 1259 OID 289034)
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
-- TOC entry 1783 (class 1259 OID 289037)
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
-- TOC entry 1784 (class 1259 OID 289040)
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
-- TOC entry 1785 (class 1259 OID 289046)
-- Dependencies: 6
-- Name: real_products; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE real_products (
    product_id uuid NOT NULL,
    simple_product_id uuid NOT NULL
);


--
-- TOC entry 1786 (class 1259 OID 289049)
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
-- TOC entry 1787 (class 1259 OID 289052)
-- Dependencies: 6
-- Name: receipt_certificate_serial_numbers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE receipt_certificate_serial_numbers (
    certificate_item_id uuid NOT NULL,
    serial_number character varying(32) NOT NULL
);


--
-- TOC entry 1788 (class 1259 OID 289055)
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
-- TOC entry 1789 (class 1259 OID 289058)
-- Dependencies: 2115 6
-- Name: registration_codes; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE registration_codes (
    registration_code uuid NOT NULL,
    email_address character varying(64) NOT NULL,
    registration_time timestamp with time zone DEFAULT now() NOT NULL
);


--
-- TOC entry 1790 (class 1259 OID 289061)
-- Dependencies: 6
-- Name: resource_bundle; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE resource_bundle (
    resource_id integer NOT NULL,
    enum_class_id integer NOT NULL,
    enum_name character varying(64) NOT NULL
);


--
-- TOC entry 1791 (class 1259 OID 289064)
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
-- TOC entry 1792 (class 1259 OID 289067)
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
-- TOC entry 1793 (class 1259 OID 289073)
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
-- TOC entry 1794 (class 1259 OID 289079)
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
-- TOC entry 3058 (class 0 OID 0)
-- Dependencies: 1794
-- Name: COLUMN security_roles.organization_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN security_roles.organization_id IS 'The parent of this entity';


--
-- TOC entry 1795 (class 1259 OID 289082)
-- Dependencies: 2116 6
-- Name: sequence_identifiers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE sequence_identifiers (
    seq_id_key bigint NOT NULL,
    seq_id_name character varying(64) NOT NULL,
    seq_id_value numeric(38,0) DEFAULT 0 NOT NULL
);


--
-- TOC entry 1796 (class 1259 OID 289086)
-- Dependencies: 2118 2119 2120 2121 2122 2123 6
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
-- TOC entry 1797 (class 1259 OID 289098)
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
-- TOC entry 1798 (class 1259 OID 289101)
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
-- TOC entry 1799 (class 1259 OID 289104)
-- Dependencies: 6
-- Name: user_group_members; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE user_group_members (
    user_group_member_id uuid NOT NULL,
    user_group_id uuid NOT NULL,
    user_organization_id uuid NOT NULL
);


--
-- TOC entry 1800 (class 1259 OID 289107)
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
-- TOC entry 1801 (class 1259 OID 289113)
-- Dependencies: 6
-- Name: user_organizations; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE user_organizations (
    user_id uuid NOT NULL,
    organization_id uuid NOT NULL,
    branch_id uuid,
    is_user_active boolean NOT NULL,
    business_unit_id uuid,
    job_title_id uuid,
    manager_id uuid,
    email_address character varying(64),
    user_organization_id uuid NOT NULL
);


--
-- TOC entry 1802 (class 1259 OID 289116)
-- Dependencies: 2124 2125 2126 2127 2128 6
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
-- TOC entry 3059 (class 0 OID 0)
-- Dependencies: 1802
-- Name: COLUMN user_rights.owner_type_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN user_rights.owner_type_id IS 'U=User, G=User Group';


--
-- TOC entry 1803 (class 1259 OID 289124)
-- Dependencies: 2129 2130 2131 2132 2133 2134 6
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
-- TOC entry 1804 (class 1259 OID 289133)
-- Dependencies: 6
-- Name: user_security_roles; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE user_security_roles (
    user_security_role_id uuid NOT NULL,
    user_organization_id uuid NOT NULL,
    security_role_id uuid NOT NULL
);


--
-- TOC entry 1805 (class 1259 OID 289136)
-- Dependencies: 2135 2136 6
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
    description text,
    next_action_after_login character varying(1024),
    person_id uuid,
    creation_time timestamp with time zone DEFAULT now() NOT NULL
);


--
-- TOC entry 1806 (class 1259 OID 289144)
-- Dependencies: 6
-- Name: uuid_test; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE uuid_test (
    test_id uuid NOT NULL,
    test_name character varying(100)
);


--
-- TOC entry 1807 (class 1259 OID 289147)
-- Dependencies: 6
-- Name: virtual_products; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE virtual_products (
    product_id uuid NOT NULL,
    product_type character(2),
    parent_id uuid
);


--
-- TOC entry 1808 (class 1259 OID 289150)
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
-- TOC entry 1809 (class 1259 OID 289153)
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
-- TOC entry 1810 (class 1259 OID 289159)
-- Dependencies: 6
-- Name: cash_recon_pay_summary_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE cash_recon_pay_summary_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 3060 (class 0 OID 0)
-- Dependencies: 1810
-- Name: cash_recon_pay_summary_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('cash_recon_pay_summary_seq', 1, true);


--
-- TOC entry 1811 (class 1259 OID 289161)
-- Dependencies: 6
-- Name: countries_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE countries_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 3061 (class 0 OID 0)
-- Dependencies: 1811
-- Name: countries_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('countries_seq', 1, true);


--
-- TOC entry 1812 (class 1259 OID 289163)
-- Dependencies: 6
-- Name: customer_payment_match_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE customer_payment_match_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 3062 (class 0 OID 0)
-- Dependencies: 1812
-- Name: customer_payment_match_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('customer_payment_match_seq', 1, true);


--
-- TOC entry 1813 (class 1259 OID 289165)
-- Dependencies: 6
-- Name: data_object_type_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE data_object_type_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 3063 (class 0 OID 0)
-- Dependencies: 1813
-- Name: data_object_type_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('data_object_type_seq', 1, true);


--
-- TOC entry 1814 (class 1259 OID 289167)
-- Dependencies: 6
-- Name: data_objects_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE data_objects_seq
    INCREMENT BY 1
    MAXVALUE 999999999999999999
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 3064 (class 0 OID 0)
-- Dependencies: 1814
-- Name: data_objects_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('data_objects_seq', 1, true);


--
-- TOC entry 1815 (class 1259 OID 289169)
-- Dependencies: 6
-- Name: enum_classes_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE enum_classes_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 3065 (class 0 OID 0)
-- Dependencies: 1815
-- Name: enum_classes_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('enum_classes_seq', 1, true);


--
-- TOC entry 1816 (class 1259 OID 289171)
-- Dependencies: 6
-- Name: invoice_item_link_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE invoice_item_link_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 3066 (class 0 OID 0)
-- Dependencies: 1816
-- Name: invoice_item_link_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('invoice_item_link_seq', 1, true);


--
-- TOC entry 1817 (class 1259 OID 289173)
-- Dependencies: 6
-- Name: order_item_match_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE order_item_match_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 3067 (class 0 OID 0)
-- Dependencies: 1817
-- Name: order_item_match_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('order_item_match_seq', 1, true);


--
-- TOC entry 1818 (class 1259 OID 289175)
-- Dependencies: 6
-- Name: pattern_mask_formats_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE pattern_mask_formats_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 3068 (class 0 OID 0)
-- Dependencies: 1818
-- Name: pattern_mask_formats_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('pattern_mask_formats_seq', 1, true);


--
-- TOC entry 1819 (class 1259 OID 289177)
-- Dependencies: 6
-- Name: resource_bundle_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE resource_bundle_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 3069 (class 0 OID 0)
-- Dependencies: 1819
-- Name: resource_bundle_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('resource_bundle_seq', 1, true);


--
-- TOC entry 1820 (class 1259 OID 289179)
-- Dependencies: 1795 6
-- Name: sequence_identifiers_seq_id_key_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE sequence_identifiers_seq_id_key_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 3070 (class 0 OID 0)
-- Dependencies: 1820
-- Name: sequence_identifiers_seq_id_key_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE sequence_identifiers_seq_id_key_seq OWNED BY sequence_identifiers.seq_id_key;


--
-- TOC entry 3071 (class 0 OID 0)
-- Dependencies: 1820
-- Name: sequence_identifiers_seq_id_key_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('sequence_identifiers_seq_id_key_seq', 1, false);


--
-- TOC entry 1821 (class 1259 OID 289181)
-- Dependencies: 6
-- Name: user_rights_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE user_rights_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 3072 (class 0 OID 0)
-- Dependencies: 1821
-- Name: user_rights_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('user_rights_seq', 1, true);


--
-- TOC entry 1822 (class 1259 OID 289183)
-- Dependencies: 6
-- Name: warehouse_product_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE warehouse_product_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 3073 (class 0 OID 0)
-- Dependencies: 1822
-- Name: warehouse_product_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('warehouse_product_seq', 1, true);


--
-- TOC entry 1823 (class 1259 OID 289185)
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
-- TOC entry 3074 (class 0 OID 0)
-- Dependencies: 1823
-- Name: xyz_id_sequence; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('xyz_id_sequence', 1, false);


--
-- TOC entry 2117 (class 2604 OID 289187)
-- Dependencies: 1820 1795
-- Name: seq_id_key; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE sequence_identifiers ALTER COLUMN seq_id_key SET DEFAULT nextval('sequence_identifiers_seq_id_key_seq'::regclass);


--
-- TOC entry 2162 (class 2606 OID 289189)
-- Dependencies: 1717 1717
-- Name: banknote_quantity_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY banknote_quantity
    ADD CONSTRAINT banknote_quantity_pkey PRIMARY KEY (banknote_amt_id);


--
-- TOC entry 2170 (class 2606 OID 289191)
-- Dependencies: 1720 1720
-- Name: business_partners_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY business_partners
    ADD CONSTRAINT business_partners_pkey PRIMARY KEY (partner_id);


--
-- TOC entry 2181 (class 2606 OID 289193)
-- Dependencies: 1723 1723
-- Name: cash_recon_pay_summary_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY cash_recon_pay_summary
    ADD CONSTRAINT cash_recon_pay_summary_pkey PRIMARY KEY (cash_recon_pay_summary_id);


--
-- TOC entry 2183 (class 2606 OID 289195)
-- Dependencies: 1724 1724
-- Name: cash_reconcile_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY cash_reconcile
    ADD CONSTRAINT cash_reconcile_pkey PRIMARY KEY (reconcile_id);


--
-- TOC entry 2222 (class 2606 OID 289197)
-- Dependencies: 1737 1737
-- Name: currency_nominal_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY currency_nominal
    ADD CONSTRAINT currency_nominal_pkey PRIMARY KEY (nominal_id);


--
-- TOC entry 2224 (class 2606 OID 289199)
-- Dependencies: 1738 1738
-- Name: customer_discount_items_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customer_discount_items
    ADD CONSTRAINT customer_discount_items_pkey PRIMARY KEY (customer_discount_item_id);


--
-- TOC entry 2238 (class 2606 OID 289201)
-- Dependencies: 1742 1742
-- Name: customer_payment_match_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customer_payment_match
    ADD CONSTRAINT customer_payment_match_pkey PRIMARY KEY (customer_payment_match_id);


--
-- TOC entry 2240 (class 2606 OID 289203)
-- Dependencies: 1743 1743
-- Name: customer_payments_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customer_payments
    ADD CONSTRAINT customer_payments_pkey PRIMARY KEY (payment_id);


--
-- TOC entry 2375 (class 2606 OID 289205)
-- Dependencies: 1791 1791
-- Name: invoice_item_link_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY sales_invoice_item_link
    ADD CONSTRAINT invoice_item_link_pkey PRIMARY KEY (id);


--
-- TOC entry 2377 (class 2606 OID 289207)
-- Dependencies: 1792 1792
-- Name: invoice_items_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY sales_invoice_items
    ADD CONSTRAINT invoice_items_pkey PRIMARY KEY (invoice_item_id);


--
-- TOC entry 2379 (class 2606 OID 289209)
-- Dependencies: 1793 1793
-- Name: invoices_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT invoices_pkey PRIMARY KEY (invoice_id);


--
-- TOC entry 2298 (class 2606 OID 289211)
-- Dependencies: 1763 1763
-- Name: order_confirmation_items_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY order_confirmation_items
    ADD CONSTRAINT order_confirmation_items_pkey PRIMARY KEY (confirmation_item_id);


--
-- TOC entry 2300 (class 2606 OID 289213)
-- Dependencies: 1764 1764
-- Name: order_confirmations_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT order_confirmations_pkey PRIMARY KEY (order_confirmation_id);


--
-- TOC entry 2302 (class 2606 OID 289215)
-- Dependencies: 1765 1765
-- Name: order_item_match_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY order_item_match
    ADD CONSTRAINT order_item_match_pkey PRIMARY KEY (id);


--
-- TOC entry 2306 (class 2606 OID 289217)
-- Dependencies: 1767 1767
-- Name: organizations_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT organizations_pkey PRIMARY KEY (organization_id);


--
-- TOC entry 2312 (class 2606 OID 289219)
-- Dependencies: 1769 1769
-- Name: pattern_mask_formats_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pattern_mask_formats
    ADD CONSTRAINT pattern_mask_formats_pkey PRIMARY KEY (pattern_mask_format_id);


--
-- TOC entry 2314 (class 2606 OID 289221)
-- Dependencies: 1770 1770
-- Name: persons_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT persons_pkey PRIMARY KEY (partner_id);


--
-- TOC entry 2138 (class 2606 OID 289223)
-- Dependencies: 1710 1710
-- Name: pk_addresses; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT pk_addresses PRIMARY KEY (address_id);


--
-- TOC entry 2140 (class 2606 OID 289225)
-- Dependencies: 1711 1711
-- Name: pk_assembling_categories; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_categories
    ADD CONSTRAINT pk_assembling_categories PRIMARY KEY (assembling_category_id);


--
-- TOC entry 2146 (class 2606 OID 289227)
-- Dependencies: 1712 1712
-- Name: pk_assembling_messages; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_messages
    ADD CONSTRAINT pk_assembling_messages PRIMARY KEY (message_id);


--
-- TOC entry 2150 (class 2606 OID 289229)
-- Dependencies: 1713 1713
-- Name: pk_assembling_schema_item_values; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schema_item_values
    ADD CONSTRAINT pk_assembling_schema_item_values PRIMARY KEY (item_value_id);


--
-- TOC entry 2152 (class 2606 OID 289231)
-- Dependencies: 1714 1714
-- Name: pk_assembling_schema_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT pk_assembling_schema_items PRIMARY KEY (item_id);


--
-- TOC entry 2154 (class 2606 OID 289233)
-- Dependencies: 1715 1715
-- Name: pk_assembling_schemas; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT pk_assembling_schemas PRIMARY KEY (product_id);


--
-- TOC entry 2160 (class 2606 OID 289235)
-- Dependencies: 1716 1716
-- Name: pk_bank_details; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT pk_bank_details PRIMARY KEY (bank_detail_id);


--
-- TOC entry 2164 (class 2606 OID 289237)
-- Dependencies: 1718 1718 1718 1718
-- Name: pk_business_document_status_log; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY business_document_status_log
    ADD CONSTRAINT pk_business_document_status_log PRIMARY KEY (document_id, document_status_id, action_time);


--
-- TOC entry 2172 (class 2606 OID 289239)
-- Dependencies: 1721 1721
-- Name: pk_business_unit_addresses; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY business_unit_addresses
    ADD CONSTRAINT pk_business_unit_addresses PRIMARY KEY (business_unit_address_id);


--
-- TOC entry 2177 (class 2606 OID 289241)
-- Dependencies: 1722 1722
-- Name: pk_business_units; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY business_units
    ADD CONSTRAINT pk_business_units PRIMARY KEY (business_unit_id);


--
-- TOC entry 2185 (class 2606 OID 289243)
-- Dependencies: 1725 1725
-- Name: pk_cities; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY cities
    ADD CONSTRAINT pk_cities PRIMARY KEY (city_id);


--
-- TOC entry 2189 (class 2606 OID 289245)
-- Dependencies: 1726 1726 1726
-- Name: pk_classified_objects; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classified_objects
    ADD CONSTRAINT pk_classified_objects PRIMARY KEY (classifier_id, classified_object_id);


--
-- TOC entry 2191 (class 2606 OID 289247)
-- Dependencies: 1727 1727 1727
-- Name: pk_classifier_applied_for_dot; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifier_applied_for_dot
    ADD CONSTRAINT pk_classifier_applied_for_dot PRIMARY KEY (classifier_id, data_object_type_id);


--
-- TOC entry 2193 (class 2606 OID 289249)
-- Dependencies: 1728 1728
-- Name: pk_classifier_groups; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifier_groups
    ADD CONSTRAINT pk_classifier_groups PRIMARY KEY (classifier_group_id);


--
-- TOC entry 2195 (class 2606 OID 289251)
-- Dependencies: 1729 1729
-- Name: pk_classifiers; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT pk_classifiers PRIMARY KEY (classifier_id);


--
-- TOC entry 2200 (class 2606 OID 289253)
-- Dependencies: 1730 1730
-- Name: pk_communication_contacts; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT pk_communication_contacts PRIMARY KEY (communication_contact_id);


--
-- TOC entry 2204 (class 2606 OID 289255)
-- Dependencies: 1731 1731
-- Name: pk_complex_product_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT pk_complex_product_items PRIMARY KEY (complex_product_item_id);


--
-- TOC entry 2206 (class 2606 OID 289257)
-- Dependencies: 1732 1732
-- Name: pk_complex_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY complex_products
    ADD CONSTRAINT pk_complex_products PRIMARY KEY (product_id);


--
-- TOC entry 2208 (class 2606 OID 289259)
-- Dependencies: 1733 1733
-- Name: pk_contact_persons; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT pk_contact_persons PRIMARY KEY (contact_person_id);


--
-- TOC entry 2212 (class 2606 OID 289261)
-- Dependencies: 1734 1734
-- Name: pk_countries; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY countries
    ADD CONSTRAINT pk_countries PRIMARY KEY (country_id);


--
-- TOC entry 2216 (class 2606 OID 289263)
-- Dependencies: 1735 1735
-- Name: pk_currencies; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY currencies
    ADD CONSTRAINT pk_currencies PRIMARY KEY (currency_id);


--
-- TOC entry 2220 (class 2606 OID 289265)
-- Dependencies: 1736 1736 1736 1736 1736
-- Name: pk_currency_exchange_rates; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY currency_exchange_rates
    ADD CONSTRAINT pk_currency_exchange_rates PRIMARY KEY (organization_id, valid_from, from_currency_id, to_currency_id);


--
-- TOC entry 2226 (class 2606 OID 289267)
-- Dependencies: 1739 1739
-- Name: pk_customer_discount_items_by_categories; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customer_discount_items_by_categories
    ADD CONSTRAINT pk_customer_discount_items_by_categories PRIMARY KEY (customer_discount_item_id);


--
-- TOC entry 2230 (class 2606 OID 289269)
-- Dependencies: 1740 1740
-- Name: pk_customer_discount_items_by_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customer_discount_items_by_products
    ADD CONSTRAINT pk_customer_discount_items_by_products PRIMARY KEY (customer_discount_item_id);


--
-- TOC entry 2234 (class 2606 OID 289271)
-- Dependencies: 1741 1741
-- Name: pk_customer_discounts; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customer_discounts
    ADD CONSTRAINT pk_customer_discounts PRIMARY KEY (customer_discount_id);


--
-- TOC entry 2242 (class 2606 OID 289273)
-- Dependencies: 1744 1744
-- Name: pk_data_object_details; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_details
    ADD CONSTRAINT pk_data_object_details PRIMARY KEY (data_object_id);


--
-- TOC entry 2246 (class 2606 OID 289275)
-- Dependencies: 1745 1745
-- Name: pk_data_object_links; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_links
    ADD CONSTRAINT pk_data_object_links PRIMARY KEY (data_object_link_id);


--
-- TOC entry 2250 (class 2606 OID 289277)
-- Dependencies: 1746 1746 1746 1746 1746
-- Name: pk_data_object_permissions; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_permissions
    ADD CONSTRAINT pk_data_object_permissions PRIMARY KEY (organization_id, data_object_id, user_right_type_id, permission_id);


--
-- TOC entry 2252 (class 2606 OID 289279)
-- Dependencies: 1747 1747 1747 1747 1747
-- Name: pk_data_object_type_permissions; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_type_permissions
    ADD CONSTRAINT pk_data_object_type_permissions PRIMARY KEY (organization_id, data_object_type_id, user_right_type_id, permission_id);


--
-- TOC entry 2254 (class 2606 OID 289281)
-- Dependencies: 1748 1748
-- Name: pk_data_object_types; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_types
    ADD CONSTRAINT pk_data_object_types PRIMARY KEY (data_object_type_id);


--
-- TOC entry 2258 (class 2606 OID 289283)
-- Dependencies: 1749 1749
-- Name: pk_data_objects; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT pk_data_objects PRIMARY KEY (data_object_id);


--
-- TOC entry 2260 (class 2606 OID 289285)
-- Dependencies: 1750 1750 1750 1750
-- Name: pk_db_properties; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY db_properties
    ADD CONSTRAINT pk_db_properties PRIMARY KEY (access_level, related_object_id, property_key);


--
-- TOC entry 2262 (class 2606 OID 289287)
-- Dependencies: 1751 1751
-- Name: pk_delivery_certificate_assignments; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY delivery_certificate_assignments
    ADD CONSTRAINT pk_delivery_certificate_assignments PRIMARY KEY (delivery_certificate_id);


--
-- TOC entry 2264 (class 2606 OID 289289)
-- Dependencies: 1752 1752
-- Name: pk_delivery_certificate_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT pk_delivery_certificate_items PRIMARY KEY (certificate_item_id);


--
-- TOC entry 2266 (class 2606 OID 289291)
-- Dependencies: 1753 1753 1753
-- Name: pk_delivery_certificate_serial_numbers; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY delivery_certificate_serial_numbers
    ADD CONSTRAINT pk_delivery_certificate_serial_numbers PRIMARY KEY (certificate_item_id, serial_number);


--
-- TOC entry 2274 (class 2606 OID 289293)
-- Dependencies: 1754 1754
-- Name: pk_delivery_certificates; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT pk_delivery_certificates PRIMARY KEY (delivery_certificate_id);


--
-- TOC entry 2166 (class 2606 OID 289295)
-- Dependencies: 1719 1719
-- Name: pk_document_entities; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY business_documents
    ADD CONSTRAINT pk_document_entities PRIMARY KEY (document_id);


--
-- TOC entry 2278 (class 2606 OID 289297)
-- Dependencies: 1755 1755 1755
-- Name: pk_entity_sequences; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY entity_sequences
    ADD CONSTRAINT pk_entity_sequences PRIMARY KEY (entity_id, data_object_type_id);


--
-- TOC entry 2280 (class 2606 OID 289299)
-- Dependencies: 1756 1756
-- Name: pk_enum_classes; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY enum_classes
    ADD CONSTRAINT pk_enum_classes PRIMARY KEY (enum_class_id);


--
-- TOC entry 2284 (class 2606 OID 289301)
-- Dependencies: 1757 1757 1757
-- Name: pk_expressions; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY expressions
    ADD CONSTRAINT pk_expressions PRIMARY KEY (organization_id, expression_key);


--
-- TOC entry 2286 (class 2606 OID 289303)
-- Dependencies: 1758 1758
-- Name: pk_goods_receipt_dc_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY goods_receipt_dc_items
    ADD CONSTRAINT pk_goods_receipt_dc_items PRIMARY KEY (receipt_item_id);


--
-- TOC entry 2288 (class 2606 OID 289305)
-- Dependencies: 1759 1759
-- Name: pk_goods_receipt_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY goods_receipt_items
    ADD CONSTRAINT pk_goods_receipt_items PRIMARY KEY (receipt_item_id);


--
-- TOC entry 2290 (class 2606 OID 289307)
-- Dependencies: 1760 1760
-- Name: pk_goods_receipt_pi_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY goods_receipt_pi_items
    ADD CONSTRAINT pk_goods_receipt_pi_items PRIMARY KEY (receipt_item_id);


--
-- TOC entry 2292 (class 2606 OID 289309)
-- Dependencies: 1761 1761
-- Name: pk_goods_receipts; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY goods_receipts
    ADD CONSTRAINT pk_goods_receipts PRIMARY KEY (goods_receipt_id);


--
-- TOC entry 2294 (class 2606 OID 289311)
-- Dependencies: 1762 1762
-- Name: pk_job_titles; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY job_titles
    ADD CONSTRAINT pk_job_titles PRIMARY KEY (job_title_id);


--
-- TOC entry 2304 (class 2606 OID 289313)
-- Dependencies: 1766 1766
-- Name: pk_organization_configurations; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY organization_configurations
    ADD CONSTRAINT pk_organization_configurations PRIMARY KEY (organization_id);


--
-- TOC entry 2308 (class 2606 OID 289315)
-- Dependencies: 1768 1768
-- Name: pk_passports; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT pk_passports PRIMARY KEY (passport_id);


--
-- TOC entry 2316 (class 2606 OID 289317)
-- Dependencies: 1771 1771
-- Name: pk_position_types; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY position_types
    ADD CONSTRAINT pk_position_types PRIMARY KEY (position_type_id);


--
-- TOC entry 2322 (class 2606 OID 289319)
-- Dependencies: 1774 1774
-- Name: pk_privilege_categories; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY privilege_categories
    ADD CONSTRAINT pk_privilege_categories PRIMARY KEY (privilege_category_id);


--
-- TOC entry 2326 (class 2606 OID 289321)
-- Dependencies: 1775 1775
-- Name: pk_privilege_roles; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY privilege_roles
    ADD CONSTRAINT pk_privilege_roles PRIMARY KEY (privilege_role_id);


--
-- TOC entry 2330 (class 2606 OID 289323)
-- Dependencies: 1776 1776
-- Name: pk_privileges; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY privileges
    ADD CONSTRAINT pk_privileges PRIMARY KEY (privilege_id);


--
-- TOC entry 2334 (class 2606 OID 289325)
-- Dependencies: 1777 1777
-- Name: pk_product_categories; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT pk_product_categories PRIMARY KEY (product_category_id);


--
-- TOC entry 2342 (class 2606 OID 289327)
-- Dependencies: 1779 1779 1779
-- Name: pk_product_suppliers; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT pk_product_suppliers PRIMARY KEY (product_id, supplier_id);


--
-- TOC entry 2387 (class 2606 OID 289329)
-- Dependencies: 1796 1796
-- Name: pk_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT pk_products PRIMARY KEY (product_id);


--
-- TOC entry 2344 (class 2606 OID 289331)
-- Dependencies: 1780 1780
-- Name: pk_products1; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY products
    ADD CONSTRAINT pk_products1 PRIMARY KEY (product_id);


--
-- TOC entry 2350 (class 2606 OID 289333)
-- Dependencies: 1781 1781
-- Name: pk_purchase_invoice_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT pk_purchase_invoice_items PRIMARY KEY (invoice_item_id);


--
-- TOC entry 2352 (class 2606 OID 289335)
-- Dependencies: 1782 1782
-- Name: pk_purchase_invoices; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY purchase_invoices
    ADD CONSTRAINT pk_purchase_invoices PRIMARY KEY (invoice_id);


--
-- TOC entry 2354 (class 2606 OID 289337)
-- Dependencies: 1783 1783
-- Name: pk_purchase_order_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT pk_purchase_order_items PRIMARY KEY (order_item_id);


--
-- TOC entry 2358 (class 2606 OID 289339)
-- Dependencies: 1785 1785
-- Name: pk_real_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY real_products
    ADD CONSTRAINT pk_real_products PRIMARY KEY (product_id);


--
-- TOC entry 2362 (class 2606 OID 289341)
-- Dependencies: 1786 1786
-- Name: pk_receipt_certificate_items; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT pk_receipt_certificate_items PRIMARY KEY (certificate_item_id);


--
-- TOC entry 2364 (class 2606 OID 289343)
-- Dependencies: 1787 1787 1787
-- Name: pk_receipt_certificate_serial_numbers; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY receipt_certificate_serial_numbers
    ADD CONSTRAINT pk_receipt_certificate_serial_numbers PRIMARY KEY (certificate_item_id, serial_number);


--
-- TOC entry 2366 (class 2606 OID 289345)
-- Dependencies: 1788 1788
-- Name: pk_receipt_certificates; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT pk_receipt_certificates PRIMARY KEY (receipt_certificate_id);


--
-- TOC entry 2370 (class 2606 OID 298380)
-- Dependencies: 1789 1789
-- Name: pk_registration_codes; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY registration_codes
    ADD CONSTRAINT pk_registration_codes PRIMARY KEY (registration_code);


--
-- TOC entry 2373 (class 2606 OID 289347)
-- Dependencies: 1790 1790
-- Name: pk_resource_bundle; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY resource_bundle
    ADD CONSTRAINT pk_resource_bundle PRIMARY KEY (resource_id);


--
-- TOC entry 2381 (class 2606 OID 289349)
-- Dependencies: 1794 1794
-- Name: pk_security_roles; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY security_roles
    ADD CONSTRAINT pk_security_roles PRIMARY KEY (security_role_id);


--
-- TOC entry 2389 (class 2606 OID 289351)
-- Dependencies: 1797 1797
-- Name: pk_team_members; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY team_members
    ADD CONSTRAINT pk_team_members PRIMARY KEY (team_member_id);


--
-- TOC entry 2393 (class 2606 OID 289353)
-- Dependencies: 1798 1798
-- Name: pk_teams; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY teams
    ADD CONSTRAINT pk_teams PRIMARY KEY (team_id);


--
-- TOC entry 2398 (class 2606 OID 289355)
-- Dependencies: 1799 1799
-- Name: pk_user_group_members; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY user_group_members
    ADD CONSTRAINT pk_user_group_members PRIMARY KEY (user_group_member_id);


--
-- TOC entry 2405 (class 2606 OID 289357)
-- Dependencies: 1801 1801
-- Name: pk_user_organizations; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY user_organizations
    ADD CONSTRAINT pk_user_organizations PRIMARY KEY (user_organization_id);


--
-- TOC entry 2409 (class 2606 OID 289359)
-- Dependencies: 1802 1802
-- Name: pk_user_rights; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY user_rights
    ADD CONSTRAINT pk_user_rights PRIMARY KEY (user_right_id);


--
-- TOC entry 2416 (class 2606 OID 289361)
-- Dependencies: 1804 1804
-- Name: pk_user_security_roles; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY user_security_roles
    ADD CONSTRAINT pk_user_security_roles PRIMARY KEY (user_security_role_id);


--
-- TOC entry 2420 (class 2606 OID 289363)
-- Dependencies: 1805 1805
-- Name: pk_users; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT pk_users PRIMARY KEY (user_id);


--
-- TOC entry 2424 (class 2606 OID 289365)
-- Dependencies: 1806 1806
-- Name: pk_uuid_test; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY uuid_test
    ADD CONSTRAINT pk_uuid_test PRIMARY KEY (test_id);


--
-- TOC entry 2426 (class 2606 OID 289367)
-- Dependencies: 1807 1807
-- Name: pk_virtual_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY virtual_products
    ADD CONSTRAINT pk_virtual_products PRIMARY KEY (product_id);


--
-- TOC entry 2430 (class 2606 OID 289369)
-- Dependencies: 1809 1809
-- Name: pk_warehouses; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY warehouses
    ADD CONSTRAINT pk_warehouses PRIMARY KEY (warehouse_id);


--
-- TOC entry 2318 (class 2606 OID 289371)
-- Dependencies: 1772 1772
-- Name: pricelist_items_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pricelist_items
    ADD CONSTRAINT pricelist_items_pkey PRIMARY KEY (item_id);


--
-- TOC entry 2320 (class 2606 OID 289373)
-- Dependencies: 1773 1773
-- Name: pricelists_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pricelists
    ADD CONSTRAINT pricelists_pkey PRIMARY KEY (pricelist_id);


--
-- TOC entry 2338 (class 2606 OID 289375)
-- Dependencies: 1778 1778
-- Name: product_pricing_value_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY product_percent_values
    ADD CONSTRAINT product_pricing_value_pkey PRIMARY KEY (percent_value_id);


--
-- TOC entry 2356 (class 2606 OID 289377)
-- Dependencies: 1784 1784
-- Name: purchase_orders_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT purchase_orders_pkey PRIMARY KEY (purchase_order_id);


--
-- TOC entry 2142 (class 2606 OID 289381)
-- Dependencies: 1711 1711 1711 1711
-- Name: uk_assembling_categories_by_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_categories
    ADD CONSTRAINT uk_assembling_categories_by_code UNIQUE (parent_id, parent_category_id, category_code);


--
-- TOC entry 2144 (class 2606 OID 289383)
-- Dependencies: 1711 1711 1711 1711
-- Name: uk_assembling_categories_by_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_categories
    ADD CONSTRAINT uk_assembling_categories_by_name UNIQUE (parent_id, parent_category_id, category_name);


--
-- TOC entry 2148 (class 2606 OID 289385)
-- Dependencies: 1712 1712 1712
-- Name: uk_assembling_messages_org_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_messages
    ADD CONSTRAINT uk_assembling_messages_org_code UNIQUE (organization_id, message_code);


--
-- TOC entry 2156 (class 2606 OID 289387)
-- Dependencies: 1715 1715 1715
-- Name: uk_assembling_schemas_by_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT uk_assembling_schemas_by_code UNIQUE (category_id, schema_code);


--
-- TOC entry 2158 (class 2606 OID 289389)
-- Dependencies: 1715 1715 1715
-- Name: uk_assembling_schemas_by_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT uk_assembling_schemas_by_name UNIQUE (category_id, schema_name);


--
-- TOC entry 2168 (class 2606 OID 289391)
-- Dependencies: 1719 1719 1719 1719
-- Name: uk_business_documents_publisher_doc_type_number; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY business_documents
    ADD CONSTRAINT uk_business_documents_publisher_doc_type_number UNIQUE (publisher_id, document_type_id, document_number);


--
-- TOC entry 2174 (class 2606 OID 289393)
-- Dependencies: 1721 1721 1721
-- Name: uk_business_unit_addresses; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY business_unit_addresses
    ADD CONSTRAINT uk_business_unit_addresses UNIQUE (business_unit_id, address_id);


--
-- TOC entry 2179 (class 2606 OID 289395)
-- Dependencies: 1722 1722 1722
-- Name: uk_business_units_org_bu_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY business_units
    ADD CONSTRAINT uk_business_units_org_bu_name UNIQUE (organization_id, business_unit_name);


--
-- TOC entry 2187 (class 2606 OID 289397)
-- Dependencies: 1725 1725 1725
-- Name: uk_cities_country_id_city_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY cities
    ADD CONSTRAINT uk_cities_country_id_city_name UNIQUE (country_id, city_name);


--
-- TOC entry 2197 (class 2606 OID 289399)
-- Dependencies: 1729 1729 1729
-- Name: uk_classifiers_parent_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT uk_classifiers_parent_code UNIQUE (parent_id, classifier_code);


--
-- TOC entry 2202 (class 2606 OID 289401)
-- Dependencies: 1730 1730 1730 1730 1730
-- Name: uk_communication_contacts_parent_type_value_contact_person; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT uk_communication_contacts_parent_type_value_contact_person UNIQUE (address_id, communication_type_id, communication_value, contact_person_id);


--
-- TOC entry 2210 (class 2606 OID 289403)
-- Dependencies: 1733 1733 1733
-- Name: uk_contact_persons_address_person; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT uk_contact_persons_address_person UNIQUE (parent_id, contact_id);


--
-- TOC entry 2214 (class 2606 OID 289405)
-- Dependencies: 1734 1734
-- Name: uk_countries_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY countries
    ADD CONSTRAINT uk_countries_name UNIQUE (country_name);


--
-- TOC entry 2218 (class 2606 OID 289407)
-- Dependencies: 1735 1735
-- Name: uk_currencies_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY currencies
    ADD CONSTRAINT uk_currencies_name UNIQUE (currency_name);


--
-- TOC entry 2228 (class 2606 OID 289409)
-- Dependencies: 1739 1739 1739
-- Name: uk_customer_discount_items_by_categories; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customer_discount_items_by_categories
    ADD CONSTRAINT uk_customer_discount_items_by_categories UNIQUE (customer_discount_id, category_id);


--
-- TOC entry 2232 (class 2606 OID 289411)
-- Dependencies: 1740 1740 1740
-- Name: uk_customer_discount_items_by_products; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customer_discount_items_by_products
    ADD CONSTRAINT uk_customer_discount_items_by_products UNIQUE (customer_discount_id, product_id);


--
-- TOC entry 2236 (class 2606 OID 289413)
-- Dependencies: 1741 1741 1741
-- Name: uk_customer_discounts; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customer_discounts
    ADD CONSTRAINT uk_customer_discounts UNIQUE (organization_id, customer_id);


--
-- TOC entry 2244 (class 2606 OID 289415)
-- Dependencies: 1744 1744 1744
-- Name: uk_data_object_details_do_id_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_details
    ADD CONSTRAINT uk_data_object_details_do_id_code UNIQUE (data_object_id, detail_code);


--
-- TOC entry 2248 (class 2606 OID 289417)
-- Dependencies: 1745 1745 1745
-- Name: uk_data_object_links_parent_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_links
    ADD CONSTRAINT uk_data_object_links_parent_name UNIQUE (parent_id, link_name);


--
-- TOC entry 2276 (class 2606 OID 289419)
-- Dependencies: 1754 1754 1754
-- Name: uk_delivery_certificates_parent_cert_number; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT uk_delivery_certificates_parent_cert_number UNIQUE (parent_id, delivery_certificate_number);


--
-- TOC entry 2256 (class 2606 OID 289421)
-- Dependencies: 1748 1748
-- Name: uk_dot_data_object_type; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY data_object_types
    ADD CONSTRAINT uk_dot_data_object_type UNIQUE (data_object_type);


--
-- TOC entry 2282 (class 2606 OID 289423)
-- Dependencies: 1756 1756
-- Name: uk_enum_classes; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY enum_classes
    ADD CONSTRAINT uk_enum_classes UNIQUE (enum_class_name);


--
-- TOC entry 2296 (class 2606 OID 289425)
-- Dependencies: 1762 1762 1762
-- Name: uk_job_titles_bu_title; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY job_titles
    ADD CONSTRAINT uk_job_titles_bu_title UNIQUE (business_unit_id, job_title);


--
-- TOC entry 2310 (class 2606 OID 289427)
-- Dependencies: 1768 1768 1768 1768
-- Name: uk_passports_parent_type_number; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT uk_passports_parent_type_number UNIQUE (parent_id, passport_type_id, passport_number);


--
-- TOC entry 2324 (class 2606 OID 289429)
-- Dependencies: 1774 1774 1774
-- Name: uk_privilege_categories_organization_category_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY privilege_categories
    ADD CONSTRAINT uk_privilege_categories_organization_category_name UNIQUE (organization_id, category_name);


--
-- TOC entry 2328 (class 2606 OID 289431)
-- Dependencies: 1775 1775 1775
-- Name: uk_privilege_roles_privilege_right; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY privilege_roles
    ADD CONSTRAINT uk_privilege_roles_privilege_right UNIQUE (privilege_id, access_right_id);


--
-- TOC entry 2332 (class 2606 OID 289433)
-- Dependencies: 1776 1776 1776
-- Name: uk_privileges_role_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY privileges
    ADD CONSTRAINT uk_privileges_role_name UNIQUE (security_role_id, privilege_name);


--
-- TOC entry 2336 (class 2606 OID 289435)
-- Dependencies: 1777 1777 1777
-- Name: uk_product_categories_parent_category_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT uk_product_categories_parent_category_name UNIQUE (parent_id, category_name);


--
-- TOC entry 2340 (class 2606 OID 289437)
-- Dependencies: 1778 1778 1778 1778
-- Name: uk_product_percent_values; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY product_percent_values
    ADD CONSTRAINT uk_product_percent_values UNIQUE (organization_id, value_type_id, value_name);


--
-- TOC entry 2346 (class 2606 OID 289439)
-- Dependencies: 1780 1780 1780
-- Name: uk_products_parent_code; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY products
    ADD CONSTRAINT uk_products_parent_code UNIQUE (parent_id, product_code);


--
-- TOC entry 2348 (class 2606 OID 289441)
-- Dependencies: 1780 1780 1780
-- Name: uk_products_parent_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY products
    ADD CONSTRAINT uk_products_parent_name UNIQUE (parent_id, product_name);


--
-- TOC entry 2360 (class 2606 OID 289443)
-- Dependencies: 1785 1785
-- Name: uk_real_products_by_simple_product; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY real_products
    ADD CONSTRAINT uk_real_products_by_simple_product UNIQUE (simple_product_id);


--
-- TOC entry 2368 (class 2606 OID 289445)
-- Dependencies: 1788 1788 1788
-- Name: uk_receipt_certificates_parent_cert_number; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT uk_receipt_certificates_parent_cert_number UNIQUE (parent_id, receipt_certificate_number);


--
-- TOC entry 2383 (class 2606 OID 289447)
-- Dependencies: 1794 1794 1794
-- Name: uk_security_roles_organization_role_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY security_roles
    ADD CONSTRAINT uk_security_roles_organization_role_name UNIQUE (organization_id, security_role_name);


--
-- TOC entry 2385 (class 2606 OID 289449)
-- Dependencies: 1795 1795
-- Name: uk_seq_id_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY sequence_identifiers
    ADD CONSTRAINT uk_seq_id_name UNIQUE (seq_id_name);


--
-- TOC entry 2391 (class 2606 OID 289451)
-- Dependencies: 1797 1797 1797
-- Name: uk_team_members_team_user; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY team_members
    ADD CONSTRAINT uk_team_members_team_user UNIQUE (team_id, user_organization_id);


--
-- TOC entry 2396 (class 2606 OID 289453)
-- Dependencies: 1798 1798 1798
-- Name: uk_teams_organization_team_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY teams
    ADD CONSTRAINT uk_teams_organization_team_name UNIQUE (organization_id, team_name);


--
-- TOC entry 2400 (class 2606 OID 289455)
-- Dependencies: 1799 1799 1799
-- Name: uk_user_group_members; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY user_group_members
    ADD CONSTRAINT uk_user_group_members UNIQUE (user_group_id, user_organization_id);


--
-- TOC entry 2407 (class 2606 OID 289457)
-- Dependencies: 1801 1801 1801
-- Name: uk_user_organizations; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY user_organizations
    ADD CONSTRAINT uk_user_organizations UNIQUE (user_id, organization_id);


--
-- TOC entry 2418 (class 2606 OID 289459)
-- Dependencies: 1804 1804 1804
-- Name: uk_user_security_roles_user_sr; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY user_security_roles
    ADD CONSTRAINT uk_user_security_roles_user_sr UNIQUE (user_organization_id, security_role_id);


--
-- TOC entry 2403 (class 2606 OID 289461)
-- Dependencies: 1800 1800
-- Name: user_groups_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY user_groups
    ADD CONSTRAINT user_groups_pkey PRIMARY KEY (user_group_id);


--
-- TOC entry 2414 (class 2606 OID 289463)
-- Dependencies: 1803 1803
-- Name: user_rights_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT user_rights_pkey PRIMARY KEY (user_right_id);


--
-- TOC entry 2428 (class 2606 OID 289465)
-- Dependencies: 1808 1808
-- Name: warehouse_products_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY warehouse_products
    ADD CONSTRAINT warehouse_products_pkey PRIMARY KEY (warehouse_product_id);


--
-- TOC entry 2198 (class 1259 OID 289466)
-- Dependencies: 1730
-- Name: fk_contact_person_id; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX fk_contact_person_id ON communication_contacts USING btree (contact_person_id);


--
-- TOC entry 2267 (class 1259 OID 289467)
-- Dependencies: 1754
-- Name: fki_certificate_status; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX fki_certificate_status ON delivery_certificates USING btree (delivery_cert_status_id);


--
-- TOC entry 2268 (class 1259 OID 289468)
-- Dependencies: 1754
-- Name: fki_creator_branch; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX fki_creator_branch ON delivery_certificates USING btree (creator_branch_id);


--
-- TOC entry 2269 (class 1259 OID 289469)
-- Dependencies: 1754
-- Name: fki_creator_organization; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX fki_creator_organization ON delivery_certificates USING btree (creator_organization_id);


--
-- TOC entry 2270 (class 1259 OID 289470)
-- Dependencies: 1754
-- Name: fki_delivery_certificates_recipient; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX fki_delivery_certificates_recipient ON delivery_certificates USING btree (recipient_id);


--
-- TOC entry 2271 (class 1259 OID 289471)
-- Dependencies: 1754
-- Name: fki_forwarder_address_constraint; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX fki_forwarder_address_constraint ON delivery_certificates USING btree (forwarder_branch_id);


--
-- TOC entry 2272 (class 1259 OID 289472)
-- Dependencies: 1754
-- Name: fki_recipient_address_constraint; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX fki_recipient_address_constraint ON delivery_certificates USING btree (recipient_branch_id);


--
-- TOC entry 2175 (class 1259 OID 289473)
-- Dependencies: 1722 1722
-- Name: idx_business_units_parent_child; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX idx_business_units_parent_child ON business_units USING btree (parent_business_unit_id, business_unit_id);


--
-- TOC entry 2394 (class 1259 OID 289474)
-- Dependencies: 1798 1798
-- Name: uidx_teams_organization_team_name; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uidx_teams_organization_team_name ON teams USING btree (organization_id, team_name);


--
-- TOC entry 2401 (class 1259 OID 289475)
-- Dependencies: 1800 1800
-- Name: uidx_user_groups_organization_ugname; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uidx_user_groups_organization_ugname ON user_groups USING btree (organization_id, user_group_name);


--
-- TOC entry 2410 (class 1259 OID 289476)
-- Dependencies: 1802 1802 1802 1802 1802 1802 1802 1802 1802 1802
-- Name: uidx_user_rights; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uidx_user_rights ON user_rights USING btree (organization_id, owner_type_id, user_id, user_group_id, access_rights, excluded, data_object_type_id, data_object_id, permission_category_id, special_permission_id);


--
-- TOC entry 2371 (class 1259 OID 298382)
-- Dependencies: 1789
-- Name: uix_registration_codes_email_address; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_registration_codes_email_address ON registration_codes USING btree (lower((email_address)::text));


--
-- TOC entry 2421 (class 1259 OID 289477)
-- Dependencies: 1805
-- Name: uix_users_email_address; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_users_email_address ON users USING btree (lower((email_address)::text));


--
-- TOC entry 2422 (class 1259 OID 289478)
-- Dependencies: 1805
-- Name: uix_users_username; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX uix_users_username ON users USING btree (lower((user_name)::text));


--
-- TOC entry 2411 (class 1259 OID 289479)
-- Dependencies: 1803
-- Name: user_group_idx; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX user_group_idx ON user_rights_old USING btree (user_group_id);


--
-- TOC entry 2412 (class 1259 OID 289480)
-- Dependencies: 1803
-- Name: user_idx; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX user_idx ON user_rights_old USING btree (user_id);


--
-- TOC entry 2431 (class 2606 OID 289481)
-- Dependencies: 2169 1720 1710
-- Name: addresses_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT addresses_parent_id_fkey FOREIGN KEY (parent_id) REFERENCES business_partners(partner_id) ON DELETE CASCADE;


--
-- TOC entry 2432 (class 2606 OID 289486)
-- Dependencies: 1800 1710 2402
-- Name: addresses_user_group_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT addresses_user_group_id_fkey FOREIGN KEY (user_group_id) REFERENCES user_groups(user_group_id);


--
-- TOC entry 2465 (class 2606 OID 289491)
-- Dependencies: 1790 2372 1716
-- Name: bank_details_currency_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT bank_details_currency_id_fkey FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2466 (class 2606 OID 289496)
-- Dependencies: 1716 1710 2137
-- Name: bank_details_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT bank_details_parent_id_fkey FOREIGN KEY (parent_id) REFERENCES addresses(address_id) ON DELETE CASCADE;


--
-- TOC entry 2511 (class 2606 OID 289501)
-- Dependencies: 1734 1725 2211
-- Name: cities_country_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cities
    ADD CONSTRAINT cities_country_id_fkey FOREIGN KEY (country_id) REFERENCES countries(country_id) ON DELETE CASCADE;


--
-- TOC entry 2515 (class 2606 OID 289506)
-- Dependencies: 1726 1749 2257
-- Name: classified_objects_classified_object_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classified_objects
    ADD CONSTRAINT classified_objects_classified_object_id_fkey FOREIGN KEY (classified_object_id) REFERENCES data_objects(data_object_id) ON DELETE CASCADE;


--
-- TOC entry 2519 (class 2606 OID 289511)
-- Dependencies: 2194 1729 1727
-- Name: classifier_applied_for_dot_classifier_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifier_applied_for_dot
    ADD CONSTRAINT classifier_applied_for_dot_classifier_id_fkey FOREIGN KEY (classifier_id) REFERENCES classifiers(classifier_id) ON DELETE CASCADE;


--
-- TOC entry 2527 (class 2606 OID 289516)
-- Dependencies: 2207 1733 1730
-- Name: communication_contacts_contact_person_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT communication_contacts_contact_person_id_fkey FOREIGN KEY (contact_person_id) REFERENCES contact_persons(contact_person_id) ON DELETE SET NULL;


--
-- TOC entry 2528 (class 2606 OID 289521)
-- Dependencies: 1710 2137 1730
-- Name: communication_contacts_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT communication_contacts_parent_id_fkey FOREIGN KEY (address_id) REFERENCES addresses(address_id) ON DELETE CASCADE;


--
-- TOC entry 2541 (class 2606 OID 289526)
-- Dependencies: 1733 1770 2313
-- Name: contact_persons_contact_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT contact_persons_contact_id_fkey FOREIGN KEY (contact_id) REFERENCES persons(partner_id) ON DELETE CASCADE;


--
-- TOC entry 2542 (class 2606 OID 289531)
-- Dependencies: 1710 1733 2137
-- Name: contact_persons_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT contact_persons_parent_id_fkey FOREIGN KEY (parent_id) REFERENCES addresses(address_id) ON DELETE CASCADE;


--
-- TOC entry 2545 (class 2606 OID 289536)
-- Dependencies: 1790 2372 1734
-- Name: countries_currency_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY countries
    ADD CONSTRAINT countries_currency_id_fkey FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2512 (class 2606 OID 289541)
-- Dependencies: 1749 2257 1725
-- Name: data_object_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cities
    ADD CONSTRAINT data_object_fk FOREIGN KEY (city_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2917 (class 2606 OID 289546)
-- Dependencies: 1803 1748 2253
-- Name: data_object_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT data_object_type FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id) ON DELETE CASCADE;


--
-- TOC entry 2918 (class 2606 OID 289551)
-- Dependencies: 1803 1749 2257
-- Name: data_objects; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT data_objects FOREIGN KEY (data_object_id) REFERENCES data_objects(data_object_id) ON DELETE CASCADE;


--
-- TOC entry 2815 (class 2606 OID 289556)
-- Dependencies: 2279 1790 1756
-- Name: fk11ef5dd39219a9be; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY resource_bundle
    ADD CONSTRAINT fk11ef5dd39219a9be FOREIGN KEY (enum_class_id) REFERENCES enum_classes(enum_class_id);


--
-- TOC entry 2444 (class 2606 OID 289561)
-- Dependencies: 1767 1712 2305
-- Name: fk2415657c51b04573; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_messages
    ADD CONSTRAINT fk2415657c51b04573 FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2825 (class 2606 OID 289566)
-- Dependencies: 1720 1793 2169
-- Name: fk25f222e6134fe2b0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e6134fe2b0 FOREIGN KEY (recipient_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2826 (class 2606 OID 289571)
-- Dependencies: 1790 1793 2372
-- Name: fk25f222e617174fab; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e617174fab FOREIGN KEY (transportation_method_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2827 (class 2606 OID 289576)
-- Dependencies: 1790 1793 2372
-- Name: fk25f222e61808129a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e61808129a FOREIGN KEY (doc_delivery_method_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2828 (class 2606 OID 289581)
-- Dependencies: 1710 1793 2137
-- Name: fk25f222e627a66c93; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e627a66c93 FOREIGN KEY (branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2829 (class 2606 OID 289586)
-- Dependencies: 1790 2372 1793
-- Name: fk25f222e63aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e63aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2830 (class 2606 OID 289591)
-- Dependencies: 1790 1793 2372
-- Name: fk25f222e63dc9408c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e63dc9408c FOREIGN KEY (payment_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2831 (class 2606 OID 289596)
-- Dependencies: 2372 1790 1793
-- Name: fk25f222e646685c7a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e646685c7a FOREIGN KEY (delivery_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2832 (class 2606 OID 289601)
-- Dependencies: 1770 2313 1793
-- Name: fk25f222e64da6f8bc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e64da6f8bc FOREIGN KEY (creator_id) REFERENCES persons(partner_id);


--
-- TOC entry 2833 (class 2606 OID 289606)
-- Dependencies: 1793 2169 1720
-- Name: fk25f222e66d20f4c9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e66d20f4c9 FOREIGN KEY (shippingagent_partner_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2834 (class 2606 OID 289611)
-- Dependencies: 2207 1733 1793
-- Name: fk25f222e67ebcc009; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e67ebcc009 FOREIGN KEY (supplier_contact_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2835 (class 2606 OID 289616)
-- Dependencies: 1790 2372 1793
-- Name: fk25f222e696e3ba71; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e696e3ba71 FOREIGN KEY (payment_terms_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2836 (class 2606 OID 289621)
-- Dependencies: 2372 1793 1790
-- Name: fk25f222e69a24d298; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e69a24d298 FOREIGN KEY (deliverystatus_resource_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2837 (class 2606 OID 289626)
-- Dependencies: 2372 1790 1793
-- Name: fk25f222e69c49320d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e69c49320d FOREIGN KEY (status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2838 (class 2606 OID 289631)
-- Dependencies: 1733 1793 2207
-- Name: fk25f222e69ff294dc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e69ff294dc FOREIGN KEY (attendee_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2839 (class 2606 OID 289636)
-- Dependencies: 1790 2372 1793
-- Name: fk25f222e6a94f3ab3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e6a94f3ab3 FOREIGN KEY (invoice_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2840 (class 2606 OID 289641)
-- Dependencies: 1790 1793 2372
-- Name: fk25f222e6b07d659a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e6b07d659a FOREIGN KEY (vat_condition_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2841 (class 2606 OID 289646)
-- Dependencies: 1770 1793 2313
-- Name: fk25f222e6fd5b3613; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fk25f222e6fd5b3613 FOREIGN KEY (sender_id) REFERENCES persons(partner_id);


--
-- TOC entry 2441 (class 2606 OID 289651)
-- Dependencies: 2139 1711 1711
-- Name: fk265f5e4cdf9c931a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_categories
    ADD CONSTRAINT fk265f5e4cdf9c931a FOREIGN KEY (parent_category_id) REFERENCES assembling_categories(assembling_category_id);


--
-- TOC entry 2605 (class 2606 OID 289656)
-- Dependencies: 1749 1750 2257
-- Name: fk26dbbb94399bdd69; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY db_properties
    ADD CONSTRAINT fk26dbbb94399bdd69 FOREIGN KEY (related_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2533 (class 2606 OID 289661)
-- Dependencies: 1790 2372 1731
-- Name: fk281fab21a2454cf2; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT fk281fab21a2454cf2 FOREIGN KEY (applied_algorithm_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2534 (class 2606 OID 289666)
-- Dependencies: 1732 2205 1731
-- Name: fk281fab21d06049d2; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT fk281fab21d06049d2 FOREIGN KEY (complex_product_id) REFERENCES complex_products(product_id);


--
-- TOC entry 2535 (class 2606 OID 289671)
-- Dependencies: 1731 1780 2343
-- Name: fk281fab21f10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT fk281fab21f10b9721 FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2505 (class 2606 OID 289676)
-- Dependencies: 1790 2372 1723
-- Name: fk2f3a073339f9dd96; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cash_recon_pay_summary
    ADD CONSTRAINT fk_cash_recon_pay_summary_currency FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2506 (class 2606 OID 289681)
-- Dependencies: 1790 2372 1723
-- Name: fk2f3a0733bbec3bc5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cash_recon_pay_summary
    ADD CONSTRAINT fk2f3a0733bbec3bc5 FOREIGN KEY (paymenttype) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2507 (class 2606 OID 289686)
-- Dependencies: 1724 1723 2182
-- Name: fk2f3a0733d3be5d1a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cash_recon_pay_summary
    ADD CONSTRAINT fk2f3a0733d3be5d1a FOREIGN KEY (cash_reconcile_id) REFERENCES cash_reconcile(reconcile_id);


--
-- TOC entry 2819 (class 2606 OID 289691)
-- Dependencies: 1792 2372 1790
-- Name: fk326ab82e1ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoice_items
    ADD CONSTRAINT fk326ab82e1ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2820 (class 2606 OID 289696)
-- Dependencies: 1809 1792 2429
-- Name: fk326ab82e9f1800e1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoice_items
    ADD CONSTRAINT fk326ab82e9f1800e1 FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2821 (class 2606 OID 289701)
-- Dependencies: 1780 2343 1792
-- Name: fk326ab82ef10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoice_items
    ADD CONSTRAINT fk326ab82ef10b9721 FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2687 (class 2606 OID 289706)
-- Dependencies: 2137 1764 1710
-- Name: fk327473ad27a66c93; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk327473ad27a66c93 FOREIGN KEY (branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2688 (class 2606 OID 289711)
-- Dependencies: 1764 1790 2372
-- Name: fk327473ad3aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk327473ad3aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2689 (class 2606 OID 289716)
-- Dependencies: 1720 1764 2169
-- Name: fk327473ad5aa049f4; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk327473ad5aa049f4 FOREIGN KEY (supplier_partner_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2690 (class 2606 OID 289721)
-- Dependencies: 1764 2207 1733
-- Name: fk327473ad7ebcc009; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk327473ad7ebcc009 FOREIGN KEY (supplier_contact_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2691 (class 2606 OID 289726)
-- Dependencies: 1764 2372 1790
-- Name: fk327473ada97faa1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmations
    ADD CONSTRAINT fk327473ada97faa1 FOREIGN KEY (document_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2433 (class 2606 OID 289731)
-- Dependencies: 1710 2402 1800
-- Name: fk34207ba212dbbc4a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT fk34207ba212dbbc4a FOREIGN KEY (user_group_id) REFERENCES user_groups(user_group_id);


--
-- TOC entry 2434 (class 2606 OID 289736)
-- Dependencies: 2169 1720 1710
-- Name: fk34207ba24358767f; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT fk34207ba24358767f FOREIGN KEY (parent_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2435 (class 2606 OID 289741)
-- Dependencies: 1749 1710 2257
-- Name: fk34207ba28d7c8fa8; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT fk34207ba28d7c8fa8 FOREIGN KEY (address_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2436 (class 2606 OID 289746)
-- Dependencies: 1710 1734 2211
-- Name: fk34207ba2977341c1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT fk34207ba2977341c1 FOREIGN KEY (country_id) REFERENCES countries(country_id);


--
-- TOC entry 2437 (class 2606 OID 289751)
-- Dependencies: 1725 2184 1710
-- Name: fk34207ba2ec30e373; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT fk34207ba2ec30e373 FOREIGN KEY (city_id) REFERENCES cities(city_id);


--
-- TOC entry 2554 (class 2606 OID 289756)
-- Dependencies: 1741 2233 1739
-- Name: fk34a6c58834ee818e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items_by_categories
    ADD CONSTRAINT fk34a6c58834ee818e FOREIGN KEY (customer_discount_id) REFERENCES customer_discounts(customer_discount_id);


--
-- TOC entry 2555 (class 2606 OID 289761)
-- Dependencies: 1739 1777 2333
-- Name: fk34a6c5886e2f83d0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items_by_categories
    ADD CONSTRAINT fk34a6c5886e2f83d0 FOREIGN KEY (category_id) REFERENCES product_categories(product_category_id);


--
-- TOC entry 2556 (class 2606 OID 289766)
-- Dependencies: 2223 1739 1738
-- Name: fk34a6c588d3502ad3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items_by_categories
    ADD CONSTRAINT fk34a6c588d3502ad3 FOREIGN KEY (customer_discount_item_id) REFERENCES customer_discount_items(customer_discount_item_id);


--
-- TOC entry 2467 (class 2606 OID 289771)
-- Dependencies: 1710 1716 2137
-- Name: fk363aa33f2f5fd250; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk363aa33f2f5fd250 FOREIGN KEY (bank_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2468 (class 2606 OID 289776)
-- Dependencies: 1790 1716 2372
-- Name: fk363aa33f3aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk363aa33f3aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2469 (class 2606 OID 289781)
-- Dependencies: 1767 1716 2305
-- Name: fk363aa33fee88a3ca; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk363aa33fee88a3ca FOREIGN KEY (bank_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2470 (class 2606 OID 289786)
-- Dependencies: 1770 1716 2313
-- Name: fk363aa33ff339b22b; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk363aa33ff339b22b FOREIGN KEY (bank_contact_id) REFERENCES persons(partner_id);


--
-- TOC entry 2616 (class 2606 OID 289791)
-- Dependencies: 1720 1754 2169
-- Name: fk3edb4c27134fe2b0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c27134fe2b0 FOREIGN KEY (recipient_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2617 (class 2606 OID 289796)
-- Dependencies: 1770 1754 2313
-- Name: fk3edb4c27157c075; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c27157c075 FOREIGN KEY (forwarder_contact_id) REFERENCES persons(partner_id);


--
-- TOC entry 2618 (class 2606 OID 289801)
-- Dependencies: 1754 1710 2137
-- Name: fk3edb4c273364a040; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c273364a040 FOREIGN KEY (creator_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2619 (class 2606 OID 289806)
-- Dependencies: 1754 1790 2372
-- Name: fk3edb4c2736e2c55d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c2736e2c55d FOREIGN KEY (delivery_cert_status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2620 (class 2606 OID 289811)
-- Dependencies: 2207 1754 1733
-- Name: fk3edb4c273b8b059c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c273b8b059c FOREIGN KEY (recipient_contact_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2621 (class 2606 OID 289816)
-- Dependencies: 1790 1754 2372
-- Name: fk3edb4c2746dd317; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c2746dd317 FOREIGN KEY (delivery_cert_method_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2622 (class 2606 OID 289821)
-- Dependencies: 1770 1754 2313
-- Name: fk3edb4c274da6f8bc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c274da6f8bc FOREIGN KEY (creator_id) REFERENCES persons(partner_id);


--
-- TOC entry 2623 (class 2606 OID 289826)
-- Dependencies: 1710 1754 2137
-- Name: fk3edb4c277c77cded; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c277c77cded FOREIGN KEY (recipient_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2624 (class 2606 OID 289831)
-- Dependencies: 1790 1754 2372
-- Name: fk3edb4c278a6109cb; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c278a6109cb FOREIGN KEY (delivery_cert_reason_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2625 (class 2606 OID 289836)
-- Dependencies: 1809 1754 2429
-- Name: fk3edb4c279f1800e1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c279f1800e1 FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2626 (class 2606 OID 289841)
-- Dependencies: 1754 2305 1767
-- Name: fk3edb4c27b4524360; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c27b4524360 FOREIGN KEY (creator_organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2627 (class 2606 OID 289846)
-- Dependencies: 1754 1710 2137
-- Name: fk3edb4c27e581d2c6; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c27e581d2c6 FOREIGN KEY (forwarder_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2628 (class 2606 OID 289851)
-- Dependencies: 1754 1767 2305
-- Name: fk3edb4c27f2569c14; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk3edb4c27f2569c14 FOREIGN KEY (forwarder_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2713 (class 2606 OID 289856)
-- Dependencies: 2169 1769 1720
-- Name: fk40afd1582b1363d6; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pattern_mask_formats
    ADD CONSTRAINT fk40afd1582b1363d6 FOREIGN KEY (owner_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2584 (class 2606 OID 289861)
-- Dependencies: 1790 1746 2372
-- Name: fk40c96239b01192e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_permissions
    ADD CONSTRAINT fk40c96239b01192e FOREIGN KEY (user_right_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2585 (class 2606 OID 289866)
-- Dependencies: 1790 1746 2372
-- Name: fk40c96239c2559310; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_permissions
    ADD CONSTRAINT fk40c96239c2559310 FOREIGN KEY (permission_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2586 (class 2606 OID 289871)
-- Dependencies: 1749 1746 2257
-- Name: fk40c96239d741e28; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_permissions
    ADD CONSTRAINT fk40c96239d741e28 FOREIGN KEY (data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2822 (class 2606 OID 289876)
-- Dependencies: 1790 1792 2372
-- Name: fk46500e3b1ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoice_items
    ADD CONSTRAINT fk46500e3b1ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2823 (class 2606 OID 289881)
-- Dependencies: 1809 1792 2429
-- Name: fk46500e3b9f1800e1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoice_items
    ADD CONSTRAINT fk46500e3b9f1800e1 FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2824 (class 2606 OID 289886)
-- Dependencies: 1780 1792 2343
-- Name: fk46500e3bf10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoice_items
    ADD CONSTRAINT fk46500e3bf10b9721 FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2546 (class 2606 OID 289891)
-- Dependencies: 1734 1790 2372
-- Name: fk509f9ab43aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY countries
    ADD CONSTRAINT fk509f9ab43aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2552 (class 2606 OID 289896)
-- Dependencies: 1738 1749 2257
-- Name: fk51a781c3c35b8d6c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items
    ADD CONSTRAINT fk51a781c3c35b8d6c FOREIGN KEY (customer_discount_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2739 (class 2606 OID 289901)
-- Dependencies: 2333 1777 1777
-- Name: fk5519b36c1c57732d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT fk5519b36c1c57732d FOREIGN KEY (parent_cat_id) REFERENCES product_categories(product_category_id);


--
-- TOC entry 2740 (class 2606 OID 289906)
-- Dependencies: 1769 1777 2311
-- Name: fk5519b36c7a956e19; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT fk5519b36c7a956e19 FOREIGN KEY (pattern_mask_format_id) REFERENCES pattern_mask_formats(pattern_mask_format_id);


--
-- TOC entry 2650 (class 2606 OID 289911)
-- Dependencies: 1790 1759 2372
-- Name: fk582b0dd01ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_items
    ADD CONSTRAINT fk582b0dd01ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2651 (class 2606 OID 289916)
-- Dependencies: 1761 1759 2291
-- Name: fk582b0dd04d7c32a1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_items
    ADD CONSTRAINT fk582b0dd04d7c32a1 FOREIGN KEY (goods_receipt_id) REFERENCES goods_receipts(goods_receipt_id);


--
-- TOC entry 2652 (class 2606 OID 289921)
-- Dependencies: 1749 1759 2257
-- Name: fk582b0dd0cf28c922; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_items
    ADD CONSTRAINT fk582b0dd0cf28c922 FOREIGN KEY (receipt_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2653 (class 2606 OID 289926)
-- Dependencies: 1780 1759 2343
-- Name: fk582b0dd0f10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_items
    ADD CONSTRAINT fk582b0dd0f10b9721 FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2662 (class 2606 OID 289931)
-- Dependencies: 2313 1770 1761
-- Name: fk5c97500422724475; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipts
    ADD CONSTRAINT fk5c97500422724475 FOREIGN KEY (storekeeper_id) REFERENCES persons(partner_id);


--
-- TOC entry 2663 (class 2606 OID 289936)
-- Dependencies: 2137 1761 1710
-- Name: fk5c9750046e1e91e0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipts
    ADD CONSTRAINT fk5c9750046e1e91e0 FOREIGN KEY (supplier_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2664 (class 2606 OID 289941)
-- Dependencies: 1761 1770 2313
-- Name: fk5c9750048c52e49b; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipts
    ADD CONSTRAINT fk5c9750048c52e49b FOREIGN KEY (supplier_contact_id) REFERENCES persons(partner_id);


--
-- TOC entry 2665 (class 2606 OID 289946)
-- Dependencies: 2137 1761 1710
-- Name: fk5c9750048f689373; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipts
    ADD CONSTRAINT fk5c9750048f689373 FOREIGN KEY (consignee_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2666 (class 2606 OID 289951)
-- Dependencies: 1720 1761 2169
-- Name: fk5c975004aac55a1d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipts
    ADD CONSTRAINT fk5c975004aac55a1d FOREIGN KEY (supplier_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2667 (class 2606 OID 289956)
-- Dependencies: 1790 1761 2372
-- Name: fk5c975004b32d38c6; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipts
    ADD CONSTRAINT fk5c975004b32d38c6 FOREIGN KEY (receipt_status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2668 (class 2606 OID 289961)
-- Dependencies: 1767 1761 2305
-- Name: fk5c975004c3399407; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipts
    ADD CONSTRAINT fk5c975004c3399407 FOREIGN KEY (consignee_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2669 (class 2606 OID 289966)
-- Dependencies: 1749 1761 2257
-- Name: fk5c975004d190cf6d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipts
    ADD CONSTRAINT fk5c975004d190cf6d FOREIGN KEY (goods_receipt_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2670 (class 2606 OID 289971)
-- Dependencies: 1790 1761 2372
-- Name: fk5c975004e0ce3c35; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipts
    ADD CONSTRAINT fk5c975004e0ce3c35 FOREIGN KEY (related_document_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2692 (class 2606 OID 289976)
-- Dependencies: 1763 1765 2297
-- Name: fk5f8caf2a59f71c23; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_item_match
    ADD CONSTRAINT fk5f8caf2a59f71c23 FOREIGN KEY (orderconfirmationitem_confirmation_item_id) REFERENCES order_confirmation_items(confirmation_item_id);


--
-- TOC entry 2693 (class 2606 OID 289981)
-- Dependencies: 1765 1783 2353
-- Name: fk5f8caf2a867a1de; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_item_match
    ADD CONSTRAINT fk5f8caf2a867a1de FOREIGN KEY (purchaseorderitem_order_item_id) REFERENCES purchase_order_items(order_item_id);


--
-- TOC entry 2447 (class 2606 OID 289986)
-- Dependencies: 1713 1714 2151
-- Name: fk61ac266056f0df10; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_item_values
    ADD CONSTRAINT fk61ac266056f0df10 FOREIGN KEY (item_id) REFERENCES assembling_schema_items(item_id);


--
-- TOC entry 2448 (class 2606 OID 289991)
-- Dependencies: 2425 1713 1807
-- Name: fk61ac266094a6e189; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_item_values
    ADD CONSTRAINT fk61ac266094a6e189 FOREIGN KEY (virtual_product_id) REFERENCES virtual_products(product_id);


--
-- TOC entry 2565 (class 2606 OID 289996)
-- Dependencies: 1720 1741 2169
-- Name: fk61e8f0315e5242cb; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discounts
    ADD CONSTRAINT fk61e8f0315e5242cb FOREIGN KEY (customer_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2566 (class 2606 OID 290001)
-- Dependencies: 1749 1741 2257
-- Name: fk61e8f031c8fb185a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discounts
    ADD CONSTRAINT fk61e8f031c8fb185a FOREIGN KEY (customer_discount_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2817 (class 2606 OID 290006)
-- Dependencies: 1792 1791 2376
-- Name: fk65f152a13e94bedc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoice_item_link
    ADD CONSTRAINT fk65f152a13e94bedc FOREIGN KEY (invoice_item_id) REFERENCES sales_invoice_items(invoice_item_id);


--
-- TOC entry 2492 (class 2606 OID 290011)
-- Dependencies: 1790 1720 2372
-- Name: fk6b9ae64a3d90490; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_partners
    ADD CONSTRAINT fk6b9ae64a3d90490 FOREIGN KEY (default_currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2743 (class 2606 OID 290016)
-- Dependencies: 1790 1779 2372
-- Name: fk725e8d71ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk725e8d71ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2744 (class 2606 OID 290021)
-- Dependencies: 1779 2372 1790
-- Name: fk725e8d73aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk725e8d73aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2745 (class 2606 OID 290026)
-- Dependencies: 2386 1779 1796
-- Name: fk725e8d7a330dfcf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk725e8d7a330dfcf FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2746 (class 2606 OID 290031)
-- Dependencies: 1779 1720 2169
-- Name: fk725e8d7aac55a1d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk725e8d7aac55a1d FOREIGN KEY (supplier_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2599 (class 2606 OID 290036)
-- Dependencies: 2257 1749 1749
-- Name: fk74e5117f2ff7d10e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk74e5117f2ff7d10e FOREIGN KEY (linked_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2600 (class 2606 OID 290041)
-- Dependencies: 1748 1749 2253
-- Name: fk74e5117fa44e2131; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk74e5117fa44e2131 FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2601 (class 2606 OID 290046)
-- Dependencies: 1749 1749 2257
-- Name: fk74e5117fafa1da5d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk74e5117fafa1da5d FOREIGN KEY (parent_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2793 (class 2606 OID 290051)
-- Dependencies: 1790 1786 2372
-- Name: fk7503fcd11ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk7503fcd11ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2794 (class 2606 OID 290056)
-- Dependencies: 1796 1786 2386
-- Name: fk7503fcd1a330dfcf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk7503fcd1a330dfcf FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2795 (class 2606 OID 290061)
-- Dependencies: 1796 1786 2386
-- Name: fk7503fcd1f10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk7503fcd1f10b9721 FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2684 (class 2606 OID 290066)
-- Dependencies: 1790 1763 2372
-- Name: fk7e6ecbc71ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmation_items
    ADD CONSTRAINT fk7e6ecbc71ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2685 (class 2606 OID 290071)
-- Dependencies: 2372 1763 1790
-- Name: fk7e6ecbc73aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmation_items
    ADD CONSTRAINT fk7e6ecbc73aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2686 (class 2606 OID 290076)
-- Dependencies: 1763 1796 2386
-- Name: fk7e6ecbc7a330dfcf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_confirmation_items
    ADD CONSTRAINT fk7e6ecbc7a330dfcf FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2696 (class 2606 OID 290081)
-- Dependencies: 2169 1767 1720
-- Name: fk8258b9a017025956; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk8258b9a017025956 FOREIGN KEY (organization_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2697 (class 2606 OID 290086)
-- Dependencies: 1790 1767 2372
-- Name: fk8258b9a0180e7eb9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk8258b9a0180e7eb9 FOREIGN KEY (organization_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2698 (class 2606 OID 290091)
-- Dependencies: 1790 1767 2372
-- Name: fk8258b9a03aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk8258b9a03aa5298e FOREIGN KEY (share_capital_currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2699 (class 2606 OID 290096)
-- Dependencies: 1710 1767 2137
-- Name: fk8258b9a05416c47; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk8258b9a05416c47 FOREIGN KEY (registration_address_id) REFERENCES addresses(address_id);


--
-- TOC entry 2700 (class 2606 OID 290101)
-- Dependencies: 1790 1767 2372
-- Name: fk8258b9a088b1a4d7; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk8258b9a088b1a4d7 FOREIGN KEY (share_capital_currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2701 (class 2606 OID 290106)
-- Dependencies: 1710 1767 2137
-- Name: fk8258b9a08b780b82; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk8258b9a08b780b82 FOREIGN KEY (administration_address_id) REFERENCES addresses(address_id);


--
-- TOC entry 2702 (class 2606 OID 290111)
-- Dependencies: 1767 1767 2305
-- Name: fk8258b9a08c46f1ed; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT fk8258b9a08c46f1ed FOREIGN KEY (registration_organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2789 (class 2606 OID 290116)
-- Dependencies: 1796 1785 2386
-- Name: fk82a39ae56103607c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY real_products
    ADD CONSTRAINT fk82a39ae56103607c FOREIGN KEY (simple_product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2790 (class 2606 OID 290121)
-- Dependencies: 1807 1785 2425
-- Name: fk82a39ae59f88efd5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY real_products
    ADD CONSTRAINT fk82a39ae59f88efd5 FOREIGN KEY (product_id) REFERENCES virtual_products(product_id);


--
-- TOC entry 2754 (class 2606 OID 290126)
-- Dependencies: 1781 1763 2297
-- Name: fk82d039701140d7eb; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT fk82d039701140d7eb FOREIGN KEY (order_confirmation_item_id) REFERENCES order_confirmation_items(confirmation_item_id);


--
-- TOC entry 2755 (class 2606 OID 290131)
-- Dependencies: 2372 1781 1790
-- Name: fk82d039701ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT fk82d039701ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2756 (class 2606 OID 290136)
-- Dependencies: 1783 1781 2353
-- Name: fk82d039705c6db99f; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT fk82d039705c6db99f FOREIGN KEY (purchase_order_item_id) REFERENCES purchase_order_items(order_item_id);


--
-- TOC entry 2757 (class 2606 OID 290141)
-- Dependencies: 1782 1781 2351
-- Name: fk82d039706f432245; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT fk82d039706f432245 FOREIGN KEY (invoice_id) REFERENCES purchase_invoices(invoice_id);


--
-- TOC entry 2758 (class 2606 OID 290146)
-- Dependencies: 1749 1781 2257
-- Name: fk82d03970802ad517; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT fk82d03970802ad517 FOREIGN KEY (invoice_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2759 (class 2606 OID 290151)
-- Dependencies: 1780 1781 2343
-- Name: fk82d03970f10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT fk82d03970f10b9721 FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2640 (class 2606 OID 290156)
-- Dependencies: 1749 1755 2257
-- Name: fk8881f7611698d59; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY entity_sequences
    ADD CONSTRAINT fk8881f7611698d59 FOREIGN KEY (entity_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2641 (class 2606 OID 290161)
-- Dependencies: 1748 1755 2253
-- Name: fk8881f76a44e2131; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY entity_sequences
    ADD CONSTRAINT fk8881f76a44e2131 FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2474 (class 2606 OID 290166)
-- Dependencies: 1718 1719 2165
-- Name: fk8b249c1c40f84bd3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_document_status_log
    ADD CONSTRAINT fk8b249c1c40f84bd3 FOREIGN KEY (document_id) REFERENCES business_documents(document_id);


--
-- TOC entry 2475 (class 2606 OID 290171)
-- Dependencies: 1718 1790 2372
-- Name: fk8b249c1c5df497e9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_document_status_log
    ADD CONSTRAINT fk8b249c1c5df497e9 FOREIGN KEY (document_status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2476 (class 2606 OID 290176)
-- Dependencies: 2313 1718 1770
-- Name: fk8b249c1cbf66cb72; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_document_status_log
    ADD CONSTRAINT fk8b249c1cbf66cb72 FOREIGN KEY (officer_id) REFERENCES persons(partner_id);


--
-- TOC entry 2508 (class 2606 OID 290181)
-- Dependencies: 1790 1724 2372
-- Name: fk8c60fd423aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cash_reconcile
    ADD CONSTRAINT fk8c60fd423aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2509 (class 2606 OID 290186)
-- Dependencies: 1770 1724 2313
-- Name: fk8c60fd42826572c5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cash_reconcile
    ADD CONSTRAINT fk8c60fd42826572c5 FOREIGN KEY (cashier_id) REFERENCES persons(partner_id);


--
-- TOC entry 2510 (class 2606 OID 290191)
-- Dependencies: 1719 1724 2165
-- Name: fk8c60fd42e14660c0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cash_reconcile
    ADD CONSTRAINT fk8c60fd42e14660c0 FOREIGN KEY (reconcile_id) REFERENCES business_documents(document_id);


--
-- TOC entry 2571 (class 2606 OID 290196)
-- Dependencies: 1710 1743 2137
-- Name: fk8f9cba6e27a66c93; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_payments
    ADD CONSTRAINT fk8f9cba6e27a66c93 FOREIGN KEY (branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2572 (class 2606 OID 290201)
-- Dependencies: 1790 1743 2372
-- Name: fk8f9cba6e3aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_payments
    ADD CONSTRAINT fk8f9cba6e3aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2573 (class 2606 OID 290206)
-- Dependencies: 1743 2372 1790
-- Name: fk8f9cba6e3dc9408c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_payments
    ADD CONSTRAINT fk8f9cba6e3dc9408c FOREIGN KEY (payment_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2574 (class 2606 OID 290211)
-- Dependencies: 1743 1770 2313
-- Name: fk8f9cba6e4da6f8bc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_payments
    ADD CONSTRAINT fk8f9cba6e4da6f8bc FOREIGN KEY (creator_id) REFERENCES persons(partner_id);


--
-- TOC entry 2575 (class 2606 OID 290216)
-- Dependencies: 1743 1720 2169
-- Name: fk8f9cba6e5e5242cb; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_payments
    ADD CONSTRAINT fk8f9cba6e5e5242cb FOREIGN KEY (customer_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2576 (class 2606 OID 290221)
-- Dependencies: 2313 1743 1770
-- Name: fk8f9cba6e826572c5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_payments
    ADD CONSTRAINT fk8f9cba6e826572c5 FOREIGN KEY (cashier_id) REFERENCES persons(partner_id);


--
-- TOC entry 2577 (class 2606 OID 290226)
-- Dependencies: 1790 1743 2372
-- Name: fk8f9cba6e9c49320d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_payments
    ADD CONSTRAINT fk8f9cba6e9c49320d FOREIGN KEY (status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2578 (class 2606 OID 290231)
-- Dependencies: 1733 1743 2207
-- Name: fk8f9cba6eb1651ab7; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_payments
    ADD CONSTRAINT fk8f9cba6eb1651ab7 FOREIGN KEY (customer_contact_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2579 (class 2606 OID 290236)
-- Dependencies: 1770 1743 2313
-- Name: fk8f9cba6ee39a2279; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_payments
    ADD CONSTRAINT fk8f9cba6ee39a2279 FOREIGN KEY (completor_id) REFERENCES persons(partner_id);


--
-- TOC entry 2581 (class 2606 OID 290241)
-- Dependencies: 1749 1745 2257
-- Name: fk9157692e2ff7d10e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_links
    ADD CONSTRAINT fk9157692e2ff7d10e FOREIGN KEY (linked_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2941 (class 2606 OID 290246)
-- Dependencies: 1710 1809 2137
-- Name: fk94f81e10a6877d01; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouses
    ADD CONSTRAINT fk94f81e10a6877d01 FOREIGN KEY (address_id) REFERENCES addresses(address_id);


--
-- TOC entry 2942 (class 2606 OID 290251)
-- Dependencies: 1733 1809 2207
-- Name: fk94f81e10fbdf54bf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouses
    ADD CONSTRAINT fk94f81e10fbdf54bf FOREIGN KEY (warehouseman_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2939 (class 2606 OID 290256)
-- Dependencies: 1808 1809 2429
-- Name: fk951433609f1800e1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouse_products
    ADD CONSTRAINT fk951433609f1800e1 FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2940 (class 2606 OID 290261)
-- Dependencies: 1808 1796 2386
-- Name: fk95143360a330dfcf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouse_products
    ADD CONSTRAINT fk95143360a330dfcf FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2800 (class 2606 OID 290266)
-- Dependencies: 2361 1787 1786
-- Name: fk98230d0e73d2d06a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_serial_numbers
    ADD CONSTRAINT fk98230d0e73d2d06a FOREIGN KEY (certificate_item_id) REFERENCES receipt_certificate_items(certificate_item_id);


--
-- TOC entry 2723 (class 2606 OID 290271)
-- Dependencies: 1796 1772 2386
-- Name: fk9a0ef6e8a330dfcf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pricelist_items
    ADD CONSTRAINT fk9a0ef6e8a330dfcf FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2438 (class 2606 OID 290276)
-- Dependencies: 1725 1710 2184
-- Name: fk_addresses_city_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT fk_addresses_city_id FOREIGN KEY (city_id) REFERENCES cities(city_id);


--
-- TOC entry 2439 (class 2606 OID 290281)
-- Dependencies: 1734 1710 2211
-- Name: fk_addresses_country_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT fk_addresses_country_id FOREIGN KEY (country_id) REFERENCES countries(country_id);


--
-- TOC entry 2440 (class 2606 OID 290286)
-- Dependencies: 1749 1710 2257
-- Name: fk_addresses_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT fk_addresses_do_id FOREIGN KEY (address_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2442 (class 2606 OID 290291)
-- Dependencies: 1749 1711 2257
-- Name: fk_assembling_categories_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_categories
    ADD CONSTRAINT fk_assembling_categories_do FOREIGN KEY (assembling_category_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2443 (class 2606 OID 290296)
-- Dependencies: 2139 1711 1711
-- Name: fk_assembling_categories_parent_category; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_categories
    ADD CONSTRAINT fk_assembling_categories_parent_category FOREIGN KEY (parent_category_id) REFERENCES assembling_categories(assembling_category_id);


--
-- TOC entry 2445 (class 2606 OID 290301)
-- Dependencies: 2257 1712 1749
-- Name: fk_assembling_messages_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_messages
    ADD CONSTRAINT fk_assembling_messages_do FOREIGN KEY (message_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2446 (class 2606 OID 290306)
-- Dependencies: 1712 1767 2305
-- Name: fk_assembling_messages_organization; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_messages
    ADD CONSTRAINT fk_assembling_messages_organization FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2449 (class 2606 OID 290311)
-- Dependencies: 2151 1713 1714
-- Name: fk_assembling_schema_item_values_as_item; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_item_values
    ADD CONSTRAINT fk_assembling_schema_item_values_as_item FOREIGN KEY (item_id) REFERENCES assembling_schema_items(item_id);


--
-- TOC entry 2450 (class 2606 OID 290316)
-- Dependencies: 1807 1713 2425
-- Name: fk_assembling_schema_item_values_vp; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_item_values
    ADD CONSTRAINT fk_assembling_schema_item_values_vp FOREIGN KEY (virtual_product_id) REFERENCES virtual_products(product_id);


--
-- TOC entry 2451 (class 2606 OID 290321)
-- Dependencies: 1790 1714 2372
-- Name: fk_assembling_schema_items_algorithm; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT fk_assembling_schema_items_algorithm FOREIGN KEY (algorithm_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2452 (class 2606 OID 290326)
-- Dependencies: 1790 1714 2372
-- Name: fk_assembling_schema_items_data_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT fk_assembling_schema_items_data_type FOREIGN KEY (data_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2453 (class 2606 OID 290331)
-- Dependencies: 1712 1714 2145
-- Name: fk_assembling_schema_items_message; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT fk_assembling_schema_items_message FOREIGN KEY (message_id) REFERENCES assembling_messages(message_id);


--
-- TOC entry 2454 (class 2606 OID 290336)
-- Dependencies: 1715 1714 2153
-- Name: fk_assembling_schema_items_owner; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT fk_assembling_schema_items_owner FOREIGN KEY (assembling_schema_id) REFERENCES assembling_schemas(product_id);


--
-- TOC entry 2459 (class 2606 OID 290341)
-- Dependencies: 1711 1715 2139
-- Name: fk_assembling_schemas_categories; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT fk_assembling_schemas_categories FOREIGN KEY (category_id) REFERENCES assembling_categories(assembling_category_id);


--
-- TOC entry 2460 (class 2606 OID 290346)
-- Dependencies: 1790 1715 2372
-- Name: fk_assembling_schemas_measure_unit; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT fk_assembling_schemas_measure_unit FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2461 (class 2606 OID 290351)
-- Dependencies: 1807 1715 2425
-- Name: fk_assembling_schemas_virtual_products; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT fk_assembling_schemas_virtual_products FOREIGN KEY (product_id) REFERENCES virtual_products(product_id);


--
-- TOC entry 2471 (class 2606 OID 290356)
-- Dependencies: 1710 1716 2137
-- Name: fk_bank_details_bank_branch; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk_bank_details_bank_branch FOREIGN KEY (bank_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2472 (class 2606 OID 290361)
-- Dependencies: 1749 1716 2257
-- Name: fk_bank_details_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bank_details
    ADD CONSTRAINT fk_bank_details_do_id FOREIGN KEY (bank_detail_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2477 (class 2606 OID 290366)
-- Dependencies: 1719 1718 2165
-- Name: fk_business_document_status_log_documents; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_document_status_log
    ADD CONSTRAINT fk_business_document_status_log_documents FOREIGN KEY (document_id) REFERENCES business_documents(document_id);


--
-- TOC entry 2478 (class 2606 OID 290371)
-- Dependencies: 1718 1770 2313
-- Name: fk_business_document_status_log_persons; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_document_status_log
    ADD CONSTRAINT fk_business_document_status_log_persons FOREIGN KEY (officer_id) REFERENCES persons(partner_id);


--
-- TOC entry 2479 (class 2606 OID 290376)
-- Dependencies: 1718 1790 2372
-- Name: fk_business_document_status_log_resource_bundle; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_document_status_log
    ADD CONSTRAINT fk_business_document_status_log_resource_bundle FOREIGN KEY (document_status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2480 (class 2606 OID 290381)
-- Dependencies: 2137 1719 1710
-- Name: fk_business_documents_publisher_branch_addresses; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_documents
    ADD CONSTRAINT fk_business_documents_publisher_branch_addresses FOREIGN KEY (publisher_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2481 (class 2606 OID 290386)
-- Dependencies: 1770 1719 2313
-- Name: fk_business_documents_publisher_contact_persons; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_documents
    ADD CONSTRAINT fk_business_documents_publisher_contact_persons FOREIGN KEY (publisher_officer_id) REFERENCES persons(partner_id);


--
-- TOC entry 2482 (class 2606 OID 290391)
-- Dependencies: 1767 2305 1719
-- Name: fk_business_documents_publisher_organizations; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_documents
    ADD CONSTRAINT fk_business_documents_publisher_organizations FOREIGN KEY (publisher_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2493 (class 2606 OID 290396)
-- Dependencies: 1720 2372 1790
-- Name: fk_business_partners_currency; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_partners
    ADD CONSTRAINT fk_business_partners_currency FOREIGN KEY (default_currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2494 (class 2606 OID 290401)
-- Dependencies: 1710 1721 2137
-- Name: fk_business_unit_addresses_address; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_unit_addresses
    ADD CONSTRAINT fk_business_unit_addresses_address FOREIGN KEY (address_id) REFERENCES addresses(address_id);


--
-- TOC entry 2495 (class 2606 OID 290406)
-- Dependencies: 1722 2176 1721
-- Name: fk_business_unit_addresses_bu; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_unit_addresses
    ADD CONSTRAINT fk_business_unit_addresses_bu FOREIGN KEY (business_unit_id) REFERENCES business_units(business_unit_id);


--
-- TOC entry 2496 (class 2606 OID 290411)
-- Dependencies: 1749 2257 1721
-- Name: fk_business_unit_addresses_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_unit_addresses
    ADD CONSTRAINT fk_business_unit_addresses_do FOREIGN KEY (business_unit_address_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2497 (class 2606 OID 290416)
-- Dependencies: 1730 2199 1721
-- Name: fk_business_unit_addresses_email; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_unit_addresses
    ADD CONSTRAINT fk_business_unit_addresses_email FOREIGN KEY (email_id) REFERENCES communication_contacts(communication_contact_id);


--
-- TOC entry 2498 (class 2606 OID 290421)
-- Dependencies: 1730 1721 2199
-- Name: fk_business_unit_addresses_fax; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_unit_addresses
    ADD CONSTRAINT fk_business_unit_addresses_fax FOREIGN KEY (fax_id) REFERENCES communication_contacts(communication_contact_id);


--
-- TOC entry 2499 (class 2606 OID 290426)
-- Dependencies: 1721 2199 1730
-- Name: fk_business_unit_addresses_phone; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_unit_addresses
    ADD CONSTRAINT fk_business_unit_addresses_phone FOREIGN KEY (phone_id) REFERENCES communication_contacts(communication_contact_id);


--
-- TOC entry 2500 (class 2606 OID 290431)
-- Dependencies: 2372 1721 1790
-- Name: fk_business_unit_addresses_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_unit_addresses
    ADD CONSTRAINT fk_business_unit_addresses_type FOREIGN KEY (address_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2501 (class 2606 OID 290436)
-- Dependencies: 1722 1722 2176
-- Name: fk_business_units_bu; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_units
    ADD CONSTRAINT fk_business_units_bu FOREIGN KEY (parent_business_unit_id) REFERENCES business_units(business_unit_id);


--
-- TOC entry 2502 (class 2606 OID 290441)
-- Dependencies: 2372 1790 1722
-- Name: fk_business_units_bu_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_units
    ADD CONSTRAINT fk_business_units_bu_type FOREIGN KEY (business_unit_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2503 (class 2606 OID 290446)
-- Dependencies: 1722 2257 1749
-- Name: fk_business_units_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_units
    ADD CONSTRAINT fk_business_units_do FOREIGN KEY (business_unit_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2504 (class 2606 OID 290451)
-- Dependencies: 1767 2305 1722
-- Name: fk_business_units_organizations; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_units
    ADD CONSTRAINT fk_business_units_organizations FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2629 (class 2606 OID 290456)
-- Dependencies: 1754 1790 2372
-- Name: fk_certificate_status; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_certificate_status FOREIGN KEY (delivery_cert_status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2516 (class 2606 OID 290461)
-- Dependencies: 2194 1729 1726
-- Name: fk_classified_objects_classifier_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classified_objects
    ADD CONSTRAINT fk_classified_objects_classifier_id FOREIGN KEY (classifier_id) REFERENCES classifiers(classifier_id);


--
-- TOC entry 2520 (class 2606 OID 290466)
-- Dependencies: 1748 1727 2253
-- Name: fk_classifier_applied_for_dot_dot_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifier_applied_for_dot
    ADD CONSTRAINT fk_classifier_applied_for_dot_dot_id FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2523 (class 2606 OID 290471)
-- Dependencies: 1728 1749 2257
-- Name: fk_classifier_groups_cg_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifier_groups
    ADD CONSTRAINT fk_classifier_groups_cg_id FOREIGN KEY (classifier_group_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2524 (class 2606 OID 290476)
-- Dependencies: 1729 1728 2192
-- Name: fk_classifiers_classifier_groups; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT fk_classifiers_classifier_groups FOREIGN KEY (classifier_group_id) REFERENCES classifier_groups(classifier_group_id);


--
-- TOC entry 2525 (class 2606 OID 290481)
-- Dependencies: 1749 1729 2257
-- Name: fk_classifiers_data_objects; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT fk_classifiers_data_objects FOREIGN KEY (classifier_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2529 (class 2606 OID 290486)
-- Dependencies: 1790 1730 2372
-- Name: fk_communication_contacts_comm_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT fk_communication_contacts_comm_type FOREIGN KEY (communication_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2530 (class 2606 OID 290491)
-- Dependencies: 1730 1749 2257
-- Name: fk_communication_contacts_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT fk_communication_contacts_do_id FOREIGN KEY (communication_contact_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2536 (class 2606 OID 290496)
-- Dependencies: 1731 2372 1790
-- Name: fk_complex_product_items_algorithm; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT fk_complex_product_items_algorithm FOREIGN KEY (applied_algorithm_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2537 (class 2606 OID 290501)
-- Dependencies: 1731 2205 1732
-- Name: fk_complex_product_items_cp; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT fk_complex_product_items_cp FOREIGN KEY (complex_product_id) REFERENCES complex_products(product_id);


--
-- TOC entry 2538 (class 2606 OID 290506)
-- Dependencies: 1780 2343 1731
-- Name: fk_complex_product_items_products; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_product_items
    ADD CONSTRAINT fk_complex_product_items_products FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2539 (class 2606 OID 290511)
-- Dependencies: 1732 2343 1780
-- Name: fk_complex_products_products; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_products
    ADD CONSTRAINT fk_complex_products_products FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2543 (class 2606 OID 290516)
-- Dependencies: 1733 2257 1749
-- Name: fk_contact_persons_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT fk_contact_persons_do_id FOREIGN KEY (contact_person_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2544 (class 2606 OID 290521)
-- Dependencies: 1771 1733 2315
-- Name: fk_contact_persons_position_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contact_persons
    ADD CONSTRAINT fk_contact_persons_position_type FOREIGN KEY (position_type_id) REFERENCES position_types(position_type_id);


--
-- TOC entry 2630 (class 2606 OID 290526)
-- Dependencies: 1710 1754 2137
-- Name: fk_creator_branch; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_creator_branch FOREIGN KEY (creator_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2631 (class 2606 OID 290531)
-- Dependencies: 1754 1767 2305
-- Name: fk_creator_organization; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_creator_organization FOREIGN KEY (creator_organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2547 (class 2606 OID 290536)
-- Dependencies: 1790 1736 2372
-- Name: fk_currency_exchange_rates_from_currency; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY currency_exchange_rates
    ADD CONSTRAINT fk_currency_exchange_rates_from_currency FOREIGN KEY (from_currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2548 (class 2606 OID 290541)
-- Dependencies: 1790 1736 2372
-- Name: fk_currency_exchange_rates_to_currency; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY currency_exchange_rates
    ADD CONSTRAINT fk_currency_exchange_rates_to_currency FOREIGN KEY (to_currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2557 (class 2606 OID 290546)
-- Dependencies: 2223 1738 1739
-- Name: fk_customer_discount_items_by_categories; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items_by_categories
    ADD CONSTRAINT fk_customer_discount_items_by_categories FOREIGN KEY (customer_discount_item_id) REFERENCES customer_discount_items(customer_discount_item_id);


--
-- TOC entry 2558 (class 2606 OID 290551)
-- Dependencies: 1739 1741 2233
-- Name: fk_customer_discount_items_by_categories_cd; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items_by_categories
    ADD CONSTRAINT fk_customer_discount_items_by_categories_cd FOREIGN KEY (customer_discount_id) REFERENCES customer_discounts(customer_discount_id);


--
-- TOC entry 2559 (class 2606 OID 290556)
-- Dependencies: 1739 2333 1777
-- Name: fk_customer_discount_items_by_categories_pc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items_by_categories
    ADD CONSTRAINT fk_customer_discount_items_by_categories_pc FOREIGN KEY (category_id) REFERENCES product_categories(product_category_id);


--
-- TOC entry 2560 (class 2606 OID 290561)
-- Dependencies: 1740 2223 1738
-- Name: fk_customer_discount_items_by_products; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items_by_products
    ADD CONSTRAINT fk_customer_discount_items_by_products FOREIGN KEY (customer_discount_item_id) REFERENCES customer_discount_items(customer_discount_item_id);


--
-- TOC entry 2561 (class 2606 OID 290566)
-- Dependencies: 1741 2233 1740
-- Name: fk_customer_discount_items_by_products_cd; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items_by_products
    ADD CONSTRAINT fk_customer_discount_items_by_products_cd FOREIGN KEY (customer_discount_id) REFERENCES customer_discounts(customer_discount_id);


--
-- TOC entry 2563 (class 2606 OID 291786)
-- Dependencies: 1738 2223 1740
-- Name: fk_customer_discount_items_by_products_cdi; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items_by_products
    ADD CONSTRAINT fk_customer_discount_items_by_products_cdi FOREIGN KEY (customer_discount_item_id) REFERENCES customer_discount_items(customer_discount_item_id);


--
-- TOC entry 2564 (class 2606 OID 291791)
-- Dependencies: 2343 1740 1780
-- Name: fk_customer_discount_items_by_products_p; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items_by_products
    ADD CONSTRAINT fk_customer_discount_items_by_products_p FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2562 (class 2606 OID 290571)
-- Dependencies: 2343 1740 1780
-- Name: fk_customer_discount_items_by_products_products; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items_by_products
    ADD CONSTRAINT fk_customer_discount_items_by_products_products FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2553 (class 2606 OID 290576)
-- Dependencies: 1738 2257 1749
-- Name: fk_customer_discount_items_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discount_items
    ADD CONSTRAINT fk_customer_discount_items_do FOREIGN KEY (customer_discount_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2567 (class 2606 OID 290581)
-- Dependencies: 1741 1749 2257
-- Name: fk_customer_discounts_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discounts
    ADD CONSTRAINT fk_customer_discounts_do FOREIGN KEY (customer_discount_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2568 (class 2606 OID 290586)
-- Dependencies: 1767 1741 2305
-- Name: fk_customer_discounts_organization; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_discounts
    ADD CONSTRAINT fk_customer_discounts_organization FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2569 (class 2606 OID 291756)
-- Dependencies: 2239 1743 1742
-- Name: fk_customer_payment_match_cp; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_payment_match
    ADD CONSTRAINT fk_customer_payment_match_cp FOREIGN KEY (customer_payment_id) REFERENCES customer_payments(payment_id);


--
-- TOC entry 2570 (class 2606 OID 291761)
-- Dependencies: 2378 1742 1793
-- Name: fk_customer_payment_match_invoice; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_payment_match
    ADD CONSTRAINT fk_customer_payment_match_invoice FOREIGN KEY (invoice_id) REFERENCES sales_invoices(invoice_id);


--
-- TOC entry 2580 (class 2606 OID 290591)
-- Dependencies: 2257 1744 1749
-- Name: fk_data_object_details_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_details
    ADD CONSTRAINT fk_data_object_details_do_id FOREIGN KEY (data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2582 (class 2606 OID 290596)
-- Dependencies: 1749 1745 2257
-- Name: fk_data_object_links_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_links
    ADD CONSTRAINT fk_data_object_links_do_id FOREIGN KEY (data_object_link_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2583 (class 2606 OID 290601)
-- Dependencies: 2257 1745 1749
-- Name: fk_data_object_links_linked_object; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_links
    ADD CONSTRAINT fk_data_object_links_linked_object FOREIGN KEY (linked_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2587 (class 2606 OID 290606)
-- Dependencies: 1749 1746 2257
-- Name: fk_data_object_permissions_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_permissions
    ADD CONSTRAINT fk_data_object_permissions_do FOREIGN KEY (data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2588 (class 2606 OID 290611)
-- Dependencies: 1746 2305 1767
-- Name: fk_data_object_permissions_organizations; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_permissions
    ADD CONSTRAINT fk_data_object_permissions_organizations FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2589 (class 2606 OID 290616)
-- Dependencies: 2372 1746 1790
-- Name: fk_data_object_permissions_permissions; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_permissions
    ADD CONSTRAINT fk_data_object_permissions_permissions FOREIGN KEY (permission_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2590 (class 2606 OID 290621)
-- Dependencies: 1746 1790 2372
-- Name: fk_data_object_permissions_urt; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_permissions
    ADD CONSTRAINT fk_data_object_permissions_urt FOREIGN KEY (user_right_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2591 (class 2606 OID 290626)
-- Dependencies: 1747 2253 1748
-- Name: fk_data_object_type_permissions_dot; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_type_permissions
    ADD CONSTRAINT fk_data_object_type_permissions_dot FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2592 (class 2606 OID 290631)
-- Dependencies: 2305 1747 1767
-- Name: fk_data_object_type_permissions_organizations; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_type_permissions
    ADD CONSTRAINT fk_data_object_type_permissions_organizations FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2593 (class 2606 OID 290636)
-- Dependencies: 2372 1790 1747
-- Name: fk_data_object_type_permissions_permissions; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_type_permissions
    ADD CONSTRAINT fk_data_object_type_permissions_permissions FOREIGN KEY (permission_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2594 (class 2606 OID 290641)
-- Dependencies: 1747 1790 2372
-- Name: fk_data_object_type_permissions_urt; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_type_permissions
    ADD CONSTRAINT fk_data_object_type_permissions_urt FOREIGN KEY (user_right_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2602 (class 2606 OID 290646)
-- Dependencies: 1748 1749 2253
-- Name: fk_data_objects_do_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk_data_objects_do_type FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2603 (class 2606 OID 290651)
-- Dependencies: 1749 1749 2257
-- Name: fk_data_objects_linked_data_object_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk_data_objects_linked_data_object_id FOREIGN KEY (linked_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2604 (class 2606 OID 290656)
-- Dependencies: 1749 1749 2257
-- Name: fk_data_objects_parent_data_object_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_objects
    ADD CONSTRAINT fk_data_objects_parent_data_object_id FOREIGN KEY (parent_data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2606 (class 2606 OID 290661)
-- Dependencies: 1749 1750 2257
-- Name: fk_db_properties_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY db_properties
    ADD CONSTRAINT fk_db_properties_do FOREIGN KEY (related_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2608 (class 2606 OID 290666)
-- Dependencies: 1780 1752 2343
-- Name: fk_delivery_certifiate_items_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fk_delivery_certifiate_items_product FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2607 (class 2606 OID 290671)
-- Dependencies: 2273 1754 1751
-- Name: fk_delivery_certificate; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_assignments
    ADD CONSTRAINT fk_delivery_certificate FOREIGN KEY (delivery_certificate_id) REFERENCES delivery_certificates(delivery_certificate_id);


--
-- TOC entry 2609 (class 2606 OID 290676)
-- Dependencies: 1752 2273 1754
-- Name: fk_delivery_certificate_items_delivery_cert; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fk_delivery_certificate_items_delivery_cert FOREIGN KEY (parent_id) REFERENCES delivery_certificates(delivery_certificate_id);


--
-- TOC entry 2610 (class 2606 OID 290681)
-- Dependencies: 1749 1752 2257
-- Name: fk_delivery_certificate_items_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fk_delivery_certificate_items_do_id FOREIGN KEY (certificate_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2611 (class 2606 OID 290686)
-- Dependencies: 2372 1752 1790
-- Name: fk_delivery_certificate_items_measure_unit; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fk_delivery_certificate_items_measure_unit FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2614 (class 2606 OID 290691)
-- Dependencies: 1753 2263 1752
-- Name: fk_delivery_certificate_serial_numbers_dci; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_serial_numbers
    ADD CONSTRAINT fk_delivery_certificate_serial_numbers_dci FOREIGN KEY (certificate_item_id) REFERENCES delivery_certificate_items(certificate_item_id);


--
-- TOC entry 2632 (class 2606 OID 290696)
-- Dependencies: 1754 2257 1749
-- Name: fk_delivery_certificates_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_delivery_certificates_do_id FOREIGN KEY (delivery_certificate_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2633 (class 2606 OID 290701)
-- Dependencies: 2372 1790 1754
-- Name: fk_delivery_certificates_reason; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_delivery_certificates_reason FOREIGN KEY (delivery_cert_reason_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2634 (class 2606 OID 290706)
-- Dependencies: 1754 1720 2169
-- Name: fk_delivery_certificates_recipient; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_delivery_certificates_recipient FOREIGN KEY (recipient_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2635 (class 2606 OID 290711)
-- Dependencies: 1754 1790 2372
-- Name: fk_delivery_certificates_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_delivery_certificates_type FOREIGN KEY (delivery_cert_method_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2636 (class 2606 OID 290716)
-- Dependencies: 1754 1809 2429
-- Name: fk_delivery_certificates_warehouse; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_delivery_certificates_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2483 (class 2606 OID 290721)
-- Dependencies: 1719 2257 1749
-- Name: fk_document_entities_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_documents
    ADD CONSTRAINT fk_document_entities_do FOREIGN KEY (document_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2484 (class 2606 OID 290726)
-- Dependencies: 2372 1719 1790
-- Name: fk_document_entities_status_resource_bundle; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_documents
    ADD CONSTRAINT fk_document_entities_status_resource_bundle FOREIGN KEY (document_status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2485 (class 2606 OID 290731)
-- Dependencies: 1719 2372 1790
-- Name: fk_document_entities_type_resource_bundle; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_documents
    ADD CONSTRAINT fk_document_entities_type_resource_bundle FOREIGN KEY (document_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2642 (class 2606 OID 290736)
-- Dependencies: 1755 1749 2257
-- Name: fk_entity_sequences_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY entity_sequences
    ADD CONSTRAINT fk_entity_sequences_do FOREIGN KEY (entity_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2643 (class 2606 OID 290741)
-- Dependencies: 1755 2253 1748
-- Name: fk_entity_sequences_dot; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY entity_sequences
    ADD CONSTRAINT fk_entity_sequences_dot FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2644 (class 2606 OID 290746)
-- Dependencies: 1767 2305 1757
-- Name: fk_expressions_organizations; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY expressions
    ADD CONSTRAINT fk_expressions_organizations FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2637 (class 2606 OID 290751)
-- Dependencies: 1754 1710 2137
-- Name: fk_forwarder_address_constraint; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_forwarder_address_constraint FOREIGN KEY (forwarder_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2646 (class 2606 OID 290756)
-- Dependencies: 1758 2263 1752
-- Name: fk_goods_receipt_dc_items_delivery_certificate_items; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_dc_items
    ADD CONSTRAINT fk_goods_receipt_dc_items_delivery_certificate_items FOREIGN KEY (delivery_certificate_item_id) REFERENCES delivery_certificate_items(certificate_item_id);


--
-- TOC entry 2647 (class 2606 OID 290761)
-- Dependencies: 1758 2287 1759
-- Name: fk_goods_receipt_dc_items_gri; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_dc_items
    ADD CONSTRAINT fk_goods_receipt_dc_items_gri FOREIGN KEY (receipt_item_id) REFERENCES goods_receipt_items(receipt_item_id);


--
-- TOC entry 2654 (class 2606 OID 290766)
-- Dependencies: 1749 2257 1759
-- Name: fk_goods_receipt_items_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_items
    ADD CONSTRAINT fk_goods_receipt_items_do FOREIGN KEY (receipt_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2655 (class 2606 OID 290771)
-- Dependencies: 1759 1761 2291
-- Name: fk_goods_receipt_items_goods_receipts; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_items
    ADD CONSTRAINT fk_goods_receipt_items_goods_receipts FOREIGN KEY (goods_receipt_id) REFERENCES goods_receipts(goods_receipt_id);


--
-- TOC entry 2656 (class 2606 OID 290776)
-- Dependencies: 2372 1759 1790
-- Name: fk_goods_receipt_items_measure_unit_rb; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_items
    ADD CONSTRAINT fk_goods_receipt_items_measure_unit_rb FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2657 (class 2606 OID 290781)
-- Dependencies: 2343 1759 1780
-- Name: fk_goods_receipt_items_products; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_items
    ADD CONSTRAINT fk_goods_receipt_items_products FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2658 (class 2606 OID 290786)
-- Dependencies: 1759 2287 1760
-- Name: fk_goods_receipt_pi_items_gri; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_pi_items
    ADD CONSTRAINT fk_goods_receipt_pi_items_gri FOREIGN KEY (receipt_item_id) REFERENCES goods_receipt_items(receipt_item_id);


--
-- TOC entry 2659 (class 2606 OID 290791)
-- Dependencies: 1781 2349 1760
-- Name: fk_goods_receipt_pi_items_poi; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_pi_items
    ADD CONSTRAINT fk_goods_receipt_pi_items_poi FOREIGN KEY (invoice_item_id) REFERENCES purchase_invoice_items(invoice_item_id);


--
-- TOC entry 2671 (class 2606 OID 290796)
-- Dependencies: 1761 1710 2137
-- Name: fk_goods_receipts_consignee_branch_addresses; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipts
    ADD CONSTRAINT fk_goods_receipts_consignee_branch_addresses FOREIGN KEY (consignee_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2672 (class 2606 OID 290801)
-- Dependencies: 2313 1761 1770
-- Name: fk_goods_receipts_consignee_contact_persons; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipts
    ADD CONSTRAINT fk_goods_receipts_consignee_contact_persons FOREIGN KEY (storekeeper_id) REFERENCES persons(partner_id);


--
-- TOC entry 2673 (class 2606 OID 290806)
-- Dependencies: 2305 1761 1767
-- Name: fk_goods_receipts_consignee_organizations; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipts
    ADD CONSTRAINT fk_goods_receipts_consignee_organizations FOREIGN KEY (consignee_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2674 (class 2606 OID 290811)
-- Dependencies: 1749 2257 1761
-- Name: fk_goods_receipts_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipts
    ADD CONSTRAINT fk_goods_receipts_do FOREIGN KEY (goods_receipt_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2675 (class 2606 OID 290816)
-- Dependencies: 1790 2372 1761
-- Name: fk_goods_receipts_doc_type_resource_bundle; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipts
    ADD CONSTRAINT fk_goods_receipts_doc_type_resource_bundle FOREIGN KEY (related_document_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2676 (class 2606 OID 290821)
-- Dependencies: 1761 2372 1790
-- Name: fk_goods_receipts_status_resource_bundle; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipts
    ADD CONSTRAINT fk_goods_receipts_status_resource_bundle FOREIGN KEY (receipt_status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2677 (class 2606 OID 290826)
-- Dependencies: 2169 1761 1720
-- Name: fk_goods_receipts_supplier_b_partners; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipts
    ADD CONSTRAINT fk_goods_receipts_supplier_b_partners FOREIGN KEY (supplier_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2678 (class 2606 OID 290831)
-- Dependencies: 1710 1761 2137
-- Name: fk_goods_receipts_supplier_branch_addresses; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipts
    ADD CONSTRAINT fk_goods_receipts_supplier_branch_addresses FOREIGN KEY (supplier_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2679 (class 2606 OID 290836)
-- Dependencies: 2313 1770 1761
-- Name: fk_goods_receipts_supplier_contact_persons; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipts
    ADD CONSTRAINT fk_goods_receipts_supplier_contact_persons FOREIGN KEY (supplier_contact_id) REFERENCES persons(partner_id);


--
-- TOC entry 2680 (class 2606 OID 290841)
-- Dependencies: 2176 1762 1722
-- Name: fk_job_titles_business_units; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY job_titles
    ADD CONSTRAINT fk_job_titles_business_units FOREIGN KEY (business_unit_id) REFERENCES business_units(business_unit_id);


--
-- TOC entry 2681 (class 2606 OID 290846)
-- Dependencies: 2257 1762 1749
-- Name: fk_job_titles_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY job_titles
    ADD CONSTRAINT fk_job_titles_do FOREIGN KEY (job_title_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2682 (class 2606 OID 290851)
-- Dependencies: 1762 1790 2372
-- Name: fk_job_titles_functional_hierarchy; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY job_titles
    ADD CONSTRAINT fk_job_titles_functional_hierarchy FOREIGN KEY (functional_hierarchy_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2683 (class 2606 OID 290856)
-- Dependencies: 2380 1794 1762
-- Name: fk_job_titles_security_roles; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY job_titles
    ADD CONSTRAINT fk_job_titles_security_roles FOREIGN KEY (security_role_id) REFERENCES security_roles(security_role_id);


--
-- TOC entry 2694 (class 2606 OID 290861)
-- Dependencies: 1766 1790 2372
-- Name: fk_organization_configurations_currency; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organization_configurations
    ADD CONSTRAINT fk_organization_configurations_currency FOREIGN KEY (default_currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2695 (class 2606 OID 290866)
-- Dependencies: 1767 2305 1766
-- Name: fk_organization_configurations_org; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organization_configurations
    ADD CONSTRAINT fk_organization_configurations_org FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2706 (class 2606 OID 290871)
-- Dependencies: 1768 2257 1749
-- Name: fk_passports_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT fk_passports_do_id FOREIGN KEY (passport_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2707 (class 2606 OID 290876)
-- Dependencies: 1768 1710 2137
-- Name: fk_passports_issuer_branch; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT fk_passports_issuer_branch FOREIGN KEY (issuer_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2708 (class 2606 OID 290881)
-- Dependencies: 2372 1790 1768
-- Name: fk_passports_pass_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT fk_passports_pass_type FOREIGN KEY (passport_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2716 (class 2606 OID 291776)
-- Dependencies: 1770 2184 1725
-- Name: fk_persons_birth_place_city; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT fk_persons_birth_place_city FOREIGN KEY (birth_place_city_id) REFERENCES cities(city_id);


--
-- TOC entry 2714 (class 2606 OID 291766)
-- Dependencies: 1734 2211 1770
-- Name: fk_persons_birth_place_country; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT fk_persons_birth_place_country FOREIGN KEY (birth_place_country_id) REFERENCES countries(country_id);


--
-- TOC entry 2715 (class 2606 OID 291771)
-- Dependencies: 1790 2372 1770
-- Name: fk_persons_gender; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT fk_persons_gender FOREIGN KEY (gender_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2717 (class 2606 OID 291781)
-- Dependencies: 1770 2169 1720
-- Name: fk_persons_partner; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persons
    ADD CONSTRAINT fk_persons_partner FOREIGN KEY (partner_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2718 (class 2606 OID 290886)
-- Dependencies: 1749 1771 2257
-- Name: fk_position_types_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY position_types
    ADD CONSTRAINT fk_position_types_do_id FOREIGN KEY (position_type_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2725 (class 2606 OID 290891)
-- Dependencies: 1767 2305 1774
-- Name: fk_privilege_categories; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privilege_categories
    ADD CONSTRAINT fk_privilege_categories FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2726 (class 2606 OID 290896)
-- Dependencies: 2257 1749 1774
-- Name: fk_privilege_categories_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privilege_categories
    ADD CONSTRAINT fk_privilege_categories_do FOREIGN KEY (privilege_category_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2727 (class 2606 OID 290901)
-- Dependencies: 1774 2372 1790
-- Name: fk_privilege_categories_privilege_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privilege_categories
    ADD CONSTRAINT fk_privilege_categories_privilege_type FOREIGN KEY (privilege_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2728 (class 2606 OID 290906)
-- Dependencies: 1790 2372 1775
-- Name: fk_privilege_roles_access_level; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privilege_roles
    ADD CONSTRAINT fk_privilege_roles_access_level FOREIGN KEY (access_level_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2729 (class 2606 OID 290911)
-- Dependencies: 2372 1790 1775
-- Name: fk_privilege_roles_access_rights; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privilege_roles
    ADD CONSTRAINT fk_privilege_roles_access_rights FOREIGN KEY (access_right_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2730 (class 2606 OID 290916)
-- Dependencies: 2257 1749 1775
-- Name: fk_privilege_roles_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privilege_roles
    ADD CONSTRAINT fk_privilege_roles_do FOREIGN KEY (privilege_role_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2731 (class 2606 OID 290921)
-- Dependencies: 1776 2329 1775
-- Name: fk_privilege_roles_privileges; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privilege_roles
    ADD CONSTRAINT fk_privilege_roles_privileges FOREIGN KEY (privilege_id) REFERENCES privileges(privilege_id);


--
-- TOC entry 2732 (class 2606 OID 290926)
-- Dependencies: 1749 1776 2257
-- Name: fk_privileges_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privileges
    ADD CONSTRAINT fk_privileges_do FOREIGN KEY (privilege_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2733 (class 2606 OID 290931)
-- Dependencies: 1776 1749 2257
-- Name: fk_privileges_entity; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privileges
    ADD CONSTRAINT fk_privileges_entity FOREIGN KEY (data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2734 (class 2606 OID 290936)
-- Dependencies: 1776 1748 2253
-- Name: fk_privileges_entity_types; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privileges
    ADD CONSTRAINT fk_privileges_entity_types FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2735 (class 2606 OID 290941)
-- Dependencies: 1790 2372 1776
-- Name: fk_privileges_permission_categories; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privileges
    ADD CONSTRAINT fk_privileges_permission_categories FOREIGN KEY (permission_category_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2736 (class 2606 OID 290946)
-- Dependencies: 1776 2321 1774
-- Name: fk_privileges_privilege_categories; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privileges
    ADD CONSTRAINT fk_privileges_privilege_categories FOREIGN KEY (privilege_category_id) REFERENCES privilege_categories(privilege_category_id);


--
-- TOC entry 2737 (class 2606 OID 290951)
-- Dependencies: 2380 1794 1776
-- Name: fk_privileges_security_roles; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privileges
    ADD CONSTRAINT fk_privileges_security_roles FOREIGN KEY (security_role_id) REFERENCES security_roles(security_role_id);


--
-- TOC entry 2738 (class 2606 OID 290956)
-- Dependencies: 1790 2372 1776
-- Name: fk_privileges_special_permissions; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privileges
    ADD CONSTRAINT fk_privileges_special_permissions FOREIGN KEY (special_permission_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2741 (class 2606 OID 290961)
-- Dependencies: 1749 1777 2257
-- Name: fk_product_categories_product_category_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT fk_product_categories_product_category_id FOREIGN KEY (product_category_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2862 (class 2606 OID 290966)
-- Dependencies: 1777 1796 2333
-- Name: fk_product_category_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_product_category_id FOREIGN KEY (category_id) REFERENCES product_categories(product_category_id);


--
-- TOC entry 2742 (class 2606 OID 290971)
-- Dependencies: 2305 1778 1767
-- Name: fk_product_percent_values_organization; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_percent_values
    ADD CONSTRAINT fk_product_percent_values_organization FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2747 (class 2606 OID 290976)
-- Dependencies: 1779 1790 2372
-- Name: fk_product_suppliers_currency; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk_product_suppliers_currency FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2748 (class 2606 OID 290981)
-- Dependencies: 2386 1779 1796
-- Name: fk_product_suppliers_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk_product_suppliers_product FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2749 (class 2606 OID 290986)
-- Dependencies: 1790 2372 1779
-- Name: fk_product_suppliers_resources; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk_product_suppliers_resources FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2750 (class 2606 OID 290991)
-- Dependencies: 1720 1779 2169
-- Name: fk_product_suppliers_supplier; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_suppliers
    ADD CONSTRAINT fk_product_suppliers_supplier FOREIGN KEY (supplier_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2751 (class 2606 OID 290996)
-- Dependencies: 1780 2257 1749
-- Name: fk_products1_product_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_products1_product_id FOREIGN KEY (product_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2863 (class 2606 OID 291001)
-- Dependencies: 1796 1790 2372
-- Name: fk_products_color_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_products_color_id FOREIGN KEY (product_color_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2752 (class 2606 OID 291006)
-- Dependencies: 1780 2372 1790
-- Name: fk_products_currency_resource; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_products_currency_resource FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2864 (class 2606 OID 291011)
-- Dependencies: 1790 2372 1796
-- Name: fk_products_dimension_unit_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_products_dimension_unit_id FOREIGN KEY (dimension_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2753 (class 2606 OID 291016)
-- Dependencies: 2372 1780 1790
-- Name: fk_products_measure_unit_resource; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_products_measure_unit_resource FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2865 (class 2606 OID 291021)
-- Dependencies: 2372 1796 1790
-- Name: fk_products_weight_unit_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_products_weight_unit_id FOREIGN KEY (weight_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2760 (class 2606 OID 291026)
-- Dependencies: 2257 1781 1749
-- Name: fk_purchase_invoice_items_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT fk_purchase_invoice_items_do FOREIGN KEY (invoice_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2761 (class 2606 OID 291031)
-- Dependencies: 1782 1781 2351
-- Name: fk_purchase_invoice_items_goods_receipts; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT fk_purchase_invoice_items_goods_receipts FOREIGN KEY (invoice_id) REFERENCES purchase_invoices(invoice_id);


--
-- TOC entry 2762 (class 2606 OID 291036)
-- Dependencies: 2372 1781 1790
-- Name: fk_purchase_invoice_items_measure_unit_rb; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT fk_purchase_invoice_items_measure_unit_rb FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2763 (class 2606 OID 291041)
-- Dependencies: 1763 1781 2297
-- Name: fk_purchase_invoice_items_order_confirmations; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT fk_purchase_invoice_items_order_confirmations FOREIGN KEY (order_confirmation_item_id) REFERENCES order_confirmation_items(confirmation_item_id);


--
-- TOC entry 2764 (class 2606 OID 291046)
-- Dependencies: 1783 1781 2353
-- Name: fk_purchase_invoice_items_poi; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT fk_purchase_invoice_items_poi FOREIGN KEY (purchase_order_item_id) REFERENCES purchase_order_items(order_item_id);


--
-- TOC entry 2765 (class 2606 OID 291051)
-- Dependencies: 1780 1781 2343
-- Name: fk_purchase_invoice_items_products; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoice_items
    ADD CONSTRAINT fk_purchase_invoice_items_products FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2766 (class 2606 OID 291056)
-- Dependencies: 1716 1782 2159
-- Name: fk_purchase_invoices_bank_details; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoices
    ADD CONSTRAINT fk_purchase_invoices_bank_details FOREIGN KEY (bank_detail_id) REFERENCES bank_details(bank_detail_id);


--
-- TOC entry 2767 (class 2606 OID 291061)
-- Dependencies: 1719 1782 2165
-- Name: fk_purchase_invoices_business_documents; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoices
    ADD CONSTRAINT fk_purchase_invoices_business_documents FOREIGN KEY (invoice_id) REFERENCES business_documents(document_id);


--
-- TOC entry 2768 (class 2606 OID 291066)
-- Dependencies: 1790 1782 2372
-- Name: fk_purchase_invoices_currency; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoices
    ADD CONSTRAINT fk_purchase_invoices_currency FOREIGN KEY (document_currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2769 (class 2606 OID 291071)
-- Dependencies: 1790 1782 2372
-- Name: fk_purchase_invoices_delivery_resource_bundle; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoices
    ADD CONSTRAINT fk_purchase_invoices_delivery_resource_bundle FOREIGN KEY (delivery_terms_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2770 (class 2606 OID 291076)
-- Dependencies: 1790 1782 2372
-- Name: fk_purchase_invoices_payment_resource_bundle; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoices
    ADD CONSTRAINT fk_purchase_invoices_payment_resource_bundle FOREIGN KEY (payment_terms_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2771 (class 2606 OID 291081)
-- Dependencies: 2169 1782 1720
-- Name: fk_purchase_invoices_supplier_b_partners; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoices
    ADD CONSTRAINT fk_purchase_invoices_supplier_b_partners FOREIGN KEY (supplier_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2772 (class 2606 OID 291086)
-- Dependencies: 1782 1710 2137
-- Name: fk_purchase_invoices_supplier_branch_addresses; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoices
    ADD CONSTRAINT fk_purchase_invoices_supplier_branch_addresses FOREIGN KEY (supplier_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2773 (class 2606 OID 291091)
-- Dependencies: 2207 1782 1733
-- Name: fk_purchase_invoices_supplier_contact_persons; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_invoices
    ADD CONSTRAINT fk_purchase_invoices_supplier_contact_persons FOREIGN KEY (supplier_contact_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2774 (class 2606 OID 291096)
-- Dependencies: 1749 1783 2257
-- Name: fk_purchase_order_items_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fk_purchase_order_items_do_id FOREIGN KEY (order_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2775 (class 2606 OID 291101)
-- Dependencies: 1790 1783 2372
-- Name: fk_purchase_order_items_measure_unit; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fk_purchase_order_items_measure_unit FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2776 (class 2606 OID 291106)
-- Dependencies: 1796 1783 2386
-- Name: fk_purchase_order_items_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fk_purchase_order_items_product FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2791 (class 2606 OID 291111)
-- Dependencies: 1796 1785 2386
-- Name: fk_real_products_simple_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY real_products
    ADD CONSTRAINT fk_real_products_simple_product FOREIGN KEY (simple_product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2792 (class 2606 OID 291116)
-- Dependencies: 1807 1785 2425
-- Name: fk_real_products_virtual_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY real_products
    ADD CONSTRAINT fk_real_products_virtual_product FOREIGN KEY (product_id) REFERENCES virtual_products(product_id);


--
-- TOC entry 2796 (class 2606 OID 291121)
-- Dependencies: 1749 1786 2257
-- Name: fk_receipt_certificate_items_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk_receipt_certificate_items_do_id FOREIGN KEY (certificate_item_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2797 (class 2606 OID 291126)
-- Dependencies: 1790 1786 2372
-- Name: fk_receipt_certificate_items_measure_unit; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk_receipt_certificate_items_measure_unit FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2798 (class 2606 OID 291131)
-- Dependencies: 1796 1786 2386
-- Name: fk_receipt_certificate_items_product; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk_receipt_certificate_items_product FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2799 (class 2606 OID 291136)
-- Dependencies: 1788 1786 2365
-- Name: fk_receipt_certificate_items_receipt_cert; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_items
    ADD CONSTRAINT fk_receipt_certificate_items_receipt_cert FOREIGN KEY (parent_id) REFERENCES receipt_certificates(receipt_certificate_id);


--
-- TOC entry 2801 (class 2606 OID 291141)
-- Dependencies: 2361 1787 1786
-- Name: fk_receipt_certificate_serial_numbers_dci; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificate_serial_numbers
    ADD CONSTRAINT fk_receipt_certificate_serial_numbers_dci FOREIGN KEY (certificate_item_id) REFERENCES receipt_certificate_items(certificate_item_id);


--
-- TOC entry 2802 (class 2606 OID 291146)
-- Dependencies: 1788 1745 2245
-- Name: fk_receipt_certificates_deliverer; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_deliverer FOREIGN KEY (deliverer_id) REFERENCES data_object_links(data_object_link_id);


--
-- TOC entry 2803 (class 2606 OID 291151)
-- Dependencies: 2257 1788 1749
-- Name: fk_receipt_certificates_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_do_id FOREIGN KEY (receipt_certificate_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2804 (class 2606 OID 291156)
-- Dependencies: 1790 1788 2372
-- Name: fk_receipt_certificates_reason; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_reason FOREIGN KEY (receipt_cert_reason_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2805 (class 2606 OID 291161)
-- Dependencies: 1790 1788 2372
-- Name: fk_receipt_certificates_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_type FOREIGN KEY (receipt_cert_method_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2806 (class 2606 OID 291166)
-- Dependencies: 1809 1788 2429
-- Name: fk_receipt_certificates_warehouse; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fk_receipt_certificates_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2638 (class 2606 OID 291171)
-- Dependencies: 1710 1754 2137
-- Name: fk_recipient_address_constraint; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_recipient_address_constraint FOREIGN KEY (recipient_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2639 (class 2606 OID 291176)
-- Dependencies: 1733 1754 2207
-- Name: fk_recipient_contact_person; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificates
    ADD CONSTRAINT fk_recipient_contact_person FOREIGN KEY (recipient_contact_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2816 (class 2606 OID 291181)
-- Dependencies: 1756 1790 2279
-- Name: fk_resource_bundle_enum_class_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY resource_bundle
    ADD CONSTRAINT fk_resource_bundle_enum_class_id FOREIGN KEY (enum_class_id) REFERENCES enum_classes(enum_class_id);


--
-- TOC entry 2859 (class 2606 OID 291186)
-- Dependencies: 1722 1794 2176
-- Name: fk_security_roles_business_units; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY security_roles
    ADD CONSTRAINT fk_security_roles_business_units FOREIGN KEY (business_unit_id) REFERENCES business_units(business_unit_id);


--
-- TOC entry 2860 (class 2606 OID 291191)
-- Dependencies: 1749 1794 2257
-- Name: fk_security_roles_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY security_roles
    ADD CONSTRAINT fk_security_roles_do FOREIGN KEY (security_role_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2861 (class 2606 OID 291196)
-- Dependencies: 1767 1794 2305
-- Name: fk_security_roles_organizations; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY security_roles
    ADD CONSTRAINT fk_security_roles_organizations FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2866 (class 2606 OID 291201)
-- Dependencies: 1796 1778 2337
-- Name: fk_simple_products_customs_duty; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_simple_products_customs_duty FOREIGN KEY (customs_duty_percent_id) REFERENCES product_percent_values(percent_value_id);


--
-- TOC entry 2867 (class 2606 OID 291206)
-- Dependencies: 1796 1778 2337
-- Name: fk_simple_products_discount; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_simple_products_discount FOREIGN KEY (discount_percent_id) REFERENCES product_percent_values(percent_value_id);


--
-- TOC entry 2868 (class 2606 OID 291211)
-- Dependencies: 2337 1796 1778
-- Name: fk_simple_products_excise_duty; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_simple_products_excise_duty FOREIGN KEY (excise_duty_percent_id) REFERENCES product_percent_values(percent_value_id);


--
-- TOC entry 2869 (class 2606 OID 291216)
-- Dependencies: 1780 1796 2343
-- Name: fk_simple_products_products; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_simple_products_products FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2870 (class 2606 OID 291221)
-- Dependencies: 1778 1796 2337
-- Name: fk_simple_products_profit; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_simple_products_profit FOREIGN KEY (profit_percent_id) REFERENCES product_percent_values(percent_value_id);


--
-- TOC entry 2871 (class 2606 OID 291226)
-- Dependencies: 2337 1796 1778
-- Name: fk_simple_products_transport; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fk_simple_products_transport FOREIGN KEY (transport_percent_id) REFERENCES product_percent_values(percent_value_id);


--
-- TOC entry 2890 (class 2606 OID 291231)
-- Dependencies: 1797 1749 2257
-- Name: fk_team_members_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY team_members
    ADD CONSTRAINT fk_team_members_do FOREIGN KEY (team_member_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2891 (class 2606 OID 291236)
-- Dependencies: 2372 1797 1790
-- Name: fk_team_members_status; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY team_members
    ADD CONSTRAINT fk_team_members_status FOREIGN KEY (status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2892 (class 2606 OID 291241)
-- Dependencies: 1798 1797 2392
-- Name: fk_team_members_teams; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY team_members
    ADD CONSTRAINT fk_team_members_teams FOREIGN KEY (team_id) REFERENCES teams(team_id);


--
-- TOC entry 2893 (class 2606 OID 291246)
-- Dependencies: 1801 1797 2404
-- Name: fk_team_members_users; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY team_members
    ADD CONSTRAINT fk_team_members_users FOREIGN KEY (user_organization_id) REFERENCES user_organizations(user_organization_id);


--
-- TOC entry 2894 (class 2606 OID 291251)
-- Dependencies: 1722 1798 2176
-- Name: fk_teams_business_units; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY teams
    ADD CONSTRAINT fk_teams_business_units FOREIGN KEY (business_unit_id) REFERENCES business_units(business_unit_id);


--
-- TOC entry 2895 (class 2606 OID 291256)
-- Dependencies: 2257 1749 1798
-- Name: fk_teams_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY teams
    ADD CONSTRAINT fk_teams_do FOREIGN KEY (team_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2896 (class 2606 OID 291261)
-- Dependencies: 2305 1798 1767
-- Name: fk_teams_organizations; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY teams
    ADD CONSTRAINT fk_teams_organizations FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2897 (class 2606 OID 291266)
-- Dependencies: 1798 2372 1790
-- Name: fk_teams_status; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY teams
    ADD CONSTRAINT fk_teams_status FOREIGN KEY (status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2898 (class 2606 OID 291271)
-- Dependencies: 2257 1799 1749
-- Name: fk_user_group_members_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_group_members
    ADD CONSTRAINT fk_user_group_members_do FOREIGN KEY (user_group_member_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2899 (class 2606 OID 291276)
-- Dependencies: 1801 2404 1799
-- Name: fk_user_group_members_u; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_group_members
    ADD CONSTRAINT fk_user_group_members_u FOREIGN KEY (user_organization_id) REFERENCES user_organizations(user_organization_id);


--
-- TOC entry 2900 (class 2606 OID 291281)
-- Dependencies: 2402 1800 1799
-- Name: fk_user_group_members_ug; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_group_members
    ADD CONSTRAINT fk_user_group_members_ug FOREIGN KEY (user_group_id) REFERENCES user_groups(user_group_id);


--
-- TOC entry 2901 (class 2606 OID 291286)
-- Dependencies: 1749 1800 2257
-- Name: fk_user_groups_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_groups
    ADD CONSTRAINT fk_user_groups_do FOREIGN KEY (user_group_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2903 (class 2606 OID 291291)
-- Dependencies: 1801 2137 1710
-- Name: fk_user_organizations_branch; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_organizations
    ADD CONSTRAINT fk_user_organizations_branch FOREIGN KEY (branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2904 (class 2606 OID 291296)
-- Dependencies: 1722 2176 1801
-- Name: fk_user_organizations_business_unit; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_organizations
    ADD CONSTRAINT fk_user_organizations_business_unit FOREIGN KEY (business_unit_id) REFERENCES business_units(business_unit_id);


--
-- TOC entry 2905 (class 2606 OID 291301)
-- Dependencies: 1749 2257 1801
-- Name: fk_user_organizations_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_organizations
    ADD CONSTRAINT fk_user_organizations_do FOREIGN KEY (user_organization_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2906 (class 2606 OID 291306)
-- Dependencies: 1801 2293 1762
-- Name: fk_user_organizations_job_title; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_organizations
    ADD CONSTRAINT fk_user_organizations_job_title FOREIGN KEY (job_title_id) REFERENCES job_titles(job_title_id);


--
-- TOC entry 2907 (class 2606 OID 291311)
-- Dependencies: 2419 1801 1805
-- Name: fk_user_organizations_manager; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_organizations
    ADD CONSTRAINT fk_user_organizations_manager FOREIGN KEY (manager_id) REFERENCES users(user_id);


--
-- TOC entry 2909 (class 2606 OID 292041)
-- Dependencies: 1805 1801 2419
-- Name: fk_user_organizations_user; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_organizations
    ADD CONSTRAINT fk_user_organizations_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE;


--
-- TOC entry 2910 (class 2606 OID 291316)
-- Dependencies: 2257 1802 1749
-- Name: fk_user_rights_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights
    ADD CONSTRAINT fk_user_rights_do FOREIGN KEY (data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2911 (class 2606 OID 291321)
-- Dependencies: 1802 2253 1748
-- Name: fk_user_rights_dot; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights
    ADD CONSTRAINT fk_user_rights_dot FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2912 (class 2606 OID 291326)
-- Dependencies: 1767 2305 1802
-- Name: fk_user_rights_organizations; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights
    ADD CONSTRAINT fk_user_rights_organizations FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2913 (class 2606 OID 291331)
-- Dependencies: 2372 1802 1790
-- Name: fk_user_rights_permission_categories; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights
    ADD CONSTRAINT fk_user_rights_permission_categories FOREIGN KEY (permission_category_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2914 (class 2606 OID 291336)
-- Dependencies: 1802 1790 2372
-- Name: fk_user_rights_permissions; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights
    ADD CONSTRAINT fk_user_rights_permissions FOREIGN KEY (special_permission_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2915 (class 2606 OID 291341)
-- Dependencies: 1802 1800 2402
-- Name: fk_user_rights_user_groups; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights
    ADD CONSTRAINT fk_user_rights_user_groups FOREIGN KEY (user_group_id) REFERENCES user_groups(user_group_id);


--
-- TOC entry 2916 (class 2606 OID 291346)
-- Dependencies: 1802 1805 2419
-- Name: fk_user_rights_users; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights
    ADD CONSTRAINT fk_user_rights_users FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- TOC entry 2932 (class 2606 OID 291351)
-- Dependencies: 2257 1749 1804
-- Name: fk_user_security_roles; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_security_roles
    ADD CONSTRAINT fk_user_security_roles FOREIGN KEY (user_security_role_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2933 (class 2606 OID 291356)
-- Dependencies: 1804 2380 1794
-- Name: fk_user_security_roles_sr; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_security_roles
    ADD CONSTRAINT fk_user_security_roles_sr FOREIGN KEY (security_role_id) REFERENCES security_roles(security_role_id);


--
-- TOC entry 2934 (class 2606 OID 291361)
-- Dependencies: 1804 1801 2404
-- Name: fk_user_security_roles_users; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_security_roles
    ADD CONSTRAINT fk_user_security_roles_users FOREIGN KEY (user_organization_id) REFERENCES user_organizations(user_organization_id);


--
-- TOC entry 2935 (class 2606 OID 291366)
-- Dependencies: 2419 1805 1805
-- Name: fk_users_creator; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk_users_creator FOREIGN KEY (creator_id) REFERENCES users(user_id);


--
-- TOC entry 2936 (class 2606 OID 291371)
-- Dependencies: 2257 1749 1805
-- Name: fk_users_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk_users_do FOREIGN KEY (user_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2938 (class 2606 OID 291376)
-- Dependencies: 1749 1807 2257
-- Name: fk_virtual_products_do; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY virtual_products
    ADD CONSTRAINT fk_virtual_products_do FOREIGN KEY (product_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2943 (class 2606 OID 291381)
-- Dependencies: 1710 1809 2137
-- Name: fk_warehouses_address_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouses
    ADD CONSTRAINT fk_warehouses_address_id FOREIGN KEY (address_id) REFERENCES addresses(address_id);


--
-- TOC entry 2944 (class 2606 OID 291386)
-- Dependencies: 2257 1749 1809
-- Name: fk_warehouses_do_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY warehouses
    ADD CONSTRAINT fk_warehouses_do_id FOREIGN KEY (warehouse_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2777 (class 2606 OID 291391)
-- Dependencies: 2372 1790 1783
-- Name: fka00989511ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fka00989511ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2778 (class 2606 OID 291396)
-- Dependencies: 2372 1790 1783
-- Name: fka00989513aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fka00989513aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2779 (class 2606 OID 291401)
-- Dependencies: 1796 2386 1783
-- Name: fka0098951a330dfcf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fka0098951a330dfcf FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2780 (class 2606 OID 291406)
-- Dependencies: 2386 1783 1796
-- Name: fka0098951f10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_order_items
    ADD CONSTRAINT fka0098951f10b9721 FOREIGN KEY (product_id) REFERENCES simple_products(product_id);


--
-- TOC entry 2648 (class 2606 OID 291411)
-- Dependencies: 2263 1752 1758
-- Name: fka5191bf0c6b416d1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_dc_items
    ADD CONSTRAINT fka5191bf0c6b416d1 FOREIGN KEY (delivery_certificate_item_id) REFERENCES delivery_certificate_items(certificate_item_id);


--
-- TOC entry 2649 (class 2606 OID 291416)
-- Dependencies: 2287 1759 1758
-- Name: fka5191bf0de886089; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_dc_items
    ADD CONSTRAINT fka5191bf0de886089 FOREIGN KEY (receipt_item_id) REFERENCES goods_receipt_items(receipt_item_id);


--
-- TOC entry 2645 (class 2606 OID 291421)
-- Dependencies: 2305 1757 1767
-- Name: fka76c0db51b04573; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY expressions
    ADD CONSTRAINT fka76c0db51b04573 FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2455 (class 2606 OID 291426)
-- Dependencies: 1790 2372 1714
-- Name: fka7bdcfd22644a070; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT fka7bdcfd22644a070 FOREIGN KEY (algorithm_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2456 (class 2606 OID 291431)
-- Dependencies: 2153 1714 1715
-- Name: fka7bdcfd2a7031f5f; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT fka7bdcfd2a7031f5f FOREIGN KEY (assembling_schema_id) REFERENCES assembling_schemas(product_id);


--
-- TOC entry 2457 (class 2606 OID 291436)
-- Dependencies: 2372 1790 1714
-- Name: fka7bdcfd2bca61a30; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT fka7bdcfd2bca61a30 FOREIGN KEY (data_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2458 (class 2606 OID 291441)
-- Dependencies: 1712 2145 1714
-- Name: fka7bdcfd2dcdf9385; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schema_items
    ADD CONSTRAINT fka7bdcfd2dcdf9385 FOREIGN KEY (message_id) REFERENCES assembling_messages(message_id);


--
-- TOC entry 2612 (class 2606 OID 291446)
-- Dependencies: 1752 2372 1790
-- Name: fka8be2f8d1ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fka8be2f8d1ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2613 (class 2606 OID 291451)
-- Dependencies: 2343 1752 1780
-- Name: fka8be2f8df10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_items
    ADD CONSTRAINT fka8be2f8df10b9721 FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2724 (class 2606 OID 291456)
-- Dependencies: 1773 2372 1790
-- Name: fka97c17ec3aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pricelists
    ADD CONSTRAINT fka97c17ec3aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2549 (class 2606 OID 291461)
-- Dependencies: 2372 1736 1790
-- Name: fkaa34146576597079; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY currency_exchange_rates
    ADD CONSTRAINT fkaa34146576597079 FOREIGN KEY (from_currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2550 (class 2606 OID 291466)
-- Dependencies: 1790 2372 1736
-- Name: fkaa341465d8e0b5ca; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY currency_exchange_rates
    ADD CONSTRAINT fkaa341465d8e0b5ca FOREIGN KEY (to_currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2486 (class 2606 OID 291471)
-- Dependencies: 1710 1719 2137
-- Name: fkab065cf93877be10; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_documents
    ADD CONSTRAINT fkab065cf93877be10 FOREIGN KEY (publisher_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2487 (class 2606 OID 291476)
-- Dependencies: 1719 2257 1749
-- Name: fkab065cf95bc0f521; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_documents
    ADD CONSTRAINT fkab065cf95bc0f521 FOREIGN KEY (document_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2488 (class 2606 OID 291481)
-- Dependencies: 1790 1719 2372
-- Name: fkab065cf95df497e9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_documents
    ADD CONSTRAINT fkab065cf95df497e9 FOREIGN KEY (document_status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2489 (class 2606 OID 291486)
-- Dependencies: 1719 2305 1767
-- Name: fkab065cf993de700a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_documents
    ADD CONSTRAINT fkab065cf993de700a FOREIGN KEY (publisher_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2490 (class 2606 OID 291491)
-- Dependencies: 1719 2372 1790
-- Name: fkab065cf9a97faa1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_documents
    ADD CONSTRAINT fkab065cf9a97faa1 FOREIGN KEY (document_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2491 (class 2606 OID 291496)
-- Dependencies: 1719 2313 1770
-- Name: fkab065cf9c8bfa995; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY business_documents
    ADD CONSTRAINT fkab065cf9c8bfa995 FOREIGN KEY (publisher_officer_id) REFERENCES persons(partner_id);


--
-- TOC entry 2531 (class 2606 OID 291501)
-- Dependencies: 1733 1730 2207
-- Name: fkac65329c8da5f302; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT fkac65329c8da5f302 FOREIGN KEY (contact_person_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2532 (class 2606 OID 291506)
-- Dependencies: 2372 1790 1730
-- Name: fkac65329c90a50e5c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY communication_contacts
    ADD CONSTRAINT fkac65329c90a50e5c FOREIGN KEY (communication_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2513 (class 2606 OID 291511)
-- Dependencies: 2257 1725 1749
-- Name: fkaeedbb496c5e8ad1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cities
    ADD CONSTRAINT fkaeedbb496c5e8ad1 FOREIGN KEY (city_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2514 (class 2606 OID 291516)
-- Dependencies: 2211 1734 1725
-- Name: fkaeedbb49977341c1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cities
    ADD CONSTRAINT fkaeedbb49977341c1 FOREIGN KEY (country_id) REFERENCES countries(country_id);


--
-- TOC entry 2719 (class 2606 OID 291521)
-- Dependencies: 2402 1800 1771
-- Name: fkb21311e312dbbc4a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY position_types
    ADD CONSTRAINT fkb21311e312dbbc4a FOREIGN KEY (user_group_id) REFERENCES user_groups(user_group_id);


--
-- TOC entry 2720 (class 2606 OID 291526)
-- Dependencies: 1771 2315 1771
-- Name: fkb21311e35a80ebb; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY position_types
    ADD CONSTRAINT fkb21311e35a80ebb FOREIGN KEY (parent_position_type_id) REFERENCES position_types(position_type_id);


--
-- TOC entry 2551 (class 2606 OID 291531)
-- Dependencies: 1737 1790 2372
-- Name: fkb2ffbeae3aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY currency_nominal
    ADD CONSTRAINT fkb2ffbeae3aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2615 (class 2606 OID 291536)
-- Dependencies: 1753 2263 1752
-- Name: fkb3b02dd2d961ef9c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY delivery_certificate_serial_numbers
    ADD CONSTRAINT fkb3b02dd2d961ef9c FOREIGN KEY (certificate_item_id) REFERENCES delivery_certificate_items(certificate_item_id);


--
-- TOC entry 2842 (class 2606 OID 291541)
-- Dependencies: 2169 1793 1720
-- Name: fkbbc878b9134fe2b0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b9134fe2b0 FOREIGN KEY (recipient_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2843 (class 2606 OID 291546)
-- Dependencies: 1793 1790 2372
-- Name: fkbbc878b917174fab; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b917174fab FOREIGN KEY (transportation_method_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2844 (class 2606 OID 291551)
-- Dependencies: 2372 1793 1790
-- Name: fkbbc878b91808129a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b91808129a FOREIGN KEY (doc_delivery_method_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2845 (class 2606 OID 291556)
-- Dependencies: 1710 1793 2137
-- Name: fkbbc878b927a66c93; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b927a66c93 FOREIGN KEY (branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2846 (class 2606 OID 291561)
-- Dependencies: 1790 1793 2372
-- Name: fkbbc878b93aa5298e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b93aa5298e FOREIGN KEY (currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2847 (class 2606 OID 291566)
-- Dependencies: 1790 1793 2372
-- Name: fkbbc878b93dc9408c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b93dc9408c FOREIGN KEY (payment_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2848 (class 2606 OID 291571)
-- Dependencies: 1790 1793 2372
-- Name: fkbbc878b946685c7a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b946685c7a FOREIGN KEY (delivery_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2849 (class 2606 OID 291576)
-- Dependencies: 1770 1793 2313
-- Name: fkbbc878b94da6f8bc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b94da6f8bc FOREIGN KEY (creator_id) REFERENCES persons(partner_id);


--
-- TOC entry 2850 (class 2606 OID 291581)
-- Dependencies: 1720 1793 2169
-- Name: fkbbc878b96d20f4c9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b96d20f4c9 FOREIGN KEY (shippingagent_partner_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2851 (class 2606 OID 291586)
-- Dependencies: 1733 1793 2207
-- Name: fkbbc878b97ebcc009; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b97ebcc009 FOREIGN KEY (supplier_contact_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2852 (class 2606 OID 291591)
-- Dependencies: 1790 1793 2372
-- Name: fkbbc878b996e3ba71; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b996e3ba71 FOREIGN KEY (payment_terms_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2853 (class 2606 OID 291596)
-- Dependencies: 1793 1790 2372
-- Name: fkbbc878b99a24d298; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b99a24d298 FOREIGN KEY (deliverystatus_resource_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2854 (class 2606 OID 291601)
-- Dependencies: 1793 1790 2372
-- Name: fkbbc878b99c49320d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b99c49320d FOREIGN KEY (status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2855 (class 2606 OID 291606)
-- Dependencies: 2207 1793 1733
-- Name: fkbbc878b99ff294dc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b99ff294dc FOREIGN KEY (attendee_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2856 (class 2606 OID 291611)
-- Dependencies: 1790 1793 2372
-- Name: fkbbc878b9a94f3ab3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b9a94f3ab3 FOREIGN KEY (invoice_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2857 (class 2606 OID 291616)
-- Dependencies: 1790 1793 2372
-- Name: fkbbc878b9b07d659a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b9b07d659a FOREIGN KEY (vat_condition_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2858 (class 2606 OID 291621)
-- Dependencies: 1770 1793 2313
-- Name: fkbbc878b9fd5b3613; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoices
    ADD CONSTRAINT fkbbc878b9fd5b3613 FOREIGN KEY (sender_id) REFERENCES persons(partner_id);


--
-- TOC entry 2473 (class 2606 OID 291626)
-- Dependencies: 1737 1717 2221
-- Name: fkc102b17c885a78c4; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY banknote_quantity
    ADD CONSTRAINT fkc102b17c885a78c4 FOREIGN KEY (currencynominal_nominal_id) REFERENCES currency_nominal(nominal_id);


--
-- TOC entry 2660 (class 2606 OID 291631)
-- Dependencies: 1781 1760 2349
-- Name: fkc10c99ea219348c0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_pi_items
    ADD CONSTRAINT fkc10c99ea219348c0 FOREIGN KEY (invoice_item_id) REFERENCES purchase_invoice_items(invoice_item_id);


--
-- TOC entry 2661 (class 2606 OID 291636)
-- Dependencies: 1759 1760 2287
-- Name: fkc10c99eade886089; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY goods_receipt_pi_items
    ADD CONSTRAINT fkc10c99eade886089 FOREIGN KEY (receipt_item_id) REFERENCES goods_receipt_items(receipt_item_id);


--
-- TOC entry 2595 (class 2606 OID 291641)
-- Dependencies: 1767 1747 2305
-- Name: fkc1a9b66a51b04573; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_type_permissions
    ADD CONSTRAINT fkc1a9b66a51b04573 FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2596 (class 2606 OID 291646)
-- Dependencies: 1747 2253 1748
-- Name: fkc1a9b66aa44e2131; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_type_permissions
    ADD CONSTRAINT fkc1a9b66aa44e2131 FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2597 (class 2606 OID 291651)
-- Dependencies: 1790 2372 1747
-- Name: fkc1a9b66ab01192e; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_type_permissions
    ADD CONSTRAINT fkc1a9b66ab01192e FOREIGN KEY (user_right_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2598 (class 2606 OID 291656)
-- Dependencies: 1790 2372 1747
-- Name: fkc1a9b66ac2559310; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY data_object_type_permissions
    ADD CONSTRAINT fkc1a9b66ac2559310 FOREIGN KEY (permission_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2781 (class 2606 OID 291661)
-- Dependencies: 1790 2372 1784
-- Name: fkc307e7e31808129a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e31808129a FOREIGN KEY (doc_delivery_method_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2782 (class 2606 OID 291666)
-- Dependencies: 1710 2137 1784
-- Name: fkc307e7e327a66c93; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e327a66c93 FOREIGN KEY (branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2783 (class 2606 OID 291671)
-- Dependencies: 2313 1784 1770
-- Name: fkc307e7e34da6f8bc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e34da6f8bc FOREIGN KEY (creator_id) REFERENCES persons(partner_id);


--
-- TOC entry 2784 (class 2606 OID 291676)
-- Dependencies: 2169 1784 1720
-- Name: fkc307e7e35aa049f4; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e35aa049f4 FOREIGN KEY (supplier_partner_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2785 (class 2606 OID 291681)
-- Dependencies: 1733 1784 2207
-- Name: fkc307e7e37ebcc009; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e37ebcc009 FOREIGN KEY (supplier_contact_id) REFERENCES contact_persons(contact_person_id);


--
-- TOC entry 2786 (class 2606 OID 291686)
-- Dependencies: 1784 1790 2372
-- Name: fkc307e7e39a24d298; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e39a24d298 FOREIGN KEY (deliverystatus_resource_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2787 (class 2606 OID 291691)
-- Dependencies: 1790 2372 1784
-- Name: fkc307e7e39c49320d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e39c49320d FOREIGN KEY (status_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2788 (class 2606 OID 291696)
-- Dependencies: 1784 1770 2313
-- Name: fkc307e7e3fd5b3613; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY purchase_orders
    ADD CONSTRAINT fkc307e7e3fd5b3613 FOREIGN KEY (sender_id) REFERENCES persons(partner_id);


--
-- TOC entry 2872 (class 2606 OID 291701)
-- Dependencies: 2372 1790 1796
-- Name: fkc42bd16427acd874; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd16427acd874 FOREIGN KEY (weight_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2873 (class 2606 OID 291706)
-- Dependencies: 1796 1777 2333
-- Name: fkc42bd1646e2f83d0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd1646e2f83d0 FOREIGN KEY (category_id) REFERENCES product_categories(product_category_id);


--
-- TOC entry 2874 (class 2606 OID 291711)
-- Dependencies: 2169 1720 1796
-- Name: fkc42bd1646e436697; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd1646e436697 FOREIGN KEY (producer_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2875 (class 2606 OID 291716)
-- Dependencies: 1769 2311 1796
-- Name: fkc42bd1647a956e19; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd1647a956e19 FOREIGN KEY (pattern_mask_format_id) REFERENCES pattern_mask_formats(pattern_mask_format_id);


--
-- TOC entry 2876 (class 2606 OID 291721)
-- Dependencies: 1796 2372 1790
-- Name: fkc42bd1648f60524c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd1648f60524c FOREIGN KEY (product_color_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2877 (class 2606 OID 291726)
-- Dependencies: 1796 2372 1790
-- Name: fkc42bd1649d6c3562; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fkc42bd1649d6c3562 FOREIGN KEY (dimension_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2517 (class 2606 OID 291731)
-- Dependencies: 1726 2194 1729
-- Name: fkc436c2e88e8748f3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classified_objects
    ADD CONSTRAINT fkc436c2e88e8748f3 FOREIGN KEY (classifier_id) REFERENCES classifiers(classifier_id);


--
-- TOC entry 2518 (class 2606 OID 291736)
-- Dependencies: 2257 1749 1726
-- Name: fkc436c2e8cf1f1951; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classified_objects
    ADD CONSTRAINT fkc436c2e8cf1f1951 FOREIGN KEY (classified_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2709 (class 2606 OID 291741)
-- Dependencies: 2137 1768 1710
-- Name: fkc84af6a180bd868d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT fkc84af6a180bd868d FOREIGN KEY (issuer_branch_id) REFERENCES addresses(address_id);


--
-- TOC entry 2710 (class 2606 OID 291746)
-- Dependencies: 2372 1768 1790
-- Name: fkc84af6a1ad6ece98; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT fkc84af6a1ad6ece98 FOREIGN KEY (passport_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2711 (class 2606 OID 291751)
-- Dependencies: 1767 2305 1768
-- Name: fkc84af6a1db08a2d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT fkc84af6a1db08a2d FOREIGN KEY (issuer_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2462 (class 2606 OID 291796)
-- Dependencies: 1790 2372 1715
-- Name: fke7efd4c21ac2f55a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT fke7efd4c21ac2f55a FOREIGN KEY (measure_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2463 (class 2606 OID 291801)
-- Dependencies: 1715 1711 2139
-- Name: fke7efd4c26d4edb2f; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT fke7efd4c26d4edb2f FOREIGN KEY (category_id) REFERENCES assembling_categories(assembling_category_id);


--
-- TOC entry 2464 (class 2606 OID 291806)
-- Dependencies: 2425 1807 1715
-- Name: fke7efd4c29f88efd5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY assembling_schemas
    ADD CONSTRAINT fke7efd4c29f88efd5 FOREIGN KEY (product_id) REFERENCES virtual_products(product_id);


--
-- TOC entry 2878 (class 2606 OID 291811)
-- Dependencies: 1790 2372 1796
-- Name: fke810995127acd874; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke810995127acd874 FOREIGN KEY (weight_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2879 (class 2606 OID 291816)
-- Dependencies: 1796 1777 2333
-- Name: fke81099516e2f83d0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke81099516e2f83d0 FOREIGN KEY (category_id) REFERENCES product_categories(product_category_id);


--
-- TOC entry 2880 (class 2606 OID 291821)
-- Dependencies: 1796 1720 2169
-- Name: fke81099516e436697; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke81099516e436697 FOREIGN KEY (producer_id) REFERENCES business_partners(partner_id);


--
-- TOC entry 2881 (class 2606 OID 291826)
-- Dependencies: 1769 2311 1796
-- Name: fke81099517a956e19; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke81099517a956e19 FOREIGN KEY (pattern_mask_format_id) REFERENCES pattern_mask_formats(pattern_mask_format_id);


--
-- TOC entry 2882 (class 2606 OID 291831)
-- Dependencies: 2337 1796 1778
-- Name: fke810995185c08915; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke810995185c08915 FOREIGN KEY (discount_percent_id) REFERENCES product_percent_values(percent_value_id);


--
-- TOC entry 2883 (class 2606 OID 291836)
-- Dependencies: 2372 1796 1790
-- Name: fke81099518f60524c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke81099518f60524c FOREIGN KEY (product_color_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2884 (class 2606 OID 291841)
-- Dependencies: 1778 2337 1796
-- Name: fke810995193879943; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke810995193879943 FOREIGN KEY (customs_duty_percent_id) REFERENCES product_percent_values(percent_value_id);


--
-- TOC entry 2885 (class 2606 OID 291846)
-- Dependencies: 2372 1790 1796
-- Name: fke81099519d6c3562; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke81099519d6c3562 FOREIGN KEY (dimension_unit_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2886 (class 2606 OID 291851)
-- Dependencies: 1796 1778 2337
-- Name: fke8109951b75e188c; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke8109951b75e188c FOREIGN KEY (excise_duty_percent_id) REFERENCES product_percent_values(percent_value_id);


--
-- TOC entry 2887 (class 2606 OID 291856)
-- Dependencies: 1778 1796 2337
-- Name: fke8109951e459462d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke8109951e459462d FOREIGN KEY (transport_percent_id) REFERENCES product_percent_values(percent_value_id);


--
-- TOC entry 2888 (class 2606 OID 291861)
-- Dependencies: 1796 1778 2337
-- Name: fke8109951f05d1032; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke8109951f05d1032 FOREIGN KEY (profit_percent_id) REFERENCES product_percent_values(percent_value_id);


--
-- TOC entry 2889 (class 2606 OID 291866)
-- Dependencies: 1796 1780 2343
-- Name: fke8109951f10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY simple_products
    ADD CONSTRAINT fke8109951f10b9721 FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2807 (class 2606 OID 291871)
-- Dependencies: 2313 1770 1788
-- Name: fke9334463157c075; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke9334463157c075 FOREIGN KEY (forwarder_contact_id) REFERENCES persons(partner_id);


--
-- TOC entry 2808 (class 2606 OID 291876)
-- Dependencies: 2313 1788 1770
-- Name: fke93344634da6f8bc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke93344634da6f8bc FOREIGN KEY (creator_id) REFERENCES persons(partner_id);


--
-- TOC entry 2809 (class 2606 OID 291881)
-- Dependencies: 2313 1770 1788
-- Name: fke93344636faaa615; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke93344636faaa615 FOREIGN KEY (deliverer_contact_id) REFERENCES persons(partner_id);


--
-- TOC entry 2810 (class 2606 OID 291886)
-- Dependencies: 2245 1745 1788
-- Name: fke933446370294164; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke933446370294164 FOREIGN KEY (deliverer_id) REFERENCES data_object_links(data_object_link_id);


--
-- TOC entry 2811 (class 2606 OID 291891)
-- Dependencies: 1788 2429 1809
-- Name: fke93344639f1800e1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke93344639f1800e1 FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id);


--
-- TOC entry 2812 (class 2606 OID 291896)
-- Dependencies: 1788 2372 1790
-- Name: fke9334463d6755f5b; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke9334463d6755f5b FOREIGN KEY (receipt_cert_method_type_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2813 (class 2606 OID 291901)
-- Dependencies: 2305 1767 1788
-- Name: fke9334463f2569c14; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke9334463f2569c14 FOREIGN KEY (forwarder_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2814 (class 2606 OID 291906)
-- Dependencies: 1788 2372 1790
-- Name: fke9334463fe9be307; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY receipt_certificates
    ADD CONSTRAINT fke9334463fe9be307 FOREIGN KEY (receipt_cert_reason_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2521 (class 2606 OID 291911)
-- Dependencies: 2194 1727 1729
-- Name: fkefe6ccf38e8748f3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifier_applied_for_dot
    ADD CONSTRAINT fkefe6ccf38e8748f3 FOREIGN KEY (classifier_id) REFERENCES classifiers(classifier_id);


--
-- TOC entry 2522 (class 2606 OID 291916)
-- Dependencies: 1727 2253 1748
-- Name: fkefe6ccf3a44e2131; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifier_applied_for_dot
    ADD CONSTRAINT fkefe6ccf3a44e2131 FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2919 (class 2606 OID 291921)
-- Dependencies: 1803 1800 2402
-- Name: fkf4b9c8cb12dbbc4a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT fkf4b9c8cb12dbbc4a FOREIGN KEY (user_group_id) REFERENCES user_groups(user_group_id);


--
-- TOC entry 2920 (class 2606 OID 291926)
-- Dependencies: 2372 1790 1803
-- Name: fkf4b9c8cb324c6e0a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT fkf4b9c8cb324c6e0a FOREIGN KEY (special_permission_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2921 (class 2606 OID 291931)
-- Dependencies: 1803 2253 1748
-- Name: fkf4b9c8cba44e2131; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT fkf4b9c8cba44e2131 FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2922 (class 2606 OID 291936)
-- Dependencies: 1805 2419 1803
-- Name: fkf4b9c8cbb4a34773; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT fkf4b9c8cbb4a34773 FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- TOC entry 2923 (class 2606 OID 291941)
-- Dependencies: 1803 1749 2257
-- Name: fkf4b9c8cbd741e28; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT fkf4b9c8cbd741e28 FOREIGN KEY (data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2526 (class 2606 OID 291946)
-- Dependencies: 1729 2192 1728
-- Name: fkf7ea2a728508c4de; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY classifiers
    ADD CONSTRAINT fkf7ea2a728508c4de FOREIGN KEY (classifier_group_id) REFERENCES classifier_groups(classifier_group_id);


--
-- TOC entry 2924 (class 2606 OID 291951)
-- Dependencies: 1800 2402 1803
-- Name: fkfdef48b312dbbc4a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT fkfdef48b312dbbc4a FOREIGN KEY (user_group_id) REFERENCES user_groups(user_group_id);


--
-- TOC entry 2925 (class 2606 OID 291956)
-- Dependencies: 1803 2372 1790
-- Name: fkfdef48b3324c6e0a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT fkfdef48b3324c6e0a FOREIGN KEY (special_permission_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2926 (class 2606 OID 291961)
-- Dependencies: 1748 1803 2253
-- Name: fkfdef48b3a44e2131; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT fkfdef48b3a44e2131 FOREIGN KEY (data_object_type_id) REFERENCES data_object_types(data_object_type_id);


--
-- TOC entry 2927 (class 2606 OID 291966)
-- Dependencies: 1805 1803 2419
-- Name: fkfdef48b3b4a34773; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT fkfdef48b3b4a34773 FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- TOC entry 2928 (class 2606 OID 291971)
-- Dependencies: 1803 2257 1749
-- Name: fkfdef48b3d741e28; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT fkfdef48b3d741e28 FOREIGN KEY (data_object_id) REFERENCES data_objects(data_object_id);


--
-- TOC entry 2818 (class 2606 OID 291976)
-- Dependencies: 2376 1792 1791
-- Name: fkfefcb143e94bedc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sales_invoice_item_link
    ADD CONSTRAINT fkfefcb143e94bedc FOREIGN KEY (invoice_item_id) REFERENCES sales_invoice_items(invoice_item_id);


--
-- TOC entry 2540 (class 2606 OID 291981)
-- Dependencies: 2343 1732 1780
-- Name: fkff77e413f10b9721; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY complex_products
    ADD CONSTRAINT fkff77e413f10b9721 FOREIGN KEY (product_id) REFERENCES products(product_id);


--
-- TOC entry 2703 (class 2606 OID 291986)
-- Dependencies: 2137 1767 1710
-- Name: organizations_administration_address_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT organizations_administration_address_id_fkey FOREIGN KEY (administration_address_id) REFERENCES addresses(address_id) ON DELETE SET NULL;


--
-- TOC entry 2704 (class 2606 OID 291991)
-- Dependencies: 2372 1767 1790
-- Name: organizations_currency_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT organizations_currency_id_fkey FOREIGN KEY (share_capital_currency_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2705 (class 2606 OID 291996)
-- Dependencies: 1767 2137 1710
-- Name: organizations_registration_address_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT organizations_registration_address_id_fkey FOREIGN KEY (registration_address_id) REFERENCES addresses(address_id) ON DELETE SET NULL;


--
-- TOC entry 2712 (class 2606 OID 292001)
-- Dependencies: 1770 2313 1768
-- Name: passports_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY passports
    ADD CONSTRAINT passports_parent_id_fkey FOREIGN KEY (parent_id) REFERENCES persons(partner_id) ON DELETE CASCADE;


--
-- TOC entry 2721 (class 2606 OID 292006)
-- Dependencies: 1771 2315 1771
-- Name: position_types_parent_position_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY position_types
    ADD CONSTRAINT position_types_parent_position_type_id_fkey FOREIGN KEY (parent_position_type_id) REFERENCES position_types(position_type_id);


--
-- TOC entry 2722 (class 2606 OID 292011)
-- Dependencies: 1771 2402 1800
-- Name: position_types_user_group_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY position_types
    ADD CONSTRAINT position_types_user_group_id_fkey FOREIGN KEY (user_group_id) REFERENCES user_groups(user_group_id) ON DELETE SET NULL;


--
-- TOC entry 2902 (class 2606 OID 292016)
-- Dependencies: 1800 2305 1767
-- Name: user_groups_organizations; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_groups
    ADD CONSTRAINT user_groups_organizations FOREIGN KEY (organization_id) REFERENCES organizations(organization_id);


--
-- TOC entry 2929 (class 2606 OID 292021)
-- Dependencies: 1803 2372 1790
-- Name: user_rights_special_permission_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT user_rights_special_permission_id_fkey FOREIGN KEY (special_permission_id) REFERENCES resource_bundle(resource_id);


--
-- TOC entry 2930 (class 2606 OID 292026)
-- Dependencies: 2402 1803 1800
-- Name: user_rights_user_group_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT user_rights_user_group_id_fkey FOREIGN KEY (user_group_id) REFERENCES user_groups(user_group_id) ON DELETE CASCADE;


--
-- TOC entry 2931 (class 2606 OID 292031)
-- Dependencies: 2419 1805 1803
-- Name: user_rights_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_rights_old
    ADD CONSTRAINT user_rights_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE;


--
-- TOC entry 2908 (class 2606 OID 292036)
-- Dependencies: 1801 2305 1767
-- Name: users_organizations_organization_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_organizations
    ADD CONSTRAINT users_organizations_organization_id_fkey FOREIGN KEY (organization_id) REFERENCES organizations(organization_id) ON DELETE CASCADE;


--
-- TOC entry 2937 (class 2606 OID 292046)
-- Dependencies: 1770 2313 1805
-- Name: users_person_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_person_id_fkey FOREIGN KEY (person_id) REFERENCES persons(partner_id);


--
-- TOC entry 3049 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: -
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2009-08-25 13:58:13

--
-- PostgreSQL database dump complete
--

