package com.ldts.ForwardWarfare;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class PositionTest {

    @Test
    public void BasePosition() {
        Position position = new Position();

        int expected = 0;
        Assertions.assertEquals(expected, position.getX());
        Assertions.assertEquals(expected, position.getY());
    }

    @Test
    public void RandomPosition() {
        Random random = new Random();
        int x = random.nextInt();
        int y = random.nextInt();

        Position position = new Position(x, y);
        Assertions.assertEquals(x, position.getX());
        Assertions.assertEquals(y, position.getY());
    }

    @Test
    public void BaseEquality() {
        Position position = new Position();
        Assertions.assertEquals(position, new Position());
    }

    @Test
    public void RandomEquality() {
        Random random = new Random();
        int x = random.nextInt();
        int y = random.nextInt();

        Position position = new Position(x, y);
        Assertions.assertEquals(position, new Position(x, y));
    }
}
