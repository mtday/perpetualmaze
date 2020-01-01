package com.perpetualmaze.store.jdbc;

import com.perpetualmaze.generator.MazeGenerator;
import com.perpetualmaze.model.Maze;
import com.perpetualmaze.store.ExternalDataSourceResource;
import com.perpetualmaze.store.MazeStore;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class JdbcMazeStoreTest {
    @ClassRule
    public static final ExternalDataSourceResource DS = new ExternalDataSourceResource();

    private MazeStore mazeStore;

    @Before
    public void before() {
        mazeStore = new JdbcMazeStore(DS.getDataSource());
    }

    @Test
    public void test() {
        Maze maze = MazeGenerator.createMaze(10, 10);
        assertFalse(mazeStore.get(maze.getId()).isPresent()); // does not exist

        assertTrue(mazeStore.save(maze)); // did not exist so save returns true

        Optional<Maze> fromDb = mazeStore.get(maze.getId());
        assertTrue(fromDb.isPresent());
        assertEquals(maze, fromDb.get());

        assertFalse(mazeStore.save(maze)); // already exists so save returns false

        assertTrue(mazeStore.delete(maze.getId())); // exists so delete returns true
        assertFalse(mazeStore.get(maze.getId()).isPresent()); // now gone

        assertFalse(mazeStore.delete(maze.getId())); // not found so delete returns false
    }
}
