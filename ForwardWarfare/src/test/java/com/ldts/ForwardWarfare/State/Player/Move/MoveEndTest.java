package com.ldts.ForwardWarfare.State.Player.Move;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Playable.Ground.LightPerson;
import com.ldts.ForwardWarfare.Element.Tile.Border;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.States.Player.Move.MoveEndState;
import com.ldts.ForwardWarfare.State.States.Player.Selection.Attack.AttackNoSelectionState;
import com.ldts.ForwardWarfare.State.States.Player.Selection.Capture.CaptureNoSelectionState;
import com.ldts.ForwardWarfare.State.States.QuitState;
import com.ldts.ForwardWarfare.State.States.StartRoundState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MoveEndTest {
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
        State state = new MoveEndState(p1, p2, map, null);

        Assertions.assertTrue(state.requiresInput());
        Mockito.verify(p1).setSelection1(null);
    }

    @Test
    public void DrawNullTest() {
        State state = new MoveEndState(p1, p2, map, null);
        state.draw(graphics);

        Mockito.verify(graphics).setBackgroundColor(TextColor.ANSI.RED_BRIGHT);
        Mockito.verify(graphics, Mockito.times(3))
                .setBackgroundColor(new TextColor.RGB(80,80,80));
        Mockito.verify(graphics).putString(1, 13, " Continue ");
    }

    @Test
    public void DrawNotNullTest() {
        Element element = new LightPerson(null);

        State state = new MoveEndState(p1, p2, map, element);
        state.draw(graphics);

        Mockito.verify(graphics).setBackgroundColor(TextColor.ANSI.RED_BRIGHT);
        Mockito.verify(graphics, Mockito.times(5))
                .setBackgroundColor(new TextColor.RGB(80,80,80));
        Mockito.verify(graphics).putString(1, 14, " Continue ");
        Mockito.verify(graphics).putString(1, 15, " End Turn ");
        Mockito.verify(graphics).putString(1, 16, " Exit ");
    }

    @Test
    public void Play0Test() {
        Element element = new LightPerson(null);

        State expected = Mockito.mock(AttackNoSelectionState.class);
        Mockito.when(p1.getInitialState(p2, map))
                .thenReturn(expected);
        Border border = new Border(null);
        Mockito.when(p1.getSelection1()).thenReturn(border);

        State state = new MoveEndState(p1, p2, map, element);
        State result = state.play(Action.NONE);
        Assertions.assertSame(state, result);

        State resultEnter = state.play(Action.ENTER);
        Mockito.verify(p1).setSelection1(border);
        Assertions.assertSame(expected, resultEnter);
    }

    @Test
    public void PlayMinus1Test() {
        Element element = new LightPerson(null);

        Border border = new Border(null);
        Mockito.when(p1.getSelection1()).thenReturn(border);

        State state = new MoveEndState(p1, p2, map, element);
        state.play(Action.UP);

        State resultEnter = state.play(Action.ENTER);
        Mockito.verify(p1).setSelection1(border);
        Assertions.assertEquals(CaptureNoSelectionState.class, resultEnter.getClass());
    }

    @Test
    public void PlayMinus2Test() {
        Element element = new LightPerson(null);

        Border border = new Border(null);
        Mockito.when(p1.getSelection1()).thenReturn(border);

        State state = new MoveEndState(p1, p2, map, element);
        state.play(Action.UP);
        state.play(Action.UP);
        state.play(Action.UP);

        State resultEnter = state.play(Action.ENTER);
        Mockito.verify(p1).setSelection1(border);
        Assertions.assertEquals(AttackNoSelectionState.class, resultEnter.getClass());
    }

    @Test
    public void PlayNullNegativeTest() {
        Border border = new Border(null);
        Mockito.when(p1.getSelection1()).thenReturn(border);
        State expected = Mockito.mock(AttackNoSelectionState.class);
        Mockito.when(p1.getInitialState(p2, map))
                .thenReturn(expected);

        State state = new MoveEndState(p1, p2, map, null);
        state.play(Action.UP);

        State resultEnter = state.play(Action.ENTER);
        Mockito.verify(p1).setSelection1(border);
        Assertions.assertSame(expected, resultEnter);
    }

    @Test
    public void Play1Test() {
        Element element = new LightPerson(null);

        Border border = new Border(null);
        Mockito.when(p1.getSelection1()).thenReturn(border);

        State state = new MoveEndState(p1, p2, map, element);
        state.play(Action.DOWN);

        State resultEnter = state.play(Action.ENTER);
        Mockito.verify(p1).endRound();
        Assertions.assertSame(StartRoundState.class, resultEnter.getClass());
    }

    @Test
    public void Play2Test() {
        Element element = new LightPerson(null);

        Border border = new Border(null);
        Mockito.when(p1.getSelection1()).thenReturn(border);

        State state = new MoveEndState(p1, p2, map, element);
        state.play(Action.DOWN);
        state.play(Action.DOWN);
        state.play(Action.DOWN);

        State resultEnter = state.play(Action.ENTER);
        Assertions.assertSame(QuitState.class, resultEnter.getClass());
        Assertions.assertTrue(resultEnter.requiresInput());
    }
}
