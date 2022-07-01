package it.polimi.ingsw.client;


import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.exceptions.DuplicateNicknameException;
import it.polimi.ingsw.messages.clienttoserver.Message;
import it.polimi.ingsw.messages.clienttoserver.NicknameChoice;
import it.polimi.ingsw.messages.clienttoserver.SerializedMessage;
import it.polimi.ingsw.messages.clienttoserver.actions.UserAction;
import it.polimi.ingsw.messages.servertoclient.ConnectionResult;
import it.polimi.ingsw.messages.servertoclient.SerializedAnswer;
import it.polimi.ingsw.messages.servertoclient.errors.ServerError;
import it.polimi.ingsw.messages.servertoclient.errors.ServerErrorTypes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;


/**
 * Class ClientConnection handles the connection between client and server
 */
public class ClientConnection {
    private final String serverAddress;
    private final int serverPort;
    private ObjectOutputStream outputStream;

    /**
     * Constructor of the class
     */
    public ClientConnection() {
        this.serverAddress = Constants.getAddress();
        this.serverPort = Constants.getPort();
    }

    /**
     * Method setupNickname is used in the initial phase to setup the nickname
     * @param nickname chosen nickname
     * @param modelView model view instance
     * @param actionHandler action handler
     * @return boolean value
     * @throws DuplicateNicknameException exception
     */
    public boolean setupNickname(String nickname, ModelView modelView, ActionHandler actionHandler) throws DuplicateNicknameException {
        try {
            System.out.println("Trying to configure a socket connection...");
            System.out.println("Opening a socket server communication on port " + serverPort + "...");
            Socket socket;
            try {
                socket = new Socket(serverAddress, serverPort);
            } catch (SocketException | UnknownHostException e) {
                return false;
            }
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            while (true) {
                try {
                    sendUserInput(new NicknameChoice(nickname));
                    if (nicknameAvailabilityCheck(inputStream.readObject())) {
                        break;
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.err.println(e.getMessage());
                }
            }
            ServerListener serverListener = new ServerListener(socket, inputStream, modelView, actionHandler);
            Thread thread = new Thread(serverListener);
            thread.start();

            return true;

        } catch (IOException e) {
            System.err.println("Error during socket configuration! Application will now close.");
            System.exit(0);
            return false;
        }
    }

    /**
     * Method nicknameAvailabilityCheck sends the nickname typed by the user and waits for the server check
     * @param nicknameIn typed nickname
     * @return true if available, false if not
     * @throws DuplicateNicknameException
     */
    public boolean nicknameAvailabilityCheck(Object nicknameIn) throws DuplicateNicknameException {
        SerializedAnswer answer = (SerializedAnswer) nicknameIn;
        if (answer.getServerAnswer() instanceof ConnectionResult && ((ConnectionResult) answer.getServerAnswer()).isConnectionCompleted()) {
            System.out.println(answer.getServerAnswer().getMessage());
            return true;
        } else if (answer.getServerAnswer() instanceof ServerError) {
            if (((ServerError) answer.getServerAnswer()).getError().equals(ServerErrorTypes.DUPLICATENICKNAME)) {
                System.err.println("This nickname is already in use! Please choose one other.");
                throw new DuplicateNicknameException();
            }
        } else if (((ServerError) answer.getServerAnswer()).getError().equals(ServerErrorTypes.FULLGAMESERVER)) {
            System.err.println(
                    "This match is already full, please try again later!\nApplication will now close...");
            System.exit(0);
        }
        return false;
    }

    /**
     * Method sendUserInput sends a message from client to server
     * @param message serializable message
     */
    public void sendUserInput(Message message) {
        SerializedMessage userInput = new SerializedMessage(message);
        try {
            outputStream.reset();
            outputStream.writeObject(userInput);
            outputStream.flush();
        } catch (IOException e) {
            System.err.println("Error during send process.");
            System.err.println(e.getMessage());
        }
    }

    /**
     * Method sendUserInput sends a user action from client to server
     * @param action action
     */
    public void sendUserInput(UserAction action) {
        SerializedMessage userInput = new SerializedMessage(action);
        try {
            outputStream.reset();
            outputStream.writeObject(userInput);
            outputStream.flush();
        } catch (IOException e) {
            System.err.println("Error during send process.");
        }
    }
}