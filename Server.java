import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static ServerSocket server;
    private static int PORT = 9876;

    public static void main(String args[]) {
        try {
            server = new ServerSocket(PORT);
            while (true) {
                Socket socket = server.accept();
                // read from socket to ObjectInputStream object
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                // convert ObjectInputStream object to String
                String message = ois.readObject().toString();
                if (socket.isConnected()) {
                    System.out.println(message.split(" ")[0] + " : ACK\n");
                } else {
                    System.out.println(message.split(" ")[0] + " : NACK\n");
                }
                System.out.println(message + "\n");
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(message);
                ois.close();
                oos.close();
                socket.close();
                // terminate the server if client sends exit or quit request
                if (message.equalsIgnoreCase("exit") || message.equalsIgnoreCase("quit"))
                    break;
            }
            System.out.println("Shutting Down Socket Server...\n");
            server.close();
        } catch (Exception e) {
            if (server != null && !server.isClosed()) {
                try {
                    server.close();
                } catch (IOException io) {
                    io.printStackTrace(System.err);
                }
            }
            System.exit(1);
        }
    }
}