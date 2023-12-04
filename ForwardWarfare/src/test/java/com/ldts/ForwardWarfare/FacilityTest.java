package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Facility.*;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Tile.Fields;
import com.ldts.ForwardWarfare.Element.Tile.Water;
import org.junit.jupiter.api.*;

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
    @Test
    public void AirportFacility() throws IOException {
        Facility facility= new  Airport();

        TextGraphics graphics = screen.newTextGraphics();
        facility.draw(graphics,new Position(1,1));
        screen.refresh();
        TextCharacter textCharacter = graphics.getCharacter(1, 1);
        Assertions.assertEquals(textCharacter.getForegroundColor(),new TextColor.RGB(255,128,0));
        Assertions.assertEquals(textCharacter.getCharacterString(),">");
        Assertions.assertTrue(textCharacter.getModifiers().isEmpty());
    }
    @Test
    public void BaseFacility() throws IOException {
        Base facility= new Base(new TextColor.RGB(255,128,255));
        Assertions.assertEquals(facility.getTextColor(),new TextColor.RGB(255,128,255));
        TextGraphics graphics = screen.newTextGraphics();
        facility.draw(graphics,new Position(1,1));
        screen.refresh();
        TextCharacter textCharacter = graphics.getCharacter(1, 1);
        Assertions.assertEquals(textCharacter.getForegroundColor(),new TextColor.RGB(255,128,255));
        Assertions.assertEquals(textCharacter.getCharacterString(),";");
        Assertions.assertTrue(textCharacter.getModifiers().isEmpty());
    }
    @Test
    public void FactoryFacility() throws IOException {
        Facility facility= new Factory();

        TextGraphics graphics = screen.newTextGraphics();
        facility.draw(graphics,new Position(1,1));
        screen.refresh();
        TextCharacter textCharacter = graphics.getCharacter(1, 1);
        Assertions.assertEquals(textCharacter.getForegroundColor(),new TextColor.RGB(32,32,32));
        Assertions.assertEquals(textCharacter.getCharacterString(),"`");
        Assertions.assertTrue(textCharacter.getModifiers().isEmpty());
    }
    @Test
    public void Oil_PumpFacility() throws IOException {
        Facility facility= new Oil_Pump();

        TextGraphics graphics = screen.newTextGraphics();
        facility.draw(graphics,new Position(1,1));
        screen.refresh();
        TextCharacter textCharacter = graphics.getCharacter(1, 1);
        Assertions.assertEquals(textCharacter.getForegroundColor(),new TextColor.RGB(255,255,0));
        Assertions.assertEquals(textCharacter.getCharacterString(),"/");
        Assertions.assertTrue(textCharacter.getModifiers().isEmpty());
    }
    @Test
    public void PortFacility() throws IOException {
        Facility facility= new Port();

        TextGraphics graphics = screen.newTextGraphics();
        facility.draw(graphics,new Position(1,1));
        screen.refresh();
        TextCharacter textCharacter = graphics.getCharacter(1, 1);
        Assertions.assertEquals(textCharacter.getForegroundColor(),new TextColor.RGB(51,255,255));
        Assertions.assertEquals(textCharacter.getCharacterString(),")");
        Assertions.assertTrue(textCharacter.getModifiers().isEmpty());
    }
}
