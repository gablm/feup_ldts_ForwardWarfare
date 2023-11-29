package com.ldts.ForwardWarfare.State.States;

import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Tile.Border;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;

public class OneSelectionState extends BaseState{

    private boolean first;
    public OneSelectionState(Controller p1, Controller p2, Map map) {
        super(p1, p2, map);
    }
    @Override
    public State play(Action action) {
        if (action != Action.ENTER) {
            return this;
        }
        p1.setSelection1(null);
        p1.setSelection2(null);
        return null;
    }
}
