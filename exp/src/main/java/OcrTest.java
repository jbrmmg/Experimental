import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
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
    private final Map<String,Integer> lookup;
    private final Map<String,List<String>> unknown;

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

    public Map<String,List<String>> getUnknowns() {
        return unknown;
    }

    public CrosswordImage(BufferedImage image) {
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.image = image;
        this.rowDetails = new ArrayList<>();
        this.columnDetails = new ArrayList<>();
        this.lookup = new HashMap<>();
        this.unknown = new HashMap<>();
        List<Integer> horizontalLines = new ArrayList<>();
        List<Integer> verticalLines = new ArrayList<>();

        lookup.put("  *    *****  *    ****** **   ****** *  * *****       *****************",13); // 15-3 1-4 3-11 14-15
        lookup.put("    ********    ********    ********  * ********    ********************",8); // 12-15
        lookup.put("*       ****  *     ******  *   *****  *  * ****        ****************",23); // 5-3 8-3 12-5 15-5 10-7 14-7 7-8 12-11 9-13 1-14
        lookup.put("  *    ****** *    ****** *  * *****       *****************************",16); // 1-1 3-1 11-1
        lookup.put("*   ********    ********    ********  * *********   ********************",6); // 14-11
        lookup.put("  *     ******      *****  *  * ****        ****************************",26); // 10-1
        lookup.put("**  *********   *********   ********    ********    ********************",4); // 6-9 15-12
        lookup.put("    **********  **********  *********  **********  *********************",7); // 6-3 3-4 1-5 4-5 5-11 10-11 13-11 1-13 12-13 5-15
        lookup.put("    ********    ********  * ********    ********************************",5); // 13-1
        lookup.put("  *   ******  *   ******* **  ******* **  ******       *****************",11); // 6-15
        lookup.put("  *    *****  *  * ****** ***  ****** **  ******       *****************",12); // 9-2 11-10
        lookup.put("    ********  * ********    ********    *********   ********************",9); // 11-9 7-12 11-15
        lookup.put("*   *  *****  * *  *******  ** ******  *** *****        ****************",21); // 13-2 3-3 9-10 9-11
        lookup.put("    ********    *********   ********  * ********    ********************",3); // 1-2 15-2 13-3 7-4 6-5 15-6 12-7 9-8 13-8 1-12 4-13 10-13 13-13 5-14
        lookup.put("*   ********    ********    ********  * ********    ********************",5); // 7-2 5-5 5-6 3-7 9-9 11-12 2-13 3-13 11-13 13-14
        lookup.put("  *  * ****** *  * ****** *  * *****       *****************************",10); // 4-1 5-1 12-1
        lookup.put("  ***  *****  **   ****** *    ****** *    *****       *****************",14); // 2-3 7-5 7-7 1-8 1-11 5-13
        lookup.put("  *********** *********** **********   *********************************",1); // 6-1 14-1
        lookup.put("  *   * ******  **  *****  **  *****        ****************************",22); // 2-1
        lookup.put("*   **  ****  * *   ******      *****  *    ****        ****************",24); // 14-3 5-4 3-5 11-5 14-5 11-8 2-9 1-10 11-11 7-14
        lookup.put("*       ****  *   * ******    * *****  *  * ****        ****************",20); // 2-5
        lookup.put("*   ********  * **********  *********  *********    ********************",2); // 3-2 11-2
        lookup.put("  *    *****  *  * ****** *  * ****** *  * *****       *****************",10); // 1-3 9-4 9-5 3-6 11-6 2-7 8-9 13-9 15-10 5-12 7-13
        lookup.put("*       ****  *   * ******  **  *****  **  *****        ****************",22); // 7-3 5-8 1-9 6-11 13-12 14-13 15-14 3-15
        lookup.put("*       ****  *     ******      *****  *  * ****        ****************",25); // 9-7 2-15
        lookup.put("  *    *****  ***  ****** ***  ****** **  ******      ******************",17); // 13-5 5-10
        lookup.put("  **********  *********** *********** **********   *********************",1); // 9-3 1-6 7-6 4-7 13-15
        lookup.put("  **   *****  *    ****** *    ****** *  * *****       *****************",16); // 11-4 13-7 15-8 3-9 9-14 8-15
        lookup.put("  *    ****** **   ****** *  * *****       *****************************",13); // 8-1
        lookup.put("**  **********  *********  **********  *********************************",7); // 9-1
        lookup.put("   *    *****  ***  *****  **  *****        ****        ****************",12); // 8-1
        lookup.put("   **   ****   **   *****  *    *****  *    ****        ****    **  ****",14); // 3-2 7-2 11-2 10-3 5-7 2-9 6-9 9-11 2-13 9-14
        lookup.put("         ***  *  *   *****   *   ****   *  * ***         ***         ***",23); // 2-3 2-5 12-9 8-11 14-11 7-12 14-13
        lookup.put("   *    ****   *    *****  ***  *****  **  *****        ****        ****",12); // 9-2 6-3 13-3 1-4 7-5 10-7 13-7 5-8 7-8 9-8 14-9 5-13 7-14 7-15 9-15
        lookup.put("   **********  **********  *********    ********    ********************",1); // 2-1 9-1
        lookup.put("   *********   **********  **********  *********    ********    ********",1); // 7-3 7-6 4-9 2-11
        lookup.put("     *********  **********  **********  *********  **********  *********",7); // 3-6 6-11 5-15
        lookup.put("   *    ****   *  * *****  *  * *****  *  * ****        ****    *   ****",10); // 3-3 9-3 2-7 12-7 5-12 1-13 9-13
        lookup.put("         ***  *    *******       ****   **** ***         ***         ***",25); // 1-9 2-15
        lookup.put("         ***  *      *****       ****   *  * ***         ***     *   ***",26); // 6-7 7-10 9-10 11-15
        lookup.put("  *  *   *****       ****   *    ***         ***     **  *********     *",24); // 7-1 14-1
        lookup.put("*   *********   ********     *******     ********** ********************",4); // 1-1
        lookup.put("  *  *********   ********   ********     *******     *******************",2); // 6-1 12-1
        lookup.put("     *   ***  *  *   *****       ****   *    ***         ***     **  ***",24); // 11-3 4-5 13-11 15-11 1-12
        lookup.put("         ***  *      *****   **  ****   **  ****         ***         ***",22); // 14-5 8-15
        lookup.put("   *    ****   *    *****  *    *****  *  * ****        ****    *   ****",16); // 15-3 15-4 3-9 7-9 10-15
        lookup.put("   *    ****   ***  *****  **  ******  **  *****    *  *****    *  *****",17); // 5-3 11-9 11-10 3-11 7-11 12-13 13-13 13-14 12-15
        lookup.put("   *    ****   **   *****  **   *****  *  * ****        ****        ****",13); // 11-4 15-7
        lookup.put("*    *******     *******     *******  *  ********    ********    *******",6); // 13-6 10-13 15-13 1-15
        lookup.put("   *    ****   *  *******  *    *****  **** ****        ****        ****",15); // 5-6
        lookup.put("  *  *   *****   *   ****   *  * ***         ***         ***************",23); // 4-1 13-1
        lookup.put("     *******  *  *******     ********    ********    ********   ********",9); // 15-2 3-5 15-5 9-6 11-8 1-11 13-12 3-14
        lookup.put("* **********     **********  *******     ********    *******************",5); // 15-1
        lookup.put("        ****  *   * ******   ** *****   *** ****         ***         ***",21); // 4-3 3-4 12-5 1-6 11-6
        lookup.put("     *********   *********   *******  *  *******     ********    *******",3); // 5-4 3-7 15-8 5-10 4-11 15-12 7-13 15-15
        lookup.put("   **   *****  *    *****  *    ****        ****    **  ****************",14); // 5-1
        lookup.put("         ***  *    * *****     * ****   *  * ***         ***     *   ***",20); // 6-5
        lookup.put("     *******  *  *********   ********   ********     *******     *******",2); // 13-4 9-5 4-7 11-7 14-7 5-9 10-11 9-12 3-13 6-13 5-14 11-14 4-15 14-15
        lookup.put("**  *********   *********   ********     *******     ********** ********",4); // 14-3 10-5 13-5 1-10 3-10 13-10 15-10 12-11 3-12 11-13 1-14 15-14 3-15 13-15
        lookup.put("     ********    ********    *******  *  *******     ********    *******",8); // 13-2 13-9
        lookup.put("*    ******** **********     **********  *******     ********    *******",5); // 5-2 7-4 10-9 4-13
        lookup.put("  *      *****       ****   *  * ***         ***     *   ***************",26); // 11-1
        lookup.put("   *   *****   *   ******  **  ******  **  *****        ****        ****",11); // 9-4 1-5 15-6 1-8 11-12
        lookup.put("  *  *******     ********    ********    ********   ********************",9); // 11-1
        lookup.put("  *   * ******   ** *****   *** ****         ***         ***********    ",21); // 9-1 13-1
        lookup.put("   ***  *****  **  ******  **  *****    *  *****    *  *****************",17); // 5-1
        lookup.put("   ***  *****  **  ******  **  *****    *  *****    *  ***********      ",17); // 10-1
        lookup.put("*   *********   ********     *******     ********** ***************     ",4); // 1-1 3-1 4-1
        lookup.put("   *   ******  **  ******  **  *****        ****        ****************",11); // 13-1
        lookup.put("  *    * *****     * ****   *  * ***         ***     *   ***************",20); // 7-1
        lookup.put("  *      *****   **  ****   **  ****         ***         ***************",22); // 6-1
        lookup.put("**   *********   *******  *  *******     ********    *******************",3); // 2-1
        lookup.put("*    ********    *******  *  *******     ********    *******************",8); // 11-1 14-1
        lookup.put("  ***  ****** ***  ****** **  ******      ******************************",17); // 9-1 15-1
        lookup.put("    ********    ********  * *********   ********************************",6); // 3-1
        lookup.put("*   *   ****  *     ******      *****  *  * ****        ****************",26); // 2-2 3-3 1-15
        lookup.put("  *     ******  *   *****  *  * ****        ****************************",23); // 11-1
        lookup.put("  *   ******* **  ******* **  ******       *****************************",11); // 13-1
        lookup.put("  *  * ****** ***  ****** **  ******       *****************************",12); // 8-1
        lookup.put("*   *********   ********    ********    ********************************",4); // 4-1
        lookup.put("  *   * ******    * *****  *  * ****        ****************************",20); // 15-1
        lookup.put("  * **********  *********  *********    ********************************",2); // 1-1 6-1
        lookup.put("  * *  *******  ** ******  *** *****        ****************************",21); // 3-1
        lookup.put("  ***  ****** ***  ****** **  ******      ***************************   ",17); // 13-1
        lookup.put("**  **********  **********  *********  **********  *********************",7); // 11-1 13-1
        lookup.put("   *  * *****  *  * *****  *  * ****        ****    *   ****************",10); // 1-1 3-1
        lookup.put("  *    *****  *  * ****** *    ****** *    *****       *****************",19); // 1-4 11-4 9-5 6-9 14-10 3-11 7-11 9-11 1-13 11-13 10-14
        lookup.put("  *  * ****** *    ****** *    *****       *****************************",19); // 12-1
        lookup.put("  *    *****  *    ****** *    ****** *  * *****       *****************",18); // 1-2 6-2 10-2 2-3 8-3 12-3 15-3 12-5 7-6 5-7 7-7 10-7 5-9 9-9 11-9 1-11 11-11 8-12 4-13 5-13 13-13 3-14 6-14 15-15
        lookup.put("  *  *   *****       ****   *    ***         ***     **  ***************",24); // 4-1
        lookup.put("  *   * ******   ** *****   *** ****         ***         ***************",21); // 13-1
        lookup.put("   *    *****  *    *****  *  * ****        ****        ****************",18); // 1-1
        lookup.put("     *******     *******  *  ********    ********    *******************",6); // 15-1
        lookup.put("   *    ****   *    *****  *    *****  *  * ****        ****        ****",18); // 4-3 15-6 8-7 13-7 1-8 5-8 3-9 4-9 6-9 3-10 11-12
        lookup.put("   *    *****  *    *****  *  * ****        ****    *   ****************",16); // 5-1 10-1 14-1
        lookup.put("   **   *****  *    *****  *    ****        ****    **  **********     *",14); // 6-1
        lookup.put("   *    ****   *  * *****  *    *****  *    ****        ****        ****",19); // 9-4 4-7 10-7 13-9 1-11 10-11 3-12 5-15 13-15
        lookup.put("   *  * *****  *    *****  *    ****        ****        ****************",19); // 3-1
        lookup.put("   ***  *****  **  ******  **  *****    *  *****    *  ***************  ",17); // 14-1
        lookup.put("  *    *******       ****   **** ***         ***         ***************",25); // 9-1
        lookup.put("   *   ******  **  ******  **  *****        ****        **************  ",11); // 12-1

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

    public boolean                                                                                                                                                                                                                                                                                                      getPixel(int x, int y) {
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
//        outputCell(x,y,100,100);

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

        // Generate a set of individual images.
        try {
            BufferedImage subImage = this.image.getSubimage(xDetail.start, yDetail.start, xDetail.end - xDetail.start, yDetail.end - yDetail.start);

            File outputFile = new File("/home/jason/Downloads/Crossword_" + String.format("%02d",x+1) + "_" + String.format("%02d",y+1) + ".png");
            ImageIO.write(subImage, "png", outputFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

//        System.out.println("|" + id + "|");


        if(lookup.containsKey(id.toString())) {
            return lookup.get(id.toString());
        }

        System.out.println("(" + x + "," + y + ") -> |" + id + "|");

        // Add to the unknowns.
        List<String> coords = new ArrayList<>();
        if(unknown.containsKey(id.toString())) {
            coords = unknown.get(id.toString());
        } else {
            unknown.put(id.toString(),coords);
        }
        coords.add((x + 1) + "-" + (y + 1));

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

    private static File[] findFiles(File directory, String pattern) {
        if(!directory.isDirectory()) {
            System.out.println("Not a directory: " + directory);
            return null;
        }

        return directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.matches(pattern);
            }
        });
    }

    private static void deleteFilesMatchingPattern(File directory, String pattern, List<String> keep) {
        File[] files = findFiles(directory, pattern);

        if(files != null) {
            for (File file : files) {
                if(!keep.contains(file.getName())) {
                    if (!file.delete()) {
                        System.out.println("Failed to deleted file: " + file);
                    }
                }
            }
        }
    }

    private static String findScreenShot(File directory, String pattern) {
        File[] files = findFiles(directory, pattern);

        if(files != null) {
            // Just return the first file.
            if(files.length > 0) {
                return files[0].getAbsolutePath();
            }
        }

        return null;
    }

    public static void main(String[] args) {
        // Details of the image.
        int blackBorderWidth = 2;
        int interLineWidth = 2;
        int squareWidth = 29;
        int squareHeight = 27;

        // Image is the crossword and is only black and white pixels.
        // The crossword has a border of white, and then a black border which is x pixels wide
        // Need to find the

        try {
            // Delete files names Crossword_x_y.png
            File directory = new File("/home/jason/Downloads");
            deleteFilesMatchingPattern(directory,"^Crossword_\\d\\d_\\d\\d\\.png$", new ArrayList<>());

            String filename = findScreenShot(directory,"^Screenshot from \\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d-\\d\\d-\\d\\d\\.png$");
            if(filename == null) {
                System.out.println("No screenshot found");
                return;
            }
            System.out.println(filename);

            // Load the image from file
            File imageFile = new File(filename);
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
                if(entry.getKey() < 10) {
                    System.out.println(" " + entry.getKey() + " " + entry.getValue());
                } else {
                    System.out.println(entry.getKey() + " " + entry.getValue());
                }
            }

            // Output any unknowns
            List<String> keepFiles = new ArrayList<>();
            for(Map.Entry<String,List<String>> entry : cwImage.getUnknowns().entrySet()) {
                StringBuilder logLine = new StringBuilder();
                //        lookup.put("************   *********   *********** *********** *********** *********",1);
                logLine.append("lookup.put(\"");
                logLine.append(entry.getKey());
                logLine.append("\",); //");

                boolean moreThanOne = false;
                for(String next : entry.getValue()) {
                    logLine.append(" ");
                    logLine.append(next);

                    if(!moreThanOne) {
                        // Delete the file for this.
                        String[] coordinates = next.split("-");
                        String x = coordinates[0];
                        if(x.length() == 1) {
                            x = "0" + x;
                        }

                        String y = coordinates[1];
                        if(y.length() == 1) {
                            y = "0" + y;
                        }

                        File toKeep = new File("/home/jason/Downloads/Crossword_" + x + "_" + y + ".png");
                        keepFiles.add(toKeep.getName());
                    }

                    moreThanOne = true;
                }

                System.out.println(logLine);
            }

            // Delete the files except the ones to keep.
            deleteFilesMatchingPattern(directory,"^Crossword_\\d\\d_\\d\\d\\.png$", keepFiles);
        } catch (IOException e) {
            System.out.println("Error loading image: " + e.getMessage());
        }
    }
}
