--
-- PostgreSQL database dump
--

-- Dumped from database version 16.3
-- Dumped by pg_dump version 16.3

-- Started on 2024-05-15 15:41:51

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 215 (class 1259 OID 17137)
-- Name: attachment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.attachment (
    id bigint NOT NULL,
    uid uuid NOT NULL,
    createdby character varying(50) NOT NULL,
    createddate timestamp without time zone NOT NULL,
    lastupdatedby character varying(50),
    lastupdateddate timestamp without time zone,
    descr character varying(255),
    header character varying(255),
    location character varying(255) NOT NULL,
    scndry_id character varying(255) NOT NULL,
    typ character varying(255)
);


ALTER TABLE public.attachment OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 17144)
-- Name: chklst; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.chklst (
    id bigint NOT NULL,
    active boolean NOT NULL,
    createdby character varying(50) NOT NULL,
    createddate timestamp without time zone NOT NULL,
    lastupdatedby character varying(50),
    lastupdateddate timestamp without time zone,
    chk_id uuid NOT NULL,
    data character varying(500) NOT NULL,
    det_desc character varying(500),
    name character varying(250),
    post_processor character varying(255),
    pre_processor character varying(255),
    remark character varying(500) NOT NULL,
    descr character varying(250),
    state integer NOT NULL,
    typ integer,
    version integer NOT NULL,
    companyid bigint
);


ALTER TABLE public.chklst OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 17151)
-- Name: chklst_inst; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.chklst_inst (
    id bigint NOT NULL,
    assoc_to character varying(10),
    assoc_type integer NOT NULL,
    createdby character varying(50) NOT NULL,
    createddate timestamp without time zone NOT NULL,
    lastupdatedby character varying(50),
    lastupdateddate timestamp without time zone,
    chk_inst_id uuid NOT NULL,
    act_end_date timestamp without time zone NOT NULL,
    data character varying(500) NOT NULL,
    chk_end_date timestamp without time zone NOT NULL,
    det_desc character varying(500),
    mas_chk_uid uuid NOT NULL,
    name character varying(255),
    post_processor character varying(255),
    pre_processor character varying(255),
    descr character varying(250),
    chk_st_date timestamp without time zone NOT NULL,
    state integer NOT NULL,
    companyid bigint,
    vesselid bigint
);


ALTER TABLE public.chklst_inst OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 17158)
-- Name: chklst_sec; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.chklst_sec (
    id bigint NOT NULL,
    enabled boolean,
    chk_id bigint,
    sec_id bigint
);


ALTER TABLE public.chklst_sec OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 17163)
-- Name: chklst_sec_inst; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.chklst_sec_inst (
    id bigint NOT NULL,
    enabled boolean,
    chk_id bigint,
    sec_id bigint
);


ALTER TABLE public.chklst_sec_inst OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 17168)
-- Name: cmpny; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cmpny (
    id bigint NOT NULL,
    uuid uuid NOT NULL,
    address1 character varying(255),
    address2 character varying(255),
    createdby character varying(50) NOT NULL,
    createddate timestamp without time zone NOT NULL,
    lastupdatedby character varying(50),
    lastupdateddate timestamp without time zone,
    city character varying(255),
    country character varying(255),
    isactive boolean,
    it_email character varying(255) NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.cmpny OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 17175)
-- Name: comp_rank; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.comp_rank (
    id bigint NOT NULL,
    companyid bigint NOT NULL,
    rank_code character varying(10) NOT NULL
);


ALTER TABLE public.comp_rank OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 17180)
-- Name: comp_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.comp_user (
    id bigint NOT NULL,
    company_id bigint NOT NULL,
    rank_code character varying(10),
    ship_id bigint,
    user_id bigint NOT NULL
);


ALTER TABLE public.comp_user OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 17185)
-- Name: country; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.country (
    code character varying(255) NOT NULL,
    createdby character varying(50) NOT NULL,
    createddate timestamp without time zone NOT NULL,
    lastupdatedby character varying(50),
    lastupdateddate timestamp without time zone,
    default_currency character varying(255),
    default_language character varying(255),
    description character varying(255),
    name character varying(255),
    tax_label character varying(255),
    tax_rate numeric(19,2)
);


ALTER TABLE public.country OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 17192)
-- Name: currency; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.currency (
    id integer NOT NULL,
    code character varying(255),
    customs_exchange_rate numeric(19,2),
    description character varying(255),
    exchange_rate numeric(19,2),
    from_date timestamp without time zone,
    max_limit numeric(19,2),
    min_limit numeric(19,2),
    name character varying(255),
    tenantid integer,
    to_date timestamp without time zone
);


