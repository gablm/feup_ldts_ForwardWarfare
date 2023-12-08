package com.ldts.ForwardWarfare.Element.Playable.Air;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Element.Playable.Water.FighterSubmarine;
import com.ldts.ForwardWarfare.Element.Position;

public class FighterPlane extends Playable {
    public FighterPlane(Position pos) {
        super(6);
        this.foregroundColor = new TextColor.RGB(80, 80, 80);
        position = pos;
    }
    @Override
    public void draw(TextGraphics textGraphics) {
        textGraphics.setForegroundColor(foregroundColor);
        textGraphics.putString(position.toTPos(), "%");
    }
    public boolean canMove(Element element) {
        return true;
    }

    @Override
    public String getType() {
        return "Air";
    }

    @Override
    public boolean canAttack(Playable playable) {
        return !(playable instanceof FighterSubmarine);
    }
}
