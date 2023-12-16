package com.ldts.ForwardWarfare.Map;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Facility.*;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Tile.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class Map extends Element {
    private List<Element> map = new ArrayList<>();
    private Element player1Base;
    private Element player2Base;
    private Element player1Factory;
    private Element player2Factory;
    public Map(String filePath) throws FileNotFoundException, MapParseException, URISyntaxException {
        readMap(filePath);
    }

    public Map(Map map) {
        this.map = new ArrayList<>(map.map);
        this.player1Base = map.player1Base;
        this.player2Base = map.player2Base;
        this.player1Factory = map.player1Factory;
        this.player2Factory = map.player2Factory;
    }
    public List<Element> getElements() {
        return map;
    }
    public List<Element> getPlayer1() {
        if (player1Base == null || player1Factory == null)
            return null;
        return Arrays.asList(player1Base, player1Factory);
    }
    public List<Element> getPlayer2() {
        if (player2Base == null || player2Factory == null)
            return null;
        return Arrays.asList(player2Base, player2Factory);
    }
    public void readMap(String filePath) throws FileNotFoundException, MapParseException, URISyntaxException {
            URL mapURL = getClass().getClassLoader().getResource("maps/" + filePath);
            Scanner scanner = new Scanner(new File(mapURL.toURI()));
            scanner.useDelimiter("\n");
            int y = 0;
            while (scanner.hasNext()) {
                Scanner line = new Scanner(scanner.nextLine());
                int x = 0;
                while (line.hasNext()) {
                    int id = line.nextInt();
                    Position pos = new Position(x,y);
                    switch (id) {
                        case 1:
                            map.add(new Florest(pos));
                            break;
                        case 2:
                            map.add(new MountainLand(pos));
                            break;
                        case 3:
                            map.add(new MountainWater(pos));
                            break;
                        case 4:
                            map.add(new Fields(pos, null));
                            break;
                        case 5:
                            map.add(new Water(pos, null));
                            break;
                        case 11:
                            map.add(new Water(pos, new OilPump()));
                            break;
                        case 12:
                            map.add(new Water(pos,new Port()));
                            break;
                        case 21:
                            map.add(new Fields(pos, new Airport()));
                            break;
                        case 22:
                            map.add(new Fields(pos, new OilPump()));
                            break;
                        case 27:
                            map.add(new Fields(pos, new Factory()));
                            break;
                        case 28:
                            if (player1Factory != null)
                                throw new MapParseException("A player cannot start with more than one factory");
                            player1Factory = new Fields(pos, new Factory());
                            map.add(player1Factory);
                            break;
                        case 29:
                            if (player2Factory != null)
                                throw new MapParseException("A player cannot start with more than one factory");
                            player2Factory = new Fields(pos, new Factory());
                            map.add(player2Factory);
                            break;
                        case 31:
                            if (player1Base != null)
                                throw new MapParseException("A player cannot have more than one base");
                            player1Base = new Fields(pos, new Base());
                            map.add(player1Base);
                            break;
                        case 32:
                            if (player2Base != null)
                                throw new MapParseException("A player cannot have more than one base");
                            player2Base = new Fields(pos, new Base());
                            map.add(player2Base);
                            break;
                        default:
                            throw new MapParseException("There is an invalid number in the map: %d".formatted(id));
                    }
                    x++;
                }
                if (x != 15)
                    throw new MapParseException("Invalid line size: %d".formatted(x));
                y++;
            }
            scanner.close();
            if (map.size() != 150)
                throw new MapParseException("The map should be 15 x 10.");
    }

    @Override
    public void draw(TextGraphics graphics) {
        for (Element elem : map) {
            elem.draw(graphics);
        }
    }

    public Tile at(Position position) {
        if (position.getX() < 0 || position.getY() < 0 || position.getX() >= 15 || position.getY() >= 10)
            return null;
        return (Tile) map.get(position.getY() * 15 + position.getX());
    }

    public void set(Position position, Element element) {
        if (position.getX() < 0 || position.getY() < 0 || position.getX() >= 15 || position.getY() >= 10)
            return;
        map.set(position.getY() * 15 + position.getX(), element);
    }

    public boolean inside(Position pos) {
        return pos.getY() >= 0 && pos.getY() < 10
                && pos.getX() >= 0 && pos.getX() < 15;
    }
}
