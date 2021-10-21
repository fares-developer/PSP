import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class conRuntime {

    public static void main(String[] args) throws InterruptedException {

        if (args.length <= 0) {
            System.err.println("Introducir programa a ejecutar");
            System.exit(-1);
        }

        Runtime runtime = Runtime.getRuntime();

        try {
            Process process = runtime.exec(args);
            TimeUnit.SECONDS.sleep(10);
            process.destroy();
            System.out.println("El proceso " + Arrays.toString(args) + " acabó");

        } catch(IOException ex){
            System.err.println("Error");
            System.exit(-1);
        }
    }
}