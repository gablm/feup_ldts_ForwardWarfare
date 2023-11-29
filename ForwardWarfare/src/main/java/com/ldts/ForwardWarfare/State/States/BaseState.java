package com.ldts.ForwardWarfare.State.States;

import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.State;

public abstract class BaseState implements State {
    protected Controller p1;
    protected Controller p2;
    protected Map map;

    public BaseState(Controller p1, Controller p2, Map map) {
        this.p1 = p1; this.p2 = p2; this.map = map;
    }
    @Override
    public Map getMap() {
        return map;
    }
    @Override
    public Controller getP1() {
        return p1;
    }
    @Override
    public Controller getP2() {
        return p2;
    }
}
