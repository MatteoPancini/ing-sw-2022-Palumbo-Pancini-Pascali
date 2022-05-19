package it.polimi.ingsw.server;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.controller.GameHandler;
import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.messages.servertoclient.*;
import it.polimi.ingsw.messages.servertoclient.errors.ServerError;
import it.polimi.ingsw.messages.servertoclient.errors.ServerErrorTypes;
import it.polimi.ingsw.model.enumerations.Wizards;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;



import java.io.IOException;


public class Server {
    private final ServerSocketHandler serverSocketHandler;

    private GameHandler gameHandler;

    private int totalGamePlayers;

    int currentClientID;

    private final List<SocketClientConnection> waitingPlayersConnection = new ArrayList<>();



    private final Map<String, Integer> nicknameMapID;
    private final Map<Integer, String> idMapNickname;
    private final Map<Integer, VirtualClientView> idMapVirtualClient;
    private final Map<VirtualClientView, SocketClientConnection> virtualClientToClientConnection;



    public Server() {
        serverSocketHandler = new ServerSocketHandler(Constants.getPort(), this);
        nicknameMapID = new HashMap<>();
        totalGamePlayers = 0;
        idMapNickname = new HashMap<>();
        idMapVirtualClient = new HashMap<>();
        virtualClientToClientConnection = new HashMap<>();

        Thread thread = new Thread(this::socketQuitting);
        thread.start();


    }

    //method used to add a connection
    public synchronized Integer registerClient(String clientNickname, SocketClientConnection socketClientConnection) {
        Integer clientID = nicknameMapID.get(clientNickname);

        //checks about nickname
        if (clientID == null) {
            if (waitingPlayersConnection.isEmpty()) {
                gameHandler = new GameHandler(this);
            }
            if (nicknameMapID.keySet().stream().anyMatch(clientNickname::equalsIgnoreCase)) {
                SerializedAnswer duplicateNicknameError = new SerializedAnswer();
                duplicateNicknameError.setServerAnswer(new ServerError(ServerErrorTypes.DUPLICATENICKNAME));
                socketClientConnection.sendServerMessage(duplicateNicknameError);
                return null;
            }

            //checks about waiting list and available slot for the game
            clientID = generateNewClientID();
            gameHandler.addGamePlayer(clientNickname, clientID);
            VirtualClientView virtualClientView = new VirtualClientView(clientID, clientNickname, socketClientConnection, gameHandler);
            if (totalGamePlayers != 0 && waitingPlayersConnection.size() >= totalGamePlayers) {
                virtualClientView.sendAnswerToClient(new ServerError(ServerErrorTypes.FULLGAMESERVER));
                return null;
            }

            idMapNickname.put(clientID, clientNickname);
            nicknameMapID.put(clientNickname, clientID);
            idMapVirtualClient.put(clientID, virtualClientView);
            virtualClientToClientConnection.put(virtualClientView, socketClientConnection);


            System.out.println("Player " + virtualClientView.getClientNickname() + " has successfully connected with id " + virtualClientView.getClientID());
            SerializedAnswer connectionCompleted = new SerializedAnswer();
            connectionCompleted.setServerAnswer(new ConnectionResult("Congrats! You successfully connected with id " + virtualClientView.getClientID(), true));
            socketClientConnection.sendServerMessage(connectionCompleted);


            if (waitingPlayersConnection.size() > 1) {
                gameHandler.sendExcept(new PlayerJoinedNotification("Player " + virtualClientView.getClientNickname() + " has officially joined the game!"), clientID);
            }
        } else { //client già registrato con quel nickname (quindi ID != null)
            VirtualClientView registeredClient = idMapVirtualClient.get(clientID);
            if (socketClientConnection != null) {
                SerializedAnswer duplicateNicknameError = new SerializedAnswer();
                duplicateNicknameError.setServerAnswer(new ServerError(ServerErrorTypes.DUPLICATENICKNAME));
                socketClientConnection.sendServerMessage(duplicateNicknameError);
                return null;
            }
        }

        return clientID;
    }