ALTER TABLE public.currency OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 17199)
-- Name: fleet; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fleet (
    id bigint NOT NULL,
    createdby character varying(50) NOT NULL,
    createddate timestamp without time zone NOT NULL,
    lastupdatedby character varying(50),
    lastupdateddate timestamp without time zone,
    name character varying(255) NOT NULL,
    uuid uuid NOT NULL,
    compid bigint NOT NULL,
    userid bigint NOT NULL
);


ALTER TABLE public.fleet OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 17204)
-- Name: fleet_ship; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fleet_ship (
    id bigint NOT NULL,
    fleet_id bigint NOT NULL,
    vessel_id bigint NOT NULL
);


ALTER TABLE public.fleet_ship OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 17209)
-- Name: non_conf; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.non_conf (
    id bigint NOT NULL,
    uuid uuid NOT NULL,
    createdby character varying(50) NOT NULL,
    createddate timestamp without time zone NOT NULL,
    lastupdatedby character varying(50),
    lastupdateddate timestamp without time zone,
    che_inst_id uuid NOT NULL,
    nc_closed_time timestamp without time zone,
    nc_creation_time timestamp without time zone NOT NULL,
    closure_rem character varying(500),
    nc_resp character varying(50) NOT NULL,
    status integer NOT NULL,
    que_inst_id bigint,
    vessel_id bigint
);


ALTER TABLE public.non_conf OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 17216)
-- Name: port; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.port (
    code character varying(255) NOT NULL,
    city character varying(255),
    description character varying(255),
    cntry_id character varying(255) NOT NULL
);


ALTER TABLE public.port OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 17223)
-- Name: priv; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.priv (
    id bigint NOT NULL,
    deflt boolean NOT NULL,
    key_code character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    status boolean NOT NULL
);


ALTER TABLE public.priv OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 17230)
-- Name: question; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.question (
    id bigint NOT NULL,
    active boolean NOT NULL,
    createdby character varying(50) NOT NULL,
    createddate timestamp without time zone NOT NULL,
    lastupdatedby character varying(50),
    lastupdateddate timestamp without time zone,
    data character varying(5000) NOT NULL,
    ordr integer,
    parent_id uuid,
    post_processor character varying(255),
    pre_processor character varying(255),
    qid integer NOT NULL,
    hlp character varying(500),
    txt character varying(500) NOT NULL,
    sno integer,
    state integer NOT NULL,
    typ integer NOT NULL,
    uuid uuid NOT NULL,
    version integer NOT NULL,
    companyid bigint NOT NULL,
	filtr character varying(500)
);


ALTER TABLE public.question OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 17237)
-- Name: question_inst; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.question_inst (
    id bigint NOT NULL,
    uuid uuid NOT NULL,
    ans character varying(5000),
    ans_by character varying(255),
    ans_on timestamp without time zone,
    assoc_to character varying(50),
    assoc_type integer NOT NULL,
    createdby character varying(50) NOT NULL,
    createddate timestamp without time zone NOT NULL,
    lastupdatedby character varying(50),
    lastupdateddate timestamp without time zone,
    data character varying(5000) NOT NULL,
    mast_prnt_uuid uuid,
    mast_uuid uuid NOT NULL,
    ordr integer,
    parent_id uuid,
    post_processor character varying(255),
    pre_processor character varying(255),
    hlp character varying(500),
    txt character varying(500) NOT NULL,
    sno integer,
    typ integer NOT NULL,
    companyid bigint NOT NULL,
    vesselid bigint NOT NULL,
	fltr character varying(500)
);


ALTER TABLE public.question_inst OWNER TO postgres;

--
-- TOC entry 232 (class 1259 OID 17244)
-- Name: role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.role (
    id bigint NOT NULL,
    code character varying(10) NOT NULL,
    deflt boolean NOT NULL,
    descr character varying(250),
    name character varying(50) NOT NULL,
    qualifier character varying(255),
    status boolean NOT NULL
);


ALTER TABLE public.role OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 17251)
-- Name: role_priv; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.role_priv (
    id bigint NOT NULL,
    enabled boolean NOT NULL,
    priv_id bigint,
    role_id bigint
);


ALTER TABLE public.role_priv OWNER TO postgres;

--
-- TOC entry 234 (class 1259 OID 17256)
-- Name: sec_que; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sec_que (
    id bigint NOT NULL,
    enabled boolean,
    que_id bigint,
    sec_id bigint
);


ALTER TABLE public.sec_que OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 17261)
-- Name: sec_que_inst; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sec_que_inst (
    id bigint NOT NULL,
    enabled boolean,
    que_id bigint,
    sec_id bigint
);


