package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import com.googlecode.lanterna.screen.Screen;
import com.ldts.ForwardWarfare.Controller.Bot;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Controller.InvalidControllerException;
import com.ldts.ForwardWarfare.Controller.Player;
import com.ldts.ForwardWarfare.UI.*;

import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.Map.MapParseException;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.States.StartRoundState;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class Game {
    private boolean running = true;
    private UiStates uiState = UiStates.MainMenu;
    private boolean gameMode;

    private Map map;
    private TextColor color1;
    private TextColor color2;

    public static void main(String[] args) {
        try {
            new Game().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Game() {}

    public void run() throws IOException, MapParseException, URISyntaxException, FontFormatException, InvalidControllerException {
        while (running) {
            switch (uiState) {
                case MainMenu:
                    UI mainMenu = new MainMenu();
                    uiState = mainMenu.build();
                    gameMode = mainMenu.getGameMode();
                    break;
                case StartGameMenu:
                    StartGameMenu startGameMenu = new StartGameMenu(gameMode);
                    uiState = startGameMenu.build();
                    map = startGameMenu.getSelectedMap();
                    color1 = startGameMenu.selectColor1();
                    color2 = startGameMenu.selectColor2();
                    break;
                case HowToPlay:
                    UI howToPlayMenu = new HowToPlayMenu();
                    uiState = howToPlayMenu.build();
                    break;
                case Exit:
                    running = false;
                    break;
                case BattleUI:

                    if (map == null || color1 == null || color2 == null)
                        return;

                    terminal = new LanternaTerminal(new TerminalSize(25,19), "tanks2_1.ttf", 40);
                    screen = terminal.createScreen();

                    p1 = new Player(map.getPlayer1(), color1, "P1");
                    p2 = gameMode ? new Player(map.getPlayer2(), color2, "P2")
                            : new Bot(map.getPlayer2(), TextColor.ANSI.RED, "P2");
                    drawer = new Drawer(p1, p2, map);
                    state = new StartRoundState(p1, p2, map);

                    runGame();
                    uiState = UiStates.MainMenu;
                    break;
            }
        }
    }

    private LanternaTerminal terminal;
    private Screen screen;
    private Controller p1;
    private Controller p2;
    private Drawer drawer;
    private State state;

    public void runGame() throws IOException {

        while (true) {
            screen.clear();
            drawer.draw(screen.newTextGraphics(), state);
            screen.refresh();

            if (state.requiresInput()) {
                KeyStroke key = screen.readInput();
                if (key.getKeyType() == KeyType.EOF)
                    return;
                state = state.play(keyToAction(key));
            } else
                state = state.play(null);

            if (state instanceof StartRoundState)
                drawer.increaseTurnCount();

            if (state == null) {
                screen.close();
                return;
            }
        }
    }

    private Action keyToAction(KeyStroke keyStroke) {
        return switch (keyStroke.getKeyType()) {
            case Enter -> Action.ENTER;
            case ArrowUp -> Action.UP;
            case ArrowDown -> Action.DOWN;
            case ArrowLeft -> Action.LEFT;
            case ArrowRight -> Action.RIGHT;
            case Escape -> Action.ESCAPE;
            case Character -> {
                if (keyStroke.getCharacter() == 'q')
                    yield Action.QUIT;
                yield Action.NONE;
            }
            default -> Action.NONE;
        };
    }

    public void setUiState(UiStates uiState) {
        this.uiState = uiState;
    }

    public void setGameMode(boolean gameMode) {
        this.gameMode = gameMode;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public void setP1(Controller p1) {
        this.p1 = p1;
    }

    public void setP2(Controller p2) {
        this.p2 = p2;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setDrawer(Drawer drawer) {
        this.drawer = drawer;
    }
}
