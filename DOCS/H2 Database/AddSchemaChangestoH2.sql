-- 1. Drop all th unused tables
delete from EMPLOYEE;
drop table EMPLOYEE; 
delete from TBL_ATTACHMENT;
drop table TBL_ATTACHMENT;
delete from TBL_CHECKLIST;
drop table TBL_CHECKLIST;
delete from TBL_CHECKLIST_ITEMS;
drop table TBL_CHECKLIST_ITEMS;
delete from TBL_CHECKLIST_SECTION;
drop table TBL_CHECKLIST_SECTION;
delete from TBL_CHKLIST_NONCONFORMANCES;
drop table TBL_CHKLIST_NONCONFORMANCES;
delete from TBL_CURRENCY;
drop table TBL_CURRENCY;
delete from TBL_MAST_CHECKLIST;
drop table TBL_MAST_CHECKLIST;
delete from TBL_MAST_CHECKLIST_ITEM;
drop table TBL_MAST_CHECKLIST_ITEM;
delete from TBL_MAST_CHECKLIST_SECTION;
drop table TBL_MAST_CHECKLIST_SECTION;
delete from TBL_MAST_FLEET;
drop table TBL_MAST_FLEET;
delete from TBL_MAST_SHIPCOMPANY;
drop table TBL_MAST_SHIPCOMPANY;
delete from TBL_MAST_USER;
drop table TBL_MAST_USER;
delete from TBL_MAST_VESSEL;
drop table TBL_MAST_VESSEL;
delete from TBL_VESSEL_DOCS;
drop table TBL_VESSEL_DOCS;
delete from TBL_VOYAGE;
drop table TBL_VOYAGE;
delete from TBL_COUNTRY;
drop table TBL_COUNTRY;
delete from TBL_MOU_COUNTRY;
drop table TBL_MOU_COUNTRY;
delete from TBL_SCHEDULE;
drop table TBL_SCHEDULE;
delete from TBL_SEAPORT;
drop table TBL_SEAPORT;
delete from TBL_TENANTCODE;
drop table TBL_TENANTCODE;
delete from TBL_TENANTCONFIG;
drop table TBL_TENANTCONFIG;
delete from TBL_TENANTREFERENCE;
drop table TBL_TENANTREFERENCE;
-- 2. Alter column names
alter table COMP_RANK
rename column RANK to RANK_CODE;
alter table COMP_USER
rename column RANK to RANK_CODE;
alter table USER
rename column RANK to RANK_CODE;

alter table PRIV
rename column KEY to KEY_CODE;

alter table ATTACHMENT
rename column DESC to DESCR;
alter table CHKLST
rename column DESC to DESCR;
alter table CHKLST_INST
rename column DESC to DESCR;
alter table RANK
rename column DESC to DESCR;
alter table ROLE
rename column DESC to DESCR;
alter table SECTION
rename column DESC to DESCR;
alter table TBL_MOU
rename column DESC to DESCR;

-- 3. Alter table names
alter table RANK
rename to TBL_RANK;
alter table USER
rename to TBL_USER;
-- 4. remove double filter

UPDATE question SET FLTR = NULL WHERE FLTR IS NOT NULL;
ALTER TABLE question DROP COLUMN FLTR;

UPDATE question_inst SET FILTR = NULL WHERE FILTR IS NOT NULL;
ALTER TABLE question_inst DROP COLUMN FILTR;
