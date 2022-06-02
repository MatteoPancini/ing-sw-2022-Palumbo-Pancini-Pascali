package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class PickController implements GUIController{

    private GUI gui;

    @FXML Button redButton;
    @FXML Button yellowButton;
    @FXML Button blueButton;
    @FXML Button greenButton;
    @FXML Button pinkButton;
    @FXML ImageView redImage;
    @FXML ImageView yellowImage;
    @FXML ImageView blueImage;
    @FXML ImageView greenImage;
    @FXML ImageView pinkImage;



    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }
}
