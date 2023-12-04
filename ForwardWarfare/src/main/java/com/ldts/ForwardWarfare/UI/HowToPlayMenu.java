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
        graphics.setBackgroundColor(new TextColor.RGB(117,200,4));
        graphics.enableModifiers(SGR.BOLD);
        graphics.fillRectangle(new TerminalPosition(0,0),new TerminalSize(75,50),' ');
        for(Component comp :listComponents)
        {
            comp.draw(graphics);
        }
        screen.refresh();
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
}
