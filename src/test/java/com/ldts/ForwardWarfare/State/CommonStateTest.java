package com.ldts.ForwardWarfare.State;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Facility.Base;
import com.ldts.ForwardWarfare.Element.Tile.Fields;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.States.EndGameState;
import com.ldts.ForwardWarfare.State.States.QuitState;
import com.ldts.ForwardWarfare.State.States.StartRoundState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CommonStateTest {
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
    public void QuitBasicTest() {
        State last = Mockito.mock(State.class);

        QuitState state = new QuitState(p1, p2, map, last);

        Assertions.assertTrue(state.requiresInput());
        Assertions.assertSame(state.getP1(), p1);
        Assertions.assertSame(state.getP2(), p2);
        Assertions.assertSame(state.getMap(), map);
    }

    @Test
    public void QuitDrawTest() {
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
        QuitState last = new QuitState(null, null, null, null);

        QuitState state = new QuitState(p1, p2, map, last);
        Assertions.assertFalse(state.requiresInput());
        Assertions.assertNull(state.play(Action.NONE));
    }

    @Test
    public void QuitPlayNoneTest() {
        State last = Mockito.mock(State.class);

        QuitState state = new QuitState(p1, p2, map, last);
        State result = state.play(Action.NONE);
        Assertions.assertSame(last, result);
    }

    @Test
    public void QuitPlayQuitTest() {
        State last = Mockito.mock(State.class);

        QuitState state = new QuitState(p1, p2, map, last);
        State result = state.play(Action.QUIT);
        Assertions.assertSame(QuitState.class, result.getClass());
        Assertions.assertFalse(result.requiresInput());
    }

    @Test
    public void QuitPlayLeftTest() {
        State last = Mockito.mock(State.class);

        QuitState state = new QuitState(p1, p2, map, last);
        State result = state.play(Action.LEFT);
        Assertions.assertSame(state, result);

        State result2 = state.play(Action.ENTER);
        Assertions.assertSame(result2, last);
    }

    @Test
    public void QuitPlayRightTest() {
        State last = Mockito.mock(State.class);

        QuitState state = new QuitState(p1, p2, map, last);
        State result = state.play(Action.RIGHT);
        Assertions.assertSame(state, result);

        State result2 = state.play(Action.ENTER);
        Assertions.assertNull(result2);
    }

    @Test
    public void EndBasicTest() {
        State state = new EndGameState(p1, p2, map);

        Mockito.verify(p1).setSelection1(null);
        Mockito.verify(p1).setSelection2(null);
        Mockito.verify(p2).setSelection1(null);
        Mockito.verify(p2).setSelection2(null);

        Assertions.assertNull(state.play(null));
        Assertions.assertTrue(state.requiresInput());
    }

    @Test
    public void EndDrawTest() {
        Mockito.when(p1.getBaseLives()).thenReturn(0);
        Mockito.when(p2.getName()).thenReturn("P2");
        Mockito.when(p2.getControllerColor()).thenReturn(TextColor.ANSI.MAGENTA);

        State state = new EndGameState(p1, p2, map);
        state.draw(graphics);

        Mockito.verify(graphics).setBackgroundColor(TextColor.ANSI.MAGENTA);
        Mockito.verify(graphics).fillRectangle(new TerminalPosition(0,0), new TerminalSize(25,19), ' ');
        Mockito.verify(graphics).setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        Mockito.verify(graphics).enableModifiers(SGR.BOLD);
        Mockito.verify(graphics).putString(8, 8, "GAME OVER");
        Mockito.verify(graphics).setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        Mockito.verify(graphics).putString(7, 11, "CONGRATS P2");
    }

    @Test
    public void StartRoundBaseTest() {
        Base base = new Base();
        Fields fields = Mockito.mock(Fields.class);
        Mockito.when(fields.getFacility()).thenReturn(base);

        Mockito.when(p1.getBase()).thenReturn(fields);
        Mockito.when(p2.getBase()).thenReturn(fields);

        State state = new StartRoundState(p1, p2, map);
        state.draw(null);

        Assertions.assertFalse(state.requiresInput());;
    }

    @Test
    public void StartRoundPlayP1Test() {
        Base base = new Base();
        Fields fields = Mockito.mock(Fields.class);
        Mockito.when(fields.getFacility()).thenReturn(base);

        Mockito.when(p1.getBase()).thenReturn(fields);
        Mockito.when(p2.getBase()).thenReturn(fields);

        State expected = new EndGameState(p1, p2, map);
        Mockito.when(p1.getInitialState(Mockito.any(), Mockito.any()))
                .thenReturn(expected);
        Mockito.when(p1.canPlay()).thenReturn(true);

        State state = new StartRoundState(p1, p2, map);

        Assertions.assertFalse(state.requiresInput());
        State result = state.play(null);
        Assertions.assertSame(expected, result);
    }

    @Test
    public void StartRoundPlayP2Test() {
        Base base = new Base();
        Fields fields = Mockito.mock(Fields.class);
        Mockito.when(fields.getFacility()).thenReturn(base);

        Mockito.when(p1.getBase()).thenReturn(fields);
        Mockito.when(p2.getBase()).thenReturn(fields);

        State expected = new EndGameState(p1, p2, map);
        Mockito.when(p2.getInitialState(Mockito.any(), Mockito.any()))
                .thenReturn(expected);
        Mockito.when(p1.canPlay()).thenReturn(false);
        Mockito.when(p2.canPlay()).thenReturn(true);

        State state = new StartRoundState(p1, p2, map);

        Assertions.assertFalse(state.requiresInput());
        State result = state.play(null);
        Assertions.assertSame(expected, result);
    }

    @Test
    public void StartRoundPlayNoneTest() {
        Base base = new Base();
        Fields fields = Mockito.mock(Fields.class);
        Mockito.when(fields.getFacility()).thenReturn(base);

        Mockito.when(p1.getBase()).thenReturn(fields);
        Mockito.when(p2.getBase()).thenReturn(fields);

        Mockito.when(p1.canPlay()).thenReturn(false);
        Mockito.when(p2.canPlay()).thenReturn(false);

        State state = new StartRoundState(p1, p2, map);

        Assertions.assertFalse(state.requiresInput());
        State result = state.play(null);

        Mockito.verify(p1).resetRound();
        Mockito.verify(p2).resetRound();

        Assertions.assertEquals(StartRoundState.class, result.getClass());
        Assertions.assertEquals(p2, result.getP1());
        Assertions.assertEquals(p1, result.getP2());
    }
}
