package com.perpetualmaze.core;

import com.perpetualmaze.core.Piece;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PieceTest {
    @Test
    public void test() {
        Piece piece = new Piece("O :OO");
        assertEquals("O :OO", piece.serialize());
    }
}
