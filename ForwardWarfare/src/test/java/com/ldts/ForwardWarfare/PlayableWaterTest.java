package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Playable.Air.FighterPlane;
import com.ldts.ForwardWarfare.Element.Playable.Air.LightHelicopter;
import com.ldts.ForwardWarfare.Element.Playable.Ground.HeavyTank;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Element.Playable.Water.FighterSubmarine;
import com.ldts.ForwardWarfare.Element.Playable.Water.LightBoat;
import com.ldts.ForwardWarfare.Element.Playable.Water.MortarBoat;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Tile.Fields;
import com.ldts.ForwardWarfare.Element.Tile.Water;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;

public class PlayableWaterTest {

    @Test
    public void FighterSubmarineDefaultTest() {
        Playable fighterSubmarine = new FighterSubmarine(new Position(1,1));

        Assertions.assertEquals(fighterSubmarine.getPosition(), new Position(1,1));
        Assertions.assertEquals(1, fighterSubmarine.getAttackRadius());
        Assertions.assertEquals(6, fighterSubmarine.getMaxMoves());
        Assertions.assertEquals(150, fighterSubmarine.getHp());
        Assertions.assertEquals(75, fighterSubmarine.getDamage());
        Assertions.assertEquals("Water", fighterSubmarine.getType());
        Assertions.assertTrue(fighterSubmarine.canMove(new Water(null, null)));
        Assertions.assertFalse(fighterSubmarine.canMove(new Fields(null, null)));
        Assertions.assertFalse(fighterSubmarine.hasMoved());

        Assertions.assertTrue(fighterSubmarine.canAttack(new FighterSubmarine(null)));
        Assertions.assertFalse(fighterSubmarine.canAttack(new FighterPlane(null)));

        TextGraphics graphics = Mockito.mock(TextGraphics.class);
        fighterSubmarine.draw(graphics);

        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(new TextColor.RGB(80,80,80));
        Mockito.verify(graphics, Mockito.never()).enableModifiers(Mockito.any(SGR.class));
        Mockito.verify(graphics, Mockito.never()).setModifiers(Mockito.any());
        Mockito.verify(graphics, Mockito.times(1)).putString(fighterSubmarine.getPosition().toTPos(), "=");

        fighterSubmarine.setHP(20);
        Assertions.assertEquals(20, fighterSubmarine.getHp());
        fighterSubmarine.setHasMoved(true);
        Assertions.assertTrue(fighterSubmarine.hasMoved());
    }

    @Test
    public void FighterSubmarineRandomTest() {
        Random random = new Random();
        int x = random.nextInt(0, 25), y = random.nextInt(0, 25);
        Playable fighterSubmarine = new FighterSubmarine(new Position(x,y));

        Assertions.assertEquals(fighterSubmarine.getPosition(), new Position(x,y));

        TextGraphics graphics = Mockito.mock(TextGraphics.class);
        int r = random.nextInt(0, 256), g = random.nextInt(0, 256), b = random.nextInt(0, 256);
        TextColor color = new TextColor.RGB(r, g, b);
        fighterSubmarine.setForegroundColor(color);
        fighterSubmarine.draw(graphics);

        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(color);
        Mockito.verify(graphics, Mockito.never()).enableModifiers(Mockito.any(SGR.class));
        Mockito.verify(graphics, Mockito.never()).setModifiers(Mockito.any());
        Mockito.verify(graphics, Mockito.times(1)).putString(fighterSubmarine.getPosition().toTPos(), "=");
    }

    @Test
    public void LightBoatDefaultTest() {
        Playable lightBoat = new LightBoat(new Position(1,1));

        Assertions.assertEquals(lightBoat.getPosition(), new Position(1,1));
        Assertions.assertEquals(1, lightBoat.getAttackRadius());
        Assertions.assertEquals(5, lightBoat.getMaxMoves());
        Assertions.assertEquals(100, lightBoat.getHp());
        Assertions.assertEquals(50, lightBoat.getDamage());
        Assertions.assertEquals("Water", lightBoat.getType());
        Assertions.assertTrue(lightBoat.canMove(new Water(null, null)));
        Assertions.assertFalse(lightBoat.canMove(new Fields(null, null)));
        Assertions.assertFalse(lightBoat.hasMoved());

        Assertions.assertTrue(lightBoat.canAttack(new FighterSubmarine(null)));
        Assertions.assertTrue(lightBoat.canAttack(new HeavyTank(null)));
        Assertions.assertFalse(lightBoat.canAttack(new FighterPlane(null)));

        TextGraphics graphics = Mockito.mock(TextGraphics.class);
        lightBoat.draw(graphics);

        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(new TextColor.RGB(80,80,80));
        Mockito.verify(graphics, Mockito.never()).enableModifiers(Mockito.any(SGR.class));
        Mockito.verify(graphics, Mockito.never()).setModifiers(Mockito.any());
        Mockito.verify(graphics, Mockito.times(1)).putString(lightBoat.getPosition().toTPos(), "<");

        lightBoat.setHP(20);
        Assertions.assertEquals(20, lightBoat.getHp());
        lightBoat.setHasMoved(true);
        Assertions.assertTrue(lightBoat.hasMoved());
    }

