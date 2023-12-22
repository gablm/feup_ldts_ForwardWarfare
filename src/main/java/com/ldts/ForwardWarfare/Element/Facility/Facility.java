package com.ldts.ForwardWarfare.Element.Facility;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Position;

public interface Facility {
    void draw(TextGraphics graphics, Position position);
    void setTextColor(TextColor color);
    void execute();
    Boolean getUsed();
}
