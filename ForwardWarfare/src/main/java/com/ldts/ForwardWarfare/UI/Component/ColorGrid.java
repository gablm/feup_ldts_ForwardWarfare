package com.ldts.ForwardWarfare.UI.Component;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.ldts.ForwardWarfare.Element.Position;

import java.util.ArrayList;
import java.util.List;

public class ColorGrid extends Component{
    private TextColor BorderColor;
    private int highlight=75;
    private int normalborder=10;
    List<Button> ColorList=new ArrayList<>();
    private int c=0;
    private int s=-1;
    private List<TextColor> gridcolor=new ArrayList<>();
    public ColorGrid(TextColor backColor, TextColor forgColor, Position position, int BorderFadeIntencity) {
        super(backColor, forgColor, position, new TerminalSize(29,19), BorderFadeIntencity);
        ColorList.add(new Button(new TextColor.RGB(200, 0, 0), new TextColor.RGB(0, 0, 0), new Position(position.getX() + 1, position.getY() + 5), new TerminalSize(6, 6), " ", highlight));
        ColorList.add(new Button(new TextColor.RGB(0, 200, 0), new TextColor.RGB(0, 0, 0), new Position(position.getX() + 8, position.getY() + 5), new TerminalSize(6, 6), " ", normalborder));
        ColorList.add(new Button(new TextColor.RGB(200, 255, 0), new TextColor.RGB(0, 0, 0), new Position(position.getX() + 15, position.getY() + 5), new TerminalSize(6, 6), " ", normalborder));
        ColorList.add(new Button(new TextColor.RGB(255, 200, 0), new TextColor.RGB(0, 0, 0), new Position(position.getX() + 22, position.getY() + 5), new TerminalSize(6, 6), " ", normalborder));
        ColorList.add(new Button(new TextColor.RGB(0, 255, 255), new TextColor.RGB(0, 0, 0), new Position(position.getX() + 1, position.getY() + 12), new TerminalSize(6, 6), " ", normalborder));
        ColorList.add(new Button(new TextColor.RGB(0, 255, 0), new TextColor.RGB(0, 0, 0), new Position(position.getX() + 8, position.getY() + 12), new TerminalSize(6, 6), " ", normalborder));
        ColorList.add(new Button(new TextColor.RGB(0, 0, 255), new TextColor.RGB(0, 0, 0), new Position(position.getX() + 15, position.getY() + 12), new TerminalSize(6, 6), " ", normalborder));
        ColorList.add(new Button(new TextColor.RGB(255, 255, 0), new TextColor.RGB(0, 0, 0), new Position(position.getX() + 22, position.getY() + 12), new TerminalSize(6, 6), " ", normalborder));
    }
    private void jumprow(boolean up){
        if(up) {
            ColorList.get(c).setBorderFadeIntencity(normalborder);
            if (c+4 > ColorList.size() - 1) {
                c = 0;
            } else
                c+=4;
            ColorList.get(c).setBorderFadeIntencity(highlight);
        }
        else
        {
            ColorList.get(c).setBorderFadeIntencity(normalborder);
            if (c-4 < 0) {
                c = ColorList.size()-1;
            } else
                c-=4;
            ColorList.get(c).setBorderFadeIntencity(highlight);
        }
    }
    private void Buttonhighligted(boolean next)
    {
        if(next) {
            ColorList.get(c).setBorderFadeIntencity(normalborder);
            if (c+1 > ColorList.size() - 1) {
                c = 0;
            } else
                c++;
            ColorList.get(c).setBorderFadeIntencity(highlight);
        }
        else
        {
            ColorList.get(c).setBorderFadeIntencity(normalborder);
            if (c-1 < 0) {
                c = ColorList.size()-1;
            } else
                c--;
            ColorList.get(c).setBorderFadeIntencity(highlight);
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
    public void processKey( KeyStroke key) {
        if (key.getKeyType() == KeyType.ArrowUp)
        {
            jumprow(false);
        } else if (key.getKeyType() == KeyType.ArrowRight)
        {
            Buttonhighligted(true);
        } else if (key.getKeyType() == KeyType.ArrowLeft) {
            Buttonhighligted(false);
        }
        else if (key.getKeyType() == KeyType.ArrowDown)
        {
            jumprow(true);
        }else if(key.getKeyType()== KeyType.Enter)
        {
            SetColor();
        }
    }
    private void SetColor()
    {
        if(c!=s )
        {
            if(s>=0) {
                ColorList.get(s).setLable("");
            }
            s=c;
            ColorList.get(c).setLable("(X)");
        }

    }
    @Override
    public void draw(TextGraphics graphics) {
        setupbord();
        new Button(backColor,forgColor,new Position(position.getX(), position.getY()),new TerminalSize(29,5),"Color Grid",25).draw(graphics);
        for(Button b:ColorList)
        {
            b.draw(graphics);
        }
        graphics.enableModifiers(SGR.BOLD);
        graphics.setBackgroundColor(BorderColor);
        graphics.drawRectangle(position.toTPos(),size,' ');
    }
}
