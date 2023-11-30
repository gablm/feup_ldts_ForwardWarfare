package com.ldts.ForwardWarfare.State.States;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;

public class AutomaticPlayState extends BaseState {
    public AutomaticPlayState(Controller p1, Controller p2, Map map) {
        super(p1, p2, map);
    }

    @Override
    public State play(Action action) {
        return new NoSelectionState(p2, p1, map);
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.ANSI.BLACK);
        graphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        graphics.putString(1, 11, "Automatic");
    }
}
