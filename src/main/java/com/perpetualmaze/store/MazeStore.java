package com.perpetualmaze.store;

import com.perpetualmaze.model.Maze;
import com.perpetualmaze.model.MazeId;

import java.util.Optional;

public interface MazeStore {
    Optional<Maze> get(MazeId id);

    boolean save(Maze maze);

    boolean delete(MazeId id);
}
