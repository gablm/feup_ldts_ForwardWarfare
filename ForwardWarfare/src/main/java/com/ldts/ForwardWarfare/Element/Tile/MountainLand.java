package com.ldts.ForwardWarfare.Element.Tile;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Position;

public class MountainLand extends Element implements Tile {
    public MountainLand(Position position)
    {
        super.position=position;
    }

    @Override
    public void draw(TextGraphics graphics,TextColor textColor) {
        graphics.setBackgroundColor(new TextColor.RGB(113,199,0));
        graphics.setForegroundColor(new TextColor.RGB(57,45,45));
        graphics.putString(position.toTPos(),"}");
    }

    @Override
    public TextColor getColor() {
        return new TextColor.RGB(113,199,0);
    }
}
