package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

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
}
