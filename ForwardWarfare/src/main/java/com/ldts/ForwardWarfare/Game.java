package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Controller.InvalidControllerException;
import com.ldts.ForwardWarfare.Controller.Player;
import com.ldts.ForwardWarfare.Element.Playable.Ground.AntiAirTank;
import com.ldts.ForwardWarfare.Element.Playable.Ground.HeavyPerson;
import com.ldts.ForwardWarfare.Element.Playable.Ground.HeavyTank;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.Map.MapParseException;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.States.StartRoundState;

import java.io.IOException;
import java.net.URISyntaxException;

public class Game {
    private LanternaTerminal terminal;
    private Screen screen;

    public static void main(String[] args) {
        try {
            new Game(new LanternaTerminal(new TerminalSize(25,19), "tanks2_1.ttf", 40)).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Game(LanternaTerminal terminal) {
        this.terminal = terminal;
    }
    public void run() throws IOException, MapParseException, URISyntaxException, InvalidControllerException {
        screen = terminal.createScreen();
        Map map = new Map("1.fw");
        Controller p1 = new Player(map.getPlayer1(), TextColor.ANSI.BLUE, "P1");
        Controller p2 = new Player(map.getPlayer2(), TextColor.ANSI.RED, "P2");
        Drawer drawer = new Drawer(p1, p2, map);

        p1.buy(new AntiAirTank(new Position(2, 6)), 0);
        p2.buy(new AntiAirTank(new Position(3, 6)), 0);
        p2.buy(new AntiAirTank(new Position(3, 7)), 0);
        p2.buy(new AntiAirTank(new Position(3, 8)), 0);
        p2.buy(new HeavyPerson(new Position(4, 8)), 0);
        p1.buy(new HeavyTank(new Position(5, 6)), 0);

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
