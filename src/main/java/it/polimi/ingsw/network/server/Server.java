package it.polimi.ingsw.network.server;

import it.polimi.ingsw.constants.Constants;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private ServerSocket serverSocket;

    private final ExecutorService executor;

    private List<Connection> connections = new ArrayList<Connection>();
    private Map<String, Connection> waitingConnection = new HashMap<>();


    //method used to add a connection
    private synchronized void registerConnection(Connection c){
        connections.add(c);
    }


    public Server() throws IOException {
        this.serverSocket = new ServerSocket(Constants.getPort());
    }


    public synchronized void lobby(Connection c, String playerNickname) {

        waitingConnection.put(playerNickname, c);

    }



    public void run(){
        int connections = 0;
        System.out.println("Server listening on port: " + Constants.getPort());
        while(true){
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Number of connections: " + connections);
                connections++;
                Connection connection = new Connection(socket, this);
                registerConnection(connection);
                executor.submit(connection);
            } catch (IOException e){
                System.err.println("Connection error!");
            }
        }
    }

}
