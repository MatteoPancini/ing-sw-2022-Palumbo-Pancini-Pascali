package it.polimi.ingsw.client;

import it.polimi.ingsw.exceptions.AlreadyPlayedAssistantException;
import it.polimi.ingsw.messages.clienttoserver.actions.UserAction;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Class notified by the CLI when there is an input,
 * it parses the given input into the corresponding user action to be sent to the server
 */
public class Parser implements PropertyChangeListener {
    private ClientConnection clientConnection;
    private ModelView modelView;
    private InputChecker inputChecker;


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
     * @throws AlreadyPlayedAssistantException
     */
    public synchronized boolean action(String actionName, String chosenValue) throws AlreadyPlayedAssistantException {
        //System.out.println("Entro in action");
        UserAction action = null;
        //System.out.println(actionName.toUpperCase());
        switch(actionName.toUpperCase()) {
            case "PICKASSISTANT" -> {
                //System.out.println("Sono in pickAssistant");
                action = inputChecker.checkAssistant(chosenValue);
            }
            case "PICKCLOUD" -> {
                action = inputChecker.checkCloud(chosenValue);
            }
            case "PICKMOVESNUMBER" -> {
                //System.out.println("Entro in pickmovesnumbero");
                action = inputChecker.checkMoves(chosenValue);
            }
            case "PICKSTUDENT" -> {
                //System.out.println("Entro in pickStudent");
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


    /**
     * Method propertyChange overrides the super's method, it handles notifies sent by the CLI
     * @param evt event
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!modelView.getActiveInput()) {
            modelView.getCli().showError("Input error: it's not your turn!");
        } else {
            try {
                if (!action(evt.getPropertyName(), evt.getNewValue().toString())) {
                    modelView.setActivateInput(true);
                }
            } catch (AlreadyPlayedAssistantException e) {
                e.printStackTrace();
            }
        }
    }
}