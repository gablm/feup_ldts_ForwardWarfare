package com.ldts.ForwardWarfare.State.Player.Selection;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Facility.Airport;
import com.ldts.ForwardWarfare.Element.Facility.Facility;
import com.ldts.ForwardWarfare.Element.Facility.Factory;
import com.ldts.ForwardWarfare.Element.Facility.OilPump;
import com.ldts.ForwardWarfare.Element.Playable.Ground.HeavyTank;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Tile.Border;
import com.ldts.ForwardWarfare.Element.Tile.Tile;
import com.ldts.ForwardWarfare.Element.Tile.Water;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.States.Player.BuyState;
import com.ldts.ForwardWarfare.State.States.Player.Move.MoveEndState;
import com.ldts.ForwardWarfare.State.States.Player.Selection.InvalidSelectState;
import com.ldts.ForwardWarfare.State.States.Player.Selection.NoSelectionState;
import com.ldts.ForwardWarfare.State.States.Player.Selection.OneSelectionState;
import com.ldts.ForwardWarfare.State.States.QuitState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class NoSelectionTest {

    private Controller p1;
    private Controller p2;
    private Map map;
    private Border border;
    private Tile tile;
    private TextGraphics graphics;
    @BeforeEach
    public void ResetMocks() {
        map = Mockito.mock(Map.class);
        border = Mockito.mock(Border.class);
        Mockito.when(border.getPosition())
                .thenReturn(new Position(5,5));
        p1 = Mockito.mock(Controller.class);
        Mockito.when(p1.getSelection2())
                .thenReturn(border);
        Mockito.when(p1.getSelection1())
                .thenReturn(border);
        p2 = Mockito.mock(Controller.class);
        graphics = Mockito.mock(TextGraphics.class);

        tile = Mockito.mock(Tile.class);
        Mockito.when(map.at(Mockito.any())).thenReturn(tile);
        Mockito.when(tile.getColor()).thenReturn(TextColor.ANSI.BLUE);
    }

    @Test
    public void BaseTest() {
        Mockito.when(tile.getColor()).thenReturn(TextColor.ANSI.RED);

        State state = new NoSelectionState(p1, p2, map);
        state.draw(graphics);

        Assertions.assertTrue(state.requiresInput());
        Mockito.verify(border).setBackgroundColor(TextColor.ANSI.RED);
    }

    @Test
    public void BaseNullTest() {
        Mockito.when(tile.getColor()).thenReturn(TextColor.ANSI.RED);
        Mockito.when(p1.getSelection1()).thenReturn(null, border);
        Mockito.when(map.inside(Mockito.any())).thenReturn(true);

        State state = new NoSelectionState(p1, p2, map);
        Mockito.verify(p1).setSelection1(Mockito.any(Border.class));
        Mockito.verify(border).setPosition(new Position(5, 7));
        Mockito.verify(border).setBackgroundColor(TextColor.ANSI.RED);
    }

    @Test
    public void BaseNotInsideTest() {
        Mockito.when(tile.getColor()).thenReturn(TextColor.ANSI.RED);
        Mockito.when(p1.getSelection1()).thenReturn(null, border);
        Mockito.when(map.inside(Mockito.any())).thenReturn(false);

        State state = new NoSelectionState(p1, p2, map);
        Mockito.verify(p1).setSelection1(Mockito.any(Border.class));
        Mockito.verify(border, Mockito.never()).setPosition(new Position(5, 7));
    }

    private void PlayTestTemplate(Action action, int x, int y) {
        Mockito.when(map.inside(Mockito.any())).thenReturn(true);

        State state = new NoSelectionState(p1, p2, map);

        State result = state.play(action);
        Assertions.assertSame(state, result);
        Mockito.verify(border).setPosition(new Position(x, y));
        Mockito.verify(border, Mockito.times(2)).setBackgroundColor(TextColor.ANSI.BLUE);
    }

    @Test
    public void PlayUpTest() {
        PlayTestTemplate(Action.UP, 5, 4);
    }

    @Test
    public void PlayDownTest() {
        PlayTestTemplate(Action.DOWN, 5, 6);
    }

    @Test
    public void PlayLeftTest() {
        PlayTestTemplate(Action.LEFT, 4, 5);
    }

    @Test
    public void PlayRightTest() {
        PlayTestTemplate(Action.RIGHT, 6, 5);
    }

    @Test
    public void PlayEscapeTest() {
        State state = new NoSelectionState(p1, p2, map);

        State result = state.play(Action.ESCAPE);
        Assertions.assertEquals(MoveEndState.class, result.getClass());
    }

    @Test
    public void PlayQuitTest() {
        State state = new NoSelectionState(p1, p2, map);

        State result = state.play(Action.QUIT);
        Assertions.assertEquals(QuitState.class, result.getClass());
    }

    @Test
    public void PlayEnterNullTest() {
        Mockito.when(p1.getTroops()).thenReturn(new ArrayList<>());
        Mockito.when(tile.getFacility()).thenReturn(null);

        State state = new NoSelectionState(p1, p2, map);

        State result = state.play(Action.ENTER);

        Assertions.assertEquals(InvalidSelectState.class, result.getClass());
    }

    @Test
    public void PlayEnterFacilityTest() {
        Mockito.when(p1.getTroops()).thenReturn(new ArrayList<>());
        Mockito.when(p1.getFacilities()).thenReturn(new ArrayList<>());
        Mockito.when(tile.getFacility()).thenReturn(new Factory());

        State state = new NoSelectionState(p1, p2, map);

        State result = state.play(Action.ENTER);

        Assertions.assertEquals(InvalidSelectState.class, result.getClass());
    }

    @Test
    public void PlayEnterInvalidFacilityTest() {
        Mockito.when(p1.getTroops()).thenReturn(new ArrayList<>());
        List<Element> facilities = List.of(new Water(new Position(5,5), null));
        Mockito.when(p1.getFacilities()).thenReturn(facilities);
        Mockito.when(tile.getFacility()).thenReturn(new OilPump());

        State state = new NoSelectionState(p1, p2, map);

        State result = state.play(Action.ENTER);

        Assertions.assertEquals(InvalidSelectState.class, result.getClass());
    }

    @Test
    public void PlayEnterOwnedFacilityTest() {
        Mockito.when(p1.getTroops()).thenReturn(new ArrayList<>());
        List<Element> facilities = List.of(new Water(new Position(5,5), null));
        Mockito.when(p1.getFacilities()).thenReturn(facilities);
        Mockito.when(tile.getFacility()).thenReturn(new Factory());

        State state = new NoSelectionState(p1, p2, map);

        State result = state.play(Action.ENTER);

        Assertions.assertEquals(BuyState.class, result.getClass());
    }

    @Test
    public void PlayEnterOwnedUsedFacilityTest() {
        Mockito.when(p1.getTroops()).thenReturn(new ArrayList<>());
        List<Element> facilities = List.of(new Water(new Position(5,5), null));
        Mockito.when(p1.getFacilities()).thenReturn(facilities);
        Facility facility = new Factory();
        facility.execute();
        Mockito.when(tile.getFacility()).thenReturn(facility);

        State state = new NoSelectionState(p1, p2, map);

        State result = state.play(Action.ENTER);

        Assertions.assertEquals(InvalidSelectState.class, result.getClass());
    }

    @Test
    public void PlayEnterNoMatchesFacilityTest() {
        Mockito.when(p1.getTroops()).thenReturn(new ArrayList<>());
        List<Element> facilities = List.of(new Water(new Position(4,6), null));
        Mockito.when(p1.getFacilities()).thenReturn(facilities);
        Mockito.when(tile.getFacility()).thenReturn(new Airport());

        State state = new NoSelectionState(p1, p2, map);

        State result = state.play(Action.ENTER);

        Assertions.assertEquals(InvalidSelectState.class, result.getClass());
    }

    @Test
    public void PlayEnterNoMatchesTest() {
        Mockito.when(p1.getFacilities()).thenReturn(new ArrayList<>());
        List<Element> troops = List.of(new HeavyTank(new Position(6,6)));
        Mockito.when(p1.getTroops()).thenReturn(troops);

        State state = new NoSelectionState(p1, p2, map);

        State result = state.play(Action.ENTER);

        Assertions.assertEquals(InvalidSelectState.class, result.getClass());
    }

    @Test
    public void PlayEnterMatchesCanMoveTest() {
        Mockito.when(p1.getFacilities()).thenReturn(new ArrayList<>());
        Playable tank = new HeavyTank(new Position(5,5));
        List<Element> troops = List.of(tank);
        Mockito.when(p1.getTroops()).thenReturn(troops);

        State state = new NoSelectionState(p1, p2, map);

        State result = state.play(Action.ENTER);

        Assertions.assertEquals(OneSelectionState.class, result.getClass());
        Mockito.verify(p1).setSelection2(Mockito.any(Border.class));
    }

    @Test
    public void PlayEnterMatchesCannotMoveTest() {
        Mockito.when(p1.getFacilities()).thenReturn(new ArrayList<>());
        Playable tank = new HeavyTank(new Position(5,5));
        tank.setHasMoved(true);
        List<Element> troops = List.of(tank);
        Mockito.when(p1.getTroops()).thenReturn(troops);

        State state = new NoSelectionState(p1, p2, map);

        State result = state.play(Action.ENTER);

        Assertions.assertEquals(InvalidSelectState.class, result.getClass());
    }
}
