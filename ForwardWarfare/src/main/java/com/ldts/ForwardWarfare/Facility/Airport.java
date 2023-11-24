package com.ldts.ForwardWarfare.Facility;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Position;

public class Airport implements Facility{
    public void draw(TextGraphics graphics, Position position) {
        graphics.setForegroundColor(new TextColor.RGB(255,128,0));
        graphics.putString(position.toTPos(),">");
    }

    @Override
    public void execute() {

    }
}
