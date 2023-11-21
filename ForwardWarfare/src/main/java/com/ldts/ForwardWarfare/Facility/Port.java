package com.ldts.ForwardWarfare.Facility;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Position;

public class Port implements Facility{
    public void draw(TextGraphics graphics, Position position) {
        graphics.setForegroundColor(new TextColor.RGB(51,255,255));
        graphics.putString(position.toTPos(),";");
    }

    @Override
    public void execute() {

    }
}
