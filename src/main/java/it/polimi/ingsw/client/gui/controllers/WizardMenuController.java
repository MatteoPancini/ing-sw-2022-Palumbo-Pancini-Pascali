package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.messages.clienttoserver.WizardChoice;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.enumerations.Wizards;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class WizardMenuController implements GUIController {

    private GUI gui;
    @FXML private Button kingButton;
    @FXML private Button forestButton;
    @FXML private Button monachButton;
    @FXML private Button witchButton;
    @FXML private Label label;
    @FXML private ImageView king;
    @FXML private ImageView monach;
    @FXML private ImageView wizard;
    @FXML private ImageView witch;



    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    //method that lifts wizards on mouse dragged
    /*public void liftWizard(ActionEvent e) {
        TranslateTransition t = new TranslateTransition();
        switch(e.getEventType()) {
            case "king" -
        }
    }

    //switch case on click
    public void chooseWizard() {

    }*/

    public void askWizard(List<Wizards> wizards) {
        gui.changeStage("wizardMenu.fxml");


        kingButton.setVisible(false);
        witchButton.setVisible(false);
        forestButton.setVisible(false);
        monachButton.setVisible(false);

        for(Wizards w : wizards) {
            if(w.toString() == "KING") {
                kingButton.setVisible(true);
            }else if(w.toString() == "WITCH") {
                witchButton.setVisible(true);
            }else if(w.toString() == "FOREST") {
                forestButton.setVisible(true);
            }else if(w.toString() == "MONACH") {
                monachButton.setVisible(true);
            }
        }



    }

    public void chooseMonach() {
        Wizards wizardChosen = Wizards.MONACH;
        gui.getClientConnection().sendUserInput(new WizardChoice(wizardChosen));
        gui.changeStage("loading.fxml");
        showWait();
    }

    public void chooseKing() {
        Wizards wizardChosen = Wizards.KING;
        gui.getClientConnection().sendUserInput(new WizardChoice(wizardChosen));
        gui.changeStage("loading.fxml");

    }

    public void chooseWitch() {
        Wizards wizardChosen = Wizards.WITCH;
        gui.getClientConnection().sendUserInput(new WizardChoice(wizardChosen));
        gui.changeStage("loading.fxml");

    }

    public void chooseForest() {
        Wizards wizardChosen = Wizards.FOREST;
        gui.getClientConnection().sendUserInput(new WizardChoice(wizardChosen));
        gui.changeStage("loading.fxml");

    }

    public void showWait() {
        LoadingController loadingController;
        loadingController = (LoadingController) gui.getControllerFromName("loading.fxml");
        loadingController.setText("Please wait... game is about to start :)");
    }


}
