package com.ldts.ForwardWarfare.State.States.Player.Selection;

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
        graphics.setBackgroundColor(TextColor.ANSI.BLACK);
        graphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        graphics.putString(1, 11, message);
    }

    @Override
    public boolean requiresInput() {
        return true;
    }

}
