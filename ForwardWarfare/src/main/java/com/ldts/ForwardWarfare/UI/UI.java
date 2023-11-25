package com.ldts.ForwardWarfare.UI;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.ldts.ForwardWarfare.LanternaTerminal;
import com.ldts.ForwardWarfare.UI.Component.Component;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class UI {
    protected List<Component> listComponents = new ArrayList<>();
    protected LanternaTerminal UITerminal;
    protected Screen screen;
    private TerminalSize terminalSize;
    private int fontsize;
    protected boolean endscreen=false;

    UI(TerminalSize terminalSize,int fontsize)
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

    public abstract void build() throws IOException;

    public abstract void draw() throws IOException;

    public abstract void addcomp();

    public abstract void run() throws IOException;
    public abstract  void processKey( KeyStroke key);
}
