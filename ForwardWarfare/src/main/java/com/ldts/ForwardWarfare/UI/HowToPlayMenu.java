package com.ldts.ForwardWarfare.UI;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Map.MapParseException;
import com.ldts.ForwardWarfare.UI.Component.Button;
import com.ldts.ForwardWarfare.UI.Component.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

public class HowToPlayMenu extends UI{
    private UiStates startgame;
    public HowToPlayMenu() {
        super(new TerminalSize(67,40),20);
    }

    @Override
    public UiStates build() throws IOException, MapParseException, URISyntaxException {
        screen = UITerminal.createScreen();
        addcomp();
        return run();
    }

    @Override
    public void draw() throws IOException {
        screen.clear();
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(new TextColor.RGB(117, 200, 4));
        graphics.enableModifiers(SGR.BOLD);
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(75, 50), ' ');
        for (Component comp : listComponents) {
            comp.draw(graphics);
        }
        graphics.setBackgroundColor(new TextColor.RGB(247,193,64));
        graphics.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(3, 3),"                         HOW TO PLAY" );
        graphics.disableModifiers(SGR.BOLD);
        graphics.setForegroundColor(new TextColor.RGB(0,0,0));
        graphics.putString(new TerminalPosition(3, 5), "        Forward Warfare is a Turn-based Strategy Game.");
        graphics.putString(new TerminalPosition(3, 7), " The objective is to Defeat the Enemy Army, Capture Bases and" );
        graphics.putString(new TerminalPosition(3, 8), "    Control Facilities to Obtain Coins and Additional Units." );
        graphics.putString(new TerminalPosition(3, 10)," After Moving a Unit Depending of Your Surroundings You Can:" );
        graphics.putString(new TerminalPosition(3, 12),"             - Capture Nearby Facilities - >;`/)." );
        graphics.putString(new TerminalPosition(3, 13),"             - Attack Nearby Enemy Units - <%($[." );
        graphics.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(3, 16),"                         FACILITIES" );
        graphics.disableModifiers(SGR.BOLD);
        graphics.setForegroundColor(new TextColor.RGB(0,0,0));
        graphics.putString(new TerminalPosition(3, 18),"   ; Bases - Capture Enemy Base to Win the Game Takes 2 Turns." );
        graphics.putString(new TerminalPosition(3, 19),"   ` Factories - You can Buy One Ground Unit Per Round." );
        graphics.putString(new TerminalPosition(3, 20),"   > Airports - You can Buy One Air Unit Per Round." );
        graphics.putString(new TerminalPosition(3, 21),"   ) Ports - You can Buy One Sea Unit Per Round." );
        graphics.putString(new TerminalPosition(3, 22),"   / OilPumps - Gives You Plus 10 Income Per Round." );
        graphics.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(3, 25),"                          MOVEMENT" );
        graphics.disableModifiers(SGR.BOLD);
        graphics.setForegroundColor(new TextColor.RGB(0,0,0));
        graphics.putString(new TerminalPosition(3, 27),"    ESC - Open the menu or goes back to the default section." );
        graphics.putString(new TerminalPosition(3, 28),"    ENTER - Confirms the action." );
        graphics.putString(new TerminalPosition(3, 29),"    ARROWS - Movement keys." );
        graphics.disableModifiers(SGR.BOLD);
        graphics.setForegroundColor(new TextColor.RGB(0,0,0));
        screen.refresh();
    }

    @Override
    public void addcomp() throws FileNotFoundException, MapParseException, URISyntaxException {
        listComponents.add(new Button(new TextColor.RGB(247,193,64),new TextColor.RGB(255,255,255),new Position(1,1),new TerminalSize(65,38),"",10));
    }

    @Override
    public UiStates run() throws IOException {
        while (!endscreen)
        {
            draw();
            KeyStroke key = screen.readInput();
            processKey(key);
        }
        screen.close();
        return startgame;    }

    @Override
    public void processKey(KeyStroke key) {
        if(key.getKeyType()==KeyType.Escape)
        {
           endscreen=true;
           startgame=UiStates.MainMenu;
        }
    }

    public UiStates getStartgame() {
        return startgame;
    }
}
