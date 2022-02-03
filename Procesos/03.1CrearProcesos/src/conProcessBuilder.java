
import java.io.IOException;

public class conProcessBuilder {

    public static void main(String[] args) throws IOException {

        //Para abrir cmd concretamente se necesita pasar "cmd","/C","start"
        ProcessBuilder pb = new ProcessBuilder("cmd.exe");
        pb.start();


    }
}
