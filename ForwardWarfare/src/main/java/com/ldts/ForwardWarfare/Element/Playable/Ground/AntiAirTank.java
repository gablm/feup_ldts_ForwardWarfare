package com.ldts.ForwardWarfare.Element.Playable.Ground;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Tile.Fields;

public class AntiAirTank extends Playable {
    public AntiAirTank(Position pos) {
        super(4, 140, 100);
        this.foregroundColor = new TextColor.RGB(80, 80, 80);
        position = pos;
    }
    @Override
    public void draw(TextGraphics textGraphics) {
        textGraphics.setForegroundColor(foregroundColor);
        textGraphics.putString(position.toTPos(), "+");
    }

    @Override
    public boolean canMove(Element element) {
        return element instanceof Fields;
    }

    @Override
    public String getType() {
        return "Ground";
    }

    @Override
    public boolean canAttack(Playable playable) {
        return true;
    }
}
