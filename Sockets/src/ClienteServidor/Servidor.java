package ClienteServidor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        try {
            // Creamos el socket y abrimos un puerto
            // por el cual estará a la escucha
            ServerSocket socketServidor = new ServerSocket(5555);
            // Le indicamos que acepte cualquier petición de un cliente
            // y cree un socket para la comunicación con ese cliente
            Socket socketComunicacion = socketServidor.accept();
            DataInputStream flujoEntrada;
            String mensaje;
            do {
                // Creamos el flujo de datos de entrada
                flujoEntrada = new DataInputStream(socketComunicacion.getInputStream());

                // Por el flujo de entrada leemos el mensaje del cliente
                mensaje = flujoEntrada.readUTF();
                if (!mensaje.equalsIgnoreCase("Fin de la comunicación")) {
                    escribirInfo("Sockets/src/ClienteServidor/Mensaje.txt",mensaje);
                    System.out.println(mensaje);
                }
            } while (!mensaje.equalsIgnoreCase("Fin de la comunicación"));
            // Cerramos el flujo de entrada
            flujoEntrada.close();
            // Cerramos las conexiones
            socketComunicacion.close();
            socketServidor.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void escribirInfo(String ruta,String texto) {

        try(//Creamos el objeto de escritura
            BufferedWriter bw = new BufferedWriter(new FileWriter(ruta,true))){
            //Escribimos en cada uno de los datos en el fichero
            bw.write(texto);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error: "+e.getMessage());
        }
    }
}
