package Ejemplo2;

/**
 * 
 * @author Joan
 *
 */
public class Principal {

	private static TicTac tictac;
	private static Thread HiloTic;
	private static Thread HiloTac;
	
	public static void main(String[] args) {
		
		//Creamos una instancia de la clase TicTac()
		tictac = new TicTac();
		
		//Primer hilo y lo arrancamos
		HiloTic = new Tic(tictac);
		HiloTic.start();

		//Segundo hilo y lo arrancamos
		HiloTac = new Tac(tictac);
		HiloTac.start();

	}

}
