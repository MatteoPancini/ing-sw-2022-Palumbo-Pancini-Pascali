package it.polimi.ingsw.view;
import it.polimi.ingsw.exceptions.AlreadyPlayedAssistantException;
import it.polimi.ingsw.messages.clienttoserver.actions.UserAction;
import it.polimi.ingsw.model.enumerations.Action;
import it.polimi.ingsw.server.SocketClientConnection;

import java.awt.desktop.QuitEvent;
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

    public UserAction parseStudent(String input) {
        UserAction message;
        message = inputChecker.checkStudent(input);
        return message;
    }

    public UserAction parseDestination(String input) {
        UserAction message = inputChecker.checkDestination(input);
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


    public synchronized boolean action(String actionName, String chosenValue) throws AlreadyPlayedAssistantException {
        UserAction action = null;
        switch(actionName.toUpperCase()) {
            case "PICKASSISTANT" -> {
                action = inputChecker.checkAssistant(chosenValue);
            }
            case "PICKCLOUD" -> {
                action = inputChecker.checkCloud(chosenValue);
            }
            case "PICKMOVESNUMBER" -> {
                action = inputChecker.checkMoves(chosenValue);
            }
            case "PICKSTUDENT" -> {
                action = inputChecker.checkStudent(chosenValue);
            }
            case "PICKDESTINATION" -> {
                action = inputChecker.checkDestination(chosenValue);
            }
            case "PICKCHARACTER" -> {
                action = inputChecker.checkCharacter(chosenValue);
            }
            case "QUIT" -> {
                connectionSocket.sendUserInput(QUIT); ???
            }
            default -> {
                return false;
            }

        }
        if(action!=null) {
            connectionSocket.sendUserInput(action);
            //modificare la model view in base all'action inviata

            return true;
        }
    }

    public void updateModelView(UserAction action) {
        switch(action) {
            case "PICKASSISTANT" -> {
                modelView.getVisualBoard().set
            }
        }
    }


    //parser ascolta la CLI
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!modelView.getActiveInput()) {
            System.out.println("Input error: it's not your turn!");
        } else {
            try {
                if (action(evt.getPropertyName(), evt.getNewValue().toString())) {
                    modelView.disableInput();
                } else {
                    modelView.enableInput();
                }
            } catch (AlreadyPlayedAssistantException e) {
                e.printStackTrace();
            }
        }
    }
}


//TODO finire action() con le restanti userAction (tutte)