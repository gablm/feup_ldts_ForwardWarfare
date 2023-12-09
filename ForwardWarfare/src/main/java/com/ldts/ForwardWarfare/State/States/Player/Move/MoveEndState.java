package com.ldts.ForwardWarfare.State.States.Player.Move;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Tile.Border;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.States.BaseState;
import com.ldts.ForwardWarfare.State.States.Player.Selection.Attack.AttackNoSelectionState;
import com.ldts.ForwardWarfare.State.States.Player.Selection.CaptureNoSelectionState;
import com.ldts.ForwardWarfare.State.States.QuitState;
import com.ldts.ForwardWarfare.State.States.StartRoundState;

public class MoveEndState extends BaseState {
    private int option = 0;
    private Border oldBorder;
    private Element element;

    public MoveEndState(Controller p1, Controller p2, Map map, Element element) {
        super(p1, p2, map);
        this.oldBorder = p1.getSelection1();
        if (!canAttack())
            option++;
        p1.setSelection1(null);
        this.element = element;
    }

    @Override
    public State play(Action action) {
        switch (action) {
            case ENTER:
                switch (option) {
                    case -2:
                        p1.setSelection1(oldBorder);
                        return new AttackNoSelectionState(p1, p2, map, element);
                    case -1:
                        p1.setSelection1(oldBorder);
                        return new CaptureNoSelectionState(p1, p2, map);
                    case 0:
                        p1.setSelection1(oldBorder);
                        return p1.getInitialState(p2, map);
                    case 1:
                        p1.endRound();
                        return new StartRoundState(p1, p2, map);
                    default:
                        return new QuitState(p1, p2, map, this);
                }
            case UP:
                option--;
                if (option < -2)
                    option = -2;
                if (!canAttack() && option == -2)
                    option = -1;
                if (!canCapture() && option == -1)
                    option = -2;
                break;
            case DOWN:
                option++;
                if (!canCapture() && option == -1)
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

        if (canCapture()) {
            graphics.setBackgroundColor(option == -1 ? TextColor.ANSI.RED_BRIGHT : TextColor.ANSI.BLACK);
            graphics.putString(1, 12, " Capture ");
        }

        if (canAttack()) {
            graphics.setBackgroundColor(option == -2 ? TextColor.ANSI.RED_BRIGHT : TextColor.ANSI.BLACK);
            graphics.putString(1, canCapture() ? 11 : 12, " Attack ");
        }

        int i = canAttack() || canCapture() ? 1 : 0;
        graphics.setBackgroundColor(option == 0 ? TextColor.ANSI.RED_BRIGHT : TextColor.ANSI.BLACK);
        graphics.putString(1, 13 + i, " Continue ");
        graphics.setBackgroundColor(option == 1 ? TextColor.ANSI.RED_BRIGHT : TextColor.ANSI.BLACK);
        graphics.putString(1, 14 + i, " End Turn ");
        graphics.setBackgroundColor(option == 2 ? TextColor.ANSI.RED_BRIGHT : TextColor.ANSI.BLACK);
        graphics.putString(1, 15 + i, " Exit ");
    }

    @Override
    public boolean requiresInput() {
        return true;
    }
    private boolean canCapture() {
        return element != null;
    }
    private boolean canAttack() {
        return element != null;
    }
}
