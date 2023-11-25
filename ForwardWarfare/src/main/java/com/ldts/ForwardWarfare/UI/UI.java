package com.ldts.ForwardWarfare.UI;

import com.googlecode.lanterna.TerminalSize;
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

    UI()
    {
        try {
            UITerminal = new LanternaTerminal(new TerminalSize(50,30), "square.ttf", 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void build() throws IOException;

    public abstract void draw() ;

}
