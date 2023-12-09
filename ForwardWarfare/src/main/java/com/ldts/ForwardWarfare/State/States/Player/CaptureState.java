package com.ldts.ForwardWarfare.State.States.Player;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Facility.Facility;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Tile.Fields;
import com.ldts.ForwardWarfare.Element.Tile.Tile;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.States.BaseState;
import com.ldts.ForwardWarfare.State.States.Player.Selection.NoSelectionState;

import java.lang.reflect.Field;

public class CaptureState extends BaseState {
    private Position pos;
    public CaptureState(Controller p1, Controller p2, Map map) {
        super(p1, p2, map);
        capture(p1.getSelection1().getPosition());
    }

    @Override
    public State play(Action action) {
        return new NoSelectionState(p1, p2, map);
    }

    @Override
    public void draw(TextGraphics graphics) {
    }

    private void capture(Position pos){
        map.at(pos).getFacility().execute();
        p1.addFacility((Element) map.at(pos));
    }
    @Override
    public boolean requiresInput() {
        return false;
    }
}
