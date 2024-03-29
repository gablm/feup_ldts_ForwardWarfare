package com.ldts.ForwardWarfare.Controller;

import com.googlecode.lanterna.TextColor;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.States.Player.Selection.NoSelectionState;

import java.util.List;

public class Player extends ControllerBase {

    public Player(List<Element> initialFacilities, TextColor color, String name) throws InvalidControllerException {
        super(initialFacilities, color, name);
    }

    @Override
    public State getInitialState(Controller p2, Map map) {
        return new NoSelectionState(this, p2, map);
    }
}
