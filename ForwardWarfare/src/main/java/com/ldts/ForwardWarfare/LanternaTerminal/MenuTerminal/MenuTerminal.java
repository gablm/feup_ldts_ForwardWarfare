package com.ldts.ForwardWarfare.LanternaTerminal.MenuTerminal;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.TerminalEmulatorAutoCloseTrigger;
import com.ldts.ForwardWarfare.LanternaTerminal.LanternaTerminal;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class MenuTerminal implements LanternaTerminal {
    private Terminal terminal;
    public MenuTerminal(TerminalSize size, int fontSize) throws IOException, URISyntaxException, FontFormatException {
        Font font = loadExternalFonts(fontSize, "square.ttf");
        terminal = createTerminal(size, font);
    }
    private Terminal createTerminal(TerminalSize size, Font font) throws IOException {
        DefaultTerminalFactory factory = new DefaultTerminalFactory();
        AWTTerminalFontConfiguration fontConfig = AWTTerminalFontConfiguration.newInstance(font);
        factory.setTerminalEmulatorFontConfiguration(fontConfig);
        factory.setForceAWTOverSwing(true);
        factory.setInitialTerminalSize(size);
        factory.setTerminalEmulatorFrameAutoCloseTrigger(TerminalEmulatorAutoCloseTrigger.CloseOnExitPrivateMode);
        return factory.createTerminal();
    }
    private Font loadExternalFonts(int fontSize, String fontPath) throws IOException, FontFormatException, URISyntaxException {
        URL resource = getClass().getClassLoader().getResource(fontPath);
        assert resource != null;
        File fontFile = new File(resource.toURI());
        Font font =  Font.createFont(Font.TRUETYPE_FONT, fontFile);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);
        return font.deriveFont(Font.PLAIN, 30);
    }
    public Screen createScreen() throws IOException {
        Screen screen = new TerminalScreen(terminal);
        screen.setCursorPosition(null);
        screen.startScreen();
        screen.doResizeIfNecessary();
        return screen;
    }
}
