package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import com.googlecode.lanterna.terminal.Terminal;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Tile.Border;
import com.ldts.ForwardWarfare.Map.Map;

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
    public void run() throws IOException {
        screen = terminal.createScreen();

        int x = 10, y = 10;
        while (true) {
            KeyStroke key = screen.readInput();
            if (key.getKeyType() == KeyType.EOF)
                return;

            screen.clear();
            DrawTiles(screen.newTextGraphics(), new Border(new Position(x,y)));
            screen.refresh();
        }
    }

    private void DrawTiles(TextGraphics graphics, Border border) throws IOException {
        try {
            Map map = new Map("1.fw");

            for (Element element : map.getElements()) {
                if (element instanceof Playable)
                    element.draw(graphics, TextColor.ANSI.BLUE);
                else {
                    element.draw(graphics, null);
                    if (element.getPosition() == border.getPosition())
                        border.draw(graphics, null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
