package com.enoch.reports;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Element;
import org.xhtmlrenderer.extend.FSImage;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.extend.ReplacedElementFactory;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.pdf.ITextFSImage;
import org.xhtmlrenderer.pdf.ITextImageElement;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.simple.extend.FormSubmissionListener;

import com.enoch.config.ApplicationProperties;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;

public class ProfileImageReplacedElementFactory implements ReplacedElementFactory {

    private final ReplacedElementFactory superFactory;
    private final ApplicationProperties props;    
    public ProfileImageReplacedElementFactory(ApplicationProperties props, ReplacedElementFactory superFactory) {
        this.superFactory = superFactory;
        this.props = props;
    }

    @Override
    public ReplacedElement createReplacedElement(LayoutContext layoutContext, BlockBox blockBox,
            UserAgentCallback userAgentCallback, int cssWidth, int cssHeight) {

        Element element = blockBox.getElement();
        if (element == null) {
            return null;
        }

        String nodeName = element.getNodeName();
        if ("img".equals(nodeName) ) {
        	String fileName = element.getAttribute("src");
            InputStream input = null;
            try {
            	File file = new File(fileName);
            	
            //	ProfileImageReplacedElementFactory.class.getResource(fileName).
            	if(!file.exists()) {
            		fileName = props .getResourceFolder() + File.separatorChar + fileName;
            		file = new File(fileName);
            	}
                input = new FileInputStream(file);
                byte[] bytes = IOUtils.toByteArray(input);
                Image image = Image.getInstance(bytes);
                FSImage fsImage = new ITextFSImage(image);

                if (fsImage != null) {
                    if ((cssWidth != -1) || (cssHeight != -1)) {
                        fsImage.scale(cssWidth, cssHeight);
                    }
                    return new ITextImageElement(fsImage);
                }
            } catch (IOException e) {
            } catch (BadElementException e) {
            } finally {
                IOUtils.closeQuietly(input);
            }
        }

        return superFactory.createReplacedElement(layoutContext, blockBox, userAgentCallback, cssWidth, cssHeight);
    }

    @Override
    public void reset() {
        superFactory.reset();
    }

    @Override
    public void remove(Element e) {
        superFactory.remove(e);
    }

    @Override
    public void setFormSubmissionListener(FormSubmissionListener listener) {
        superFactory.setFormSubmissionListener(listener);
    }
}