ALTER TABLE public.sec_que_inst OWNER TO postgres;

--
-- TOC entry 236 (class 1259 OID 17266)
-- Name: section; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.section (
    id bigint NOT NULL,
    uuid uuid NOT NULL,
    active boolean NOT NULL,
    createdby character varying(50) NOT NULL,
    createddate timestamp without time zone NOT NULL,
    lastupdatedby character varying(50),
    lastupdateddate timestamp without time zone,
    data character varying(1000),
    detail character varying(500),
    name character varying(100) NOT NULL,
    post_processor character varying(255),
    pre_processor character varying(255),
    descr character varying(250) NOT NULL,
    sno integer,
    state integer NOT NULL,
    version integer,
    companyid bigint NOT NULL
);


ALTER TABLE public.section OWNER TO postgres;

--
-- TOC entry 237 (class 1259 OID 17273)
-- Name: section_inst; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.section_inst (
    id bigint NOT NULL,
    uuid uuid NOT NULL,
    createdby character varying(50) NOT NULL,
    createddate timestamp without time zone NOT NULL,
    lastupdatedby character varying(50),
    lastupdateddate timestamp without time zone,
    data character varying(500) NOT NULL,
    mast_uuid uuid NOT NULL,
    name character varying(100) NOT NULL,
    sno integer,
    companyid bigint NOT NULL,
    vesselid bigint
);


ALTER TABLE public.section_inst OWNER TO postgres;

--
-- TOC entry 238 (class 1259 OID 17280)
-- Name: ship; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ship (
    id bigint NOT NULL,
    uuid uuid NOT NULL,
    active_since timestamp without time zone,
    createdby character varying(50) NOT NULL,
    createddate timestamp without time zone NOT NULL,
    lastupdatedby character varying(50),
    lastupdateddate timestamp without time zone,
    call_sign character varying(255),
    calss character varying(255),
    email character varying(255),
    flag character varying(255),
    grt character varying(255),
    imo character varying(255),
    name character varying(255),
    status character varying(255),
    vessel_type character varying(255),
    companyid bigint NOT NULL
);


ALTER TABLE public.ship OWNER TO postgres;

--
-- TOC entry 239 (class 1259 OID 17287)
-- Name: tbl_mou; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tbl_mou (
    id bigint NOT NULL,
    createdby character varying(50) NOT NULL,
    createddate timestamp without time zone NOT NULL,
    lastupdatedby character varying(50),
    lastupdateddate timestamp without time zone,
    active_cic_checklist bigint,
    code character varying(255),
    descr character varying(255),
    cic_checklist_fromdate timestamp without time zone,
    cic_checklist_enddate timestamp without time zone
);


ALTER TABLE public.tbl_mou OWNER TO postgres;

