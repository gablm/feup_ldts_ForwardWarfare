package com.ldts.ForwardWarfare.State.States;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;

import java.awt.*;

public class EndGameState extends BaseState {
    public EndGameState(Controller p1, Controller p2, Map map) {
        super(p1, p2, map);
        p1.setSelection1(null);
        p1.setSelection2(null);
        p2.setSelection1(null);
        p2.setSelection2(null);
    }

    @Override
    public State play(Action action) {
        return null;
    }

    @Override
    public void draw(TextGraphics graphics) {
        Controller winner = p1.getBaseLives() == 0 ? p2 : p1;
        graphics.setBackgroundColor(winner.getControllerColor());
        graphics.fillRectangle(new TerminalPosition(0,0), new TerminalSize(25,19), ' ');
        graphics.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(8, 8, "GAME OVER");
        graphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        graphics.putString(7, 11, "CONGRATS " + winner.getName());
    }

    @Override
    public boolean requiresInput() {
        return true;
    }
}

