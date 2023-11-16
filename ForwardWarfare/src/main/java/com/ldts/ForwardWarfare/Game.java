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
    public Game() {}

    private Font LoadExternalFonts() throws IOException, FontFormatException, URISyntaxException {
        URL resource = getClass().getClassLoader().getResource("square.ttf");
        assert resource != null;
        File fontFile = new File(resource.toURI());
        Font font =  Font.createFont(Font.TRUETYPE_FONT, fontFile);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);

        return font.deriveFont(Font.PLAIN, 3);
    }
    public void run() throws IOException, URISyntaxException, FontFormatException {
        DefaultTerminalFactory factory = new DefaultTerminalFactory();

        AWTTerminalFontConfiguration fontConfig = AWTTerminalFontConfiguration.newInstance(LoadExternalFonts());
        factory.setTerminalEmulatorFontConfiguration(fontConfig);
        factory.setForceAWTOverSwing(true);
        factory.setInitialTerminalSize(new TerminalSize(256, 160 + 45));

        Terminal terminal = factory.createTerminal();
        screen = new TerminalScreen(terminal);
        screen.setCursorPosition(null);
        screen.startScreen();
        screen.doResizeIfNecessary();

        DrawTiles(screen.newTextGraphics());
    }

    private void DrawTiles(TextGraphics graphics) throws IOException {
        boolean change = true;
        for (int j = 0; j < 160; j += 16) {
            change = !change;
            for (int i = 0; i < 256; i += 16) {
                graphics.setBackgroundColor(change ? TextColor.ANSI.CYAN : TextColor.ANSI.YELLOW);
                change = !change;
                graphics.drawRectangle(new TerminalPosition(i, j), new TerminalSize(16, 16), ' ');
            }
        }
        screen.refresh();
    }
}
