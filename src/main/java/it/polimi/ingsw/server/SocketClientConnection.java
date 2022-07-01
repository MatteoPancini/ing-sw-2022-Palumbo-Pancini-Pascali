package it.polimi.ingsw.server;
import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.messages.clienttoserver.*;
import it.polimi.ingsw.messages.clienttoserver.actions.*;
import it.polimi.ingsw.messages.servertoclient.*;
import it.polimi.ingsw.messages.servertoclient.errors.ServerError;
import it.polimi.ingsw.messages.servertoclient.errors.ServerErrorTypes;
import it.polimi.ingsw.model.enumerations.Wizards;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Class used to handle a connection between client and server, sending and receiving messages
 */
public class SocketClientConnection implements Runnable {

    private final Socket socket;
    private final Server server;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
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
            System.err.println("Error caught while initializing the client: " + e.getMessage());
        }

    }

    /**
     * method used to close a connection between client and server
     */
    public void closeConnection() {
        server.unregisterPlayer(clientID);
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    public Integer getClientID() {
        return clientID;
    }

    /**
     * method used to set up player's number of the game
     *
     * @param request -> message containing number of players of the game
     */
    public void setupPlayers(NumOfPlayerRequest request) {
        SerializedAnswer serverAns = new SerializedAnswer();
        serverAns.setServerAnswer(request);
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
                        server.getVirtualClientFromID(clientID).sendAnswerToClient(new DynamicAnswer("Players number officially set to " + playersNumber, false));
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

    /**
     * method used to set up game mode of the game
     *
     * @param answer -> answer sent from server to client, asking game mode
     * @throws IOException -> in case client triggers an exception from input
     * @throws ClassNotFoundException -> in case client triggers an exception from input
     */
    public void setupGameMode(ExpertModeAnswer answer) throws IOException, ClassNotFoundException {
        SerializedAnswer serverAns = new SerializedAnswer();
        serverAns.setServerAnswer(answer);
        sendServerMessage(serverAns);
        while (true) {
            SerializedMessage input = (SerializedMessage) inputStream.readObject();
            Message clientMessage = input.message;
            if (clientMessage instanceof ExpertModeChoice) {
                String expertMode = ((ExpertModeChoice) clientMessage).getExpertChoice();
                if(expertMode.equalsIgnoreCase("y")) {
                    server.getGameFromID(clientID).setExpertMode(true);
                    server.getVirtualClientFromID(clientID).sendAnswerToClient(new DynamicAnswer("Game will be played in Expert Mode!\nPlease wait for other players to connect :)", false));
                }
                else if(expertMode.equalsIgnoreCase("n")){
                    server.getGameFromID(clientID).setExpertMode(false);
                    server.getVirtualClientFromID(clientID).sendAnswerToClient(new DynamicAnswer("Game will be played in Standard Mode!\nPlease wait for other players to connect :)", false));
                } else {
                    setupGameMode(new ExpertModeAnswer("Please type [y/n] to setup the Expert GameMode:\ny: Expert Mode\nn: Standard Mode"));
                }
                break;
            }
        }
    }


    /**
     * method used to send a message from client to server
     *
     * @param serverMessage -> server message
     */
    public void sendServerMessage(SerializedAnswer serverMessage) {
        try {
            outputStream.reset();
            outputStream.writeObject(serverMessage);
            outputStream.flush();
        } catch (IOException e) {
            closeConnection();
        }
    }

    /**
     * method used to read (constantly) messages from client
     *
     * @throws IOException -> exception triggered by client error
     * @throws ClassNotFoundException -> exception triggered by client error
     */
    public synchronized void readClientStream() throws IOException, ClassNotFoundException {
        SerializedMessage clientInput = (SerializedMessage) inputStream.readObject();
        if(clientInput.message != null) {
            Message userMessage = clientInput.message;
            actionHandler(userMessage);

        } else if (clientInput.userAction != null) {
            UserAction userAction = clientInput.userAction;
            actionHandler(userAction);
        }
    }

    /**
     * method used to switch choices messages during pre-game phase
     *
     * @param userMessage -> message sent by client
     */
    public void actionHandler(Message userMessage) {
        if(userMessage instanceof NicknameChoice) {
            checkConnection((NicknameChoice) userMessage);
        } else if(userMessage instanceof WizardChoice) {
            if(Wizards.isChosen(((WizardChoice) userMessage).getWizardChosen())) {
                server.getVirtualClientFromID(clientID).sendAnswerToClient(new WizardAnswer("Sorry, this wizard has already been chosen. Please choose another wizard!"));
            } else {
                server.getGameFromID(clientID).getController().setPlayerWizard(clientID, ((WizardChoice) userMessage).getWizardChosen());
                Wizards.removeAvailableWizard(((WizardChoice) userMessage).getWizardChosen());
                server.getGameFromID(clientID).sendSinglePlayer(new WizardAnswer(null, (((WizardChoice) userMessage).getWizardChosen().toString())), clientID);
                server.getGameFromID(clientID).sendExcept(new DynamicAnswer(server.getNicknameFromID(clientID) + "'s Wizard is: " + ((WizardChoice) userMessage).getWizardChosen().toString(), false), clientID );
                server.getGameFromID(clientID).initializeWizards();
            }
        } else if (userMessage instanceof QuitGame) {
            server.getGameFromID(clientID).sendExcept(new DynamicAnswer("Player " + server.getNicknameFromID(clientID) + " has disconnected from the game!", false), clientID);
            server.getGameFromID(clientID).endPlayerGame(server.getNicknameFromID(clientID));
            closeConnection();
        }

    }

    public void actionHandler(UserAction userAction) {
        if(server.getGameFromID(clientID).isMatchStarted()) {
            if(userAction instanceof PickAssistant) {
                server.getGameFromID(clientID).parseActions(userAction, "PickAssistant");
            }
            if(userAction instanceof PickStudent) {
                server.getGameFromID(clientID).parseActions(userAction, "PickStudent");
            }

            if(userAction instanceof PickDestination) {
                if(server.getGameFromID(clientID).getController().getExpertController() != null) {
                    if(server.getGameFromID(clientID).getController().getExpertController().isGrannyHerbsEffect()) {
                        server.getGameFromID(clientID).parseActions(userAction, "GrannyHerbsTile");
                    } else {
                        server.getGameFromID(clientID).parseActions(userAction, "PickDestination");
                    }
                } else {
                    server.getGameFromID(clientID).parseActions(userAction, "PickDestination");
                }
            }

            if(userAction instanceof PickMovesNumber) {
                server.getGameFromID(clientID).parseActions(userAction, "PickMovesNumber");
            }
            if(userAction instanceof PickCloud) {
                server.getGameFromID(clientID).parseActions(userAction, "PickCloud");
            }

            if(userAction instanceof PickCharacter) {
                if(server.getGameFromID(clientID).getExpertMode()) {
                    server.getGameFromID(clientID).parseActions(userAction, "PickCharacter");
                } else {
                    server.getGameFromID(clientID).sendSinglePlayer(new ServerError(ServerErrorTypes.NOTVALIDINPUT, "Game is in standard mode! You can't play a character card!"), clientID);
                }
            }

            if(userAction instanceof PickPawnType) {
                server.getGameFromID(clientID).parseActions(userAction, "PickPawnType");
            }
            if(userAction instanceof PickCharacterActionsNum) {
                server.getGameFromID(clientID).parseActions(userAction, "PickCharacterActionsNum");
            }
        } else {
            server.getGameFromID(clientID).sendSinglePlayer(new ServerError(ServerErrorTypes.NOTVALIDINPUT), clientID);
        }
    }


    /**
     * method used to check client registration parameters and register a new client
     *
     * @param nickname -> nickname chosen by player
     */
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

    /**
     * thread that keep up reading messages from client, while connection is active
     */
    @Override
    public void run() {
        try {
            while (activeConnection) {
                readClientStream();
            }
        } catch(IOException e) {
            GameHandler game = server.getGameFromID(clientID);
            String player = server.getNicknameFromID(clientID);
            server.unregisterPlayer(clientID);
            if (game.isMatchStarted()) {
                game.endPlayerGame(player);
            }
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
        SerializedAnswer serverOut = new SerializedAnswer();
        serverOut.setServerAnswer(new ServerError(ServerErrorTypes.SERVEROUT));
        sendServerMessage(serverOut);
    }
}