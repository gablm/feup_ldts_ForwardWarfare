package com.ldts.ForwardWarfare.State.States.Player.Selection;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Facility.Airport;
import com.ldts.ForwardWarfare.Element.Facility.Facility;
import com.ldts.ForwardWarfare.Element.Facility.Factory;
import com.ldts.ForwardWarfare.Element.Facility.Port;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Tile.Border;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.BaseState;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.States.*;
import com.ldts.ForwardWarfare.State.States.Player.BuyState;
import com.ldts.ForwardWarfare.State.States.Player.Move.MoveEndState;

import java.util.Optional;

public class NoSelectionState extends BaseState {

    public NoSelectionState(Controller p1, Controller p2, Map map) {
        super(p1, p2, map);
        Border border = p1.getSelection1();
        if (border == null) {
            p1.setSelection1(new Border(new Position(0, 0)));
            moveTo(5, 7);
        } else {
            TextColor color = map.at(border.getPosition()).getColor();
            border.setBackgroundColor(color);
        }
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
                Optional<Element> findTroop = p1.getTroops().stream().filter(x -> x.getPosition().equals(pos)).findFirst();
                if (findTroop.isPresent()) {
                    if (((Playable) findTroop.get()).hasMoved())
                        return new InvalidSelectState(p1, p2, map, "Already moved");
                    p1.setSelection2(new Border(pos));
                    return new OneSelectionState(p1, p2, map);
                }
                Facility facility = map.at(pos).getFacility();
                if (facility != null && (facility.getClass() == Factory.class
                        || facility.getClass() == Airport.class
                        || facility.getClass() == Port.class)) {
                    if (!p1.getFacilities().stream().anyMatch(x -> x.getPosition().equals(pos)))
                        return new InvalidSelectState(p1, p2, map, "Not Owned");
                    else {
                        if (facility.getUsed())
                            return new InvalidSelectState(p1, p2, map, "Already used");
                        return new BuyState(p1, p2, map, map.at(pos).getFacility(), pos);
                    }
                }
                return new InvalidSelectState(p1, p2, map, "Invalid play");
            case ESCAPE:
                return new MoveEndState(p1, p2, map, null);
            case QUIT:
                return new QuitState(p1, p2, map, this);
        }
        return this;
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(p1.getControllerColor());
        graphics.fillRectangle(new TerminalPosition(0,10), new TerminalSize(15,9), ' ');

        graphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        graphics.putString(1, 11, "Select a tile");

        graphics.putString(1, 14, "Current: ");
        TextCharacter character = graphics.getCharacter(p1.getSelection1().getPosition().toTPos());
        graphics.setCharacter(1 + 10, 14, character);

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
        p1.getSelection1().setPosition(pos);
        TextColor color = map.at(pos).getColor();
        if (color != null)
            p1.getSelection1().setBackgroundColor(color);
    }
}
