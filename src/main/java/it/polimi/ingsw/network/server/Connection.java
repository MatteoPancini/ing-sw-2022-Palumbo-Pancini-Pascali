package it.polimi.ingsw.network.server;



import it.polimi.ingsw.constants.Constants;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Connection implements Runnable {

    private Socket socket;
    private Server server;
    private Scanner inputStream;
    private PrintWriter outputStream;
    private String nickname;
    private int playersNum;

    private final boolean active = true;

    public Connection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    private void close(){
        closeConnection();
        System.out.println("Deregistering client...");
        server.deregisterConnection(this);
        System.out.println("Done!");
    }

    private synchronized boolean isActive(){
        return active;
    }

    public Socket getSocket() {
        return socket;
    }


    //FAI LOBBY (server) + run di connection

    @Override
    public void run() {
        try {
            inputStream = new Scanner(socket.getInputStream());
            outputStream = new PrintWriter(socket.getOutputStream());

            outputStream.println("Welcome to Eriantys! What's your nickname?");
            nickname = inputStream.nextLine();
            outputStream.println("Which game mode do you want to start? (2, 3 or 4 players)");
            playersNum = 0;
            try {
                playersNum = inputStream.nextInt();
            } catch (InputMismatchException e) {
                System.err.println("Sorry, you have to insert a number between 2 and 4!\nApplication will now close...");
                System.exit(-1);
            }
            Constants.setPlayersNum(playersNum);

            server.lobby(this, nickname);

            while(isActive()){
                String read = inputStream.nextLine();
                notify(read);
            }

        } catch(IOException e){
            System.err.println(e.getMessage());
        } finally {
            close();
        }
    }

    }
}