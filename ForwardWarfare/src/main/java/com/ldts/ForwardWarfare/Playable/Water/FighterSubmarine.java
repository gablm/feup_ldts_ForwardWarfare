package com.ldts.ForwardWarfare.Playable.Water;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element;
import com.ldts.ForwardWarfare.Playable.Playable;

public class FighterSubmarine extends Playable {
    public FighterSubmarine() {
        super("Water", 6);
    }
    public void draw(TextGraphics graphics) {

    }
    public boolean canMove(Element element) {
        return false;
    }
}
