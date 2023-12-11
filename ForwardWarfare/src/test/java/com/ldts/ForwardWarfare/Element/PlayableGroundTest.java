package com.ldts.ForwardWarfare.Element;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Playable.Air.FighterPlane;
import com.ldts.ForwardWarfare.Element.Playable.Ground.*;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Element.Playable.Water.FighterSubmarine;
import com.ldts.ForwardWarfare.Element.Playable.Water.LightBoat;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Tile.Fields;
import com.ldts.ForwardWarfare.Element.Tile.Water;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;

public class PlayableGroundTest {
    
    @Test
    public void AntiAirTankDefaultTest() {
        Playable antiAirTank = new AntiAirTank(new Position(1,1));

        Assertions.assertEquals(1, antiAirTank.getAttackRadius());
        Assertions.assertEquals(4, antiAirTank.getMaxMoves());
        Assertions.assertEquals(140, antiAirTank.getHp());
        Assertions.assertEquals(100, antiAirTank.getDamage());
        Assertions.assertEquals("Ground", antiAirTank.getType());
        Assertions.assertFalse(antiAirTank.canMove(new Water(null, null)));
        Assertions.assertTrue(antiAirTank.canMove(new Fields(null, null)));
        Assertions.assertFalse(antiAirTank.hasMoved());

        Assertions.assertTrue(antiAirTank.canAttack(new FighterSubmarine(null)));
        Assertions.assertTrue(antiAirTank.canAttack(new FighterPlane(null)));
        Assertions.assertEquals(antiAirTank.getPosition(), new Position(1,1));

        TextGraphics graphics = Mockito.mock(TextGraphics.class);
        antiAirTank.draw(graphics);

        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(new TextColor.RGB(80,80,80));
        Mockito.verify(graphics, Mockito.never()).enableModifiers(Mockito.any(SGR.class));
        Mockito.verify(graphics, Mockito.never()).setModifiers(Mockito.any());
        Mockito.verify(graphics, Mockito.times(1)).putString(antiAirTank.getPosition().toTPos(), "+");

        antiAirTank.setHP(20);
        Assertions.assertEquals(20, antiAirTank.getHp());
        antiAirTank.setHasMoved(true);
        Assertions.assertTrue(antiAirTank.hasMoved());
    }

    @Test
    public void AntiAirTankRandomTest() {
        Random random = new Random();
        int x = random.nextInt(0, 25), y = random.nextInt(0, 25);
        Playable antiAirTank = new AntiAirTank(new Position(x,y));

        Assertions.assertEquals(antiAirTank.getPosition(), new Position(x,y));

        TextGraphics graphics = Mockito.mock(TextGraphics.class);
        int r = random.nextInt(0, 256), g = random.nextInt(0, 256), b = random.nextInt(0, 256);
        TextColor color = new TextColor.RGB(r, g, b);
        antiAirTank.setForegroundColor(color);
        antiAirTank.draw(graphics);

        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(color);
        Mockito.verify(graphics, Mockito.never()).enableModifiers(Mockito.any(SGR.class));
        Mockito.verify(graphics, Mockito.never()).setModifiers(Mockito.any());
        Mockito.verify(graphics, Mockito.times(1)).putString(antiAirTank.getPosition().toTPos(), "+");
    }


    @Test
    public void HeavyPersonDefaultTest() {
        Playable heavyPerson = new HeavyPerson(new Position(1,1));

        Assertions.assertEquals(1, heavyPerson.getAttackRadius());
        Assertions.assertEquals(3, heavyPerson.getMaxMoves());
        Assertions.assertEquals(100, heavyPerson.getHp());
        Assertions.assertEquals(75, heavyPerson.getDamage());
        Assertions.assertEquals("Ground", heavyPerson.getType());
        Assertions.assertFalse(heavyPerson.canMove(new Water(null, null)));
        Assertions.assertTrue(heavyPerson.canMove(new Fields(null, null)));
        Assertions.assertFalse(heavyPerson.hasMoved());

        Assertions.assertFalse(heavyPerson.canAttack(new FighterSubmarine(null)));
        Assertions.assertFalse(heavyPerson.canAttack(new FighterPlane(null)));
        Assertions.assertTrue(heavyPerson.canAttack(new LightBoat(null)));
        Assertions.assertTrue(heavyPerson.canAttack(new HeavyTank(null)));
        Assertions.assertEquals(heavyPerson.getPosition(), new Position(1,1));

        TextGraphics graphics = Mockito.mock(TextGraphics.class);
        heavyPerson.draw(graphics);

        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(new TextColor.RGB(80,80,80));
        Mockito.verify(graphics, Mockito.never()).enableModifiers(Mockito.any(SGR.class));
        Mockito.verify(graphics, Mockito.never()).setModifiers(Mockito.any());
        Mockito.verify(graphics, Mockito.times(1)).putString(heavyPerson.getPosition().toTPos(), "_");

        heavyPerson.setHP(20);
        Assertions.assertEquals(20, heavyPerson.getHp());
        heavyPerson.setHasMoved(true);
        Assertions.assertTrue(heavyPerson.hasMoved());
    }

