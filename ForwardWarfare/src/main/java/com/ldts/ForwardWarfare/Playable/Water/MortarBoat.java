package com.ldts.ForwardWarfare.Playable.Water;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element;
import com.ldts.ForwardWarfare.Playable.Playable;
import com.ldts.ForwardWarfare.Position;

public class MortarBoat extends Playable {
    public MortarBoat(Position pos) {
        super(3);
        position = pos;
    }
    public void draw(TextGraphics graphics) {

    }
    public boolean canMove(Element element) {
        return false;
    }
}
