package com.ldts.ForwardWarfare.State.Player.Selection;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Tile.Border;
import com.ldts.ForwardWarfare.Element.Tile.Tile;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.States.Player.Move.MoveValidationState;
import com.ldts.ForwardWarfare.State.States.Player.Selection.NoSelectionState;
import com.ldts.ForwardWarfare.State.States.Player.Selection.OneSelectionState;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

public class OneSelectionTest {
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
        State state = new OneSelectionState(p1, p2, map);
        state.draw(graphics);

        Assertions.assertTrue(state.requiresInput());
        Mockito.verify(border).setBackgroundColor(TextColor.ANSI.BLUE);
    }

    @Test
    public void BasePlayInvalidTest() {
        Tile tile = Mockito.mock(Tile.class);
        Mockito.when(map.at(Mockito.any())).thenReturn(tile);
        Mockito.when(tile.getColor()).thenReturn(TextColor.ANSI.BLUE);

        State state = new OneSelectionState(p1, p2, map);
        State result = state.play(Action.QUIT);

        Assertions.assertSame(state, result);
    }

    private void PlayTestTemplate(Action action, int x, int y) {
        Mockito.when(map.inside(Mockito.any())).thenReturn(true);

        State state = new OneSelectionState(p1, p2, map);

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
        Mockito.when(map.inside(Mockito.any())).thenReturn(true);

        State state = new OneSelectionState(p1, p2, map);

        State result = state.play(Action.ESCAPE);

        Assertions.assertEquals(NoSelectionState.class, result.getClass());
        Mockito.verify(p1).setSelection1(Mockito.notNull());
        Mockito.verify(p1).setSelection2(null);
    }

    @Test
    public void PlayEnterTest() {
        Mockito.when(map.inside(Mockito.any())).thenReturn(true);

        State state = new OneSelectionState(p1, p2, map);

        State result = state.play(Action.ENTER);

        Assertions.assertEquals(MoveValidationState.class, result.getClass());
    }

    @Test
    public void PlayOutsideMapTest() {
        Mockito.when(map.inside(Mockito.any())).thenReturn(false);

        State state = new OneSelectionState(p1, p2, map);
        State result = state.play(Action.UP);

        Mockito.verify(border, Mockito.never()).setPosition(Mockito.any());
    }
}
