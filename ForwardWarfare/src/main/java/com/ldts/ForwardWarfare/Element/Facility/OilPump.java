package com.ldts.ForwardWarfare.Element.Facility;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Position;

public class OilPump implements Facility {
    private boolean Used = false;
    private TextColor textColor = new TextColor.RGB(255, 255, 0);
    public void draw(TextGraphics graphics, Position position) {
        graphics.setForegroundColor(textColor);
        graphics.putString(position.toTPos(),"/");
    }
    @Override
    public void setTextColor(TextColor color) {
        this.textColor = color;
    }

    @Override
    public void execute() {
        Used = !Used;
    }

    @Override
    public Boolean getUsed() {
        return Used;
    }

}
