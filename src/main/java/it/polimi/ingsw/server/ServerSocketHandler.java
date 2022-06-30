package it.polimi.ingsw.server;

import it.polimi.ingsw.constants.Constants;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ServerSocketHandler implements Runnable {
    private final int port;
    private final ExecutorService executorService;
    private final Server server;
    private boolean activeSocket;

    /**
     * constructor of the utility class used just to create a connection, in order to reduce server functionalities
     *
     * @param port -> port of the server
     * @param server -> server who creates a new connection
     */
    public ServerSocketHandler(int port, Server server) {
        this.port = port;
        this.server = server;
        executorService = Executors.newCachedThreadPool();
        activeSocket = true;
    }


    /**
     * method used to create a connection between client and server
     *
     * @param serverSocket -> new server socket related to a client
     */

    public void createConnections(ServerSocket serverSocket) {
        while (activeSocket) {
            try {
                SocketClientConnection socketClient = new SocketClientConnection(serverSocket.accept(), server);
                executorService.submit(socketClient);
            } catch (IOException e) {
                System.err.println("Error while trying to create a client-server connection!" + e.getMessage());
            }
        }
    }

    public void setActiveSocket(boolean activeSocket) {
        this.activeSocket = activeSocket;
    }


    /**
     * thread used to instantiate a new client-server connection
     */
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(Constants.getPort());
            System.out.println("Server socket opened on port " + port + ".\nPlease type \"QUIT\" to exit!");
            createConnections(serverSocket);

        } catch(IOException e) {
            System.err.println("Error during initialization of socket connection! Application will now close...");
            System.exit(0);
        }
    }
}



