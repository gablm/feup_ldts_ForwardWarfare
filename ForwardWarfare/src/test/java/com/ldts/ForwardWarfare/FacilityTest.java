package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class FacilityTest {
    private static Screen screen;
    @BeforeAll
    public static void setScreen() throws IOException, URISyntaxException, FontFormatException {
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
}