--
-- TOC entry 240 (class 1259 OID 17294)
-- Name: tbl_rank; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tbl_rank (
    code character varying(10) NOT NULL,
    createdby character varying(50) NOT NULL,
    createddate timestamp without time zone NOT NULL,
    lastupdatedby character varying(50),
    lastupdateddate timestamp without time zone,
    descr character varying(255),
    active boolean NOT NULL,
    multi_user boolean NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.tbl_rank OWNER TO postgres;

--
-- TOC entry 241 (class 1259 OID 17301)
-- Name: tbl_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tbl_user (
    id bigint NOT NULL,
    uuid uuid NOT NULL,
    createdby character varying(50) NOT NULL,
    createddate timestamp without time zone NOT NULL,
    lastupdatedby character varying(50),
    lastupdateddate timestamp without time zone,
    cdc character varying(255),
    data_export_date timestamp without time zone,
    dob timestamp without time zone,
    fname character varying(255) NOT NULL,
    isactive boolean,
    lname character varying(255),
    name character varying(255),
    passport character varying(255),
    password character varying(255) NOT NULL,
    reporting_to integer,
    email character varying(255) NOT NULL,
    rank_code character varying(10) NOT NULL
);


ALTER TABLE public.tbl_user OWNER TO postgres;

--
-- TOC entry 242 (class 1259 OID 17308)
-- Name: template; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.template (
    id bigint NOT NULL,
    uid uuid NOT NULL,
    createdby character varying(50) NOT NULL,
    createddate timestamp without time zone NOT NULL,
    lastupdatedby character varying(50),
    lastupdateddate timestamp without time zone,
    qual uuid,
    template character varying(255) NOT NULL,
    typ character varying(255)
);


ALTER TABLE public.template OWNER TO postgres;

--
-- TOC entry 243 (class 1259 OID 17315)
-- Name: usr_priv; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usr_priv (
    id bigint NOT NULL,
    enabled boolean,
    usr_id character varying(255),
    priv_id bigint
);


ALTER TABLE public.usr_priv OWNER TO postgres;

--
-- TOC entry 244 (class 1259 OID 17320)
-- Name: usr_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usr_role (
    id bigint NOT NULL,
    enabled boolean,
    usr_id character varying(255),
    role_id bigint
);


ALTER TABLE public.usr_role OWNER TO postgres;

--
-- TOC entry 245 (class 1259 OID 17325)
-- Name: voyage; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.voyage (
    id bigint NOT NULL,
    atd timestamp without time zone,
    createdby character varying(50) NOT NULL,
    createddate timestamp without time zone NOT NULL,
    lastupdatedby character varying(50),
    lastupdateddate timestamp without time zone,
    checklistetc timestamp without time zone,
    eta timestamp without time zone,
    etd timestamp without time zone,
    status integer,
    voyageno character varying(15) NOT NULL,
    comp_id bigint NOT NULL,
    destination character varying(255) NOT NULL,
    origin character varying(255) NOT NULL,
    vesselid bigint NOT NULL
);


ALTER TABLE public.voyage OWNER TO postgres;

--
-- TOC entry 5070 (class 0 OID 17137)
-- Dependencies: 215
-- Data for Name: attachment; Type: TABLE DATA; Schema: public; Owner: postgres
--

--
-- TOC entry 4808 (class 2606 OID 17143)
-- Name: attachment attachment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.attachment
    ADD CONSTRAINT attachment_pkey PRIMARY KEY (id);


--
-- TOC entry 4812 (class 2606 OID 17157)
-- Name: chklst_inst chklst_inst_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chklst_inst
    ADD CONSTRAINT chklst_inst_pkey PRIMARY KEY (id);


--
-- TOC entry 4810 (class 2606 OID 17150)
-- Name: chklst chklst_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chklst
    ADD CONSTRAINT chklst_pkey PRIMARY KEY (id);


--
-- TOC entry 4816 (class 2606 OID 17167)
-- Name: chklst_sec_inst chklst_sec_inst_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chklst_sec_inst
    ADD CONSTRAINT chklst_sec_inst_pkey PRIMARY KEY (id);


--
-- TOC entry 4814 (class 2606 OID 17162)
-- Name: chklst_sec chklst_sec_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chklst_sec
    ADD CONSTRAINT chklst_sec_pkey PRIMARY KEY (id);


--
-- TOC entry 4818 (class 2606 OID 17174)
-- Name: cmpny cmpny_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cmpny
    ADD CONSTRAINT cmpny_pkey PRIMARY KEY (id);


--
-- TOC entry 4822 (class 2606 OID 17179)
-- Name: comp_rank comp_rank_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comp_rank
    ADD CONSTRAINT comp_rank_pkey PRIMARY KEY (id);


--
-- TOC entry 4826 (class 2606 OID 17184)
-- Name: comp_user comp_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comp_user
    ADD CONSTRAINT comp_user_pkey PRIMARY KEY (id);


--
-- TOC entry 4828 (class 2606 OID 17191)
-- Name: country country_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.country
    ADD CONSTRAINT country_pkey PRIMARY KEY (code);


--
-- TOC entry 4830 (class 2606 OID 17198)
-- Name: currency currency_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.currency
    ADD CONSTRAINT currency_pkey PRIMARY KEY (id);


--
-- TOC entry 4832 (class 2606 OID 17203)
-- Name: fleet fleet_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fleet
    ADD CONSTRAINT fleet_pkey PRIMARY KEY (id);


--
-- TOC entry 4834 (class 2606 OID 17208)
-- Name: fleet_ship fleet_ship_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fleet_ship
    ADD CONSTRAINT fleet_ship_pkey PRIMARY KEY (id);


--
-- TOC entry 4838 (class 2606 OID 17215)
-- Name: non_conf non_conf_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.non_conf
    ADD CONSTRAINT non_conf_pkey PRIMARY KEY (id);


--
-- TOC entry 4842 (class 2606 OID 17222)
-- Name: port port_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.port
    ADD CONSTRAINT port_pkey PRIMARY KEY (code);


--
-- TOC entry 4844 (class 2606 OID 17229)
-- Name: priv priv_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.priv
    ADD CONSTRAINT priv_pkey PRIMARY KEY (id);


--
-- TOC entry 4850 (class 2606 OID 17243)
-- Name: question_inst question_inst_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.question_inst
    ADD CONSTRAINT question_inst_pkey PRIMARY KEY (id);


--
-- TOC entry 4848 (class 2606 OID 17236)
-- Name: question question_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.question
    ADD CONSTRAINT question_pkey PRIMARY KEY (id);


--
-- TOC entry 4852 (class 2606 OID 17250)
-- Name: role role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);


