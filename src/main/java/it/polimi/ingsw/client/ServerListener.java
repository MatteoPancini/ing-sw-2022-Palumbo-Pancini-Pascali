package it.polimi.ingsw.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import it.polimi.ingsw.messages.servertoclient.SerializedAnswer;

/**
 * ServerListener class is used by the client to always listen to server messages
 */
public class ServerListener implements Runnable {
    private final Socket socket;
    private final ModelView modelView;
    private final ObjectInputStream objectInputStream;
    private final ActionHandler actionHandler;
    private boolean activeConnection;


    public ServerListener(Socket socket, ObjectInputStream objectInputStream, ModelView modelView, ActionHandler actionHandler) {
        this.modelView = modelView;
        this.socket = socket;
        this.actionHandler = actionHandler;
        this.objectInputStream = objectInputStream;
        activeConnection = true;
    }


    /**
     * Method used to read an answer from the server, triggering the action handler class
     *
     * @param answerFromServer -> answer received from server
     */
    public void readAnswerFromServer(SerializedAnswer answerFromServer) {
        modelView.setServerAnswer(answerFromServer.getServerAnswer());
        actionHandler.answerHandler();
    }


    /**
     * method used to close the connection between the client and the server
     */
    public synchronized void closeConnection() {
        System.out.println("Closing connection!");
        activeConnection = false;
        try {
            objectInputStream.close();
            socket.close();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isActiveConnection() {
        return activeConnection;
    }


    /**
     * method that handles deserialization of server messages
     *
     * @throws IOException -> if there is an error with deserialization of messages
     */
    public void clientConnectionHandler() throws IOException {
        while(isActiveConnection()) {
            try {
                SerializedAnswer serializedAnswer = (SerializedAnswer) objectInputStream.readObject();
                readAnswerFromServer(serializedAnswer);
            } catch(IOException | ClassNotFoundException e) {
                System.out.println("Ooops... player disconnected or server is currently out :(");
                System.out.println("Disconnecting from the game... Please try again later!");
                closeConnection();
                break;
            }
        }
    }

    /**
     * Thread that handles the connection between client and server
     */
    @Override
    public void run() {
        try {
            clientConnectionHandler();
        } catch(IOException e) {
            e.printStackTrace();
            closeConnection();
        }
    }
}