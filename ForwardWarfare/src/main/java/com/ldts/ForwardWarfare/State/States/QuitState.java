package com.ldts.ForwardWarfare.State.States;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Facility.OilPump;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;

public class QuitState extends BaseState {
    private State lastState;
    private int option = 0;
    public QuitState(Controller p1, Controller p2, Map map, State lastState) {
        super(p1, p2, map);
        this.lastState = lastState;
    }

    @Override
    public State play(Action action) {
        if (lastState.getClass() == this.getClass())
            return null;
        return switch (action) {
            case QUIT -> new QuitState(p1, p2, map, this);
            case LEFT -> {
                option = 0;
                yield this;
            }
            case RIGHT -> {
                option = 1;
                yield this;
            }
            case ENTER -> {
                if (option == 1)
                    yield null;
                yield lastState;
            }
            default -> lastState;
        };
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.ANSI.BLACK);
        graphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        graphics.putString(1, 11, "Are you sure?");

        graphics.setBackgroundColor(option == 0 ? TextColor.ANSI.RED_BRIGHT : TextColor.ANSI.BLACK);
        graphics.putString(3, 13, " No ");
        graphics.setBackgroundColor(option == 1 ? TextColor.ANSI.RED_BRIGHT : TextColor.ANSI.BLACK);
        graphics.putString(7, 13, " Yes ");
    }

    @Override
    public boolean requiresInput() {
        return lastState.getClass() != this.getClass();
    }
}
