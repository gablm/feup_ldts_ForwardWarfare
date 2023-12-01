package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.ldts.ForwardWarfare.Element.Playable.Ground.*;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Element.Position;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;

public class PlayableGroundTest {
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
    public void AntiAirTankDefaultTest() throws IOException {
        Playable antiAirTank = new AntiAirTank(new Position(1,1));

        Assertions.assertEquals(antiAirTank.getPosition(), new Position(1,1));

        TextGraphics graphics = screen.newTextGraphics();
        antiAirTank.draw(graphics);
        screen.refresh();
        TextCharacter textCharacter = graphics.getCharacter(1, 1);

        Assertions.assertEquals(textCharacter.getCharacterString(), "+");
        Assertions.assertEquals(textCharacter.getForegroundColor(), new TextColor.RGB(80, 80, 80));
        Assertions.assertTrue(textCharacter.getModifiers().isEmpty());
    }

    @Test
    public void AntiAirTankRandomTest() throws IOException {
        Random random = new Random();
        int x = random.nextInt(0, 25), y = random.nextInt(0, 25);
        Playable antiAirTank = new AntiAirTank(new Position(x,y));

        Assertions.assertEquals(antiAirTank.getPosition(), new Position(x,y));

        TextGraphics graphics = screen.newTextGraphics();
        int r = random.nextInt(0, 256), g = random.nextInt(0, 256), b = random.nextInt(0, 256);
        antiAirTank.setForegroundColor(new TextColor.RGB(r, g, b));
        antiAirTank.draw(graphics);
        screen.refresh();
        TextCharacter textCharacter = graphics.getCharacter(x, y);

        Assertions.assertNotNull(textCharacter);
        Assertions.assertEquals(textCharacter.getCharacterString(), "+");
        Assertions.assertEquals(textCharacter.getForegroundColor(), new TextColor.RGB(r, g, b));
        Assertions.assertTrue(textCharacter.getModifiers().isEmpty());
    }

    @Test
    public void HeavyPersonDefaultTest() throws IOException {
        Playable bomberPlane = new HeavyPerson(new Position(1,1));

        Assertions.assertEquals(bomberPlane.getPosition(), new Position(1,1));

        TextGraphics graphics = screen.newTextGraphics();
        bomberPlane.draw(graphics);
        screen.refresh();
        TextCharacter textCharacter = graphics.getCharacter(1, 1);

        Assertions.assertEquals(textCharacter.getCharacterString(), "_");
        Assertions.assertEquals(textCharacter.getForegroundColor(), new TextColor.RGB(80, 80, 80));
        Assertions.assertTrue(textCharacter.getModifiers().isEmpty());
    }

    @Test
    public void HeavyPersonRandomTest() throws IOException {
        Random random = new Random();
        int x = random.nextInt(0, 25), y = random.nextInt(0, 25);
        Playable heavyPerson = new HeavyPerson(new Position(x,y));

        Assertions.assertEquals(heavyPerson.getPosition(), new Position(x,y));

        TextGraphics graphics = screen.newTextGraphics();
        int r = random.nextInt(0, 256), g = random.nextInt(0, 256), b = random.nextInt(0, 256);
        heavyPerson.setForegroundColor(new TextColor.RGB(r, g, b));
        heavyPerson.draw(graphics);
        screen.refresh();
        TextCharacter textCharacter = graphics.getCharacter(x, y);

        Assertions.assertNotNull(textCharacter);
        Assertions.assertEquals(textCharacter.getCharacterString(), "_");
        Assertions.assertEquals(textCharacter.getForegroundColor(), new TextColor.RGB(r, g, b));
        Assertions.assertTrue(textCharacter.getModifiers().isEmpty());
    }

    @Test
    public void HeavyTankDefaultTest() throws IOException {
        Playable heavyTank = new HeavyTank(new Position(1,1));

        Assertions.assertEquals(heavyTank.getPosition(), new Position(1,1));

        TextGraphics graphics = screen.newTextGraphics();
        heavyTank.draw(graphics);
        screen.refresh();
        TextCharacter textCharacter = graphics.getCharacter(1, 1);

        Assertions.assertEquals(textCharacter.getCharacterString(), "#");
        Assertions.assertEquals(textCharacter.getForegroundColor(), new TextColor.RGB(80, 80, 80));
        Assertions.assertTrue(textCharacter.getModifiers().isEmpty());
    }

    @Test
    public void HeavyTankRandomTest() throws IOException {
        Random random = new Random();
        int x = random.nextInt(0, 25), y = random.nextInt(0, 25);
        Playable heavyTank = new HeavyTank(new Position(x,y));

        Assertions.assertEquals(heavyTank.getPosition(), new Position(x,y));

        TextGraphics graphics = screen.newTextGraphics();
        int r = random.nextInt(0, 256), g = random.nextInt(0, 256), b = random.nextInt(0, 256);
        heavyTank.setForegroundColor(new TextColor.RGB(r, g, b));
        heavyTank.draw(graphics);
        screen.refresh();
        TextCharacter textCharacter = graphics.getCharacter(x, y);

        Assertions.assertNotNull(textCharacter);
        Assertions.assertEquals(textCharacter.getCharacterString(), "#");
        Assertions.assertEquals(textCharacter.getForegroundColor(), new TextColor.RGB(r, g, b));
        Assertions.assertTrue(textCharacter.getModifiers().isEmpty());
    }

