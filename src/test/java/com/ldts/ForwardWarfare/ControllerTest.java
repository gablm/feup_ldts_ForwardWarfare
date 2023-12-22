package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.*;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Facility.*;
import com.ldts.ForwardWarfare.Element.Playable.Ground.HeavyTank;
import com.ldts.ForwardWarfare.Element.Playable.Ground.LightTank;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Tile.Border;
import com.ldts.ForwardWarfare.Element.Tile.Fields;
import com.ldts.ForwardWarfare.Element.Tile.MountainLand;
import com.ldts.ForwardWarfare.Element.Tile.Tile;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.States.Automatic.AutomaticPlayState;
import com.ldts.ForwardWarfare.State.States.Player.Selection.NoSelectionState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {

    @Test
    public void ControllerInvalidTest() {
        Exception playerEx = Assertions.assertThrows(InvalidControllerException.class, () -> {
            new Player(null, TextColor.ANSI.MAGENTA_BRIGHT, null);
        });
        Exception botEx = Assertions.assertThrows(InvalidControllerException.class, () -> {
            new Bot(null, TextColor.ANSI.GREEN_BRIGHT, null);
        });

        assertEquals("Invalid initial Factory and Base", playerEx.getMessage());
        assertEquals("Invalid initial Factory and Base", botEx.getMessage());
    }

    @Test
    public void ControllerWrongSizeTest() {
        List<Element> elements = List.of(new Fields(new Position(0, 0), new Base()));
        Exception playerEx = Assertions.assertThrows(InvalidControllerException.class, () -> {
            new Player(elements, TextColor.ANSI.GREEN_BRIGHT, null);
        });
        Exception botEx = Assertions.assertThrows(InvalidControllerException.class, () -> {
            new Bot(elements, TextColor.ANSI.GREEN_BRIGHT, null);
        });

        assertEquals("Invalid initial Factory and Base", playerEx.getMessage());
        assertEquals("Invalid initial Factory and Base", botEx.getMessage());
    }

    @Test
    public void ControllerDefaultTest() throws InvalidControllerException {
        Element base = new Fields(new Position(0, 0), new Base());
        Fields factory = new Fields(new Position(0, 0), new Factory());
        List<Element> elements = Arrays.asList(base, factory);

        Controller player = new Player(elements, TextColor.ANSI.GREEN_BRIGHT, "P1");

        assertEquals(15, player.getCoins());
        Assertions.assertSame(player.getBase(), base);
        assertEquals(1, player.getFacilities().size());
        Assertions.assertSame(factory, player.getFacilities().get(0));
        Assertions.assertTrue(player.getTroops().isEmpty());
        Assertions.assertEquals("P1", player.getName());
        Assertions.assertEquals(TextColor.ANSI.GREEN_BRIGHT, player.getControllerColor());
    }

    @Test
    public void ControllerBuyAllTest() throws InvalidControllerException {
        Element base = new Fields(new Position(0, 0), new Base());
        Fields factory = new Fields(new Position(0, 0), new Factory());
        List<Element> elements = Arrays.asList(base, factory);

        Controller player = new Player(elements, TextColor.ANSI.GREEN_BRIGHT, null);

        assertEquals(15, player.getCoins());
        Playable troop = new LightTank(new Position(1, 1));
        boolean result = player.buy(troop, 15);
        Assertions.assertTrue(result);
        assertEquals(0, player.getCoins());
        assertEquals(1, player.getTroops().size());
        Assertions.assertSame(troop, player.getTroops().get(0));
    }

    @Test
    public void ControllerBuyLessTest() throws InvalidControllerException {
        Element base = new Fields(new Position(0, 0), new Base());
        Fields factory = new Fields(new Position(0, 0), new Factory());
        List<Element> elements = Arrays.asList(base, factory);

        Controller player = new Player(elements, TextColor.ANSI.GREEN_BRIGHT, null);

        assertEquals(15, player.getCoins());
        Playable troop = new LightTank(new Position(1, 1));
        boolean result = player.buy(troop, 10);
        Assertions.assertTrue(result);
        assertEquals(5, player.getCoins());
        assertEquals(1, player.getTroops().size());
        Assertions.assertSame(troop, player.getTroops().get(0));
    }

    @Test
    public void ControllerBuyMoreTest() throws InvalidControllerException {
        Element base = new Fields(new Position(0, 0), new Base());
        Fields factory = new Fields(new Position(0, 0), new Factory());
        List<Element> elements = Arrays.asList(base, factory);

        Controller player = new Player(elements, TextColor.ANSI.GREEN_BRIGHT, null);

        assertEquals(15, player.getCoins());
        Playable troop = new LightTank(new Position(1, 1));
        boolean result = player.buy(troop, 120);
        assertFalse(result);
        assertEquals(15, player.getCoins());
        Assertions.assertTrue(player.getTroops().isEmpty());
    }

    @Test
    public void EndRoundTest() throws InvalidControllerException {
        List<Element> elements = List.of(new Fields(new Position(0, 0), new Base()),new Fields(new Position(0, 0), new Factory()));
        Controller yourInstance = new Bot(elements,TextColor.ANSI.BLUE_BRIGHT, null);
        yourInstance.endRound();
        assertFalse(yourInstance.canPlay());
        assertNull(yourInstance.getSelection1());
        assertNull(yourInstance.getSelection2());
    }

    @Test
    public void GetSetSelection1Test() throws InvalidControllerException {
        List<Element> elements = List.of(new Fields(new Position(0, 0), new Base()),new Fields(new Position(0, 0), new Factory()));
        Border borderMock = Mockito.mock(Border.class);
        Controller yourInstance = new Bot(elements, TextColor.ANSI.WHITE_BRIGHT, null);
        yourInstance.setSelection1(borderMock);
        assertEquals(borderMock, yourInstance.getSelection1());
    }

    @Test
    public void GetSetSelection2Test() throws InvalidControllerException {
        List<Element> elements = List.of(new Fields(new Position(0, 0), new Base()),new Fields(new Position(0, 0), new Factory()));
        Border borderMock = Mockito.mock(Border.class);
        Controller yourInstance = new Player(elements, TextColor.ANSI.RED_BRIGHT, null);
        yourInstance.setSelection2(borderMock);
        assertEquals(borderMock, yourInstance.getSelection2());
    }

    @Test
    public void DrawTest() throws InvalidControllerException {
        List<Element> elements = List.of(new Fields(new Position(0, 0), new Base()),new Fields(new Position(0, 0), new Factory()));
        TextGraphics graphicsMock = Mockito.mock(TextGraphics.class);
        ControllerBase yourInstance = new Player(elements, TextColor.ANSI.BLUE, null);
        Map map = Mockito.mock(Map.class);
        yourInstance.buy(new HeavyTank(new Position(0,0)), 0);
        Mockito.when(map.at(Mockito.any())).thenReturn(new Fields(new Position(0, 0), null));
        yourInstance.draw(graphicsMock, map);
        Mockito.verify(graphicsMock,
                Mockito.times(yourInstance.getTroops().size() + yourInstance.getFacilities().size() * 2 + 2))
                .putString(Mockito.any(TerminalPosition.class),Mockito.any(String.class));
    }

    @Test
    public void DrawBorderTest() throws InvalidControllerException {
        List<Element> elements = List.of(new Fields(new Position(0, 0), new Base()),new Fields(new Position(0, 0), new Factory()));
        TextGraphics graphicsMock = Mockito.mock(TextGraphics.class);
        Border selection1Mock = Mockito.mock(Border.class);
        Border selection2Mock = Mockito.mock(Border.class);
        Controller yourInstance = new Bot(elements, new TextColor.RGB(255, 255, 255), null);
        yourInstance.setSelection1(selection1Mock);
        yourInstance.setSelection2(selection2Mock);
        yourInstance.drawBorder(graphicsMock);
        Mockito.verify(selection1Mock, Mockito.times(1)).draw(graphicsMock);
        Mockito.verify(selection2Mock, Mockito.times(1)).draw(graphicsMock);
    }

    @Test
    public void ResetRoundTest() throws InvalidControllerException {
        List<Element> elements = List.of(new Fields(new Position(0, 0), new Base()),new Fields(new Position(0, 0), new Factory()));
        Controller yourInstance = new Player(elements, new TextColor.RGB(255, 0, 255), null);
        int initialCoins = yourInstance.getCoins();

        Facility oilPump1 = new OilPump();
        yourInstance.addFacility(new Fields(new Position(0,0), oilPump1));
        Facility oilPump2 = new OilPump();
        yourInstance.addFacility(new Fields(new Position(0,0), oilPump2));
        Assertions.assertFalse(oilPump1.getUsed());
        oilPump2.execute();
        Assertions.assertTrue(oilPump2.getUsed());
        HeavyTank tank = new HeavyTank(new Position(0,0));
        tank.setHasMoved(true);
        Assertions.assertTrue(tank.hasMoved());
        yourInstance.buy(tank, 0);

        yourInstance.resetRound();
        assertTrue(yourInstance.canPlay());
        long coinsFromOilPump = yourInstance.getFacilities().stream().filter(x -> ((Tile)x).getFacility() instanceof OilPump).count();
        assertEquals(initialCoins + 5 + 5 * coinsFromOilPump, yourInstance.getCoins());

        Assertions.assertFalse(oilPump1.getUsed());
        Assertions.assertFalse(oilPump2.getUsed());
        Assertions.assertFalse(tank.hasMoved());
    }

    @Test
    public void CanPlayTest() throws InvalidControllerException {
        List<Element> elements = List.of(new Fields(new Position(0, 0), new Base()),new Fields(new Position(0, 0), new Factory()));
        ControllerBase yourInstance = new Bot(elements, TextColor.ANSI.CYAN, null);
        assertTrue(yourInstance.canPlay()); // Assuming initially canPlay is true
    }

    @Test
    public void PlayerBeginState() throws InvalidControllerException {
        List<Element> elements = List.of(new Fields(new Position(0, 0), new Base()),new Fields(new Position(0, 0), new Factory()));
        Controller controller = new Player(elements, TextColor.ANSI.CYAN, null);
        Controller p2 = Mockito.mock(Controller.class);
        Map map = Mockito.mock(Map.class);

        Assertions.assertEquals(NoSelectionState.class, controller.getInitialState(p2, map).getClass());
    }

    @Test
    public void BotBeginState() throws InvalidControllerException {
        List<Element> elements = List.of(new Fields(new Position(0, 0), new Base()),new Fields(new Position(0, 0), new Factory()));
        Controller controller = new Bot(elements, TextColor.ANSI.CYAN, null);
        Controller p2 = Mockito.mock(Controller.class);
        Map map = Mockito.mock(Map.class);
        Mockito.when(map.at(Mockito.any())).thenReturn(new MountainLand(new Position(0,0)));

        Assertions.assertEquals(AutomaticPlayState.class, controller.getInitialState(p2, map).getClass());
    }

    @Test
    public void BaseGetLifes() throws InvalidControllerException {
        Base base = new Base();
        Fields fields = new Fields(new Position(0, 0), base);
        List<Element> elements = List.of(fields, new Fields(new Position(0, 0), new Factory()));
        Controller controller = new Bot(elements, TextColor.ANSI.CYAN, null);

        Assertions.assertEquals(base.getLives(), controller.getBaseLives());

        base.setLives(20);
        Assertions.assertEquals(20, controller.getBaseLives());
    }

    @Test
    public void ControllerBuyChangeColorTest() throws InvalidControllerException {
        Element base = new Fields(new Position(0, 0), new Base());
        Fields factory = new Fields(new Position(0, 0), new Factory());
        List<Element> elements = Arrays.asList(base, factory);

        Controller player = new Player(elements, TextColor.ANSI.GREEN_BRIGHT, null);

        assertEquals(15, player.getCoins());
        Playable troop = new LightTank(new Position(1, 1));
        player.buy(troop, 15);

        TextGraphics graphics = Mockito.mock(TextGraphics.class);
        troop.draw(graphics);

        Mockito.verify(graphics).setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
    }

    @Test
    public void ControllerFacilityChangeColorTest() throws InvalidControllerException {
        Element base = new Fields(new Position(0, 0), new Base());
        Fields factory = new Fields(new Position(0, 0), new Factory());
        List<Element> elements = Arrays.asList(base, factory);

        Controller player = new Player(elements, TextColor.ANSI.GREEN_BRIGHT, null);

        Fields port = new Fields(new Position(0, 0), new Port());
        player.addFacility(port);
        TextGraphics graphics = Mockito.mock(TextGraphics.class);

        port.draw(graphics);
        base.draw(graphics);
        factory.draw(graphics);

        Mockito.verify(graphics, Mockito.times(3)).setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
    }

    @Test
    public void ControllerSetSelection1() throws InvalidControllerException {
        Element base = new Fields(new Position(0, 0), new Base());
        Fields factory = new Fields(new Position(0, 0), new Factory());
        List<Element> elements = Arrays.asList(base, factory);

        Controller player = new Player(elements, TextColor.ANSI.GREEN_BRIGHT, null);
        player.setSelection1(null);
        Assertions.assertNull(player.getSelection1());

        Border border = new Border(new Position(1,1));
        player.setSelection1(border);

        TextGraphics graphics = Mockito.mock(TextGraphics.class);
        border.draw(graphics);

        Mockito.verify(graphics).setForegroundColor(TextColor.ANSI.RED_BRIGHT);
    }

    @Test
    public void ControllerSetSelection2() throws InvalidControllerException {
        Element base = new Fields(new Position(0, 0), new Base());
        Fields factory = new Fields(new Position(0, 0), new Factory());
        List<Element> elements = Arrays.asList(base, factory);

        Controller player = new Player(elements, TextColor.ANSI.GREEN_BRIGHT, null);
        player.setSelection2(null);
        Assertions.assertNull(player.getSelection2());

        Border border = new Border(new Position(1,1));
        player.setSelection2(border);

        TextGraphics graphics = Mockito.mock(TextGraphics.class);
        border.draw(graphics);

        Mockito.verify(graphics).setForegroundColor(TextColor.ANSI.CYAN_BRIGHT);
    }

    @Test
    public void ControllerSetBase() throws InvalidControllerException {
        Element base = new Fields(new Position(0, 0), new Base());
        Fields factory = new Fields(new Position(0, 0), new Factory());
        List<Element> elements = Arrays.asList(base, factory);

        Controller player = new Player(elements, TextColor.ANSI.GREEN_BRIGHT, null);

        Assertions.assertSame(base, player.getBase());

        Element newBase = new Fields(new Position(5, 5), new Base());
        player.setBase(newBase);
        Assertions.assertSame(newBase, player.getBase());
    }

    @Test
    public void ResetRoundBaseAttackedTest() throws InvalidControllerException {
        Base base =  new Base();
        base.setAttackedLastTurn(true);
        base.takeDamage();
        base.execute();
        List<Element> elements = List.of(new Fields(new Position(0, 0), base),
                new Fields(new Position(0, 0), new Factory()));

        Controller yourInstance = new Player(elements, new TextColor.RGB(255, 0, 255), null);
        yourInstance.resetRound();

        Assertions.assertFalse(base.getUsed());
        Assertions.assertEquals(1, base.getLives());
        Assertions.assertFalse(base.getAttackedLastTurn());
    }

    @Test
    public void ResetRoundBaseNotAttackedTest() throws InvalidControllerException {
        Base base =  new Base();
        base.setAttackedLastTurn(false);
        base.takeDamage();
        List<Element> elements = List.of(new Fields(new Position(0, 0), base),
                new Fields(new Position(0, 0), new Factory()));

        Controller yourInstance = new Player(elements, new TextColor.RGB(255, 0, 255), null);
        yourInstance.resetRound();

        Assertions.assertFalse(base.getUsed());
        Assertions.assertEquals(2, base.getLives());
        Assertions.assertFalse(base.getAttackedLastTurn());
    }
}
