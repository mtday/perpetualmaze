package com.perpetualmaze.generator;

import com.perpetualmaze.model.*;

import java.util.Optional;
import java.util.Random;

import static com.perpetualmaze.model.ExitDirection.*;

public class MazeGenerator {
    public static Maze createMaze(int width, int height) {
        int level = 1;
        Random random = new Random(level);
        boolean[][] cellInclusion = new boolean[height][width];
        ExitDirection[][] exitDirections = new ExitDirection[height][width];
        Walls walls = new Walls(width, height);

        // all the left- and right-most edges are set to true
        for (int row = 0; row < height; row++) {
            walls.setLeftWall(new Coord(row, 0)); // all the column 0 left walls
            walls.setRightWall(new Coord(row, width - 1)); // all the right-most right walls
        }

        // start with a random entry point in the bottom
        Coord entry = new Coord(height - 1, random.nextInt(width));
        cellInclusion[entry.getRow()][entry.getCol()] = true;
        walls.clearBottomWall(entry);

        // start with a cell from the top row
        Coord start = new Coord(0, random.nextInt(width));
        Optional<Coord> excludedCell = Optional.of(start);
        while (excludedCell.isPresent()) {
            randomWalk(random, cellInclusion, exitDirections, walls, excludedCell.get(), width, height);
            excludedCell = getRandomExcludedCell(random, cellInclusion, width, height);
        }
        walls.clearTopWall(start); // include an exit to the top

        return new Maze()
                .setId(new MazeId().setLevel(level).setWidth(width).setHeight(height))
                .setSerialized(walls.serialize());
    }

    public static Maze createMaze(Maze previousMaze) {
        int level = previousMaze.getId().getLevel() + 1;
        int width = previousMaze.getId().getWidth();
        int height = previousMaze.getId().getHeight();

        Random random = new Random(level);
        boolean[][] cellInclusion = new boolean[height][width];
        ExitDirection[][] exitDirections = new ExitDirection[height][width];
        Walls walls = new Walls(width, height);

        // all the left- and right-most edges are set to true
        for (int row = 0; row < height; row++) {
            walls.setLeftWall(new Coord(row, 0)); // all the column 0 left walls
            walls.setRightWall(new Coord(row, width - 1)); // all the right-most right walls
        }

        // start with entry cells in the bottom for each exit from the previous maze
        Walls previousWalls = Walls.deserialize(previousMaze.getSerialized(), width, height);
        for (int col = 0; col < width; col++) {
            if (!previousWalls.hasTopWall(new Coord(0, col))) {
                cellInclusion[height - 1][col] = true;
                walls.clearAllWalls(new Coord(height - 1, col));
            }
        }

        // start with a cell from the top row
        Coord start = new Coord(0, random.nextInt(width));
        Optional<Coord> excludedCell = Optional.of(start);
        while (excludedCell.isPresent()) {
            randomWalk(random, cellInclusion, exitDirections, walls, excludedCell.get(), width, height);
            excludedCell = getRandomExcludedCell(random, cellInclusion, width, height);
        }
        walls.clearTopWall(start); // include an exit to the top

        return new Maze()
                .setId(new MazeId().setLevel(level).setWidth(width).setHeight(height))
                .setSerialized(walls.serialize());
    }

    private static void randomWalk(Random random,
                                   boolean[][] cellInclusion,
                                   ExitDirection[][] exitDirections,
                                   Walls walls,
                                   Coord start,
                                   int width,
                                   int height) {
        // randomly walk through cells til we find an included cell
        Coord currentCoord = new Coord(start);
        while (!cellInclusion[currentCoord.getRow()][currentCoord.getCol()]) {
            ExitDirection exitDirection = ExitDirection.random(random);
            exitDirections[currentCoord.getRow()][currentCoord.getCol()] = exitDirection;
            currentCoord.update(exitDirection);
            if (isInvalid(currentCoord, width, height)) {
                currentCoord.revert(exitDirection);
            }
        }
        // update all the cells along the path (excluding loops) and mark them as included
        // and also update the walls to reflect the new path
        currentCoord = new Coord(start);
        ExitDirection direction = null, prevDirection;
        while (!cellInclusion[currentCoord.getRow()][currentCoord.getCol()]) {
            cellInclusion[currentCoord.getRow()][currentCoord.getCol()] = true;
            prevDirection = direction;
            direction = exitDirections[currentCoord.getRow()][currentCoord.getCol()];
            walls.setAllWalls(currentCoord);
            if (direction == UP || prevDirection == DOWN) {
                walls.clearTopWall(currentCoord);
            }
            if (direction == DOWN || prevDirection == UP) {
                walls.clearBottomWall(currentCoord);
            }
            if (direction == LEFT || prevDirection == RIGHT) {
                walls.clearLeftWall(currentCoord);
            }
            if (direction == RIGHT || prevDirection == LEFT) {
                walls.clearRightWall(currentCoord);
            }
            currentCoord.update(direction);
        }
    }

    private static Optional<Coord> getRandomExcludedCell(Random random, boolean[][] cellInclusion, int width, int height) {
        int excludedCells = getExcludedCellCount(cellInclusion, width, height);
        if (excludedCells == 0) {
            return Optional.empty();
        }

        while (true) {
            // find a random starting cell
            Coord coord = new Coord(random.nextInt(height), random.nextInt(width));
            if (!cellInclusion[coord.getRow()][coord.getCol()]) {
                // if this random cell is excluded, return it
                return Optional.of(coord);
            }

            // pick a random direction (horizontally, vertically, or diagonally) to move away from this cell
            // and attempt to find an excluded cell in that direction
            // if the modifiers are both zero (no movement), then move on to the next random coordinate
            int rowModifier = random.nextInt(3) - 1; // -1, 0, or 1
            int colModifier = random.nextInt(3) - 1; // -1, 0, or 1
            boolean done = rowModifier == 0 && colModifier == 0;
            while (!done) {
                coord.update(rowModifier, colModifier);
                done = isInvalid(coord, width, height);
                if (!done && !cellInclusion[coord.getRow()][coord.getCol()]) {
                    return Optional.of(coord);
                }
            }
        }
    }

    private static int getExcludedCellCount(boolean[][] cellInclusion, int width, int height) {
        int count = 0;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                count += cellInclusion[row][col] ? 0 : 1;
            }
        }
        return count;
    }

    private static boolean isInvalid(Coord coord, int width, int height) {
        return coord.getRow() < 0 || coord.getCol() < 0 || coord.getRow() >= height || coord.getCol() >= width;
    }
}
