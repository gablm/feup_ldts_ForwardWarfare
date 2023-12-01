package com.ldts.ForwardWarfare.Element.Playable.Ground;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Element.Position;

public class HeavyTank extends Playable {
    public HeavyTank(Position pos) {
        super(2);
        this.foregroundColor = new TextColor.RGB(80, 80, 80);
        position = pos;
    }
    @Override
    public void draw(TextGraphics textGraphics) {
        textGraphics.setForegroundColor(foregroundColor);
        textGraphics.putString(position.toTPos(), "#");
    }
    public boolean canMove(Element element) {
        return false;
    }
}
