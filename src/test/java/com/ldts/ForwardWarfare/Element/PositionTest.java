package com.ldts.ForwardWarfare.Element;

import com.ldts.ForwardWarfare.Element.Playable.Ground.HeavyPerson;
import com.ldts.ForwardWarfare.Element.Position;
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
        Assertions.assertNotEquals(new Position(2, 2), position);
    }

    @Test
    public void RandomEquality() {
        Random random = new Random();
        int x = random.nextInt();
        int y = random.nextInt();

        Position position = new Position(x, y);
        Assertions.assertEquals(position, new Position(x, y));
    }

    @Test
    public void NotValidEqualities() {
        Random random = new Random();
        int x = random.nextInt();
        int y = random.nextInt();

        Position position = new Position(x, y);
        Assertions.assertFalse(position.equals(null));
        Assertions.assertFalse(position.equals(new HeavyPerson(null)));
        Assertions.assertEquals(position, position);
    }
}
