package com.ldts.ForwardWarfare.State.States;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;

public class MoveEndState extends BaseState {
    private int option = 0;
    public MoveEndState(Controller p1, Controller p2, Map map) {
        super(p1, p2, map);
    }

    @Override
    public State play(Action action) {
        switch (action) {
            case ENTER:
                switch (option) {
                    case 0:
                        return p1.getInitialState(p2, map);
                    case 1:
                        p1.endRound();
                        return new StartRoundState(p1, p2, map);
                    default:
                        return new QuitState(p1, p2, map, this);
                }
            case UP:
                option--;
                if (option < 0)
                    option = 0;
                break;
            case DOWN:
                option++;
                if (option > 2)
                    option = 2;
                break;
        }
        return this;
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        graphics.setBackgroundColor(option == 0 ? TextColor.ANSI.RED_BRIGHT : TextColor.ANSI.BLACK);
        graphics.putString(1, 11, " Continue ");
        graphics.setBackgroundColor(option == 1 ? TextColor.ANSI.RED_BRIGHT : TextColor.ANSI.BLACK);
        graphics.putString(1, 12, " End Round ");
        graphics.setBackgroundColor(option == 2 ? TextColor.ANSI.RED_BRIGHT : TextColor.ANSI.BLACK);
        graphics.putString(1, 13, " Exit ");
    }

    @Override
    public boolean requiresInput() {
        return true;
    }
}
