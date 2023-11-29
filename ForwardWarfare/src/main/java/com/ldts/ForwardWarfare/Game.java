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
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.Map.MapParseException;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.States.NoSelectionState;

import java.io.IOException;
import java.net.URISyntaxException;

public class Game {
    private LanternaTerminal terminal;
    private Screen screen;

    public static void main(String[] args) {
        try {
            new Game(new LanternaTerminal(new TerminalSize(15,10), "tanks2_0.ttf", 50)).run();
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
        Controller p1 = new Player(map.getPlayer1(), TextColor.ANSI.RED_BRIGHT);
        Controller p2 = new Player(map.getPlayer2(), TextColor.ANSI.GREEN_BRIGHT);

        State state = new NoSelectionState(p1, p2, map);
        while (true) {
            screen.clear();
            TextGraphics graphics = screen.newTextGraphics();
            p1.draw(graphics);
            p2.draw(graphics);
            map.draw(graphics, null);
            p1.drawBorder(graphics);
            p2.drawBorder(graphics);
            screen.refresh();

            KeyStroke key = screen.readInput();
            if (key.getKeyType() == KeyType.EOF)
                return;
            state = state.play(keyToAction(key));
        }
    }

    private Action keyToAction(KeyStroke keyStroke) {
        switch (keyStroke.getKeyType()) {
            case Enter:
                return Action.ENTER;
            case ArrowUp:
                return Action.UP;
            case ArrowDown:
                return Action.DOWN;
            case ArrowLeft:
                return Action.LEFT;
            case ArrowRight:
                return Action.RIGHT;
        }
        return Action.ESCAPE;
    }
}
