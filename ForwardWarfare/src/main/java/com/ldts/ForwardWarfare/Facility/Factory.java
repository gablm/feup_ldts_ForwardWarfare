package com.ldts.ForwardWarfare.Facility;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Position;

public class Factory implements Facility{
    public void draw(TextGraphics graphics, Position position) {
        graphics.setForegroundColor(new TextColor.RGB(32,32,32));
        graphics.putString(position.toTPos(),"`");
    }

    @Override
    public void execute() {

    }
}
