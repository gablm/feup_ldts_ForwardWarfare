package com.ldts.ForwardWarfare.Element;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Facility.Facility;

public abstract class Element {
    protected Position position;
    public Element() { }

    public Position getPosition() {
        return position;
    }
    public void setPosition(Position pos) {
        this.position = pos;
    }
    abstract public void draw(TextGraphics graphics, TextColor textColor);

    public Facility getFacility() {
        return null;
    }
}
