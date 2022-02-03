package Ejemplo1;

/**
 * 
 * @author Joan
 *
 * Hilo que genera un número entero aleatorio entre 0 y 99,
 * y llama al método ponDato() de la clase PonerCoger() con dicho número;
 * 
 */
public class Ponedor extends Thread {

	private PonerCoger bolsa;
	private Integer    aleatorio;
	
	
	public Ponedor(PonerCoger bolsa, String nombreHilo) {
		
		this.bolsa = bolsa;
		setName(nombreHilo);
		
	}
	
	public void run() {
		
		while (true) {
			
			aleatorio = (int) (Math.random() *100);
			bolsa.ponDato(aleatorio, getName());
			
			try {
				sleep(500);
			} catch (InterruptedException ie) {
				ie.getStackTrace();
			}
			
		}
		
	}
	
	
}
