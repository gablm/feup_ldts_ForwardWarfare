package com.ldts.ForwardWarfare;

import com.googlecode.lanterna.TextColor;
import com.ldts.ForwardWarfare.Element.Element;
import com.ldts.ForwardWarfare.Element.Facility.*;
import com.ldts.ForwardWarfare.Element.Position;
import com.ldts.ForwardWarfare.Element.Tile.*;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Map {
    private List<Element> map;
    private List<Position> spawnPoints;
    private List<Element> inicialFactory;

    public Map() {
        readMap(null);
    }
    public Map(String filePath) {
        readMap(filePath);
    }

    private void readMap(String filePath) {
        try {
            Random random = new Random();
            Scanner scanner;
            if (filePath != null)
                scanner = new Scanner(new File("src/main/resources/maps/%d.fw".formatted(random.nextInt(3) + 1)));
            else
                scanner = new Scanner(new File("src/main/resources/maps/%d.fw".formatted(random.nextInt(3) + 1)));
            scanner.useDelimiter("\n");
            int x, y = 0;
            while (scanner.hasNext()) {
                Scanner line = new Scanner(scanner.nextLine());
                x = 0;
                while (line.hasNext()) {
                    Integer id = line.nextInt();
                    Position pos = new Position(x,y);
                    switch (id) {
                        case 1:
                            map.add(new Florest(pos));
                            break;
                        case 2:
                            map.add(new Montain_land(pos));
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
                            map.add(new Fields(pos, new Factory()));
                            break;
                        case 23:
                            map.add(new Fields(pos, new OilPump()));
                            break;
                        case 24:
                        case -24:
                            map.add(new Fields(pos, new Base(id > 0 ? TextColor.ANSI.GREEN : TextColor.ANSI.RED)));
                            break;
                        default:
                            throw new IllegalArgumentException();
                    }
                    x++;
                }
                y++;
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
