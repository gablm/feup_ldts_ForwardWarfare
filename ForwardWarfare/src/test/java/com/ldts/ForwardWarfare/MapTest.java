package com.ldts.ForwardWarfare;

import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.Map.MapParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public class MapTest {
    @Test
    public void MapDefault() throws FileNotFoundException, MapParseException, URISyntaxException {
        Map map = new Map("tests/noBuildings.fw");

        Assertions.assertEquals(150, map.getElements().size());
        Assertions.assertNull(map.getPlayer1());
        Assertions.assertNull(map.getPlayer2());

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
            new Map("tests/empty.fw");
        });
        Assertions.assertEquals("The map should be 15 x 10.", exception.getMessage());
    }

    @Test
    public void MapInvalidNumberTest() {
        Exception exception = Assertions.assertThrows(MapParseException.class, () -> {
            new Map("tests/invalidNumber.fw");
        });
        Assertions.assertEquals("There is an invalid number in the map:", exception.getMessage().substring(0, 38));
    }

    @Test
    public void MapInvalidSizeTest() {
        Exception exception = Assertions.assertThrows(MapParseException.class, () -> {
            new Map("tests/invalidSize.fw");
        });
        Assertions.assertEquals("Invalid line size:", exception.getMessage().substring(0, 18));
    }

    @Test
    public void MapRepeatFactoryTest() {
        Exception exception = Assertions.assertThrows(MapParseException.class, () -> {
            new Map("tests/repeatFactory.fw");
        });
        Assertions.assertEquals("A player cannot start with more than one factory", exception.getMessage());
    }

    @Test
    public void MapRepeatBaseTest() {
        Exception exception = Assertions.assertThrows(MapParseException.class, () -> {
            new Map("tests/repeatBase.fw");
        });
        Assertions.assertEquals("A player cannot have more than one base", exception.getMessage());
    }
}
