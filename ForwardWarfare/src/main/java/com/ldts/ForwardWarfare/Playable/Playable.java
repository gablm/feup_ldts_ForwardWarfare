package com.ldts.ForwardWarfare.Playable;

import com.ldts.ForwardWarfare.Element;
import com.ldts.ForwardWarfare.Tile;

public abstract class Playable extends Element {
    private int hp = 0;
    private final int maxMoves;
    private String tileType;
    public Playable(String tileType, int maxMoves) {
        this.tileType = tileType;
        this.maxMoves = maxMoves;
    }
    public void takeHP(int damage) {
        hp -= damage;
    }
    public void setHP(int hp) {
        this.hp = hp;
    }
    protected abstract boolean canMove(Tile tile);
}
