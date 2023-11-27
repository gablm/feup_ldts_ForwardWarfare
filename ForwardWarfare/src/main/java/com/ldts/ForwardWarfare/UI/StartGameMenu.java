package com.ldts.ForwardWarfare.UI;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.UI.Component.Button;
import com.ldts.ForwardWarfare.UI.Component.ColorGrid;
import com.ldts.ForwardWarfare.UI.Component.Component;
import com.ldts.ForwardWarfare.UI.Component.MapBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StartGameMenu extends UI {
    private boolean SelectingColor=false;
    private int highlight=75;
    private int normalborder=10;
    private int bc=0;
    ColorGrid grid=new ColorGrid(new TextColor.RGB(247,193,64),new TextColor.RGB(255,255,255),new Position(44,1),25);
    List<Component>ButtonsList=new ArrayList<>();

    public StartGameMenu() {
        super(new TerminalSize(74,35),15);
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
        graphics.setBackgroundColor(new TextColor.RGB(117,200,4));
        graphics.enableModifiers(SGR.BOLD);
        graphics.fillRectangle(new TerminalPosition(0,0),new TerminalSize(74,35),' ');
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
        ButtonsList.add(new Button(new TextColor.RGB(247,193,64),new TextColor.RGB(255,255,255),new Position(1,1),new TerminalSize(42,5),"START",highlight));
        ButtonsList.add(new Button(new TextColor.RGB(247,193,64),new TextColor.RGB(255,255,255),new Position(1,8),new TerminalSize(42,5),"COLOR SELECT",normalborder));
        ButtonsList.add(new Button(new TextColor.RGB(247,193,64),new TextColor.RGB(255,255,255),new Position(1,15),new TerminalSize(42,5),"MAP SELECT",normalborder));
        listComponents.add(new MapBox(new TextColor.RGB(247,193,64),new TextColor.RGB(255,255,255),new Position(1,23),new TerminalSize(17,10),normalborder));
        listComponents.add(new MapBox(new TextColor.RGB(247,193,64),new TextColor.RGB(255,255,255),new Position(19,23),new TerminalSize(17,10),normalborder));
        listComponents.add(new MapBox(new TextColor.RGB(247,193,64),new TextColor.RGB(255,255,255),new Position(37,23),new TerminalSize(17,10),normalborder));
        listComponents.add(new MapBox(new TextColor.RGB(247,193,64),new TextColor.RGB(255,255,255),new Position(55,23),new TerminalSize(17,10),normalborder));
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
    private void processButton()
    {
        switch (bc)
        {
            case 0:

                break;
            case 1:
                SelectingColor = true;
                grid.start();
                break;
            case 2:

                break;
        }
    }
    private void Buttonhighlighted(boolean next)
    {
        if(next) {
            ButtonsList.get(bc).setBorderFadeIntensity(normalborder);
            if (bc+1 > ButtonsList.size() - 1) {
                bc = 0;
            } else
                bc++;
            ButtonsList.get(bc).setBorderFadeIntensity(highlight);
        }
        else
        {
            ButtonsList.get(bc).setBorderFadeIntensity(normalborder);
            if (bc-1 < 0) {
                bc = ButtonsList.size()-1;
            } else
                bc--;
            ButtonsList.get(bc).setBorderFadeIntensity(highlight);
        }
    }
    
    @Override
    public void processKey(KeyStroke key) {
        if (SelectingColor) {
            SelectingColor=grid.processKey(key);
        }else {
            if (key.getKeyType() == KeyType.ArrowUp)
            {
                Buttonhighlighted(false);
            } else if (key.getKeyType() == KeyType.ArrowRight)
            {
                Buttonhighlighted(true);
            } else if (key.getKeyType() == KeyType.ArrowLeft) {
                Buttonhighlighted(false);
            }
            else if (key.getKeyType() == KeyType.ArrowDown) {
                Buttonhighlighted(true);
            }else if(key.getKeyType()== KeyType.Enter)
            {
                processButton();
            } else if (key.getKeyType() == KeyType.Escape) {
                endscreen=true;
            }
        }
    }
}
