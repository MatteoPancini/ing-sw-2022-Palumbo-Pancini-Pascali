package it.polimi.ingsw.client;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.exceptions.AlreadyPlayedAssistantException;
import it.polimi.ingsw.messages.clienttoserver.actions.PickDestination;
import it.polimi.ingsw.messages.clienttoserver.actions.UserAction;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.Transient;


public class Parser implements PropertyChangeListener {
    private ClientConnection clientConnection;
    private ModelView modelView;
    private InputChecker inputChecker;

    public Parser(ClientConnection conn, ModelView mv) {
        this.clientConnection = conn;
        this.modelView = mv;
        this.inputChecker = new InputChecker(mv, getModelView().getCli(), conn);
    }

    public ModelView getModelView() {
        return modelView;
    }

    public ClientConnection getConnectionSocket() {
        return clientConnection;
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
        //modelView.setLastUserAction(message);
        return message;
    }

    public synchronized boolean action(String actionName, String chosenValue) throws AlreadyPlayedAssistantException {
        System.out.println("Entro in action");
        UserAction action = null;
        switch(actionName.toUpperCase()) {
            case "PICKASSISTANT" -> {
                System.out.println("Sono in pickAssistant");

                action = inputChecker.checkAssistant(chosenValue);
                System.out.println("action creata: " + action.toString());

            }
            case "PICKCLOUD" -> {
                action = inputChecker.checkCloud(chosenValue);
            }
            case "PICKMOVESNUMBER" -> {
                action = inputChecker.checkMoves(chosenValue);
            }
            case "PICKSTUDENT" -> {
                System.out.println("Entro in pickStudent");
                action = inputChecker.checkStudent(chosenValue);
            }
            case "PICKDESTINATION" -> {
                action = inputChecker.checkDestination(chosenValue);
            }
            case "PICKCHARACTER" -> {
                action = inputChecker.checkCharacter(chosenValue);
            }
            case "PICKISLAND" -> {
                action = inputChecker.checkIsland(chosenValue);
            }
            case "PICKPAWNTYPE" -> {
                action = inputChecker.checkPawnType(chosenValue);
            }
            case "QUIT" -> {
                inputChecker.quitGame();
                return true;
            }
            default -> {
                modelView.getCli().showError("Unknown input, please try again!");
                return false;
            }

        }

        if(action!=null) {
            System.out.println("invio action");
            clientConnection.sendUserInput(action);
            return true;
        }
        return false;
    }



    //parser ascolta la CLI
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!modelView.getActiveInput()) {
            modelView.getCli().showError("Input error: it's not your turn!");
        } else {
            try {
                if (action(evt.getPropertyName(), evt.getNewValue().toString())) {
                    System.err.println("action giusta: " + evt.getNewValue().toString() + " <-questa");
                    //modelView.setActivateInput(false);
                } else {
                    modelView.setActivateInput(true);
                }
            } catch (AlreadyPlayedAssistantException e) {
                e.printStackTrace();
            }
        }
    }
}