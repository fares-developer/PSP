package Ejemplo2;

/**
 * 
 * @author Joan
 *
 * Hilo que llama al método Tac() de la clase TicTac();
 *
 */
public class Tac extends Thread {

	private TicTac tictac;
	
	public Tac(TicTac tictac) {
		
		this.tictac = tictac;
		
	}
	
	public void run () {
		
		while (true) {
			tictac.Tac();

			try {
				sleep(500);
			} catch (InterruptedException ie) {
				ie.getStackTrace();
			}
		
		}
		
	}
	
}
