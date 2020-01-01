package com.perpetualmaze.generator;

public class Coord {
    private int row;
    private int col;

    public Coord(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void update(int rowModifier, int colModifier) {
        row += rowModifier;
        col += colModifier;
    }

    public void update(ExitDirection exitDirection) {
        update(exitDirection.getRowChange(), exitDirection.getColChange());
    }

    public void revert(ExitDirection exitDirection) {
        update(exitDirection.getRowChange() * -1, exitDirection.getColChange() * -1);
    }

    @Override
    public String toString() {
        return String.format("%d,%d", row, col);
    }
}
