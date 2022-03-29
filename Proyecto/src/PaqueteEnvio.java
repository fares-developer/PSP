import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;

public class PaqueteEnvio implements Serializable {

    @Serial
    private static final long serialVersionUID = 8319373084594555343L;
    protected HashMap<String, String> ipNickEmisor;
    protected String mensaje;
    protected HashMap<String, String> ipNickReceptor;
    protected HashMap<String, String> listaIps;

    public PaqueteEnvio(HashMap<String, String> ipNickEmisor, String mensaje, HashMap<String, String> ipNickReceptor,
                        HashMap<String, String> listaIps) {
        this.ipNickEmisor = ipNickEmisor;
        this.mensaje = mensaje;
        this.ipNickReceptor = ipNickReceptor;
        this.listaIps = listaIps;
    }

    public String getMensaje() {
        return mensaje;
    }
    
    public HashMap<String, String> getIpNickEmisor() {
        return ipNickEmisor;
    }

    public HashMap<String, String> getIpNickReceptor() {
        return ipNickReceptor;
    }

    public HashMap<String, String> getListaIps() {
        return listaIps;
    }
}