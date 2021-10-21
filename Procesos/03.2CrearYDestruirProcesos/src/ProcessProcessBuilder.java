import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class ProcessProcessBuilder {

    public static void main(String[] args) throws IOException {

        if (args.length <= 0) {
            System.err.println("Introducir programa a ejecutar");
            System.exit(-1);
        }

        ProcessBuilder pb = new ProcessBuilder(args);

        try {
            Process process = pb.start();
            TimeUnit.SECONDS.sleep(10);
            process.destroy();
            System.out.println("El proceso " + Arrays.toString(args) + " acabó");

        }catch(IOException ex) {
            System.err.println("Error");
            System.exit(-1);

        }catch(InterruptedException ex)	{
            System.err.println("Error");
            System.exit(-1);
        }

    }
}