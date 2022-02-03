package Gestion;

public class Leopardo extends Thread {

    public void run() {
        try {
            for (int a = 0; a < 5; a++) {
                sleep(4);
                System.out.println("Leopardo");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
