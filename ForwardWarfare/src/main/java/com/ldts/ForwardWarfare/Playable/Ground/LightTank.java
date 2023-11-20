package com.ldts.ForwardWarfare.Playable.Ground;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element;
import com.ldts.ForwardWarfare.Playable.Playable;
import com.ldts.ForwardWarfare.Position;

public class LightTank extends Playable {
    public LightTank(Position pos) {
        super("Ground", 4);
        position = pos;
    }
    public void draw(TextGraphics graphics) {

    }

    protected boolean canMove(Element element) {
        return false;
    }
}
