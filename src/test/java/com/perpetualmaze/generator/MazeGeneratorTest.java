package com.perpetualmaze.generator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MazeGeneratorTest {
    private static final long RANDOM_SEED = 1234;

    @Test
    public void testInitialMaze4() {
        MazeGenerator mazeGenerator = new MazeGenerator(4, 4, RANDOM_SEED);
        Maze maze = mazeGenerator.createMaze(null);

        assertEquals(1, maze.getMinLevel());
        assertEquals(4, maze.getWidth());
        assertEquals(4, maze.getHeight());
    }

    @Test
    public void testInitialMaze8() {
        MazeGenerator mazeGenerator = new MazeGenerator(8, 8, RANDOM_SEED);
        Maze maze = mazeGenerator.createMaze(null);

        assertEquals(1, maze.getMinLevel());
        assertEquals(8, maze.getWidth());
        assertEquals(8, maze.getHeight());
    }

    @Test
    public void testInitialMaze16() {
        MazeGenerator mazeGenerator = new MazeGenerator(16, 16, RANDOM_SEED);
        Maze maze = mazeGenerator.createMaze(null);

        assertEquals(1, maze.getMinLevel());
        assertEquals(16, maze.getWidth());
        assertEquals(16, maze.getHeight());
    }

    @Test
    public void testInitialMaze100() {
        MazeGenerator mazeGenerator = new MazeGenerator(100, 100, RANDOM_SEED);
        Maze maze = mazeGenerator.createMaze(null);

        assertEquals(1, maze.getMinLevel());
        assertEquals(100, maze.getWidth());
        assertEquals(100, maze.getHeight());
    }

    @Test
    public void testFollowOnMaze4() {
        MazeGenerator mazeGenerator = new MazeGenerator(4, 4, RANDOM_SEED);
        Maze initialMaze = mazeGenerator.createMaze(null);
        Maze maze = mazeGenerator.createMaze(initialMaze);

        assertEquals(5, maze.getMinLevel());
        assertEquals(4, maze.getWidth());
        assertEquals(4, maze.getHeight());
    }

    @Test
    public void testFollowOnMaze8() {
        MazeGenerator mazeGenerator = new MazeGenerator(8, 8, RANDOM_SEED);
        Maze initialMaze = mazeGenerator.createMaze(null);
        Maze maze = mazeGenerator.createMaze(initialMaze);

        assertEquals(9, maze.getMinLevel());
        assertEquals(8, maze.getWidth());
        assertEquals(8, maze.getHeight());
    }

    @Test
    public void testFollowOnMaze16() {
        MazeGenerator mazeGenerator = new MazeGenerator(16, 16, RANDOM_SEED);
        Maze initialMaze = mazeGenerator.createMaze(null);
        Maze maze = mazeGenerator.createMaze(initialMaze);

        assertEquals(17, maze.getMinLevel());
        assertEquals(16, maze.getWidth());
        assertEquals(16, maze.getHeight());
    }

    @Test
    public void testFollowOnMaze100() {
        MazeGenerator mazeGenerator = new MazeGenerator(100, 100, RANDOM_SEED);
        Maze initialMaze = mazeGenerator.createMaze(null);
        Maze maze = mazeGenerator.createMaze(initialMaze);

        assertEquals(101, maze.getMinLevel());
        assertEquals(100, maze.getWidth());
        assertEquals(100, maze.getHeight());
    }
}
