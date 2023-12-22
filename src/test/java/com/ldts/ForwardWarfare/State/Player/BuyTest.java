package com.ldts.ForwardWarfare.State.Player;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Facility.*;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Tile.Border;
import com.ldts.ForwardWarfare.Element.Tile.Water;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.Map.MapParseException;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.States.Player.BuyState;
import com.ldts.ForwardWarfare.State.States.Player.Selection.NoSelectionState;
import com.ldts.ForwardWarfare.State.States.Player.SpawnTroopState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.URISyntaxException;

public class BuyTest {
    private Controller p1;
    private Controller p2;
    private Map map;
    private Border border;
    private TextGraphics graphics;
    @BeforeEach
    public void ResetMocks() throws FileNotFoundException, MapParseException, URISyntaxException {
        map = Mockito.mock(Map.class);
        p1 = Mockito.mock(Controller.class);
        p2 = Mockito.mock(Controller.class);
        graphics = Mockito.mock(TextGraphics.class);

        border = Mockito.mock(Border.class);
        Mockito.when(p1.getSelection1()).thenReturn(border);
        Mockito.when(border.getPosition()).thenReturn(new Position(0,0));
    }

    @Test
    public void BaseFactoryTest() {
        Facility facility = new Factory();
        Position pos = new Position(0, 0);

        State state = new BuyState(p1, p2, map, facility, pos);
        state.draw(graphics);

        Assertions.assertTrue(state.requiresInput());
        Mockito.verify(graphics).setBackgroundColor(new TextColor.RGB(80, 80, 80));
    }

    @Test
    public void BasePortTest() {
        Facility facility = new Port();
        Position pos = new Position(0, 0);

        State state = new BuyState(p1, p2, map, facility, pos);
        state.draw(graphics);

        Mockito.verify(graphics).setBackgroundColor(new TextColor.RGB(0, 124, 206));
    }

    @Test
    public void BaseAirportTest() {
        Facility facility = new Airport();
        Position pos = new Position(0, 0);

        State state = new BuyState(p1, p2, map, facility, pos);
        state.draw(graphics);

        Assertions.assertTrue(state.requiresInput());
        Mockito.verify(graphics).setBackgroundColor(new TextColor.RGB(153, 76, 0));
    }

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    @Test
    public void BaseInvalidTest() {
        System.setOut(new PrintStream(outContent));

        Facility facility = new OilPump();
        Position pos = new Position(0, 0);

        State state = new BuyState(p1, p2, map, facility, pos);
        state.draw(graphics);

        Assertions.assertEquals("Invalid facility", outContent.toString().substring(0, 16));
    }

    @Test
    public void NoCashDrawTest() {
        Facility facility = new Factory();
        Position pos = new Position(0, 0);
        Mockito.when(p1.getCoins()).thenReturn(0);

        State state = new BuyState(p1, p2, map, facility, pos);
        State result = state.play(Action.ENTER);
        state.draw(graphics);

        Assertions.assertSame(state, result);

        State result1 = state.play(Action.ENTER);

        Assertions.assertSame(state, result1);
        Mockito.verify(p1).getCoins();
        Mockito.verify(graphics).putString(2, 17, "Too Expensive");
        Mockito.verify(graphics, Mockito.times(2)).setBackgroundColor(new TextColor.RGB(80, 80, 80));
    }

    @Test
    public void NoCashAirportPlayTest() {
        Facility facility = new Airport();
        Position pos = new Position(0, 0);
        Mockito.when(p1.getCoins()).thenReturn(0);

        State state = new BuyState(p1, p2, map, facility, pos);
        state.play(Action.ENTER);
        state.draw(graphics);

        Mockito.verify(graphics).putString(2, 17, "Too Expensive");
        Mockito.verify(graphics, Mockito.times(2)).setBackgroundColor(new TextColor.RGB(153, 76, 0));
    }

    @Test
    public void NoCashPortPlayTest() {
        Facility facility = new Port();
        Position pos = new Position(0, 0);
        Mockito.when(p1.getCoins()).thenReturn(0);

        State state = new BuyState(p1, p2, map, facility, pos);
        state.play(Action.ENTER);
        state.draw(graphics);

        Mockito.verify(graphics).putString(2, 17, "Too Expensive");
        Mockito.verify(graphics, Mockito.times(2)).setBackgroundColor(new TextColor.RGB(0, 124, 206));
    }

