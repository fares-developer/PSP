import java.io.Serial;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaqueteEnvio implements Serializable {

    @Serial
    private static final long serialVersionUID = 8484886515554412098L;
    private String mensaje;
    private String ip;
    private Date hora;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHora() {
        SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
        return formato.format(hora);
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }


}