    @Test
    public void HeavyPersonRandomTest() {
        Random random = new Random();
        int x = random.nextInt(0, 25), y = random.nextInt(0, 25);
        Playable heavyPerson = new HeavyPerson(new Position(x,y));

        Assertions.assertEquals(heavyPerson.getPosition(), new Position(x,y));

        TextGraphics graphics = Mockito.mock(TextGraphics.class);
        int r = random.nextInt(0, 256), g = random.nextInt(0, 256), b = random.nextInt(0, 256);
        TextColor color = new TextColor.RGB(r, g, b);
        heavyPerson.setForegroundColor(color);
        heavyPerson.draw(graphics);

        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(color);
        Mockito.verify(graphics, Mockito.never()).enableModifiers(Mockito.any(SGR.class));
        Mockito.verify(graphics, Mockito.never()).setModifiers(Mockito.any());
        Mockito.verify(graphics, Mockito.times(1)).putString(heavyPerson.getPosition().toTPos(), "_");
    }

    @Test
    public void HeavyTankDefaultTest() {
        Playable heavyTank = new HeavyTank(new Position(1,1));

        Assertions.assertEquals(1, heavyTank.getAttackRadius());
        Assertions.assertEquals(2, heavyTank.getMaxMoves());
        Assertions.assertEquals(175, heavyTank.getHp());
        Assertions.assertEquals(100, heavyTank.getDamage());
        Assertions.assertEquals("Ground", heavyTank.getType());
        Assertions.assertFalse(heavyTank.canMove(new Water(null, null)));
        Assertions.assertTrue(heavyTank.canMove(new Fields(null, null)));
        Assertions.assertFalse(heavyTank.hasMoved());

        Assertions.assertFalse(heavyTank.canAttack(new FighterSubmarine(null)));
        Assertions.assertFalse(heavyTank.canAttack(new FighterPlane(null)));
        Assertions.assertTrue(heavyTank.canAttack(new LightBoat(null)));
        Assertions.assertTrue(heavyTank.canAttack(new HeavyTank(null)));
        Assertions.assertEquals(heavyTank.getPosition(), new Position(1,1));

        TextGraphics graphics = Mockito.mock(TextGraphics.class);
        heavyTank.draw(graphics);

        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(new TextColor.RGB(80,80,80));
        Mockito.verify(graphics, Mockito.never()).enableModifiers(Mockito.any(SGR.class));
        Mockito.verify(graphics, Mockito.never()).setModifiers(Mockito.any());
        Mockito.verify(graphics, Mockito.times(1)).putString(heavyTank.getPosition().toTPos(), "#");

        heavyTank.setHP(20);
        Assertions.assertEquals(20, heavyTank.getHp());
        heavyTank.setHasMoved(true);
        Assertions.assertTrue(heavyTank.hasMoved());
    }

    @Test
    public void HeavyTankRandomTest() {
        Random random = new Random();
        int x = random.nextInt(0, 25), y = random.nextInt(0, 25);
        Playable heavyTank = new HeavyTank(new Position(x,y));

        Assertions.assertEquals(heavyTank.getPosition(), new Position(x,y));

        TextGraphics graphics = Mockito.mock(TextGraphics.class);
        int r = random.nextInt(0, 256), g = random.nextInt(0, 256), b = random.nextInt(0, 256);
        TextColor color = new TextColor.RGB(r, g, b);
        heavyTank.setForegroundColor(color);
        heavyTank.draw(graphics);

        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(color);
        Mockito.verify(graphics, Mockito.never()).enableModifiers(Mockito.any(SGR.class));
        Mockito.verify(graphics, Mockito.never()).setModifiers(Mockito.any());
        Mockito.verify(graphics, Mockito.times(1)).putString(heavyTank.getPosition().toTPos(), "#");
    }

