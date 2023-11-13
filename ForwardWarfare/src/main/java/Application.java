public class Application {
    public static void main(String[] args) {
        try {
            Game a = new Game();
            a.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
