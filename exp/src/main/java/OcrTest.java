import net.sourceforge.tess4j.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.min;

class CrosswordImage{
    static class RowColDetail {
        public int start;
        public int end;

        @Override
        public String toString() {
            return start + "-" + end;
        }
    }

    private final int width;
    private final int height;
    private final BufferedImage image;
    private final List<RowColDetail> rowDetails;
    private final List<RowColDetail> columnDetails;

    private boolean horizontalLine(int row) {
        int onCount = 0;
        int offCount = 0;

        for(int x = 0; x < width; x++){
            if(getPixel(x,row)) {
                onCount++;
            } else {
                offCount++;
            }
        }

        // Is the on count or the count nearly the full width.
        if((onCount > width - 20) || (offCount > width - 20)) {
            return true;
        }

        return false;
    }

    private boolean verticalLine(int col) {
        int onCount = 0;
        int offCount = 0;

        for(int y = 0; y < height; y++){
            if(getPixel(col,y)) {
                onCount++;
            } else {
                offCount++;
            }
        }

        // Is the on count or the count nearly the full width.
        if((onCount > height - 20) || (offCount > height - 20)) {
            return true;
        }

        return false;
    }

    public int getCrossWordGridHeight() {
        return rowDetails.size();
    }

    public int getCrossWordGridWidth() {
        return columnDetails.size();
    }

