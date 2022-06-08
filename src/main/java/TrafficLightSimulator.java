import static java.lang.Thread.sleep;

public class TrafficLightSimulator implements Runnable {
    private TrafficLightColor color; // информация о текущем цвете
    boolean stop = false; // для остановки имитации светофора установить в true
    boolean changed = false; // получает значение true при переключении светофора

    TrafficLightSimulator(TrafficLightColor color) {
        this.color = color;
    }

    TrafficLightSimulator() {
        color = TrafficLightColor.RED;
    }

    synchronized TrafficLightColor getColor() { // возврат текущего цвета
        return color;
    }

    synchronized void cancel() { // прекращение имитации светофора
        stop = true;
    }


    synchronized void waitForChange() {
        try {
            while (!changed) {
                wait(); // ожидаеть завершения, пока в методе changeColor() не будет вызван метод notify()
            }
            changed = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized void changeColor() { // переключение светофора
        switch (color) {
            case GREEN:
                color = TrafficLightColor.RED;
                break;
            case YELLOW:
                color = TrafficLightColor.GREEN;
                break;
            case RED:
                color = TrafficLightColor.YELLOW;
                break;
        }
        changed = true;
        notify(); // уведомить о переключении светофора (обратиться к методу notify() можно только из synchronized)
    }

    @Override
    public void run() { // запуск имитации светофора
        while (!stop) {
            try {
                switch (color) {
                    case GREEN:
                        sleep(10000); // зеленый на 10 сек
                        break;
                    case YELLOW:
                        sleep(2000); // желтый на 2 сек
                        break;
                    case RED:
                        sleep(12000); // красный на 12 сек
                        break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            changeColor();
        }
    }
}
