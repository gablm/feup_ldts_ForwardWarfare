package com.ldts.ForwardWarfare.UI.Component;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Map.Map;


public class MapBox extends Component {
    private Map mapa;
    private TextColor BorderColor;
    public MapBox(TextColor backColor, TextColor forgColor, Position position, TerminalSize size, int BorderFadeIntencity,Map map) {
        super(backColor, forgColor, position, size, BorderFadeIntencity);
        this.mapa=map;
        for (Element e:mapa.getElements())
        {
            e.setPosition(new Position(e.getPosition().getX()+position.getX()+1,e.getPosition().getY()+ position.getY()+1));
        }
    }
    private void setupbord() {
        int r, g, b;
        if (backColor.getRed() - BorderFadeIntencity > 0) {
            r = backColor.getRed() - BorderFadeIntencity;
        } else {
            r = backColor.getRed();
        }

        if (backColor.getGreen() - BorderFadeIntencity > 0) {
            g = backColor.getGreen() - BorderFadeIntencity;
        } else {
            g = backColor.getGreen();
        }

        if (backColor.getBlue() - BorderFadeIntencity > 0) {
            b = backColor.getBlue() - BorderFadeIntencity;
        } else {
            b = backColor.getBlue();
        }
        BorderColor = new TextColor.RGB(r, g, b);
    }
    @Override
    public void draw(TextGraphics graphics) {
        setupbord();
        graphics.setBackgroundColor(BorderColor);
        graphics.drawRectangle(position.toTPos(),size,' ');
        for (Element e:mapa.getElements())
        {
            e.draw(graphics, TextColor.ANSI.BLACK);
        }
    }
}
