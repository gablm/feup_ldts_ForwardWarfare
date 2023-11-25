package com.ldts.ForwardWarfare.UI;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.graphics.Theme;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class MainMenu {
    public static void main(String[] args) {
        try {
            // Set up terminal and screen
            Terminal terminal = new DefaultTerminalFactory().createTerminal();
            Screen screen = new TerminalScreen(terminal);
            screen.startScreen();

            // Set up window
            BasicWindow window = new BasicWindow();
            window.setHints(java.util.Arrays.asList(Window.Hint.CENTERED));
            window.setTheme(new SimpleTheme(TextColor.ANSI.RED,TextColor.ANSI.BLACK));

            // Create a panel to hold components
            Panel panel = new Panel();
            panel.setLayoutManager(new GridLayout(1));
            panel.setTheme(new SimpleTheme(TextColor.ANSI.RED,TextColor.ANSI.BLACK));

            // Add Start button
            Button startButton = new Button("Start");
            startButton.setTheme(new SimpleTheme(TextColor.ANSI.RED,TextColor.ANSI.BLACK));
            panel.addComponent(startButton);

            // Add Exit button
            Button exitButton = new Button("Exit", () -> {
                try {
                    screen.stopScreen();
                    System.exit(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            panel.addComponent(exitButton);

            // Add panel to window
            window.setComponent(panel);

            // Create GUI and set it as the GUI for the window
            MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLACK));
            gui.addWindowAndWait(window);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
