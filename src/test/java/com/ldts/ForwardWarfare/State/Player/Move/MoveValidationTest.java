package com.ldts.ForwardWarfare.State.Player.Move;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Playable.Ground.HeavyTank;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Tile.Border;
import com.ldts.ForwardWarfare.Element.Tile.MountainLand;
import com.ldts.ForwardWarfare.Element.Tile.Water;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.Map.MapParseException;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.States.Player.Move.MoveAnimationState;
import com.ldts.ForwardWarfare.State.States.Player.Move.MoveEndState;
import com.ldts.ForwardWarfare.State.States.Player.Move.MoveValidationState;
import com.ldts.ForwardWarfare.State.States.Player.Selection.InvalidSelectState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MoveValidationTest {

    private Controller p1;
    private Controller p2;
    private Map map;
    private TextGraphics graphics;
    @BeforeEach
    public void ResetMocks() {
        map = Mockito.mock(Map.class);
        p1 = Mockito.mock(Controller.class);
        p2 = Mockito.mock(Controller.class);
        graphics = Mockito.mock(TextGraphics.class);
    }

    @Test
    public void BaseTest() {
        State state = new MoveValidationState(p1, p2, map);
        state.draw(graphics);

        Assertions.assertFalse(state.requiresInput());
    }

    @Test
    public void InvalidPlayTest() {
        Position pos = new Position(0,0);
        Mockito.when(p1.getSelection2()).thenReturn(new Border(pos));
        Mockito.when(map.at(pos)).thenReturn(new MountainLand(pos));

        State state = new MoveValidationState(p1, p2, map);
        State result = state.play(null);
        Assertions.assertEquals(InvalidSelectState.class, result.getClass());
        Mockito.verify(p1).setSelection2(null);
    }

    @Test
    public void PlayEmptyTest() {
        Position pos = new Position(0,0);
        Border border = new Border(pos);
        Mockito.when(p1.getSelection2()).thenReturn(border);
        Mockito.when(map.at(pos)).thenReturn(new Water(pos, null));

        State state = new MoveValidationState(p1, p2, map);
        State result = state.play(null);

        Mockito.verify(p1).setSelection2(null);
        Mockito.verify(p1).setSelection1(border);
        Assertions.assertEquals(MoveEndState.class, result.getClass());
    }

    @Test
    public void PlayTest() throws FileNotFoundException, MapParseException, URISyntaxException {
        Position pos = new Position(2,3);
        Map map = new Map("1.fw");

        Border border = new Border(pos);
        Mockito.when(p1.getSelection2()).thenReturn(border);
        Mockito.when(p1.getSelection1()).thenReturn(border);

        HeavyTank tank = new HeavyTank(pos);
        Assertions.assertFalse(tank.hasMoved());
        List<Element> elements = List.of(tank);
        Mockito.when(p1.getTroops()).thenReturn(elements);
        Mockito.when(p2.getTroops()).thenReturn(new ArrayList<>());

        State state = new MoveValidationState(p1, p2, map);
        State result = state.play(null);

        Mockito.verify(p1).setSelection1(border);
        Mockito.verify(p1).setSelection2(null);
        Assertions.assertTrue(tank.hasMoved());
        Assertions.assertEquals(MoveAnimationState.class, result.getClass());
    }

    @Test
    public void NullPlayTest() throws FileNotFoundException, MapParseException, URISyntaxException {
        Position pos = new Position(2,3);
        Map map = new Map("1.fw");

        Border border = new Border(pos);
        Border border2 = new Border(new Position(2,1));
        Mockito.when(p1.getSelection2()).thenReturn(border2);
        Mockito.when(p1.getSelection1()).thenReturn(border);

        HeavyTank tank = new HeavyTank(pos);
        Assertions.assertFalse(tank.hasMoved());
        List<Element> elements = List.of(tank);
        Mockito.when(p1.getTroops()).thenReturn(elements);
        Mockito.when(p2.getTroops()).thenReturn(new ArrayList<>());

        State state = new MoveValidationState(p1, p2, map);
        State result = state.play(null);

        Assertions.assertEquals(InvalidSelectState.class, result.getClass());
    }

    @Test
    public void NoMatchTest() throws FileNotFoundException, MapParseException, URISyntaxException {
        Position pos = new Position(2,3);
        Map map = new Map("1.fw");

        Border border = new Border(new Position(2, 1));
        Border border2 = new Border(new Position(2,1));
        Mockito.when(p1.getSelection2()).thenReturn(border2);
        Mockito.when(p1.getSelection1()).thenReturn(border);

        HeavyTank tank = new HeavyTank(pos);
        List<Element> elements = List.of(tank);
        Mockito.when(p1.getTroops()).thenReturn(elements);
        Mockito.when(p2.getTroops()).thenReturn(new ArrayList<>());

        State state = new MoveValidationState(p1, p2, map);
        State result = state.play(null);

        Assertions.assertEquals(MoveEndState.class, result.getClass());
    }

    @Test
    public void EqualPlayTest() throws FileNotFoundException, MapParseException, URISyntaxException {
        Position pos = new Position(2,3);
        Map map = new Map("1.fw");

        Border border = new Border(pos);
        Border border2 = new Border(new Position(2,6));
        Mockito.when(p1.getSelection2()).thenReturn(border2);
        Mockito.when(p1.getSelection1()).thenReturn(border);

        HeavyTank tank = new HeavyTank(pos);
        Assertions.assertFalse(tank.hasMoved());
        List<Element> elements = List.of(tank);
        Mockito.when(p1.getTroops()).thenReturn(elements);
        Mockito.when(p2.getTroops()).thenReturn(new ArrayList<>());

        State state = new MoveValidationState(p1, p2, map);
        State result = state.play(null);

        Assertions.assertEquals(InvalidSelectState.class, result.getClass());
    }

    @Test
    public void CanMoveLineTest() throws FileNotFoundException, MapParseException, URISyntaxException {
        Map map = new Map("1.fw");

        MoveValidationState state = new MoveValidationState(p1, p2, map);
        Position pos1 = new Position(2,3);
        Position pos2 = new Position(2, 6);
        List<Position> elements = state.canMove(pos1, pos2, new HeavyTank(pos1));

        List<Position> expected = Arrays.asList(pos2, new Position(2, 5),
                new Position(2,4), pos1);
        Assertions.assertEquals(expected, elements);
    }

    @Test
    public void CanMove00Test() throws FileNotFoundException, MapParseException, URISyntaxException {
        Map map = new Map("tests/allGrass.fw");

        Mockito.when(p1.getTroops()).thenReturn(
                List.of(new HeavyTank(new Position(0, 2))));
        Mockito.when(p2.getTroops()).thenReturn(
                List.of(new HeavyTank(new Position(0, 1))));

        MoveValidationState state = new MoveValidationState(p1, p2, map);
        Position pos1 = new Position(0,0);
        Position pos2 = new Position(0, 3);
        List<Position> elements = state.canMove(pos1, pos2, new HeavyTank(pos1));

        List<Position> expected = Arrays.asList(pos2, new Position(1, 3),
                new Position(1, 2), new Position(1, 1),
                new Position(1,0), pos1);
        Assertions.assertEquals(expected, elements);
    }

    @Test
    public void CanMove1510Test() throws FileNotFoundException, MapParseException, URISyntaxException {
        Map map = new Map("tests/allGrass.fw");

        Mockito.when(p1.getTroops()).thenReturn(
                List.of(new HeavyTank(new Position(14, 8))));
        Mockito.when(p2.getTroops()).thenReturn(
                List.of(new HeavyTank(new Position(14, 7))));

        MoveValidationState state = new MoveValidationState(p1, p2, map);
        Position pos1 = new Position(14,9);
        Position pos2 = new Position(14, 6);
        List<Position> elements = state.canMove(pos1, pos2, new HeavyTank(pos1));

        Assertions.assertEquals(6, elements.size());
    }
}
