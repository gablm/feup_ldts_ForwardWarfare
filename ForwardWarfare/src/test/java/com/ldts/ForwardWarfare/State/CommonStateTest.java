package com.ldts.ForwardWarfare.State;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.States.QuitState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CommonStateTest {

    @Test
    public void QuitBasicTest() {
        Map map = Mockito.mock(Map.class);
        Controller p1 = Mockito.mock(Controller.class);
        Controller p2 = Mockito.mock(Controller.class);
        State last = Mockito.mock(State.class);

        QuitState state = new QuitState(p1, p2, map, last);

        Assertions.assertTrue(state.requiresInput());
        Assertions.assertSame(state.getP1(), p1);
        Assertions.assertSame(state.getP2(), p2);
        Assertions.assertSame(state.getMap(), map);
    }

    @Test
    public void QuitDrawTest() {
        Map map = Mockito.mock(Map.class);
        Controller p1 = Mockito.mock(Controller.class);
        Controller p2 = Mockito.mock(Controller.class);
        State last = Mockito.mock(State.class);
        TextGraphics graphics = Mockito.mock(TextGraphics.class);

        QuitState state = new QuitState(p1, p2, map, last);
        state.draw(graphics);

        Mockito.verify(graphics).fillRectangle(new TerminalPosition(0,10), new TerminalSize(15,9), ' ');
        Mockito.verify(graphics, Mockito.times(2)).setBackgroundColor(new TextColor.RGB(80,80,80));
        Mockito.verify(graphics).setBackgroundColor(TextColor.ANSI.RED_BRIGHT);
        Mockito.verify(graphics).setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        Mockito.verify(graphics).putString(3, 15, " No ");
        Mockito.verify(graphics).putString(7, 15, " Yes ");
    }

    @Test
    public void QuitWithQuitStateTest() {
        Map map = Mockito.mock(Map.class);
        Controller p1 = Mockito.mock(Controller.class);
        Controller p2 = Mockito.mock(Controller.class);
        QuitState last = new QuitState(null, null, null, null);

        QuitState state = new QuitState(p1, p2, map, last);
        Assertions.assertFalse(state.requiresInput());
        Assertions.assertNull(state.play(Action.NONE));
    }

    @Test
    public void QuitPlayNoneTest() {
        Map map = Mockito.mock(Map.class);
        Controller p1 = Mockito.mock(Controller.class);
        Controller p2 = Mockito.mock(Controller.class);
        State last = Mockito.mock(State.class);

        QuitState state = new QuitState(p1, p2, map, last);
        State result = state.play(Action.NONE);
        Assertions.assertSame(last, result);
    }

    @Test
    public void QuitPlayQuitTest() {
        Map map = Mockito.mock(Map.class);
        Controller p1 = Mockito.mock(Controller.class);
        Controller p2 = Mockito.mock(Controller.class);
        State last = Mockito.mock(State.class);

        QuitState state = new QuitState(p1, p2, map, last);
        State result = state.play(Action.QUIT);
        Assertions.assertSame(QuitState.class, result.getClass());
        Assertions.assertFalse(result.requiresInput());
    }

    @Test
    public void QuitPlayLeftTest() {
        Map map = Mockito.mock(Map.class);
        Controller p1 = Mockito.mock(Controller.class);
        Controller p2 = Mockito.mock(Controller.class);
        State last = Mockito.mock(State.class);

        QuitState state = new QuitState(p1, p2, map, last);
        State result = state.play(Action.LEFT);
        Assertions.assertSame(state, result);

        State result2 = state.play(Action.ENTER);
        Assertions.assertSame(result2, last);
    }

    @Test
    public void QuitPlayRightTest() {
        Map map = Mockito.mock(Map.class);
        Controller p1 = Mockito.mock(Controller.class);
        Controller p2 = Mockito.mock(Controller.class);
        State last = Mockito.mock(State.class);

        QuitState state = new QuitState(p1, p2, map, last);
        State result = state.play(Action.RIGHT);
        Assertions.assertSame(state, result);

        State result2 = state.play(Action.ENTER);
        Assertions.assertNull(result2);
    }
}
