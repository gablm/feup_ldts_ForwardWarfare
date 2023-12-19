package com.ldts.ForwardWarfare.UI;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Game;
import com.ldts.ForwardWarfare.UI.Component.Button;
import com.ldts.ForwardWarfare.UI.Component.Component;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class MainMenu extends UI {
    private boolean fsb=true;
    private UiStates startgame;

    private List<Component> fs=new ArrayList<>();
    private List<Component> ss=new ArrayList<>();
    private int cb=0;
    private int highlight=30;
    private int normalborder=10;
    private Button Chosegamemode;
    public MainMenu() throws IOException, URISyntaxException, FontFormatException {
        super(new TerminalSize(41,23),25);
        Chosegamemode=new Button(new TextColor.RGB(237,173,24),new TextColor.RGB(255,255,255),new Position(10,2),new TerminalSize(21,5),"CHOSE GAMEMODE",normalborder);
    }
    @Override
    public UiStates build() throws IOException {
        screen = UITerminal.createScreen();
        addcomp();
        setScreen();
        return run();
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
            else
            {
                endscreen=true;
                startgame=UiStates.Exit;
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
                    endscreen = true;
                    startgame=UiStates.HowToPlay;
                    break;
                case 2:
                    endscreen = true;
                    startgame=UiStates.Exit;
                    break;
            }
        }
        else
        {
            switch (cb) {
                case 0:
                    endscreen = true;
                    gameMode=true; //vs Player
                    startgame = UiStates.StartGameMenu;
                    break;
                case 1:
                    endscreen = true;
                    gameMode=false; //vs AI
                    startgame = UiStates.StartGameMenu;
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
        ss.add(new Button(new TextColor.RGB(247,193,64),new TextColor.RGB(255,255,255),new Position(10,9),new TerminalSize(21,5),"PLAY VS PLAYER",highlight));
        ss.add(new Button(new TextColor.RGB(247,193,64),new TextColor.RGB(255,255,255),new Position(10,16),new TerminalSize(21,5),"PLAY VS AI",normalborder));
    }

    @Override
    public UiStates run() throws IOException{
        while (!endscreen)
        {
            draw();
            KeyStroke key = screen.readInput();
            processKey(key);
        }
        screen.close();

        return startgame;
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
        if(!fsb) {
            Chosegamemode.draw(graphics);
        }
        graphics.clearModifiers();
        screen.refresh();
    }

    public UiStates getStartgame() {
        return startgame;
    }

    public int getCb() {
        return cb;
    }

    public void setFsb(boolean fsb) {
        this.fsb = fsb;
    }

    public List<Component> getFs() {
        return fs;
    }

    public List<Component> getSs() {
        return ss;
    }

    public void setCb(int cb) {
        this.cb = cb;
    }

    public boolean isFsb() {
        return fsb;
    }

}
