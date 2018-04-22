package bg.uni.sofia.fmi.mjt.imdb.client;

import bg.uni.sofia.fmi.mjt.imdb.core.Command;
import bg.uni.sofia.fmi.mjt.imdb.server.ImdbServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ImdbClient {

    private static final int PORT = ImdbServer.SERVER_PORT;
    private static final String HOST = "localhost";
    private static final String SERVER_MESSAGE = "Server sent message: ";
    private static final String HOST_NOT_FOUND_MESSAGE = "Host ID not found!";
    private static final String CONNECTION_ERROR_MESSAGE = "Client could not connect to the server!";


    public static void main(String[] args) {
        try (Socket s = new Socket(HOST, PORT);
             PrintWriter networkOutput = new PrintWriter(s.getOutputStream());
             BufferedReader networkInput = new BufferedReader(new InputStreamReader(s.getInputStream()));
             Scanner userEntry = new Scanner(System.in)) {

            String line = networkInput.readLine();
            System.out.println(SERVER_MESSAGE + line);
            talkToServer(networkInput, networkOutput, userEntry);

        } catch (UnknownHostException uhe) {
            System.out.println(HOST_NOT_FOUND_MESSAGE);
            uhe.printStackTrace();
        } catch (IOException e) {
            System.out.println(CONNECTION_ERROR_MESSAGE);
            e.printStackTrace();
        }

    }

    private static void talkToServer(BufferedReader networkInput, PrintWriter networkOutput, Scanner userEntry) throws IOException {
        boolean run = true;
        while (run) {
            String userRequest = userEntry.nextLine();
            if (userRequest.trim().equalsIgnoreCase(Command.QUIT.toString())) {
                requestQuit(networkInput, networkOutput);
                run = false;
            } else {
                requestCommandFromServer(networkInput, networkOutput, userRequest);
            }
        }
    }


    private static void requestQuit(BufferedReader networkInput, PrintWriter networkOutput) throws IOException {
        networkOutput.println(Command.QUIT);
        networkOutput.flush();
        System.out.println(networkInput.readLine());

    }

    private static void requestCommandFromServer(BufferedReader networkInput, PrintWriter networkOutput, String userRequest) throws IOException {
        networkOutput.println(userRequest);
        networkOutput.flush();
        String response = networkInput.readLine();
        if (response != null) {
            System.out.println(response);
        }
    }

}



