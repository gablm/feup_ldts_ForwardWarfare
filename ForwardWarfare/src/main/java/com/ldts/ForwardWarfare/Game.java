package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Controller.InvalidControllerException;
import com.ldts.ForwardWarfare.Controller.Player;
import com.ldts.ForwardWarfare.Element.Playable.PlayableFactory;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.Map.MapParseException;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.States.StartRoundState;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

public class Game {
    private LanternaTerminal terminal;
    private Screen screen;
    private int roundCount = 0;

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
        p1.buy(PlayableFactory.createAATank(2, 6), 0);
        p2.buy(PlayableFactory.createAATank(3, 6), 0);
        p2.buy(PlayableFactory.createAATank(3, 7), 0);
        p2.buy(PlayableFactory.createAATank(3, 8), 0);
        p2.buy(PlayableFactory.createHeavyPerson(4, 8), 0);
        p1.buy(PlayableFactory.createFighterPlane(5, 6), 0);

        State state = new StartRoundState(p1, p2, map);
        while (true) {
            screen.clear();
            TextGraphics graphics = screen.newTextGraphics();
            map.draw(graphics);
            p1.draw(graphics, map);
            p2.draw(graphics, map);
            state.draw(graphics);
            p1.drawBorder(graphics);
            p2.drawBorder(graphics);
            drawSide(graphics, p1, p2);
            screen.refresh();

            if (state.requiresInput()) {
                KeyStroke key = screen.readInput();
                if (key.getKeyType() == KeyType.EOF)
                    return;
                state = state.play(keyToAction(key));
            } else
                state = state.play(null);

            if (state instanceof StartRoundState)
                roundCount++;

            if (state == null) {
                screen.close();
                return;
            }
        }
    }

    private void ScreenWarning(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.ANSI.BLACK);
        graphics.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
        graphics.putString(1, 11, "WARNING:");
        graphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        graphics.putString(1, 12, "Use the other ");
        graphics.putString(1, 13, "window to move!");
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

    private void drawSide(TextGraphics graphics, Controller p1, Controller p2) {
        graphics.setBackgroundColor(TextColor.ANSI.YELLOW);
        graphics.fillRectangle(new TerminalPosition(15,0), new TerminalSize(10,19), ' ');

        graphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        graphics.putString(16, 2, "TURN");
        graphics.putString(16 + 6, 2, roundCount % 3 == 0 ? p1.getName() : p2.getName());
        graphics.putString(16, 4, "ROUND");
        String rounds = new StringBuilder().append(roundCount / 3 + 1).toString();
        graphics.putString(16 + 5 + 1, 4, rounds);

        graphics.putString(16, 8, "P1");
        String coins = new StringBuilder().append(p1.getCoins()).append("!").toString();
        graphics.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
        graphics.putString(16 + 8 - coins.length(), 8, coins);
        graphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        graphics.putString(16 + 2, 10, "BASE");
        graphics.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        graphics.putString(16 + 2, 11, "SAFE");

        graphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        graphics.putString(16, 13, "P2");
        coins = new StringBuilder().append(p2.getCoins()).append("!").toString();
        graphics.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
        graphics.putString(16 + 8 - coins.length(), 13, coins);
        graphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        graphics.putString(16 + 2, 15, "BASE");
        graphics.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
        graphics.putString(16, 16, "IN ATTACK");
    }
}
