package Ejemplo1;

/**
 * 
 * @author alumno
 *
 * Hilo llama al método cogeDato() de la clase PonerCoger();
 *
 */

public class Cogedor extends Thread {

	private PonerCoger bolsa;
	
	public Cogedor(PonerCoger bolsa, String nombreHilo) {
		
		this.bolsa = bolsa;
		setName(nombreHilo);
		
	}
	
	public void run() {
		
		while (true) {
			
			bolsa.cogeDato(getName());
			
			try {
				sleep(500);
			} catch (InterruptedException ie) {
				ie.getStackTrace();
			}
			
		}
		
	}
	
}
