package Ejemplo1;

/**
 * @author Joan
 */
public class Principal {

    private static PonerCoger bolsa;
    private static Thread HiloPonedor;
    private static Thread HiloCogedor;

    public static void main(String[] args) {

        // Creamos una instancia de la clase PonerCoger()
        bolsa = new PonerCoger();

        // Primer hilo y lo arrancamos
        HiloPonedor = new Ponedor(bolsa, "Hilo 1");
        HiloPonedor.start();

        // Segundo hilo y lo arrancamos
        HiloCogedor = new Cogedor(bolsa, "Hilo 2");
        HiloCogedor.start();

    }

}
