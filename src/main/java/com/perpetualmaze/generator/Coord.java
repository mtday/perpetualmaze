package com.perpetualmaze.generator;

public class Coord {
    private int row;
    private int col;

    public Coord(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Coord(Coord other) {
        this.row = other.getRow();
        this.col = other.getCol();
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Coord update(int rowModifier, int colModifier) {
        row += rowModifier;
        col += colModifier;
        return this;
    }

    public Coord update(ExitDirection exitDirection) {
        return update(exitDirection.getRowChange(), exitDirection.getColChange());
    }

    public Coord revert(ExitDirection exitDirection) {
        return update(exitDirection.getRowChange() * -1, exitDirection.getColChange() * -1);
    }

    @Override
    public String toString() {
        return String.format("%d,%d", row, col);
    }
}
