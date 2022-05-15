package it.polimi.ingsw.client;

import it.polimi.ingsw.exceptions.AlreadyPlayedAssistantException;
import it.polimi.ingsw.messages.clienttoserver.actions.PickDestination;
import it.polimi.ingsw.messages.clienttoserver.actions.UserAction;
import it.polimi.ingsw.messages.clienttoserver.actions.Action;
import it.polimi.ingsw.server.SocketClientConnection;

import java.awt.desktop.QuitEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class Parser implements PropertyChangeListener {
    private ClientConnection connectionSocket;
    private ModelView modelView;
    private InputChecker inputChecker;

    public Parser(ClientConnection conn, ModelView mv) {
        this.connectionSocket = conn;
        this.modelView = mv;
        this.inputChecker = new InputChecker(mv, getModelView().getCli(), conn);
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
        modelView.setLastUserAction(message);
        return message;
    }

    public UserAction parseStudent(String input) {
        UserAction message;
        message = inputChecker.checkStudent(input);
        modelView.setLastUserAction(message);
        return message;
    }

    public UserAction parseDestination(String input) {
        UserAction message = inputChecker.checkDestination(input);
        modelView.setDestinationUserAction(message);
        Object dest = ((PickDestination) modelView.getDestinationUserAction()).getDestination();
        ((PickDestination) modelView.getDestinationUserAction()).setDestination(dest);
        return message;
    }

    public UserAction parseMoves(String input) {
        UserAction message;
        message = inputChecker.checkMoves(input);
        modelView.setLastUserAction(message);
        return message;
    }

    public UserAction parseCloud(String input) {
        UserAction message;
        message = inputChecker.checkCloud(input);
        modelView.setLastUserAction(message);
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
                inputChecker.quitGame();
                return true;
            }
            default -> {
                return false;
            }

        }
        if(action!=null) {
            connectionSocket.sendUserInput(action);
            //modificare la model view in base all'action inviata
            // qui dovrei ricevere il messaggio dal serve che mi notifica di modificare la model view
            // ricordare che ci sono due metodi di update, uno per la student move e l'altro per le restanti

            return true;
        }
        return false;
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