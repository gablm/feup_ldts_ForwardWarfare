package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.ldts.ForwardWarfare.Element.Playable.Air.BomberPlane;
import com.ldts.ForwardWarfare.Element.Playable.Air.FighterPlane;
import com.ldts.ForwardWarfare.Element.Playable.Air.LightHelicopter;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Element.Position;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;

public class PlayableAirTest {
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
    @Test
    public void BomberPlaneDefaultTest() throws IOException {
        Playable bomberPlane = new BomberPlane(new Position(1,1));

        Assertions.assertEquals(bomberPlane.getPosition(), new Position(1,1));

        TextGraphics graphics = screen.newTextGraphics();
        bomberPlane.draw(graphics);
        screen.refresh();
        TextCharacter textCharacter = graphics.getCharacter(1, 1);

        Assertions.assertEquals(textCharacter.getCharacterString(), "&");
        Assertions.assertEquals(textCharacter.getForegroundColor(), new TextColor.RGB(80, 80, 80));
        Assertions.assertTrue(textCharacter.getModifiers().isEmpty());
    }

    @Test
    public void BomberPlaneRandomTest() throws IOException {
        Random random = new Random();
        int x = random.nextInt(0, 25), y = random.nextInt(0, 25);
        Playable bomberPlane = new BomberPlane(new Position(x,y));

        Assertions.assertEquals(bomberPlane.getPosition(), new Position(x,y));

        TextGraphics graphics = screen.newTextGraphics();
        int r = random.nextInt(0, 256), g = random.nextInt(0, 256), b = random.nextInt(0, 256);
        bomberPlane.setForegroundColor(new TextColor.RGB(r, g, b));
        bomberPlane.draw(graphics);
        screen.refresh();
        TextCharacter textCharacter = graphics.getCharacter(x, y);

        Assertions.assertNotNull(textCharacter);
        Assertions.assertEquals(textCharacter.getCharacterString(), "&");
        Assertions.assertEquals(textCharacter.getForegroundColor(), new TextColor.RGB(r, g, b));
        Assertions.assertTrue(textCharacter.getModifiers().isEmpty());
    }

    @Test
    public void FighterPlaneDefaultTest() throws IOException {
        Playable fighterPlane = new FighterPlane(new Position(1,1));

        Assertions.assertEquals(fighterPlane.getPosition(), new Position(1,1));

        TextGraphics graphics = screen.newTextGraphics();
        fighterPlane.draw(graphics);
        screen.refresh();
        TextCharacter textCharacter = graphics.getCharacter(1, 1);

        Assertions.assertEquals(textCharacter.getCharacterString(), "%");
        Assertions.assertEquals(textCharacter.getForegroundColor(), new TextColor.RGB(80, 80, 80));
        Assertions.assertTrue(textCharacter.getModifiers().isEmpty());
    }

    @Test
    public void FighterPlaneRandomTest() throws IOException {
        Random random = new Random();
        int x = random.nextInt(0, 25), y = random.nextInt(0, 25);
        Playable fighterPlane = new FighterPlane(new Position(x,y));

        Assertions.assertEquals(fighterPlane.getPosition(), new Position(x,y));

        TextGraphics graphics = screen.newTextGraphics();
        int r = random.nextInt(0, 256), g = random.nextInt(0, 256), b = random.nextInt(0, 256);
        fighterPlane.setForegroundColor(new TextColor.RGB(r, g, b));
        fighterPlane.draw(graphics);
        screen.refresh();
        TextCharacter textCharacter = graphics.getCharacter(x, y);

        Assertions.assertNotNull(textCharacter);
        Assertions.assertEquals(textCharacter.getCharacterString(), "%");
        Assertions.assertEquals(textCharacter.getForegroundColor(), new TextColor.RGB(r, g, b));
        Assertions.assertTrue(textCharacter.getModifiers().isEmpty());
    }

    @Test
    public void LightHelicopterDefaultTest() throws IOException {
        Playable lightHelicopter = new LightHelicopter(new Position(1,1));

        Assertions.assertEquals(lightHelicopter.getPosition(), new Position(1,1));

        TextGraphics graphics = screen.newTextGraphics();
        lightHelicopter.draw(graphics);
        screen.refresh();
        TextCharacter textCharacter = graphics.getCharacter(1, 1);

        Assertions.assertEquals(textCharacter.getCharacterString(), "[");
        Assertions.assertEquals(textCharacter.getForegroundColor(), new TextColor.RGB(80, 80, 80));
        Assertions.assertTrue(textCharacter.getModifiers().isEmpty());
    }

    @Test
    public void LightHelicopterRandomTest() throws IOException {
        Random random = new Random();
        int x = random.nextInt(0, 25), y = random.nextInt(0, 25);
        Playable lightHelicopter = new LightHelicopter(new Position(x,y));

        Assertions.assertEquals(lightHelicopter.getPosition(), new Position(x,y));

        TextGraphics graphics = screen.newTextGraphics();
        int r = random.nextInt(0, 256), g = random.nextInt(0, 256), b = random.nextInt(0, 256);
        lightHelicopter.setForegroundColor(new TextColor.RGB(r, g, b));
        lightHelicopter.draw(graphics);
        screen.refresh();
        TextCharacter textCharacter = graphics.getCharacter(x, y);

        Assertions.assertNotNull(textCharacter);
        Assertions.assertEquals(textCharacter.getCharacterString(), "[");
        Assertions.assertEquals(textCharacter.getForegroundColor(), new TextColor.RGB(r, g, b));
        Assertions.assertTrue(textCharacter.getModifiers().isEmpty());
    }
}
