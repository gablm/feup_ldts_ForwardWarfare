package com.ldts.ForwardWarfare.UI.Component;


import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Position;

public abstract class Component {
     protected Position position;
     protected TextColor backColor;
     protected TextColor forgColor;
     protected TerminalSize size;
     public Component(TextColor backColor,TextColor forgColor,Position position,TerminalSize size)
     {
         this.backColor=backColor;
         this.forgColor=forgColor;
         this.position=position;
         this.size=size;
     }
     public abstract void draw(TextGraphics graphics);
}
