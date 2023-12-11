package com.ldts.ForwardWarfare.Element;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public abstract class Element {
    protected Position position;
    protected TextColor backgroundColor;
    protected TextColor foregroundColor;
    public Element() { }
    public void setForegroundColor(TextColor foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    public void setBackgroundColor(TextColor backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Position getPosition() {
        return position;
    }
    public void setPosition(Position pos) {
        this.position = pos;
    }
    abstract public void draw(TextGraphics graphics);
}
