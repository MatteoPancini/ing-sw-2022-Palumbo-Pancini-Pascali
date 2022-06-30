package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.messages.clienttoserver.WizardChoice;
import it.polimi.ingsw.model.enumerations.Wizards;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.util.List;
import java.util.Objects;

/**
 * Class WizardMenuController handles the choice of the wizards by showing a new stage
 */
public class WizardMenuController implements GUIController {
    private GUI gui;
    @FXML private Button kingButton;
    @FXML private Button forestButton;
    @FXML private Button monachButton;
    @FXML private Button witchButton;

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    /**
     * Method askWizard asks the user to choose a wizard between the available ones
     * @param wizards available wizards
     */
    public void askWizard(List<Wizards> wizards) {
        gui.changeStage("wizardMenu.fxml");

        kingButton.setVisible(false);
        witchButton.setVisible(false);
        forestButton.setVisible(false);
        monachButton.setVisible(false);

        for(Wizards w : wizards) {
            if(Objects.equals(w.toString(), "KING")) {
                kingButton.setVisible(true);
            }else if(Objects.equals(w.toString(), "WITCH")) {
                witchButton.setVisible(true);
            }else if(Objects.equals(w.toString(), "FOREST")) {
                forestButton.setVisible(true);
            }else if(Objects.equals(w.toString(), "MONACH")) {
                monachButton.setVisible(true);
            }
        }
    }

    /**
     * Method chooseMonach handles the click event on the Monach wizard by sending it to server
     * and then it changes the stage
     */
    public void chooseMonach() {
        Wizards wizardChosen = Wizards.MONACH;
        gui.getClientConnection().sendUserInput(new WizardChoice(wizardChosen));
        gui.changeStage("loading.fxml");
        showWait();
    }
    /**
     * Method chooseKing handles the click event on the King wizard by sending it to server
     * and then it changes the stage
     */
    public void chooseKing() {
        Wizards wizardChosen = Wizards.KING;
        gui.getClientConnection().sendUserInput(new WizardChoice(wizardChosen));
        gui.changeStage("loading.fxml");

    }
    /**
     * Method chooseWitch handles the click event on the Witch wizard by sending it to server
     * and then it changes the stage
     */
    public void chooseWitch() {
        Wizards wizardChosen = Wizards.WITCH;
        gui.getClientConnection().sendUserInput(new WizardChoice(wizardChosen));
        gui.changeStage("loading.fxml");

    }
    /**
     * Method chooseForest handles the click event on the Forest wizard by sending it to server
     * and then it changes the stage
     */
    public void chooseForest() {
        Wizards wizardChosen = Wizards.FOREST;
        gui.getClientConnection().sendUserInput(new WizardChoice(wizardChosen));
        gui.changeStage("loading.fxml");

    }

    /**
     * Method showWait shows a new loading stage while waiting for the next action
     */
    public void showWait() {
        LoadingController loadingController;
        loadingController = (LoadingController) gui.getControllerFromName("loading.fxml");
        loadingController.setText("Please wait... game is about to start :)");
    }
}
