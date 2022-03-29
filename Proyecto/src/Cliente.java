import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Cliente {

    public static void main(String[] args) {
        marcoCliente mc = new marcoCliente();
        mc.setVisible(true);
    }
}

class marcoCliente extends JFrame {

    @Serial
    private static final long serialVersionUID = 4952155290172436409L;

    //Aquí creamos el JFrame y especificamos el logo y del programa
    public marcoCliente() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Cliente");
        setIconImage(new ImageIcon("src/Recursos/logo_edib.png").getImage());
        setBounds(300, 100, 700, 600);
        setResizable(false);//Impedimos que se pueda redimensionar el JFrame
        laminaCliente lc = new laminaCliente();
        add(lc);
        addWindowListener(new avisoOnline());//Avisamos al servidor de nuestra conexion
    }
}

class laminaCliente extends JPanel implements Runnable {

    @Serial
    private static final long serialVersionUID = -4713163829087018657L;
    private final JTextField mensaje;
    private final JComboBox<String> selector;
    Color color = new Color(0x1976D2);
    static public JLabel nick;
    private final JButton enviar;
    private final JTextArea area;
    public static String mi_ip = "192.168.1.115";//Esta dirección varía en función de nuestra ip local
    public static String ip_server;

    public laminaCliente() {
        Font mi_fuente = new Font("Candara Regular", Font.PLAIN, 18);
        String nick_emisor;
        do {
            nick_emisor = JOptionPane.showInputDialog("Introduzca el su Nick: ");
        } while (nick_emisor.isBlank());

        //En las siguientes líneas describimos la distribución de los componentes en pantalla

        //Creamos la etiqueta Nick
        setLayout(new BorderLayout(10, 10));
        Box cajaH = Box.createHorizontalBox();
        JLabel eti_nick = new JLabel("Nick: ");
        eti_nick.setFont(mi_fuente);
        cajaH.add(eti_nick);
        cajaH.add(Box.createHorizontalStrut(5));
        //--------Creamos el label que contendrá el nick del emisor
        nick = new JLabel();
        nick.setText(nick_emisor);
        nick.setFont(mi_fuente);
        cajaH.add(nick);
        cajaH.add(Box.createHorizontalStrut(5));
        JLabel texto = new JLabel("Online: ");
        texto.setFont(mi_fuente);
        cajaH.add(texto);
        cajaH.add(Box.createHorizontalStrut(5));
        //-------------Creamos el combobox que contendrá las ips y nicks
        selector = new JComboBox<>();
        selector.setBorder(new LineBorder(color, 1, true));
        cajaH.add(selector);
        cajaH.setBorder(new EmptyBorder(20, 20, 0, 20));
        add(cajaH, BorderLayout.NORTH);
        //--------------Creamos el TextArea de los mensajes
        Box cajaH2 = Box.createHorizontalBox();
        area = new JTextArea(12, 20);
        area.setEditable(false);
        area.setMargin(new Insets(10, 10, 10, 10));
        area.setBorder(new LineBorder(color, 1, true));
        area.setLineWrap(true);
        JScrollPane s = new JScrollPane(area);
        cajaH2.add(s);
        cajaH2.setBorder(new EmptyBorder(0, 20, 0, 20));
        add(cajaH2, BorderLayout.CENTER);
        //-------------Creamos el textfield y boton para enviar los mensajes
        Box cajaH3 = Box.createHorizontalBox();
        mensaje = new JTextField(20);
        mensaje.setFont(new Font("Candara Regular", Font.PLAIN, 16));
        mensaje.setBorder(new LineBorder(color, 2));
        mensaje.setMargin(new Insets(5, 10, 5, 5));
        cajaH3.add(mensaje);

        enviar = new JButton("ENVIAR");
        enviar.setBorder(new EmptyBorder(5, 20, 5, 20));
        enviar.setFont(new Font("Cambria",Font.BOLD,18));
        enviar.setBackground(color);
        cajaH3.add(enviar);
        cajaH3.setBorder(new EmptyBorder(20, 20, 20, 20));
        add(cajaH3, BorderLayout.SOUTH);
        //-------------------------------------------------

        enviaTexto env_texto = new enviaTexto();//Creamos un objeto de la clase enviarTexto
        enviar.addActionListener(env_texto);//Añadimos al boton enviar su funcionalidad
        mensaje.addKeyListener(env_texto);//Nos aseguramos de que pulsando enter se envie igualmente el mensaje

        //Creamos un hilo para recibir mensajes del servidor en cualquier momento
        Thread hilo = new Thread(this);
        hilo.start();
    }

