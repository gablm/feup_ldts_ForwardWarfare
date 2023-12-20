package com.ldts.ForwardWarfare.UITest.ComponentsTest;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.UI.Component.Button;
import com.ldts.ForwardWarfare.UI.Component.ColorGrid;
import com.ldts.ForwardWarfare.UI.Component.Component;
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
    public void testColors() {
        ColorGrid temp= new ColorGrid(TextColor.ANSI.CYAN, TextColor.ANSI.BLACK, new Position(0, 0), 0, true);
        List<Button> cores = temp.getColorList();
        Assertions.assertEquals(8, cores.size());
        Assertions.assertEquals(TextColor.ANSI.RED, cores.get(0).getBackColor());
        Assertions.assertEquals(TextColor.ANSI.BLUE, cores.get(1).getBackColor());
        Assertions.assertEquals(new TextColor.RGB(204, 0, 204), cores.get(2).getBackColor());
        Assertions.assertEquals(new TextColor.RGB(51, 25, 0), cores.get(3).getBackColor());
        Assertions.assertEquals(new TextColor.RGB(127, 0, 255), cores.get(4).getBackColor());
        Assertions.assertEquals(new TextColor.RGB(0, 153, 153), cores.get(5).getBackColor());
        Assertions.assertEquals(new TextColor.RGB(0, 51, 0), cores.get(6).getBackColor());
        Assertions.assertEquals(new TextColor.RGB(101, 73, 73), cores.get(7).getBackColor());

        Assertions.assertEquals(new Position(1, 5), cores.get(0).getPosition());
        Assertions.assertEquals(new Position(8, 5), cores.get(1).getPosition());
        Assertions.assertEquals(new Position(15, 5), cores.get(2).getPosition());
        Assertions.assertEquals(new Position(22, 5), cores.get(3).getPosition());
        Assertions.assertEquals(new Position(1, 12), cores.get(4).getPosition());
        Assertions.assertEquals(new Position(8, 12), cores.get(5).getPosition());
        Assertions.assertEquals(new Position(15, 12), cores.get(6).getPosition());
        Assertions.assertEquals(new Position(22, 12), cores.get(7).getPosition());

    }
    @Test
    public void testColorAI() {
        ColorGrid temp= new ColorGrid(TextColor.ANSI.CYAN, TextColor.ANSI.BLACK, new Position(0, 0), 0, false);
        List<Button> cores = temp.getColorList();
        Assertions.assertEquals(cores.get(0).getBorderFadeIntencity(),50);
        Assertions.assertEquals(cores.get(0).isFixBorder(),true);
    }
    @Test
    public void testProcessKeyArrowRight() {
        buttonList.add(buttonmock2);
        colorGridPlayer.setColorList(buttonList);
        KeyStroke keyStroke = new KeyStroke(KeyType.ArrowRight);
        boolean result = colorGridPlayer.processKey(keyStroke);
        Assertions.assertTrue(result);
        Mockito.verify(buttonmock, Mockito.times(1)).setBorderFadeIntensity(Mockito.anyInt());
    }

    @Test
    public void testProcessKeyArrowLeft() {
        buttonList.add(buttonmock2);
        colorGridPlayer.setColorList(buttonList);
        KeyStroke keyStroke = new KeyStroke(KeyType.ArrowLeft);
        boolean result = colorGridPlayer.processKey(keyStroke);
        Assertions.assertTrue(result);
        Mockito.verify(buttonmock, Mockito.times(1)).setBorderFadeIntensity(Mockito.anyInt());

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
        Mockito.verify(buttonmock, Mockito.times(1)).setBorderFadeIntensity(Mockito.anyInt());
        Mockito.verify(buttonmock2, Mockito.times(3)).setBorderFadeIntensity(Mockito.anyInt());
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
    public void testSetColor_alredyselected()
    {
        KeyStroke keyStroke = new KeyStroke(KeyType.Enter);
        Button buttonmock3 = Mockito.mock(Button.class);
        colorGridPlayer.setS(1);
        colorGridPlayer.setC(0);
        buttonList.add(buttonmock3);
        colorGridPlayer.processKey(keyStroke);
        colorGridPlayer.processKey(keyStroke);
        Mockito.verify(buttonmock2, Mockito.times(2)).setFixBorder(Mockito.anyBoolean());
        Mockito.verify(buttonmock2, Mockito.times(1)).getBackColor();
        Mockito.verify(buttonmock2, Mockito.times(4)).setBorderFadeIntensity(Mockito.anyInt());

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
        Mockito.verify(textGraphicsMock, Mockito.times(3)).putString(Mockito.any(),Mockito.anyString());

    }

    @Test void testsetupcolor()
    {
        ColorGrid temp = new ColorGrid(TextColor.ANSI.CYAN, TextColor.ANSI.BLACK, new Position(0, 0), 50, true);
        TextColor first= temp.getBorderColor();
        temp.draw(textGraphicsMock);
        Assertions.assertTrue(first.getBlue()>=temp.getBorderColor().getBlue());
        Assertions.assertTrue(first.getGreen()>=temp.getBorderColor().getGreen());
        Assertions.assertTrue(first.getRed()>=temp.getBorderColor().getRed());
    }
    @Test void  testsetupcolorusage()
    {
        ColorGrid temp = new ColorGrid(new TextColor.RGB(20,20,20),new TextColor.RGB(20,20,20), new Position(0, 0), 1, true);
        TextColor first= temp.getBorderColor();
        temp.draw(textGraphicsMock);
        Assertions.assertTrue(first.getBlue()>temp.getBorderColor().getBlue());
        Assertions.assertTrue(first.getGreen()>temp.getBorderColor().getGreen());
        Assertions.assertTrue(first.getRed()>temp.getBorderColor().getRed());
    }

    @Test
    public void testRestart_player() {
        colorGridPlayer.restart();
        Mockito.verify(buttonmock, Mockito.times(1)).setBorderFadeIntensity(Mockito.anyInt());
        Mockito.verify(buttonmock, Mockito.times(1)).setFixBorder(Mockito.anyBoolean());
        Assertions.assertTrue(colorGridPlayer.getButonused().isEmpty());
    }

    @Test
    public void testRestart_AI() {
        Button temp = new Button(TextColor.ANSI.RED, TextColor.ANSI.RED, new Position(0, 0), new TerminalSize(6, 6), " ", 50);
        colorGridAI.getColorList().add(temp);
        colorGridAI.getButonused().add(temp);
        colorGridAI.restart();
        Mockito.verify(buttonmock, Mockito.times(1)).setBorderFadeIntensity(Mockito.anyInt());
        Mockito.verify(buttonmock, Mockito.times(1)).setFixBorder(Mockito.anyBoolean());
        Assertions.assertEquals(colorGridAI.getButonused().get(0), temp);
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
    @Test
    public void testButtonHighlighted()
    {
        KeyStroke escapeKey  = new KeyStroke(KeyType.ArrowRight);
        Button buttonMock = Mockito.mock(Button.class);
        buttonList.add(buttonMock);
        int s=colorGridPlayer.getC();
        colorGridPlayer.processKey(escapeKey);
        Assertions.assertTrue(s<colorGridPlayer.getC());
        s=colorGridPlayer.getC();
        colorGridPlayer.processKey(escapeKey);
        Assertions.assertTrue(s<colorGridPlayer.getC());
    }

}
