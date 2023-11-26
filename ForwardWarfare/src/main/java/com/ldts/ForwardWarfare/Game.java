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

import java.io.IOException;

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
    public void run() throws IOException, MapParseException {
        screen = terminal.createScreen();
        Map map = new Map("1.fw");

        while (true) {
            screen.clear();
            DrawTiles(map, screen.newTextGraphics());
            screen.refresh();

            KeyStroke key = screen.readInput();
            if (key.getKeyType() == KeyType.EOF)
                return;
        }
    }

    private void DrawTiles(Map map, TextGraphics graphics) {
        try {
            for (Element element : map.getElements()) {
                if (element instanceof Playable)
                    element.draw(graphics, TextColor.ANSI.BLUE);
                else {
                    element.draw(graphics, null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
