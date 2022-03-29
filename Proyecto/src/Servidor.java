import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Servidor {

    public static void main(String[] args) {
        marcoServidor ms = new marcoServidor();
        ms.setVisible(true);
    }
}

class marcoServidor extends JFrame implements Runnable {

    @Serial
    private static final long serialVersionUID = -113201668075189548L;
    JTextArea area_texto;
    HashMap<String, String> listaIps = new HashMap<>();
    //------------------------------Creamos los formatos para las fechas y horas
    SimpleDateFormat formato = new SimpleDateFormat("HH:mm");
    SimpleDateFormat formato2 = new SimpleDateFormat("yyyyMMdd");

    public marcoServidor() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Servidor");
        setIconImage(new ImageIcon("src/Recursos/logo_edib.png").getImage());
        setBounds(400, 90, 700, 600);
        JPanel milamina = new JPanel();
        milamina.setLayout(new BorderLayout());

        area_texto = new JTextArea();
        area_texto.setEditable(false);
        area_texto.setLineWrap(true);
        area_texto.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane s = new JScrollPane(area_texto);
        s.setBorder(new LineBorder(Color.BLACK, 2, true));
        milamina.add(s, BorderLayout.CENTER);
        milamina.setBorder(new EmptyBorder(20, 20, 20, 20));
        add(milamina);

        Thread mihilo = new Thread(this);
        mihilo.start();

        Thread hilo_desc = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    listaActualizada();
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        hilo_desc.start();


    }

    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(5050);
            String mensaje;
            PaqueteEnvio paquete_recibido;

            while (true){
                Socket server_socekt = server.accept();//Aceptamos las conexiones entrantes
                ObjectInputStream ois = new ObjectInputStream(server_socekt.getInputStream());
                paquete_recibido = (PaqueteEnvio) ois.readObject();//Leemos el paquete que no envían

                //Recogemos y ordenamos los datos del emisor
                String[] emisor = parsearIpNick(paquete_recibido.getIpNickEmisor());
                String ip_emisor = emisor[0];
                String nick_emisor = emisor[1];

                mensaje = paquete_recibido.getMensaje();

                //A continuación controlamos si es un mensaje a un destinatario o es la primera vez que se conecta alguien
                if (!mensaje.equalsIgnoreCase("@online")) {

                    //Recogemos y ordenamos los datos del receptor para mostrarlos en area de texto
                    String[] receptor = parsearIpNick(paquete_recibido.getIpNickReceptor());
                    String ip_receptor = receptor[0];
                    String nick_receptor = receptor[1];

                    ObjectOutputStream reenvio = null;
                    try {
                        Socket destinatario = new Socket(ip_receptor, 9090);
                        reenvio = new ObjectOutputStream(destinatario.getOutputStream());
                        reenvio.writeObject(paquete_recibido);

                        //Mostramos el mensaje del emisor al receptor
                        area_texto.append(formato.format(new Date()) + "  "
                                + nick_emisor + "  (" + ip_emisor + ") a " + nick_receptor + " (" + ip_receptor + "): "
                                + paquete_recibido.getMensaje() + "\n");
                        writeLog();//Escribimos en el log
                        destinatario.close();

                    } catch (SocketException e) {//Esta excepción salta si no se ha podido conectar con el receptor
                        listaIps.remove(ip_receptor);//Eliminamos la ip del receptor al que no se ha podido conectar
                        listaActualizada();//Enviamos la lista de ips actualizada
                    }

                    if (reenvio != null) reenvio.close();//Cerramos el socket de reenvio de datos

                } else {
                    // -------DETECTAMOS SI EL CLIENTE ESTA ONLINE, Y ACTUALIZAMOS LA LISTA DE CONECTADOS
                    area_texto.append(formato.format(new Date())
                            + "  " + nick_emisor + " (" + ip_emisor + ")  Se ha conectado \n");
                    writeLog();
                    listaIps.put(ip_emisor, nick_emisor);//Añadimos el ip y nick todos los que se conectan

                    listaActualizada();//Enviamos la lista de ips actualizada a todos los clientes
                }
                server_socekt.close();//Cerramos el server
            }

        } catch (SocketException ex) {
            JOptionPane.showMessageDialog(null, "Algo ha pasado con el server socket");
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Se ha producido un error en el servidor");
        }
    }

    public void writeLog() {//Este método se encarga de crear y escribir en el fichero log
        //------------------------Creamos el fichero log de registro-------------------------------
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(formato2.format(new Date()) + ".log"));
            bw.write(area_texto.getText());
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Se ha producido un error al escribir en el log ");
        }
    }

    //Con este método nos aseguramos de extraer correctamente la ip y nick de un hasmap.
    public String[] parsearIpNick(HashMap<String, String> hash) {
        String ip = "";
        String nick = "";
        for (String i : hash.keySet()) {
            ip = i;
            nick = hash.get(i);
        }
        return new String[]{ip, nick};
    }

    public void listaActualizada() {//Con este método enviamos una lista actualizada de las ips a todos los clientes
        String ip_actual = null;
        PaqueteEnvio pack = new PaqueteEnvio(null, null, null, listaIps);
        try {
            for (String i : listaIps.keySet()) {//Enviamos un paquete a todos los clientes conectados
                ip_actual = i;
                SocketAddress sock_add = new InetSocketAddress(i, 9090);
                Socket destinatario = new Socket();
                destinatario.connect(sock_add, 1000);
                ObjectOutputStream reenvio_ips = new ObjectOutputStream(destinatario.getOutputStream());
                reenvio_ips.writeObject(pack);

                reenvio_ips.close();
                destinatario.close();
            }
        } catch (IOException e) {//Este excepción salta porque no se ha podido conectar con el cliente
            area_texto.append(formato.format(new Date()) + "  "
                    + listaIps.get(ip_actual) + " (" + ip_actual + ")  Se ha desconectado\n");
            writeLog();
            listaIps.remove(ip_actual);
        }
    }
}
