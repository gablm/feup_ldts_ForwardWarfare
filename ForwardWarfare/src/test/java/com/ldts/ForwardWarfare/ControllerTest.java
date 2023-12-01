package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.TextColor;
import com.ldts.ForwardWarfare.Controller.Bot;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Controller.InvalidControllerException;
import com.ldts.ForwardWarfare.Controller.Player;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Facility.Base;
import com.ldts.ForwardWarfare.Element.Facility.Facility;
import com.ldts.ForwardWarfare.Element.Facility.Factory;
import com.ldts.ForwardWarfare.Element.Playable.Ground.LightTank;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Tile.Fields;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class ControllerTest {

    @Test
    public void ControllerInvalidTest() {
        Exception playerEx = Assertions.assertThrows(InvalidControllerException.class, () -> {
            new Player(null, TextColor.ANSI.MAGENTA_BRIGHT);
        });
        Exception botEx = Assertions.assertThrows(InvalidControllerException.class, () -> {
            new Bot(null, TextColor.ANSI.GREEN_BRIGHT);
        });

        Assertions.assertEquals("Invalid initial Factory and Base", playerEx.getMessage());
        Assertions.assertEquals("Invalid initial Factory and Base", botEx.getMessage());
    }

    @Test
    public void ControllerWrongSizeTest() {
        List<Element> elements = List.of(new Fields(new Position(0, 0), new Base()));
        Exception playerEx = Assertions.assertThrows(InvalidControllerException.class, () -> {
            new Player(elements, TextColor.ANSI.GREEN_BRIGHT);
        });
        Exception botEx = Assertions.assertThrows(InvalidControllerException.class, () -> {
            new Bot(elements, TextColor.ANSI.GREEN_BRIGHT);
        });

        Assertions.assertEquals("Invalid initial Factory and Base", playerEx.getMessage());
        Assertions.assertEquals("Invalid initial Factory and Base", botEx.getMessage());
    }

    @Test
    public void ControllerDefaultTest() throws InvalidControllerException {
        Element base = new Fields(new Position(0, 0), new Base());
        Fields factory = new Fields(new Position(0, 0), new Factory());
        List<Element> elements = Arrays.asList(base, factory);

        Controller player = new Player(elements, TextColor.ANSI.GREEN_BRIGHT);

        Assertions.assertEquals(100, player.getCoins());
        Assertions.assertSame(player.getBase(), base);
        Assertions.assertEquals(1, player.getFacilities().size());
        Assertions.assertSame(factory, player.getFacilities().get(0));
        Assertions.assertTrue(player.getTroops().isEmpty());
    }

    @Test
    public void ControllerBuyAllTest() throws InvalidControllerException {
        Element base = new Fields(new Position(0, 0), new Base());
        Fields factory = new Fields(new Position(0, 0), new Factory());
        List<Element> elements = Arrays.asList(base, factory);

        Controller player = new Player(elements, TextColor.ANSI.GREEN_BRIGHT);

        Assertions.assertEquals(100, player.getCoins());
        Playable troop = new LightTank(new Position(1, 1));
        boolean result = player.buy(troop, 100);
        Assertions.assertTrue(result);
        Assertions.assertEquals(0, player.getCoins());
        Assertions.assertEquals(1, player.getTroops().size());
        Assertions.assertSame(troop, player.getTroops().get(0));
    }

    @Test
    public void ControllerBuyLessTest() throws InvalidControllerException {
        Element base = new Fields(new Position(0, 0), new Base());
        Fields factory = new Fields(new Position(0, 0), new Factory());
        List<Element> elements = Arrays.asList(base, factory);

        Controller player = new Player(elements, TextColor.ANSI.GREEN_BRIGHT);

        Assertions.assertEquals(100, player.getCoins());
        Playable troop = new LightTank(new Position(1, 1));
        boolean result = player.buy(troop, 50);
        Assertions.assertTrue(result);
        Assertions.assertEquals(50, player.getCoins());
        Assertions.assertEquals(1, player.getTroops().size());
        Assertions.assertSame(troop, player.getTroops().get(0));
    }

    @Test
    public void ControllerBuyMoreTest() throws InvalidControllerException {
        Element base = new Fields(new Position(0, 0), new Base());
        Fields factory = new Fields(new Position(0, 0), new Factory());
        List<Element> elements = Arrays.asList(base, factory);

        Controller player = new Player(elements, TextColor.ANSI.GREEN_BRIGHT);

        Assertions.assertEquals(100, player.getCoins());
        Playable troop = new LightTank(new Position(1, 1));
        boolean result = player.buy(troop, 120);
        Assertions.assertFalse(result);
        Assertions.assertEquals(100, player.getCoins());
        Assertions.assertTrue(player.getTroops().isEmpty());
    }
}
