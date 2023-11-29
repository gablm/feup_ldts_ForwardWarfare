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
        super.position=position;
        if (facility!=null) {
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
    public void draw(TextGraphics graphics,TextColor textColor) {
        graphics.setBackgroundColor(new TextColor.RGB(113,199,0));
        graphics.setForegroundColor(new TextColor.RGB(226,214,106));
        graphics.putString(position.toTPos(),"|");
        if (facility!=null) {
            facility.draw(graphics, position);
        }
    }
    public TextColor getColor() {
        return new TextColor.RGB(113,199,0);
    }
}
