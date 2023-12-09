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
import com.ldts.ForwardWarfare.UI.Component.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

public class HowToPlayMenu extends UI{
    private UiStates startgame;
    private Position position;

    public HowToPlayMenu() {
        super(new TerminalSize(75,50),15);
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
        screen.refresh();
        int x;
        int y;
        TextGraphics textGraphics = graphics.putString(new TerminalPosition(position.getX(), position.getY()), "\"FowardWarfare is our recreation of the game called Advance Wars. It’s a series of turn-based strategy games developed by Intelligent Systems and published by Nintendo." +
                " The game was first released for the Game Boy Advance and features tactical battles between armies on a grid-based map. It offers challenging gameplay where players command various military units such as infantry, tanks, aircraft, and ships, each with unique abilities and characteristics. " +
                "The objective is to defeat the enemy army, capture bases, and control facilities to obtain resources and additional units. This said, it also allows us to build troops in different places, like airports, factories, ports for example. To obtain cash we have a base income, but also, every time we . On other side, if our opponent capture our base we lose the game.\"));\n");

    }

    @Override
    public void addcomp() throws FileNotFoundException, MapParseException, URISyntaxException {
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
        if(key.getKeyType()== KeyType.Escape)
        {
           endscreen=true;
           startgame=UiStates.MainMenu;
        }
    }

    public UiStates getStartgame() {
        return startgame;
    }
}
