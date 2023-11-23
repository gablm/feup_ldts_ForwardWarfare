package com.ldts.ForwardWarfare.Playable.Water;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element;
import com.ldts.ForwardWarfare.Playable.Playable;

public class FighterSubmarine extends Playable {
    public FighterSubmarine() {
        super(6);
    }
    @Override
    public void draw(TextGraphics textGraphics, TextColor textColor) {
        textGraphics.setForegroundColor(textColor != null ? textColor : new TextColor.RGB(80, 80, 80));
        textGraphics.putString(position.toTPos(), "");
    }
    public boolean canMove(Element element) {
        return false;
    }
}
