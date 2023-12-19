package com.ldts.ForwardWarfare.UITest;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.ldts.ForwardWarfare.Element.Facility.Facility;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.LanternaTerminal;
import com.ldts.ForwardWarfare.Map.MapParseException;
import com.ldts.ForwardWarfare.UI.Component.Component;
import com.ldts.ForwardWarfare.UI.HowToPlayMenu;
import com.ldts.ForwardWarfare.UI.UI;
import com.ldts.ForwardWarfare.UI.UiStates;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

public class HowtoPlayMenuTest {
        @Mock
        private LanternaTerminal uiTerminalMock;

        @Mock
        private Screen screenMock;

        @Mock
        private TextGraphics textGraphicsMock;

        @Mock
        private Component componentMock;

        private HowToPlayMenu howToPlayMenu;

        @BeforeEach
        public void setUp() throws IOException, URISyntaxException, FontFormatException {
            MockitoAnnotations.openMocks(this);
            howToPlayMenu = Mockito.spy(new HowToPlayMenu());
            howToPlayMenu.setUITerminal(uiTerminalMock);
            howToPlayMenu.setScreen(screenMock);
            howToPlayMenu.getListComponents().add(componentMock);
        }
        @Test
        public void testConstruct(){
            Assertions.assertEquals(new TerminalSize(67,40), howToPlayMenu.getTerminalSize());
            Assertions.assertEquals(20, howToPlayMenu.getFontsize());
        }
        @Test
        public void testBuild() throws IOException, MapParseException, URISyntaxException {
            UiStates expectedUiState = UiStates.HowToPlay;
            Mockito.doReturn(expectedUiState).when(howToPlayMenu).run();
            Mockito.doNothing().when(howToPlayMenu).addcomp();

            UiStates actualUiState = howToPlayMenu.build();

            Mockito.verify(howToPlayMenu).addcomp();
            Mockito.verify(howToPlayMenu).run();
            Assertions.assertEquals(expectedUiState, actualUiState);
        }

        @Test
        public void testDraw() throws IOException {
            Mockito.when(screenMock.newTextGraphics()).thenReturn(textGraphicsMock);

            howToPlayMenu.draw();

            Mockito.verify(screenMock).clear();
            Mockito.verify(textGraphicsMock, Mockito.times(2)).setBackgroundColor(Mockito.any());
            Mockito.verify(textGraphicsMock, Mockito.times(4)).enableModifiers(Mockito.any());
            Mockito.verify(textGraphicsMock).fillRectangle(Mockito.any(), Mockito.any(), Mockito.anyChar());
            Mockito.verify(componentMock).draw(textGraphicsMock);
            Mockito.verify(screenMock).refresh();
        }

        @Test
        public void testRun() throws IOException {
            KeyStroke escapeKey = new KeyStroke(KeyType.Escape);

            Mockito.when(screenMock.readInput()).thenReturn(escapeKey);
            Mockito.doNothing().when(howToPlayMenu).draw();

            UiStates result = howToPlayMenu.run();

            Mockito.verify(howToPlayMenu, Mockito.times(1)).draw();
            Mockito.verify(howToPlayMenu, Mockito.times(1)).processKey(Mockito.any());
            Mockito.verify(screenMock, Mockito.times(1)).close();

            Assertions.assertEquals(UiStates.MainMenu, result);
        }

        @Test
        public void testProcessKey() {
            KeyStroke escapeKey = new KeyStroke(KeyType.Escape);

            howToPlayMenu.processKey(escapeKey);

            Assertions.assertTrue(howToPlayMenu.isEndscreen());
            Assertions.assertEquals(UiStates.MainMenu, howToPlayMenu.getStartgame());
        }

        @Test
        public void testAddcomp() throws FileNotFoundException, MapParseException, URISyntaxException {
            howToPlayMenu.addcomp();
            Assertions.assertEquals(2,howToPlayMenu.getListComponents().size());
        }
}
