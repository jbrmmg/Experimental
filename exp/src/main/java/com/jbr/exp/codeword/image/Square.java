package com.jbr.exp.codeword.image;

public class Square {
    private final Coordinate bottomLeft;
    private final Coordinate topRight;

    public Square(Coordinate bottomLeft, Coordinate topRight) {
        this.bottomLeft = bottomLeft;
        this.topRight = topRight;
    }

    public Coordinate getBottomLeft() {
        return bottomLeft;
    }

    public Coordinate getTopRight() {
        return topRight;
    }

    public boolean coordinateIsWithin(Coordinate coordinate) {
        return coordinate.getX() >= bottomLeft.getX() && coordinate.getX() <= topRight.getX() && coordinate.getY() >= bottomLeft.getY() && coordinate.getY() <= topRight.getY();
    }
}
