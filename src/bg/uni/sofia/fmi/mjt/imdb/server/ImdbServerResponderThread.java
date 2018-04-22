package bg.uni.sofia.fmi.mjt.imdb.server;

import bg.uni.sofia.fmi.mjt.imdb.core.Command;
import bg.uni.sofia.fmi.mjt.imdb.exceptions.CommandTypoException;
import bg.uni.sofia.fmi.mjt.imdb.exceptions.UnknownCommandException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ImdbServerResponderThread extends Thread {

    private Socket socket;

    private static final String GOODBYE_MESSAGE = "Goodbye, Client!";
    private static final String PROCESSED_CONNECTION_MESSAGE = "Processed connection from port:";
    private static final String ENTER_COMMAND_MESSAGE = "Please, enter a command:";
    private static final String NO_MATCH = "No match! ";
    private static final String CLOSING_MESSAGE = "Closing client from port ";


    public ImdbServerResponderThread(Socket s) {
        socket = s;
    }

    @Override
    public void run() {
        try (BufferedReader clientInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter clientOutput = new PrintWriter(socket.getOutputStream())) {

            System.out.println(PROCESSED_CONNECTION_MESSAGE + socket.getPort());
            //prints to the client
            clientOutput.println(ENTER_COMMAND_MESSAGE);
            clientOutput.flush();

            boolean run = true;
            while (run) {
                String clientRequest = clientInput.readLine();
                if (Command.QUIT.toString().equals(clientRequest)) {
                    clientOutput.println(GOODBYE_MESSAGE);
                    clientOutput.flush();
                    run = false;
                    socket.close();

                } else {
                    try {
                        ICommand command = CommandFactory.factory(clientRequest);
                        byte[] response = command.process();

                        socket.getOutputStream().write(response);
                        socket.getOutputStream().write(System.lineSeparator().getBytes());
                        socket.getOutputStream().flush();
                        /*clientOutput.print(response);
                        clientOutput.flush();*/

                    } catch (UnknownCommandException | CommandTypoException e) {
                        System.out.println(CLOSING_MESSAGE + socket.getPort());
                        clientOutput.print(e.getMessage() + CLOSING_MESSAGE);
                        clientOutput.flush();
                        socket.close();
                        run = false;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("There is an issue with the connection");
            e.printStackTrace();


        }


    }
}
