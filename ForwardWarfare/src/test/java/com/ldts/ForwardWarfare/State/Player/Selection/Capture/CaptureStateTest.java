package com.ldts.ForwardWarfare.State.Player.Selection.Capture;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Facility.Base;
import com.ldts.ForwardWarfare.Element.Facility.OilPump;
import com.ldts.ForwardWarfare.Element.Playable.Ground.HeavyPerson;
import com.ldts.ForwardWarfare.Element.Playable.Ground.HeavyTank;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Tile.Border;
import com.ldts.ForwardWarfare.Element.Tile.Tile;
import com.ldts.ForwardWarfare.Element.Tile.Water;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.States.EndGameState;
import com.ldts.ForwardWarfare.State.States.Player.Move.MoveEndState;
import com.ldts.ForwardWarfare.State.States.Player.Selection.Attack.AttackState;
import com.ldts.ForwardWarfare.State.States.Player.Selection.Capture.CaptureState;
import com.ldts.ForwardWarfare.State.States.Player.Selection.NoSelectionState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CaptureStateTest {

    private Controller p1;
    private Controller p2;
    private Map map;
    private Border border;
    private Element baseTile;
    private Base base;
    private Tile tile;
    private TextGraphics graphics;
    @BeforeEach
    public void ResetMocks() {
        map = Mockito.mock(Map.class);
        p1 = Mockito.mock(Controller.class);
        p2 = Mockito.mock(Controller.class);
        graphics = Mockito.mock(TextGraphics.class);

        border = Mockito.mock(Border.class);
        Mockito.when(p1.getSelection1()).thenReturn(border);

        base = new Base();
        baseTile = new Water(new Position(0,0), base);
        Mockito.when(p2.getBase()).thenReturn(baseTile);

        tile = new Water(null, new OilPump());
        Mockito.when(map.at(Mockito.any())).thenReturn(tile);
    }

    @Test
    public void BaseTest() {
        State state = new CaptureState(p1, p2, map);

        state.draw(graphics);

        Assertions.assertFalse(state.requiresInput());
        State result = state.play(null);
        Assertions.assertEquals(NoSelectionState.class, result.getClass());
    }

    @Test
    public void EndGameTest() {
        base.takeDamage();
        Mockito.when(border.getPosition())
                .thenReturn(new Position(0,0));

        State state = new CaptureState(p1, p2, map);
        State result = state.play(null);

        Assertions.assertEquals(EndGameState.class, result.getClass());
        Assertions.assertTrue(base.getAttackedLastTurn());
        Assertions.assertEquals(0, base.getLives());
        Assertions.assertTrue(base.getUsed());
    }

    @Test
    public void CaptureFacilityTest() {
        Mockito.when(border.getPosition())
                .thenReturn(new Position(5,5));
        Element tempTile = new Water(new Position(5,5), null);
        List<Element> p2L = new ArrayList<>();
        p2L.add(tempTile); p2L.add(tempTile);
        Mockito.when(p2.getFacilities()).thenReturn(p2L);

        new CaptureState(p1, p2, map);
        Assertions.assertTrue(p2L.isEmpty());
        Mockito.verify(p1).addFacility((Element) tile);
        Assertions.assertTrue(tile.getFacility().getUsed());
    }

    @Test
    public void CaptureFacilityNoP2Test() {
        Mockito.when(border.getPosition())
                .thenReturn(new Position(5,5));
        List<Element> p2L = new ArrayList<>();
        p2L.add(new Water(new Position(5,5), null));
        p2L.add(new Water(new Position(7,7), null));
        Mockito.when(p2.getFacilities()).thenReturn(p2L);

        new CaptureState(p1, p2, map);
        Assertions.assertEquals(1, p2L.size());
        Mockito.verify(p1).addFacility((Element) tile);
        Assertions.assertTrue(tile.getFacility().getUsed());
    }

    @Test
    public void CaptureFacilityAlreadyCapturedTest() {
        Mockito.when(border.getPosition())
                .thenReturn(new Position(5,5));
        Element tempElem = new Water(new Position(5,5), null);
        List<Element> p1L = new ArrayList<>();
        p1L.add(tempElem);
        Mockito.when(p1.getFacilities()).thenReturn(p1L);

        new CaptureState(p1, p2, map);
        Mockito.verify(p1, Mockito.never()).addFacility((Element) tile);
    }

    @Test
    public void CaptureNoFacilityTest() {
        Mockito.when(border.getPosition())
                .thenReturn(new Position(5,5));
        Element tempElem = new Water(new Position(3,3), null);
        List<Element> p1L = new ArrayList<>();
        p1L.add(tempElem);
        Mockito.when(p1.getFacilities()).thenReturn(p1L);

        new CaptureState(p1, p2, map);
        Mockito.verify(p1).addFacility((Element) tile);
    }
}
