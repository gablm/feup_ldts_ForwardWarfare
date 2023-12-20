package com.ldts.ForwardWarfare.State;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Facility.Base;
import com.ldts.ForwardWarfare.Element.Facility.Facility;
import com.ldts.ForwardWarfare.Element.Playable.Ground.HeavyTank;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Tile.Fields;
import com.ldts.ForwardWarfare.Element.Tile.Tile;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.Map.MapParseException;
import com.ldts.ForwardWarfare.State.States.Automatic.AutomaticPlayState;
import com.ldts.ForwardWarfare.State.States.EndGameState;
import com.ldts.ForwardWarfare.State.States.StartRoundState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class AutomaticPlayTest {
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
        State state = new AutomaticPlayState(p1, p2, map);
        state.draw(graphics);

        Assertions.assertFalse(state.requiresInput());
    }

    @Test
    public void PlayTest() {
        State state = new AutomaticPlayState(p1, p2, map);

        State result = state.play(Action.NONE);

        Assertions.assertEquals(StartRoundState.class, result.getClass());
        Mockito.verify(p1).endRound();
    }

    @Test
    public void PlayEndGameTest() throws FileNotFoundException, MapParseException, URISyntaxException {
        Map map = new Map("1.fw");
        Element element = map.getPlayer2().get(0);
        Base base = (Base)((Tile) element).getFacility();
        Mockito.when(p2.getBase()).thenReturn(element);

        AutomaticPlayState state = new AutomaticPlayState(p1, p2, map);

        base.takeDamage();
        state.capture(element.getPosition());
        State result = state.play(Action.NONE);

        Assertions.assertEquals(EndGameState.class, result.getClass());
        Mockito.verify(p1).endRound();
    }

    @Test
    public void CanMoveLineTest() throws FileNotFoundException, MapParseException, URISyntaxException {
        Map map = new Map("1.fw");

        AutomaticPlayState state = new AutomaticPlayState(p1, p2, map);
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

        Mockito.when(p2.getBase())
                .thenReturn(new Fields(new Position(0, 0), new Base()));

        AutomaticPlayState state = new AutomaticPlayState(p1, p2, map);
        Position pos1 = new Position(0,0);
        Position pos2 = new Position(0, 3);
        List<Position> elements = state.canMove(pos1, pos2, new HeavyTank(pos1));

        Assertions.assertTrue(elements.size() > 6);
    }

    @Test
    public void CanMove1510Test() throws FileNotFoundException, MapParseException, URISyntaxException {
        Map map = new Map("tests/allGrass.fw");

        Mockito.when(p1.getTroops()).thenReturn(
                List.of(new HeavyTank(new Position(14, 8))));
        Mockito.when(p2.getTroops()).thenReturn(
                List.of(new HeavyTank(new Position(14, 7))));
        Mockito.when(p2.getBase())
                .thenReturn(new Fields(new Position(0, 0), new Base()));

        AutomaticPlayState state = new AutomaticPlayState(p1, p2, map);
        Position pos1 = new Position(14,9);
        Position pos2 = new Position(14, 6);
        List<Position> elements = state.canMove(pos1, pos2, new HeavyTank(pos1));

        Assertions.assertEquals(8, elements.size());
    }
}
