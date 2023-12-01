package com.ldts.ForwardWarfare.Element.Facility;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Position;

public class Factory implements Facility {
    private TextColor textColor = new TextColor.RGB(32, 32, 32);
    public void draw(TextGraphics graphics, Position position) {
        graphics.setForegroundColor(textColor);
        graphics.putString(position.toTPos(),"`");
    }
    @Override
    public void setTextColor(TextColor color) {
        this.textColor = color;
    }
    @Override
    public void execute() {

    }
}
