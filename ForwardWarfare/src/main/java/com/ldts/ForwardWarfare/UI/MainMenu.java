package com.ldts.ForwardWarfare.UI;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.UI.Component.Button;
import com.ldts.ForwardWarfare.UI.Component.Component;

import java.io.IOException;

public class MainMenu extends UI {
    public MainMenu()
    {
        super(new TerminalSize(50,30),30);
    }
    private int cb=0;
    private int highlight=100;
    private int normalborder=25;
    @Override
    public void build() throws IOException {
        screen = UITerminal.createScreen();
        addcomp();
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
        }
    }

    private void processButton()
    {
        switch (cb)
        {
            case 0:
                break;
            case 1:
                break;
            case 2:
                endscreen=true;
                break;
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
    @Override
    public void addcomp() {
        Component StartButton = new Button(new TextColor.RGB(0,255,225),new TextColor.RGB(0,0,0),new Position(14,2),new TerminalSize(15,5),"Start Game",highlight);
        Component HowToPlayButton = new Button(new TextColor.RGB(0,255,225),new TextColor.RGB(0,0,0),new Position(14,9),new TerminalSize(15,5),"How to Play",normalborder);
        Component ExitButton = new Button(new TextColor.RGB(0,255,225),new TextColor.RGB(0,0,0),new Position(14,16),new TerminalSize(15,5),"Exit Game",normalborder);

        listComponents.add(StartButton);
        listComponents.add(HowToPlayButton);
        listComponents.add(ExitButton);
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
        for (Component component:listComponents)
        {
            component.draw(graphics);
        }
        screen.refresh();
    }

}
