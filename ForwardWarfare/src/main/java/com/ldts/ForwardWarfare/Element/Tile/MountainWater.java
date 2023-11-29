package com.ldts.ForwardWarfare.Element.Tile;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Position;

public class MountainWater extends Element implements Tile {
    public MountainWater(Position position) {
        super.position=position;
    }
    @Override
    public void draw(TextGraphics graphics,TextColor textColor) {
        graphics.setBackgroundColor(new TextColor.RGB(0,124,206));
        graphics.setForegroundColor(new TextColor.RGB(160,160,160));
        graphics.putString(position.toTPos(),"]");
    }

    @Override
    public TextColor getColor() {
        return new TextColor.RGB(0,124,206);
    }

    @Override
    public boolean noCollision() {
        return false;
    }
}
