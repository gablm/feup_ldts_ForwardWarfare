package com.ldts.ForwardWarfare.State.States;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Controller.Controller;
import com.ldts.ForwardWarfare.Controller.Player;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Facility.Factory;
import com.ldts.ForwardWarfare.Element.Playable.Playable;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Tile.Fields;
import com.ldts.ForwardWarfare.Element.Tile.MountainLand;
import com.ldts.ForwardWarfare.Element.Tile.Tile;
import com.ldts.ForwardWarfare.Map.Map;
import com.ldts.ForwardWarfare.State.Action;
import com.ldts.ForwardWarfare.State.State;

import java.util.*;

public class MoveValidationState extends BaseState {
    public MoveValidationState(Controller p1, Controller p2, Map map) {
        super(p1, p2, map);
    }

    @Override
    public State play(Action action) {
        Position pos = p1.getSelection2().getPosition();
        if (!map.at(pos).noCollision()) {
            p1.setSelection2(null);
            return new InvalidSelectState(p1, p2, map, "Not allowed");
        }
        for (Element i : p1.getTroops()) {
            if (i.getPosition().equals(p1.getSelection1().getPosition())) {
                List<Position> res = canMove(p1.getSelection1().getPosition(),
                                                p1.getSelection2().getPosition(),
                                                (Playable) i);
                p1.setSelection2(null);
                p1.setSelection1(res == null ? null : p1.getSelection2());
                if (res == null)
                    return new InvalidSelectState(p1, p2, map, "Invalid move");
                return new MoveAnimationState(p1, p2, map, res, i);
            }
        }
        p1.setSelection1(p1.getSelection2());
        p1.setSelection2(null);
        return new MoveEndState(p1, p2, map);
    }

    public List<Position> canMove(Position pos, Position end, Playable playable){

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
                        && tile.noCollision() && playable.canMove((Element) tile)){
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
    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.ANSI.BLACK);
        graphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        graphics.putString(1, 11, "Validating");
    }

    @Override
    public boolean requiresInput() {
        return false;
    }
}
