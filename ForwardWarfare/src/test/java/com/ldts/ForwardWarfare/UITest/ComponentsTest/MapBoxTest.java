package com.ldts.ForwardWarfare.UITest.ComponentsTest;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.Map.MapParseException;
import com.ldts.ForwardWarfare.UI.Component.MapBox;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public class MapBoxTest {
    @Mock
    private TextGraphics textGraphicsMock;
    @Mock
    private MapBox mapBox;
    @Mock
    private Map map;
    @BeforeEach
    public void setUp() throws FileNotFoundException, MapParseException, URISyntaxException {
        MockitoAnnotations.openMocks(this);
        mapBox = Mockito.spy(new MapBox(TextColor.ANSI.CYAN,TextColor.ANSI.BLACK,new Position(0,0),new TerminalSize(17,12),0,"tests/MapBoxTest.fw"));
    }

    @Test
    public void MapBox_ConstructorTest(){
        Assertions.assertEquals(TextColor.ANSI.CYAN,mapBox.getBackColor());
        Assertions.assertEquals(TextColor.ANSI.BLACK,mapBox.getForgColor());
        Assertions.assertEquals(new Position(0,0),mapBox.getPosition());
        Assertions.assertEquals(new TerminalSize(17,12),mapBox.getSize());
        Assertions.assertEquals(0,mapBox.getBorderFadeIntensity());
    }

    @Test
    public void MapBox_draw(){
        TextColor first= mapBox.getBorderColor();
        mapBox.draw(textGraphicsMock);
        Mockito.verify(textGraphicsMock, Mockito.times(151)).setBackgroundColor(Mockito.any());
        Mockito.verify(textGraphicsMock, Mockito.times(1)).drawRectangle(Mockito.any(),Mockito.any(),Mockito.anyChar());
        Mockito.verify(textGraphicsMock, Mockito.times(150)).putString(Mockito.any(),Mockito.anyString());
        Assertions.assertTrue(first.getBlue()>=mapBox.getBorderColor().getBlue());
        Assertions.assertTrue(first.getGreen()>=mapBox.getBorderColor().getGreen());
        Assertions.assertTrue(first.getRed()>=mapBox.getBorderColor().getRed());
    }
    @Test
    public void MapBox_draw_mapoes_test() throws FileNotFoundException, MapParseException, URISyntaxException {
        MapBox temp = new MapBox(TextColor.ANSI.CYAN,TextColor.ANSI.BLACK,new Position(12,50),new TerminalSize(17,12),0,"tests/MapBoxTest.fw");
        Map tempmap = temp.getMap();
        Map nm = temp.getnewMap();
        for (int i = 0; i < tempmap.getElements().size(); i++) {
            Assertions.assertNotEquals(tempmap.getElements().get(i).getPosition(),nm.getElements().get(i).getPosition());
            Assertions.assertTrue(tempmap.getElements().get(i).getPosition().getX()<temp.getPosition().getX() || tempmap.getElements().get(i).getPosition().getY()<temp.getPosition().getY());
            Assertions.assertTrue(new Position(tempmap.getElements().get(i).getPosition().getX() + 13, tempmap.getElements().get(i).getPosition().getY() + 51).equals(nm.getElements().get(i).getPosition()));
        }
    }

    @Test
    public void Mapbox_setboard_test() throws FileNotFoundException, MapParseException, URISyntaxException {
        MapBox temp = new MapBox(TextColor.ANSI.CYAN,TextColor.ANSI.BLACK,new Position(0,0),new TerminalSize(17,12),300,"tests/MapBoxTest.fw");
        TextColor first= temp.getBorderColor();
        temp.draw(textGraphicsMock);
        Assertions.assertTrue(first.getBlue()>=temp.getBorderColor().getBlue());
        Assertions.assertTrue(first.getGreen()>=temp.getBorderColor().getGreen());
        Assertions.assertTrue(first.getRed()>=temp.getBorderColor().getRed());
    }
    @Test
    public void Mapbox_setboardexistence_test() throws FileNotFoundException, MapParseException, URISyntaxException {
        MapBox temp = new MapBox(new TextColor.RGB(20,20,20),new TextColor.RGB(20,20,20),new Position(0,0),new TerminalSize(17,12),10,"tests/MapBoxTest.fw");
        TextColor first= temp.getBorderColor();
        temp.draw(textGraphicsMock);
        Assertions.assertTrue(first.getBlue()>temp.getBorderColor().getBlue());
        Assertions.assertTrue(first.getGreen()>temp.getBorderColor().getGreen());
        Assertions.assertTrue(first.getRed()>temp.getBorderColor().getRed());
    }
    @Test
    public void MapBox_getMap_test(){
        Map temp = Mockito.mock(Map.class);
        mapBox.setOldMap(temp);
        Assertions.assertEquals(temp,mapBox.getMap());
    }
}
