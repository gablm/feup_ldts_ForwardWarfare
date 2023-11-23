package com.ldts.ForwardWarfare.Playable.Water;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element;
import com.ldts.ForwardWarfare.Playable.Playable;
import com.ldts.ForwardWarfare.Position;

public class LightBoat extends Playable {
    public LightBoat(Position pos) {
        super(5);
        position = pos;
    }
    @Override
    public void draw(TextGraphics textGraphics, TextColor textColor) {
        textGraphics.setForegroundColor(textColor != null ? textColor : new TextColor.RGB(80, 80, 80));
        textGraphics.putString(position.toTPos(), "");
    }
    protected boolean canMove(Element element) {
        return false;
    }
}
