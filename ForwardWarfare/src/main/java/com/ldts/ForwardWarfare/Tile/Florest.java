package com.ldts.ForwardWarfare.Tile;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element;
import com.ldts.ForwardWarfare.Position;

public class Florest extends Element {
    public Florest(Position position) {
        super.position=position;
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(new TextColor.RGB(113,199,0));
        graphics.setForegroundColor(new TextColor.RGB(0,102,51));
        graphics.putString(position.toTPos(),"{");
    }
}
