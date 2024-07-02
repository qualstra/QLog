package com.enoch.reports.risq;

import static com.enoch.builder.QuestionConstants.OPTION;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.data.category.DefaultCategoryDataset;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;


import com.enoch.attachment.dto.AttachmentDTO;
import com.enoch.attachment.service.AttachmentService;
import com.enoch.attr.model.Attribute;
import com.enoch.builder.QuestionConstants;
import com.enoch.common.exception.checklist.WeightageNotSupportedException;
import com.enoch.config.ApplicationProperties;
import com.enoch.controller.TestUtils;
import com.enoch.dto.checklist.inst.CheckListInstDTO;
import com.enoch.dto.checklist.inst.QuestionInstDTO;
import com.enoch.dto.checklist.inst.SectionInstDTO;
import com.enoch.reports.BarChartGenerator;
import com.enoch.reports.CASReport;
import com.enoch.reports.Report;
import com.enoch.reports.LineChartGenerator;
import com.enoch.service.CheckListInstService;
import com.enoch.service.client.AttributeClient;
import com.enoch.utils.CopyUtil;
import com.enoch.utils.JSONUtils;
import com.enoch.utils.StringUtils;
import com.enoch.vo.CheckListInstVO;
import com.enoch.vo.QuestionInstVO;
import com.enoch.vo.SectionInstVO;
import com.google.common.base.Function;

import lombok.Data;

@Component
@Data
public class RISQReport implements Report{
	protected final Log logger = LogFactory.getLog(RISQReport.class);
	
	private CheckListInstVO checklist;
	@Autowired
	CheckListInstService chkInstService;
	@Autowired
	AttachmentService attService;
	@Autowired
	TestUtils utils;
	@Autowired
	ApplicationProperties props;
	private File file;
	private File chkReport;
	private AttachmentDTO logo;
	private JSONObject sumarry;
	private HashMap<String, Attribute> attributeMap = new HashMap<String, Attribute>();

	public RISQReport() {
	}

	public String getReportName() {
		return "Detailed Report";

	}
	public String getPath() {
		return new File("").getAbsolutePath();	
	}
	

	public String report(Context cont) {

		cont.setVariable("logo", logo);
		cont.setVariable("prevSummaryChart", this.file);
		cont.setVariable("chkReport", this.chkReport);
		cont.setVariable("checklist", this.checklist);
		cont.setVariable("summary", this.sumarry);
		cont.setVariable("helper", this);
		return "bravo/detail/layout";
	}

	public Function<Context, String> getReport(CheckListInstVO checkListInstVO) {
		this.checklist = checkListInstVO;
		return this::report;
	}

	public Function<Context, String> getReport(CheckListInstDTO checkListInst) {
		this.checklist = populate(checkListInst);
		return this::report;
	}

	@Autowired
	private AttachmentService attServ;

	private QuestionInstVO mapToVO(QuestionInstDTO que) {
		QuestionInstVO res = com.enoch.controller.Helper.toVO(que);
		res.setAttachments(CopyUtil.map(attServ.getAll(que, "PIC"), att -> com.enoch.controller.Helper.toVO(att)));
		res.setChildQuestions(CopyUtil.map(que.getChildQuestions(), inst -> {
			QuestionInstVO childVO = com.enoch.controller.Helper.toVO(inst);
			childVO.setAttachments(
					CopyUtil.map(attServ.getAll(inst, "PIC"), att -> com.enoch.controller.Helper.toVO(att)));
			return childVO;
		}));
		return res;
	}

	private static SimpleDateFormat ansDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy HH:MM:SS");
	private static SimpleDateFormat format2 = new SimpleDateFormat("MMM-yyyy");
	private static SimpleDateFormat ddmmyy = new SimpleDateFormat("dd-MM-yyyy");
	private static SimpleDateFormat ddmmyyhh = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public String getFormatedDate(Date strDate) {
		try {

			return ddmmyy.format((strDate));
		} catch (Exception e) {
			return "";
		}

	}

	public String getFormatedDate(String strDate) {
		try {

			return ddmmyy.format(ansDateFormat.parse(strDate));

		} catch (Exception e) {
			return "";
		}

	}

	public String getFormatedDatetime(String strDate) {
		try {

			return ddmmyyhh.format(ansDateFormat.parse(strDate));

		} catch (Exception e) {
			return "";
		}

	}
	

