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
     protected int BorderFadeIntencity;
    public int getBorderFadeIntencity() {
         return BorderFadeIntencity;
       }

    public void setBorderFadeIntensity(int borderFadeIntencity) {
        if(!fixBorder) {
            BorderFadeIntencity = borderFadeIntencity;
        }
     }

    public void setFixBorder(boolean fixBorder) {
        this.fixBorder = fixBorder;
    }

    public Component(TextColor backColor, TextColor forgColor, Position position, TerminalSize size, int BorderFadeIntencity)
    {
         this.backColor = backColor;
         this.forgColor = forgColor;
         this.position = position;
         this.size = size;
         this.BorderFadeIntencity = BorderFadeIntencity;
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
}
