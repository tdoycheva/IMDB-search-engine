package bg.uni.sofia.fmi.mjt.imdb.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ImdbServer  {
    public static final int SERVER_PORT = 4444;

    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(SERVER_PORT)) {
            //System.out.println("Server is waiting for connections...");
            while (true) {
                Socket s = ss.accept();
                new ImdbServerResponderThread(s).start();
            }
        } catch (IOException e) {

            System.out.println("There is a problem with the server socket");
            e.printStackTrace();
        }
    }
}
