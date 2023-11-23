package com.ldts.ForwardWarfare.Playable;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element;
import com.ldts.ForwardWarfare.Position;

public abstract class Playable extends Element {
    private int hp = 0;
    private final int maxMoves;
    public Playable(int maxMoves) {
        this.maxMoves = maxMoves;
    }
    public void takeHP(int damage) {
        hp -= damage;
    }
    public void setHP(int hp) {
        this.hp = hp;
    }

    public abstract void draw(TextGraphics textGraphics, TextColor textColor);
    protected abstract boolean canMove(Element element);
}
