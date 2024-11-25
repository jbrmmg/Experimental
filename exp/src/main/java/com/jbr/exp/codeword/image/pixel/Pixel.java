package com.jbr.exp.codeword.image.pixel;

public class Pixel {
    private final int scale;

    public Pixel(int scale) {
        this.scale = scale;
    }

    public int getScale() {
        return scale;
    }

    public boolean isBlack() {
        return this.scale == 0;
    }

    public boolean isWhite() {
        return this.scale == 255;
    }
}
