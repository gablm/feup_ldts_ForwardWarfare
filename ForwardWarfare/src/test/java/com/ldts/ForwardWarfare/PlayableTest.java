package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.ldts.ForwardWarfare.Element.Playable.Air.BomberPlane;
import com.ldts.ForwardWarfare.Element.Position;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class PlayableTest {
    private Screen screen;
    @BeforeEach
    public void setScreen() throws IOException, URISyntaxException, FontFormatException {
        LanternaTerminal terminal = new LanternaTerminal(new TerminalSize(20,20), "tanks2_0.ttf", 30);
        screen = terminal.createScreen();
        screen.doResizeIfNecessary();
        screen.setCursorPosition(null);
        screen.startScreen();
    }

    @AfterEach
    public void reset() throws IOException {
        screen.clear();
        screen.stopScreen();
    }

    @Test
    public void TestBomberPlane() throws IOException {
        BomberPlane bomberPlane = new BomberPlane(new Position(1,1));

        Assertions.assertEquals(bomberPlane.getPosition(), new Position(1,1));
        TextGraphics graphics = screen.newTextGraphics();
        bomberPlane.draw(graphics, null);
        screen.refresh();
        TextCharacter textCharacter = graphics.getCharacter(1, 1);
    }
}
