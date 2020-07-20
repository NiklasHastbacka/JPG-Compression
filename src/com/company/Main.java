
package com.company;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.Buffer;
import java.util.Iterator;

public class Main {

    public static void compress(String file) throws IOException
    {

        BufferedImage img = ImageIO.read(new File(file));
        File jpgImg = new File(/* File where the jpg output should be */);
        ImageIO.write(img, "jpg", jpgImg);

        BufferedImage image = ImageIO.read(jpgImg);
        File compressedImageFile = new File(/* Where the compressed image(JPG) should be */);
        OutputStream os = new FileOutputStream(compressedImageFile);

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        ImageWriter writer = (ImageWriter) writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();

        // Check if image can be compressed
        if (param.canWriteCompressed()) {
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            // How much it should be compressed
            param.setCompressionQuality(0.3f);
            System.out.println("Compressing image");
        } else {
            System.out.println("Could not compress");
        }

        // Write the compressed image to the folder
        writer.write(null, new IIOImage(image, null, null), param);

        System.out.println("Done compressing");

        os.close();
        ios.close();
        writer.dispose();

    }

    public static void main(String[] args) throws IOException {
        String imageToCompress = /* File that should be compressed */;
        compress(imageToCompress);
    }
}