--
-- TOC entry 4856 (class 2606 OID 17255)
-- Name: role_priv role_priv_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role_priv
    ADD CONSTRAINT role_priv_pkey PRIMARY KEY (id);


--
-- TOC entry 4860 (class 2606 OID 17265)
-- Name: sec_que_inst sec_que_inst_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_que_inst
    ADD CONSTRAINT sec_que_inst_pkey PRIMARY KEY (id);


--
-- TOC entry 4858 (class 2606 OID 17260)
-- Name: sec_que sec_que_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_que
    ADD CONSTRAINT sec_que_pkey PRIMARY KEY (id);


--
-- TOC entry 4864 (class 2606 OID 17279)
-- Name: section_inst section_inst_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.section_inst
    ADD CONSTRAINT section_inst_pkey PRIMARY KEY (id);


--
-- TOC entry 4862 (class 2606 OID 17272)
-- Name: section section_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.section
    ADD CONSTRAINT section_pkey PRIMARY KEY (id);


--
-- TOC entry 4866 (class 2606 OID 17286)
-- Name: ship ship_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ship
    ADD CONSTRAINT ship_pkey PRIMARY KEY (id);


--
-- TOC entry 4868 (class 2606 OID 17293)
-- Name: tbl_mou tbl_mou_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_mou
    ADD CONSTRAINT tbl_mou_pkey PRIMARY KEY (id);


--
-- TOC entry 4870 (class 2606 OID 17300)
-- Name: tbl_rank tbl_rank_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_rank
    ADD CONSTRAINT tbl_rank_pkey PRIMARY KEY (code);


--
-- TOC entry 4872 (class 2606 OID 17307)
-- Name: tbl_user tbl_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_user
    ADD CONSTRAINT tbl_user_pkey PRIMARY KEY (id);


--
-- TOC entry 4878 (class 2606 OID 17314)
-- Name: template template_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.template
    ADD CONSTRAINT template_pkey PRIMARY KEY (id);


--
-- TOC entry 4874 (class 2606 OID 17345)
-- Name: tbl_user uk_2qycvbk9ukxemg2nu136eun2b; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_user
    ADD CONSTRAINT uk_2qycvbk9ukxemg2nu136eun2b UNIQUE (passport);


--
-- TOC entry 4884 (class 2606 OID 17349)
-- Name: voyage uk_apbs5en114dwj2ow6ka4rr5lb; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.voyage
    ADD CONSTRAINT uk_apbs5en114dwj2ow6ka4rr5lb UNIQUE (voyageno);


--
-- TOC entry 4836 (class 2606 OID 17337)
-- Name: fleet_ship uk_kjvdbxr8vdw2h9ydyttggx00u; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fleet_ship
    ADD CONSTRAINT uk_kjvdbxr8vdw2h9ydyttggx00u UNIQUE (vessel_id);


--
-- TOC entry 4876 (class 2606 OID 17347)
-- Name: tbl_user uk_npn1wf1yu1g5rjohbek375pp1; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_user
    ADD CONSTRAINT uk_npn1wf1yu1g5rjohbek375pp1 UNIQUE (email);


--
-- TOC entry 4820 (class 2606 OID 17333)
-- Name: cmpny uk_pg7dug5nr6nh9bt4qg0qgqjn0; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cmpny
    ADD CONSTRAINT uk_pg7dug5nr6nh9bt4qg0qgqjn0 UNIQUE (it_email);


--
-- TOC entry 4824 (class 2606 OID 17335)
-- Name: comp_rank ukfy36g0exdiibnrwxjx13ov9um; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comp_rank
    ADD CONSTRAINT ukfy36g0exdiibnrwxjx13ov9um UNIQUE (companyid, rank_code);


--
-- TOC entry 4840 (class 2606 OID 17339)
-- Name: non_conf ukgchuvoixdtlmvngvwvcmx130h; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.non_conf
    ADD CONSTRAINT ukgchuvoixdtlmvngvwvcmx130h UNIQUE (che_inst_id, que_inst_id);


--
-- TOC entry 4854 (class 2606 OID 17343)
-- Name: role ukp5hn989a0ft403sfd6ggaa13g; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT ukp5hn989a0ft403sfd6ggaa13g UNIQUE (qualifier, name);


--
-- TOC entry 4846 (class 2606 OID 17341)
-- Name: priv uktjxvmso5ar360gmmpkm1maddl; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.priv
    ADD CONSTRAINT uktjxvmso5ar360gmmpkm1maddl UNIQUE (key_code, name);


