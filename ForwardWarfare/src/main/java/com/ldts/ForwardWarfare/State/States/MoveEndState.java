package com.ldts.ForwardWarfare.State.States;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Tile.Border;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;

public class MoveEndState extends BaseState {
    private int option = 0;
    private Border oldBorder;
    public MoveEndState(Controller p1, Controller p2, Map map) {
        super(p1, p2, map);
        this.oldBorder = p1.getSelection1();
        if (!canAttack())
            option++;
        p1.setSelection1(null);
    }

    private boolean canAttack() {
        try {
            throw new Exception("Not implemented");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public State play(Action action) {
        switch (action) {
            case ENTER:
                switch (option) {
                    case 1:
                        p1.setSelection1(oldBorder);
                        return p1.getInitialState(p2, map);
                    case 2:
                        p1.endRound();
                        return new StartRoundState(p1, p2, map);
                    case 3:
                        return new CaptureState(p1, p2, map);
                    default:
                        return new QuitState(p1, p2, map, this);
                }
            case UP:
                option--;
                if (option < 0)
                    option = 0;
                if (option < 1 && !canAttack())
                    option = 1;
                break;
            case DOWN:
                option++;
                if (option > 4)
                    option = 4;
                break;
        }
        return this;
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(canAttack() ? TextColor.ANSI.WHITE_BRIGHT : new TextColor.RGB(80,80,80));
        graphics.setBackgroundColor(option == 0 ? TextColor.ANSI.RED_BRIGHT : TextColor.ANSI.BLACK);
        graphics.putString(1, 11, canAttack() ? " Attack " : " No action ");
        graphics.setBackgroundColor(option == 3 ? TextColor.ANSI.RED_BRIGHT : TextColor.ANSI.BLACK);
        graphics.putString(1, 14, canAttack() ? " Capture  " : " No action ");
        graphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        graphics.setBackgroundColor(option == 1 ? TextColor.ANSI.RED_BRIGHT : TextColor.ANSI.BLACK);
        graphics.putString(1, 12, " Continue ");
        graphics.setBackgroundColor(option == 2 ? TextColor.ANSI.RED_BRIGHT : TextColor.ANSI.BLACK);
        graphics.putString(1, 13, " End Round ");
        graphics.setBackgroundColor(option == 4 ? TextColor.ANSI.RED_BRIGHT : TextColor.ANSI.BLACK);
        graphics.putString(1, 15, " Exit ");
    }

    @Override
    public boolean requiresInput() {
        return true;
    }
    private boolean canCapture() {
        try {
            throw new Exception("Not implemented");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
