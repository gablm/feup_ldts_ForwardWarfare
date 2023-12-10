package com.ldts.ForwardWarfare.Element.Tile;

import com.googlecode.lanterna.TextColor;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Facility.Facility;

public interface Tile {
    TextColor getColor();
    boolean noCollision();
    Facility getFacility();
}
