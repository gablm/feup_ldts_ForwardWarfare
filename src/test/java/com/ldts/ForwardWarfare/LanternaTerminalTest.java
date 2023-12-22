package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class LanternaTerminalTest {

    @Test
    public void TerminalBuildTest() throws IOException, URISyntaxException, FontFormatException {

        LanternaTerminal terminal = new LanternaTerminal(
                new TerminalSize(19, 21),
                "Tanks2_1.ttf",
                10
        );

        Assertions.assertNotNull(terminal.getTerminal());
        Assertions.assertNotNull(terminal.getFont());
    }

    @Test
    public void TerminalGetScreen() throws IOException, URISyntaxException, FontFormatException {
        LanternaTerminal terminal = new LanternaTerminal(
                new TerminalSize(19, 21),
                "Tanks2_1.ttf",
                10
        );

        Screen screen = terminal.createScreen();

        Assertions.assertNotNull(screen);
        screen.stopScreen();
        Assertions.assertNull(screen.getCursorPosition());
    }
}
