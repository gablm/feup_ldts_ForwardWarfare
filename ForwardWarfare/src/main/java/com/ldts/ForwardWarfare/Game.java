package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.screen.Screen;
import com.ldts.ForwardWarfare.LanternaTerminal.LanternaTerminal;
import com.ldts.ForwardWarfare.LanternaTerminal.MenuTerminal.MenuTerminalFactory;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class Game {
    public static void main(String[] args) {
        try {
            new Game().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private Screen screen;
    private LanternaTerminal terminal;
    public Game() {}

    public void run() throws IOException, URISyntaxException, FontFormatException {
        MenuTerminalFactory terminalFactory = new MenuTerminalFactory();
        terminalFactory.setDimensions(20, 20);
        terminalFactory.setFontSize(30);
        terminal = terminalFactory.createTerminal();
        screen = terminal.createScreen();
    }
}
