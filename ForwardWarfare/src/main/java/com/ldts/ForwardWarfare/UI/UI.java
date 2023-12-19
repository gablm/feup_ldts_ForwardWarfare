package com.ldts.ForwardWarfare.UI;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.ldts.ForwardWarfare.LanternaTerminal;
import com.ldts.ForwardWarfare.Map.MapParseException;
import com.ldts.ForwardWarfare.UI.Component.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public abstract class UI {
    protected List<Component> listComponents = new ArrayList<>();
    protected LanternaTerminal UITerminal;
    protected Screen screen;
    private TerminalSize terminalSize;
    private int fontsize;
    protected boolean endscreen=false;
    protected boolean gameMode;

    public UI(TerminalSize terminalSize,int fontsize)
    {
        this.fontsize=fontsize;
        this.terminalSize=terminalSize;
        try {
            UITerminal = new LanternaTerminal(new TerminalSize(terminalSize.getColumns(),terminalSize.getRows()), "tanks2_0.ttf", fontsize);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract UiStates build() throws IOException, MapParseException, URISyntaxException;

    public abstract void draw() throws IOException;

    public abstract void addcomp() throws FileNotFoundException, MapParseException, URISyntaxException;

    public abstract UiStates run() throws IOException;
    public abstract  void processKey( KeyStroke key);

    public List<Component> getListComponents() {
        return listComponents;
    }

    public TerminalSize getTerminalSize() {
        return terminalSize;
    }

    public int getFontsize() {
        return fontsize;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public void setUITerminal(LanternaTerminal UITerminal) {
        this.UITerminal = UITerminal;
    }

    public boolean isEndscreen() {
        return endscreen;
    }

    public boolean getGameMode() {
        return gameMode;
    }

    public void setListComponents(List<Component> listComponents) {
        this.listComponents = listComponents;
    }
}
