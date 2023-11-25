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

    public String getLable() {
        return Lable;
    }

    public void setLable(String lable) {
        Lable = lable;
    }

    private TextColor BorderColor;
    public Button(TextColor backColor, TextColor forgColor, Position position, TerminalSize size, String Lable,int BorderFadeIntencity)
    {
        super(backColor,forgColor,position,size,BorderFadeIntencity);
        this.Lable=Lable;
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
        setupbord();

        for(int x=1;x<size.getColumns()-1;x++)
        {
            for (int y=1;y<size.getRows()-1;y++)
            {
                graphics.putString(new TerminalPosition(x+ position.getX(),y+ position.getY())," ");
            }
        }
        if(Lable.length()%2==0) {
            graphics.putString(new TerminalPosition(position.getX() + (size.getColumns() / 2) - (Lable.length() / 2)+1, position.getY() + (size.getRows() / 2)), Lable);
        }else
        {
            graphics.putString(new TerminalPosition(position.getX() + (size.getColumns() / 2) - (Lable.length() / 2), position.getY() + (size.getRows() / 2)), Lable);
        }
        graphics.setBackgroundColor(BorderColor);

        graphics.drawRectangle(position.toTPos(),size,' ');
    }
}
