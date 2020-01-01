package com.perpetualmaze.generator;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Maze maze = (Maze) o;
        return minLevel == maze.minLevel &&
                width == maze.width &&
                height == maze.height &&
                Objects.equals(walls, maze.walls);
    }

    @Override
    public int hashCode() {
        return Objects.hash(minLevel, width, height, walls);
    }

    @Override
    public String toString() {
        return "Min Level: " + minLevel + "\n" + walls.toString();
    }
}
