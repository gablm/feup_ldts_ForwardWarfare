package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Controller.InvalidControllerException;
import com.ldts.ForwardWarfare.Controller.Player;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Element.Position;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.ldts.ForwardWarfare.Element.Facility.*;
import com.ldts.ForwardWarfare.Element.Tile.*;
import com.ldts.ForwardWarfare.UI.*;

import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.Map.MapParseException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Struct;

public class Game {
    private boolean running=true;
    private LanternaTerminal terminal;
    private Screen screen;
    private boolean state=false;

    public static void main(String[] args) throws IOException, MapParseException, URISyntaxException {
       Game game=new Game();
       game.run();
    }
    public Game() {

    }
    public void run() throws IOException, MapParseException, URISyntaxException {
        while (running) {
            running=false;
            UI UI = new MainMenu();
            state = UI.build();
            System.out.println(state);
            if (state) {
                UI = new StartGameMenu();
                state=UI.build();
                if (state) {
                    UI= new BattleUI();
                    state= UI.build();
                } else {
                    running=true;
                }
            }
            else
            {
                UI = new HowToPlayMenu();
                state=UI.build();
            }
        }
    }

}
