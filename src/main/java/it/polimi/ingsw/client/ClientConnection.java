package it.polimi.ingsw.client;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.exceptions.DuplicateNicknameException;
import it.polimi.ingsw.messages.clienttoserver.SetupNickname;
import it.polimi.ingsw.messages.servertoclient.SerializedAnswer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ClientConnection {
   // ClientConnection class handles the connection between the client and the server.

    private final String serverAddress;
    private final int serverPort;

    public ClientConnection() {
        this.serverAddress = Constants.getAddress();
        this.serverPort = Constants.getPort();
    }


    //Method setup initializes a new socket connection and handles the nickname-choice response. It
    //   * loops until the server confirms the successful connection (with no nickname duplication and
    //   * with a correctly configured match lobby).
    public boolean setupNickname(String nickname) throws DuplicateNicknameException {
        try {
            System.out.println("Trying to configure a socket connection...");
            System.out.println("Opening a socket server communication on port " + serverPort + "...");
            Socket socket;
            try {
                socket = new Socket(serverAddress, serverPort);
            } catch (SocketException | UnknownHostException e) {
                return false;
            }

            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            try {
                SerializedAnswer answer = (SerializedAnswer) input;

            }


            return true;

        } catch (IOException e) {
            System.err.println("Error during socket configuration! Application will now close.");
            System.exit(0);
            return false;
        }
    }
