package com.ldts.ForwardWarfare.Element.Tile;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Facility.Facility;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Position;

public class Water extends Element {
    Facility facility;
    public Water(Position position,Facility facility) {
        super.position=position;
        if (facility!=null) {
            this.facility = facility;
        }
    }
    @Override
    public Facility getFacility()
    {
        return facility;
    }
    @Override
    public void draw(TextGraphics graphics,TextColor textColor) {
        graphics.setBackgroundColor(new TextColor.RGB(0,124,206));
        graphics.setForegroundColor(new TextColor.RGB(224,224,224));
        graphics.putString(position.toTPos(),"~");
        if (facility!=null) {
            facility.draw(graphics, position);
        }
    }
}
