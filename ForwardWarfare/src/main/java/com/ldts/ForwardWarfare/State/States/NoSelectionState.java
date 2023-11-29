package com.ldts.ForwardWarfare.State.States;

import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;

public class NoSelectionState extends BaseState {
    public NoSelectionState(Controller p1, Controller p2, Map map, boolean first) {
        super(p1, p2, map);
    }
    @Override
    public State play(Action action) {
        if (action != Action.ENTER)
            return this;
        return this;
    }
}
