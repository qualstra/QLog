package com.enoch.controller;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.UUID;

import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.enoch.attr.model.Attribute;
import com.enoch.config.ApplicationProperties;
import com.enoch.service.client.AttributeClient;
import com.enoch.temp.AttributeHelper;
import com.enoch.vo.ChecklistUploadVO;

@RestController
@RequestMapping("/rs/attr/{compId}")
public class AttributeController {

	@Autowired
	AttributeClient attributeClient;

	@Autowired
	ApplicationProperties aProp;

	@RequestMapping("/{typ}")
	public Iterable<Attribute> list(@PathVariable UUID compId, @PathVariable String typ) {

		return attributeClient.getCompanyAttributesFor(compId, typ);

	}

	@RequestMapping("/{typ}/{vesselId}")
	public Iterable<Attribute> list(@PathVariable UUID compId, @PathVariable UUID vesselId, @PathVariable String typ) {

		return attributeClient.getCompanyAttributesFor(compId, typ, vesselId);

	}

	@PostMapping("/add")
	public Attribute add(@PathVariable UUID compId, @RequestBody Attribute attribute) throws Exception {
		try {
			return attributeClient.insertAttrib(compId, attribute);
		} catch (Exception e) {
			throw new Exception("error while adding Attribute");
		}
	}

	@RequestMapping(value = "/excelupdate/{vesselId}", method = RequestMethod.POST, consumes = "multipart/form-data")
	public ResponseEntity<?> excelUpdate(@PathVariable UUID compId, @PathVariable UUID vesselId,
			@ModelAttribute MultipartFile file) throws Exception {
		if (compId.compareTo(vesselId) == 0) {
			vesselId = null;
		}
		File file2 = File.createTempFile("Attr2", "ATTR");
		file.transferTo(file2);
		FileInputStream fis = new FileInputStream(file2);
		Workbook workbook = WorkbookFactory.create(fis);
		workbook.setMissingCellPolicy(Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		// System.out.println("executed");
		try {
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				Sheet sheet = workbook.getSheetAt(i);
				for (Row row : sheet) {
					if (row.getRowNum() != 0) {
						Cell attrname = row.getCell(1);
						Cell attrcode = row.getCell(2);
						Cell attrvalue = row.getCell(3);
						Cell function = row.getCell(4);
						Cell attrtype = row.getCell(5);
						// don't add if any of name or code is empty
						if (!(attrname.toString().isEmpty() || attrcode.toString().isEmpty())) {
							if (function.toString().equalsIgnoreCase("delete")) {
								// search for it in database
								Iterable<Attribute> attrlist = attributeClient.getCompanyAttributesFor(compId, "vessel",
										vesselId);
								for (Attribute attr : attrlist) {
									if (attrcode.toString().equals(attr.getCode())) {
										// delete the attribute
										attributeClient.removeAttrib(compId, attr);
									}
								}
							} else if (function.toString().equalsIgnoreCase("add")) {
								// search and update if not found, add
								Iterable<Attribute> attrlist = attributeClient.getCompanyAttributesFor(compId, "vessel",
										vesselId);
								String id=null;
								for (Attribute attr : attrlist) {
									if (attrcode.toString().equals(attr.getCode())) {
										id = attr.getId();
									}
								}
									// update the attribute
									Attribute attrnew = AttributeHelper.getattribute(id, compId, "vessel",
											vesselId, null, attrname.toString(), attrtype.toString(),
											attrvalue.toString(), "", sheet.getSheetName(), row.getRowNum(), i + 1,
											attrcode.toString(), attrname.toString());
									// test code
									// if(!attrvalue.toString().equals(attr.getValue()))
									// System.out.println(attrvalue.toString()+"|"+attr.getValue());
									attributeClient.insertAttrib(compId, attrnew);
								
							}

						} else {
							throw new Exception("Unknown function: " + function.toString());
						}
					}
				}
			}
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			throw new Exception("error while inserting Company Attribute" + e.getMessage(), e);
		}
	}

