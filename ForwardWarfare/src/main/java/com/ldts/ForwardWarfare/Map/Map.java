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
    private List<Element> player1 = new ArrayList<>(2);
    private List<Element> player2 = new ArrayList<>(2);
    public Map(String filePath) throws FileNotFoundException, MapParseException {
        readMap(filePath);
    }
    public List<Element> getElements() {
        return map;
    }
    public List<Element> getPlayer1() {
        return player1;
    }
    public List<Element> getPlayer2() {
        return player2;
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
                                if (player1.get(1) == null)
                                    player1.add(1, iniField);
                                else
                                    throw new MapParseException("A player cannot start with more than one factory");
                            if (id == 29)
                                if (player2.get(1) == null)
                                    player2.add(1, iniField);
                                else
                                    throw new MapParseException("A player cannot start with more than one factory");
                            map.add(iniField);
                            break;
                        case 31:
                        case 32:
                            Element base = new Fields(pos, new Base(id == 31 ? TextColor.ANSI.YELLOW : TextColor.ANSI.RED));
                            if (id == 31)
                                if (player1.get(0) == null)
                                    player1.add(0, base);
                                else
                                    throw new MapParseException("A player cannot start with more than one factory");
                            else
                                if (player2.get(0) == null)
                                    player2.add(0, base);
                                else
                                    throw new MapParseException("A player cannot start with more than one factory");
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
