package com.ldts.ForwardWarfare.State.Player.Selection.Attack;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Controller.Player;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Playable.Ground.HeavyTank;
import com.ldts.ForwardWarfare.Element.Playable.Ground.LightPerson;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Tile.Border;
import com.ldts.ForwardWarfare.Element.Tile.Tile;
import com.ldts.ForwardWarfare.Element.Tile.Water;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.States.Player.Selection.Attack.AttackNoSelectionState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class AttackNoSelectionTest {
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
        HeavyTank tank = new HeavyTank(null);
        State state = new AttackNoSelectionState(p1, p2, map, tank);

        Assertions.assertTrue(state.requiresInput());
        Mockito.verify(p1).setSelection1(null);

        state.draw(graphics);
        Mockito.verify(graphics).putString(1, 12, "Nothing to");
    }

    @Test
    public void NullElementTest() {
        State state = new AttackNoSelectionState(p1, p2, map, null);

        Assertions.assertTrue(state.requiresInput());
        Mockito.verify(p1).setSelection1(null);
    }

    @Test
    public void WithTargetsTest() {
        HeavyTank tank = new HeavyTank(new Position(0, 0));

        Mockito.when(map.inside(new Position(1,1))).thenReturn(true);
        Water water = new Water(new Position(1, 1), null);
        water.setBackgroundColor(TextColor.ANSI.RED_BRIGHT);
        Mockito.when(map.at(new Position(1,1)))
                .thenReturn(water);

        Border border = Mockito.mock(Border.class);
        Mockito.when(p1.getSelection1())
                .thenReturn(border);

        List<Element> list = new ArrayList<>();
        Mockito.when(p2.getTroops()).thenReturn(list);
        list.add(new LightPerson(new Position(1,1)));

        State state = new AttackNoSelectionState(p1, p2, map, tank);

        Mockito.verify(p1).setSelection1(null);
        Mockito.verify(p1).setSelection1(Mockito.notNull());
        Mockito.verify(border).setPosition(new Position(1, 1));
        Mockito.verify(border).setBackgroundColor(TextColor.ANSI.RED_BRIGHT);

        Mockito.when(border.getPosition()).thenReturn(new Position(1, 1));
        state.draw(graphics);
        Mockito.verify(graphics).putString(1, 11, "Select troop");
    }

    @Test
    public void InvalidColorTest() {
        HeavyTank tank = new HeavyTank(new Position(0, 0));

        Mockito.when(map.inside(new Position(1,1))).thenReturn(true);
        Water water = new Water(new Position(1, 1), null);
        water.setBackgroundColor(null);
        Mockito.when(map.at(new Position(1,1)))
                .thenReturn(water);

        Border border = Mockito.mock(Border.class);
        Mockito.when(p1.getSelection1())
                .thenReturn(border);

        List<Element> list = new ArrayList<>();
        Mockito.when(p2.getTroops()).thenReturn(list);
        list.add(new LightPerson(new Position(1,1)));

        new AttackNoSelectionState(p1, p2, map, tank);
        Mockito.verify(border, Mockito.never()).setBackgroundColor(Mockito.any());
    }
}
