package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.Map.MapParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

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
        assertThrows(NullPointerException.class, () -> {
            new Map("adawdioadmawmdmoawda");
        });
    }

    @Test
    public void MapEmptyTest() {
        Exception exception = assertThrows(MapParseException.class, () -> {
            new Map("tests/empty.fw");
        });
        Assertions.assertEquals("The map should be 15 x 10.", exception.getMessage());
    }

    @Test
    public void MapInvalidNumberTest() {
        Exception exception = assertThrows(MapParseException.class, () -> {
            new Map("tests/invalidNumber.fw");
        });
        Assertions.assertEquals("There is an invalid number in the map:", exception.getMessage().substring(0, 38));
    }

    @Test
    public void MapInvalidSizeTest() {
        Exception exception = assertThrows(MapParseException.class, () -> {
            new Map("tests/invalidSize.fw");
        });
        Assertions.assertEquals("Invalid line size:", exception.getMessage().substring(0, 18));
    }

    @Test
    public void MapRepeatFactoryTest() {
        Exception exception = assertThrows(MapParseException.class, () -> {
            new Map("tests/repeatFactory.fw");
        });
        Assertions.assertEquals("A player cannot start with more than one factory", exception.getMessage());
    }
    @Test
    public void MapRepeatFactoryEnemyTest() {
        Exception exception = assertThrows(MapParseException.class, () -> {
            new Map("tests/repeatFactoryenemy.fw");
        });
        Assertions.assertEquals("A player cannot start with more than one factory", exception.getMessage());
    }

    @Test
    public void MapRepeatBaseTest() {
        Exception exception = assertThrows(MapParseException.class, () -> {
            new Map("tests/repeatBase.fw");
        });
        Assertions.assertEquals("A player cannot have more than one base", exception.getMessage());
    }
    @Test
    public void MapRepeatBaseEnemyTest() {
        Exception exception = assertThrows(MapParseException.class, () -> {
            new Map("tests/repeatBaseEnemy.fw");
        });
        Assertions.assertEquals("A player cannot have more than one base", exception.getMessage());
    }
    @Test
    public void testReadMap_validMapFile() throws FileNotFoundException, MapParseException, URISyntaxException {
        Map mapReaderMock = Mockito.mock(Map.class);
        Mockito.doNothing().when(mapReaderMock).readMap(anyString());

        assertDoesNotThrow(() -> mapReaderMock.readMap("valid_map.txt"));
        Mockito.verify(mapReaderMock, Mockito.times(1)).readMap("valid_map.txt");
    }
    @Test
    public void testReadMap_invalidMapFile() throws FileNotFoundException, URISyntaxException, MapParseException {
        Map mapReaderMock = Mockito.mock(Map.class);
        Mockito.doThrow(MapParseException.class).when(mapReaderMock).readMap(anyString());

        assertThrows(MapParseException.class, () -> mapReaderMock.readMap("invalid_map.txt"));
        Mockito.verify(mapReaderMock, Mockito.times(1)).readMap("invalid_map.txt");
    }
    @Test
    public void InsideTest() throws FileNotFoundException, MapParseException, URISyntaxException {
        Map map = new Map("1.fw");

        Assertions.assertTrue(map.inside(new Position(0, 0)));
        Assertions.assertTrue(map.inside(new Position(14, 9)));
        Assertions.assertFalse(map.inside(new Position(15, 10)));
        Assertions.assertFalse(map.inside(new Position(-1, -1)));
        Assertions.assertTrue(map.inside(new Position(5, 5)));
        Assertions.assertFalse(map.inside(new Position(15, 5)));
        Assertions.assertFalse(map.inside(new Position(4, 10)));
        Assertions.assertFalse(map.inside(new Position(-1, 0)));
        Assertions.assertFalse(map.inside(new Position(0, -1)));
    }
    @Test
    public void drawTest() throws FileNotFoundException, MapParseException, URISyntaxException {
        Map map = new Map("tests/allGrass.fw");
        TextGraphics te = Mockito.mock(TextGraphics.class);
        map.draw(te);
        Mockito.verify(te, Mockito.times(150)).putString(Mockito.any(), Mockito.anyString());
    }
    @Test
    public void setTest() throws FileNotFoundException, MapParseException, URISyntaxException {
        Map map = new Map("tests/allGrass.fw");
        {
            Element temp = (Element) map.at(new Position(0, 0));
            map.set(new Position(1, 0), temp);
            Assertions.assertEquals(temp, map.at(new Position(1, 0)));
        }
        {
            Element temp = (Element) map.at(new Position(0, 0));
            map.set(new Position(-20, -1), temp);
            Assertions.assertNotEquals(temp, map.at(new Position(-20,-1)));
        }
        {
            Element temp = (Element) map.at(new Position(0, 0));
            map.set(new Position(-20, 1), temp);
            Assertions.assertNotEquals(temp, map.at(new Position(-20,1)));
        }
        {
            Element temp = (Element) map.at(new Position(0, 0));
            map.set(new Position(1, -20), temp);
            Assertions.assertNotEquals(temp, map.at(new Position(1,-20)));
        }
        {
            Element temp = (Element) map.at(new Position(0, 0));
            map.set(new Position(20, 0), temp);
            Assertions.assertNotEquals(temp, map.at(new Position(20,0)));
        }
        {
            Element temp = (Element) map.at(new Position(0, 0));
            map.set(new Position(0, 20), temp);
            Assertions.assertNotEquals(temp, map.at(new Position(0,20)));
        }
        {
            Element temp = (Element) map.at(new Position(0, 0));
            map.set(new Position(20, 20), temp);
            Assertions.assertNotEquals(temp, map.at(new Position(20,20)));
        }
    }

}
