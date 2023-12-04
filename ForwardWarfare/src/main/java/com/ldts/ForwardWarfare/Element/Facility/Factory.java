package com.ldts.ForwardWarfare.Element.Facility;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Position;

public class Factory implements Facility {
    public void draw(TextGraphics graphics, Position position) {
        graphics.setForegroundColor(new TextColor.RGB(32, 32, 32));
        graphics.putString(position.toTPos(), "`");
    }
}
