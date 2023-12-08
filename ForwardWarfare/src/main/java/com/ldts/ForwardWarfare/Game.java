package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Controller.InvalidControllerException;
import com.ldts.ForwardWarfare.Controller.Player;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Element.Playable.PlayableFactory;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.Map.MapParseException;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.States.NoSelectionState;
import com.ldts.ForwardWarfare.State.States.StartRoundState;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class Game {
    private LanternaTerminal terminal;
    private Screen screen;

    public static void main(String[] args) {
        try {
            new Game(new LanternaTerminal(new TerminalSize(15,19), "tanks2_1.ttf", 40)).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Game(LanternaTerminal terminal) {
        this.terminal = terminal;
    }
    public void run() throws IOException, MapParseException, URISyntaxException, InvalidControllerException, FontFormatException {
        screen = terminal.createScreen();
        Map map = new Map("1.fw");
        Controller p1 = new Player(map.getPlayer1(), TextColor.ANSI.BLUE);
        Controller p2 = new Player(map.getPlayer2(), TextColor.ANSI.RED);
        p1.buy(PlayableFactory.createAATank(2, 6), 0);

        AudioManager audioManager = AudioManager.get();
        audioManager.play("termini.wav");

        State state = new StartRoundState(p1, p2, map);
        while (true) {
            screen.clear();
            TextGraphics graphics = screen.newTextGraphics();
            map.draw(graphics);
            p1.draw(graphics, map);
            p2.draw(graphics, map);
            p1.drawBorder(graphics);
            p2.drawBorder(graphics);
            state.draw(graphics);
            screen.refresh();

            if (state.requiresInput()) {
                KeyStroke key = screen.readInput();
                if (key.getKeyType() == KeyType.EOF)
                    return;
                state = state.play(keyToAction(key));
            } else
                state = state.play(null);

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
}
