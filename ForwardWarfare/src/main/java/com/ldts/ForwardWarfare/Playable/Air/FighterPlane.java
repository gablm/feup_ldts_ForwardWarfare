package com.ldts.ForwardWarfare.Playable.Air;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Playable.Playable;
import com.ldts.ForwardWarfare.Tile;

public class FighterPlane extends Playable {
    public FighterPlane() {
        super("Air");
    }

    public void draw(TextGraphics graphics) {

    }

    protected boolean canMove(Tile tile) {
        return false;
    }
}
