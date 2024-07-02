package com.enoch.utils.image;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;

import org.springframework.beans.factory.annotation.Autowired;


public class Compressor {

    public static void main(String[] args) throws IOException {
        InputStream is = new FileInputStream("C:\\Users\\vijay\\Downloads\\vijay.jpg");
        saveImage(is ,"C:\\Users\\vijay\\Downloads\\tmp\\vijay","jpg");
    }
    
    public static void saveImage(InputStream inputStream, String fileName, String extn) throws IOException{
        BufferedImage bi = ImageIO.read(inputStream);
        File f = new File(fileName+"_origninal."+extn);
        f.getParentFile().mkdirs();
        ImageIO.write(bi, extn, f);
        rescale(bi,extn,fileName+"_icon."+extn);
        compress(1,bi,extn, fileName);
        compress(4,bi,extn, fileName+"_mid");
    	
    }

    private static void rescale(BufferedImage bi, String format, String absFilePath) throws IOException {
        int originalWidth = bi.getWidth();
        int originalHeight = bi.getHeight();
        int type = bi.getType() == 0? BufferedImage.TYPE_INT_ARGB : bi.getType();
        int scale = originalHeight / 100;
        //rescale 50%
        BufferedImage resizedImage = new BufferedImage(originalWidth/scale , originalHeight/scale , type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(bi, 0, 0, originalWidth/scale , originalHeight/scale , null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        ImageIO.write(resizedImage, format , new File(absFilePath));
    }

    private static void compress(int compression, BufferedImage bi,String format, String absFilePath)
            throws FileNotFoundException, IOException {
        Iterator<ImageWriter> i = ImageIO.getImageWritersByFormatName(format);
        ImageWriter jpegWriter = i.next();

        // Set the compression quality
        ImageWriteParam param = jpegWriter.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(0.1f * compression);

        // Write the image to a file
        FileImageOutputStream out = new FileImageOutputStream(new File(absFilePath+"."+format) );
        jpegWriter.setOutput(out);
        jpegWriter.write(null, new IIOImage(bi, null, null), param);
        jpegWriter.dispose();
        out.close();
    }

}