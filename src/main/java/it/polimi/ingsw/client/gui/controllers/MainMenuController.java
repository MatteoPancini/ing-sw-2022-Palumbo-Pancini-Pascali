package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.constants.Constants;
import javafx.fxml.FXML;

import java.awt.*;

public class MainMenuController implements GUIController {

    private GUI gui;
    @FXML
    private TextField username;
    @FXML private TextField address;
    @FXML private TextField port;
    @FXML private Label confirmation;



    /** Method quit kills the application when the "Quit" button is pressed. */
    public void quit() {
        System.out.println("Thanks for playing! See you next time!");
        System.exit(0);
    }


    public void play() {
        gui.changeState("setup.fxml");
    }

    public void start() {
        if (username.getText().equals("") || address.getText().equals("") || port.getText().equals("")) {
            confirmation.setText("Attention: missing parameters!!!");
        } else if (address.getText().contains(" ")) {
            confirmation.setText("Attention: address must not contain spaces!");
        } else {
            //gui.getModelView().s(username.getText());
            LoadingController loadingController;
            try {
                Constants.setAddress(address.getText());
                Constants.setPort(Integer.parseInt(port.getText()));
            } catch (NumberFormatException e) {
                confirmation.setText("Error: missing parameters!");
                return;
            }


        }
    }







    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }
}
