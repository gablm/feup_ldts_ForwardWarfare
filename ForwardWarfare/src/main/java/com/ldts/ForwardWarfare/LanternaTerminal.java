package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.TerminalEmulatorAutoCloseTrigger;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
public class LanternaTerminal {
    private Terminal terminal;
    public LanternaTerminal(int width, int height) throws IOException, URISyntaxException, FontFormatException {
        Font font = loadExternalFonts();
        terminal = createTerminal(width, height, font);
    }
    private Terminal createTerminal(int width, int height, Font font) throws IOException {
        DefaultTerminalFactory factory = new DefaultTerminalFactory();

        AWTTerminalFontConfiguration fontConfig = AWTTerminalFontConfiguration.newInstance(font);
        factory.setTerminalEmulatorFontConfiguration(fontConfig);
        factory.setForceAWTOverSwing(true);
        factory.setInitialTerminalSize(new TerminalSize(width, height));
        factory.setTerminalEmulatorFrameAutoCloseTrigger(TerminalEmulatorAutoCloseTrigger.CloseOnExitPrivateMode);
        return factory.createTerminal();
    }
    private Font loadExternalFonts() throws IOException, FontFormatException, URISyntaxException {
        URL resource = getClass().getClassLoader().getResource("tanks2_0.ttf");
        assert resource != null;
        File fontFile = new File(resource.toURI());
        Font font =  Font.createFont(Font.TRUETYPE_FONT, fontFile);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);

        return font.deriveFont(Font.PLAIN, 50);
    }
    public Screen createScreen() throws IOException {
        Screen screen = new TerminalScreen(terminal);
        screen.setCursorPosition(null);
        screen.startScreen();
        screen.doResizeIfNecessary();
        return screen;
    }
}