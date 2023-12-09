package com.ldts.ForwardWarfare.Controller;

import com.googlecode.lanterna.TextColor;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.States.Automatic.AutomaticPlayState;

import java.util.List;

public class Bot extends ControllerBase {
    public Bot(List<Element> initialFacilities, TextColor color) throws InvalidControllerException {
        super(initialFacilities, color);
    }

    @Override
    public State getInitialState(Controller p2, Map map) {
        return new AutomaticPlayState(this, p2, map);
    }
}
