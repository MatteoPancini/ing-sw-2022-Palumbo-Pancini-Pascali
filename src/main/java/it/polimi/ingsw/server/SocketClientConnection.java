package it.polimi.ingsw.server;
import it.polimi.ingsw.controller.GameHandler;
import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.messages.clienttoserver.*;
import it.polimi.ingsw.messages.clienttoserver.actions.UserAction;
import it.polimi.ingsw.messages.servertoclient.*;
import it.polimi.ingsw.messages.servertoclient.errors.ServerError;
import it.polimi.ingsw.messages.servertoclient.errors.ServerErrorTypes;
import it.polimi.ingsw.model.enumerations.Wizards;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;


public class SocketClientConnection implements Runnable {

    //app.Server.SocketClientConnection handles a connection between client and server, permitting sending and
    // receiving messages.

    private Socket socket;
    private Server server;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private String nickname;
    private int playersNum;
    private Integer clientID;
    private boolean activeConnection;

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

    private void closeConnection() {
        server.unregisterPlayer(clientID);
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
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

    public void setupPlayers(NumOfPlayerRequest request) {
        SerializedAnswer serverAns = new SerializedAnswer();
        serverAns.setServerAnswer(request);
        System.out.println("Inizio setup players");
        sendServerMessage(serverAns);
        while(true) {
            try {
                SerializedMessage input = (SerializedMessage) inputStream.readObject();
                Message clientMessage = input.message;
                if(clientMessage instanceof PlayersNumberChoice) {
                    try {
                        int playersNumber = ((PlayersNumberChoice) clientMessage).getNumberOfPlayers();
                        server.setTotalGamePlayers(playersNumber);
                        server.getGameFromID(clientID).setPlayersNumber(playersNumber);

                        if(playersNumber == 4) {
                            server.getGameFromID(clientID).setTeamMode(true);
                        }

                        server.getVirtualClientFromID(clientID).sendAnswerToClient(new DynamicAnswer("Players number officially set to " + playersNumber, false));

                        /*
                        SerializedAnswer expertModeChoice = new SerializedAnswer();
                        expertModeChoice.setServerAnswer(new ExpertModeAnswer("Do you want to play in expert mode or not?[y/n]"));
                        sendServerMessage(expertModeChoice);


                         */
                        break;
                    } catch(OutOfBoundException e) {
                        server.getVirtualClientFromID(clientID).sendAnswerToClient(new DynamicAnswer("Please insert a value between 2 and 4", true));
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                closeConnection();
                System.err.println("Error occurred while setting-up the game mode: " + e.getMessage());
            }


        }

    }

    public void setupGameMode(ExpertModeAnswer answer) throws IOException, ClassNotFoundException {
        SerializedAnswer serverAns = new SerializedAnswer();
        serverAns.setServerAnswer(answer);
        System.out.println("Inizio setup gamemode");
        sendServerMessage(serverAns);
        while (true) {
            SerializedMessage input = (SerializedMessage) inputStream.readObject();
            Message clientMessage = input.message;
            if (clientMessage instanceof ExpertModeChoice) {
                String expertMode = ((ExpertModeChoice) clientMessage).getExpertChoice();
                if(expertMode.equalsIgnoreCase("y")) {
                    server.getGameFromID(clientID).setExpertMode(true);
                    server.getVirtualClientFromID(clientID).sendAnswerToClient(new DynamicAnswer("Game will be played in Expert Mode!", false));
                }
                else if(expertMode.equalsIgnoreCase("n")){
                    server.getGameFromID(clientID).setExpertMode(false);
                    server.getVirtualClientFromID(clientID).sendAnswerToClient(new DynamicAnswer("Game will be played in Standard Mode!", false));

                } else {
                    setupGameMode(new ExpertModeAnswer("Please type [y/n] to setup the Expert GameMode:\ny: Expert Mode\nn: Standard Mode"));
                }
                break;
            }
        }
    }


    public void sendServerMessage(SerializedAnswer serverMessage) {
        try {
            System.out.println("Ho inviato l'answer: " + serverMessage.getServerAnswer().getMessage());
            outputStream.reset();
            outputStream.writeObject(serverMessage);
            outputStream.flush();
        } catch (IOException e) {
            closeConnection();
        }
    }


    public synchronized void readClientStream() throws IOException, ClassNotFoundException {
        SerializedMessage clientInput = (SerializedMessage) inputStream.readObject();
        System.out.println("Leggo da client messaggio " + clientInput.message.toString());
        if(clientInput.message != null) {
            Message userCommand = clientInput.message;
            actionHandler(userCommand);

        } else if (clientInput.userAction != null) {
            UserAction userAction = clientInput.userAction;
            actionHandler(userAction);
        }
    }


    public void actionHandler(Message userMessage) { //TODO: disconnesione!
        if(userMessage instanceof NicknameChoice) {
            checkConnection((NicknameChoice) userMessage);
        } else if(userMessage instanceof WizardChoice) {
            if(Wizards.isChosen(((WizardChoice) userMessage).getWizardChosen())) {
                server.getVirtualClientFromID(clientID).sendAnswerToClient(new WizardAnswer("Sorry, this wizard has already been chosen. Please choose another wizard!"));
                return;
            } else {
                server.getGameFromID(clientID).getController().setPlayerWizard(clientID, ((WizardChoice) userMessage).getWizardChosen());
                Wizards.removeAvailableWizard(((WizardChoice) userMessage).getWizardChosen());
                server.getGameFromID(clientID).sendSinglePlayer(new WizardAnswer(null, (((WizardChoice) userMessage).getWizardChosen().toString())), clientID);
                server.getGameFromID(clientID).sendExcept(new DynamicAnswer(server.getNicknameFromID(clientID) + "'s Wizard is: " + ((WizardChoice) userMessage).getWizardChosen().toString(), false), clientID );
                server.getGameFromID(clientID).initializeWizards();
            }
        }



    }


    public void actionHandler(UserAction userAction) {


    }

    public void setActiveConnection(boolean activeConnection) {
        this.activeConnection = activeConnection;
    }

    private void checkConnection(NicknameChoice nickname) {
        try {
            clientID = server.registerClient(nickname.getNicknameChosen(), this);
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
        SerializedAnswer serverOut = new SerializedAnswer();
        serverOut.setServerAnswer(new ServerError(ServerErrorTypes.SERVEROUT));
        sendServerMessage(serverOut);
    }



}