    @Test
    public void LightPersonDefaultTest() {
        Playable lightPerson = new LightPerson(new Position(1,1));

        Assertions.assertEquals(1, lightPerson.getAttackRadius());
        Assertions.assertEquals(5, lightPerson.getMaxMoves());
        Assertions.assertEquals(50, lightPerson.getHp());
        Assertions.assertEquals(50, lightPerson.getDamage());
        Assertions.assertEquals("Ground", lightPerson.getType());
        Assertions.assertFalse(lightPerson.canMove(new Water(null, null)));
        Assertions.assertTrue(lightPerson.canMove(new Fields(null, null)));
        Assertions.assertFalse(lightPerson.hasMoved());

        Assertions.assertFalse(lightPerson.canAttack(new FighterSubmarine(null)));
        Assertions.assertFalse(lightPerson.canAttack(new FighterPlane(null)));
        Assertions.assertTrue(lightPerson.canAttack(new LightBoat(null)));
        Assertions.assertTrue(lightPerson.canAttack(new HeavyTank(null)));
        Assertions.assertEquals(lightPerson.getPosition(), new Position(1,1));

        TextGraphics graphics = Mockito.mock(TextGraphics.class);
        lightPerson.draw(graphics);

        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(new TextColor.RGB(80,80,80));
        Mockito.verify(graphics, Mockito.never()).enableModifiers(Mockito.any(SGR.class));
        Mockito.verify(graphics, Mockito.never()).setModifiers(Mockito.any());
        Mockito.verify(graphics, Mockito.times(1)).putString(lightPerson.getPosition().toTPos(), "(");

        lightPerson.setHP(20);
        Assertions.assertEquals(20, lightPerson.getHp());
        lightPerson.setHasMoved(true);
        Assertions.assertTrue(lightPerson.hasMoved());
    }

    @Test
    public void LightPersonRandomTest() {
        Random random = new Random();
        int x = random.nextInt(0, 25), y = random.nextInt(0, 25);
        Playable lightPerson = new LightPerson(new Position(x,y));

        TextGraphics graphics = Mockito.mock(TextGraphics.class);
        int r = random.nextInt(0, 256), g = random.nextInt(0, 256), b = random.nextInt(0, 256);
        TextColor color = new TextColor.RGB(r, g, b);
        lightPerson.setForegroundColor(color);
        lightPerson.draw(graphics);

        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(color);
        Mockito.verify(graphics, Mockito.never()).enableModifiers(Mockito.any(SGR.class));
        Mockito.verify(graphics, Mockito.never()).setModifiers(Mockito.any());
        Mockito.verify(graphics, Mockito.times(1)).putString(lightPerson.getPosition().toTPos(), "(");
    }

    @Test
    public void LightTankDefaultTest() {
        Playable lightTank = new LightTank(new Position(1,1));

        Assertions.assertEquals(1, lightTank.getAttackRadius());
        Assertions.assertEquals(4, lightTank.getMaxMoves());
        Assertions.assertEquals(100, lightTank.getHp());
        Assertions.assertEquals(50, lightTank.getDamage());
        Assertions.assertEquals("Ground", lightTank.getType());
        Assertions.assertFalse(lightTank.canMove(new Water(null, null)));
        Assertions.assertTrue(lightTank.canMove(new Fields(null, null)));
        Assertions.assertFalse(lightTank.hasMoved());

        Assertions.assertFalse(lightTank.canAttack(new FighterSubmarine(null)));
        Assertions.assertFalse(lightTank.canAttack(new FighterPlane(null)));
        Assertions.assertTrue(lightTank.canAttack(new LightBoat(null)));
        Assertions.assertTrue(lightTank.canAttack(new HeavyTank(null)));
        Assertions.assertEquals(lightTank.getPosition(), new Position(1,1));

        TextGraphics graphics = Mockito.mock(TextGraphics.class);
        lightTank.draw(graphics);

        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(new TextColor.RGB(80,80,80));
        Mockito.verify(graphics, Mockito.never()).enableModifiers(Mockito.any(SGR.class));
        Mockito.verify(graphics, Mockito.never()).setModifiers(Mockito.any());
        Mockito.verify(graphics, Mockito.times(1)).putString(lightTank.getPosition().toTPos(), "$");

        lightTank.setHP(20);
        Assertions.assertEquals(20, lightTank.getHp());
        lightTank.setHasMoved(true);
        Assertions.assertTrue(lightTank.hasMoved());
    }

