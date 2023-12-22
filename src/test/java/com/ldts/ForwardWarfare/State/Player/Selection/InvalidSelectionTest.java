package com.ldts.ForwardWarfare.State.Player.Selection;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.States.Player.Selection.InvalidSelectState;
import com.ldts.ForwardWarfare.State.States.Player.Selection.NoSelectionState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class InvalidSelectionTest {
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
        State state = new InvalidSelectState(p1, p2, map, null);

        State result = state.play(null);

        Assertions.assertTrue(state.requiresInput());
        Assertions.assertEquals(NoSelectionState.class, result.getClass());
    }

    @Test
    public void DrawTest() {
        State state = new InvalidSelectState(p1, p2, map, "");

        state.draw(graphics);
        Mockito.verify(graphics, Mockito.times(1))
                .putString(Mockito.eq(1), Mockito.eq(11), Mockito.anyString());
    }

    @Test
    public void Draw2Test() {
        State state = new InvalidSelectState(p1, p2, map, "a\na\n");

        state.draw(graphics);
        Mockito.verify(graphics,Mockito.times(3))
                .putString(Mockito.eq(1), Mockito.anyInt(), Mockito.anyString());
    }

    @Test
    public void Draw6Test() {
        State state = new InvalidSelectState(p1, p2, map,
                "0\n0\n0\n0\n1\n0\n___");

        state.draw(graphics);
        Mockito.verify(graphics).putString(1, 15, "1");
        Mockito.verify(graphics).putString(1, 16, "0");
        Mockito.verify(graphics).putString(1, 17, "...");
    }
}
