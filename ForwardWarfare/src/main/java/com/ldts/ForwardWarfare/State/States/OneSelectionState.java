package com.ldts.ForwardWarfare.State.States;

import com.googlecode.lanterna.TextColor;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Tile.Border;
import com.ldts.ForwardWarfare.Element.Tile.Tile;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;

public class OneSelectionState extends BaseState{
    public OneSelectionState(Controller p1, Controller p2, Map map) {
        super(p1, p2, map);
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
        }
        return this;

    }

    private void moveTo(int x, int y) {
        Position pos = new Position(x, y);
        if (!map.inside(pos))
            return;
        p1.getSelection2().setPosition(pos);
        TextColor color;
        color = ((Tile) map.at(pos)).getColor();
        if (color != null)
            p1.getSelection2().setBackground(color);
    }
}
