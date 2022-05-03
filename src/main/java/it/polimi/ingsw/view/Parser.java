package it.polimi.ingsw.view;
import it.polimi.ingsw.exceptions.AlreadyPlayedAssistantException;
import it.polimi.ingsw.messages.clienttoserver.actions.UserAction;
import it.polimi.ingsw.server.SocketClientConnection;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class Parser implements PropertyChangeListener {
    private ClientConnection connectionSocket;
    private ModelView modelView;
    private InputChecker inputChecker;

    public Parser(ClientConnection conn, ModelView mv, InputChecker checker) {
        this.connectionSocket = conn;
        this.modelView = mv;
        this.inputChecker = checker;
    }

    public ModelView getModelView() {
        return modelView;
    }

    public ClientConnection getConnectionSocket() {
        return connectionSocket;
    }


    public UserAction parseAssistant(String input) throws AlreadyPlayedAssistantException {
        UserAction message = null;
        try {
            message = inputChecker.checkAssistant(input);
        } catch (AlreadyPlayedAssistantException e) { modelView.getCli().getOutput().println(e.getMessage()); }
        return message;
    }

    public UserAction parseStudentMove(String input1, String input2) {
        UserAction message;
        message = inputChecker.checkStudentMove(input1, input2);
        return message;
    }

    public UserAction parseMoves(String input) {
        UserAction message;
        message = inputChecker.checkMoves(input);
        return message;
    }

    public UserAction parseCloud(String input) {
        UserAction message;
        message = inputChecker.checkCloud(input);
        return message;
    }

    //definire altre azioni che il client può richiedere in qualsiasi momento
    public synchronized boolean action(String input) {
        UserAction message = null;
        switch(input.toUpperCase()) {
            case "PICK_CHARACTER" -> {
                message = inputChecker.checkCharacter(input);
            }
            //case ...
        }
        if(message!=null) {
            connectionSocket.sendUserInput(message);
            return true;
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!modelView.getActiveInput()) {
            System.out.println("Input error: it's not your turn!");
        } else if (action(evt.getNewValue().toString())) {
            modelView.disableInput();
        } else {
            modelView.enableInput();
        }
    }
}