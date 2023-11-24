package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.TextColor;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Facility.*;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Tile.*;

import java.io.File;
import java.util.*;

public class Map {
    private List<Element> map = new ArrayList<>();
    private Element inicialFactory1;
    private Element inicialFactory2;

    public Map() {
        readMap(null);
    }
    public Map(String filePath) {
        readMap(filePath);
    }
    public List<Element> getElements() {
        return map;
    }
    public List<Element> getInitialFactories() {
        return Arrays.asList(inicialFactory1, inicialFactory2);
    }
    private void readMap(String filePath) {
        try {
            Random random = new Random();
            Scanner scanner;
            if (filePath != null)
                scanner = new Scanner(new File("src/main/resources/maps/" + filePath));
            else
                scanner = new Scanner(new File("src/main/resources/maps/%d.fw".formatted(random.nextInt(3) + 1)));
            scanner.useDelimiter("\n");
            int x, y = 0;
            while (scanner.hasNext()) {
                Scanner line = new Scanner(scanner.nextLine());
                x = 0;
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
                                if (inicialFactory1 == null)
                                    inicialFactory1 = iniField;
                                else
                                    throw new IllegalArgumentException("A player cannot start with more than one factory");
                            if (id == 29)
                                if (inicialFactory2 == null)
                                    inicialFactory2 = iniField;
                                else
                                    throw new IllegalArgumentException("A player cannot start with more than one factory");
                            map.add(iniField);
                            break;
                        case 31:
                        case 32:
                            map.add(new Fields(pos, new Base(id == 31 ? TextColor.ANSI.YELLOW : TextColor.ANSI.RED)));
                            break;
                        default:
                            throw new IllegalArgumentException("There is an invalid number in the map: %d".formatted(id));
                    }
                    x++;
                }
                if (x != 15)
                    throw new IllegalArgumentException("Invalid line size: %d".formatted(x));
                y++;
            }
            scanner.close();
            if (map.size() != 150)
                throw new IllegalArgumentException("The map should be 15 x 10.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
