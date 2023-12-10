package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.ldts.ForwardWarfare.Element.Playable.Air.BomberPlane;
import com.ldts.ForwardWarfare.Element.Playable.Air.FighterPlane;
import com.ldts.ForwardWarfare.Element.Playable.Air.LightHelicopter;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Element.Playable.Water.FighterSubmarine;
import com.ldts.ForwardWarfare.Element.Position;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;

public class PlayableAirTest {

    @Test
    public void BomberPlaneDefaultTest() {
        Playable bomberPlane = new BomberPlane(new Position(1,1));

        Assertions.assertEquals(2, bomberPlane.getAttackRadius());
        Assertions.assertEquals(3, bomberPlane.getMaxMoves());
        Assertions.assertEquals(125, bomberPlane.getHp());
        Assertions.assertEquals(200, bomberPlane.getDamage());
        Assertions.assertEquals("Air", bomberPlane.getType());
        Assertions.assertFalse(bomberPlane.hasMoved());

        Assertions.assertFalse(bomberPlane.canAttack(new FighterSubmarine(null)));
        Assertions.assertTrue(bomberPlane.canAttack(new FighterPlane(null)));
        Assertions.assertEquals(bomberPlane.getPosition(), new Position(1,1));

        TextGraphics graphics = Mockito.mock(TextGraphics.class);
        bomberPlane.draw(graphics);

        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(new TextColor.RGB(80,80,80));
        Mockito.verify(graphics, Mockito.never()).enableModifiers(Mockito.any(SGR.class));
        Mockito.verify(graphics, Mockito.never()).setModifiers(Mockito.any());
        Mockito.verify(graphics, Mockito.times(1)).putString(bomberPlane.getPosition().toTPos(), "&");

        bomberPlane.setHP(20);
        Assertions.assertEquals(20, bomberPlane.getHp());
        bomberPlane.setHasMoved(true);
        Assertions.assertTrue(bomberPlane.hasMoved());
    }

    @Test
    public void BomberPlaneRandomTest() {
        Random random = new Random();
        int x = random.nextInt(0, 25), y = random.nextInt(0, 25);
        Playable bomberPlane = new BomberPlane(new Position(x,y));

        Assertions.assertEquals(bomberPlane.getPosition(), new Position(x,y));

        TextGraphics graphics = Mockito.mock(TextGraphics.class);
        int r = random.nextInt(0, 256), g = random.nextInt(0, 256), b = random.nextInt(0, 256);
        TextColor color = new TextColor.RGB(r, g, b);
        bomberPlane.setForegroundColor(color);
        bomberPlane.draw(graphics);

        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(color);
        Mockito.verify(graphics, Mockito.never()).enableModifiers(Mockito.any(SGR.class));
        Mockito.verify(graphics, Mockito.never()).setModifiers(Mockito.any());
        Mockito.verify(graphics, Mockito.times(1)).putString(bomberPlane.getPosition().toTPos(), "&");
    }


    @Test
    public void FighterPlaneDefaultTest() {
        Playable fighterPlane = new FighterPlane(new Position(1,1));

        Assertions.assertEquals(1, fighterPlane.getAttackRadius());
        Assertions.assertEquals(fighterPlane.getPosition(), new Position(1,1));

        TextGraphics graphics = Mockito.mock(TextGraphics.class);
        fighterPlane.draw(graphics);

        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(new TextColor.RGB(80,80,80));
        Mockito.verify(graphics, Mockito.never()).enableModifiers(Mockito.any(SGR.class));
        Mockito.verify(graphics, Mockito.never()).setModifiers(Mockito.any());
        Mockito.verify(graphics, Mockito.times(1)).putString(fighterPlane.getPosition().toTPos(), "%");
    }

    @Test
    public void FighterPlaneRandomTest() {
        Random random = new Random();
        int x = random.nextInt(0, 25), y = random.nextInt(0, 25);
        Playable fighterPlane = new FighterPlane(new Position(x,y));

        Assertions.assertEquals(fighterPlane.getPosition(), new Position(x,y));

        TextGraphics graphics = Mockito.mock(TextGraphics.class);
        int r = random.nextInt(0, 256), g = random.nextInt(0, 256), b = random.nextInt(0, 256);
        TextColor color = new TextColor.RGB(r, g, b);
        fighterPlane.setForegroundColor(color);
        fighterPlane.draw(graphics);

        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(color);
        Mockito.verify(graphics, Mockito.never()).enableModifiers(Mockito.any(SGR.class));
        Mockito.verify(graphics, Mockito.never()).setModifiers(Mockito.any());
        Mockito.verify(graphics, Mockito.times(1)).putString(fighterPlane.getPosition().toTPos(), "%");
    }

    @Test
    public void LightHelicopterDefaultTest() {
        Playable lightHelicopter = new LightHelicopter(new Position(1,1));

        Assertions.assertEquals(lightHelicopter.getPosition(), new Position(1,1));

        TextGraphics graphics = Mockito.mock(TextGraphics.class);
        lightHelicopter.draw(graphics);

        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(new TextColor.RGB(80,80,80));
        Mockito.verify(graphics, Mockito.never()).enableModifiers(Mockito.any(SGR.class));
        Mockito.verify(graphics, Mockito.never()).setModifiers(Mockito.any());
        Mockito.verify(graphics, Mockito.times(1)).putString(lightHelicopter.getPosition().toTPos(), "[");
    }

    @Test
    public void LightHelicopterRandomTest() {
        Random random = new Random();
        int x = random.nextInt(0, 25), y = random.nextInt(0, 25);
        Playable lightHelicopter = new LightHelicopter(new Position(x,y));

        Assertions.assertEquals(lightHelicopter.getPosition(), new Position(x,y));

        TextGraphics graphics = Mockito.mock(TextGraphics.class);
        int r = random.nextInt(0, 256), g = random.nextInt(0, 256), b = random.nextInt(0, 256);
        TextColor color = new TextColor.RGB(r, g, b);
        lightHelicopter.setForegroundColor(color);
        lightHelicopter.draw(graphics);

        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(color);
        Mockito.verify(graphics, Mockito.never()).enableModifiers(Mockito.any(SGR.class));
        Mockito.verify(graphics, Mockito.never()).setModifiers(Mockito.any());
        Mockito.verify(graphics, Mockito.times(1)).putString(lightHelicopter.getPosition().toTPos(), "[");
    }
}
