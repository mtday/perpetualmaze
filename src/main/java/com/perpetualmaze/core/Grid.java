package com.perpetualmaze.core;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.BitSet;
import java.util.Optional;

import static java.lang.Character.isWhitespace;
import static java.util.Optional.empty;
import static java.util.Optional.of;

public class Grid {
    private final BitSet bitSet;
    private final int width;
    private final int height;

    public Grid(String grid) {
        String[] rows = grid.split(":");
        this.height = rows.length;
        this.width = rows[0].length();
        this.bitSet = new BitSet(width * height);

        for (int r = 0; r < height; r++) {
            char[] row = rows[r].toCharArray();
            for (int c = 0; c < row.length; c++) {
                bitSet.set(r * width + c, !isWhitespace(row[c]));
            }
        }
    }

    public Grid(int width, int height) {
        this.bitSet = new BitSet(width * height);
        this.width = width;
        this.height = height;
    }

    @JsonIgnore
    public int getWidth() {
        return width;
    }

    @JsonIgnore
    public int getHeight() {
        return height;
    }

    @JsonIgnore
    public BitSet getBitSet() {
        return bitSet;
    }

    @SuppressWarnings("unused")
    public String getGrid() {
        return serialize();
    }

    private boolean isSet(int row, int col) {
        return bitSet.get(row * getWidth() + col);
    }

    private void set(int row, int col) {
        bitSet.set(row * getWidth() + col);
    }

    private void clear(int row, int col) {
        bitSet.clear(row * getWidth() + col);
    }

    public Optional<Location> canPlace(Grid other) {
        for (int r = 0; r < getHeight(); r++) {
            for (int c = 0; c < getWidth(); c++) {
                if (canPlace(other, r, c)) {
                    return of(new Location(r, c));
                }
            }
        }
        return empty();
    }

    public boolean canPlace(Grid other, int r, int c) {
        for (int or = 0; or < other.getHeight(); or++) {
            for (int oc = 0; oc < other.getWidth(); oc++) {
                if (other.isSet(or, oc) && isSet(r + or, c + oc)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void place(Grid other, int r, int c) {
        for (int or = 0; or < other.getHeight(); or++) {
            for (int oc = 0; oc < other.getWidth(); oc++) {
                if (other.isSet(or, + oc)) {
                    set(r + or, c + oc);
                }
            }
        }
    }

    public void clearFull() {
        // Check the rows
        for (int r = 0; r < getHeight(); r++) {
            boolean allSet = true;

            for (int c = 0; c < getWidth(); c++) {
                allSet &= isSet(r, c);
            }
            if (allSet) {
                clearRow(r);
            }
        }
        // Check the columns
        for (int c = 0; c < getWidth(); c++) {
            boolean allSet = true;
            for (int r = 0; r < getHeight(); r++) {
                allSet &= isSet(r, c);
            }
            if (allSet) {
                clearColumn(c);
            }
        }
    }

    private void clearRow(int row) {
        for (int c = 0; c < getWidth(); c++) {
            clear(row, c);
        }
    }

    private void clearColumn(int col) {
        for (int r = 0; r < getHeight(); r++) {
            clear(r, col);
        }
    }

    public String serialize() {
        StringBuilder grid = new StringBuilder();
        for (int r = 0; r < getHeight(); r++) {
            for (int c = 0; c < getWidth(); c++) {
                grid.append(isSet(r, c) ? 'O' : ' ');
            }
            if (r < getHeight() - 1) {
                grid.append(":");
            }
        }
        return grid.toString();
    }
}
