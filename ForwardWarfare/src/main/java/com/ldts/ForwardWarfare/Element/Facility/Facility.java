package com.ldts.ForwardWarfare.Element.Facility;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Position;

public interface Facility {
    void draw(TextGraphics graphics, Position position);
    void execute();
}
