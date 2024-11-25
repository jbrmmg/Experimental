package com.jbr.exp.codeword.image;

import com.jbr.exp.codeword.image.pixel.Pixels;
import com.jbr.exp.codeword.utils.Utils;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Image {
    static Logger log = Logger.getLogger(Image.class.getName());

    private final String workingDirectory;
    private final Pixels image;

    public Image(String workingDirectory, String imageFile) throws IOException {
        this.workingDirectory = workingDirectory;
        this.image = new Pixels();
        BufferedImage image = ImageIO.read(new File(imageFile));

        // Get details of the image.
        List<String> rgbValues = new ArrayList<>();
        for(int x = 0; x < image.getWidth(); x++) {
            for(int y = 0; y < image.getHeight(); y++) {
                int rgb = image.getRGB(x, y);
                String rgbHex = Integer.toHexString(Utils.validateRGB(x,y,rgb)).toUpperCase();
                if(rgbHex.length() > 2) {
                    rgbHex = rgbHex.substring(rgbHex.length() - 2);
                }

                if(!rgbValues.contains(rgbHex)) {
                    rgbValues.add(rgbHex);
                }

                this.image.setPixel(new Coordinate(x,y), Integer.parseInt(rgbHex, 16));
            }
        }

        for (String rgbValue : rgbValues) {
            log.trace("RGB value: " + rgbValue);
        }
    }

    public int getWidth() {
        return this.image.getWidth();
    }

    public int getHeight() {
        return this.image.getHeight();
    }

    public boolean isWhitePixel(Coordinate coordinate) {
        return this.image.isWhite(coordinate);
    }
}
