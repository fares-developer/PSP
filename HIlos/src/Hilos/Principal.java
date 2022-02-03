package Hilos;

public class Principal {

    public static void main(String[] args) {

        /*HiloConThread hilo = new HiloConThread();
        HiloConThread hilo2 = new HiloConThread();
        HiloConThread hilo3 = new HiloConThread();
        HiloConThread hilo4 = new HiloConThread();
        hilo.start();
        hilo2.start();
        hilo3.start();
        hilo3.interrupt();
        hilo4.start();
        System.out.println("Soy el hilo principal y esto es lo que hago");*/

        HiloConThread h1 = new HiloConThread();
        HiloConThread h2 = new HiloConThread();
        HiloConThread h3 = new HiloConThread();

        try {
            h1.join();
            h2.join();
            h3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        h1.start();
        h2.start();
        h3.start();




    }
}
