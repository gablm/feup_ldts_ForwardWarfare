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
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.UI.*;

import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.Map.MapParseException;
import com.ldts.ForwardWarfare.Element.Playable.Ground.AntiAirTank;
import com.ldts.ForwardWarfare.Element.Playable.Ground.HeavyPerson;
import com.ldts.ForwardWarfare.Element.Playable.Ground.HeavyTank;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.States.StartRoundState;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class Game {
    private boolean running = true;
    private UiStates state = UiStates.MainMenu;
    private boolean GameMode;

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
            switch (state) {
                case MainMenu:
                    UI mainmenu = new MainMenu();
                    state = mainmenu.build();
                    GameMode = mainmenu.getGameMode();
                    break;
                case StartGameMenu:
                    StartGameMenu startgamemenu = new StartGameMenu(GameMode);
                    state = startgamemenu.build();
                    map = startgamemenu.getSelectedMap();
                    color1 = startgamemenu.selectColor1();
                    color2 = startgamemenu.selectColor2();
                    break;
                case HowToPlay:
                    UI howtoplay = new HowToPlayMenu();
                    state = howtoplay.build();
                    break;
                case Exit:
                    running = false;
                    break;
                case BattleUI:
                    runGame();
                    state = UiStates.MainMenu;
                    break;
            }
        }
    }

    public void runGame() throws IOException, URISyntaxException, InvalidControllerException, FontFormatException {

        if (map == null || color1 == null || color2 == null)
            return;

        LanternaTerminal terminal = new LanternaTerminal(new TerminalSize(25,19), "tanks2_1.ttf", 40);
        Screen screen = terminal.createScreen();
        Controller p1 = new Player(map.getPlayer1(), color1, "P1");
        Controller p2 = GameMode ? new Player(map.getPlayer2(), color2, "P2") : new Bot(map.getPlayer2(), TextColor.ANSI.RED, "P2");
        Drawer drawer = new Drawer(p1, p2, map);

        State state = new StartRoundState(p1, p2, map);
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

}
