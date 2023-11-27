package com.ldts.ForwardWarfare.Element.Playable;

import com.ldts.ForwardWarfare.Element.Playable.Air.BomberPlane;
import com.ldts.ForwardWarfare.Element.Playable.Air.FighterPlane;
import com.ldts.ForwardWarfare.Element.Playable.Air.LightHelicopter;
import com.ldts.ForwardWarfare.Element.Playable.Ground.*;
import com.ldts.ForwardWarfare.Element.Playable.Water.LightBoat;
import com.ldts.ForwardWarfare.Element.Playable.Water.MortarBoat;
import com.ldts.ForwardWarfare.Element.Position;

import javax.swing.plaf.PanelUI;

public class PlayableFactory {
    public PlayableFactory() { }

    public static Playable createBomberPlane(int x, int y) {
        return new BomberPlane(new Position(x, y));
    }
    public static Playable createFighterPlane(int x, int y) {
        return new FighterPlane(new Position(x, y));
    }
    public static Playable createLightHelicopter(int x, int y) {
        return new LightHelicopter(new Position(x, y));
    }
    public static Playable createAATank(int x, int y) {
        return new AntiAirTank(new Position(x, y));
    }
    public static Playable createHeavyPerson(int x, int y) {
        return new HeavyPerson(new Position(x, y));
    }
    public static Playable createHeavyTank(int x, int y) {
        return new HeavyTank(new Position(x, y));
    }
    public static Playable createLightPerson(int x, int y) {
        return new LightPerson(new Position(x, y));
    }
    public static Playable createLightTank(int x, int y) {
        return new LightTank(new Position(x, y));
    }
    public static Playable createMortarPerson(int x, int y) {
        return new MortarPerson(new Position(x, y));
    }
    public static Playable createFighterSubmarine(int x, int y) {
        return new FighterPlane(new Position(x, y));
    }
    public static Playable createLightBoat(int x, int y) {
        return new LightBoat(new Position(x, y));
    }
    public static Playable createMortarBoat(int x, int y) {
        return new MortarBoat(new Position(x, y));
    }
}