--
-- TOC entry 4880 (class 2606 OID 17319)
-- Name: usr_priv usr_priv_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usr_priv
    ADD CONSTRAINT usr_priv_pkey PRIMARY KEY (id);


--
-- TOC entry 4882 (class 2606 OID 17324)
-- Name: usr_role usr_role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usr_role
    ADD CONSTRAINT usr_role_pkey PRIMARY KEY (id);


--
-- TOC entry 4886 (class 2606 OID 17331)
-- Name: voyage voyage_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.voyage
    ADD CONSTRAINT voyage_pkey PRIMARY KEY (id);


--
-- TOC entry 4892 (class 2606 OID 17375)
-- Name: chklst_sec_inst fk1jwm8q0mweme6y9ljiunpudc3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chklst_sec_inst
    ADD CONSTRAINT fk1jwm8q0mweme6y9ljiunpudc3 FOREIGN KEY (chk_id) REFERENCES public.chklst_inst(id);


--
-- TOC entry 4907 (class 2606 OID 17450)
-- Name: question fk2b0cusfa7vqy23wumi9eeep4o; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.question
    ADD CONSTRAINT fk2b0cusfa7vqy23wumi9eeep4o FOREIGN KEY (companyid) REFERENCES public.cmpny(id);


--
-- TOC entry 4908 (class 2606 OID 17460)
-- Name: question_inst fk66wl9o9h91c10iltqd5btqyib; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.question_inst
    ADD CONSTRAINT fk66wl9o9h91c10iltqd5btqyib FOREIGN KEY (vesselid) REFERENCES public.ship(id);


--
-- TOC entry 4912 (class 2606 OID 17480)
-- Name: sec_que fk77b9v0duvq4axkropfnisajkr; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_que
    ADD CONSTRAINT fk77b9v0duvq4axkropfnisajkr FOREIGN KEY (sec_id) REFERENCES public.section(id);


--
-- TOC entry 4913 (class 2606 OID 17475)
-- Name: sec_que fk7tyjn9x34o7d1ftf76u745rl1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_que
    ADD CONSTRAINT fk7tyjn9x34o7d1ftf76u745rl1 FOREIGN KEY (que_id) REFERENCES public.question(id);


--
-- TOC entry 4910 (class 2606 OID 17465)
-- Name: role_priv fk8fybdps9ommh46xtq2we6r5wu; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role_priv
    ADD CONSTRAINT fk8fybdps9ommh46xtq2we6r5wu FOREIGN KEY (priv_id) REFERENCES public.priv(id);


--
-- TOC entry 4922 (class 2606 OID 17525)
-- Name: usr_role fk8nnhbni24gsa159a2e1dd5mr; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usr_role
    ADD CONSTRAINT fk8nnhbni24gsa159a2e1dd5mr FOREIGN KEY (role_id) REFERENCES public.role(id);


--
-- TOC entry 4914 (class 2606 OID 17485)
-- Name: sec_que_inst fkatdg0guc4lcjugo8inh7f4ffq; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_que_inst
    ADD CONSTRAINT fkatdg0guc4lcjugo8inh7f4ffq FOREIGN KEY (que_id) REFERENCES public.question_inst(id);


--
-- TOC entry 4894 (class 2606 OID 17390)
-- Name: comp_rank fkbhvxxjpguqlvpcvg5i11h6xy5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comp_rank
    ADD CONSTRAINT fkbhvxxjpguqlvpcvg5i11h6xy5 FOREIGN KEY (rank_code) REFERENCES public.tbl_rank(code);


--
-- TOC entry 4911 (class 2606 OID 17470)
-- Name: role_priv fkbrkt9wy3wi314sii2asw7adqt; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role_priv
    ADD CONSTRAINT fkbrkt9wy3wi314sii2asw7adqt FOREIGN KEY (role_id) REFERENCES public.role(id);


--
-- TOC entry 4896 (class 2606 OID 17400)
-- Name: comp_user fkcg5bg75whu6y9mtbcur7xv2ak; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comp_user
    ADD CONSTRAINT fkcg5bg75whu6y9mtbcur7xv2ak FOREIGN KEY (rank_code) REFERENCES public.tbl_rank(code);


--
-- TOC entry 4897 (class 2606 OID 17395)
-- Name: comp_user fkcgmjxut9l92wpg5apq3m4rewn; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comp_user
    ADD CONSTRAINT fkcgmjxut9l92wpg5apq3m4rewn FOREIGN KEY (company_id) REFERENCES public.cmpny(id);


