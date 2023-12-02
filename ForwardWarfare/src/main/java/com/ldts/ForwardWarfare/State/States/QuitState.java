package com.ldts.ForwardWarfare.State.States;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;

public class QuitState extends BaseState {
    private State lastState;
    public QuitState(Controller p1, Controller p2, Map map, State lastState) {
        super(p1, p2, map);
        this.lastState = lastState;
    }

    @Override
    public State play(Action action) {
        if (lastState.getClass() == this.getClass())
            return null;
        if (action == Action.QUIT)
            return new QuitState(p1, p2, map, this);
        return lastState;
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.ANSI.BLACK);
        graphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        graphics.putString(1, 11, "Are you sure? Q");
    }

    @Override
    public boolean requiresInput() {
        return lastState.getClass() != this.getClass();
    }
}
