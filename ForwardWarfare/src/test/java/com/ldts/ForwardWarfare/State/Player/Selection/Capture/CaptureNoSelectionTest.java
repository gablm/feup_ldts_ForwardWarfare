package com.ldts.ForwardWarfare.State.Player.Selection.Capture;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Playable.Ground.HeavyTank;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Tile.Border;
import com.ldts.ForwardWarfare.Element.Tile.MountainLand;
import com.ldts.ForwardWarfare.Element.Tile.Tile;
import com.ldts.ForwardWarfare.Element.Tile.Water;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.Map.MapParseException;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.States.Player.Selection.Attack.AttackNoSelectionState;
import com.ldts.ForwardWarfare.State.States.Player.Selection.Capture.CaptureNoSelectionState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class CaptureNoSelectionTest {

    private Element origin;
    private Controller p1;
    private Controller p2;
    private Map map;
    private Border border;
    private Tile tile;
    private TextGraphics graphics;
    @BeforeEach
    public void ResetMocks() throws FileNotFoundException, MapParseException, URISyntaxException {
        map = new Map("1.fw");
        p1 = Mockito.mock(Controller.class);
        p2 = Mockito.mock(Controller.class);
        graphics = Mockito.mock(TextGraphics.class);

        border = Mockito.mock(Border.class);
        Mockito.when(p1.getSelection1()).thenReturn(border);

        origin = new Water(new Position(5,5), null);
        Mockito.when(p1.getBase()).thenReturn(map.getPlayer1().get(0));
    }

    @Test
    public void BaseTest() {
        State state = new CaptureNoSelectionState(p1, p2, map, origin);

        Assertions.assertTrue(state.requiresInput());
        Mockito.verify(p1).setSelection1(null);

        state.draw(graphics);
        Mockito.verify(graphics).putString(1, 12, "Nothing to");
    }
}