    @Test
    public void LightPersonDefaultTest() throws IOException {
        Playable lightPerson = new LightPerson(new Position(1,1));

        Assertions.assertEquals(lightPerson.getPosition(), new Position(1,1));

        TextGraphics graphics = screen.newTextGraphics();
        lightPerson.draw(graphics);
        screen.refresh();
        TextCharacter textCharacter = graphics.getCharacter(1, 1);

        Assertions.assertEquals(textCharacter.getCharacterString(), "(");
        Assertions.assertEquals(textCharacter.getForegroundColor(), new TextColor.RGB(80, 80, 80));
        Assertions.assertTrue(textCharacter.getModifiers().isEmpty());
    }

    @Test
    public void LightPersonRandomTest() throws IOException {
        Random random = new Random();
        int x = random.nextInt(0, 25), y = random.nextInt(0, 25);
        Playable lightPerson = new LightPerson(new Position(x,y));

        Assertions.assertEquals(lightPerson.getPosition(), new Position(x,y));

        TextGraphics graphics = screen.newTextGraphics();
        int r = random.nextInt(0, 256), g = random.nextInt(0, 256), b = random.nextInt(0, 256);
        lightPerson.setForegroundColor(new TextColor.RGB(r, g, b));
        lightPerson.draw(graphics);
        screen.refresh();
        TextCharacter textCharacter = graphics.getCharacter(x, y);

        Assertions.assertNotNull(textCharacter);
        Assertions.assertEquals(textCharacter.getCharacterString(), "(");
        Assertions.assertEquals(textCharacter.getForegroundColor(), new TextColor.RGB(r, g, b));
        Assertions.assertTrue(textCharacter.getModifiers().isEmpty());
    }

    @Test
    public void LightTankDefaultTest() throws IOException {
        Playable lightTank = new LightTank(new Position(1,1));

        Assertions.assertEquals(lightTank.getPosition(), new Position(1,1));

        TextGraphics graphics = screen.newTextGraphics();
        lightTank.draw(graphics);
        screen.refresh();
        TextCharacter textCharacter = graphics.getCharacter(1, 1);

        Assertions.assertEquals(textCharacter.getCharacterString(), "$");
        Assertions.assertEquals(textCharacter.getForegroundColor(), new TextColor.RGB(80, 80, 80));
        Assertions.assertTrue(textCharacter.getModifiers().isEmpty());
    }

    @Test
    public void LightTankRandomTest() throws IOException {
        Random random = new Random();
        int x = random.nextInt(0, 25), y = random.nextInt(0, 25);
        Playable lightTank = new LightTank(new Position(x,y));

        Assertions.assertEquals(lightTank.getPosition(), new Position(x,y));

        TextGraphics graphics = screen.newTextGraphics();
        int r = random.nextInt(0, 256), g = random.nextInt(0, 256), b = random.nextInt(0, 256);
        lightTank.setForegroundColor(new TextColor.RGB(r, g, b));
        lightTank.draw(graphics);
        screen.refresh();
        TextCharacter textCharacter = graphics.getCharacter(x, y);

        Assertions.assertNotNull(textCharacter);
        Assertions.assertEquals(textCharacter.getCharacterString(), "$");
        Assertions.assertEquals(textCharacter.getForegroundColor(), new TextColor.RGB(r, g, b));
        Assertions.assertTrue(textCharacter.getModifiers().isEmpty());
    }

    @Test
    public void MortarPersonDefaultTest() throws IOException {
        Playable mortarPerson = new MortarPerson(new Position(1,1));

        Assertions.assertEquals(mortarPerson.getPosition(), new Position(1,1));

        TextGraphics graphics = screen.newTextGraphics();
        mortarPerson.draw(graphics);
        screen.refresh();
        TextCharacter textCharacter = graphics.getCharacter(1, 1);

        Assertions.assertEquals(textCharacter.getCharacterString(), "@");
        Assertions.assertEquals(textCharacter.getForegroundColor(), new TextColor.RGB(80, 80, 80));
        Assertions.assertTrue(textCharacter.getModifiers().isEmpty());
    }

    @Test
    public void MortarPersonRandomTest() throws IOException {
        Random random = new Random();
        int x = random.nextInt(0, 25), y = random.nextInt(0, 25);
        Playable mortarPerson = new MortarPerson(new Position(x,y));

        Assertions.assertEquals(mortarPerson.getPosition(), new Position(x,y));

        TextGraphics graphics = screen.newTextGraphics();
        int r = random.nextInt(0, 256), g = random.nextInt(0, 256), b = random.nextInt(0, 256);
        mortarPerson.setForegroundColor(new TextColor.RGB(r, g, b));
        mortarPerson.draw(graphics);
        screen.refresh();
        TextCharacter textCharacter = graphics.getCharacter(x, y);

        Assertions.assertNotNull(textCharacter);
        Assertions.assertEquals(textCharacter.getCharacterString(), "@");
        Assertions.assertEquals(textCharacter.getForegroundColor(), new TextColor.RGB(r, g, b));
        Assertions.assertTrue(textCharacter.getModifiers().isEmpty());
    }
}
