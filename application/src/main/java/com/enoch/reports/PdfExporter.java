package com.enoch.reports;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.enoch.config.ApplicationProperties;

public class PdfExporter {

	ApplicationProperties props;

	public PdfExporter(ApplicationProperties props) {
		this.props = props;
	}

	public byte[] exportHtml(String html) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		exportHtml(html, out);

		return out.toByteArray();
	}

	public void exportHtml(String html, File file) throws Exception {
		exportHtml(html, new FileOutputStream(file));
	}

	private void exportHtml(String html, OutputStream out) throws Exception {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = builder.parse(new ByteArrayInputStream(html.replaceAll("&nbsp;", "").getBytes()));

		ITextRenderer renderer = new ITextRenderer();
		renderer.getSharedContext().setReplacedElementFactory(
				new ProfileImageReplacedElementFactory(props, renderer.getSharedContext().getReplacedElementFactory()));
		renderer.setDocument(doc, null);
		// FIXME
		// renderer.getFontResolver().addFont("C:/Windows/Fonts/CALIBRI.TTF",
		// true);

		renderer.layout();
		renderer.createPDF(out);
		out.flush();
		out.close();
	}
}