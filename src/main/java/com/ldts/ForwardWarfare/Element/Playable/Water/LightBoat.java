package com.ldts.ForwardWarfare.Element.Playable.Water;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Tile.Water;

public class LightBoat extends Playable {
    public LightBoat(Position pos) {
        super(5, 100, 50);
        this.foregroundColor = new TextColor.RGB(80, 80, 80);
        position = pos;
    }
    @Override
    public void draw(TextGraphics textGraphics) {
        textGraphics.setForegroundColor(foregroundColor);
        textGraphics.putString(position.toTPos(), "<");
    }

    @Override
    public boolean canMove(Element element) {
        return element instanceof Water;
    }

    @Override
    public String getType() {
        return "Water";
    }

    @Override
    public boolean canAttack(Playable playable) {
        return playable.getType().equals("Water") || playable.getType().equals("Ground");
    }
}
