package com.ldts.ForwardWarfare.UITest.ComponentsTest;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.UI.Component.Button;
import com.ldts.ForwardWarfare.UI.Component.ColorGrid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class ColorGridTest {

    @Mock
    private TextGraphics textGraphicsMock;
    @Mock
    private Button buttonmock;
    @Mock
    private Button buttonmock2;
    @Mock
    private Button buttonmock3;
    private List<Button> buttonList = new ArrayList<>();
    private ColorGrid colorGridPlayer;
    private ColorGrid colorGridAI;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        colorGridPlayer =Mockito.spy(new ColorGrid(TextColor.ANSI.CYAN, TextColor.ANSI.BLACK, new Position(0, 0), 0, true));
        colorGridAI = Mockito.spy(new ColorGrid(TextColor.ANSI.CYAN, TextColor.ANSI.BLACK, new Position(0, 0), 300, false));
        buttonList.add(buttonmock);
        colorGridPlayer.setColorList(buttonList);
        buttonList.add(buttonmock2);
        colorGridAI.setColorList(buttonList);
    }

    @Test
    public void testProcessKeyArrowRight() {
        buttonList.add(buttonmock2);
        colorGridPlayer.setColorList(buttonList);
        KeyStroke keyStroke = new KeyStroke(KeyType.ArrowRight);
        boolean result = colorGridPlayer.processKey(keyStroke);
        Assertions.assertTrue(result);
    }

    @Test
    public void testProcessKeyArrowLeft() {
        buttonList.add(buttonmock2);
        colorGridPlayer.setColorList(buttonList);
        KeyStroke keyStroke = new KeyStroke(KeyType.ArrowLeft);
        boolean result = colorGridPlayer.processKey(keyStroke);
        Assertions.assertTrue(result);
    }
    @Test
    public void testProcessKeyArrowLeftrepeted() {
        buttonList.add(buttonmock2);
        colorGridPlayer.setColorList(buttonList);
        KeyStroke keyStroke = new KeyStroke(KeyType.ArrowLeft);
        boolean result = colorGridPlayer.processKey(keyStroke);
        Assertions.assertTrue(result);
        boolean result2 = colorGridPlayer.processKey(keyStroke);
        Assertions.assertTrue(result2);
    }

    @Test
    public void testProcessKeyEnter() {
        KeyStroke keyStroke = new KeyStroke(KeyType.Enter);
        boolean result = colorGridPlayer.processKey(keyStroke);
        Assertions.assertTrue(result);
        boolean result2 = colorGridAI.processKey(keyStroke);
        Assertions.assertTrue(result2);
    }
    @Test
    public void testProcessKeyEnterrepeted() {
        buttonList.add(buttonmock3);
        colorGridPlayer.setColorList(buttonList);
        KeyStroke keyStroke = new KeyStroke(KeyType.Enter);
        boolean result = colorGridPlayer.processKey(keyStroke);
        colorGridPlayer.setPlayer1Color(TextColor.ANSI.BLACK);
        Assertions.assertTrue(result);
        boolean result2 = colorGridPlayer.processKey(keyStroke);
        colorGridPlayer.setPlayer2Color(TextColor.ANSI.CYAN);
        Assertions.assertTrue(result2);
        boolean result3 = colorGridPlayer.processKey(keyStroke);
        Assertions.assertFalse(result3);
    }
    @Test
    public void testProcessKeyEnterEmptyUsesList() {
        KeyStroke keyStroke = new KeyStroke(KeyType.Enter);
        boolean result = colorGridPlayer.processKey(keyStroke);
        colorGridPlayer.setPlayer1Color(TextColor.ANSI.BLACK);
        colorGridPlayer.setButonused(new ArrayList<>());
        Assertions.assertTrue(result);
        boolean result2 = colorGridPlayer.processKey(keyStroke);
        colorGridPlayer.setPlayer2Color(TextColor.ANSI.CYAN);
        Assertions.assertTrue(result2);
    }

    @Test
    public void testProcessKeyOtherKey() {
        KeyStroke keyStroke = new KeyStroke(KeyType.Escape);
        boolean result = colorGridPlayer.processKey(keyStroke);
        Assertions.assertTrue(result);
        Mockito.verifyNoInteractions(buttonmock);
    }

    @Test
    public void testSetColor() {
        KeyStroke keyStroke = new KeyStroke(KeyType.Enter);
        colorGridPlayer.processKey(keyStroke);
        Mockito.verify(buttonmock, Mockito.times(1)).setFixBorder(Mockito.anyBoolean());
        Mockito.verify(buttonmock, Mockito.times(1)).getBackColor();
        Mockito.verify(buttonmock, Mockito.times(2)).setBorderFadeIntensity(Mockito.anyInt());
    }

    @Test
    public void testStart() {
        colorGridPlayer.start();
        Mockito.verify(buttonmock, Mockito.times(1)).setBorderFadeIntensity(Mockito.anyInt());
    }

    @Test
    public void testDraw_notusedButtons() {
        colorGridPlayer.draw(textGraphicsMock);
        colorGridAI.draw(textGraphicsMock);
        Mockito.verify(buttonmock, Mockito.times(2)).draw(textGraphicsMock);
        Mockito.verify(textGraphicsMock, Mockito.times(8)).setBackgroundColor(Mockito.any());
        Mockito.verify(textGraphicsMock, Mockito.times(3)).setForegroundColor(Mockito.any());
        Mockito.verify(textGraphicsMock, Mockito.times(5)).enableModifiers(Mockito.any());
        Mockito.verify(textGraphicsMock, Mockito.times(181)).putString(Mockito.any(),Mockito.anyString());
    }

    @Test
    public void testRestart_player() {
        colorGridPlayer.restart();
        Mockito.verify(buttonmock, Mockito.times(1)).setBorderFadeIntensity(Mockito.anyInt());
        Mockito.verify(buttonmock, Mockito.times(1)).setFixBorder(Mockito.anyBoolean());
    }

    @Test
    public void testRestart_AI() {
        colorGridAI.restart();
        Mockito.verify(buttonmock, Mockito.times(1)).setBorderFadeIntensity(Mockito.anyInt());
        Mockito.verify(buttonmock, Mockito.times(1)).setFixBorder(Mockito.anyBoolean());
    }

    @Test
    public void testSetPlayerColors() {
        TextColor color1 = TextColor.ANSI.RED;
        TextColor color2 = TextColor.ANSI.BLUE;

        colorGridPlayer.setPlayer1Color(color1);
        colorGridPlayer.setPlayer2Color(color2);

        Assertions.assertEquals(color1, colorGridPlayer.getPlayer1Color());
        Assertions.assertEquals(color2, colorGridPlayer.getPlayer2Color());
    }

    @Test
    public void testGetters() {
        Assertions.assertTrue(colorGridPlayer.isGamemode());
        Assertions.assertFalse(colorGridAI.isGamemode());
        Assertions.assertEquals(0, colorGridPlayer.getBorderFadeIntencity());
        Assertions.assertEquals(300, colorGridAI.getBorderFadeIntencity());
    }

}
