package com.ldts.ForwardWarfare.Facility;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Position;

public class Base implements Facility {
    private boolean Ally;
    public Base(boolean Ally)
    {
        this.Ally=Ally;
    }
    public void draw(TextGraphics graphics, Position position) {
        if(Ally)
        {
            graphics.setForegroundColor(new TextColor.RGB(0,0,255));
            graphics.putString(position.toTPos(), ";");
        }
        else {
            graphics.setForegroundColor(new TextColor.RGB(204, 0,0));
            graphics.putString(position.toTPos(), ";");
        }
    }

    @Override
    public void execute() {

    }
}
