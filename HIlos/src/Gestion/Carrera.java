package Gestion;

public class Carrera {

    public static void main(String[] args) {

        System.out.println("Comienza el Leopardo");
        System.out.println("Comienza la Liebre");
        System.out.println("Comienza la Tortuga");

        Tortuga tortu = new Tortuga();
        Liebre liebre = new Liebre();
        Leopardo leopardo = new Leopardo();

        tortu.start();
        liebre.start();
        leopardo.start();

    }
}
