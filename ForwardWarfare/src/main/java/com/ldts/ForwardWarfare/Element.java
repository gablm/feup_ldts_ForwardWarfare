package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.graphics.TextGraphics;

public abstract class Element {

    protected Position position;
    protected int hp = -1;
    public Element() { }

    public int getHP() { return hp; }
    public void takeHP(int damage) { this.hp -= damage; }
    public void setHP(int hp) { this.hp = hp; }
    public Position getPosition() {
        return position;
    }
    public void setPosition(Position pos) {
        this.position = pos;
    }
    abstract public void draw(TextGraphics graphics);

}
