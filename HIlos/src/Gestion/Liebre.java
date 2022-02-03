package Gestion;

public class Liebre extends Thread {

    @Override
    public void run() {

        try {
            for (int a = 0; a < 5; a++) {
                Thread.sleep(3);
                System.out.println("Liebre");
                if (a == 3) {
                    break;
                }
            }
            System.out.println("Termina la liebre");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
