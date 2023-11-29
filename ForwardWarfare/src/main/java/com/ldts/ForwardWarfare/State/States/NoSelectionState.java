package com.ldts.ForwardWarfare.State.States;

import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Tile.Border;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;

public class NoSelectionState extends BaseState {
    public NoSelectionState(Controller p1, Controller p2, Map map) {
        super(p1, p2, map);
        if (p1.getSelection1() == null)
            p1.setSelection1(new Border(new Position(7, 5)));
    }
    @Override
    public State play(Action action) {
        Position pos = p1.getSelection1().getPosition();
        switch (action) {
            case UP:
                p1.getSelection1().setPosition(new Position(pos.getX(), pos.getY() - 1));
                break;
            case DOWN:
                p1.getSelection1().setPosition(new Position(pos.getX(), pos.getY() + 1));
                break;
            case LEFT:
                p1.getSelection1().setPosition(new Position(pos.getX() - 1, pos.getY()));
                break;
            case RIGHT:
                p1.getSelection1().setPosition(new Position(pos.getX() + 1, pos.getY()));
                break;
            case ENTER:
                p1.setSelection2(new Border(p1.getSelection1().getPosition()));
                return new OneSelectionState(p1, p2, map);
        }
        return this;
    }
}
