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
import com.ldts.ForwardWarfare.UI.Component.Component;
import com.ldts.ForwardWarfare.UI.Component.MapBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainMenu extends UI {
    public MainMenu()
    {
        super(new TerminalSize(41,23),25);
    }
    private boolean fsb=true;
    private List<Component> fs=new ArrayList<>();
    private List<Component> ss=new ArrayList<>();
    private int cb=0;
    private int highlight=75;
    private int normalborder=20;
    @Override
    public void build() throws IOException {
        screen = UITerminal.createScreen();
        addcomp();
        setScreen();
        run();
    }
    @Override
    public void processKey( KeyStroke key) {
        if (key.getKeyType() == KeyType.ArrowUp)
        {
            Buttonhighlighted(false);
        } else if (key.getKeyType() == KeyType.ArrowRight)
        {
            Buttonhighlighted(true);
        } else if (key.getKeyType() == KeyType.ArrowLeft) {
            Buttonhighlighted(false);
        }
        else if (key.getKeyType() == KeyType.ArrowDown)
        {
            Buttonhighlighted(true);
        }else if(key.getKeyType()== KeyType.Enter)
        {
            processButton();
        } else if (key.getKeyType()==KeyType.Escape) {
            if(!fsb)
            {
                fsb=true;
                setScreen();
            }
        }
    }

    private void processButton()
    {
        if(fsb) {
            switch (cb) {
                case 0:
                    fsb = false;
                    setScreen();
                    break;
                case 1:
                    break;
                case 2:
                    endscreen = true;
                    break;
            }
        }
        else
        {
            switch (cb) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
            }
        }
    }
    private void Buttonhighlighted(boolean next)
    {
        if(next) {
            listComponents.get(cb).setBorderFadeIntensity(normalborder);
            if (cb+1 > listComponents.size() - 1) {
                cb = 0;
            } else
                cb++;
            listComponents.get(cb).setBorderFadeIntensity(highlight);
        }
        else
        {
            listComponents.get(cb).setBorderFadeIntensity(normalborder);
            if (cb-1 < 0) {
                cb = listComponents.size()-1;
            } else
                cb--;
            listComponents.get(cb).setBorderFadeIntensity(highlight);
        }
    }
    private void setScreen()
    {
        if(fsb)
        {
            cb=0;
            listComponents=fs;
        }
        else
        {
            cb=0;
            listComponents=ss;
        }
    }

    @Override
    public void addcomp() {
        Component StartButton = new Button(new TextColor.RGB(247,193,64),new TextColor.RGB(255,255,255),new Position(10,2),new TerminalSize(21,5),"START GAME",highlight);
        Component HowToPlayButton = new Button(new TextColor.RGB(247,193,64),new TextColor.RGB(255,255,255),new Position(10,9),new TerminalSize(21,5),"HOW TO PLAY",normalborder);
        Component ExitButton = new Button(new TextColor.RGB(247,193,64),new TextColor.RGB(255,255,255),new Position(10,16),new TerminalSize(21,5),"EXIT GAME",normalborder);
        fs.add(StartButton);
        fs.add(HowToPlayButton);
        fs.add(ExitButton);
        ss.add(StartButton);
        ss.add(new Button(new TextColor.RGB(247,193,64),new TextColor.RGB(255,255,255),new Position(10,9),new TerminalSize(21,5),"PLAY VS PLAYER",normalborder));
        ss.add(new Button(new TextColor.RGB(247,193,64),new TextColor.RGB(255,255,255),new Position(10,16),new TerminalSize(21,5),"PLAY VS AI",normalborder));
    }
    @Override
    public void run() throws IOException{
        while (!endscreen)
        {
            draw();
            KeyStroke key = screen.readInput();
            processKey(key);
        }
        screen.close();
    }
    @Override
    public void draw() throws IOException {
        screen.clear();
        TextGraphics graphics = screen.newTextGraphics();
        graphics.enableModifiers(SGR.BOLD);
        graphics.setBackgroundColor(new TextColor.RGB(117,200,4));
        graphics.fillRectangle(new TerminalPosition(0,0),new TerminalSize(41,23),' ');
        for (Component component:listComponents)
        {
            component.draw(graphics);
        }
        graphics.clearModifiers();
        screen.refresh();
    }

}
