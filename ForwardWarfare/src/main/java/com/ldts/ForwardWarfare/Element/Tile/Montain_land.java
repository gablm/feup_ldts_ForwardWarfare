package com.ldts.ForwardWarfare.Element.Tile;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Position;

public class Montain_land extends Element {
    public Montain_land(Position position)
    {
        super.position=position;
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(new TextColor.RGB(113,199,0));
        graphics.setForegroundColor(new TextColor.RGB(57,45,45));
        graphics.putString(position.toTPos(),"}");
    }
}
