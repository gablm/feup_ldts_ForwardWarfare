package com.ldts.ForwardWarfare.UITest;

import com.googlecode.lanterna.TerminalSize;
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

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        startGameMenu = Mockito.spy(new StartGameMenu(false));
        startGameMenu.setUITerminal(uiTerminalMock);
        startGameMenu.setScreen(screenMock);
        startGameMenu.getListComponents().add(componentMock);
        startGameMenu.getButtonsList().add(ButtonMock);
        startGameMenu.setGrid(GridMock);
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

        Mockito.verify(screenMock).clear();
        Mockito.verify(textGraphicsMock, Mockito.times(5)).setBackgroundColor(Mockito.any());
        Mockito.verify(textGraphicsMock, Mockito.times(3)).enableModifiers(Mockito.any());
        Mockito.verify(textGraphicsMock).fillRectangle(Mockito.any(), Mockito.any(), Mockito.anyChar());
        Mockito.verify(componentMock).draw(textGraphicsMock);
        Mockito.verify(screenMock).refresh();
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

        Component MapMock = Mockito.mock(Component.class);
        Component MapMock2 = Mockito.mock(Component.class);
        startGameMenu.getListComponents().add(MapMock);
        startGameMenu.getListComponents().add(MapMock2);

        startGameMenu.processKey(escapeKey);
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

        startGameMenu.processKey(escapeKey);
        startGameMenu.processKey(escapeKey);

        Assertions.assertFalse(startGameMenu.isEndscreen());
        Mockito.verify(MapMock, Mockito.times(2)).setBorderFadeIntensity(Mockito.anyInt());
        Mockito.verify(MapMock2, Mockito.times(2)).setBorderFadeIntensity(Mockito.anyInt());
    }
    @Test
    public void testProcessKey_ArrowLeft_second()
    {
        KeyStroke escapeKey  = new KeyStroke(KeyType.ArrowLeft);

        Component ButtonMock2 = Mockito.mock(Button.class);
        startGameMenu.getButtonsList().add(ButtonMock2);
        startGameMenu.processKey(escapeKey);
        startGameMenu.processKey(escapeKey);

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
        startGameMenu.processKey(escapeKey);
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

        Assertions.assertTrue(startGameMenu.getSelectingColor());

    }
    @Test
    public void testProcessKey_Enter_SelectMapButton()
    {
        KeyStroke escapeKey  = new KeyStroke(KeyType.Enter);
        startGameMenu.setBc(2);
        Component MapMock = Mockito.mock(Component.class);
        startGameMenu.getListComponents().add(MapMock);

        startGameMenu.processKey(escapeKey);
        Assertions.assertTrue(startGameMenu.getSelectingMap());
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
}
