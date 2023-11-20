package com.ldts.ForwardWarfare.Playable.Ground;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element;
import com.ldts.ForwardWarfare.Playable.Playable;
import com.ldts.ForwardWarfare.Position;

public class HeavyTank extends Playable {
    public HeavyTank(Position pos) {
        super("Ground", 2);
    }

    public void draw(TextGraphics graphics) {

    }

    protected boolean canMove(Element element) {
        return false;
    }
}
