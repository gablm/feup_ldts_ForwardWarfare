package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;

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
    }

    @Test
    public void QuitInputTest() throws IOException {
        Mockito.when(state.requiresInput()).thenReturn(true);
        Mockito.when(screen.readInput()).thenReturn(new KeyStroke(KeyType.Character));

        game.runGame();
        Mockito.verify(state).play(Action.QUIT);
    }
}
