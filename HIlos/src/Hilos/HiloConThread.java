package Hilos;

public class HiloConThread extends Thread {

    public void run() {
        for (int a = 0; a < 3; a++) {
            System.out.println("Soy el hilo "+getName()+" y esto es lo que hago");
        }
    }
}

class HiloConRunnable implements Runnable {

    @Override
    public void run() {
        for (int a = 0; a < 4; a++) {
            System.out.println("Soy el hilo");
        }
    }
}