    @Test
    public void EnoughCashPlayTest() {
        Facility facility = new Port();
        Position pos = new Position(0, 0);
        Mockito.when(p1.getCoins()).thenReturn(1000);

        State state = new BuyState(p1, p2, map, facility, pos);
        State result = state.play(Action.ENTER);

        Assertions.assertEquals(SpawnTroopState.class, result.getClass());
    }

    @Test
    public void BarelyEnoughCashPlayTest() {
        Facility facility = new Port();
        Position pos = new Position(0, 0);
        Mockito.when(p1.getCoins()).thenReturn(7);

        State state = new BuyState(p1, p2, map, facility, pos);
        State result = state.play(Action.ENTER);

        Assertions.assertEquals(SpawnTroopState.class, result.getClass());
    }

    @Test
    public void PlayEscapeTest() {
        Facility facility = new Port();
        Position pos = new Position(0, 0);
        Mockito.when(p1.getCoins()).thenReturn(1000);
        Mockito.when(map.at(Mockito.any())).thenReturn(new Water(null, null));

        State state = new BuyState(p1, p2, map, facility, pos);
        State result = state.play(Action.ESCAPE);

        Assertions.assertEquals(NoSelectionState.class, result.getClass());
    }

    private void DirectionalTest(BuyState state, int initial, Action action, int expected) {
        state.setHighlighted(initial);
        state.play(action);
        Assertions.assertEquals(expected, state.getHighlighted());
    }

    @Test
    public void FactoryDirectionalPlayTest() {
        Facility facility = new Factory();
        Position pos = new Position(0, 0);
        Mockito.when(p1.getCoins()).thenReturn(1000);
        Mockito.when(map.at(Mockito.any())).thenReturn(new Water(null, null));

        BuyState state = new BuyState(p1, p2, map, facility, pos);

        DirectionalTest(state, 0, Action.UP, 5);
        DirectionalTest(state, 1, Action.UP, 0);

        DirectionalTest(state, 4, Action.DOWN, 5);
        DirectionalTest(state, 5, Action.DOWN, 0);

        DirectionalTest(state, 2, Action.RIGHT, 5);
        DirectionalTest(state, 1, Action.RIGHT, 4);
        DirectionalTest(state, 4, Action.RIGHT, 1);

        DirectionalTest(state, 3, Action.LEFT, 0);
        DirectionalTest(state, 2, Action.LEFT, 5);
        DirectionalTest(state, 1, Action.LEFT, 4);
    }

    @Test
    public void AirportDirectionalPlayTest() {
        Facility facility = new Airport();
        Position pos = new Position(0, 0);
        Mockito.when(p1.getCoins()).thenReturn(1000);
        Mockito.when(map.at(Mockito.any())).thenReturn(new Water(null, null));

        BuyState state = new BuyState(p1, p2, map, facility, pos);

        DirectionalTest(state, 0, Action.UP, 2);
        DirectionalTest(state, 1, Action.UP, 0);

        DirectionalTest(state, 1, Action.DOWN, 2);
        DirectionalTest(state, 2, Action.DOWN, 0);

        DirectionalTest(state, 2, Action.LEFT, 0);
        DirectionalTest(state, 1, Action.LEFT, 2);

        DirectionalTest(state, 2, Action.RIGHT, 0);
        DirectionalTest(state, 1, Action.RIGHT, 2);
    }

    @Test
    public void PortDirectionalPlayTest() {
        Facility facility = new Port();
        Position pos = new Position(0, 0);
        Mockito.when(p1.getCoins()).thenReturn(1000);
        Mockito.when(map.at(Mockito.any())).thenReturn(new Water(null, null));

        BuyState state = new BuyState(p1, p2, map, facility, pos);

        DirectionalTest(state, 0, Action.UP, 2);
        DirectionalTest(state, 1, Action.UP, 0);

        DirectionalTest(state, 1, Action.DOWN, 2);
        DirectionalTest(state, 2, Action.DOWN, 0);

        DirectionalTest(state, 2, Action.LEFT, 0);
        DirectionalTest(state, 1, Action.LEFT, 2);

        DirectionalTest(state, 2, Action.RIGHT, 0);
        DirectionalTest(state, 1, Action.RIGHT, 2);
    }
}
