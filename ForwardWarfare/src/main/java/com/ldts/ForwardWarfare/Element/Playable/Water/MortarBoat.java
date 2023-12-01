package com.ldts.ForwardWarfare.Element.Playable.Water;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Element.Position;

public class MortarBoat extends Playable {
    public MortarBoat(Position pos) {
        super(3);
        this.foregroundColor = new TextColor.RGB(80, 80, 80);
        position = pos;
    }
    @Override
    public void draw(TextGraphics textGraphics) {
        textGraphics.setForegroundColor(foregroundColor);
        textGraphics.putString(position.toTPos(), "'");
    }
    public boolean canMove(Element element) {
        return false;
    }
}
