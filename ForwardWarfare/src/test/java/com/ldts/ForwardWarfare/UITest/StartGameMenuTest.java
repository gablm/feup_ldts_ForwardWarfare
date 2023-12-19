package com.ldts.ForwardWarfare.UITest;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.ldts.ForwardWarfare.LanternaTerminal;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.Map.MapParseException;
import com.ldts.ForwardWarfare.UI.Component.Button;
import com.ldts.ForwardWarfare.UI.Component.ColorGrid;
import com.ldts.ForwardWarfare.UI.Component.Component;
import com.ldts.ForwardWarfare.UI.Component.MapBox;
import com.ldts.ForwardWarfare.UI.StartGameMenu;
import com.ldts.ForwardWarfare.UI.UiStates;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class StartGameMenuTest {
    @Mock
    private LanternaTerminal uiTerminalMock;

    @Mock
    private Screen screenMock;

    @Mock
    private TextGraphics textGraphicsMock;

    @Mock
    private Component componentMock;
    @Mock
    private Button ButtonMock;
    @Mock
    private ColorGrid GridMock;

    private StartGameMenu startGameMenu;
    private StartGameMenu startGameMenu2;

    @BeforeEach
    public void setUp() throws IOException, URISyntaxException, FontFormatException {
        MockitoAnnotations.openMocks(this);
        startGameMenu = Mockito.spy(new StartGameMenu(true));
        startGameMenu2 = Mockito.spy(new StartGameMenu(false));
        startGameMenu.setUITerminal(uiTerminalMock);
        startGameMenu.setScreen(screenMock);
        startGameMenu.getListComponents().add(componentMock);
        startGameMenu.getButtonsList().add(ButtonMock);
        startGameMenu.setGrid(GridMock);
        startGameMenu2.setUITerminal(uiTerminalMock);
        startGameMenu2.setScreen(screenMock);
        startGameMenu2.getListComponents().add(componentMock);
        startGameMenu2.getButtonsList().add(ButtonMock);
        startGameMenu2.setGrid(GridMock);
    }
    @Test
    public void testConstruct(){
        Assertions.assertEquals(new TerminalSize(74,36),startGameMenu.getTerminalSize());
        Assertions.assertEquals(15, startGameMenu.getFontsize());
    }
    @Test
    public void testBuild() throws IOException, MapParseException, URISyntaxException {
        UiStates expectedUiState = UiStates.HowToPlay;
        Mockito.doReturn(expectedUiState).when(startGameMenu).run();
        Mockito.doNothing().when(startGameMenu).addcomp();

        UiStates actualUiState = startGameMenu.build();

        Mockito.verify(startGameMenu).addcomp();
        Mockito.verify(startGameMenu).run();
        Assertions.assertEquals(expectedUiState, actualUiState);
    }

    @Test
    public void testDraw() throws IOException {
        Mockito.when(screenMock.newTextGraphics()).thenReturn(textGraphicsMock);

        startGameMenu.draw();
        Mockito.verify(textGraphicsMock).putString(new TerminalPosition(32,10),"P2 Color");
        Mockito.verify(screenMock).clear();
        Mockito.verify(textGraphicsMock, Mockito.times(5)).setBackgroundColor(Mockito.any());
        Mockito.verify(textGraphicsMock, Mockito.times(3)).enableModifiers(Mockito.any());
        Mockito.verify(textGraphicsMock).fillRectangle(Mockito.any(), Mockito.any(), Mockito.anyChar());
        Mockito.verify(componentMock).draw(textGraphicsMock);
        Mockito.verify(ButtonMock).draw(textGraphicsMock);
        Mockito.verify(screenMock).refresh();
    }
    @Test
    public void testDrawAI() throws IOException {
        Mockito.when(screenMock.newTextGraphics()).thenReturn(textGraphicsMock);

        startGameMenu2.draw();
        Mockito.verify(textGraphicsMock).putString(new TerminalPosition(32,10),"AI Color");
    }
    @Test
    public void testRun() throws IOException {
        KeyStroke escapeKey = new KeyStroke(KeyType.Escape);

        Mockito.when(screenMock.readInput()).thenReturn(escapeKey);
        Mockito.doNothing().when(startGameMenu).draw();

        UiStates result = startGameMenu.run();

        Mockito.verify(startGameMenu, Mockito.times(1)).draw();
        Mockito.verify(startGameMenu, Mockito.times(1)).processKey(Mockito.any());
        Mockito.verify(screenMock, Mockito.times(1)).close();

        Assertions.assertEquals(UiStates.MainMenu, result);
    }
    @Test
    public void testAddcomp() throws IOException, MapParseException, URISyntaxException {
        startGameMenu.addcomp();

        Assertions.assertEquals(5+1,startGameMenu.getListComponents().size());
        Assertions.assertEquals(3+1,startGameMenu.getButtonsList().size());
    }
    @Test
    public void testProcessKey_SelectColor()
    {
        KeyStroke escapeKey  = new KeyStroke(KeyType.ArrowDown);
        startGameMenu.getListComponents().clear();
        startGameMenu.getListComponents().add(startGameMenu.getGrid());
        startGameMenu.setSelectingColor(true);

        Mockito.when(GridMock.processKey(Mockito.any())).thenReturn(true);
        startGameMenu.processKey(escapeKey);

        Assertions.assertFalse(startGameMenu.isEndscreen());
        Assertions.assertTrue(startGameMenu.getSelectingColor());
    }
    @Test
    public void testProcessKey_ArrowUp_selectingMap()
    {
        KeyStroke escapeKey  = new KeyStroke(KeyType.ArrowUp);
        startGameMenu.setSelectingMap(true);

        Component MapMock = Mockito.mock(Component.class);
        startGameMenu.getListComponents().add(MapMock);

        startGameMenu.processKey(escapeKey);

        Assertions.assertFalse(startGameMenu.isEndscreen());
        Mockito.verify(MapMock, Mockito.times(2)).setBorderFadeIntensity(Mockito.anyInt());
    }
    @Test
    public void testProcessKey_ArrowLeft_selectingMap()
    {
        KeyStroke escapeKey  = new KeyStroke(KeyType.ArrowLeft);
        startGameMenu.setSelectingMap(true);

        Component MapMock = Mockito.mock(Component.class);
        startGameMenu.getListComponents().add(MapMock);

        startGameMenu.processKey(escapeKey);

        Assertions.assertFalse(startGameMenu.isEndscreen());
        Mockito.verify(MapMock, Mockito.times(2)).setBorderFadeIntensity(Mockito.anyInt());
    }
    @Test
    public void testProcessKey_ArrowRight_selectingMap()
    {
        KeyStroke escapeKey  = new KeyStroke(KeyType.ArrowRight);
        startGameMenu.setSelectingMap(true);

        Component MapMock = Mockito.mock(Component.class);
        startGameMenu.getListComponents().add(MapMock);

        startGameMenu.processKey(escapeKey);

        Assertions.assertFalse(startGameMenu.isEndscreen());
        Mockito.verify(MapMock, Mockito.times(2)).setBorderFadeIntensity(Mockito.anyInt());
    }
    @Test
    public void testProcessKey_ArrowDown_selectingMap()
    {
        KeyStroke escapeKey  = new KeyStroke(KeyType.ArrowDown);
        startGameMenu.setSelectingMap(true);

        Component MapMock = Mockito.mock(Component.class);
        startGameMenu.getListComponents().add(MapMock);

        startGameMenu.processKey(escapeKey);

        Assertions.assertFalse(startGameMenu.isEndscreen());
        Mockito.verify(MapMock, Mockito.times(2)).setBorderFadeIntensity(Mockito.anyInt());
    }
    @Test
    public void testProcessKey_ArrowLeft_selectingMap_second()
    {
        KeyStroke escapeKey  = new KeyStroke(KeyType.ArrowLeft);
        startGameMenu.setSelectingMap(true);
        int s=startGameMenu.getMs();

        Component MapMock = Mockito.mock(Component.class);
        Component MapMock2 = Mockito.mock(Component.class);
        startGameMenu.getListComponents().add(MapMock);
        startGameMenu.getListComponents().add(MapMock2);

        startGameMenu.processKey(escapeKey);
        Assertions.assertTrue(s<startGameMenu.getMs());
        s=startGameMenu.getMs();
        startGameMenu.processKey(escapeKey);

        Assertions.assertFalse(startGameMenu.isEndscreen());
        Mockito.verify(MapMock, Mockito.times(2)).setBorderFadeIntensity(Mockito.anyInt());
        Mockito.verify(MapMock2, Mockito.times(2)).setBorderFadeIntensity(Mockito.anyInt());
    }
    @Test
    public void testProcessKey_ArrowRight_selectingMap_second()
    {
        KeyStroke escapeKey  = new KeyStroke(KeyType.ArrowRight);
        startGameMenu.setSelectingMap(true);

        Component MapMock = Mockito.mock(Component.class);
        Component MapMock2 = Mockito.mock(Component.class);
        startGameMenu.getListComponents().add(MapMock);
        startGameMenu.getListComponents().add(MapMock2);
        int s=startGameMenu.getMs();

        startGameMenu.processKey(escapeKey);
        Assertions.assertTrue(s<startGameMenu.getMs());

        startGameMenu.processKey(escapeKey);

        Assertions.assertFalse(startGameMenu.isEndscreen());
        Mockito.verify(MapMock, Mockito.times(2)).setBorderFadeIntensity(Mockito.anyInt());
        Mockito.verify(MapMock2, Mockito.times(2)).setBorderFadeIntensity(Mockito.anyInt());
    }
    @Test
    public void testProcessKey_ArrowLeft_second()
    {
        KeyStroke escapeKey  = new KeyStroke(KeyType.ArrowLeft);
        int s=startGameMenu.getBc();

        Component ButtonMock2 = Mockito.mock(Button.class);
        startGameMenu.getButtonsList().add(ButtonMock2);
        startGameMenu.processKey(escapeKey);
        Assertions.assertTrue(s<startGameMenu.getBc());
        s=startGameMenu.getBc();
        startGameMenu.processKey(escapeKey);
        Assertions.assertTrue(s>startGameMenu.getBc());

        Assertions.assertFalse(startGameMenu.isEndscreen());
        Mockito.verify(ButtonMock, Mockito.times(2)).setBorderFadeIntensity(Mockito.anyInt());
        Mockito.verify(ButtonMock, Mockito.times(2)).setBorderFadeIntensity(Mockito.anyInt());

    }
    @Test
    public void testProcessKey_ArrowRight_second()
    {
        KeyStroke escapeKey  = new KeyStroke(KeyType.ArrowRight);
        Component ButtonMock2 = Mockito.mock(Button.class);
        startGameMenu.getButtonsList().add(ButtonMock2);
        int s=startGameMenu.getBc();
        startGameMenu.processKey(escapeKey);
        Assertions.assertTrue(s<startGameMenu.getBc());
        startGameMenu.processKey(escapeKey);

        Assertions.assertFalse(startGameMenu.isEndscreen());
        Mockito.verify(ButtonMock, Mockito.times(2)).setBorderFadeIntensity(Mockito.anyInt());
        Mockito.verify(ButtonMock, Mockito.times(2)).setBorderFadeIntensity(Mockito.anyInt());

    }
    @Test
    public void testProcessKey_ArrowUp()
    {
        KeyStroke escapeKey  = new KeyStroke(KeyType.ArrowUp);
        startGameMenu.processKey(escapeKey);

        Assertions.assertFalse(startGameMenu.isEndscreen());
        Mockito.verify(ButtonMock, Mockito.times(2)).setBorderFadeIntensity(Mockito.anyInt());
    }
    @Test
    public void testProcessKey_ArrowLeft()
    {
        KeyStroke escapeKey  = new KeyStroke(KeyType.ArrowLeft);
        startGameMenu.processKey(escapeKey);

        Assertions.assertFalse(startGameMenu.isEndscreen());
        Mockito.verify(ButtonMock, Mockito.times(2)).setBorderFadeIntensity(Mockito.anyInt());
    }
    @Test
    public void testProcessKey_ArrowRight()
    {
        KeyStroke escapeKey  = new KeyStroke(KeyType.ArrowRight);
        startGameMenu.processKey(escapeKey);
        Assertions.assertFalse(startGameMenu.isEndscreen());
        Mockito.verify(ButtonMock, Mockito.times(2)).setBorderFadeIntensity(Mockito.anyInt());
    }
    @Test
    public void testProcessKey_ArrowDown()
    {
        KeyStroke escapeKey  = new KeyStroke(KeyType.ArrowDown);
        startGameMenu.processKey(escapeKey);

        Assertions.assertFalse(startGameMenu.isEndscreen());
        Mockito.verify(ButtonMock, Mockito.times(2)).setBorderFadeIntensity(Mockito.anyInt());
    }

    @Test
    public void testProcessKey_Enter_StartGameButton_nullcomp()
    {
        KeyStroke escapeKey  = new KeyStroke(KeyType.Enter);
        startGameMenu.setBc(0);
        startGameMenu.processKey(escapeKey);

        Assertions.assertFalse(startGameMenu.isEndscreen());
        Assertions.assertEquals(UiStates.BattleUI, startGameMenu.getStartgame());
    }
    @Test
    public void testProcessKey_Enter_StartGameButton_notnullcomp()
    {
        KeyStroke escapeKey  = new KeyStroke(KeyType.Enter);
        startGameMenu.setBc(0);
        startGameMenu.setSelectedMap(Mockito.mock(Map.class));
        Mockito.when(GridMock.getPlayer1Color()).thenReturn(TextColor.ANSI.BLUE);
        Mockito.when(GridMock.getPlayer2Color()).thenReturn(TextColor.ANSI.RED);
        startGameMenu.processKey(escapeKey);

        Assertions.assertTrue(startGameMenu.isEndscreen());
        Assertions.assertEquals(UiStates.BattleUI, startGameMenu.getStartgame());
    }
    @Test
    public void testProcessKey_Enter_SelectColorButton()
    {
        KeyStroke escapeKey  = new KeyStroke(KeyType.Enter);
        startGameMenu.setBc(1);
        startGameMenu.processKey(escapeKey);
        Mockito.verify(GridMock, Mockito.times(1)).restart();
        Mockito.verify(GridMock, Mockito.times(1)).start();
        Assertions.assertTrue(startGameMenu.getSelectingColor());

    }
    @Test
    public void testProcessKey_Enter_SelectMapButton()
    {
        KeyStroke escapeKey  = new KeyStroke(KeyType.Enter);
        startGameMenu.setBc(2);
        Component MapMock = Mockito.mock(Component.class);
        startGameMenu.getListComponents().add(MapMock);
        Assertions.assertEquals(startGameMenu.getMs(),1);
        startGameMenu.processKey(escapeKey);
        Assertions.assertTrue(startGameMenu.getSelectingMap());
        Mockito.verify(MapMock, Mockito.times(1)).setBorderFadeIntensity(Mockito.anyInt());
    }
    @Test
    public void testProcessKey_Enter_SetMap_selected()
    {
        KeyStroke escapeKey  = new KeyStroke(KeyType.Enter);
        startGameMenu.setSelectingMap(true);
        startGameMenu.setBc(2);
        Component MapMock = Mockito.mock(MapBox.class);
        startGameMenu.getListComponents().add(MapMock);

        startGameMenu.processKey(escapeKey);
        Assertions.assertFalse(startGameMenu.getSelectingMap());
        Mockito.verify(MapMock, Mockito.times(1)).setBorderFadeIntensity(Mockito.anyInt());
        Mockito.verify(MapMock, Mockito.times(1)).setFixBorder(Mockito.anyBoolean());
    }
    @Test
    public void testProcessKey_Enter_SetMap_unselected()
    {
        KeyStroke escapeKey  = new KeyStroke(KeyType.Enter);
        startGameMenu.setSelectingMap(true);
        startGameMenu.setBc(2);
        Component MapMock = Mockito.mock(MapBox.class);
        Component MapMock2 = Mockito.mock(MapBox.class);
        startGameMenu.getListComponents().add(MapMock);
        startGameMenu.getListComponents().add(MapMock2);

        startGameMenu.processKey(escapeKey);
        Assertions.assertFalse(startGameMenu.getSelectingMap());
        startGameMenu.setMs(2);
        startGameMenu.setSelectingMap(true);
        startGameMenu.processKey(escapeKey);
        Assertions.assertFalse(startGameMenu.getSelectingMap());
        Mockito.verify(MapMock, Mockito.times(2)).setBorderFadeIntensity(Mockito.anyInt());
        Mockito.verify(MapMock, Mockito.times(2)).setFixBorder(Mockito.anyBoolean());
        Mockito.verify(MapMock2, Mockito.times(1)).setBorderFadeIntensity(Mockito.anyInt());
        Mockito.verify(MapMock2, Mockito.times(1)).setFixBorder(Mockito.anyBoolean());
    }

    @Test
    public void testPlayerColors()
    {
        Mockito.when(GridMock.getPlayer1Color()).thenReturn(TextColor.ANSI.BLUE);
        Mockito.when(GridMock.getPlayer2Color()).thenReturn(TextColor.ANSI.RED);
        Assertions.assertEquals(TextColor.ANSI.BLUE,startGameMenu.selectColor1());
        Assertions.assertEquals(TextColor.ANSI.RED,startGameMenu.selectColor2());
    }

    @Test
    public void testSetMap()
    {
        Map map = Mockito.mock(Map.class);
        startGameMenu.setSelectedMap(map);
        Assertions.assertEquals(map,startGameMenu.getSelectedMap());
    }

    @Test
    public void testButtonHighlighted()
    {
        KeyStroke escapeKey  = new KeyStroke(KeyType.ArrowRight);
        Component ButtonMock2 = Mockito.mock(Button.class);
        Component ButtonMock3 = Mockito.mock(Button.class);
        startGameMenu.getButtonsList().add(ButtonMock2);
        startGameMenu.getButtonsList().add(ButtonMock3);
        int s=startGameMenu.getBc();
        startGameMenu.processKey(escapeKey);
        Assertions.assertTrue(s<startGameMenu.getBc());
        s=startGameMenu.getBc();
        startGameMenu.processKey(escapeKey);
        Assertions.assertTrue(s<startGameMenu.getBc());
    }
    @Test
    public void testButtonHighlighted_map()
    {
        KeyStroke escapeKey  = new KeyStroke(KeyType.ArrowRight);
        startGameMenu.setSelectingMap(true);

        Component MapMock = Mockito.mock(Component.class);
        Component MapMock2 = Mockito.mock(Component.class);
        Component MapMock3 = Mockito.mock(Component.class);
        startGameMenu.getListComponents().add(MapMock);
        startGameMenu.getListComponents().add(MapMock2);
        startGameMenu.getListComponents().add(MapMock3);
        int s=startGameMenu.getMs();
        startGameMenu.processKey(escapeKey);
        Assertions.assertTrue(s<startGameMenu.getMs());
        s=startGameMenu.getMs();
        startGameMenu.processKey(escapeKey);
        Assertions.assertTrue(s<startGameMenu.getMs());

    }

    @Test
    public void testSetMap_selected()
    {
        KeyStroke escapeKey  = new KeyStroke(KeyType.Enter);
        startGameMenu.setSelectingMap(true);
        Component MapMock = Mockito.mock(MapBox.class);
        Component MapMock2 = Mockito.mock(MapBox.class);
        Component MapMock3 = Mockito.mock(MapBox.class);
        startGameMenu.getListComponents().add(MapMock);
        startGameMenu.getListComponents().add(MapMock2);
        startGameMenu.getListComponents().add(MapMock3);

        startGameMenu.processKey(escapeKey);
        startGameMenu.setMs(2);
        startGameMenu.setSelectingMap(true);
        startGameMenu.processKey(escapeKey);


        Mockito.verify(MapMock, Mockito.times(2)).setBorderFadeIntensity(Mockito.anyInt());
        Mockito.verify(MapMock, Mockito.times(2)).setFixBorder(Mockito.anyBoolean());
        Mockito.verify(MapMock2, Mockito.times(1)).setBorderFadeIntensity(Mockito.anyInt());
        Mockito.verify(MapMock2, Mockito.times(1)).setFixBorder(Mockito.anyBoolean());
    }
}
