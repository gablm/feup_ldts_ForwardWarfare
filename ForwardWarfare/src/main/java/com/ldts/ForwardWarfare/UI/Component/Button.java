package com.ldts.ForwardWarfare.UI.Component;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Position;

import java.nio.file.WatchEvent;

public class Button extends Component{
    private String Lable;
    private int BorderFadeIntencity;
    private TextColor BorderColor;
    public Button(TextColor backColor, TextColor forgColor, Position position, TerminalSize size, String Lable,int BorderFadeIntencity)
    {
        super(backColor,forgColor,position,size);
        this.Lable=Lable;
        this.BorderFadeIntencity=BorderFadeIntencity;
        setupbord();
    }
    private void setupbord()
    {
        int r,g,b;
        if(backColor.getRed()-BorderFadeIntencity>0)
        {
            r=backColor.getRed()-BorderFadeIntencity;
        } else{
            r=backColor.getRed();
        }

        if(backColor.getGreen()-BorderFadeIntencity>0)
        {
            g=backColor.getGreen()-BorderFadeIntencity;
        } else{
            g=backColor.getGreen();
        }

        if(backColor.getBlue()-BorderFadeIntencity>0)
        {
            b=backColor.getBlue()-BorderFadeIntencity;
        } else{
            b=backColor.getBlue();
        }
        BorderColor=new TextColor.RGB(r,g,b);
    }
    @Override
    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(backColor);
        graphics.setForegroundColor(forgColor);
        graphics.enableModifiers(SGR.BOLD);

        for(int x=1;x<size.getColumns()-1;x++)
        {
            for (int y=1;y<size.getRows()-1;y++)
            {
                graphics.putString(new TerminalPosition(x+ position.getX(),y+ position.getY())," ");
            }
        }
        graphics.putString(new TerminalPosition(position.getX()+(size.getColumns()/2)-(Lable.length()/2), position.getY()+(size.getRows()/2)),Lable);

        graphics.setBackgroundColor(BorderColor);

        for (int y=0; y < size.getRows(); y++)
        {
            graphics.putString(new TerminalPosition(position.getX(),y+ position.getY())," ");
            graphics.putString(new TerminalPosition(position.getX()+size.getColumns()-1,y+ position.getY())," ");
        }
        for(int x=0;x<size.getColumns();x++)
        {
            graphics.putString(new TerminalPosition(x+ position.getX(),position.getY())," ");
            graphics.putString(new TerminalPosition(x+ position.getX(),position.getY()+size.getRows()-1)," ");
        }
    }
}
