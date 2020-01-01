package com.perpetualmaze.generator;

public class Maze {
    private long minLevel;
    private int width;
    private int height;
    private Walls walls;

    public long getMinLevel() {
        return minLevel;
    }

    public Maze setMinLevel(long minLevel) {
        this.minLevel = minLevel;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public Maze setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public Maze setHeight(int height) {
        this.height = height;
        return this;
    }

    public Walls getWalls() {
        return walls;
    }

    public Maze setWalls(Walls walls) {
        this.walls = walls;
        return this;
    }
}
