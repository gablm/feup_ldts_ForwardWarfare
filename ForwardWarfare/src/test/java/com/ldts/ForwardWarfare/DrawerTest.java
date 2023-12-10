package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
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

    private Screen screen;

    public void setScreen() throws IOException, URISyntaxException, FontFormatException {
        LanternaTerminal terminal = new LanternaTerminal(new TerminalSize(50,50), "tanks2_0.ttf", 30);
        screen = terminal.createScreen();
        screen.doResizeIfNecessary();
        screen.setCursorPosition(null);
        screen.startScreen();
    }

    public void reset() throws IOException {
        screen.clear();
        screen.refresh();
    }

    public void close() throws IOException {
        screen.close();
        screen.stopScreen();
    }

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
    public void DrawerDraw() throws IOException, URISyntaxException, FontFormatException {
        Controller p1 = Mockito.mock(Player.class);
        Mockito.when(p1.getName()).thenReturn("P1");
        Controller p2 = Mockito.mock(Player.class);
        Mockito.when(p1.getName()).thenReturn("P2");
        Map map = Mockito.mock(Map.class);
        State state = Mockito.mock(State.class);

        setScreen();
        TextGraphics graphics = screen.newTextGraphics();
        Drawer drawer = new Drawer(p1, p2, map);

        reset();
        drawer.draw(graphics, state);
        screen.refresh();

        Mockito.verify(p1, Mockito.times(1)).draw(graphics, map);
        Mockito.verify(p2, Mockito.times(1)).draw(graphics, map);
        Mockito.verify(map, Mockito.times(1)).draw(graphics);
        Mockito.verify(state, Mockito.times(1)).draw(graphics);
        Mockito.verify(p1, Mockito.times(1)).drawBorder(graphics);
        Mockito.verify(p2, Mockito.times(1)).drawBorder(graphics);

        close();
    }
}