    @Test
    public void LightTankRandomTest() {
        Random random = new Random();
        int x = random.nextInt(0, 25), y = random.nextInt(0, 25);
        Playable lightTank = new LightTank(new Position(x,y));

        Assertions.assertEquals(lightTank.getPosition(), new Position(x,y));

        TextGraphics graphics = Mockito.mock(TextGraphics.class);
        int r = random.nextInt(0, 256), g = random.nextInt(0, 256), b = random.nextInt(0, 256);
        TextColor color = new TextColor.RGB(r, g, b);
        lightTank.setForegroundColor(color);
        lightTank.draw(graphics);

        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(color);
        Mockito.verify(graphics, Mockito.never()).enableModifiers(Mockito.any(SGR.class));
        Mockito.verify(graphics, Mockito.never()).setModifiers(Mockito.any());
        Mockito.verify(graphics, Mockito.times(1)).putString(lightTank.getPosition().toTPos(), "$");
    }

    @Test
    public void MortarPersonDefaultTest() {
        Playable mortarPerson = new MortarPerson(new Position(1,1));

        Assertions.assertEquals(2, mortarPerson.getAttackRadius());
        Assertions.assertEquals(2, mortarPerson.getMaxMoves());
        Assertions.assertEquals(75, mortarPerson.getHp());
        Assertions.assertEquals(75, mortarPerson.getDamage());
        Assertions.assertEquals("Ground", mortarPerson.getType());
        Assertions.assertFalse(mortarPerson.canMove(new Water(null, null)));
        Assertions.assertTrue(mortarPerson.canMove(new Fields(null, null)));
        Assertions.assertFalse(mortarPerson.hasMoved());

        Assertions.assertFalse(mortarPerson.canAttack(new FighterSubmarine(null)));
        Assertions.assertFalse(mortarPerson.canAttack(new FighterPlane(null)));
        Assertions.assertTrue(mortarPerson.canAttack(new LightBoat(null)));
        Assertions.assertTrue(mortarPerson.canAttack(new HeavyTank(null)));
        Assertions.assertEquals(mortarPerson.getPosition(), new Position(1,1));

        TextGraphics graphics = Mockito.mock(TextGraphics.class);
        mortarPerson.draw(graphics);

        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(new TextColor.RGB(80,80,80));
        Mockito.verify(graphics, Mockito.never()).enableModifiers(Mockito.any(SGR.class));
        Mockito.verify(graphics, Mockito.never()).setModifiers(Mockito.any());
        Mockito.verify(graphics, Mockito.times(1)).putString(mortarPerson.getPosition().toTPos(), "@");

        mortarPerson.setHP(20);
        Assertions.assertEquals(20, mortarPerson.getHp());
        mortarPerson.setHasMoved(true);
        Assertions.assertTrue(mortarPerson.hasMoved());
    }

    @Test
    public void MortarPersonRandomTest() {
        Random random = new Random();
        int x = random.nextInt(0, 25), y = random.nextInt(0, 25);
        Playable mortarPerson = new MortarPerson(new Position(x,y));

        Assertions.assertEquals(mortarPerson.getPosition(), new Position(x,y));

        TextGraphics graphics = Mockito.mock(TextGraphics.class);
        int r = random.nextInt(0, 256), g = random.nextInt(0, 256), b = random.nextInt(0, 256);
        TextColor color = new TextColor.RGB(r, g, b);
        mortarPerson.setForegroundColor(color);
        mortarPerson.draw(graphics);

        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(color);
        Mockito.verify(graphics, Mockito.never()).enableModifiers(Mockito.any(SGR.class));
        Mockito.verify(graphics, Mockito.never()).setModifiers(Mockito.any());
        Mockito.verify(graphics, Mockito.times(1)).putString(mortarPerson.getPosition().toTPos(), "@");
    }
}
