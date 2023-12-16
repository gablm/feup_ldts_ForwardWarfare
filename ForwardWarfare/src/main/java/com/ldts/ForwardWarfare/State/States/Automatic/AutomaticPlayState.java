package com.ldts.ForwardWarfare.State.States.Automatic;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Facility.*;
import com.ldts.ForwardWarfare.Element.Playable.Air.BomberPlane;
import com.ldts.ForwardWarfare.Element.Playable.Air.FighterPlane;
import com.ldts.ForwardWarfare.Element.Playable.Air.LightHelicopter;
import com.ldts.ForwardWarfare.Element.Playable.Ground.*;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Element.Playable.Water.FighterSubmarine;
import com.ldts.ForwardWarfare.Element.Playable.Water.LightBoat;
import com.ldts.ForwardWarfare.Element.Playable.Water.MortarBoat;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Tile.Fields;
import com.ldts.ForwardWarfare.Element.Tile.MountainLand;
import com.ldts.ForwardWarfare.Element.Tile.Tile;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;
import com.ldts.ForwardWarfare.State.BaseState;
import com.ldts.ForwardWarfare.State.States.EndGameState;
import com.ldts.ForwardWarfare.State.States.StartRoundState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AutomaticPlayState extends BaseState {

    private boolean endgame = false;
    private List<List<Integer>> values = List.of(List.of(2,5,10,7,15,10),
                                                    List.of(7,20,10),
                                                    List.of(10,25,15));
    private List<List<Element>> troops = List.of(List.of(new LightPerson(new Position(0,0)), new HeavyPerson(new Position(0,0)),
                                                        new MortarPerson(new Position(0,0)),new LightTank(new Position(0,0)),
                                                        new HeavyTank(new Position(0,0)), new AntiAirTank(new Position(0,0))),
                                                List.of(new LightBoat(new Position(0,0)), new MortarBoat(new Position(0,0)),
                                                        new FighterSubmarine(new Position(0,0))),
                                                List.of(new FighterPlane(new Position(0,0)), new BomberPlane(new Position(0,0)),
                                                        new LightHelicopter(new Position(0,0))));

    public AutomaticPlayState(Controller p1, Controller p2, Map map) {
        super(p1, p2, map);
        automatedLogic();
    }

    private void automatedLogic() {

        for (int troopBuys = 0; troopBuys < 2 && !p1.getFacilities().isEmpty(); troopBuys++) {
            int random = new Random().nextInt(0, p1.getFacilities().size());
            Element i = p1.getFacilities().get(random);
            Tile tile = (Tile) i;
            if (tile.getFacility().getUsed()) {
                troopBuys++;
                continue;
            }
            if (tile.getFacility() instanceof Airport)
                buyHighest(2, i.getPosition());
            if (tile.getFacility() instanceof Port)
                buyHighest(1, i.getPosition());
            if (tile.getFacility() instanceof Factory)
                buyHighest(0, i.getPosition());
            tile.getFacility().execute();
            troopBuys++;
        }

        List<Integer> x = Arrays.asList(0, 0, 1, -1);
        List<Integer> y = Arrays.asList(1, -1, 0, 0);

        List<Position> small;
        Playable vitimRes = null;
        Element facilityRes = null;

        int troopMoves = 0;
        for (Element i : p1.getTroops()) {
            if (troopMoves == 3)
                break;

            Playable troop = (Playable) i;
            if (troop.hasMoved()) {
                troopMoves++;
                continue;
            }

            small = new ArrayList<>();
            for (Element vitim : p2.getTroops()) {
                if (!troop.canAttack((Playable) vitim))
                    continue;
                int tries = 0;
                List<Position> res = null;
                while (tries < 4 && res == null) {
                    Position p = vitim.getPosition();
                    int a = p.getY() + x.get(tries);
                    int b = p.getX() + y.get(tries);
                    res = canMove(i.getPosition(), new Position(b, a), troop);
                    tries++;
                }
                if (res == null)
                    continue;
                if (res.size() < small.size() || small.isEmpty()) {
                    small = res;
                    vitimRes = (Playable) vitim;
                }
            }


            for (Element element : map.getElements()) {
                if (((Tile)element).getFacility() == null)
                    continue;
                if (p1.getFacilities().stream().anyMatch(nw -> nw.getPosition().equals(element.getPosition())))
                    continue;
                if (p1.getBase().getPosition().equals(element.getPosition()))
                    continue;
                if (p2.getBase().getPosition().equals(element.getPosition()))
                    continue;
                List<Position> res = null;
                int tries = 0;
                while (tries < 4 && res == null) {
                    Position p = element.getPosition();
                    int a = p.getY() + x.get(tries);
                    int b = p.getX() + y.get(tries);
                    res = canMove(i.getPosition(), new Position(b, a), troop);
                    tries++;
                }
                if (res != null && (res.size() < small.size() || small.isEmpty())) {
                    small = res;
                    facilityRes = element;
                    vitimRes = null;
                }
            }

            List<Position> res = null;
            int tries = 0;
            while (tries < 4 && res == null) {
                Position p = p2.getBase().getPosition();
                int a = p.getY() + x.get(tries);
                int b = p.getX() + y.get(tries);
                res = canMove(i.getPosition(), new Position(b, a), troop);
                tries++;
            }
            if (res != null && (res.size() < small.size() || small.isEmpty())) {
                small = res;
                facilityRes = null;
                vitimRes = null;
            }

            if (!small.isEmpty() && small.size() < troop.getMaxMoves() + 2) {
                if (vitimRes != null) {
                    int finalHp = vitimRes.getHp() - troop.getDamage();
                    if (finalHp > 0)
                        vitimRes.setHP(finalHp);
                    else
                        p2.getTroops().remove(vitimRes);
                } else {
                    if (facilityRes != null)
                        capture(facilityRes.getPosition());
                    else
                        capture(p2.getBase().getPosition());
                }
                troop.setPosition(small.get(0));
            } else if (small.size() > troop.getMaxMoves()) {
                troop.setPosition(small.get(small.size() - troop.getMaxMoves() - 1));
            }
            troopMoves++;
        }
    }

    private void buyHighest(int type, Position pos) {
        Position temp = new Position(0,0);
        int round = 0;
        boolean found = false;
        while (!found) {
            if (round == 8)
                break;
            switch (round) {
                case 0:
                    temp = new Position(pos.getX() + 1, pos.getY());
                    break;
                case 1:
                    temp = new Position(pos.getX() + 1, pos.getY() + 1);
                    break;
                case 2:
                    temp = new Position(pos.getX(), pos.getY() + 1);
                    break;
                case 3:
                    temp = new Position(pos.getX() - 1, pos.getY() + 1);
                    break;
                case 4:
                    temp = new Position(pos.getX() - 1, pos.getY());
                    break;
                case 5:
                    temp = new Position(pos.getX() - 1, pos.getY() - 1);
                    break;
                case 6:
                    temp = new Position(pos.getX(), pos.getY() - 1);
                    break;
                case 7:
                    temp = new Position(pos.getX() + 1, pos.getY() - 1);
                    break;
            }
            round++;
            Position finalTemp = temp;
            if (map.at(temp).noCollision()
                    && p1.getTroops().stream().noneMatch(x -> x.getPosition().equals(finalTemp))
                    && p2.getTroops().stream().noneMatch(x -> x.getPosition().equals(finalTemp)))
                found = true;
        }
        if (!found)
            return;

        boolean notBought = true;
        int tries = 0;
        while (notBought && tries < 5) {
            int random = new Random().nextInt(0, values.get(type).size());
            if (values.get(type).get(random) < p1.getCoins()) {
                Playable res = (Playable) troops.get(type).get(random);
                if (!res.canMove((Element) map.at(temp))) {
                    tries++;
                    continue;
                }
                res.setPosition(temp);
                res.setHasMoved(true);
                p1.buy(res, values.get(type).get(random));
                notBought = false;
            }
            tries++;
        }
    }

    private void capture(Position pos){
        if(map.at(pos).getFacility().getClass()== Base.class)
        {
            if (p2.getBase().getPosition().equals(pos) && !((Base) ((Tile) p2.getBase()).getFacility()).getAtackedlastturn()) {
                System.out.println("Base");
                Base basep1 = (Base) map.at(pos).getFacility();
                basep1.takeDamage();
                basep1.setAtackedlastturn(true);
                if(!basep1.getUsed())
                    basep1.execute();
                map.set(pos, new Fields(pos,basep1));
                p2.setBase((Element) map.at(pos));
                System.out.println(basep1.getLives());
                if (basep1.getLives() <= 0)
                {
                    endgame = true;
                }
            }
        }
        else {
            if (p2.getFacilities().stream().anyMatch(facility -> facility.getPosition().equals(pos)))
                p2.getFacilities().removeIf(facility -> facility.getPosition().equals(pos));
            if (p1.getFacilities().stream().noneMatch(facility -> facility.getPosition().equals(pos))) {
                map.at(pos).getFacility().execute();
                p1.addFacility((Element) map.at(pos));
            }
        }
    }

    public List<Position> canMove(Position pos, Position end, Playable playable) {

        Map copy = new Map(map);
        for (Element i : p1.getTroops())
            copy.set(i.getPosition(), new MountainLand(i.getPosition()));
        for (Element i : p2.getTroops())
            copy.set(i.getPosition(), new MountainLand(i.getPosition()));

        List<Integer> x = Arrays.asList(0, 0, 1, -1);
        List<Integer> y = Arrays.asList(1, -1, 0, 0);
        List<Position> q = new ArrayList<>();
        q.add(pos);

        int dist[][] = new int[10][15];
        boolean found = false;

        for(int []a : dist){
            Arrays.fill(a,-1);
        }
        dist[pos.getY()][pos.getX()] = 0;
        while(!q.isEmpty()){
            Position p = q.remove(0);
            if (p.equals(end)) {
                found = true;
                break;
            }
            for(int i = 0; i < 4; i++){
                int a = p.getY() + x.get(i);
                int b = p.getX() + y.get(i);
                Tile tile = copy.at(new Position(b, a));
                if(a >= 0 && b >= 0 && a < 10 && b < 15 && dist[a][b] == -1
                        && tile.noCollision() && (playable.canMove((Element) tile))){
                    dist[a][b] = 1 + dist[p.getY()][p.getX()];
                    q.add(new Position(b, a));
                }
            }
        }

        if (!found)
            return null;

        List<Position> res = new ArrayList<>();
        int x1 = end.getX(), y1 = end.getY();
        while (dist[y1][x1] != 0) {
            for(int i = 0; i < 4; i++) {
                int x2 = x1 + x.get(i);
                int y2 = y1 + y.get(i);
                if (x2 >= 0 && y2 >= 0 && y2 < 10 && x2 < 15 && dist[y2][x2] < dist[y1][x1] && dist[y2][x2] != -1) {
                    res.add(new Position(x2, y2));
                    x1 = x2; y1 = y2;
                }
            }
        }
        res.add(0, end);
        return res;
    }

    @Override
    public State play(Action action) {
        p1.endRound();
        if (endgame)
            return new EndGameState(p1, p2, map);
        return new StartRoundState(p1, p2, map);
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(p1.getControllerColor());
        graphics.fillRectangle(new TerminalPosition(0,10), new TerminalSize(15,9), ' ');
        graphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        graphics.putString(1, 11, "Automatic");
    }

    @Override
    public boolean requiresInput() {
        return false;
    }
}
