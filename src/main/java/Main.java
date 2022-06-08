public class Main {
    public static void main(String[] args) {
        Thread thread;
        int size = 9;

        TrafficLightSimulator trafficLight = new TrafficLightSimulator();
        thread = new Thread(trafficLight);

        thread.start();

        for (int i = 0; i < size; i++) {
            System.out.println(trafficLight.getColor());
            trafficLight.waitForChange();
        }
        trafficLight.cancel();
    }
}
