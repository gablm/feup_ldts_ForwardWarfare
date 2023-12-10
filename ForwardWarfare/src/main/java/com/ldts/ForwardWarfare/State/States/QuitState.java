package com.ldts.ForwardWarfare.State.States;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
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
        TextColor color = new TextColor.RGB(80, 80, 80);
        graphics.setBackgroundColor(color);
        graphics.fillRectangle(new TerminalPosition(0,10), new TerminalSize(15,9), ' ');

        graphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        graphics.putString(1, 13, "Are you sure?");

        graphics.setBackgroundColor(option == 0 ? TextColor.ANSI.RED_BRIGHT : color);
        graphics.putString(3, 15, " No ");
        graphics.setBackgroundColor(option == 1 ? TextColor.ANSI.RED_BRIGHT : color);
        graphics.putString(7, 15, " Yes ");
    }

    @Override
    public boolean requiresInput() {
        return lastState.getClass() != this.getClass();
    }
}
