package com.perpetualmaze.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class WallsTest {
    private Stream<Coord> getCoordStream(Walls walls) {
        List<Coord> coords = new ArrayList<>(walls.getWidth() * walls.getHeight());
        for (int row = 0; row < walls.getHeight(); row++) {
            for (int col = 0; col < walls.getWidth(); col++) {
                coords.add(new Coord(row, col));
            }
        }
        return coords.stream();
    }

    private void testTopWall(Walls walls, Coord coord) {
        assertFalse(walls.hasTopWall(coord));
        walls.setTopWall(coord);
        assertTrue(walls.hasTopWall(coord));

        // only 1 top wall should exist
        assertEquals(1, getCoordStream(walls).mapToInt(c -> walls.hasTopWall(c) ? 1 : 0).sum());

        walls.clearTopWall(coord);
        assertFalse(walls.hasTopWall(coord));
        assertEquals(0, getCoordStream(walls).mapToInt(c -> walls.hasTopWall(c) ? 1 : 0).sum());
    }

    @Test
    public void testTopWallTopLeft() {
        testTopWall(new Walls(3, 4), new Coord(0, 0));
    }

    @Test
    public void testTopWallTopMid() {
        testTopWall(new Walls(3, 4), new Coord(0, 1));
    }

    @Test
    public void testTopWallTopRight() {
        testTopWall(new Walls(3, 4), new Coord(0, 2));
    }

    @Test
    public void testTopWallMidLeft() {
        testTopWall(new Walls(3, 4), new Coord(1, 0));
    }

    @Test
    public void testTopWallMidMid() {
        testTopWall(new Walls(3, 4), new Coord(1, 1));
    }

    @Test
    public void testTopWallMidRight() {
        testTopWall(new Walls(3, 4), new Coord(1, 2));
    }

    @Test
    public void testTopWallBottomLeft() {
        testTopWall(new Walls(3, 4), new Coord(3, 0));
    }

    @Test
    public void testTopWallBottomMid() {
        testTopWall(new Walls(3, 4), new Coord(3, 1));
    }

    @Test
    public void testTopWallBottomRight() {
        testTopWall(new Walls(3, 4), new Coord(3, 2));
    }

    private void testLeftWall(Walls walls, Coord coord) {
        assertFalse(walls.hasLeftWall(coord));
        walls.setLeftWall(coord);
        assertTrue(walls.hasLeftWall(coord));

        // only 1 left wall should exist
        assertEquals(1, getCoordStream(walls).mapToInt(c -> walls.hasLeftWall(c) ? 1 : 0).sum());

        walls.clearLeftWall(coord);
        assertFalse(walls.hasLeftWall(coord));
        assertEquals(0, getCoordStream(walls).mapToInt(c -> walls.hasLeftWall(c) ? 1 : 0).sum());
    }

    @Test
    public void testLeftWallTopLeft() {
        testLeftWall(new Walls(3, 4), new Coord(0, 0));
    }

    @Test
    public void testLeftWallTopMid() {
        testLeftWall(new Walls(3, 4), new Coord(0, 1));
    }

    @Test
    public void testLeftWallTopRight() {
        testLeftWall(new Walls(3, 4), new Coord(0, 2));
    }

    @Test
    public void testLeftWallMidLeft() {
        testLeftWall(new Walls(3, 4), new Coord(1, 0));
    }

    @Test
    public void testLeftWallMidMid() {
        testLeftWall(new Walls(3, 4), new Coord(1, 1));
    }

    @Test
    public void testLeftWallMidRight() {
        testLeftWall(new Walls(3, 4), new Coord(1, 2));
    }

    @Test
    public void testLeftWallBottomLeft() {
        testLeftWall(new Walls(3, 4), new Coord(3, 0));
    }

    @Test
    public void testLeftWallBottomMid() {
        testLeftWall(new Walls(3, 4), new Coord(3, 1));
    }

    @Test
    public void testLeftWallBottomRight() {
        testLeftWall(new Walls(3, 4), new Coord(3, 2));
    }

    private void testBottomWall(Walls walls, Coord coord) {
        assertFalse(walls.hasBottomWall(coord));
        walls.setBottomWall(coord);
        assertTrue(walls.hasBottomWall(coord));

        // only 1 bottom wall should exist
        assertEquals(1, getCoordStream(walls).mapToInt(c -> walls.hasBottomWall(c) ? 1 : 0).sum());

        walls.clearBottomWall(coord);
        assertFalse(walls.hasBottomWall(coord));
        assertEquals(0, getCoordStream(walls).mapToInt(c -> walls.hasBottomWall(c) ? 1 : 0).sum());
    }

    @Test
    public void testBottomWallTopLeft() {
        testBottomWall(new Walls(3, 4), new Coord(0, 0));
    }

    @Test
    public void testBottomWallTopMid() {
        testBottomWall(new Walls(3, 4), new Coord(0, 1));
    }

    @Test
    public void testBottomWallTopRight() {
        testBottomWall(new Walls(3, 4), new Coord(0, 2));
    }

    @Test
    public void testBottomWallMidLeft() {
        testBottomWall(new Walls(3, 4), new Coord(1, 0));
    }

    @Test
    public void testBottomWallMidMid() {
        testBottomWall(new Walls(3, 4), new Coord(1, 1));
    }

    @Test
    public void testBottomWallMidRight() {
        testBottomWall(new Walls(3, 4), new Coord(1, 2));
    }

    @Test
    public void testBottomWallBottomLeft() {
        testBottomWall(new Walls(3, 4), new Coord(3, 0));
    }

    @Test
    public void testBottomWallBottomMid() {
        testBottomWall(new Walls(3, 4), new Coord(3, 1));
    }

    @Test
    public void testBottomWallBottomRight() {
        testBottomWall(new Walls(3, 4), new Coord(3, 2));
    }

    private void testRightWall(Walls walls, Coord coord) {
        assertFalse(walls.hasRightWall(coord));
        walls.setRightWall(coord);
        assertTrue(walls.hasRightWall(coord));

        // only 1 right wall should exist
        assertEquals(1, getCoordStream(walls).mapToInt(c -> walls.hasRightWall(c) ? 1 : 0).sum());

        walls.clearRightWall(coord);
        assertFalse(walls.hasRightWall(coord));
        assertEquals(0, getCoordStream(walls).mapToInt(c -> walls.hasRightWall(c) ? 1 : 0).sum());
    }

    @Test
    public void testRightWallTopLeft() {
        testRightWall(new Walls(3, 4), new Coord(0, 0));
    }

    @Test
    public void testRightWallTopMid() {
        testRightWall(new Walls(3, 4), new Coord(0, 1));
    }

    @Test
    public void testRightWallTopRight() {
        testRightWall(new Walls(3, 4), new Coord(0, 2));
    }

    @Test
    public void testRightWallMidLeft() {
        testRightWall(new Walls(3, 4), new Coord(1, 0));
    }

    @Test
    public void testRightWallMidMid() {
        testRightWall(new Walls(3, 4), new Coord(1, 1));
    }

    @Test
    public void testRightWallMidRight() {
        testRightWall(new Walls(3, 4), new Coord(1, 2));
    }

    @Test
    public void testRightWallBottomLeft() {
        testRightWall(new Walls(3, 4), new Coord(3, 0));
    }

    @Test
    public void testRightWallBottomMid() {
        testRightWall(new Walls(3, 4), new Coord(3, 1));
    }

    @Test
    public void testRightWallBottomRight() {
        testRightWall(new Walls(3, 4), new Coord(3, 2));
    }

    @Test
    public void testRoundTripSerializationEmptyNoCompression() {
        Walls original = new Walls(3, 4);
        String serialized = original.serialize();
        assertEquals("789c030000000001", serialized);

        Walls deserialized = Walls.deserialize(serialized, 3, 4);
        assertEquals(original, deserialized);
    }

    @Test
    public void testRoundTripSerializationSomeWallsNoCompression() {
        Walls original = new Walls(3, 4);
        original.setAllWalls(new Coord(0, 0));
        original.setAllWalls(new Coord(1, 1));
        original.setAllWalls(new Coord(2, 2));

        String serialized = original.serialize();
        assertEquals("789c9b6929ac000003590106", serialized);

        Walls deserialized = Walls.deserialize(serialized, 3, 4);
        assertEquals(original, deserialized);
    }

    @Test
    public void testRoundTripSerializationAllWallsNoCompression() {
        Walls original = new Walls(3, 4);
        for (int row = 0; row < original.getHeight(); row++) {
            for (int col = 0; col < original.getWidth(); col++) {
                original.setAllWalls(new Coord(row, col));
            }
        }
        String serialized = original.serialize();
        assertEquals("789cfbffff7f3d00097a037d", serialized);

        Walls deserialized = Walls.deserialize(serialized, 3, 4);
        assertEquals(original, deserialized);
    }

    @Test
    public void testRoundTripSerializationAllWallsLargeNoCompression() {
        Walls original = new Walls(300, 300);
        for (int row = 0; row < original.getHeight(); row += 2) {
            for (int col = 0; col < original.getWidth(); col += 2) {
                original.setAllWalls(new Coord(row, col));
            }
        }
        String serialized = original.serialize();
        String expected = "789cedcc310100000400300da4d33f0a15f81c5b805501000000000000000000007ff546c6864a"
                + "a552a9542a954aa552a9542a954aa552a9542a954aa552a9542a954aa552a9542a954aa5ba540366843a93";
        assertEquals(expected, serialized);

        Walls deserialized = Walls.deserialize(serialized, 300, 300);
        assertEquals(original, deserialized);
    }

    @Test
    public void testToStringEmpty() {
        Walls walls = new Walls(3, 4);
        String expected =
                // @formatter:off
                "+  +  +  +\n" +
                "          \n" +
                "+  +  +  +\n" +
                "          \n" +
                "+  +  +  +\n" +
                "          \n" +
                "+  +  +  +\n" +
                "          \n" +
                "+  +  +  +\n";
                // @formatter:on
        assertEquals(expected, walls.toString());
    }

    @Test
    public void testToStringOneCell() {
        Walls walls = new Walls(3, 4);
        walls.setAllWalls(new Coord(1, 1));
        String expected =
                // @formatter:off
                "+  +  +  +\n" +
                "          \n" +
                "+  +--+  +\n" +
                "   |  |   \n" +
                "+  +--+  +\n" +
                "          \n" +
                "+  +  +  +\n" +
                "          \n" +
                "+  +  +  +\n";
                // @formatter:on
        assertEquals(expected, walls.toString());
    }

    @Test
    public void testToStringSeveralCells() {
        Walls walls = new Walls(3, 4);
        walls.setAllWalls(new Coord(0, 0));
        walls.setAllWalls(new Coord(1, 1));
        walls.setAllWalls(new Coord(2, 2));
        walls.setAllWalls(new Coord(3, 2));
        String expected =
                // @formatter:off
                "+--+  +  +\n" +
                "|  |      \n" +
                "+--+--+  +\n" +
                "   |  |   \n" +
                "+  +--+--+\n" +
                "      |  |\n" +
                "+  +  +--+\n" +
                "      |  |\n" +
                "+  +  +--+\n";
                // @formatter:on
        assertEquals(expected, walls.toString());
    }

    @Test
    public void testToStringOutsideWalls() {
        Walls walls = new Walls(3, 4);
        walls.setTopWall(new Coord(0, 0));
        walls.setLeftWall(new Coord(0, 0));
        walls.setTopWall(new Coord(0, 1));
        walls.setTopWall(new Coord(0, 2));
        walls.setRightWall(new Coord(0, 2));
        walls.setLeftWall(new Coord(1, 0));
        walls.setRightWall(new Coord(1, 2));
        walls.setLeftWall(new Coord(2, 0));
        walls.setRightWall(new Coord(2, 2));
        walls.setLeftWall(new Coord(3, 0));
        walls.setBottomWall(new Coord(3, 0));
        walls.setBottomWall(new Coord(3, 1));
        walls.setBottomWall(new Coord(3, 2));
        walls.setRightWall(new Coord(3, 2));
        String expected =
                // @formatter:off
                "+--+--+--+\n" +
                "|        |\n" +
                "+  +  +  +\n" +
                "|        |\n" +
                "+  +  +  +\n" +
                "|        |\n" +
                "+  +  +  +\n" +
                "|        |\n" +
                "+--+--+--+\n";
                // @formatter:on
        assertEquals(expected, walls.toString());
    }

    @Test
    public void testToStringAllWalls() {
        Walls walls = new Walls(3, 4);
        for (int row = 0; row < walls.getHeight(); row++) {
            for (int col = 0; col < walls.getWidth(); col++) {
                walls.setAllWalls(new Coord(row, col));
            }
        }
        String expected =
                // @formatter:off
                "+--+--+--+\n" +
                "|  |  |  |\n" +
                "+--+--+--+\n" +
                "|  |  |  |\n" +
                "+--+--+--+\n" +
                "|  |  |  |\n" +
                "+--+--+--+\n" +
                "|  |  |  |\n" +
                "+--+--+--+\n";
                // @formatter:on
        assertEquals(expected, walls.toString());
    }
}
