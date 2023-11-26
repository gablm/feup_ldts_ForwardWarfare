package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Playable.Ground.AntiAirTank;
import com.ldts.ForwardWarfare.Element.Playable.Ground.HeavyTank;
import com.ldts.ForwardWarfare.Element.Playable.Ground.LightPerson;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Element.Position;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.ldts.ForwardWarfare.Element.Facility.*;
import com.ldts.ForwardWarfare.Element.Tile.*;
import com.ldts.ForwardWarfare.UI.MainMenu;
import com.ldts.ForwardWarfare.UI.StartGameMenu;
import com.ldts.ForwardWarfare.UI.UI;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private Screen screen;
    private LanternaTerminal terminal;

    public static void main(String[] args) {
        /*
        try {
            new Game(new LanternaTerminal(new TerminalSize(15,15), "tanks2_0.ttf", 50)).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        try {
            UI startgamemenu = new StartGameMenu();
            startgamemenu.build();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Game(LanternaTerminal terminal) {
        this.terminal = terminal;
    }
    public void run() throws IOException {
        screen = terminal.createScreen();
        screen.clear();
        DrawTiles(screen.newTextGraphics());
        screen.refresh();
    }

    private void DrawTiles(TextGraphics graphics) {
        List<Element> elementList = new ArrayList<>();

        elementList.add(new AntiAirTank(new Position(5, 5)));
        elementList.add(new LightPerson(new Position(12, 7)));
        elementList.add(new HeavyTank(new Position(3, 4)));
        /*boolean change = true;
        for (int j = 0; j < 160; j += 16) {
            change = !change;
            for (int i = 0; i < 256; i += 16) {
                graphics.setBackgroundColor(change ? TextColor.ANSI.CYAN : TextColor.ANSI.YELLOW);
                change = !change;
                graphics.drawRectangle(new TerminalPosition(i, j), new TerminalSize(16, 16), ' ');
            }
        }*/
        /*
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));

        graphics.putString(new TerminalPosition(0,0), "Tanks $#+ ");
        graphics.putString(new TerminalPosition(0,1), "Person (_@ ");
        graphics.putString(new TerminalPosition(0,2), "Plane %& ");
        graphics.putString(new TerminalPosition(0,3), "Helly [ ");
        graphics.putString(new TerminalPosition(0,4), "Boat <' ");
        graphics.putString(new TerminalPosition(0,5), "Submarine = ");
        graphics.putString(new TerminalPosition(0,6), "Tiles~}{]|");
        graphics.putString(new TerminalPosition(0,7), "Borders ^* ");
        graphics.putString(new TerminalPosition(0,8), "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        graphics.putString(new TerminalPosition(0,9), "abcdefghijklmnop");
        graphics.putString(new TerminalPosition(0,10),"Fac ;>`)/");
        //graphics.putString(new TerminalPosition(0,10), ".,!?-:");*/

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


        elementList.add(new Florest(new Position(0,0)));
        elementList.add(new Montain_land(new Position(0,1)));
        elementList.add(new Montain_Water(new Position(1,1)));
        elementList.add(new Water(new Position(1,0),null));
        elementList.add(new Water(new Position(2,0),null));
        elementList.add(new Fields(new Position(0,2),null));
        elementList.add(new Water(new Position(1,2),new Port()));
        elementList.add(new Water(new Position(1,3),new Oil_Pump()));
        elementList.add(new Fields(new Position(0,3),new Base(TextColor.ANSI.RED)));
        elementList.add(new Fields(new Position(0,4),new Base(TextColor.ANSI.BLUE)));
        elementList.add(new Fields(new Position(0,5),new Factory()));
        elementList.add(new Fields(new Position(0,6),new Airport()));
        elementList.add(new Fields(new Position(0,7),new Oil_Pump()));





        for (Element element : elementList) {
            if (element instanceof Playable)
                ((Playable)element).draw(graphics, TextColor.ANSI.BLUE);
            else
                element.draw(graphics,null);
        }
    }
}
