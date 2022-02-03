package Ejemplo2;

/**
 * 
 * @author Joan
 *
 * Hilo que llama al método Tic() de la clase TicTac();
 *
 */
public class Tic extends Thread {

	private TicTac tictac;
	
	public Tic(TicTac tictac) {
		
		this.tictac = tictac;
		
	}
	
	public void run () {
		
		while (true) {
			tictac.Tic();

			try {
				sleep(500);
			} catch (InterruptedException ie) {
				ie.getStackTrace();
			}
		
		}
		
	}
	
}