--
-- TOC entry 4923 (class 2606 OID 17545)
-- Name: voyage fkchn92m2yrgtkhy7k2r5fj91e8; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.voyage
    ADD CONSTRAINT fkchn92m2yrgtkhy7k2r5fj91e8 FOREIGN KEY (vesselid) REFERENCES public.ship(id);


--
-- TOC entry 4893 (class 2606 OID 17380)
-- Name: chklst_sec_inst fkcig9koy2ppf0k1u0c5g3h92j2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chklst_sec_inst
    ADD CONSTRAINT fkcig9koy2ppf0k1u0c5g3h92j2 FOREIGN KEY (sec_id) REFERENCES public.section_inst(id);


--
-- TOC entry 4906 (class 2606 OID 17445)
-- Name: port fkco3dm8crcc1n90m9d3ixfsgq3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.port
    ADD CONSTRAINT fkco3dm8crcc1n90m9d3ixfsgq3 FOREIGN KEY (cntry_id) REFERENCES public.country(code);


--
-- TOC entry 4904 (class 2606 OID 17440)
-- Name: non_conf fkd1o7a900pm96kvs6y5bi6j8wg; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.non_conf
    ADD CONSTRAINT fkd1o7a900pm96kvs6y5bi6j8wg FOREIGN KEY (vessel_id) REFERENCES public.ship(id);


--
-- TOC entry 4916 (class 2606 OID 17495)
-- Name: section fkdkf911ag6fxy3re6xgcj3hfmw; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.section
    ADD CONSTRAINT fkdkf911ag6fxy3re6xgcj3hfmw FOREIGN KEY (companyid) REFERENCES public.cmpny(id);


--
-- TOC entry 4895 (class 2606 OID 17385)
-- Name: comp_rank fkds81ji4dhmnbplidpesq9j0ir; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comp_rank
    ADD CONSTRAINT fkds81ji4dhmnbplidpesq9j0ir FOREIGN KEY (companyid) REFERENCES public.cmpny(id);


--
-- TOC entry 4900 (class 2606 OID 17415)
-- Name: fleet fkdydi2u6p3qq9q2388wcuj2cdi; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fleet
    ADD CONSTRAINT fkdydi2u6p3qq9q2388wcuj2cdi FOREIGN KEY (compid) REFERENCES public.cmpny(id);


--
-- TOC entry 4905 (class 2606 OID 17435)
-- Name: non_conf fkeiiw6dkb393augm6jeduoa4ld; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.non_conf
    ADD CONSTRAINT fkeiiw6dkb393augm6jeduoa4ld FOREIGN KEY (que_inst_id) REFERENCES public.question_inst(id);


--
-- TOC entry 4924 (class 2606 OID 17535)
-- Name: voyage fkfkisytoli6ppci5w4vay0hvbb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.voyage
    ADD CONSTRAINT fkfkisytoli6ppci5w4vay0hvbb FOREIGN KEY (destination) REFERENCES public.port(code);


--
-- TOC entry 4917 (class 2606 OID 17500)
-- Name: section_inst fkfkmr7rgm7sby1pdf7ml5bm2fa; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.section_inst
    ADD CONSTRAINT fkfkmr7rgm7sby1pdf7ml5bm2fa FOREIGN KEY (companyid) REFERENCES public.cmpny(id);


--
-- TOC entry 4898 (class 2606 OID 17410)
-- Name: comp_user fkii4njr4tpnipq4k4kibpipcw6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comp_user
    ADD CONSTRAINT fkii4njr4tpnipq4k4kibpipcw6 FOREIGN KEY (user_id) REFERENCES public.tbl_user(id);


--
-- TOC entry 4888 (class 2606 OID 17355)
-- Name: chklst_inst fkii4x9f4enef3sd7arqnwbgawq; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chklst_inst
    ADD CONSTRAINT fkii4x9f4enef3sd7arqnwbgawq FOREIGN KEY (companyid) REFERENCES public.cmpny(id);


--
-- TOC entry 4915 (class 2606 OID 17490)
-- Name: sec_que_inst fkjb7w1syjohytvyyoot0p5qlwg; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_que_inst
    ADD CONSTRAINT fkjb7w1syjohytvyyoot0p5qlwg FOREIGN KEY (sec_id) REFERENCES public.section_inst(id);


--
-- TOC entry 4925 (class 2606 OID 17530)
-- Name: voyage fkjd1gh2c0le57ofcxd2o5sods; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.voyage
    ADD CONSTRAINT fkjd1gh2c0le57ofcxd2o5sods FOREIGN KEY (comp_id) REFERENCES public.cmpny(id);


