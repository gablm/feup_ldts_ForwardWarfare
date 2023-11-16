package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.TerminalPosition;

public class Position {
    private int x;
    private int y;

    public Position() { this.x = 0; this.y = 0; }
    public Position(int x, int y) { this.x = x; this.y = y; }

    public TerminalPosition toTPos() {
        return new TerminalPosition(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() { return y; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null) return false;

        if (getClass() != o.getClass()) return false;

        Position position = (Position) o;
        return x == position.x && y == position.y;
    }
}
