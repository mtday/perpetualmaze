package com.perpetualmaze.model;

import java.util.Objects;

public class Maze {
    private MazeId id;
    private String serialized;

    public MazeId getId() {
        return id;
    }

    public Maze setId(MazeId id) {
        this.id = id;
        return this;
    }

    public String getSerialized() {
        return serialized;
    }

    public Maze setSerialized(String serialized) {
        this.serialized = serialized;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Maze maze = (Maze) o;
        return Objects.equals(id, maze.id) &&
                Objects.equals(serialized, maze.serialized);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serialized);
    }

    @Override
    public String toString() {
        return "Maze{" +
                "id=" + id +
                ", serialized='" + serialized + '\'' +
                '}';
    }
}
