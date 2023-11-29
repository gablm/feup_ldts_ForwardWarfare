package com.ldts.ForwardWarfare.State;

import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Map.Map;

public interface State {
    State play(Action action);
    Controller getP1();
    Controller getP2();
    Map getMap();
}
