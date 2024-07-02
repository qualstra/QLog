package com.enoch.reports.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;

import com.enoch.config.ApplicationProperties;
import com.enoch.vo.AttachmentVO;
import com.enoch.vo.NCVO;

public class ExcelEngine {
	ApplicationProperties prop;

	public ExcelEngine(ApplicationProperties prop) {
		this.prop = prop;
	}

	CellStyle dateStyle = null;
	CellStyle wrapStyle = null;
	public void process(List<NCVO> ncs, File file) throws Exception {

		/* Create a Workbook and Worksheet */
		HSSFWorkbook my_workbook = new HSSFWorkbook();
		dateStyle = my_workbook.createCellStyle();
		wrapStyle = my_workbook.createCellStyle();
		CreationHelper creationHelper = my_workbook.getCreationHelper();
		dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd-mm-yyyy"));
		wrapStyle.setWrapText(true);
		HSSFSheet my_sheet = my_workbook.createSheet("NC");
		int rowInd = 0;
		HSSFRow row = my_sheet.createRow(++rowInd);
		populateHeader(my_workbook, row, "SNo", "Question", "NC Remark", "NC Created Date", "NC Closure Remark",
				"NC Closure Date","NC Category", "Status");

		my_sheet.setColumnWidth(1, 256 * 30);
		my_sheet.setColumnWidth(2, 256 * 30);
		my_sheet.setColumnWidth(3, 256 * 15);
		my_sheet.setColumnWidth(4, 256 * 30);
		my_sheet.setColumnWidth(5, 256 * 15);
		my_sheet.setColumnWidth(6, 256 * 15);
		int serialNo = 1;
		for (NCVO nc : ncs) {
			HSSFRow rowf = my_sheet.createRow(++rowInd);
			populateData(my_workbook, rowf, serialNo++, nc);
			populateData(my_workbook, my_sheet.createRow(++rowInd), "NC Attachments", nc.getNcAttachments());
			populateData(my_workbook, my_sheet.createRow(++rowInd), "NC Closure Attachments",
					nc.getNcClosureAttachments());
		}
		/* Write changes to the workbook */
		FileOutputStream out = new FileOutputStream(file);
		my_workbook.write(out);
		out.close();
	}

	private void populateData(HSSFWorkbook my_workbook, HSSFRow row, String string, List<AttachmentVO> ncAttachments)
			throws Exception {
		row.setHeight((short) 1000);
		int i = 1;
		Cell cell = row.createCell(i++);
		cell.setCellValue(string);
		for (AttachmentVO vo : ncAttachments) {
			String fileName = prop.getResourceFolder() + File.separatorChar + vo.getLocation();
			if(!new File(fileName).exists()) {
				continue;
			}
			/* Set image Height */
			row.setHeight((short) 2000);
			/* Read the input image into InputStream */
			InputStream my_banner_image = new FileInputStream(fileName);
			/* Convert Image to byte array */
			byte[] bytes = IOUtils.toByteArray(my_banner_image);
			/* Add Picture to workbook and get a index for the picture */
			int my_picture_id = my_workbook.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
			/* Close Input Stream */
			my_banner_image.close();
			/* Create the drawing container */
			HSSFPatriarch drawing = row.getSheet().createDrawingPatriarch();
			/* Create an anchor point */
			ClientAnchor my_anchor = new HSSFClientAnchor();
			/* Define top left corner, and we can resize picture suitable from there */
			my_anchor.setCol1(i++);
			my_anchor.setRow1(row.getRowNum());
			/* Invoke createPicture and pass the anchor point and ID */
			HSSFPicture my_picture = drawing.createPicture(my_anchor, my_picture_id);
			/* Call resize method, which resizes the image */
			my_picture.resize(1, 1);
		}
	}

	private void populateData(HSSFWorkbook my_workbook, HSSFRow rowow, int rowInd, NCVO nc) {
		//test code
		//System.out.println(nc.getLevel());
		//test ends
		rowow.setHeight((short) 500);
		int i = 0;
		Cell cell = rowow.createCell(i++);
		cell.setCellValue(rowInd);
		cell = rowow.createCell(i++);
		cell .setCellStyle(wrapStyle);
		cell.setCellValue(nc.getQuestion().getQuestionText());
		cell = rowow.createCell(i++);
		switch (nc.getQuestion().getType()) {
		case String:
			cell.setCellValue(nc.getQuestion().getAnswerAttrib("remark"));
			break;
		case Option:
		case Date:
			cell.setCellValue(
					nc.getQuestion().getAnswerAttrib("answer") + " - " + nc.getQuestion().getAnswerAttrib("remark"));
			break;
		default:
			cell.setCellValue(nc.getQuestion().getAnswerAttrib("answer"));
		}
		cell .setCellStyle(wrapStyle);
		cell = rowow.createCell(i++);
		if (nc.getAuditTrail().getCreatedDate() != null) {
			cell.setCellStyle(dateStyle);
			cell.setCellValue(nc.getAuditTrail().getCreatedDate());
		}
		cell = rowow.createCell(i++);
		cell.setCellValue(nc.getRemarks());
		cell .setCellStyle(wrapStyle);
		cell = rowow.createCell(i++);
		if (nc.getClosureTime() != null) {
			cell.setCellStyle(dateStyle);
			cell.setCellValue(nc.getClosureTime());
		}
		cell = rowow.createCell(i++);
		cell.setCellValue(nc.getLevel());
		cell = rowow.createCell(i++);
		cell.setCellValue(nc.getStatus().toString());

	}

	private void populateHeader(HSSFWorkbook workbook, HSSFRow headerRow, String... columns) {
		headerRow.setHeight((short) 500);
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 13);
		headerFont.setColor(IndexedColors.BLACK.getIndex());
		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();

		headerCellStyle.setFont(headerFont);

		// Create cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);
		}
	}
}
