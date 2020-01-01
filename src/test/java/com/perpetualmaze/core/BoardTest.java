package com.perpetualmaze.core;

import com.perpetualmaze.core.Board;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoardTest {
    @Test
    public void test() {
        Board board = new Board("O  :O O: O ");
        assertEquals("O  :O O: O ", board.serialize());
    }
}
