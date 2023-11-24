package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Playable.Ground.AntiAirTank;
import com.ldts.ForwardWarfare.Element.Playable.Ground.HeavyTank;
import com.ldts.ForwardWarfare.Element.Playable.Ground.LightPerson;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Facility.*;
import com.ldts.ForwardWarfare.Element.Tile.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private Screen screen;
    private LanternaTerminal terminal;

    public static void main(String[] args) {
        try {
            new Game(new LanternaTerminal(new TerminalSize(15,15), "tanks2_0.ttf", 50)).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Game(LanternaTerminal terminal) {
        this.terminal = terminal;
    }
    public void run() throws IOException {
        screen = terminal.createScreen();
        screen.clear();
        DrawTiles(screen.newTextGraphics());
        screen.refresh();
    }

    private void DrawTiles(TextGraphics graphics) {
        Map map = new Map("0.fw");
        for (Element element : map.getElements()) {
            if (element instanceof Playable)
                element.draw(graphics, TextColor.ANSI.BLUE);
            else
                element.draw(graphics, null);
        }
    }
}
