package com.ldts.ForwardWarfare.Tile;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element;
import com.ldts.ForwardWarfare.Position;

public class Water extends Element {
    public Water(Position position) {
        super.position=position;
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(new TextColor.RGB(0,124,206));
        graphics.setForegroundColor(new TextColor.RGB(0,0,0));
        graphics.putString(position.toTPos(),"}");
    }
}
