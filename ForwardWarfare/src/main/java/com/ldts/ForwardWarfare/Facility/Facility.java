package com.ldts.ForwardWarfare.Facility;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Position;

public interface Facility {
    void draw(TextGraphics graphics, Position position);
    void execute();
}
