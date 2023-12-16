package com.ldts.ForwardWarfare.State.States;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Facility.Base;
import com.ldts.ForwardWarfare.Element.Facility.Facility;
import com.ldts.ForwardWarfare.Element.Tile.Tile;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.BaseState;
import com.ldts.ForwardWarfare.State.State;

public class StartRoundState extends BaseState {
    public StartRoundState(Controller p1, Controller p2, Map map) {
        super(p1, p2, map);
    }

    @Override
    public State play(Action action) {
        if (p1.canPlay())
            return p1.getInitialState(p2, map);
        if (p2.canPlay())
            return p2.getInitialState(p1, map);
        p1.resetRound();
        p2.resetRound();
        return new StartRoundState(p2, p1, map);
    }

    @Override
    public void draw(TextGraphics graphics) {

    }

    @Override
    public boolean requiresInput() {
        return false;
    }
}
