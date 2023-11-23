package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.screen.Screen;
import com.ldts.ForwardWarfare.LanternaTerminal.GameTerminal.GameTerminalFactory;
import com.ldts.ForwardWarfare.LanternaTerminal.LanternaTerminal;
import com.ldts.ForwardWarfare.LanternaTerminal.MenuTerminal.MenuTerminalFactory;

import java.io.IOException;
import java.awt.FontFormatException;
import java.util.ArrayList;
import java.util.List;
import java.net.URISyntaxException;

public class Game {
    public static void main(String[] args) {
        try {
            new Game().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private List<Screen> screens = new ArrayList<>();
    private List<LanternaTerminal> terminals = new ArrayList<>();
    public Game() {}

    public void run() throws IOException, URISyntaxException, FontFormatException {
        MenuTerminalFactory terminalFactory = new MenuTerminalFactory();
        terminalFactory.setDimensions(20, 20);
        terminalFactory.setFontSize(30);
        terminals.add(terminalFactory.createTerminal());

        GameTerminalFactory gameTerminalFactory = new GameTerminalFactory();
        gameTerminalFactory.setDimensions(40, 40);
        gameTerminalFactory.setFontSize(50);
        terminals.add(terminalFactory.createTerminal());

        terminals.get(0).createScreen();
        terminals.get(1).createScreen();
    }
}
