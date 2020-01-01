package com.perpetualmaze.generator;

import java.util.Random;

@SuppressWarnings("unused")
public enum ExitDirection {
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1);

    private final int rowChange;
    private final int colChange;

    ExitDirection(int rowChange, int colChange) {
        this.rowChange = rowChange;
        this.colChange = colChange;
    }

    public int getRowChange() {
        return rowChange;
    }

    public int getColChange() {
        return colChange;
    }

    public static ExitDirection random(Random random) {
        return values()[random.nextInt(values().length)];
    }
}
