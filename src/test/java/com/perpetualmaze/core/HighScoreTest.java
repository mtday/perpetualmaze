package com.perpetualmaze.core;

import com.perpetualmaze.core.HighScore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HighScoreTest {
    @Test
    public void test() {
        HighScore highScore = new HighScore("John Doe", 1234);
        assertEquals("John Doe", highScore.getName());
        assertEquals(1234, highScore.getScore());
    }
}
