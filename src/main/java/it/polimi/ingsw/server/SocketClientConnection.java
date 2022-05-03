package it.polimi.ingsw.server;
import it.polimi.ingsw.controller.GameHandler;
import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.messages.clienttoserver.Message;
import it.polimi.ingsw.messages.clienttoserver.SerializedMessage;
import it.polimi.ingsw.messages.clienttoserver.SetupNickname;
import it.polimi.ingsw.messages.clienttoserver.GameModeChoice;
import it.polimi.ingsw.messages.clienttoserver.actions.UserAction;
import it.polimi.ingsw.messages.servertoclient.DynamicAnswer;
import it.polimi.ingsw.messages.servertoclient.SerializedAnswer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;


public class SocketClientConnection implements Runnable {

    //SocketClientConnection handles a connection between client and server, permitting sending and
    // receiving messages.

    private Socket socket;
    private Server server;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private String nickname;
    private int playersNum;
    private Integer clientID;
    private boolean activeConnection = true;

    public SocketClientConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        activeConnection = true;
        clientID = 0;
        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Error catched while initializing the client: " + e.getMessage());
        }

    }

    private void close(){
        closeConnection();
        System.out.println("Deregistering client...");
        server.deregisterConnection(this);
        System.out.println("Done!");
    }

    private synchronized boolean isActiveConnection(){
        return activeConnection;
    }

    public Socket getSocket() {
        return socket;
    }

    public Integer getClientID() {
        return clientID;
    }

    public void setupGameMode() {
        while(true) {
            try {
                SerializedMessage input = (SerializedMessage) inputStream.readObject();
                Message clientMessage = input.message;
                if(clientMessage instanceof GameModeChoice) {
                    try {
                        int playersNumber = ((GameModeChoice) clientMessage).getPlayersNumber();
                        server.setTotalGamePlayers(playersNumber);
                        server.getGameFromID(clientID).setPlayersNumber(playersNumber);

                        server.getVirtualClientFromID(clientID).sendAnswerToClient(new DynamicAnswer("Players number officially set to " + playersNumber));
                        SerializedAnswer expertModeChoice = new SerializedAnswer();
                        expertModeChoice.setServerAnswer(new DynamicAnswer("Do you want to play in expert mode or not?[y/n]"));
                        Scanner scanner = new Scanner(System.in);
                        String expertMode = scanner.nextLine();
                        if(expertMode.equalsIgnoreCase("y")) {
                            server.getGameFromID(clientID).setExpertMode(true);
                            server.getVirtualClientFromID(clientID).sendAnswerToClient(new DynamicAnswer("The game will be played in Expert Mode!"));
                        }
                        else {
                            server.getVirtualClientFromID(clientID).sendAnswerToClient(new DynamicAnswer("The game will be played in Standard Mode!"));
                        }

                        break;
                    } catch(OutOfBoundException e) {
                        server.getVirtualClientFromID(clientID).sendAnswerToClient(new DynamicAnswer("Please insert a value between 2 and 4"));
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                close();
                System.err.println("Error occurred while setting-up the game mode: " + e.getMessage());
            }


        }

    }


    public void sendServerMessage(SerializedAnswer serverMessage) {
        try {
            outputStream.reset();
            outputStream.writeObject(serverMessage);
            outputStream.flush();
        } catch (IOException e) {
            close();
        }
    }


    public synchronized void readClientStream() throws IOException, ClassNotFoundException {
        SerializedMessage clientInput = (SerializedMessage) inputStream.readObject();
        if(clientInput.message != null) {
            Message userCommand = clientInput.message;
            actionHandler(userCommand);

        } else if (clientInput.userAction != null) {
            UserAction userAction = clientInput.userAction;
            actionHandler(userAction);
        }
    }


    public void actionHandler(Message userCommand) {
        if(userCommand instanceof SetupNickname) {
            checkConnection((SetupNickname) userCommand);
        }
        //TODO: aggiungi la scelta del wizard e disconnesione!

    }


    public void actionHandler(UserAction userAction) {

    }

    private void checkConnection(SetupNickname command) {
        try {
            clientID = server.registerClient(command.getNickname(), this);
            if (clientID == null) {
                activeConnection = false;
                return;
            }
            server.lobby(this);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }


    @Override
    public void run() {
        try {
            while (activeConnection) {
                readClientStream();
            }
        } catch(IOException e) {
            e.printStackTrace();
        } catch(ClassNotFoundException e) {
            e.printStackTrace();

        }
    }



}


