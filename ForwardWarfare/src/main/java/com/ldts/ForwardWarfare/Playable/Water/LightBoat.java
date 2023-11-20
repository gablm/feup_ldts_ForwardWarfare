package com.ldts.ForwardWarfare.Playable.Water;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element;
import com.ldts.ForwardWarfare.Playable.Playable;
import com.ldts.ForwardWarfare.Position;

public class LightBoat extends Playable {
    public LightBoat(Position pos) {
        super("Water", 5);
        position = pos;
    }
    public void draw(TextGraphics graphics) {

    }
    protected boolean canMove(Element element) {
        return false;
    }
}
