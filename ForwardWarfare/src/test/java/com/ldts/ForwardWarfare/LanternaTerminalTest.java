package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.groupcdg.pitest.annotations.CoverageIgnore;
import com.groupcdg.pitest.annotations.DoNotMutate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

@CoverageIgnore
public class LanternaTerminalTest {

    @DoNotMutate
    public void TerminalBuildTest() throws IOException, URISyntaxException, FontFormatException {

        LanternaTerminal terminal = new LanternaTerminal(
                new TerminalSize(19, 21),
                "Tanks2_1.ttf",
                10
        );

        Assertions.assertNotNull(terminal.getTerminal());
        Assertions.assertNotNull(terminal.getFont());
    }

    @DoNotMutate
    public void TerminalGetScreen() throws IOException, URISyntaxException, FontFormatException {
        LanternaTerminal terminal = Mockito.mock(LanternaTerminal.class);

        Screen screen = terminal.createScreen();

        Assertions.assertNotNull(screen);
        screen.stopScreen();
        Assertions.assertNull(screen.getCursorPosition());
    }
}
