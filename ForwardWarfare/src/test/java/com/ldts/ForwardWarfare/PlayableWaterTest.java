package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Element.Playable.Water.FighterSubmarine;
import com.ldts.ForwardWarfare.Element.Playable.Water.LightBoat;
import com.ldts.ForwardWarfare.Element.Playable.Water.MortarBoat;
import com.ldts.ForwardWarfare.Element.Position;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;

public class PlayableWaterTest {
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
    public void FighterSubmarineDefaultTest() throws IOException, InterruptedException {
        Playable fighterSubmarine = new FighterSubmarine(new Position(1,1));

        Assertions.assertEquals(fighterSubmarine.getPosition(), new Position(1,1));

        TextGraphics graphics = screen.newTextGraphics();
        fighterSubmarine.draw(graphics, null);
        screen.refresh();
        TextCharacter textCharacter = graphics.getCharacter(1, 1);

        Assertions.assertEquals(textCharacter.getCharacterString(), "=");
        Assertions.assertEquals(textCharacter.getForegroundColor(), new TextColor.RGB(80, 80, 80));
        Assertions.assertTrue(textCharacter.getModifiers().isEmpty());
    }

    @Test
    public void FighterSubmarineRandomTest() throws IOException {
        Random random = new Random();
        int x = random.nextInt(0, 25), y = random.nextInt(0, 25);
        Playable fighterSubmarine = new FighterSubmarine(new Position(x,y));

        Assertions.assertEquals(fighterSubmarine.getPosition(), new Position(x,y));

        TextGraphics graphics = screen.newTextGraphics();
        int r = random.nextInt(0, 256), g = random.nextInt(0, 256), b = random.nextInt(0, 256);
        fighterSubmarine.draw(graphics, new TextColor.RGB(r, g, b));
        screen.refresh();
        TextCharacter textCharacter = graphics.getCharacter(x, y);

        Assertions.assertNotNull(textCharacter);
        Assertions.assertEquals(textCharacter.getCharacterString(), "=");
        Assertions.assertEquals(textCharacter.getForegroundColor(), new TextColor.RGB(r, g, b));
        Assertions.assertTrue(textCharacter.getModifiers().isEmpty());
    }

    @Test
    public void LightBoatDefaultTest() throws IOException {
        Playable lightBoat = new LightBoat(new Position(1,1));

        Assertions.assertEquals(lightBoat.getPosition(), new Position(1,1));

        TextGraphics graphics = screen.newTextGraphics();
        lightBoat.draw(graphics, null);
        screen.refresh();
        TextCharacter textCharacter = graphics.getCharacter(1, 1);

        Assertions.assertEquals(textCharacter.getCharacterString(), "<");
        Assertions.assertEquals(textCharacter.getForegroundColor(), new TextColor.RGB(80, 80, 80));
        Assertions.assertTrue(textCharacter.getModifiers().isEmpty());
    }

    @Test
    public void LightBoatRandomTest() throws IOException {
        Random random = new Random();
        int x = random.nextInt(0, 25), y = random.nextInt(0, 25);
        Playable lightBoat = new LightBoat(new Position(x,y));

        Assertions.assertEquals(lightBoat.getPosition(), new Position(x,y));

        TextGraphics graphics = screen.newTextGraphics();
        int r = random.nextInt(0, 256), g = random.nextInt(0, 256), b = random.nextInt(0, 256);
        lightBoat.draw(graphics, new TextColor.RGB(r, g, b));
        screen.refresh();
        TextCharacter textCharacter = graphics.getCharacter(x, y);

        Assertions.assertNotNull(textCharacter);
        Assertions.assertEquals(textCharacter.getCharacterString(), "<");
        Assertions.assertEquals(textCharacter.getForegroundColor(), new TextColor.RGB(r, g, b));
        Assertions.assertTrue(textCharacter.getModifiers().isEmpty());
    }

    @Test
    public void MortarBoatDefaultTest() throws IOException {
        Playable mortarBoat = new MortarBoat(new Position(1,1));

        Assertions.assertEquals(mortarBoat.getPosition(), new Position(1,1));

        TextGraphics graphics = screen.newTextGraphics();
        mortarBoat.draw(graphics, null);
        screen.refresh();
        TextCharacter textCharacter = graphics.getCharacter(1, 1);

        Assertions.assertEquals(textCharacter.getCharacterString(), "'");
        Assertions.assertEquals(textCharacter.getForegroundColor(), new TextColor.RGB(80, 80, 80));
        Assertions.assertTrue(textCharacter.getModifiers().isEmpty());
    }

    @Test
    public void MortarBoatRandomTest() throws IOException {
        Random random = new Random();
        int x = random.nextInt(0, 25), y = random.nextInt(0, 25);
        Playable mortarBoat = new MortarBoat(new Position(x,y));

        Assertions.assertEquals(mortarBoat.getPosition(), new Position(x,y));

        TextGraphics graphics = screen.newTextGraphics();
        int r = random.nextInt(0, 256), g = random.nextInt(0, 256), b = random.nextInt(0, 256);
        mortarBoat.draw(graphics, new TextColor.RGB(r, g, b));
        screen.refresh();
        TextCharacter textCharacter = graphics.getCharacter(x, y);

        Assertions.assertNotNull(textCharacter);
        Assertions.assertEquals(textCharacter.getCharacterString(), "'");
        Assertions.assertEquals(textCharacter.getForegroundColor(), new TextColor.RGB(r, g, b));
        Assertions.assertTrue(textCharacter.getModifiers().isEmpty());
    }
}
