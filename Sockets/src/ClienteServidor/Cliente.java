package ClienteServidor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            // Creamos el socket y establecemos una conexión con el servidor
            Socket socketCliente = new Socket("localhost", 9999);
            // Creamos el flujo de datos de salida
            DataOutputStream flujoSalida;

            String mensaje;
            do {
                flujoSalida = new DataOutputStream(socketCliente.getOutputStream());
                // Por el flujo de salida enviamos un mensaje al servidor
                System.out.println("Introduce el mensaje");
                mensaje = sc.nextLine();
                flujoSalida.writeUTF(mensaje);
                // Cerramos el flujo de salida
            } while (!mensaje.equalsIgnoreCase("Fin de la comunicación"));
            flujoSalida.close();
            // Cerramos la conexion
            socketCliente.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
