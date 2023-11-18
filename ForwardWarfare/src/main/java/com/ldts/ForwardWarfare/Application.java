package com.ldts.ForwardWarfare;
public class Application {
    public static void main(String[] args) {
        try {
            Game a = new Game(new LanternaTerminal(256,160 + 50));
            a.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
