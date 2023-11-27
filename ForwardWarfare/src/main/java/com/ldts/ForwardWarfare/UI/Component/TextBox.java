package com.ldts.ForwardWarfare.UI.Component;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Position;


public class TextBox extends Component{
    String Text;
    public TextBox(TextColor backColor, TextColor forgColor, Position position, TerminalSize size, int BorderFadeIntencity,String Text) {
        super(backColor, forgColor, position, size, BorderFadeIntencity);
        this.Text=Text;
    }

    @Override
    public void draw(TextGraphics graphics) {
        int textsize=0;
        int x= position.getX();
        int y= position.getY();
        char [] texto=Text.toCharArray();
        while (textsize<Text.length())
        {
            int linesize=0;
            int wordsize=0;
            StringBuilder line=new StringBuilder();
            StringBuilder Word=new StringBuilder();

            if (texto[textsize]!=' ')
            {
                wordsize++;
                Word.append(texto[textsize]);
            }
            else
            {
                if (linesize+wordsize<=size.getColumns())
                {
                    line.append(Word);
                }
                else
                {
                    graphics.putString(new TerminalPosition(x,y),line.toString());
                    y++;
                    
                }
            }
            textsize++;
        }
    }
}
