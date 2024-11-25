package com.jbr.exp.codeword.image.pixel;

import com.jbr.exp.codeword.image.Coordinate;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class Pixels {
    static Logger log = Logger.getLogger(Pixels.class.getName());

    private final Map<String, Pixel> image;
    private int height;
    private int width;

    public Pixels() {
        this.image = new HashMap<>();
        height = 0;
        width = 0;
    }

    private String getCoordinate(Coordinate coordinate) {
        return Integer.toString(coordinate.getX()) + "x" + Integer.toString(coordinate.getY());
    }

    public void setPixel(Coordinate coordinate, int scale) {
        if(this.image.containsKey(getCoordinate(coordinate))) {
            log.warn("Pixel already set: " + getCoordinate(coordinate));
        } else {
            this.image.put(getCoordinate(coordinate),new Pixel(scale));
        }

        if(coordinate.getY() > this.height) {
            this.height = coordinate.getY();
        }

        if(coordinate.getX() > this.width) {
            this.width = coordinate.getX();
        }
    }

    public Pixel getPixel(Coordinate coordinate) {
        if(this.image.containsKey(getCoordinate(coordinate))) {
            return this.image.get(getCoordinate(coordinate));
        }

        return null;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public boolean isWhite(Coordinate coordinate) {
        Pixel pixel = getPixel(coordinate);
        if(pixel == null) {
            return false;
        }

        return pixel.isWhite();
    }
}
