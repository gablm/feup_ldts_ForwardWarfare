package com.ldts.ForwardWarfare.Element.Tile;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Facility.Facility;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Position;

public class Water extends Element implements Tile {
    Facility facility;
    public Water(Position position, Facility facility) {
        super.position = position;
        super.backgroundColor = new TextColor.RGB(0,124,206);
        super.foregroundColor = new TextColor.RGB(224,224,224);
        if (facility != null)
            this.facility = facility;
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(backgroundColor);
        graphics.setForegroundColor(foregroundColor);
        graphics.putString(position.toTPos(),"~");
        if (facility != null) {
            facility.draw(graphics, position);
        }
    }

    @Override
    public TextColor getColor() {
        return backgroundColor;
    }

    @Override
    public boolean noCollision() {
        return facility == null;
    }
}
