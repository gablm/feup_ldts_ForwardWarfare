package com.ldts.ForwardWarfare.State.States.Player.Move;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Tile.Border;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.BaseState;
import com.ldts.ForwardWarfare.State.States.Player.Selection.Attack.AttackNoSelectionState;
import com.ldts.ForwardWarfare.State.States.Player.Selection.Capture.CaptureNoSelectionState;
import com.ldts.ForwardWarfare.State.States.QuitState;
import com.ldts.ForwardWarfare.State.States.StartRoundState;

public class MoveEndState extends BaseState {
    private int option = 0;
    private Border oldBorder;
    private Element element;

    public MoveEndState(Controller p1, Controller p2, Map map, Element element) {
        super(p1, p2, map);
        this.oldBorder = p1.getSelection1();
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
                        return new CaptureNoSelectionState(p1, p2, map, element);
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
                if (element == null && option < 0)
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
        TextColor color = new TextColor.RGB(80, 80, 80);
        graphics.setBackgroundColor(color);
        graphics.fillRectangle(new TerminalPosition(0,10), new TerminalSize(15,9), ' ');

        graphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);

        if (element != null) {
            graphics.setBackgroundColor(option == -1 ? TextColor.ANSI.RED_BRIGHT : color);
            graphics.putString(1, 12, " Capture ");

            graphics.setBackgroundColor(option == -2 ? TextColor.ANSI.RED_BRIGHT : color);
            graphics.putString(1, 11, " Attack ");
        }

        int i = element != null ? 1 : 0;
        graphics.setBackgroundColor(option == 0 ? TextColor.ANSI.RED_BRIGHT : color);
        graphics.putString(1, 13 + i, " Continue ");
        graphics.setBackgroundColor(option == 1 ? TextColor.ANSI.RED_BRIGHT : color);
        graphics.putString(1, 14 + i, " End Turn ");
        graphics.setBackgroundColor(option == 2 ? TextColor.ANSI.RED_BRIGHT : color);
        graphics.putString(1, 15 + i, " Exit ");
    }

    @Override
    public boolean requiresInput() {
        return true;
    }
}
