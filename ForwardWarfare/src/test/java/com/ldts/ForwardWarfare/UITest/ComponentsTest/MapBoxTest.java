package com.ldts.ForwardWarfare.UITest.ComponentsTest;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.Map.MapParseException;
import com.ldts.ForwardWarfare.UI.Component.Button;
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
        mapBox = new MapBox(TextColor.ANSI.CYAN,TextColor.ANSI.BLACK,new Position(0,0),new TerminalSize(17,12),0, "test/MapBoxtest.fw");
    }

    @Test
    public void MapBox_ConstructorTest(){
        Assertions.assertEquals(TextColor.ANSI.CYAN,mapBox.getBackColor());
        Assertions.assertEquals(TextColor.ANSI.BLACK,mapBox.getForgColor());
        Assertions.assertEquals(new Position(0,0),mapBox.getPosition());
        Assertions.assertEquals(new TerminalSize(10,10),mapBox.getSize());
        Assertions.assertEquals(0,mapBox.getBorderFadeIntencity());
    }

    @Test
    public void MapBox_drawpairlabletest(){
        mapBox.draw(textGraphicsMock);
        Mockito.verify(textGraphicsMock, Mockito.times(1)).setBackgroundColor(TextColor.ANSI.CYAN);
        Mockito.verify(textGraphicsMock, Mockito.times(1)).setForegroundColor(TextColor.ANSI.BLACK);
        Mockito.verify(textGraphicsMock, Mockito.times(1)).enableModifiers(Mockito.any());        Mockito.verify(textGraphicsMock, Mockito.times((8*8)+1)).putString(Mockito.any(),Mockito.anyString());


    }

    @Test
    public void MapBox_drawoddlableTest(){
        mapBox.draw(textGraphicsMock);
        Mockito.verify(textGraphicsMock, Mockito.times(2)).setBackgroundColor(Mockito.any());
        Mockito.verify(textGraphicsMock, Mockito.times(1)).setForegroundColor(Mockito.any());
        Mockito.verify(textGraphicsMock, Mockito.times(1)).enableModifiers(Mockito.any());
        Mockito.verify(textGraphicsMock, Mockito.times((8*8)+1)).putString(Mockito.any(),Mockito.anyString());
    }
    @Test
    public void MapBoxdraw_borderTest(){
        mapBox.draw(textGraphicsMock);
        Mockito.verify(textGraphicsMock, Mockito.times(2)).setBackgroundColor(Mockito.any());
        Mockito.verify(textGraphicsMock, Mockito.times(1)).setForegroundColor(Mockito.any());
        Mockito.verify(textGraphicsMock, Mockito.times(1)).enableModifiers(Mockito.any());
        Mockito.verify(textGraphicsMock, Mockito.times((8*8)+1)).putString(Mockito.any(),Mockito.anyString());
    }
}
