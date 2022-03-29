
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        try {

            ServerSocket socketServidor = new ServerSocket(4444);
            ObjectInputStream flujoEntrada;
            PaqueteEnvio pack;
            Socket socketComunicacion;

            // Le indicamos que acepte cualquier petición de un cliente
            socketComunicacion = socketServidor.accept();
            System.out.println("Conectado");

            do {

                // Creamos el flujo de datos de entrada
                flujoEntrada = new ObjectInputStream(socketComunicacion.getInputStream());
                pack = (PaqueteEnvio) flujoEntrada.readObject();
                System.out.println("(" + pack.getHora() + ")  " + pack.getIp() + " dice: " + pack.getMensaje());

            } while (!pack.getMensaje().equalsIgnoreCase("fin de la comunicación"));

            // Cerramos el flujo de entrada
            flujoEntrada.close();
            // Cerramos las conexiones
            socketComunicacion.close();
            socketServidor.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
