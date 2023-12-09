package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Controller.Player;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.States.QuitState;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class DrawerTest {
    private static Drawer drawer;
    private static Screen screen;
    @BeforeAll
    public static void CreateThings() throws IOException, URISyntaxException, FontFormatException {
        Controller p1 = Mockito.mock(Player.class);
        Controller p2 = Mockito.mock(Player.class);
        Map map = Mockito.mock(Map.class);
        drawer = new Drawer(p1, p2, map);

        LanternaTerminal terminal = new LanternaTerminal(new TerminalSize(50,50), "tanks2_0.ttf", 30);
        screen = terminal.createScreen();
        screen.doResizeIfNecessary();
        screen.setCursorPosition(null);
        screen.startScreen();
    }

    @AfterEach
    public void reset() throws IOException {
        screen.clear();
        screen.refresh();
    }

    @AfterAll
    public static void close() throws IOException {
        screen.close();
        screen.stopScreen();
    }

    @Test
    public void Draw() {
        State state = Mockito.mock(State.class);
        drawer.draw(screen.newTextGraphics(), state);

    }
}
