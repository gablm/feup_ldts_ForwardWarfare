package com.ldts.ForwardWarfare.UITest;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.ldts.ForwardWarfare.LanternaTerminal;
import com.ldts.ForwardWarfare.Map.MapParseException;
import com.ldts.ForwardWarfare.UI.Component.Button;
import com.ldts.ForwardWarfare.UI.Component.Component;
import com.ldts.ForwardWarfare.UI.MainMenu;
import com.ldts.ForwardWarfare.UI.UiStates;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.URISyntaxException;

public class MainMenuTest {
    @Mock
    private LanternaTerminal uiTerminalMock;

    @Mock
    private Screen screenMock;

    @Mock
    private TextGraphics textGraphicsMock;

    @Mock
    private Component componentMock;

    private MainMenu mainMenu;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mainMenu = Mockito.spy(new MainMenu());
        mainMenu.setUITerminal(uiTerminalMock);
        mainMenu.setScreen(screenMock);
        mainMenu.getListComponents().add(componentMock);
    }
    @Test
    public void testConstruct(){

        Assertions.assertEquals(new TerminalSize(41,23),mainMenu.getTerminalSize());
        Assertions.assertEquals(25,mainMenu.getFontsize());
    }
    @Test
    public void testBuild() throws IOException {
        UiStates expectedUiState = UiStates.HowToPlay;
        mainMenu.getListComponents().clear();
        mainMenu.getFs().add(componentMock);
        Mockito.doReturn(expectedUiState).when(mainMenu).run();
        Mockito.doNothing().when(mainMenu).addcomp();

        UiStates actualUiState = mainMenu.build();
        Assertions.assertEquals(mainMenu.getListComponents().get(0),componentMock);
        Mockito.verify(mainMenu).addcomp();
        Mockito.verify(mainMenu).run();
        Assertions.assertEquals(expectedUiState, actualUiState);
    }

    @Test
    public void testDraw_firstmenu() throws IOException {
        Mockito.when(screenMock.newTextGraphics()).thenReturn(textGraphicsMock);

        mainMenu.draw();

        Mockito.verify(screenMock).clear();
        Mockito.verify(textGraphicsMock).setBackgroundColor(Mockito.any());
        Mockito.verify(textGraphicsMock).enableModifiers(Mockito.any());
        Mockito.verify(textGraphicsMock).fillRectangle(Mockito.any(), Mockito.any(), Mockito.anyChar());
        Mockito.verify(componentMock).draw(textGraphicsMock);
        Mockito.verify(screenMock).refresh();
    }
    @Test
    public void testDraw_secondmenu() throws IOException {
        Mockito.when(screenMock.newTextGraphics()).thenReturn(textGraphicsMock);

        mainMenu.setFsb(false);
        mainMenu.draw();

        Mockito.verify(screenMock).clear();
        Mockito.verify(textGraphicsMock,Mockito.times(3)).setBackgroundColor(Mockito.any());
        Mockito.verify(textGraphicsMock,Mockito.times(2)).enableModifiers(Mockito.any());
        Mockito.verify(textGraphicsMock, Mockito.times(2)).fillRectangle(Mockito.any(), Mockito.any(), Mockito.anyChar());
        Mockito.verify(componentMock).draw(textGraphicsMock);
        Mockito.verify(screenMock).refresh();
    }

    @Test
    public void testAddcomp()  {
        mainMenu.addcomp();

        Assertions.assertEquals(3, mainMenu.getFs().size());
        Assertions.assertEquals(2, mainMenu.getSs().size());
    }
    @Test
    public void testRun() throws IOException {
        KeyStroke escapeKey = new KeyStroke(KeyType.Escape);

        Mockito.when(screenMock.readInput()).thenReturn(escapeKey);
        Mockito.doNothing().when(mainMenu).draw();

        UiStates result = mainMenu.run();

        Mockito.verify(mainMenu, Mockito.times(1)).draw();
        Mockito.verify(mainMenu, Mockito.times(1)).processKey(Mockito.any());
        Mockito.verify(screenMock, Mockito.times(1)).close();

        Assertions.assertEquals(UiStates.Exit, result);
    }

    @Test
    public void testProcessKey_Escape_1()
    {
        KeyStroke escapeKey = new KeyStroke(KeyType.Escape);

        mainMenu.processKey(escapeKey);

        Assertions.assertTrue(mainMenu.isEndscreen());
        Assertions.assertEquals(UiStates.Exit, mainMenu.getStartgame());
    }
    @Test
    public void testProcessKey_ArrowUp()
    {
        KeyStroke escapeKey  = new KeyStroke(KeyType.ArrowUp);

        mainMenu.processKey(escapeKey);

        Assertions.assertFalse(mainMenu.isEndscreen());
        Mockito.verify(componentMock, Mockito.times(2)).setBorderFadeIntensity(Mockito.anyInt());

    }
    @Test
    public void testProcessKey_ArrowLeft_first()
    {
        KeyStroke escapeKey  = new KeyStroke(KeyType.ArrowLeft);
        Component componentMock2 = Mockito.mock(Component.class);
        mainMenu.getListComponents().add(componentMock2);
        mainMenu.processKey(escapeKey);

        Assertions.assertFalse(mainMenu.isEndscreen());
        Mockito.verify(componentMock, Mockito.times(1)).setBorderFadeIntensity(Mockito.anyInt());
        Mockito.verify(componentMock2, Mockito.times(1)).setBorderFadeIntensity(Mockito.anyInt());
    }
    @Test
    public void testProcessKey_ArrowLeft_second()
    {
        int s=mainMenu.getCb();

        KeyStroke escapeKey  = new KeyStroke(KeyType.ArrowLeft);

        Component componentMock2 = Mockito.mock(Component.class);
        mainMenu.getListComponents().add(componentMock2);
        mainMenu.processKey(escapeKey);
        Assertions.assertTrue(s<mainMenu.getCb());
        s=mainMenu.getCb();
        mainMenu.processKey(escapeKey);
        Assertions.assertTrue(s>mainMenu.getCb());

        Assertions.assertFalse(mainMenu.isEndscreen());
        Mockito.verify(componentMock, Mockito.times(2)).setBorderFadeIntensity(Mockito.anyInt());
        Mockito.verify(componentMock2, Mockito.times(2)).setBorderFadeIntensity(Mockito.anyInt());

    }
    @Test
    public void testProcessKey_ArrowRight_first()
    {
        int s=mainMenu.getCb();
        KeyStroke escapeKey  = new KeyStroke(KeyType.ArrowRight);
        Component componentMock2 = Mockito.mock(Component.class);
        mainMenu.getListComponents().add(componentMock2);
        mainMenu.processKey(escapeKey);

        Assertions.assertFalse(mainMenu.isEndscreen());
        Mockito.verify(componentMock, Mockito.times(1)).setBorderFadeIntensity(Mockito.anyInt());
        Mockito.verify(componentMock2, Mockito.times(1)).setBorderFadeIntensity(Mockito.anyInt());
        Assertions.assertTrue(s<mainMenu.getCb());

    }
    @Test
    public void testProcessKey_ArrowRight_second()
    {
        KeyStroke escapeKey  = new KeyStroke(KeyType.ArrowRight);

        Component componentMock2 = Mockito.mock(Component.class);
        mainMenu.getListComponents().add(componentMock2);
        mainMenu.processKey(escapeKey);
        mainMenu.processKey(escapeKey);

        Assertions.assertFalse(mainMenu.isEndscreen());
        Mockito.verify(componentMock, Mockito.times(2)).setBorderFadeIntensity(Mockito.anyInt());
        Mockito.verify(componentMock2, Mockito.times(2)).setBorderFadeIntensity(Mockito.anyInt());
    }
    @Test
    public void testProcessKey_ArrowDown()
    {
        KeyStroke escapeKey  = new KeyStroke(KeyType.ArrowDown);

        mainMenu.processKey(escapeKey);

        Assertions.assertFalse(mainMenu.isEndscreen());
        Mockito.verify(componentMock, Mockito.times(2)).setBorderFadeIntensity(Mockito.anyInt());
        Assertions.assertEquals(0, mainMenu.getCb());
    }
    @Test
    public void testProcessKey_Escape_2()
    {
        KeyStroke escapeKey  = new KeyStroke(KeyType.Escape);
        mainMenu.setFsb(false);
        mainMenu.getFs().add(componentMock);
        Component componentMock2 = Mockito.mock(Component.class);
        mainMenu.getSs().add(componentMock2);
        mainMenu.setListComponents(mainMenu.getSs());
        mainMenu.processKey(escapeKey);

        Assertions.assertTrue(mainMenu.isFsb());
        Assertions.assertEquals(componentMock, mainMenu.getListComponents().get(0));
        Assertions.assertFalse(mainMenu.isEndscreen());
        Assertions.assertEquals(0, mainMenu.getCb());
    }
    @Test
    public void testProcessKey_Enter_StartGameButton()
    {
        Component componentMock2 = Mockito.mock(Component.class);
        mainMenu.getSs().add(componentMock2);
        KeyStroke escapeKey  = new KeyStroke(KeyType.Enter);

        mainMenu.processKey(escapeKey);
        Assertions.assertEquals(mainMenu.isFsb(), false);
        Assertions.assertEquals(componentMock2, mainMenu.getListComponents().get(0));
        Assertions.assertFalse(mainMenu.isEndscreen());
        Assertions.assertEquals(0,mainMenu.getCb());
    }
    @Test
    public void testProcessKey_Enter_HowToPlayGameButton()
    {
        KeyStroke escapeKey  = new KeyStroke(KeyType.Enter);
        mainMenu.setCb(1);

        mainMenu.processKey(escapeKey);

        Assertions.assertTrue(mainMenu.isEndscreen());
        Assertions.assertEquals(UiStates.HowToPlay, mainMenu.getStartgame());
    }
    @Test
    public void testProcessKey_Enter_ExitButton()
    {
        KeyStroke escapeKey  = new KeyStroke(KeyType.Enter);
        mainMenu.setCb(2);

        mainMenu.processKey(escapeKey);

        Assertions.assertTrue(mainMenu.isEndscreen());
        Assertions.assertEquals(UiStates.Exit, mainMenu.getStartgame());
    }
    @Test
    public void testProcessKey_Enter_PlayerVSPlayerButton()
    {
        KeyStroke escapeKey  = new KeyStroke(KeyType.Enter);
        mainMenu.setCb(0);
        mainMenu.setFsb(false);

        mainMenu.processKey(escapeKey);

        Assertions.assertTrue(mainMenu.isEndscreen());
        Assertions.assertEquals(UiStates.StartGameMenu, mainMenu.getStartgame());
        Assertions.assertEquals(true, mainMenu.getGameMode());
    }
    @Test
    public void testProcessKey_Enter_PlayerVSAIButton()
    {
        KeyStroke escapeKey  = new KeyStroke(KeyType.Enter);
        mainMenu.setCb(1);
        mainMenu.setFsb(false);

        mainMenu.processKey(escapeKey);

        Assertions.assertTrue(mainMenu.isEndscreen());
        Assertions.assertEquals(UiStates.StartGameMenu, mainMenu.getStartgame());
        Assertions.assertEquals(false, mainMenu.getGameMode());
    }
    @Test
    public void testButtonHighlighted()
    {
        KeyStroke escapeKey  = new KeyStroke(KeyType.ArrowRight);
        Component ButtonMock2 = Mockito.mock(Button.class);
        Component ButtonMock3 = Mockito.mock(Button.class);
        mainMenu.getListComponents().add(ButtonMock2);
        mainMenu.getListComponents().add(ButtonMock3);
        int s=mainMenu.getCb();
        mainMenu.processKey(escapeKey);
        Assertions.assertTrue(s<mainMenu.getCb());
        s=mainMenu.getCb();
        mainMenu.processKey(escapeKey);
        Assertions.assertTrue(s<mainMenu.getCb());
    }
}
