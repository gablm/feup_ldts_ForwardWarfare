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
    private Font font;
    private TerminalSize size;
    public LanternaTerminal(TerminalSize size, String fontPath, int fontSize) throws IOException, URISyntaxException, FontFormatException {
        Font font = loadExternalFonts(fontPath, fontSize);
        this.size = size; this.font = font;
    }
    public Terminal createTerminal() throws IOException {
        return createTerminal(size, font);
    };
    private Terminal createTerminal(TerminalSize size, Font font) throws IOException {
        DefaultTerminalFactory factory = new DefaultTerminalFactory();

        AWTTerminalFontConfiguration fontConfig = AWTTerminalFontConfiguration.newInstance(font);
        factory.setTerminalEmulatorFontConfiguration(fontConfig);
        factory.setForceAWTOverSwing(true);
        factory.setInitialTerminalSize(size);
        factory.setTerminalEmulatorFrameAutoCloseTrigger(TerminalEmulatorAutoCloseTrigger.CloseOnExitPrivateMode);
        return factory.createTerminal();
    }
    private Font loadExternalFonts(String fontPath, int fontSize) throws IOException, FontFormatException, URISyntaxException {
        URL resource = getClass().getClassLoader().getResource(fontPath);
        assert resource != null;
        File fontFile = new File(resource.toURI());
        Font font =  Font.createFont(Font.TRUETYPE_FONT, fontFile);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);

        return font.deriveFont(Font.PLAIN, fontSize);
    }
    public static Screen createScreen(Terminal terminal) throws IOException {
        Screen screen = new TerminalScreen(terminal);
        screen.setCursorPosition(null);
        screen.startScreen();
        screen.doResizeIfNecessary();
        return screen;
    }
}