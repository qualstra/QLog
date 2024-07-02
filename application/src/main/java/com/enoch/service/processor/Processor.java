package com.enoch.service.processor;

import static com.enoch.builder.QuestionConstants.OPTION;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.enoch.attachment.dto.AttachmentDTO;
import com.enoch.attachment.service.AttachmentService;
import com.enoch.builder.QuestionConstants;
import com.enoch.config.ApplicationProperties;
import com.enoch.dto.checklist.inst.CheckListInstDTO;
import com.enoch.dto.checklist.inst.QuestionInstDTO;
import com.enoch.dto.checklist.inst.SectionInstDTO;
import com.enoch.dto.nc.NCDTO;
import com.enoch.misc.Tuple;
import com.enoch.model.checklist.QuestionType;
import com.enoch.reports.CASReport;
import com.enoch.reports.HTMLEngine;
import com.enoch.reports.PdfExporter;
import com.enoch.service.CheckListInstService;
import com.enoch.service.Helper;
import com.enoch.service.NCService;
import com.enoch.utils.CopyUtil;
import com.enoch.utils.JSONUtils;
import com.enoch.utils.StringUtils;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import ch.qos.logback.classic.Logger;

@Component("processor")
public class Processor {

	public static void main(String[] args) {
		String[] answers = {"[NC] world [NC]",
				"[NC] world [NC] hello",
				"hello [NC] world [NC] hello",
				"\nhello [NC] world [NC] hello",
				"hello \n[NC] world [NC] hello",
				"hello [NC] world \n[NC] hello",
				"hello [NC] world [NC] \nhello"};
		Pattern pattern = Pattern.compile("(?<=\\[NC\\])(.*)(?=\\[NC\\])");
		for (String answer : answers) {
			Matcher matcher = pattern.matcher(answer.replace('\n', ' '));
			System.out.println(matcher.find());
		}
	}
	
	protected final Log logger = LogFactory.getLog(Processor.class);

	@Autowired
	CheckListInstService instServ;

	@Autowired
	NCProcessor ncProcessor;

	@Autowired
	WeightageProcessor wtProcessor;
	@Autowired
	ReportProcesor reportProcessor;

	public final void process(UUID chekInstId) {
		Optional<CheckListInstDTO> checkListInst = instServ.loadCheckListInst(chekInstId);
		checkListInst.ifPresent(this::process);
	}

