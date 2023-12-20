package com.ldts.ForwardWarfare.State.Player.Selection.Capture;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Facility.OilPump;
import com.ldts.ForwardWarfare.Element.Playable.Ground.HeavyTank;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Tile.Border;
import com.ldts.ForwardWarfare.Element.Tile.MountainLand;
import com.ldts.ForwardWarfare.Element.Tile.Tile;
import com.ldts.ForwardWarfare.Element.Tile.Water;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.Map.MapParseException;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.Player.Move.MoveEndTest;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.States.Player.Move.MoveEndState;
import com.ldts.ForwardWarfare.State.States.Player.Selection.Attack.AttackNoSelectionState;
import com.ldts.ForwardWarfare.State.States.Player.Selection.Capture.CaptureNoSelectionState;
import com.ldts.ForwardWarfare.State.States.Player.Selection.Capture.CaptureState;
import com.ldts.ForwardWarfare.State.States.QuitState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Or;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class CaptureNoSelectionTest {

    private Element origin;
    private Controller p1;
    private Controller p2;
    private Map map;
    private Border border;
    private Tile tile;
    private TextGraphics graphics;
    @BeforeEach
    public void ResetMocks() throws FileNotFoundException, MapParseException, URISyntaxException {
        map = new Map("tests/capture.fw");
        p1 = Mockito.mock(Controller.class);
        p2 = Mockito.mock(Controller.class);
        graphics = Mockito.mock(TextGraphics.class);

        border = Mockito.mock(Border.class);
        Mockito.when(p1.getSelection1()).thenReturn(border);
        Mockito.when(border.getPosition()).thenReturn(new Position(0,0));

        origin = new Water(new Position(5,5), null);

        Mockito.when(p1.getBase()).thenReturn(map.getPlayer1().get(0));
        Mockito.when(p2.getBase()).thenReturn(map.getPlayer2().get(0));
    }

    @Test
    public void BaseTest() {
        State state = new CaptureNoSelectionState(p1, p2, map, origin);

        Assertions.assertTrue(state.requiresInput());
        Mockito.verify(p1).setSelection1(null);

        state.draw(graphics);
        Mockito.verify(graphics).putString(1, 12, "Nothing to");
    }

    @Test
    public void PlayEmptyTest() {
        State state = new CaptureNoSelectionState(p1, p2, map, origin);

        State result = state.play(Action.NONE);

        Assertions.assertEquals(MoveEndState.class, result.getClass());
        Mockito.verify(p1).setSelection1(Mockito.notNull());
    }

    @Test
    public void PlayQuitTest() {
        origin.setPosition(new Position(11, 9));

        State state = new CaptureNoSelectionState(p1, p2, map, origin);

        State result = state.play(Action.QUIT);
        state.draw(graphics);

        Assertions.assertEquals(QuitState.class, result.getClass());
        Assertions.assertTrue(result.requiresInput());
        Mockito.verify(p1).setSelection1(Mockito.notNull());
        Mockito.verify(graphics).putString(3, 13, "Oil Pump");
    }

    @Test
    public void PlayEscapeTest() {
        origin.setPosition(new Position(11, 9));
        State state = new CaptureNoSelectionState(p1, p2, map, origin);

        State result = state.play(Action.ESCAPE);

        Assertions.assertEquals(MoveEndState.class, result.getClass());
        Mockito.verify(p1, Mockito.times(2)).setSelection1(Mockito.any(Border.class));
    }

    @Test
    public void PlayEnterTest() {
        origin.setPosition(new Position(11, 9));

        State state = new CaptureNoSelectionState(p1, p2, map, origin);

        State result = state.play(Action.ENTER);

        Assertions.assertEquals(CaptureState.class, result.getClass());
    }

    @Test
    public void PlayYaxisTest() {
        origin.setPosition(new Position(11, 8));

        State state = new CaptureNoSelectionState(p1, p2, map, origin);

        Mockito.reset(border);
        State result = state.play(Action.UP);

        Assertions.assertSame(result, state);
        Mockito.verify(border).setPosition(new Position(10, 7));

        Mockito.reset(border);
        result = state.play(Action.DOWN);

        Assertions.assertSame(result, state);
        Mockito.verify(border).setPosition(new Position(10, 7));
        Mockito.verify(border).setBackgroundColor(Mockito.notNull());
    }

    @Test
    public void DrawNamesTest() {
        origin.setPosition(new Position(11, 8));

        State state = new CaptureNoSelectionState(p1, p2, map, origin);

        state.draw(graphics);
        Mockito.verify(graphics).putString(3, 13, "Port");

        state.play(Action.RIGHT);
        state.draw(graphics);
        Mockito.verify(graphics).putString(3, 13, "Airport");

        state.play(Action.RIGHT);
        state.play(Action.RIGHT);
        state.play(Action.RIGHT);
        state.draw(graphics);
        Mockito.verify(graphics).putString(3, 13, "Factory");

        Mockito.reset(graphics);
        origin.setPosition(new Position(0, 4));
        State state1 = new CaptureNoSelectionState(p1, p2, map, origin);
        state1.draw(graphics);
        Mockito.verify(graphics).putString(3, 13, "Base");
    }

    @Test
    public void VerifyBorderMovement() {
        origin.setPosition(new Position(11, 8));

        new CaptureNoSelectionState(p1, p2, map, origin);
        Mockito.verify(border).setPosition(new Position(10, 7));
    }

    @Test
    public void UsedBaseTest() {
        ((Tile) p2.getBase()).getFacility().execute();
        origin.setPosition(new Position(0, 4));

        State state = new CaptureNoSelectionState(p1, p2, map, origin);
        state.draw(graphics);

        Mockito.verify(graphics).putString(1, 12, "Nothing to");
    }

    @Test
    public void OutSideMapTest() {
        origin.setPosition(new Position(20, 20));
        Map map = Mockito.mock(Map.class);
        List<Element> elements = Arrays.asList(
                new Water(new Position(20, 19), new OilPump()),
                new Water(new Position(20, 18), null));
        Mockito.when(map.getElements()).thenReturn(elements);
        Mockito.when(map.inside(Mockito.any())).thenReturn(false);
        Mockito.when(map.at(Mockito.notNull())).thenReturn((Tile) elements.get(0));

        new CaptureNoSelectionState(p1, p2, map, origin);

        Mockito.verify(border, Mockito.never()).setPosition(Mockito.notNull());
    }

    @Test
    public void PlayXaxisTest() {
        origin.setPosition(new Position(0, 8));

        State state = new CaptureNoSelectionState(p1, p2, map, origin);

        Mockito.verify(border).setPosition(new Position(0, 9));
        state.play(Action.RIGHT);
        Mockito.verify(border).setPosition(new Position(1, 9));

        Mockito.reset(border);
        state.play(Action.RIGHT);
        Mockito.verify(border).setPosition(new Position(1, 9));

        state.play(Action.LEFT);
        Mockito.verify(border).setPosition(new Position(0, 9));

        Mockito.reset(border);
        state.play(Action.LEFT);
        Mockito.verify(border).setPosition(new Position(0, 9));
    }

    @Test
    public void NotOwnedTest() {
        origin.setPosition(new Position(1, 0));
        List<Element> elements = List.of(map.getPlayer1().get(1));
        Mockito.when(p1.getFacilities()).thenReturn(elements);

        State state = new CaptureNoSelectionState(p1, p2, map, origin);
        state.draw(graphics);

        Mockito.verify(graphics).putString(1, 12, "Nothing to");
    }

    @Test
    public void OneOwnedTest() {
        origin.setPosition(new Position(0, 8));
        List<Element> elements = List.of((Element) map.at(new Position(0, 9)));
        Mockito.when(p1.getFacilities()).thenReturn(elements);

        State state = new CaptureNoSelectionState(p1, p2, map, origin);
        state.draw(graphics);

        Mockito.verify(graphics).putString(3, 13, "Port");
    }
}
