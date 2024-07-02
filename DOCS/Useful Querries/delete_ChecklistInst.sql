
DELETE FROM sec_que_inst
WHERE que_id IN (SELECT id FROM question_inst where vesselid=17);
delete from question_inst where vesselid=17;

delete from chklst_sec_inst
WHERE sec_id IN (SELECT id FROM section_inst where vesselid=17);
delete from section_inst where vesselid=17;


delete from chklst_inst where vesselid=17;




