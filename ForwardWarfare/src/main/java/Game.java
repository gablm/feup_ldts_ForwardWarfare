import com.googlecode.lanterna.SGR;
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
        URL resource = getClass().getClassLoader().getResource("tanks.ttf");
        assert resource != null;
        File fontFile = new File(resource.toURI());
        Font font =  Font.createFont(Font.TRUETYPE_FONT, fontFile);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);

        return font.deriveFont(Font.PLAIN, 60);
    }
    public void run() throws IOException, URISyntaxException, FontFormatException {
        DefaultTerminalFactory factory = new DefaultTerminalFactory();

        AWTTerminalFontConfiguration fontConfig = AWTTerminalFontConfiguration.newInstance(LoadExternalFonts());
        factory.setTerminalEmulatorFontConfiguration(fontConfig);
        factory.setForceAWTOverSwing(true);
        factory.setInitialTerminalSize(new TerminalSize(11,11 ));

        Terminal terminal = factory.createTerminal();
        screen = new TerminalScreen(terminal);
        screen.setCursorPosition(null);
        screen.startScreen();
        screen.doResizeIfNecessary();

        DrawTiles(screen.newTextGraphics());
    }

    private void DrawTiles(TextGraphics graphics) throws IOException {
        /*boolean change = true;
        for (int j = 0; j < 160; j += 16) {
            change = !change;
            for (int i = 0; i < 256; i += 16) {
                graphics.setBackgroundColor(change ? TextColor.ANSI.CYAN : TextColor.ANSI.YELLOW);
                change = !change;
                graphics.drawRectangle(new TerminalPosition(i, j), new TerminalSize(16, 16), ' ');
            }
        }*/

        graphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        /*
        graphics.putString(new TerminalPosition(0,0), "Tanks $#>+ ");
        graphics.putString(new TerminalPosition(0,1), "Person (_@ ");
        graphics.putString(new TerminalPosition(0,2), "Plane %& ");
        graphics.putString(new TerminalPosition(0,3), "Helly /[ ");
        graphics.putString(new TerminalPosition(0,4), "Boat <)' ");
        graphics.putString(new TerminalPosition(0,5), "Submarine = ");
        graphics.putString(new TerminalPosition(0,6), "Tiles ~}{`]|;");
        graphics.putString(new TerminalPosition(0,7), "Borders ^* ");
        graphics.putString(new TerminalPosition(0,8), "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        graphics.putString(new TerminalPosition(0,9), "abcdefghijklmnop");
        graphics.putString(new TerminalPosition(0,10), ".,!?-:");
        */
        /*
        graphics.setBackgroundColor(TextColor.ANSI.BLUE);
        graphics.putString(new TerminalPosition(0,0), "~~~~~~~~~~~");
        graphics.putString(new TerminalPosition(0,1), "~");
        graphics.putString(new TerminalPosition(10,1), "~");
        graphics.putString(new TerminalPosition(0,2), "~");
        graphics.putString(new TerminalPosition(7,2), "~~~~");
        graphics.putString(new TerminalPosition(0,3), "~~~~");
        graphics.putString(new TerminalPosition(10,3), "~");
        graphics.putString(new TerminalPosition(0,4), "~");
        graphics.putString(new TerminalPosition(10,4), "~");
        graphics.putString(new TerminalPosition(0,5), "~");
        graphics.putString(new TerminalPosition(10,5), "~");
        graphics.putString(new TerminalPosition(0,6), "~");
        graphics.putString(new TerminalPosition(8,6), "~~~");
        graphics.putString(new TerminalPosition(0,7), "~~~");
        graphics.putString(new TerminalPosition(10,7), "~");
        graphics.putString(new TerminalPosition(0,8), "~");
        graphics.putString(new TerminalPosition(10,8), "~");
        graphics.putString(new TerminalPosition(0,9), "~");
        graphics.putString(new TerminalPosition(10,9), "~");
        graphics.putString(new TerminalPosition(0,10), "~~~~~~~~~~~");
        graphics.setForegroundColor(TextColor.ANSI.CYAN);
        graphics.putString(new TerminalPosition(4,3), ";");
        graphics.setForegroundColor(TextColor.ANSI.WHITE);
        graphics.setBackgroundColor(new TextColor.RGB(128,128,128));
        graphics.putString(new TerminalPosition(1,5), "}}}}");
        graphics.putString(new TerminalPosition(6,5), "}}}}");
        graphics.setBackgroundColor(TextColor.ANSI.GREEN);
        graphics.putString(new TerminalPosition(1,1), "|");
        graphics.putString(new TerminalPosition(4,1), "|||");
        graphics.putString(new TerminalPosition(8,1), "||");
        graphics.putString(new TerminalPosition(1,2), "||||||");
        graphics.putString(new TerminalPosition(5,3), "|||");
        graphics.putString(new TerminalPosition(9,3), "|");
        graphics.putString(new TerminalPosition(1,4), "||");
        graphics.putString(new TerminalPosition(4,4), "||||||");
        graphics.putString(new TerminalPosition(5,5), "|");
        graphics.putString(new TerminalPosition(1,6), "|");
        graphics.putString(new TerminalPosition(3,6), "|||||");
        graphics.putString(new TerminalPosition(3,7), "|||||||");
        graphics.putString(new TerminalPosition(1,8), "|||");
        graphics.putString(new TerminalPosition(5,8), "|||||");
        graphics.putString(new TerminalPosition(1,9), "|");
        graphics.putString(new TerminalPosition(3,9), "|||||");
        graphics.putString(new TerminalPosition(9,9), "|");
        graphics.setForegroundColor(TextColor.ANSI.BLACK);
        graphics.putString(new TerminalPosition(3,1), "#");
        graphics.putString(new TerminalPosition(2,1), ";");
        graphics.setForegroundColor(TextColor.ANSI.BLUE_BRIGHT);
        graphics.putString(new TerminalPosition(7,1), ";");
        graphics.setForegroundColor(TextColor.ANSI.MAGENTA);
        graphics.putString(new TerminalPosition(8,3), ";");
        graphics.setForegroundColor(TextColor.ANSI.YELLOW);
        graphics.putString(new TerminalPosition(3,4), ";");
        graphics.setForegroundColor(TextColor.ANSI.YELLOW);
        graphics.putString(new TerminalPosition(2,6), ";");
        graphics.setForegroundColor(TextColor.ANSI.MAGENTA);
        graphics.putString(new TerminalPosition(4,8), ";");
        graphics.setForegroundColor(TextColor.ANSI.BLUE_BRIGHT);
        graphics.putString(new TerminalPosition(2,9), ";");
        graphics.setForegroundColor(TextColor.ANSI.BLACK);
        graphics.putString(new TerminalPosition(8,9), ";");
        */

        screen.refresh();
    }
}
