package com.perpetualmaze.core;

import com.perpetualmaze.core.Grid;
import com.perpetualmaze.core.Location;
import org.junit.Assert;
import org.junit.Test;

import java.util.BitSet;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class GridTest {
    @Test
    public void testStringConstructor() {
        Grid grid = new Grid(" OO :O  O");
        assertEquals(4, grid.getWidth());
        assertEquals(2, grid.getHeight());

        BitSet bitSet = grid.getBitSet();
        assertFalse(bitSet.get(0));
        assertTrue(bitSet.get(1));
        assertTrue(bitSet.get(2));
        assertFalse(bitSet.get(3));
        assertTrue(bitSet.get(4));
        assertFalse(bitSet.get(5));
        assertFalse(bitSet.get(6));
        assertTrue(bitSet.get(7));
    }

    @Test
    public void testSizeConstructor() {
        Grid grid = new Grid(4, 2);
        assertEquals(4, grid.getWidth());
        assertEquals(2, grid.getHeight());

        BitSet bitSet = grid.getBitSet();
        Stream.iterate(0, i -> i + 1)
                .limit(grid.getWidth() * grid.getHeight())
                .map(bitSet::get)
                .forEach(Assert::assertFalse);
    }

    private void testCanPlace(String board, String piece, int row, int col) {
        Grid boardGrid = new Grid(board);
        Grid pieceGrid = new Grid(piece);
        Optional<Location> location = boardGrid.canPlace(pieceGrid);
        assertTrue(location.isPresent());
        assertEquals(row, location.get().getRow());
        assertEquals(col, location.get().getCol());
    }

    private void testCanPlace(String board, String piece) {
        Grid boardGrid = new Grid(board);
        Grid pieceGrid = new Grid(piece);
        Optional<Location> location = boardGrid.canPlace(pieceGrid);
        assertFalse(location.isPresent());
    }

    @Test
    public void testCanPlace() {
        // horizontal piece
        testCanPlace("  OO:OOOO:OOOO", "OO", 0, 0); // top-left
        testCanPlace("O  O:OOOO:OOOO", "OO", 0, 1); // top-middle
        testCanPlace("OO  :OOOO:OOOO", "OO", 0, 2); // top-right
        testCanPlace("OOOO:  OO:OOOO", "OO", 1, 0); // middle-left
        testCanPlace("OOOO:O  O:OOOO", "OO", 1, 1); // middle-middle
        testCanPlace("OOOO:OO  :OOOO", "OO", 1, 2); // middle-right
        testCanPlace("OOOO:OOOO:  OO", "OO", 2, 0); // bottom-left
        testCanPlace("OOOO:OOOO:O  O", "OO", 2, 1); // bottom-middle
        testCanPlace("OOOO:OOOO:OO  ", "OO", 2, 2); // bottom-right
        testCanPlace("OOOO:OOOO:OOOO", "OO"); // full
        testCanPlace("OOOO: OOO: OOO", "OO"); // only vertical available

        // vertical piece
        testCanPlace(" OOO: OOO:OOOO:OOOO", "O:O", 0, 0); // top-left
        testCanPlace("O OO:O OO:OOOO:OOOO", "O:O", 0, 1); // top-middle
        testCanPlace("OOO :OOO :OOOO:OOOO", "O:O", 0, 3); // top-right
        testCanPlace("OOOO: OOO: OOO:OOOO", "O:O", 1, 0); // middle-left
        testCanPlace("OOOO:O OO:O OO:OOOO", "O:O", 1, 1); // middle-middle
        testCanPlace("OOOO:OOO :OOO :OOOO", "O:O", 1, 3); // middle-right
        testCanPlace("OOOO:OOOO: OOO: OOO", "O:O", 2, 0); // bottom-left
        testCanPlace("OOOO:OOOO:O OO:O OO", "O:O", 2, 1); // bottom-middle
        testCanPlace("OOOO:OOOO:OOO :OOO ", "O:O", 2, 3); // bottom-right
        testCanPlace("OOOO:OOOO:OOOO:OOOO", "O:O"); // full
        testCanPlace("OOOO:  OO:OOOO:OOOO", "O:O"); // only horizontal available
    }

    private void testClearFull(String before, String after) {
        Grid beforeGrid = new Grid(before);
        Grid afterGrid = new Grid(after);
        beforeGrid.clearFull();
        assertEquals(afterGrid.serialize(), beforeGrid.serialize());
    }

    private void testPlace(String board, String piece, int row, int col, String expected) {
        Grid boardGrid = new Grid(board);
        Grid pieceGrid = new Grid(piece);
        assertTrue(boardGrid.canPlace(pieceGrid, row, col));
        boardGrid.place(pieceGrid, row, col);
        assertEquals(expected, boardGrid.serialize());
    }

    @Test
    public void testPlace() {
        testPlace("  :  ", "OO:OO", 0, 0, "OO:OO"); // fill
        testPlace("  :OO", "OO",    0, 0, "OO:OO"); // top
        testPlace(" O: O", "O:O",   0, 0, "OO:OO"); // left
        testPlace("OO:  ", "OO",    1, 0, "OO:OO"); // bottom
        testPlace("O :O ", "O:O",   0, 1, "OO:OO"); // right
        testPlace("   :   ", "OO",  0, 1, " OO:   "); // middle horizontal
        testPlace("   :   ", "O:O", 0, 1, " O : O "); // middle vertical
    }

    @Test
    public void testClearFull() {
        // Horizontal
        testClearFull("OO:  ", "  :  ");        // top
        testClearFull("O :OO: O", "O :  : O");  // middle
        testClearFull("  :OO", "  :  ");        // bottom

        // Vertical
        testClearFull("O  :O  ", "   :   ");    // left
        testClearFull(" O : O ", "   :   ");    // middle
        testClearFull("  O:  O", "   :   ");    // right
    }

    @Test
    public void testSerialize() {
        assertEquals(" OO :O  O", new Grid(" OO :O  O").serialize());
    }
}
