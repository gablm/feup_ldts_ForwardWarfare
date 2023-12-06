package com.ldts.ForwardWarfare.State.States;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Tile.Tile;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MoveValidationState extends BaseState {
    public MoveValidationState(Controller p1, Controller p2, Map map) {
        super(p1, p2, map);
    }

    @Override
    public State play(Action action) {
        Position pos = p1.getSelection2().getPosition();
        if (!map.at(pos).noCollision()) {
            p1.setSelection2(null);
            return new InvalidSelectState(p1, p2, map, "Not allowed");
        }
        for (Element i : p1.getTroops()) {
            if (i.getPosition().equals(p1.getSelection1().getPosition()))
                i.setPosition(pos);
        }
        p1.setSelection1(p1.getSelection2());
        p1.setSelection2(null);
        return new MoveEndState(p1, p2, map);
    }

    private boolean canMove() {
        List<Boolean> mapBool = new ArrayList<>(150);
        map.getElements().forEach(x -> mapBool.add(((Tile)x).noCollision()));
        return false;
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.ANSI.BLACK);
        graphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        graphics.putString(1, 11, "Validating");
    }

    @Override
    public boolean requiresInput() {
        return false;
    }
}
