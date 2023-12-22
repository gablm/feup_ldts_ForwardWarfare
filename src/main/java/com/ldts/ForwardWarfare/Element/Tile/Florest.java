package com.ldts.ForwardWarfare.Element.Tile;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Facility.Facility;
import com.ldts.ForwardWarfare.Element.Position;

public class Florest extends Element implements Tile {
    public Florest(Position position) {
        super.position = position;
        super.backgroundColor = new TextColor.RGB(113,199,0);
        super.foregroundColor = new TextColor.RGB(0,102,51);
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(backgroundColor);
        graphics.setForegroundColor(foregroundColor);
        graphics.putString(position.toTPos(),"{");
    }

    @Override
    public TextColor getColor() {
        return backgroundColor;
    }

    @Override
    public boolean noCollision() {
        return false;
    }

    @Override
    public Facility getFacility() {
        return null;
    }
}
