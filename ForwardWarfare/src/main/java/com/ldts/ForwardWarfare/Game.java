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
import com.ldts.ForwardWarfare.Map.Map;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

import static com.ldts.ForwardWarfare.LanternaTerminal.createScreen;

public class Game {
    private LanternaTerminal lanternaTerminal;
    private Terminal terminal;
    private Terminal uiTerminal;

    public static void main(String[] args) {
        try {
            new Game(new LanternaTerminal(new TerminalSize(15,10), "tanks2_0.ttf", 50)).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Game(LanternaTerminal terminal) throws IOException {
        this.lanternaTerminal = terminal;
    }
    public void run() throws IOException, URISyntaxException, FontFormatException {
        uiTerminal = new LanternaTerminal(new TerminalSize(20, 10), "square.ttf", 20).createTerminal();
        Screen uiScreen = createScreen(uiTerminal);

        KeyStroke key = uiScreen.readInput();
        if (key.getKeyType() == KeyType.EOF)
            return;
        terminal = lanternaTerminal.createTerminal();
        Screen screen = createScreen(terminal);
        screen.clear();
        DrawTiles(screen.newTextGraphics());
        screen.refresh();
    }

    private void DrawTiles(TextGraphics graphics) throws IOException {
        try {
            Map map = new Map("1.fw");

            for (Element element : map.getElements()) {
                if (element instanceof Playable)
                    element.draw(graphics, TextColor.ANSI.BLUE);
                else
                    element.draw(graphics, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