    public synchronized void unregisterPlayer(int clientID) {
        getGameFromID(clientID).unregisterPlayer(clientID);
        VirtualClientView client = idMapVirtualClient.get(clientID);
        System.out.println("Unregistering client " + client.getClientNickname() + "...");
        idMapVirtualClient.remove(clientID);
        nicknameMapID.remove(client.getClientNickname());
        waitingPlayersConnection.remove(virtualClientToClientConnection.get(client));
        idMapNickname.remove(client.getClientID());
        virtualClientToClientConnection.remove(client);
        System.out.println("Client has been successfully unregistered.");
    }


    public void setTotalGamePlayers (int totalGamePlayers) throws OutOfBoundException {
        if (totalGamePlayers < Constants.NUM_MIN_PLAYERS || totalGamePlayers > Constants.NUM_MAX_PLAYERS) {
            throw new OutOfBoundException();
        } else {
            this.totalGamePlayers = totalGamePlayers;
        }
    }




    public synchronized int generateNewClientID() {
        int clientID = currentClientID;
        currentClientID++;
        return clientID;

    }




    public void socketQuitting() {
        Scanner quittingInput = new Scanner(System.in);
        while (true) {
            String quittingUpper = quittingInput.nextLine().toUpperCase();
            if (quittingUpper.equalsIgnoreCase("QUIT")) {
                getServerSocketHandler().setActiveSocket(false);
                System.exit(0);
                break;
            }
        }
    }

    public String getNicknameFromID(int clientID) {
        return idMapNickname.get(clientID);
    }

    public synchronized ServerSocketHandler getServerSocketHandler() {
        return serverSocketHandler;
    }



    public synchronized void lobby(SocketClientConnection socketClientConnection) throws InterruptedException {
        waitingPlayersConnection.add(socketClientConnection); //new connected player (no needs it's a new player of the game)
        if(waitingPlayersConnection.size() == 1) { //if it's the first player
            socketClientConnection.setupPlayers(new NumOfPlayerRequest("Hi " + idMapVirtualClient.get(socketClientConnection.getClientID()).getClientNickname() + " you are now the host of this lobby.\nPlease choose the number of player you want to play with [2, 3, 4]:"));
            try {
                socketClientConnection.setupGameMode(new ExpertModeAnswer("Do you want to play in Expert Mode? [y/n]"));
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if(waitingPlayersConnection.size() == totalGamePlayers) {
            System.out.println("The lobby has reached the number of player requested by the host.\nStarting the game...");
            for(int timer = 3; timer > 0; timer--) {
                gameHandler.sendBroadcast(new DynamicAnswer("Game is starting in " + timer, false));
                TimeUnit.MILLISECONDS.sleep(1000);
            }
            waitingPlayersConnection.clear();
            Wizards.reset();

            if(gameHandler.isTeamMode()) {
                gameHandler.setupTeams();
            }


            gameHandler.initializeWizards();

        } else {
            gameHandler.sendBroadcast(new DynamicAnswer("There are " + (totalGamePlayers - waitingPlayersConnection.size()) + " slots left!", false));
        }



    }


    public GameHandler getGameFromID(int clientID) {
        return idMapVirtualClient.get(clientID).getGameHandler();
    }


    public VirtualClientView getVirtualClientFromID(int clientID) {
        return idMapVirtualClient.get(clientID);
    }

    public int getIDFromNickname(String clientNickname) {
        return nicknameMapID.get(clientNickname);
    }

    public static void main(String[] args) {
        
        System.out.println(" ____  ____  ____    __    _  _  ____  _  _  ___    ___  ____  ____  _  _  ____  ____ \n" +
                "( ___)(  _ \\(_  _)  /__\\  ( \\( )(_  _)( \\/ )/ __)  / __)( ___)(  _ \\( \\/ )( ___)(  _ \\\n" +
                " )__)  )   / _)(_  /(__)\\  )  (   )(   \\  / \\__ \\  \\__ \\ )__)  )   / \\  /  )__)  )   /\n" +
                "(____)(_)\\_)(____)(__)(__)(_)\\_) (__)  (__) (___/  (___/(____)(_)\\_)  \\/  (____)(_)\\_)");
        System.out.print("This is the Server of Eryantis: Welcome!");
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nDo you want to use a server default configuration?[y/n]");
        System.out.print(">");
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

        System.out.println("Starting Socket Server...");

        Server server = new Server();
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(server.serverSocketHandler);
    }
}







