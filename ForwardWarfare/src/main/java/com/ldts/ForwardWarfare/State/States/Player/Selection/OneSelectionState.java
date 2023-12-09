package com.ldts.ForwardWarfare.State.States.Player.Selection;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Tile.Border;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.States.BaseState;
import com.ldts.ForwardWarfare.State.States.Player.Move.MoveValidationState;

public class OneSelectionState extends BaseState {
    public OneSelectionState(Controller p1, Controller p2, Map map) {
        super(p1, p2, map);
        Border border = p1.getSelection2();
        TextColor color = map.at(border.getPosition()).getColor();
        border.setBackgroundColor(color);
    }
    @Override
    public State play(Action action) {
        Position pos = p1.getSelection2().getPosition();
        switch (action) {
            case UP:
                moveTo(pos.getX(), pos.getY() - 1);
                break;
            case DOWN:
                moveTo(pos.getX(), pos.getY() + 1);
                break;
            case LEFT:
                moveTo(pos.getX() - 1, pos.getY());
                break;
            case RIGHT:
                moveTo(pos.getX() + 1, pos.getY());
                break;
            case ESCAPE:
                p1.setSelection1(new Border(p1.getSelection2().getPosition()));
                p1.setSelection2(null);
                return new NoSelectionState(p1, p2, map);
            case ENTER:
                return new MoveValidationState(p1, p2, map);
        }
        return this;

    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(p1.getControllerColor());
        graphics.fillRectangle(new TerminalPosition(0,10), new TerminalSize(15,9), ' ');

        graphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        graphics.putString(1, 12, "Select where");
        graphics.putString(1, 13, "to go");

        graphics.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        graphics.putString(1, 17, "ENTER");
    }

    @Override
    public boolean requiresInput() {
        return true;
    }

    private void moveTo(int x, int y) {
        Position pos = new Position(x, y);
        if (!map.inside(pos))
            return;
        p1.getSelection2().setPosition(pos);
        TextColor color;
        color = map.at(pos).getColor();
        if (color != null)
            p1.getSelection2().setBackgroundColor(color);
    }
}