	public void process(CheckListInstDTO checklistInst) {
		List<String> postProcessor = JSONUtils.getStrList(checklistInst.getPostProcessor());
		postProcessor.forEach(processorName -> {

			switch (processorName) {
			case "NC":
				try {
					reportProcessor.process(checklistInst);
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					ncProcessor.process(checklistInst);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case "WT":
				try {
					wtProcessor.process(checklistInst);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			default:
				logger.info("Not processing " + processorName);
			}
		});
	}

	public void process(UUID uuid, String processor) {
		Optional<CheckListInstDTO> optchecklistInst = this.instServ.loadCheckListInst(uuid);
		optchecklistInst.ifPresent(checklistInst -> {
			switch (processor) {
			case "REP":
				try {
					reportProcessor.process(checklistInst);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case "NC":
				try {
					ncProcessor.process(checklistInst);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case "WT":
				try {
					wtProcessor.process(checklistInst);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			default:
				logger.info("Not processing " + processor);
			}
		});
	}

}

@Service
class WeightageProcessor extends Processor {
	@Autowired
	CheckListInstService chkInstServ;

	public WeightageProcessor() {

	}

	@Override
	public void process(CheckListInstDTO checkListInst) {
		// checkListInst.get
		JSONObject chkData = JSONUtils.getJSObject(checkListInst.getData());
		Object weightageObj = chkData.opt(QuestionConstants.WEIGHTAGE);
		boolean isPercent = (weightageObj instanceof String) && ("percent".equalsIgnoreCase(weightageObj.toString()));
		int chkWt = StringUtils.getIntVal(weightageObj.toString(), isPercent ? 100 : 0);
		Map<Tuple<SectionInstDTO, Double>, List<Tuple<QuestionInstDTO, Double>>> secsWt = chkInstServ
				.loadSections(checkListInst).stream().map(secInst -> {
					JSONObject secOpt = JSONUtils.getJSObject(secInst.getData());
					double ansWt = secOpt.optDouble(QuestionConstants.WEIGHTAGE, 0);
					return new Tuple<SectionInstDTO, Double>(secInst, ansWt);
				}).map(secInstWt -> {
					SectionInstDTO secInst = secInstWt.getValue1();
					return new Tuple<Tuple<SectionInstDTO, Double>, List<Tuple<QuestionInstDTO, Double>>>(secInstWt,
							chkInstServ.loadQuestions(secInst).stream().map(questionInstDTO -> {
								JSONObject answer = JSONUtils.getJSObject(questionInstDTO.getAnswer());
								double ansWt = answer.optDouble(QuestionConstants.WEIGHTAGE, 0);
								for (QuestionInstDTO childQue : questionInstDTO.getChildQuestions()) {
									JSONObject childAns = JSONUtils.getJSObject(questionInstDTO.getAnswer());
									ansWt += childAns.optDouble(QuestionConstants.WEIGHTAGE, 0);
								}
								return new Tuple<QuestionInstDTO, Double>(questionInstDTO, ansWt);
							}).collect(Collectors.toList()));
				}).collect(Collectors.toMap(a -> a.getValue1(), b -> b.getValue2()));
		List<Tuple<SectionInstDTO, Double>> sesUpdated = new ArrayList<Tuple<SectionInstDTO, Double>>();
		secsWt.forEach((Tuple<SectionInstDTO, Double> key, List<Tuple<QuestionInstDTO, Double>> val) -> {
			SectionInstDTO sec = key.getValue1();
			JSONObject obj = new JSONObject(key.getValue1().getData());
			double calculate = obj.optDouble(QuestionConstants.WEIGHTAGE, -1);
			if (calculate > 0) {
				calculate = val.stream().map(a -> a.getValue2().doubleValue()).filter(a -> a > 0)
						.collect(Collectors.summingDouble((Double::doubleValue))).doubleValue();
				obj.put(QuestionConstants.WEIGHTAGE_CALC, calculate);
			} else {
				obj.put(QuestionConstants.WEIGHTAGE_CALC, 0);
			}
			sec.setData(obj.toString());
			sesUpdated.add(new Tuple(chkInstServ.save(sec), calculate));
		});

		double calcWT = sesUpdated.stream().map(a -> a.getValue2().doubleValue()).filter(a -> a > 0)
				.collect(Collectors.summingDouble((Double::doubleValue))).doubleValue();
		JSONObject obj = new JSONObject(checkListInst.getData());
		obj.put(QuestionConstants.WEIGHTAGE_CALC, calcWT);
		checkListInst.setData(obj.toString());
		chkInstServ.save(checkListInst);

	}

}

@Service
class ReportProcesor extends Processor {
	@Autowired
	CASReport casReport;
	@Autowired
	ApplicationProperties appProperties;
	protected final Log logger = LogFactory.getLog(ReportProcesor.class);
	@Autowired
	AttachmentService attachmentService;

	@Override
	public void process(CheckListInstDTO checkListInst) {

		try {
			HTMLEngine engine = new HTMLEngine();
			final String result = engine.process(casReport.getReport(checkListInst));

			String location = String.format("%s%sREPORT%s%s.pdf", checkListInst.getCheckId(), File.separatorChar,
					File.separatorChar, UUID.randomUUID());
			// Document document = new Document();
			File file = File.createTempFile("REPORT", "pdf");
			PdfExporter exporter = new PdfExporter(appProperties);
			exporter.exportHtml(result, file);
			// PdfWriter writer = PdfWriter.getInstance(document, new
			// FileOutputStream(file));
			// document.open();
			// XMLWorkerHelper.getInstance().parseXHtml(writer, document, new
			// ByteArrayInputStream(result.getBytes()));
			// document.close();
			CopyUtil.writeToFile(new FileInputStream(file),
					appProperties.getResourceFolder() + File.separatorChar + location);

			AttachmentDTO attachmentDTO = new AttachmentDTO();
			attachmentDTO.setDesc(String.format("Detailed report for %s", checkListInst.getName()));
			attachmentDTO.setUUID(checkListInst.getCompany().getUUID());
			attachmentDTO.setHeader("Detailed Report");
			attachmentDTO.setLocation(location);
			attachmentDTO.setType("application/pdf");
			attachmentDTO.setMulti(true);
			attachmentDTO.setSecondaryId("REPORT");
			attachmentDTO.setAuditTrail(com.enoch.service.Helper.getAuditTrail());
			attachmentService.create(attachmentDTO);

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}

	}
}

@Service
class NCProcessor extends Processor {
	protected final Log logger = LogFactory.getLog(NCProcessor.class);

	@Autowired
	CheckListInstService chkInstServ;
	@Autowired
	NCService ncService;
	@Autowired
	AttachmentService attService;

	@Override
	public void process(CheckListInstDTO checkListInst) {
		logger.info("Processing NC for the checklist");
		ncService.loadNCSForChecklistInst(checkListInst.getCheckId()).forEach(attService::deleteAll);
		ncService.clearNCSForChecklistInst(checkListInst.getCheckId());
		chkInstServ.loadSections(checkListInst).forEach(secInst -> {
			chkInstServ.loadQuestions(secInst).stream().forEach(questionInstDTO -> {
				checkAndCreateNC(checkListInst, questionInstDTO);
				questionInstDTO.getChildQuestions().forEach(e -> checkAndCreateNC(checkListInst, e));
			});
		});
	}

	private void checkAndCreateNC(CheckListInstDTO checkListInst, QuestionInstDTO questionInstDTO) {
		try {
			switch (questionInstDTO.getType()) {
			case Option:
				checkAndCreateNCForOption(checkListInst, questionInstDTO);
				break;
			case String:
				checkAndCreateNCForString(checkListInst, questionInstDTO);
				break;
			default:
			}
		} catch (Exception e) {
			logger.info("Not processing " + checkListInst);
		}
	}

	private void checkAndCreateNCForString(CheckListInstDTO checkListInst, QuestionInstDTO questionInstDTO) {
		if (JSONUtils.getJSObject(questionInstDTO.getAnswer()) == null)
			return;
		String answer = JSONUtils.getJSObject(questionInstDTO.getAnswer()).optString(QuestionConstants.REMARK);
		Pattern pattern = Pattern.compile("(?<=\\[NC\\])(.*)(?=\\[NC\\])");
		String selectedlevel = JSONUtils.getJSObject(questionInstDTO.getAnswer()).optString(QuestionConstants.LEVEL);
		Matcher matcher = pattern.matcher(answer.replace('\n', ' '));
		if (matcher.find()) {
			logger.info(String.format("Creating NC for the Question[%s]", questionInstDTO.getUUID()));
			NCDTO ncdto = new NCDTO();
			ncdto.setCheckId(checkListInst.getCheckId());
			ncdto.setQuestion(questionInstDTO);
			ncdto.setShip(checkListInst.getVessel());
			ncdto.setResponsible(questionInstDTO.getAnswerBy());
			ncdto.setCreationTime(new Date());
			ncdto.setAuditTrail(Helper.getAuditTrail());
			ncdto.setLevel(selectedlevel);
			ncService.createNC(ncdto);
		}
	}

	private void checkAndCreateNCForOption(CheckListInstDTO checkListInst, QuestionInstDTO questionInstDTO) {
		String selected = JSONUtils.getJSObject(questionInstDTO.getAnswer()).optString(QuestionConstants.ANSWER);
		String selectedlevel = JSONUtils.getJSObject(questionInstDTO.getAnswer()).optString(QuestionConstants.LEVEL);
		JSONObject o = JSONUtils.getJSObject(questionInstDTO.getData());
		Object orgvalue = o.get(OPTION);
		JSONArray optValues = null;
		if (orgvalue instanceof JSONArray) {
			optValues = (JSONArray) orgvalue;
		} else if (orgvalue instanceof JSONObject) {
			if (!((JSONObject) orgvalue).isNull("values")) {
				optValues = ((JSONObject) orgvalue).getJSONArray("values");
			}
		}
		
		
		JSONObject selectedOpt = null;
		for (int i = optValues.length() - 1; i >= 0; i--) {
			if (selected.equalsIgnoreCase(((JSONObject) optValues.get(i)).optString(QuestionConstants.CODE))) {
				selectedOpt = (JSONObject) optValues.get(i);
				break;
			}
		}
		if (selectedOpt != null && StringUtils.getBoolVal(selectedOpt.optString(QuestionConstants.ISNC, "N"), false)) {
			logger.info(String.format("Creating NC for the Question[%s]", questionInstDTO.getUUID()));
			NCDTO ncdto = new NCDTO();
			ncdto.setCheckId(checkListInst.getCheckId());
			ncdto.setQuestion(questionInstDTO);
			ncdto.setShip(checkListInst.getVessel());
			ncdto.setResponsible(questionInstDTO.getAnswerBy());
			ncdto.setCreationTime(new Date());
			ncdto.setAuditTrail(Helper.getAuditTrail());
			ncdto.setLevel(selectedlevel);
			ncService.createNC(ncdto);

		}
	}


}