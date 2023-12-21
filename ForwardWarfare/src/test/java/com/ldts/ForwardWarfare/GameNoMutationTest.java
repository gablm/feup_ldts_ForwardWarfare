package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.screen.Screen;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Controller.InvalidControllerException;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.Map.MapParseException;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.UI.UiStates;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.Executable;
import java.net.URISyntaxException;

public class GameNoMutationTest {

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
    public void MainMenuTest() throws IOException, MapParseException, URISyntaxException, FontFormatException, InvalidControllerException, AWTException, InterruptedException {
        game.setUiState(UiStates.MainMenu);
        Assertions.assertEquals(UiStates.MainMenu, game.getUiState());

        System.setProperty("java.awt.headless", "false");
        Robot robot = new Robot();
        Thread thread = new Thread(() -> {
            robot.delay(500);
            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_DOWN);
            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_DOWN);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        });
        thread.start();

        game.run();

        thread.join();
        Assertions.assertEquals(UiStates.Exit, game.getUiState());
    }

    @Test
    public void StaticRunTest() throws AWTException {
        System.setProperty("java.awt.headless", "false");
        Robot robot = new Robot();
        Thread thread = new Thread(() -> {
            Game.main(new String[] {});
        });
        thread.start();

        robot.delay(500);
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    @Test
    public void StartMenuTest() throws AWTException, IOException, MapParseException, URISyntaxException, FontFormatException, InvalidControllerException, InterruptedException {
        game.setUiState(UiStates.StartGameMenu);

        System.setProperty("java.awt.headless", "false");
        Robot robot = new Robot();
        Thread thread = new Thread(() -> {
            robot.delay(500);
            robot.keyPress(KeyEvent.VK_ESCAPE);
            robot.keyRelease(KeyEvent.VK_ESCAPE);
            robot.delay(500);
            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_DOWN);
            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_DOWN);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        });
        thread.start();

        game.run();

        thread.join();
        Assertions.assertEquals(UiStates.Exit, game.getUiState());
    }

    @Test
    public void HowToPlayTest() throws AWTException, IOException, MapParseException, URISyntaxException, FontFormatException, InvalidControllerException, InterruptedException {
        game.setUiState(UiStates.HowToPlay);

        System.setProperty("java.awt.headless", "false");
        Robot robot = new Robot();
        Thread thread = new Thread(() -> {
            robot.delay(500);
            robot.keyPress(KeyEvent.VK_ESCAPE);
            robot.keyRelease(KeyEvent.VK_ESCAPE);
            robot.delay(500);
            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_DOWN);
            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_DOWN);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        });
        thread.start();

        game.run();

        thread.join();
        Assertions.assertEquals(UiStates.Exit, game.getUiState());
    }
}
