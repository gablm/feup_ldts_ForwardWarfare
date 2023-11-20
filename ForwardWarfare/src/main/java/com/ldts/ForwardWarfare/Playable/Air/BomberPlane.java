package com.ldts.ForwardWarfare.Playable.Air;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Playable.Playable;
import com.ldts.ForwardWarfare.Tile;

public class BomberPlane extends Playable {
    public BomberPlane() {
        super("Air", 4);
    }
    public void draw(TextGraphics graphics) {

    }
    protected boolean canMove(Tile tile) {
        return false;
    }
}
