package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.State;

public class Drawer {
    private Controller p1;
    private Controller p2;
    private Map map;

    public Drawer(Controller p1, Controller p2, Map map) {
        this.p1 = p1;
        this.p2 = p2;
        this.map = map;
    }

    public void draw(TextGraphics graphics, State state) {
        map.draw(graphics);
        p1.draw(graphics, map);
        p2.draw(graphics, map);
        p1.drawBorder(graphics);
        p2.drawBorder(graphics);
        state.draw(graphics);
    }
}