	@Autowired
	private AttributeClient attrClient;

	private void init(CheckListInstDTO checklistDTO) {
		Iterable<com.enoch.attr.model.Attribute> attrs = attrClient
				.getCompanyAttributesFor(checklistDTO.getCompany().getUUID(), "checklist", checklistDTO.getCheckId());
		attrs.forEach(attribute -> {
			attributeMap.put(attribute.getCode(), attribute);
		});

	}

	public String getDockletNo(CheckListInstVO instVO) {
		return JSONUtils.getStringValue(instVO.getData(), QuestionConstants.CHECK_DOCKLET_NO);

	}

	public CheckListInstVO populate(CheckListInstDTO checklistDTO) {
		init(checklistDTO);
		CheckListInstVO vo = com.enoch.controller.Helper.toVO(checklistDTO);
		List<AttachmentDTO> attachments = attService.getAll(checklistDTO.getCompany(), "PROFILE");
		if (attachments != null && attachments.size() > 0) {
			this.logo = attachments.get(0);
		}
		List<SectionInstDTO> res = chkInstService.loadSections(checklistDTO);
		vo.setSectionInstVOs(CopyUtil.map(res, a -> {
			SectionInstVO secVO = com.enoch.controller.Helper.toVO(a);
			Collection<QuestionInstDTO> questions = chkInstService.loadQuestions(a, (b) -> true);
			List<QuestionInstVO> questionVOs = CopyUtil.map(questions, que -> {
				QuestionInstVO queV = mapToVO(que);
				return queV;
			});
			Integer ansQuestions = questions.stream().mapToInt(q -> {
				return q.getAnswerBy() != null ? 1 : 0;
			}).sum();
			secVO.setQuestions(questionVOs);
			secVO.setAnsQuesCount(ansQuestions);
			secVO.setQuesCount(questionVOs.size());
			return secVO;
		}));
		vo.setAnsQuesCount(vo.getSections().stream().mapToInt(sec -> sec.getAnsQuesCount()).sum());
		vo.setQuesCount(vo.getSections().stream().mapToInt(sec -> sec.getQuesCount()).sum());
		try {
			int countToCompare = props.getPrev_summary_count_chart();
			List<CheckListInstDTO> some = chkInstService.getPreviousChecklist(checklistDTO,countToCompare
					);
			final List<JSONObject> summData = some.stream()
					.map(check -> chkInstService.getSummaryData(check.getCheckId())).collect(Collectors.toList());
			try {
			if(summData.size() < countToCompare) {
				Attribute prevScores = attributeMap.get(checklistDTO.getType()+"_prev_scores");
				JSONArray array = new JSONArray(prevScores.getValue());
				int len = array.length() ;
				for(int i = countToCompare - summData.size(), j =0  ; i > 0 & len> j ; i-- ) {
					summData.add(0,(JSONObject) array.get(j++));
				}
			}
			}catch(Exception e) {}
			this.sumarry = chkInstService.getSummaryData(checklistDTO.getCheckId());
			final List<String> top5Section = getTop6Section((JSONArray) sumarry.get("sections")); 
			this.file = File.createTempFile("CHART", "png", new File(props.getResourceFolder()));
			BarChartGenerator.getCHart(checklistDTO.getVessel().getName() + " RISQ Scores", "Section", "Score", () -> {
				DefaultCategoryDataset dataset = new DefaultCategoryDataset();
				summData.forEach(summary -> {
					JSONObject jchkSum = (JSONObject) summary.get("checklist");
					String time = jchkSum.getString("time");
					JSONArray secData = (JSONArray) summary.get("sections");
					secData = getSectionData(secData,top5Section);
					for (int i = 0; i < secData.length(); i++) {
						JSONObject jsecSum = (JSONObject) secData.get(i);
						double appliedWt = jsecSum.optFloat(QuestionConstants.WEIGHTAGE_APPLIED, 0);
						if (appliedWt > 0) {
							dataset.addValue(appliedWt, time, jsecSum.getString("name"));
						} else {
							dataset.addValue(jsecSum.optFloat(QuestionConstants.WEIGHTAGE_CALC, 0), time,
									jsecSum.getString("name"));
						}
					}
				});

				return dataset;
			}, this.file);
			this.chkReport = File.createTempFile("CHART", "png", new File(props.getResourceFolder()));
			LineChartGenerator.getCHart(checklistDTO.getVessel().getName() + " RISQ Scores comparison", "Checklist",
					"Score", () -> {
						DefaultCategoryDataset dataset = new DefaultCategoryDataset();
						final AtomicInteger integ = new AtomicInteger(0);
						summData.forEach(summary -> {
							JSONObject jchkSum = (JSONObject) summary.get("checklist");
							String time = integ.incrementAndGet() + "";
							try {
								SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
								SimpleDateFormat format2 = new SimpleDateFormat("MMM-yyyy hh:mm:ss");
								DateTimeFormatter format3 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
								   LocalDateTime now = LocalDateTime.now();  
								time = format2.format(format.parse(jchkSum.optString("time")));
							} catch (Exception e) {
							}
							
							

							double applied = jchkSum.optFloat(QuestionConstants.WEIGHTAGE_APPLIED, 0);
							if (applied > 0) {
								dataset.addValue(applied, "chk", time);
							} else {
								dataset.addValue(jchkSum.optFloat(QuestionConstants.WEIGHTAGE_CALC, 0), "chk", time);
							}
						});

						return dataset;
					}, this.chkReport);

		} catch (WeightageNotSupportedException e) {
			// No issues as weightage is not supported
		} catch (IOException e) {
			logger.info("Unable to create summary chart");
			logger.error("Exception creating chart", e);

		}
		return vo;
	}


