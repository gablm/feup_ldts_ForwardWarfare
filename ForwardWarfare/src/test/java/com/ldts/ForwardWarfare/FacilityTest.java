package com.ldts.ForwardWarfare;

import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Facility.Facility;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class FacilityTest {
    @Test
    public void testRemoveFacility() {
        Controller yourInstance;
        Facility facilityMock = Mockito.mock(Facility.class);
        yourInstance.addFacility(facilityMock);
        int initialFacilitiesSize = yourInstance.getFacilities().size();
        yourInstance.removeFacility(facilityMock);
        assertEquals(initialFacilitiesSize - 1, yourInstance.getFacilities().size());
        assertFalse(yourInstance.getFacilities().contains(facilityMock));
    }

    @Test
    public void testAddFacility() {
        Controller yourInstance;
        int initialFacilitiesSize = yourInstance.getFacilities().size();
        Facility facilityMock = Mockito.mock(Facility.class);
        yourInstance.addFacility(facilityMock);
        assertEquals(initialFacilitiesSize + 1, yourInstance.getFacilities().size());
        assertTrue(yourInstance.getFacilities().contains(facilityMock));
    }

    @Test
    public void testGetCoins() {
        Controller yourInstance;
        int coins = yourInstance.getCoins();
        // Assuming initial coins are greater than or equal to 0
        assertTrue(coins >= 0);
    }
}