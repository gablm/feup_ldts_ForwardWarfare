package com.ldts.ForwardWarfare.Element.Facility;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Position;

public class Base implements Facility {
    TextColor textColor;

    public TextColor getTextColor() {
        return textColor;
    }

    public Base(TextColor textColor)
    {
        this.textColor=textColor;
    }
    public void draw(TextGraphics graphics, Position position) {
            graphics.setForegroundColor(textColor);
            graphics.putString(position.toTPos(), ";");
    }


}
