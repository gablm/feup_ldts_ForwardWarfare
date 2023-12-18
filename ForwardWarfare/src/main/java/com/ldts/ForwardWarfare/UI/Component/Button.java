package com.ldts.ForwardWarfare.UI.Component;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Position;

public class Button extends Component{
    private String label;
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    private TextColor BorderColor;
    public Button(TextColor backColor, TextColor forgColor, Position position, TerminalSize size, String label, int BorderFadeIntencity)
    {
        super(backColor,forgColor,position,size,BorderFadeIntencity);
        this.label = label;
    }
    private void setupBoard()
    {
        int r = backColor.getRed() - BorderFadeIntencity;
        if (r <= 0)
            r = backColor.getRed();

        int g = backColor.getGreen() - BorderFadeIntencity;
        if (g <= 0)
            g = backColor.getGreen();

        int b = backColor.getBlue() - BorderFadeIntencity;
        if (b <= 0)
            b = backColor.getBlue();
        BorderColor = new TextColor.RGB(r,g,b);
    }
    @Override
    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(backColor);
        graphics.setForegroundColor(forgColor);
        graphics.enableModifiers(SGR.BOLD);
        setupBoard();

        for(int x = 1; x < size.getColumns() - 1; x++)
        {
            for (int y = 1; y < size.getRows() - 1; y++)
                graphics.putString(new TerminalPosition(x + position.getX(),y + position.getY())," ");
        }

        if(label.length() % 2 == 0)
            graphics.putString(new TerminalPosition(position.getX() + (size.getColumns() / 2) - (label.length() / 2)+1,
                    position.getY() + (size.getRows() / 2)), label);
        else
            graphics.putString(new TerminalPosition(position.getX() + (size.getColumns() / 2) - (label.length() / 2),
                    position.getY() + (size.getRows() / 2)), label);
        graphics.setBackgroundColor(BorderColor);
        graphics.drawRectangle(position.toTPos(),size,' ');
    }
}