    @Test
    public void LightBoatRandomTest() {
        Random random = new Random();
        int x = random.nextInt(0, 25), y = random.nextInt(0, 25);
        Playable lightBoat = new LightBoat(new Position(x,y));

        Assertions.assertEquals(lightBoat.getPosition(), new Position(x,y));

        TextGraphics graphics = Mockito.mock(TextGraphics.class);
        int r = random.nextInt(0, 256), g = random.nextInt(0, 256), b = random.nextInt(0, 256);
        TextColor color = new TextColor.RGB(r, g, b);
        lightBoat.setForegroundColor(color);
        lightBoat.draw(graphics);

        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(color);
        Mockito.verify(graphics, Mockito.never()).enableModifiers(Mockito.any(SGR.class));
        Mockito.verify(graphics, Mockito.never()).setModifiers(Mockito.any());
        Mockito.verify(graphics, Mockito.times(1)).putString(lightBoat.getPosition().toTPos(), "<");
    }

    @Test
    public void MortarBoatDefaultTest() {
        Playable mortarBoat = new MortarBoat(new Position(1,1));

        Assertions.assertEquals(mortarBoat.getPosition(), new Position(1,1));
        Assertions.assertEquals(2, mortarBoat.getAttackRadius());
        Assertions.assertEquals(3, mortarBoat.getMaxMoves());
        Assertions.assertEquals(150, mortarBoat.getHp());
        Assertions.assertEquals(100, mortarBoat.getDamage());
        Assertions.assertEquals("Water", mortarBoat.getType());
        Element test = Mockito.mock(Element.class);
        Assertions.assertTrue(mortarBoat.canMove(new Water(null, null)));
        Assertions.assertFalse(mortarBoat.canMove(new Fields(null, null)));
        Assertions.assertFalse(mortarBoat.hasMoved());

        Assertions.assertTrue(mortarBoat.canAttack(new FighterSubmarine(null)));
        Assertions.assertTrue(mortarBoat.canAttack(new HeavyTank(null)));
        Assertions.assertFalse(mortarBoat.canAttack(new FighterPlane(null)));

        TextGraphics graphics = Mockito.mock(TextGraphics.class);
        mortarBoat.draw(graphics);

        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(new TextColor.RGB(80,80,80));
        Mockito.verify(graphics, Mockito.never()).enableModifiers(Mockito.any(SGR.class));
        Mockito.verify(graphics, Mockito.never()).setModifiers(Mockito.any());
        Mockito.verify(graphics, Mockito.times(1)).putString(mortarBoat.getPosition().toTPos(), "'");

        mortarBoat.setHP(20);
        Assertions.assertEquals(20, mortarBoat.getHp());
        mortarBoat.setHasMoved(true);
        Assertions.assertTrue(mortarBoat.hasMoved());
    }

    @Test
    public void MortarBoatRandomTest() {
        Random random = new Random();
        int x = random.nextInt(0, 25), y = random.nextInt(0, 25);
        Playable mortarBoat = new MortarBoat(new Position(x,y));

        Assertions.assertEquals(mortarBoat.getPosition(), new Position(x,y));

        TextGraphics graphics = Mockito.mock(TextGraphics.class);
        int r = random.nextInt(0, 256), g = random.nextInt(0, 256), b = random.nextInt(0, 256);
        TextColor color = new TextColor.RGB(r, g, b);
        mortarBoat.setForegroundColor(color);
        mortarBoat.draw(graphics);

        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(color);
        Mockito.verify(graphics, Mockito.never()).enableModifiers(Mockito.any(SGR.class));
        Mockito.verify(graphics, Mockito.never()).setModifiers(Mockito.any());
        Mockito.verify(graphics, Mockito.times(1)).putString(mortarBoat.getPosition().toTPos(), "'");
    }
}
