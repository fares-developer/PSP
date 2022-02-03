package Gestion;

public class Tortuga extends Thread {

    @Override
    public void run() {
        try {
            for (int a = 0; a < 5; a++) {
                Thread.sleep(5);
                System.out.println("Tortuga");
            }
            System.out.println("Termina la tortuga");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
