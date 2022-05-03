
package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.GameHandler;
import it.polimi.ingsw.messages.servertoclient.Answer;
import it.polimi.ingsw.messages.servertoclient.SerializedAnswer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class VirtualClientView implements PropertyChangeListener {

    //VirtualClientView manages comunication between server and client, representing a virtual instance of the client.
    //Useful to prepare server messages for client


    private int clientID;
    private String clientNickname;
    private SocketClientConnection socketClientConnection;
    private GameHandler gameHandler;

    public VirtualClientView( int clientID, String clientNickname, SocketClientConnection socketClientConnection, GameHandler gameHandler) {
        this.clientID = clientID;
        this.clientNickname = clientNickname;
        this.socketClientConnection = socketClientConnection;
        this.gameHandler = gameHandler;
    }


    public String getClientNickname() {
        return clientNickname;
    }

    public int getClientID() {
        return clientID;
    }

    public GameHandler getGameHandler() {
        return gameHandler;
    }

    public SocketClientConnection getSocketClientConnection() {
        return socketClientConnection;
    }


    //IN REALTA' QUESTA CLASSE SI POTREBBE ANCHE GESTIRE DIRETTAMENTE DAL SERVER... FORSE PIU' COMODO COMUNQUE QUI
    public void sendAnswerToClient(Answer answer) {
        SerializedAnswer serverAnswer = new SerializedAnswer();
        serverAnswer.setServerAnswer(answer);
        socketClientConnection.sendServerMessage(serverAnswer);
    }




}

