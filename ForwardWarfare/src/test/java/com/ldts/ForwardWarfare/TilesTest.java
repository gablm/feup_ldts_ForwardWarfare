package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Facility.Facility;
import com.ldts.ForwardWarfare.Element.Facility.Factory;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Tile.*;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class TilesTest {
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
    public void WaterTileTest() throws IOException {
        Element Tile= new Water(new Position(1,1),null);

        Assertions.assertEquals(Tile.getPosition(),new Position(1,1));
        Assertions.assertNull(Tile.getFacility());

        TextGraphics graphics = screen.newTextGraphics();
        Tile.draw(graphics, null);
        screen.refresh();
        TextCharacter textCharacter = graphics.getCharacter(1, 1);

        Assertions.assertEquals(textCharacter.getBackgroundColor(),new TextColor.RGB(0,124,206));
        Assertions.assertEquals(textCharacter.getForegroundColor(),new TextColor.RGB(224,224,224));
        Assertions.assertEquals(textCharacter.getCharacterString(),"~");
        Assertions.assertTrue(textCharacter.getModifiers().isEmpty());
    }

    @Test
    public void WaterTileTest_WithFacility() throws IOException {
        Element Tile= new Water(new Position(1, 1), new Factory() );

        Assertions.assertEquals(Tile.getPosition(),new Position(1,1));
        Assertions.assertEquals(Tile.getFacility().getClass(), Factory.class);

        TextGraphics graphics = screen.newTextGraphics();
        Tile.draw(graphics, null);
        screen.refresh();
        TextCharacter textCharacter = graphics.getCharacter(1, 1);

        Assertions.assertEquals(textCharacter.getBackgroundColor(),new TextColor.RGB(0,124,206));
        Assertions.assertEquals(textCharacter.getForegroundColor(),new TextColor.RGB(32, 32, 32));
        Assertions.assertEquals(textCharacter.getCharacterString(),"`");
        Assertions.assertTrue(textCharacter.getModifiers().isEmpty());
    }

    @Test
    public void FieldsTileTest() throws IOException {
        Element Tile= new Fields(new Position(1,1),null);

        Assertions.assertEquals(Tile.getPosition(),new Position(1,1));
        Assertions.assertNull(Tile.getFacility());

        TextGraphics graphics = screen.newTextGraphics();
        Tile.draw(graphics, null);
        screen.refresh();
        TextCharacter textCharacter = graphics.getCharacter(1, 1);

        Assertions.assertEquals(textCharacter.getBackgroundColor(),new TextColor.RGB(113,199,0));
        Assertions.assertEquals(textCharacter.getForegroundColor(),new TextColor.RGB(226,214,106));
        Assertions.assertEquals(textCharacter.getCharacterString(),"|");
        Assertions.assertTrue(textCharacter.getModifiers().isEmpty());
    }

    @Test
    public void FieldsTileTest_WithFacility() throws IOException {
        Element Tile= new Fields(new Position(1,1),new Factory());

        Assertions.assertEquals(Tile.getPosition(),new Position(1,1));
        Assertions.assertEquals(Tile.getFacility().getClass(), Factory.class);

        TextGraphics graphics = screen.newTextGraphics();
        Tile.draw(graphics, null);
        screen.refresh();
        TextCharacter textCharacter = graphics.getCharacter(1, 1);

        Assertions.assertEquals(textCharacter.getBackgroundColor(),new TextColor.RGB(113,199,0));
        Assertions.assertEquals(textCharacter.getForegroundColor(),new TextColor.RGB(32, 32, 32));
        Assertions.assertEquals(textCharacter.getCharacterString(),"`");
        Assertions.assertTrue(textCharacter.getModifiers().isEmpty());
    }

    @Test
    public void ForestTileTest() throws IOException {
        Element Tile= new Forest(new Position(1,1));

        Assertions.assertEquals(Tile.getPosition(),new Position(1,1));
        Assertions.assertNull(Tile.getFacility());

        TextGraphics graphics = screen.newTextGraphics();
        Tile.draw(graphics, null);
        screen.refresh();
        TextCharacter textCharacter = graphics.getCharacter(1, 1);

        Assertions.assertEquals(textCharacter.getBackgroundColor(),new TextColor.RGB(113,199,0));
        Assertions.assertEquals(textCharacter.getForegroundColor(),new TextColor.RGB(0,102,51));
        Assertions.assertEquals(textCharacter.getCharacterString(),"{");
        Assertions.assertTrue(textCharacter.getModifiers().isEmpty());
    }

    @Test
    public void MountainWaterTileTest() throws IOException {
        Element Tile= new MountainWater(new Position(1,1));

        Assertions.assertEquals(Tile.getPosition(),new Position(1,1));
        Assertions.assertNull(Tile.getFacility());

        TextGraphics graphics = screen.newTextGraphics();
        Tile.draw(graphics, null);
        screen.refresh();
        TextCharacter textCharacter = graphics.getCharacter(1, 1);

        Assertions.assertEquals(textCharacter.getBackgroundColor(),new TextColor.RGB(0,124,206));
        Assertions.assertEquals(textCharacter.getForegroundColor(),new TextColor.RGB(160,160,160));
        Assertions.assertEquals(textCharacter.getCharacterString(),"]");
        Assertions.assertTrue(textCharacter.getModifiers().isEmpty());
    }
    @Test
    public void MountainLandTileTest() throws IOException {
        Element Tile= new MountainLand(new Position(1,1));

        Assertions.assertEquals(Tile.getPosition(),new Position(1,1));
        Assertions.assertNull(Tile.getFacility());

        TextGraphics graphics = screen.newTextGraphics();
        Tile.draw(graphics, null);
        screen.refresh();
        TextCharacter textCharacter = graphics.getCharacter(1, 1);

        Assertions.assertEquals(textCharacter.getBackgroundColor(),new TextColor.RGB(113,199,0));
        Assertions.assertEquals(textCharacter.getForegroundColor(),new TextColor.RGB(57,45,45));
        Assertions.assertEquals(textCharacter.getCharacterString(),"}");
        Assertions.assertTrue(textCharacter.getModifiers().isEmpty());
    }

    @Test
    public void WaterTileTest_ChangePosition()  {
        Element Tile= new Water(new Position(1,1),null);
        Assertions.assertEquals(Tile.getPosition(),new Position(1,1));

        Tile.setPosition(new Position(2,2));
        Assertions.assertEquals(Tile.getPosition(),new Position(2,2));
    }


    @Test
    public void MountainLandTileTest_ChangePosition()   {
        Element Tile= new MountainLand(new Position(1,1));
        Assertions.assertEquals(Tile.getPosition(),new Position(1,1));

        Tile.setPosition(new Position(2,2));
        Assertions.assertEquals(Tile.getPosition(),new Position(2,2));
    }
    @Test
    public void MountainWaterTileTest_ChangePosition()   {
        Element Tile= new MountainWater(new Position(1,1));
        Assertions.assertEquals(Tile.getPosition(),new Position(1,1));

        Tile.setPosition(new Position(2,2));
        Assertions.assertEquals(Tile.getPosition(),new Position(2,2));
    }
    @Test
    public void ForestTileTest_ChangePosition()   {
        Element Tile= new Forest(new Position(1,1));
        Assertions.assertEquals(Tile.getPosition(),new Position(1,1));

        Tile.setPosition(new Position(2,2));
        Assertions.assertEquals(Tile.getPosition(),new Position(2,2));
    }
    @Test
    public void FieldsTileTest_ChangePosition()   {
        Element Tile= new Fields(new Position(1,1),null);
        Assertions.assertEquals(Tile.getPosition(),new Position(1,1));

        Tile.setPosition(new Position(2,2));
        Assertions.assertEquals(Tile.getPosition(),new Position(2,2));
    }
    @Test
    public void FieldsTileTest_WithFacility_ChangePosition()   {
        Element Tile= new Fields(new Position(1,1),new Factory());
        Assertions.assertEquals(Tile.getPosition(),new Position(1,1));

        Tile.setPosition(new Position(2,2));
        Assertions.assertEquals(Tile.getPosition(),new Position(2,2));
    }
    @Test
    public void WaterTileTest_WithFacility_ChangePosition()   {
        Element Tile= new Water(new Position(1,1),new Factory());
        Assertions.assertEquals(Tile.getPosition(),new Position(1,1));

        Tile.setPosition(new Position(2,2));
        Assertions.assertEquals(Tile.getPosition(),new Position(2,2));
    }

}
