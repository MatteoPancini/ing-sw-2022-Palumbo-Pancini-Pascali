package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.servertoclient.Answer;
import it.polimi.ingsw.messages.servertoclient.SerializedAnswer;


public class VirtualClientView {

    private final int clientID;
    private final String clientNickname;
    private final SocketClientConnection socketClientConnection;
    private final GameHandler gameHandler;

    /**
     * constructor of the utility class used to manage communication between client and server
     *
     * @param clientID -> ID of the client created
     * @param clientNickname -> nickname of the client
     * @param socketClientConnection -> connection between the server and the client
     * @param gameHandler -> game handler related to the client
     */
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


    /**
     * method used to prepare an answer to be sent
     *
     * @param answer -> server answer to be sent
     */
    public void sendAnswerToClient(Answer answer) {
        SerializedAnswer serverAnswer = new SerializedAnswer();
        serverAnswer.setServerAnswer(answer);
        socketClientConnection.sendServerMessage(serverAnswer);
    }




}
