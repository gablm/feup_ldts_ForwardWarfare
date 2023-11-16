package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class Game {

    private Screen screen;
    private LanternaTerminal terminal;
    public Game(LanternaTerminal terminal) throws IOException {
        this.terminal = terminal;
    }

    public void run() throws IOException {
        screen = terminal.createScreen();
        screen.clear();
        DrawTiles(screen.newTextGraphics());
        screen.refresh();
    }

    private void DrawTiles(TextGraphics graphics) {
        boolean change = true;
        for (int j = 0; j < 160; j += 16) {
            change = !change;
            for (int i = 0; i < 256; i += 16) {
                graphics.setBackgroundColor(change ? TextColor.ANSI.CYAN : TextColor.ANSI.YELLOW);
                change = !change;
                graphics.drawRectangle(new TerminalPosition(i, j), new TerminalSize(16, 16), ' ');
            }
        }
    }
}
