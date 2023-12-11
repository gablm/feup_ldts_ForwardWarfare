package com.ldts.ForwardWarfare.Element;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Facility.Facility;
import com.ldts.ForwardWarfare.Element.Tile.Border;
import com.ldts.ForwardWarfare.Element.Tile.Fields;
import com.ldts.ForwardWarfare.Element.Tile.Water;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TilesTest {

    @Test
    public void BorderBaseTest() {
        Position position = new Position(0, 0);
        Border border = new Border(position);

        border.setBackgroundColor(TextColor.ANSI.BLUE);
        border.setForegroundColor(TextColor.ANSI.RED_BRIGHT);

        TextGraphics graphics = Mockito.mock(TextGraphics.class);

        border.draw(graphics);

        Mockito.verify(graphics).setForegroundColor(TextColor.ANSI.RED_BRIGHT);
        Mockito.verify(graphics).setBackgroundColor(TextColor.ANSI.BLUE);
        Mockito.verify(graphics).putString(position.toTPos(), "*");
    }

    @Test
    public void FieldsFacilityTest() {
        Facility facility = Mockito.mock(Facility.class);

        Position position = new Position(0, 0);
        Fields fields = new Fields(position, facility);

        Assertions.assertNotNull(fields.getFacility());
        Assertions.assertFalse(fields.noCollision());

        TextGraphics graphics = Mockito.mock(TextGraphics.class);

        fields.draw(graphics);

        Mockito.verify(graphics).setForegroundColor(new TextColor.RGB(226,214,106));
        Mockito.verify(graphics).setBackgroundColor(new TextColor.RGB(113,199,0));
        Mockito.verify(graphics).putString(position.toTPos(), "|");
        Mockito.verify(facility).draw(graphics, position);
        Assertions.assertEquals(new TextColor.RGB(113,199,0), fields.getColor());

        fields.setForegroundColor(TextColor.ANSI.WHITE);
        Mockito.verify(facility).setTextColor(TextColor.ANSI.WHITE);
    }

    @Test
    public void FieldsNoFacilityTest() {
        Position position = new Position(0, 0);
        Fields fields = new Fields(position, null);

        Assertions.assertNull(fields.getFacility());
        Assertions.assertTrue(fields.noCollision());

        TextGraphics graphics = Mockito.mock(TextGraphics.class);

        fields.draw(graphics);

        Mockito.verify(graphics).setForegroundColor(new TextColor.RGB(226,214,106));
        Mockito.verify(graphics).setBackgroundColor(new TextColor.RGB(113,199,0));
        Mockito.verify(graphics).putString(position.toTPos(), "|");
    }

    @Test
    public void WaterFacilityTest() {
        Facility facility = Mockito.mock(Facility.class);

        Position position = new Position(0, 0);
        Water water = new Water(position, facility);

        Assertions.assertNotNull(water.getFacility());
        Assertions.assertFalse(water.noCollision());

        TextGraphics graphics = Mockito.mock(TextGraphics.class);

        water.draw(graphics);

        Mockito.verify(graphics).setForegroundColor(new TextColor.RGB(224,224,224));
        Mockito.verify(graphics).setBackgroundColor(new TextColor.RGB(0,124,206));
        Mockito.verify(graphics).putString(position.toTPos(), "~");
        Mockito.verify(facility).draw(graphics, position);
        Assertions.assertEquals(new TextColor.RGB(0,124,206), water.getColor());

        water.setForegroundColor(TextColor.ANSI.WHITE);
        Mockito.verify(facility).setTextColor(TextColor.ANSI.WHITE);
    }

    @Test
    public void WaterNoFacilityTest() {
        Position position = new Position(0, 0);
        Water water = new Water(position, null);

        Assertions.assertNull(water.getFacility());
        Assertions.assertTrue(water.noCollision());

        TextGraphics graphics = Mockito.mock(TextGraphics.class);

        water.draw(graphics);

        Mockito.verify(graphics).setForegroundColor(new TextColor.RGB(224,224,224));
        Mockito.verify(graphics).setBackgroundColor(new TextColor.RGB(0,124,206));
        Mockito.verify(graphics).putString(position.toTPos(), "~");
    }

    @Test
    public void FlorestTest() {

    }
}
