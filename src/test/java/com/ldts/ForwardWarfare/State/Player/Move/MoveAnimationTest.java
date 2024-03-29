package com.ldts.ForwardWarfare.State.Player.Move;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.States.Player.Move.MoveAnimationState;
import com.ldts.ForwardWarfare.State.States.Player.Move.MoveEndState;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

public class MoveAnimationTest {
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
        Mockito.when(p1.getControllerColor()).thenReturn(TextColor.ANSI.BLACK);
        Element element = Mockito.mock(Element.class);

        List<Position> moves = new ArrayList<>();

        MoveAnimationState state = new MoveAnimationState(p1, p2, map, moves, element);
        state.draw(graphics);

        Assertions.assertFalse(state.requiresInput());
    }

    @Test
    public void PlayEmptyTest() {
        Mockito.when(p1.getControllerColor()).thenReturn(TextColor.ANSI.BLACK);
        Element element = Mockito.mock(Element.class);

        List<Position> moves = new ArrayList<>();

        MoveAnimationState state = new MoveAnimationState(p1, p2, map, moves, element);
        State result = state.play(null);

        Assertions.assertEquals(MoveEndState.class, result.getClass());
    }

    @Test
    public void PlayFillTest() {
        Mockito.when(p1.getControllerColor()).thenReturn(TextColor.ANSI.BLACK);
        Element element = Mockito.mock(Element.class);

        List<Position> moves = new ArrayList<>();
        moves.add(new Position(1, 0));
        moves.add(new Position(0,0));

        MoveAnimationState state = new MoveAnimationState(p1, p2, map, moves, element);

        long startTime = System.currentTimeMillis();
        State result = state.play(null);
        long endTime = System.currentTimeMillis();

        Assertions.assertTrue(endTime - startTime >= 150);
        Assertions.assertSame(state, result);
        Assertions.assertEquals(1, moves.size());
        Mockito.verify(element).setPosition(new Position(0, 0));
    }
}
