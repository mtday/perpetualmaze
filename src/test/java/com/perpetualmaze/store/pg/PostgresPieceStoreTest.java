package com.perpetualmaze.store.pg;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import com.perpetualmaze.core.Piece;
import com.perpetualmaze.store.ExternalDataSourceResource;
import com.perpetualmaze.store.PieceStore;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PostgresPieceStoreTest {
    @ClassRule
    public static final ExternalDataSourceResource DS = new ExternalDataSourceResource();

    private PieceStore pieceStore;

    @Before
    public void before() {
        pieceStore = new PostgresPieceStore(DS.getDataSource());
    }

    @Test
    public void test() {
        List<Piece> pieces = pieceStore.getAll();
        assertEquals(19, pieces.size());

        Piece piece = pieceStore.random();
        assertNotNull(piece);
    }
}
