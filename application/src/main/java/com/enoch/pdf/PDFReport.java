/*
 * package com.enoch.pdf;
 * 
 * import static com.enoch.pdf.PDFHelper.BOLD; import static
 * com.enoch.pdf.PDFHelper.H1_BOLD; import static com.enoch.pdf.PDFHelper.H2;
 * import static com.enoch.pdf.PDFHelper.NEW_LINE; import static
 * com.enoch.pdf.PDFHelper.NORMAL; import static com.enoch.pdf.PDFHelper.SMALL;
 * import static com.enoch.pdf.PDFHelper.SMALL_BOLD; import static
 * com.enoch.pdf.ReportConstant.APPROVED_BY; import static
 * com.enoch.pdf.ReportConstant.CHK_NAME; import static
 * com.enoch.pdf.ReportConstant.COMP_NAME; import static
 * com.enoch.pdf.ReportConstant.DOC_NO; import static
 * com.enoch.pdf.ReportConstant.DOC_TITLE; import static
 * com.enoch.pdf.ReportConstant.EFFEC_DT; import static
 * com.enoch.pdf.ReportConstant.MANUAL_TITLE; import static
 * com.enoch.pdf.ReportConstant.PAGE_NO; import static
 * com.enoch.pdf.ReportConstant.PREPARED_BY; import static
 * com.enoch.pdf.ReportConstant.REV_NO;
 * 
 * import java.io.File; import java.io.IOException; import
 * java.net.MalformedURLException; import java.util.HashMap; import
 * java.util.List; import java.util.Map; import java.util.Optional; import
 * java.util.ResourceBundle;
 * 
 * import com.enoch.psc.checkList.vo.CheckListItemVO; import
 * com.enoch.psc.core.constants.IEasyShippingConstants; import
 * com.enoch.psc.reporting.model.ReportCriteria; import
 * com.itextpdf.io.font.FontConstants; import
 * com.itextpdf.io.image.ImageDataFactory; import
 * com.itextpdf.kernel.events.Event; import
 * com.itextpdf.kernel.events.IEventHandler; import
 * com.itextpdf.kernel.events.PdfDocumentEvent; import
 * com.itextpdf.kernel.font.PdfFont; import
 * com.itextpdf.kernel.font.PdfFontFactory; import
 * com.itextpdf.kernel.geom.PageSize; import
 * com.itextpdf.kernel.pdf.PdfDocument; import com.itextpdf.kernel.pdf.PdfName;
 * import com.itextpdf.kernel.pdf.PdfNumber; import
 * com.itextpdf.kernel.pdf.PdfWriter; import com.itextpdf.layout.Document;
 * import com.itextpdf.layout.Style; import
 * com.itextpdf.layout.element.AreaBreak; import
 * com.itextpdf.layout.element.Cell; import
 * com.itextpdf.layout.element.IBlockElement; import
 * com.itextpdf.layout.element.Image; import
 * com.itextpdf.layout.element.Paragraph; import
 * com.itextpdf.layout.element.Table; import com.itextpdf.layout.element.Text;
 * import com.itextpdf.layout.property.HorizontalAlignment; import
 * com.itextpdf.layout.property.TextAlignment; import
 * com.itextpdf.layout.property.UnitValue;
 * 
 * class ReportConstant { public static String COMP_NAME = "companyName"; public
 * static String CHK_NAME = "checklistName"; public static String MANUAL_TITLE =
 * "manualName"; public static String DOC_TITLE = "docTitle"; public static
 * String DOC_NO = "docNo"; public static String EFFEC_DT = "effectiveDate";
 * public static String REV_NO = "REV_NO"; public static String PREPARED_BY =
 * "PREPARED_BY"; public static String APPROVED_BY = "APPROVED_BY"; public
 * static String PAGE_NO = "PAGE_NO";
 * 
 * }
 * 
 * public abstract class PDFReport { private String DEST =
 * "/home/disney/eclipse-workspace/PDFGen/out/hello.pdf"; private Document doc =
 * null; private ReportCriteria criteria; public PDFReport(String dest) {
 * this(dest, false); }
 * 
 * public PDFReport(String dest, boolean landscape) { try { PDFHelper.init();
 * File file = new File(dest); file.getParentFile().mkdirs(); DEST = dest;
 * PdfDocument pdfDoc = new PdfDocument(new PdfWriter(DEST));
 * 
 * if (landscape) doc = new Document(pdfDoc, PageSize.A4.rotate()); else doc =
 * new Document(pdfDoc);
 * 
 * 
 * if(isLandscape) { PageOrientationsEventHandler eventHandler = new
 * PageOrientationsEventHandler();
 * pdfDoc.addEventHandler(PdfDocumentEvent.START_PAGE, eventHandler);
 * eventHandler.setOrientation(PageOrientationsEventHandler.LANDSCAPE);
 * 
 * }
 * 
 * 
 * } catch (Exception e) { // TODO Auto-generated catch block
 * e.printStackTrace(); }
 * 
 * }
 * 
 * protected Paragraph getAsPara(String selection) { if (selection == null)
 * selection = ""; return new Paragraph().add(new
 * Text(selection).setFontSize(7)); } protected Paragraph getAsPara(String
 * selection,boolean center) { if (selection == null) selection = ""; if(center)
 * return new Paragraph().add(new
 * Text(selection).setFontSize(7).setHorizontalAlignment(HorizontalAlignment.
 * CENTER)); return new Paragraph().add(new Text(selection).setFontSize(7)); }
 * protected Cell[] addDataToTable(Table table, String head, int[] noMerge) {
 * return addDataToTable(table, new String[] { head }, false, noMerge); }
 * 
 * protected Cell[] addDataToTable(Table table, String[] head, int[] noMerge) {
 * return addDataToTable(table, head, false, noMerge); }
 * 
 * protected Cell[] addDataToTable(Table table, String[] head, boolean bold,
 * int[] merge) { Cell[] cell = new Cell[merge.length + 1]; Cell cell1 = bold ?
 * new Cell(1, 1).add(new Paragraph().add(new
 * Text(head[0]).addStyle(SMALL_BOLD))) : new Cell().add(new Paragraph().add(new
 * Text(head[0]).addStyle(SMALL))); table.addCell(cell1); int cellInd = 0;
 * cell[cellInd++] = cell1; for (int index : merge) { Cell cell2 = null; if
 * (head.length > cellInd) { Text text = new Text(head[cellInd]);
 * text.addStyle(bold ? SMALL_BOLD : SMALL); cell2 = new Cell(1, index).add(new
 * Paragraph().add(text)); } else cell2 = new Cell(1, index).add(new
 * Paragraph().add(new Text(""))); cell[cellInd++] = cell2;
 * table.addCell(cell2); } return cell; }
 * 
 * protected Paragraph getParagraph() { Paragraph p = new Paragraph(); return p;
 * }
 * 
 * protected void addToDoc(AreaBreak areaBreak) { doc.add(areaBreak);
 * 
 * }
 * 
 * protected void addToDoc(IBlockElement areaBreak) { doc.add(areaBreak);
 * 
 * }
 * 
 * protected void addToDoc(Table table) { table.setRotationAngle(80);
 * 
 * if(isLandscape) {
 * table.setRotationAngle(PageOrientationsEventHandler.LANDSCAPE.getValue()); }
 * 
 * doc.add(table);
 * 
 * }
 * 
 * public void addLine(String string, boolean center, Style style) { Paragraph p
 * = getParagraph(); if (center) p.setTextAlignment(TextAlignment.CENTER);
 * 
 * Text text = new Text(string); text.addStyle(style); if (center)
 * text.addStyle(new
 * Style().setHorizontalAlignment(HorizontalAlignment.CENTER)); p.add(text);
 * doc.add(p);
 * 
 * }
 * 
 * public ReportCriteria getCriteria() { return criteria; }
 * 
 * public void setCriteria(ReportCriteria criteria) { this.criteria = criteria;
 * }
 * 
 * public void addLine(String string, boolean center, boolean bold, boolean
 * underline) { Style style = new Style(); if (bold) style.setBold(); if (bold)
 * style.setUnderline(); addLine(string, center, style); }
 * 
 * public void addPage() { try { generateHeader(doc); } catch (Exception e) { //
 * TODO Auto-generated catch block e.printStackTrace(); } }
 * 
 * public void closeDoc() { doc.close(); }
 * 
 * public void generateReport() throws Exception { addPage(); closeDoc(); }
 * 
 * private Paragraph getHeaderNameParagraph(String ShipName, String manualTitle,
 * String docTitle, String docNo) { Paragraph p = getParagraph(); p.add(new
 * Text(ShipName).addStyle(PDFHelper.H2_BOLD)).add(PDFHelper.NEW_LINE);
 * p.add(new Text("Manual Title:       ").addStyle(NORMAL)); p.add(new
 * Text(manualTitle).addStyle(H2)).add(PDFHelper.NEW_LINE); p.add(new
 * Text("Document Title:   ").addStyle(NORMAL)); p.add(new
 * Text(docTitle).addStyle(H1_BOLD)).add(PDFHelper.NEW_LINE); p.add(new
 * Text("Document No.:     ").addStyle(NORMAL)); p.add(new
 * Text(docNo).addStyle(H1_BOLD)).add(NEW_LINE);
 * 
 * return p; }// \n\n\u00a0\u00a0
 * 
 * protected Map<String, String> getHeaderInfo() { Map<String, String> map = new
 * HashMap<String, String>(); map.put(COMP_NAME, "companyName");
 * map.put(CHK_NAME, "checklistName"); map.put(MANUAL_TITLE, "manualName");
 * map.put(DOC_TITLE, "docTitle"); map.put(DOC_NO, "docNo"); map.put(EFFEC_DT,
 * "effectiveDate"); map.put(REV_NO, "REV_NO"); map.put(PREPARED_BY,
 * "PREPARED_BY"); map.put(APPROVED_BY, "APPROVED_BY"); map.put(PAGE_NO,
 * "PAGE_NO"); return map; }
 * 
 * protected void generateHeader(Document doc) throws Exception { String
 * imageLocation = rb.getString(IEasyShippingConstants.PATH) + "\\reports\\";
 * 
 * Map<String, String> map = getHeaderInfo(); Image image = null;
 * 
 * try{ image = getImage(imageLocation + "t_500x300.jpg").scaleToFit(145, 85);
 * }catch (Exception e) { // TODO: handle exception }
 * 
 * Table table = new
 * Table(UnitValue.createPercentArray(6)).useAllAvailableWidth();
 * 
 * Cell cell = new Cell(3, 5).add( getHeaderNameParagraph(map.get(COMP_NAME),
 * map.get(MANUAL_TITLE), map.get(DOC_TITLE), map.get(DOC_NO)));
 * 
 * table.addCell(cell); if(image!= null) cell = new Cell(6, 1).add(new
 * Paragraph().add(image)); else cell = new Cell(6, 1).add(new
 * Paragraph().add(new Text("Greatship Prachi"))); table.addCell(cell);
 * 
 * cell = new Cell().add(new Paragraph().add(new
 * Text("Effective Date").addStyle(NORMAL)).add(PDFHelper.NEW_LINE) .add(new
 * Text(map.get(EFFEC_DT)).addStyle(NORMAL))); table.addCell(cell);
 * 
 * cell = new Cell().add(new Paragraph().add(new
 * Text("Revision No.").addStyle(NORMAL)).add(PDFHelper.NEW_LINE) .add(new
 * Text(map.get(REV_NO)).addStyle(BOLD))); table.addCell(cell);
 * 
 * cell = new Cell().add(new Paragraph().add(new
 * Text("Prepared By:").addStyle(NORMAL)).add(PDFHelper.NEW_LINE) .add(new
 * Text(map.get(PREPARED_BY)).addStyle(BOLD))); table.addCell(cell); cell = new
 * Cell().add(new Paragraph().add(new
 * Text("Approved By:").addStyle(NORMAL)).add(PDFHelper.NEW_LINE) .add(new
 * Text(map.get(APPROVED_BY)).addStyle(BOLD))); table.addCell(cell);
 * 
 * cell = new Cell().add(new Paragraph("Page | " + map.get(PAGE_NO)));
 * table.addCell(cell); doc.add(table); }
 * 
 * protected final String getSelection(List<CheckListItemVO> vos, int number) {
 * try { CheckListItemVO classDP = vos.stream().filter(que ->
 * que.getMasterChecklistItemId() == number).findFirst() .get(); return
 * classDP.getSelection() == null? "":classDP.getSelection(); } catch (Exception
 * e) { return ""; }
 * 
 * }
 * 
 * protected final String getRemarks(List<CheckListItemVO> vos, int number,
 * String defStr) { try { CheckListItemVO classDP = vos.stream().filter(que ->
 * que.getMasterChecklistItemId() == number).findFirst() .get(); return
 * classDP.getRemarks() == null? defStr:classDP.getRemarks(); } catch (Exception
 * e) { return defStr; }
 * 
 * }
 * 
 * protected final String getQuestion(List<CheckListItemVO> vos, int number) {
 * return getQuestion(vos,number,""); } protected final String
 * getQuestion(List<CheckListItemVO> vos, int number, String defStr) { try {
 * CheckListItemVO classDP = vos.stream().filter(que ->
 * que.getMasterChecklistItemId() == number).findFirst() .get(); return
 * classDP.getQuestionDesc() == null? "":classDP.getQuestionDesc(); } catch
 * (Exception e) { return ""; }
 * 
 * } protected final String getRemarks(List<CheckListItemVO> vos, int number) {
 * return this.getRemarks(vos, number,""); }
 * 
 * ResourceBundle rb = ResourceBundle.getBundle("application");
 * 
 * protected final Optional<Image> getAttachment(List<CheckListItemVO> vos, int
 * number) throws MalformedURLException { Image image = null;
 * 
 * 
 * String reportName = rb.getString(IEasyShippingConstants.PATH) +
 * "\\reports\\"; try { CheckListItemVO classDP = vos.stream().filter(que ->
 * que.getMasterChecklistItemId() == number).findFirst() .get(); image =
 * getImage(reportName+classDP.getAttacmentfileName()); image.scaleToFit(100,
 * 100); } catch (Exception e) { // TODO: handle exception }
 * 
 * return Optional.ofNullable(image); } protected final Optional<Image>
 * getAttachmentSignature(List<CheckListItemVO> vos, int number) throws
 * MalformedURLException { Image image = null;
 * 
 * 
 * String reportName = rb.getString(IEasyShippingConstants.PATH) +
 * "\\reports\\"; try { CheckListItemVO classDP = vos.stream().filter(que ->
 * que.getMasterChecklistItemId() == number).findFirst() .get(); image =
 * getImage(reportName+classDP.getAttacmentfileName()); } catch (Exception e) {
 * // TODO: handle exception }
 * 
 * return Optional.ofNullable(image); } protected Image getImage(String
 * filename) throws MalformedURLException {
 * 
 * Image image = new Image(ImageDataFactory.create(filename)); return image; } }
 * 
 * class PageOrientationsEventHandler implements IEventHandler { public static
 * final PdfNumber PORTRAIT = new PdfNumber(0); public static final PdfNumber
 * LANDSCAPE = new PdfNumber(90); public static final PdfNumber INVERTEDPORTRAIT
 * = new PdfNumber(180); public static final PdfNumber SEASCAPE = new
 * PdfNumber(270); private PdfNumber orientation = LANDSCAPE;
 * 
 * public void setOrientation(PdfNumber orientation) { this.orientation =
 * orientation; }
 * 
 * @Override public void handleEvent(Event currentEvent) { PdfDocumentEvent
 * docEvent = (PdfDocumentEvent) currentEvent;
 * docEvent.getPage().put(PdfName.Rotate, orientation); } }
 * 
 * class PDFHelper { public static Style SMALL; public static Style SMALL_BOLD;
 * public static Style NORMAL; public static Style BOLD;
 * 
 * public static Style H1; public static Style H1_BOLD;
 * 
 * public static Style H2; public static Style H2_BOLD; public static Text
 * NEW_LINE = new Text("\n"); public static Style CENTER = new
 * Style().setHorizontalAlignment(HorizontalAlignment.CENTER).setWidth(300);
 * 
 * public static void init() throws Exception { PdfFont font =
 * PdfFontFactory.createFont(FontConstants.HELVETICA); NORMAL = new Style();
 * NORMAL.setFont(font).setFontSize(10); BOLD = new Style();
 * BOLD.setFont(font).setFontSize(10).setBold(); H1 = new Style();
 * H1.setFont(font).setFontSize(12); H1_BOLD = new Style();
 * H1_BOLD.setFont(font).setFontSize(12).setBold(); H2 = new Style();
 * H2.setFont(font).setFontSize(14); H2_BOLD = new Style();
 * H2_BOLD.setFont(font).setFontSize(14).setBold(); SMALL = new Style();
 * SMALL.setFont(font).setFontSize(7); SMALL_BOLD = new Style();
 * SMALL_BOLD.setFont(font).setFontSize(7).setBold(); }
 * 
 * }
 */