--
-- TOC entry 4902 (class 2606 OID 17430)
-- Name: fleet_ship fkk6yw7q74l9hi9jyu1nsv38ljv; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fleet_ship
    ADD CONSTRAINT fkk6yw7q74l9hi9jyu1nsv38ljv FOREIGN KEY (vessel_id) REFERENCES public.ship(id);


--
-- TOC entry 4903 (class 2606 OID 17425)
-- Name: fleet_ship fklj1ukyedip3vlxqbxmdk4vbr; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fleet_ship
    ADD CONSTRAINT fklj1ukyedip3vlxqbxmdk4vbr FOREIGN KEY (fleet_id) REFERENCES public.fleet(id);


--
-- TOC entry 4919 (class 2606 OID 17510)
-- Name: ship fkmd2j6y89hw40091b7g7bn3g26; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ship
    ADD CONSTRAINT fkmd2j6y89hw40091b7g7bn3g26 FOREIGN KEY (companyid) REFERENCES public.cmpny(id);


--
-- TOC entry 4889 (class 2606 OID 17360)
-- Name: chklst_inst fknjis5de9l8xemm4i21y2vsoky; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chklst_inst
    ADD CONSTRAINT fknjis5de9l8xemm4i21y2vsoky FOREIGN KEY (vesselid) REFERENCES public.ship(id);


--
-- TOC entry 4909 (class 2606 OID 17455)
-- Name: question_inst fknmp1o69dc8aumualljtm9j7fe; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.question_inst
    ADD CONSTRAINT fknmp1o69dc8aumualljtm9j7fe FOREIGN KEY (companyid) REFERENCES public.cmpny(id);


--
-- TOC entry 4890 (class 2606 OID 17365)
-- Name: chklst_sec fkovhf8vh51tiww847w5kdlj6uq; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chklst_sec
    ADD CONSTRAINT fkovhf8vh51tiww847w5kdlj6uq FOREIGN KEY (chk_id) REFERENCES public.chklst(id);


--
-- TOC entry 4921 (class 2606 OID 17520)
-- Name: usr_priv fkpsokqiax7l3x39bv8wege8uvw; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usr_priv
    ADD CONSTRAINT fkpsokqiax7l3x39bv8wege8uvw FOREIGN KEY (priv_id) REFERENCES public.priv(id);


--
-- TOC entry 4920 (class 2606 OID 17515)
-- Name: tbl_user fkpt7iucjhhl683hym7cafxfks4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_user
    ADD CONSTRAINT fkpt7iucjhhl683hym7cafxfks4 FOREIGN KEY (rank_code) REFERENCES public.tbl_rank(code);


--
-- TOC entry 4918 (class 2606 OID 17505)
-- Name: section_inst fkq2jhh6sqjqm3m1m8sw8p0lm9j; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.section_inst
    ADD CONSTRAINT fkq2jhh6sqjqm3m1m8sw8p0lm9j FOREIGN KEY (vesselid) REFERENCES public.ship(id);


--
-- TOC entry 4887 (class 2606 OID 17350)
-- Name: chklst fkqctsg5vubnwvv6i5wd2je8s5d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chklst
    ADD CONSTRAINT fkqctsg5vubnwvv6i5wd2je8s5d FOREIGN KEY (companyid) REFERENCES public.cmpny(id);


--
-- TOC entry 4891 (class 2606 OID 17370)
-- Name: chklst_sec fkr3biai69cg4wnlioqe3x0tg5l; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chklst_sec
    ADD CONSTRAINT fkr3biai69cg4wnlioqe3x0tg5l FOREIGN KEY (sec_id) REFERENCES public.section(id);


--
-- TOC entry 4926 (class 2606 OID 17540)
-- Name: voyage fkra2b5bml0hrdqgcrqejqh2qmv; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.voyage
    ADD CONSTRAINT fkra2b5bml0hrdqgcrqejqh2qmv FOREIGN KEY (origin) REFERENCES public.port(code);


--
-- TOC entry 4901 (class 2606 OID 17420)
-- Name: fleet fksdg3gyi340ndhwlqfg6u1rfcr; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fleet
    ADD CONSTRAINT fksdg3gyi340ndhwlqfg6u1rfcr FOREIGN KEY (userid) REFERENCES public.tbl_user(id);


--
-- TOC entry 4899 (class 2606 OID 17405)
-- Name: comp_user fktoj91r56yghl8geetkfqmd1d9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comp_user
    ADD CONSTRAINT fktoj91r56yghl8geetkfqmd1d9 FOREIGN KEY (ship_id) REFERENCES public.ship(id);


-- Completed on 2024-05-15 15:41:51

--
-- PostgreSQL database dump complete
--