	@RequestMapping("/init/{vesselId}")
	public void init(@PathVariable UUID compId, @PathVariable UUID vesselId) throws Exception {
		try {
			attributeClient.getCompanyAttributesFor(compId, "vessel").forEach(attr -> {
				attr.setEntityId(vesselId);
				attr.setId(null);
				attributeClient.insertAttrib(compId, attr);
			});
		} catch (Exception e) {
			throw new Exception("error while inserting Attribute");
		}
	}

//	@RequestMapping("/excelinit/{vesselId}")
//	public void initVessel(@PathVariable UUID compId, @PathVariable UUID vesselId) throws Exception {
//		FileInputStream fis = new FileInputStream(aProp.getResourceFolder() + "/attribute/process.xlsx");
//		Workbook workbook = WorkbookFactory.create(fis);
//		workbook.setMissingCellPolicy(Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
//		try {
//			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
//				Sheet sheet = workbook.getSheetAt(i);
//				for (Row row : sheet) {
//					if (row.getRowNum() != 0) {
//						Cell attrname = row.getCell(0);
//						Cell attrcode = row.getCell(1);
//						Cell attrvalue = row.getCell(2);
//						// don't add if any of name or code is empty
//						if (!(attrname.toString().isEmpty() || attrcode.toString().isEmpty())) {
//							Attribute attr = AttributeHelper.getattribute(null, compId, "vessel", vesselId, null,
//									attrname.toString(), "string", attrvalue.toString(), "", sheet.getSheetName(),
//									row.getRowNum(), i + 1, attrcode.toString(), attrname.toString());
//							// System.out.println(attr);
//							attributeClient.insertAttrib(compId, attr);
//						}
//
//					}
//				}
//			}
//
//		} catch (Exception e) {
//			throw new Exception("error while inserting Company Attribute");
//		}
//	}

	@RequestMapping("/init")
	public void initCompany(@PathVariable UUID compId) throws Exception {
		try {
			AttributeHelper.init(compId, null).forEach(attr -> {
				attr.setEntityId(null);
				// System.out.println(attr);
				attributeClient.insertAttrib(compId, attr);
			});
		} catch (Exception e) {
			throw new Exception("error while inserting Company Attribute");
		}
	}

//	@RequestMapping("/excelinit")
//	public void excelinitCompany(@PathVariable UUID compId) throws Exception {
//		FileInputStream fis = new FileInputStream(aProp.getResourceFolder() + "/attribute/process.xlsx");
//		Workbook workbook = WorkbookFactory.create(fis);
//		workbook.setMissingCellPolicy(Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
//		try {
//			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
//				Sheet sheet = workbook.getSheetAt(i);
//				for (Row row : sheet) {
//					if (row.getRowNum() != 0) {
//						Cell attrname = row.getCell(0);
//						Cell attrcode = row.getCell(1);
//						Cell attrvalue = row.getCell(2);
//						// don't add if any of name or code is empty
//						if (!(attrname.toString().isEmpty() || attrcode.toString().isEmpty())) {
//							Attribute attr = AttributeHelper.getattribute(null, compId, "vessel", null, null,
//									attrname.toString(), "string", attrvalue.toString(), "", sheet.getSheetName(),
//									row.getRowNum(), i + 1, attrcode.toString(), attrname.toString());
//							// System.out.println(attr);
//							attributeClient.insertAttrib(compId, attr);
//						}
//
//					}
//				}
//			}
//
//		} catch (Exception e) {
//			throw new Exception("error while inserting Company Attribute");
//		}
//
//	}

	@PostMapping("/delete")
	public ResponseEntity delete(@PathVariable UUID compId, @RequestBody Attribute attribute) throws Exception {
		try {
			attributeClient.removeAttrib(compId, attribute);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			throw new Exception("error while Deleting Attribute");
		}
	}
}