    public CrosswordImage(BufferedImage image) {
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.image = image;
        this.rowDetails = new ArrayList<>();
        this.columnDetails = new ArrayList<>();
        List<Integer> horizontalLines = new ArrayList<>();
        List<Integer> verticalLines = new ArrayList<>();

        // Find the horizontal lines.
        for(int y = 0; y < height; y++) {
            if(horizontalLine(y)) {
                horizontalLines.add(y);
            }
        }

        // Find the vertical lines.
        for(int x = 0; x < width; x++) {
            if(verticalLine(x)) {
                verticalLines.add(x);
            }
        }

        // Find the details of the columns.
        Integer previousVerticalLine = null;
        for(Integer nextVerticalLine : verticalLines) {
            // Is this line one more that the previous?
            if(previousVerticalLine != null) {
                if(nextVerticalLine > previousVerticalLine + 1) {
                    // There is a gap between these lines, this is a column.
                    RowColDetail currentColumnDetail = new RowColDetail();
                    currentColumnDetail.start = previousVerticalLine + 1;
                    currentColumnDetail.end = nextVerticalLine - 1;
                    this.columnDetails.add(currentColumnDetail);
                }
            }

            // Set the next vertical line.
            previousVerticalLine = nextVerticalLine;
        }

        // Find the details of the rows.
        Integer previousHorizontalLine = null;
        for(Integer nextHorizontalLine : horizontalLines) {
            // Is this line one more that the previous?
            if(previousHorizontalLine != null) {
                if(nextHorizontalLine > previousHorizontalLine + 1) {
                    // There is a gap between these lines, this is a column.
                    RowColDetail currentColumnDetail = new RowColDetail();
                    currentColumnDetail.start = previousHorizontalLine + 1;
                    currentColumnDetail.end = nextHorizontalLine - 1;
                    this.rowDetails.add(currentColumnDetail);
                }
            }

            // Set the next vertical line.
            previousHorizontalLine = nextHorizontalLine;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean getPixel(int x, int y) {
        int rgb = image.getRGB(x, y);

        return rgb == -1;
    }

    public void outputCell(int x, int y, int maxX, int maxY) {
        // Get the details of the row and column.
        RowColDetail xDetail = this.columnDetails.get(x);
        RowColDetail yDetail = this.rowDetails.get(y);

        StringBuilder line = new StringBuilder();
        line.append("+");
        for(int pixelx = xDetail.start; pixelx <= min(xDetail.start + maxX,xDetail.end); pixelx++) {
            line.append("-");
        }
        line.append("+");
        System.out.println(line);


        for(int pixely = yDetail.start; pixely <= min(yDetail.start + maxY,yDetail.end); pixely++) {
            line = new StringBuilder();
            line.append("|");

            for(int pixelx = xDetail.start; pixelx <= min(xDetail.start + maxX,xDetail.end); pixelx++) {
                if(getPixel(pixelx, pixely)) {
                    line.append("*");
                } else {
                    line.append(" ");
                }
            }

            line.append("|");
            System.out.println(line);
        }

        line = new StringBuilder();
        line.append("+");
        for(int pixelx = xDetail.start; pixelx <= min(xDetail.start + maxX,xDetail.end); pixelx++) {
            line.append("-");
        }
        line.append("+");
        System.out.println(line);
    }

    public int getNumber(int x, int y) {
        // Get the details of the row and column.
        RowColDetail xDetail = this.columnDetails.get(x);
        RowColDetail yDetail = this.rowDetails.get(y);

        // Output the cell
        outputCell(x,y,100,100);

        // Extract the pixels that make up this square.
        StringBuilder id = new StringBuilder();
        for(int pixely = yDetail.start; pixely <= yDetail.start + 5; pixely++) {
            for(int pixelx = xDetail.start; pixelx <= xDetail.start + 11; pixelx++) {
                if(getPixel(pixelx, pixely)) {
                    id.append("*");
                } else {
                    id.append(" ");
                }
            }
        }

        System.out.println("|" + id + "|");

        Map<String,Integer> lookup = new HashMap<>();
        lookup.put("************   *********   *********** *********** *********** *********",1);
        lookup.put("   *********   *********** *********** *********** *********** *********",1);
        lookup.put("*************     *******  *  **********  *********   ********   *******",2);
        lookup.put("*************     *******  *  *********   ********    **********  ******",3);
        lookup.put("***************  *********   *********   ********    *******      ******",4);
        lookup.put("*************     *******  **********    ********     **********  ******",5);
        lookup.put("*************     *******  *  *******    ********     *******  *  ******",6);
        lookup.put("*************     *********   *********  *********   *********  ********",7);
        lookup.put("*     *********   *********  *********   *********  **********  ********",7);
        lookup.put("*************     *******  *  *******     *******     ******   *  ******",8);
        lookup.put("*************     *******  *  ******  **  *******     *******     ******",9);
        lookup.put("*     *******  *  ******  **  *******     *******     *******     ******",9);
        lookup.put("************   **     **   **  *  **** **  **  *** **  **  *** **  **  *",10);
        lookup.put("   **   ****   **   ****** ***  ****** ***  ****** ***  ****** ***  ****",11);
        lookup.put("************   **   ****   **   ****** ***  ****** ***  ****** ***  ****",11);
        lookup.put("   **     **   **  *  **** *****  **** ****   **** ***   ***** **   ****",12);
        lookup.put("************   **     **   **  *  **** *****  **** ****   **** ***   ***",12);
        lookup.put("************   **     **   **  *  **** ****   **** ****   **** *****   *",13);
        lookup.put("************   ****   **   ****   **** ***    **** **     **** **      *",14);
        lookup.put("************   **     **   **  ******* **     **** **      *** ******  *",15);
        lookup.put("************   ***    **   **     **** **     **** **      *** **  **  *",16);
        lookup.put("************   **      *   *****  **** ****   **** ****  ***** ****  ***",17);
        lookup.put("************   **     **   **  *  **** **     **** **     **** **  *   *",18);
        lookup.put("************   **     **   **  *  **** **  **  *** **      *** ***     *",19);
        lookup.put("*************          **  *    *  *****    **  ***     **  **   *  **  ",20);
        lookup.put("*     *   ***  *      ******  **  *****   **  ****   ***  ***  *****  **",21);
        lookup.put("*************     *   ***  *      ******  **  *****   **  ****   ***  **",21);
        lookup.put("*          **  *    *  *****  ***  ****   **   ***   **   ***  ***   ***",22);
        lookup.put("*************          **  *    *  *****  ***  ****   **   ***   **   **",22);
        lookup.put("*************          **  *    *  *****  **   ****   **   ***   ****   ",23);
        lookup.put("*************     **   **  *  **   *****  *    ****        ***   *      ",24);
        lookup.put("*************          **  *    ********       ****         **   *****  ",25);
        lookup.put("*     *    **  *       *****       ****         **   *  **  *  ***      ",26);
        lookup.put("*************     *    **  *       *****       ****         **   *  **  ",26);
        lookup.put("   **     **   **  *  **** **  **  *** **  **  *** **  **  *** **  *  **",10);
        lookup.put("   ****   **   ****   **** ***    **** **     **** **      *** **      *",14);
        lookup.put("*     *******  *  **********  *********   ********   ********  *********",2);
        lookup.put("   **     **   **  *  **** **     **** **     **** **  *   *** **  *   *",18);
        lookup.put("*     *******  **********    ********     **********  ******   *  ******",5);
        lookup.put("   ***    **   **     **** **     **** **      *** **  **  *** **      *",16);
        lookup.put("*     *******  *  *******     *******     ******   *  ******   *  ******",8);
        lookup.put("   **     **   **  *  **** ****   **** ****   **** *****   *** **  *   *",13);
        lookup.put("   **      *   *****  **** ****   **** ****  ***** ****  ***** ***  ****",17);

        if(lookup.containsKey(id.toString())) {
            return lookup.get(id.toString());
        }

        return 0;
    }

    public boolean isGridBlack(int x, int y) {
        // Get the details of the row and column.
        RowColDetail xDetail = this.columnDetails.get(x);
        RowColDetail yDetail = this.rowDetails.get(y);

        for(int pixelx = xDetail.start; pixelx <= xDetail.end; pixelx++) {
            for(int pixely = yDetail.start; pixely <= yDetail.end; pixely++) {
                if(getPixel(pixelx, pixely)) {
                    return false;
                }
            }
        }

        return true;
    }
}

public class OcrTest {

    public static void outputImage(CrosswordImage image,int maxX, int maxY) {
        StringBuilder line = new StringBuilder();
        line.append("+");
        for(int x = 0; x < image.getWidth(); x++) {
            line.append("-");
        }
        line.append("+");
        System.out.println(line);

        for(int y = 0; y < min(image.getHeight(),maxY); y++) {
            line = new StringBuilder();
            line.append("|");

            for(int x = 0; x < min(image.getWidth(),maxX); x++) {
                if(image.getPixel(x, y)) {
                    line.append("*");
                } else {
                    line.append(" ");
                }
            }

            line.append("|");
            System.out.println(line);
        }

        line = new StringBuilder();
        line.append("+");
        for(int x = 0; x < image.getWidth(); x++) {
            line.append("-");
        }
        line.append("+");
        System.out.println(line);
    }

    public static void main(String[] args) {
        /*        // Path to your image file
        File imageFile = new File("/home/jason/Downloads/Crossword.png");

        // Initialize Tesseract instance
        Tesseract tesseract = new Tesseract();

        // Set tessdata folder if not in classpath (optional)
        tesseract.setDatapath("/home/jason/Source/GitHub/Working/Experimental/exp/target/testdata");

        try {
            // Perform OCR on the image
            String result = tesseract.doOCR(imageFile);

            // Output the OCR result
            System.out.println("OCR Result: ");
            System.out.println(result);

        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }*/
        // Details of the image.
        int blackBorderWidth = 2;
        int interLineWidth = 2;
        int squareWidth = 29;
        int squareHeight = 27;

        // Image is the crossword and is only black and white pixels.
        // The crossword has a border of white, and then a black border which is x pixels wide
        // Need to find the

        try {
            // Load the image from file
            File imageFile = new File("/home/jason/Downloads/Crossword.png");
            BufferedImage image = ImageIO.read(imageFile);

            // Get image dimensions
            CrosswordImage cwImage = new CrosswordImage(image);
            outputImage(cwImage,1000,1000);

            // Output the Pattern of the crossword.
            Map<Integer,Integer> numberCount = new HashMap<>();
            for(int n = 0; n <= 26; n++) {
                numberCount.put(n,0);
            }

            for(int y = 0; y < cwImage.getCrossWordGridHeight(); y++) {
                StringBuilder nextLine = new StringBuilder();

                for(int x = 0; x < cwImage.getCrossWordGridWidth(); x++) {
                    boolean isBlack = cwImage.isGridBlack(x, y);
                    nextLine.append(isBlack ? "#" : " ");

                    if(!isBlack) {
                        // Get the number at this grid space.
                        int number = cwImage.getNumber(x,y);

                        if(number >= 0 && number <= 26) {
                            Integer count = numberCount.get(number);
                            count++;
                            numberCount.put(number,count);
                        }
                    }
                }

                System.out.println(nextLine);
            }

            // Output the distribution
            for(Map.Entry<Integer,Integer> entry : numberCount.entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }
        } catch (IOException e) {
            System.out.println("Error loading image: " + e.getMessage());
        }
    }
}
