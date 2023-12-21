package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.groupcdg.pitest.annotations.DoNotMutate;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Controller.InvalidControllerException;
import com.ldts.ForwardWarfare.Controller.Player;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.Map.MapParseException;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.States.StartRoundState;
import com.ldts.ForwardWarfare.UI.UiStates;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class GameTest {
    private Map map;
    private Controller p1;
    private Controller p2;
    private Screen screen;
    private State state;
    private Drawer drawer;

    private Game game;

    @BeforeEach
    public void ResetMocks() {
        p1 = Mockito.mock(Controller.class);
        p2 = Mockito.mock(Controller.class);
        state = Mockito.mock(State.class);
        screen = Mockito.mock(Screen.class);
        map = Mockito.mock(Map.class);
        drawer = Mockito.mock(Drawer.class);

        game = new Game();
        game.setP1(p1);
        game.setP2(p2);
        game.setMap(map);
        game.setState(state);
        game.setScreen(screen);
        game.setDrawer(drawer);
    }

    @Test
    public void RunTest() throws IOException {
        game.runGame();

        Mockito.verify(screen).clear();
        Mockito.verify(drawer).draw(screen.newTextGraphics(), state);
        Mockito.verify(screen).refresh();
        Mockito.verify(state).play(Mockito.any());
        Mockito.verify(screen).close();
    }

    @Test
    public void RunWithInputTest() throws IOException {
        Mockito.when(state.requiresInput()).thenReturn(true);
        Mockito.when(screen.readInput()).thenReturn(new KeyStroke(KeyType.EOF));

        game.runGame();
        Mockito.verify(state, Mockito.never()).play(Mockito.any());
    }

    @ParameterizedTest
    @CsvSource({"Escape,ESCAPE", "ArrowUp,UP", "ArrowDown,DOWN", "ArrowLeft,LEFT",
            "ArrowRight,RIGHT", "Enter,ENTER", "Backspace,NONE"})
    public void InputTest(KeyType type, Action action) throws IOException {
        Mockito.when(state.requiresInput()).thenReturn(true);
        Mockito.when(screen.readInput()).thenReturn(new KeyStroke(type));

        game.runGame();
        Mockito.verify(state).play(action);
        Mockito.verify(drawer, Mockito.never()).increaseTurnCount();
    }

    @Test
    public void QuitInputTest() throws IOException {
        Mockito.when(state.requiresInput()).thenReturn(true);
        Mockito.when(screen.readInput())
                .thenReturn(new KeyStroke('q', false, false));

        game.runGame();
        Mockito.verify(state).play(Action.QUIT);
    }

    @Test
    public void DrawerIncreaseTest() throws IOException {
        Mockito.when(state.play(Mockito.any())).thenReturn(
                Mockito.mock(StartRoundState.class));

        game.runGame();

        Mockito.verify(drawer).increaseTurnCount();
    }

    @Test
    public void CharInvalidInputTest() throws IOException {
        Mockito.when(state.requiresInput()).thenReturn(true);
        Mockito.when(screen.readInput())
                .thenReturn(new KeyStroke('c', false, false));

        game.runGame();
        Mockito.verify(state).play(Action.NONE);
    }

    @Test
    public void ExitStateTest() throws IOException, MapParseException, URISyntaxException, FontFormatException, InvalidControllerException {
        game.setUiState(UiStates.Exit);
        Assertions.assertTrue(game.isRunning());

        game.run();
        Assertions.assertFalse(game.isRunning());
    }

    @Test
    public void GetUiStateTest() {
        game.setUiState(UiStates.BattleUI);
        Assertions.assertEquals(UiStates.BattleUI, game.getUiState());

        game.setUiState(UiStates.HowToPlay);
        Assertions.assertEquals(UiStates.HowToPlay, game.getUiState());
    }
}
