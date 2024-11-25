package com.jbr.exp.codeword;

import com.jbr.exp.codeword.image.Image;
import com.jbr.exp.codeword.image.Square;
import com.jbr.exp.codeword.transform.Transformer;
import com.jbr.exp.codeword.utils.Utils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CwMain {
    static Logger log = Logger.getLogger(CwMain.class.getName());

    public static void main(String[] args) {
        try {
            // Parameters
            String filePattern = "^Screenshot from \\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d-\\d\\d-\\d\\d\\.png$";
            String directory = "/home/jason/Downloads/";
            String working = "/home/jason/Downloads/CodewordAnalysis/";

            // Setup the image.
            String imageFile = Utils.reset(filePattern, directory, working);
            Image image = new Image(working, directory + "/" + imageFile);

            // Transform into squares.
            List<Square> squares = Transformer.imageToSquares(image);
        } catch (IOException e) {
            log.error("Failed",e);
        }
    }
}
