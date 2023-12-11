package com.ldts.ForwardWarfare.Element;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Facility.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class FacilityTest {

    private void FacilityTestCases(Facility facility, TextColor baseColor, String baseChar) {
        Position position = new Position(0, 0);

        Assertions.assertFalse(facility.getUsed());
        facility.execute();
        Assertions.assertTrue(facility.getUsed());
        facility.execute();
        Assertions.assertFalse(facility.getUsed());

        TextGraphics graphics = Mockito.mock(TextGraphics.class);

        facility.draw(graphics, position);
        Mockito.verify(graphics).setForegroundColor(baseColor);
        Mockito.verify(graphics).putString(position.toTPos(), baseChar);

        facility.setTextColor(TextColor.ANSI.WHITE);
        facility.draw(graphics, position);
        Mockito.verify(graphics).setForegroundColor(TextColor.ANSI.WHITE);
    }

    @Test
    void AirportTest() {
        Airport airport = new Airport();
        FacilityTestCases(airport,
                new TextColor.RGB(255, 128, 0),
                ">");
    }

    @Test
    void FactoryTest() {
        Factory factory = new Factory();
        FacilityTestCases(factory,
                new TextColor.RGB(32, 32, 32),
                "`");
    }

    @Test
    void OilPumpTest() {
        OilPump oilPump = new OilPump();
        FacilityTestCases(oilPump,
                new TextColor.RGB(255, 255, 0),
                "/");
    }

    @Test
    void PortTest() {
        Port port = new Port();
        FacilityTestCases(port,
                new TextColor.RGB(51, 255, 255),
                ")");
    }

    @Test
    void BaseTest() {
        Base base = new Base();
        base.setTextColor(TextColor.ANSI.RED_BRIGHT);
        FacilityTestCases(base,
                TextColor.ANSI.RED_BRIGHT,
                ";");
        Assertions.assertEquals(2, base.getLives());
        base.takeDamage();
        Assertions.assertEquals(1, base.getLives());
        base.setLives(20);
        Assertions.assertEquals(20, base.getLives());

        Assertions.assertFalse(base.getAtackedlastturn());
        base.setAtackedlastturn(true);
        Assertions.assertTrue(base.getAtackedlastturn());
        base.setAtackedlastturn(false);
        Assertions.assertFalse(base.getAtackedlastturn());
    }
}
