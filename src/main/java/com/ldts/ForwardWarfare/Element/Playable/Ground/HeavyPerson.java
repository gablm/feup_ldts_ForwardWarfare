package com.ldts.ForwardWarfare.Element.Playable.Ground;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Element.Playable.Water.FighterSubmarine;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Tile.Fields;

import java.util.Objects;

public class HeavyPerson extends Playable {
    public HeavyPerson(Position pos) {
        super(3, 100, 75);
        this.foregroundColor = new TextColor.RGB(80, 80, 80);
        position = pos;
    }
    @Override
    public void draw(TextGraphics textGraphics) {
        textGraphics.setForegroundColor(foregroundColor);
        textGraphics.putString(position.toTPos(), "_");
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
        return playable.getType().equals("Ground") ||
                (playable.getType().equals("Water") && !(playable instanceof FighterSubmarine));
    }
}
