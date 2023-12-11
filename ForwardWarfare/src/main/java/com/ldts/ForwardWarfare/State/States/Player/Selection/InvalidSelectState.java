package com.ldts.ForwardWarfare.State.States.Player.Selection;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.States.BaseState;
import com.ldts.ForwardWarfare.State.States.Player.Selection.NoSelectionState;

public class InvalidSelectState extends BaseState {
    private String message;
    public InvalidSelectState(Controller p1, Controller p2, Map map, String message) {
        super(p1, p2, map);
        this.message = message;
    }
    @Override
    public State play(Action action) {
        return new NoSelectionState(p1, p2, map);
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(p1.getControllerColor());
        graphics.fillRectangle(new TerminalPosition(0,10), new TerminalSize(15,9), ' ');

        graphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        int i = 0;
        for (String str : message.split("\n")) {
            if (i == 6) {
                graphics.putString(1, 11 + i, "...");
                break;
            }
            graphics.putString(1, 11 + i, str);
            i++;
        }
        graphics.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        graphics.putString(1, 17, "ENTER");
    }

    @Override
    public boolean requiresInput() {
        return true;
    }

}
