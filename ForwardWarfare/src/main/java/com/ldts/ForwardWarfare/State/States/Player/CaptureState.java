package com.ldts.ForwardWarfare.State.States.Player;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.States.BaseState;
import com.ldts.ForwardWarfare.State.States.Player.Selection.NoSelectionState;

public class CaptureState extends BaseState {
    private Position pos;
    public CaptureState(Controller p1, Controller p2, Map map) {
        super(p1, p2, map);
    }

    @Override
    public State play(Action action) {
        return new NoSelectionState(p1, p2, map);
    }

    @Override
    public void draw(TextGraphics graphics) {

    }

    @Override
    public boolean requiresInput() {
        return false;
    }
}
