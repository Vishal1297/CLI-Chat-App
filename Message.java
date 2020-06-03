import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Message {
    public static File getMessageFile() {
        return new File("message.txt");
    }

    public static File getClientFile() {
        return new File("client.txt");
    }

    public static String getRandomMessageOrClient(File f) throws FileNotFoundException {
        String result = null;
        int n = 0;
        final Random rand = new Random();
        final Scanner sc = new Scanner(f);
        while (sc.hasNext()) {
            ++n;
            final String line = sc.nextLine();
            if (rand.nextInt(n) == 0)
                result = line;
        }
        sc.close();
        return result;
    }
}