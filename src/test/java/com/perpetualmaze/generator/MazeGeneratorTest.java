package com.perpetualmaze.generator;

import com.perpetualmaze.model.Maze;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MazeGeneratorTest {
    private static final long RANDOM_SEED = 1234;

    @Test
    public void testInitialMaze4x4() {
        Maze maze = MazeGenerator.createMaze(4, 4);

        assertEquals(1, maze.getId().getLevel());
        assertEquals(4, maze.getId().getWidth());
        assertEquals(4, maze.getId().getHeight());
    }

    @Test
    public void testInitialMaze8x8() {
        Maze maze = MazeGenerator.createMaze(8, 8);

        assertEquals(1, maze.getId().getLevel());
        assertEquals(8, maze.getId().getWidth());
        assertEquals(8, maze.getId().getHeight());
    }

    @Test
    public void testInitialMaze16x16() {
        Maze maze = MazeGenerator.createMaze(16, 16);

        assertEquals(1, maze.getId().getLevel());
        assertEquals(16, maze.getId().getWidth());
        assertEquals(16, maze.getId().getHeight());
    }

    @Test
    public void testInitialMaze16x12() {
        Maze maze = MazeGenerator.createMaze(16, 12);

        assertEquals(1, maze.getId().getLevel());
        assertEquals(16, maze.getId().getWidth());
        assertEquals(12, maze.getId().getHeight());
    }

    @Test
    public void testInitialMaze100() {
        Maze maze = MazeGenerator.createMaze(100, 100);

        assertEquals(1, maze.getId().getLevel());
        assertEquals(100, maze.getId().getWidth());
        assertEquals(100, maze.getId().getHeight());
    }

    @Test
    public void testFollowOnMaze4x4() {
        Maze initialMaze = MazeGenerator.createMaze(4, 4);
        Maze maze = MazeGenerator.createMaze(initialMaze);

        assertEquals(2, maze.getId().getLevel());
        assertEquals(4, maze.getId().getWidth());
        assertEquals(4, maze.getId().getHeight());
    }

    @Test
    public void testFollowOnMaze8x8() {
        Maze initialMaze = MazeGenerator.createMaze(8, 8);
        Maze maze = MazeGenerator.createMaze(initialMaze);

        assertEquals(2, maze.getId().getLevel());
        assertEquals(8, maze.getId().getWidth());
        assertEquals(8, maze.getId().getHeight());
    }

    @Test
    public void testFollowOnMaze16x16() {
        Maze initialMaze = MazeGenerator.createMaze(16, 16);
        Maze maze = MazeGenerator.createMaze(initialMaze);

        assertEquals(2, maze.getId().getLevel());
        assertEquals(16, maze.getId().getWidth());
        assertEquals(16, maze.getId().getHeight());
    }

    @Test
    public void testFollowOnMaze100x100() {
        Maze initialMaze = MazeGenerator.createMaze(100, 100);
        Maze maze = MazeGenerator.createMaze(initialMaze);

        assertEquals(2, maze.getId().getLevel());
        assertEquals(100, maze.getId().getWidth());
        assertEquals(100, maze.getId().getHeight());
    }

    @Test
    public void testMazeChain16x16() {
        Maze maze = MazeGenerator.createMaze(16, 16);
        for (int i = 0; i < 10; i++) {
            maze = MazeGenerator.createMaze(maze);
        }

        assertEquals(11, maze.getId().getLevel());
        assertEquals(16, maze.getId().getWidth());
        assertEquals(16, maze.getId().getHeight());
    }
}
