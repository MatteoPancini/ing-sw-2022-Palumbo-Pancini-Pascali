package it.polimi.ingsw.server;

import it.polimi.ingsw.constants.Constants;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ServerSocketHandler implements Runnable {
    // In order to create a smaller Server class, ServerSocketHandler class creates sockets
    // and threads that work with them

    private final int port;
    private final ExecutorService executorService;
    private final Server server;
    private boolean activeSocket;


    public ServerSocketHandler(int port, Server server) {
        this.port = port;
        this.server = server;
        executorService = Executors.newCachedThreadPool();
        activeSocket = true;
    }



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

    public void acceptConnections(ServerSocket serverSocket) {
        while(activeSocket) {
            try {
                SocketClientConnection socketClientConnection = new SocketClientConnection(serverSocket.accept(), server);
                executorService.submit(socketClientConnection);

            } catch (IOException e) {
                System.err.println("Catched error trying to establish a socket client connection: " + e.getMessage());
            }
        }
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(Constants.getPort());
            System.out.println("Server socket opened on port " + port + ".\nPlease type \"QUIT\" to exit!");
            acceptConnections(serverSocket);

        } catch(IOException e) {
            System.err.println("Error during initialization of socket connection! Application will now close...");
            System.exit(0);
        }
    }
}


