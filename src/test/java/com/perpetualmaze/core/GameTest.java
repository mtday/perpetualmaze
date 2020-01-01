package com.perpetualmaze.core;

import com.perpetualmaze.core.Board;
import com.perpetualmaze.core.Game;
import com.perpetualmaze.core.Piece;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameTest {
    @Test
    public void test() {
        Game game = new Game("id", 0, new Board("  :  "), new Piece("OO"), new Piece("O:O"), new Piece("OO:OO"));
        assertEquals("id", game.getId());
        assertEquals(0, game.getScore());
        assertEquals("  :  ", game.getBoard().serialize());
        assertEquals("OO", game.getPiece1().serialize());
        assertEquals("O:O", game.getPiece2().serialize());
        assertEquals("OO:OO", game.getPiece3().serialize());
    }
}
