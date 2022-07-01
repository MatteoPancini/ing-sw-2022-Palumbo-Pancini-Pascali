package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.clienttoserver.actions.UserAction;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Class notified by the CLI when there is an input,
 * it parses the given input into the corresponding user action to be sent to the server
 */
public class Parser implements PropertyChangeListener {
    private final ClientConnection clientConnection;
    private final ModelView modelView;
    private final InputChecker inputChecker;


    /**
     * Constructor of the class with the connection and the model view instance of the game
     * @param conn connection
     * @param mv model view
     */
    public Parser(ClientConnection conn, ModelView mv) {
        this.clientConnection = conn;
        this.modelView = mv;
        this.inputChecker = new InputChecker(mv, getModelView().getCli(), conn);
    }

    public ModelView getModelView() {
        return modelView;
    }

    /**
     * Method action called in the property change, executed when the cli notifies the parser
     * it switches between the action received and it calls the input checker which returns the parsed user action
     * to be sent to the server
     * @param actionName action received from the cli
     * @param chosenValue chosen value by the player
     * @return boolean value which tells if the action can be done, and it has been sent to the server
     */
    public synchronized boolean action(String actionName, String chosenValue) {
        UserAction action;
        switch(actionName.toUpperCase()) {
            case "PICKASSISTANT" -> action = inputChecker.checkAssistant(chosenValue);
            case "PICKCLOUD" -> action = inputChecker.checkCloud(chosenValue);
            case "PICKMOVESNUMBER" -> action = inputChecker.checkMoves(chosenValue);
            case "PICKSTUDENT" -> action = inputChecker.checkStudent(chosenValue);
            case "PICKDESTINATION" -> action = inputChecker.checkDestination(chosenValue);
            case "PICKCHARACTER" -> action = inputChecker.checkCharacter(chosenValue);
            case "PICKISLAND" -> action = inputChecker.checkIsland(chosenValue);
            case "PICKPAWNTYPE" -> action = inputChecker.checkPawnType(chosenValue);
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
            clientConnection.sendUserInput(action);
            return true;
        }
        return false;
    }


    /**
     * Method propertyChange overrides the super's method, it handles notifies sent by the CLI
     * @param evt event
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!modelView.getActiveInput()) {
            modelView.getCli().showError("Input error: it's not your turn!");
        } else {
            if (!action(evt.getPropertyName(), evt.getNewValue().toString())) {
                modelView.setActivateInput(true);
            }
        }
    }
}