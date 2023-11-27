package com.ldts.ForwardWarfare.UI.Component;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Position;


public class MapBox extends Component {
    public MapBox(TextColor backColor, TextColor forgColor, Position position, TerminalSize size, int BorderFadeIntencity) {
        super(backColor, forgColor, position, size, BorderFadeIntencity);
    }
    @Override
    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(backColor);
        graphics.drawRectangle(position.toTPos(),size,' ');
    }
    private void resizemap()
    {

    }
}
