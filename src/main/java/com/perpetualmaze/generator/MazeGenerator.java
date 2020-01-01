package com.perpetualmaze.generator;

import java.util.Optional;
import java.util.Random;

public class MazeGenerator {
    private final short width;
    private final short height;
    private final long randomSeed;

    public MazeGenerator(int width, int height, long randomSeed) {
        this.width = (short) width;
        this.height = (short) height;
        this.randomSeed = randomSeed;
    }

    public Maze createFirstMaze() {
        Random random = new Random(randomSeed);
        boolean[][] cellInclusion = new boolean[height][width];
        ExitDirection[][] exitDirections = new ExitDirection[height][width];
        Walls walls = new Walls(width, height);

        // all the left- and right-most edges are set to true
        for (int row = 0; row <= height; row++) {
            walls.setLeftWall(new Coord(row, 0)); // all the column 0 left walls
            walls.setRightWall(new Coord(row, width)); // all the right-most right walls
        }

        // start with one cell included
        Coord coord = new Coord(random.nextInt(height), random.nextInt(width));
        cellInclusion[coord.getRow()][coord.getCol()] = true;
        walls.setAllWalls(coord);

        Optional<Coord> excludedCell = getRandomExcludedCell(random, cellInclusion);
        while (excludedCell.isPresent()) {
            randomWalk(random, cellInclusion, exitDirections, walls, excludedCell.get());
            excludedCell = getRandomExcludedCell(random, cellInclusion);
        }

        return new Maze()
                .setMinLevel(1)
                .setWidth(width)
                .setHeight(height)
                .setWalls(walls);
    }

    private void randomWalk(Random random,
                            boolean[][] cellInclusion,
                            ExitDirection[][] exitDirections,
                            Walls walls,
                            Coord start) {
        // randomly walk through cells til we find an included cell
        Coord currentCoord = start;
        while (!cellInclusion[currentCoord.getRow()][currentCoord.getCol()]) {
            ExitDirection exitDirection = ExitDirection.random(random);
            exitDirections[currentCoord.getRow()][currentCoord.getCol()] = exitDirection;
            currentCoord.update(exitDirection);
            if (isInvalid(currentCoord)) {
                currentCoord.revert(exitDirection);
            }
        }
        // update all the cells along the path (excluding loops) and mark them as included
        currentCoord = start;
        while (!cellInclusion[currentCoord.getRow()][currentCoord.getCol()]) {
            cellInclusion[currentCoord.getRow()][currentCoord.getCol()] = true;
            ExitDirection exitDirection = exitDirections[currentCoord.getRow()][currentCoord.getCol()];
            currentCoord.update(exitDirection);
        }
    }

    private Optional<Coord> getRandomExcludedCell(Random random, boolean[][] cellInclusion) {
        int excludedCells = getExcludedCellCount(cellInclusion);
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
            int rowModifier = random.nextInt(3) - 1; // -1, 0, or 1
            int colModifier = random.nextInt(3) - 1; // -1, 0, or 1
            boolean done = false;
            while (!done) {
                coord.update(rowModifier, colModifier);
                done = isInvalid(coord);
                if (!done && !cellInclusion[coord.getRow()][coord.getCol()]) {
                    return Optional.of(coord);
                }
            }
        }
    }

    private int getExcludedCellCount(boolean[][] cellInclusion) {
        int count = 0;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                count += cellInclusion[row][col] ? 0 : 1;
            }
        }
        return count;
    }

    private boolean isInvalid(Coord coord) {
        return coord.getRow() < 0 || coord.getCol() < 0 || coord.getRow() >= height || coord.getCol() >= width;
    }

}
