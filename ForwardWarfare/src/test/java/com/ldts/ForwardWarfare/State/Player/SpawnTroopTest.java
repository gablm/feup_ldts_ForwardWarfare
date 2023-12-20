package com.ldts.ForwardWarfare.State.Player;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Facility.Facility;
import com.ldts.ForwardWarfare.Element.Facility.OilPump;
import com.ldts.ForwardWarfare.Element.Playable.Air.FighterPlane;
import com.ldts.ForwardWarfare.Element.Playable.Ground.HeavyPerson;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Element.Playable.Water.FighterSubmarine;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Tile.*;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.Map.MapParseException;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.States.Player.Selection.NoSelectionState;
import com.ldts.ForwardWarfare.State.States.Player.SpawnTroopState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class SpawnTroopTest {
    private Controller p1;
    private Controller p2;
    private Map map;
    private Border border;
    private TextGraphics graphics;
    @BeforeEach
    public void ResetMocks() throws FileNotFoundException, MapParseException, URISyntaxException {
        map = Mockito.mock(Map.class);
        p1 = Mockito.mock(Controller.class);
        p2 = Mockito.mock(Controller.class);
        graphics = Mockito.mock(TextGraphics.class);

        border = Mockito.mock(Border.class);
        Mockito.when(p1.getSelection1()).thenReturn(border);
        Mockito.when(border.getPosition()).thenReturn(new Position(0,0));
    }

    @Test
    public void BaseTest() {
        Mockito.when(map.at(Mockito.any())).thenReturn(new Water(null, null));

        State state = new SpawnTroopState(p1, p2, map, null, 0, null);

        State result = state.play(Action.NONE);

        Assertions.assertFalse(state.requiresInput());
        Assertions.assertEquals(NoSelectionState.class, result.getClass());
    }

    @Test
    public void DrawTest() {
        Facility facility = Mockito.mock(OilPump.class);
        Mockito.when(map.at(Mockito.any(Position.class)))
                .thenReturn(new MountainLand(null))
                .thenReturn(new Fields(null, facility))
                .thenReturn(new Fields(null, null));
        Position pos = new Position(5, 5);
        Playable troop = new HeavyPerson(null);
        Mockito.when(map.at(pos)).thenReturn(new Fields(null, facility));

        State state = new SpawnTroopState(p1, p2, map, pos, 0, troop);
        state.draw(graphics);

        Mockito.verify(p1).buy(troop, 0);
        Mockito.verify(graphics).putString(1, 13, "Continue?");
        Mockito.verify(facility).execute();
        Assertions.assertNotNull(troop.getPosition());
    }

    @Test
    public void DrawNoSpaceTest() {
        Facility facility = Mockito.mock(OilPump.class);
        Mockito.when(map.at(Mockito.any(Position.class)))
                .thenReturn(new MountainLand(null))
                .thenReturn(new Fields(null, facility));
        Position pos = new Position(5, 5);
        Playable troop = new HeavyPerson(null);
        Mockito.when(map.at(pos)).thenReturn(new Fields(null, facility));

        State state = new SpawnTroopState(p1, p2, map, pos, 0, troop);
        state.draw(graphics);

        Mockito.verify(graphics).putString(1, 13, "No Space ");
        Assertions.assertNull(troop.getPosition());
    }

    @Test
    public void DrawWaterTest() {
        Facility facility = Mockito.mock(OilPump.class);
        Mockito.when(map.at(Mockito.any(Position.class)))
                .thenReturn(new Water(null, null));

        Position pos = new Position(5, 5);
        Mockito.when(map.at(pos))
                .thenReturn(new Fields(null, facility));

        Playable troop = new FighterSubmarine(null);

        State state = new SpawnTroopState(p1, p2, map, pos, 0, troop);
        state.draw(graphics);

        Mockito.verify(p1).buy(troop, 0);
        Mockito.verify(graphics).putString(1, 13, "Continue?");
        Mockito.verify(facility).execute();
        Assertions.assertNotNull(troop.getPosition());
    }

    @Test
    public void DrawAirTest() {
        Facility facility = Mockito.mock(OilPump.class);
        Mockito.when(map.at(Mockito.any(Position.class)))
                .thenReturn(new Fields(null, null));

        Position pos = new Position(5, 5);
        Mockito.when(map.at(pos)).thenReturn(new Fields(null, facility));

        Playable troop = new FighterPlane(null);

        State state = new SpawnTroopState(p1, p2, map, pos, 0, troop);
        state.draw(graphics);

        Mockito.verify(p1).buy(troop, 0);
        Mockito.verify(graphics).putString(1, 13, "Continue?");
        Mockito.verify(facility).execute();
        Assertions.assertNotNull(troop.getPosition());
    }

    @ParameterizedTest
    @CsvSource({"6,5", "6,6", "5,6", "4,4", "4,5", "5,4", "4,6", "6,4"})
    public void CaseTestFormat(int x, int y) {
        Mockito.when(map.at(Mockito.any(Position.class))).thenReturn(new MountainLand(null));
        Mockito.when(map.at(new Position(x, y))).thenReturn(new Fields(null, null));

        Position pos = new Position(5, 5);
        Facility facility = Mockito.mock(OilPump.class);
        Mockito.when(map.at(pos)).thenReturn(new Fields(null, facility));

        Playable troop = new FighterPlane(null);

        State state = new SpawnTroopState(p1, p2, map, pos, 0, troop);
        state.draw(graphics);

        Mockito.verify(p1).buy(troop, 0);
    }

    @Test
    public void MatchesP1TroopTest() {
        Mockito.when(map.at(Mockito.any(Position.class))).thenReturn(new MountainLand(null));
        Mockito.when(map.at(new Position(5, 6))).thenReturn(new Fields(null, null));

        Position pos = new Position(5, 5);
        Facility facility = Mockito.mock(OilPump.class);
        Mockito.when(map.at(pos)).thenReturn(new Fields(null, facility));
        Mockito.when(p1.getTroops()).thenReturn(List.of(new HeavyPerson(new Position(5, 6))));

        Playable troop = new FighterPlane(null);

        State state = new SpawnTroopState(p1, p2, map, pos, 0, troop);
        state.draw(graphics);

        Mockito.verify(p1, Mockito.never()).buy(troop, 0);
    }

    @Test
    public void NoMatchP1TroopTest() {
        Mockito.when(map.at(Mockito.any(Position.class))).thenReturn(new MountainLand(null));
        Mockito.when(map.at(new Position(6, 6))).thenReturn(new Fields(null, null));

        Position pos = new Position(5, 5);
        Facility facility = Mockito.mock(OilPump.class);
        Mockito.when(map.at(pos)).thenReturn(new Fields(null, facility));
        Mockito.when(p1.getTroops()).thenReturn(List.of(new HeavyPerson(new Position(5, 6))));

        Playable troop = new FighterPlane(null);

        State state = new SpawnTroopState(p1, p2, map, pos, 0, troop);
        state.draw(graphics);

        Mockito.verify(p1).buy(troop, 0);
    }

    @Test
    public void MatchesP2TroopTest() {
        Mockito.when(map.at(Mockito.any(Position.class))).thenReturn(new MountainLand(null));
        Mockito.when(map.at(new Position(5, 6))).thenReturn(new Fields(null, null));

        Position pos = new Position(5, 5);
        Facility facility = Mockito.mock(OilPump.class);
        Mockito.when(map.at(pos)).thenReturn(new Fields(null, facility));
        Mockito.when(p2.getTroops()).thenReturn(List.of(new HeavyPerson(new Position(5, 6))));

        Playable troop = new FighterPlane(null);

        State state = new SpawnTroopState(p1, p2, map, pos, 0, troop);
        state.draw(graphics);

        Mockito.verify(p1, Mockito.never()).buy(troop, 0);
    }

    @Test
    public void NoMatchP2TroopTest() {
        Mockito.when(map.at(Mockito.any(Position.class))).thenReturn(new MountainLand(null));
        Mockito.when(map.at(new Position(6, 6))).thenReturn(new Fields(null, null));

        Position pos = new Position(5, 5);
        Facility facility = Mockito.mock(OilPump.class);
        Mockito.when(map.at(pos)).thenReturn(new Fields(null, facility));
        Mockito.when(p2.getTroops()).thenReturn(List.of(new HeavyPerson(new Position(5, 6))));

        Playable troop = new FighterPlane(null);

        State state = new SpawnTroopState(p1, p2, map, pos, 0, troop);
        state.draw(graphics);

        Mockito.verify(p1).buy(troop, 0);
    }
}
