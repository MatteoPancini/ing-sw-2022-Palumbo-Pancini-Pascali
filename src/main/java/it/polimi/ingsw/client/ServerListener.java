package it.polimi.ingsw.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import it.polimi.ingsw.messages.servertoclient.SerializedAnswer;


public class ServerListener implements Runnable {
    private final Socket socket;
    private final ModelView modelView;
    private final ObjectInputStream objectInputStream;
    private final ActionHandler actionHandler;
    private boolean activeConnection;


    public ServerListener (Socket socket, ObjectInputStream objectInputStream, ModelView modelView, ActionHandler actionHandler) {
        this.modelView = modelView;
        this.socket = socket;
        this.actionHandler = actionHandler;
        this.objectInputStream = objectInputStream;
        activeConnection = true;

    }


    public void readAnswerFromServer(SerializedAnswer answerFromServer) {
        //System.out.print("Ho letto dal server: " + answerFromServer.getServerAnswer().getMessage());
        modelView.setServerAnswer(answerFromServer.getServerAnswer());
        //System.out.println("Setting serverAnswer..." + modelView.getServerAnswer());
        actionHandler.answerHandler();
    }


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


    public void clientConnectionHandler() throws IOException {
        while(isActiveConnection()) {
            try {
                SerializedAnswer serializedAnswer = (SerializedAnswer) objectInputStream.readObject();
                readAnswerFromServer(serializedAnswer);
            } catch(IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

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