package com.ldts.ForwardWarfare.UI;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.UI.Component.Button;
import com.ldts.ForwardWarfare.UI.Component.ColorGrid;
import com.ldts.ForwardWarfare.UI.Component.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StartGameMenu extends UI {
    ColorGrid grid=new ColorGrid(TextColor.ANSI.WHITE,TextColor.ANSI.WHITE,new Position(30,1),25);
    List<Component>ButtonsList=new ArrayList<>();

    public StartGameMenu() {
        super(new TerminalSize(60,40),15);
    }

    @Override
    public void build() throws IOException {
        screen = UITerminal.createScreen();
        addcomp();
        run();
    }

    @Override
    public void draw() throws IOException {
        screen.clear();
        TextGraphics graphics = screen.newTextGraphics();
        for (Component component:listComponents)
        {
            component.draw(graphics);
        }
        for (Component butao:ButtonsList)
        {
            butao.draw(graphics);
        }
        screen.refresh();
    }

    @Override
    public void addcomp() {
        listComponents.add(grid);
        ButtonsList.add(new Button(TextColor.ANSI.WHITE,TextColor.ANSI.WHITE,new Position(1,1),new TerminalSize(28,5),"START",25));
        ButtonsList.add(new Button(TextColor.ANSI.WHITE,TextColor.ANSI.WHITE,new Position(1,8),new TerminalSize(28,5),"COLOR SELECT",25));
        ButtonsList.add(new Button(TextColor.ANSI.WHITE,TextColor.ANSI.WHITE,new Position(1,15),new TerminalSize(28,5),"MAP SELECT",25));

    }

    @Override
    public void run() throws IOException {
        while (!endscreen)
        {
            draw();
            KeyStroke key = screen.readInput();
            processKey(key);
        }
        screen.close();
    }

    @Override
    public void processKey(KeyStroke key) {
        grid.processKey(key);
    }
}
