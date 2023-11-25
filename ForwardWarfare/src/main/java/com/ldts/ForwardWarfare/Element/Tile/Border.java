package com.ldts.ForwardWarfare.Element.Tile;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Position;

public class Border extends Element {
    public Border(Position position)
    {
        this.position = position;
    }

    @Override
    public void draw(TextGraphics graphics,TextColor textColor) {
        graphics.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
        graphics.putString(position.toTPos(),"*");
    }
}
