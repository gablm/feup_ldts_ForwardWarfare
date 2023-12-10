package com.ldts.ForwardWarfare.State.States.Player.Selection.Capture;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Facility.Facility;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Tile.Border;
import com.ldts.ForwardWarfare.Element.Tile.Tile;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.States.BaseState;
import com.ldts.ForwardWarfare.State.States.Player.CaptureState;
import com.ldts.ForwardWarfare.State.States.Player.Move.MoveEndState;
import com.ldts.ForwardWarfare.State.States.Player.Selection.Attack.AttackState;
import com.ldts.ForwardWarfare.State.States.QuitState;

import java.util.ArrayList;
import java.util.List;


public class CaptureNoSelectionState extends BaseState {
    private List<Element> selectables = new ArrayList<>();
    private int index = 0;

    public CaptureNoSelectionState(Controller p1, Controller p2, Map map) {
        super(p1, p2, map);
        Border border = p1.getSelection1();
        if (border == null) {
            p1.setSelection1(new Border(new Position(0, 0)));
        } else {
            TextColor color = map.at(border.getPosition()).getColor();
            border.setBackgroundColor(color);
        }
        for(Element i: getelements())
        {
            if(isnewfacility(i.getPosition()))
            {
                selectables.add(i);
            }
        }
        if (selectables.isEmpty())
            return;
        moveTo(selectables.get(0).getPosition());
    }

    @Override
    public State play(Action action) {
        if (selectables.isEmpty())
            return new MoveEndState(p1, p2, map, p1.getSelection1());
        switch (action) {
            case UP:
                break;
            case DOWN:
                break;
            case LEFT:
                index = index > 0 ? index - 1 : 0;
                break;
            case RIGHT:
                index = index < selectables.size() - 1 ? index + 1 : selectables.size() - 1;
                break;
            case ENTER:
                return new CaptureState(p1, p2, map);
            case ESCAPE:
                return new MoveEndState(p1, p2, map,null);
            case QUIT:
                return new QuitState(p1, p2, map, this);
        }
        moveTo(selectables.get(index).getPosition());
        return this;
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(p1.getControllerColor());
        graphics.fillRectangle(new TerminalPosition(0,10), new TerminalSize(15,9), ' ');

        graphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);

        if (!selectables.isEmpty()) {
            graphics.putString(1, 11, "Select Facility");
            TextCharacter character = graphics.getCharacter(p1.getSelection1().getPosition().toTPos());
            graphics.setCharacter(1, 13, character);
            graphics.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
            graphics.putString(1, 15, "LEFT");
            graphics.putString(1, 15, "RIGHT");
        } else {
            graphics.putString(1, 11, "Nothing to");
            graphics.putString(1, 12, "Capture");
        }
        graphics.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        graphics.putString(1, 17, "ENTER");
    }

    @Override
    public boolean requiresInput() {
        return true;
    }

    private void moveTo(Position pos) {
        if (!map.inside(pos))
            return;
        p1.getSelection1().setPosition(pos);
        TextColor color = map.at(pos).getColor();
        if (color != null)
            p1.getSelection1().setBackgroundColor(color);
    }
    private boolean withinRadius(Position point2) {
        Position point1 = p1.getSelection1().getPosition();
        System.out.printf("rad:", point1.getX(), point1.getY(), point2.getX(), point2.getY());
        if (point2.getX() > point1.getX() + 1)
            return false;
        if (point2.getX() < point1.getX() - 1)
            return false;
        if (point2.getY() > point1.getY() + 1)
            return false;
        if (point2.getY() < point1.getY() - 1)
            return false;
        return true;
    }
    private boolean isnewfacility(Position pos){
        if (p1.getFacilities().stream().anyMatch(facility -> facility.getPosition().equals(pos)) || p1.getBase().getPosition().equals(pos))
        {
            return false;
        }
        else
            return map.at(pos).getFacility()!=null;
    }
    private List<Element> getelements(){
        List<Element> elements = new ArrayList<>();
        for(Element i: map.getElements())
        {
            if(withinRadius(i.getPosition()))
                elements.add(i);
        }
        return elements;
    }
}
