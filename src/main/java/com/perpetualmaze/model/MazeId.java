package com.perpetualmaze.model;

import java.util.Objects;

public class MazeId {
    private int level;
    private int width;
    private int height;

    public int getLevel() {
        return level;
    }

    public MazeId setLevel(int level) {
        this.level = level;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public MazeId setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public MazeId setHeight(int height) {
        this.height = height;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MazeId mazeId = (MazeId) o;
        return level == mazeId.level &&
                width == mazeId.width &&
                height == mazeId.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(level, width, height);
    }

    @Override
    public String toString() {
        return "MazeId{" +
                "level=" + level +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
