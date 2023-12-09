package com.ldts.ForwardWarfare.State.States.Player.Selection;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Tile.Border;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.States.BaseState;
import com.ldts.ForwardWarfare.State.States.Player.CaptureState;
import com.ldts.ForwardWarfare.State.States.Player.Move.MoveEndState;
import com.ldts.ForwardWarfare.State.States.QuitState;

import java.util.Optional;

public class CaptureNoSelectionState extends BaseState {
    private Position UnitPos;

    public CaptureNoSelectionState(Controller p1, Controller p2, Map map) {
        super(p1, p2, map);
        Border border = p1.getSelection1();
        if (border == null) {
            p1.setSelection1(new Border(new Position(0, 0)));
            moveTo(5, 7);
        } else {
            TextColor color = map.at(border.getPosition()).getColor();
            border.setBackgroundColor(color);
        }
        UnitPos = p1.getSelection1().getPosition();
    }

    @Override
    public State play(Action action) {
        Position pos = p1.getSelection1().getPosition();
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
            case ENTER:
                if (isnewfacility(pos))
                    return new CaptureState(p1, p2, map);
                else
                    return new CaptureInvalidState(p1, p2, map,"Invalid Facility");
            case ESCAPE:
                return new MoveEndState(p1, p2, map, null);
            case QUIT:
                return new QuitState(p1, p2, map, this);
        }
        return this;
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.ANSI.BLACK);
        graphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        graphics.putString(1, 11, "Select facility");
    }

    @Override
    public boolean requiresInput() {
        return true;
    }

    private void moveTo(int x, int y) {
        Position pos = new Position(x, y);
        if(pos.getX()<=UnitPos.getX()+1 && pos.getX()>=UnitPos.getX()-1 && pos.getY()<=UnitPos.getY()+1 && pos.getY()>=UnitPos.getY()-1) {
            if (!map.inside(pos))
                return;
            p1.getSelection1().setPosition(pos);
            TextColor color = map.at(pos).getColor();
            if (color != null)
                p1.getSelection1().setBackgroundColor(color);
        }
    }
    private boolean isnewfacility(Position pos){
        if (p1.getFacilities().stream().anyMatch(facility -> facility.getPosition().equals(pos)))
        {
            return false;
        }
        else
            return map.at(pos).getFacility()!=null;
    }
}
