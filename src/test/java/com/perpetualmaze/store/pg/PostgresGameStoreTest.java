package com.perpetualmaze.store.pg;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import com.perpetualmaze.store.ExternalDataSourceResource;
import com.perpetualmaze.store.GameStore;

import static org.junit.Assert.assertFalse;

public class PostgresGameStoreTest {
    @ClassRule
    public static final ExternalDataSourceResource DS = new ExternalDataSourceResource();

    private GameStore gameStore;

    @Before
    public void before() {
        gameStore = new PostgresGameStore(DS.getDataSource());
    }

    @Test
    public void test() {
        assertFalse(gameStore.get("missing").isPresent());
    }
}
