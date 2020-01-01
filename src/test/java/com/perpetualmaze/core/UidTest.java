package com.perpetualmaze.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UidTest {
    @Test
    public void testDefaultLength() {
        String randomUid = Uid.randomUid();
        assertEquals(8, randomUid.length());
        assertTrue(randomUid.matches("[a-zA-Z0-9]{8}"));
    }

    @Test
    public void testCustomLength() {
        String randomUid = Uid.randomUid(12);
        assertEquals(12, randomUid.length());
        assertTrue(randomUid.matches("[a-zA-Z0-9]{12}"));
    }
}