    @Override
    public void run() {
        try {
            //En este método run nos encargamos de recibir todos los mensajes que nos envían otros clientes
            ServerSocket server_cliente = new ServerSocket(9090);//Creamos el servidor del cliente para escuchar
            Socket cliente;//Creamos el socket cliente
            PaqueteEnvio paqueteRecibido;//creamos el paquete envío que recibiremos

            while (true) {//Este bucle es el que permite la escucha constante desde el servidor
                cliente = server_cliente.accept();//Aceptamos la conexíón entrante desde el puerto indicado
                ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());
                paqueteRecibido = (PaqueteEnvio) ois.readObject();//Leemos el paquete recibido

                if (paqueteRecibido.getListaIps() == null) {//Si la lista de ips es null significa que nos envían un mensaje
                    for (String s : paqueteRecibido.getIpNickEmisor().keySet()) {

                        area.append(paqueteRecibido.getIpNickEmisor().get(s)
                                + "(" + s + "): " + paqueteRecibido.getMensaje() + "\n ");//Mostramos el mensaje
                    }

                } else {//Si la lista de ips no es nula entonces estamos actualizando nuestra lista ComboBox
                    HashMap<String, String> ips_actualizadas = paqueteRecibido.listaIps;

                    //Guardamos el item seleccionado para que no cambie si se actualiza la lista
                    String item_actual = " ";
                    if(selector.getItemCount()>0)item_actual = selector.getSelectedItem().toString();
                    selector.removeAllItems();
                    for (String s : ips_actualizadas.keySet()) {
                        //Controlamos que se añadan todas las ips excepto la nuestra
                        if (s.equals(mi_ip)) {
                            selector.setBorder(new LineBorder(Color.RED));
                        } else {
                            selector.setBorder(new LineBorder(color));
                            selector.setBorder(new LineBorder(Color.lightGray));
                            selector.addItem(ips_actualizadas.get(s) + " " + s);//Añadimos un nuevo ip_nick
                        }
                    }
                    //Establecemos el item seleccionado antes de actualizar la lista ya que por defecto cambia
                    selector.setSelectedItem(item_actual);
                }
                cliente.close();
                ois.close();//Cerramos el stream
            }

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "Hay un problema con alguna de las clases ");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Algo ha pasado recibir el mensaje desde el servidor ");
        } catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error Inesperado",
                    "Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    private class enviaTexto extends KeyAdapter implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            sendMessage();
        }//Esto se dispara cuando pulsamos el boton enviar

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                sendMessage();//Esto llama al método para enviar el mensaje si pulsamos enter
            }
        }

        public void sendMessage() {
            try {
                Socket socket = new Socket(ip_server, 5050);

                //Recogemos y enviamos los datos del emisor
                HashMap<String, String> emisor = new HashMap<>();
                emisor.put(mi_ip, nick.getText());

                //Recogemos y manejamos los datos del receptor para enviarlos
                HashMap<String, String> receptor = new HashMap<>();
                String[] datos_receptor = selector.getSelectedItem().toString().split("\\s");
                receptor.put(datos_receptor[1], datos_receptor[0]);

                //Enviamos el mensaje al servidor y este lo envía al destinatario
                PaqueteEnvio pack = new PaqueteEnvio(emisor, mensaje.getText(), receptor, null);
                area.setForeground(Color.blue);
                area.setAlignmentX(RIGHT_ALIGNMENT);
                area.append("\t\tYo: " + pack.getMensaje() + "\n");

                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(pack);
                oos.close();
                socket.close();

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Se ha perdido la conexión");
            } catch (Exception e){
                JOptionPane.showMessageDialog(null,"Error inesperado");
            }
        }
    }
}

//-----------------------------------AVISAMOS AL SERVIDOR DE NUESTRA CONEXION-------------------------------
class avisoOnline extends WindowAdapter {

    public avisoOnline() {
        do {//Esto se repite siempre y cuando la ip esté en blanco
            laminaCliente.ip_server = JOptionPane.showInputDialog("Introduzca la ip del servidor: ");
        } while (laminaCliente.ip_server.isBlank());
    }

    @Override
    public void windowOpened(WindowEvent e) {//Este evento se lanza cuando aparece la ventana principal del cliente
        try {
            //Aquí nos encargamos de avisar al servidor de que estamos en línea
            Socket avisar_servidor = new Socket(laminaCliente.ip_server, 5050);//Creamos el socket para avisar al sevidor

            //Creamos un HashMap con los datos del emisor
            HashMap<String, String> emisor = new HashMap<>();
            emisor.put(laminaCliente.mi_ip, laminaCliente.nick.getText());

            //Enviamos el aviso
            PaqueteEnvio aviso = new PaqueteEnvio(emisor, "@online", null, null);
            ObjectOutputStream oos = new ObjectOutputStream(avisar_servidor.getOutputStream());
            oos.writeObject(aviso);
            avisar_servidor.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "No se ha podido establecer conexión.\nRevisa los datos introducidos",
                    "Error", JOptionPane.ERROR_MESSAGE);
            JOptionPane.showMessageDialog(null, "Se cerrará el programa en breve");
            try {
                Thread.sleep(3000);//Esperamos 3 segundos y cerramos el programa para evitar errores
                System.exit(-1);
            } catch (InterruptedException exc) {
                exc.printStackTrace();
            }
        }
    }
}

