package com.ldts.ForwardWarfare.Playable.Ground;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element;
import com.ldts.ForwardWarfare.Playable.Playable;
import com.ldts.ForwardWarfare.Position;

public class LightPerson extends Playable {
    public LightPerson(Position pos) {
        super(5);
        position = pos;
    }

    public void draw(TextGraphics graphics) {

    }

    protected boolean canMove(Element element) {
        return false;
    }
}
