package Ejemplo1;

/**
 * 
 * @author Joan
 *
 * Clase con dos métodos sincronizados que comparten un recurso,
 * concretamente el atributo dato
 *  
 */
public class PonerCoger {

	/**
	 * 
	 *  Recurso compartido los hilos que llaman a los métodos de esta clase
	 *    
	 */
	private Integer dato     = 0;

	/**
	 * 
	 *  Atributo para controlar qué hilo de los que llaman a estos dos
	 *  métodos debe ponerse en modo wait()
	 *    
	 */
	private boolean hayDatos = false;
	
	/**
	 * 
	 * @param numero
	 * @param nombreHilo
	 * 
	 * Método que recibe por parámetro un número y, si el atributo hayDatos
	 * está a false lo almacena en el atributo dato, actualiza hayDatos a true
	 * y avisa al hilo que llama al otro método para que reanude su ejecución.
	 * En caso de hayDatos estar a true, el hilo que llama a este método
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
	 * Si el atributo hayDatosestá a false, muestra el valor del atributo dato,
	 * actualiza hayDatos a true y avisa al hilo que llama al otro
	 * método para que reanude su ejecución.
	 * En caso de hayDatos estar a true, el hilo que llama a este método
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
