package it.polimi.ingsw.server;

import it.polimi.ingsw.constants.Constants;
//import it.polimi.ingsw.controller.GameHandler;
import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.messages.servertoclient.DynamicAnswer;
import it.polimi.ingsw.messages.servertoclient.PlayerJoinedNotification;
import it.polimi.ingsw.messages.servertoclient.SerializedAnswer;
import it.polimi.ingsw.messages.servertoclient.errors.ServerError;
import it.polimi.ingsw.messages.servertoclient.errors.ServerErrorTypes;
import it.polimi.ingsw.model.Game;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
public class Server {
    private ServerSocketHandler serverSocketHandler;

    private GameHandler gameHandler;

    private int totaleGamePlayers;

    int currentClientID;

    private List<SocketClientConnection> waitingPlayers = new ArrayList<>();


    //private List<SocketClientConnection> socketClientConnections = new ArrayList<SocketClientConnection>();
    private List<SocketClientConnection> waitingConnection = new ArrayList<>();

    private final Map<String, Integer> nicknameMapID;
    private final Map<Integer, String> idMapNickname;
    private final Map<Integer, VirtualClientView> idMapVirtualClient;
    private final Map<VirtualClientView, SocketClientConnection> virtualClientToClientConnection;



    //method used to add a connection
    public synchronized Integer registerClient(String nickname, SocketClientConnection socketClientConnection) {
        Integer clientID = nicknameMapID.get(nickname);

        //checks about nickname
        if (clientID == null) {
            if (waitingPlayers.isEmpty()) {
                gameHandler = new GameHandler(this);
            }
            if (nicknameMapID.keySet().stream().anyMatch(nickname::equalsIgnoreCase)) {
                SerializedAnswer duplicateNicknameError = new SerializedAnswer();
                duplicateNicknameError.setServerAnswer(new ServerError(ServerErrorTypes.DUPLICATENICKNAME));
                socketClientConnection.sendServerMessage(duplicateNicknameError);
                return null;
            }

            //checks about waiting list and available slot for the game
            currentClientID = generateNewClientID();
            gameHandler.addGamePlayer(nickname, clientID);
            VirtualClientView virtualClientView = new VirtualClientView(clientID, nickname, socketClientConnection, gameHandler);
            if (totaleGamePlayers != 0 && waitingPlayers.size() >= totaleGamePlayers) {
                virtualClientView.sendAnswerToClient(new ServerError(ServerErrorTypes.FULLGAMESERVER));
                return null;
            }

            //qui potrebbero starci un pò di map... al momento ometto
            System.out.println("Player " + virtualClientView.getClientNickname() + " has successfully connected with id " + virtualClientView.getClientID());

            if (waitingPlayers.size() > 1) {
                gameHandler.sendBroadcast(new PlayerJoinedNotification("Player " + virtualClientView.getClientNickname() + " has officially joined the game!"));
            } else { //client già registrato con quel nickname (quindi ID != null)
                VirtualClientView registeredClient = idMapVirtualClient.get(clientID);
                if (socketClientConnection != null) {
                    SerializedAnswer duplicateNicknameError = new SerializedAnswer();
                    duplicateNicknameError.setServerAnswer(new ServerError(ServerErrorTypes.DUPLICATENICKNAME));
                    socketClientConnection.sendServerMessage(duplicateNicknameError);
                    return null;
                }
            }
        }

        return clientID;
    }

    public void setTotalGamePlayers (int totalGamePlayers) throws OutOfBoundException {
        if (totalGamePlayers < Constants.NUM_MIN_PLAYERS || totalGamePlayers > Constants.NUM_MAX_PLAYERS) {
            throw new OutOfBoundException();
        } else {
            this.totaleGamePlayers = totalGamePlayers;
        }
    }




    public synchronized int generateNewClientID() {
        int clientID = currentClientID;
        currentClientID++;
        return clientID;

    }



    public Server() {
        serverSocketHandler = new ServerSocketHandler(Constants.getPort(), this);
        nicknameMapID = new HashMap<>();
        totaleGamePlayers = 0;
        idMapNickname = new HashMap<>();
        idMapVirtualClient = new HashMap<>();
        virtualClientToClientConnection = new HashMap<>();

        Thread thread = new Thread(this::socketQuitting);
        thread.start();


    }


    public void socketQuitting() {
        Scanner quittingInput = new Scanner(System.in);
        while (true) {
            if (quittingInput.next().equalsIgnoreCase("QUIT")) {
                getServerSocketHandler().setActiveSocket(false);
                System.exit(0);
                break;
            }
        }
    }


    public synchronized ServerSocketHandler getServerSocketHandler() {
        return serverSocketHandler;
    }



    public synchronized void lobby(SocketClientConnection c) throws InterruptedException{
        waitingConnection.add(c); //first connected player
        if(waitingConnection.size() == 1) {

            SerializedAnswer serverAns = new SerializedAnswer();
            serverAns.setServerAnswer(new DynamicAnswer("Hi " + idMapVirtualClient.get(c.getClientID()).getClientNickname() + " you are now the host of this lobby.\nPlease choose the number of player you want to play with [2, 3, 4]:"));
            c.sendServerMessage(serverAns);
            c.setupGameMode();
        }


    }


    public GameHandler getGameFromID(int clientID) {
        return idMapVirtualClient.get(clientID).getGameHandler();
    }


    public VirtualClientView getVirtualClientFromID(int clientID){
        return idMapVirtualClient.get(clientID);
    }

    public int getIDFromNickname(String clientNickname) {
        return nicknameMapID.get(clientNickname);
    }

    public static void main(String[] args) {
        System.out.print("This is the Server of Eryantis: Welcome!");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Do you want to use a server default configuration?[y/n]");
        String configServer = scanner.nextLine();
        if (configServer.equalsIgnoreCase("y")) {
            Constants.setPort(1234);
        } else {
            System.out.println(">Please insert the server's port number");
            System.out.print(">");
            int portNumber = 0;
            try {
                portNumber = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.err.println("Sorry, you have to insert a number!\nApplication will now close...");
                System.exit(-1);
            }
            if (portNumber < 0 || (portNumber > 0 && portNumber < 1024)) {
                System.err.println("Sorry, insert a port between 1024 and 65535");
                main(null);
            }
            Constants.setPort(portNumber);

        }

        System.out.println("Starting Socket Server");

        Server server = new Server();
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(server.serverSocketHandler);
    }
}



 */



