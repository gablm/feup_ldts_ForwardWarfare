package com.ldts.ForwardWarfare.Element.Tile;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Facility.Facility;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Position;
public class Fields extends Element implements Tile {
    private Facility facility;
    public Fields(Position position, Facility facility)
    {
        super.position = position;
        this.backgroundColor = new TextColor.RGB(113,199,0);
        this.foregroundColor = new TextColor.RGB(226,214,106);
        if (facility != null) {
            this.facility = facility;
        }
    }
    public Facility getFacility() {
        return facility;
    }
    public int getCoins() {
        return 30;
    }
    @Override
    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(backgroundColor);
        graphics.setForegroundColor(foregroundColor);
        graphics.putString(position.toTPos(),"|");
        if (facility != null) {
            facility.draw(graphics, position);
        }
    }
    public TextColor getColor() {
        return backgroundColor;
    }

    @Override
    public boolean noCollision() {
        return facility == null;
    }
}
