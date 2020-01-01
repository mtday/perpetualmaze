package com.perpetualmaze.core;

import com.perpetualmaze.core.Location;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LocationTest {
    @Test
    public void testConstructor() {
        Location location = new Location(2, 3);
        assertEquals(2, location.getRow());
        assertEquals(3, location.getCol());
    }

    @Test
    public void testToString() {
        assertEquals("<2,3>", new Location(2, 3).toString());
    }
}
