
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Introduzca la ip del servidor de destino");
            String ip = sc.nextLine();

            // Creamos el socket y establecemos una conexión con el servidor
            Socket socketCliente = new Socket(ip, 9999);

            ObjectOutputStream flujoSalida;
            PaqueteEnvio packet;
            String mensaje;

            do {
                System.out.println("Introduce el mensaje que quieres enviar");
                mensaje = sc.nextLine();

                packet = new PaqueteEnvio();
                packet.setIp("192.168.1.115");
                packet.setMensaje(mensaje);
                packet.setHora(new Date());

                // Creamos el flujo de datos de salida
                flujoSalida = new ObjectOutputStream(socketCliente.getOutputStream());
                // Por el flujo de salida enviamos un objeto al servidor
                flujoSalida.writeObject(packet);
            } while (!mensaje.equalsIgnoreCase("fin de la comunicación"));

            // Cerramos el flujo de salida
            flujoSalida.close();
            // Cerramos la conexion
            socketCliente.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}