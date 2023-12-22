package com.ldts.ForwardWarfare.UI.Component;


import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Position;

public abstract class Component {
     protected Position position;
     private boolean fixBorder = false;
     protected TextColor backColor;
     protected TextColor forgColor;
     protected TerminalSize size;
     protected int borderFadeIntensity;
    public int getBorderFadeIntensity() {
         return borderFadeIntensity;
       }

    public void setBorderFadeIntensity(int borderFadeIntensity) {
        if(!fixBorder) {
            this.borderFadeIntensity = borderFadeIntensity;
        }
     }

    public void setFixBorder(boolean fixBorder) {
        this.fixBorder = fixBorder;
    }

    public Component(TextColor backColor, TextColor forgColor, Position position, TerminalSize size, int borderFadeIntensity)
    {
         this.backColor = backColor;
         this.forgColor = forgColor;
         this.position = position;
         this.size = size;
         this.borderFadeIntensity = borderFadeIntensity;
    }
    public abstract void draw(TextGraphics graphics);

    public TextColor getBackColor() {
        return backColor;
    }

    public TerminalSize getSize() {
        return size;
    }

    public TextColor getForgColor() {
        return forgColor;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isFixBorder() {
        return fixBorder;
    }
}
