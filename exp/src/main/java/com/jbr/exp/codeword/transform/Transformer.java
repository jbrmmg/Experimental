package com.jbr.exp.codeword.transform;

import com.jbr.exp.codeword.image.Coordinate;
import com.jbr.exp.codeword.image.Image;
import com.jbr.exp.codeword.image.Square;

import java.util.ArrayList;
import java.util.List;

public class Transformer {
    private static Square getSquare(Image image, Coordinate coordinate, List<Square> existing) {
        // Is it a white pixel?
        if(image.isWhitePixel(coordinate)) {
            return null;
        }

        // Is it within an existing square? if yes then ignore it.
        for(Square square : existing) {
            if(square.coordinateIsWithin(coordinate)) {
                return null;
            }
        }

        //

        return null;
    }

    public static List<Square> imageToSquares(Image image) {
        List<Square> result = new ArrayList<>();

        // Find squares within the image;
        // Squares will be mostly be white background and bordered by a square, a digit in top left and possibly a letter in the middle.
        // There will always be a border of non-white that is at least two pixels wide.

        for(int x = 0; x < image.getWidth(); x++) {
            for(int y = 0; y < image.getHeight(); y++) {
                Square newSquare = getSquare(image, new Coordinate(x,y), result);
                if(newSquare != null) {
                    result.add(newSquare);
                }
            }
        }

        return result;
    }
}
