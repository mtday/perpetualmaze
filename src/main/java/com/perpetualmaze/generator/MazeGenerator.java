package com.perpetualmaze.generator;

import java.util.Optional;
import java.util.Random;

import static com.perpetualmaze.generator.ExitDirection.*;

public class MazeGenerator {
    private final short width;
    private final short height;
    private final long randomSeed;

    public MazeGenerator(int width, int height, long randomSeed) {
        this.width = (short) width;
        this.height = (short) height;
        this.randomSeed = randomSeed;
    }

    public Maze createMaze(Maze previousMaze) {
        Random random = new Random(randomSeed * (previousMaze == null ? 1 : previousMaze.getMinLevel()));
        boolean[][] cellInclusion = new boolean[height][width];
        ExitDirection[][] exitDirections = new ExitDirection[height][width];
        Walls walls = new Walls(width, height);

        // all the left- and right-most edges are set to true
        for (int row = 0; row < height; row++) {
            walls.setLeftWall(new Coord(row, 0)); // all the column 0 left walls
            walls.setRightWall(new Coord(row, width - 1)); // all the right-most right walls
        }

        if (previousMaze != null) {
            // start with entry cells in the bottom for each exit from the previous maze
            for (int col = 0; col < width; col++) {
                if (!previousMaze.getWalls().hasTopWall(new Coord(0, col))) {
                    cellInclusion[height - 1][col] = true;
                    walls.clearAllWalls(new Coord(height - 1, col));
                }
            }
        } else {
            // start with a random entry point in the bottom
            Coord entry = new Coord(height - 1, random.nextInt(width));
            cellInclusion[entry.getRow()][entry.getCol()] = true;
            walls.clearBottomWall(entry);
        }

        // start with a cell from the top row
        Coord start = new Coord(0, random.nextInt(width));
        Optional<Coord> excludedCell = Optional.of(start);
        while (excludedCell.isPresent()) {
            randomWalk(random, cellInclusion, exitDirections, walls, excludedCell.get());
            excludedCell = getRandomExcludedCell(random, cellInclusion);
        }
        walls.clearTopWall(start); // include an exit to the top

        /*
        while (!findSolution(walls)) {
            // randomly open up a gap along the top wall to allow exit from this maze
            walls.clearTopWall(new Coord(0, random.nextInt(width)));
        }
         */

        return new Maze()
                .setMinLevel(previousMaze == null ? 1 : previousMaze.getMinLevel() + height)
                .setWidth(width)
                .setHeight(height)
                .setWalls(walls);
    }

    /*
    private boolean findSolution(Walls walls) {
        boolean[][] seen = new boolean[height][width];
        Queue<Coord> toCheck = new ArrayDeque<>();

        // add maze entry coords into the list
        for (int col = 0; col < width; col++) {
            Coord coord = new Coord(height - 1, col);
            if (!walls.hasBottomWall(coord)) {
                toCheck.offer(coord);
            }
        }

        // do a breadth-first search
        while (!toCheck.isEmpty()) {
            Coord current = toCheck.poll();
            if (isInvalid(current) || seen[current.getRow()][current.getCol()]) {
                continue;
            }

            seen[current.getRow()][current.getCol()] = true;
            if (current.getRow() == 0 && !walls.hasTopWall(current)) {
                // found an exit
                return true;
            }
            if (!walls.hasTopWall(current)) {
                toCheck.offer(new Coord(current).update(UP));
            }
            if (!walls.hasBottomWall(current)) {
                toCheck.offer(new Coord(current).update(DOWN));
            }
            if (!walls.hasLeftWall(current)) {
                toCheck.offer(new Coord(current).update(LEFT));
            }
            if (!walls.hasRightWall(current)) {
                toCheck.offer(new Coord(current).update(RIGHT));
            }
        }
        return false;
    }
     */

    private void randomWalk(Random random,
                            boolean[][] cellInclusion,
                            ExitDirection[][] exitDirections,
                            Walls walls,
                            Coord start) {
        // randomly walk through cells til we find an included cell
        Coord currentCoord = new Coord(start);
        while (!cellInclusion[currentCoord.getRow()][currentCoord.getCol()]) {
            ExitDirection exitDirection = ExitDirection.random(random);
            exitDirections[currentCoord.getRow()][currentCoord.getCol()] = exitDirection;
            currentCoord.update(exitDirection);
            if (isInvalid(currentCoord)) {
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
            // if the modifiers are both zero (no movement), then move on to the next random coordinate
            int rowModifier = random.nextInt(3) - 1; // -1, 0, or 1
            int colModifier = random.nextInt(3) - 1; // -1, 0, or 1
            boolean done = rowModifier == 0 && colModifier == 0;
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
