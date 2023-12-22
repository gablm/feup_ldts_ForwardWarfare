package com.ldts.ForwardWarfare.UITest.ComponentsTest;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.UI.Component.Button;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ButtonTest {
    @Mock
    private TextGraphics textGraphicsMock;
    @Mock
    private Button button;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        button = new Button(TextColor.ANSI.CYAN,TextColor.ANSI.BLACK,new Position(0,0),new TerminalSize(10,10),"Test",50);
    }

    @Test
    public void Button_ConstructorTest(){
        Assertions.assertEquals(TextColor.ANSI.CYAN,button.getBackColor());
        Assertions.assertEquals(TextColor.ANSI.BLACK,button.getForgColor());
        Assertions.assertEquals(new Position(0,0),button.getPosition());
        Assertions.assertEquals(new TerminalSize(10,10),button.getSize());
        Assertions.assertEquals(50,button.getBorderFadeIntensity());
        Assertions.assertFalse(button.isFixBorder());
    }

    @Test
    public void Button_drawpairlabletest(){
        button.draw(textGraphicsMock);
        Mockito.verify(textGraphicsMock, Mockito.times(1)).setBackgroundColor(TextColor.ANSI.CYAN);
        Mockito.verify(textGraphicsMock, Mockito.times(1)).setForegroundColor(TextColor.ANSI.BLACK);
        Mockito.verify(textGraphicsMock, Mockito.times(1)).enableModifiers(Mockito.any());
        Mockito.verify(textGraphicsMock).putString(new TerminalPosition(4,5),"Test");
    }

    @Test
    public void Button_drawoddlableTest(){
        button.setLabel("Teste");
        button.draw(textGraphicsMock);
        Mockito.verify(textGraphicsMock, Mockito.times(2)).setBackgroundColor(Mockito.any());
        Mockito.verify(textGraphicsMock, Mockito.times(1)).setForegroundColor(Mockito.any());
        Mockito.verify(textGraphicsMock, Mockito.times(1)).enableModifiers(Mockito.any());
        Mockito.verify(textGraphicsMock).putString(new TerminalPosition(3,5),"Teste");
    }
    @Test
    public void Buttondraw_defaultTest(){
        TextColor first= button.getBorderColor();
        button.draw(textGraphicsMock);
        Mockito.verify(textGraphicsMock, Mockito.times(2)).setBackgroundColor(Mockito.any());
        Mockito.verify(textGraphicsMock, Mockito.times(1)).setForegroundColor(Mockito.any());
        Mockito.verify(textGraphicsMock, Mockito.times(1)).enableModifiers(Mockito.any());
        Mockito.verify(textGraphicsMock).fillRectangle(new TerminalPosition(0,0),new TerminalSize(10,10),' ');
        Mockito.verify(textGraphicsMock).drawRectangle(new TerminalPosition(0,0),new TerminalSize(10,10),' ');
        Assertions.assertTrue(first.getBlue()>=button.getBorderColor().getBlue());
        Assertions.assertTrue(first.getGreen()>=button.getBorderColor().getGreen());
        Assertions.assertTrue(first.getRed()>=button.getBorderColor().getRed());
    }

    @Test
    public void Button_LabelTest()
    {
        button.setLabel("Teste");
        Assertions.assertEquals("Teste",button.getLabel());
    }
}
