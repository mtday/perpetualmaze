package com.perpetualmaze.store.pg;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import com.perpetualmaze.core.HighScore;
import com.perpetualmaze.store.ExternalDataSourceResource;
import com.perpetualmaze.store.HighScoreStore;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PostgresHighScoreStoreTest {
    @ClassRule
    public static final ExternalDataSourceResource DS = new ExternalDataSourceResource();

    private HighScoreStore highScoreStore;

    @Before
    public void before() {
        highScoreStore = new PostgresHighScoreStore(DS.getDataSource());
    }

    @Test
    public void test() {
        Optional<HighScore> highScore = highScoreStore.get();
        assertTrue(highScore.isPresent());

        assertEquals("", highScore.get().getName());
        assertEquals(0, highScore.get().getScore());

        HighScore newScore = new HighScore("name", 100);
        highScoreStore.save(newScore);

        Optional<HighScore> updated = highScoreStore.get();
        assertTrue(updated.isPresent());
        assertEquals(newScore.getName(), updated.get().getName());
        assertEquals(newScore.getScore(), updated.get().getScore());
    }
}
