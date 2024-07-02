package com.enoch.xl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.enoch.common.exception.ServiceException;

public class Parser {

	public static void main(String[] args) throws Exception {
		
	}
	
	public static Map<Integer, List<String>> getData(File file2){
		try {
		FileInputStream file = new FileInputStream(file2);
		Workbook workbook = new XSSFWorkbook(file);
		Sheet sheet = workbook.getSheetAt(0);

		Map<Integer, List<String>> data = new HashMap<>();
		int i = 0;
		for (Row row : sheet) {
			data.put(i, new ArrayList<String>());
			for (Cell cell : row) {
				switch (cell.getCellTypeEnum()) {
				case STRING:
					data.get(new Integer(i)).add(cell.getRichStringCellValue().getString());
					break;
				case NUMERIC:
					if (DateUtil.isCellDateFormatted(cell)) {
						data.get(i).add(cell.getDateCellValue() + "");
					} else {
						data.get(i).add(cell.getNumericCellValue() + "");
					}
					break;
				case BOOLEAN:
					data.get(i).add(cell.getBooleanCellValue() + "");
					break;
				case FORMULA:
					data.get(i).add(cell.getCellFormula() + "");
					break;
				default:
					data.get(new Integer(i)).add(" ");
				}
			}
			i++;
		}
		
		return data;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
