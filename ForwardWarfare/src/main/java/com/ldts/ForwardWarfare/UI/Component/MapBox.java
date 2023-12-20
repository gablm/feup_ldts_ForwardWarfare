package com.ldts.ForwardWarfare.UI.Component;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.Map.MapParseException;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;


public class MapBox extends Component {
    private Map map;
    private Map oldMap;
    private TextColor BorderColor;
    public MapBox(TextColor backColor, TextColor forgColor, Position position, TerminalSize size, int BorderFadeIntencity, String mapName) throws FileNotFoundException, MapParseException, URISyntaxException {
        super(backColor, forgColor, position, size, BorderFadeIntencity);
        this.map = new Map(mapName);
        this.oldMap = new Map(mapName);
        BorderColor= backColor;
        for (Element e: this.map.getElements())
        {
            e.setPosition(new Position(e.getPosition().getX()+position.getX()+1,e.getPosition().getY()+ position.getY()+1));
        }
    }
    private void setupBoard() {
        int r = backColor.getRed() - BorderFadeIntencity;
        if (r <= 0)
            r = backColor.getRed();

        int g = backColor.getGreen() - BorderFadeIntencity;
        if (g <= 0)
            g = backColor.getGreen();

        int b = backColor.getBlue() - BorderFadeIntencity;
        if (b <= 0)
            b = backColor.getBlue();
        BorderColor = new TextColor.RGB(r, g, b);
    }
    @Override
    public void draw(TextGraphics graphics) {
        setupBoard();
        graphics.setBackgroundColor(BorderColor);
        graphics.drawRectangle(position.toTPos(),size,' ');

        for (Element e: map.getElements()) {
                e.draw(graphics);
        }
    }

    public Map getMap() {
        return oldMap;
    }

    public void setOldMap(Map oldMap) {
        this.oldMap = oldMap;
    }

    public Map getnewMap() {
        return map;
    }

    public TextColor getBorderColor() {
        return BorderColor;
    }
}
