package com.ldts.ForwardWarfare;

import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.Map.MapParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class MapTest {
    @Test
    public void MapDefault() throws FileNotFoundException, MapParseException {
        Map map = new Map("noBuildings.fw");

        Assertions.assertEquals(150, map.getElements().size());
        Assertions.assertTrue(map.getPlayer1().isEmpty());
        Assertions.assertTrue(map.getPlayer2().isEmpty());

        int i = 0;
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 15; x++) {
                Assertions.assertEquals(new Position(x, y), map.getElements().get(i).getPosition());
                i++;
            }
        }
    }

    @Test
    public void MapInvalidFileTest() {
        Assertions.assertThrows(FileNotFoundException.class, () -> {
            new Map("adawdioadmawmdmoawda");
        });
    }

    @Test
    public void MapEmptyTest() {
        Exception exception = Assertions.assertThrows(MapParseException.class, () -> {
            new Map("empty.fw");
        });
        Assertions.assertEquals("The map should be 15 x 10.", exception.getMessage());
    }

    @Test
    public void MapInvalidNumberTest() {
        Exception exception = Assertions.assertThrows(MapParseException.class, () -> {
            new Map("invalidNumber.fw");
        });
        Assertions.assertEquals("There is an invalid number in the map:", exception.getMessage().substring(0, 38));
    }

    @Test
    public void MapInvalidSizeTest() {
        Exception exception = Assertions.assertThrows(MapParseException.class, () -> {
            new Map("invalidSize.fw");
        });
        Assertions.assertEquals("Invalid line size:", exception.getMessage().substring(0, 18));
    }
}
