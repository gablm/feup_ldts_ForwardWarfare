package com.ldts.ForwardWarfare.UI;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.UI.Component.Button;
import com.ldts.ForwardWarfare.UI.Component.Component;

import java.io.IOException;
import java.util.List;

public class MainMenu extends UI {

    @Override
    public void build() throws IOException {
        screen = UITerminal.createScreen();
        screen.clear();
        addcomp();
        draw();
        screen.refresh();
    }

    private void addcomp() {
        Component StartButton = new Button(new TextColor.RGB(0,255,225),new TextColor.RGB(0,0,0),new Position(13,10),new TerminalSize(25,5),"Start Game",100);
        Component ExitButton = new Button(new TextColor.RGB(0,255,225),new TextColor.RGB(0,0,0),new Position(13,20),new TerminalSize(25,5),"Exit Game",100);

        listComponents.add(ExitButton);
        listComponents.add(StartButton);
    }

    @Override
    public void draw() {
        for (Component component:listComponents)
        {
            component.draw(screen.newTextGraphics());
        }
    }

}
