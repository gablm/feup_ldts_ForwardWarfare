package com.ldts.ForwardWarfare.Element.Tile;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Position;

public class Forest extends Element {
    public Forest(Position position) {
        super.position=position;
    }

    @Override
    public void draw(TextGraphics graphics,TextColor textColor) {
        graphics.setBackgroundColor(new TextColor.RGB(113,199,0));
        graphics.setForegroundColor(new TextColor.RGB(0,102,51));
        graphics.putString(position.toTPos(),"{");
    }
}
