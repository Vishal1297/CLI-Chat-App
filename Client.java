import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    // Maximum Connections
    private static int MAX = 5;

    public static void main(final String[] args) {
        try {
            final InetAddress host = InetAddress.getLocalHost();
            Socket socket = null;
            ObjectOutputStream oos = null;
            ObjectInputStream ois = null;
            for (int i = 1; i <= MAX; i++) {
                socket = new Socket(host.getHostName(), 9876);
                final String clientName = Message.getRandomMessageOrClient(Message.getClientFile());
                final String msg = Message.getRandomMessageOrClient(Message.getMessageFile());
                final String log = clientName + " : " + msg;
                System.out.println();
                if (socket.isConnected()) {
                    System.out.println(clientName + " : ACK\n");
                } else {
                    System.out.println(clientName + " : NACK\n");
                }
                // write to socket using ObjectOutputStream
                oos = new ObjectOutputStream(socket.getOutputStream());
                // System.out.println("Sending request to Socket Server");
                if (i == MAX) {
                    oos.writeObject("exit");
                } else {
                    oos.writeObject(log);
                }
                ois = new ObjectInputStream(socket.getInputStream());
                System.out.println(ois.readObject().toString());
                ois.close();
                oos.close();
                Thread.sleep(100);
            }
        } catch (IOException io) {
            System.out.println("Server Not Running...\n");
        } catch (InterruptedException ie) {
            System.out.println(ie.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Restart Required\n");
            System.exit(1);
        }
    }
}