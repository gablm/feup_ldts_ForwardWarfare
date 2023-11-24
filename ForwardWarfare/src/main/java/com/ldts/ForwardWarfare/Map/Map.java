package com.ldts.ForwardWarfare.Map;

import com.googlecode.lanterna.TextColor;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Facility.*;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Tile.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Map {
    private List<Element> map = new ArrayList<>();
    private Element player1Base;
    private Element player2Base;
    private Element player1Factory;
    private Element player2Factory;
    public Map(String filePath) throws FileNotFoundException, MapParseException {
        readMap(filePath);
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
    private void readMap(String filePath) throws FileNotFoundException, MapParseException {
            Scanner scanner = new Scanner(new File("src/main/resources/maps/" + filePath));
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
                        case 12:
                            map.add(new Water(pos, id == 11 ? new OilPump() : new Port()));
                            break;
                        case 21:
                            map.add(new Fields(pos, new Airport()));
                            break;
                        case 22:
                            map.add(new Fields(pos, new OilPump()));
                            break;
                        case 27:
                        case 28:
                        case 29:
                            Element iniField = new Fields(pos, new Factory());
                            if (id == 28)
                                if (player1Factory == null)
                                    player1Factory = iniField;
                                else
                                    throw new MapParseException("A player cannot start with more than one factory");
                            if (id == 29)
                                if (player2Factory == null)
                                    player2Factory = iniField;
                                else
                                    throw new MapParseException("A player cannot start with more than one factory");
                            map.add(iniField);
                            break;
                        case 31:
                        case 32:
                            Element base = new Fields(pos, new Base(id == 31 ? TextColor.ANSI.YELLOW : TextColor.ANSI.RED));
                            if (id == 31)
                                if (player1Base == null)
                                    player1Base = base;
                                else
                                    throw new MapParseException("A player cannot have more than one base");
                            else
                                if (player2Base == null)
                                    player2Base = base;
                                else
                                    throw new MapParseException("A player cannot have more than one base");
                            map.add(base);
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
}
