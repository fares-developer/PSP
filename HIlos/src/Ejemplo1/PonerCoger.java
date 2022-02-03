package Ejemplo1;

/**
 * 
 * @author Joan
 *
 * Clase con dos m�todos sincronizados que comparten un recurso,
 * concretamente el atributo dato
 *  
 */
public class PonerCoger {

	/**
	 * 
	 *  Recurso compartido los hilos que llaman a los m�todos de esta clase
	 *    
	 */
	private Integer dato     = 0;

	/**
	 * 
	 *  Atributo para controlar qu� hilo de los que llaman a estos dos
	 *  m�todos debe ponerse en modo wait()
	 *    
	 */
	private boolean hayDatos = false;
	
	/**
	 * 
	 * @param numero
	 * @param nombreHilo
	 * 
	 * M�todo que recibe por par�metro un n�mero y, si el atributo hayDatos
	 * est� a false lo almacena en el atributo dato, actualiza hayDatos a true
	 * y avisa al hilo que llama al otro m�todo para que reanude su ejecuci�n.
	 * En caso de hayDatos estar a true, el hilo que llama a este m�todo
	 * se pone en modo wait()
	 * 
	 */
	public synchronized void ponDato(int numero, String nombreHilo) {
		
		while (hayDatos) {
			
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		this.dato = numero;
		System.out.println("Soy el " + nombreHilo + " y pongo un " + dato);
		hayDatos = true;
		notifyAll();

	}
	
	/**
	 * 
	 * @param nombreHilo
	 * 
	 * Si el atributo hayDatosest� a false, muestra el valor del atributo dato,
	 * actualiza hayDatos a true y avisa al hilo que llama al otro
	 * m�todo para que reanude su ejecuci�n.
	 * En caso de hayDatos estar a true, el hilo que llama a este m�todo
	 * se pone en modo wait()
	 * 
	 */
	public synchronized void cogeDato(String nombreHilo) {
		
		while (!hayDatos) {
			
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Soy el " + nombreHilo + " y pongo un " + dato);
		hayDatos = false;
		notifyAll();

	}
	
}
