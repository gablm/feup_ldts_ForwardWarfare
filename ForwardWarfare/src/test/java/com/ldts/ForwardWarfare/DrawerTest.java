package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Controller.Player;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.State;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;

public class DrawerTest {

    @Test
    public void DrawerIncrease() {
        Controller p1 = Mockito.mock(Player.class);
        Controller p2 = Mockito.mock(Player.class);
        Map map = Mockito.mock(Map.class);
        Drawer drawer = new Drawer(p1, p2, map);

        Assertions.assertEquals(0, drawer.getTurnCount());
        drawer.increaseTurnCount();
        Assertions.assertEquals(1, drawer.getTurnCount());
    }

    @Test
    public void DrawerRandomIncrease() {
        Controller p1 = Mockito.mock(Player.class);
        Controller p2 = Mockito.mock(Player.class);
        Map map = Mockito.mock(Map.class);
        Drawer drawer = new Drawer(p1, p2, map);

        Assertions.assertEquals(0, drawer.getTurnCount());
        int times = new Random().nextInt(0, 20);
        for (int i = 0; i < times; i++)
            drawer.increaseTurnCount();
        Assertions.assertEquals(times, drawer.getTurnCount());
    }

    @Test
    public void DrawerDraw() {
        Controller p1 = Mockito.mock(Player.class);
        Mockito.when(p1.getName()).thenReturn("P1");
        Controller p2 = Mockito.mock(Player.class);
        Mockito.when(p1.getName()).thenReturn("P2");
        Map map = Mockito.mock(Map.class);
        State state = Mockito.mock(State.class);
        TextGraphics graphics = Mockito.mock(TextGraphics.class);
        Drawer drawer = new Drawer(p1, p2, map);

        drawer.draw(graphics, state);

        Mockito.verify(p1, Mockito.times(1)).draw(graphics, map);
        Mockito.verify(p2, Mockito.times(1)).draw(graphics, map);
        Mockito.verify(map, Mockito.times(1)).draw(graphics);
        Mockito.verify(state, Mockito.times(1)).draw(graphics);
        Mockito.verify(p1, Mockito.times(1)).drawBorder(graphics);
        Mockito.verify(p2, Mockito.times(1)).drawBorder(graphics);

        Mockito.verify(graphics, Mockito.times(1)).putString(16, 2, "TURN");

        Mockito.verify(graphics, Mockito.times(1)).putString(22, 2, p1.getName());
        Mockito.verify(graphics, Mockito.times(1)).putString(22, 4, "1");
    }

    @Test
    public void DrawerNotZero() {
        Controller p1 = Mockito.mock(Player.class);
        Mockito.when(p1.getName()).thenReturn("P1");
        Controller p2 = Mockito.mock(Player.class);
        Mockito.when(p1.getName()).thenReturn("P2");
        Map map = Mockito.mock(Map.class);
        State state = Mockito.mock(State.class);
        TextGraphics graphics = Mockito.mock(TextGraphics.class);
        Drawer drawer = new Drawer(p1, p2, map);

        drawer.increaseTurnCount();
        drawer.draw(graphics, state);

        Mockito.verify(graphics, Mockito.times(1)).putString(22, 2, p2.getName());
        Mockito.verify(graphics, Mockito.times(1)).putString(22, 4, "1");
    }

    @Test
    public void DrawerMultiOfThree() {
        Controller p1 = Mockito.mock(Player.class);
        Mockito.when(p1.getName()).thenReturn("P1");
        Controller p2 = Mockito.mock(Player.class);
        Mockito.when(p1.getName()).thenReturn("P2");
        Map map = Mockito.mock(Map.class);
        State state = Mockito.mock(State.class);
        TextGraphics graphics = Mockito.mock(TextGraphics.class);
        Drawer drawer = new Drawer(p1, p2, map);

        drawer.increaseTurnCount();
        drawer.increaseTurnCount();
        drawer.increaseTurnCount();
        drawer.draw(graphics, state);

        Mockito.verify(graphics, Mockito.times(1)).putString(22, 2, p1.getName());
        Mockito.verify(graphics, Mockito.times(1)).putString(22, 4, "2");
    }

    @Test
    public void DrawerBaseLives() {
        Controller p1 = Mockito.mock(Player.class);
        Mockito.when(p1.getBaseLives()).thenReturn(1);
        Mockito.when(p1.getCoins()).thenReturn(0);
        Controller p2 = Mockito.mock(Player.class);
        Mockito.when(p2.getBaseLives()).thenReturn(3);
        Mockito.when(p2.getCoins()).thenReturn(50);
        Map map = Mockito.mock(Map.class);
        State state = Mockito.mock(State.class);
        TextGraphics graphics = Mockito.mock(TextGraphics.class);
        Drawer drawer = new Drawer(p1, p2, map);

        drawer.draw(graphics, state);

        Mockito.verify(graphics, Mockito.times(2)).setForegroundColor(TextColor.ANSI.RED_BRIGHT);
        Mockito.verify(graphics, Mockito.times(1)).putString(17, 11, "1 Life");
        Mockito.verify(graphics, Mockito.times(1)).putString(17, 16, "3 Lives");
        Mockito.verify(graphics, Mockito.times(1)).putString(22, 8, "0!");
        Mockito.verify(graphics, Mockito.times(1)).putString(21, 13, "50!");
    }
}
