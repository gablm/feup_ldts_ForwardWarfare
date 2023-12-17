package com.ldts.ForwardWarfare.State.Player.Selection.Attack;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Controller.Player;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Playable.Ground.AntiAirTank;
import com.ldts.ForwardWarfare.Element.Playable.Ground.HeavyPerson;
import com.ldts.ForwardWarfare.Element.Playable.Ground.HeavyTank;
import com.ldts.ForwardWarfare.Element.Playable.Ground.LightPerson;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Tile.Border;
import com.ldts.ForwardWarfare.Element.Tile.Tile;
import com.ldts.ForwardWarfare.Element.Tile.Water;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.States.Player.Move.MoveEndState;
import com.ldts.ForwardWarfare.State.States.Player.Selection.Attack.AttackNoSelectionState;
import com.ldts.ForwardWarfare.State.States.Player.Selection.Attack.AttackState;
import com.ldts.ForwardWarfare.State.States.QuitState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class AttackNoSelectionTest {

    private Playable tank;
    private Controller p1;
    private Controller p2;
    private Map map;
    private Border border;
    private Tile tile;
    private TextGraphics graphics;
    @BeforeEach
    public void ResetMocks() {
        tank = new HeavyTank(new Position(0, 0));
        map = Mockito.mock(Map.class);
        p1 = Mockito.mock(Controller.class);
        p2 = Mockito.mock(Controller.class);
        graphics = Mockito.mock(TextGraphics.class);

        border = Mockito.mock(Border.class);
        Mockito.when(p1.getSelection1()).thenReturn(border);

        Mockito.when(map.inside(Mockito.any())).thenReturn(true);
        tile = Mockito.mock(Tile.class);
        Mockito.when(map.at(Mockito.any())).thenReturn(tile);
    }

    @Test
    public void BaseTest() {
        tank = new HeavyTank(null);
        State state = new AttackNoSelectionState(p1, p2, map, tank);

        Assertions.assertTrue(state.requiresInput());
        Mockito.verify(p1).setSelection1(null);

        state.draw(graphics);
        Mockito.verify(graphics).putString(1, 12, "Nothing to");
    }

    @Test
    public void NullElementTest() {
        State state = new AttackNoSelectionState(p1, p2, map, null);

        Assertions.assertTrue(state.requiresInput());
        Mockito.verify(p1).setSelection1(null);
    }

    @Test
    public void WithTargetsTest() {
        Mockito.when(map.inside(new Position(1,1))).thenReturn(true);
        Water water = new Water(new Position(1, 1), null);
        water.setBackgroundColor(TextColor.ANSI.RED_BRIGHT);
        Mockito.when(map.at(new Position(1,1)))
                .thenReturn(water);

        List<Element> list = new ArrayList<>();
        Mockito.when(p2.getTroops()).thenReturn(list);
        list.add(new LightPerson(new Position(1,1)));

        State state = new AttackNoSelectionState(p1, p2, map, tank);

        Mockito.verify(p1).setSelection1(null);
        Mockito.verify(p1).setSelection1(Mockito.notNull());
        Mockito.verify(border).setPosition(new Position(1, 1));
        Mockito.verify(border).setBackgroundColor(TextColor.ANSI.RED_BRIGHT);

        Mockito.when(border.getPosition()).thenReturn(new Position(1, 1));
        state.draw(graphics);
        Mockito.verify(graphics).putString(1, 11, "Select troop");
    }

    @Test
    public void InvalidColorTest() {
        Mockito.when(map.inside(new Position(1,1))).thenReturn(true);
        Water water = new Water(new Position(1, 1), null);
        water.setBackgroundColor(null);
        Mockito.when(map.at(new Position(1,1)))
                .thenReturn(water);

        List<Element> list = new ArrayList<>();
        Mockito.when(p2.getTroops()).thenReturn(list);
        list.add(new LightPerson(new Position(1,1)));

        new AttackNoSelectionState(p1, p2, map, tank);
        Mockito.verify(border, Mockito.never()).setBackgroundColor(Mockito.any());
    }
    @Test
    public void OutsideMapTest() {
        Mockito.when(map.inside(Mockito.any())).thenReturn(false);

        List<Element> list = List.of(new LightPerson(new Position(1,1)));
        Mockito.when(p2.getTroops()).thenReturn(list);

        new AttackNoSelectionState(p1, p2, map, tank);
        Mockito.verify(border, Mockito.never()).setPosition(Mockito.any(Position.class));
    }

    @Test
    public void PlayDefaultEmptyListTest() {
        Mockito.when(map.inside(Mockito.any())).thenReturn(false);

        Mockito.when(p2.getTroops()).thenReturn(new ArrayList<>());

        State state = new AttackNoSelectionState(p1, p2, map, tank);

        State result = state.play(Action.NONE);
        Assertions.assertEquals(MoveEndState.class, result.getClass());
        Mockito.verify(p1).setSelection1(Mockito.any(Border.class));
    }

    @Test
    public void PlayDefaultTest() {
        List<Element> list = List.of(new LightPerson(new Position(1,1)));
        Mockito.when(p2.getTroops()).thenReturn(list);

        State state = new AttackNoSelectionState(p1, p2, map, tank);

        State result = state.play(Action.UP);
        Assertions.assertSame(result, state);

        State result2 = state.play(Action.DOWN);
        Assertions.assertSame(result2, state);

        Mockito.verify(border, Mockito.times(3)).setPosition(Mockito.any());
    }

    @Test
    public void PlayEnterTest() {
        List<Element> list = List.of(new LightPerson(new Position(1,1)));
        Mockito.when(p2.getTroops()).thenReturn(list);

        State state = new AttackNoSelectionState(p1, p2, map, tank);

        State result = state.play(Action.ENTER);
        Assertions.assertSame(AttackState.class, result.getClass());
    }

    @Test
    public void PlayQuitTest() {
        List<Element> list = List.of(new LightPerson(new Position(1,1)));
        Mockito.when(p2.getTroops()).thenReturn(list);

        State state = new AttackNoSelectionState(p1, p2, map, tank);

        State result = state.play(Action.QUIT);
        Assertions.assertSame(QuitState.class, result.getClass());
    }

    @Test
    public void PlayEscapeTest() {
        List<Element> list = List.of(new LightPerson(new Position(1,1)));
        Mockito.when(p2.getTroops()).thenReturn(list);

        State state = new AttackNoSelectionState(p1, p2, map, tank);

        State result = state.play(Action.ESCAPE);
        Assertions.assertSame(MoveEndState.class, result.getClass());
        Mockito.verify(p1, Mockito.times(2)).setSelection1(Mockito.any(Border.class));
    }

    @Test
    public void SelectablesRadiusTest() {
        tank.setPosition(new Position(5, 5));
        List<Element> list = List.of(
                new LightPerson(new Position(3,5)),
                new HeavyTank(new Position(7, 5)),
                new AntiAirTank(new Position(5,7)),
                new AntiAirTank(new Position(5, 3)),
                new LightPerson(new Position(3,3)),
                new HeavyPerson(new Position(7, 7))
        );
        Mockito.when(p2.getTroops()).thenReturn(list);

        new AttackNoSelectionState(p1, p2, map, tank);
        Mockito.verify(p1).setSelection1(Mockito.any());
    }

    @Test
    public void SelectablesRadiusBelowTest() {
        tank.setPosition(new Position(5, 5));
        List<Element> list = List.of(new LightPerson(new Position(4,4)));
        Mockito.when(p2.getTroops()).thenReturn(list);

        new AttackNoSelectionState(p1, p2, map, tank);
        Mockito.verify(p1, Mockito.times(2)).setSelection1(Mockito.any());
    }

    @Test
    public void PlayLeftTest() {
        tank.setPosition(new Position(5, 5));
        List<Element> list = List.of(
                new LightPerson(new Position(4,4)),
                new HeavyTank(new Position(6, 5)),
                new AntiAirTank(new Position(6,7)),
                new AntiAirTank(new Position(4, 5)),
                new LightPerson(new Position(23,10)),
                new HeavyPerson(new Position(6, 6))
        );
        Mockito.when(p2.getTroops()).thenReturn(list);

        State state = new AttackNoSelectionState(p1, p2, map, tank);

        Mockito.reset(border);
        state.play(Action.LEFT);
        Mockito.verify(border).setPosition(new Position(4,4));

        Mockito.reset(border);
        state.play(Action.RIGHT);
        state.play(Action.LEFT);
        Mockito.verify(border).setPosition(new Position(4,4));
    }

    @Test
    public void PlayRightTest() {
        tank.setPosition(new Position(5, 5));
        List<Element> list = List.of(
                new LightPerson(new Position(4,4)),
                new HeavyTank(new Position(6, 5)),
                new HeavyPerson(new Position(6, 6))
        );
        Mockito.when(p2.getTroops()).thenReturn(list);

        State state = new AttackNoSelectionState(p1, p2, map, tank);

        Mockito.reset(border);
        state.play(Action.RIGHT);
        Mockito.verify(border).setPosition(new Position(6,5));

        Mockito.reset(border);
        state.play(Action.RIGHT);
        Mockito.verify(border).setPosition(new Position(6,6));

        Mockito.reset(border);
        state.play(Action.RIGHT);
        Mockito.verify(border).setPosition(new Position(6,6));
    }
}