	public <T> List<List<T>> split(List<T> list, int count) {
		List<T> liste = null;
		List<List<T>> result = new ArrayList<List<T>>();
		int index = 0;
		for (T elem : list) {
			if (index % count == 0) {
				liste = new ArrayList<T>();
				result.add(liste);
			}
			index++;
			liste.add(elem);
		}
		return result;
	}
	public String getAttrValue(String attrCode) {
		Attribute result = this.attributeMap.get(attrCode);
		return result == null ? "" : result.getValue();
	}

	public boolean checkNCForOption(QuestionInstVO questionInstDTO) {
		switch (questionInstDTO.getType()) {
		case String:
			return questionInstDTO.getAnswerAttrib("remark").contains("[NC]");
		}
		String selected = JSONUtils.getJSObject(questionInstDTO.getAnswer()).optString(QuestionConstants.ANSWER);
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
			return true;

		}
		return false;
	}

	public static void main(String[] args) throws Exception{
//		CASReport cas = new CASReport();
//		Attribute attr = cas.getAttribute("Attendees");
//		System.out.println(attr.getName());
//		System.out.println(attr.getValue());
		 String strDate = "2021-03-19T12:44";
		   
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			SimpleDateFormat format2 = new SimpleDateFormat("MMM-yyyy");
		System.out.print(format2.format(format.parse("26-11-2020 10:17:54")));
		
	}

	private Attribute getAttribute(String string) {

		return attributeMap.get(string);
	}

	private JSONArray getSectionData(JSONArray secData, List<String> sectionName) {
		ArrayList<JSONObject> listdata = new ArrayList<JSONObject>();
		if (secData != null) {
			for (int i = 0; i < secData.length(); i++) {
				if(sectionName.contains(((JSONObject)secData.get(i)).get("name"))){
					listdata.add((JSONObject) secData.get(i));	
				}
			}
		}
		JSONArray result = new JSONArray(listdata);
		return result;
	}

	private List<String> getTop6Section(JSONArray secData) {
		ArrayList<JSONObject> listdata = new ArrayList<JSONObject>();
		if (secData != null) {
			for (int i = 0; i < secData.length(); i++) {
				listdata.add((JSONObject) secData.get(i));
			}
		}
		Collections.sort(listdata, new Comparator<JSONObject>() {
			@Override
			public int compare(JSONObject o1, JSONObject o2) {
				return Float.compare(o2.optFloat(QuestionConstants.WEIGHTAGE, 0),
						o1.optFloat(QuestionConstants.WEIGHTAGE, 0));

			}
		});
		return listdata.subList(0, listdata.size() > 6 ? 6 : listdata.size()).stream().map(sec -> sec.getString("name")).collect(Collectors.toList());
	}
	

}
