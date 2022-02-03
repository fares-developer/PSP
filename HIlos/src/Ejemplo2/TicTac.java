package Ejemplo2;

/**
 * 
 * @author Joan
 *
 * Clase con dos m�todos sincronizados que �nicamente muestran un mensaje
 * por consola, el primero "Tic" y el segundo "Tac"
 *  
 */
public class TicTac {

	/**
	 * 
	 *  Atributo para controlar qu� hilo de los que llaman a estos dos
	 *  m�todos debe ponerse en modo wait()
	 *    
	 */
	private boolean TicMostrado = false;
	
	/**
	 * 
	 * Si el atributo TicMostrado est� a false, muestra "Tic" por pantalla,
	 * actualiza TicMostrado a true y avisa al hilo que llama al otro
	 * m�todo para que reanude su ejecuci�n.
	 * En caso de TicMostrado estar a true, el hilo que llama a este m�todo
	 * se pone en modo wait()
	 * 
	 */
	public synchronized void Tic() {
		
		while (TicMostrado) {

			try {
				wait();
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
			
		}
		
		System.out.println("Tic");
		TicMostrado = true;
		notifyAll();
		
	}
	
	/**
	 * 
	 * Si el atributo TicMostrado est� a true, muestra "Tac" por pantalla,
	 * actualiza TicMostrado a false y avisa al hilo que llama al otro
	 * m�todo para que reanude su ejecuci�n.
	 * En caso de TicMostrado estar a false, el hilo que llama a este m�todo
	 * se pone en modo wait()
	 * 
	 */
	public synchronized void Tac() {
		
		while (!TicMostrado) {
			
			try {
				wait();
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
			
		}

		System.out.println("Tac");
		TicMostrado = false;
		notifyAll();
		
	}
	
}
