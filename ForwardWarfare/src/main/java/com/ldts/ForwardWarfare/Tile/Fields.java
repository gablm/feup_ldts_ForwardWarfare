package com.ldts.ForwardWarfare.Tile;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element;
import com.ldts.ForwardWarfare.Facility.Facility;
import com.ldts.ForwardWarfare.Position;

public class Fields extends Element {
    private Facility facility;
    public Fields(Position position, Facility facility)
    {
        super.position=position;
        if (facility!=null) {
            this.facility = facility;
        }
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(new TextColor.RGB(113,199,0));
        graphics.setForegroundColor(new TextColor.RGB(226,214,106));
        graphics.putString(position.toTPos(),"|");
        if (facility!=null) {
            facility.draw(graphics, position);
        }
    }
}
